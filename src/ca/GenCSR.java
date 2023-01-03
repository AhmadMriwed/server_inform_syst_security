package ca;

import controller.Request;
import request_response.CSR;
import request_response.Msg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;
import java.util.Base64;
import java.util.Map;

public class GenCSR {
    private static String createCSr(PrivateKey pvtKey, Map<String, Object> csr) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withRSA");

        //Initialize the signature
        sign.initSign(pvtKey);

        // byte[] bytes = map.getBytes("UTF-8");
        byte[] bytes = convertMapToByte( csr);

        //Adding data to the signature
        sign.update(bytes);

        //Calculating the signature
        return Base64.getEncoder().encodeToString(sign.sign());
    }
    public static CSR createCSr() {
        CSR csr=new CSR();
            csr.setName(Request.name);
            csr.setHttp(Request.http);
            csr.setPublicKey(Request.publicKey);
        return csr;
    }
    public static Msg sendCertificateSignature(Msg msg){

    }
}
