package com.xgh.mng.services;

import com.xgh.mng.dao.ISysButtonDao;
import com.xgh.mng.dao.ISysPrivilegeListDao;
import com.xgh.mng.entity.SysButton;
import com.xgh.mng.entity.SysMenu;
import com.xgh.mng.entity.SysUser;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysButtonService")
public class SysButtonServiceImpl
  implements ISysButtonService
{
  @Autowired
  protected ISysButtonDao sysButtonDao;
  @Autowired
  protected ISysMenuService sysMenuService;
  @Autowired
  protected ISysPrivilegeListDao sysPrivilegeListDao;
  
  public int add(SysButton sysButton)
  {
    return this.sysButtonDao.add(sysButton);
  }
  
  public int delete(long id)
  {
    return this.sysButtonDao.deleteById(id);
  }
  
  public int deleteByIds(List<Long> ids)
  {
    return this.sysButtonDao.deleteByIds(ids);
  }
  
  public int update(SysButton sysButton)
  {
    return this.sysButtonDao.update(sysButton);
  }
  
  public SysButton get(long id)
  {
    return this.sysButtonDao.get(id);
  }
  
  public List<SysButton> getList(SysButton sysButton)
  {
    List<SysButton> list = this.sysButtonDao.getList(sysButton);
    


    return list;
  }
  
  public List<SysButton> getListPage(Map<String, Object> map)
  {
    return this.sysButtonDao.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysButtonDao.getRows(map);
  }
  
  public Map<String, Object> getGridData(long menuId)
  {
    Map<String, Object> gridData = new HashMap();
    
    Map<String, Object> map = new HashMap();
    map.put("menuId", Long.valueOf(menuId));
    List<SysButton> dataList = this.sysButtonDao.getListPage(map);
    if (dataList == null) {
      dataList = new ArrayList();
    }
    gridData.put("Rows", dataList);
    gridData.put("Total", Integer.valueOf(dataList.size()));
    return gridData;
  }
  
  public void saveButton(long menuId, List<Map<String, Object>> dataList)
  {
    List<SysButton> list = new ArrayList();
    
    List<Long> deletefilerIds = new ArrayList();
    
    Date date = new Date();
    if (dataList != null)
    {
      for (Map<String, Object> data : dataList)
      {
        SysButton sysButton = new SysButton();
        sysButton.setCreateDate(date);
        sysButton.setUpdateDate(date);
        sysButton.setMenuId(menuId);
        sysButton.setStatus(1);
        sysButton.setButtonName(data.get("buttonName") + "");
        if ((data.get("buttonUrl") != null) && (!data.get("buttonUrl").equals(""))) {
          sysButton.setButtonUrl(data.get("buttonUrl") + "");
        }
        sysButton.setButtonIcon(data.get("buttonIcon") + "");
        if ((data.get("buttonClick") != null) && (!data.get("buttonClick").equals(""))) {
          sysButton.setButtonClick(data.get("buttonClick") + "");
        }
        if ((data.get("ord") != null) && (!data.get("ord").equals(""))) {
          sysButton.setOrd(Long.valueOf(data.get("ord") + "").longValue());
        }
        if ((data.get("isVisible") != null) && (!data.get("isVisible").equals(""))) {
          sysButton.setIsVisible(Long.valueOf(data.get("isVisible") + "").longValue());
        }
        if ((data.get("isPublic") != null) && (!data.get("isPublic").equals(""))) {
          sysButton.setIsPublic(Long.valueOf(data.get("isPublic") + "").longValue());
        }
        if ((data.get("isSys") != null) && (!data.get("isSys").equals(""))) {
          sysButton.setIsSys(Long.valueOf(data.get("isSys") + "").longValue());
        }
        if ((data.get("id") == null) || (data.get("id").equals("")))
        {
          list.add(sysButton);
        }
        else
        {
          deletefilerIds.add(Long.valueOf(data.get("id") + ""));
          sysButton.setId(Long.valueOf(data.get("id") + "").longValue());
          this.sysButtonDao.update(sysButton);
        }
      }
      Map<String, Object> params = new HashMap();
      params.put("menuId", Long.valueOf(menuId));
      if (!deletefilerIds.isEmpty()) {
        params.put("filterIdList", deletefilerIds);
      }
      this.sysButtonDao.deleteByMenuId(params);
      if (!list.isEmpty()) {
        this.sysButtonDao.addBatch(list);
      }
    }
  }
  
  public String getMenuButtons(HttpServletRequest request, long sysRoleId, SysUser sysUser)
  {
    StringBuffer buttonStrBuffer = new StringBuffer("");
    
    SysMenu sysMenu = this.sysMenuService.getSysMenuByRequest(request);
    if (sysMenu != null)
    {
      List<SysButton> buttonList = new ArrayList();
      if ((sysUser.getUnitId() == 1L) && (sysMenu.getIsSys() == 1) && ("SuperAdmin".equals(sysUser.getAccount())))
      {
        buttonList = this.sysButtonDao.getButtonByMenuId(sysMenu.getId());
      }
      else
      {
        List<Long> roleIds = this.sysPrivilegeListDao.getRoleIdsByUserId(sysUser.getId());
        if ((roleIds != null) && (!roleIds.isEmpty()))
        {
          Map<String, Object> buttonMap = new HashMap();
          buttonMap.put("menuId", Long.valueOf(sysMenu.getId()));
          buttonMap.put("unitId", Long.valueOf(sysUser.getUnitId()));
          buttonMap.put("sysRoleId", Long.valueOf(sysRoleId));
          buttonMap.put("roleList", roleIds);
          if (sysUser.getIsSys() == 1L) {
            buttonMap.put("type", "sys");
          }
          buttonList = this.sysButtonDao.getUserButtons(buttonMap);
        }
      }
      if ((buttonList != null) && (!buttonList.isEmpty())) {
        for (SysButton sysButton : buttonList)
        {
          if (!buttonStrBuffer.toString().equals("")) {
            buttonStrBuffer.append(",");
          }
          buttonStrBuffer.append(String.format("{ line:true },{ text: '%s' , click: %s , icon:'%s' }", new Object[] { sysButton.getButtonName(), sysButton.getButtonClick(), sysButton.getButtonIcon() }));
        }
      }
    }
    return buttonStrBuffer.toString();
  }
}
