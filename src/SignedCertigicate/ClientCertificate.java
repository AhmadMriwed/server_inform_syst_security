package SignedCertigicate;

import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

//شهادة العميل
public class ClientCertificate  {
//يتم تحديد تنسيق هذه الشهادات بواسطة معيار X.509 أو معيار EMV.
    X509Certificate certificate;
    //authorities declare as <name of file , (r,w,rw)>
    Map<String,String> authorities=new HashMap<>();

    public X509Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }

    public Map<String, String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Map<String, String> authorities) {
        this.authorities = authorities;
    }

    public boolean Authorized(String fileName,String auth){
        String s=authorities.get(fileName);
        return s.contains(auth);
    }
}
