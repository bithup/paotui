package com.xgh.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

/**
 * 解析properties配置文件
 *
 * @author：段晓刚
 * @update：2014年7月31日 下午12:18:43
 * @Email：
 */
public class PropertiesUtil {

    //默认的配置文件
    private String path = "/config.properties";

    private static Properties mProperties = new Properties();

    private static Map<String, String> mValueMap = new HashMap<String, String>();

    private static Set<Entry<Object, Object>> mValueSet = null;

    private static PropertiesUtil mPropertiesUtil = null;

    private PropertiesUtil(String path) throws IOException {
        if (path == null || "null".equals(path) || "".equals(path))
            path = this.path;
        this.load(path);
    }

    /**
     * 实现静态获取
     *
     * @return
     * @throws IOException
     */
    public static PropertiesUtil getInstance(String path) {
        try {
            if (mPropertiesUtil == null)
                mPropertiesUtil = new PropertiesUtil(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return mPropertiesUtil;
    }

    /**
     * 加载配置文件
     *
     * @return
     * @throws IOException
     */
    private void load(String path) throws IOException {

        InputStream is = this.getClass().getResourceAsStream(path);

        mProperties.load(is);
        mValueSet = mProperties.entrySet();
        is.close();

        Iterator<Entry<Object, Object>> it = mValueSet.iterator();

        while (it.hasNext()) {
            Entry<Object, Object> entry = it.next();
            String key = entry.getKey() + "";
            String value = entry.getValue() + "";
            // System.out.println(key+"\t"+value);
            mValueMap.put(key, value);
        }
    }

    public String getValueByKey(String key) {

        return mValueMap.get(key);
    }

    public Map<String, String> getMap() {

        return mValueMap;
    }

//	public static void main(String args[]){
//		System.out.println(getInstance(null).getMap());
//	}
}