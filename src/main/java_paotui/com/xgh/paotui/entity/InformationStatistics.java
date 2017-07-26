package com.xgh.paotui.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 首页信息统计表
 * 
 **/
@SuppressWarnings("serial")
public class InformationStatistics implements Serializable {

	/**唯一id**/
	private long id;

	/**昨日订单量**/
	private int yesterdayOrder;

	/**7天订单量**/
	private int sevendayOrder;

	/**30天订单量**/
	private int thirtydayOrder;

	/**全部订单量**/
	private long allOrder;

	/**昨日新增客户数**/
	private int yesterdayAddCustomer;

	/**7天新增客户数**/
	private int sevendayAddCustomer;

	/**30天新增客户数**/
	private int thirtydayAddCutomer;

	/**所有客户**/
	private long allCustomer;

	/**昨日充值金额**/
	private BigDecimal yesterdayRechargeAmount;

	/**昨日平台佣金收入**/
	private BigDecimal yesterdayPlatformRevenue;

	/**7天充值金额**/
	private BigDecimal sevendayRechargeAmount;

	/**7天平台佣金收入**/
	private BigDecimal sevendayPlatformRevenue;

	/**30天充值金额**/
	private BigDecimal thirtydayRechargeAmount;

	/**30天平台佣金收入**/
	private BigDecimal thirtydayPlatformRevenue;

	/**总充值金额**/
	private BigDecimal allRechargeAmount;

	/**平台佣金总收入**/
	private BigDecimal allPlatformRevenue;

	/**创建时间**/
	private Date createDate;

	/**状态（1：正常 2：已删除）**/
	private int status;

	/**扩展1**/
	private long data1;

	/**扩展2**/
	private int data2;

	/**扩展3**/
	private String data3;

	/**扩展4**/
	private String data4;

	/**扩展5**/
	private String data5;


	public InformationStatistics() { super(); }

	public InformationStatistics(long id) {
	 super();
	 this.id=id;
	}

	public InformationStatistics(long id,int yesterdayOrder,int sevendayOrder,int thirtydayOrder,long allOrder,int yesterdayAddCustomer,int sevendayAddCustomer,int thirtydayAddCutomer,long allCustomer,BigDecimal yesterdayRechargeAmount,BigDecimal yesterdayPlatformRevenue,BigDecimal sevendayRechargeAmount,BigDecimal sevendayPlatformRevenue,BigDecimal thirtydayRechargeAmount,BigDecimal thirtydayPlatformRevenue,BigDecimal allRechargeAmount,BigDecimal allPlatformRevenue,Date createDate,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.yesterdayOrder = yesterdayOrder;
		this.sevendayOrder = sevendayOrder;
		this.thirtydayOrder = thirtydayOrder;
		this.allOrder = allOrder;
		this.yesterdayAddCustomer = yesterdayAddCustomer;
		this.sevendayAddCustomer = sevendayAddCustomer;
		this.thirtydayAddCutomer = thirtydayAddCutomer;
		this.allCustomer = allCustomer;
		this.yesterdayRechargeAmount = yesterdayRechargeAmount;
		this.yesterdayPlatformRevenue = yesterdayPlatformRevenue;
		this.sevendayRechargeAmount = sevendayRechargeAmount;
		this.sevendayPlatformRevenue = sevendayPlatformRevenue;
		this.thirtydayRechargeAmount = thirtydayRechargeAmount;
		this.thirtydayPlatformRevenue = thirtydayPlatformRevenue;
		this.allRechargeAmount = allRechargeAmount;
		this.allPlatformRevenue = allPlatformRevenue;
		this.createDate = createDate;
		this.status = status;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.data4 = data4;
		this.data5 = data5;

	}
	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setYesterdayOrder(Integer yesterdayOrder){
		this.yesterdayOrder = yesterdayOrder;
	}

	public Integer getYesterdayOrder(){
		return this.yesterdayOrder;
	}

	public void setSevendayOrder(Integer sevendayOrder){
		this.sevendayOrder = sevendayOrder;
	}

	public Integer getSevendayOrder(){
		return this.sevendayOrder;
	}

	public void setThirtydayOrder(Integer thirtydayOrder){
		this.thirtydayOrder = thirtydayOrder;
	}

	public Integer getThirtydayOrder(){
		return this.thirtydayOrder;
	}

	public void setAllOrder(Long allOrder){
		this.allOrder = allOrder;
	}

	public Long getAllOrder(){
		return this.allOrder;
	}

	public void setYesterdayAddCustomer(Integer yesterdayAddCustomer){
		this.yesterdayAddCustomer = yesterdayAddCustomer;
	}

	public Integer getYesterdayAddCustomer(){
		return this.yesterdayAddCustomer;
	}

	public void setSevendayAddCustomer(Integer sevendayAddCustomer){
		this.sevendayAddCustomer = sevendayAddCustomer;
	}

	public Integer getSevendayAddCustomer(){
		return this.sevendayAddCustomer;
	}

	public void setThirtydayAddCutomer(Integer thirtydayAddCutomer){
		this.thirtydayAddCutomer = thirtydayAddCutomer;
	}

	public Integer getThirtydayAddCutomer(){
		return this.thirtydayAddCutomer;
	}

	public void setAllCustomer(Long allCustomer){
		this.allCustomer = allCustomer;
	}

	public Long getAllCustomer(){
		return this.allCustomer;
	}

	public void setYesterdayRechargeAmount(BigDecimal yesterdayRechargeAmount){
		this.yesterdayRechargeAmount = yesterdayRechargeAmount;
	}

	public BigDecimal getYesterdayRechargeAmount(){
		return this.yesterdayRechargeAmount;
	}

	public void setYesterdayPlatformRevenue(BigDecimal yesterdayPlatformRevenue){
		this.yesterdayPlatformRevenue = yesterdayPlatformRevenue;
	}

	public BigDecimal getYesterdayPlatformRevenue(){
		return this.yesterdayPlatformRevenue;
	}

	public void setSevendayRechargeAmount(BigDecimal sevendayRechargeAmount){
		this.sevendayRechargeAmount = sevendayRechargeAmount;
	}

	public BigDecimal getSevendayRechargeAmount(){
		return this.sevendayRechargeAmount;
	}

	public void setSevendayPlatformRevenue(BigDecimal sevendayPlatformRevenue){
		this.sevendayPlatformRevenue = sevendayPlatformRevenue;
	}

	public BigDecimal getSevendayPlatformRevenue(){
		return this.sevendayPlatformRevenue;
	}

	public void setThirtydayRechargeAmount(BigDecimal thirtydayRechargeAmount){
		this.thirtydayRechargeAmount = thirtydayRechargeAmount;
	}

	public BigDecimal getThirtydayRechargeAmount(){
		return this.thirtydayRechargeAmount;
	}

	public void setThirtydayPlatformRevenue(BigDecimal thirtydayPlatformRevenue){
		this.thirtydayPlatformRevenue = thirtydayPlatformRevenue;
	}

	public BigDecimal getThirtydayPlatformRevenue(){
		return this.thirtydayPlatformRevenue;
	}

	public void setAllRechargeAmount(BigDecimal allRechargeAmount){
		this.allRechargeAmount = allRechargeAmount;
	}

	public BigDecimal getAllRechargeAmount(){
		return this.allRechargeAmount;
	}

	public void setAllPlatformRevenue(BigDecimal allPlatformRevenue){
		this.allPlatformRevenue = allPlatformRevenue;
	}

	public BigDecimal getAllPlatformRevenue(){
		return this.allPlatformRevenue;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return this.status;
	}

	public void setData1(Long data1){
		this.data1 = data1;
	}

	public Long getData1(){
		return this.data1;
	}

	public void setData2(Integer data2){
		this.data2 = data2;
	}

	public Integer getData2(){
		return this.data2;
	}

	public void setData3(String data3){
		this.data3 = data3;
	}

	public String getData3(){
		return this.data3;
	}

	public void setData4(String data4){
		this.data4 = data4;
	}

	public String getData4(){
		return this.data4;
	}

	public void setData5(String data5){
		this.data5 = data5;
	}

	public String getData5(){
		return this.data5;
	}

}
