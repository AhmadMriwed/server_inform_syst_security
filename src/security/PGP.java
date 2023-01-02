package security;


import controller.Request;

import controller.ServiceType;

import request_response.Header;
import request_response.Msg;

import javax.crypto.*;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.UUID;

public class PGP {
    public PGP() throws NoSuchProviderException, NoSuchAlgorithmException, IOException, NoSuchPaddingException, ClassNotFoundException {
        GenerateKeys generateKeys = new GenerateKeys();
        generateKeys.createKeys();
        String uniqueID = UUID.randomUUID().toString();
        if(!Json.containsKey("uniqueID",Request.pathUniqueID)){
            Request.uniqueID=uniqueID;
            Json.put("uniqueID",uniqueID,Request.pathUniqueID);
        }else {
            Request.uniqueID= (String) Json.get("uniqueID",Request.pathUniqueID);
        }
        Request.privateKey = generateKeys.getPrivateKey();
        Request.publicKey = generateKeys.getPublicKey();
        Request.mapPublicKey.put(Request.uniqueID,Request.publicKey);
    }
    public static Map<String,Object> encrypt(Map<String,Object> map)  {

        Header header=new Header().fromMap((Map<String, Object>) map.get("header"));
        try {
            SecretKey secretKey = Symmetric.generateSymmetricKey();
            map =new Encryption((Serializable) map.get("body"),Request.mapPublicKey.get(header.getUniqueID()),secretKey).run(map);

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
        Header header=new Header().fromMap((Map<String, Object>) map.get("header"));
        if(!Request.mapPublicKey.containsKey(header.getUniqueID())){
            map.put("status",false);
            map.put("message","Disconnect");
            return map;
        }
        try {
            map=new Decryption((SealedObject) map.get("body"),header.getSecretKey()).run(map);
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
