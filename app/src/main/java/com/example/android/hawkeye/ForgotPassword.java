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

public class ForgotPassword extends AppCompatActivity {

    EditText email_id;
    Button reset;
    String email_addr;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mPushDatabaseReference;
    boolean present;
    String npassword;
    String key;
    UtilFunctions utilFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            this.getSupportActionBar().hide();
        }
        catch(NullPointerException e){}
        setContentView(R.layout.activity_forgot_password);
        Log.d("TAG","Started forgotpassword activity");
        email_id=(EditText)findViewById(R.id.email);
        reset=(Button)findViewById(R.id.reset_button);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("clients");

        utilFunctions = new UtilFunctions();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_addr=email_id.getText().toString();


                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        present = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String x = (String) snapshot.getKey();
                            Log.d("TAG2","The key is:"+x+"\n");
                            Log.d("TAG","The emails are:"+email_addr+"        "+(String) snapshot.child("email").getValue()+".......");
                            if(email_addr.equals((String) snapshot.child("email").getValue().toString())){
                                present=true;
                                Log.d("TAG$","Equal!!");
                                break;
                            }
                        }

                        Log.d("TAG3","The value of present:"+present);
                        if(!present){
                            Log.d("TAG","1st if");
                            Toast.makeText(ForgotPassword.this,"Use your registered Email Id only",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            npassword = utilFunctions.generate_newpassword();

                            //Toast.makeText(ForgotPassword.this,"DONE",Toast.LENGTH_SHORT).show();


                             Log.d("TAG","2nd if");
                            key = utilFunctions.getUser_key(email_addr);
                            Log.d("TAG","The key is:"+key);
                            Log.d("TAG","The new password is:"+npassword);
                            mPushDatabaseReference = mFirebaseDatabase.getReference().child("clients");
                            mPushDatabaseReference.child(key).child("password").setValue(npassword);


                            Toast.makeText(ForgotPassword.this, "Passowrd reset Successfully", Toast.LENGTH_SHORT).show();



                            Log.d("TAG","1st else");
                            String fromEmail = "axis.appteam@gmail.com";
                            String fromPassword = "AXISisLIFE";
                            String toEmails = email_addr;
                            String adminEmail = "admin@gmail.com";
                            String emailSubject = "Password Reset email";
                            String adminSubject = "App Registration Mail";
                            String emailBody = "The newpassword for your account is:"+npassword+", and has been reset successfully";
                            String adminBody = "Your message";
                            new SendMailTask(ForgotPassword.this).execute(fromEmail,
                                    fromPassword, toEmails, emailSubject, emailBody);
                            Toast.makeText(ForgotPassword.this, "Passowrd reset email has been sent", Toast.LENGTH_SHORT).show();



                            Intent intent= new Intent(ForgotPassword.this,MainActivity.class);
                            startActivity(intent);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
                Log.d("TAG3","The value of present:"+present);
//                if(present){
//
//                }



            }
        });

    }
}
