package com.proj.piu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<HistoryModal> historyModalArrayList;
    private DBHandler dbHandler;
    private HistoryRVAdapter historyRVAdapter;
    private RecyclerView historyRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        historyModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(HistoryActivity.this);

        historyModalArrayList = dbHandler.getHistory();

        historyRVAdapter = new HistoryRVAdapter(historyModalArrayList, HistoryActivity.this);
        historyRV = findViewById(R.id.idRVHistory);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryActivity.this, RecyclerView.VERTICAL, false);
        historyRV.setLayoutManager(linearLayoutManager);

        historyRV.setAdapter(historyRVAdapter);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});
    }
}