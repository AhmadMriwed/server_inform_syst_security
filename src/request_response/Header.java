package request_response;

import java.util.HashMap;
import java.util.Map;

public class Header{
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

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }

    public void setService(String service) {
        this.service = service;
    }
}
