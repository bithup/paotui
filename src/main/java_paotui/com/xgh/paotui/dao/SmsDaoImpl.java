package com.xgh.paotui.dao;

import com.xgh.paotui.dao.read.ISmsDaoR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service("smsDao")
public class SmsDaoImpl implements ISmsDao {

    @Autowired
    protected ISmsDaoR smsDaoR;

    public Map<String, Object> smsDock(Map<String, Object> map) {
        return smsDaoR.smsDock(map);
    }

}