package com.xgh.dict;

import com.xgh.mng.entity.SysDictDetail;
import com.xgh.util.ConstantUtil;

import java.util.Comparator;

/**
 * 字典排序字符串
 *
 * @author:段晓刚
 * @time:2012-12-19 上午09:08:56
 * @Email:
 */
public class ComparatorSysDictDetail implements Comparator<SysDictDetail> {

    private String clumnName = ConstantUtil.DictClumn.id.toString();//默认使用Id排序
    private String orderBy = ConstantUtil.DictOrderBy.asc.toString();//降序，asc升序

    private static String ss = "0000000000000000000000";

    private static int len = 20;

    public ComparatorSysDictDetail(ConstantUtil.DictClumn clumnName, ConstantUtil.DictOrderBy orderBy) {
        super();
        if (clumnName != null) {
            this.setClumnName(clumnName.toString());
        }
        if (orderBy != null) {
            this.setOrderBy(orderBy.toString());
        }
    }

    public int compare(SysDictDetail o1, SysDictDetail o2) {
        // TODO Auto-generated method stub
        if (clumnName.equals(ConstantUtil.DictClumn.id.toString())) {
            if (orderBy.equals(ConstantUtil.DictOrderBy.asc.toString()))
                return comparelong(o1.getId(), o2.getId());
            else
                return comparelong(o2.getId(), o1.getId());
        } else if (clumnName.equals(ConstantUtil.DictClumn.value.toString())) {
            if (orderBy.equals(ConstantUtil.DictOrderBy.asc.toString()))
                return o1.getValue().compareTo(o2.getValue());
            else
                return o2.getValue().compareTo(o1.getValue());
        } else if (clumnName.equals(ConstantUtil.DictClumn.code.toString())) {
            String code1 = getCode(o1.getCode());
            String code2 = getCode(o2.getCode());

            if (orderBy.equals(ConstantUtil.DictOrderBy.asc.toString()))
                return code1.compareTo(code2);
            else
                return code2.compareTo(code1);
        } else if (clumnName.equals(ConstantUtil.DictClumn.ord.toString())) {

            if (orderBy.equals(ConstantUtil.DictOrderBy.asc.toString()))
                return comparelong(o1.getOrd(), o2.getOrd());
            else
                return comparelong(o2.getOrd(), o1.getOrd());
        }
        return 0;
    }

    private int comparelong(long param1, long param2) {

        if (param1 > param2) {
            return 1;
        } else if (param1 == param2) {
            return 0;
        } else {
            return -1;
        }
    }


    private String getCode(String code) {

        String temp = ss + code;

        code = temp.substring(temp.length() - len);

        return code;
    }


    public String getClumnName() {
        return clumnName;
    }

    public void setClumnName(String clumnName) {
        this.clumnName = clumnName;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}