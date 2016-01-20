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
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.activity.fragments.dialogs.RelayOff;
import com.example.cottagealarmandroid.app.activity.fragments.dialogs.RelayTimePeriod;
import com.example.cottagealarmandroid.app.adapters.MyExpListAdapter;
import com.example.cottagealarmandroid.app.controllers.AdvancePreferences;
import com.example.cottagealarmandroid.app.controllers.DevicesAlarm;
import com.example.cottagealarmandroid.app.model.Relay;


public class RelayFragment extends Fragment {
    private static final int REQUEST_OFF = 1;
    private static final int REQUEST_ON = 2;

    private ExpandableListView expListView;
    private DevicesAlarm devicesAlarm;
    private Relay[] relays;

    private View v;
    private int groupPosition, childPosition;
    private MyExpListAdapter adapter;

    private Fragment relayFragment;
    private DialogFragment dialogFragment;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.relay_view, container, false);

        relayFragment = this;//Используем для ссылки в Listener

        devicesAlarm = DevicesAlarm.getInstance();
        relays = devicesAlarm.getRelays();

        String[] modeControl = getResources().getStringArray(R.array.modeControl);

        adapter = new MyExpListAdapter(view.getContext(), getNameRelayStr(),
                modeControl, getOptionRelayStr());
        expListView = (ExpandableListView) view.findViewById(R.id.expListViewRelay);
        expListView.setAdapter(adapter);
        expListListener(adapter);


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_OFF:
                    Toast.makeText(getContext(), "Вернулись в фрагмент", Toast.LENGTH_SHORT).show();
                    AdvancePreferences.addProperty(relays[groupPosition].getNAME_PREFS_OPTION()
                            , "0");
                    break;
                case REQUEST_ON:
                    relays[groupPosition].setOption(data.getStringExtra(RelayTimePeriod.TAG_OPTION_RELAY));
                    AdvancePreferences.addProperty(relays[groupPosition].getNAME_PREFS_OPTION()
                            , relays[groupPosition].getOption());
                    break;
            }

            TextView textExistChild = (TextView) v.findViewById(android.R.id.text1);
            adapter.setExistChild(groupPosition, String.valueOf(textExistChild.getText()));

            String str = String.valueOf(childPosition);
            relays[groupPosition].setModeControl(str);
//            relays[groupPosition].setExistChild(String.valueOf(childPosition));
            AdvancePreferences.addProperty(relays[groupPosition].getNAME_PREFS_MODE_CONTROL(), str);


            expListView.setAdapter(adapter);
        }
    }

    //	@Override
//	public void onResume() {
//		super.onResume();
//		TextView textOption = (TextView) v.findViewById(android.R.id.text1);
//		adapter.setExistChild(groupPosition, String.valueOf(textOption.getText()));
//		relays[groupPosition].setExistChild(String.valueOf(childPosition));
//	}

    private void expListListener(final MyExpListAdapter adapter) {
        adapter.setIndicatorGroupRight(expListView, getActivity());

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {

                setListParameters(v, groupPosition, childPosition);

                switch (childPosition) {
                    case (0):
//                        dialogFragment = new RelayOff();
//                        dialogFragment.setTargetFragment(relayFragment, REQUEST_OFF);
//                        dialogFragment.show(getFragmentManager(), dialogFragment.getClass().getName());
                        openDialog(new RelayOff());
                        break;
                    case (1):
                        openDialog(new RelayTimePeriod());
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

    private void openDialog(DialogFragment df) {
        df.setTargetFragment(relayFragment, REQUEST_OFF);
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

    private String[] getOptionRelayStr() {
        Relay[] relay = devicesAlarm.getRelays();
        String[] optionRelay = new String[relay.length];
        for (int i = 0; i < relay.length; i++) {
            optionRelay[i] = relay[i].getOption();
        }
        return optionRelay;
    }

}
