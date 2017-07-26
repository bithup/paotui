package com.xgh.paotui.controller;

import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.services.ICapitalLogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Scope("prototype")
@RequestMapping(value="/paotui/capitalLog/")
public class CapitalLogController extends BaseController {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(CapitalLogController.class);

    @Autowired
    protected ICapitalLogService capitalLogService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "init")
    public ModelAndView init() {
        return getModelAndView("paotui/capitalLog/init");
    }

    @RequestMapping(value = "/getList")
    public void getList() {
        logger.info("getList");
        outJson(capitalLogService.getGridList(request));
    }

}
