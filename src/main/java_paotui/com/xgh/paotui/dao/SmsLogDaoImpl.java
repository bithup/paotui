package com.xgh.paotui.dao;

import com.xgh.paotui.dao.read.ISmsLogDaoR;
import com.xgh.paotui.dao.write.ISmsLogDaoW;
import com.xgh.paotui.entity.SmsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service("smsRecordDao")
public class SmsLogDaoImpl implements ISmsLogDao{

    @Autowired
    protected ISmsLogDaoW smsLogDaoW;

    @Autowired
    protected ISmsLogDaoR smsLogDaoR;

    public int add(SmsLog smsLog) {
        return smsLogDaoW.add(smsLog);
    }

    public Map<String, Object> getCount(Map<String, Object> map) {
        return smsLogDaoR.getCount(map);
    }
}
