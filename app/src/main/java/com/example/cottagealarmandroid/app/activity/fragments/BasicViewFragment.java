package com.example.cottagealarmandroid.app.activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.model.DevicesAlarm;

public class BasicViewFragment extends Fragment implements
        CompoundButton.OnCheckedChangeListener{

    private TextView dateAlarm;
    private ToggleButton tgBtn, tgBtn2, tgBtn3;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.basic_view, container, false);

        dateAlarm = (TextView) view.findViewById(R.id.valueStateOnDate);
        tgBtn = (ToggleButton) view.findViewById(R.id.toggleButton);
        tgBtn2 = (ToggleButton) view.findViewById(R.id.toggleButton2);
        tgBtn3 = (ToggleButton) view.findViewById(R.id.toggleButton3);

        tgBtn.setOnCheckedChangeListener(this);
        tgBtn2.setOnCheckedChangeListener(this);
        tgBtn3.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        dateAlarm.setText(DevicesAlarm.getInstance().getBasicAlarmProperty().getDateInDevice());
        if(DevicesAlarm.getInstance().getRelay(3).getModeControl().equals("0")){
            tgBtn.setChecked(true);
        } else {
            tgBtn.setChecked(false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.toggleButton:
                Toast.makeText(getContext(),"Reley 4", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toggleButton2:
                Toast.makeText(getContext(),"Reley 5", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toggleButton3:
                Toast.makeText(getContext(),"Reley 6", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
