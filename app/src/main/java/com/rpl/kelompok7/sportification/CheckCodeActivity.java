package com.rpl.kelompok7.sportification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CheckCodeActivity extends AppCompatActivity {

    private Button checkCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);

        checkCode = findViewById(R.id.btn_check_code);

        checkCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateAgendaActivity.class);
                startActivity(intent);
            }
        });

    }
}
