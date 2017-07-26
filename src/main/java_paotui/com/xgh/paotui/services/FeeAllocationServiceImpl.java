package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.dao.IFeeAllocationDao;
import com.xgh.paotui.dao.IOpenCityDao;
import com.xgh.paotui.entity.FeeAllocation;
import com.xgh.paotui.entity.OpenCity;
import com.xgh.util.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Tian on 2017/2/23.
 */
@Service("feeAllocationService")
public class FeeAllocationServiceImpl  extends BaseService implements IFeeAllocationService {


    @Autowired
    protected IFeeAllocationDao feeAllocationDao;


    @Autowired
    protected IOpenCityDao openCityDao;



    public int update(FeeAllocation feeAllocation) {
        return feeAllocationDao.update(feeAllocation);
    }


    /**
     * 保存主题
     *
     * @param request
     * @param feeAllocation
     * @return
     */
    public int save(HttpServletRequest request, FeeAllocation feeAllocation) {

        //此处认为不为空，进行操作，是否为空的判断在controller中进行
        if (feeAllocation != null && feeAllocation.getId() < 1) {
            //该处认为是添加操作
            Date date = new Date();
            feeAllocation.setCreateDate(date);
            feeAllocation.setStatus(1);
            int ret=feeAllocationDao.add(feeAllocation);
            return ret;
        } else {
            FeeAllocation feeAllocation2 = get(feeAllocation.getId());

            feeAllocation2.setOpenCityId(feeAllocation.getOpenCityId());
            feeAllocation2.setAllocationType(feeAllocation.getAllocationType());

            //按比例
            if(feeAllocation.getAllocationType()==1){
                feeAllocation2.setRunProportion(feeAllocation.getRunProportion());
                feeAllocation2.setCrowdsourcingProportion(feeAllocation.getCrowdsourcingProportion());
                feeAllocation2.setRunOrderFee(null);
                feeAllocation2.setCrowdsourcingOrderFee(null);
            }
            //按单量
            else{
                feeAllocation2.setRunProportion(null);
                feeAllocation2.setCrowdsourcingProportion(null);
                feeAllocation2.setRunOrderFee(feeAllocation.getRunOrderFee());
                feeAllocation2.setCrowdsourcingOrderFee(feeAllocation.getCrowdsourcingOrderFee());
            }

            feeAllocation2.setUpdateDate(new Date());
            return update(feeAllocation2);
        }
    }

    public int delete(long id) {
        return feeAllocationDao.deleteById(id);
    }

    public FeeAllocation get(long id) {

        FeeAllocation feeAllocation = feeAllocationDao.get(id);

        return feeAllocation;
    }


    public List<Map<String, Object>> getList(Map<String, Object> map) {

        return feeAllocationDao.getList(map);
    }

    public Map<String, Object> getGridList(HttpServletRequest request) {
        Map<String, Object> gridMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        String page = request.getParameter("page");
        String pagesize = request.getParameter("pagesize");
        map.put("page", Integer.parseInt(page));
        map.put("pagesize", Integer.parseInt(pagesize));
        SysUser sysUser = getCurrentUser(request);
        if (sysUser.getOpenCityId()!=null&&sysUser.getOpenCityId()!=0){
            map.put("openCityId",sysUser.getOpenCityId());
        }
        List<FeeAllocation> dataList = feeAllocationDao.getListPage(map);
        if (dataList == null) {
            dataList = new ArrayList<FeeAllocation>();
        }
        List<Map<String, Object>> retMapList=new ArrayList<Map<String, Object>>();
        for(FeeAllocation data:dataList){
            Map retMap=new HashMap();
            retMap.put("id",data.getId());
            //开通城市
            Long openCityId=data.getOpenCityId();
            OpenCity openCity=openCityDao.get(openCityId);
            retMap.put("openCityName",openCity.getCityName());

            String allocationType=String.valueOf(data.getAllocationType());
            retMap.put("allocationTypeName", DictUtils.getDictLabel(allocationType,"paotui_allocation_type",""));

            String allocationName="";
            //按比例
            if("1".equals(allocationType)){
                allocationName+="公司员工跑客收入";
                allocationName+=data.getRunProportion();
                allocationName+="%跑腿费，众包跑客收入";
                allocationName+=data.getCrowdsourcingProportion();
                allocationName+="%跑腿费";
            }
            else{
                allocationName+="公司员工跑客每单";
                allocationName+=data.getRunOrderFee();
                allocationName+="元跑腿费，众包跑客每单";
                allocationName+=data.getCrowdsourcingOrderFee();
                allocationName+="元跑腿费";
            }

            retMap.put("allocationName",allocationName);
            retMapList.add(retMap);
        }
        gridMap.put("Rows", retMapList);
        gridMap.put("Total", feeAllocationDao.getRows(map));
        return gridMap;
    }


}