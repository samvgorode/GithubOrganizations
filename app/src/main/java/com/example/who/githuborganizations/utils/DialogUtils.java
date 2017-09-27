package com.example.who.githuborganizations.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by who on 27.09.2017.
 */

public abstract class DialogUtils {

    public static void showInternetAlertDialog(Context context){
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle("Error");
        alertDialog.setMessage("Internet not available, please check your internet connectivity and try again");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               alertDialog.hide();
            }
        });
        alertDialog.show();
    }
}
