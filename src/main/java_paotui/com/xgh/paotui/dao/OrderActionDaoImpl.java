package com.xgh.paotui.dao;

import com.xgh.paotui.dao.read.IOrderActionDaoR;
import com.xgh.paotui.dao.write.IOrderActionDaoW;
import com.xgh.paotui.entity.OrderAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("orderActionDao")
public class OrderActionDaoImpl implements IOrderActionDao {

	@Autowired
	private IOrderActionDaoR orderActionDaoR;

	@Autowired
	private IOrderActionDaoW orderActionDaoW;

	public  OrderAction get(Long id){
		return  orderActionDaoR.get(id);
	}

	public List<OrderAction> getList(Map<String, Object> map){
		return  orderActionDaoR.getList(map);
	}

	public List<OrderAction> getListPage(Map<String, Object> map){
		return  orderActionDaoR.getListPage(map);
	}

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  orderActionDaoR.getListMapPage(map);
	}

	public long getRows(Map<String, Object> map){
		return  orderActionDaoR.getRows(map);
	}

	public  int add(OrderAction  orderAction){
		return  orderActionDaoW.add(orderAction);
	}

	public  int update(OrderAction  orderAction){
		return  orderActionDaoW.update(orderAction);
	}

	public  int deleteById(long id){
		return  orderActionDaoW.deleteById(id);
	}

}
