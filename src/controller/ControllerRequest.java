package controller;

import database.Service;
import model.ClientModel;
import model.MessageModel;
import model.NumberModel;
import model.ResultModel;
import request_response.Msg;
import security.Json;

import java.util.List;

public class ControllerRequest {
    Service service=new Service();
    public  Msg processService(Msg msg){
        switch (msg.header.getService()){
            case ServiceType.LogIn:
                return logIn(msg);
            case ServiceType.SignUp:
                return signUp(msg);
            case ServiceType.ShowNumbers:
                return showNumbers(msg);
            case ServiceType.ShowSendingNumbers:
                return ShowSendingNumbers(msg);
            case ServiceType.ChooseNumber:
                return ChooseNumber(msg);
            case ServiceType.ShowAllMessageFromNumber:
                return ShowAllMessageFromNumber(msg);
            case ServiceType.Chat:
                return chat(msg);
            case ServiceType.SendMessage:
                return addMessage(msg);
            case ServiceType.ShowAllMessageChat:
                return getAllMessagesByClient(msg);
            case ServiceType.getPublicKey:
                return getPublicKey(msg);
            case ServiceType.done:
            default:
                return new Msg();
        }

    }
    public  Msg afterProcessService(Msg msg){
        switch (msg.header.getService()){
            case ServiceType.Chat:
                return afterChat(msg);
            case ServiceType.done:
            default:
                return new Msg();
        }

    }
    private  Msg logIn(Msg msg){
        ClientModel clientModel= (ClientModel) msg.body;
        ResultModel resultModel= service.logIn(clientModel.getNumber(),clientModel.getPassword());
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private  Msg signUp(Msg msg){
        ClientModel clientModel= (ClientModel) msg.body;
        ResultModel resultModel= service.signUp(clientModel);
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private  Msg showNumbers(Msg msg){
        ResultModel resultModel= service.getNumbersToClient(msg.header.getRec_id());
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private  Msg ShowSendingNumbers(Msg msg){
        ResultModel resultModel= service.ShowSendingNumbers(msg.header.getRec_id());
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private  Msg ChooseNumber(Msg msg){
        ClientModel clientModel= (ClientModel) msg.body;
        ResultModel resultModel= service.searchByNumber(clientModel.getNumber());
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private  Msg ShowAllMessageFromNumber(Msg msg){
        NumberModel numberModel= (NumberModel) msg.body;
        ResultModel resultModel= service.getMessagesByClient(numberModel.getNumber(),numberModel.getClient_number());
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private  Msg getAllMessagesByClient(Msg msg){
        NumberModel numberModel= (NumberModel) msg.body;
        ResultModel resultModel= service.getAllMessagesByClient(numberModel.getNumber(),numberModel.getClient_number());
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private  Msg addMessage(Msg msg){
        MessageModel messageModel= (MessageModel) msg.body;
        ResultModel resultModel= service.addMessage(messageModel);
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private  Msg chat(Msg msg){
        NumberModel numberModel= (NumberModel) msg.body;
        ResultModel resultModel= service.getSendingClientBySend_idAndRec_idAndCheck_rec(numberModel);
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private  Msg afterChat(Msg msg){
        List<MessageModel> messageModelList= (List) msg.bodyList;
        ResultModel resultModel= service.doneRecMessage(messageModelList);
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private  Msg getPublicKey(Msg msg){
        Request.mapPublicKey.put(msg.header.getUniqueID(),msg.header.getPublicKey());
        msg.header.setUniqueID(Request.uniqueID);
        msg.header.setPublicKey(Request.publicKey);
        msg.status=true;
        msg.message="able to connect";
        return msg;
    }
}
