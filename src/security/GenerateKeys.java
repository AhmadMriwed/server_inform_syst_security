package security;

import java.security.*;

public class GenerateKeys {
    static  private int KEY_LENGTH=1024;
    private  KeyPairGenerator keyGen;
    private  KeyPair pair;
    private   PrivateKey privateKey;
    private   PublicKey publicKey;

    public  GenerateKeys()
            throws NoSuchAlgorithmException, NoSuchProviderException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(KEY_LENGTH);
    }

    public void createKeys() {
        this.pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();

    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
