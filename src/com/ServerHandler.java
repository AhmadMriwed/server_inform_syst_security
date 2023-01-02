package com;

import controller.ControllerRequest;
import controller.Request;
import controller.ServiceType;
import database.Service;
import model.ClientModel;
import model.ResultModel;
import netscape.javascript.JSObject;
import request_response.Header;
import request_response.Msg;
import security.Security;

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
    ControllerRequest controllerRequest= new ControllerRequest();
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
                var object =inFromClient.readObject();
                Map<String, Object> map = (Map<String, Object>) object;
                // TODO here1
                map= Security.Decryption((String) map.get("securityType"),map);

                Msg msg = new Msg();
                if((boolean) map.get("status")){

                    msg.fromMap(map);
                    //TODO service
                    msg = controllerRequest.processService(msg);
                    map = msg.toMap();

                    //send
                    //TODO here2
                    map= Security.Encryption((String) map.get("securityType"),map);
                    Header header=new Header().fromMap((Map<String, Object>) map.get("header"));
                    header.setUniqueID(Request.uniqueID);
                    map.put("header",header.toMap());

                }



                outToClient.writeObject(map);
                msg = controllerRequest.afterProcessService(msg);

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
