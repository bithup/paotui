package com.xgh.paotui.controller.front.weixinFront;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.AppToken;
import com.xgh.paotui.entity.Customer;
import com.xgh.paotui.entity.DeliveryManState;
import com.xgh.paotui.services.ICustomerService;
import com.xgh.paotui.services.IDeliveryManStateService;
import com.xgh.security.MD5Util;
import com.xgh.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

@Controller
@Api(value="微信端客户信息API")
@RequestMapping(value = "paotui/front/weixinFront/v1/customer/")
public class CustomerFrontWeixinController extends BaseController{

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(CustomerFrontWeixinController.class);

    @Autowired
    protected ICustomerService customerService;

    @Autowired
    protected IDeliveryManStateService deliveryManStateService;

    @ResponseBody
    @RequestMapping(value = "getCustomer", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取用户个人信息", httpMethod = "GET", notes = "微信端")
    public String getCustomer(
            @ApiParam(value="用户身份标识") @RequestParam(value = "token") String token) {

        long customerId=getCustomerIdByToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
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
            if (customer.getHeadPicUrl()!=null&&!"".equals(customer.getHeadPicUrl())){
                String headPicUrl = ConstantUtil.SERVER_URL+customer.getHeadPicUrl();
                map.put("headPicUrl",headPicUrl);
            }else {
                map.put("headPicUrl","");
            }
            resultMap = getResultMap("1","获取用户个人信息成功",map);
        }else {
            resultMap = getResultMap("0","获取用户个人信息失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getNearbyDeliveryMan", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获得附近跑客数量及坐标", httpMethod = "GET", notes = "获得附近跑客数量及坐标")
    public String getNearbyDeliveryMan(
            @ApiParam(value = "开通城市id") @RequestParam(value = "openCityId") int openCityId,
            @ApiParam(value = "当前经度") @RequestParam(value = "longitude") String longitude,
            @ApiParam(value = "当前纬度") @RequestParam(value = "latitude") String latitude) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("openCityId",openCityId);
        List<DeliveryManState>  deliveryManStatesList=deliveryManStateService.getList(map);
        if (deliveryManStatesList!=null&&deliveryManStatesList.size()>0){
            List<Map<String, Object>>  mapList=new ArrayList<Map<String, Object>>();
            //附近的跑腿师傅
            for(int i=0;i<deliveryManStatesList.size();i++){
                //腾讯地图经纬度转换成百度地图经纬度
                String longitudeLatitude = MapUtil.map_tx2bd(Double.parseDouble(latitude),Double.parseDouble(longitude));
                String[] array;
                array = longitudeLatitude.split(",");
                latitude = array[0];
                longitude = array[1];

                DeliveryManState deliveryManStatesList_ = deliveryManStatesList.get(i);
                double distance= MapUtil.GetDistance(Double.parseDouble(latitude),Double.parseDouble(longitude),deliveryManStatesList_.getNowLatitude(),deliveryManStatesList_.getNowLongitude());
                //暂时计算10000米内的跑腿师傅
                if(distance<10000){
                    Map<String, Object> map1=new HashMap<String, Object>();
                    map1.put("deliverManId",deliveryManStatesList_.getDeliverManId());
                    map1.put("nowLatitude",deliveryManStatesList_.getNowLatitude());
                    map1.put("nowLongitude",deliveryManStatesList_.getNowLongitude());
                    mapList.add(map1);
                }
            }
            Map<String, Object> map2=new HashMap<String, Object>();
            map2.put("nearbyDeliveryManNum",mapList.size());
            mapList.add(map2);
            resultMap = getResultMap("1","获得附近跑客数量及坐标成功",mapList);
        }else {
            resultMap = getResultMap("0","没有获取到跑客");
        }
        return JSONUtil.getJson(resultMap);
    }


    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "登录", httpMethod = "POST", notes = "微信端使用")
    public String login(
            @ApiParam(required=true,value="用户名") @RequestParam(value = "loginName") String loginName,
            @ApiParam(required=true,value="密码") @RequestParam(value = "password") String password,
            @ApiParam(required=true,value="微信端openid") @RequestParam(value = "openid") String openid) {
        Map<String, Object> loginMap = customerService.updateOfWeixinLogin(loginName,password,openid);
        return JSONUtil.getJson(loginMap);
    }



    @ResponseBody
    @RequestMapping(value = "register", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "用户注册", httpMethod = "POST", notes = "微信端使用")
    public String register(
            @ApiParam(value = "注册手机号") @RequestParam(value = "loginName") String loginName,
            @ApiParam(value = "短信验证码") @RequestParam(value = "verifCode") String verifCode,
            @ApiParam(value = "密码") @RequestParam(value = "password1") String password1,
            @ApiParam(value = "确认密码") @RequestParam(value = "password2") String password2,
            @ApiParam(value="微信端openid") @RequestParam(value = "openid") String openid) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("loginName",loginName);
        List<Customer>  customers= customerService.getList(map);
        if (customers.size()>0){
            resultMap = getResultMap("-1","此账号已经被注册!");
        }else {
            if (!password1.equals(password2)){
                resultMap=getResultMap("-1","密码不一致，请重新输入!");
            }else {
                String verifCode_ = (String) request.getSession().getAttribute("validationCode");
                if (verifCode_==null||"".equals(verifCode_)){
                    resultMap = getResultMap("-1", "验证码已失效!");
                }else if (!verifCode.equals(verifCode_)){
                    resultMap = getResultMap("-1", "验证码错误!");
                }else {
                    Customer customer1 = new Customer();
                    customer1.setLoginName(loginName);
                    customer1.setPassword(MD5Util.getMD5(password1));
                    customer1.setRegisterTime(new Date());
                    customer1.setStatus(1);
                    customer1.setToken(openid);
                    //初始账户余额0元
                    customer1.setBalance(new BigDecimal(0));
                    int flag = customerService.addWithOpenid(customer1,openid);
                    if (flag>0){
                        Map token=new HashMap();
                        AppToken appToken = new AppToken();
                        appToken.setId(customer1.getId());
                        appToken.setAppType("weixin");
                        token.put("token", JWT.sign(appToken,30L * 24L * 3600L * 1000L));

                        resultMap = getResultMap("1", "注册成功!",token);
                        //
                    }else {
                        resultMap = getResultMap("0", "注册失败!");
                    }
                }
            }
        }
        return JSONUtil.getJson(resultMap);
    }


}
