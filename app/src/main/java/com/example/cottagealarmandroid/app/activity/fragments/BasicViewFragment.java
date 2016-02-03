package com.example.cottagealarmandroid.app.activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.controllers.DevicesAlarm;

public class BasicViewFragment extends Fragment {

    private TextView dateAlarm;
    private ToggleButton tgBtn;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.basic_view, container, false);

        dateAlarm = (TextView) view.findViewById(R.id.valueStateOnDate);
        tgBtn = (ToggleButton) view.findViewById(R.id.toggleButton);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        dateAlarm.setText(DevicesAlarm.getInstance().basicAlarmProperty.getDateInDevice());
        if(DevicesAlarm.getInstance().getRelay(3).getModeControl().equals("0")){
            tgBtn.setChecked(true);

        } else {
            tgBtn.setChecked(false);
        }
    }
}
