///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package SignedCertigicate;
//
//
//import sun.security.x509.X500Name;
//
//import javax.security.auth.x500.X500Principal;
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.security.*;
//
///**
// *
// * @author User
// */
//
//
//
//
//public class CSRGenerator {
//    private static PublicKey publicKey = null;
//    private static PrivateKey privateKey = null;
//    private static KeyPairGenerator keyGen = null;
//    private static CSRGenerator gcsr = null;
//
//    public CSRGenerator() { // 1:Get instance of KeyPairGenerator using standard encryption algorithm. I am using RSA here.
//        try {
//            keyGen = KeyPairGenerator.getInstance("RSA");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        keyGen.initialize(2048, new SecureRandom());//2:Initialize the instance by providing keysize and source of randomness.
//        KeyPair keypair = keyGen.generateKeyPair();
//        publicKey = keypair.getPublic();
//        privateKey = keypair.getPrivate();//3:Generate the PrivateKey and PublicKey that will be used in generating CSR.
//    }
//
//    public static CSRGenerator getInstance() {
//        if (gcsr == null)
//            gcsr = new CSRGenerator();
//        return gcsr;
//    }
//
//    public String getCSR(String cn) throws Exception {
//        byte[] csr = generatePKCS10(cn, "Java", "JournalDev", "Cupertino",
//                "California", "USA");
//        return new String(csr);
//    }
//
//    /**
//     *
//     * @param CN
//     *            Common Name, is X.509 speak for the name that distinguishes
//     *            the Certificate best, and ties it to your Organization
//     * @param OU
//     *            Organizational unit
//     * @param O
//     *            Organization NAME
//     * @param L
//     *            Location
//     * @param S
//     *            State
//     * @param C
//     *            Country
//     * @return
//     * @throws Exception
//     */
//    private static byte[] generatePKCS10(String CN, String OU, String O,
//            String L, String S, String C) throws Exception {
//        // generate PKCS10 certificate request
//        String sigAlg = "MD5WithRSA";//5:Get instance of Signature using standard algorithm. I am using MD5WithRSA in my case.
//        PKCS1 pkcs10 = new PKCS10(publicKey);//4:Initialize PKCS10 using the PublicKey.
//        Signature signature = Signature.getInstance(sigAlg);
//        signature.initSign(privateKey);//6:Initialize the signature object using the PrivateKey.
//        // common, orgUnit, org, locality, state, country
//        X500Principal principal = new X500Principal( "CN=Ole Nordmann, OU=ACME, O=Sales, C=NO");
////7:Create X500Name object by passing Common Name, Organization Unit, Organization, Location, State and Country
//   //     pkcs10CertificationRequest kpGen = new PKCS10CertificationRequest(sigAlg, principal, publicKey, null, privateKey);
//     //   byte[] c = kpGen.getEncoded();
//        X500Name x500name=null;
//        x500name= new X500Name(principal.getEncoded());//8:Encode and Sign the PKCS10 object using X500Signer, Signature and X500Name object
//        pkcs10.encodeAndSign(x500name, signature);
//        ByteArrayOutputStream bs = new ByteArrayOutputStream();
//        PrintStream ps = new PrintStream(bs);
//        pkcs10.print(ps);
//        byte[] c = bs.toByteArray();
//        try {
//            if (ps != null)
//                ps.close();
//            if (bs != null)
//                bs.close();
//        } catch (Throwable th) {
//        }
//        return c;
//    }
//
//    public PublicKey getPublicKey() {
//        return publicKey;
//    }
//
//    public PrivateKey getPrivateKey() {
//        return privateKey;
//    }
//
//    public static void main(String[] args) throws Exception {
//        CSRGenerator gcsr = CSRGenerator.getInstance();
//
//        System.out.println("Public Key:\n"+gcsr.getPublicKey().toString());
//
//        System.out.println("Private Key:\n"+gcsr.getPrivateKey().toString());
//        String csr = gcsr.getCSR("journaldev.com <https://www.journaldev.com>");
//        System.out.println("CSR Request Generated!!");
//        System.out.println(csr);
//    }
//
//}
//
//
