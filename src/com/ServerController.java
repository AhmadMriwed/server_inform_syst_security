package com;

import controller.Request;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerController extends Thread{
    private static final int COUNT_CLIENT_SOCKET = 10;
    private static final int COUNT_CLIENT_CHAT_SOCKET = 10;
    ServerSocket welcomeSocket;
    ServerSocket chatSocket;
    ServerHandler[] clientList= new ServerHandler[COUNT_CLIENT_SOCKET];
    ServerHandler[] clientChatList= new ServerHandler[COUNT_CLIENT_CHAT_SOCKET];
    @Override
    public void run() {
        try {
         welcomeSocket = new ServerSocket(Request.port);
         chatSocket = new ServerSocket(Request.portChat);
        while (true){

            //initialize socket client
            for (int i=0;i<COUNT_CLIENT_SOCKET;i++) {
                //initialize serverHandler
                if(clientList[i]==null){
                    clientList[i]=new ServerHandler(welcomeSocket);
                    clientList[i].start();
                }
                if(!clientList[i].checkRun){
                    clientList[i].checkRun=true;
                    //System.out.println("["+welcomeSocket.getLocalPort()+"] server "+i+":"+clientList[i].getId());
                }

            }
           // initialize socket client chat
            for (int j=0;j<COUNT_CLIENT_CHAT_SOCKET;j++) {
                //initialize serverHandler
                if(clientChatList[j]==null){
                    clientChatList[j]=new ServerHandler(chatSocket);
                    clientChatList[j].start();
                }
                if(!clientChatList[j].checkRun){
                    clientChatList[j].checkRun=true;
                   // System.out.println("["+chatSocket.getLocalPort()+"] server "+j+":"+clientChatList[j].getId());
                }
            }
            Thread.sleep(1000);
           // Thread.sleep(5000);
        }
        } catch (Exception e) {
            System.err.println("Server Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.println("Stack Trace: " + e.getStackTrace());
            System.err.println("To String: " + e.toString());
        }

    }
}
