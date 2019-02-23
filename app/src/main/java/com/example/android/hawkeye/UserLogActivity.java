package com.example.android.hawkeye;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserLogActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase5;
    private DatabaseReference mDatabaseReference5;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterApprove r;

    ArrayList<Client> cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log);

        cl=new ArrayList<Client>();

        mFirebaseDatabase5 = FirebaseDatabase.getInstance();
        mDatabaseReference5 = mFirebaseDatabase5.getReference().child("clients");

        mDatabaseReference5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if("admin".equals((String) snapshot.child("desc").getValue())){

                    }
                    else
                        cl.add(snapshot.getValue(Client.class));
                }
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    public void updateUI(){

        recyclerView = findViewById(R.id.userlogs);
        Log.d("TAG","Inside update ui function");
        //recyclerView.setHasFixedSize(true);
        r=new RecyclerViewAdapterApprove(cl,this);
        Log.d("TAG","The size of lst:"+cl.size());
        recyclerView.setAdapter(r);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));


        //recyclerView.scrollToPosition(0);
        //pg.setVisibility(View.GONE);
    }
}
