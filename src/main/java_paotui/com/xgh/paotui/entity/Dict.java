package com.xgh.paotui.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Dict implements Serializable {

	/**编号**/
	private long id;

	/**数据值**/
	private String value;

	/**标签名**/
	private String label;

	/**类型**/
	private String type;

	/**描述**/
	private String description;

	/**排序（升序）**/
	private BigDecimal sort;

	/**创建时间**/
	private Date createDate;

	/**更新时间**/
	private Date updateDate;

	/**备注信息**/
	private String remarks;

	/**状态（1：正常 2：已删除）**/
	private String status;


	public Dict() { super(); }

	public Dict(long id) {
		//增加注释
	 super();
	 this.id=id;
	}

	public Dict(long id,String value,String label,String type,String description,BigDecimal sort,Date createDate,Date updateDate,String remarks,String status){
		super();
		this.id = id;
		this.value = value;
		this.label = label;
		this.type = type;
		this.description = description;
		this.sort = sort;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.remarks = remarks;
		this.status = status;

	}
	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return this.value;
	}

	public void setLabel(String label){
		this.label = label;
	}

	public String getLabel(){
		return this.label;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setSort(java.math.BigDecimal sort){
		this.sort = sort;
	}

	public java.math.BigDecimal getSort(){
		return this.sort;
	}

	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}

	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}

	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	public void setRemarks(String remarks){
		this.remarks = remarks;
	}

	public String getRemarks(){
		return this.remarks;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return this.status;
	}

}
