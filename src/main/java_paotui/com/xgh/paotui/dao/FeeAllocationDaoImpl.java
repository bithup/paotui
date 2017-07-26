package com.xgh.paotui.dao;
import com.xgh.paotui.dao.read.IFeeAllocationDaoR;
import com.xgh.paotui.dao.write.IFeeAllocationDaoW;
import com.xgh.paotui.entity.FeeAllocation;

import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;


/**
 * 
 * 平台佣金分配表
 * 
 **/
@Service("feeAllocationDao")
public class FeeAllocationDaoImpl implements IFeeAllocationDao {


@Autowired
private IFeeAllocationDaoR feeAllocationDaoR;

@Autowired
private IFeeAllocationDaoW feeAllocationDaoW;


	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  FeeAllocation get(Long id){
		return  feeAllocationDaoR.get(id);
	}



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<Map<String, Object>> getList(Map<String, Object> map){
		return  feeAllocationDaoR.getList(map);
	}


	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<FeeAllocation> getListPage(Map<String, Object> map){
		return  feeAllocationDaoR.getListPage(map);
	}


	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  feeAllocationDaoR.getListMapPage(map);
	}


	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map){
		return  feeAllocationDaoR.getRows(map);
	}


	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(FeeAllocation  feeAllocation){
		return  feeAllocationDaoW.add(feeAllocation);
	}


	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(FeeAllocation  feeAllocation){
		return  feeAllocationDaoW.update(feeAllocation);
	}


	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id){
		return  feeAllocationDaoW.deleteById(id);
	}

}
