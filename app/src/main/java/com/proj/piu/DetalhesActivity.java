package com.proj.piu;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DetalhesActivity extends AppCompatActivity {

    private DBHandler dbHandler;
    private String idEspecie, url, nomeComum;
    private Especies especieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WebView webBird = findViewById(R.id.webBird);

        dbHandler = new DBHandler(DetalhesActivity.this);

        idEspecie = getIntent().getStringExtra("idEspecie");

        especieData = dbHandler.getEspecieData(idEspecie);

        nomeComum = especieData.getNomeComum();
        url = especieData.getUrlWikiaves();

        getSupportActionBar().setTitle(nomeComum);

        webBird.loadUrl(url);
        webBird.getSettings().setJavaScriptEnabled(true);
        webBird.setWebViewClient(new WebViewClient());
    }
}