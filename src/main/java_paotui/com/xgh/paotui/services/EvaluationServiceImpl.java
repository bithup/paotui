package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.paotui.dao.IDeliveryManDao;
import com.xgh.paotui.dao.IEvaluationDao;
import com.xgh.paotui.dao.IOrderDao;
import com.xgh.paotui.entity.DeliveryMan;
import com.xgh.paotui.entity.Evaluation;
import com.xgh.paotui.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("evaluationService")
public class EvaluationServiceImpl extends BaseService implements IEvaluationService {

    @Autowired
    protected IEvaluationDao evaluationDao;

    @Autowired
    protected IOrderDao orderDao;

    @Autowired
    protected IDeliveryManDao deliveryManDao;

    public int update(Evaluation evaluation) {
        return evaluationDao.update(evaluation);
    }

    public Evaluation get(long id) {
        Evaluation evaluation= evaluationDao.get(id);
        return evaluation;
    }

    public List<Map<String, Object>> getList(Map<String, Object> map) {
        return evaluationDao.getList(map);
    }


    /**
     * 评论接口
     * @return
     */
    public Map<String,Object> addEvaluation(Evaluation evaluation){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Order order = orderDao.get(evaluation.getOrderId());
        if (order!=null&&order.getId()>0){
            if (order.getEvaluationStatus()==2){
                evaluation.setDeliveryManId(order.getDeliveryManId());
                int falg= evaluationDao.add(evaluation);
                if (falg>0){
                    order.setEvaluationStatus(1);
                    orderDao.update(order);
                    /*重新计算跑客好评率*/
                    long deliveryManId = order.getDeliveryManId();
                    DeliveryMan deliveryMan = deliveryManDao.get(deliveryManId);
                    //跑客接单总数
                    int orderCount = deliveryMan.getGetOrderCount();
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("deliveryManId",order.getDeliveryManId());
                    map.put("level",1);
                    //跑客好评订单数
                    long praiseOrderCount = evaluationDao.getRows(map);
                    Double praiseRate;
                    if ("1".equals(evaluation.getLevel())){
                        praiseOrderCount = praiseOrderCount+1;
                    }
                    //好评率
                    praiseRate = Double.parseDouble(String.valueOf(praiseOrderCount))/(orderCount+1);
                    BigDecimal bigDecimal = new BigDecimal(praiseRate);
                    Double praiseRate_ = bigDecimal.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
                    deliveryMan.setPraiseRate(praiseRate_*100);
                    deliveryManDao.update(deliveryMan);
                    resultMap = getResultMap("1","添加评价成功");
                }else {
                    resultMap = getResultMap("0","添加评价失败");
                }
            }else if (order.getEvaluationStatus()==1){
                resultMap = getResultMap("-2","订单已评论");
            }else {
                resultMap = getResultMap("-3","订单不能评论");
            }
        }else {
            resultMap = getResultMap("-1","订单不存在");
        }
        return resultMap;
    }

}