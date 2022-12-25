package com;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args)throws IOException {
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



            ServerSocket ss = new ServerSocket(4999);
            Socket s = ss.accept();
            Socket sss = ss.accept();


            System.out.println("client connected");
            //InputStreamReader in = new InputStreamReader(s.getInputStream());
            // BufferedReader bf =  new BufferedReader(in);

            //String str = bf.readLine();
            //System.out.println("client :"+ str );

            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println("yes");
            pr.flush();


    }
}
