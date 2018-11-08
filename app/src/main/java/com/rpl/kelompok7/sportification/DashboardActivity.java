package com.rpl.kelompok7.sportification;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpl.kelompok7.sportification.Models.Agenda;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private  DashboardAdapter mAdapter;
    ArrayList<Agenda> agendaList;
    private Button createAgenda;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private FirebaseAuth mAuth;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        createAgenda = findViewById(R.id.btn_create_agenda);
        recyclerView = findViewById(R.id.recycler_view);


        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("agenda");
        agendaList = new ArrayList<>();



        //Get data from firebase
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Log.d("Database","run");
                    Agenda value = dataSnapshot1.getValue(Agenda.class);
                    agendaList.add(new Agenda(value.waktuMulai,value.waktuSelesai,value.usernamePembuat,value.keterangan,value.lapangan,
                            value.jumlahSlot,value.codeBooking));
                }

                mAdapter = new DashboardAdapter(agendaList,DashboardActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Test", "Failed to read value.", databaseError.toException());
            }
        });


        //Create agenda on CreateAgendaActivity
        createAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateAgendaActivity.class);
                startActivity(intent);
            }
        });

    }

    void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
