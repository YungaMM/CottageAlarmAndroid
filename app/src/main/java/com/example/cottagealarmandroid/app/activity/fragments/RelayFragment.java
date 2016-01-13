package com.example.cottagealarmandroid.app.activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.cottagealarmandroid.app.R;

public class RelayFragment extends Fragment implements View.OnClickListener{

	private Button btnRelay1, btnRelay2, btnRelay3, btnRelay4, btnRelay5, btnRelay6;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
							 final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.relay_view, container, false);

		setButton(view);


		return view;
	}

	private void setButton(final View view){
		btnRelay1 = (Button) view.findViewById(R.id.btnRelay1);
		btnRelay2 = (Button) view.findViewById(R.id.btnRelay2);
		btnRelay3 = (Button) view.findViewById(R.id.btnRelay3);
		btnRelay4 = (Button) view.findViewById(R.id.btnRelay4);
		btnRelay5 = (Button) view.findViewById(R.id.btnRelay5);
		btnRelay6 = (Button) view.findViewById(R.id.btnRelay6);

		btnRelay1.setOnClickListener(this);
		btnRelay2.setOnClickListener(this);
		btnRelay3.setOnClickListener(this);
		btnRelay4.setOnClickListener(this);
		btnRelay5.setOnClickListener(this);
		btnRelay6.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btnRelay1:

				break;
			case R.id.btnRelay2:
				break;
			case R.id.btnRelay3:
				break;
			case R.id.btnRelay4:
				break;
			case R.id.btnRelay5:

				break;
			case R.id.btnRelay6:
				break;
		}
	}
}
