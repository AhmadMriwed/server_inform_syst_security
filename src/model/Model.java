package model;

import java.util.Map;

public interface Model {
    public  Model getModel();
    public Map<String,Object> toMap();
    public  Model FromMap(Map<String,Object> map);
}
