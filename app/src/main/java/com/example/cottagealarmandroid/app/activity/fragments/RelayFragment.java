package com.example.cottagealarmandroid.app.activity.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.activity.fragments.dialogs.RelayOff;
import com.example.cottagealarmandroid.app.adapters.MyExpListAdapter;
import com.example.cottagealarmandroid.app.controllers.DevicesAlarm;
import com.example.cottagealarmandroid.app.model.Relay;


public class RelayFragment extends Fragment{
	private ExpandableListView expListView;
	private DevicesAlarm devicesAlarm;
	private Relay[] relays;

	private View v;
	private int groupPosition, childPosition;
	private MyExpListAdapter adapter;

	private Activity mainActivity;


	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
							 final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.relay_view, container, false);


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
				setListParametrs(v,groupPosition,childPosition);

				switch (childPosition){
					case (0):
						new RelayOff().show(getFragmentManager(),"Relay off");
						break;
					case (1):

						//break;
					case (2):

						//break;
					case (3):

						//break;
					case (4):

						//break;
					case (5):

						//break;
					default:
						Toast.makeText(v.getContext(),"Обработка режима управления не установлена. " +
								"Обратитесь к разработчику.",Toast.LENGTH_LONG).show();
				}
				//Сделать проверку: если состояние Реле не изменилось, оставить подпись как было
				//иначе изменить
				//Или заново считать опции и установить
//				TextView textOption = (TextView) v.findViewById(android.R.id.text1);
//				adapter.setOption(groupPosition, String.valueOf(textOption.getText()));
//				relays[groupPosition].setOption(String.valueOf(childPosition));
//

				expListView.collapseGroup(groupPosition);
				return false;
			}
		});
	}

	private void setListParametrs(View v, int groupPosition, int childPosition) {
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
			nameRelay[i] = str.replace(String.valueOf(i),String.valueOf(i+1));
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
