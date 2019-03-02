package com.example.android.hawkeye;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class GuardActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private FirebaseDatabase mFirebaseDatabase3;
    private DatabaseReference mDatabaseReference3;
    private DatabaseReference mPushDatabaseReference3;

    String email_addr;
    String key;

    TextView cid;
    Button vel;
    Button ra;
    Button lo;
    Button fv;
    String id;

    UtilFunctions utilFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try{
            this.getSupportActionBar().hide();
        }
        catch(NullPointerException e){}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard);
        cid=(TextView)findViewById(R.id.client_id);
        id=getIntent().getStringExtra("ID");
        cid.setText(id);
        vel=(Button)findViewById(R.id.entry_log);
        lo=(Button)findViewById(R.id.logout);
        ra=(Button)findViewById(R.id.raise);
        fv=(Button)findViewById(R.id.forced_validate);

        utilFunctions= new UtilFunctions();
        mFirebaseDatabase3=FirebaseDatabase.getInstance();
        mDatabaseReference3=mFirebaseDatabase3.getReference().child("clients");


//        ra.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Cliked r button", Toast.LENGTH_LONG).show();
//
//            }
//        });

        fv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(GuardActivity.this,ForcedValidate.class);
                intent.putExtra("ID",id);
                startActivity(intent);
            }
        });

        vel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(GuardActivity.this,EntryLogActivity.class);
                intent.putExtra("ID",id);
                startActivity(intent);
            }
        });

//        ra.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GuardActivity.this,MainActivity.class);

                SaveSharedPreference.setUserName(GuardActivity.this,"");

                intent.putExtra("ID",id);


                mDatabaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("TAG",id+"Cpomap"+(String)snapshot.child("id").getValue());
                            if(id.equals((String)snapshot.child("id").getValue())){
                                email_addr=snapshot.child("email").getValue().toString();

                                Toast.makeText(GuardActivity.this, "Found User", Toast.LENGTH_LONG).show();

                                key = utilFunctions.getUser_key(email_addr);

                                mPushDatabaseReference3 = mFirebaseDatabase3.getReference().child("clients");
                                mPushDatabaseReference3.child(key).child("user_key").setValue("");

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

    @Override
    public void onBackPressed(){

        Toast.makeText(GuardActivity.this, "Bye", Toast.LENGTH_SHORT).show();

//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mDatabaseReference = mFirebaseDatabase.getReference().child("clients");

        Log.d("TAG","Going back");


        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Log.d("TAG","Going back");

        Toast.makeText(GuardActivity.this, "Bye", Toast.LENGTH_SHORT).show();

        intent.putExtra("ID",id);
        //intent.putExtra("KEY",key);
        startActivity(intent);
    }

}
