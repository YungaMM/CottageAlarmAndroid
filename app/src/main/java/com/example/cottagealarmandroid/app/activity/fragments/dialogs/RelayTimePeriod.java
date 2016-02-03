package com.example.cottagealarmandroid.app.activity.fragments.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.controllers.DevicesAlarm;
import com.example.cottagealarmandroid.app.model.Relay;

public class RelayTimePeriod extends DialogFragment {
    public static final String TAG_MIN = "Min";
    public static final String TAG_SEC = "Sec";

    private static final DevicesAlarm devicesAlarm = DevicesAlarm.getInstance();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.set_relay_time_period, null);

        setMinSecText(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                EditText valueMin = (EditText) view.findViewById(R.id.valueMinOn);
                EditText valueSec = (EditText) view.findViewById(R.id.valueSecOn);

                String minute = checkValue(valueMin.getText().toString());
                String sec = checkValue(valueSec.getText().toString());

                intent.putExtra(TAG_MIN, minute);
                intent.putExtra(TAG_SEC, sec);
                getTargetFragment().onActivityResult(getTargetRequestCode()
                        , Activity.RESULT_OK
                        , intent);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setCancelable(true);

        Button btnAbout = (Button) view.findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment df = new AboutRelayTimePeriod();
                FragmentManager mng = getFragmentManager();
                df.show(mng, df.getClass().getName());
            }
        });

        return builder.create();
    }

    private void setMinSecText(final View view) {
        String textMin, textSec;
        Relay relay = devicesAlarm.getRelay(getArguments().getInt("countRelay"));
        String txtSmsCommand = relay.getSmsCommand();

        if (txtSmsCommand.contains("=0")) {
            textMin = txtSmsCommand.substring(txtSmsCommand.indexOf(","), 2);
            textSec = txtSmsCommand.substring(txtSmsCommand.indexOf("-"), 2);
        } else {
            textMin = "00";
            textSec = "00";
        }

        EditText valueMin = (EditText) view.findViewById(R.id.valueMinOn);
        EditText valueSec = (EditText) view.findViewById(R.id.valueSecOn);

        valueMin.setText(textMin);
        valueSec.setText(textSec);

    }

    private String checkValue(String value) {
        if (value.equals("")) value = "00";
        else if (value.length() < 2) value = "0" + value;

        return value;
    }
}
