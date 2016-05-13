package com.example.cottagealarmandroid.app.activity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import java.util.ArrayList;

public class BasicViewFragment extends Fragment implements
        CompoundButton.OnCheckedChangeListener{

    //переменная для чтения ЛОГа
    final String LOG_TAG = "myLogs"; //this.getClass().getSimpleName();
    final String NAME_RESTORED_VIEW_METOD = "onViewStateRestored";

    private static final String RELAY_MODE_CONTROL = "1"; //Включение реле на заданное время
    private TextView dateAlarm;
    private Relay[] relay;
    private ToggleButton tgBtn, tgBtn2, tgBtn3;
    private ArrayList<ToggleButton> groupTgBtn = new ArrayList<>();

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.basic_view, container, false);

        dateAlarm = (TextView) fragmentView.findViewById(R.id.valueStateOnDate);
        tgBtn = (ToggleButton) fragmentView.findViewById(R.id.relay4Btn);
        tgBtn2 = (ToggleButton) fragmentView.findViewById(R.id.relay5Btn);
        tgBtn3 = (ToggleButton) fragmentView.findViewById(R.id.relay6Btn);

        groupTgBtn.add(tgBtn);
        groupTgBtn.add(tgBtn2);
        groupTgBtn.add(tgBtn3);

        for (int i = 0; i < groupTgBtn.size(); i++) {
            groupTgBtn.get(i).setOnCheckedChangeListener(this);
        }

        return fragmentView;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        //Устанавливаем значения кнопок Реле согласно записанным установкам
        relay = DevicesAlarm.getInstance().getRelays();
        for (int i = 0; i < relay.length-3; i++) {
            if(relay[i+3].getModeControl().equals("0")){
                groupTgBtn.get(i).setChecked(false);
            } else {
                groupTgBtn.get(i).setChecked(true);
            }
            String log = "i=" + i + " relay[i].getModeControl()=" + relay[i].getModeControl() +
                    " tgBtn=" + groupTgBtn.get(i).getId() +
                    " tgBtn.isChecked()=" + groupTgBtn.get(i).isChecked();
            Log.i(LOG_TAG, log);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        dateAlarm.setText(DevicesAlarm.getInstance().getBasicAlarmProperty().getDateInDevice());
    }

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
