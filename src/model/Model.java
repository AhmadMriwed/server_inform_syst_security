package model;

import java.io.Serializable;
import java.util.Map;

public interface Model extends Serializable {
    public  Model getModel();
    public Map<String,Object> toMap();
    public  Model FromMap(Map<String,Object> map);
}
