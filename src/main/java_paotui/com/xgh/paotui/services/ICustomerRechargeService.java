package com.xgh.paotui.services;

import com.xgh.paotui.entity.CustomerRecharge;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ICustomerRechargeService {

    public CustomerRecharge get(Long id);

    public Map<String, Object> getGridList(HttpServletRequest request);

}
