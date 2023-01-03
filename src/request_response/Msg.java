package request_response;

import controller.SecurityType;
import model.ClientModel;
import model.MessageModel;
import model.Model;
import model.NumberModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Msg implements Serializable {
   public Header header=new Header();
   public CSR csr=new CSR();
    public  Model body;
    public List<Model> bodyList=new ArrayList<>();
    public boolean status=false;
    public String message;
    public String mac;
    public String signature;
    public String securityType= SecurityType.Zero;
    public byte[] salt;
    public byte[] iv;

    public Map<String,Object> toMap(){
        Map<String,Object> msg=new HashMap<>();
        msg.put("header",header.toMap());
        msg.put("csr",csr.toMap());
        msg.put("status",status);
        msg.put("message",message);
        msg.put("signature",signature);
        msg.put("mac",mac);
            msg.put("salt",salt);


        msg.put("iv",iv);
        msg.put("securityType",securityType);

        if(body!=null)
        msg.put("body",body.toMap());
        else msg.put("body",null);
            List bodyMap=new ArrayList();
            for (Model model:
                 bodyList) {
                bodyMap.add(model.toMap());
            }
            msg.put("bodyList",bodyMap);

     return msg;
    }
    public Msg fromMap(Map<String,Object> map){
        header.fromMap((Map<String, Object>) map.get("header"));
        csr.fromMap((Map<String, Object>) map.get("csr"));
        status= (boolean) map.get("status");
        message= (String) map.get("message");
        mac= (String) map.get("mac");

            salt= ( byte[]) map.get("salt");

        iv= (byte[]) map.get("iv");
        securityType= ( String) map.get("securityType");
        signature= ( String) map.get("signature");

        if(map.get("body")!=null){
            body  = fromBody(((Map<String, Object>) map.get("body")));
        };
        bodyList.clear();
        for (var tempMap:(List) map.get("bodyList")
        ) {
            bodyList.add(fromBody((Map<String, Object>) tempMap));
        }
        return  this;
    }
    Model fromBody(Map<String,Object> map){
        Model body=new ClientModel();
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
