package com.example.irbidcitycenter.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;
public class SmsService extends Service {

    private String Sender=null;
    private String Code=null;
    public void setCode(String code){
        Code=code;
    }
    public void setSender(String sender){
        Sender=sender;
    }
    private IBinder mBinder = new MyBinder();
    private BroadcastReceiver smsReceiver;

    private static final String ACTION="android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onCreate(){
        smsReceiver=new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // Do whatever you need it to do when it receives the broadcast
                // Example show a Toast message...
                Bundle extras = intent.getExtras();
                Log.i("Receiver","broadcast received");

                if (extras == null)
                    return;

                Object[] pdus = (Object[]) extras.get("pdus");
                SmsMessage msg = SmsMessage.createFromPdu((byte[]) pdus[0]);
                String origNumber = msg.getOriginatingAddress();
                String msgBody = msg.getMessageBody();
                Log.i("Receiver",origNumber);
                Log.i("Receiver",msgBody);
                Log.i("Receiver", intent.toString());

                if(Sender!=null && origNumber.contains(Sender)){
                    if(Code!=null && msgBody.contains(Code)){
                        Log.i("Receiver","matched");
                    }
                }
            }
        };

        final IntentFilter theFilter = new IntentFilter();
        theFilter.addAction(ACTION);
        theFilter.setPriority(2147483647);
        this.registerReceiver(this.smsReceiver, theFilter);
        Log.i("Received","service");
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    public class MyBinder extends Binder {
        public SmsService getService() {
            return SmsService.this;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(smsReceiver);

    }
}
