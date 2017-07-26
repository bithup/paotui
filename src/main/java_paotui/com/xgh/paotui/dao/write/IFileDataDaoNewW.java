package com.xgh.paotui.dao.write;

import com.xgh.paotui.entity.FileData;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public abstract interface IFileDataDaoNewW
{
    public abstract int add(FileData paramFileData);

    public abstract int update(FileData paramFileData);

    public abstract int deleteById(long paramLong);

    public abstract int deleteByNid(long paramLong);

    public abstract int updateByDataSource(Map<String, Object> paramMap);

    public abstract int addBatch(List<FileData> paramList);
}