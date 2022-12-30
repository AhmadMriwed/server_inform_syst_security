package controller;

import database.Service;
import model.ClientModel;
import model.MessageModel;
import model.NumberModel;
import model.ResultModel;
import request_response.Msg;

import java.util.List;

public class ControllerRequest {
    public static Msg processService(Msg msg){
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
            case ServiceType.done:
            default:
                return new Msg();
        }

    }
    public static Msg afterProcessService(Msg msg){
        switch (msg.header.getService()){
            case ServiceType.Chat:
                return afterChat(msg);
            case ServiceType.done:
            default:
                return new Msg();
        }

    }
    private static Msg logIn(Msg msg){
        ClientModel clientModel= (ClientModel) msg.body;
        ResultModel resultModel= Service.logIn(clientModel.getNumber(),clientModel.getPassword());
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private static Msg signUp(Msg msg){
        ClientModel clientModel= (ClientModel) msg.body;
        ResultModel resultModel= Service.signUp(clientModel);
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private static Msg showNumbers(Msg msg){
        ResultModel resultModel= Service.getNumbersToClient(msg.header.getRec_id());
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private static Msg ShowSendingNumbers(Msg msg){
        ResultModel resultModel= Service.ShowSendingNumbers(msg.header.getRec_id());
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private static Msg ChooseNumber(Msg msg){
        ClientModel clientModel= (ClientModel) msg.body;
        ResultModel resultModel= Service.searchByNumber(clientModel.getNumber());
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private static Msg ShowAllMessageFromNumber(Msg msg){
        NumberModel numberModel= (NumberModel) msg.body;
        ResultModel resultModel= Service.getMessagesByClient(numberModel.getNumber(),numberModel.getClient_number());
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private static Msg getAllMessagesByClient(Msg msg){
        NumberModel numberModel= (NumberModel) msg.body;
        ResultModel resultModel= Service.getAllMessagesByClient(numberModel.getNumber(),numberModel.getClient_number());
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private static Msg addMessage(Msg msg){
        MessageModel messageModel= (MessageModel) msg.body;
        ResultModel resultModel= Service.addMessage(messageModel);
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private static Msg chat(Msg msg){
        NumberModel numberModel= (NumberModel) msg.body;
        ResultModel resultModel= Service.getSendingClientBySend_idAndRec_idAndCheck_rec(numberModel);
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
    private static Msg afterChat(Msg msg){
        List<MessageModel> messageModelList= (List) msg.bodyList;
        ResultModel resultModel= Service.doneRecMessage(messageModelList);
        msg.status=resultModel.isStatus();
        msg.body=resultModel.getModel();
        msg.message=resultModel.getMessage();
        msg.bodyList=resultModel.getModelList();
        return msg;
    }
}
