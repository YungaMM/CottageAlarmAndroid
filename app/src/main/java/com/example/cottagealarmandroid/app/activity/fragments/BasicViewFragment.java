package com.example.cottagealarmandroid.app.activity.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.controllers.ProcessingSMS;
import com.example.cottagealarmandroid.app.controllers.SmsCommandsAlarm;
import com.example.cottagealarmandroid.app.model.DevicesAlarm;
import com.example.cottagealarmandroid.app.model.Relay;

import java.util.ArrayList;

public class BasicViewFragment extends Fragment implements
        View.OnClickListener {

    //переменная для чтения ЛОГа
    final String LOG_TAG = "myLogs"; //this.getClass().getSimpleName();

    private TextView dateAlarm;
    private Relay[] relay;
    private ToggleButton relay4Btn, relay5Btn, relay6Btn;
    private ArrayList<ToggleButton> groupTgBtn = new ArrayList<>();

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.basic_view, container, false);

        dateAlarm = (TextView) fragmentView.findViewById(R.id.valueStateOnDate);

        relay4Btn = (ToggleButton) fragmentView.findViewById(R.id.relay4Btn);
        relay5Btn = (ToggleButton) fragmentView.findViewById(R.id.relay5Btn);
        relay6Btn = (ToggleButton) fragmentView.findViewById(R.id.relay6Btn);
        groupTgBtn.add(relay4Btn);
        groupTgBtn.add(relay5Btn);
        groupTgBtn.add(relay6Btn);
        for (int i = 0; i < groupTgBtn.size(); i++) {
            groupTgBtn.get(i).setOnClickListener(this);
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

    private void setRelay(Relay relay, boolean tgBtnChecked) {
        String sms;
        int modeControl;
        if (tgBtnChecked) {
            sms = SmsCommandsAlarm.createSmsRelay(relay, "00", "00");
            modeControl = 1;
        } else {
            sms = SmsCommandsAlarm.createSmsRelayOff(relay);
            modeControl = 0;
        }
        relay.setSmsCommand(sms, modeControl);
        ProcessingSMS.sendSms(getContext(), sms);
    }

    @Override
    public void onClick(View view) {
        int relayId = 0;
        boolean isChecked = false;
        switch (view.getId()) {
            case R.id.relay4Btn:
                isChecked = relay4Btn.isChecked();
                relayId = 3;
                break;
            case R.id.relay5Btn:
                isChecked = relay5Btn.isChecked();
                relayId = 4;
                break;
            case R.id.relay6Btn:
                isChecked = relay6Btn.isChecked();
                relayId = 5;
                break;
        }
        if (relayId != 0) {
            setRelay(relay[relayId], isChecked);
//            Fragment fragment = Fragment.instantiate(getContext(), RelayFragment.class.getName());
//            fragment.onActivityResult(Activity.RESULT_OK,);
        }
    }
}
