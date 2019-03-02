package com.example.android.hawkeye;

import android.content.Intent;
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

public class UserActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase2;
    private DatabaseReference mDatabaseReference2,mDatabaseReference3,mDatabaseReference4,mDatabaseReference5;
    private DatabaseReference mPushDatabaseReference2;
    UtilFunctions utilFunctions;
    TextView cid;
    String id;
    String si;
    Button av;
    Button agv;
    Button logout;
    Button val;
    String email_addr;
    String key;
    Button map_btn;
    Boolean fd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            this.getSupportActionBar().hide();
        }
        catch(NullPointerException e){}
        Log.d("TAG","UserActivity Strated");
        utilFunctions= new UtilFunctions();
        setContentView(R.layout.activity_user);
        cid=(TextView)findViewById(R.id.client_id);
        id=getIntent().getStringExtra("ID");
       // si=getIntent().getStringExtra("SOCIETY");
        Log.d("TAG","The recieved id is"+id);
        cid.setText(id);
        av=(Button)findViewById(R.id.add_vehicle);
        agv=(Button)findViewById(R.id.add_guest);
        logout=(Button)findViewById(R.id.logout);
        val=(Button)findViewById(R.id.validate);
        mFirebaseDatabase2=FirebaseDatabase.getInstance();
        map_btn=(Button)findViewById(R.id.map_btn);
        mDatabaseReference2=mFirebaseDatabase2.getReference().child("clients");
        mDatabaseReference3=mFirebaseDatabase2.getReference().child("clients");
        mDatabaseReference4=mFirebaseDatabase2.getReference().child("clients");
        mDatabaseReference5=mFirebaseDatabase2.getReference().child("entrylog");

        fd=false;


        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(UserActivity.this,Maps.class);
                startActivity(intent);
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
                            Log.d("TAG","gjvvhjkj "+(Boolean) snapshot.child("exit_status").getValue());
                            if(snapshot.child("uid").exists() && (!(Boolean) snapshot.child("exit_status").getValue()) && id.equals((String) snapshot.child("uid").getValue())){
                                // email_addr=snapshot.child("email").getValue().toString();
                               // si = (String)snapshot.child("society_info").getValue();
                              //  Log.d("SET","SEtv the value of si"+si);
                                fd=true;
                                break;
                            }
                        }

                        if(fd){
                            Intent intent = new Intent(UserActivity.this,Validate.class);
                            intent.putExtra("ID",id);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(UserActivity.this, "You do not have any invalidated exits", Toast.LENGTH_SHORT).show();

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



        av.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(UserActivity.this,MyVehiclesUser.class);
                intent.putExtra("ID",id);

                mDatabaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("TAG",id+"Cpomap"+(String)snapshot.child("id").getValue());
                            if(id.equals((String)snapshot.child("id").getValue())){
                                // email_addr=snapshot.child("email").getValue().toString();
                                si = (String)snapshot.child("society_info").getValue();
                                Log.d("SET","SEtv the value of si"+si);
                                break;
                            }
                        }

                        Log.d("SEN","Sending si"+si);
                        intent.putExtra("SOCIETY",si);
                        startActivity(intent);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





            }
        });

        agv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(UserActivity.this,MyGuest.class);
                intent.putExtra("ID",id);

                mDatabaseReference4.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("TAG",id+"Cpomap"+(String)snapshot.child("id").getValue());
                            if(id.equals((String)snapshot.child("id").getValue())){
                                // email_addr=snapshot.child("email").getValue().toString();
                                si = (String)snapshot.child("society_info").getValue();
                                Log.d("SET","SEtv the value of si"+si);
                                break;
                            }
                        }

                        Log.d("SEN","Sending guest si"+si);
                        intent.putExtra("SOCIETY",si);
                        startActivity(intent);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this,MainActivity.class);

                SaveSharedPreference.setUserName(UserActivity.this,"");

                intent.putExtra("ID",id);


                mDatabaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("TAG",id+"Cpomap"+(String)snapshot.child("id").getValue());
                            if(id.equals((String)snapshot.child("id").getValue())){
                                email_addr=snapshot.child("email").getValue().toString();

                                Toast.makeText(UserActivity.this, "Found User", Toast.LENGTH_LONG).show();

                                key = utilFunctions.getUser_key(email_addr);

                                mPushDatabaseReference2 = mFirebaseDatabase2.getReference().child("clients");
                                mPushDatabaseReference2.child(key).child("user_key").setValue("");

                                break;
                            }
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed(){

        Toast.makeText(UserActivity.this, "Bye", Toast.LENGTH_SHORT).show();

//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mDatabaseReference = mFirebaseDatabase.getReference().child("clients");

        Log.d("TAG","Going back");


        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Log.d("TAG","Going back");

        Toast.makeText(UserActivity.this, "Bye", Toast.LENGTH_SHORT).show();

        intent.putExtra("ID",id);
        //intent.putExtra("KEY",key);
        startActivity(intent);
    }
}
