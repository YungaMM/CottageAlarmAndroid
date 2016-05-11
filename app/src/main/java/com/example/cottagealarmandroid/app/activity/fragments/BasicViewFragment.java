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
import com.example.cottagealarmandroid.app.controllers.AdvancePreferences;
import com.example.cottagealarmandroid.app.controllers.ProcessingSMS;
import com.example.cottagealarmandroid.app.controllers.SmsCommandsAlarm;
import com.example.cottagealarmandroid.app.model.DevicesAlarm;
import com.example.cottagealarmandroid.app.model.Relay;

public class BasicViewFragment extends Fragment implements
        CompoundButton.OnCheckedChangeListener{

    private static final String RELAY_MODE_CONTROL = "1"; //Включение реле на заданное время
    private TextView dateAlarm;
    private Relay[] relay;
    private ToggleButton tgBtn, tgBtn2, tgBtn3;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.basic_view, container, false);

        dateAlarm = (TextView) view.findViewById(R.id.valueStateOnDate);
        tgBtn = (ToggleButton) view.findViewById(R.id.relay4Btn);
        tgBtn2 = (ToggleButton) view.findViewById(R.id.relay5Btn);
        tgBtn3 = (ToggleButton) view.findViewById(R.id.relay6Btn);

        tgBtn.setOnCheckedChangeListener(this);
        tgBtn2.setOnCheckedChangeListener(this);
        tgBtn3.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        dateAlarm.setText(DevicesAlarm.getInstance().getBasicAlarmProperty().getDateInDevice());
        //Устанавливаем значения кнопок Реле согласно записанным установкам
        relay = DevicesAlarm.getInstance().getRelays();
        for (int i = 3; i < relay.length; i++) {
            if(relay[i].getModeControl().equals("0")){
                tgBtn.setChecked(false);
            } else {
                tgBtn.setChecked(true);
            }
        }
    }
//*****
    @Override
    public void onCheckedChanged(CompoundButton relayButton, boolean isChecked) {
        switch (relayButton.getId()){
            case R.id.relay4Btn:
                setRelay(relay[3], isChecked);
                break;
            case R.id.relay5Btn:
                setRelay(relay[4], isChecked);
                break;
            case R.id.relay6Btn:
                setRelay(relay[5], isChecked);
                break;
        }
    }

    private void setRelay(Relay relay, boolean tgBtnChecked){
        String sms;
        if (tgBtnChecked){
            sms = SmsCommandsAlarm.createSmsRelay(relay,"00","00");
        }
        else {
           sms = SmsCommandsAlarm.createSmsRelayOff(relay);
        }
        relay.setSmsCommand(sms);
        AdvancePreferences.addProperty(relay.getNAME_PREFS_SMS(), sms);
        relay.setModeControl(RELAY_MODE_CONTROL);
        AdvancePreferences.addProperty(relay.getNAME_PREFS_MODE_CONTROL(),RELAY_MODE_CONTROL);

        ProcessingSMS.sendSms(getContext(),sms);
    }
}
