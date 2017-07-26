package com.xgh.paotui.dao;
import com.xgh.paotui.dao.read.IDeliverManMarketDaoR;
import com.xgh.paotui.dao.write.IDeliverManMarketDaoW;
import com.xgh.paotui.entity.DeliverManMarket;

import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;


/**
 * 
 * 跑客推广表
 * 
 **/
@Service("deliverManMarketDao")
public class DeliverManMarketDaoImpl implements IDeliverManMarketDao {


@Autowired
private IDeliverManMarketDaoR deliverManMarketDaoR;

@Autowired
private IDeliverManMarketDaoW deliverManMarketDaoW;


	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  DeliverManMarket get(Long id){
		return  deliverManMarketDaoR.get(id);
	}



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<DeliverManMarket> getList(Map<String, Object> map){
		return  deliverManMarketDaoR.getList(map);
	}


	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<DeliverManMarket> getListPage(Map<String, Object> map){
		return  deliverManMarketDaoR.getListPage(map);
	}


	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  deliverManMarketDaoR.getListMapPage(map);
	}


	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map){
		return  deliverManMarketDaoR.getRows(map);
	}


	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(DeliverManMarket  deliverManMarket){
		return  deliverManMarketDaoW.add(deliverManMarket);
	}


	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(DeliverManMarket  deliverManMarket){
		return  deliverManMarketDaoW.update(deliverManMarket);
	}


	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id){
		return  deliverManMarketDaoW.deleteById(id);
	}

}
