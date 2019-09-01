package com.github.soiyeah.smartshoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Tab2 extends Fragment {

    Button openMaps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);

//        openMaps = (Button) rootView.findViewById(R.id.btn_open_maps);
//
//        openMaps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openMapActivity();
//            }
//        });

        return rootView;
    }

    private void openMapActivity() {

        Intent myIntent = new Intent(Tab2.this.getActivity(), MapsActivity.class);
        startActivity(myIntent);
    }


}