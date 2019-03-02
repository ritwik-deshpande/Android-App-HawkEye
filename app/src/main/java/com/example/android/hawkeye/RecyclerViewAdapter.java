package com.example.android.hawkeye;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
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

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    String TAG="RecyclerView";
    private ArrayList<Vehicle> vehicles= new ArrayList<>();
    Context context;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    ArrayList<Pair<ImageButton,Integer>> p;

    public RecyclerViewAdapter(ArrayList<Vehicle> vehicles, Context context) {
        this.vehicles = vehicles;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_vehicle_user,viewGroup,false);

        p = new ArrayList<>();
        MyViewHolder myViewHolder= new RecyclerViewAdapter.MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.MyViewHolder myViewHolder, final int i) {
        Log.d(TAG,"Setting my View holder");
        //Glide.with(context).asBitmap().load(events.get(i).getImg_url()).into(myViewHolder.image);
        myViewHolder.cname.setText(vehicles.get(i).getVehicle_company());
        myViewHolder.reg_num.setText(vehicles.get(i).getReg_number());

        p.add(Pair.create(myViewHolder.rv,i));

        myViewHolder.rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mDatabaseReference = mFirebaseDatabase.getReference().child("vehicles");

                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (vehicles.get(i).getReg_number().equals((String) snapshot.child("reg_number").getValue())) {
                                snapshot.getRef().removeValue();
                            }
                        }
                        Intent intent = new Intent(context,MyVehiclesUser.class);
                        intent.putExtra("ID",vehicles.get(i).getUserid());

                        Toast.makeText(context,"Removed your vehicle from db",Toast.LENGTH_SHORT).show();


                        context.startActivity(intent);


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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cname=(TextView)itemView.findViewById(R.id.cname);
            reg_num=(TextView)itemView.findViewById(R.id.reg_num);
            rv=(ImageButton)itemView.findViewById(R.id.remove_vehicle);
        }
    }

}
