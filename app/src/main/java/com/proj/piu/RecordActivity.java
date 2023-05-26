package com.proj.piu;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RecordActivity extends AppCompatActivity {

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static String mFileName = null;
    private static String mDirPath = Environment.getExternalStorageDirectory() + File.separator
            + Environment.DIRECTORY_MUSIC + File.separator
            + "Piu";
    //private static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    private static long idGravacao;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView recordingStatus = findViewById(R.id.txtRecStatus);

        FloatingActionButton recordButton = findViewById(R.id.fabRecord);
        FloatingActionButton stopButton = findViewById(R.id.fabStop);

        stopButton.setEnabled(false);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(startRecording()){
                    recordButton.setEnabled(false);
                    recordButton.setVisibility(View.GONE);
                    recordingStatus.setText("Gravando\n" + mFileName);

                    stopButton.setVisibility(View.VISIBLE);
                    stopButton.setEnabled(true);
                }

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopButton.setEnabled(false);
                stopButton.setVisibility(View.GONE);

                stopRecording();

                recordingStatus.setText("");

                recordButton.setVisibility(View.VISIBLE);
                recordButton.setEnabled(true);

                Intent audioActivity = new Intent(RecordActivity.this,AudioActivity.class);
                audioActivity.putExtra("idGravacao",idGravacao);
                RecordActivity.this.startActivity(audioActivity);
                finish();
            }
        });
    }

    private boolean startRecording(){
        // Verifica se o usuario deu permissoes para o uso do microfone e acesso ao armazenamento
        //if(checkPermissions()){

            /*mFileName = Environment.getExternalStorageDirectory() + File.separator
                    + Environment.DIRECTORY_MUSIC + File.separator
                    + "Piu" + File.separator
                    + System.currentTimeMillis() + ".m4a";*/

        try {
            Files.createDirectories(Paths.get(mDirPath));

            mFileName = mDirPath + File.separator
                        + System.currentTimeMillis() + ".m4a";

                Log.i("REC-ACT",mFileName);

                mRecorder = new MediaRecorder();
                mRecorder.reset();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                mRecorder.setAudioEncodingBitRate(128000);
                mRecorder.setAudioSamplingRate(44100);
                mRecorder.setOutputFile(mFileName);
                try {
                    mRecorder.prepare();

                    mRecorder.start();

                    return true;
                } catch (IOException e) {
                    Log.e("REC-ACT","Media recorder prepare() failed");
                }

                //Toast.makeText(getApplicationContext(),"Gravação iniciada",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return false;
        /*}else{
            requestPermissions();
        }*/
    }

    private void stopRecording() {
        if(mRecorder != null){
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            //Toast.makeText(getApplicationContext(),"Arquivo salvo em\n" + mFileName,Toast.LENGTH_LONG).show();

            dbHandler = new DBHandler(RecordActivity.this);

            idGravacao = dbHandler.addGravacao(mFileName);
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // this method is called when user will
        // grant the permission for audio recording.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permissao obtida", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permissao negada", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermissions() {
        int wesResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int recResult = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return wesResult == PackageManager.PERMISSION_GRANTED && recResult == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(RecordActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }*/
}