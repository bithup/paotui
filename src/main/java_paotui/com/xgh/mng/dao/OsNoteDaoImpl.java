package com.xgh.mng.dao;

import com.xgh.mng.dao.read.IOsNoteDaoR;
import com.xgh.mng.dao.write.IOsNoteDaoW;
import com.xgh.mng.entity.OsNote;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("osNoteDao")
public class OsNoteDaoImpl
  implements IOsNoteDao
{
  @Autowired
  protected IOsNoteDaoR osNoteDaoR;
  @Autowired
  protected IOsNoteDaoW osNoteDaoW;
  
  public int add(OsNote osNote)
  {
    return this.osNoteDaoW.add(osNote);
  }
  
  public int update(OsNote osNote)
  {
    return this.osNoteDaoW.update(osNote);
  }
  
  public int deleteById(long id)
  {
    return this.osNoteDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.osNoteDaoW.deleteByNid(nid);
  }
  
  public OsNote get(long id)
  {
    return this.osNoteDaoR.get(id);
  }
  
  public OsNote getByNid(long nid)
  {
    return this.osNoteDaoR.getByNid(nid);
  }
  
  public List<OsNote> getList(OsNote osNote)
  {
    return this.osNoteDaoR.getList(osNote);
  }
  
  public List<OsNote> getListPage(Map<String, Object> map)
  {
    return this.osNoteDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.osNoteDaoR.getRows(map);
  }
}
