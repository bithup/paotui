package com.xgh.paotui.services;

import com.xgh.paotui.entity.ErrandsFee;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IErrandsFeeService {

    public ErrandsFee get(Long id);

    public int save(HttpServletRequest request,ErrandsFee errandsFee);

    public int update(ErrandsFee errandsFee);

    public Map<String, Object> getGridList(HttpServletRequest request);

    public List<ErrandsFee> getList(Map<String, Object> map);

}
