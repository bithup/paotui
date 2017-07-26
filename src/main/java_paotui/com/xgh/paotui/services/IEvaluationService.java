package com.xgh.paotui.services;

import com.xgh.paotui.entity.Evaluation;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IEvaluationService {

    public int update(Evaluation evaluation);

    public Evaluation get(long id);

    public List<Map<String, Object>> getList(Map<String, Object> map);



    /**
     * 接口
     */
    public Map<String,Object> addEvaluation(Evaluation evaluation);

}
