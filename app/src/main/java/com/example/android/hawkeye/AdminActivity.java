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

public class AdminActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase4;
    private DatabaseReference mDatabaseReference4;
    private DatabaseReference mPushDatabaseReference4;

    UtilFunctions utilFunctions;

    String email_addr;
    String key;


    TextView cid;
    Button lgt;
    Button vel;
    Button appruser;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            this.getSupportActionBar().hide();
        }
        catch(NullPointerException e){}
        setContentView(R.layout.activity_admin);
        cid=(TextView)findViewById(R.id.client_id);
        lgt=(Button)findViewById(R.id.logout);
        vel=(Button)findViewById(R.id.entrylog);
        appruser=(Button)findViewById(R.id.approve_user);
        id=getIntent().getStringExtra("ID");

        utilFunctions= new UtilFunctions();
        mFirebaseDatabase4=FirebaseDatabase.getInstance();
        mDatabaseReference4=mFirebaseDatabase4.getReference().child("clients");

        vel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminActivity.this,EntryLogActivity.class);
                intent.putExtra("ID",id);
                startActivity(intent);
            }
        });

        lgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminActivity.this,MainActivity.class);

                SaveSharedPreference.setUserName(AdminActivity.this,"");

                intent.putExtra("ID",id);


                mDatabaseReference4.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("TAG",id+"Cpomap"+(String)snapshot.child("id").getValue());
                            if(id.equals((String)snapshot.child("id").getValue())){
                                email_addr=snapshot.child("email").getValue().toString();

                                Toast.makeText(AdminActivity.this, "Found User", Toast.LENGTH_LONG).show();

                                key = utilFunctions.getUser_key(email_addr);

                                mPushDatabaseReference4 = mFirebaseDatabase4.getReference().child("clients");
                                mPushDatabaseReference4.child(key).child("user_key").setValue("");

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

        appruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this,UserLogActivity.class);
                i.putExtra("ID",id);
                startActivity(i);
            }
        });

        cid.setText(id);

    }

    @Override
    public void onBackPressed(){

        Toast.makeText(AdminActivity.this, "Bye", Toast.LENGTH_SHORT).show();

//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mDatabaseReference = mFirebaseDatabase.getReference().child("clients");

        Log.d("TAG","Going back");


        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Log.d("TAG","Going back");

        Toast.makeText(AdminActivity.this, "Bye", Toast.LENGTH_SHORT).show();

        intent.putExtra("ID",id);
        //intent.putExtra("KEY",key);
        startActivity(intent);
    }
}
