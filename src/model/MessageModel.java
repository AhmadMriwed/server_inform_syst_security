package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MessageModel implements Model{
    public Long message_id;
    public String text;
    public int send_id;
    public int rec_id;
    public Date date=new Date(System.currentTimeMillis());
    public boolean checkSend=false;
    public boolean checkRec=false;


    public MessageModel(Long message_id,String text, int send_id, int rec_id,Date date, boolean checkSend,boolean checkRec){
        this.message_id=message_id;
        this.text=text;
        this.send_id=send_id;
        this.rec_id=rec_id;
        this.date=date;
        this.checkRec=checkRec;
        this.checkSend=checkSend;
    }
    public MessageModel(Long message_id,String text, int send_id, int rec_id, boolean checkSend,boolean checkRec){
        this.message_id=message_id;
        this.text=text;
        this.send_id=send_id;
        this.rec_id=rec_id;
        this.checkRec=checkRec;
        this.checkSend=checkSend;
    }
    public MessageModel(String text, int send_id, int rec_id){
        this.text=text;
        this.send_id=send_id;
        this.rec_id=rec_id;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> map=new HashMap<>();
        map.put("text",text);
        map.put("rec_id",rec_id);
        map.put("send_id",send_id);
        map.put("date",date);
        map.put("checkSend",checkSend);
        map.put("checkRec",checkRec);
        return map;
    }

    @Override
    public  Model FromMap(Map<String,Object> map) {
        this.message_id= (Long) map.get("message_id");
        this.text= (String) map.get("text");
        this.send_id= (int) map.get("send_id");
        this.rec_id= (int) map.get("rec_id");
        this.checkSend= (boolean) map.get("checkSend");
        this.checkRec= (boolean) map.get("checkRec");
        return this;
    }
    @Override
    public Model getModel() {
        return this;
    }
}
