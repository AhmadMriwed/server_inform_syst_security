package model;

import java.util.ArrayList;
import java.util.List;

public class ResultModel {
    String message;
    Model model;
    List<Model> modelList=new ArrayList<>();
    boolean status;
    public ResultModel(Model model, String message){
        this.message=message;
        this.model=model;
        this.status=false;
    }
    public ResultModel(Model model, String message,boolean status){
        this.message=message;
        this.model=model;
        this.status=status;
    }
    public ResultModel( String message,List<Model> modelList){
        this.message=message;
        this.modelList=modelList;
        this.status=false;
    }
    public ResultModel( String message,boolean status,List<Model> modelList){
        this.message=message;
        this.modelList=modelList;
        this.status=status;
    }


    public List<Model> getModelList() {
        return modelList;
    }

    public void setModelList(List<Model> modelList) {
        this.modelList = modelList;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public Model getModel() {
        return model;
    }

    public boolean isStatus() {
        return status;
    }
}
