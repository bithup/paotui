package com.xgh.paotui.dao.read;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public interface ISmsDaoR {

    public Map<String,Object> smsDock(Map<String, Object> map);

}