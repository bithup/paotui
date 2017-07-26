package com.xgh.paotui.controller;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.entity.DeliveryMan;
import com.xgh.paotui.entity.DeliveryManAuth;
import com.xgh.paotui.services.IDeliveryManAuthService;
import com.xgh.paotui.services.IDeliveryManService;
import com.xgh.paotui.services.IFileDataServiceNew;
import com.xgh.util.ConstantUtil;
import com.xgh.util.DictUtils;
import com.xgh.util.JSONUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope("prototype")
@RequestMapping(value="/paotui/deliveryMan/")
public class DeliveryManController extends BaseController {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(DeliveryManController.class);

    @Autowired
    protected IDeliveryManService deliveryManService;

    @Autowired
    protected IDeliveryManAuthService deliveryManAuthService;

    @Autowired
    protected IFileDataServiceNew fileDataServiceNew;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    @RequestMapping(value = "init")
    public ModelAndView init() {
        return getModelAndView("paotui/deliveryMan/init");
    }

    @RequestMapping(value = "add")
    public ModelAndView add(@ModelAttribute DeliveryMan deliveryMan, @RequestParam String op) {
        ModelAndView view = new ModelAndView();
        view.addObject("op", op);
        if ("modify".equals(op)) {
            deliveryMan = deliveryManService.get(deliveryMan.getId());
            String qrCodeUrl = ConstantUtil.SERVER_URL+deliveryMan.getQrCode();
            DeliveryManAuth deliveryManAuth= deliveryManAuthService.getDeliveryManAuth(deliveryMan.getId());
            if (deliveryManAuth != null) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("instId", getCurrentInstId());
                params.put("dataId", deliveryManAuth.getId());
                params.put("dataCode", ConstantUtil.FileUploadCode.DeliveryMan.value());
                params.put("dataVersion", 0);
                params.put("server", ConstantUtil.SERVER_URL);
                List<Map<String, Object>> fileDataList = fileDataServiceNew.getFileDatas(params);
                view.addObject("fileDataListJson", JSONUtil.getJson(fileDataList));
            }
            view.addObject("qrCodeUrl", qrCodeUrl);
            view.addObject("deliveryManAuth", deliveryManAuth);
        }
        view.setViewName("paotui/deliveryMan/add");
        view.addObject("deliveryMan", deliveryMan);
        view.addObject("belongTpeRadioInit", DictUtils.getDictListForRadio("paotui_belong_type"));
        return view;
    }

    @RequestMapping(value = "/getList")
    public void getList() {
        logger.info("getList");
        SysUser sysUser = this.getCurrentUser();
        String belongCityId="";
        if (sysUser.getOpenCityId()!=null  && sysUser.getOpenCityId()!=0){
           belongCityId=String.valueOf(sysUser.getOpenCityId());
        }
        outJson(deliveryManService.getGridList(request,belongCityId));
    }

    @RequestMapping(value = "/save")
    public void save(@ModelAttribute DeliveryMan deliveryMan) {
        logger.info("save");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (deliveryMan != null) {
            int flg = deliveryManService.save(request,deliveryMan);
            if (flg == 1) {
                    resultMap = getResultMap("1", "修改成功!");
            } else {
                    resultMap = getResultMap("0", "修改失败!");
            }
        } else{
            resultMap = getResultMap("0", "数据初始化失败!");
        }
        outJson(resultMap);
    }

}
