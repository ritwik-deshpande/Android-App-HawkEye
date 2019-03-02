package com.example.android.hawkeye;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Validate extends AppCompatActivity {

    Button val;
    Button den;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id=getIntent().getStringExtra("ID");
        setContentView(R.layout.activity_validate);
        val=(Button)findViewById(R.id.accept);
        den=(Button)findViewById(R.id.deny);


        val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Validate.this,MainActivity.class);
                i.putExtra("ID",id);
                startActivity(i);
            }
        });
        den.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Validate.this,MainActivity.class);
                i.putExtra("ID",id);
                startActivity(i);
            }
        });
    }
}
