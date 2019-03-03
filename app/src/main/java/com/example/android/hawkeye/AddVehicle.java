package com.example.android.hawkeye;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class AddVehicle extends AppCompatActivity  implements PopupMenu.OnMenuItemClickListener  {

    ImageView imageView;
    String id;
    String si;
    Button submit;
    TextView VC,VM,rn,vehicle_color;
    DataBaseHelper db;

    String vehicle_company;
    String vehicle_model;
    String colour;
    String reg_number;
    String ukey;
    ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        imageView=(ImageView)findViewById(R.id.imageView);

        btn=(ImageButton)findViewById(R.id.back_button);

        id=getIntent().getStringExtra("ID");
        si=getIntent().getStringExtra("SOCIETY");
        ukey=getIntent().getStringExtra("userkey");
        VC=(TextView)findViewById(R.id.vc);
        VM=(TextView)findViewById(R.id.vm);
        rn=(TextView)findViewById(R.id.reg_num);
        vehicle_color=(TextView)findViewById(R.id.color);
        submit=(Button)findViewById(R.id.submit);
        db= new DataBaseHelper();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id.substring(0,3).equals("USR")) {
                    Intent i = new Intent(AddVehicle.this, UserActivity.class);
                    i.putExtra("ID", id);
                    startActivity(i);
                }
                if(id.substring(0,3).equals("ADM")) {
                    Intent i = new Intent(AddVehicle.this, AdminActivity.class);
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
                Log.d("TAG","The si is"+si);
                db.createVehicle(new Vehicle(id,vehicle_company,vehicle_model,colour,reg_number,si),id,ukey);

                if(id.substring(0,3).equals("USR")) {
                    Intent intent = new Intent(AddVehicle.this, UserActivity.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("SOCIETY", si);
                    Toast.makeText(AddVehicle.this, "Added your vehicle to db successfully", Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                }
                if(id.substring(0,3).equals("ADM")){
                    Intent intent = new Intent(AddVehicle.this, AdminActivity.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("SOCIETY", si);
                    Toast.makeText(AddVehicle.this, "Added your vehicle to db successfully", Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                }
            }
        });

    }
    void showPopUp(View v){

        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this );
        popupMenu.inflate(R.menu.pop_vehicle_image_upload);
        popupMenu.show();


    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.camera:
            {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
            }
            break;
            case R.id.gallery:
            {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code

            }
            break;
        }
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){


                    Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");

                    Log.d("TAG","The image is:"+photo);

                    imageView.setImageBitmap(photo);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);
                }
                break;
        }
    }
    @Override
    public void onBackPressed(){

        if(id.substring(0,3).equals("USR")) {
            Intent i = new Intent(AddVehicle.this, UserActivity.class);
            i.putExtra("ID", id);
            startActivity(i);
        }
        if(id.substring(0,3).equals("ADM")) {
            Intent i = new Intent(AddVehicle.this, AdminActivity.class);
            i.putExtra("ID", id);
            startActivity(i);
        }
    }
}
