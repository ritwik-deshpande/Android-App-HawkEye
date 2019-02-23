package com.example.android.hawkeye;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecyclerViewAdapterGuest extends RecyclerView.Adapter<RecyclerViewAdapterGuest.MyViewHolder> {

    private ArrayList<vehicle_guest> vehicles= new ArrayList<>();
    Context context;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    Date d;

    String rn;
    String id;


    public RecyclerViewAdapterGuest(ArrayList<vehicle_guest> vehicles, Context context) {
        this.vehicles = vehicles;
        this.context = context;
    }



    @NonNull
    @Override
    public RecyclerViewAdapterGuest.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_guest,viewGroup,false);

        RecyclerViewAdapterGuest.MyViewHolder myViewHolder= new RecyclerViewAdapterGuest.MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterGuest.MyViewHolder myViewHolder, int i) {

        //Log.d(TAG,"Setting my View holder");
        //Glide.with(context).asBitmap().load(events.get(i).getImg_url()).into(myViewHolder.image);
        myViewHolder.cname.setText(vehicles.get(i).getVehicle_company());
        myViewHolder.reg_num.setText(vehicles.get(i).getReg_number());


        d = vehicles.get(i).getAtime();

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(d);


        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);

        int year = calendar.get(Calendar.YEAR);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        int minute= calendar.get(Calendar.MINUTE);

        Log.d("TAG","The current date is:"+day+"/"+month+"/"+year);

        myViewHolder.ad.setText(day+"/"+month+"/"+year);

        rn=vehicles.get(i).getReg_number();
        id=vehicles.get(i).getUserid();

        myViewHolder.rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mDatabaseReference = mFirebaseDatabase.getReference().child("guest_vehicles");

                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (rn.equals((String) snapshot.child("reg_number").getValue())) {
                                snapshot.getRef().removeValue();
                            }
                        }
                        Intent i = new Intent(context,UserActivity.class);
                        i.putExtra("ID",id);

                        Toast.makeText(context,"Removed your vehicle from db",Toast.LENGTH_SHORT).show();


                        context.startActivity(i);


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView cname;
        TextView reg_num;
        ImageButton rv;
        ImageButton exd;
        TextView ad;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cname=(TextView)itemView.findViewById(R.id.cname);
            reg_num=(TextView)itemView.findViewById(R.id.reg_num);
            rv=(ImageButton)itemView.findViewById(R.id.remove_vehicle);
            exd=(ImageButton)itemView.findViewById(R.id.extend);
            ad=(TextView)itemView.findViewById(R.id.adate);
        }
    }
}
