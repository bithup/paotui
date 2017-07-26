package com.xgh.paotui.controller.front;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.*;
import com.xgh.paotui.services.IDeliveryManService;
import com.xgh.paotui.services.IFeeAllocationService;
import com.xgh.paotui.services.IOrderActionService;
import com.xgh.paotui.services.IOrderService;
import com.xgh.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lcp on 2017/4/10.
 */
@Controller
@Api(value="跑客端代排队接口API")
@RequestMapping(value = "paotui/front/v1/deliveryManLineup/")
public class DeliveryManLineupController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(DeliveryManLineupController.class);
    @Autowired
    protected IOrderService orderService;

    @Autowired
    protected IOrderActionService orderActionService;

    @Autowired
    protected IDeliveryManService deliveryManService;

    @Autowired
    protected IFeeAllocationService feeAllocationService;

    @ResponseBody
    @RequestMapping(value = "actionStepForLineup", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "跑客执行动作接口", httpMethod = "POST", notes = "APP跑客端")
    public String actionStepForLineup(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value = "当前操作步骤(1,2,4,5)") @RequestParam(value = "actionStep") int actionStep){
        Map<String, Object> resultMap=new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
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
        /*动作2:到达排队地*/
        else if (actionStep==2){
            orderAction.setActionName("到达排队地");
            orderAction.setActionType(1);
            orderAction.setShowFlag(1);
        }
        /*动作4:计时开始*/
        else if (actionStep==4){
            orderAction.setActionName("开始计时");
            orderAction.setActionType(1);
            orderAction.setShowFlag(1);
        }
        /*动作5:计时结束*/
        else if (actionStep==5){
            orderAction.setActionName("结束计时");
            orderAction.setActionType(1);
            orderAction.setShowFlag(1);
        }else {
            resultMap=getResultMap("-1","执行动作不正确");
            return JSONUtil.getJson(resultMap);
        }
        int flag = orderActionService.updateLineupOrderActionStep(deliveryManId,orderId,actionStep,orderAction);
        if (flag>0){
            resultMap=this.getResultMap("1","跑客执行动作"+actionStep+"成功");
        }else {
            resultMap=this.getResultMap("0","跑客执行动作"+actionStep+"失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "actionStepPhotoForLineup", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "跑客执行代排队拍照动作接口", httpMethod = "POST", notes = "APP跑客端")
    public String actionStepPhotoForLineup(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value = "商品照片") @RequestPart(value = "image") MultipartFile image) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            //可以上传一张也可以上传多张图片
            Iterator fileNames = multipartRequest.getFileNames();
            String actionImage = "";
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
                    String realPath_ = "/" + "orderAction" + "/" + relative_path + saveName;
                    File filePath = new File(realPath);
                    actionImage=realPath_;
                    if (!filePath.exists()) {
                        filePath.mkdirs();
                    }
                    FileUtil.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, saveName));
                }
            }
            OrderAction orderAction = new OrderAction();
            orderAction.setOrderId(orderId);
            orderAction.setActionName("跑客已拍照");
            orderAction.setActionTime(new Date());
            orderAction.setActionType(1);
            orderAction.setShowFlag(2);
            orderAction.setActionImage(actionImage);
            orderAction.setStatus(1);
            int flag = orderActionService.add(orderAction);
            if (flag > 0) {
                Order order = orderService.get(orderId);
                order.setActionStep(3);
                orderService.update(order);
                resultMap = getResultMap("1", "执行拍照动作成功");
            } else {
                resultMap = getResultMap("0", "执行拍照动作失败");
            }
        } catch (IOException var18) {
            var18.printStackTrace();
            logger.error(var18.getMessage(), var18);
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "actionStepInputSmsCodeForLineup", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "代排队跑客执行输入验证码接口", httpMethod = "POST", notes = "APP跑客端")
    public String actionStepInputSmsCodeForLineup(
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
                //执行输入验证码动作
                Date date = new Date();
                OrderAction orderAction= new OrderAction();
                orderAction.setOrderId(orderId);
                orderAction.setActionName("签收");
                orderAction.setActionTime(date);
                orderAction.setActionType(1);
                orderAction.setShowFlag(1);
                orderAction.setStatus(1);
                //改变订单状态
                order.setActionStep(6);
                order.setOrderStatus(5);
                order.setSignTime(date);
                order.setEvaluationStatus(2);
                //跑客获取佣金
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
                //添加资金日志
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
                int flag = orderActionService.updateLineupSmsCodeOrderAction(order,orderAction,deliveryMan,capitalLog);
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

}