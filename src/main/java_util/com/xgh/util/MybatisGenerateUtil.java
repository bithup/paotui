package com.xgh.util;



import java.io.BufferedWriter;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.OutputStreamWriter;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

/**
 *  自动生成MyBatis的实体类、实体映射XML文件、Mapper
 *
 * @author   WYS
 * @date     2014-11-8
 * @version  v1.0
 */
@SuppressWarnings("hiding")
public class MybatisGenerateUtil {

    /**
     **********************************使用前必读*******************
     **
     ** 使用前请将moduleName更改为自己模块的名称即可（一般情况下与数据库名一致），其他无须改动。
     **
     ***********************************************************
     */

    private final String type_char = "char";
    private final String type_varchar = "varchar";

    private final String type_double = "double";

    private final String type_date = "date";

    private final String type_timestamp = "timestamp";

    private final String type_int = "int";

    private final String type_bigint = "bigint";

    private final String type_text = "text";

    private final String type_bit = "bit";

    private final String type_decimal = "decimal";

    private final String type_blob = "blob";


    private final String moduleName = "paotui"; // 对应模块名称（根据自己模块做相应调整!!!务必修改^_^）

    private final String bean_path = "d:/entity_bean";

    private final String dao_path = "d:/entity_dao";

    private final String mapper_path = "d:/entity_mapper";

    private final String xml_path = "d:/entity_mapper/xml";

    private final String bean_package = "com.xgh." + moduleName + ".entity";

    private final String mapper_package = "com.xgh." + moduleName + ".mapper";

    private final String dao_package = "com.xgh." + moduleName + ".dao";


    private final String driverName = "com.mysql.jdbc.Driver";

    private final String user = "root";

    private final String password = "root";

    private final String url = "jdbc:mysql://192.168.3.86:3306/" + moduleName + "?characterEncoding=utf8";

    private String tableName = null;

    private String beanName = null;


    private final String beanFullName = "com.xgh.paotui.entity.";

    private String mapperName = null;

    private Connection conn = null;


    private void init() throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, user, password);
    }


    /**
     *  获取所有的表
     *
     * @return
     * @throws SQLException
     */
    private List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<String>();
        PreparedStatement pstate = conn.prepareStatement("show tables");
        ResultSet results = pstate.executeQuery();
        while ( results.next() ) {
            String tableName = results.getString(1);
            //          if ( tableName.toLowerCase().startsWith("yy_") ) {
            tables.add(tableName);
            //          }
        }
        return tables;
    }


    private void processTable( String table ) {
        StringBuffer sb = new StringBuffer(table.length());
        String tableNew = table.toLowerCase();
        String[] tables = tableNew.split("_");
        String temp = null;
        for ( int i = 1 ; i < tables.length ; i++ ) {
            temp = tables[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        beanName = sb.toString();
        mapperName = beanName + "Dao";
    }


    private String processType( String type ) {
        if ( type.indexOf(type_char) > -1 ) {
            return "String";
        } else if ( type.indexOf(type_bigint) > -1 ) {
            return "Long";
        } else if ( type.indexOf(type_int) > -1 ) {
            return "Integer";
        } else if ( type.indexOf(type_date) > -1 ) {
            return "java.util.Date";
        } else if ( type.indexOf(type_text) > -1 ) {
            return "String";
        } else if ( type.indexOf(type_timestamp) > -1 ) {
            return "java.util.Date";
        } else if ( type.indexOf(type_bit) > -1 ) {
            return "Boolean";
        } else if ( type.indexOf(type_decimal) > -1 ) {
            return "java.math.BigDecimal";
        } else if ( type.indexOf(type_blob) > -1 ) {
            return "byte[]";
        } else if ( type.indexOf(type_double) > -1 ) {
            return "Double";
        }
        return null;
    }


    private String processField( String field ) {
        StringBuffer sb = new StringBuffer(field.length());
        //field = field.toLowerCase();
        String[] fields = field.split("_");
        String temp = null;
        sb.append(fields[0]);
        for ( int i = 1 ; i < fields.length ; i++ ) {
            temp = fields[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        return sb.toString();
    }


    /**
     *  将实体类名首字母改为小写
     *
     * @param beanName
     * @return
     */
    private String processResultMapId( String beanName ) {
        return beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
    }


    /**
     *  构建类上面的注释
     *
     * @param bw
     * @param text
     * @return
     * @throws IOException
     */
    private BufferedWriter buildClassComment( BufferedWriter bw, String text ) throws IOException {
        bw.newLine();
        bw.newLine();
        bw.write("/**");
        bw.newLine();
        bw.write(" * ");
        bw.newLine();
        bw.write(" * " + text);
        bw.newLine();
        bw.write(" * ");
        bw.newLine();
        bw.write(" **/");
        return bw;
    }


    /**
     *  构建方法上面的注释
     *
     * @param bw
     * @param text
     * @return
     * @throws IOException
     */
    private BufferedWriter buildMethodComment( BufferedWriter bw, String text ) throws IOException {
        bw.newLine();
        bw.write("\t/**");
        bw.newLine();
        bw.write("\t * ");
        bw.newLine();
        bw.write("\t * " + text);
        bw.newLine();
        bw.write("\t * ");
        bw.newLine();
        bw.write("\t **/");
        return bw;
    }


    /**
     *  生成实体类
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException
     */
    private void buildEntityBean( List<String> columns, List<String> types, List<String> comments, String tableComment )
            throws IOException {
        File folder = new File(bean_path);
        if ( !folder.exists() ) {
            folder.mkdir();
        }

        File beanFile = new File(bean_path, beanName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        bw.write("package " + bean_package + ";");
        bw.newLine();
        bw.write("import java.io.Serializable;");
        bw.newLine();
        //bw.write("import lombok.Data;");
        //      bw.write("import javax.persistence.Entity;");
        bw = buildClassComment(bw, tableComment);
        bw.newLine();
        bw.write("@SuppressWarnings(\"serial\")");
        bw.newLine();
        //      bw.write("@Entity");
        //bw.write("@Data");
        //bw.newLine();
        bw.write("public class " + beanName + " implements Serializable {");
        bw.newLine();
        bw.newLine();
        int size = columns.size();
        for ( int i = 0 ; i < size ; i++ ) {
            bw.write("\t/**" + comments.get(i) + "**/");
            bw.newLine();
            bw.write("\tprivate " + processEntityType(types.get(i)) + " " + processField(columns.get(i)) + ";");
            bw.newLine();
            bw.newLine();
        }
        bw.newLine();


        //
        bw.write("\tpublic "  + beanName+ "() {");
        bw.write(" super();");
        bw.write(" }");
        bw.newLine();
        bw.newLine();

        //
        bw.write("\tpublic "  + beanName+ "(long id) {");
        bw.newLine();
        bw.write("\t super();");
        bw.newLine();
        bw.write("\t this.id=id;");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();
        bw.newLine();


        String stringBuffer="";
        StringBuffer stringBuffer2=new StringBuffer();
        for ( int i = 0 ; i < size ; i++ ) {
            stringBuffer= processEntityType(types.get(i)) +" "+ processField(columns.get(i))+",";
            if(i==size-1){
                stringBuffer= processEntityType(types.get(i)) +" "+ processField(columns.get(i))+"";
            }
            stringBuffer2.append(stringBuffer);
        }
        bw.write("\tpublic "+beanName+"("+stringBuffer2+"){");
        bw.newLine();
        bw.write("\t\tsuper();");
        bw.newLine();
        String tempField = null;
        String _tempField = null;
        String tempType = null;
        for ( int i = 0 ; i < size ; i++ ) {
            tempType = processType(types.get(i));
            _tempField = processField(columns.get(i));
            tempField = _tempField.substring(0, 1).toUpperCase() + _tempField.substring(1);
            bw.write("\t\tthis." + _tempField + " = " + _tempField + ";");
            bw.newLine();
        }
        bw.newLine();
        bw.write("\t}");
        // 生成get 和 set方法
        //String tempField = null;
        //String _tempField = null;
        //String tempType = null;
        for ( int i = 0 ; i < size ; i++ ) {
            tempType = processType(types.get(i));
            _tempField = processField(columns.get(i));
            tempField = _tempField.substring(0, 1).toUpperCase() + _tempField.substring(1);
            bw.newLine();
            //          bw.write("\tpublic void set" + tempField + "(" + tempType + " _" + _tempField + "){");
            bw.write("\tpublic void set" + tempField + "(" + tempType + " " + _tempField + "){");
            bw.newLine();
            //          bw.write("\t\tthis." + _tempField + "=_" + _tempField + ";");
            bw.write("\t\tthis." + _tempField + " = " + _tempField + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();
            bw.write("\tpublic " + tempType + " get" + tempField + "(){");
            bw.newLine();
            bw.write("\t\treturn this." + _tempField + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
        }
        bw.newLine();
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();
    }


    /**
     *  构建Mapper文件
     *
     * @throws IOException
     */
    private void buildMapper() throws IOException {
        File folder = new File(mapper_path);
        if ( !folder.exists() ) {
            folder.mkdirs();
        }

        File mapperFile = new File(mapper_path, mapperName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
        bw.write("package " + mapper_package + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import " + bean_package + "." + beanName + ";");
        bw.newLine();
        bw.write("import org.apache.ibatis.annotations.Param;");
        bw = buildClassComment(bw, mapperName + "数据库操作接口类");
        bw.newLine();
        bw.newLine();
        //      bw.write("public interface " + mapperName + " extends " + mapper_extends + "<" + beanName + "> {");
        bw.write("public interface " + mapperName + "{");
        bw.newLine();
        bw.newLine();
        // ----------定义Mapper中的方法Begin----------
        bw = buildMethodComment(bw, "查询（根据主键ID查询）");
        bw.newLine();
        bw.write("\t" + beanName + "  selectByPrimaryKey ( @Param(\"id\") Long id );");
        bw.newLine();
        bw = buildMethodComment(bw, "删除（根据主键ID删除）");
        bw.newLine();
        bw.write("\t" + "int deleteByPrimaryKey ( @Param(\"id\") Long id );");
        bw.newLine();
        bw = buildMethodComment(bw, "添加");
        bw.newLine();
        bw.write("\t" + "int insert( " + beanName + " record );");
        bw.newLine();
        bw = buildMethodComment(bw, "添加 （匹配有值的字段）");
        bw.newLine();
        bw.write("\t" + "int insertSelective( " + beanName + " record );");
        bw.newLine();
        bw = buildMethodComment(bw, "修改 （匹配有值的字段）");
        bw.newLine();
        bw.write("\t" + "int updateByPrimaryKeySelective( " + beanName + " record );");
        bw.newLine();
        bw = buildMethodComment(bw, "修改（根据主键ID修改）");
        bw.newLine();
        bw.write("\t" + "int updateByPrimaryKey ( " + beanName + " record );");
        bw.newLine();

        // ----------定义Mapper中的方法End----------
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
    }


    /**
     *  构建实体类映射XML文件
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException
     */
    private void buildMapperXml( List<String> columns, List<String> types, List<String> comments ) throws IOException {
        File folder = new File(xml_path);
        if ( !folder.exists() ) {
            folder.mkdirs();
        }

        String fileNameR=xml_path+"/mapperR";
        File folderR = new File(fileNameR);
        if ( !folderR.exists() ) {
            folderR.mkdirs();
        }

        File mapperXmlFileR = new File(fileNameR, "I"+mapperName + "R.xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFileR)));
        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        bw.newLine();
        bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
        bw.newLine();
        bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        bw.newLine();
        bw.write("<mapper namespace=\"" + dao_package + ".read.I" + mapperName + "R\">");
        bw.newLine();
        bw.newLine();

        bw.write("\t<!--实体映射-->");
        bw.newLine();
        bw.write("\t<resultMap id=\"" + this.processResultMapId(beanName) + "ResultMap\" type=\"" +bean_package+"."+ beanName + "\">");
        bw.newLine();
        bw.write("\t\t<!--" + comments.get(0) + "-->");
        bw.newLine();
        bw.write("\t\t<id property=\"" + this.processField(columns.get(0)) + "\" column=\"" + columns.get(0) + "\" />");
        bw.newLine();
        int size = columns.size();
        for ( int i = 1 ; i < size ; i++ ) {
            bw.write("\t\t<!--" + comments.get(i) + "-->");
            bw.newLine();
            bw.write("\t\t<result property=\""
                    + this.processField(columns.get(i)) + "\" column=\"" + columns.get(i) + "\" javaType=\"" + processEntityType(types.get(i)) + "\" jdbcType=\"" + processJdbcType(types.get(i)) + "\" />");
            bw.newLine();
        }
        bw.write("\t</resultMap>");

        bw.newLine();
        bw.newLine();
        bw.newLine();

        // 下面开始写SqlMapper中的方法
        // this.outputSqlMapperMethod(bw, columns, types);
        buildSQLR(bw, columns, types);

        bw.write("</mapper>");
        bw.flush();
        bw.close();



        String fileNameW=xml_path+"/mapperW";
        File folderW = new File(fileNameW);
        if ( !folderW.exists() ) {
            folderW.mkdirs();
        }


        File mapperXmlFileW = new File(fileNameW, "I"+mapperName + "W.xml");
        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFileW)));
        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        bw.newLine();
        bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
        bw.newLine();
        bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        bw.newLine();
        bw.write("<mapper namespace=\"" + dao_package + ".write.I" + mapperName + "W\">");
        bw.newLine();
        bw.newLine();

        bw.write("\t<!--实体映射-->");
        bw.newLine();
        bw.write("\t<resultMap id=\"" + this.processResultMapId(beanName) + "ResultMap\" type=\"" +bean_package+"."+ beanName + "\">");
        bw.newLine();
        bw.write("\t\t<!--" + comments.get(0) + "-->");
        bw.newLine();
        bw.write("\t\t<id property=\"" + this.processField(columns.get(0)) + "\" column=\"" + columns.get(0) + "\" />");
        bw.newLine();
        size = columns.size();
        for ( int i = 1 ; i < size ; i++ ) {
            bw.write("\t\t<!--" + comments.get(i) + "-->");
            bw.newLine();
            bw.write("\t\t<result property=\""
                    + this.processField(columns.get(i)) + "\" column=\"" + columns.get(i) + "\" javaType=\"" + processEntityType(types.get(i)) + "\" jdbcType=\"" + processJdbcType(types.get(i)) + "\" />");
            bw.newLine();
        }
        bw.write("\t</resultMap>");

        bw.newLine();
        bw.newLine();
        bw.newLine();

        // 下面开始写SqlMapper中的方法
        // this.outputSqlMapperMethod(bw, columns, types);
        buildSQLW(bw, columns, types);

        bw.write("</mapper>");
        bw.flush();
        bw.close();
    }


    private void buildSQLR( BufferedWriter bw, List<String> columns, List<String> types ) throws IOException {
        int size = columns.size();
        // 通用结果列
        /**
         bw.write("\t<!-- 通用查询结果列-->");
         bw.newLine();
         bw.write("\t<sql id=\"Base_Column_List\">");
         bw.newLine();

         bw.write("\t\t id,");
         for ( int i = 1 ; i < size ; i++ ) {
         bw.write("\t" + columns.get(i));
         if ( i != size - 1 ) {
         bw.write(",");
         }
         }

         bw.newLine();
         bw.write("\t</sql>");
         bw.newLine();
         bw.newLine();
         **/

        // 通用结果列
        bw.write("\t<!-- 通用查询结果列-->");
        bw.newLine();
        bw.write("\t<sql id=\"Base_Column_List\">");
        bw.newLine();

        bw.write("\t\t id as id,");
        bw.newLine();
        for ( int i = 1 ; i < size ; i++ ) {
            bw.write("\t" + columns.get(i));

            bw.write("\t as " + this.processField(columns.get(i)));
            if ( i != size - 1 ) {
                bw.write(",");
                bw.newLine();
            }
        }

        bw.newLine();
        bw.write("\t</sql>");
        bw.newLine();
        bw.newLine();


        // 查询（根据主键ID查询）
        bw.write("\t<!-- 查询（根据主键ID查询） -->");
        bw.newLine();
        bw.write("\t<select id=\"get\" resultMap=\""
                + processResultMapId(beanName) + "ResultMap\" parameterType=\"java.lang." + processType(types.get(0)) + "\">");
        bw.newLine();
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM " + tableName);
        bw.newLine();
        bw.write("\t\t WHERE  status = 1  and " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();

        // 查询（根据查询条件查询）
        bw.write("\t<!-- 查询（根据查询条件查询） -->");
        bw.newLine();
        bw.write("\t<select id=\"getList\" resultMap=\"" + processResultMapId(beanName) + "ResultMap\" parameterType=\"hashmap\">");
        bw.newLine();
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM " + tableName);
        bw.newLine();
        bw.write("\t\t WHERE   status = 1 ");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();


        // 查询（根据查询条件分页查询）
        bw.write("\t<!-- 查询（根据查询条件分页查询） -->");
        bw.newLine();
        bw.write("\t<select id=\"getListPage\" resultMap=\""
                + processResultMapId(beanName) + "ResultMap" + "\" parameterType=\"hashmap\">");
        bw.newLine();
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM " + tableName);
        bw.newLine();
        bw.write("\t\t WHERE   status = 1 ");
        bw.newLine();
        bw.write("\t\t limit ${(page-1)*pagesize},${pagesize} ");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();


        // 查询（根据查询条件分页查询,结果map列表）
        bw.write("\t<!-- 查询（根据查询条件分页查询） -->");
        bw.newLine();
        bw.write("\t<select id=\"getListMapPage\" resultType=\"hashmap\"  parameterType=\"hashmap\">");
        bw.newLine();
        bw.write("\t\t SELECT");
        bw.newLine();
        bw.write("\t\t <include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\t FROM " + tableName);
        bw.newLine();
        bw.write("\t\t WHERE   status = 1 ");
        bw.newLine();
        bw.write("\t\t limit ${(page-1)*pagesize},${pagesize} ");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();


        // 查询记录总数量（根据查询条件查询记录总数量）
        bw.write("\t<!-- 查询记录总数量（根据查询条件查询记录总数量） -->");
        bw.newLine();
        bw.write("\t<select id=\"getRows\" resultType=\"long\" parameterType=\"hashmap\">");
        bw.newLine();
        bw.write("\t\t SELECT ");
        bw.newLine();
        bw.write("\t\t count(*)  ");
        bw.newLine();
        bw.write("\t\t FROM " + tableName);
        bw.newLine();
        bw.write("\t\t WHERE   status = 1 ");
        bw.newLine();
        bw.write("\t</select>");
        // 查询完



    }
    private void buildSQLW( BufferedWriter bw, List<String> columns, List<String> types ) throws IOException {
        int size = columns.size();

        // 删除（根据主键ID删除）
        bw.write("\t<!--删除：根据主键ID逻辑删除-->");
        bw.newLine();
        bw.write("\t<delete id=\"delete\" parameterType=\"java.lang." + processType(types.get(0)) + "\">");
        bw.newLine();
        bw.write("\t\t update " + tableName);
        bw.newLine();
        bw.write("\t\t set status=2  WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("\t</delete>");
        bw.newLine();
        bw.newLine();
        // 删除完


        // 添加insert方法
        bw.write("\t<!-- 添加 -->");
        bw.newLine();
        bw.write("\t<insert id=\"add\" parameterType=\"" + bean_package+"."+beanName + "\">");
        bw.newLine();
        bw.write("\t\t INSERT INTO " + tableName);
        bw.newLine();
        bw.write(" \t\t(");
        for ( int i = 0 ; i < size ; i++ ) {
            bw.write(columns.get(i));
            if ( i != size - 1 ) {
                bw.write(",");
            }
        }
        bw.write(") ");
        bw.newLine();
        bw.write("\t\t VALUES ");
        bw.newLine();
        bw.write(" \t\t(");
        for ( int i = 0 ; i < size ; i++ ) {
            bw.write("#{" + processField(columns.get(i)) + "}");
            if ( i != size - 1 ) {
                bw.write(",");
            }
        }
        bw.write(") ");
        bw.newLine();
        bw.write("\t</insert>");
        bw.newLine();
        bw.newLine();
        // 添加insert完



        String tempField = null;

        //---------------  完毕



        // ----- 修改
        bw.write("\t<!-- 修 改-->");
        bw.newLine();
        bw.write("\t<update id=\"update\" parameterType=\"" +bean_package+"."+beanName + "\">");
        bw.newLine();
        bw.write("\t\t UPDATE " + tableName);
        bw.newLine();
        bw.write("\t\t SET ");

        bw.newLine();
        tempField = null;
        for ( int i = 1 ; i < size ; i++ ) {
            tempField = processField(columns.get(i));
            bw.write("\t\t\t " + columns.get(i) + " = #{" + tempField + "}");
            if ( i != size - 1 ) {
                bw.write(",");
            }
            bw.newLine();
        }

        bw.write("\t\t WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("\t</update>");
        bw.newLine();


        // 修改update方法
//        bw.write("\t<!-- 修 改（匹配有值的字段）-->");
//        bw.newLine();
//        bw.write("\t<update id=\"updateByPrimaryKeySelective\" parameterType=\"" + processResultMapId(beanName) + "\">");
//        bw.newLine();
//        bw.write("\t\t UPDATE " + tableName);
//        bw.newLine();
//        bw.write(" \t\t <set> ");
//        bw.newLine();
//
//        tempField = null;
//        for ( int i = 1 ; i < size ; i++ ) {
//            tempField = processField(columns.get(i));
//            bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
//            bw.newLine();
//            bw.write("\t\t\t\t " + columns.get(i) + " = #{" + tempField + "},");
//            bw.newLine();
//            bw.write("\t\t\t</if>");
//            bw.newLine();
//        }
//
//        bw.newLine();
//        bw.write(" \t\t </set>");
//        bw.newLine();
//        bw.write("\t\t WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
//        bw.newLine();
//        bw.write("\t</update>");
//        bw.newLine();
//        bw.newLine();

        // update方法完毕

        bw.newLine();
    }



    /**
     *  获取所有的数据库表注释
     *
     * @return
     * @throws SQLException
     */
    private Map<String, String> getTableComment() throws SQLException {
        Map<String, String> maps = new HashMap<String, String>();
        PreparedStatement pstate = conn.prepareStatement("show table status");
        ResultSet results = pstate.executeQuery();
        while ( results.next() ) {
            String tableName = results.getString("NAME");
            String comment = results.getString("COMMENT");
            maps.put(tableName, comment);
        }
        return maps;
    }


    public void generate() throws ClassNotFoundException, SQLException, IOException {
        init();
        String prefix = "show full fields from ";
        List<String> columns = null;
        List<String> types = null;
        List<String> comments = null;
        PreparedStatement pstate = null;
        List<String> tables = getTables();
        Map<String, String> tableComments = getTableComment();
        for ( String table : tables ) {
            columns = new ArrayList<String>();
            types = new ArrayList<String>();
            comments = new ArrayList<String>();
            pstate = conn.prepareStatement(prefix + table);
            ResultSet results = pstate.executeQuery();
            while ( results.next() ) {
                columns.add(results.getString("FIELD"));
                types.add(results.getString("TYPE"));
                comments.add(results.getString("COMMENT"));
            }
            tableName = table;
            processTable(table);
            //          this.outputBaseBean();
            String tableComment = tableComments.get(tableName);
            System.out.println(tableComment);
            buildEntityBean(columns, types, comments, tableComment);
            buildMapper();
            buildMapperXml(columns, types, comments);

            buildDao(tableComment);
        }
        conn.close();
    }


    /**
     *  生成类
     *
     * @throws IOException
     */
    private void buildDao( String tableComment )
            throws IOException {
        File folder = new File(dao_path);
        if ( !folder.exists() ) {
            folder.mkdir();
        }

        //read dao
        File folderR = new File(dao_path+"/read");
        if ( !folderR.exists() ) {
            folderR.mkdir();
        }

        File beanFileR = new File(dao_path+"/read", "I"+beanName + "DaoR.java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFileR)));
        bw.write("package " + bean_package + ";");
        bw.newLine();
        bw.write("import "+bean_package+"."+beanName+";");
        bw.newLine();

        bw.newLine();
        bw.write("import java.util.List; ");
        bw.newLine();
        bw.write("import java.util.Map;");
        bw.newLine();

        bw = buildClassComment(bw, tableComment);
        bw.newLine();
        bw.write("public interface " + "I"+beanName + "DaoR" + "  {");
        bw.newLine();
        bw.newLine();

        bw.newLine();
        bw = buildMethodComment(bw, "返回对象");
        bw.newLine();
        bw.write("\tpublic  " +beanName + " get(Long id);");
        bw.newLine();
        bw.newLine();

        bw.newLine();
        bw = buildMethodComment(bw, "返回列表");
        bw.newLine();
        bw.write("\tpublic List<Map<String, Object>> getList(Map<String, Object> map);");
        bw.newLine();
        bw.newLine();

        bw.newLine();
        bw = buildMethodComment(bw, "返回实体对象的分页记录");
        bw.newLine();
        bw.write("\tpublic List<"+beanName+"> getListPage(Map<String, Object> map);");
        bw.newLine();
        bw.newLine();

        bw.newLine();
        bw = buildMethodComment(bw, "返回Map对象的分页记录");
        bw.newLine();

        bw.write("\tpublic List<Map<String, Object>> getListMapPage(Map<String, Object> map);");
        bw.newLine();
        bw.newLine();


        bw.newLine();
        bw = buildMethodComment(bw, "返回记录数量");
        bw.newLine();
        bw.write("\tpublic long getRows(Map<String, Object> map);");
        bw.newLine();
        bw.newLine();

        bw.newLine();
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();



        //read dao
        File folderW = new File(dao_path+"/write");
        if ( !folderW.exists() ) {
            folderW.mkdir();
        }

        File beanFileW = new File(dao_path+"/write", "I"+beanName + "DaoW.java");
        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFileW)));
        bw.write("package " + bean_package + ";");
        bw.newLine();

        bw.write("import "+bean_package+"."+beanName+";");
        bw.newLine();

        bw.newLine();
        bw.write("import java.util.List; ");
        bw.newLine();
        bw.write("import java.util.Map;");
        bw.newLine();

        bw = buildClassComment(bw, tableComment);
        bw.newLine();
        bw.write("public interface " + "I"+beanName + "DaoW" + "  {");
        bw.newLine();
        bw.newLine();
        bw.newLine();
        bw = buildMethodComment(bw, "新增");
        bw.newLine();
        bw.write("\tpublic  int add("+beanName+"  "+ processResultMapId(beanName)+");");
        bw.newLine();
        bw.newLine();

        bw.newLine();
        bw = buildMethodComment(bw, "更新");
        bw.newLine();
        bw.write("\tpublic  int update("+beanName+"  "+  processResultMapId(beanName)+");");
        bw.newLine();
        bw.newLine();

        bw.newLine();
        bw = buildMethodComment(bw, "逻辑删除");
        bw.newLine();
        bw.write("\tpublic  int deleteById(long id);");
        bw.newLine();
        bw.newLine();


        bw.newLine();
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();


        //read  and wirte dao

        File beanFile = new File(dao_path+"", "I"+beanName + "Dao.java");
        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        bw.write("package " + bean_package + ";");
        bw.newLine();

        bw.write("import "+bean_package+"."+beanName+";");
        bw.newLine();

        bw.newLine();
        bw.write("import java.util.List; ");
        bw.newLine();
        bw.write("import java.util.Map;");
        bw.newLine();

        bw = buildClassComment(bw, tableComment);
        bw.newLine();
        bw.write("public interface " + "I"+beanName + "Dao" + "  {");
        bw.newLine();
        bw.newLine();


        bw.newLine();
        bw = buildMethodComment(bw, "返回对象");
        bw.newLine();
        bw.write("\tpublic  " +beanName + " get(Long id);");
        bw.newLine();
        bw.newLine();

        bw.newLine();
        bw = buildMethodComment(bw, "返回列表");
        bw.newLine();
        bw.write("\tpublic List<Map<String, Object>> getList(Map<String, Object> map);");
        bw.newLine();
        bw.newLine();


        bw.newLine();
        bw = buildMethodComment(bw, "返回实体对象的分页记录");
        bw.newLine();
        bw.write("\tpublic List<"+beanName+"> getListPage(Map<String, Object> map);");
        bw.newLine();
        bw.newLine();

        bw.newLine();
        bw = buildMethodComment(bw, "返回Map对象的分页记录");
        bw.newLine();
        bw.write("\tpublic List<Map<String, Object>> getListMapPage(Map<String, Object> map);");
        bw.newLine();
        bw.newLine();


        bw.newLine();
        bw = buildMethodComment(bw, "返回记录数量");
        bw.newLine();
        bw.write("\tpublic long getRows(Map<String, Object> map);");
        bw.newLine();
        bw.newLine();

        bw.newLine();
        bw = buildMethodComment(bw, "新增");
        bw.newLine();
        bw.write("\tpublic  int add("+beanName+"  "+ processResultMapId(beanName)+");");
        bw.newLine();
        bw.newLine();

        bw.newLine();
        bw = buildMethodComment(bw, "更新");
        bw.newLine();
        bw.write("\tpublic  int update("+beanName+"  "+  processResultMapId(beanName)+");");
        bw.newLine();
        bw.newLine();


        bw.newLine();
        bw = buildMethodComment(bw, "逻辑删除");
        bw.newLine();
        bw.write("\tpublic  int deleteById(long id);");
        bw.newLine();
        bw.newLine();


        bw.newLine();
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();


        //read  and wirte dao

        File daoImplFile = new File(dao_path+"", beanName + "DaoImpl.java");
        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(daoImplFile)));
        bw.write("package " + bean_package + ";");
        bw.newLine();


        bw.write("import "+dao_package+".read.I"+beanName+"DaoR;");
        bw.newLine();

        bw.write("import "+dao_package+".write.I"+beanName+"DaoW;");
        bw.newLine();

        bw.write("import "+bean_package+"."+beanName+";");
        bw.newLine();

        bw.newLine();
        bw.write("import java.util.List; ");
        bw.newLine();
        bw.write("import java.util.Map;");
        bw.newLine();
        bw.write("import org.springframework.beans.factory.annotation.Autowired; ");
        bw.newLine();
        bw.write("import org.springframework.stereotype.Service;");

        bw.newLine();


        bw = buildClassComment(bw, tableComment);
        bw.newLine();
        bw.write("@Service(\"");
        bw.write(this.processResultMapId(beanName));
        bw.write("Dao\")");
        bw.newLine();
        bw.write("public class " + beanName + "DaoImpl implements I" +beanName +  "Dao {");
        bw.newLine();
        bw.newLine();


        bw.newLine();
        bw.write("@Autowired");
        bw.newLine();
        bw.write("private I"+ beanName + "DaoR");
        bw.write(" "+ processResultMapId(beanName) + "DaoR;");
        bw.newLine();

        bw.newLine();
        bw.write("@Autowired");
        bw.newLine();
        bw.write("private I"+ beanName + "DaoW");
        bw.write(" "+ processResultMapId(beanName) + "DaoW;");
        bw.newLine();


        bw.newLine();
        bw = buildMethodComment(bw, "返回对象");
        bw.newLine();
        bw.write("\tpublic  " +beanName + " get(Long id){");
        bw.newLine();

        bw.write("\t\treturn  " +processResultMapId(beanName) + "DaoR.get(id);");
        bw.newLine();

        bw.write("\t}");
        bw.newLine();

        bw.newLine();

        bw.newLine();
        bw = buildMethodComment(bw, "返回列表");
        bw.newLine();
        bw.write("\tpublic List<Map<String, Object>> getList(Map<String, Object> map){");
        bw.newLine();

        bw.write("\t\treturn  " +processResultMapId(beanName) + "DaoR.getList(map);");
        bw.newLine();

        bw.write("\t}");
        bw.newLine();


        bw.newLine();
        bw = buildMethodComment(bw, "返回实体对象的分页记录");
        bw.newLine();
        bw.write("\tpublic List<"+beanName+"> getListPage(Map<String, Object> map){");
        bw.newLine();

        bw.write("\t\treturn  " +processResultMapId(beanName) + "DaoR.getListPage(map);");
        bw.newLine();

        bw.write("\t}");

        bw.newLine();

        bw.newLine();
        bw = buildMethodComment(bw, "返回Map对象的分页记录");
        bw.newLine();
        bw.write("\tpublic List<Map<String, Object>> getListMapPage(Map<String, Object> map){");
        bw.newLine();

        bw.write("\t\treturn  " +processResultMapId(beanName) + "DaoR.getListMapPage(map);");
        bw.newLine();

        bw.write("\t}");
        bw.newLine();


        bw.newLine();
        bw = buildMethodComment(bw, "返回记录数量");
        bw.newLine();
        bw.write("\tpublic long getRows(Map<String, Object> map){");
        bw.newLine();

        bw.write("\t\treturn  " +processResultMapId(beanName) + "DaoR.getRows(map);");
        bw.newLine();

        bw.write("\t}");
        bw.newLine();

        bw.newLine();
        bw = buildMethodComment(bw, "新增");
        bw.newLine();
        bw.write("\tpublic  int add("+beanName+"  "+ processResultMapId(beanName)+"){");
        bw.newLine();

        bw.write("\t\treturn  " +processResultMapId(beanName) + "DaoW.add("+ processResultMapId(beanName)+");");
        bw.newLine();

        bw.write("\t}");
        bw.newLine();

        bw.newLine();
        bw = buildMethodComment(bw, "更新");
        bw.newLine();
        bw.write("\tpublic  int update("+beanName+"  "+  processResultMapId(beanName)+"){");
        bw.newLine();

        bw.write("\t\treturn  " +processResultMapId(beanName) + "DaoW.update("+ processResultMapId(beanName)+");");
        bw.newLine();

        bw.write("\t}");
        bw.newLine();


        bw.newLine();
        bw = buildMethodComment(bw, "逻辑删除");
        bw.newLine();
        bw.write("\tpublic  int deleteById(long id){");
        bw.newLine();

        bw.write("\t\treturn  " +processResultMapId(beanName) + "DaoW.deleteById(id);");
        bw.newLine();

        bw.write("\t}");
        bw.newLine();


        bw.newLine();
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();
    }

    private String processEntityType( String type ) {
        if ( type.indexOf(type_char) > -1 ) {
            return "String";
        } else if ( type.indexOf(type_bigint) > -1 ) {
            return "long";
        } else if ( type.indexOf(type_varchar) > -1 ) {
            return "String";
        } else if ( type.indexOf(type_int) > -1 ) {
            return "int";
        } else if ( type.indexOf(type_date) > -1 ) {
            return "Date";
        } else if ( type.indexOf(type_text) > -1 ) {
            return "String";
        } else if ( type.indexOf(type_timestamp) > -1 ) {
            return "Date";
        } else if ( type.indexOf(type_double) > -1 ) {
            return "Double";
        }else if ( type.indexOf(type_bit) > -1 ) {
            return "Boolean";
        } else if ( type.indexOf(type_decimal) > -1 ) {
            return "BigDecimal";
        } else if ( type.indexOf(type_blob) > -1 ) {
            return "byte[]";
        }
        return null;
    }

    private String processJdbcType( String type ) {
        if ( type.indexOf(type_char) > -1 ) {
            return "VARCHAR";
        } else if ( type.indexOf(type_bigint) > -1 ) {
            return "BIGINT";
        } else if ( type.indexOf(type_varchar) > -1 ) {
            return "VARCHAR";
        }else if ( type.indexOf(type_int) > -1 ) {
            return "INTEGER";
        }else if ( type.indexOf(type_double) > -1 ) {
            return "DOUBLE";
        } else if ( type.indexOf(type_date) > -1 ) {
            return "TIMESTAMP";
        } else if ( type.indexOf(type_text) > -1 ) {
            return "VARCHAR";
        } else if ( type.indexOf(type_timestamp) > -1 ) {
            return "TIMESTAMP";
        }
        else if(type.indexOf(type_decimal) > -1){
            return "DECIMAL";
        }
        return null;
    }
    public static void main( String[] args ) {
        try {
            new MybatisGenerateUtil().generate();
            // 自动打开生成文件的目录
            //Runtime.getRuntime().exec("cmd /c start explorer D:\\");
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        } catch ( SQLException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}