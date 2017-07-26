package com.xgh.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * FreeMarker调用工具
 *
 * @author：段晓刚
 * @update：2015年11月1日 下午6:46:08
 * @Email：
 */
public class FreeMarkerUtil {

    private static Logger logger = Logger.getLogger(FreeMarkerUtil.class);

    /**
     * @param dataMap        数据Map
     * @param path           模板文件路径
     * @param sourceDocument freemarker模板文件名称
     * @return 经freemarker解析后的内容
     */
    public static String DocumentHandler(Map<String, Object> dataMap, String path, String sourceDocument) {
        Configuration configuration = null;
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(FreeMarkerUtil.class, path);
        Template t = null;
        try {
            t = configuration.getTemplate(sourceDocument);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringWriter out = null;
        try {
            out = new StringWriter();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            t.process(dataMap, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return out.getBuffer().toString();
    }


    /**
     * @param dataMap        数据Map
     * @param path           模板文件加载目录
     * @param sourceDocument freemarker模板文件名称
     * @return 经freemarker解析后的内容
     */
    public static String getContentFromFileTemplate(Map<String, Object> dataMap, String path, String sourceDocument) {
        Configuration configuration = null;
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        StringWriter out = null;
        try {

            configuration.setDirectoryForTemplateLoading(new File(path));
            Template t = configuration.getTemplate(sourceDocument);
            out = new StringWriter();
            t.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out.getBuffer().toString();
    }


    /**
     * @param dataMap        数据Map
     * @param stringTemplate freemarker模板文件名称
     * @return 经freemarker解析后的内容
     */
    public static String getContentFromStringTemplate(Map<String, Object> dataMap, String stringTemplate) {

        Configuration configuration = null;
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        StringWriter out = null;
        try {

            configuration.setTemplateLoader(new StringTemplateLoader(stringTemplate));
            Template template = configuration.getTemplate("");
            out = new StringWriter();
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out.getBuffer().toString();
    }
}
