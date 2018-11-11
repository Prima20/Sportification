package com.rpl.kelompok7.sportification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheckAgendaActivity extends AppCompatActivity {


    private TextView pemesan, lapangan, waktu, slot;
    private Button joinAgenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_agenda);

        //Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        pemesan = findViewById(R.id.tv_pemesan);
        lapangan = findViewById(R.id.tv_lapangan);
        waktu = findViewById(R.id.tv_waktu);
        slot = findViewById(R.id.tv_slot);

        lapangan.setText(getIntent().getExtras().getString("lapangan"));
        waktu.setText(getIntent().getExtras().getString("waktuMulai") + ":" +
                getIntent().getExtras().getString("waktuSelesai"));
        slot.setText(getIntent().getExtras().getString("slotPemain"));

        joinAgenda = findViewById(R.id.btn_join_agenda);
        joinAgenda();

    }

    void joinAgenda(){
        joinAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Player joined",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}
