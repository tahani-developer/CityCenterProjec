package com.example.irbidcitycenter.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
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
import android.view.inputmethod.EditorInfo;
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


import com.example.irbidcitycenter.Models.LockLayer;
import com.example.irbidcitycenter.Models.ServiceActionsReceiver;
import com.example.irbidcitycenter.Models.UserPermissions;
import com.example.irbidcitycenter.Models.appSettings;

import com.example.irbidcitycenter.Models.customViewGroup;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Filter;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
import static com.example.irbidcitycenter.Activity.MainActivity.FILE_NAME;
import static com.example.irbidcitycenter.Activity.MainActivity.KEY_LANG;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;


public class Login extends AppCompatActivity {
    public static UserPermissions userPermissions;
    EditText username, password;
    LinearLayout yearr, comLin, userrnum;

    boolean activitySwitchFlag = false;
    LinearLayout colorLinear, imgInner, imgOutter;
    FrameLayout mainLinearAnim;
    ImportData importData;
    public RoomAllData my_dataBase;
    public static TextView getListCom, selectedCompany;
    public String selectedCom = "", cono = "", coYear = "";
    ;
    Dialog logindialog;
    GeneralMethod generalMethod;
    TextView settings, show_UN;
    public String COMPANYNO;
    public appSettings setting;
    List<appSettings> appSettings;
    public String SET_qtyup;
    public static TextView UserperRespons;
    public List<UserPermissions> DBUserPermissions = new ArrayList<>();
    private TextView login;
    //

    // To keep track of activity's window focus
    boolean currentFocus;

    // To keep track of activity's foreground/background status
    boolean isPaused;

    Handler collapseNotificationHandler;
    private boolean flage;


    public static String SYSTEM_DIALOG_REASON_KEY = "reason";
    static String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    public static String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    UserPermissions userPermissions3;

    public LinearLayout companyLin;
    private LockLayer lockLayer;
    private View lockView;
    Dialog authenticationdialog;


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        currentFocus = hasFocus;

        if (!hasFocus) {

            // Method that handles loss of window focus
            collapseNow();
        }
    }

    private void loadLanguage() {
        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String langCode = preferences.getString(KEY_LANG, Locale.getDefault().getLanguage() );
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    public void collapseNow() {

        // Initialize 'collapseNotificationHandler'
        if (collapseNotificationHandler == null) {
            collapseNotificationHandler = new Handler();
        }

        // If window focus has been lost && activity is not in a paused state
        // Its a valid check because showing of notification panel
        // steals the focus from current activity's window, but does not
        // 'pause' the activity
        if (!currentFocus && !isPaused) {

            // Post a Runnable with some delay - currently set to 300 ms
            collapseNotificationHandler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    // Use reflection to trigger a method from 'StatusBarManager'

                    Object statusBarService = getSystemService("statusbar");
                    Class<?> statusBarManager = null;

                    try {
                        statusBarManager = Class.forName("android.app.StatusBarManager");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    Method collapseStatusBar = null;

                    try {

                        // Prior to API 17, the method to call is 'collapse()'
                        // API 17 onwards, the method to call is `collapsePanels()`

                        if (Build.VERSION.SDK_INT > 16) {
                            collapseStatusBar = statusBarManager.getMethod("collapsePanels");
                        } else {
                            collapseStatusBar = statusBarManager.getMethod("collapse");
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    collapseStatusBar.setAccessible(true);

                    try {
                        collapseStatusBar.invoke(statusBarService);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    // Check if the window focus has been returned
                    // If it hasn't been returned, post this Runnable again
                    // Currently, the delay is 100 ms. You can change this
                    // value to suit your needs.
                    if (!currentFocus && !isPaused) {
                        collapseNotificationHandler.postDelayed(this, 100L);
                    }

                }
            }, 300L);
        }
    }

    public static void resetPreferredLauncherAndOpenChooser(Context context) {
        Log.e("fffff", "fffff");
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, Login.class);
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        Intent selector = new Intent(Intent.ACTION_MAIN);
        selector.addCategory(Intent.CATEGORY_HOME);
        selector.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(selector);

        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_login);
        init();


        selectedCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showAvilableCompany();
            }
        });
        //   companyLin.setVisibility(View.INVISIBLE);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        /*  lock HOME BUTTON
        lockView = View.inflate(this, R.layout.activity_main, null);

        lockLayer = LockLayer.getInstance(this);
        lockLayer.setLockView(lockView);
        lockLayer.lock();*/
      /*  IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        ServiceActionsReceiver serviceActionsReceiver=new ServiceActionsReceiver();
        registerReceiver(serviceActionsReceiver, filter);*/

        // disablePullNotificationTouch();
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        appSettings = my_dataBase.settingDao().getallsetting();
        DBUserPermissions = my_dataBase.userPermissionsDao().getAll();
        userPermissions = new UserPermissions();


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
                // Create an intent filter to specify the Home category.
                IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
                filter.addCategory(Intent.CATEGORY_HOME);
                filter.addCategory(Intent.CATEGORY_DEFAULT);
                Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                sendBroadcast(closeDialog);
                //  openUthenticationDialog();
              /*  Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.irbidcitycenter");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }*/
                ;


             /*   ActivityManager activityManager = (ActivityManager) getApplicationContext()
                        .getSystemService(Context.ACTIVITY_SERVICE);
                activityManager.moveTaskToFront(getTaskId(), 0);*/


                // moveTaskToBack(false);
                //android.os.Process.killProcess(android.os.Process.myPid());
                // System.exit(0);
                onResume();

            }

            @Override
            public void onHomeLongPressed() {
                Log.e("heeere==", "LONGhome");
            }
        });
        mHomeWatcher.startWatch();

        //END OF 2


        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appSettings = my_dataBase.settingDao().getallsetting();
                if (appSettings.size() == 0) {
                    openSettingDialog();
                    Log.e(" caseA1", " caseA1");

                }
                //// 1. app setting is not empty
                else {

                    //2. user tabel not empty   (ip address correct)

                    if (DBUserPermissions.size() > 0) {
                        Log.e(" caseB1", " caseB1");
                        openloginDailog();


                    } /// end of 2

                    else {
                        Log.e(" caseC1", " caseC1");
                        //3. user tabel empty   ( may be ip address not correct)

                        openSettingDialog();
                        //end of 3


                    }
                }
////////////end of 1
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!username.getText().toString().trim().equals("") &&
                        !password.getText().toString().trim().equals("")) {
                    //   my_dataBase.settingDao().updateusernum(username.getText().toString().trim());
                    appSettings = my_dataBase.settingDao().getallsetting();


                    if (appSettings.size() == 0) {


                        generalMethod.showSweetDialog(Login.this, 3, getResources().getString(R.string.fill_setting), "");
                    } else {

                        userPermissions = my_dataBase.userPermissionsDao().getUserPermissions(username.getText().toString().trim());


                        if (userPermissions == null) {
                            Log.e("case1", "case1");

                            generalMethod.showSweetDialog(Login.this, 3, getResources().getString(R.string.Permission), "");
                        } else {
                            if (password.getText().toString().equals(userPermissions.getUserPassword())) {
                                if (userPermissions.getUserActive().equals("1")) {


                                    MainActivity.SET_userNO = username.getText().toString().trim();
                                    my_dataBase.settingDao().updateusernum(username.getText().toString().trim());
                                    showAvilableCompany(userPermissions);


                                    Log.e("case3", "case3");

                                } else {
                                    Log.e("case5", "case5");

                                    showSweetDialog(Login.this, 3, getResources().getString(R.string.activeuser), "");

                                }
                            } else {
                                password.setError(getResources().getString(R.string.invalid_password));


                                Log.e("case2", "case2");
                                //  my_dataBase.settingDao().updateusernum(username.getText().toString().trim());

                            }

                        }
                    }

                } else {
                    if (username.getText().toString().trim().equals(""))
                        username.setError(getString(R.string.required2));
                    if (password.getText().toString().trim().equals(""))
                        password.setError(getString(R.string.required2));
                }


            }
        });
    }


    private void openloginDailog() {


        logindialog = new Dialog(Login.this);
        logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logindialog.setCancelable(false);
        logindialog.setContentView(R.layout.logindailog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(logindialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        logindialog.getWindow().setAttributes(lp);

        logindialog.show();

        Button button = logindialog.findViewById(R.id.log_authentic);
        TextView cancelbutton = logindialog.findViewById(R.id.log_cancel2);
        EditText UsNa = logindialog.findViewById(R.id.log_usernum);
        UsNa.requestFocus();

        EditText pass = logindialog.findViewById(R.id.log_pass);
        pass.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UserPermissions userPerm = my_dataBase.userPermissionsDao().getUserPermissions(UsNa.getText().toString().trim());
                if (userPerm != null) {
                    if (userPerm.getUserPassword().equals(pass.getText().toString().trim())) {
                        if (userPerm.getUserActive().equals("1")) {

                            if (userPerm.getSetting_Per().equals("0")) {
                                if (userPerm.getMasterUser().equals("0")) {

                                    new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText(getResources().getString(R.string.confirm_title))
                                            .setContentText(getResources().getString(R.string.perMsg))
                                            .setConfirmButton(R.string.yes, new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                                            openauthenticDailog();
                                            sweetAlertDialog.dismiss();
                                        }
                                    }).setCancelButton(R.string.no, new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    }).show();

                                    logindialog.dismiss();

                                } else {
                                    openSettingDialog();
                                    logindialog.dismiss();
                                }

                            } else
                                openSettingDialog();
                            logindialog.dismiss();
                            Log.e("a3", "a3");
                        } else {


                            showSweetDialog(Login.this, 3, getResources().getString(R.string.activeuser), "");

                        }
                    } else pass.setError(getResources().getString(R.string.invalid_password));

                } else {

                    UsNa.setError(getResources().getString(R.string.invalid_username));

                }


            }
        });
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logindialog.dismiss();
            }
        });
        logindialog.show();


    }


    private void openauthenticDailog() {


        authenticationdialog = new Dialog(Login.this);
        authenticationdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        authenticationdialog.setCancelable(false);
        authenticationdialog.setContentView(R.layout.settingauthentic);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(authenticationdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        authenticationdialog.getWindow().setAttributes(lp);

        authenticationdialog.show();

        Button button = authenticationdialog.findViewById(R.id.set_authentic);
        TextView cancelbutton = authenticationdialog.findViewById(R.id.cancel7);


        EditText pass = authenticationdialog.findViewById(R.id.pass1);
        pass.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.getText().toString().trim().equals("123456")) {
                    openSettingDialog();
                    authenticationdialog.dismiss();

                } else {
                    pass.setError(getResources().getString(R.string.invalid_password));

                }
            }
        });
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticationdialog.dismiss();
            }
        });
        authenticationdialog.show();


    }

    private void disablePullNotificationTouch() {
        WindowManager manager = ((WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE));
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        // localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        //  WindowManager.LayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        localLayoutParams.gravity = Gravity.TOP;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                // this is to enable the notification to recieve touch events
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                // Draws over status bar
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = (int) (25 * getResources()
                .getDisplayMetrics().scaledDensity);
        localLayoutParams.format = PixelFormat.RGBX_8888;
        customViewGroup view = new customViewGroup(this);
        manager.addView(view, localLayoutParams);
    }

    private boolean CheckCompanyPer() {
        String comNUm = "";
        String Userno = "";
        if (appSettings.size() != 0) {
            Userno = appSettings.get(0).getUserNumber();
            comNUm = appSettings.get(0).getCompanyNum();

        }
        for (int i = 0; i < DBUserPermissions.size(); i++)
            if (DBUserPermissions.get(i).getUserNO().equals(Userno)) {

                if (DBUserPermissions.get(i).getCONO1().equals(comNUm)) return true;
                else if (DBUserPermissions.get(i).getCONO2().equals(comNUm)) return true;
                else if (DBUserPermissions.get(i).getCONO3().equals(comNUm)) return true;
                else if (DBUserPermissions.get(i).getCONO4().equals(comNUm)) return true;
                else if (DBUserPermissions.get(i).getCONO5().equals(comNUm)) return true;
                else if (DBUserPermissions.get(i).getCONO6().equals(comNUm)) return true;
                else if (DBUserPermissions.get(i).getCONO7().equals(comNUm)) return true;
                else if (DBUserPermissions.get(i).getCONO8().equals(comNUm)) return true;
                else if (DBUserPermissions.get(i).getCONO9().equals(comNUm)) return true;
                else if (DBUserPermissions.get(i).getCONO10().equals(comNUm)) return true;
                else
                    return false;
            }

        return false;
    }

    public void getUsernameAndpass() {

        flage = false;
        String comNUm = "";
        String Userno = "";
        appSettings = my_dataBase.settingDao().getallsetting();
        if (appSettings.size() != 0) {
            Userno = appSettings.get(0).getUserNumber();
            comNUm = appSettings.get(0).getCompanyNum();

        }
        Log.e("here", " " + DBUserPermissions.size());
        userPermissions = my_dataBase.userPermissionsDao().getUserPermissions(Userno);


        //   Toast.makeText(Login.this,"This user is not recognized ",Toast.LENGTH_SHORT).show();


    }

    private void init() {
        companyLin = findViewById(R.id.companyLin);
        show_UN = findViewById(R.id.show_UN);
        login = findViewById(R.id.login);
        UserperRespons = findViewById(R.id.UserperRespons);
        username = findViewById(R.id.username_input);
        password = findViewById(R.id.pass);
        colorLinear = findViewById(R.id.colorLinear);
        mainLinearAnim = findViewById(R.id.mainLinearAnim);
        imgInner = (LinearLayout) findViewById(R.id.colorLinear);
        imgOutter = (LinearLayout) findViewById(R.id.colorLinear2);
        selectedCompany = findViewById(R.id.selectedCompany);
        generalMethod = new GeneralMethod(Login.this);
        my_dataBase = RoomAllData.getInstanceDataBase(Login.this);
        setImageLoop();

        password = findViewById(R.id.pass);
        importData = new ImportData(Login.this);

        username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != KeyEvent.ACTION_UP) {

                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                            || i == EditorInfo.IME_NULL) {
                        String UName = my_dataBase.userPermissionsDao().getUSERnAM(username.getText().toString().trim());
                        if (UName != null) {
                            show_UN.setText(UName);
                            password.requestFocus();
                        } else {
                            //   username.setError("Invalid User Number");
                        }
                    }
                }
                return true;
            }
        });


        settings = findViewById(R.id.setting);

        UserperRespons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {

                    if (editable.toString().trim().equals("USERNO")) {
                        my_dataBase.userPermissionsDao().deleteall();
                        Log.e("UserPermissions", " " + ImportData.UserPermissions.size());
                        my_dataBase.userPermissionsDao().insertAll(ImportData.UserPermissions);
                        //importData.pdUserPer.dismiss();
                        DBUserPermissions.clear();
                        DBUserPermissions = my_dataBase.userPermissionsDao().getAll();
                        Log.e(" DBUserPermissions", " " + DBUserPermissions.size());

                    } else {

                        Toast.makeText(Login.this, getString(R.string.netWorkError), Toast.LENGTH_SHORT).show();
                        importData.pdUserPer.dismiss();

                    }

                }
            }
        });

    }

    private void showAvilableCompany(UserPermissions userPermissions3) {
        ArrayList<String> companyInList = new ArrayList<>();
        companyInList.clear();
        Log.e("ssssss", "sssss");
        final Dialog dialog = new Dialog(Login.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.zone_search);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.show();
        appSettings settings;
        importData = new ImportData(Login.this);
        ArrayList<String> nameOfEngi = new ArrayList<>();
        final ListView listCompany = dialog.findViewById(R.id.listViewEngineering);
        //importData.getCompanyInfo();

        getListCom = dialog.findViewById(R.id.getListCom);


        if (!userPermissions3.getCONO1().equals("")) companyInList.add(userPermissions3.getCONO1());
        if (!userPermissions3.getCONO2().equals("")) companyInList.add(userPermissions3.getCONO2());
        if (!userPermissions3.getCONO3().equals("")) companyInList.add(userPermissions3.getCONO3());
        if (!userPermissions3.getCONO4().equals("")) companyInList.add(userPermissions3.getCONO4());
        if (!userPermissions3.getCONO5().equals("")) companyInList.add(userPermissions3.getCONO5());
        if (!userPermissions3.getCONO6().equals("")) companyInList.add(userPermissions3.getCONO6());
        if (!userPermissions3.getCONO7().equals("")) companyInList.add(userPermissions3.getCONO7());
        if (!userPermissions3.getCONO8().equals("")) companyInList.add(userPermissions3.getCONO8());
        if (!userPermissions3.getCONO9().equals("")) companyInList.add(userPermissions3.getCONO9());
        if (!userPermissions3.getCONO10().equals(""))
            companyInList.add(userPermissions3.getCONO10());

       /* if( companyInList.size()!=0) {
            for (int i = 0; i < companyInList.size(); i++) {
                nameOfEngi.add(companyInList.get(i).getCoNo() + "\t\t" + importData.companyInList.get(i).getCoYear() + "\t\t" + importData.companyInList.get(i).getCoNameA());
            }*/

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (Login.this, android.R.layout.simple_list_item_1, companyInList) {
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

        selectedCom = companyInList.get(0);

        // DataBind ListView with items from ArrayAdapter
        listCompany.setAdapter(arrayAdapter);

        listCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                listCompany.requestFocusFromTouch();

                listCompany.setSelection(position);

                selectedCom = companyInList.get(position);

                try {
                    for (int ctr = 0; ctr <= companyInList.size(); ctr++) {
                        if (position == ctr) {
                            listCompany.getChildAt(ctr).setBackgroundColor(Color.YELLOW);
                        } else {
                            listCompany.getChildAt(ctr).setBackgroundColor(Color.WHITE);
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        Button btn_send = dialog.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                my_dataBase.settingDao().updateCompanyInfo(selectedCom);

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                dialog.dismiss();


            }
        });
    }


    //
    @Override
    protected void onResume() {
        super.onResume();

        // Activity's been resumed
        isPaused = false;
    }

    @Override
    protected void onStop() {

        super.onStop();

        Log.d("tag==", "MYonStop is called");
        // insert here your instructions
    }

  /*  @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.e("onKeyUp==",keyCode+"");
        return super.onKeyUp(keyCode, event);
    }*/

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        Log.e("onKeyDown==", keyCode + "");
        return super.onKeyLongPress(keyCode, event);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e("heeere", "BACK");
            activitySwitchFlag = true;
            String Userno1;
            {
                appSettings = my_dataBase.settingDao().getallsetting();
                Log.e("appSettings.size===", appSettings.size() + "");
                if (appSettings.size() != 0) {
                    Log.e("usernum===", appSettings.get(0).getUserNumber());
                    userPermissions = my_dataBase.userPermissionsDao().getUserPermissions(appSettings.get(0).getUserNumber());

                }


            }
            //Log.e("userPermissions",userPermissions.getAddZone_Open());

            if (userPermissions != null) {
                Log.e("heeere8", "BACK");
                if (userPermissions.getMasterUser() != null) {
                    Log.e("heeere7", "BACK");
                    if (
                            userPermissions.getMasterUser().equals("1")) {
                        Log.e("heeere22", "BACK");

                        ExitDailog();

                    } else {
                        showSweetDialog(Login.this, 0, getString(R.string.noPermToExit), "");

                    }

                } else {
                    showSweetDialog(Login.this, 0, getString(R.string.noPermToExit), "");

                }

            } else showSweetDialog(Login.this, 0, getString(R.string.noPermToExit), "");

        }
        return true;

    }

    void ExitDailog() {
        new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.confirm_title))
                .setContentText(getResources().getString(R.string.messageExit3))
                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();

                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);


                    }
                })
                .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onPause() {
        Log.e("onPause", "onPause");

        isPaused = true;
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
        super.onPause();

    /*  lockLayer.unlock();
      finish();*/


        //openUthenticationDialog();
    }

   /*     Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);*/


    public void aaa(Activity activity) {
        ActivityManager activityManager = (ActivityManager) activity
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
    }

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
        if (view.getId() == R.id.companyName) {
            if (existCoNo(2))
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
        importData = new ImportData(Login.this);
        ArrayList<String> nameOfEngi = new ArrayList<>();
        final ListView listCompany = dialog.findViewById(R.id.listViewEngineering);
        //importData.getCompanyInfo();

        getListCom = dialog.findViewById(R.id.getListCom);

//        getListCom.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(editable.toString().length()!=0)
//                {
//                    if(editable.toString().equals("fill"))
//                    {
//                        if( importData.companyInList.size()!=0) {
//                            for (int i = 0; i < importData.companyInList.size(); i++) {
//                                nameOfEngi.add(importData.companyInList.get(i).getCoNo() + "\t\t" + importData.companyInList.get(i).getCoYear() + "\t\t" + importData.companyInList.get(i).getCoNameA());
//                            }
//
//                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
//                                    (Login.this, android.R.layout.simple_list_item_1, nameOfEngi) {
//                                @Override
//                                public View getView(int position, View convertView, ViewGroup parent) {
//                                    /// Get the Item from ListView
//                                    View view = super.getView(position, convertView, parent);
//
//                                    TextView tv = (TextView) view.findViewById(android.R.id.text1);
//
//                                    // Set the text size 25 dip for ListView each item
//                                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
//                                    tv.setTextColor(getResources().getColor(R.color.text_color));
//                                    // Return the view
//                                    return view;
//                                }
//                            };
//
//                            // DataBind ListView with items from ArrayAdapter
//                            listCompany.setAdapter(arrayAdapter);
//
//
//                        }
//                    }
//
//                }
//
//            }
//        });


        final int[] rowZone = new int[1];
        selectedCom = "";

       /* listCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cono="";coYear="";
                rowZone[0] =position;
                listCompany.requestFocusFromTouch();

                //listCompany.setSelection(position);
              selectedCom =importData.companyInList.get(position).getCoNameA();
                cono=importData.companyInList.get(position).getCoNo();
                coYear=importData.companyInList.get(position).getCoYear();

            }
        });*/


        Button btn_send = dialog.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    selectedCompany.setText(selectedCom);

                if (listCompany.getSelectedItem().toString() != null) {
                    updateCono(listCompany.getSelectedItem().toString(), coYear);
                    dialog.dismiss();
                } else {
                    showSweetDialog(Login.this, 0, "", getString(R.string.selectCompany));
                }


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


    private boolean existCoNo(int flag) {
        // ipAddress=my_dataBase.settingDao().getIpAddress().trim();
        String CONO = "";
        try {
            if (flag == 1) {
                CONO = my_dataBase.settingDao().getCono();
            } else {
                if (flag == 2)
                    CONO = my_dataBase.settingDao().getIpAddress();
            }

            Log.e("CONO", "" + CONO);
            if (CONO != null) {
                if (CONO.length() != 0) {
                    return true;
                }

            }
        } catch (Exception e) {
            return false;
        }


        return false;


    }

    private void openSettingDialog() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        final Dialog dialog = new Dialog(Login.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ip_setting_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = height / 2;
        lp.gravity = Gravity.TOP;
        dialog.getWindow().setAttributes(lp);
        dialog.show();


        final EditText ip = dialog.findViewById(R.id.ipEditText);
        final TextView editip = dialog.findViewById(R.id.editip);
        // ip.setEnabled(false);
        final EditText conNO = dialog.findViewById(R.id.cono);
        final EditText years = dialog.findViewById(R.id.storeNo_edit);
        final CheckBox qtyUP = (CheckBox) dialog.findViewById(R.id.qtycheck);
        final EditText usernum = dialog.findViewById(R.id.usernumber);
        final EditText deviceId = dialog.findViewById(R.id.deviceId);
        comLin = dialog.findViewById(R.id.comLin);
        yearr = dialog.findViewById(R.id.yearrLin);
        userrnum = dialog.findViewById(R.id.userrnumLin);
        comLin.setVisibility(View.GONE);
        yearr.setVisibility(View.GONE);
        userrnum.setVisibility(View.GONE);
        years.setVisibility(View.GONE);
        usernum.setVisibility(View.GONE);

        ip.setEnabled(true);
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


                    EditText editText = dialog1.findViewById(R.id.passwordd);
                    Button donebutton = dialog1.findViewById(R.id.done);
                    Button cancelbutton = dialog1.findViewById(R.id.cancel);
                    donebutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (editText.getText().toString().trim().equals("304555")) {
                                ip.setEnabled(true);
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
                } else {
                    Log.e("dd", "dd");
                    ip.setEnabled(true);
                    ip.requestFocus();
                }
            }

        });
        getDataZone();
        if (appSettings.size() != 0) {

            ip.setText(appSettings.get(0).getIP());
            conNO.setText(appSettings.get(0).getCompanyNum());
            COMPANYNO = appSettings.get(0).getCompanyNum();


            try {
                deviceId.setText(appSettings.get(0).getDeviceId());
            } catch (Exception e) {
                Log.e("deviceId", "" + e.getMessage());
            }

            if (appSettings.get(0).getUpdateQTY().equals("1"))
                qtyUP.setChecked(true);
        }


        //****************************
        dialog.findViewById(R.id.saveSetting).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                deletesettings();
                final String SET_IP = ip.getText().toString();
                final String SET_conNO = conNO.getText().toString();
                COMPANYNO = conNO.getText().toString();

                String device_Id = deviceId.getText().toString().trim();
                //  usernum.setText();

                if (qtyUP.isChecked())
                    SET_qtyup = "1";
                else
                    SET_qtyup = "0";

                setting = new appSettings();
                setting.setIP(SET_IP);
                setting.setCompanyNum(SET_conNO);
                setting.setUpdateQTY(SET_qtyup);
                setting.setUserNumber("");
                setting.setYears("");

                setting.setDeviceId(device_Id);
                // Log.e("setting","=="+setting.getDeviceId().toString().trim().length());

                if (ip.getText().toString().trim().length() != 0)


                    if (setting.getDeviceId().toString().trim().length() != 0) {


                        saveData(setting);
                        dialog.dismiss();
                        importData = new ImportData(Login.this);
                        importData.getUserPermissions();


                    } else {
                        deviceId.setError(getResources().getString(R.string.reqired_filled));


                    }

                else {
                    usernum.setError(getResources().getString(R.string.reqired_filled));


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
        appSettings = new ArrayList();
        try {
            appSettings = my_dataBase.settingDao().getallsetting();
        } catch (Exception e) {
        }
    }

    private void deletesettings() {
        if (appSettings.size() != 0)
            my_dataBase.settingDao().deleteALL();
    }

    private void saveData(appSettings settings) {
        my_dataBase.settingDao().deleteALL();
        my_dataBase.settingDao().insert(settings);
        my_dataBase.storeDao().deleteall();
        my_dataBase.userPermissionsDao().deleteall();
        DBUserPermissions.clear();
        show_UN.setText("");
        generalMethod.showSweetDialog(this, 1, this.getResources().getString(R.string.savedSuccsesfule2), "");

    }


//        for(int i=0;i<DBUserPermissions.size();i++) {
//            if (DBUserPermissions.get(i).getUserNO().equals(Userno)) {
//                 flage=true;
//                userPermissions.setUserName(DBUserPermissions.get(i).getUserName());
//                userPermissions.setUserNO(DBUserPermissions.get(i).getUserNO());
//                userPermissions.setUserPassword(DBUserPermissions.get(i).getUserPassword());
//                userPermissions.setUserActive(DBUserPermissions.get(i).getUserActive());
//                userPermissions.setMasterUser(DBUserPermissions.get(i).getMasterUser());
//                userPermissions.setSetting_Per(DBUserPermissions.get(i).getSetting_Per());
//                userPermissions.setSH_RepOpen(DBUserPermissions.get(i).getSH_RepOpen());
//                userPermissions.setST_RepOpen(DBUserPermissions.get(i).getST_RepOpen());
//                userPermissions.setRep_Open(DBUserPermissions.get(i).getRep_Open());
//                userPermissions.setSHIP_Open(DBUserPermissions.get(i).getSHIP_Open());
//                userPermissions.setAddZone_Open(DBUserPermissions.get(i).getAddZone_Open());
//                userPermissions.setStockTake_Open(DBUserPermissions.get(i).getStockTake_Open());
//                userPermissions.setZoneRep_Open(DBUserPermissions.get(i).getZoneRep_Open());
//                userPermissions.setImport_Per(DBUserPermissions.get(i).getImport_Per());
//                userPermissions.setExport_Per(DBUserPermissions.get(i).getExport_Per());
//                userPermissions.setSHIP_Save(DBUserPermissions.get(i).getSHIP_Save());
//                userPermissions.setSHIP_LocalDelete(DBUserPermissions.get(i).getSHIP_LocalDelete());
//
//                userPermissions.setAddZone_Save(DBUserPermissions.get(i).getAddZone_Save());
//                userPermissions.setAddZone_LocalDelete(DBUserPermissions.get(i).getAddZone_LocalDelete());
//
//                userPermissions.setStockTake_Save(DBUserPermissions.get(i).getStockTake_Save());
//                userPermissions.setStockTake_LocalDelete(DBUserPermissions.get(i).getStockTake_LocalDelete());
//
//                userPermissions.setZoneRep_Save(DBUserPermissions.get(i).getZoneRep_Save());
//                userPermissions.setZoneRep_LocalDelete(DBUserPermissions.get(i).getZoneRep_LocalDelete());
//                userPermissions.setRep_Save(DBUserPermissions.get(i).getRep_Save());
//                userPermissions.setRep_LocalDelete(DBUserPermissions.get(i).getRep_LocalDelete());
//
//                userPermissions.setStockTake_UpdateQty(DBUserPermissions.get(i).getStockTake_UpdateQty());
//                userPermissions.setZoneRep_UpdateQty(DBUserPermissions.get(i).getZoneRep_UpdateQty());
//
//            }
//        }

}