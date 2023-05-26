package com.proj.piu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RetornoActivity extends AppCompatActivity {

    private ArrayList<RetornoModal> retornoModalArrayList;
    private DBHandler dbHandler;
    private RetornoRVAdapter retornoRVAdapter;
    private RecyclerView retornoRV;
    private long idGravacao;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retorno);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView txtMensagem = findViewById(R.id.txtMensagem);
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setVisibility(View.GONE);

        retornoModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(RetornoActivity.this);

        idGravacao = getIntent().getLongExtra("idGravacao",0);

        retornoModalArrayList = dbHandler.getRetornos(idGravacao);

        if(retornoModalArrayList.size() > 0){
            retornoRVAdapter = new RetornoRVAdapter(retornoModalArrayList, RetornoActivity.this);
            retornoRV = findViewById(R.id.idRetornoRVHistory);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RetornoActivity.this, RecyclerView.VERTICAL, false);
            retornoRV.setLayoutManager(linearLayoutManager);

            retornoRV.setAdapter(retornoRVAdapter);
        }else{
            txtMensagem.setText("A vocalização não foi identificada");
            btnDelete.setVisibility(View.VISIBLE);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbHandler.deleteGravacaoData(idGravacao);

                    Intent mainAct = new Intent(RetornoActivity.this,MainActivity.class);
                    RetornoActivity.this.startActivity(mainAct);
                    finish();
                }
            });
        }


    }

    public void onBackPressed(){
        Intent historyActivity = new Intent(RetornoActivity.this,HistoryActivity.class);
        historyActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(historyActivity);
        super.onBackPressed();
    }
}