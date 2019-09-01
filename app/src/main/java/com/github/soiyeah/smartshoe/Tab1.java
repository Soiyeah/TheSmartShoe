package com.github.soiyeah.smartshoe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.github.soiyeah.smartshoe.Entity.ShoeData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class Tab1 extends Fragment {

    TextView tvStepCount;
    TextView tvSpeed;
    TextView tvCurrentActivity;
    TextView tvPressure;
    TextView tvPressureStatus;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("SmartShoe").child("shoeData");
    DatabaseReference dbRef;
    ArrayList<ShoeData> shoeDataList = new ArrayList<>();

    ShoeData shoeData = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        tvStepCount = (TextView) rootView.findViewById(R.id.tv_step_count);
        tvSpeed = (TextView) rootView.findViewById(R.id.tv_speed);
        tvCurrentActivity = (TextView) rootView.findViewById(R.id.tv_current_activity);
        tvPressure = (TextView) rootView.findViewById(R.id.tv_pressure);
        tvPressureStatus = (TextView) rootView.findViewById(R.id.tv_pressure_status);

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


    private void showData(DataSnapshot dataSnapshot) {

//        String value = dataSnapshot.getValue(String.class);
//        Log.d("file", "Value is: " + value);
//        textView1.setText(value);
    }

    private void showAddedChild(DataSnapshot dataSnapshot) {

        String key = dataSnapshot.getKey();
        Log.d(TAG, "Key: " + key);

        shoeData = dataSnapshot.getValue(ShoeData.class);

        if (shoeData != null) {
            Log.d(TAG, "Shoe.longitude: " + shoeData.getLongitude().toString());
            Log.d(TAG, "Shoe.latitude: " + shoeData.getLatitude().toString());
            Log.d(TAG, "Shoe.pressure: " + shoeData.getPressure().toString());
            Log.d(TAG, "Shoe.speed: " + shoeData.getSpeed().toString());
            Log.d(TAG, "Shoe.steps: " + shoeData.getSteps().toString());

            tvStepCount.setText(shoeData.getSteps().toString());
            tvSpeed.setText(shoeData.getSpeed().toString());
            tvCurrentActivity.setText(calculateActivityStatus(shoeData.getSpeed()));  // Calculate Activity
            tvPressure.setText(shoeData.getPressure().toString());
            tvPressureStatus.setText(calculatePressureStatus(shoeData.getPressure()));    // Calculate Status

        }

    }

    private String calculateActivityStatus(Long speed) {
        String activityStatus = "";

        if (speed <= 500) {
            activityStatus = "Walking";
        } else if (speed > 500 && speed <= 1200) {
            activityStatus = "Jogging";
        } else {
            activityStatus = "Running";
        }
        return activityStatus;
    }

    private String calculatePressureStatus(Long pressure) {
        String activityStatus = "";

        if (pressure <= 400) {
            activityStatus = "Low";
        } else if (pressure > 400 && pressure <= 800) {
            activityStatus = "Normal";
        } else {
            activityStatus = "High";
        }
        return activityStatus;
    }

}
