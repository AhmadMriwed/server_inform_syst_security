package request_response;

import model.ClientModel;
import model.MessageModel;
import model.Model;
import model.NumberModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Msg {
   public Header header=new Header();
    public  Model body;
    public List<Model> bodyList=new ArrayList<>();
    public boolean status=false;
    public String message;
    public String mac;
    public byte[] salt;

    public Map<String,Object> toMap(){
        Map<String,Object> msg=new HashMap<>();
        msg.put("header",header.toMap());
        msg.put("status",status);
        msg.put("message",message);
        msg.put("mac",mac);
        msg.put("salt",salt);
        if(body!=null)
        msg.put("body",body.toMap());
        else
            msg.put("body",null);
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
        mac= (String) map.get("mac");
        salt= ( byte[]) map.get("salt");
        if(map.get("body")!=null){
            body  = fromBody(body,((Map<String, Object>) map.get("body")));
        }



        return  this;
    }
    Model fromBody(Model body,Map<String,Object> map){
        if (map.containsKey("password"))
            body=new ClientModel();
        else if(map.containsKey("message_id"))
            body=new MessageModel();
        else if(map.containsKey("number_id"))
            body=new NumberModel();
        body.FromMap(map);
        return body;
    }
}
