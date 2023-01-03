package ca;

import controller.Request;
import request_response.CSR;
import request_response.Header;
import request_response.Msg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class CertificateSignature {

    public static byte[] convertMapToByte(Map<String, Object> map) throws IOException {
// Convert Map to byte array
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(map);
        return byteOut.toByteArray();
    }

    private static String CertificateSignatureDoc(PrivateKey pvtKey, Map<String, Object> csr) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
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
    public static Map<String, Object> genCertificateSignature(Map<String, Object> map) {
        Msg msg=new Msg().fromMap(map);
        try {

            msg.csr.setCa(CertificateSignatureDoc(CA.privateKey,msg.csr.toMapCSR()));
            msg.csr.setSourceCa(CA.sourceCa);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        return msg.toMap();
    }


    private static boolean VerifyCertificateSignature1(Map<String, Object> csr) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        // System.out.println(">>>>>>>> Start Verify Signature  ");
        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withRSA");

        //Initializing the signature
        sign.initVerify(CA.publicKey);
        CSR csr1=new CSR().fromMap(csr);
        byte[] bytes = convertMapToByte(csr1.toMapCSR());
        //Update the data to be verified
        sign.update(bytes);

        //Verify the signature
        return sign.verify(Base64.getDecoder().decode(csr1.getCa()));
    }
    public static Map<String, Object> VerifyCertificateSignature(Map<String, Object> map){
        try {
            boolean verifySignature= VerifyCertificateSignature1((Map<String, Object>) map.get("csr"));
            map.put("status",verifySignature);
            if(!verifySignature)
                map.put("message","Certificate Signature not correct");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        return map;
    }
}
