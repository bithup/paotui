package com.xgh.paotui.controller.front;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.*;
import com.xgh.paotui.services.*;
import com.xgh.util.*;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Tian on 2017/3/21.
 */
@Controller
@Api(value="跑客端帮送或帮取接口API")
@RequestMapping(value = "paotui/front/v1/deliveryManSend/")
public class DeliveryManSendController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(DeliveryManSendController.class);

    @Autowired
    protected IDeliveryManStateService deliveryManStateService;

    @Autowired
    protected IDeliveryManService deliveryManService;

    @Autowired
    protected IOrderService orderService;

    @Autowired
    protected IOrderActionService orderActionService;

    @Autowired
    protected IFeeAllocationService feeAllocationService;

    @ResponseBody
    @RequestMapping(value = "actionStepForSend", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮送跑客执行动作接口", httpMethod = "POST", notes = "帮送联系发货人、我已达到、我已取货")
    public String actionStepForSend(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value = "当前操作步骤(1,2,4,5,6)") @RequestParam(value = "actionStep") int actionStep
    ) {
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String, Object> resultMap=new HashMap<String, Object>();
        OrderAction orderAction= new OrderAction();
        orderAction.setOrderId(orderId);
        orderAction.setActionTime(new Date());
        orderAction.setStatus(1);
        /*动作1:联系下单人*/
        if(actionStep==1){
            orderAction.setActionName("联系下单人");
            orderAction.setActionType(1);
            orderAction.setShowFlag(1);
        }
        /*动作2:到达取货货地*/
        else if (actionStep==2){
            orderAction.setActionName("跑客到达取货地");
            orderAction.setActionType(1);
            orderAction.setShowFlag(1);
        }
        /*动作4:完成取货*/
        else if (actionStep==4){
            orderAction.setActionName("跑客已取货");
            orderAction.setActionType(1);
            orderAction.setShowFlag(1);
        }
        /*动作5:联系收货人*/
        else if (actionStep==5){
            orderAction.setActionName("联系收货人");
            orderAction.setActionType(1);
            orderAction.setShowFlag(1);
        }
        /*动作6:到达收货地*/
        else if (actionStep==6){
            //我已送达动作
            orderAction.setActionName("到达收货地");
            orderAction.setActionType(1);
            orderAction.setShowFlag(1);
        }else {
            resultMap=getResultMap("-1","执行动作不正确");
            return JSONUtil.getJson(resultMap);
        }
        int flag = orderActionService.updateSendOrderActionStep(deliveryManId,orderId,actionStep,orderAction);
        if (flag>0){
            resultMap=this.getResultMap("1","跑客执行动作"+actionStep+"成功");
        }else {
            resultMap=this.getResultMap("0","跑客执行动作"+actionStep+"失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "actionStepPhotoForSend", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮送跑客执行拍照接口", httpMethod = "POST", notes = "帮送拍照接口")
    public String actionStepPhotoForSend(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value = "货物照片") @RequestPart(value = "goodsPicUrl",required = false) MultipartFile goodsPicUrl,
            HttpServletRequest request){
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String, Object> retMap=new HashMap<String, Object>();
        OrderAction orderAction= new OrderAction();
        orderAction.setActionTime(new Date());
        orderAction.setOrderId(orderId);
        orderAction.setStatus(1);
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Iterator fileNames = multipartRequest.getFileNames();//可以上传一张也可以上传多张图片
            String photos="";
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
                    String realPath = ConstantUtil.SAVE_PATH + "/" + "orderAction" + "/" + relative_path;
                    photos+="/" + "orderAction" + "/" + relative_path + saveName;
                    File filePath = new File(realPath);
                    if (!filePath.exists()) {
                        filePath.mkdirs();
                    }
                    FileUtil.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, saveName));
                }
            }
            orderAction.setActionImage(photos);
            //拍照动作
            orderAction.setActionName("跑客已拍照");
            orderAction.setActionType(1);//完成的动作
            orderAction.setShowFlag(2);//存档，用户端不显示
            int flag = orderActionService.save(request,orderAction);
            if (flag>0){
                Order order = orderService.get(orderId);
                order.setActionStep(3);
                orderService.update(order);
                retMap = getResultMap("1", "执行拍照动作成功");
            } else {
                retMap = getResultMap("0", "执行拍照动作失败");
            }
            return JSONUtil.getJson(retMap);
        } catch (IOException var18) {
            var18.printStackTrace();
            logger.error(var18.getMessage(), var18);
        }
        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "actionStepInputSmsCodeForSend", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮送跑客执行输入验证码接口", httpMethod = "POST", notes = "帮送执行输入验证码接口")
    public String actionStepInputSmsCodeForSend(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value = "验证码") @RequestParam(value = "smsCode") String smsCode){
        Map<String, Object> resultMap=new HashMap<String, Object>();
        long deliveryManId = getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Order order = orderService.get(orderId);
        if (order!=null&&order.getId()>0){
            if(StringUtil.isEmpty(smsCode) || !smsCode.equals(order.getSmsCode())){
                resultMap = getResultMap("0","验证码不正确");
            } else {
                /*增加跑客操作步骤*/
                Date date = new Date();
                OrderAction orderAction= new OrderAction();
                orderAction.setOrderId(orderId);
                orderAction.setActionName("签收");
                orderAction.setActionTime(date);
                orderAction.setActionType(1);
                orderAction.setShowFlag(1);
                orderAction.setStatus(1);
                /*改变订单状态*/
                order.setActionStep(7);
                order.setOrderStatus(5);
                order.setSignTime(date);
                order.setEvaluationStatus(2);
                /*跑客获取佣金*/
                BigDecimal fee = null;
                BigDecimal originalFreight = order.getOriginalFreight();
                FeeAllocation feeAllocation = feeAllocationService.get(order.getOrderCity());
                //佣金分配方式（1：按比例； 2：按单量）
                int allocationType = feeAllocation.getAllocationType();
                if (allocationType==1){
                    BigDecimal a = new BigDecimal("100");
                    BigDecimal runProportion = feeAllocation.getRunProportion();
                    fee = originalFreight.multiply(runProportion).divide(a);
                }else if (allocationType==2){
                    fee = feeAllocation.getRunOrderFee();
                }
                //小费
                BigDecimal tips = order.getTips();
                if (tips!=null){
                    fee = fee.add(tips);
                }
                DeliveryMan deliveryMan = deliveryManService.get(deliveryManId);
                BigDecimal balance = deliveryMan.getBalance();
                deliveryMan.setBalance(balance.add(fee));
                deliveryMan.setGetOrderCount(deliveryMan.getGetOrderCount()+1);
                /*添加资金日志*/
                CapitalLog capitalLog = new CapitalLog();
                capitalLog.setAccountType(2);
                capitalLog.setAccountId(deliveryManId);
                capitalLog.setLoginName(deliveryMan.getLoginName());
                capitalLog.setRealName(deliveryMan.getRealName());
                capitalLog.setType(order.getOrderType());
                capitalLog.setOrderId(order.getId());
                capitalLog.setOrderNo(order.getOrderNo());
                capitalLog.setTradeFrom(1);
                capitalLog.setCapitalChangeReason("跑客跑腿费");
                capitalLog.setChangeMoney(fee);
                capitalLog.setCreateDate(date);
                capitalLog.setStatus(1);
                int flag = orderActionService.updateSendSmsCodeOrderAction(deliveryManId,order,orderAction,deliveryMan,capitalLog);
                if (flag>0){
                    resultMap=this.getResultMap("1","跑客执行输入验证码动作成功");
                }else {
                    resultMap=this.getResultMap("0","跑客执行输入验证码动作失败");
                }
            }
        }else {
            resultMap = getResultMap("0","订单不存在");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getGoSendRoute", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取接单跑客、发货地/取货地位置",httpMethod = "POST", notes = "APP跑客端")
    public String getGoSendRoute(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        DeliveryManState deliveryManState = deliveryManStateService.get(deliveryManId);
        Order order = orderService.get(orderId);
        if (deliveryManState!=null&&order!=null){
            Double deliveryManLongitude = deliveryManState.getNowLongitude();
            Double deliveryManLatitude = deliveryManState.getNowLatitude();
            Double orderLongitude = order.getLongitude();
            Double orderLatitude = order.getLatitude();
            map.put("deliveryManLongitude",deliveryManLongitude);
            map.put("deliveryManLatitude",deliveryManLatitude);
            map.put("orderLongitude",orderLongitude);
            map.put("orderLatitude",orderLatitude);
            resultMap = getResultMap("1","获取接单跑客、发货地/取货地位置成功",map);
        }else {
            resultMap = getResultMap("0","获取接单跑客、发货地/取货地位置失败");
        }
        return JSONUtil.getJson(resultMap);
    }

}
