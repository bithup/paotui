package com.xgh.paotui.dao.read;

import com.xgh.paotui.entity.FileData;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public abstract interface IFileDataDaoNewR
{
    public abstract FileData get(long paramLong);

    public abstract FileData getByNid(long paramLong);

    public abstract List<FileData> getList(Map<String, Object> paramMap);

    public abstract List<FileData> getListPage(Map<String, Object> paramMap);

    public abstract long getRows(Map<String, Object> paramMap);
}