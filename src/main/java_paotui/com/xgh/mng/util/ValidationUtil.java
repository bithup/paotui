package com.xgh.mng.util;

import com.xgh.mng.entity.SysValidation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ValidationUtil
{
  public static void addValidationDataToBuffer(StringBuffer dataBuffer, List<SysValidation> validations)
  {
    Map<String, String> msgInfo;
    if (validations != null)
    {
      msgInfo = new HashMap();
      
      Map<String, String> ruleInfo = new HashMap();
      for (SysValidation sysValidation : validations)
      {
        String field = sysValidation.getField();
        String msg = sysValidation.getMsg();
        String ruleType = sysValidation.getRuleType();
        String ruleTypeValue = sysValidation.getRuleTypeValue();
        if (field != null)
        {
          if ((msg != null) && (!msg.equals("")))
          {
            String oldMsg = (String)msgInfo.get(field);
            if (oldMsg != null) {
              oldMsg = oldMsg + ",'" + ruleType + "':'" + msg + "'";
            } else {
              oldMsg = "'" + ruleType + "':'" + msg + "'";
            }
            msgInfo.put(field, oldMsg);
          }
          if ((ruleTypeValue != null) && (!ruleTypeValue.equals("")))
          {
            String oldRule = (String)ruleInfo.get(field);
            if (oldRule != null) {
              oldRule = oldRule + ",'" + ruleType + "':" + ruleTypeValue + "";
            } else {
              oldRule = "'" + ruleType + "':" + ruleTypeValue + "";
            }
            ruleInfo.put(field, oldRule);
          }
        }
      }
      for (Map.Entry<String, String> entry : ruleInfo.entrySet())
      {
        String ruleMsg = (String)msgInfo.get(entry.getKey());
        String info = "";
        if (ruleMsg != null) {
          info = String.format("$(\"#%s\").attr(\"validate\",\"{%s,messages:{%s}}\");\n", new Object[] { entry.getKey(), entry.getValue(), ruleMsg });
        } else {
          info = String.format("$(\"#%s\").attr(\"validate\",\"{%s}\");\n", new Object[] { entry.getKey(), entry.getValue() });
        }
        dataBuffer.append(info);
      }
    }
  }
}
