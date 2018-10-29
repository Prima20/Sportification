package com.rpl.kelompok7.sportification.CreateAgenda;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rpl.kelompok7.sportification.R;

public class CheckCode extends AppCompatActivity {

    private EditText codeText;
    private Button buttonCheck;

    private boolean m_statusCode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_code);

        codeText = findViewById(R.id.edt_code);
        buttonCheck = findViewById(R.id.btn_check);

        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(m_statusCode){
                    Intent intent = new Intent(view.getContext(), BuatAgenda.class);
                    intent.putExtra("codeBooking",codeText.getText().toString());
                    startActivity(intent);
                }else{
                    Toast.makeText(view.getContext(),"Code Booking salah",Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
