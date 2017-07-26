package com.xgh.paotui.controller.front;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.dao.ICustomerDao;
import com.xgh.paotui.entity.Customer;
import com.xgh.paotui.services.ICustomerService;
import com.xgh.paotui.services.IOrderService;
import com.xgh.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(value="客户信息API")
@RequestMapping(value = "paotui/front/v1/customer/")
public class CustomerFrontController extends BaseController{

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(CustomerFrontController.class);

    @Autowired
    protected ICustomerService customerService;

    @Autowired
    protected ICustomerDao customerDao;

    @Autowired
    protected IOrderService orderService;

    @ResponseBody
    @RequestMapping(value = "getVerifCodeUse", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取短信验证码(验证账号是否已使用)", httpMethod = "POST", notes = "APP端")
    public String getVerifCodeUse(
            @ApiParam(value = "手机号") @RequestParam(value = "loginName") String loginName) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("loginName",loginName);
        Customer customer = customerDao.login(map);
        if (customer!=null&&customer.getId()>0){
            resultMap = getResultMap("-1","此账号已经使用");
        }else {
            String code = ValidationCode.getSecurityCode(4, 3);
            String content = "您本次注册的验证码为：" + code + "。30分钟内有效，请确认本人操作并注意保密！";
            int flag = ShortMessageUtil.sendMessage(loginName, content);
            if (flag > 0) {
                request.getSession().setAttribute("telephone", loginName);
                request.getSession().setAttribute("validationCode", code);
                resultMap = getResultMap("1", "验证码发送成功！");
            } else {
                resultMap = getResultMap("0", "验证码发送失败！");
            }
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getVerifCodeExistence", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取短信验证码(验证账号是否存在)", httpMethod = "POST", notes = "APP端")
    public String getVerifCodeExistence(
            @ApiParam(value = "手机号") @RequestParam(value = "loginName") String loginName) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("loginName",loginName);
        Customer customer = customerDao.login(map);
        if (customer==null||customer.getId()<1){
            resultMap = getResultMap("-1","用户账号不存在!");
        }else {
            String code = ValidationCode.getSecurityCode(4, 3);
            String content = "您本次注册的验证码为：" + code + "。30分钟内有效，请确认本人操作并注意保密！";
            int flag = ShortMessageUtil.sendMessage(loginName, content);
            if (flag > 0) {
                request.getSession().setAttribute("telephone", loginName);
                request.getSession().setAttribute("validationCode", code);
                resultMap = getResultMap("1", "验证码发送成功！");
            } else {
                resultMap = getResultMap("0", "验证码发送失败！");
            }
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "登录", httpMethod = "POST", notes = "APP端使用")
    public String login(
        @ApiParam(value="用户名") @RequestParam(value = "loginName") String loginName,
        @ApiParam(value="密码") @RequestParam(value = "password") String password,
        HttpServletRequest request) {
            Map<String, Object> loginMap = customerService.login(request);
            return JSONUtil.getJson(loginMap);
    }

    @ResponseBody
    @RequestMapping(value = "register", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "用户注册", httpMethod = "POST", notes = "APP端使用")
    public String register(
        @ApiParam(value = "注册手机号") @RequestParam(value = "loginName") String loginName,
        @ApiParam(value = "短信验证码") @RequestParam(value = "verifCode") String verifCode,
        @ApiParam(value = "密码") @RequestParam(value = "password1") String password1,
        @ApiParam(value = "确认密码") @RequestParam(value = "password2") String password2,
        HttpServletRequest request) {
        Map<String,Object> registerMap= customerService.register(request);
        return JSONUtil.getJson(registerMap);
    }

    @ResponseBody
    @RequestMapping(value = "modifyPassword", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "修改密码", httpMethod = "POST", notes = "APP端使用")
    public String modifyPassword(
            @ApiParam(value = "用户账号") @RequestParam(value = "loginName") String loginName,
            @ApiParam(value = "原密码") @RequestParam(value = "oldPassword") String oldPassword,
            @ApiParam(value = "新密码") @RequestParam(value = "newPassword") String newPassword,
            HttpServletRequest request) {
        Map<String,Object> modifyPasswordMap= customerService.modifyPassword(request);
        return JSONUtil.getJson(modifyPasswordMap);
    }

    @ResponseBody
    @RequestMapping(value = "forgetPassword", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "忘记密码", httpMethod = "POST", notes = "APP端使用")
    public String forgetPassword(
            @ApiParam(value = "用户账号") @RequestParam(value = "loginName") String loginName,
            @ApiParam(value = "短信验证码") @RequestParam(value = "verifCode") String verifCode,
            @ApiParam(value = "新密码") @RequestParam(value = "newPassword") String newPassword,
            HttpServletRequest request) {
        Map<String,Object> forgetPasswordMap= customerService.forgetPassword(request);
        return JSONUtil.getJson(forgetPasswordMap);
    }

    @ResponseBody
    @RequestMapping(value = "finishcCustomer", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "完善用户信息", httpMethod = "POST", notes = "APP端使用")
    public String finishcCustomer(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "昵称") @RequestParam(value = "nickName",required = false) String nickName,
            @ApiParam(value = "真实姓名") @RequestParam(value = "realName",required = false) String realName,
            @ApiParam(value = "性别") @RequestParam(value = "sex",required = false) String sex,
            @ApiParam(value = "身份证号") @RequestParam(value = "idCardNo",required = false) String idCardNo,
            @ApiParam(value = "手机号") @RequestParam(value = "mobilePhone",required = false) String mobilePhone,
            @ApiParam(value = "头像") @RequestPart(value = "headPicUrl",required = false) MultipartFile headPicUrl,
            HttpServletRequest request){
            long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
            Map<String,Object> finishcCustomerMap = customerService.finishcCustomer(request,customerId);
        return JSONUtil.getJson(finishcCustomerMap);
    }

    @ResponseBody
    @RequestMapping(value = "replacePhoneNumberOld", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "更换手机号(验证原手机号)", httpMethod = "POST", notes = "APP端使用")
    public String replacePhoneNumberOld(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "原手机号") @RequestParam(value = "loginName") String loginName,
            @ApiParam(value = "短信验证码") @RequestParam(value = "verifCode") String verifCode,
            HttpServletRequest request) {
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String,Object> customer= customerService.replacePhoneNumberOld(request,customerId);
        return JSONUtil.getJson(customer);
    }

    @ResponseBody
    @RequestMapping(value = "replacePhoneNumberNew", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "更换手机号(确定更换)", httpMethod = "POST", notes = "APP端使用")
    public String replacePhoneNumberNew(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "更换手机号") @RequestParam(value = "loginName") String loginName,
            @ApiParam(value = "短信验证码") @RequestParam(value = "verifCode") String verifCode,
            HttpServletRequest request) {
            long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String,Object> customer= customerService.replacePhoneNumberNew(request,customerId);
        return JSONUtil.getJson(customer);
    }

    @ResponseBody
    @RequestMapping(value = "getCustomer", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取用户个人信息", httpMethod = "GET", notes = "APP端使用")
    public String getCustomer(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        long customerId=getCustomerIdByAppToken(token);
        Customer customer= customerService.get(customerId);
        if (customer!=null&&customer.getId()>0){
            map.put("loginName",customer.getLoginName());
            map.put("nickName",StringUtil.convertNullToEmpty(customer.getNickName()));
            map.put("realName",customer.getRealName());
            map.put("idCardNo",customer.getIdCardNo());
            map.put("sex",customer.getSex());
            map.put("mobilePhone",StringUtil.convertNullToEmpty(customer.getMobilePhone()));
            map.put("balance",customer.getBalance());
            map.put("weixinLoginName",StringUtil.convertNullToEmpty(customer.getWeixinLoginName()));
            map.put("registerTime",customer.getRegisterTime());
            map.put("belongCity",customer.getBelongCity());
            map.put("newestDeliverAddress", StringUtil.convertNullToEmpty(customer.getNewestDeliverAddress()));
            if (customer.getHeadPicUrl()!=null&&customer.getHeadPicUrl()!=""){
                String headPicUrl = ConstantUtil.SERVER_URL+customer.getHeadPicUrl();
                map.put("headPicUrl",headPicUrl);
            }else {
                map.put("headPicUrl","");
            }
            mapList.add(map);
            resultMap = getResultMap("1","获取用户个人信息成功",mapList);
        }else {
            resultMap = getResultMap("0","获取用户个人信息失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getBalance", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取账户余额", httpMethod = "GET", notes = "APP端使用")
    public String getBalance(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        long customerId=getCustomerIdByAppToken(token);
        Customer customer= customerService.get(customerId);
        if (customer!=null&&customer.getId()>0){
            resultMap.put("balance",customer.getBalance());
            resultMap=getResultMap("1","获取账户余额成功",resultMap);
        }else {
            resultMap=getResultMap("0","获取账户余额失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getMyOrder", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "我的订单", httpMethod = "GET", notes = "APP端使用")
    public String getMyOrder(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "订单状态") @RequestParam(required = false,value = "orderStatus") String orderStatus,
            @ApiParam(value = "页") @RequestParam(value = "page") int page,
            @ApiParam(value = "每页数量") @RequestParam(value = "pagesize") int pagesize) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        long customerId=getCustomerIdByAppToken(token);
        resultMap.put("page",page);
        resultMap.put("pagesize",pagesize);
        resultMap.put("customerId",customerId);
        resultMap.put("orderStatus",orderStatus);
        Map<String,Object> myOrderList= orderService.getMyOrder(resultMap);
        return JSONUtil.getJson(myOrderList);
    }

}
