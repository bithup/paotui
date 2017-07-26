package com.xgh.paotui.dao;

import com.xgh.paotui.entity.SmsLog;
import java.util.Map;

public interface ISmsLogDao {

    public int add(SmsLog smsLog);

    public Map<String,Object> getCount(Map<String, Object> map);
}
