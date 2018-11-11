package com.rpl.kelompok7.sportification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rpl.kelompok7.sportification.Models.Agenda;
import com.rpl.kelompok7.sportification.Models.Penyedia;


public class CreateLapanganActivity extends AppCompatActivity {

    private EditText noLapangan, alamat;
    private Button submitLapangan;
    private String lapangan, address, jamMulai, jamSelesai, generateCode;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lapangan);

        noLapangan = findViewById(R.id.edt_Nolapangan_penyedia);
        submitLapangan = findViewById(R.id.button_submit_lapangan);
        alamat = findViewById(R.id.edt_alamat);

        firebaseSetup();
        submitLapangan();

    }

    void setData(){
        lapangan = noLapangan.getText().toString();
        address = alamat.getText().toString();

    }

    void submitLapangan(){

        submitLapangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                Penyedia penyedia = new Penyedia(lapangan, address);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("penyedia");
                String penyediaId = mDatabase.push().getKey();


               // Penyedia penyedia = new Penyedia(lapangan, address, jamMulai, jamSelesai, kode);


                mDatabase.child(penyediaId).setValue(penyedia);

                Intent intent = new Intent(view.getContext(), DashboardPenyediaActivity.class);
                startActivity(intent);

            }
        });

    }

    //setup firebase
    void firebaseSetup(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
}
