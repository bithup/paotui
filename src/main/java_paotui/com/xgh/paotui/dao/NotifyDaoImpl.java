package com.xgh.paotui.dao;

import com.xgh.paotui.dao.read.INotifyDaoR;
import com.xgh.paotui.dao.write.INotifyDaoW;
import com.xgh.paotui.entity.Notify;
import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

@Service("notifyDao")
public class NotifyDaoImpl implements INotifyDao {

@Autowired
private INotifyDaoR notifyDaoR;

@Autowired
private INotifyDaoW notifyDaoW;

	public  Notify get(Long id){
		return  notifyDaoR.get(id);
	}

	public List<Map<String, Object>> getList(Map<String, Object> map){
		return  notifyDaoR.getList(map);
	}

	public List<Notify> getListPage(Map<String, Object> map){
		return  notifyDaoR.getListPage(map);
	}

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  notifyDaoR.getListMapPage(map);
	}

	public long getRows(Map<String, Object> map){
		return  notifyDaoR.getRows(map);
	}

	public  int add(Notify  notify){
		return  notifyDaoW.add(notify);
	}

	public  int update(Notify  notify){
		return  notifyDaoW.update(notify);
	}

	public  int deleteById(long id){
		return  notifyDaoW.deleteById(id);
	}


	public List<Map<String, Object>> getNotify(Map<String, Object> map){
		return  notifyDaoR.getNotify(map);
	}

}
