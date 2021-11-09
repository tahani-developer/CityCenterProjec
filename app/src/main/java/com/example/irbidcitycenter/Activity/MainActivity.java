package com.example.irbidcitycenter.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.ReplenishmentReverseModel;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.Models.StocktakeModel;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.Models.ZoneReplashmentModel;
import com.example.irbidcitycenter.Models.appSettings;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.irbidcitycenter.Activity.Login.userPermissions;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;
import static com.example.irbidcitycenter.ImportData.AllImportItemlist;
import static com.example.irbidcitycenter.ImportData.hideProgressDialogWithTitle;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    GeneralMethod generalMethod;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    public static String SET_userNO;
    private Toolbar toolbar;
    LinearLayout ReplacmentReverselinear, itemcheckerlinear, zoneLinear, shipmentlinear, replacmentlinear, stocktakelinear, zoneReplacmentlinear;
    public String SET_qtyup;
    public appSettings settings;
    ExportData exportData;
    public static boolean exportFromMainAct = false;
    public static boolean exportFromMainAct2 = false;
    public static int setflage = -1;
    public static String COMPANYNO;
    private Animation animation;
    public List<appSettings> settingslist = new ArrayList<>();
    ImportData importData;
    public RoomAllData my_dataBase;
    List<appSettings> appSettings;
    public static TextView sh_res, zo_res, re_res;
    public static TextView itemrespons, exportrespon, exportZonReprespon;
    public static int activityflage = 1;

    public static int Items_activityflage = 1;
    private TextView companyNum, username_show;

    Resources resources;
    Context context;
    public static final String FILE_NAME = "file_lang"; // preference file name
    public static final String KEY_LANG = "key_lang"; // preference key
    //////

    public static TextView RepRevExportsatate, addzoneexportstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_main);


        my_dataBase = RoomAllData.getInstanceDataBase(MainActivity.this);

        initial();


    }


    private boolean CheckViewSetting_Permissitions() {


        if (userPermissions != null) {


            if (userPermissions.getSetting_Per().equals("1")) {
                return true;
            } else
                return false;
        } else
            return false;

    }

    private boolean CheckImportPermissitions() {

        if (userPermissions != null) {


            if (userPermissions.getImport_Per().equals("1")) {
                return true;
            }
        }
        return false;

    }

    private boolean CheckExportPermissitions() {

        if (userPermissions != null) {
            if (userPermissions.getExport_Per().equals("1"))

                return true;
            else
                return false;
        } else return false;


    }

    private boolean CheckviewShipmentRep() {

        if (userPermissions != null) {
            if (userPermissions.getSH_RepOpen().equals("1"))

                return true;
            else
                return false;
        } else return false;


    }

    private boolean CheckviewStocktakeRep() {

        if (userPermissions != null) {
            if (userPermissions.getST_RepOpen().equals("1"))

                return true;
            else
                return false;
        } else return false;


    }

    public void getUsernameAndpass() {


        String comNUm = "";
        String Userno = "";
        appSettings = my_dataBase.settingDao().getallsetting();
        if (appSettings.size() != 0) {
            Userno = appSettings.get(0).getUserNumber();
            comNUm = appSettings.get(0).getCompanyNum();

        }

        userPermissions = my_dataBase.userPermissionsDao().getUserPermissions(Userno);


        //   Toast.makeText(Login.this,"This user is not recognized ",Toast.LENGTH_SHORT).show();


    }

    private void initial() {
        if (userPermissions == null) getUsernameAndpass();
        companyNum = findViewById(R.id.companyNum);
        username_show = findViewById(R.id.username_show);


        ReplacmentReverselinear = findViewById(R.id.ReplacmentReverselinear);
        ReplacmentReverselinear.setOnClickListener(onClickListener);


        itemcheckerlinear = findViewById(R.id.itemcheckerlinear);
        exportZonReprespon = findViewById(R.id.exportZonReprespon);
        exportrespon = findViewById(R.id.stocksrespon);
        itemrespons = findViewById(R.id.ST_itemrespons);
        importData = new ImportData(MainActivity.this);
        drawerLayout = findViewById(R.id.main_drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        stocktakelinear = findViewById(R.id.Stocktakelinear);
        zoneReplacmentlinear = findViewById(R.id.zoneReplacmentlinear);
        setupDrawerContent(navigationView);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        zoneLinear = findViewById(R.id.zoneLinear);
        zoneLinear.setOnClickListener(onClickListener);
        shipmentlinear = findViewById(R.id.hipmentlinear);
        shipmentlinear.setOnClickListener(onClickListener);
        itemcheckerlinear.setOnClickListener(onClickListener);
        stocktakelinear.setOnClickListener(onClickListener);
        zoneReplacmentlinear.setOnClickListener(onClickListener);
        replacmentlinear = findViewById(R.id.Replacmentlinear);
        replacmentlinear.setOnClickListener(onClickListener);
        exportData = new ExportData(MainActivity.this);
        sh_res = findViewById(R.id.shipmentsrespon);

        re_res = findViewById(R.id.replashmentssrespon);
        addzoneexportstate = findViewById(R.id.addzoneexportstate);

        exportZonReprespon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {

                    if (editable.toString().trim().equals("exported")) {
                        my_dataBase.zoneReplashmentDao().setposted();


                    } else {


                    }

                }
            }
        });
        exportrespon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {

                    if (editable.toString().trim().equals("exported")) {
                        my_dataBase.stocktakeDao().setposted("1");
                        // showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                        // stocktakelist.clear();
                        //stocktakeAdapter.notifyDataSetChanged();


                    } else {
                        // savedata("0");

                    }
                }
            }
        });
        itemrespons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {

                    if (editable.toString().equals("ItemOCode")) {
                        Log.e("herea", "aaaaa");
                        my_dataBase.itemDao().deleteall();
                        my_dataBase.itemDao().insertAll(AllImportItemlist);
                        hideProgressDialogWithTitle();
                        //   Toast.makeText(MainActivity.this,"gat all data",Toast.LENGTH_SHORT).show();
                        showSweetDialog(MainActivity.this, 1, getString(R.string.dataStored), "");

                    } else if (editable.toString().equals("nodata")) {
                        hideProgressDialogWithTitle();
                        Handler h = new Handler(Looper.getMainLooper());

                        h.post(new Runnable() {
                            public void run() {
                                showSweetDialog(MainActivity.this, 0, getString(R.string.netWorkError), "");
                            }
                        });

                    }


                }
            }
        });
        sh_res.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {
                    Log.e("sh_res", sh_res.getText().toString());
                    if (sh_res.getText().toString().trim().equals("exported")) {
                        Log.e("sh_res", "true");
                        int x = my_dataBase.shipmentDao().updateShipmentPosted();
                        Log.e("vlauOFX", x + "");
                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                        Log.e("sh_res", "false");
                    }
                }
            }
        });

        re_res.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {
                    if (re_res.getText().toString().trim().equals("exported")) {
                        my_dataBase.replacementDao().updateReplashmentPosted();

                    }
                }
            }
        });
        RepRevExportsatate = findViewById(R.id.RepRevExportsatate);
        RepRevExportsatate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {
                    if (editable.toString().trim().equals("exported")) {
                        my_dataBase.repReversDao().updateReplashmentPosted();
                        showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                    }
                }
            }
        });

        try {
            String comNo = my_dataBase.settingDao().getCono().trim();
            if (comNo != null) companyNum.setText(comNo);

            String UName = my_dataBase.userPermissionsDao().getUSERnAM(my_dataBase.settingDao().getUserNo());
            if (UName != null) username_show.setText(UName);
        } catch (Exception e) {

        }

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.zoneLinear:
                    animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                    zoneLinear.startAnimation(animation);
                    if (userPermissions == null) getUsernameAndpass();
                    if (userPermissions.getAddZone_Open().equals("1")) {


                        Intent intent = new Intent(MainActivity.this, AddZone.class);
                        startActivity(intent);


                    } else {
                        if (userPermissions == null) getUsernameAndpass();
                        if (userPermissions.getMasterUser().equals("1")) {
                            Intent intent = new Intent(MainActivity.this, AddZone.class);
                            startActivity(intent);

                        } else
                            showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");
                    }
                    break;
                case R.id.hipmentlinear:
                    if (userPermissions == null) getUsernameAndpass();
                    animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                    shipmentlinear.startAnimation(animation);

                    if (userPermissions.getSHIP_Open().equals("1")) {
                        Intent intent2 = new Intent(MainActivity.this, NewShipment.class);
                        startActivity(intent2);
                    } else if (userPermissions.getMasterUser().equals("1")) {
                        Intent intent = new Intent(MainActivity.this, NewShipment.class);
                        startActivity(intent);

                    } else
                        showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");

                    break;
                case R.id.Replacmentlinear:
                    if (userPermissions == null) getUsernameAndpass();
                    animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                    replacmentlinear.startAnimation(animation);

                    if (userPermissions.getMasterUser().equals("0")) {
                        Log.e("(userPerm", userPermissions.getRep_Open());
                        if (userPermissions.getRep_Open().equals("1")) {

                            Intent intent3 = new Intent(MainActivity.this, Replacement.class);
                            startActivity(intent3);
                        } else
                            showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");
                    } else {
                        Intent intent3 = new Intent(MainActivity.this, Replacement.class);
                        startActivity(intent3);
                    }
                    break;
                case R.id.Stocktakelinear:
                    if (userPermissions == null) getUsernameAndpass();
                    animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                    stocktakelinear.startAnimation(animation);

                    if (userPermissions.getMasterUser().equals("0")) {
                        if (userPermissions.getStockTake_Open().equals("1")) {
                            Intent intent4 = new Intent(MainActivity.this, Stoketake.class);
                            startActivity(intent4);
                        } else
                            showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");
                    } else {
                        Intent intent4 = new Intent(MainActivity.this, Stoketake.class);
                        startActivity(intent4);
                    }
                    break;
                case R.id.zoneReplacmentlinear:
                    if (userPermissions == null) getUsernameAndpass();
                    animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                    zoneReplacmentlinear.startAnimation(animation);

                    if (userPermissions.getMasterUser().equals("0")) {
                        if (userPermissions.getZoneRep_Open().equals("1")) {
                            Intent intent5 = new Intent(MainActivity.this, ZoneReplacment.class);
                            startActivity(intent5);
                        } else
                            showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");
                    } else {
                        Intent intent5 = new Intent(MainActivity.this, ZoneReplacment.class);
                        startActivity(intent5);
                    }
                    break;
                case R.id.itemcheckerlinear:

                    Intent intent9 = new Intent(MainActivity.this, ItemChecker.class);
                    startActivity(intent9);

                    break;
                case R.id.ReplacmentReverselinear:
                    animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                    ReplacmentReverselinear.startAnimation(animation);
                    Intent intent10 = new Intent(MainActivity.this, ReplenishmentReverse.class);
                    startActivity(intent10);
            }
        }
    };

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            Log.e("iditem", "onOptionsItemSelected " + item.getItemId());
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // if(keyCode==KeyEvent.KEYCODE_BACK || keyCode==KeyEvent.KEYCODE_HOME||keyCode==KeyEvent.KEYCODE_SWITCH_CHARSET){
        Log.e("onKeyDown==", keyCode + "");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.e("id", "onNavigationItemSelected " + id);
        switch (id) {
            case R.id.menu_setting: {
                if (userPermissions == null) getUsernameAndpass();
                if (userPermissions.getMasterUser().equals("0")) {
                    if (CheckViewSetting_Permissitions()) {
                        Log.e("id", "menu_setting " + id);
                        drawerLayout.closeDrawer(navigationView);
                        openSettingDialog();
                    } else
                        showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");


                } else {
                    Log.e("id", "menu_setting " + id);
                    drawerLayout.closeDrawer(navigationView);
                    openSettingDialog();
                }

            }
            break;
            case R.id.menu_export: {
                if (userPermissions == null) getUsernameAndpass();
                if (userPermissions.getMasterUser().equals("0")) {
                    if (CheckExportPermissitions() == true) {
                        exportFromMainAct = true;
                        exportAllData();
                    } else
                        showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");

                } else {
                    exportFromMainAct = true;
                    exportAllData();
                }

            }

            break;
            case R.id.menu_import: {
                if (userPermissions == null) getUsernameAndpass();
                if (userPermissions.getMasterUser().equals("0")) {
                    if (CheckImportPermissitions() == true)
                        getAllItems();
                    else
                        showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");
                } else {
                    getAllItems();
                }
                break;
            }

            case R.id.StocktakeReport: {
                if (userPermissions == null) getUsernameAndpass();
                if (userPermissions.getMasterUser().equals("0")) {
                    if (CheckviewStocktakeRep()) {
                        Intent intent = new Intent(MainActivity.this, StockTakeReport.class);
                        startActivity(intent);
                    } else
                        showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");
                } else {
                    Intent intent = new Intent(MainActivity.this, StockTakeReport.class);
                    startActivity(intent);
                }
                break;

            }
            case R.id.shipmentReports: {
                if (userPermissions == null) getUsernameAndpass();
                if (userPermissions.getMasterUser().equals("0")) {
                    if (CheckviewShipmentRep() == true) {
                        Intent intent = new Intent(MainActivity.this, ShipmentsReport.class);
                        startActivity(intent);
                    } else
                        showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");


                    break;

                } else {
                    Intent intent = new Intent(MainActivity.this, ShipmentsReport.class);
                    startActivity(intent);

                }
                break;
            }
            case R.id.logout: {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;
            }
            case R.id.enLang: {
                saveLanguage("en");
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                break;
            }
            case R.id.arLang: {
                saveLanguage("ar");
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                break;
            }
        }


        return true;

    }

    public void saveLanguage(String lang) {

// we can use this method to save language
        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_LANG, lang);
        editor.apply();
// we have saved
// recreate activity after saving to load the new language, this is the same
// as refreshing activity to load new language

        recreate();

    }

    public void loadLanguage() {
// we can use this method to load language,
// this method should be called before setContentView() method of the onCreate method

        Locale locale = new Locale(getLangCode());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    public String getLangCode() {
        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String langCode = preferences.getString(KEY_LANG, Locale.getDefault().getLanguage() );
// save english ‘en’ as the default language
        return langCode;
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        conf.setLayoutDirection(myLocale);
        res.updateConfiguration(conf, dm);
        conf.setLayoutDirection(myLocale);

        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);


    }

    private void getAllItems() {
        importData = new ImportData(MainActivity.this);
        importData.getAllItems();
    }

    private void exportAllData() {
        activityflage = 1;
        List<ZoneModel> listZon = new ArrayList();
        ArrayList<Shipment> newShipmentList = new ArrayList();
        ArrayList<ReplacementModel> replacementList = new ArrayList();
        List<StocktakeModel> StoktakeList = new ArrayList();
        List<ZoneReplashmentModel> zoneReplacmentList = new ArrayList<>();
        List<ReplenishmentReverseModel> ReplacmentReversList = new ArrayList<>();
        List<Shipment> listShipment;
        List<ReplacementModel> listReplasment;
        activityflage = 1;
        exportFromMainAct2 = true;
        listZon = my_dataBase.zoneDao().getUnpostedZone("0");
        listShipment = my_dataBase.shipmentDao().getUnpostedShipment("0");
        listReplasment = my_dataBase.replacementDao().getUnpostedReplacement("0");
        StoktakeList = my_dataBase.stocktakeDao().getall();
        ReplacmentReversList = my_dataBase.repReversDao().getallReplacement();
        zoneReplacmentList = my_dataBase.zoneReplashmentDao().getAllZonesUnposted();
        newShipmentList = (ArrayList<Shipment>) listShipment;

        replacementList = (ArrayList<ReplacementModel>) listReplasment;
        exportData.exportAllUnposted(listZon, newShipmentList, replacementList, StoktakeList, zoneReplacmentList, ReplacmentReversList);

    }

    private void openSettingDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ip_setting_dialog);
        dialog.show();


        final EditText ip = dialog.findViewById(R.id.ipEditText);
        final TextView editip = dialog.findViewById(R.id.editip);
        final EditText conNO = dialog.findViewById(R.id.cono);
        final EditText years = dialog.findViewById(R.id.storeNo_edit);
        final CheckBox qtyUP = (CheckBox) dialog.findViewById(R.id.qtycheck);
        final EditText usernum = dialog.findViewById(R.id.usernumber);
        final EditText deviceId = dialog.findViewById(R.id.deviceId);
        //  usernum.setText(SET_userNO);

        conNO.setEnabled(false);
        years.setEnabled(false);
        usernum.setEnabled(false);

        editip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ipgetText", ip.getText().toString());
                if (!ip.getText().toString().equals("")) {
                    final Dialog dialog1 = new Dialog(MainActivity.this);
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
            years.setText(appSettings.get(0).getYears());
            usernum.setText(appSettings.get(0).getUserNumber());
            if (appSettings.get(0).getUpdateQTY().equals("1"))
                qtyUP.setChecked(true);

            try {
                deviceId.setText(appSettings.get(0).getDeviceId());
            } catch (Exception e) {
                Log.e("deviceId", "" + e.getMessage());
            }
        }
        //****************************


        Button save = dialog.findViewById(R.id.saveSetting);
        //  if(CheckSaveSetting_Permissitions()==true)
        save.setEnabled(true);
        //  else save.setEnabled(false);


        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                deletesettings();
                final String SET_IP = ip.getText().toString();
                final String SET_conNO = conNO.getText().toString();
                COMPANYNO = conNO.getText().toString();
                String device_Id = deviceId.getText().toString().trim();
                final String SET_years = years.getText().toString();
                usernum.setText(SET_userNO);

                if (qtyUP.isChecked())
                    SET_qtyup = "1";
                else
                    SET_qtyup = "0";

                settings = new appSettings();
                settings.setIP(SET_IP);
                settings.setCompanyNum(SET_conNO);
                settings.setUpdateQTY(SET_qtyup);
                settings.setYears(SET_years);
                settings.setUserNumber(usernum.getText().toString());
                settings.setDeviceId(device_Id);

                if (settings.getDeviceId().toString().trim().length() == 0) {
                    deviceId.setError(getResources().getString(R.string.reqired_filled));

                } else {
                    saveData(settings);
                    dialog.dismiss();
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

    private void saveData(appSettings settings) {

        my_dataBase.settingDao().insert(settings);

        generalMethod.showSweetDialog(this, 1, this.getResources().getString(R.string.savedSuccsesfule2), "");

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        Log.e("onPause", "onPause");
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
        //openUthenticationDialog();

    }

}
