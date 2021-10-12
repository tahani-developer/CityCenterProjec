package com.example.irbidcitycenter.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Interfaces.OnHomePressedListener;

import com.example.irbidcitycenter.Models.HomeWatcher;


import com.example.irbidcitycenter.Models.appSettings;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;




import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity {
    EditText username,password;

    boolean activitySwitchFlag = false;
    LinearLayout colorLinear, imgInner, imgOutter;
    FrameLayout mainLinearAnim;
    ImportData importData;
    public RoomAllData my_dataBase;
    public  static   TextView getListCom,selectedCompany;
    public   String selectedCom="", cono="",coYear="";;
    GeneralMethod generalMethod;
TextView settings;
    public String COMPANYNO;
    public appSettings setting;
    List<appSettings> appSettings;
    public  String SET_qtyup;

    public boolean isApplicationSentToBackground(final Context context)
    {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.


// home key is locked since then
       // homeKeyLocker.unlock();
// home key is unlocked since then





        //1.THIS IS FOR APP LUNCHE AFTER SCREEN_ON
        KeyguardManager.KeyguardLock lock = ((KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE)).newKeyguardLock(KEYGUARD_SERVICE);
        PowerManager powerManager = ((PowerManager) getSystemService(Context.POWER_SERVICE));
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wake = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");

        lock.disableKeyguard();
        wake.acquire();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
     //END OF 1

        //2.THIS IS FOR HOME PRESS CONTROL
        HomeWatcher mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                Log.e("heeere==","home");
              //  openUthenticationDialog();
              /*  Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.irbidcitycenter");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }*/
            ;







            }
            @Override
            public void onHomeLongPressed() {
                Log.e("heeere==","LONGhome");
            }
        });
        mHomeWatcher.startWatch();

        //END OF 2



        //stop notification
         //Remove title bar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
      //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);




        colorLinear = findViewById(R.id.colorLinear);
        mainLinearAnim = findViewById(R.id.mainLinearAnim);
        imgInner = (LinearLayout) findViewById(R.id.colorLinear);
        imgOutter = (LinearLayout) findViewById(R.id.colorLinear2);
        selectedCompany=findViewById(R.id.selectedCompany);
        generalMethod=new GeneralMethod(Login.this);
        my_dataBase= RoomAllData.getInstanceDataBase(Login.this);
        setImageLoop();
        username = findViewById(R.id.username_input);
        password = findViewById(R.id.pass);
        importData=new ImportData(Login.this);
        settings=findViewById(R.id.setting);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingDialog();
            }
        });

        username=findViewById(R.id.username_input);
        password=findViewById(R.id.pass);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().equals("6") && password.getText().toString().trim().equals("123")) {
                    MainActivity.SET_userNO = username.getText().toString().trim();
                    if(existCoNo(2))
                    {
                        if(existCoNo(1))
                        {
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            generalMethod.showSweetDialog(Login.this,3,"fill company No setting","");
                        }

                    }
                    else {
                        generalMethod.showSweetDialog(Login.this,3,"fill ip setting","");
                    }

                } else {
                    username.setError("");
                    password.setError("");

                }
            }
        });

    }
    //

  /* @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("tag==", "MYonStop is called");
        // insert here your instructions
    }*/

   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

       // if(keyCode==KeyEvent.KEYCODE_BACK || keyCode==KeyEvent.KEYCODE_HOME||keyCode==KeyEvent.KEYCODE_SWITCH_CHARSET){
       Log.e("heeere==",keyCode+"");
       if( keyCode==KeyEvent.KEYCODE_BACK)
       {
           Log.e("heeere==","home");
           activitySwitchFlag = true;
            //openUthenticationDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

   /* @Override
    protected void onPause() {
        Log.e("onPause","onPause");
        super.onPause();

      ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
        //openUthenticationDialog();


   /*     Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);*/


    /*
    @Override
    public void onPause(){
        super.onPause();
        Log.v("TAG", "onPause" );
        if(activitySwitchFlag)
            Log.v("TAG", "activity switch");
        else
            Log.v("TAG", "home button");
        activitySwitchFlag = false;
    }*/

    private void openUthenticationDialog() {
        final Dialog dialog1 = new Dialog(Login.this);
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
                    moveTaskToBack(true);
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

    private void setImageLoop() {
        // Need a thread to get the real size or the parent
        // container, after the UI is displayed
        imgInner.post(new Runnable() {
            @Override
            public void run() {
                TranslateAnimation outAnim =
                        new TranslateAnimation(
                                0f, mainLinearAnim.getWidth(), 0f, 0f);
                // move from 0 (START) to width (PARENT_SIZE)
                outAnim.setInterpolator(new LinearInterpolator());
                outAnim.setRepeatMode(Animation.INFINITE); // repeat the animation
                outAnim.setRepeatCount(Animation.INFINITE);
                outAnim.setDuration(2000);

                TranslateAnimation inAnim =
                        new TranslateAnimation(
                                -mainLinearAnim.getWidth(), 0f, 0f, 0f);
                // move from out width (-PARENT_SIZE) to 0 (START)
                inAnim.setInterpolator(new LinearInterpolator());
                inAnim.setRepeatMode(Animation.INFINITE);
                inAnim.setRepeatCount(Animation.INFINITE);
                inAnim.setDuration(2000); // same duration as the first

                imgInner.startAnimation(outAnim); // start first anim
                imgOutter.startAnimation(inAnim); // start second anim
            }
        });
    }

    private void animateFromButtumToTop() {
        Animation RightSwipe = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f);

        RightSwipe.setDuration(2000); // animation duration
        RightSwipe.setRepeatCount(Animation.INFINITE); // animation repeat count
        RightSwipe.setRepeatMode(2); // repeat animation (left to right, right to left)

        colorLinear.startAnimation(RightSwipe);
    }

    public void slidefromRightToLeft(View view) {

        TranslateAnimation animate;
        if (view.getHeight() == 0) {
            view.getHeight(); // parent layout
            animate = new TranslateAnimation(view.getWidth() / 2,
                    0, 0, 0);
        } else {
            animate = new TranslateAnimation(view.getWidth(), 0, 0, 0); // View for animation
        }

        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE); // Change visibility VISIBLE or GONE
    }

    public void showCompanyDialog(View view) {
        if(view.getId()==R.id.companyName)
        {
            if(existCoNo(2))
            showCompany(Login.this);
            else {
                openSettingDialog();
                //generalMethod.openSettingDialog();
            }
        }
    }
    private void showCompany(final Context context1) {

        final Dialog dialog = new Dialog(context1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.zone_search);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
         appSettings settings;
        importData=new ImportData(Login.this);
        ArrayList<String> nameOfEngi=new ArrayList<>();
        final ListView listCompany = dialog.findViewById(R.id.listViewEngineering);
        importData.getCompanyInfo();

        getListCom=dialog.findViewById(R.id.getListCom);

        getListCom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()!=0)
                {
                    if(editable.toString().equals("fill"))
                    {
                        if( importData.companyInList.size()!=0) {
                            for (int i = 0; i < importData.companyInList.size(); i++) {
                                nameOfEngi.add(importData.companyInList.get(i).getCoNo() + "\t\t" + importData.companyInList.get(i).getCoYear() + "\t\t" + importData.companyInList.get(i).getCoNameA());
                            }

                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                    (Login.this, android.R.layout.simple_list_item_1, nameOfEngi) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    /// Get the Item from ListView
                                    View view = super.getView(position, convertView, parent);

                                    TextView tv = (TextView) view.findViewById(android.R.id.text1);

                                    // Set the text size 25 dip for ListView each item
                                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                                    tv.setTextColor(getResources().getColor(R.color.text_color));
                                    // Return the view
                                    return view;
                                }
                            };

                            // DataBind ListView with items from ArrayAdapter
                            listCompany.setAdapter(arrayAdapter);


                        }
                    }

                }

            }
        });


        final int[] rowZone = new int[1];
        selectedCom="";

        listCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cono="";coYear="";
                rowZone[0] =position;
                listCompany.requestFocusFromTouch();
                listCompany.setSelection(position);
                selectedCom =importData.companyInList.get(position).getCoNameA();
                cono=importData.companyInList.get(position).getCoNo();
                coYear=importData.companyInList.get(position).getCoYear();

            }
        });


        Button btn_send = dialog.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCompany.setText(selectedCom);

                    updateCono(cono,coYear);

                dialog.dismiss();


            }
        });
        dialog.show();

    }

    private void updateCono(String cono, String coYear) {
        my_dataBase.settingDao().updateCompanyInfo(cono);
        my_dataBase.settingDao().updateCompanyYear(coYear);

       //Log.e("updateCono",""+up);
    }

    private void addSetting() {
        //my_dataBase.settingDao().insert(settings);
    }



    private boolean existCoNo(int flag ) {
           // ipAddress=my_dataBase.settingDao().getIpAddress().trim();
        String  CONO="";
        try {
            if(flag==1)
            {
                CONO=my_dataBase.settingDao().getCono();
            }else {
                if(flag==2)
                    CONO=my_dataBase.settingDao().getIpAddress();
            }

            Log.e("CONO",""+CONO);
            if(CONO!=null)
            {
                if(CONO.length()!=0)
                {
                    return true;
                }

            }
        }catch (Exception e){
            return  false;
        }


            return  false;


        }
    private void openSettingDialog() {
        final Dialog dialog = new Dialog(Login.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ip_setting_dialog);
        dialog.show();


        final EditText ip= dialog.findViewById(R.id.ipEditText);
        final TextView editip= dialog.findViewById(R.id.editip);
       // ip.setEnabled(false);
        final EditText conNO= dialog.findViewById(R.id.cono);
        final EditText years=dialog.findViewById(R.id.storeNo_edit);
        final CheckBox qtyUP=(CheckBox)dialog.findViewById(R.id.qtycheck);
        final EditText usernum= dialog.findViewById(R.id.usernumber);
        final EditText deviceId= dialog.findViewById(R.id.deviceId);



        ip.setEnabled(false);
       // conNO.setEnabled(false);
      //  years.setEnabled(false);
      //  usernum.setEnabled(false);
        editip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!ip.getText().toString().equals("")) {
                final Dialog dialog1 = new Dialog(Login.this);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(false);
                dialog1.setContentView(R.layout.passworddailog);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog1.getWindow().getAttributes());
                ip.setEnabled(true);
                lp.gravity = Gravity.CENTER;
                dialog1.getWindow().setAttributes(lp);


                EditText editText=dialog1.findViewById(R.id.passwordd);
                Button donebutton=dialog1.findViewById(R.id.done);
                Button cancelbutton=dialog1.findViewById(R.id.cancel);
                donebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(editText.getText().toString().trim().equals("304555"))
                        {
                            ip.setEnabled(true);
                            dialog1.dismiss();
                        }
                    }
                });
                cancelbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ip.setEnabled(false);
                        dialog1.dismiss();
                    }
                });





                dialog1.show();
            }else {
                    Log.e("dd","dd");
                    ip.setEnabled(true);
                    ip.requestFocus();
                }}

        });
        getDataZone();
        if(appSettings.size()!=0) {

            ip.setText(appSettings.get(0).getIP());
            conNO.setText(appSettings.get(0).getCompanyNum());
            COMPANYNO=appSettings.get(0).getCompanyNum();
            years.setText(appSettings.get(0).getYears());
            usernum.setText(appSettings.get(0).getUserNumber());
            try {
                deviceId.setText(appSettings.get(0).getDeviceId());
            }catch ( Exception e){
                Log.e("deviceId",""+e.getMessage());
            }

            if (appSettings.get(0).getUpdateQTY().equals("1"))
                qtyUP.setChecked(true);
        }


        //****************************
        dialog.findViewById(R.id.saveSetting).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                deletesettings();
                final String SET_IP=ip.getText().toString();
                final String SET_conNO=conNO.getText().toString();
                COMPANYNO=conNO.getText().toString();
                final String SET_years=years.getText().toString();
                String device_Id=deviceId.getText().toString().trim();
              //  usernum.setText();

                if(qtyUP.isChecked())
                    SET_qtyup="1";
                else
                    SET_qtyup="0";

                setting = new appSettings();
                setting.setIP(SET_IP);
                setting.setCompanyNum(SET_conNO);
                setting.setUpdateQTY( SET_qtyup);
                setting.setYears(SET_years );
                setting.setUserNumber(usernum.getText().toString().trim());
                setting.setDeviceId(device_Id);
               // Log.e("setting","=="+setting.getDeviceId().toString().trim().length());



                if(setting.getDeviceId().toString().trim().length()!=0)

                {
                    saveData(setting);
                dialog.dismiss();

                }
                else
                {    deviceId.setError(getResources().getString(R.string.reqired_filled));


                }
            }
        });
        dialog.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
    private void getDataZone() {
        appSettings=new ArrayList();
        try {
            appSettings=my_dataBase.settingDao().getallsetting();
        }
        catch (Exception e){}
    }
    private void deletesettings(){
        if(appSettings.size()!=0)
            my_dataBase.settingDao().deleteALL();
    }
    private void saveData(appSettings settings) {
        my_dataBase.settingDao().deleteALL();
        my_dataBase.settingDao().insert(settings);
        my_dataBase.storeDao().deleteall();
        generalMethod.showSweetDialog(this,1,this.getResources().getString(R.string.savedSuccsesfule),"");

    }




}