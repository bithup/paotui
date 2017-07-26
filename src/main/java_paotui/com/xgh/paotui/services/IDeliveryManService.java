package com.xgh.paotui.services;

import com.xgh.paotui.entity.DeliveryMan;
import com.xgh.paotui.entity.DeliveryManAuth;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IDeliveryManService {

    public DeliveryMan get(Long id);

    public int update(DeliveryMan deliveryMan);

    public int  save(HttpServletRequest request,DeliveryMan deliveryMan);
    public Map<String, Object> getGridList(HttpServletRequest request,String cityId);

    public Map<String,Object> login(HttpServletRequest request);

    public Map<String,Object> register(HttpServletRequest request);

    public Map<String, Object> updatePassword(HttpServletRequest request,long deliveryManId);

    public Map<String,Object> forgetPassword(HttpServletRequest request);

    public List<Map<String,Object>> getCanRobOrder(Map<String,Object> map);

    public int saveDeliveryMan(DeliveryMan deliveryMan,DeliveryManAuth deliveryManAuth);

    public Map<String,Object> replacePhoneNumberOld(HttpServletRequest request,long deliveryManId);

    public Map<String,Object> replacePhoneNumberNew(HttpServletRequest request,long deliveryManId );


    /**
     *更新微信认证状态
     * @param deliveryManId
     * @param weixinOpenid
     * @return 0:失败；1:成功; 2:用户不存在
     */
    public int updateOfWeixinAuth( Long deliveryManId,String weixinOpenid);


    /**
     *申请提现处理
     * @param deliveryManId
     * @param money 提现金额
     * @return 0:失败；1:成功; 2:用户不存在;3:提现金额大于账户余额
     */
    public int updateOfApplyWithdrawals( Long deliveryManId,BigDecimal money);


    /**
     * 更新推广二维码信息
     * @param deliveryManId
     * @param fileName 二维码文件名
     * @return
     */
    public int updateMarketQrcode(long deliveryManId,String  fileName);

}
