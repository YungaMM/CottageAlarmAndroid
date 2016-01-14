package com.example.cottagealarmandroid.app.activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.adapters.MyExpListAdapter;
import com.example.cottagealarmandroid.app.controllers.DevicesAlarm;
import com.example.cottagealarmandroid.app.model.Relay;


public class RelayFragment extends Fragment implements View.OnClickListener{

	private DevicesAlarm devicesAlarm;

	String[] nameRelay, optionRelay;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
							 final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.relay_view, container, false);

//		devicesAlarm = DevicesAlarm.getInstance();
//
//		getRelayStr();
//
//		String[] modeControl = getResources().getStringArray(R.array.modeControl);
//
//		final MyExpListAdapter adapter = new MyExpListAdapter(view.getContext(), nameRelay,
//				modeControl, optionRelay);
//

		return view;
	}

	private void getRelayStr() {
		Relay[] relay = devicesAlarm.getRelays();
		nameRelay = new String[relay.length];

		for (int i = 0; i < relay.length; i++) {
			nameRelay[i] = relay[i].NAME_PREFS_RELAY;
			optionRelay[i] = "";
			//optionRelay[i] = relay[i].getOption();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){

		}
	}
}
