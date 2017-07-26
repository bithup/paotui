package com.xgh.paotui.services;

import com.xgh.paotui.entity.FileData;
import com.xgh.util.ConstantUtil;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public abstract interface IFileDataServiceNew
{
    public abstract int add(FileData paramFileData);

    public abstract int delete(long paramLong);

    public abstract int updateByDataSource(Map<String, Object> paramMap);

    public abstract int update(FileData paramFileData);

    public abstract FileData get(long paramLong);

    public abstract List<FileData> getList(Map<String, Object> paramMap);

    public abstract List<FileData> getListPage(Map<String, Object> paramMap);

    public abstract long getRows(Map<String, Object> paramMap);

    public abstract List<Map<String, Object>> getFileDatas(Map<String, Object> paramMap);

    public abstract List<FileData> saveFiles(HttpServletRequest paramHttpServletRequest, String[] paramArrayOfString, long paramLong, ConstantUtil.FileUploadCode paramFileUploadCode, int paramInt, int type);


}