package com.xgh.paotui.dao;


import com.xgh.paotui.dao.read.IFileDataDaoNewR;
import com.xgh.paotui.dao.write.IFileDataDaoNewW;
import com.xgh.paotui.entity.FileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("fileDataNewDao")
public class FileDataDaoNewImpl implements IFileDataNewDao {

    @Autowired
    protected IFileDataDaoNewR fileDataDaoR;

    @Autowired
    protected IFileDataDaoNewW fileDataDaoW;

    public int add(FileData fileData)
    {
        return this.fileDataDaoW.add(fileData);
    }

    public int update(FileData fileData)
    {
        return this.fileDataDaoW.update(fileData);
    }

    public int deleteById(long id)
    {
        return this.fileDataDaoW.deleteById(id);
    }

    public int updateByDataSource(Map<String, Object> map)
    {
        return this.fileDataDaoW.updateByDataSource(map);
    }

    public int addBatch(List<FileData> list)
    {
        return this.fileDataDaoW.addBatch(list);
    }

    public FileData get(long id)
    {
        return this.fileDataDaoR.get(id);
    }

    public List<FileData> getList(Map<String, Object> map)
    {
        return this.fileDataDaoR.getList(map);
    }

    public List<FileData> getListPage(Map<String, Object> map)
    {
        return this.fileDataDaoR.getListPage(map);
    }

    public long getRows(Map<String, Object> map)
    {
        return this.fileDataDaoR.getRows(map);
    }
}