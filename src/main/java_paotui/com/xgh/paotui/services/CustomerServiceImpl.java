package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.dao.*;
import com.xgh.paotui.entity.*;
import com.xgh.paotui.weixin.pay.WeiChartUtil;
import com.xgh.security.MD5Util;
import com.xgh.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("customerService")
public class CustomerServiceImpl extends BaseService implements ICustomerService{

	private Logger logger = Logger.getLogger(CustomerServiceImpl.class);

	@Autowired
	protected ICustomerDao customerDao;

	@Autowired
	protected IDeliveryManDao deliveryManDao;

	@Autowired
	protected IDeliverManMarketDao deliverManMarketDao;

	@Autowired
	protected IOpenCityDao openCityDao;

	@Autowired
	protected ICapitalLogDao capitalLogDao;


	public Customer get(Long id){
		return customerDao.get(id);
	}

	public int add(Customer customer){
		return customerDao.add(customer);
	}

	/**
	 * 微信端注册
	 * @param customer
	 * @param openid
     * @return
     */
	public int addWithOpenid(Customer customer,String openid){
		Map<String,Object> map1 = new HashMap<String, Object>();
		map1.put("token",openid);
		List<Customer>  customers=customerDao.getList(map1);
		if(customers.size()>0){
			for(Customer data:customers){
				//如果微信openid登录授权。已经授权给其他手机号，先取消
				data.setToken("");
				//更新微信授权id
				customerDao.update(data);

			}
		}
		customer.setToken(openid);
		int flag = customerDao.add(customer);
		if (flag>0){
			Map<String,Object> map2 = new HashMap<String, Object>();
			map2.put("openId",openid);
			List<DeliverManMarket> deliverManMarketList = deliverManMarketDao.getList(map2);
			if (deliverManMarketList!=null&&deliverManMarketList.size()>0){
				DeliverManMarket deliverManMarket = deliverManMarketList.get(0);
				deliverManMarket.setCustomerId(customer.getId());
				deliverManMarketDao.update(deliverManMarket);
			}
		}
		return flag;
	}

	public List<Customer> getList(Map<String, Object> map)
	{
		return  customerDao.getList(map);
	}

	public Map<String,Object> getGridList(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> gridMap = new HashMap<String, Object>();
		String page = request.getParameter("page");
		String pagesize = request.getParameter("pagesize");
		String loginName = request.getParameter("loginName");
		String deliveryManloginName=request.getParameter("deliveryManloginName");
		map.put("page", Integer.parseInt(page));
		map.put("pagesize", Integer.parseInt(pagesize));
		map.put("loginName",loginName);
		map.put("deliveryManloginName",deliveryManloginName);
		SysUser sysUser = getCurrentUser(request);
		if (sysUser.getOpenCityId()!=null&&sysUser.getOpenCityId()!=0){
			OpenCity openCity = openCityDao.get(sysUser.getOpenCityId());
			if (openCity!=null&&openCity.getId()>0){
				map.put("belongCity",openCity.getCityName());
			}
		}
		List<Customer> dataList = customerDao.getListPage(map);
		if (dataList == null) {
			dataList = new ArrayList<Customer>();
		}
		gridMap.put("Rows", dataList);
		gridMap.put("Total", customerDao.getRows(map));
		return gridMap;
	}


	/**
	 * 登录接口
	 * @param request
	 * @return
     */
	public Map<String,Object> login(HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		resultMap.put("loginName",loginName);
		Customer customer = customerDao.login(resultMap);
		if (customer!=null&&customer.getId()>0){
			if (password.equals(customer.getPassword())){
				Map token=new HashMap();
				AppToken appToken = new AppToken();
				appToken.setId(customer.getId());
				appToken.setAppType("customer");
				token.put("token",JWT.sign(appToken,30L * 24L * 3600L * 1000L));
				resultMap=getResultMap("1","登录成功!",token);
			}else {
				resultMap=getResultMap("0","密码错误!");
			}
		}else {
			resultMap=getResultMap("-1","账号不存在!");
		}
		return resultMap;
	}

	/**
	 * 注册接口
	 * @param request
     * @return
     */
	public Map<String,Object> register(HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String loginName = request.getParameter("loginName");
		String verifCode = request.getParameter("verifCode");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		resultMap.put("loginName",loginName);
		Customer customer = customerDao.login(resultMap);
		if (customer!=null&&customer.getId()>0){
			resultMap = getResultMap("-1","此账号已经被注册!");
		}else {
			if (!password1.equals(password2)){
				resultMap=getResultMap("-2","密码不一致，请重新输入!");
			}else {
				String telephone = (String) request.getSession().getAttribute("telephone");
				String verifCode_ = (String) request.getSession().getAttribute("validationCode");
				if (verifCode_==null||"".equals(verifCode_)){
					resultMap = getResultMap("-3", "验证码已失效!");
				}else if (!loginName.equals(telephone)){
					resultMap = getResultMap("-5", "手机号不一致!");
				}else if (!verifCode.equals(verifCode_)){
					resultMap = getResultMap("-4", "验证码错误!");
				}else {
					Customer customer1 = new Customer();
					customer1.setLoginName(loginName);
					customer1.setPassword(MD5Util.getMD5(password1));
					customer1.setRegisterTime(new Date());
					customer1.setStatus(1);
					//初始账户余额0元
					customer1.setBalance(new BigDecimal(0));
					int flag = customerDao.add(customer1);
					if (flag>0){
						resultMap = getResultMap("1", "注册成功!");
					}else {
						resultMap = getResultMap("0", "注册失败!");
					}
				}
			}
		}
		return resultMap;
	}

	/**
	 * 修改密码接口
	 * @param request
	 * @return
     */
	public Map<String,Object> modifyPassword(HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String loginName = request.getParameter("loginName");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		resultMap.put("loginName",loginName);
		Customer customer = customerDao.login(resultMap);
		if (customer!=null&&customer.getId()>0){
			if (oldPassword.equals(newPassword)){
				resultMap = getResultMap("-1","密码一致，请重新输入！");
			}else {
				if (!oldPassword.equals(customer.getPassword())){
					resultMap = getResultMap("-1","原密码不正确！");
				}else {
					customer.setPassword(newPassword);
					int falg = customerDao.update(customer);
					if (falg>0){
						resultMap = getResultMap("1","密码修改成功！");
					}else{
						resultMap = getResultMap("0","密码修改失败！");
					}
				}
			}
		}else {
			resultMap = getResultMap("-1","用户账号不存在！");
		}
		return resultMap;
	}

	/**
	 * 忘记密码接口
	 * @param request
	 * @return
     */
	public Map<String,Object> forgetPassword(HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String loginName = request.getParameter("loginName");
		String verifCode = request.getParameter("verifCode");
		String newPassword = request.getParameter("newPassword");
		resultMap.put("loginName",loginName);
		Customer customer = customerDao.login(resultMap);
		if (customer!=null&&customer.getId()>0){
			String telephone = (String) request.getSession().getAttribute("telephone");
			String verifCode_ = (String) request.getSession().getAttribute("validationCode");
			if (verifCode_==null||"".equals(verifCode_)){
				resultMap = getResultMap("-2","验证码已失效！");
			}else if (!verifCode.equals(verifCode_)){
				resultMap = getResultMap("-3","验证码错误！");
			}else if (!loginName.equals(telephone)){
				resultMap = getResultMap("-4", "手机号不一致!");
			}else {
				customer.setPassword(newPassword);
				int falg = customerDao.update(customer);
				if (falg>0){
					resultMap = getResultMap("1","重置密码成功！");
				}else {
					resultMap = getResultMap("0","重置密码失败！");
				}
			}
		}else {
			resultMap = getResultMap("-1","用户账号不存在！");
		}
		return resultMap;
	}

	/**
	 * 完善用户信息接口
	 * @param request
	 * @return
     */
	public Map<String,Object> finishcCustomer(HttpServletRequest request,long customerId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String nickName = request.getParameter("nickName");
		String realName = request.getParameter("realName");
		String sex = request.getParameter("sex");
		String idCardNo = request.getParameter("idCardNo");
		String mobilePhone= request.getParameter("mobilePhone");
		try {
			Customer customer = customerDao.get(customerId);
			if (customer != null && customer.getId() > 0) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				Iterator fileNames = multipartRequest.getFileNames();
				if (fileNames!=null&&fileNames.hasNext()){
					String name = (String) fileNames.next();
					MultipartFile myfile = multipartRequest.getFile(name);
					if (myfile.isEmpty()) {
						logger.info("文件未上传");
					} else {
						String OriginalFileName = myfile.getOriginalFilename();
						String saveName = DateUtil.getSystemTime().getTime() + ""+ OriginalFileName.substring(OriginalFileName.lastIndexOf("."), OriginalFileName.length());
						SimpleDateFormat formatdate = new SimpleDateFormat("yyyy/MM/dd/");
						String relative_path = formatdate.format(new Date());
						String realPath = ConstantUtil.SAVE_PATH + "/" + "customer" + "/" + relative_path;
						customer.setHeadPicUrl("/" + "customer" + "/" + relative_path + saveName);
						File filePath = new File(realPath);
						if (!filePath.exists()) {
							filePath.mkdirs();
						}
						FileUtil.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, saveName));
					}
				}
				customer.setNickName(nickName);
				customer.setRealName(realName);
				customer.setSex(sex);
				customer.setIdCardNo(idCardNo);
				customer.setMobilePhone(mobilePhone);
				int flag = customerDao.update(customer);
				if (flag > 0) {
					resultMap = getResultMap("1", "完善用户信息成功");
				} else {
					resultMap = getResultMap("0", "完善用户信息失败");
				}
			}else {
				resultMap = getResultMap("-1", "用户不存在");
			}
		} catch (IOException var18) {
			var18.printStackTrace();
			logger.error(var18.getMessage(), var18);
		}
		return resultMap;
	}

	/**
	 * 更换手机号(验证原手机号)接口
	 * @param request
	 * @return
     */
	public Map<String,Object> replacePhoneNumberOld(HttpServletRequest request,long customerId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String loginName = request.getParameter("loginName");
		String verifCode = request.getParameter("verifCode");
		Customer customer = customerDao.get(customerId);
		if (customer!=null&&customer.getId()>0){
			if (customer.getLoginName().equals(loginName)){
				String telephone = (String) request.getSession().getAttribute("telephone");
				String verifCode_ = (String) request.getSession().getAttribute("validationCode");
				if (verifCode_==null||"".equals(verifCode_)){
					resultMap = getResultMap("-2","验证码已失效");
				}else if (!verifCode.equals(verifCode_)){
					resultMap = getResultMap("-3","验证码错误");
				}else if (!loginName.equals(telephone)){
					resultMap = getResultMap("-4", "手机号不一致");
				}else {
					resultMap = getResultMap("1","原手机号验证成功");
				}
			}else {
				resultMap = getResultMap("0","原手机号不正确");
			}
		}else {
			resultMap = getResultMap("-1","用户不存在");
		}
		return resultMap;
	}

	/**
	 *更换手机号(确认更换)接口
	 * @param request
	 * @return
     */
	public Map<String,Object> replacePhoneNumberNew(HttpServletRequest request,long customerId){
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String loginName = request.getParameter("loginName");
		String verifCode = request.getParameter("verifCode");
		Customer customer = customerDao.get(customerId);
		if (customer!=null&&customer.getId()>0){
			map.put("loginName",loginName);
			Customer customer1 = customerDao.login(map);
			if (customer1!=null&&customer1.getId()>0){
				resultMap = getResultMap("-1","此手机号已使用");
			}else {
				String telephone = (String) request.getSession().getAttribute("telephone");

				String verifCode_ = (String) request.getSession().getAttribute("validationCode");
				if (verifCode_==null||"".equals(verifCode_)){
					resultMap = getResultMap("-2","验证码已失效！");
				}else if (!verifCode.equals(verifCode_)){
					resultMap = getResultMap("-3","验证码错误！");
				}else if (!loginName.equals(telephone)){
					resultMap = getResultMap("-4", "手机号不一致");
				}else {
					customer.setLoginName(loginName);
					int falg = customerDao.update(customer);
					if (falg>0){
						resultMap = getResultMap("1","更换手机号成功");
					}else {
						resultMap = getResultMap("0","更换手机号失败");
					}
				}
			}
		}else {
			resultMap = getResultMap("-1","用户不存在");
		}
		return resultMap;
	}


	/**
	 * 通过支付宝充值
	 * @param capitalLogId
	 * @param trade_no 支付宝交易号
	 * @return 1：成功；2：失败
	 */
	public int addBalanceWithAliPay(long capitalLogId,String trade_no){
		try{
			CapitalLog capitalLog = capitalLogDao.get(capitalLogId);
			Customer customer = customerDao.get(capitalLog.getAccountId());
			BigDecimal accountMoney=PaotuiUtil.getAccountMoneyByRealMoney(capitalLog.getChangeMoney(),Integer.parseInt(capitalLog.getRemark()));
			customer.setBalance(customer.getBalance().add(accountMoney));
			customerDao.update(customer);
			capitalLog.setTradeNo(trade_no);
			capitalLog.setCapitalChangeReason("支付宝充值成功");
			capitalLogDao.update(capitalLog);
		}catch(Exception e){
			e.printStackTrace();
			return  2;
		}
		return  1;
	}

	/**
	 * 通过微信充值
	 * @param capitalLogId
	 * @param trade_no 微信交易号
	 * @param total_fee 订单总金额，单位为分
	 * @return 1：成功；2：失败；9:已通知
	 */
	public int addBalanceWithWeixinPay(long capitalLogId,String trade_no,String total_fee){
		try{
			CapitalLog capitalLog = capitalLogDao.get(capitalLogId);
			//校验微信支付成功通知是否多次发送给商户系统
			if(capitalLog.getData2()==1){
				return  9;
			}
			//校验返回的订单金额是否与商户侧的订单金额一致
			if(!total_fee.equals(WeiChartUtil.changeToFen(capitalLog.getChangeMoney().toString()))){
				return  2;
			}
			Customer customer = customerDao.get(capitalLog.getAccountId());
			//虚拟金额
			BigDecimal accountMoney=PaotuiUtil.getAccountMoneyByRealMoney(capitalLog.getChangeMoney(),Integer.parseInt(capitalLog.getRemark()));
			customer.setBalance(customer.getBalance().add(accountMoney));
			customerDao.update(customer);
			/*更新资金日志表 */
			capitalLog.setTradeNo(trade_no);
			capitalLog.setData2(1);
			capitalLog.setCapitalChangeReason("微信充值成功");
			capitalLogDao.update(capitalLog);
		}catch(Exception e){
			e.printStackTrace();
			return  2;
		}
		return  1;
	}


	/**
	 * 微信端登录接口，绑定微信openid
	 * @param loginName 用户名
	 * @param password 密码
	 * @param openid 微信端openid
	 * @return
	 */
	public Map<String,Object> updateOfWeixinLogin(String loginName, String password,String openid){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("loginName",loginName);
		Customer customer = customerDao.login(map);
		if (customer!=null&&customer.getId()>0){
			if (MD5Util.getMD5(password).equals(customer.getPassword())){
				Map<String,Object> map1 = new HashMap<String, Object>();
				map1.put("token",openid);
				List<Customer>  customers=customerDao.getList(map1);
				if(customers.size()>0){
					for(Customer data:customers){
						if(customer.getId()!=data.getId().longValue()){
							//如果微信openid登录授权。已经授权给其他手机号，先取消
							data.setToken("");
							//更新微信授权id
							customerDao.update(data);
						}
					}
				}
				customer.setToken(openid);
				customerDao.update(customer);
				Map token=new HashMap();
				AppToken appToken = new AppToken();
				appToken.setId(customer.getId());
				appToken.setAppType("weixin");
				token.put("token",JWT.sign(appToken,30L * 24L * 3600L * 1000L));
				resultMap=getResultMap("1","微信登录成功!",token);
			}else {
				resultMap=getResultMap("0","密码错误!");
			}
		}else {
			resultMap=getResultMap("-1","账号不存在!");
		}
		return resultMap;
	}
}