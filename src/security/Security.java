package security;

import controller.SecurityType;

import javax.crypto.Mac;
import java.security.Key;
import java.util.Map;

public class Security {
    public static String MAC(Key key,Object data) throws Exception {

        Mac mac = Mac.getInstance("HMACSHA1");
        mac.init(key);
        mac.update(data.toString().getBytes());
        byte[] macResult = mac.doFinal();

          // System.out.println(new String(macResult));
        return new String(macResult);
    }
    public static String getKeyClient(int number,String password){
        try {
            String keyClient = Symmetric.generateStorngPasswordHash(password);
          Json.put(number,keyClient);

            return keyClient;
        }catch (  Exception e){

        }
      return null;

    }
    public static Map<String,Object> Encryption(String securityType,Map<String,Object> map){
        if((boolean)map.get("status")) return map;
        if(securityType==null)
            securityType=SecurityType.Zero;
        map.put("securityType",securityType);
        switch (securityType){
            case SecurityType.SymmetricCBC:
                return CBC.encrypt(map);
            case SecurityType.SymmetricGCM:
                return GCM.encrypt(map);
            case SecurityType.PGP:
                return  PGP.encrypt(map);
            case SecurityType.DigitalSignature:
                return DigitalSignature.genSignature(map);
            case SecurityType.PGP_DigitSign:
                return Encryption(SecurityType.PGP_DigitSign,Encryption(SecurityType.PGP,map));
            case SecurityType.AllSecurity:
                break;
            default:
                return map;
        }

        return map;
    }
    public static Map<String,Object> Decryption(String securityType,Map<String,Object> map){
        if((boolean)map.get("status")) return map;
        switch (securityType){
            case SecurityType.SymmetricCBC:
                return CBC.decrypt(map);
            case SecurityType.SymmetricGCM:
                return GCM.decrypt(map);
            case SecurityType.PGP:
                return  PGP.decrypt(map);
            case SecurityType.DigitalSignature:
                return DigitalSignature.VerifySignature(map);
            case SecurityType.PGP_DigitSign:
                return Decryption(SecurityType.PGP_DigitSign,Decryption(SecurityType.PGP,map));
            case SecurityType.AllSecurity:
                return map;
            default:
                return map;
        }
        //  return map;
    }

}
