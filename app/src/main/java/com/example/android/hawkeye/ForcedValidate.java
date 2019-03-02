package com.example.android.hawkeye;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    String id2;
    EditText uuid;
    Boolean fd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forced_validate);
        val=(Button)findViewById(R.id.valid);

        mFirebaseDatabase3=FirebaseDatabase.getInstance();
        mDatabaseReference3=mFirebaseDatabase3.getReference().child("entrylog");

        id=getIntent().getStringExtra("ID");
//        ud=val.getText().toString();
        id2=id;

        fd=false;

        val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                uuid=(EditText)findViewById(R.id.accept);
                id=uuid.getText().toString();
                mDatabaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // dataSnapshot.getRef().child("cl_status").setValue(true);

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("TAG","szfdgvh   "+id+"    gggugg "+(String) snapshot.child("uid").getValue());
                            Log.d("TAG","XCVB  "+snapshot.child("exit_status").getValue());
                            if (id.equals((String) snapshot.child("uid").getValue())  && !(Boolean) snapshot.child("exit_status").getValue() ) {
                                snapshot.child("exit_status").getRef().setValue(true);
                                fd=true;
                                break;
                            }
                        }

//                        Intent intent = new Intent(context,AdminActivity.class);
//                        intent.putExtra("ID",cl.get(i).getId());

                        if(fd) {

                            Toast.makeText(ForcedValidate.this, "Validated Exit of your Vehicle", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ForcedValidate.this, GuardActivity.class);
                            i.putExtra("ID", id2);

                            startActivity(i);
                        }
                        else{
                            Toast.makeText(ForcedValidate.this, "InValid User Id", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
            }
        });
    }
}
