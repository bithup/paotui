package com.xgh.paotui.dao;
import com.xgh.paotui.dao.read.IErrandsFeeDaoR;
import com.xgh.paotui.dao.write.IErrandsFeeDaoW;
import com.xgh.paotui.entity.ErrandsFee;

import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;


/**
 * 
 * 跑腿费表
 * 
 **/
@Service("errandsFeeDao")
public class ErrandsFeeDaoImpl implements IErrandsFeeDao {


@Autowired
private IErrandsFeeDaoR errandsFeeDaoR;

@Autowired
private IErrandsFeeDaoW errandsFeeDaoW;


	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  ErrandsFee get(Long id){
		return  errandsFeeDaoR.get(id);
	}



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<ErrandsFee> getList(Map<String, Object> map){
		return  errandsFeeDaoR.getList(map);
	}


	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<ErrandsFee> getListPage(Map<String, Object> map){
		return  errandsFeeDaoR.getListPage(map);
	}


	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  errandsFeeDaoR.getListMapPage(map);
	}


	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map){
		return  errandsFeeDaoR.getRows(map);
	}


	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(ErrandsFee  errandsFee){
		return  errandsFeeDaoW.add(errandsFee);
	}


	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(ErrandsFee  errandsFee){
		return  errandsFeeDaoW.update(errandsFee);
	}


	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id){
		return  errandsFeeDaoW.deleteById(id);
	}

}
