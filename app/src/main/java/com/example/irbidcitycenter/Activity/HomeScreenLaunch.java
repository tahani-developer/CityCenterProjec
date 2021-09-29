package com.example.irbidcitycenter.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
public class HomeScreenLaunch extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)||
                intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)||
                intent.getAction().equals(Intent.ACTION_USER_PRESENT)||
                intent.getAction().equals(Intent.ACTION_REBOOT) ||
                intent.getAction().equals(Intent.ACTION_USER_UNLOCKED)
        )
        {

            Intent launch = new Intent(context, Login.class);
            launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(launch);
        }
    }
}