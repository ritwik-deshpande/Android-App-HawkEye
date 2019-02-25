package com.example.android.hawkeye;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SignUp extends AppCompatActivity {
    String desc;
    EditText dob;
    EditText sname;
    EditText pword;
    EditText pnum;

    Client client;
    protected String fname;
    protected String lname;
    protected Date DOB;
    static String email;
    protected String gender;
    protected String society_info;
    protected String raddress;
    protected String aadharno;
    protected String pno;
    protected String password;
    Calendar c;
    TextView info;
    Button submit;
    DatePickerDialog dpd;
    Spinner society_name;
    RadioGroup radioGroup;
    DataBaseHelper db;
    UtilFunctions utilFunctions;
    String [] names;
    String id;
    String userkey;
    ImageButton btn;
    Button sae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            this.getSupportActionBar().hide();
        }
        catch(NullPointerException e){}
        setContentView(R.layout.activity_sign_up);
        desc=getIntent().getStringExtra("Desc");
        userkey=getIntent().getStringExtra("userkey");
        db= new DataBaseHelper();


        sae=findViewById(R.id.appr_email);
        btn=findViewById(R.id.back_button);

        submit=(Button)findViewById(R.id.submit);
        utilFunctions=new UtilFunctions();
        dob=(EditText)findViewById(R.id.dob);
        names=new String[]{"Rambharose,Saraf Lane","Raj Apartment,Mira Road","Western Heights,DN Nagar","Rambharose,Saraf","Rambharose,Saraf","Rambharose,Saraf","Rambharose,Saraf"};


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this,MainActivity.class);
                // i.putExtra("ID", id);
                startActivity(i);
            }
        });

        sae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String mail="mailto:"+"spatankar224@gmail.com";
                intent.setData(Uri.parse(mail));
                intent.putExtra(Intent.EXTRA_SUBJECT, "APPROVAL EMAIL");
                intent.putExtra(Intent.EXTRA_TEXT, "Approve me as ADMIN");
                startActivity(intent);
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c=Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month=c.get(Calendar.MONTH);
                int year=c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int day, int month, int year) {

                        month++;
                        dob.setText(year+"/"+month+"/"+day);

                    }
                },day,month,year);
                dpd.show();
            }
        });
        info=(TextView)findViewById(R.id.info);
        info.setText("Registering as "+desc);
        society_name=(Spinner)findViewById(R.id.societynames);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,names);
        society_name.setAdapter(adapter);

        if(desc.equals("admin")){
            society_name.setMinimumHeight(0);
            society_name.setMinimumWidth(0);
            society_name.setAlpha(0);
        }
        else{
            sname=(EditText)findViewById(R.id.sname);
            sname.setHeight(0);
            sname.setAlpha(0);
            sname.setWidth(0);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = ((EditText)findViewById(R.id.firstname)).getText().toString();
                lname = ((EditText)findViewById(R.id.lastname)).getText().toString();
                raddress = ((EditText)findViewById(R.id.raddr)).getText().toString();
                email = ((EditText)findViewById(R.id.email)).getText().toString();
                aadharno = ((EditText)findViewById(R.id.aadharno)).getText().toString();
                password =((EditText)findViewById(R.id.password)).getText().toString();
                pno=((EditText)findViewById(R.id.phone_number)).getText().toString();
                society_info=society_name.getSelectedItem().toString();
                radioGroup = ((RadioGroup)findViewById(R.id.gender));
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                if(selectedId == -1)
                {
                    Toast.makeText(SignUp.this,"Please select your gender",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    gender = ((RadioButton) findViewById(selectedId)).getText().toString();
                }
                try {
                    DOB = new SimpleDateFormat("dd/MM/yyyy").parse(dob.getText().toString());
                 }catch (Exception e){
                    System.out.println(e.toString());
                    DOB = new GregorianCalendar(2019,02,30,10,30).getTime();
                 }
                //DOB  = dob.getText().toString();
 //public Client(String fname, String lname, String email, String gender, String raddress, String aadharno,String d,String society_info,String password,String desc,String pno) {
                //

                Log.d("TAG","The desc is"+desc);

//                Intent i = new Intent(SignUp.this,MainActivity.class);
                //startActivity(i);
                if(desc.equals("user")){
                    id=utilFunctions.generate_id("USR");
                    client = new Client(fname,lname,email,gender, raddress, aadharno,DOB,society_info,password,desc,pno,id,userkey,false);
                    db.createClient(client);

                    Toast.makeText(SignUp.this,"User Account created Successfully",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUp.this,MainActivity.class);

                 //   SaveSharedPreference.setUserName(SignUp.this,id);

                  //  Log.d("TAG","The id is"+id);

//                    intent.putExtra("ID",id);

                    startActivity(intent);
                }
                if(desc.equals("guard")){
                    id=utilFunctions.generate_id("GRD");
                    client = new Client(fname,lname,email,gender, raddress, aadharno,DOB,society_info,password,desc,pno,id,userkey,false);
                    db.createClient(client);

                    Toast.makeText(SignUp.this,"Guard Account created Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this,MainActivity.class);
                   // intent.putExtra("ID",id);

                   // SaveSharedPreference.setUserName(SignUp.this,id);

                    startActivity(intent);
                }
                if(desc.equals("admin")){
                    id=utilFunctions.generate_id("ADM");
                    client = new Client(fname,lname,email,gender, raddress, aadharno,DOB,society_info,password,desc,pno,id,userkey,false);
                    db.createClient(client);

                    Toast.makeText(SignUp.this,"Admin Account created Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this,MainActivity.class);
                    //intent.putExtra("ID",id);

                    //SaveSharedPreference.setUserName(SignUp.this,id);

                    startActivity(intent);
                }

               // client = new Client(fname, email,axisid,college,country, DOB,address,gender,phone,zipcode);

            }
        });

    }
    @Override
    public void onBackPressed(){

        Intent i = new Intent(SignUp.this,MainActivity.class);
        // i.putExtra("ID", id);
        startActivity(i);


    }
}
