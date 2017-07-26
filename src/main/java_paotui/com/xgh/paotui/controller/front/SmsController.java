package com.xgh.paotui.controller.front;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.SmsLog;
import com.xgh.paotui.services.ISmsLogService;
import com.xgh.util.JSONUtil;
import com.xgh.util.ShortMessageUtil;
import com.xgh.util.ValidationCode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@Api(value="短信API")
@RequestMapping(value = "paotui/front/v1/sms/")
public class SmsController extends BaseController {

    private Logger logger = Logger.getLogger(SmsController.class);

    @Autowired
    protected ISmsLogService smsLogService;

    @ResponseBody
    @RequestMapping(value = "getVerifCode", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取短信验证码", httpMethod = "POST", notes = "短信端")
    public String getVerifCode(
            @ApiParam(value = "手机号") @RequestParam(value = "telephone") String telephone) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = ValidationCode.getSecurityCode(4,3);
        String content = "您本次注册的验证码为："+code+"。30分钟内有效，请确认本人操作并注意保密！";
        int flag = ShortMessageUtil.sendMessage(telephone,content);
        if(flag>0){
            request.getSession().setAttribute("telephone",telephone);
            request.getSession().setAttribute("validationCode",code);
            resultMap = getResultMap("1","验证码发送成功！");
        }else{
            resultMap = getResultMap("0","验证码发送失败！");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "sendSms", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "发送短信", httpMethod = "POST", notes = "短信端")
    public String sendSms(
            @ApiParam(value = "手机号") @RequestParam(value = "telephone") String telephone,
            @ApiParam(value = "短信内容") @RequestParam(value = "content") String content) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        int flag = ShortMessageUtil.sendMessage(telephone,content);
        SmsLog smsLog = new SmsLog();
        smsLog.setTelephone(telephone);
        smsLog.setContent(content);
        smsLog.setSendTime(new Date());
        if(flag>0){
            smsLog.setStatus(1);
            smsLogService.add(smsLog);
            resultMap = getResultMap("1","发送短信成功！");
        }else{
            smsLog.setStatus(2);
            smsLogService.add(smsLog);
            resultMap = getResultMap("0","发送短信失败！");
        }
        return JSONUtil.getJson(resultMap);
    }
}
