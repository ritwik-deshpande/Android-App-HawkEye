package com.example.android.hawkeye;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddGuestActivity extends AppCompatActivity {

    String id;
    Button submit;
    TextView VC,VM,rn,vehicle_color,dob;
    DataBaseHelper db;
    Calendar c;
    DatePickerDialog dpd;

    String vehicle_company;
    String vehicle_model;
    String colour;
    String reg_number;
    String si;
    Date dt;
    String ad;
    ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);


        id=getIntent().getStringExtra("ID");
        si=getIntent().getStringExtra("SOCIETY");
        VC=(TextView)findViewById(R.id.vc);
        VM=(TextView)findViewById(R.id.vm);
        rn=(TextView)findViewById(R.id.reg_num);
        vehicle_color=(TextView)findViewById(R.id.color);
        submit=(Button)findViewById(R.id.submit);
        dob=(TextView)findViewById(R.id.adate);
        btn=findViewById(R.id.back_button);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c=Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year=c.get(Calendar.YEAR);



                dpd = new DatePickerDialog(AddGuestActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int day, int month, int year) {

                        month++;
                        dob.setText(year+"/"+month+"/"+day);
                        dt=c.getTime();
                    }
                },day,month,year);
                dpd.show();
            }
        });
        db= new DataBaseHelper();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id.substring(0,3).equals("USR")) {
                    Intent i = new Intent(AddGuestActivity.this, UserActivity.class);
                    i.putExtra("ID", id);
                    startActivity(i);
                }
                if(id.substring(0,3).equals("ADM")) {
                    Intent i = new Intent(AddGuestActivity.this, AdminActivity.class);
                    i.putExtra("ID", id);
                    startActivity(i);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicle_company=VC.getText().toString();
                vehicle_model=VM.getText().toString();
                colour=vehicle_color.getText().toString();
                reg_number=rn.getText().toString();
                ad=dob.getText().toString();


                db.createGuestVehicle(new vehicle_guest(id,vehicle_company,vehicle_model,colour,reg_number,si,dt,5));

                if(id.substring(0,3).equals("USR"))
                {
                    Intent intent = new Intent(AddGuestActivity.this, UserActivity.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("SOCIETY", si);
                    Toast.makeText(AddGuestActivity.this, "Added your Guest vehicle to db successfully", Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                }
                if(id.substring(0,3).equals("ADM"))
                {
                    Intent intent = new Intent(AddGuestActivity.this, AdminActivity.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("SOCIETY", si);
                    Toast.makeText(AddGuestActivity.this, "Added your Guest vehicle to db successfully", Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onBackPressed(){

        if(id.substring(0,3).equals("USR")) {
            Intent i = new Intent(AddGuestActivity.this, UserActivity.class);
            i.putExtra("ID", id);
            startActivity(i);
        }
        if(id.substring(0,3).equals("ADM")) {
            Intent i = new Intent(AddGuestActivity.this, AdminActivity.class);
            i.putExtra("ID", id);
            startActivity(i);
        }

    }
}
