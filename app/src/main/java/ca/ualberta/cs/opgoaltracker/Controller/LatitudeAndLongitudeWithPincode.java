/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 *
 */

package ca.ualberta.cs.opgoaltracker.Controller;

/**
 * Created by yongjiahuang on 2017-11-28.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


/**
 * This class will get the lat long values.
 */
public class LatitudeAndLongitudeWithPincode {

    public LatitudeAndLongitudeWithPincode(){

    }

    public String getHTTPDate(String requestURL){
        URL url;
        String response = "";
        try{
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpsURLConnection.HTTP_OK){
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine())!=null)
                    response+=line;
            }
            else
                response = "";
        }catch (ProtocolException e){
            e.printStackTrace();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }



}