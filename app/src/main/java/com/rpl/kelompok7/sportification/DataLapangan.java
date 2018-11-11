package com.rpl.kelompok7.sportification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rpl.kelompok7.sportification.Models.Agenda;
import com.rpl.kelompok7.sportification.Models.Lapangan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DataLapangan extends AppCompatActivity {

    private EditText namaLapangan, alamat, ket, lapanganAgenda;
    private TextView generateCode, jamMulai, jamSelesai;
    private Button submitLapangan;
    private String namaLap, address;
    private DatabaseReference mDatabase;
    private int kode;
    private String pembuat, mulai, selesai, keterangan, lap, id, tanggal, code;
    private Date tanggalAgenda;



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
        setContentView(R.layout.activity_data_lapangan);

        namaLapangan = findViewById(R.id.edt_namaLapangan);
        alamat = findViewById(R.id.edt_alamat);
        generateCode = findViewById(R.id.tv_generateCode);
        submitLapangan = findViewById(R.id.btn_submitLapangan);

        jamMulai = findViewById(R.id.waktu_mulai);
        jamSelesai = findViewById(R.id.waktu_selesai);
        lapanganAgenda = findViewById(R.id.edt_lapangan);
        ket = findViewById(R.id.edt_keterangan);


        firebaseSetup();
        GenerateCode();
        submitLapangan();




    }

    void setData(){
        namaLap = namaLapangan.getText().toString();
        address = alamat.getText().toString();
        tanggal = "01/01/1990";
        try {
            tanggalAgenda = new SimpleDateFormat("dd/MM/yyyy").parse(tanggal);
        }catch (Exception ex){
            ex.getMessage();
        }

        mulai = "0";
        selesai = "0";
        keterangan = "0";
        lap = "0";
        //mulai = jamMulai.getText().toString();
        //selesai = jamSelesai.getText().toString();
        //keterangan = ket.getText().toString();
        //lap = lapanganAgenda.getText().toString();
        //kode = generateCode.getText().toString();
        //kode = Integer.parseInt(generateCode);




    }

    void GenerateCode(){
        generateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int number = random.nextInt(1000)+1;
                code = String.valueOf(number);
                generateCode.setText(code);
                id = String.valueOf(number);
            }
        });
    }

    void submitLapangan(){
        final int slot = 0;

        submitLapangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                Lapangan lapangan = new Lapangan(namaLap, address);

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("lapangan");
                String lapanganId = mDatabase.push().getKey();
                mDatabase.child(lapanganId).setValue(lapangan);

                Agenda agenda = new Agenda(id,tanggalAgenda, mulai, selesai, pembuat, keterangan, lapangan, slot, code );
                DatabaseReference dr = FirebaseDatabase.getInstance().getReference("agenda");
                String agendaId = String.valueOf(code);
                dr.child(agendaId).setValue(agenda);

//                Agenda agenda = new Agenda(mulai,selesai,pembuat,keterangan,lap,slot,number);
//                agenda.setId(number);
//                //Agenda agenda = new Agenda(number, number);
//                DatabaseReference dr = FirebaseDatabase.getInstance().getReference("agenda");
//                String agendaId = String.valueOf(number);
//                dr.child(agendaId).setValue(agenda);

                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    void firebaseSetup(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
