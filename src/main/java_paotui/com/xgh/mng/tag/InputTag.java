package com.xgh.mng.tag;

import com.xgh.mng.entity.SysDictDetail;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang.StringUtils;

public class InputTag
  extends TagSupport
{
  private static final long serialVersionUID = 1L;
  private String id;
  private String name;
  private String type;
  private String dictcode;
  private String title;
  private String value;
  private String css;
  private String style;
  private Object initoption;
  private String readonly;
  private String onclick;
  private String onchange;
  private String disabled;
  private static String inputRadioF = "<input type=\"radio\" id=\"%s\"  name=\"%s\"    value=\"%s\"   class=\"%s\"  title=\"%s\"  style=\"%s\"  %s    /><label for=\"%s\">%s</label>&nbsp;&nbsp;";
  private static String inputCheckboxF = "<input type=\"checkbox\"  id=\"%s\"  name=\"%s\"    value=\"%s\"   class=\"%s\"  title=\"%s\"  style=\"%s\"   %s   /><label for=\"%s\">%s</label>&nbsp;&nbsp;";
  private static String inputSelectF = "<select  id=\"%s\"  name=\"%s\" class=\"%s\"  title=\"%s\"  style=\"%s\"   %s    >";
  private static String selectOptionF = "<option value=\"%s\" title=\"%s\"  %s>%s</option>";
  
  private void clear()
  {
    this.id = null;
    this.css = null;
    this.dictcode = null;
    this.name = null;
    this.style = null;
    this.title = null;
    this.type = null;
    this.onclick = null;
    this.initoption = null;
    this.readonly = null;
    this.onchange = null;
    this.disabled = null;
  }
  
  public int doEndTag()
    throws JspException
  {
    clear();
    return 6;
  }
  
  public int doStartTag()
    throws JspException
  {
    String htmlText = null;
    if ("checkbox".equals(getType())) {
      htmlText = getCheckboxHtml();
    } else if ("radio".equals(getType())) {
      htmlText = getRadioHtml();
    } else if ("select".equals(getType())) {
      htmlText = getSelectHtml();
    }
    try
    {
      this.pageContext.getOut().write(htmlText);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new JspException("write   error!");
    }
    return 0;
  }
  
  public String getRadioHtml()
  {
    StringBuffer html = new StringBuffer();
    
    List<SysDictDetail> sysDictDetails = getListDictDetail();
    if ((sysDictDetails != null) && (!sysDictDetails.isEmpty())) {
      for (SysDictDetail sysDictDetail : sysDictDetails) {
        html.append(String.format(inputRadioF, new Object[] { getId() + "_" + sysDictDetail.getCode(), getName(), sysDictDetail.getCode(), getCss(), getTitle(), getStyle(), "" + getOnclick() + getOnchange() + getReadonly() + getDisabled(), getId() + "_" + sysDictDetail.getCode(), sysDictDetail.getValue() }));
      }
    }
    return html.toString();
  }
  
  public String getCheckboxHtml()
  {
    StringBuffer html = new StringBuffer();
    
    String[] vals = getValue().split(",");
    
    List<SysDictDetail> sysDictDetails = getListDictDetail();
    if ((sysDictDetails != null) && (!sysDictDetails.isEmpty())) {
      for (SysDictDetail sysDictDetail : sysDictDetails) {
        html.append(String.format(inputCheckboxF, new Object[] { getId() + "_" + sysDictDetail.getCode(), getName(), sysDictDetail.getCode(), getCss(), getTitle(), getStyle(), getCheckboxChecked(vals, sysDictDetail.getCode()) + getOnclick() + getOnchange() + getReadonly() + getDisabled(), getId() + "_" + sysDictDetail.getCode(), sysDictDetail.getValue() }));
      }
    }
    return html.toString();
  }
  
  public String getSelectHtml()
  {
    StringBuffer html = new StringBuffer();
    
    html.append(String.format(inputSelectF, new Object[] { getId(), getName(), getCss(), getTitle(), getStyle(), getOnclick() + getOnchange() + getReadonly() + getDisabled() }));
    







    List<SysDictDetail> sysDictDetails = getListDictDetail();
    if ((sysDictDetails != null) && (!sysDictDetails.isEmpty())) {
      for (SysDictDetail sysDictDetail : sysDictDetails) {
        html.append(String.format(selectOptionF, new Object[] { sysDictDetail.getCode(), sysDictDetail.getValue(), getValue().equals(sysDictDetail.getCode()) ? "selected=\"selected\"" : "", sysDictDetail.getValue() }));
      }
    }
    return html.toString() + "</select>";
  }
  
  private String getCheckboxChecked(String[] vals, String value)
  {
    if ((vals != null) && (vals.length > 0)) {
      for (String string : vals) {
        if (string.equals(value)) {
          return " checked=\"checked\" ";
        }
      }
    }
    return "";
  }
  
  private List<SysDictDetail> getListDictDetail()
  {
    List<SysDictDetail> list = new ArrayList();
    

    addOptionsToList(list);
    if ((getDictcode() != null) && (!getDictcode().equals(""))) {}
    return list;
  }
  
  private void addOptionsToList(List<SysDictDetail> list)
  {
    if ((getInitoption() instanceof String))
    {
      String temp = (String)this.initoption;
      String[] atemp = temp.split(":");
      if (atemp != null) {
        for (String astr : atemp)
        {
          String[] items = astr.split(",");
          if (items.length == 2)
          {
            SysDictDetail sysDictDetail = new SysDictDetail();
            sysDictDetail.setCode(items[0]);
            sysDictDetail.setValue(items[1]);
            list.add(sysDictDetail);
          }
        }
      }
    }
    else if ((getInitoption() instanceof ArrayList))
    {
      List<Map<String, Object>> initList = (ArrayList)this.initoption;
      for (Map<String, Object> obj : initList)
      {
        SysDictDetail sysDictDetail = new SysDictDetail();
        sysDictDetail.setCode(obj.get("value").toString());
        sysDictDetail.setValue(obj.get("text").toString());
        list.add(sysDictDetail);
      }
    }
  }
  
  public String processOnClick()
  {
    return " onclick=\"" + this.onclick + "\" ";
  }
  
  public String processOnChange()
  {
    return this.css;
  }
  
  public String processReadonly()
  {
    return this.css;
  }
  
  public String processDisabled()
  {
    return this.css;
  }
  
  public String getId()
  {
    if (this.id == null) {
      return "";
    }
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getName()
  {
    if (this.name == null) {
      return "";
    }
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getType()
  {
    if (this.type == null) {
      return "";
    }
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getDictcode()
  {
    if (this.dictcode == null) {
      return "";
    }
    return this.dictcode;
  }
  
  public void setDictcode(String dictcode)
  {
    this.dictcode = dictcode;
  }
  
  public String getTitle()
  {
    if (this.title == null) {
      return "";
    }
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getValue()
  {
    if (this.value == null) {
      return "";
    }
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  public String getCss()
  {
    if (this.css == null) {
      return "";
    }
    return this.css;
  }
  
  public void setCss(String css)
  {
    this.css = css;
  }
  
  public String getStyle()
  {
    if (this.style == null) {
      return "";
    }
    return this.style;
  }
  
  public void setStyle(String style)
  {
    this.style = style;
  }
  
  public String getOnclick()
  {
    if (StringUtils.isNotBlank(this.onclick)) {
      return " onclick=\"" + this.onclick + "\" ";
    }
    return "";
  }
  
  public void setOnclick(String onclick)
  {
    this.onclick = onclick;
  }
  
  public Object getInitoption()
  {
    return this.initoption;
  }
  
  public void setInitoption(Object initoption)
  {
    this.initoption = initoption;
  }
  
  public String getReadonly()
  {
    if ((StringUtils.isNotBlank(this.readonly)) && (this.readonly.equals("readonly"))) {
      return " readonly=\"readonly\" ";
    }
    return "";
  }
  
  public void setReadonly(String readonly)
  {
    this.readonly = readonly;
  }
  
  public String getOnchange()
  {
    if (StringUtils.isNotBlank(this.onchange)) {
      return " onchange=\"" + this.onchange + "\" ";
    }
    return "";
  }
  
  public void setOnchange(String onchange)
  {
    this.onchange = onchange;
  }
  
  public String getDisabled()
  {
    if ((StringUtils.isNotBlank(this.disabled)) && (this.disabled.equals("disabled"))) {
      return " disabled=\"disabled\" ";
    }
    return "";
  }
  
  public void setDisabled(String disabled)
  {
    this.disabled = disabled;
  }
}
