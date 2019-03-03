package com.example.android.hawkeye;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.android.hawkeye.SignUp.email;

public class DataBaseHelper {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mPushDatabaseReference, rootRef, fromRef, toRef;
    private UtilFunctions utilFunctions;
    private String key;
    public DataBaseHelper()
    {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        rootRef = mFirebaseDatabase.getReference();
        utilFunctions = new UtilFunctions();

    }

    public void createClient(Client clt)
    {
        key = utilFunctions.getUser_key(email);
        mPushDatabaseReference = mFirebaseDatabase.getReference().child("clients");
        mPushDatabaseReference.child(key).setValue(clt);

        if(clt.getDesc().equals("admin")){
            rootRef.child("Credential").child(clt.getSociety_info()).child("Admin").child("user_key").setValue(clt.getUser_key());
        }
        if(clt.getDesc().equals("guard")){
            rootRef.child("Credential").child(clt.getSociety_info()).child("Guard").child("user_key").setValue(clt.getUser_key());
        }
    }

    public void createVehicle(Vehicle vehicle,String uid,String u_key){
        key = vehicle.reg_number;
        mPushDatabaseReference = mFirebaseDatabase.getReference().child("vehicles");
        mPushDatabaseReference.child(key).setValue(vehicle);

        rootRef.child("Credential").child(vehicle.getS_info()).child(vehicle.getReg_number()).child("id").setValue(uid);
        rootRef.child("Credential").child(vehicle.getS_info()).child(vehicle.getReg_number()).child("user_key").setValue(u_key);

    }

    public void createGuestVehicle(vehicle_guest vehicle,String uid,String u_key){
        key = vehicle.reg_number;
        mPushDatabaseReference = mFirebaseDatabase.getReference().child("guest_vehicles");
        mPushDatabaseReference.child(key).setValue(vehicle);

        rootRef.child("Credential").child(vehicle.getS_info()).child(vehicle.getReg_number()).child("id").setValue(uid);
        rootRef.child("Credential").child(vehicle.getS_info()).child(vehicle.getReg_number()).child("user_key").setValue(u_key);

    }

    boolean user_present(String phone,String email){


        return true;

    }
}
