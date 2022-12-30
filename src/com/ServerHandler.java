package com;

import controller.ControllerRequest;
import controller.ServiceType;
import database.Service;
import model.ClientModel;
import model.ResultModel;
import request_response.Msg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class ServerHandler extends Thread{
    ServerSocket server = null;
    boolean checkRun = false;
    Socket client;
    ServerHandler(ServerSocket server){
        this.server=server;
    }
    ServerHandler(){
    }

    @Override
    public void run() {
        socket();
    }

    private void socket(){
        if(server==null)return;
        checkRun=true;
        while (true) {
            if(!checkRun) continue;
            try {
                client = server.accept();
                ObjectOutputStream outToClient = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream inFromClient = new ObjectInputStream(client.getInputStream());
                //rec


                Map<String, Object> map = (Map<String, Object>) inFromClient.readObject();
                // TODO TDO
                Msg msg = new Msg();
                msg.fromMap(map);
                //TODO service
                msg = ControllerRequest.processService(msg);

                //send
                map = msg.toMap();
                outToClient.writeObject(map);
                msg = ControllerRequest.afterProcessService(msg);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (client != null)
                    try {
                        client.close();
                      //  this.checkRun = false;

                    } catch (IOException  e) {
                        e.printStackTrace();
                    }
            }
        }


    }


}
