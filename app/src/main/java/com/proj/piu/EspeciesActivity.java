package com.proj.piu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EspeciesActivity extends AppCompatActivity {

    private ArrayList<EspeciesModal> especiesModalArrayList;
    private DBHandler dbHandler;
    private EspeciesRVAdapter especiesRVAdapter;
    private RecyclerView especiesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);

        especiesModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(EspeciesActivity.this);

        especiesModalArrayList = dbHandler.getAllEspecies();

        especiesRVAdapter = new EspeciesRVAdapter(especiesModalArrayList, EspeciesActivity.this);
        especiesRV = findViewById(R.id.idEspeciesRVHistory);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EspeciesActivity.this, RecyclerView.VERTICAL, false);
        especiesRV.setLayoutManager(linearLayoutManager);

        especiesRV.setAdapter(especiesRVAdapter);

    }
}