package com.xgh.paotui.services;

import com.xgh.paotui.dao.ISmsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service("smsService")
public class SmsServiceImpl implements ISmsService{
	@Autowired
	protected ISmsDao smsDao;

	public Map<String, Object> smsDock(Map<String, Object> map) {
		return smsDao.smsDock(map);
	}
}