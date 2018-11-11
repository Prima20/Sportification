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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckCodeActivity extends AppCompatActivity {

    private Button checkCode;
    private EditText code;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);


        checkCode = findViewById(R.id.btn_check_code);
        code = findViewById(R.id.edt_check_code);

        firebaseSetup();
        checkCode();

    }

    void checkCode(){
        checkCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(code.getText().toString())){
                            Intent intent = new Intent(view.getContext(), CreateAgendaActivity.class);
                            intent.putExtra("codeBooking",code.getText().toString());
                            startActivity(intent);
                        }else{
                            Toast.makeText(CheckCodeActivity.this, "Code Booking salah", Toast.LENGTH_SHORT);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    void firebaseSetup(){
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("agenda");
    }


}
