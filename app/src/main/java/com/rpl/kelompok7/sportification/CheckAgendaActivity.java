package com.rpl.kelompok7.sportification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpl.kelompok7.sportification.Models.Agenda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CheckAgendaActivity extends AppCompatActivity {


    private TextView pemesan, lapangan, waktu, slot, keterangan, lokasiLapangan;
    private Button joinAgenda;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private FirebaseAuth mAuth;

    String idAgenda, userEmail;

    HistoryActivity history;
    Date tanggalAgenda;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_agenda);

        //Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        history = new HistoryActivity();

        pemesan = findViewById(R.id.tv_pemesan);
        lapangan = findViewById(R.id.tv_lapangan);
        waktu = findViewById(R.id.tv_waktu);
        slot = findViewById(R.id.tv_slot);
        keterangan = findViewById(R.id.tv_keterangan);
        lokasiLapangan = findViewById(R.id.tv_lokasiLapangan);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        pemesan.setText("Pembuat:" + getIntent().getExtras().getString("pembuat"));

        lapangan.setText(getIntent().getExtras().getString("lapangan"));

        waktu.setText(getIntent().getExtras().getString("waktuMulai") + ":" +
                getIntent().getExtras().getString("waktuSelesai"));

        slot.setText(getIntent().getExtras().getString("slotPemain"));

        keterangan.setText(getIntent().getExtras().getString("keterangan"));

        lokasiLapangan.setText(getIntent().getExtras().getString("lokasi"));

        try {
            tanggalAgenda = dateFormatter.parse(getIntent().getExtras().getString("tanggal"));
        }catch (Exception  e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        SetupFirebase();

        idAgenda = getIntent().getExtras().getString("idAgenda");

        joinAgenda = findViewById(R.id.btn_join_agenda);

        joinAgenda();

    }

    void SetupFirebase(){
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("agenda");

        mAuth = FirebaseAuth.getInstance();
        userEmail = mAuth.getCurrentUser().getEmail();
    }

    void joinAgenda(){
        joinAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Log.d("id agenda", idAgenda);

                final DatabaseReference mDbref = FirebaseDatabase.getInstance().getReference("agenda");

                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Agenda agenda = null;
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            Agenda value = dataSnapshot1.getValue(Agenda.class);

                            Log.d("Check", value.id + " && " + idAgenda);
                            if(idAgenda.equalsIgnoreCase(value.id)){
                                Log.d("Lapangan","Id tersedia");

                                //Get agenda you want to update
                                agenda = value;

                                }
                            }

                            //Add email
                            agenda.updateUserEmail(userEmail);

                            //Update email to database
                            mDbref.child(idAgenda).setValue(agenda);
                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            startActivity(intent);

                        Log.d("Lapangan","looping selesai");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
