package com.example.lewan.myapplication;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class Authorization extends Thread {

    private String login;
    private String password;
    private CallBack callBack;


    public Authorization(String client_login, String client_password, CallBack callBack) {
        login = client_login;
        password = client_password;

        this.callBack = callBack;
    }


    public void run() {


        try {
            URL url = new URL("http://mysite/index.php?proc=logIn&login=" + login + "&password=" + password);
            URLConnection yc = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                sb.append(inputLine + "\n");
            }
            in.close();

            inputLine = sb.toString();
            JSONObject jObject = new JSONObject(inputLine);
            String aJsonString = (String) jObject.get("Eee");
            callBack.setStatus(aJsonString);
            //status = aJsonString;
//            System.out.println("JSOON" + aJsonString);


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
