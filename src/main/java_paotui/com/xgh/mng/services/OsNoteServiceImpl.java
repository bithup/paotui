package com.xgh.mng.services;

import com.xgh.mng.dao.IOsNoteDao;
import com.xgh.mng.entity.OsNote;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("osNoteService")
public class OsNoteServiceImpl
  implements IOsNoteService
{
  @Autowired
  protected IOsNoteDao osNoteDao;
  
  public int add(OsNote osNote)
  {
    return this.osNoteDao.add(osNote);
  }
  
  public int delete(long id)
  {
    return this.osNoteDao.deleteById(id);
  }
  
  public int update(OsNote osNote)
  {
    return this.osNoteDao.update(osNote);
  }
  
  public OsNote get(long id)
  {
    return this.osNoteDao.get(id);
  }
  
  public List<OsNote> getList(OsNote osNote)
  {
    return this.osNoteDao.getList(osNote);
  }
  
  public List<OsNote> getListPage(Map<String, Object> map)
  {
    return this.osNoteDao.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.osNoteDao.getRows(map);
  }
}
