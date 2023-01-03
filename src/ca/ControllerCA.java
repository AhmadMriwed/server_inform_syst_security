package ca;

import controller.Request;
import controller.ServiceType;
import database.Service;
import model.ClientModel;
import model.MessageModel;
import model.NumberModel;
import model.ResultModel;
import request_response.Msg;

import java.util.List;

public class ControllerCA {

    public  Msg processCA(Msg msg){
        switch (msg.header.getService()){
            case ServiceType.GenCertificateSignature:
                return genCertificateSignature(msg);
            case ServiceType.VerifyCertificateSignature:
                return verifyCertificateSignature(msg);
            default:
                return new Msg();
        }

    }

    private  Msg genCertificateSignature(Msg msg){
        msg.fromMap(CertificateSignature.genCertificateSignature(msg.toMap()));
        return msg;
    }
    private  Msg verifyCertificateSignature(Msg msg){
        msg.fromMap(CertificateSignature.VerifyCertificateSignature(msg.toMap()));
        return msg;
    }

}
