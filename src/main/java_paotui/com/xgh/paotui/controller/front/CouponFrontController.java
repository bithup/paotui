package com.xgh.paotui.controller.front;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.Coupon;
import com.xgh.paotui.services.ICouponService;
import com.xgh.util.DictUtils;
import com.xgh.util.JSONUtil;
import com.xgh.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Api(value="优惠劵API")
@RequestMapping(value = "paotui/front/v1/coupon/")
public class CouponFrontController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(CouponFrontController.class);

    @Autowired
    protected ICouponService couponService;

    @ResponseBody
    @RequestMapping(value = "getCouponList", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取优惠劵列表", httpMethod = "GET", notes = "APP端使用")
    public String getCouponList(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        map.put("customerId",customerId);
        //先获取所有优惠劵，根据优惠劵有效时间修改状态
        List<Map<String,Object>> list = couponService.getCoupon(map);
        if (list!=null&&list.size()>0) {
            for (Map<String, Object> list_ : list) {
                long couponId = Long.parseLong(StringUtil.convertNullToEmpty(list_.get("id")));
                Coupon coupon = couponService.get(couponId);
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String endDate = sdf.format(coupon.getEndDate());
                String data = sdf.format(new Date());
                if (data.compareTo(endDate)>=0) {
                    coupon.setUseStatus(3);
                    coupon.setUpdateDate(new Date());
                    couponService.updateGetCouponList(coupon);
                }
            }
        }
        map.put("useStatus",1);
        List<Map<String,Object>> couponList = couponService.getCoupon(map);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (couponList!=null&&couponList.size()>0) {
            for (Map<String,Object> couponList_:couponList){
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("couponName",StringUtil.convertNullToEmpty(couponList_.get("couponName")));
                map1.put("moneyAmount",StringUtil.convertNullToEmpty(couponList_.get("moneyAmount")));
                map1.put("startDate",StringUtil.convertNullToEmpty(couponList_.get("startDate")));
                map1.put("endDate",StringUtil.convertNullToEmpty(couponList_.get("endDate")));
                String useStatus = StringUtil.convertNullToEmpty(couponList_.get("useStatus"));
                map1.put("useStatus", DictUtils.getDictLabel(useStatus,"paotui_use_status",""));
                mapList.add(map1);
            }
            resultMap = getResultMap("1","优惠劵列表显示成功",mapList);
        }else {
            resultMap = getResultMap("0","优惠劵列表显示失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getUseCoupon", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "支付时获取可用优惠劵", httpMethod = "GET", notes = "APP端使用")
    public String getUseCoupon(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String useDate = sdf.format(new Date());
        map.put("customerId",customerId);
        map.put("useStatus",1);
        List<Map<String, Object>> couponList= couponService.getCoupon(map);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (couponList!=null&&couponList.size()>0) {
            for (Map<String,Object> couponList_:couponList){
                String endDate = StringUtil.convertNullToEmpty(couponList_.get("endDate"));
                if (useDate.compareTo(endDate)<0) {
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    map1.put("couponName",StringUtil.convertNullToEmpty(couponList_.get("couponName")));
                    map1.put("moneyAmount",StringUtil.convertNullToEmpty(couponList_.get("moneyAmount")));
                    map1.put("startDate",StringUtil.convertNullToEmpty(couponList_.get("startDate")));
                    map1.put("endDate",StringUtil.convertNullToEmpty(couponList_.get("endDate")));
                    String useStatus = StringUtil.convertNullToEmpty(couponList_.get("useStatus"));
                    map1.put("useStatus", DictUtils.getDictLabel(useStatus,"paotui_use_status",""));
                    mapList.add(map1);
                }
            }
            resultMap = getResultMap("1","可用优惠劵获取成功",mapList);
        }else {
            resultMap = getResultMap("0","可用优惠劵获取失败");
        }
        return JSONUtil.getJson(resultMap);
    }

}
