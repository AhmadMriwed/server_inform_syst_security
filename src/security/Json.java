package security;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Json {
    private static String path="S:/Users/hp/client/src/security/key.json";
    public static Object put(Object key,Object value){

       return put(key,value,path);
    }
    public static Object get(Object key){
        return get(key,path);
    }
    public static boolean containsKey(Object key){
       return containsKey(key,path);
    }

    public static Object put(Object key,Object value,String path){

        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject)jsonP.parse(new FileReader(path));
            jsonO.put(key,value);
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                out.write(jsonO.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Object get(Object key,String path){
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject)jsonP.parse(new FileReader(path));
            return  jsonO.get(key.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  "null";
    }
    public static boolean containsKey(Object key,String path){
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject)jsonP.parse(new FileReader(path));
            return  jsonO.containsKey(key.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  false;
    }
}
