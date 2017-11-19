/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import cleverbot.Cleverbot;
import java.io.*;
import java.util.Scanner;
import org.json.*;
import org.jsoup.*;
import org.jsoup.helper.*;

/**
 *
 * @author Marchella Metta
 */
public class Tester {
    public static void main(String[] args) throws IOException, JSONException {
        Scanner sc = new Scanner(System.in);
        Cleverbot c = new Cleverbot();
        boolean done = false;
        
        while(!done){
            String message = sc.nextLine();
            if(message.equals("done")){
                break;
            }
            String reply = c.sendMessage(message);
            System.out.println(reply);
        }
    
  }
}
