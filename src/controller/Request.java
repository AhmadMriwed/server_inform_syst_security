package controller;

import request_response.Msg;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class Request {
    public static int port=5000;
    public static int portChat=5001;
    public static String host="localhost";
    public  static PrivateKey privateKey;
    public  static PublicKey publicKey;
    public  static String securityType;
    public  static String uniqueID;
    public  static Map<String, PublicKey> mapPublicKey=new HashMap<>();
    public static String pathPvKey="S:/Users/hp/IdeaProjects/sever/src/security/PvKey.dat";
    public static String pathPbKey="S:/Users/hp/IdeaProjects/sever/src/security/PbKey.dat";
    public static String pathUniqueID="S:/Users/hp/IdeaProjects/sever/src/security/uniqueID.json";



}
