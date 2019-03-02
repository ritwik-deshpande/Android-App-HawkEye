package com.example.android.hawkeye;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    //fiCE08sCgqQ:APA91bESiJB-VN0UVOti1yEYG1sdcYPKcKXGrVAXBS8ZFeo0Bg-AndCy-DPnSc9lQhpeAgs4uYERIxjDI18YPUP0O0guU-yB8RKRASnLtRmcHAeB-2y_VDYJ6W8gERXMEP6XvvRc4ie7

   //c0dPK0q7G-0:APA91bGj-0PqY1Mo_8OML2DBCxvMy-U-fqeBHhTZOu7FIFzr18_58PazKl40lYjK_CXymPKPLTn9doXY1vmWwQl8N1Y-CVE9g7qqoE_DIZmsCTYyZxIApPbbMwWbEgrkSv3fzcEDLa4o

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage,forgotpassword;
    EditText pno,pword;
    static String type;
    private FirebaseDatabase mFirebaseDatabase,mFirebaseDatabase2;
    private DatabaseReference mDatabaseReference,mDatabaseReference2;
    private DatabaseReference mPushDatabaseReference,mPushDatabaseReference2;

    boolean loggedin;
    String phone_number;
    String password;
    String desc;
    String message;
    String id;
    String user_key;
    String email_addr;
    String key;
    String key2;
    String si;
    String nty;
    UtilFunctions utilFunctions;
    boolean found;
    boolean approve;

    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            this.getSupportActionBar().hide();
        }
        catch(NullPointerException e){}
        Log.d("TAG","The main CAtivity statred");
        setContentView(R.layout.activity_main);
        txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) findViewById(R.id.txt_push_message);
        login_btn=(Button)findViewById(R.id.login_button);


        id=getIntent().getStringExtra("ID");
        nty=getIntent().getStringExtra("NOTIFY");

        utilFunctions= new UtilFunctions();
        forgotpassword=(TextView)findViewById(R.id.fp);
        Log.d("TAG","Started main Activity");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
      //  mFirebaseDatabase2 = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("clients");
       // mDatabaseReference2 = mFirebaseDatabase2.getReference().child("clients");

//        if (user != null) {
//            // User is signed in
//
//            Intent intent = new Intent(MainActivity.this,UserActivity.class);
//
//            id=user.getDisplayName();
//
//            Log.d("TAG","The id is"+id);
//
//
//
//            intent.putExtra("ID",id);
//
//            startActivity(intent);
//        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    user_key=displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    message = intent.getStringExtra("message");
                    nty=intent.getStringExtra("NOTIFY");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                    Log.d("TAG","Tdgxf    xcvbn   "+message);
                    txtMessage.setText(message);
                }
            }
        };


        Log.d("TAG","THe notification recieved is :"+nty+"    xcvbn   "+message);



        String sid=SaveSharedPreference.getUserName(MainActivity.this);
        if(sid.length() > 0)
        {
            if(nty==null) {

                if (sid.substring(0, 3).equals("USR")) {

                    type = "USR";
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    intent.putExtra("ID", sid);
                    startActivity(intent);
                }
                if (sid.substring(0, 3).equals("GRD")) {
                    type = "GRD";
                    Intent intent = new Intent(MainActivity.this, GuardActivity.class);
                    intent.putExtra("ID", sid);
                    startActivity(intent);

                }
                if (sid.substring(0, 3).equals("ADM")) {
                    type = "ADM";
                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    intent.putExtra("ID", sid);
                    startActivity(intent);

                }
            }
            else{
                if (sid.substring(0, 3).equals("USR")) {
                    type = "USR";
                    Intent intent = new Intent(MainActivity.this, Validate.class);
                    intent.putExtra("ID", sid);
                    startActivity(intent);
                }
                if (sid.substring(0, 3).equals("GRD")) {
                    type = "GRD";
                    Intent intent = new Intent(MainActivity.this, GuardActivity.class);
                    intent.putExtra("ID", sid);
                    startActivity(intent);

                }
                if (sid.substring(0, 3).equals("ADM")) {
                    type = "ADM";
                    Intent intent = new Intent(MainActivity.this, Validate.class);
                    intent.putExtra("ID", sid);
                    startActivity(intent);

                }
            }
        }


        if(id!=null) {
            Toast.makeText(MainActivity.this, "Changing Key", Toast.LENGTH_LONG).show();




            if(found){

                mPushDatabaseReference2 = mFirebaseDatabase2.getReference().child("clients");

                mPushDatabaseReference2.child(key2).child("user_key").setValue("");
            }
        }








        user_key=displayFirebaseRegId();

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(intent);

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pno=(EditText)findViewById(R.id.pno);
                pword=(EditText)findViewById(R.id.password);

                phone_number=pno.getText().toString();
                password=pword.getText().toString();

                loggedin=false;
                approve=false;
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String x = (String) snapshot.getKey();
//                            if (key.equals(x)) {
//                                Toast.makeText(MainActivity.this, "Welcome back!", Toast.LENGTH_SHORT).show();
//                                isNewUser = false;
//                                axisid = (String) snapshot.child("axisid").getValue();
//                                if(snapshot.child("phone").getValue().equals("NULL"))
//                                {
//                                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
//                                    startActivity(i);
//                                }
//                                break;
//                            }

                            Log.d("TAG","comparing   "+  phone_number + "    "+  (String) snapshot.child("pno").getValue());


                            if(phone_number.equals((String) snapshot.child("pno").getValue())){
                                if(password.equals((String) snapshot.child("password").getValue())){
                                    loggedin=true;
                                    desc=snapshot.child("desc").getValue().toString();
                                    id=snapshot.child("id").getValue().toString();

                                    si=snapshot.child("society_info").getValue().toString();
                                    email_addr=snapshot.child("email").getValue().toString();


                                    key = utilFunctions.getUser_key(email_addr);
                                    Log.d("TAG","The key is:"+key);
                                   // Log.d("TAG","The new password is:"+npassword);
                                    mPushDatabaseReference = mFirebaseDatabase.getReference().child("clients");
                                    if((boolean) snapshot.child("cl_status").getValue())
                                    {
                                        mPushDatabaseReference.child(key).child("user_key").setValue(user_key);
                                        approve=true;
                                    }
                                    break;
                                }

                            }

                        }
                        if(loggedin && approve){
                            Toast.makeText(MainActivity.this,"Logged In Succesfully",Toast.LENGTH_SHORT).show();

                            SaveSharedPreference.setUserName(MainActivity.this,id);
                            if(desc.equals("user")){

                                Intent intent = new Intent(MainActivity.this,UserActivity.class);

                                Log.d("TAG","The id is"+id);

                                intent.putExtra("ID",id);

                                intent.putExtra("SOCIETY",si);

                                startActivity(intent);

                            }
                            if(desc.equals("admin")){
                                Intent intent = new Intent(MainActivity.this,AdminActivity.class);

                                Log.d("TAG","The id is"+id);

                                intent.putExtra("ID",id);

                                startActivity(intent);

                            }
                            if(desc.equals("guard")){
                                Intent intent = new Intent(MainActivity.this,GuardActivity.class);

                                Log.d("TAG","The id is"+id);

                                intent.putExtra("ID",id);

                                startActivity(intent);

                            }

                        }

                        else{
                            if(!loggedin)
                                Toast.makeText(MainActivity.this,"Invalid Phone Number or Password",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(MainActivity.this,"You have not yet been approved",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
    private String displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");
        return regId;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
    @Override
    public void onBackPressed(){

        SaveSharedPreference.setUserName(MainActivity.this,"");

        Toast.makeText(MainActivity.this, "Bye", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    void showPopUp(View v){

        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this );
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();


    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
           /* case R.id.guard:
            {
                Intent i = new Intent(MainActivity.this,SignUp.class);
                i.putExtra("Desc","guard");

                i.putExtra("userkey",user_key);
                startActivity(i);
            }
            break;
            */
            case R.id.user:
            {
                Intent i = new Intent(MainActivity.this,SignUp.class);
                i.putExtra("Desc","user");

                i.putExtra("userkey",user_key);

                startActivity(i);

            }
            break;
            case R.id.admin:
            {
                Intent i = new Intent(MainActivity.this,SignUp.class);
                i.putExtra("Desc","admin");

                i.putExtra("userkey",user_key);

                startActivity(i);
            }
            break;
        }
        return true;
    }

}
