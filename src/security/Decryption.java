package security;


import controller.Request;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.Serializable;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

public class Decryption {

    static SealedObject sealedObject;
    static byte[] encryptedKeybyte;
    public Decryption(SealedObject sealedObject,byte[] encryptedKeybyte){
        this.sealedObject=sealedObject;
        this.encryptedKeybyte=encryptedKeybyte;
    }
    private static PrivateKey getPrivate(Key key, String algorithm) throws Exception {

        byte[] keyBytes =key.getEncoded();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        return kf.generatePrivate(spec);
    }

    private static PublicKey getPublic(Key key, String algorithm) throws Exception {

        byte[] keyBytes = key.getEncoded();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        return kf.generatePublic(spec);

    }

    private static SecretKeySpec getSecretKey(Key key, String algorithm) throws IOException {

        byte[] keyBytes = key.getEncoded();
        return new SecretKeySpec(keyBytes, algorithm);

    }
    private static SecretKeySpec getSecretKey(byte[] keyBytes, String algorithm) throws IOException {

        return new SecretKeySpec(keyBytes, algorithm);

    }

    private static Serializable decryptData(SealedObject sealedObject, Key secretKey, String cipherAlgorithm)
            throws IOException, GeneralSecurityException, ClassNotFoundException {
         Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        Serializable unsealObject = (Serializable) sealedObject.getObject(cipher);
        return unsealObject;
    }

    private static byte[] decryptKey(PrivateKey key,Key secretKey, String cipherAlgorithm)
            throws IOException, GeneralSecurityException{
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(secretKey.getEncoded());


    }

    public  Map<String,Object> run(Map<String,Object> map)
            throws IOException, GeneralSecurityException, Exception{

        SecretKey encryptedKey =getSecretKey(encryptedKeybyte,"AES");
        //TODO
        PrivateKey privateKey = getPrivate(Request.privateKey,"RSA");
        SecretKey secretKey=getSecretKey(decryptKey(privateKey,encryptedKey,"RSA"),"AES");
        Serializable serializable=decryptData(sealedObject,secretKey,"AES");
        map.put("body",serializable);
        return map;
    }

}
