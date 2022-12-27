package database;

import model.ClientModel;
import model.MessageModel;
import model.NumberModel;
import model.ResultModel;

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
            return   Repository.saveMessage(messageModel);
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

}
