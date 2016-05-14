package com.example.cottagealarmandroid.app.activity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.controllers.AdvancePreferences;
import com.example.cottagealarmandroid.app.controllers.ProcessingSMS;
import com.example.cottagealarmandroid.app.controllers.SmsCommandsAlarm;
import com.example.cottagealarmandroid.app.model.DevicesAlarm;
import com.example.cottagealarmandroid.app.model.Relay;

import java.util.ArrayList;

public class BasicViewFragment extends Fragment implements
//        CompoundButton.OnCheckedChangeListener,
        View.OnClickListener {

    //переменная для чтения ЛОГа
    final String LOG_TAG = "myLogs"; //this.getClass().getSimpleName();

    private static final String RELAY_MODE_CONTROL = "1"; //Включение реле на заданное время
    private TextView dateAlarm;
    private Relay[] relay;
    private ToggleButton tgBtn, tgBtn2, tgBtn3;
    private ArrayList<ToggleButton> groupTgBtn = new ArrayList<>();
    private Button smsRelayBtn;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.basic_view, container, false);

        dateAlarm = (TextView) fragmentView.findViewById(R.id.valueStateOnDate);

        smsRelayBtn = (Button) fragmentView.findViewById(R.id.smsRelayBtn);
        smsRelayBtn.setOnClickListener(this);

        tgBtn = (ToggleButton) fragmentView.findViewById(R.id.relay4Btn);
        tgBtn2 = (ToggleButton) fragmentView.findViewById(R.id.relay5Btn);
        tgBtn3 = (ToggleButton) fragmentView.findViewById(R.id.relay6Btn);
        groupTgBtn.add(tgBtn);
        groupTgBtn.add(tgBtn2);
        groupTgBtn.add(tgBtn3);

        for (int i = 0; i < groupTgBtn.size(); i++) {
            groupTgBtn.get(i).setOnClickListener(this);
//            groupTgBtn.get(i).setOnCheckedChangeListener(this);
        }

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        dateAlarm.setText(DevicesAlarm.getInstance().getBasicAlarmProperty().getDateInDevice());

        //Устанавливаем значения кнопок Реле согласно записанным установкам
        relay = DevicesAlarm.getInstance().getRelays();
        int relayId = 3;
        for (ToggleButton aGroupTgBtn : groupTgBtn) {
            boolean isCheck = !relay[relayId].getModeControl().equals("0");
            aGroupTgBtn.setChecked(isCheck);
            relayId++;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

//    @Override
//    public void onCheckedChanged(CompoundButton relayButton, boolean isChecked) {
//        int relayId = 0;
//        switch (relayButton.getId()) {
//            case R.id.relay4Btn:
//                relayId = 3;
//                break;
//            case R.id.relay5Btn:
//                relayId = 4;
//                break;
//            case R.id.relay6Btn:
//                relayId = 5;
//                break;
//        }
//        if (relayId != 0) {
//            Toast.makeText(getContext(), "Зашли в  onCheckedChanged", Toast.LENGTH_SHORT).show();
//            setRelay(relay[relayId], isChecked);
//        }
//
//    }

    private void setRelay(Relay relay, boolean tgBtnChecked) {
        String sms;
        int modeControl;
        if (tgBtnChecked) {
            sms = SmsCommandsAlarm.createSmsRelay(relay, "00", "00");
            modeControl=1;
        } else {
            sms = SmsCommandsAlarm.createSmsRelayOff(relay);
            modeControl=0;
        }
        relay.setSmsCommand(sms,modeControl);
//        AdvancePreferences.addProperty(relay.getNAME_PREFS_SMS(), sms);
//        relay.setModeControl(RELAY_MODE_CONTROL);
//        AdvancePreferences.addProperty(relay.getNAME_PREFS_MODE_CONTROL(), RELAY_MODE_CONTROL);

        ProcessingSMS.sendSms(getContext(), sms);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.relay4Btn:

                break;
            case R.id.relay5Btn:

                break;
            case R.id.relay6Btn:

                break;

        }
        Toast.makeText(getContext(), "Нажали smsRelayBtn", Toast.LENGTH_SHORT).show();
    }
}
