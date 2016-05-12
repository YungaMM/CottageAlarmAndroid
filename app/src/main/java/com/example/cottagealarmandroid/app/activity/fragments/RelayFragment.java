package com.example.cottagealarmandroid.app.activity.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.activity.fragments.dialogs.RelayOff;
import com.example.cottagealarmandroid.app.activity.fragments.dialogs.RelayTimePeriod;
import com.example.cottagealarmandroid.app.adapters.MyExpListAdapter;
import com.example.cottagealarmandroid.app.controllers.AdvancePreferences;
import com.example.cottagealarmandroid.app.model.DevicesAlarm;
import com.example.cottagealarmandroid.app.controllers.ProcessingSMS;
import com.example.cottagealarmandroid.app.controllers.SmsCommandsAlarm;
import com.example.cottagealarmandroid.app.model.Relay;


public class RelayFragment extends Fragment {
    //переменная для чтения ЛОГа
    final String LOG_TAG = "myLogs"; //this.getClass().getSimpleName();

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

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.relay_view, container, false);

        relayFragment = this;//Используем для ссылки в Listener

        devicesAlarm = DevicesAlarm.getInstance();
        relays = devicesAlarm.getRelays();
        for (int i = 0; i < relays.length; i++) {
            String log = "i=" + i + " relay[i].getName=" + relays[i].getName() +
                    " relays[i].getModeControl()=" + relays[i].getModeControl();
            Log.i(LOG_TAG, log);
        }

        String[] modeControl = getResources().getStringArray(R.array.modeControl);

        adapter = new MyExpListAdapter(view.getContext(), getNameRelayStr(),
                modeControl, getModeControlStr());
        expListView = (ExpandableListView) view.findViewById(R.id.expListViewRelay);
        expListView.setAdapter(adapter);
        expListListener(adapter);


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String sms = "";
        Relay relay = relays[groupPosition];

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_OFF:
                    sms = SmsCommandsAlarm.createSmsRelayOff(relay);
                    break;
                case REQUEST_ON:
                    String minute = data.getStringExtra(RelayTimePeriod.TAG_MIN);
                    String sec = data.getStringExtra(RelayTimePeriod.TAG_SEC);
                    sms = SmsCommandsAlarm.createSmsRelay(relay, minute, sec);
                    break;
            }
            relay.setSmsCommand(sms);
            AdvancePreferences.addProperty(relay.getNAME_PREFS_SMS(), sms);
            String str = String.valueOf(childPosition);
            relays[groupPosition].setModeControl(str);
            AdvancePreferences.addProperty(relays[groupPosition].getNAME_PREFS_MODE_CONTROL(), str);

            ProcessingSMS.sendSms(getContext(), sms);

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
