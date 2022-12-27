package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ClientModel implements Model{
    int number;
    String password;
    String name="";
    public ClientModel(int number, String password, String name){
        this.name=name;
        this.number=number;
        this.password=password;
    }
    public ClientModel(){
        this.name="";
        this.number=0;
        this.password="";
    }

    @Override
    public Model getModel() {
        return this;
    }


    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> map=new HashMap<>();
        map.put("number",number);
        map.put("password","");
        map.put("name",name);
        return map;
    }

    @Override
    public  Model FromMap(Map<String,Object> map) {
        this.name= (String) map.get("name");
        this.password= (String) map.get("password");
        this.number= (int) map.get("number");
        return this;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
