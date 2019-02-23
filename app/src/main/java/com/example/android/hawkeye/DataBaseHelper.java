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
    }

    public void createVehicle(Vehicle vehicle){
        key = vehicle.reg_number;
        mPushDatabaseReference = mFirebaseDatabase.getReference().child("vehicles");
        mPushDatabaseReference.child(key).setValue(vehicle);
    }

    public void createGuestVehicle(vehicle_guest vehicle){
        key = vehicle.reg_number;
        mPushDatabaseReference = mFirebaseDatabase.getReference().child("guest_vehicles");
        mPushDatabaseReference.child(key).setValue(vehicle);
    }

    boolean user_present(String phone,String email){


        return true;

    }
}
