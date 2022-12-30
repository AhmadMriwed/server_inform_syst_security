package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NumberModel implements Model, Serializable {
    long number_id;
    int client_number;
    int number;

    public NumberModel(int number_id, int client_number, int number){
        this.number_id=number_id;
        this.number=number;
        this.client_number=client_number;
    }
    public NumberModel( int client_number, int number){
        this.number_id=0;
        this.number=number;
        this.client_number=client_number;
    }
    public NumberModel(){
        this.number_id=0;
        this.number=0;
        this.client_number=0;
    }

    @Override
    public Model getModel() {
        return this;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> map=new HashMap<>();
        map.put("number_id",number_id);
        map.put("number",number);
        map.put("client_number",client_number);
        return map;
    }

    @Override
    public  Model FromMap(Map<String,Object> map) {
        this.client_number= (int) map.get("client_number");
        this.number= (int) map.get("number");
        this.number_id= (long) map.get("number_id");
        return this;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public long getNumber_id() {
        return number_id;
    }

    public void setNumber_id(long number_id) {
        this.number_id = number_id;
    }

    public int getClient_number() {
        return client_number;
    }

    public void setClient_number(int client_number) {
        this.client_number = client_number;
    }
}
