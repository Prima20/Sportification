package com.rpl.kelompok7.sportification.CreateAgenda;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.rpl.kelompok7.sportification.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BuatAgenda extends AppCompatActivity{


    //Text pilih waktu
    private TextView pilihWaktuMulai, pilihWaktuSelesai;

    //Button untuk menambah dan mengurangi pemain
    private Button tambahPemain, kurangiPemain;

    //Text jumlah pemain
    private TextView textPemain;

    //Spinner list lapangan
    private Spinner pilihLapangan;

    //Button buat agenda
    private Button buatAgenda;

    private int jumlahPemain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buat_agenda);

        pilihWaktuMulai = findViewById(R.id.tv_pilihWaktuMulai);
        pilihWaktuSelesai = findViewById(R.id.tv_pilihWaktuSelesai);

        tambahPemain = findViewById(R.id.btn_plus);
        kurangiPemain = findViewById(R.id.btn_minus);
        textPemain = findViewById(R.id.tv_player);

        pilihLapangan = findViewById(R.id.spn_lapangan);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            String code = (String)bundle.get("codeBooking");
            Log.d("Code",code);
        }

        //Hanya untuk dummy test lapangan
        List<String> lapangan = new ArrayList<String>();
        lapangan.add("SM Futsal Lapangan 1");
        lapangan.add("SM Futsal Lapangan 2");
        lapangan.add("Angkasa Futsal lapangan 1");
        lapangan.add("Angkasa Futsal lapangan 2");
        lapangan.add("Angkasa Futsal lapangan 3");

        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lapangan);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        pilihLapangan.setAdapter(dataAdapter);

        //Click text waktu mulai
        pilihWaktuMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Menampilkan TimePickerDialog
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                final int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(BuatAgenda.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinutes) {
                        pilihWaktuMulai.setText( String.format("%02d:%02d", selectedHour, selectedMinutes));
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        //Click text waktu selesai
        pilihWaktuSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Menampilkan TimePickerDialog
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                final int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(BuatAgenda.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinutes) {
                        pilihWaktuSelesai.setText( String.format("%02d:%02d", selectedHour, selectedMinutes));
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        //Klik button tambah
        tambahPemain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlahPemain+=1;
                if(jumlahPemain <= 10){
                    textPemain.setText(jumlahPemain + "/10");
                }else {
                    jumlahPemain-=1;
                }
            }
        });


        //click button kurang
        kurangiPemain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlahPemain-=1;
                if(jumlahPemain >= 0){
                    textPemain.setText(jumlahPemain + "/10");
                }else{
                    jumlahPemain+=1;
                }
            }
        });


    }
}
