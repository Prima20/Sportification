package com.rpl.kelompok7.sportification;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpl.kelompok7.sportification.Models.Agenda;
import com.rpl.kelompok7.sportification.Models.Lapangan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateAgendaActivity extends AppCompatActivity {


    private EditText namaPemesan, slotPemain, lapanganAgenda, keteranganAgenda;
    private Button submitAgenda;
    private TextView waktuMulai, waktuSelesai, tanggal;
    private Spinner spinnerLapangan;

    private String codeBooking, pembuat, mulai, selesai, keterangan,slot;
    private Date tanggalAgenda;
    private Lapangan lapangan;

    Calendar dateSelected = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;

    //Fairebase atributes
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private ArrayList<Lapangan> lapanganList;
    SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_agenda);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        namaPemesan = findViewById(R.id.edt_nama_penyewa);
        slotPemain = findViewById(R.id.edt_slot);
//        lapanganAgenda = findViewById(R.id.edt_lapangan);

        submitAgenda = findViewById(R.id.button_submit_agenda);

        waktuMulai = findViewById(R.id.waktu_mulai);
        waktuSelesai = findViewById(R.id.waktu_selesai);
        tanggal = findViewById(R.id.tanggal);

        keteranganAgenda = findViewById(R.id.edt_keterangan);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        getCodeBooking();

        //call method below on onCreate method
        firebaseSetup();
        setSpinnerLapangan();
        setTimePicker();
        submitAgenda();
        setDatetime();
    }

    //Get code booking
    void getCodeBooking(){
        Bundle extra = getIntent().getExtras();
        codeBooking = extra.getString("codeBooking");
    }

    //Set spinner item from firebase
        void setSpinnerLapangan(){
        spinnerLapangan = findViewById(R.id.spn_lapangan);
        lapanganList = new ArrayList<>();

        DatabaseReference mReference = mDatabase.getReference("lapangan");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Lapangan value = dataSnapshot1.getValue(Lapangan.class);
                    lapanganList.add(new Lapangan(value.getLapangan(), value.getLokasi()));
                }
                ArrayAdapter<Lapangan> adapter  = new
                        ArrayAdapter<Lapangan>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, lapanganList);

                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                spinnerLapangan.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spinnerLapangan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                lapangan = (Lapangan) parent.getItemAtPosition(position);

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + lapangan.getLapangan(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    //Setup firebase
    void firebaseSetup(){
        mDatabase = FirebaseDatabase.getInstance();
    }

    //Set data from layout to class variable
    void setData(){
        mulai = waktuMulai.getText().toString();
        selesai = waktuSelesai.getText().toString();
        keterangan = keteranganAgenda.getText().toString();
        pembuat = namaPemesan.getText().toString();
        slot = slotPemain.getText().toString();
//        lapangan = lapanganAgenda.getText().toString();
    }

    //Submit agendaList
    //Add data from variabel to class, than add to firebase
    void submitAgenda(){
        final int slot = 10;

        submitAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("agenda");

                setData();

                //agendaId become codeBooking
                Agenda agenda = new Agenda(codeBooking,tanggalAgenda,mulai,selesai,pembuat,keterangan,lapangan,slot,codeBooking);

                mDatabase.child(codeBooking).setValue(agenda);

                Intent intent = new Intent(view.getContext(), DashboardActivity.class);
                startActivity(intent);

            }
        });

    }

    //Add listener to Textview
    void setDatetime(){
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateTimeField();
            }
        });
    }

    //Make DatePickerDialog than add value to TextView
    private void setDateTimeField() {

        Calendar newCalendar = dateSelected;
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateSelected.set(year, monthOfYear, dayOfMonth, 0, 0);
                //Set date to variable
                tanggalAgenda = dateSelected.getTime();
                //Set date to Text
                tanggal.setText("Tanggal: " + dateFormatter.format(dateSelected.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//        tanggal.setText(dateFormatter.format(dateSelected.getTime()));
        datePickerDialog.show();
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
