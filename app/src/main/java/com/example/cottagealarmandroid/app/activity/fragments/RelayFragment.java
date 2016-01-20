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
                    break;
                case REQUEST_ON:
                    Toast.makeText(getContext(), "Вернулись из вкл реле на время", Toast.LENGTH_SHORT).show();
//                    relays[groupPosition].setOption(data.getStringExtra(RelayTimePeriod.TAG_OPTION_RELAY));
                    break;
            }

            TextView textOption = (TextView) v.findViewById(android.R.id.text1);
            adapter.setOption(groupPosition, String.valueOf(textOption.getText()));
            relays[groupPosition].setModeControl(String.valueOf(childPosition));
//            relays[groupPosition].setOption(String.valueOf(childPosition));

            expListView.setAdapter(adapter);
        }
    }

    //	@Override
//	public void onResume() {
//		super.onResume();
//		TextView textOption = (TextView) v.findViewById(android.R.id.text1);
//		adapter.setOption(groupPosition, String.valueOf(textOption.getText()));
//		relays[groupPosition].setOption(String.valueOf(childPosition));
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
                        dialogFragment = new RelayOff();
                        dialogFragment.setTargetFragment(relayFragment, REQUEST_OFF);
                        dialogFragment.show(getFragmentManager(), dialogFragment.getClass().getName());
                        break;
                    case (1):
                        dialogFragment = new RelayTimePeriod();
                        dialogFragment.setTargetFragment(relayFragment, REQUEST_ON);
                        dialogFragment.show(getFragmentManager()
                                , dialogFragment.getClass().getName());
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

    private String[] getOptionRelayStr() {
        Relay[] relay = devicesAlarm.getRelays();
        String[] optionRelay = new String[relay.length];
        for (int i = 0; i < relay.length; i++) {
            optionRelay[i] = relay[i].getOption();
        }
        return optionRelay;
    }

}
