package com.example.cottagealarmandroid.app.activity.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.controllers.AdvancePreferences;
import com.example.cottagealarmandroid.app.controllers.DevicesAlarm;
import com.example.cottagealarmandroid.app.model.BasicAlarmProperty;

public class EnterPhoneAlarmFragment extends DialogFragment implements
        DialogInterface.OnClickListener {

    private View form = null;

    private EditText phoneAlarm;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        form = getActivity().getLayoutInflater()
                .inflate(R.layout.enter_phone_alarm, null);

        phoneAlarm = (EditText) form.findViewById(R.id.value_phone_alarm);
        phoneAlarm.setText(DevicesAlarm.getInstance().basicAlarmProperty.getAlarmPhone());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return (builder.setTitle(getString(R.string.title_phone_alarm)).setView(form)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null).create());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String phone = phoneAlarm.getText().toString();
        DevicesAlarm.getInstance().basicAlarmProperty.setAlarmPhone(phone);
        AdvancePreferences.addProperty(BasicAlarmProperty.NAME_PREFS_ALARM_PHONE, phone);
    }

    @Override
    public void onDismiss(DialogInterface unused) {
        super.onDismiss(unused);
    }

    @Override
    public void onCancel(DialogInterface unused) {
        super.onCancel(unused);
    }
}
