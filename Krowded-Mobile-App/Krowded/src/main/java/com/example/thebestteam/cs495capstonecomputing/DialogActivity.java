package com.example.thebestteam.cs495capstonecomputing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class DialogActivity extends DialogFragment {

   public String internal_message;
   private AlertDialog d = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(internal_message)
                .setPositiveButton("selected positive button", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("selected negative button", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                }).show();
        // Create the AlertDialog object and return it
        return builder.create();
    }

    void setMessage(String message)
    {
        internal_message = message;
    }

}