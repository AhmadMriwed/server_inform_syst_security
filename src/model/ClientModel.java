package model;

public class ClientModel {
    int number;
    String password;
    String name="";
    ClientModel(int number,String password,String name){
        this.name=name;
        this.number=number;
        this.password=password;
    }
    ClientModel(){
        this.name="";
        this.number=0;
        this.password="";
    }
}
