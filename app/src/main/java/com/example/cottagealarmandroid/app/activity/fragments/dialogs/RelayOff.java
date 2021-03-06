package com.example.cottagealarmandroid.app.activity.fragments.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import com.example.cottagealarmandroid.app.R;

public class RelayOff extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.title));  // заголовок
        builder.setMessage(getString(R.string.message)); // сообщение

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setCancelable(true);// Чтобы окно можно было закрыть только нажатием на кнопки

        return builder.create();
    }
}
