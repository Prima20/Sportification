package com.rpl.kelompok7.sportification;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpl.kelompok7.sportification.Models.Agenda;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private  DashboardAdapter mAdapter;
    ArrayList<Agenda> agendaList;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private FirebaseAuth mAuth;

    private DrawerLayout mDrawerLayout;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.item_logout:
                Toast.makeText(getApplicationContext(),"User logout",Toast.LENGTH_SHORT);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        recyclerView = findViewById(R.id.recycler_view);


        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("agenda");
        agendaList = new ArrayList<>();

        //Navigation drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        displayView(menuItem.getItemId());
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });



        //Get data from firebase
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Log.d("Database","run");
                    Agenda value = dataSnapshot1.getValue(Agenda.class);
                    if(value.usernamePembuat != null){
                        agendaList.add(new Agenda(value.id,value.tanggalAgenda,value.waktuMulai,value.waktuSelesai,value.usernamePembuat,value.keterangan,value.lapangan,
                                value.jumlahSlot,value.codeBooking));
                    }
                }

                mAdapter = new DashboardAdapter(agendaList,DashboardActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Test", "Failed to read value.", databaseError.toException());
            }
        });

    }

    void displayView(int id){

        switch (id){
            //Move to checkCodeActivity to create agenda
            case R.id.item_create_agenda:
                Intent intent = new Intent(getApplicationContext(), CheckCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.item_history:
                seeHistory();
                break;
            case R.id.item_logout:
                Toast.makeText(getApplicationContext(),"User logout",Toast.LENGTH_SHORT).show();
                logout();
                break;
        }
    }

    void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    void seeHistory(){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}
