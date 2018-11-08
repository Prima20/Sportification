package com.rpl.kelompok7.sportification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class CheckAgendaActivity extends AppCompatActivity {


    private TextView pemesan, lapangan, waktu, slot;
    private Button joinAgenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_agenda);

        pemesan = findViewById(R.id.tv_pemesan);
        lapangan = findViewById(R.id.tv_lapangan);
        waktu = findViewById(R.id.tv_waktu);
        slot = findViewById(R.id.tv_slot);

        joinAgenda = findViewById(R.id.btn_join_agenda);

    }
}
