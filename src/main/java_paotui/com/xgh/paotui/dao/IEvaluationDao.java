package com.xgh.paotui.dao;

import com.xgh.paotui.entity.Evaluation;
import java.util.List;
import java.util.Map;

public interface IEvaluationDao  {

	public  Evaluation get(Long id);

	public List<Map<String, Object>> getList(Map<String, Object> map);

	public List<Evaluation> getListPage(Map<String, Object> map);

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);

	public long getRows(Map<String, Object> map);

	public  int add(Evaluation evaluation);

	public  int update(Evaluation evaluation);

	public  int deleteById(long id);


}
