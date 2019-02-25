package com.example.android.hawkeye;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EntryLogActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterLog r;
    ImageButton btn;
    String id;

    ArrayList<EntryLog> el;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_log);
        el=new ArrayList<EntryLog>();
        id=getIntent().getStringExtra("ID");

        btn = (ImageButton)findViewById(R.id.back_button);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("entrylog");

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    el.add(snapshot.getValue(EntryLog.class));
                }
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if(id.substring(0,3).equals("GRD")) {
                    i= new Intent(EntryLogActivity.this, GuardActivity.class);
                    i.putExtra("ID",id);
                    startActivity(i);
                }
                if(id.substring(0,3).equals("ADM")) {
                    i= new Intent(EntryLogActivity.this, AdminActivity.class);
                    i.putExtra("ID",id);
                    startActivity(i);
                }

            }
        });

    }
    public void updateUI(){

        recyclerView = findViewById(R.id.entrylogs);
        Log.d("TAG","Inside update ui function");
        //recyclerView.setHasFixedSize(true);
        r=new RecyclerViewAdapterLog(el,this);
        Log.d("TAG","The size of lst:"+el.size());
        recyclerView.setAdapter(r);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));


        //recyclerView.scrollToPosition(0);
        //pg.setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed(){

        Intent i;
        if(id.substring(0,3).equals("GRD")) {
            i= new Intent(EntryLogActivity.this, GuardActivity.class);
            i.putExtra("ID",id);
            startActivity(i);
        }
        if(id.substring(0,3).equals("ADM")) {
            i= new Intent(EntryLogActivity.this, AdminActivity.class);
            i.putExtra("ID",id);
            startActivity(i);
        }
    }
}
