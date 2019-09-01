package com.github.soiyeah.smartshoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

import com.github.soiyeah.smartshoe.Entity.ShoeData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference myRef = database.getReference();
    ArrayList<ShoeData> shoeDataList = new ArrayList<>();

    ShoeData shoeData = null;



//    SHA 2a:96:df:51:38:8d:e1:18:f0:29:0b:48:db:11:2d:6a:3a:b0:76:85

    Switch s1;
    Switch s2;

    TextView tv1;
    TextView tv2;

    Device switch1;
    Device switch2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
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
        }
    }



}
