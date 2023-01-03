//package SignedCertigicate;
//
//import cryptography.Asymmetric;
//import security.GenerateKeys;
//import security.Symmetric;
//
//import javax.xml.bind.DatatypeConverter;
//import java.net.ServerSocket;
//import java.security.KeyPair;
//import java.security.cert.CertificateEncodingException;
//import java.security.cert.X509Certificate;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class CAMultiThreading {
//
//    static KeyPair keyPair;
//    static ArrayList<X509Certificate> certs=new ArrayList<>();
//    static String issuer_DN="CN=CA.COM  O=., L=CA, ST=Damascus, C= SY";
//
//    public static void main(String[] args) throws Exception {
//        GenerateKeys generateKeys=new GenerateKeys();
//
//        keyPair= generateKeys.getKeyGen().generateKeyPair();
//        try (ServerSocket listener = new ServerSocket(22222)) {
//            System.out.print("The CA is running...");
//            ExecutorService pool = Executors.newFixedThreadPool(20);
//            while (true) {
//                pool.execute(new CertificateAuthority(listener.accept()));
//            }
//        }
//    }
//
//    public static boolean verifyCert(X509Certificate cert) throws CertificateEncodingException {
//        System.out.println("SIZE before: "+certs.size());
//        //update certs active
//        updateCerts();
//
//        System.out.println("SIZE AFTER: "+certs.size());
//        //search about cert
//        for (X509Certificate c:certs) {
//            if (DatatypeConverter.printHexBinary(c.getEncoded()).equals(DatatypeConverter.printHexBinary(cert.getEncoded()))){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private static void updateCerts(){
//        for (X509Certificate c:certs) {
//            if ((new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24).compareTo(c.getNotAfter())>0)
//                    && (new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24).compareTo(c.getNotAfter())<0)  )
//                certs.remove(c);
//        }
//    }
//
//}
