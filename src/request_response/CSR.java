package request_response;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class CSR implements Serializable {
    String name="name";
    String http="www.hello.com";
    PublicKey publicKey;
    String ca="ca";
    String sourceCa="test";
    public Map<String,Object> toMap(){
        Map<String,Object> csr=new HashMap<>();
        csr.put("name",name);
        csr.put("http",http);
        csr.put("ca",ca);
        csr.put("sourceCa",sourceCa);
        csr.put("publicKey",publicKey);
        return csr;
    }
    public Map<String,Object> toMapCSR(){
        Map<String,Object> csr=new HashMap<>();
        csr.put("name",name);
        csr.put("http",http);
        csr.put("publicKey",publicKey);
        return csr;
    }
    public CSR fromMap(Map<String,Object> map){
        name= (String) map.get("name");
        http= (String) map.get("http");
        ca= (String) map.get("ca");
        sourceCa= (String) map.get("sourceCa");
        publicKey= (PublicKey) map.get("publicKey");
        return  this;
    }
    public CSR fromMapCSR(Map<String,Object> map){
        name= (String) map.get("name");
        http= (String) map.get("http");
        publicKey= (PublicKey) map.get("publicKey");
        return  this;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getSourceCa() {
        return sourceCa;
    }

    public void setSourceCa(String sourceCa) {
        this.sourceCa = sourceCa;
    }
}
