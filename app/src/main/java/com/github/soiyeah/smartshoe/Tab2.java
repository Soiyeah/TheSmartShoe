package com.github.soiyeah.smartshoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.soiyeah.smartshoe.Entity.ShoeData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class Tab2 extends Fragment implements OnMapReadyCallback {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("SmartShoe").child("shoeData");
    DatabaseReference dbRef;
    ArrayList<ShoeData> shoeDataList = new ArrayList<>();

    ShoeData shoeData = null;

    private GoogleMap mMap;

    Double longitude = 79.8902733;
    Double latitude = 6.748773;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);


        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        mapFragment.getMapAsync(this);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Query query = myRef.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    showAddedChild(snap);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                showAddedChild(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                showAddedChild(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return rootView;
    }

    private void openMapActivity() {

        Intent myIntent = new Intent(Tab2.this.getActivity(), MapsActivity.class);
        startActivity(myIntent);
    }


    private void showAddedChild(DataSnapshot dataSnapshot) {

        String key = dataSnapshot.getKey();
        Log.d(TAG, "Key: " + key);

        shoeData = dataSnapshot.getValue(ShoeData.class);

        if (shoeData != null) {
            Log.d(TAG, "Shoe.longitude: " + shoeData.getLongitude().toString());
            Log.d(TAG, "Shoe.latitude: " + shoeData.getLatitude().toString());

            latitude = shoeData.getLatitude();
            longitude = shoeData.getLongitude();

            LatLng shoeLocation = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(shoeLocation)
                    .title("Smart Shoe"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(shoeLocation));

            LatLng pos = new LatLng(latitude,longitude);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 18.0f));

        }

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.

        mMap = googleMap;

        LatLng shoeLocation = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(shoeLocation)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(shoeLocation));

        LatLng pos = new LatLng(latitude,longitude);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 18.0f));

    }
}