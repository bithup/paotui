package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.services.IOsNoteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("prototype")
@RequestMapping({"OsNote"})
public class OsNoteController
  extends BaseController
{
  private static final long serialVersionUID = 1L;
  private static Logger logger = Logger.getLogger(OsNoteController.class);
  @Autowired
  protected IOsNoteService osNoteService;
  
  public void init()
  {
    logger.info("init");
  }
  
  public String getList()
  {
    logger.info("getList");
    
    return null;
  }
  
  public String add()
  {
    logger.info("add");
    
    return null;
  }
  
  public String save()
  {
    logger.info("save");
    
    return null;
  }
  
  public String delete()
  {
    logger.info("delete");
    
    return null;
  }
}
