package com.xgh.paotui.controller.front.weixinFront;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.Dict;
import com.xgh.paotui.entity.Notify;
import com.xgh.util.DictUtils;
import com.xgh.util.JSONUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping(value="/paotui/wechatPay/")
public class WechatPayController  extends BaseController {

    @ResponseBody
    @RequestMapping(value = "toSendPay1", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "微信端帮送支付跑腿费", httpMethod = "POST", notes = "微信端")
    public String toSendPay1(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap=new HashMap<String, Object>();


        return JSONUtil.getJson(resultMap);
    }



    @RequestMapping(value = "toSendPay")
    public ModelAndView toSendPay(String token) {
        ModelAndView view = new ModelAndView();
        view.addObject("token",token);

        view.setViewName("weixin/weixinPay");
        return view;
    }




}