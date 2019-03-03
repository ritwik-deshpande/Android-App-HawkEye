package com.example.android.hawkeye;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RentPark extends AppCompatActivity {


    TextView cost,noofp,sn;
    private String socName;
    private int parkSlots,rent;
    private static final int TEZ_REQUEST_CODE = 123;

    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_park);

        socName = getIntent().getStringExtra("SocietyName");
        parkSlots = getIntent().getIntExtra("parkSlots",0);

        rent = getIntent().getIntExtra("Rent",500);
        Log.d("TAG",socName + " $ " + parkSlots);
        sn=(TextView)findViewById(R.id.sname);
        sn.setText(socName);

        noofp = (TextView)findViewById(R.id.p_slot);
        noofp.setText(Integer.toString(parkSlots));

        cost=(TextView)findViewById(R.id.cost);
        cost.setText(Integer.toString(rent));
        Button payButton = findViewById(R.id.book);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri =
                        new Uri.Builder()
                                .scheme("upi")
                                .authority("pay")
                                .appendQueryParameter("pa", "ayushvnit@oksbi")
                                .appendQueryParameter("pn", "Test Merchant")
                                .appendQueryParameter("mc", "123456789")
                                .appendQueryParameter("tr", "123456789")
                                .appendQueryParameter("tn", "some transaction note")
                                .appendQueryParameter("am", Integer.toString(rent))
                                .appendQueryParameter("cu", "INR")
                                .appendQueryParameter("url", "https://test.merchant.website")
                                .build();

                DatabaseReference updateData = FirebaseDatabase.getInstance()
                        .getReference("Societies")
                        .child(socName);
                parkSlots--;
                updateData.child("parkSlots").setValue(parkSlots);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME);
                startActivityForResult(intent, TEZ_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEZ_REQUEST_CODE) {
            // Process based on the data in response.
            // Log.d("result", data.getStringExtra("Status"));
            DatabaseReference updateData = FirebaseDatabase.getInstance()
                    .getReference("Societies")
                    .child(socName);
            parkSlots--;
            updateData.child("parkSlots").setValue(parkSlots);
        }
    }
}
