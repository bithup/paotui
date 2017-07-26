package com.xgh.paotui.dao;
import com.xgh.paotui.dao.read.IOrderGoodsDaoR;
import com.xgh.paotui.dao.write.IOrderGoodsDaoW;
import com.xgh.paotui.entity.OrderGoods;

import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;


/**
 * 
 * 订单-帮送、帮取、帮买表
 * 
 **/
@Service("orderGoodsDao")
public class OrderGoodsDaoImpl implements IOrderGoodsDao {


@Autowired
private IOrderGoodsDaoR orderGoodsDaoR;

@Autowired
private IOrderGoodsDaoW orderGoodsDaoW;

	public  OrderGoods get(Long id){
		return  orderGoodsDaoR.get(id);
	}

	public  OrderGoods getOrderGoods(Long orderId){
		return  orderGoodsDaoR.getOrderGoods(orderId);
	}

	public List<Map<String, Object>> getList(Map<String, Object> map){
		return  orderGoodsDaoR.getList(map);
	}

	public List<OrderGoods> getListPage(Map<String, Object> map){
		return  orderGoodsDaoR.getListPage(map);
	}

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  orderGoodsDaoR.getListMapPage(map);
	}

	public long getRows(Map<String, Object> map){
		return  orderGoodsDaoR.getRows(map);
	}

	public  int add(OrderGoods  orderGoods){
		return  orderGoodsDaoW.add(orderGoods);
	}

	public  int update(OrderGoods  orderGoods){
		return  orderGoodsDaoW.update(orderGoods);
	}

	public  int deleteById(long id){
		return  orderGoodsDaoW.deleteById(id);
	}

}
