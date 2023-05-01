package com.example.piu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GCPFunctions {

    private static final String GCP_URL = "https://function-1-6xkxvh64aa-rj.a.run.app";

    public GCPFunctions(){
        System.out.println("GCP Functions initialized");
    }

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
                urlConnection.setRequestMethod("GET");
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
}
