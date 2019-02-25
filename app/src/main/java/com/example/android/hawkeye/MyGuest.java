package com.example.android.hawkeye;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyGuest extends AppCompatActivity {

    ArrayList<vehicle_guest> myv;
    Button addv;
    //Button rv;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterGuest r;
    String id;
    String si;

    ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_guest);


        myv=new ArrayList<vehicle_guest>();





        id=getIntent().getStringExtra("ID");

        si=getIntent().getStringExtra("SOCIETY");

        btn=findViewById(R.id.back_button);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("guest_vehicles");

        addv=(Button)findViewById(R.id.add);
        //rv=(Button)findViewById(R.id.remove);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id.substring(0,3).equals("USR")) {
                    Intent i = new Intent(MyGuest.this, UserActivity.class);
                    i.putExtra("ID", id);
                    startActivity(i);
                }
                if(id.substring(0,3).equals("ADM")) {
                    Intent i = new Intent(MyGuest.this, AdminActivity.class);
                    i.putExtra("ID", id);
                    startActivity(i);
                }

            }
        });

        addv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyGuest.this,AddGuestActivity.class);
                i.putExtra("ID",id);
                i.putExtra("SOCIETY",si);
                startActivity(i);
            }
        });

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(id.equals((String) snapshot.child("userid").getValue())){
                        myv.add(snapshot.getValue(vehicle_guest.class));
                    }
                }
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void updateUI(){

        recyclerView=(RecyclerView)findViewById(R.id.myguest);

        Log.d("TAG","Inside update ui function");
        //recyclerView.setHasFixedSize(true);
        r=new RecyclerViewAdapterGuest(myv,this);
        Log.d("TAG","The size of lst:"+myv.size());
        recyclerView.setAdapter(r);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));


        //recyclerView.scrollToPosition(0);
        //pg.setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed(){

        if(id.substring(0,3).equals("USR")) {
            Intent i = new Intent(MyGuest.this, UserActivity.class);
            i.putExtra("ID", id);
            startActivity(i);
        }
        if(id.substring(0,3).equals("ADM")) {
            Intent i = new Intent(MyGuest.this, AdminActivity.class);
            i.putExtra("ID", id);
            startActivity(i);
        }

    }
}
