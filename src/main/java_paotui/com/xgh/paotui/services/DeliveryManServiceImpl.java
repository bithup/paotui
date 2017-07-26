package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.paotui.dao.*;
import com.xgh.paotui.entity.*;
import com.xgh.security.MD5Util;
import com.xgh.util.ConstantUtil;
import com.xgh.util.DictUtils;
import com.xgh.util.JWT;
import com.xgh.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;


@Service("deliveryManService")
public class DeliveryManServiceImpl extends BaseService implements IDeliveryManService{

	@Autowired
	protected IDeliveryManDao deliveryManDao;

	@Autowired
	protected IDeliveryManAuthDao deliveryManAuthDao;

	@Autowired
	protected IDeliveryManWithdrawalsDao deliveryManWithdrawalsDao;

	@Autowired
	protected IDeliveryManStateDao deliveryManStateDao;

	@Autowired
	protected IOpenCityDao openCityDao;

	@Autowired
	protected IOrderDao orderDao;

	@Autowired
	protected IFileDataServiceNew fileDataServiceNew;

	public DeliveryMan get(Long id){
		return deliveryManDao.get(id);
	}

	public int update(DeliveryMan deliveryMan){
		return deliveryManDao.update(deliveryMan);
	}

	public Map<String,Object> getGridList(HttpServletRequest request,String cityId){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> gridMap = new HashMap<String, Object>();
		String page = request.getParameter("page");
		String pagesize = request.getParameter("pagesize");
		String realName = request.getParameter("realName");
		String mobilePhone = request.getParameter("mobilePhone");
		map.put("page", Integer.parseInt(page));
		map.put("pagesize", Integer.parseInt(pagesize));
		map.put("realName",realName);
		map.put("mobilePhone",mobilePhone);
		if(StringUtil.isNotEmpty(cityId)){
			map.put("belongCityId",cityId);
		}

		List<Map<String, Object>> dataList = deliveryManDao.getListMapPage(map);
		if (dataList == null) {
			dataList = new ArrayList<Map<String, Object>>();
		}
		for (Map<String,Object> dataList_:dataList){
			long deliveryManid = Long.parseLong(dataList_.get("id").toString());
			long belongCityId = Long.parseLong(dataList_.get("belongCityId").toString());
			DeliveryManAuth deliveryManAuth = deliveryManAuthDao.getDeliveryManAuth(deliveryManid);
			OpenCity openCity = openCityDao.get(belongCityId);
			if (deliveryManAuth!=null){
				dataList_.put("idCardNo",deliveryManAuth.getIdCardNo());
			}
			if (openCity!=null){
				dataList_.put("cityName",openCity.getCityName());
			}
			if (String.valueOf(dataList_.get("belongType"))!=null) {
				String belongType = String.valueOf(dataList_.get("belongType"));
				dataList_.put("belongType", DictUtils.getDictLabel(belongType,"paotui_belong_type",""));
			}
			if (String.valueOf(dataList_.get("authStatus"))!=null) {
				String authStatus = String.valueOf(dataList_.get("authStatus"));
				dataList_.put("authStatus", DictUtils.getDictLabel(authStatus,"paotui_auth_status",""));
			}
		}
		gridMap.put("Rows", dataList);
		gridMap.put("Total", deliveryManDao.getRows(map));
		return gridMap;
	}

	public int save(HttpServletRequest request, DeliveryMan deliveryMan){
		if(deliveryMan!=null && deliveryMan.getId()>0){
			DeliveryMan deliveryMan1 = deliveryManDao.get(deliveryMan.getId());
			deliveryMan1.setBelongType(deliveryMan.getBelongType());
			deliveryMan1.setStarLevel(deliveryMan.getStarLevel());
			deliveryMan1.setBelongCityId(deliveryMan.getBelongCityId());
			deliveryMan1.setAuthStatus(deliveryMan.getAuthStatus());
			int flag = deliveryManDao.update(deliveryMan1);
			if (flag>0){
				//当认证通过时需要在deliveryManState表中添加一条信息
				if (deliveryMan.getAuthStatus()==1){
					DeliveryManState deliveryManState = new DeliveryManState();
					deliveryManState.setId(deliveryMan1.getId());
					deliveryManState.setDeliverManId(deliveryMan1.getId());
					deliveryManState.setBelongCityId(deliveryMan1.getBelongCityId());
					deliveryManState.setWorkStatus(2);
					deliveryManState.setGainNewOrder(2);
					deliveryManState.setStateUpdateTime(new Date());
					deliveryManState.setStatus(1);
					deliveryManStateDao.add(deliveryManState);
				}
			}
			return flag;
		}else{
			return 0;
		}
	}

	/**
	 * 添加图片
	 *
	 * @param request
	 */
	private boolean addFileData(HttpServletRequest request,DeliveryManAuth deliveryManAuth) {
		String[] picData_array = request.getParameterValues("picData");
		//图片信息
		List<FileData> fileDataList = fileDataServiceNew.saveFiles(request, picData_array, deliveryManAuth.getId(), ConstantUtil.FileUploadCode.DeliveryMan,
				0,0);
		if (fileDataList != null && fileDataList.size() > 0) {
			FileData fileData = fileDataList.get(0);
			deliveryManAuth.setIdCardPicture1("/" + fileData.getRelativePath() + "/" + fileData.getFileName());
			fileData = fileDataList.get(1);
			deliveryManAuth.setIdCardPicture2("/" + fileData.getRelativePath() + "/" + fileData.getFileName());
			fileData = fileDataList.get(2);
			deliveryManAuth.setIdCardPicture3("/" + fileData.getRelativePath() + "/" + fileData.getFileName());
			deliveryManAuthDao.update(deliveryManAuth);
		}
		return true;
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
		DeliveryMan deliveryMan = deliveryManDao.login(resultMap);
		if (deliveryMan!=null&&deliveryMan.getId()>0){
			if (MD5Util.getMD5(password).equals(deliveryMan.getPassword())){
				Map token=new HashMap();
				AppToken appToken = new AppToken();
				appToken.setId(deliveryMan.getId());
				appToken.setAppType("deliveryMan");
				token.put("token", JWT.sign(appToken,30L * 24L * 3600L * 1000L));
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
		String belongType = request.getParameter("belongType");
		String verifCode = request.getParameter("verifCode");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		resultMap.put("loginName",loginName);
		DeliveryMan deliveryMan = deliveryManDao.login(resultMap);
		if (deliveryMan!=null && deliveryMan.getId()>0){
			resultMap = getResultMap("-1","此账号已经被注册!");
		}else {
			if (!password1.equals(password2)){
				resultMap=getResultMap("-1","密码不一致，请重新输入!");
			} else {
				String telephone = (String) request.getSession().getAttribute("telephone");
				String verifCode_ = (String) request.getSession().getAttribute("validationCode");
				if (verifCode_==null||"".equals(verifCode_)){
					resultMap = getResultMap("-1", "验证码已失效!");
				}else if (!loginName.equals(telephone)){
					resultMap = getResultMap("-1", "手机号不一致!");
				}else if (!verifCode.equals(verifCode_)){
					resultMap = getResultMap("-1", "验证码错误!");
				}else {
					DeliveryMan deliveryMan1 = new DeliveryMan();
					deliveryMan1.setLoginName(loginName);
					deliveryMan1.setBelongType(Integer.parseInt(belongType));
					deliveryMan1.setPassword(MD5Util.getMD5(password1));
					deliveryMan1.setBalance(new BigDecimal("0"));
					deliveryMan1.setAuthStatus(2);
					deliveryMan1.setStatus(1);
					int flag = deliveryManDao.add(deliveryMan1);
					if (flag>0){
						DeliveryManAuth deliveryManAuth = new DeliveryManAuth();
						deliveryManAuth.setDeliveryManId(deliveryMan1.getId());
						deliveryManAuth.setStatus(1);
						deliveryManAuth.setWeixinAuthStatus(0);//设置为未认证
						deliveryManAuthDao.add(deliveryManAuth);
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
	public Map<String,Object> updatePassword(HttpServletRequest request,long deliveryManId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		DeliveryMan deliveryMan = deliveryManDao.get(deliveryManId);
		if (deliveryMan!=null&&deliveryMan.getId()>0){
			if (oldPassword.equals(newPassword)){
				resultMap = getResultMap("-1","密码一致，请重新输入！");
			}else {
				if (!MD5Util.getMD5(oldPassword).equals(deliveryMan.getPassword())){
					resultMap = getResultMap("-1","原密码不正确！");
				}else {
					deliveryMan.setPassword(MD5Util.getMD5(newPassword));
					int falg = deliveryManDao.update(deliveryMan);
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
		DeliveryMan deliveryMan = deliveryManDao.login(resultMap);
		if (deliveryMan!=null&&deliveryMan.getId()>0){
			String verifCode_ = (String) request.getSession().getAttribute("validationCode");
			if (verifCode_==null||"".equals(verifCode_)){
				resultMap = getResultMap("-1","验证码已失效！");
			}else {
				if (!verifCode.equals(verifCode_)){
					resultMap = getResultMap("-1","验证码错误！");
				}else {
					deliveryMan.setPassword(MD5Util.getMD5(newPassword));
					int falg = deliveryManDao.update(deliveryMan);
					if (falg>0){
						resultMap = getResultMap("1","重置密码成功！");
					}else {
						resultMap = getResultMap("0","重置密码失败！");
					}
				}
			}
		}else {
			resultMap = getResultMap("-1","用户账号不存在！");
		}
		return resultMap;
	}

	/**
	 * 完善跑客信息
	 * @param deliveryMan
	 * @param deliveryManAuth
     * @return
     */
	public int saveDeliveryMan(DeliveryMan deliveryMan,DeliveryManAuth deliveryManAuth){
		int flag = deliveryManDao.update(deliveryMan);
		if (flag>0){
			DeliveryManAuth deliveryManAuth1 = deliveryManAuthDao.getDeliveryManAuth(deliveryMan.getId());
			if (deliveryManAuth1==null){
				deliveryManAuth.setDeliveryManId(deliveryMan.getId());
				deliveryManAuth.setStatus(1);
				deliveryManAuthDao.add(deliveryManAuth);
			}else {
				deliveryManAuth1.setIdCardNo(deliveryManAuth.getIdCardNo());
				deliveryManAuthDao.update(deliveryManAuth1);
			}
		}
		return flag;
	}

	/**
	 * 更换手机号接口(确认原手机号)
	 * @param request
	 * @return
     */
	public Map<String,Object> replacePhoneNumberOld(HttpServletRequest request,long deliveryManId){
		Map<String,Object>resultMap=new HashMap<String, Object>();
		String loginName=request.getParameter("loginName");
		String verifCode=request.getParameter("verifCode");
		DeliveryMan deliveryMan = deliveryManDao.get(deliveryManId);
		if(deliveryMan!=null&&deliveryMan.getId()>0){
			if(deliveryMan.getLoginName().equals(loginName)){
				String verifCode_=(String)request.getSession().getAttribute("validationCode");
				if(verifCode_==null||"".equals(verifCode_)){
					resultMap=getResultMap("-1","验证码已失效");
				}else{
					if(!verifCode.equals(verifCode_)){
						resultMap=getResultMap("-1","验证码错误");
					}else{
						resultMap=getResultMap("1","原手机号验证成功");
					}
				}

			}else{
				resultMap=getResultMap("-1","原手机号不正确");}
		}else{
			resultMap=getResultMap("0","用户不存在");
		}
		return resultMap;
	}

	/**
	 * 更换手机号接口(更换新手机号)
	 * @param request
	 * @return
     */
	public Map<String,Object> replacePhoneNumberNew(HttpServletRequest request,long deliveryManId){
		Map<String,Object>resultMap=new HashMap<String, Object>();
		String loginName=request.getParameter("loginName");
		String verifCode=request.getParameter("verifCode");
		DeliveryMan deliveryMan=deliveryManDao.get(deliveryManId);
		if(deliveryMan!=null&&deliveryMan.getId()>0){
			String verifCode_=(String)request.getSession().getAttribute("validationCode");
			if(verifCode_==null||"".equals(verifCode_)){
				resultMap=getResultMap("-1","验证码已失效");
			}else{
				if(!verifCode.equals(verifCode_)){
					resultMap=getResultMap("-1","验证码错误");
				}else{
					deliveryMan.setLoginName(loginName);
					int flag = deliveryManDao.update(deliveryMan);
					if (flag>0){
						resultMap=getResultMap("1","更换手机号成功");
					}else {
						resultMap=getResultMap("0","更换手机号失败");
					}
				}
			}
		}else{
			resultMap=getResultMap("0","用户不存在");
		}
		return resultMap;
	}

	/**
	 * 获取可抢订单
	 * @param map
	 * @return
     */
	public List<Map<String,Object>> getCanRobOrder(Map<String,Object> map){
		return orderDao.getMyOrder(map);
	}


	/**
	 *更新微信认证状态
	 * @param deliveryManId
	 * @param weixinOpenid
     * @return 0:失败；1:成功; 2:用户不存在
     */
	public int updateOfWeixinAuth( Long deliveryManId,String weixinOpenid){
		int ret=1;
		DeliveryMan deliveryMan = deliveryManDao.get(deliveryManId);
		if(deliveryMan==null){
			ret=2;
			return  ret;
		}
		deliveryMan.setToken(weixinOpenid);
		ret=deliveryManDao.update(deliveryMan);

		Map<String, Object> map= new HashMap<String, Object>();
		map.put("deliveryManId",deliveryManId);
		List<DeliveryManAuth>  deliveryManAuths = deliveryManAuthDao.getList(map);
		if(deliveryManAuths!=null && deliveryManAuths.size()>0){
			DeliveryManAuth  deliveryManAuth=deliveryManAuths.get(0);
			deliveryManAuth.setWeixinOpenId(weixinOpenid);
			deliveryManAuth.setWeixinAuthTime(new Date());
			deliveryManAuth.setWeixinAuthStatus(1);//已认证

			deliveryManAuthDao.update(deliveryManAuth);
		}

		return ret;
	}


	/**
	 *申请提现处理
	 * @param deliveryManId
	 * @param money 提现金额
	 * @return 0:失败；1:成功; 2:用户不存在;3:提现金额大于账户余额
	 */
	public int updateOfApplyWithdrawals(Long deliveryManId,BigDecimal money){
		int flag=1;
		DeliveryMan deliveryMan = deliveryManDao.get(deliveryManId);
		if(deliveryMan!=null&&deliveryMan.getId()>0){
			if(money.compareTo(deliveryMan.getBalance())==1){
				flag=3;
			}else {
				DeliveryManWithdrawals deliveryManWithdrawals= new DeliveryManWithdrawals();
				deliveryManWithdrawals.setDeliverManId(deliveryMan.getId());
				deliveryManWithdrawals.setWithdrawalsBeforeMoney(deliveryMan.getBalance());
				deliveryManWithdrawals.setWithdrawalsMoney(money);
				deliveryManWithdrawals.setWithdrawalsAfterMoney(deliveryMan.getBalance().subtract(money));
				deliveryManWithdrawals.setWithdrawalsDate(new Date());
				deliveryManWithdrawals.setCreateDate(new Date());
				deliveryManWithdrawals.setCheckState(2);
				deliveryManWithdrawals.setStatus(1);
				flag= deliveryManWithdrawalsDao.add(deliveryManWithdrawals);
				if(flag==1){
					//提现后的账户余额
					deliveryMan.setBalance(deliveryMan.getBalance().subtract(money));
					flag=deliveryManDao.update(deliveryMan);
				}
			}
		}else {
			flag=2;
		}
		return flag;
	}


	/**
	 * 更新推广二维码信息
	 * @param deliveryManId
	 * @param fileName 二维码文件名
     * @return
     */
	public int updateMarketQrcode(long deliveryManId,String  fileName){
		DeliveryMan deliveryMan  = deliveryManDao.get(deliveryManId);
		deliveryMan.setQrCode(fileName);
		return deliveryManDao.update(deliveryMan);
	}
}