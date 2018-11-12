package com.rpl.kelompok7.sportification;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rpl.kelompok7.sportification.Models.User;
import com.rpl.kelompok7.sportification.Utils.FirebaseMethods;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;

    private static final String TAG = "RegisterActivity";
    private static String append;
    private Context mContext;
    private ProgressBar mProgressBar;
    private EditText mEmail , mUserName , mPassword , mRepassword;
    private String email , username , password , repassword , role;
    private Button registerButton;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = RegisterActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);
        mProgressBar = (ProgressBar) findViewById(R.id.register_progresbar);
        mProgressBar.setVisibility(View.GONE);
        mEmail = (EditText) findViewById(R.id.register_email);
        mUserName = (EditText) findViewById(R.id.register_user_name);
        mPassword = (EditText) findViewById(R.id.register_password);
        mRepassword = (EditText) findViewById(R.id.register_repassword);
        registerButton = (Button) findViewById(R.id.register_button);
        mSpinner = (Spinner) findViewById(R.id.register_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this ,
                R.array.role ,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        setupFirebaseAuth();
        init();
    }

    private void init(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                username = mUserName.getText().toString();
                password = mPassword.getText().toString();
                repassword = mRepassword.getText().toString();

                if(isStringNull(email) || isStringNull(username) || isStringNull(password) || isStringNull(repassword)){
                    Toast.makeText(mContext , "isi semua input field terlebih dahulu" , Toast.LENGTH_SHORT).show();
                } else if(!password.equals(repassword)){
                    Toast.makeText(mContext , "password dan repassword tidak cocok" , Toast.LENGTH_SHORT).show();
                }
                else{
                    mProgressBar.setVisibility(View.VISIBLE);
                    firebaseMethods.registerNewEmail(email , username , password );
                }
            }
        });
    }

    private boolean isStringNull(String string){
        if(string.equals("")){
            return true;
        }else {
            return false;
        }
    }

    /*
    * ==========================================firebase===============================================
    */
    private void checkIfUsernameExists(final String username) {
        Log.d(TAG, "checkIfUsernameExists: Checking if  " + username + " already exists.");



        String mUsername = "";
        mUsername = username ;


        //add new user to the database
        firebaseMethods.addNewUser(email, mUsername,role);

        mAuth.signOut();
//
//        Query query = reference
//                .child(getString(R.string.db_users))
//                .orderByChild(getString(R.string.db_field_username))
//                .equalTo(username);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
//                    if (singleSnapshot.exists()){
//                        Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + singleSnapshot.getValue(User.class).getUsername());
//                        append = myRef.push().getKey().substring(3,10);
//                        Log.d(TAG, "onDataChange: username already exists. Appending random string to name: " + append);
//                    }
//                }
//
//                String mUsername = "";
//                mUsername = username + append;
//
//
//                //add new user to the database
//                firebaseMethods.addNewUser(email, mUsername,role);
//
//                mAuth.signOut();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            checkIfUsernameExists(username);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    finish();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        this.role = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        this.role = adapterView.getItemAtPosition(0).toString();
    }
}
