package com.rpl.kelompok7.sportification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rpl.kelompok7.sportification.Models.Agenda;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckAgendaActivity extends AppCompatActivity {


    private TextView pemesan, lapangan, waktu, slot, keterangan, lokasiLapangan;
    private Button joinAgenda;

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

        joinAgenda = findViewById(R.id.btn_join_agenda);
        joinAgenda();

    }

    void joinAgenda(){
        joinAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DashboardActivity.canJoin){
                    Toast.makeText(getApplicationContext(),"Player joined",Toast.LENGTH_SHORT).show();
                    DashboardActivity.canJoin = false;
                    HistoryActivity.agendaList.add(0,new Agenda(tanggalAgenda));
                    Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Sudah join agenda",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
