package com.proj.piu;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONObject;
import org.json.JSONException;

public class GCPFunctions {

    private static final String GCP_URL = "https://function-1-6xkxvh64aa-rj.a.run.app";

    /*public GCPFunctions(){
        System.out.println("GCP Functions initialized");
    }*/

    public String CallGCP(String name) {
        System.out.println("CallGCP method initialized");
//        String jsonBody = "{'name': '" + name + "'}";
//        System.out.println(jsonBody);

        try{
            JSONObject jsonData = new JSONObject();
            jsonData.put("name",name);
            System.out.println(jsonData);

            URL url = new URL(GCP_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try{
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setChunkedStreamingMode(0);

//                try (OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream())) {
//                    out.write(jsonBody.getBytes());
//                    out.flush();
//                }
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
                outputStreamWriter.write(jsonData.toString());
                outputStreamWriter.flush();

                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    return response.toString();
                }

            }finally{
                urlConnection.disconnect();
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return "Abc";
    }

    public static JSONObject httpPostGCP(String b64String){

        JSONObject json = new JSONObject();
        String jsonString;

        try{
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(600, TimeUnit.SECONDS)
                    .writeTimeout(600, TimeUnit.SECONDS)
                    .readTimeout(600, TimeUnit.SECONDS)
                    .build();
            MediaType mediaType = MediaType.parse("application/json");

            json.put("name","Android app");
            json.put("b64data",b64String);

            jsonString = json.toString();

            //RequestBody body = RequestBody.create(mediaType, "{\r\n  \"name\": \"Postman\"\r\n}\r\n");
            RequestBody body = RequestBody.create(mediaType, jsonString);
            Request request = new Request.Builder()
                    .url(GCP_URL)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();

            String httpResponse = response.body().string();
            JSONObject jsonResponse = new JSONObject(httpResponse);

            return jsonResponse;

        } catch (Exception e) {
            //Log.e("GCP-FUNCTIONS",e.getMessage());
            e.printStackTrace();
        }

        return null;

    }
}
