package database;

import model.*;

import javax.crypto.NullCipher;
import java.nio.channels.AsynchronousFileChannel;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.FutureTask;

public class Repository {

   static Statement statement = null;
   static Connection connection = null;
   static public ResultModel saveClient(ClientModel clientModel){
           try {
               connect();
               ResultSet resultExitNumber = statement.executeQuery("SELECT * FROM `client` WHERE number= "+ clientModel.getNumber()+" ");
               if(resultExitNumber.next())
                   return new ResultModel(null,"Number is already found");
               else {
                   int res= statement.executeUpdate("INSERT INTO client (number,name,password) VALUES ( '"+clientModel.getNumber()+"','"+clientModel.getName()+"','"+clientModel.getPassword()+"')",Statement.RETURN_GENERATED_KEYS);
                   return new ResultModel(clientModel,"save done",true);
               }
           } catch (SQLException throwables) {
              // System.out.println(throwables);
           } catch (NoSuchAlgorithmException e) {
               //System.out.println(e);
           } catch (InvalidKeySpecException e) {
               //System.out.println(e);
           }catch (Exception e) {
               //e.printStackTrace();
               //System.out.println(e);
           }
       return new ResultModel(null,"database error");
   }
    static public ResultModel getClientByNumber(int number){
        try {
            connect();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `client` WHERE number= "+ number+" ");
            if(resultSet.next())
                return new ResultModel(fromDatabaseToClientModel(resultSet),"Fetch client done",true);
            else {
                return new ResultModel(null,"number not found");
            }
        } catch (SQLException throwables) {
            // System.out.println(throwables);
        } catch (NoSuchAlgorithmException e) {
            //System.out.println(e);
        } catch (InvalidKeySpecException e) {
            //System.out.println(e);
        }catch (Exception e) {
            //e.printStackTrace();
            //System.out.println(e);
        }
        return new ResultModel(null,"database error");
    }
    static public ResultModel getClientByNumberAndPassword(int number,String password){
        try {
            connect();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `client` WHERE number= "+ number+" And "+"password= \""+ password+"\"");
            if(resultSet.next())
                return new ResultModel(fromDatabaseToClientModel(resultSet),"Fetch client done",true);
            else {
                return new ResultModel(null,"enter not correct");
            }
        } catch (SQLException throwables) {
            // System.out.println(throwables);
        } catch (NoSuchAlgorithmException e) {
            //System.out.println(e);
        } catch (InvalidKeySpecException e) {
            //System.out.println(e);
        }catch (Exception e) {
            //e.printStackTrace();
            //System.out.println(e);
        }
        return new ResultModel(null,"database error");
    }
    static public ResultModel saveNumber(NumberModel numberModel){
        try {
            connect();

            ResultSet resultExitClientNumber = statement.executeQuery("SELECT * FROM `client` WHERE number= "+ numberModel.getClient_number());
            statement = connection.createStatement();
            ResultSet resultExitNumber = statement.executeQuery("SELECT * FROM `client` WHERE number= "+ numberModel.getNumber());
            statement = connection.createStatement();
             ResultSet resultExitClientNumberAndNumber = statement.executeQuery("SELECT * FROM `numbers` WHERE client_number= "+ numberModel.getClient_number()+" AND "+"number= "+ numberModel.getNumber());
            if(!resultExitClientNumber.next())
                return new ResultModel(null,"Client Number not found");
           else if(!resultExitNumber.next())
                return new ResultModel(null,"Number not found");
            else if(resultExitClientNumberAndNumber.next())
                return new ResultModel(null,"Number to client number is already found");
            else {
                statement = connection.createStatement();


                numberModel.setNumber_id((numberModel.getNumber()+new Date(System.currentTimeMillis()).getTime())/999);
                int res= statement.executeUpdate("INSERT INTO numbers (number_id ,client_number,number) VALUES ( '"+numberModel.getNumber()+"','"+numberModel.getClient_number()+"','"+numberModel.getNumber()+"')",Statement.RETURN_GENERATED_KEYS);
                return new ResultModel(numberModel,"save number to notes done ",true);
            }
        } catch (SQLException throwables) {
            // System.out.println(throwables);
        } catch (NoSuchAlgorithmException e) {
            //System.out.println(e);
        } catch (InvalidKeySpecException e) {
            //System.out.println(e);
        }catch (Exception e) {
            //e.printStackTrace();
            //System.out.println(e);
        }
        return new ResultModel(null,"database error");
    }
    static public ResultModel getNumbersByNumber(int number){
        try {
            connect();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `numbers` WHERE client_number= "+ number+" ");
            List<Model> moduleList=new ArrayList<>();
            while(resultSet.next())
                moduleList.add(fromDatabaseToNumberModel(resultSet));
            return new ResultModel("Fetch numbers done",true,moduleList);

        } catch (SQLException throwables) {
            // System.out.println(throwables);
        } catch (NoSuchAlgorithmException e) {
            //System.out.println(e);
        } catch (InvalidKeySpecException e) {
            //System.out.println(e);
        }catch (Exception e) {
            //e.printStackTrace();
            //System.out.println(e);
        }
        return new ResultModel(null,"database error");
    }
    static public ResultModel saveMessage(MessageModel messageModel){
        try {
            connect();

            ResultSet resultExitSend_id = statement.executeQuery("SELECT * FROM `client` WHERE number= "+ messageModel.send_id);
            statement = connection.createStatement();
            ResultSet resultExitRec_id = statement.executeQuery("SELECT * FROM `client` WHERE number= "+ messageModel.rec_id);
            if(!resultExitSend_id.next())
                return new ResultModel(null,"send_id not found");
            else if(!resultExitRec_id.next())
                return new ResultModel(null,"rec_id not found");
            else {
                statement = connection.createStatement();
                messageModel.date=new Date(System.currentTimeMillis());
                messageModel.message_id=(messageModel.rec_id+messageModel.date.getTime())/999;
                int res= statement.executeUpdate("INSERT INTO message (message_id,send_id,rec_id,text,checkSend,checkRec,date) VALUES ( '"
                        +messageModel.message_id+"','"+messageModel.send_id+"','"+messageModel.rec_id
                        +"','"+messageModel.text+"',"+messageModel.checkSend+","+messageModel.checkRec
                        +",'"+ new java.sql.Timestamp(messageModel.date.getTime())+"')",Statement.RETURN_GENERATED_KEYS);
                return new ResultModel(messageModel,"save message done ",true);
            }
        } catch (SQLException throwables) {
            // System.out.println(throwables);
        } catch (NoSuchAlgorithmException e) {
            //System.out.println(e);
        } catch (InvalidKeySpecException e) {
            //System.out.println(e);
        }catch (Exception e) {
            //e.printStackTrace();
            //System.out.println(e);
        }
        return new ResultModel(null,"database error");
    }
    static public ResultModel getMessagesByClient(int send_id,int rec_id){
        try {
            connect();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `message` WHERE send_id= "+ send_id+" And "+"rec_id= "+ rec_id);
            List<Model> moduleList=new ArrayList<>();
            while(resultSet.next())
                moduleList.add(fromDatabaseToMessageModel(resultSet));
            return new ResultModel("Fetch messages done",true,moduleList);

        } catch (SQLException throwables) {
            // System.out.println(throwables);
        } catch (NoSuchAlgorithmException e) {
            //System.out.println(e);
        } catch (InvalidKeySpecException e) {
            //System.out.println(e);
        }catch (Exception e) {
            //e.printStackTrace();
            //System.out.println(e);
        }
        return new ResultModel(null,"database error");
    }
    static public ResultModel getMessageById(long message_id){
        try {
            connect();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `message` WHERE message_id= "+ message_id);

            if(!resultSet.next())
                return new ResultModel(null,"message  not found");
            else
            return new ResultModel(fromDatabaseToMessageModel(resultSet),"Fetch message done",true);

        } catch (SQLException throwables) {
            // System.out.println(throwables);
        } catch (NoSuchAlgorithmException e) {
            //System.out.println(e);
        } catch (InvalidKeySpecException e) {
            //System.out.println(e);
        }catch (Exception e) {
            //e.printStackTrace();
            //System.out.println(e);
        }
        return new ResultModel(null,"database error");
    }
    static public ResultModel updateMessage(MessageModel messageModel){
        try {
            connect();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM `message` WHERE message_id= "+ messageModel.message_id);
            if(!resultSet.next())
                return new ResultModel(null,"message  not found");
            else {
                statement = connection.createStatement();
                int res= statement.executeUpdate("update  message set "
                +"text = '"+messageModel.text+"',checkSend = "+messageModel.checkSend
                        +",checkRec = "+messageModel.checkRec+",date = '"+new java.sql.Timestamp(messageModel.date.getTime())
                        +"' where message_id = "+messageModel.message_id);
                return new ResultModel(messageModel,"update message done ",true);
            }
        } catch (SQLException throwables) {
            // System.out.println(throwables);
        } catch (NoSuchAlgorithmException e) {
            //System.out.println(e);
        } catch (InvalidKeySpecException e) {
            //System.out.println(e);
        }catch (Exception e) {
            //e.printStackTrace();
            //System.out.println(e);
        }
        return new ResultModel(null,"database error");
    }

    public  static ClientModel fromDatabaseToClientModel(ResultSet resultSet) throws Exception{
        return new ClientModel(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3)
        );
    }
    public  static NumberModel fromDatabaseToNumberModel(ResultSet resultSet) throws Exception{
        return new NumberModel(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getInt(3)
        );
    }
    public  static MessageModel fromDatabaseToMessageModel(ResultSet resultSet) throws Exception{
        return new MessageModel(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getInt(3),
                resultSet.getInt(4),
                resultSet.getDate(5),
                resultSet.getBoolean(6),
                resultSet.getBoolean(7)
        );
    }

   static public boolean connect() throws  Exception {
        String database="jdbc:mysql://localhost:3306/lhma";
        String userName="root";
        String password="";

        if(statement!=null) { return  true;}
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    database, userName, password
            );

            statement = connection.createStatement();
            return  true;
        }catch (SQLException throwables) {
            throw  throwables;

           // throwables.printStackTrace();
        }
        catch (Exception e) {
            throw  e;
            //e.printStackTrace();
            //System.out.println(e);
            }

    }
}
