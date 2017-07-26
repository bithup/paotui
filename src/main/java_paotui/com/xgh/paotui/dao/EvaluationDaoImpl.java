package com.xgh.paotui.dao;
import com.xgh.paotui.dao.read.IEvaluationDaoR;
import com.xgh.paotui.dao.write.IEvaluationDaoW;
import com.xgh.paotui.entity.Evaluation;

import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;


/**
 * 
 * 订单评价表
 * 
 **/
@Service("evaluationDao")
public class EvaluationDaoImpl implements IEvaluationDao {


@Autowired
private IEvaluationDaoR evaluationDaoR;

@Autowired
private IEvaluationDaoW evaluationDaoW;


	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  Evaluation get(Long id){
		return  evaluationDaoR.get(id);
	}



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<Map<String, Object>> getList(Map<String, Object> map){
		return  evaluationDaoR.getList(map);
	}


	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<Evaluation> getListPage(Map<String, Object> map){
		return  evaluationDaoR.getListPage(map);
	}


	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  evaluationDaoR.getListMapPage(map);
	}


	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map){
		return  evaluationDaoR.getRows(map);
	}


	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(Evaluation  evaluation){
		return  evaluationDaoW.add(evaluation);
	}


	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(Evaluation  evaluation){
		return  evaluationDaoW.update(evaluation);
	}


	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id){
		return  evaluationDaoW.deleteById(id);
	}

}
