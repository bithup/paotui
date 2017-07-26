package com.xgh.paotui.services;

import com.xgh.paotui.entity.SmsLog;
import java.util.Map;

public interface ISmsLogService {

    public int add(SmsLog smsLog);

    public Map<String,Object> getCount(Map<String, Object> map);
}
