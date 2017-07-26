package com.xgh.paotui.dao;

import com.xgh.paotui.dao.read.IOpenCityDaoR;
import com.xgh.paotui.dao.write.IOpenCityDaoW;
import com.xgh.paotui.entity.OpenCity;
import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

@Service("openCityDao")
public class OpenCityDaoImpl implements IOpenCityDao {

@Autowired
private IOpenCityDaoR openCityDaoR;

@Autowired
private IOpenCityDaoW openCityDaoW;

	public  OpenCity get(Long id){
		return  openCityDaoR.get(id);
	}

	public List<OpenCity> getList(Map<String, Object> map){
		return  openCityDaoR.getList(map);
	}

	public List<OpenCity> getListPage(Map<String, Object> map){
		return  openCityDaoR.getListPage(map);
	}

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  openCityDaoR.getListMapPage(map);
	}

	public long getRows(Map<String, Object> map){
		return  openCityDaoR.getRows(map);
	}

	public  int add(OpenCity  openCity){
		return  openCityDaoW.add(openCity);
	}

	public  int update(OpenCity  openCity){
		return  openCityDaoW.update(openCity);
	}

	public  int deleteById(long id){
		return  openCityDaoW.deleteById(id);
	}



	public List<Map<String, Object>> getOpenCity(Map<String, Object> map){
		return  openCityDaoR.getOpenCity(map);
	}

}
