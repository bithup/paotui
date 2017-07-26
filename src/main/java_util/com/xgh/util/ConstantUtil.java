package com.xgh.util;


public class ConstantUtil {

	/**
	 * mencached 缓存有效时间（秒）
	 */
	public final static int EXPIRY_TIME = 1800;

	public static String CMBS_URL = "";

	public static String FP_URL = "";

	public static String TEMP_PATH = "/opt/temp_path/";

	public static String SAVE_PATH = "/opt/";

	public static String SERVER_URL = "http://127.0.0.1/";


	static {

		PropertiesUtil mPropertiesUtil = PropertiesUtil.getInstance("/config.properties");

		if (mPropertiesUtil.getValueByKey("cmbs.url") != null) {
			CMBS_URL = mPropertiesUtil.getValueByKey("cmbs.url");
		}

		if (mPropertiesUtil.getValueByKey("fp.url") != null) {
			FP_URL = mPropertiesUtil.getValueByKey("fp.url");
		}

		if (mPropertiesUtil.getValueByKey("temp.path") != null) {
			TEMP_PATH = mPropertiesUtil.getValueByKey("temp.path");
		}

		if (mPropertiesUtil.getValueByKey("save.path") != null) {
			SAVE_PATH = mPropertiesUtil.getValueByKey("save.path");
		}

		if (mPropertiesUtil.getValueByKey("server.url") != null) {
			SERVER_URL = mPropertiesUtil.getValueByKey("server.url");
		}

	}

	public static String getTempFileKey(String sessionId){
		return sessionId+"_tempFile";
	}

	/**
	 * 获取存储的用户的相关信息的key
	 */
	public enum SessionKeys{

		IndustryKey("industry"),
		UnitKey("unit"),
		UserKey("user"),
		SysRoleKey("sysRole");

		private String _value;
		private SessionKeys(String value){
			this._value = value;
		}

		public String value(){
			return _value;
		}
	}

	/**
	 * 文件上传分类编码
	 */
	public enum FileUploadCode{
		//单位图片
		Unit("unit"),
		Shop("shop"),
		ShopLogo("shoplogo"),
		Goods("goods"),
		GoodsLogo("goodslogo"),
		Subject("subject"),
		Member("member"),
		MemberLogo("member"),
		Teacher("teacher"),
		TeacherLogo("teacherlogo"),
		Customer("customer"),
		DeliveryMan("deliveryMan"),
		Kinds("kinds");




		private String _value = "";
		private FileUploadCode(String value){
			this._value = value;
		}

		public String value(){
			return _value;
		}
	}

	/**
	 * 混合选择窗口，列表健
	 */
	public enum SelectListKey {

		/**
		 * 人员列表健
		 */
		PeopleKey("p_"),

		/**
		 * 部门列表健
		 */
		DeptKey("d_");

		private String _value;

		private SelectListKey(String value) {
			this._value = value;
		}

		public String value(){
			return _value;
		}
	}


	/**
	 * 权限类型静态变量
	 */
	public class PrivilegeKey {

		/**
		 * 单位
		 */
		public final static String Unit = "unit";


		/**
		 * 用户
		 */
		public final static String User = "user";

		/**
		 * 角色
		 */
		public final static String Role = "role";

		/**
		 * 菜单
		 */
		public final static String Menu = "menu";


		/**
		 * 按钮
		 */
		public final static String Button = "button";
	}



	/**
	 * 系统维护关联类型
	 */
	public class SysJoinType {


		/**
		 * 菜单
		 */
		public final static String menu = "menu";


		/**
		 * 表格列
		 */
		public final static String grid = "grid";

		/**
		 * 验证
		 */
		public final static String validate = "validate";


		/**
		 * 查询
		 */
		public final static String query = "query";
	}


	/**
	 * 操作结果
	 */
	public enum ResultKey {

		//返回标记1、成功，0失败，-1、其他
		resultFlg("resultFlg"),
		//返回消息
		resultMsg("resultMsg"),
		//返回数据
		resultData("resultData");

		private String _value;

		private ResultKey(String value) {
			this._value = value;
		}

		public String value() {
			return _value;
		}
	}

	/**
	 * 字典前缀
	 */
	public class DictKeys {

		/**
		 * 主表
		 */
		public final static String DicMain = "dic_main";


		/**
		 * 详细表
		 */
		public final static String DicDetail = "dic_detail";
	}


	public enum DictClumn {
		//id
		id("id"),
		//主表Id
		dictMainId("dictMainId"),
		//编码
		code("code"),
		//对应值
		value("value"),
		//备注信息
		memo("memo"),
		//排序字段
		ord("ord");

		private String _value;
		private DictClumn(String value) {
			this._value = value;
		}

		public String value(){
			return _value;
		}
	}

	public enum DictOrderBy {
		//降序
		desc("desc"),
		//升序
		asc("asc");

		private String _value;
		private DictOrderBy(String value) {
			this._value = value;
		}

		public String value(){
			return _value;
		}
	}


	/**
	 * 操作类型
	 */
	public enum OpType {

		/**
		 * 登陆
		 */
		Login("Login"),

		/**
		 * 退出
		 */
		LoginOut("LoginOut"),

		/**
		 * 添加
		 */
		Add("Add"),

		/**
		 * 删除
		 */
		Delete("Delete"),

		/**
		 * 修改
		 */
		Modify("Modify"),

		/**
		 * 查询
		 */
		Search("Search");

		private String _value;

		private OpType(String value) {
			this._value = value;
		}

		public String value(){
			return _value;
		}
	}

	/**
	 * 操作结果
	 */
	public enum OpResult {

		/**
		 * 操作成功
		 */
		Success("Success"),

		/**
		 * 操作失败
		 */
		Fail("Fail");

		private String _value;

		private OpResult(String value) {
			this._value = value;
		}

		public String value(){
			return _value;
		}
	}


	/**
	 * 段晓刚
	 * 混合选择窗口，列表健
	 */
	public enum SelectsTableName {

		SysIndustry("tb_sys_industry"),

		/**
		 * 角色表
		 */
		SysRole("tb_sys_role"),

		/**
		 * 部门表
		 */
		SysDepartment("tb_sys_department"),

		/**
		 * 用户部门用户关联表
		 */
		SysDeptUser("tb_sys_dept_user"),

		/**
		 * 用户表
		 */
		SysUser("tb_sys_user"),

		/**
		 * 权限表
		 */
		SysPrivilegeList("tb_sys_privilege_list"),

		/**
		 * 会员
		 */
		MemberUser("tb_member_user"),

		/**
		 * 区域表
		 */
		Zone("tb_zone"),


		/**
		 * 会员中心
		 */
		memberUser("tb_member_user"),

		/**
		 * 图片维护
		 */
		commonImage("tb_common_image");

		private String _value;

		private SelectsTableName(String value) {
			this._value = value;
		}

		public String value(){
			return _value;
		}
	}


	/**
	 * 段晓刚
	 * 混合选择窗口，列表健
	 */
	public enum SMSTemplate {

		/**
		 * 后台用户注册密码短信模板
		 */
		MngUserRegisterPwd("MngUserRegisterPwd", "【放上去】账号注册成功，密码为：${pwd}，请注意保存密码，并且打死都不说出来！"),

		/**
		 * 后台用户重置密码短信模板
		 */
		MngUserResetPwd("MngUserResetPwd", "【放上去】密码重置成功，密码为：${pwd}，请注意保存密码，并且打死都不说出来！");

		public String key;
		public String value;

		private SMSTemplate(String key, String value) {

			this.key = key;
			this.value = value;
		}
	}
}