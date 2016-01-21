package com.example.cottagealarmandroid.app.activity.fragments.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.controllers.SmsCommandsAlarm;

public class RelayTimePeriod extends DialogFragment {
    public static final String TAG_MIN = "Min";
    public static final String TAG_SEC = "Sec";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.set_relay_time_period, null);

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

        return builder.create();
    }

    private String checkValue(String value) {
        if (value.equals("")) value = "00";
        else if (value.length() < 2) value = "0" + value;

        return value;
    }
}
