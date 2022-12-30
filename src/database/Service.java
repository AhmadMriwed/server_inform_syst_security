package database;

import model.ClientModel;
import model.MessageModel;
import model.NumberModel;
import model.ResultModel;

import java.util.List;

public class Service {
    static  private  Repository repository=new Repository();

    static public ResultModel signUp(ClientModel clientModel){
        try {
            return   Repository.saveClient(clientModel);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
    static public ResultModel logIn(int number,String password){
        try {
            return   Repository.getClientByNumberAndPassword(number, password);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
    static public ResultModel searchByNumber(int number){
        try {
            return   Repository.getClientByNumber(number);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
    static public ResultModel ShowSendingNumbers(int number){
        try {
            return   Repository.getSendingClientByRec_idAndCheck_rec(number,false);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
    static public ResultModel addNumberToClient(NumberModel numberModel){
        try {
            return   Repository.saveNumber(numberModel);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
    static public ResultModel getNumbersToClient(int number){
        try {
            return   Repository.getNumbersByNumber(number);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
    static public ResultModel addMessage(MessageModel messageModel){
        try {

            addNumberToClient(new NumberModel(messageModel.send_id,messageModel.rec_id));
            return   Repository.saveMessage(messageModel);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
    static public ResultModel updateMessage(MessageModel messageModel){
        try {

            return   Repository.updateMessage(messageModel);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
    static public ResultModel getMessageById(long message_id){
        try {
            return   Repository.getMessageById(message_id);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
    static public ResultModel getMessagesByClient(int send_id,int rec_id){
        try {
            return   Repository.getMessagesByClient(send_id,rec_id);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
    static public ResultModel getAllMessagesByClient(int send_id,int rec_id){
        try {
            return   Repository.getAllMessagesByClient(send_id,rec_id);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
    static public ResultModel getSendingClientBySend_idAndRec_idAndCheck_rec(NumberModel numberModel){
        try {
            return   Repository.getSendingClientBySend_idAndRec_idAndCheck_rec(numberModel.getNumber(),numberModel.getClient_number(),false);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
    static public ResultModel doneRecMessage(List<MessageModel> messageModels){
        try {
            ResultModel resultModel =new ResultModel(null,"error");;
            for (MessageModel messageModel: messageModels) {
                messageModel.checkRec=true;
               resultModel= updateMessage(messageModel);
            }
            return  resultModel;
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }

}
