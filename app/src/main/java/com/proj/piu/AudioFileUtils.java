package com.proj.piu;

import android.database.SQLException;
import android.util.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class AudioFileUtils {
    public static String convertAudioToBase64(String filePath){
        try{
            Path path = Paths.get(filePath);
            byte[] audioData = Files.readAllBytes(path);
            String encodedData = Base64.getEncoder().encodeToString(audioData);

            return encodedData;
        } catch (IOException e) {
            Log.e("AF-UTILS",e.getMessage());
        }

        return "Failure";
    }
}
