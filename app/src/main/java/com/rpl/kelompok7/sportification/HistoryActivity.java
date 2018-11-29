package com.rpl.kelompok7.sportification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rpl.kelompok7.sportification.Models.Agenda;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  HistoryAdapter mAdapter;
    public  static  ArrayList<Agenda> agendaList = new ArrayList<>();;
    private  ArrayList<Agenda> historyList;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;

    String emailUser;

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

        setupFirebase();

        getDataUserHistory();



//        mAdapter = new HistoryAdapter(historyList,HistoryActivity.this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
//        recyclerView.setAdapter(mAdapter);

//        for(Agenda s : agendaList) {
//            historyList.add(s);
//            mAdapter.notifyDataSetChanged();
//        }
    }

    void setupFirebase(){
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("agenda");

        mAuth = FirebaseAuth.getInstance();
        emailUser = mAuth.getCurrentUser().getEmail();
    }

    void getDataUserHistory(){
        Query queryRef = mReference.child("codeBooking").orderByChild("emailPemain").equalTo(0);
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Agenda value = dataSnapshot1.getValue(Agenda.class);
                    historyList.add(new Agenda(value.tanggalAgenda));

                }

                mAdapter = new HistoryAdapter(historyList,HistoryActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
