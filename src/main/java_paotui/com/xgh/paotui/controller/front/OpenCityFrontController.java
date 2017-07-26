package com.xgh.paotui.controller.front;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.services.IOpenCityService;
import com.xgh.util.JSONUtil;
import com.xgh.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(value="开通城市API")
@RequestMapping(value = "paotui/front/v1/openCity/")
public class OpenCityFrontController extends BaseController{

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(OpenCityFrontController.class);

    @Autowired
    protected IOpenCityService openCityService;

    @ResponseBody
    @RequestMapping(value = "getOpenCity", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取开通城市列表", httpMethod = "GET", notes = "APP端使用")
    public String getOpenCity(
            @ApiParam(value = "开通城市id") @RequestParam(required = false,value = "openCityId") String openCityId,
            @ApiParam(value = "城市名") @RequestParam(required = false,value = "cityName") String cityName) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("openCityId",openCityId);
        resultMap.put("cityName",cityName);
        List<Map<String,Object>> openCityList= openCityService.getOpenCity(resultMap);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (openCityList!=null&&openCityList.size()>0){
            for (Map<String,Object> openCityList_:openCityList){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("openCityId",StringUtil.convertNullToEmpty(openCityList_.get("id")));
                map.put("cityName",StringUtil.convertNullToEmpty(openCityList_.get("cityName")));
                mapList.add(map);
            }
            resultMap = getResultMap("1","获取开通城市列表成功",mapList);
        }else {
            resultMap = getResultMap("0","获取开通城市列表失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "isOpenCity", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "判断是是否为开通城市", httpMethod = "GET", notes = "APP端使用")
    public String isOpenCity(
            @ApiParam(value = "城市名") @RequestParam(value = "cityName") String cityName,
            HttpServletRequest request) {
        Map<String,Object> openCityList= openCityService.isOpenCity(request);
        return JSONUtil.getJson(openCityList);
    }
}
