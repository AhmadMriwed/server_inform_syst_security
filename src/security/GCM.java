package security;

import request_response.Header;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class GCM {
    public static Map<String,Object> encrypt(Map<String,Object> map)  {
        try {
            byte[] salt = Symmetric.getSalt();
            Header header=new Header().fromMap((Map<String, Object>) map.get("header"));
            SecretKey key = Symmetric.getKeyFromPassword((String) Json.get(header.getRec_id()), salt);
            IvParameterSpec ivParameterSpec = Symmetric.generateIv();
            String algorithm = "AES/GCM/PKCS5Padding";
            SealedObject sealedObject1 = Symmetric.encryptGCM(
                    algorithm, (Serializable) map.get("body"), key, ivParameterSpec);
            map.put("body",sealedObject1);
            map.put("salt", salt);
            map.put("iv", ivParameterSpec.getIV());
            map.put("mac", Security.MAC(key, map.get("body")));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    public static Map<String,Object> decrypt(Map<String,Object> map)  {
        try {

            byte[] salt = (byte[]) map.get("salt");
           //  salt =  Symmetric.getSalt();
            Header header=new Header().fromMap((Map<String, Object>) map.get("header"));
            SecretKey key = Symmetric.getKeyFromPassword((String) Json.get(header.getRec_id()), salt);
            String mac=Security.MAC(key, map.get("body"));
            if(!mac.contains((CharSequence) map.get("mac"))){
                map.put("status",true);
               // return map;
            }
            IvParameterSpec ivParameterSpec =new IvParameterSpec((byte[]) map.get("iv"));
            String algorithm = "AES/GCM/PKCS5Padding";
            Map<String,Object> object = (Map<String, Object>) Symmetric.decryptGCM(
                    algorithm, (SealedObject) map.get("body"), key, ivParameterSpec);
            map.put("body",object);
            return map;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
