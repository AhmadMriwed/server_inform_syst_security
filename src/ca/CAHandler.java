package ca;

import controller.ControllerRequest;
import controller.Request;
import request_response.Header;
import request_response.Msg;
import security.Security;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class CAHandler extends Thread{
    ServerSocket caServer = null;
    Socket client;
    ControllerCA controllerCA= new ControllerCA();
    CAHandler(ServerSocket server){
        this.caServer=server;
    }
    CAHandler(){
    }

    @Override
    public void run() {
        socket();
    }

    private void socket(){
        if(caServer==null)return;
        //checkRun=true;
        while (true) {
           // if(!checkRun) continue;
            try {

                client = caServer.accept();
                ObjectOutputStream outToClient = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream inFromClient = new ObjectInputStream(client.getInputStream());
                //rec
                var object =inFromClient.readObject();
                Map<String, Object> map = (Map<String, Object>) object;
                // TODO here1
             //   map= Security.Decryption((String) map.get("securityType"),map);

                Msg msg = new Msg();
                if((boolean) map.get("status")){

                    msg.fromMap(map);
                    //TODO service
                    msg = controllerCA.processCA(msg);
                    map = msg.toMap();

                    //send
                    //TODO here2
                //    map= Security.Encryption((String) map.get("securityType"),map);
                    Header header=new Header().fromMap((Map<String, Object>) map.get("header"));
                    header.setUniqueID(Request.uniqueID);
                    map.put("header",header.toMap());

                }



                outToClient.writeObject(map);
                //msg = controllerRequest.afterProcessService(msg);

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
