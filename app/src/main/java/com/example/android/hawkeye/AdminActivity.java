package com.example.android.hawkeye;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase4;
    private DatabaseReference mDatabaseReference4;
    private DatabaseReference mDatabaseReference6, mDatabaseReference5;
    private DatabaseReference mPushDatabaseReference4;


    private static final int TEZ_REQUEST_CODE = 123;

    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";



    UtilFunctions utilFunctions;

    String email_addr;
    String key;

    Button a_vehicle;
    Button a_guest_vehicle;


    TextView cid;
    Button lgt;
    Button vel;
    Button appruser, val, addgrd,parking,paym;

    String id;
    String si;
    String ukey;
    boolean fd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_admin);

        val = (Button) findViewById(R.id.validate);
        addgrd = (Button) findViewById(R.id.addgrd);

        cid = (TextView) findViewById(R.id.client_id);
        lgt = (Button) findViewById(R.id.logout);
        vel = (Button) findViewById(R.id.entrylog);
        appruser = (Button) findViewById(R.id.approve_user);
        parking=(Button)findViewById(R.id.parking);
        id = getIntent().getStringExtra("ID");
        //si=getIntent().getStringExtra("SOCIETY");
        a_vehicle = (Button) findViewById(R.id.add_vehicle);
        a_guest_vehicle = (Button) findViewById(R.id.add_guest);

        paym=(Button)findViewById(R.id.payment);

        utilFunctions = new UtilFunctions();
        mFirebaseDatabase4 = FirebaseDatabase.getInstance();
        mDatabaseReference4 = mFirebaseDatabase4.getReference().child("clients");
        mDatabaseReference6 = mFirebaseDatabase4.getReference().child("clients");
        mDatabaseReference5 = mFirebaseDatabase4.getReference().child("entrylog");

        fd = false;

        parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminActivity.this,Maps.class);
                intent.putExtra("ID",id);
                startActivity(intent);
            }
        });

        addgrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_key = displayFirebaseRegId();
                Intent i = new Intent(AdminActivity.this, SignUp.class);
                i.putExtra("Desc", "guard");

                i.putExtra("userkey", user_key);
                startActivity(i);
            }
        });

        paym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri =
                        new Uri.Builder()
                                .scheme("upi")
                                .authority("pay")
                                .appendQueryParameter("pa", "ayushvnit@oksbi")
                                .appendQueryParameter("pn", "Test Merchant")
                                .appendQueryParameter("mc", "123456789")
                                .appendQueryParameter("tr", "123456789")
                                .appendQueryParameter("tn", "Society Bills")
                                //  .appendQueryParameter("am", Integer.toString(rent))
                                .appendQueryParameter("cu", "INR")
                                .appendQueryParameter("url", "https://test.merchant.website")
                                .build();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                intent.putExtra("ID",id);
                intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME);
                startActivityForResult(intent, TEZ_REQUEST_CODE);
            }
        });

        val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(UserActivity.this,Validate.class);
//                intent.putExtra("ID",id);
                mDatabaseReference5.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("TAG", "gjvvhjkj " + (Boolean) snapshot.child("exit_status").getValue());
                            if (snapshot.child("uid").exists() && (!(Boolean) snapshot.child("exit_status").getValue()) && id.equals((String) snapshot.child("uid").getValue())) {
                                // email_addr=snapshot.child("email").getValue().toString();
                                // si = (String)snapshot.child("society_info").getValue();
                                //  Log.d("SET","SEtv the value of si"+si);
                                fd = true;
                                break;
                            }
                        }

                        if (fd) {
                            Intent intent = new Intent(AdminActivity.this, Validate.class);
                            intent.putExtra("ID", id);
                            startActivity(intent);

                        } else {
                            Toast.makeText(AdminActivity.this, "You do not have any invalid exits", Toast.LENGTH_SHORT).show();

                        }
//                        Log.d("SEN","Sending si"+si);
//                        intent.putExtra("SOCIETY",si);
//                        startActivity(intent);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        vel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, EntryLogActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);

            }
        });

        a_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent intent = new Intent(AdminActivity.this, MyVehiclesUser.class);
                intent.putExtra("ID", id);

                mDatabaseReference6.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("TAG", id + "Cpomap" + (String) snapshot.child("id").getValue());
                            if (id.equals((String) snapshot.child("id").getValue())) {
                                // email_addr=snapshot.child("email").getValue().toString();
                                si = (String) snapshot.child("society_info").getValue();
                                ukey=(String) snapshot.child("user_key").getValue();
                                Log.d("SET", "SEtv the value of si" + si);

                                Log.d("SET", "SEtv the value of si" + ukey);

                                break;
                            }
                        }

                        Log.d("SEN", "Sending guest si" + si);
                        Log.d("SEN", "Sending guest si" + si);
                        intent.putExtra("SOCIETY", si);
                        intent.putExtra("userkey",ukey);
                        startActivity(intent);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        a_guest_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent intent = new Intent(AdminActivity.this, MyGuest.class);
                intent.putExtra("ID", id);
               // startActivity(intent);
                mDatabaseReference6.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("TAG", id + "Cpomap" + (String) snapshot.child("id").getValue());
                            if (id.equals((String) snapshot.child("id").getValue())) {
                                // email_addr=snapshot.child("email").getValue().toString();
                                si = (String) snapshot.child("society_info").getValue();
                                ukey=(String) snapshot.child("user_key").getValue();


                                Log.d("SET", "SEtv the value of si" + si);
                                break;
                            }
                        }

                        Log.d("SEN", "Sending guest si" + si);
                        intent.putExtra("SOCIETY", si);
                        intent.putExtra("userkey",ukey);
                        startActivity(intent);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        lgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent intent = new Intent(AdminActivity.this, MainActivity.class);

                SaveSharedPreference.setUserName(AdminActivity.this, "");

                intent.putExtra("ID", id);


                mDatabaseReference4.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("TAG", id + "Cpomap" + (String) snapshot.child("id").getValue());
                            if (id.equals((String) snapshot.child("id").getValue())) {
                                email_addr = snapshot.child("email").getValue().toString();

                                Toast.makeText(AdminActivity.this, "Found User", Toast.LENGTH_LONG).show();

                                key = utilFunctions.getUser_key(email_addr);

                                mPushDatabaseReference4 = mFirebaseDatabase4.getReference().child("clients");
                                mPushDatabaseReference4.child(key).child("user_key").setValue("");

                                startActivity(intent);
                                break;
                            }
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        appruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this, UserLogActivity.class);
                i.putExtra("ID", id);
                startActivity(i);
            }
        });

        cid.setText(id);

    }


    @Override
    public void onBackPressed() {

        Toast.makeText(AdminActivity.this, "Bye", Toast.LENGTH_SHORT).show();

//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mDatabaseReference = mFirebaseDatabase.getReference().child("clients");

        Log.d("TAG", "Going back");


        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Log.d("TAG", "Going back");

        Toast.makeText(AdminActivity.this, "Bye", Toast.LENGTH_SHORT).show();

        intent.putExtra("ID", id);
        //intent.putExtra("KEY",key);
        startActivity(intent);
    }

    private String displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        //Log.e(TAG, "Firebase reg id: " + regId);

        //if (!TextUtils.isEmpty(regId))
            //txtRegId.setText("Firebase Reg Id: " + regId);
        //else
        //    txtRegId.setText("Firebase Reg Id is not received yet!");
        return regId;
    }
}
