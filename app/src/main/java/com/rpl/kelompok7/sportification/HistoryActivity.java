package com.rpl.kelompok7.sportification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.rpl.kelompok7.sportification.Models.Agenda;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  HistoryAdapter mAdapter;
    public  static  ArrayList<Agenda> agendaList = new ArrayList<>();;
    private  ArrayList<Agenda> historyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        historyList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_history);

        for(Agenda s : agendaList) {
            historyList.add(s);
            mAdapter.notifyDataSetChanged();
        }


        mAdapter = new HistoryAdapter(historyList,HistoryActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
        recyclerView.setAdapter(mAdapter);
    }
}
