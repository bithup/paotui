package com.xgh.paotui.controller.front;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.Evaluation;
import com.xgh.paotui.services.IEvaluationService;
import com.xgh.paotui.services.IOrderService;
import com.xgh.util.JSONUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

@Controller
@Api(value="订单评价API")
@RequestMapping(value = "paotui/front/v1/evaluation/")
public class EvaluationFrontController extends BaseController{

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(EvaluationFrontController.class);

    @Autowired
    protected IEvaluationService evaluationService;

    @Autowired
    protected IOrderService orderService;

    @ResponseBody
    @RequestMapping(value = "addEvaluation", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "评价", httpMethod = "POST", notes = "APP端使用")
    public String addEvaluation(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value = "评价内容") @RequestParam(value = "content") String content,
            @ApiParam(value = "评价等级(1：好评  2：中评  3：差评)") @RequestParam(value = "level") String level) {
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Evaluation evaluation = new Evaluation();
        evaluation.setOrderId(orderId);
        evaluation.setCustomerId(customerId);
        evaluation.setContent(content);
        evaluation.setLevel(level);
        evaluation.setCreateDate(new Date());
        evaluation.setStatus(1);
        Map<String,Object> evaluationMap= evaluationService.addEvaluation(evaluation);
        return JSONUtil.getJson(evaluationMap);
    }

}
