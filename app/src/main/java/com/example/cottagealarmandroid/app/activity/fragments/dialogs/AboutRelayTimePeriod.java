package com.example.cottagealarmandroid.app.activity.fragments.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import com.example.cottagealarmandroid.app.R;

public class AboutRelayTimePeriod extends DialogFragment{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Описание");
        builder.setMessage(R.string.textDescriptionRelayTimePeriod);
        builder.setPositiveButton(android.R.string.ok, null);

        return builder.create();
    }
}
