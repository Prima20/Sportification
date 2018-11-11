package com.rpl.kelompok7.sportification;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.content.Intent;

import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;
import java.util.Random;

public class BookingActivity extends AppCompatActivity {

    private TextView jamMulai, jamSelesai, generateCode;
    private String mulai, selesai, code;
    private Button submitBooking;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        jamMulai = findViewById(R.id.tv_waktu_mulai_booking);
        jamSelesai = findViewById(R.id.tv_waktu_selesai_booking);
        generateCode = findViewById(R.id.tv_gen_code);
        submitBooking = findViewById(R.id.btn_submit_booking);

        setData();
        setPicker();

        submitBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DashboardPenyediaActivity.class);
                startActivity(intent);
            }
        });


    }

    void setData(){
        mulai = jamMulai.getText().toString();
        selesai = jamSelesai.getText().toString();
        code = generateCode.getText().toString();
    }
    
    void setPicker(){
        jamMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        jamMulai.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                },hour, minute, true);
                mTimePicker.show();

            }
        });

        jamSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        jamSelesai.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                },hour, minute, true);
                mTimePicker.show();

            }
        });

        generateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int number = random.nextInt(100)+1;
                String myString = String.valueOf(number);
                generateCode.setText(myString);
            }
        });
    }


}

