package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.dao.ISysUserDao;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.dao.ICapitalLogDao;
import com.xgh.paotui.dao.IDeliveryManDao;
import com.xgh.paotui.dao.IDeliveryManWithdrawalsDao;
import com.xgh.paotui.entity.CapitalLog;
import com.xgh.paotui.entity.DeliveryMan;
import com.xgh.paotui.entity.DeliveryManWithdrawals;
import com.xgh.paotui.weixin.pay.WeiChartUtil;
import com.xgh.util.DictUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;


@Service("deliveryManWithdrawalsService")
public class DeliveryManWithdrawalsServiceImpl extends BaseService implements IDeliveryManWithdrawalsService {

	@Autowired
	protected IDeliveryManWithdrawalsDao deliveryManWithdrawalsDao;

	@Autowired
	protected IDeliveryManDao deliveryManDao;

	@Autowired
	private ICapitalLogDao capitalLogDao;

	@Autowired
	private ISysUserDao sysUserDao;

	public DeliveryManWithdrawals get(Long id) {
		return deliveryManWithdrawalsDao.get(id);
	}

	public Map<String, Object> getGridList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> gridMap = new HashMap<String, Object>();
		String page = request.getParameter("page");
		String pagesize = request.getParameter("pagesize");
		String realName = request.getParameter("realName");
		String checkState = request.getParameter("checkState");
		map.put("page", Integer.parseInt(page));
		map.put("pagesize", Integer.parseInt(pagesize));
		map.put("realName", realName);
		map.put("checkState", checkState);
		//系统人员所属地区
		SysUser sysUser = getCurrentUser(request);
		if (sysUser.getOpenCityId() != null && sysUser.getOpenCityId() != 0) {
			map.put("belongCityId", sysUser.getOpenCityId());
		}
		List<Map<String, Object>> dataList = deliveryManWithdrawalsDao.getListMapPage(map);
		if (dataList == null) {
			dataList = new ArrayList<Map<String, Object>>();
		}
		for (Map<String, Object> dataList_ : dataList) {
			if (dataList_.get("deliverManId") != null) {
				long deliverManId = Long.parseLong(dataList_.get("deliverManId").toString());
				DeliveryMan deliveryMan = deliveryManDao.get(deliverManId);
				if (deliveryMan != null) {
					dataList_.put("realName", deliveryMan.getRealName());
					if (deliveryMan.getBelongType() != null) {
						String belongType = String.valueOf(deliveryMan.getBelongType());
						dataList_.put("belongType", DictUtils.getDictLabel(belongType, "paotui_belong_type", ""));
					}
					dataList_.put("mobilePhone", deliveryMan.getMobilePhone());
				}
			}
			if (dataList_.get("checkState") != null) {
				checkState = String.valueOf(dataList_.get("checkState"));
				dataList_.put("checkState", DictUtils.getDictLabel(checkState, "paotui_check_state", ""));
			}
		}
		gridMap.put("Rows", dataList);
		gridMap.put("Total", deliveryManWithdrawalsDao.getRows(map));
		return gridMap;
	}

	/**
	 * @param deliveryManWithdrawals
	 * @return 1：审核通过，提现成功；2：审核不通过成功；3：审核通过，提现失败；0：失败
	 */
	public int save( DeliveryManWithdrawals deliveryManWithdrawals) {


		if (deliveryManWithdrawals != null && deliveryManWithdrawals.getId() > 0) {
			DeliveryManWithdrawals deliveryManWithdrawals1 = deliveryManWithdrawalsDao.get(deliveryManWithdrawals.getId());

			DeliveryMan deliveryMan = deliveryManDao.get(deliveryManWithdrawals1.getDeliverManId());

			if (deliveryManWithdrawals.getCheckState() == 1) {
				//审核通过
				CapitalLog capitalLog = new CapitalLog();
				capitalLog.setAccountType(2);
				capitalLog.setAccountId(deliveryMan.getId());
				capitalLog.setLoginName(deliveryMan.getLoginName());
				capitalLog.setRealName(deliveryMan.getRealName());
				capitalLog.setType(5);
				capitalLog.setWithdrawalsId(deliveryManWithdrawals.getId());
				capitalLog.setCapitalChangeReason("提现");
				capitalLog.setRemark("提现，提交到微信帐号中...");
				capitalLog.setChangeMoney(deliveryManWithdrawals1.getWithdrawalsMoney());
				capitalLog.setCreateDate(new Date());
				capitalLog.setStatus(1);
				capitalLogDao.add(capitalLog);

				//提交到微信支付，转账

				// 发送到微信转账请求
				String strResponse = null;
				try {

					String amount = WeiChartUtil.changeToFen(deliveryManWithdrawals1.getWithdrawalsMoney().toString());
					Map<String, String> retMap = WeiChartUtil.transfers(deliveryMan.getToken(), String.valueOf(capitalLog.getId()), deliveryMan.getRealName(), amount);

					strResponse = retMap.toString();
					String return_code = retMap.get("return_code");
					String result_code=retMap.get("result_code");

					//String return_code ="success";
					if ("success".equalsIgnoreCase(return_code)  && "success".equalsIgnoreCase(result_code)) {
						capitalLog.setRemark("提现成功");
						capitalLogDao.update(capitalLog);


						deliveryManWithdrawals1.setCheckState(deliveryManWithdrawals.getCheckState());
						deliveryManWithdrawals1.setRemark(deliveryManWithdrawals.getRemark());
						deliveryManWithdrawals1.setCheckDate(new Date());
						deliveryManWithdrawals1.setCheckUserId(deliveryManWithdrawals.getCheckUserId());

						deliveryManWithdrawalsDao.update(deliveryManWithdrawals1);
						Logger.getLogger(getClass()).info("weixinTransfersRequest："+strResponse);
						return 1;
					} else {



						capitalLog.setCapitalChangeReason("提现到跑客微信帐号失败,请重试");
						capitalLogDao.update(capitalLog);
						Logger.getLogger(getClass()).info("weixinTransfersRequest："+strResponse);
						return 3;

					}

				} catch (Exception e) {
					Logger.getLogger(getClass()).error("weixinTransfersRequest：", e);
				}
				strResponse = "提现到跑客微信帐号失败,请重试";
				capitalLog.setRemark(strResponse);
				capitalLogDao.update(capitalLog);

				return 3;

			} else if (deliveryManWithdrawals.getCheckState() == 3) {
				//审核未通过

				CapitalLog capitalLog = new CapitalLog();
				capitalLog.setAccountType(2);
				capitalLog.setAccountId(deliveryMan.getId());
				capitalLog.setLoginName(deliveryMan.getLoginName());
				capitalLog.setRealName(deliveryMan.getRealName());
				capitalLog.setType(5);
				capitalLog.setWithdrawalsId(deliveryManWithdrawals.getId());
				capitalLog.setCapitalChangeReason("提现");
				capitalLog.setRemark("提现审核未通过:"+deliveryManWithdrawals.getRemark());
				capitalLog.setChangeMoney(deliveryManWithdrawals1.getWithdrawalsMoney());
				capitalLog.setCreateDate(new Date());
				capitalLog.setStatus(1);
				capitalLogDao.add(capitalLog);

				deliveryManWithdrawals1.setCheckState(deliveryManWithdrawals.getCheckState());
				deliveryManWithdrawals1.setRemark(deliveryManWithdrawals.getRemark());
				deliveryManWithdrawals1.setCheckDate(new Date());
				deliveryManWithdrawals1.setCheckUserId(deliveryManWithdrawals.getCheckUserId());
				deliveryManWithdrawalsDao.update(deliveryManWithdrawals1);

				//提现审核未通过,跑客账户还原
				deliveryMan.setBalance(deliveryMan.getBalance().add(deliveryManWithdrawals1.getWithdrawalsMoney()));
				deliveryManDao.update(deliveryMan);
				return 2;

			} else {
				return 0;
			}


		} else {
			return 0;
		}
	}


}