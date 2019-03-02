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

public class RecyclerViewAdapterApprove extends  RecyclerView.Adapter<RecyclerViewAdapterApprove.MyViewHolder>{
    private ArrayList<Client> cl;
    Context context;
    UtilFunctions utilFunctions;
    String email;
    String id;
    String key;
    private FirebaseDatabase mFirebaseDatabase7;
    private DatabaseReference mDatabaseReference7;

    public RecyclerViewAdapterApprove(ArrayList<Client> cl, Context context) {
        this.cl = cl;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_client,parent,false);

        MyViewHolder myViewHolder= new RecyclerViewAdapterApprove.MyViewHolder(v);

        utilFunctions = new UtilFunctions();

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.uname.setText("Name:  "+cl.get(i).getFname()+" "+cl.get(i).getLname());
        myViewHolder.uuid.setText("Unique ID: "+cl.get(i).getId());
        if(cl.get(i).getCl_status())
            myViewHolder.ustatus.setText("Approval Status: Approved");
        else
            myViewHolder.ustatus.setText("Approval Status: Pending");

        myViewHolder.udesc.setText("Decription: "+cl.get(i).getDesc());

//        email = cl.get(i).getEmail();
//        key=utilFunctions.getUser_key(email);
//        id = cl.get(i).getId();

        myViewHolder.disappr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFirebaseDatabase7 = FirebaseDatabase.getInstance();
                mDatabaseReference7 = mFirebaseDatabase7.getReference().child("clients");

                //Log.d("TAG","The key is:"+key);

                mDatabaseReference7.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // dataSnapshot.getRef().child("cl_status").setValue(true);

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (cl.get(i).getId().equals((String) snapshot.child("id").getValue())) {
                                snapshot.child("cl_status").getRef().setValue(false);
                            }
                        }

                        Intent intent = new Intent(context,UserLogActivity.class);
                        intent.putExtra("ID",cl.get(i).getId());

                        Toast.makeText(context,"Debarred the user",Toast.LENGTH_SHORT).show();


                        context.startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });


            }
        });

        myViewHolder.appr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseDatabase7 = FirebaseDatabase.getInstance();
                mDatabaseReference7 = mFirebaseDatabase7.getReference().child("clients");

                //Log.d("TAG","The key is:"+key);

                mDatabaseReference7.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       // dataSnapshot.getRef().child("cl_status").setValue(true);

                        boolean f;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (cl.get(i).getId().equals((String) snapshot.child("id").getValue())) {

                                Log.d("TAG","ARSDTFYGUHIJO "+(Boolean) snapshot.child("cl_status").getValue());
                                if(cl.get(i).getCl_status()){
                                    Toast.makeText(context,"Already Approved",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    snapshot.child("cl_status").getRef().setValue(true);
                                    Intent intent = new Intent(context,UserLogActivity.class);
                                    intent.putExtra("ID",cl.get(i).getId());

                                    Toast.makeText(context,"Approved user",Toast.LENGTH_SHORT).show();

                                    context.startActivity(intent);

                                }
                            }
                        }

//                        Intent intent = new Intent(context,UserLogActivity.class);
//                        intent.putExtra("ID",cl.get(i).getId());
//
//                        Toast.makeText(context,"Approved user",Toast.LENGTH_SHORT).show();
//
//
//                        context.startActivity(intent);

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
        return cl.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {

        TextView uname;
        TextView udesc;
        TextView uuid;
        TextView ustatus;
        ImageButton appr;
        ImageButton disappr;
        public MyViewHolder(View itemView) {
            super(itemView);
            uname=itemView.findViewById(R.id.user_name);
            udesc=itemView.findViewById(R.id.desc);
            uuid=itemView.findViewById(R.id.user_id);
            ustatus=itemView.findViewById(R.id.status);
            appr=itemView.findViewById(R.id.approve);
            disappr=itemView.findViewById(R.id.disapprove);

        }
    }
}
