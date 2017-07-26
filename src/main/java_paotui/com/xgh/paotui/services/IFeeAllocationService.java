package com.xgh.paotui.services;

import com.xgh.paotui.entity.FeeAllocation;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Tian on 2017/2/23.
 */
public interface IFeeAllocationService {
    public int update(FeeAllocation feeAllocation);

    public int save(HttpServletRequest request, FeeAllocation feeAllocation);

    public int delete(long id);

    public FeeAllocation get(long id);

    public List<Map<String, Object>> getList(Map<String, Object> map);

    public Map<String,Object> getGridList(HttpServletRequest request);
}
