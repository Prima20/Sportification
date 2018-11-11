package com.rpl.kelompok7.sportification;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rpl.kelompok7.sportification.Models.Agenda;

import java.util.Calendar;

public class CreateAgendaActivity extends AppCompatActivity {


    private EditText namaPemesan, slotPemain, lapanganAgenda, keteranganAgenda;
    private Button submitAgenda;
    private TextView waktuMulai, waktuSelesai;

    private String pembuat, mulai, selesai, keterangan,lapangan, slot;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_agenda);

        namaPemesan = findViewById(R.id.edt_nama_penyewa);
        slotPemain = findViewById(R.id.edt_slot);
        lapanganAgenda = findViewById(R.id.edt_lapangan);

        submitAgenda = findViewById(R.id.button_submit_agenda);

        waktuMulai = findViewById(R.id.waktu_mulai);
        waktuSelesai = findViewById(R.id.waktu_selesai);

        keteranganAgenda = findViewById(R.id.edt_keterangan);

        setTimePicker();
        firebaseSetup();
        submitAgenda();


    }

    //Setup firebase
    void firebaseSetup(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    //Set data from layout to class variable
    void setData(){
        mulai = waktuMulai.getText().toString();
        selesai = waktuSelesai.getText().toString();
        keterangan = keteranganAgenda.getText().toString();
        pembuat = namaPemesan.getText().toString();
        slot = slotPemain.getText().toString();
        lapangan = lapanganAgenda.getText().toString();
    }

    //Submit agendaList
    //Add data from variabel to class, than add to firebase
    void submitAgenda(){
        final int slot = 10;

        submitAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("agenda");

                String agendaId = mDatabase.push().getKey();

                setData();

                //Agenda agenda = new Agenda(mulai,selesai,pembuat,keterangan,lapangan,slot,0);

               // mDatabase.child(agendaId).setValue(agenda);

                //Intent intent = new Intent(view.getContext(), DashboardActivity.class);
                //startActivity(intent);

            }
        });

    }

    //Set time from TimePickerDialog
    void setTimePicker(){
        waktuMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker = new TimePickerDialog(CreateAgendaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        waktuMulai.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                },hour, minute, true);
                mTimePicker.show();

            }
        });

        waktuSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker = new TimePickerDialog(CreateAgendaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        waktuSelesai.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                },hour, minute, true);
                mTimePicker.show();

            }
        });



    }
}
