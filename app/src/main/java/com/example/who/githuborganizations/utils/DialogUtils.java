package com.example.who.githuborganizations.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.who.githuborganizations.R;

/**
 * Created by who on 27.09.2017.
 */

public abstract class DialogUtils {

    public static void showInternetAlertDialog(Context context) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(context.getString(R.string.internet_error_title));
        alertDialog.setMessage(context.getString(R.string.internet_error_message));
        Drawable icon = ContextCompat.getDrawable(context, R.drawable.alert);
        alertDialog.setIcon(icon);
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.hide();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
