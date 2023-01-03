package ca;

import com.ServerController;
import security.PGP;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class CA {
  public static   String sourceCa="www.lam_ca.com";
  public static PrivateKey privateKey;
  public static PublicKey publicKey;
  public static int portCA=5002;
  public static String host="localhost";
  public  static String uniqueID;
  public static String pathUniqueID="S:/Users/hp/IdeaProjects/sever/src/security/uniqueID.json";

  public static void main(String[] args) throws NoSuchAlgorithmException, ClassNotFoundException, NoSuchProviderException, NoSuchPaddingException, IOException {
    new PGP();
    CAController caController=new CAController();
    caController.start();

  }
}
