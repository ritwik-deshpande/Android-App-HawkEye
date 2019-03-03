package com.example.android.hawkeye;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.events.Event;

import java.util.ArrayList;
import java.util.List;

public class Maps  extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<Society> lst;
    List<Marker> markers;

    DatabaseReference rootRef, imagesRef;
    ValueEventListener valueEventListener;
    String id;
    //Marker vnit_marker,civil_marker,elec_marker,phy_marker,mech_marker,audi_marker,meta_marker,hostel_section_marker,ece_marker,chem_marker,canara_marker,archi_marker,audi_lawns_marker,apm_marker,lib_lawns_marker,cse_marker;
    //LatLng vnit,civil,electrical,phy,mech,audi,meta,hostel_section,ece,chem,canara,archi,audi_lawns,apm,lib_lawns,cse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        id=getIntent().getStringExtra("ID");
        lst=new ArrayList<>();
        markers = new ArrayList<>();

        // Creating adapter for spinner
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*
        vnit = new LatLng(21.123104, 79.051511);
        civil = new LatLng(21.123206,79.052165);
        electrical = new LatLng(21.122682,79.052438);
        phy = new LatLng(21.122897,79.050556);
        mech = new LatLng(21.123615,79.051084);
        audi = new LatLng(21.124618,79.051240);
        meta = new LatLng(21.123910,79.050896);
        hostel_section = new LatLng(21.123854, 79.051627);
        ece = new LatLng(21.124477,79.052947);
        chem = new LatLng(21.125232,79.049916);
        canara = new LatLng(21.123883,79.051386);
        archi = new LatLng(21.123715,79.052909);
        audi_lawns = new LatLng(21.124287,79.051501);
        apm = new LatLng(21.123476,79.052087);
        lib_lawns = new LatLng(21.124914,79.051304);
        cse = new LatLng(21.125143,79.052318);
        */

        FetchEventList fel = new FetchEventList();
        fel.execute();
        /*
        vnit_marker = mMap.addMarker(new MarkerOptions().position(vnit).title("VNIT"));
        civil_marker = mMap.addMarker(new MarkerOptions().position(civil).title("CIVIL DEPARTMENT"));
        elec_marker = mMap.addMarker(new MarkerOptions().position(electrical).title("ELECTRICAL DEPARTMENT"));
        phy_marker = mMap.addMarker(new MarkerOptions().position(phy).title("PHYSICS DEPARTMENT"));
        mech_marker = mMap.addMarker(new MarkerOptions().position(mech).title("MECHANICAL DEPARTMENT"));
        audi_marker = mMap.addMarker(new MarkerOptions().position(audi).title("AUDITORIUM"));
        meta_marker = mMap.addMarker(new MarkerOptions().position(meta).title("METALLURGY DEPARTMENT"));
        hostel_section_marker = mMap.addMarker(new MarkerOptions().position(hostel_section).title("HOSTEL SECTION"));
        ece_marker = mMap.addMarker(new MarkerOptions().position(ece).title("ELECTRONICS DEPARTMENT"));
        chem_marker = mMap.addMarker(new MarkerOptions().position(chem).title("CHEMICAL DEPARTMENT"));
        canara_marker = mMap.addMarker(new MarkerOptions().position(canara).title("CANARA BANK"));
        archi_marker = mMap.addMarker(new MarkerOptions().position(archi).title("ARCHITECTURE DEPARTMENT"));
        audi_lawns_marker = mMap.addMarker(new MarkerOptions().position(audi_lawns).title("AUDITORIUM LAWNS"));
        apm_marker = mMap.addMarker(new MarkerOptions().position(apm).title("APPLIED MECHANICS"));
        lib_lawns_marker = mMap.addMarker(new MarkerOptions().position(lib_lawns).title("LIBRARY LAWNS"));
        cse_marker = mMap.addMarker(new MarkerOptions().position(cse).title("COMPUTER SCIENCE DEPARTMENT"));

        moveCamera(vnit,vnit_marker,"Select Event","VNIT");


        */
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                int j;
                //String actionId = markerMap.get(marker.getId());
                for(j=0; j<markers.size(); j++)
                {
                    if(marker.getPosition().equals(markers.get(j).getPosition()))
                        break;
                }
                if(lst.get(j).getParkSlots() == 0)
                {
                    Toast.makeText(Maps.this, "There are no available slots here :(", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Log.d("TAG",lst.get(j).getSocietyName()+" $ " + lst.get(j).getParkSlots());
                    Intent intent = new Intent(Maps.this, RentPark.class);
                    intent.putExtra("SocietyName",lst.get(j).getSocietyName());

                    Log.d("TAG", " $ " +lst.get(j).getParkSlots());

                    intent.putExtra("parkSlots",lst.get(j).getParkSlots());
                    intent.putExtra("Rent",100);
                    startActivity(intent);
                }


            }
        });
        mMap.setBuildingsEnabled(true);
    }
    /*
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String item = parent.getItemAtPosition(position).toString();
            if (item == "Select Event") {
               // moveCamera(vnit, vnit_marker, item, "VNIT");
            }
            else if(item == "AQUAHUNT") {
                moveCamera(hostel_section,hostel_section_marker,item,"HOSTEL SECTION");
            }
            else if(item == "AQUASKYLARK") {
                moveCamera(canara,canara_marker,item,"CANARA BANK");
            }
            else if(item == "AUTOBOT") {
                moveCamera(meta,meta_marker,item,"METALLURGY DEPARTMENT");
            }
            else if(item == "BRAINSTORM") {
                moveCamera(cse,cse_marker,item,"COMPUTER SCIENCE DEPARTMENT");
            }
            else if(item == "CREPIDO") {
                moveCamera(canara,canara_marker,item,"CANARA BANK");
            }
            else if(item == "CRYPTOCRUX") {
                moveCamera(cse,cse_marker,item,"COMPUTER SCIENCE DEPARTMENT");
            }
            else if(item == "DEVISE") {
                moveCamera(archi,archi_marker,item,"ARCHITECTURE DEPARTMENT");
            }
            else if(item == "DEXTER") {
                moveCamera(electrical,elec_marker,item,"ELECTRICAL DEPARTMENT");
            }
            else if(item == "ELECTROBLITZ") {
                moveCamera(cse,cse_marker,item,"COMPUTER SCIENCE DEPARTMENT");
            }
            else if(item == "FREAK-O-MATRIX") {
                moveCamera(chem,chem_marker,item,"CHEMICAL DEPARTMENT");
            }
            else if(item == "GAMESUTRA") {
                moveCamera(meta,meta_marker,item,"METALLURGY DEPARTMENT");
            }
            else if(item == "INFORMALS") {
                moveCamera(lib_lawns,lib_lawns_marker,item,"LIBRARY LAWNS");
            }
            else if(item == "LASERLITT") {
                moveCamera(canara,canara_marker,item,"CANARA BANK");
            }
            else if(item == "MECHATRYST") {
                moveCamera(audi_lawns,audi_lawns_marker,item,"AUDITORIUM LAWNS");
            }
            else if(item  == "PARADEIGMA") {
                moveCamera(audi,audi_marker,item,"AUDITORIUM");
            }
            else if(item == "ROBOWARS") {
                moveCamera(apm,apm_marker,item,"APPLIED MECHANICS DEPARTMENT");
            }
            else if(item == "ROBO TERRAFORMER") {
                moveCamera(meta,meta_marker,item,"METALLURGY DEPARTMENT");
            }
            else if(item == "ROBOCUP") {
                moveCamera(canara,canara_marker,item,"CANARA BANK");
            }
            else if(item == "TECHNO.DOCX") {
                moveCamera(mech,mech_marker,item,"MECHANICAL DEPARTMENT");
            }
            else if(item == "TURBOFLUX") {
                moveCamera(canara,canara_marker,item,"CANARA BANK");
            }
            else if(item == "WALL STREET") {
                moveCamera(audi,audi_marker,item,"AUDITORIUM");
            }
            else if (item == "WHO'S THE BOSS") {
                moveCamera(chem, chem_marker, item, "CHEMICAL DEPARTMENT");
            }
            else {
                LatLng electrical = new LatLng(21.122682, 79.052438);
                moveCamera(electrical, elec_marker, item, "ELECTRICAL DEPARTMENT");
            }
        }



        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            LatLng vnit = new LatLng(21.123104, 79.051511);
            moveCamera(vnit,vnit_marker,"Select Event", "VNIT");
        }
    */
    public void moveCamera(LatLng loc, Marker marker, String event_name, String loc_name)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(loc)                    // Sets the center of the map to loc
                .zoom(19)                       // Sets the zoom
                .bearing(0)                     // Sets the orientation of the camera to east
                .tilt(45)                       // Sets the tilt of the camera to 45 degrees
                .build();                       // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if(event_name!="Select Event")
        {
            marker.setTitle(event_name);
            marker.showInfoWindow();
        }
        marker.setTitle(loc_name);
    }

    public class FetchEventList extends AsyncTask<Void,Void,ArrayList<Event>> {

        @Override
        protected void onPreExecute() {
            //bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Event> doInBackground(Void... params) {

            lst.clear();
            rootRef = FirebaseDatabase.getInstance().getReference();
            imagesRef = rootRef.child("Societies");
            //LatLng loc = new LatLng(25.27654535,82.98884151);
            //moveCamera(loc,mMap.addMarker(new MarkerOptions().position(loc) ),"Plumeria","Plumeria");
            valueEventListener = new ValueEventListener() {

                int i =0;
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Toast.makeText(getContext(),"retrieving data",Toast.LENGTH_SHORT).show();
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {

                        lst.add((ds.getValue(Society.class)));
                        markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(lst.get(i).getLatitude(),lst.get(i).getLongitude())).title(lst.get(i).getSocietyName())));
                        Log.d("TAG","firebase created event object");
                        i++;
                    }
                    //Log.d("Size of list is ","size=" + ((Integer) lst.size()).toString());

                    //runAnimation(recyclerView,0);

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
            imagesRef.addListenerForSingleValueEvent(valueEventListener);

            return null;
        }
    }

    @Override
    public void onBackPressed(){

        Intent i;
        if(id.substring(0,3).equals("USR")) {
            i= new Intent(this, UserActivity.class);
            i.putExtra("ID",id);
            startActivity(i);
        }
        if(id.substring(0,3).equals("ADM")) {
            i= new Intent(this, AdminActivity.class);
            i.putExtra("ID",id);
            startActivity(i);
        }
    }

}
