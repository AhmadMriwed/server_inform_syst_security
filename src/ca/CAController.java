package ca;

import com.ServerHandler;
import controller.Request;

import java.net.ServerSocket;

public class CAController extends Thread{
    private static final int COUNT_CLIENT_SOCKET = 10;
    ServerSocket welcomeSocket;
    CAHandler[] clientList= new CAHandler[COUNT_CLIENT_SOCKET];
    @Override
    public void run() {
        try {
         welcomeSocket = new ServerSocket(Request.port);
        while (true){

            //initialize socket client
            for (int i=0;i<COUNT_CLIENT_SOCKET;i++) {
                //initialize serverHandler
                if(clientList[i]==null){
                    clientList[i]=new CAHandler(welcomeSocket);
                    clientList[i].start();
                }
//                if(!clientList[i].checkRun){
//                    clientList[i].checkRun=true;
//                    //System.out.println("["+welcomeSocket.getLocalPort()+"] server "+i+":"+clientList[i].getId());
//                }

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
