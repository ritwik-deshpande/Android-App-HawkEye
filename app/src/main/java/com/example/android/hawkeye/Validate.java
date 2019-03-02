package com.example.android.hawkeye;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static com.example.android.hawkeye.MainActivity.type;

public class Validate extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private FirebaseDatabase mFirebaseDatabase2;
    private DatabaseReference mDatabaseReference7,mDatabaseReference8;
    Button val;
    Button den;
    String id;
    TextView cid, txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id=getIntent().getStringExtra("ID");
        setContentView(R.layout.activity_validate);
        val=(Button)findViewById(R.id.accept);
        cid=(TextView)findViewById(R.id.c_id);
        txt=(TextView)findViewById(R.id.txt);
        den=(Button)findViewById(R.id.deny);
        mFirebaseDatabase2=FirebaseDatabase.getInstance();
        mDatabaseReference7=mFirebaseDatabase2.getReference().child("entrylog");

        mDatabaseReference8=mFirebaseDatabase2.getReference().child("clients");
        cid.setText(id);
        if (type.equals("USR")) {
            txt.setText("User ID:");
        }
        else if (type.equals("ADM")) {
            txt.setText("Admin ID:");
        }

        val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                mDatabaseReference7.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // dataSnapshot.getRef().child("cl_status").setValue(true);

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (id.equals((String) snapshot.child("uid").getValue())  &&  !(Boolean)snapshot.child("exit_status").getValue()) {
                                snapshot.child("exit_status").getRef().setValue(true);
                                break;
                            }
                        }

//                        Intent intent = new Intent(context,AdminActivity.class);
//                        intent.putExtra("ID",cl.get(i).getId());

                        Toast.makeText(Validate.this,"Validated Exit of your Vehicle",Toast.LENGTH_SHORT).show();

                        if (type.equals("USR")) {
                            Intent i = new Intent(Validate.this, UserActivity.class);

                            i.putExtra("ID", id);
                            startActivity(i);
                        }
                        else if(type.equals("ADM")){
                            Intent i = new Intent(Validate.this, AdminActivity.class);

                            i.putExtra("ID", id);
                            startActivity(i);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });


            }
        });

    }
    void showPopUp(View v){

        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this );
        popupMenu.inflate(R.menu.pop_raise_alarm);
        popupMenu.show();





    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.callguard: {


                mDatabaseReference8.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // dataSnapshot.getRef().child("cl_status").setValue(true);

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (id.equals((String) snapshot.child("uid").getValue())  &&  !(Boolean)snapshot.child("exit_status").getValue()) {
                                snapshot.child("exit_status").getRef().setValue(true);
                                break;
                            }
                        }

//                        Intent intent = new Intent(context,AdminActivity.class);
//                        intent.putExtra("ID",cl.get(i).getId());



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });


                Intent intent = new Intent(Intent.ACTION_DIAL);



                intent.setData(Uri.parse("tel:" + "9820188402"));
                startActivity(intent);

            }
            break;
            case R.id.calladmin: {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "9820188402"));
                startActivity(intent);

            }
            break;
            case R.id.callpolice: {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "9820188402"));
                startActivity(intent);
            }
            break;
        }
        return true;
    }
}


