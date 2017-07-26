package com.xgh.paotui.controller.front.weixinFront;


import com.alibaba.fastjson.JSONObject;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.controller.front.ComnonFrontController;
import com.xgh.paotui.entity.AppToken;
import com.xgh.paotui.entity.Customer;
import com.xgh.paotui.services.ICustomerService;
import com.xgh.paotui.weixin.WeixinHttpUtil;
import com.xgh.paotui.weixin.WeixinUtil;
import com.xgh.util.JSONUtil;
import com.xgh.util.JWT;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping(value="/paotui/wechat/")
public class WechatController extends BaseController {

    private static Logger logger = Logger.getLogger(WechatController.class);

    @Autowired
    protected ICustomerService customerService;

    /**
     * 微信跳转到指定页面
     */
    @RequestMapping(value = "toWeixinPage")
    @ApiIgnore
    public ModelAndView toWeixinPage(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "state") String state
    ) {

        ModelAndView view = new ModelAndView();


        logger.info("网页授权返回code:"+code);
        logger.info("state:"+state);

        /*通过code换取网页授权access_token*/
        StringBuffer url= new StringBuffer("https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
        url.append(WeixinUtil.APP_ID) ;

        url.append("&secret=");
        url.append(WeixinUtil.APP_SECRET);

        url.append("&code=");
        url.append(code);

        url.append("&grant_type=authorization_code");

        CloseableHttpClient httpClient = WeixinHttpUtil.getHTTPSClient();

        String result = WeixinHttpUtil.get(httpClient, url.toString());

        JSONObject resJSON=JSONObject.parseObject(result);


        if(resJSON.containsKey("openid")){
            String openid=resJSON.getString("openid");
            view.addObject("openid", openid);
            view.addObject("access_token", resJSON.getString("access_token"));
            view.addObject("refresh_token", resJSON.getString("refresh_token"));


            //判断是否需要登录
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("token",openid);
            List<Customer>  customers= customerService.getList(map);
            if(customers.size()>0){
                view.addObject("token",this.getTokenByCustomerId(customers.get(0).getId()));

                if("1".equals(state)){
                    view.setViewName("weixin/placeOrder");
                }
                else if("2".equals(state)){
                    view.setViewName("weixin/myOrder");
                }
                else if("3".equals(state)){
                    view.setViewName("weixin/myCenter");
                }
            }
            else{

                view.addObject("state",state);
                view.setViewName("weixin/login");
            }

            view.addObject("oauth2", "获取微信网页授权成功");
        }
        else{
            logger.info("获取微信网页授权失败");
            view.addObject("oauth2", "获取微信网页授权失败");
        }



        return view;
    }



    /**
     * 微信跳转到state指定页面
     */
    @RequestMapping(value = "toMenuPage")
    @ApiIgnore
    public ModelAndView toMenuPage(
            @RequestParam(value = "openid") String openid,
            @RequestParam(value = "token") String token,
            @RequestParam(value = "state") String state
    ) {

        ModelAndView view = new ModelAndView();

        view.addObject("openid",openid);
        view.addObject("token", token);

        if("1".equals(state)){
            view.setViewName("weixin/placeOrder");
        }
        else if("2".equals(state)){
            view.setViewName("weixin/myOrder");
        }
        else if("3".equals(state)){
            view.setViewName("weixin/myCenter");
        }



        return view;
    }


    private String getTokenByCustomerId(long customerId){

        AppToken appToken = new AppToken();
        appToken.setId(customerId);
        appToken.setAppType("weixin");
        return  JWT.sign(appToken,30L * 24L * 3600L * 1000L);

    }
}
