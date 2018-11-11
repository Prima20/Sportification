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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpl.kelompok7.sportification.Models.Penyedia;

import java.util.ArrayList;

public class DashboardPenyediaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DashboardPenyediaAdapter mAdapter;
    ArrayList<Penyedia> penyediaList;
    private Button createLapangan;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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
        setContentView(R.layout.activity_dashboard_penyedia);

        createLapangan = findViewById(R.id.btn_createLapangan);
        recyclerView = findViewById(R.id.rec_view);

//        recyclerView.setAdapter(mAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("penyedia");
        penyediaList = new ArrayList<>();

        //Get data from Firebase
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Penyedia value = dataSnapshot1.getValue(Penyedia.class);
                    penyediaList.add(new Penyedia(value.noLapangan, value.alamat));


                }
                mAdapter = new DashboardPenyediaAdapter(penyediaList, DashboardPenyediaActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(DashboardPenyediaActivity.this));
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Test", "Failed to read value.", databaseError.toException());
            }
        });



        createLapangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateLapanganActivity.class);
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
