package ca;

import controller.Request;
import request_response.CSR;
import request_response.Msg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;
import java.util.Base64;
import java.util.Map;

public class GenCSR {

    public static CSR createCSr() {
        CSR csr=new CSR();
            csr.setName(Request.name);
            csr.setHttp(Request.http);
            csr.setPublicKey(Request.publicKey);
        return csr;
    }
    public static Msg sendCertificateSignature(Msg msg){
        return msg;
    }
    public static Msg getCertificateSignature(Msg msg){
        return msg;
    }
}
