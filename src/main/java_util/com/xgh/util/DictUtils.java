/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.xgh.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.xgh.paotui.dao.DictDaoImpl;
import com.xgh.paotui.dao.IDictDao;
import com.xgh.paotui.entity.Dict;
import org.apache.commons.lang.StringUtils;
import  com.xgh.spring.SpringContextHolder;

/**
 * 字典工具类
 */
public class DictUtils {
	
	private static IDictDao dictDao = SpringContextHolder.getBean(DictDaoImpl.class);

	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}
	
	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList =new ArrayList<String>();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	
	public static List<Dict> getDictList(String type){

		Map<String, Object> map	=new HashMap<String, Object>();
		map.put("type",type);
		List<Dict> dictList =dictDao.getList(map);
		return dictList;
	}


	public static String getDictListForRadio(String type){
		String ret="";
		Map<String, Object> map	=new HashMap<String, Object>();
		map.put("type",type);

		List<Dict> dictList =dictDao.getList(map);
		for(Dict data:dictList){
			ret+=data.getValue();
			ret+=",";
			ret+=data.getLabel();
			ret+=":";
		}
		ret=ret.substring(0,ret.length()-1);
		return ret;
	}
	
	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
//	public static String getDictListJson(String type){
//
//		return JsonMapper.toJsonString(getDictList(type));
//	}
	
}
