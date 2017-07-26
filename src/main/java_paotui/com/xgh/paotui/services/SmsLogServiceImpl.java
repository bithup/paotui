package com.xgh.paotui.services;

import com.xgh.paotui.dao.ISmsLogDao;
import com.xgh.paotui.entity.SmsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service("smsRecordService")
public class SmsLogServiceImpl implements ISmsLogService {

    @Autowired
    protected ISmsLogDao smsLogDao;

    public int add(SmsLog smsLog) {
        return smsLogDao.add(smsLog);
    }

    public Map<String, Object> getCount(Map<String, Object> map) {
        return smsLogDao.getCount(map);
    }
}


