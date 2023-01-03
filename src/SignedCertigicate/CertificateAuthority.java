//package SignedCertigicate;
//
//import DigitalSignature.DigitalSignature;
//import Server.PasswordManagerServer;
//import com.google.gson.Gson;
//import cryptography.Asymmetric;
//import org.bouncycastle.jce.X509Principal;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.openssl.PEMParser;
//import org.bouncycastle.pkcs.PKCS10CertificationRequest;
//import org.bouncycastle.x509.X509V3CertificateGenerator;
//import security.DigitalSignature;
//
//import javax.xml.bind.DatatypeConverter;
//import java.io.*;
//import java.math.BigInteger;
//import java.net.Socket;
//import java.security.*;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.*;
//
//////في علم التشفير ، يعتبر المرجع المصدق أو المرجع المصدق (CA) كيانًا يصدر الشهادات الرقمية.
//public class CertificateAuthority implements Runnable{
//    private Socket socket;
//    public String VerifyingString="Hey this is a verifying string";
//    public static final String CREATE="create";
//    public static final String VERIFY="verify";
//
//    CertificateAuthority(Socket socket){
//        this.socket=socket;
//    }
//
//    @Override
//    public void run() {
//        System.out.println("Connected: " + socket);
//
//        try {
//            Scanner in = new Scanner(socket.getInputStream());
//            String requestType = in.nextLine();
//            System.out.println("request Type : "+requestType);
//            if (requestType.equals(CREATE)) {
//                //create Request
//                createCertificateRequest();
//            }else if (requestType.equals(VERIFY)) {
//                //verify request
//                verifyCertificateRequest(in);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    private void verifyCertificateRequest(Scanner in) throws Exception{
//        System.out.println("VERIFY MODE ..");
//        //receive public key
//        //Scanner in = new Scanner(socket.getInputStream());
//        String strPK = in.nextLine();
//        System.out.println("recieved public key..");
//
//        byte[] buffer= DatatypeConverter.parseHexBinary(strPK);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
//        PublicKey senderPK = keyFactory.generatePublic(keySpec);
//
//        System.out.println("recieved public key..");
//
//        //receive cert
//        String strCert = in.nextLine();
//        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
//        byte[] cert = DatatypeConverter.parseHexBinary(strCert);
//        X509Certificate receivedCert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(cert));
//
//        System.out.println("recieved cert");
//
//        //verify cert
//        byte[] response;
//        if (CAMultiThreading.verifyCert(receivedCert)){
//            response= Asymmetric.Encrept("true",senderPK);
//        }else {
//            response= Asymmetric.Encrept("false",senderPK);
//        }
//
//        //send result
//        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//        out.println(DatatypeConverter.printHexBinary(response));
//
//    }
//
//    private void createCertificateRequest() throws Exception {
//        //send Public Key   1
//        try {
//
//
//        KeyPair keypair=CAMultiThreading.keyPair;
//        PublicKey publicKey=keypair.getPublic();
//        System.out.println("sending server public key ..");
////        System.out.println("sending server public key .." +
////                "\n "+DatatypeConverter.printHexBinary(publicKey.getEncoded()));
//        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//        out.println(DatatypeConverter.printHexBinary(publicKey.getEncoded()));
//        System.out.println("receive CSR from server ..");
//        //receive CSR from server    4
//        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
////        Scanner in=new Scanner(socket.getInputStream());
//        String csr="";
//
//        ArrayList verifing=null;
//        verifing=(ArrayList) ois.readObject();
//
//        String sign=verifing.get(0).toString();
//        String message=verifing.get(1).toString();
//        csr=verifing.get(2).toString();
//
//        System.out.println("csr received str : "+csr);
//
//        PKCS10CertificationRequest csrr=getPKCS10CertificationRequest(csr);
//        //get the public key received in CSR
//
//
//
//
//        PublicKey serverPublicKey=getPublicKeyFromCsr(csr);
//
////decrypt response
//        byte[] byteresponse=DatatypeConverter.parseHexBinary(message);
//        String response1 = Asymmetric.Decrept(byteresponse, keypair.getPrivate());
//        System.out.println("decrypted message " + response1);
////        String str[]=getResponseSeparateSign(verifyingMessage);
////        System.out.println("separate message" +str[0]+
////                "\n signing "+str[1]);
//
//        String resp="";
//
//        ////check server identity by verify signing
//        if (DigitalSignature.Verify_Digital_Signature(
//                response1.getBytes(),
//                DatatypeConverter.parseHexBinary(sign), serverPublicKey)) {
//            resp = customResponse(false, "", "error .. can not be verified MITM failed ");
//            // out.println(response);
//        }
//        else {
//            //create certificate
//            X509Certificate certificate=createCertificate(serverPublicKey,csrr.getSubject().toString(),CAMultiThreading.issuer_DN);
//            resp=customResponse(true,DatatypeConverter.printHexBinary(certificate.getEncoded()),"");
//        }
//        String response= PasswordManagerServer.ReadResponse(resp);
//        System.out.println("response   "+resp+"size  :  "+response.length());
//        PrivateKey privateKey=keypair.getPrivate();
//        //sign response
//        //generate signature
//        byte[] digitSign = DigitalSignature.Create_Digital_Signature(resp.getBytes(), privateKey);
//        String signResponse = DatatypeConverter.printHexBinary(digitSign);
//        //encrypt request
////        byte[] cypherRequest = Asymmetric.Encrept(response, serverPublicKey);
////        String cypherResponse = DatatypeConverter.printHexBinary(cypherRequest);
//        ObjectOutputStream oo = new ObjectOutputStream(socket.getOutputStream());
//
//        ArrayList<String> res = new ArrayList<>();
//        res.add(signResponse);
//        res.add(resp);
//    System.out.println("cypherResponse: "+resp+"              nh    " );
//
//        //send signing response
//        oo.writeObject(res);
//
//
//         }   catch (Exception e){
//              System.out.println("EOF " );
//        }
//    }
//
//    private PKCS10CertificationRequest getPKCS10CertificationRequest(String csrReceived) throws UnsupportedEncodingException {
//          Security.addProvider(new BouncyCastleProvider());
//       // Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//        PKCS10CertificationRequest csr = null;
//        ByteArrayInputStream pemStream = null;
//        try {
//            pemStream = new ByteArrayInputStream(csrReceived.getBytes("UTF-8"));
//        } catch (UnsupportedEncodingException ex) {
//            System.out.println("UnsupportedEncodingException, convertPemToPublicKey "+ ex);
//        }
//
//        Reader pemReader = new BufferedReader(new InputStreamReader(pemStream));
//        PEMParser pemParser = new PEMParser(pemReader);
//
//        try {
//            Object parsedObj = pemParser.readObject();
//
////            System.out.println("PemParser returned: " + parsedObj);
//
//            if (parsedObj instanceof PKCS10CertificationRequest) {
//                csr = (PKCS10CertificationRequest) parsedObj;
//
//            }
//        } catch (IOException ex) {
//            System.out.println("IOException, convertPemToPublicKey "+ ex);
//        }
//
//        return csr;
//    }
//
//    private PublicKey getPublicKeyFromCsr(String csrReceived) throws Exception {
//        System.out.println("csr received : "+csrReceived);
//        PKCS10CertificationRequest csr=getPKCS10CertificationRequest(csrReceived);
//        String publicKeyStr=DatatypeConverter.printHexBinary(csr.getSubjectPublicKeyInfo().getEncoded());
//
//        byte[] buffer= DatatypeConverter.parseHexBinary(publicKeyStr);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
//        PublicKey publicKey = keyFactory.generatePublic(keySpec);
//
//        return publicKey;
//    }
//
//    X509Certificate createCertificate(PublicKey subjectPublicKey,String issuerDN,String subjectDN) throws Exception{
//        X509Certificate cert = null;
//
//        // GENERATE THE X509 CERTIFICATE
//        X509V3CertificateGenerator v3CertGen =  new X509V3CertificateGenerator();
//        v3CertGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
//        v3CertGen.setIssuerDN(new X509Principal(issuerDN));
//        v3CertGen.setNotBefore(new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24));
//        v3CertGen.setNotAfter(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 365*10)));
//        v3CertGen.setSubjectDN(new X509Principal(subjectDN));
//        v3CertGen.setPublicKey(subjectPublicKey);
//        v3CertGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
//        cert = v3CertGen.generateX509Certificate(CAMultiThreading.keyPair.getPrivate());
//        //saveCert(cert,CAMultiThreading.keyPair.getPrivate());
//        CAMultiThreading.certs.add(cert);
//
////        String strCert=DatatypeConverter.printHexBinary(cert.getEncoded());
////        System.out.println("CERTTT "+ strCert);
////
////        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
////        byte[] in = DatatypeConverter.parseHexBinary(strCert);
////        X509Certificate reverseCert = (X509Certificate)certFactory.generateCertificate(new ByteArrayInputStream(in));
////        System.out.println("reverseCert "+ DatatypeConverter.printHexBinary(reverseCert.getEncoded()));
////
//        return cert;
//    }
//
//
//
//    private String customResponse(boolean status, String data, String error) {
//        Map<String, String> reponse = new HashMap<>();
//        reponse.put("status", status?"true":"false");
//        reponse.put("data", data);
//        reponse.put("error", error);
//
//        return this.toJsonString(reponse);
//    }
//
//    private String toJsonString(Map<String, String> data){
//        Gson gson = new Gson();
//        String output = gson.toJson(data);
//        return output;
//    }
//
//}
