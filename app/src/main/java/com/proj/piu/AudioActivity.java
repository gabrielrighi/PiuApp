package com.proj.piu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AudioActivity extends AppCompatActivity {

    private DBHandler dbHandler;
    private HistoryModal reconData;
    private String recDataHora, recArquivo;
    private String b64String;
    private long idGravacao;

    private TextView txtRecId, txtRecPath, txtRecStatus;
    private Button btnSend, btnListen, btnStop, btnDelete;

    MediaPlayer mediaPlayer;

    //GCPFunctions gcpFunction = new GCPFunctions();
    AudioFileUtils audioFileUtils = new AudioFileUtils();

    // https://code.tutsplus.com/tutorials/android-from-scratch-using-rest-apis--cms-27117
    String GCP_URL = "https://function-1-6xkxvh64aa-rj.a.run.app";
    ProgressDialog progressDialog; // Change with ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtRecId = findViewById(R.id.txtRecId);
        txtRecPath = findViewById(R.id.txtRecPath);

        txtRecStatus = findViewById(R.id.txtRecStatus);
        btnSend = findViewById(R.id.btnSend);
        btnListen = findViewById(R.id.btnListen);
        btnStop = findViewById(R.id.btnStop);
        btnDelete = findViewById(R.id.btnDelete);

        btnStop.setVisibility(View.GONE);

        dbHandler = new DBHandler(AudioActivity.this);

        idGravacao = getIntent().getLongExtra("idGravacao", 0);

        reconData = dbHandler.getRowData(idGravacao);
        recDataHora = reconData.getDataHora();
        recArquivo = reconData.getArquivo();

        txtRecId.setText(Long.toString(idGravacao));
        txtRecPath.setText(recArquivo);

        setPlayer();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b64String = AudioFileUtils.convertAudioToBase64(recArquivo);

                MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                myAsyncTasks.execute(b64String);

            }
        });

        btnListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer == null){
                    setPlayer();
                }
                btnListen.setVisibility(View.GONE);
                mediaPlayer.start();
                btnStop.setVisibility(View.VISIBLE);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null) {
                    mediaPlayer.pause();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

                btnStop.setVisibility(View.GONE);
                btnListen.setVisibility(View.VISIBLE);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.deleteGravacaoData(idGravacao);

                Intent mainAct = new Intent(AudioActivity.this,MainActivity.class);
                AudioActivity.this.startActivity(mainAct);
                finish();
            }
        });

    }

    public void setPlayer(){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        try {
            mediaPlayer.setDataSource(reconData.getArquivo());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed(){
        Intent mainActivity = new Intent(AudioActivity.this,MainActivity.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivity);
        super.onBackPressed();
    }

    private String getB64AudioFile(String filePath) {
        String base64String = audioFileUtils.convertAudioToBase64(filePath);

        return base64String;
    }

    /*private JSONObject getReconResult(String base64String) {
        JSONObject jsonResult = gcpFunction.httpPostGCP(b64String);

        return jsonResult;
    }*/

    private boolean insertReconResult(JSONObject jsonResult) {

        boolean insertResult = dbHandler.addRecon(idGravacao, jsonResult);

        if (insertResult) {
            dbHandler.updateGravacaoEnviado(idGravacao);
        }

        return insertResult;
    }


    // NOVO
    public class MyAsyncTasks extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog to show the user what is happening
            progressDialog = new ProgressDialog(AudioActivity.this);
            progressDialog.setMessage("Identificando...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        }

        @Override
        protected String doInBackground(String... params) {
            // fetch data from the API in background
            JSONObject json = new JSONObject();
            String jsonString;
            String result = "";

            try{
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .connectTimeout(600, TimeUnit.SECONDS)
                        .writeTimeout(600, TimeUnit.SECONDS)
                        .readTimeout(600, TimeUnit.SECONDS)
                        .build();
                MediaType mediaType = MediaType.parse("application/json");

                json.put("name","Android app");
                json.put("b64data",params[0]);

                jsonString = json.toString();

                RequestBody body = RequestBody.create(mediaType, jsonString);
                Request request = new Request.Builder()
                        .url(GCP_URL)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                Response response = client.newCall(request).execute();

                result = response.body().string();

                return result;
            } catch (Exception e) {
                //Log.e("GCP-FUNCTIONS",e.getMessage());
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            // show results
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            progressDialog.dismiss();
            try{
                JSONObject reconResult = new JSONObject(s);
                try {
                    String status = reconResult.getString("status");
                    if (reconResult != null && status.equalsIgnoreCase("success")) {
                        insertReconResult(reconResult);
                    }

                    Intent retornoAct = new Intent(AudioActivity.this,RetornoActivity.class);
                    retornoAct.putExtra("idGravacao",idGravacao);
                    AudioActivity.this.startActivity(retornoAct);
                    finish();
                } catch (Exception e) {
                    Log.e("AUDIO-ACT", e.getMessage());
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}