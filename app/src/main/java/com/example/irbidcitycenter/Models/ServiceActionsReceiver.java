package com.example.irbidcitycenter.Models;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.http.DelegatingSSLSession;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.irbidcitycenter.Activity.Login;
import com.example.irbidcitycenter.R;

import static com.example.irbidcitycenter.Activity.Login.SYSTEM_DIALOG_REASON_HOME_KEY;
import static com.example.irbidcitycenter.Activity.Login.SYSTEM_DIALOG_REASON_KEY;

public class ServiceActionsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("onReceive","onReceive");
            if (intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))

            {
              String reason =intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason == SYSTEM_DIALOG_REASON_HOME_KEY) {
                    Log.e("recev","home");
                }
                    else {
                    Log.e("elserecev","home");
                   System.exit(1);

                    }
            }


}
    private void openUthenticationDialog(Context context) {
        final Dialog dialog1 = new Dialog(context);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(false);
        dialog1.setContentView(R.layout.passworddailog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());

        lp.gravity = Gravity.CENTER;
        dialog1.getWindow().setAttributes(lp);


        EditText editText = dialog1.findViewById(R.id.passwordd);
        Button donebutton = dialog1.findViewById(R.id.done);
        Button cancelbutton = dialog1.findViewById(R.id.cancel);
        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().equals("304555")) {

                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                    dialog1.dismiss();
                }
            }
        });
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog1.dismiss();
            }
        });


        dialog1.show();
      /*  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();*/
    }
}
