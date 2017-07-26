package com.xgh.paotui.controller.front;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.Notify;
import com.xgh.paotui.services.INotifyService;
import com.xgh.util.JSONUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(value="信息通知API")
@RequestMapping(value = "paotui/front/v1/notify/")
public class NotifyFrontController extends BaseController{

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(NotifyFrontController.class);

    @Autowired
    protected INotifyService notifyService;

    @ResponseBody
    @RequestMapping(value = "getNotify", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取消息通知列表", httpMethod = "GET", notes = "APP端使用")
    public String getNotify(
            @ApiParam(value="用户id") @RequestParam(required = false,value = "token") String token) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        long customerId=getCustomerIdByToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        resultMap.put("customerId",customerId);
        List<Map<String,Object>> openCityList= notifyService.getNotify(resultMap);
        if (openCityList!=null&&openCityList.size()>0){
            resultMap = getResultMap("1","获取消息通知列表成功",openCityList);
        }else {
            resultMap = getResultMap("0","获取消息通知列表失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getNotifyDetail", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取消息详情", httpMethod = "GET", notes = "APP端使用")
    public String getNotifyDetail(
            @ApiParam(value = "消息id") @RequestParam(value = "notifyId") long notifyId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Notify notify= notifyService.get(notifyId);
        if (notify!=null&&notify.getId()>0){
            resultMap = getResultMap("1","获取消息详情成功",notify);
        }else {
            resultMap = getResultMap("0","获取消息详情失败");
        }
        return JSONUtil.getJson(resultMap);
    }

}
