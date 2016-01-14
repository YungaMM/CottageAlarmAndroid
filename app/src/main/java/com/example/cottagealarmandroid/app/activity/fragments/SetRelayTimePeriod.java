package com.example.cottagealarmandroid.app.activity.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.cottagealarmandroid.app.R;

public class SetRelayTimePeriod extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_relay_time_period, container, false);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
