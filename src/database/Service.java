package database;

import model.ClientModel;
import model.MessageModel;
import model.NumberModel;
import model.ResultModel;

import java.util.List;

public class Service {
      private  Repository repository=new Repository();

     public ResultModel signUp(ClientModel clientModel){
        try {
            return   repository.saveClient(clientModel);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
     public ResultModel logIn(int number,String password){
        try {
            return   repository.getClientByNumberAndPassword(number, password);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
     public ResultModel searchByNumber(int number){
        try {
            return   repository.getClientByNumber(number);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
     public ResultModel ShowSendingNumbers(int number){
        try {
            return   repository.getSendingClientByRec_idAndCheck_rec(number,false);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
     public ResultModel addNumberToClient(NumberModel numberModel){
        try {
            return   repository.saveNumber(numberModel);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
     public ResultModel getNumbersToClient(int number){
        try {
            return   repository.getNumbersByNumber(number);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
     public ResultModel addMessage(MessageModel messageModel){
        try {

            addNumberToClient(new NumberModel(messageModel.send_id,messageModel.rec_id));
            return   repository.saveMessage(messageModel);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
     public ResultModel updateMessage(MessageModel messageModel){
        try {

            return   repository.updateMessage(messageModel);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
     public ResultModel getMessageById(long message_id){
        try {
            return   repository.getMessageById(message_id);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
     public ResultModel getMessagesByClient(int send_id,int rec_id){
        try {
            return   repository.getMessagesByClient(send_id,rec_id);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
     public ResultModel getAllMessagesByClient(int send_id,int rec_id){
        try {
            return   repository.getAllMessagesByClient(send_id,rec_id);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
     public ResultModel getSendingClientBySend_idAndRec_idAndCheck_rec(NumberModel numberModel){
        try {
            return   repository.getSendingClientBySend_idAndRec_idAndCheck_rec(numberModel.getNumber(),numberModel.getClient_number(),false);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResultModel(null,"error");
    }
     public ResultModel doneRecMessage(List<MessageModel> messageModels){
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
