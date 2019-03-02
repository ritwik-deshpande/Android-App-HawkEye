package com.example.android.hawkeye;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForcedValidate extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase3;
    private DatabaseReference mDatabaseReference3;
    Button val;
    String ud;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forced_validate);
        val=(Button)findViewById(R.id.valid);

        mFirebaseDatabase3=FirebaseDatabase.getInstance();
        mDatabaseReference3=mFirebaseDatabase3.getReference().child("entrylog");

        id=getIntent().getStringExtra("ID");
        ud=val.getText().toString();

        val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // dataSnapshot.getRef().child("cl_status").setValue(true);

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (ud.equals((String) snapshot.child("uid").getValue())) {
                                snapshot.child("exit_status").getRef().setValue(true);
                                break;
                            }
                        }

//                        Intent intent = new Intent(context,AdminActivity.class);
//                        intent.putExtra("ID",cl.get(i).getId());

                        Toast.makeText(ForcedValidate.this,"Validated Exit of your Vehicle",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ForcedValidate.this,GuardActivity.class);
                        i.putExtra("ID",id);
                        startActivity(i);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
            }
        });
    }
}
