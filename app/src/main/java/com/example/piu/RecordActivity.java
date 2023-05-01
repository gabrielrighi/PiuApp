package com.example.piu;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
import java.time.Instant;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RecordActivity extends AppCompatActivity {

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static String mFileName = null;
    private static final int REQUEST_AUDIO_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GCPFunctions gcpFunction = new GCPFunctions();

        TextView recordingStatus = findViewById(R.id.txtRecStatus);

        FloatingActionButton recordButton = findViewById(R.id.fabRecord);
        FloatingActionButton stopButton = findViewById(R.id.fabStop);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new Thread(new Runnable() {
                //    public void run() {
                        //Snackbar.make(view, gcpFunction.CallGCP("Android"), Snackbar.LENGTH_LONG)
                        //        .setAction("Action", null).show();

                //    }
                //}).start();

                startRecording();
                recordingStatus.setText("Gravando\n" + mFileName);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecording();
                recordingStatus.setText("");
            }
        });
    }

    private void startRecording(){
        // Verifica se o usuario deu permissoes para o uso do microfone e acesso ao armazenamento
        if(checkPermissions()){

            mFileName = Environment.getExternalStorageDirectory() + File.separator
                    + Environment.DIRECTORY_MUSIC + File.separator
                    + System.currentTimeMillis() + ".m4a";

            Log.i("REC-ACT",mFileName);

            mRecorder = new MediaRecorder();
            mRecorder.reset();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mRecorder.setOutputFile(mFileName);
            try {
                mRecorder.prepare();
            } catch (IOException e) {
                Log.e("REC-ACT","Media recorder prepare() failed");
            }

            mRecorder.start();
            Toast.makeText(getApplicationContext(),"Started recording",Toast.LENGTH_LONG).show();
        }else{
            RequestPermissions();
        }
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        Toast.makeText(getApplicationContext(),"Arquivo salvo em\n" + mFileName,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // this method is called when user will
        // grant the permission for audio recording.
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
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

    private void RequestPermissions() {
        ActivityCompat.requestPermissions(RecordActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }
}