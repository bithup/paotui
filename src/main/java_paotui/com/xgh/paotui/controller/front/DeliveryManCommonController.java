package com.xgh.paotui.controller.front;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.dao.IDeliveryManDao;
import com.xgh.paotui.entity.DeliveryMan;
import com.xgh.paotui.entity.DeliveryManAuth;
import com.xgh.paotui.entity.DeliveryManState;
import com.xgh.paotui.entity.OpenCity;
import com.xgh.paotui.services.*;
import com.xgh.paotui.weixin.AccessToken;
import com.xgh.paotui.weixin.WeixinHttpUtil;
import com.xgh.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Api(value="跑客端通用接口API")
@RequestMapping(value = "paotui/front/v1/deliveryManCommon/")
public class DeliveryManCommonController  extends BaseController {

    private Logger logger = Logger.getLogger(DeliveryManCommonController.class);

    @Autowired
    protected IDeliveryManService deliveryManService;

    @Autowired
    protected IDeliveryManAuthService deliveryManAuthService;

    @Autowired
    protected IDeliveryManStateService deliveryManStateService;

    @Autowired
    protected IOrderService orderService;

    @Autowired
    protected IOrderActionService orderActionService;

    @Autowired
    protected ICapitalLogService capitalLogService;

    @Autowired
    protected IOpenCityService openCityService;

    @Autowired
    protected IDeliveryManDao deliveryManDao;

    @ResponseBody
    @RequestMapping(value = "getVerifCodeUse", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取短信验证码(验证账号是否已使用)", httpMethod = "POST", notes = "APP端")
    public String getVerifCodeUse(
            @ApiParam(value = "手机号") @RequestParam(value = "loginName") String loginName) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("loginName",loginName);
        DeliveryMan deliveryMan= deliveryManDao.login(map);
        if (deliveryMan!=null&&deliveryMan.getId()>0){
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
        DeliveryMan deliveryMan = deliveryManDao.login(map);
        if (deliveryMan==null||deliveryMan.getId()<1){
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
    @ApiOperation(value = "跑客登录", httpMethod = "POST", notes = "APP跑客端")
    public String login(
            @ApiParam(value="用户名") @RequestParam(value = "loginName") String loginName,
            @ApiParam(value="密码") @RequestParam(value = "password") String password,
            HttpServletRequest request) {
        Map<String, Object> loginMap = deliveryManService.login(request);
        return JSONUtil.getJson(loginMap);
    }

    @ResponseBody
    @RequestMapping(value = "register", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "跑客注册", httpMethod = "POST", notes = "APP跑客端")
    public String register(
            @ApiParam(value = "注册手机号") @RequestParam(value = "loginName") String loginName,
            @ApiParam(value = "跑客类型") @RequestParam(value = "belongType") String belongType,
            @ApiParam(value = "短信验证码") @RequestParam(value = "verifCode") String verifCode,
            @ApiParam(value = "密码") @RequestParam(value = "password1") String password1,
            @ApiParam(value = "确认密码") @RequestParam(value = "password2") String password2,
            HttpServletRequest request) {
        Map<String,Object> registerMap= deliveryManService.register(request);
        return JSONUtil.getJson(registerMap);
    }

    @ResponseBody
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "修改密码",httpMethod = "POST", notes = "APP跑客端")
    public String updatePassword(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "原密码") @RequestParam(value = "oldPassword") String oldPassword,
            @ApiParam(value = "新密码") @RequestParam(value = "newPassword") String newPassword,
            HttpServletRequest request) {
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String,Object> updatePasswordMap= deliveryManService.updatePassword(request,deliveryManId);
        return JSONUtil.getJson(updatePasswordMap);
    }

    @ResponseBody
    @RequestMapping(value = "forgetPassword", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "忘记密码",httpMethod = "POST", notes = "APP跑客端")
    public String forgetPassword(
            @ApiParam(value = "跑客账号") @RequestParam(value = "loginName") String loginName,
            @ApiParam(value = "短信验证码") @RequestParam(value = "verifCode") String verifCode,
            @ApiParam(value = "新密码") @RequestParam(value = "newPassword") String newPassword,
            HttpServletRequest request) {
        Map<String,Object> forgetPasswordMap=deliveryManService.forgetPassword(request);
        return JSONUtil.getJson(forgetPasswordMap);
    }

    @ResponseBody
    @RequestMapping(value = "saveDeliveryMan", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "完善跑客信息",httpMethod = "POST", notes = "APP跑客端")
    public String saveDeliveryMan(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "跑客真实姓名") @RequestParam(value = "realName") String realName,
            @ApiParam(value = "出生年月") @RequestParam(value = "birthday") String birthday,
            @ApiParam(value = "性别") @RequestParam(value = "sex") String sex,
            @ApiParam(value = "手机号") @RequestParam(value = "mobilePhone") String mobilePhone,
            @ApiParam(value = "所属城市id") @RequestParam(value = "belongCityId") long belongCityId,
            @ApiParam(value = "身份证号") @RequestParam(value = "idCardNo") String idCardNo) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        DeliveryMan deliveryMan = deliveryManService.get(deliveryManId);
        if (deliveryMan!=null&&deliveryMan.getId()>0){
            deliveryMan.setRealName(realName);
            try{
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                deliveryMan.setBirthday(format.parse(birthday));
            }catch (ParseException p){
                return JSONUtil.getJson("时间格式转换错误,时间输入不正确");
            }
            deliveryMan.setSex(sex);
            deliveryMan.setMobilePhone(mobilePhone);
            deliveryMan.setBelongCityId(belongCityId);
            deliveryMan.setStarLevel(1);
            deliveryMan.setPraiseRate((double) 0);
            DeliveryManAuth deliveryManAuth = new DeliveryManAuth();
            deliveryManAuth.setIdCardNo(idCardNo);
            int flag = deliveryManService.saveDeliveryMan(deliveryMan,deliveryManAuth);
            if (flag>0){
                resultMap = getResultMap("1","完善跑客信息成功");
            }else {
                resultMap = getResultMap("0","完善跑客信息失败");
            }
        }else {
            resultMap = getResultMap("0","跑客信息不存在");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value="replacePhoneNumberOld",method=RequestMethod.POST, produces={"application/json;charset=utf-8"})
    @ApiOperation(value="更换手机号（验证原手机号）",httpMethod="POST",notes="App端使用")
    public String replacePhoneNumberOld(
            @ApiParam(value="跑客身份标识")@RequestParam(value="token")String token,
            @ApiParam(value="原手机号")@RequestParam(value="loginName")String loginName,
            @ApiParam(value="短信验证码")@RequestParam(value="verifCode")String verifCode,
            HttpServletRequest request){
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String,Object>deliveryMan=deliveryManService.replacePhoneNumberOld(request,deliveryManId);
        return JSONUtil.getJson(deliveryMan);
    }

    @ResponseBody
    @RequestMapping(value="replacePhoneNumberNew",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
    @ApiOperation(value="更换手机号（确认更换）",httpMethod="POST",notes="App端使用")
    public String replacePhoneNumberNew(

            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="更换手机号")@RequestParam(value="loginName")String loginName,
            @ApiParam(value="短信验证码")@RequestParam(value="verifCode")String verifCode,
            HttpServletRequest request) {
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String, Object> deliveryMan = deliveryManService.replacePhoneNumberNew(request,deliveryManId);
        return JSONUtil.getJson(deliveryMan);
    }

    @ResponseBody
    @RequestMapping(value = "updateHeadImage", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "更换头像", httpMethod = "POST", notes = "APP跑客端")
    public String updateHeadImage(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "头像") @RequestPart(value = "headImage") MultipartFile headImage){
        Map<String, Object> resultMap=new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        try {
            DeliveryMan deliveryMan= deliveryManService.get(deliveryManId);
            if (deliveryMan!=null&&deliveryMan.getId()>0){
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
                        String realPath = ConstantUtil.SAVE_PATH + "/" + "deliveryMan" + "/" + relative_path;
                        deliveryMan.setHeadImage("/" + "deliveryMan" + "/" + relative_path + saveName);
                        File filePath = new File(realPath);
                        if (!filePath.exists()) {
                            filePath.mkdirs();
                        }
                        FileUtil.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, saveName));
                    }
                    int flag = deliveryManService.update(deliveryMan);
                    if (flag > 0) {
                        resultMap = getResultMap("1", "更改用户头像成功");
                    } else {
                        resultMap = getResultMap("0", "更改用户头像失败");
                    }
                }else {
                    resultMap = getResultMap("0", "没有选择头像");
                }
            }else {
                resultMap = getResultMap("0","跑客不存在");
            }
        } catch (IOException var18) {
            var18.printStackTrace();
            logger.error(var18.getMessage(), var18);
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "addIdCardPicture", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "上传证件图片", httpMethod = "POST", notes = "APP跑客端")
    public String addIdCardPicture(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "身份证正面照") @RequestPart(value = "idCardPicture1") MultipartFile idCardPicture1,
            @ApiParam(value = "身份证背面照") @RequestPart(value = "idCardPicture2") MultipartFile idCardPicture2,
            @ApiParam(value = "手持身份证照") @RequestPart(value = "idCardPicture3") MultipartFile idCardPicture3){
        Map<String, Object> resultMap=new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        try {
            DeliveryManAuth deliveryManAuth = deliveryManAuthService.getDeliveryManAuth(deliveryManId);
            if (deliveryManAuth!=null&&deliveryManAuth.getId()>0){
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                Iterator fileNames = multipartRequest.getFileNames();
                for (int i = 0; fileNames.hasNext(); ++i) {
                    String name = (String) fileNames.next();
                    MultipartFile myfile = multipartRequest.getFile(name);
                    if (myfile.isEmpty()) {
                        logger.info("文件未上传");
                    } else {
                        String OriginalFileName = myfile.getOriginalFilename();
                        String saveName = DateUtil.getSystemTime().getTime() + "" + i + OriginalFileName.substring(OriginalFileName.lastIndexOf("."), OriginalFileName.length());
                        SimpleDateFormat formatdate = new SimpleDateFormat("yyyy/MM/dd/");
                        String relative_path = formatdate.format(new Date());
                        String realPath = ConstantUtil.SAVE_PATH + "/" + "deliveryMan" + "/" + relative_path;
                        if (i==0){
                            deliveryManAuth.setIdCardPicture1("/" + "deliveryMan" + "/" + relative_path + saveName);
                        }else if (i==1){
                            deliveryManAuth.setIdCardPicture2("/" + "deliveryMan" + "/" + relative_path + saveName);
                        }else {
                            deliveryManAuth.setIdCardPicture3("/" + "deliveryMan" + "/" + relative_path + saveName);
                        }
                        File filePath = new File(realPath);
                        if (!filePath.exists()) {
                            filePath.mkdirs();
                        }
                        FileUtil.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, saveName));
                    }
                }
                int flag = deliveryManAuthService.update(deliveryManAuth);
                if(flag>0){
                    resultMap = getResultMap("1","上传证件图片成功");
                }else{
                    resultMap = getResultMap("0","上传证件图片失败");
                }
            }else {
                resultMap = getResultMap("0","没有跑客认证信息");
            }
        } catch (IOException var18) {
            var18.printStackTrace();
            logger.error(var18.getMessage(), var18);
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "startWork", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "跑客设置开工接口", httpMethod = "POST", notes = "跑客设置开工接口")
    public String startWork(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token) {
        Map<String, Object> retMap=new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        DeliveryManState  deliveryManState= deliveryManStateService.get(deliveryManId);
        if(deliveryManState!=null&&deliveryManState.getId()>0){
            if (deliveryManState.getWorkStatus()!=1){
                deliveryManState.setWorkStatus(1);
                //判断当前能不能抢单
                int gainNewOrder=deliveryManStateService.getGainNewOrder(deliveryManId);
                deliveryManState.setGainNewOrder(gainNewOrder);
                deliveryManState.setStateUpdateTime(new Date());
                int ret= deliveryManStateService.update(deliveryManState);
                if(ret==1){
                    retMap=getResultMap("1","开工成功");
                }else{
                    retMap=getResultMap("0","开工失败");
                }
            }else {
                retMap=getResultMap("-1","跑客已开工");
            }
        }else {
            retMap=getResultMap("-1","开工失败,请确认跑客是否认证通过");
        }

        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "endWork", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "跑客设置收工接口", httpMethod = "POST", notes = "跑客设置收工接口")
    public String endWork(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token) {
        Map<String, Object> retMap=new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        DeliveryManState  deliveryManState= deliveryManStateService.get(deliveryManId);
        if (deliveryManState.getWorkStatus()!=2){
            deliveryManState.setWorkStatus(2);
            int ret= deliveryManStateService.update(deliveryManState);
            if(ret==1){
                retMap=getResultMap("1","收工成功");
            }else{
                retMap=getResultMap("0","收工失败");
            }
        }else {
            retMap=getResultMap("-1","跑客已经收工");
        }
        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "gainNewOrder", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "跑客抢单接口", httpMethod = "POST", notes = "APP跑客端")
    public String gainNewOrder(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> retMap=new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        DeliveryManState  deliveryManState= deliveryManStateService.get(deliveryManId);
        //不可以抢单的情况下
        if(2==deliveryManState.getGainNewOrder()){
            retMap=getResultMap("-1","您有任务不能抢单");
            return JSONUtil.getJson(retMap);
        }
        int ret= deliveryManStateService.updateOfGainNewOrder(deliveryManId,orderId);
        if(ret==1){
            retMap=getResultMap("1","抢单成功");
        }else if(ret==2){
            retMap=getResultMap("-2","订单未支付");
        }else if(ret==3){
            retMap=getResultMap("-3","订单已被自己抢了");
        }else if(ret==4){
            retMap=getResultMap("-4","订单被别人抢了");
        }else{
            retMap=getResultMap("0","抢单失败！");
        }
        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "myTaskOfOrderList", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "跑客我的任务列表接口", httpMethod = "POST", notes = "跑客我的任务列表接口")
    public String myTaskOfOrderList(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        resultMap.put("deliveryManId",deliveryManId);
        resultMap.put("orderStatusList","3,4");
        List<Map<String, Object>> orderList=orderService.getOrderInfoList(resultMap);
        List<Map<String, Object>>  mapList = new ArrayList<Map<String, Object>>();
        if (orderList!=null&&orderList.size()>0){
            for(Map<String, Object> orderList_:orderList){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("orderId", StringUtil.convertNullToEmpty(orderList_.get("id")));
                //订单类型
                String orderType=StringUtil.convertNullToEmpty(orderList_.get("orderType"));
                map.put("orderType",DictUtils.getDictLabel(orderType,"paotui_order_type",""));
                //跑腿费，使用原始跑腿费
                map.put("freight", StringUtil.convertNullToEmpty(orderList_.get("originalFreight")));
                //小费
                map.put("tips", StringUtil.convertNullToEmpty(orderList_.get("tips")));
                //订单预约类型：1、立即订单；2：预约订单
                String bookingType= StringUtil.convertNullToEmpty(orderList_.get("bookingType"));
                map.put("bookingType", DictUtils.getDictLabel(bookingType,"paotui_booking_type",""));
                if("2".equals(bookingType)){
                    Date bookingDate=(Date)orderList_.get("bookingTime");
                    map.put("bookingTime", DateUtil.getChineseDateTime(bookingDate));
                }
                /*帮送/帮取*/
                if("1".equals(orderType) || "2".equals(orderType) ){
                    map.put("addressA", StringUtil.convertNullToEmpty(orderList_.get("locationAddressA"))
                            +StringUtil.convertNullToEmpty(orderList_.get("locationAddressNameA"))
                            +StringUtil.convertNullToEmpty(orderList_.get("detailAddressA")));
                    map.put("addressB", StringUtil.convertNullToEmpty(orderList_.get("locationAddressB"))
                            +StringUtil.convertNullToEmpty(orderList_.get("locationAddressNameB"))
                            +StringUtil.convertNullToEmpty(orderList_.get("detailAddressB")));
                    //特殊要求
                    map.put("specialRequire", StringUtil.convertNullToEmpty(orderList_.get("specialRequire")));
                }
                /*帮买*/
                else if("3".equals(orderType) ){
                    //是否就近购买(1：收货地附近就近购买；2：指定购买地）
                    String isNearBuy =StringUtil.convertNullToEmpty(orderList_.get("isNearBuy"));
                    if("1".equals(isNearBuy)){
                        map.put("addressA", "收货地附近就近购买");
                    }else{
                        map.put("addressA", StringUtil.convertNullToEmpty(orderList_.get("locationAddressA"))
                                +StringUtil.convertNullToEmpty(orderList_.get("locationAddressNameA"))
                                +StringUtil.convertNullToEmpty(orderList_.get("detailAddressA")));
                    }
                    map.put("addressB", StringUtil.convertNullToEmpty(orderList_.get("locationAddressB"))
                            +StringUtil.convertNullToEmpty(orderList_.get("locationAddressNameB"))
                            +StringUtil.convertNullToEmpty(orderList_.get("detailAddressB")));
                    //购买要求
                    map.put("buyRequire", StringUtil.convertNullToEmpty(orderList_.get("buyRequire")));
                    String isKonwPrice = StringUtil.convertNullToEmpty(orderList_.get("isKonwPrice"));
                    if ("1".equals(isKonwPrice)) {
                        //商品价格
                        map.put("buyPrice", StringUtil.convertNullToEmpty(orderList_.get("buyPrice")));
                    } else {
                        map.put("isKonwPrice", DictUtils.getDictLabel(isKonwPrice, "paotui_is_konw_price", ""));
                    }
                    //商品费
                    String addPayTime = StringUtil.convertNullToEmpty(orderList_.get("addPayTime"));
                    if(StringUtil.isEmpty(addPayTime)){
                        //未支付商品费
                        map.put("addPayType", 2);
                    }else{
                        //已支付商品费
                        map.put("addPayType", 1);
                        //商品费
                        map.put("addPayMoney", StringUtil.convertNullToEmpty(orderList_.get("addPayMoney")));
                    }
                }
                /*代排队*/
                else if("4".equals(orderType) ){
                    map.put("lineupAddress", StringUtil.convertNullToEmpty(orderList_.get("lineupLocationAddress"))
                            +StringUtil.convertNullToEmpty(orderList_.get("lineupLocationAddressName"))
                            +StringUtil.convertNullToEmpty(orderList_.get("lineupDetailAddress")));
                    // 排队时长
                    map.put("lineupDuration", StringUtil.convertNullToEmpty(orderList_.get("lineupDuration")));
                    // 排队要求
                    map.put("lineupRequire", StringUtil.convertNullToEmpty(orderList_.get("lineupRequire")));
                }
                mapList.add(map);
            }
            resultMap = getResultMap("1","获取跑客我的任务列表成功",mapList);
        }else {
            resultMap = getResultMap("0","获取跑客我的任务列表失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getDeliveryMan", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取跑客个人信息", httpMethod = "GET", notes = "APP跑客端")
    public String getDeliveryMan(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token) {
        long deliveryManId = getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        DeliveryMan deliveryMan= deliveryManService.get(deliveryManId);
        if (deliveryMan!=null&&deliveryMan.getId()>0){
            map.put("loginName",deliveryMan.getLoginName());
            map.put("realName",deliveryMan.getRealName());
            map.put("sex",deliveryMan.getSex());
            map.put("birthday",deliveryMan.getBirthday());
            map.put("mobilePhone",StringUtil.convertNullToEmpty(deliveryMan.getMobilePhone()));
            map.put("balance",deliveryMan.getBalance());
            map.put("starLevel",deliveryMan.getStarLevel());
            String belongType = StringUtil.convertNullToEmpty(deliveryMan.getBelongType());
            map.put("belongType", DictUtils.getDictLabel(belongType,"paotui_belong_type",""));
            long belongCityId = deliveryMan.getBelongCityId();
            OpenCity openCity = openCityService.get(belongCityId);
            if (openCity!=null&&openCity.getId()>0){
                map.put("cityName",openCity.getCityName());
            }
            map.put("getOrderCount",deliveryMan.getGetOrderCount());
            map.put("praiseRate",deliveryMan.getPraiseRate());
            if (deliveryMan.getQrCode()!=null&&deliveryMan.getQrCode()!=""){
                String qrCode = ConstantUtil.SERVER_URL+deliveryMan.getQrCode();
                map.put("qrCode",qrCode);
            }else {
                map.put("qrCode","");
            }
            if (deliveryMan.getHeadImage()!=null&&deliveryMan.getHeadImage()!=""){
                String headImage = ConstantUtil.SERVER_URL+deliveryMan.getHeadImage();
                map.put("headImage",headImage);
            }else {
                map.put("headImage","");
            }
            resultMap = getResultMap("1","获取跑客个人信息成功",map);
        }else {
            resultMap = getResultMap("0","获取跑客个人信息失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getDeliveryManWithdrawals", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取跑客可提现余额", httpMethod = "GET", notes = "APP跑客端")
    public String getDeliveryManWithdrawals(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        long deliveryManId = getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        DeliveryMan deliveryMan= deliveryManService.get(deliveryManId);
        if (deliveryMan!=null&&deliveryMan.getId()>0){
            //认证状态
            int authStatus = deliveryMan.getAuthStatus();
            if (authStatus==1){
                map.put("balance",deliveryMan.getBalance());
                resultMap = getResultMap("1","获取跑客可提现余额成功",map);
            }else {
                resultMap = getResultMap("0","该跑客未认证，不能提现");
            }
        }else {
            resultMap = getResultMap("-1","获取跑客信息失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getCanRobOrder", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取可抢订单列表", httpMethod = "GET", notes = "APP跑客端")
    public String getCanRobOrder(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="跑客位置经度") @RequestParam(value = "longitude") String longitude,
            @ApiParam(value="跑客位置纬度") @RequestParam(value = "latitude") String latitude){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        long deliveryManId = getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        DeliveryManState deliveryManState= deliveryManStateService.get(deliveryManId);
        if (deliveryManState!=null&&deliveryManState.getId()>0){
            if (deliveryManState.getGainNewOrder()==1){
                resultMap.put("orderStatus",2);
                List<Map<String,Object>> canRobOrderList = deliveryManService.getCanRobOrder(resultMap);
                if (canRobOrderList!=null&&canRobOrderList.size()>0){
                    for (Map<String,Object> canRobOrderList_:canRobOrderList){
                        //订单经度
                        String orderLongitude = StringUtil.convertNullToEmpty(canRobOrderList_.get("longitude"));
                        //订单纬度
                        String orderLatitude = StringUtil.convertNullToEmpty(canRobOrderList_.get("latitude"));
                        //跑客和订单的距离
                        double distance=MapUtil.GetDistance(Double.parseDouble(longitude),Double.parseDouble(latitude),Double.parseDouble(orderLongitude),Double.parseDouble(orderLatitude));
                        if (distance<4000){
                            Map<String, Object> map = new HashMap<String, Object>();
                            //订单id
                            map.put("orderId",canRobOrderList_.get("id"));
                            //跑客和订单的距离
                            map.put("distance",distance);
                            //跑腿费
                            map.put("originalFreight",canRobOrderList_.get("originalFreight"));
                            //订单类型
                            String orderType = StringUtil.convertNullToEmpty(canRobOrderList_.get("orderType"));
                            map.put("orderType",DictUtils.getDictLabel(orderType,"paotui_order_type",""));
                            //订单预约类型
                            String bookingType = StringUtil.convertNullToEmpty(canRobOrderList_.get("bookingType"));
                            map.put("bookingType",DictUtils.getDictLabel(bookingType,"paotui_booking_type",""));
                            if (canRobOrderList_.get("bookingType").equals(2)){
                                map.put("bookingTime",canRobOrderList_.get("bookingTime"));
                            }
                            /*帮送、帮取*/
                            if("1".equals(orderType)||"2".equals(orderType)) {
                                String addressA = "";
                                addressA += StringUtil.convertNullToEmpty(canRobOrderList_.get("locationAddressA"));
                                addressA += StringUtil.convertNullToEmpty(canRobOrderList_.get("locationAddressNameA"));
                                addressA += StringUtil.convertNullToEmpty(canRobOrderList_.get("detailAddressA"));
                                map.put("addressA",addressA);
                                map.put("linkmanNameA",StringUtil.convertNullToEmpty(canRobOrderList_.get("linkmanNameA")));
                                map.put("mobilePhoneA",StringUtil.convertNullToEmpty(canRobOrderList_.get("mobilePhoneA")));
                                String addressB = "";
                                addressB += StringUtil.convertNullToEmpty(canRobOrderList_.get("locationAddressB"));
                                addressB += StringUtil.convertNullToEmpty(canRobOrderList_.get("locationAddressNameB"));
                                addressB += StringUtil.convertNullToEmpty(canRobOrderList_.get("detailAddressB"));
                                map.put("addressB",addressB);
                                map.put("linkmanNameB",StringUtil.convertNullToEmpty(canRobOrderList_.get("linkmanNameB")));
                                map.put("mobilePhoneB",StringUtil.convertNullToEmpty(canRobOrderList_.get("mobilePhoneB")));
                                //物品类型
                                map.put("goodsTypeName",StringUtil.convertNullToEmpty(canRobOrderList_.get("goodsTypeName")));
                                //特殊要求
                                map.put("specialRequire",StringUtil.convertNullToEmpty(canRobOrderList_.get("specialRequire")));
                            }
                            /*帮买*/
                            else if ("3".equals(orderType)){
                                String isNearBuy = StringUtil.convertNullToEmpty(canRobOrderList_.get("isNearBuy"));
                                String isKonwPrice = StringUtil.convertNullToEmpty(canRobOrderList_.get("isKonwPrice"));
                                //指定地方购买购买
                                if ("2".equals(isNearBuy)) {
                                    String addressA = "";
                                    addressA += StringUtil.convertNullToEmpty(canRobOrderList_.get("locationAddressA"));
                                    addressA += StringUtil.convertNullToEmpty(canRobOrderList_.get("locationAddressNameA"));
                                    addressA += StringUtil.convertNullToEmpty(canRobOrderList_.get("detailAddressA"));
                                    map.put("addressA", addressA);
                                }else {
                                    map.put("addressA",DictUtils.getDictLabel(isNearBuy,"paotui_is_near_buy",""));
                                }
                                String addressB = "";
                                addressB += StringUtil.convertNullToEmpty(canRobOrderList_.get("locationAddressB"));
                                addressB += StringUtil.convertNullToEmpty(canRobOrderList_.get("locationAddressNameB"));
                                addressB += StringUtil.convertNullToEmpty(canRobOrderList_.get("detailAddressB"));
                                map.put("addressB", addressB);
                                map.put("linkmanNameB",StringUtil.convertNullToEmpty(canRobOrderList_.get("linkmanNameB")));
                                map.put("mobilePhoneB", StringUtil.convertNullToEmpty(canRobOrderList_.get("mobilePhoneB")));
                                //不知道商品价格
                                if ("2".equals(isKonwPrice)){
                                    map.put("isKonwPrice",DictUtils.getDictLabel(isKonwPrice,"paotui_is_konw_price",""));
                                }else {
                                    map.put("isKonwPrice",DictUtils.getDictLabel(isKonwPrice,"paotui_is_konw_price",""));
                                    map.put("buyPrice",canRobOrderList_.get("buyPrice"));
                                }
                                //购买要求
                                map.put("buyRequire", StringUtil.convertNullToEmpty(canRobOrderList_.get("buyRequire")));
                            }
                            /*带排队*/
                            else if ("4".equals(orderType)){
                                String address = "";
                                address += StringUtil.convertNullToEmpty(canRobOrderList_.get("lineupLocationAddress"));
                                address += StringUtil.convertNullToEmpty(canRobOrderList_.get("lineupLocationAddressName"));
                                address += StringUtil.convertNullToEmpty(canRobOrderList_.get("lineupDetailAddress"));
                                map.put("address",address);
                                //排队要求
                                map.put("lineupRequire",StringUtil.convertNullToEmpty(canRobOrderList_.get("lineupRequire")));
                                //排队时长
                                map.put("lineupDuration",StringUtil.convertNullToEmpty(canRobOrderList_.get("lineupDuration")));
                                //联系电话
                                map.put("lineupPhone",StringUtil.convertNullToEmpty(canRobOrderList_.get("lineupPhone")));
                            }
                            mapList.add(map);
                        }
                    }
                    resultMap = getResultMap("1","获取可抢订单列表成功",mapList);
                }else {
                    resultMap = getResultMap("0","没有可抢订单");
                }
            }else {
                resultMap = getResultMap("-2","当前不能接单");
            }
        }else {
            resultMap = getResultMap("-1","没有该跑客的状态");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "addDeliveryManState", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "实时上传跑客位置", httpMethod = "POST", notes = "APP跑客端")
    public String addDeliveryManState(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="跑客当前位置经度") @RequestParam(value = "longitude") String longitude,
            @ApiParam(value="跑客当前位置纬度") @RequestParam(value = "latitude") String latitude,
            @ApiParam(value="跑客当前位置") @RequestParam(value = "address") String address) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        long deliveryManId = getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        DeliveryManState  deliveryManState= deliveryManStateService.get(deliveryManId);
        if(deliveryManState!=null&&deliveryManState.getId()>0){
            deliveryManState.setNowLongitude(Double.parseDouble(longitude));
            deliveryManState.setNowLatitude(Double.parseDouble(latitude));
            deliveryManState.setNowAddress(address);
            deliveryManState.setStateUpdateTime(new Date());
            int flag = deliveryManStateService.addDeliveryManState(deliveryManState);
            if (flag>0){
                resultMap = getResultMap("1","上传跑客位置成功");
            }else {
                resultMap = getResultMap("0","上传跑客位置失败");
            }
        }else {
            resultMap = getResultMap("-1","没有该跑客的状态");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "myTaskOfOrderDetail", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "跑客任务详情", httpMethod = "GET", notes = "APP跑客端")
    public String myTaskOfOrderDetail(
            @ApiParam(value = "任务订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        resultMap.put("id", orderId);
        List<Map<String, Object>> orderDetailList = orderService.getOrderInfoList(resultMap);
        if (orderDetailList != null && orderDetailList.size() > 0) {
            for (Map<String, Object> orderDetailList_ : orderDetailList) {
                //订单类型
                String orderType = StringUtil.convertNullToEmpty(orderDetailList_.get("orderType"));
                map.put("orderType", DictUtils.getDictLabel(orderType, "paotui_order_type", ""));
                //订单编号
                map.put("orderNo", StringUtil.convertNullToEmpty(orderDetailList_.get("orderNo")));
                //下单人姓名
                map.put("customerName", StringUtil.convertNullToEmpty(orderDetailList_.get("customerName")));
                //下单人电话
                map.put("customerPhone", StringUtil.convertNullToEmpty(orderDetailList_.get("customerPhone")));
                //订单预约类型：1、立即订单；2：预约订单
                String bookingType = StringUtil.convertNullToEmpty(orderDetailList_.get("bookingType"));
                map.put("bookingType", DictUtils.getDictLabel(bookingType, "paotui_booking_type", ""));
                if ("2".equals(bookingType)) {
                //预约时间
                Date bookingTime = (Date) orderDetailList_.get("bookingTime");
                map.put("bookingTime", DateUtil.getChineseDateTime(bookingTime));
                }
                //订单状态
                String orderStatus = StringUtil.convertNullToEmpty(orderDetailList_.get("orderStatus"));
                map.put("orderStatus", DictUtils.getDictLabel(orderStatus, "paotui_order_state", ""));
                //原始跑腿费
                map.put("originalFreight", StringUtil.convertNullToEmpty(orderDetailList_.get("originalFreight")));
                if ("5".equals(orderStatus)) {
                    //实际跑腿费
                    map.put("actualFreight", StringUtil.convertNullToEmpty(orderDetailList_.get("actualFreight")));
                }
                //是否使用优惠券：1、使用；0：未使用
                String isUseCoupons = StringUtil.convertNullToEmpty(orderDetailList_.get("isUseCoupons"));
                map.put("isUseCoupons", DictUtils.getDictLabel(isUseCoupons, "paotui_is_use_coupons", ""));
                if ("1".equals(isUseCoupons)) {
                    //优惠券优惠金额
                    map.put("couponsMoney", StringUtil.convertNullToEmpty(orderDetailList_.get("couponsMoney")));
                }
                //小费
                map.put("tips", StringUtil.convertNullToEmpty(orderDetailList_.get("tips")));
                //下单时间
                map.put("createTime", StringUtil.convertNullToEmpty(orderDetailList_.get("createTime")));
                /*帮送、帮取*/
                if ("1".equals(orderType) || "2".equals(orderType)) {
                    String addressA = "";
                    addressA += StringUtil.convertNullToEmpty(orderDetailList_.get("locationAddressA"));
                    addressA += StringUtil.convertNullToEmpty(orderDetailList_.get("locationAddressNameA"));
                    addressA += StringUtil.convertNullToEmpty(orderDetailList_.get("detailAddressA"));
                    map.put("addressA", addressA);
                    map.put("linkmanNameA", StringUtil.convertNullToEmpty(orderDetailList_.get("linkmanNameA")));
                    map.put("mobilePhoneA", StringUtil.convertNullToEmpty(orderDetailList_.get("mobilePhoneA")));
                    String addressB = "";
                    addressB += StringUtil.convertNullToEmpty(orderDetailList_.get("locationAddressB"));
                    addressB += StringUtil.convertNullToEmpty(orderDetailList_.get("locationAddressNameB"));
                    addressB += StringUtil.convertNullToEmpty(orderDetailList_.get("detailAddressB"));
                    map.put("addressB", addressB);
                    map.put("linkmanNameB", StringUtil.convertNullToEmpty(orderDetailList_.get("linkmanNameB")));
                    map.put("mobilePhoneB", StringUtil.convertNullToEmpty(orderDetailList_.get("mobilePhoneB")));
                    //物品类型
                    map.put("goodsTypeName", StringUtil.convertNullToEmpty(orderDetailList_.get("goodsTypeName")));
                    //特殊要求
                    map.put("specialRequire", StringUtil.convertNullToEmpty(orderDetailList_.get("specialRequire")));
                }
                /*帮买*/
                else if ("3".equals(orderType)) {
                    String isNearBuy = StringUtil.convertNullToEmpty(orderDetailList_.get("isNearBuy"));
                    if ("2".equals(isNearBuy)) {
                        String addressA = "";
                        addressA += StringUtil.convertNullToEmpty(orderDetailList_.get("locationAddressA"));
                        addressA += StringUtil.convertNullToEmpty(orderDetailList_.get("locationAddressNameA"));
                        addressA += StringUtil.convertNullToEmpty(orderDetailList_.get("detailAddressA"));
                        map.put("addressA", addressA);
                    } else {
                        map.put("addressA", DictUtils.getDictLabel(isNearBuy, "paotui_is_near_buy", ""));
                    }
                    String addressB = "";
                    addressB += StringUtil.convertNullToEmpty(orderDetailList_.get("locationAddressB"));
                    addressB += StringUtil.convertNullToEmpty(orderDetailList_.get("locationAddressNameB"));
                    addressB += StringUtil.convertNullToEmpty(orderDetailList_.get("detailAddressB"));
                    map.put("addressB", addressB);
                    map.put("linkmanNameB", StringUtil.convertNullToEmpty(orderDetailList_.get("linkmanNameB")));
                    map.put("mobilePhoneB", StringUtil.convertNullToEmpty(orderDetailList_.get("mobilePhoneB")));
                    //购买要求
                    map.put("buyRequire", StringUtil.convertNullToEmpty(orderDetailList_.get("buyRequire")));
                    //商是否知道价格
                    String isKonwPrice = StringUtil.convertNullToEmpty(orderDetailList_.get("isKonwPrice"));
                    if ("1".equals(isKonwPrice)) {
                        //商品价格
                        map.put("buyPrice", StringUtil.convertNullToEmpty(orderDetailList_.get("buyPrice")));
                    } else {
                        map.put("isKonwPrice", DictUtils.getDictLabel(isKonwPrice, "paotui_is_konw_price", ""));
                    }
                }
                /*带排队*/
                else if ("4".equals(orderType)) {
                    String address = "";
                    address += StringUtil.convertNullToEmpty(orderDetailList_.get("lineupLocationAddress"));
                    address += StringUtil.convertNullToEmpty(orderDetailList_.get("lineupLocationAddressName"));
                    address += StringUtil.convertNullToEmpty(orderDetailList_.get("lineupDetailAddress"));
                    map.put("address", address);
                    //联系电话
                    map.put("lineupPhone", StringUtil.convertNullToEmpty(orderDetailList_.get("lineupPhone")));
                    // 排队要求
                    map.put("lineupRequire", StringUtil.convertNullToEmpty(orderDetailList_.get("lineupRequire")));
                    // 排队时长
                    map.put("lineupDuration", StringUtil.convertNullToEmpty(orderDetailList_.get("lineupDuration")));
                }
            }
            resultMap = getResultMap("1","获取跑客任务详情成功",map);
        }else {
            resultMap = getResultMap("0","获取跑客任务详情失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getTodayAchievement", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "今日成就", httpMethod = "GET", notes = "APP跑客端")
    public String getTodayAchievement(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String today1 = format.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String today2 = format.format(calendar.getTime());
        resultMap.put("deliveryManId",deliveryManId);
        resultMap.put("today1",today1);
        resultMap.put("today2",today2);
        List<Map<String,Object>> todayAchievementList = orderService.getTodayAchievement(resultMap);
        BigDecimal actualFreight = new BigDecimal("0");
        BigDecimal totalDistance = new BigDecimal("0");
        int lineupRealDuration = 0;
        for (Map<String,Object> todayAchievementList_:todayAchievementList){
            //订单类型
            String orderType = StringUtil.convertNullToEmpty(todayAchievementList_.get("orderType"));
            //订单实际跑腿费
            String actualFreight_ = StringUtil.convertNullToEmpty(todayAchievementList_.get("actualFreight"));
            BigDecimal bigDecimal1 = new BigDecimal(actualFreight_);
            actualFreight = actualFreight.add(bigDecimal1);
            if (!"4".equals(orderType)){
                //订单总里程
                String totalDistance_ = StringUtil.convertNullToEmpty(todayAchievementList_.get("totalDistance"));
                BigDecimal bigDecimal2 = new BigDecimal(totalDistance_);
                totalDistance = totalDistance.add(bigDecimal2);
            }else {
                //排队时长
                int lineupRealDuration_ = Integer.parseInt(StringUtil.convertNullToEmpty(todayAchievementList_.get("lineupRealDuration")));
                lineupRealDuration = lineupRealDuration+lineupRealDuration_;
            }
        }
        //今日完成任务数
        map.put("todayOrderNum",todayAchievementList.size());
        //今日收入
        map.put("todayIncome",actualFreight);
        //今日总里程
        map.put("todayMileage",totalDistance);
        //今日排队时长
        map.put("todayTime",lineupRealDuration);
        resultMap = getResultMap("1","获取今日成就成功",map);
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getMyOrder", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "我的订单列表", httpMethod = "GET", notes = "APP端使用")
    public String getMyOrder(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "订单状态") @RequestParam(required = false,value = "orderStatus") String orderStatus,
            @ApiParam(value = "页") @RequestParam(value = "page") int page,
            @ApiParam(value = "每页数量") @RequestParam(value = "pagesize") int pagesize) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        resultMap.put("page",page);
        resultMap.put("pagesize",pagesize);
        resultMap.put("deliveryManId",deliveryManId);
        resultMap.put("orderStatus",orderStatus);
        List<Map<String,Object>> myOrderList= orderService.getOrderDetail(resultMap);
        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
        if (myOrderList!=null&&myOrderList.size()>0){
            for(Map<String, Object> myOrderList_:myOrderList){
                Map<String, Object> map = new HashMap<String, Object>();
                //订单状态
                orderStatus=StringUtil.convertNullToEmpty(myOrderList_.get("orderStatus"));
                map.put("orderStatus",DictUtils.getDictLabel(orderStatus,"paotui_order_state",""));
                //订单类型
                String orderType = StringUtil.convertNullToEmpty(myOrderList_.get("orderType"));
                map.put("orderType",DictUtils.getDictLabel(orderType,"paotui_order_type",""));
                //订单预约类型：1、立即订单；2：预约订单
                String bookingType = StringUtil.convertNullToEmpty(myOrderList_.get("bookingType"));
                map.put("bookingType", DictUtils.getDictLabel(bookingType, "paotui_booking_type", ""));
                if ("2".equals(bookingType)) {
                    //预约时间
                    Date bookingTime = (Date) myOrderList_.get("bookingTime");
                    map.put("bookingTime", DateUtil.getChineseDateTime(bookingTime));
                }
                //跑腿费
                map.put("originalFreight",StringUtil.convertNullToEmpty(myOrderList_.get("originalFreight")));
                /*帮送、帮取*/
                if ("1".equals(orderType) || "2".equals(orderType)) {
                    String addressA = "";
                    addressA += StringUtil.convertNullToEmpty(myOrderList_.get("locationAddressA"));
                    addressA += StringUtil.convertNullToEmpty(myOrderList_.get("locationAddressNameA"));
                    addressA += StringUtil.convertNullToEmpty(myOrderList_.get("detailAddressA"));
                    map.put("addressA", addressA);
                    String addressB = "";
                    addressB += StringUtil.convertNullToEmpty(myOrderList_.get("locationAddressB"));
                    addressB += StringUtil.convertNullToEmpty(myOrderList_.get("locationAddressNameB"));
                    addressB += StringUtil.convertNullToEmpty(myOrderList_.get("detailAddressB"));
                    map.put("addressB", addressB);
                }
                /*帮买*/
                else if ("3".equals(orderType)) {
                    String isNearBuy = StringUtil.convertNullToEmpty(myOrderList_.get("isNearBuy"));
                    if ("2".equals(isNearBuy)) {
                        String addressA = "";
                        addressA += StringUtil.convertNullToEmpty(myOrderList_.get("locationAddressA"));
                        addressA += StringUtil.convertNullToEmpty(myOrderList_.get("locationAddressNameA"));
                        addressA += StringUtil.convertNullToEmpty(myOrderList_.get("detailAddressA"));
                        map.put("addressA", addressA);
                    } else {
                        map.put("addressA", DictUtils.getDictLabel(isNearBuy, "paotui_is_near_buy", ""));
                    }
                    String addressB = "";
                    addressB += StringUtil.convertNullToEmpty(myOrderList_.get("locationAddressB"));
                    addressB += StringUtil.convertNullToEmpty(myOrderList_.get("locationAddressNameB"));
                    addressB += StringUtil.convertNullToEmpty(myOrderList_.get("detailAddressB"));
                    map.put("addressB", addressB);
                    //购买要求
                    map.put("buyRequire", StringUtil.convertNullToEmpty(myOrderList_.get("buyRequire")));
                    //商是否知道价格
                    String isKonwPrice = StringUtil.convertNullToEmpty(myOrderList_.get("isKonwPrice"));
                    if ("1".equals(isKonwPrice)) {
                        //商品价格
                        map.put("buyPrice", StringUtil.convertNullToEmpty(myOrderList_.get("buyPrice")));
                    } else {
                        map.put("isKonwPrice", DictUtils.getDictLabel(isKonwPrice, "paotui_is_konw_price", ""));
                    }
                }
                /*带排队*/
                else if ("4".equals(orderType)) {
                    String address = "";
                    address += StringUtil.convertNullToEmpty(myOrderList_.get("lineupLocationAddress"));
                    address += StringUtil.convertNullToEmpty(myOrderList_.get("lineupLocationAddressName"));
                    address += StringUtil.convertNullToEmpty(myOrderList_.get("lineupDetailAddress"));
                    map.put("address", address);
                    //联系电话
                    map.put("lineupPhone", StringUtil.convertNullToEmpty(myOrderList_.get("lineupPhone")));
                    // 排队要求
                    map.put("lineupRequire", StringUtil.convertNullToEmpty(myOrderList_.get("lineupRequire")));
                    // 排队时长
                    map.put("lineupDuration", StringUtil.convertNullToEmpty(myOrderList_.get("lineupDuration")));
                }
                mapList.add(map);
            }
            Map<String,Object> map1 = new HashMap<String, Object>();
            map1.put("row",myOrderList.size());
            mapList.add(map1);
            resultMap = getResultMap("1","获取我的订单列表成功",mapList);
        }else {
            resultMap = getResultMap("0","获取我的订单列表失败");
        }
        return JSONUtil.getJson(resultMap);
    }


    @ResponseBody
    @RequestMapping(value = "weixinAuth", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "跑客微信认证", httpMethod = "POST", notes = "跑客微信认证,将app端获取的微信open_id保存的数据库表中")
    public String weixinAuth(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="商户appid下，某用户的openid") @RequestParam(value = "openid") String openid) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        int ret  = deliveryManService.updateOfWeixinAuth(deliveryManId,openid);

        if(ret==1){
            return JSONUtil.getJson(getResultMap("1","微信认证成功"));
        }
        else if(ret==2){
            return JSONUtil.getJson(getResultMap("0","用户不存在"));
        }
        else{
            return JSONUtil.getJson(getResultMap("0","微信认证失败"));
        }
    }


    @ResponseBody
    @RequestMapping(value = "applyWithdrawals", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "跑客申请提现", httpMethod = "POST", notes = "跑客申请提现,后台审核提现申请")
    public String applyWithdrawals(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="提现金额") @RequestParam(value = "money") BigDecimal money) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        int flag = deliveryManService.updateOfApplyWithdrawals(deliveryManId,money);
        if(flag==1){
            resultMap = getResultMap("1","提现申请提交成功，请等待审核");
        }else if(flag==2){
            resultMap = getResultMap("-1","获取跑客失败");
        }else if (flag==3){
            resultMap = getResultMap("-2","提现金额大于余额");
        }else {
            resultMap = getResultMap("0","提现申请提交失败");
        }
        return JSONUtil.getJson(resultMap);
    }


    @ResponseBody
    @RequestMapping(value = "getMyWorkedOrder", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "跑腿历程", httpMethod = "GET", notes = "APP端使用")
    public String getMyWorkedOrder(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "页") @RequestParam(value = "page") int page,
            @ApiParam(value = "每页数量") @RequestParam(value = "pagesize") int pagesize) {
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        map.put("pagesize",pagesize);
        map.put("accountType",2);
        map.put("deliveryManId",deliveryManId);
        map.put("capitalChangeReason","跑客跑腿费");
        Map<String, Object> dataList = capitalLogService.getPageList(map);
        List<Map<String,Object>> gridDataList=(List<Map<String,Object>>)dataList.get("Rows");
        List<Map<String,Object>> needDataList=new ArrayList<Map<String, Object>>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for(Map<String,Object> data:gridDataList){
            Map<String,Object> needData= new HashMap<String, Object>();
            needData.put("realName",data.get("realName"));
            needData.put("changeMoney",data.get("changeMoney"));
            needData.put("createDate",dateFormat.format(data.get("createDate")));
            needData.put("orderTypeName",DictUtils.getDictLabel(String.valueOf(data.get("type")),"paotui_order_type",""));
            needDataList.add(needData);
        }
        dataList.put("Rows",needDataList);
        return JSONUtil.getJson(dataList);
    }


    @ResponseBody
    @RequestMapping(value = "getRankingList", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "本城市跑客收入排行榜(前20)", httpMethod = "GET", notes = "APP端")
    public String getRankingList(
            @ApiParam(value = "城市id") @RequestParam(value = "openCityId") String openCityId,
            @ApiParam(value = "类型：1：日排行榜；2：月排行榜") @RequestParam(value = "rankingListType") int rankingListType) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("belongCityId",openCityId);
        map.put("accountType",2);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        if(rankingListType==1){
            Date nowDate= new Date();
            String today = format.format(nowDate);
            map.put("createDate1",today+" 00:00:00");
            map.put("createDate2",today+" 23:59:59");
        } else if(rankingListType==2){
            //获取前月的第一天
            Calendar cal_1=Calendar.getInstance();//获取当前日期
            cal_1.add(Calendar.MONTH, -1);
            cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
            map.put("createDate1",format.format(cal_1.getTime())+" 00:00:00");
            //获取前月的最后一天
            Calendar cale = Calendar.getInstance();
            cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
            map.put("createDate2", format.format(cale.getTime())+" 23:59:59");
        }
        map.put("capitalChangeReason","跑客跑腿费");
        List<Map<String,Object>> gridDataList= capitalLogService.getRankingList(map);
        List<Map<String,Object>> needDataList=new ArrayList<Map<String, Object>>();
        for(int i=0;i<gridDataList.size() && i<20;i++){
            Map<String,Object> data=gridDataList.get(i);
            Map<String,Object> needData= new HashMap<String, Object>();
            needData.put("realName",data.get("realName"));
            needData.put("changeMoney",data.get("changeMoney"));
            needData.put("ranking",String.valueOf(i+1));
            needDataList.add(needData);
        }
        if(rankingListType==1){
            resultMap = getResultMap("1","获取日排行榜成功",needDataList);
        }else if(rankingListType==2){
            resultMap = getResultMap("1","获取月排行榜成功",needDataList);
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getMyMarketQrcode", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取我的推广二维码", httpMethod = "GET", notes = "APP端使用")
    public String getMyMarketQrcode(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token) {
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        DeliveryMan deliveryMan = deliveryManService.get(deliveryManId);
        if(deliveryMan!=null){
            if(StringUtils.isNotEmpty(deliveryMan.getQrCode())){
                return JSONUtil.getJson(getResultMap("1","获取我的推广二维码成功",ConstantUtil.SERVER_URL+deliveryMan.getQrCode()));
            } else{
                String url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
                url+= AccessToken.getInstance().getAccessToken();
                //生成我的推广二维码
                String qrcodeJson2 = "{\"action_name\":\"QR_LIMIT_STR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":\""+String.valueOf(deliveryMan.getId())+"\"}}}";
                CloseableHttpClient httpClient = WeixinHttpUtil.getHTTPSClient();
                String result = WeixinHttpUtil.postJson(httpClient,url,qrcodeJson2);
                JSONObject resultJson=  JSONObject.parseObject(result);
                String realPath = ConstantUtil.SAVE_PATH + "/" + "qrcode" + "/" ;
                try{
                    File filePath = new File(realPath);
                    if (!filePath.exists()) {
                        filePath.mkdirs();
                    }
                    System.out.println("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+resultJson.getString("ticket"));
                    String fileName=QRCodeUtil.encodeMarketForDeliveryMan(resultJson.getString("url"), String.valueOf(deliveryManId), realPath, false);
                    //更新跑客表推广二维码
                    deliveryManService.updateMarketQrcode(deliveryManId,"/qrcode/" +fileName );
                    return JSONUtil.getJson(getResultMap("1","获取我的推广二维码成功",ConstantUtil.SERVER_URL+"/qrcode/" +fileName));
                } catch(Exception e){
                }
            }
        }else{
            return JSONUtil.getJson(getResultMap("0","获取我的推广二维码失败"));
        }
        return JSONUtil.getJson(getResultMap("0","获取我的推广二维码失败"));

    }

}
