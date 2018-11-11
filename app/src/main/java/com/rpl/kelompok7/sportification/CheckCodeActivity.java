package com.rpl.kelompok7.sportification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CheckCodeActivity extends AppCompatActivity {

    private Button checkCode;
    private EditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);

        checkCode = findViewById(R.id.btn_check_code);
        code = findViewById(R.id.edt_check_code);

        checkCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(code.getText().toString().equals("1234")){
                    Intent intent = new Intent(view.getContext(), CreateAgendaActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(view.getContext(),"Code booking salah",Toast.LENGTH_SHORT);
                }
            }
        });

    }
}
