package com.example.cottagealarmandroid.app.activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.controllers.DevicesAlarm;

public class BasicViewFragment extends Fragment {

    private TextView dateAlarm;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.basic_view_fragment, container, false);

        dateAlarm = (TextView) view.findViewById(R.id.valueStateOnDate);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        dateAlarm.setText(DevicesAlarm.getInstance().basicAlarmProperty.getDateInDevice());
    }
}
