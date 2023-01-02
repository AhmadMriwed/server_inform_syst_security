package com;
import database.Service;
import model.ClientModel;
import model.NumberModel;
import request_response.Msg;
import security.PGP;
import security.SelfSignedCertificateGeneration;

import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
class temp extends  Thread{
    @Override
    public void run() {
        try {
            ServerSocket welcomeSocket = new ServerSocket(5000);

            while (true) {
                // Create the Client Socket
                Socket clientSocket = welcomeSocket.accept();
                System.out.println("Socket Extablished...");
                // Create input and output streams to client
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());

                // Read modify
                // TODO here
                Msg msg= new Msg();
                msg.status=true;
                msg.message="message";
               // msg.body=new ClientModel();
                msg.fromMap((Map<String, Object>) inFromClient.readObject());
                System.out.println(msg.toMap());
                outToClient.writeObject(msg.toMap());
                System.out.println("ok");
//                /* Modify the message object */
//                inMsg.setMessage(inMsg.getMessage().toUpperCase());
//                inMsg.setIndex(5);
//                inMsg.setAverage(10.5f);
//
//                /* Send the modified Message object back */
//                outToClient.writeObject(inMsg);

            }

        } catch (Exception e) {
            System.err.println("Server Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.println("Stack Trace: " + e.getStackTrace());
            System.err.println("To String: " + e.toString());
        }

    }
}
public class Server {
    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, ClassNotFoundException {
//        ServerSocket ss = new ServerSocket(4999);
//
//
//        while (true){
//            Socket s = ss.accept();
//
//            System.out.println("client connected");
//            InputStreamReader in = new InputStreamReader(s.getInputStream());
//            BufferedReader bf =  new BufferedReader(in);
//
//            String str = bf.readLine();
//            System.out.println("client :"+ str );
//
//            PrintWriter pr = new PrintWriter(s.getOutputStream());
//            pr.println("yes");
//            pr.flush();
//        }


//
//            ServerSocket ss = new ServerSocket(4999);
//            Socket s = ss.accept();
//
//
//
//            System.out.println("client connected");
//
//            PrintWriter pr = new PrintWriter(s.getOutputStream());
//
//            pr.println("yes");
//            pr.flush();

        //temp t=new temp();

       // new SelfSignedCertificateGeneration().selfSignedCertificateGeneration();
      //  new SelfSignedCertificateGeneration().selfSignedCertificateGeneration();
       // ServerSocket welcomeSocket = new ServerSocket(5000);
       // ServerHandler serverHandler=new ServerHandler(welcomeSocket);
     //   System.out.println( Service.addNumberToClient(new NumberModel(2,1)).getMessage());
        new PGP();
        ServerController serverController=new ServerController();
        serverController.start();


       // serverHandler.run();
//        while (true)
//            if (serverHandler.server==null){
//                //System.out.println("start server");
//                serverHandler.server=welcomeSocket;
//                serverHandler.run();
//            }

    }
}
