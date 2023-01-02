package security;

import request_response.Header;

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

public class Encryption {

   static Serializable serializable;
    static PublicKey publicKey;
    static SecretKey secretKey;
    public Encryption(Serializable serializable,PublicKey publicKey, SecretKey secretKey){
        this.serializable=serializable;
        this.publicKey=publicKey;
        this.secretKey=secretKey;
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

    private static SealedObject encryptData(Serializable serializable, Key secretKey, String cipherAlgorithm)
            throws IOException, GeneralSecurityException{
         Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        SealedObject sealedObject = new SealedObject( serializable, cipher);
        return sealedObject;
    }

    private static byte[] encryptKey(PublicKey key,Key secretKey, String cipherAlgorithm)
            throws IOException, GeneralSecurityException{
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(secretKey.getEncoded());


    }
    private static byte[] decryptKey(PrivateKey key,Key secretKey, String cipherAlgorithm)
            throws IOException, GeneralSecurityException{
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(secretKey.getEncoded());


    }
    private static SecretKeySpec getSecretKey(byte[] keyBytes, String algorithm) throws IOException {

        return new SecretKeySpec(keyBytes, algorithm);

    }
    public  Map<String, Object> run(Map<String,Object> map)
            throws IOException, GeneralSecurityException, Exception{

       // Encryption startEnc = new Encryption();

        Key secretKey1 =getSecretKey(secretKey,"AES");

        //TODO
        PublicKey publicKey1 = getPublic(publicKey,"RSA");
        byte[] encryptedKeybyte=encryptKey(publicKey1,secretKey1,"RSA");
        SealedObject sealedObject=encryptData(serializable,secretKey1,"AES");
        Header header=new Header().fromMap((Map<String, Object>) map.get("header"));
        header.setSecretKey(encryptedKeybyte);
        map.put("body",sealedObject);
        map.put("header", header.toMap());
        return map;

    }

}
