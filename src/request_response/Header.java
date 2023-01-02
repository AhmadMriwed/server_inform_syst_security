package request_response;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class Header implements Serializable {
    int rec_id=0;
    String service="service";
    String uniqueID="uniqueID";
    PublicKey publicKey;
    byte[] secretKey;
    public Map<String,Object> toMap(){
        Map<String,Object> header=new HashMap<>();
        header.put("rec_id",rec_id);
        header.put("service",service);
        header.put("uniqueID",uniqueID);
        header.put("publicKey",publicKey);
        header.put("secretKey",secretKey);
        return header;
    }
    public Header fromMap(Map<String,Object> map){
        rec_id= (int) map.get("rec_id");
        service= (String) map.get("service");
        uniqueID= (String) map.get("uniqueID");
        publicKey= (PublicKey) map.get("publicKey");
        secretKey= (byte[]) map.get("secretKey");
        return  this;
    }

    public void setSecretKey(byte[] secretKey) {
        this.secretKey = secretKey;
    }

    public byte[] getSecretKey() {
        return secretKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getService() {
        return service;
    }

    public int getRec_id() {
        return rec_id;
    }

    public void setService(String service) {
        this.service = service;
    }
}
