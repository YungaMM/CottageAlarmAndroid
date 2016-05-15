package com.example.cottagealarmandroid.app.activity.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.activity.fragments.dialogs.RelayOff;
import com.example.cottagealarmandroid.app.activity.fragments.dialogs.RelayTimePeriod;
import com.example.cottagealarmandroid.app.adapters.MyExpListAdapter;
import com.example.cottagealarmandroid.app.model.DevicesAlarm;
import com.example.cottagealarmandroid.app.controllers.ProcessingSMS;
import com.example.cottagealarmandroid.app.controllers.SmsCommandsAlarm;
import com.example.cottagealarmandroid.app.model.Relay;

import java.util.ArrayList;


public class RelayFragment extends Fragment {
    //переменная для чтения ЛОГа
//    final String LOG_TAG = "myLogs"; //this.getClass().getSimpleName();

    private static final int REQUEST_OFF = 1;
    private static final int REQUEST_ON = 2;

    private ExpandableListView expListView;
    private DevicesAlarm devicesAlarm;
    private Relay[] relays;

    //Переменные для передачи данных из Listener
    private View v;
    private int groupPosition, childPosition;
    private MyExpListAdapter adapter;
    private Fragment relayFragment;
    private View relayFragmentView;

    //ToggleButton из BasicViewFragment
    private ToggleButton relay4Btn, relay5Btn, relay6Btn;
    private ArrayList<ToggleButton> groupTgBtn = new ArrayList<>();

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        relayFragmentView = inflater.inflate(R.layout.relay_view, container, false);
        relayFragment = this;//Используем для ссылки в Listener

        devicesAlarm = DevicesAlarm.getInstance();
        relays = devicesAlarm.getRelays();
        String[] modeControl = getResources().getStringArray(R.array.modeControl);

        adapter = new MyExpListAdapter(relayFragmentView.getContext(), getNameRelayStr(),
                modeControl, getModeControlStr());
        expListView = (ExpandableListView) relayFragmentView.findViewById(R.id.expListViewRelay);
        expListView.setAdapter(adapter);
        expListListener(adapter);

        relay4Btn = (ToggleButton) getActivity().findViewById(R.id.relay4Btn);
        relay5Btn = (ToggleButton) getActivity().findViewById(R.id.relay5Btn);
        relay6Btn = (ToggleButton) getActivity().findViewById(R.id.relay6Btn);
        groupTgBtn.add(relay4Btn);
        groupTgBtn.add(relay5Btn);
        groupTgBtn.add(relay6Btn);

        return relayFragmentView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String sms = "";
        Relay relay = relays[groupPosition];
        Boolean checkRelay = false;

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_OFF:
                    sms = SmsCommandsAlarm.createSmsRelayOff(relay);
                    checkRelay = false;
                    break;
                case REQUEST_ON:
                    String minute = data.getStringExtra(RelayTimePeriod.TAG_MIN);
                    String sec = data.getStringExtra(RelayTimePeriod.TAG_SEC);
                    sms = SmsCommandsAlarm.createSmsRelay(relay, minute, sec);
                    checkRelay = true;
                    break;
            }

            relay.setSmsCommand(sms, childPosition);
            ProcessingSMS.sendSms(getContext(), sms);
            if (relay.getCount() >= 3)
                groupTgBtn.get(relay.getCount()-3).setChecked(checkRelay);

            TextView textExistChild = (TextView) v.findViewById(android.R.id.text1);
            adapter.setExistChild(groupPosition, String.valueOf(textExistChild.getText()));
            expListView.setAdapter(adapter);
        }
    }

    private void expListListener(final MyExpListAdapter adapter) {
        adapter.setIndicatorGroupRight(expListView, getActivity());

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                setListParameters(v, groupPosition, childPosition);
                switch (childPosition) {
                    case (0):
                        openDialog(new RelayOff(), REQUEST_OFF);
                        break;
                    case (1):
                        openDialog(new RelayTimePeriod(), REQUEST_ON);
                        break;
                    case (2):

                        //break;
                    case (3):

                        //break;
                    case (4):

                        //break;
                    case (5):

                        //break;
                    default:
                        Toast.makeText(v.getContext(), "Обработка режима управления не установлена. " +
                                "Обратитесь к разработчику.", Toast.LENGTH_LONG).show();
                }
                expListView.collapseGroup(groupPosition);
                return false;
            }
        });
    }

    private void setListParameters(View v, int groupPosition, int childPosition) {
        this.v = v;
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
    }


    private String[] getNameRelayStr() {
        Relay[] relay = devicesAlarm.getRelays();
        String[] nameRelay = new String[relay.length];
        String str;
        for (int i = 0; i < relay.length; i++) {
            str = relay[i].getNAME_PREFS_RELAY();
            nameRelay[i] = str.replace(String.valueOf(i), String.valueOf(i + 1));
        }
        return nameRelay;
    }

    private void openDialog(final DialogFragment df, final int request) {
        Bundle args = new Bundle();
        args.putInt("countRelay", relays[groupPosition].getCount());
        df.setArguments(args);

        df.setTargetFragment(relayFragment, request);
        df.show(getFragmentManager(), df.getClass().getName());
    }

    private String[] getModeControlStr() {
        Relay[] relay = devicesAlarm.getRelays();
        String[] modeControl = new String[relay.length];
        for (int i = 0; i < relay.length; i++) {
            modeControl[i] = relay[i].getModeControl();
        }
        return modeControl;
    }
}
