package security;


import controller.Request;
import request_response.Header;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class DigitalSignature {

    public static byte[] convertMapToByte(Map<String, Object> map) throws IOException {
// Convert Map to byte array
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(map);
        return byteOut.toByteArray();
    }
    public static byte[] convertListMapToByte(List<Map<String, Object>> listMap) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        for (Map map :listMap) {
            out.writeObject(map);
        }
        return byteOut.toByteArray();
    }
    private static String SignDoc(PrivateKey pvtKey, List<Map<String, Object>> listMap) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
     //   System.out.println(">>>>>>>> Start Add Signature  ");
        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withRSA");

        //Initialize the signature
        sign.initSign(pvtKey);

       // byte[] bytes = map.getBytes("UTF-8");
        byte[] bytes = convertListMapToByte(listMap);

        //Adding data to the signature
        sign.update(bytes);

        //Calculating the signature
        return Base64.getEncoder().encodeToString(sign.sign());
    }
    public static Map<String, Object> genSignature(Map<String, Object> map) {
      //  System.out.println(">>>>>>>> Start Add Signature  ");
        try {
            List arrayList=new ArrayList();
            arrayList.add(map.get("header"));
            arrayList.add(map.get("body"));
            map.put("signature",SignDoc(Request.privateKey,arrayList));
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


    private static boolean VerifySignature(PublicKey pubKey, String SignedData, List<Map<String, Object>> listMap) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
       // System.out.println(">>>>>>>> Start Verify Signature  ");
        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withRSA");

        //Initializing the signature
        sign.initVerify(pubKey);
        byte[] bytes = convertListMapToByte(listMap);
        //Update the data to be verified
        sign.update(bytes);

        //Verify the signature
        return sign.verify(Base64.getDecoder().decode(SignedData));
    }
    public static Map<String, Object> VerifySignature(Map<String, Object> map){
        //System.out.println(">>>>>>>> Start Add Signature  ");
        Header header=new Header().fromMap((Map<String, Object>) map.get("header"));
        if(!Request.mapPublicKey.containsKey(header.getUniqueID())){
            map.put("status",false);
            map.put("message","Disconnect");
            return map;
        }
        try {
            List arrayList=new ArrayList();
            arrayList.add(map.get("header"));
            arrayList.add(map.get("body"));

            boolean verifySignature= VerifySignature(Request.mapPublicKey.get(header.getUniqueID()), (String) map.get("signature"),arrayList);
            map.put("status",verifySignature);
            if(!verifySignature)
                map.put("message","Signature not correct");
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
