package request_response;

import model.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Header{
    int rec_id=0;
    String service="service";
    public Map<String,Object> toMap(){
        Map<String,Object> header=new HashMap<>();
        header.put("rec_id",rec_id);
        header.put("service",service);
        return header;
    }
    public Header fromMap(Map<String,Object> map){
        rec_id= (int) map.get("rec_id");
        service= (String) map.get("service");
        return  this;
    }

}
public class Msg {
   public Header header=new Header();
    public  Model body;
    public List<Model> bodyList=new ArrayList<>();
    public boolean status=false;
    public String message;

    public Map<String,Object> toMap(){
        Map<String,Object> msg=new HashMap<>();
        msg.put("header",header.toMap());
        msg.put("status",status);
        msg.put("message",message);
        if(body!=null)
        msg.put("body",body.toMap());
            List bodyMap=new ArrayList();
            for (Model model:
                 bodyList) {
                bodyMap.add(model.toMap());
            }
            msg.put("bodyList",bodyList);

     return msg;
    }
    public Msg fromMap(Map<String,Object> map){

        header.fromMap((Map<String, Object>) map.get("header"));
        status= (boolean) map.get("status");
        message= (String) map.get("message");
        body.FromMap((Map<String, Object>) map.get("body"));
        return  this;
    }
}
