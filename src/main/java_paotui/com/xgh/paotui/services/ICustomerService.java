package com.xgh.paotui.services;

import com.xgh.paotui.entity.Customer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ICustomerService {

    public Customer get(Long id);

    public Map<String, Object> getGridList(HttpServletRequest request);
    public int add(Customer customer);

    /**
     * 微信端注册
     * @param customer
     * @param openid
     * @return
     */
    public int addWithOpenid(Customer customer,String openid);

    public List<Customer> getList(Map<String, Object> map);


    /**
     * 接口
     */
    public Map<String,Object> login(HttpServletRequest request);

    public Map<String,Object> register(HttpServletRequest request);

    public Map<String, Object> modifyPassword(HttpServletRequest request);

    public Map<String,Object> forgetPassword(HttpServletRequest request);

    public Map<String,Object> finishcCustomer(HttpServletRequest request,long customerId);

    public Map<String,Object> replacePhoneNumberOld(HttpServletRequest request,long customerId);

    public Map<String,Object> replacePhoneNumberNew(HttpServletRequest request,long customerId);

    /**
     * 通过支付宝充值
     * @param capitalLogId
     * @param trade_no 支付宝交易号
     * @return 1：成功；2：失败
     */
    public int addBalanceWithAliPay(long capitalLogId,String trade_no);


    /**
     * 通过微信充值
     * @param capitalLogId
     * @param trade_no 微信交易号
     * @param total_fee 订单总金额，单位为分
     * @return 1：成功；2：失败；9:已通知
     */
    public int addBalanceWithWeixinPay(long capitalLogId,String trade_no,String total_fee);

    /**
     * 微信端登录接口，绑定微信openid
     * @param loginName 用户名
     * @param password 密码
     * @param openid 微信端openid
     * @return
     */
    public Map<String,Object> updateOfWeixinLogin(String loginName, String password,String openid);

}
