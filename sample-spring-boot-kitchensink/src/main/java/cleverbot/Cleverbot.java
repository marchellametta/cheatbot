/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleverbot;

import java.io.*;
import org.json.*;
import org.jsoup.*;
import org.jsoup.helper.*;

/**
 *
 * @author Marchella Metta
 */
public class Cleverbot {
    private String cs = "&cs=";
    private String url= "https://www.cleverbot.com/getreply?key=CC53jHOvCyt7z78q91SjOrUiqpA";
    
    public Cleverbot()throws IOException, JSONException{
        Connection connection = HttpConnection.connect(url);
        connection.ignoreContentType(true);
        String content = connection.execute().body();
        JSONObject jsonObject = new JSONObject(content);
        this.cs = this.cs+ jsonObject.get("cs").toString();
        System.out.println(jsonObject.get("cs").toString());
    }
    
    public String sendMessage(String message) throws IOException, JSONException{
        String input = message.replace(" ","%20");
        System.out.println(url + cs + "&input=" + input);
        Connection connection = HttpConnection.connect(url + cs + "&input=" + input );
        connection.ignoreContentType(true);
        String content = connection.execute().body();
        JSONObject jsonObject = new JSONObject(content);
        System.out.println(content);
        this.cs = "&cs=" + jsonObject.get("cs").toString();
        return jsonObject.get("output").toString();
    }
    
}
