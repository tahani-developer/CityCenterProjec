package com.example.irbidcitycenter.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.AllItems;
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

import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.irbidcitycenter.Activity.Login.userPermissions;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;
import static com.example.irbidcitycenter.ImportData.AllImportItemlist;
import static com.example.irbidcitycenter.ImportData.WebSerlistAllZone;
import static com.example.irbidcitycenter.ImportData.listAllZone;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final int TAKE_PHOTO = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    final int FROM_STORAGE = 2;
    public  static TextView getzonerespon;
    public  static ArrayList<Integer>Respon_arrayList=new ArrayList<>();
    private DrawerLayout drawerLayout;
    GeneralMethod generalMethod;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    Dialog    authenticationdialog;
    public static String SET_userNO;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Toolbar toolbar;
    LinearLayout uploadimageLin,ReplacmentReverselinear, itemcheckerlinear, zoneLinear, shipmentlinear, replacmentlinear, stocktakelinear, zoneReplacmentlinear;
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
 ImageView imageView;
    private TextView companyNum , username_show,logout2,exist2,uploadimage;
    private UiModeManager uiModeManager;
    public static List<AllItems> AllstocktakeDBlist = new ArrayList<>();
    Resources resources;
    Context context;
    public static final String FILE_NAME = "file_lang"; // preference file name
    public static final String KEY_LANG = "key_lang"; // preference key
    //////
    RadioButton Lang_En, Lang_AR;

    public static TextView RepRevExportsatate, addzoneexportstate;
    public void NightModeON(View view){
        Log.e("NightModeON","NightModeON");
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
    }

    public void NightModeOFF(View view){
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_main);

    //    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        my_dataBase = RoomAllData.getInstanceDataBase(MainActivity.this);

        initial();


        //get Zone
        listAllZone.clear();
        listAllZone=my_dataBase.zonsDataDao().getAll();
        Log.e("listAllZone==",listAllZone.size()+"");
         if( listAllZone.size()==0)
         {

             importData.getAllZones();
             listAllZone=my_dataBase.zonsDataDao().getAll();
             Log.e("listAllZone==",listAllZone.size()+"");
         }
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{ Manifest.permission.READ_CONTACTS },
                    999);
        }




        ImageView imageView1=   findViewById(R.id.nav_header_imageView);
        uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);


        try {
            byte[] imageBytes= my_dataBase.settingDao().getlogo();


            if (imageBytes != null)
            {

                Bitmap bmp= convertByteArrayToBitmap(imageBytes);
                imageView.setImageBitmap(bmp);


            }
        }catch (Exception e){

        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (checkPermission()) {
                        selectImage();
                    } else {
                        requestPermission();
                    }


                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Storage Permission", Toast.LENGTH_SHORT).show();

                }



            }
        });
        logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);

            }
        });
        exist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
uploadimage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (checkPermission()) {
            selectImage();
        } else {
            requestPermission();
        }

    }
});
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.Lang_En:
                if (checked)
            saveLanguage("en");
                    break;
            case R.id.Lang_ER:
                if (checked)
                    saveLanguage("ar");
                    break;
        }
    }
    private Bitmap convertByteArrayToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
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

Log.e("getUsernameAndpass","getUsernameAndpass");
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
        generalMethod=new GeneralMethod(MainActivity.this);
        Lang_En = findViewById(R.id.  Lang_En);
        Lang_AR = findViewById(R.id.Lang_ER);
        if (userPermissions == null) getUsernameAndpass();
        companyNum = findViewById(R.id.companyNum);
        username_show = findViewById(R.id.username_show);
        getzonerespon= findViewById(R.id. getzonerespon);
        logout2= findViewById(R.id.logout2);
        exist2= findViewById(R.id.exits2);
        uploadimage= findViewById(R.id. uploadimage);
        ReplacmentReverselinear = findViewById(R.id.ReplacmentReverselinear);
        ReplacmentReverselinear.setOnClickListener(onClickListener);
        imageView=findViewById(R.id.logo);

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
        uploadimageLin=findViewById(R.id. uploadimageLin);
  if(userPermissions.getMasterUser().equals("1"))
      uploadimageLin.setVisibility(View.VISIBLE);
  else uploadimageLin.setVisibility(View.GONE);
        exportData = new ExportData(MainActivity.this);
        sh_res = findViewById(R.id.shipmentsrespon);

        re_res = findViewById(R.id.replashmentssrespon);
        addzoneexportstate = findViewById(R.id.addzoneexportstate);
        getzonerespon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
if(!s.equals("")){
    my_dataBase.zonsDataDao().deleteAll();
    my_dataBase.zonsDataDao().insertAll(WebSerlistAllZone);

}
            }
        });
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
                    if (editable.toString().equals("Internal Application Error")) {
                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                try {
                                    Respon_arrayList.add(0);
                                    showSweetDialog(MainActivity.this, 0, "Internal Application Error", "");
                                    importData.New_getAllPoDetalis();


                                } catch (WindowManager.BadTokenException e) {
                                    //use a log message
                                }
                            }
                        });

                    }else
                    if (editable.toString().equals("ItemOCode")) {
                        Respon_arrayList.add(1);
                        my_dataBase.itemDao().deleteall();
                        my_dataBase.itemDao().insertAll(AllImportItemlist);
                           Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                try {

                                    importData.New_getAllPoDetalis();

                                } catch (WindowManager.BadTokenException e) {
                                    //use a log message
                                }
                            }
                        });
                    } else if (editable.toString().equals("nodata")) {
                        Respon_arrayList.add(0);
                        importData.New_getAllPoDetalis();

                      /*
                        Handler h = new Handler(Looper.getMainLooper());

                        h.post(new Runnable() {
                            public void run() {
                                showSweetDialog(MainActivity.this, 0, "NetWork Error", "");
                            }
                        });*/

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
                    if(editable.toString().trim().equals("Internal Application Error")){
                        showSweetDialog(MainActivity.this, 0, "Server Error", "");
                    }else
                    if (sh_res.getText().toString().trim().equals("exported")) {
                        Log.e("sh_res", "true");
                        int x = my_dataBase.shipmentDao().updateShipmentPosted();
                        Log.e("vlauOFX", x + "");
                    } else {
                        Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
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

                    if(editable.toString().trim().equals("Internal Application Error")){
                     //   showSweetDialog(MainActivity.this, 0, "Server Error", "");
                    }else
                    if (re_res.getText().toString().trim().equals("exported")) {
                        my_dataBase.replacementDao().updateReplashmentPosted();


                    }
                else    if (re_res.getText().toString().trim().equals("not")) {



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
            String comname=my_dataBase.companyDao().getcomName(comNo);
            if (comNo != null) companyNum.setText(comNo);
            if (comNo != null&&comname != null) companyNum.setText(comNo+" "+Login.CompName);
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

                        if (userPermissions.getMasterUser().equals("1")) {
                            Intent intent = new Intent(MainActivity.this, AddZone.class);
                            startActivity(intent);

                        } else {
                           // showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");

                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.confirm_title))
                                    .setContentText(getResources().getString(R.string.del_per))
                                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            authenticDailog2(5);
                                            sweetAlertDialog.dismiss();
                                        }

                                    })
                                    .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    })
                                    .show();


                        } }
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

                    } else {
                       // showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");

                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(getResources().getString(R.string.confirm_title))
                                .setContentText(getResources().getString(R.string.del_per))
                                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        authenticDailog2(6);
                                        sweetAlertDialog.dismiss();
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
                        } else {
                           // showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");

                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.confirm_title))
                                    .setContentText(getResources().getString(R.string.del_per))
                                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            authenticDailog2(7);
                                            sweetAlertDialog.dismiss();
                                        }

                                    })
                                    .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    })
                                    .show();

                        }  } else {
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
                        } else {
                           // showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");


                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.confirm_title))
                                    .setContentText(getResources().getString(R.string.del_per))
                                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            authenticDailog2(10);
                                            sweetAlertDialog.dismiss();
                                        }

                                    })
                                    .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    })
                                    .show();

                        }  } else {
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
                        } else {
                            //     showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");

                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.confirm_title))
                                    .setContentText(getResources().getString(R.string.del_per))
                                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            authenticDailog2(9);
                                            sweetAlertDialog.dismiss();
                                        }

                                    })
                                    .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    })
                                    .show();

                        }     } else {
                        Intent intent5 = new Intent(MainActivity.this, ZoneReplacment.class);
                        startActivity(intent5);
                    }
                    break;
                case R.id.itemcheckerlinear:
                    animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                    itemcheckerlinear.startAnimation(animation);

                    Intent intent9 = new Intent(MainActivity.this, ItemChecker.class);
                    startActivity(intent9);

                    break;
                case R.id.ReplacmentReverselinear:
                    animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                    ReplacmentReverselinear.startAnimation(animation);

                    if (userPermissions == null) getUsernameAndpass();
                    if (userPermissions.getMasterUser().equals("0")) {
                        if (userPermissions.getRevRep_Open().equals("1")) {

                            Intent intent10 = new Intent(MainActivity.this, ReplenishmentReverse.class);
                            startActivity(intent10);
                        } else {
                            //showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");
                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.confirm_title))
                                    .setContentText(getResources().getString(R.string.del_per))
                                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            authenticDailog2(8);
                                            sweetAlertDialog.dismiss();
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
                    } else {

                        Intent intent10 = new Intent(MainActivity.this, ReplenishmentReverse.class);
                        startActivity(intent10);
                    }
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
                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(getResources().getString(R.string.confirm_title))
                                .setContentText(getResources().getString(R.string.del_per))
                                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        authenticDailog2(2);
                                        sweetAlertDialog.dismiss();
                                    }

                                })
                                .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();
                } else {
                    exportFromMainAct = true;
                    exportAllData();

                }

            }
            ;
            break;
            case R.id.menu_import: {
                if (userPermissions == null) getUsernameAndpass();
                if (userPermissions.getMasterUser().equals("0")) {
                    if (CheckImportPermissitions() == true) {

                        getAllItems();

                    } else {
                        //showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");

                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(getResources().getString(R.string.confirm_title))
                                .setContentText(getResources().getString(R.string.del_per))
                                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        authenticDailog2(1);
                                        sweetAlertDialog.dismiss();
                                    }

                                })
                                .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();

                    } } else {
                    getAllItems();

                }
                break;
            }
            case R.id.exits: {

                  exit();
                   break;
           }
            case R.id.StocktakeReport: {
                if (userPermissions == null) getUsernameAndpass();
                if (userPermissions.getMasterUser().equals("0")) {
                    if (CheckviewStocktakeRep() == true) {
                        Intent intent = new Intent(MainActivity.this, StockTakeReport.class);
                        startActivity(intent);
                    } else {
                        // showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");


                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(getResources().getString(R.string.confirm_title))
                                .setContentText(getResources().getString(R.string.del_per))
                                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        authenticDailog2(3);
                                        sweetAlertDialog.dismiss();
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
                     //   showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");

                    {
                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(getResources().getString(R.string.confirm_title))
                                .setContentText(getResources().getString(R.string.del_per))
                                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        authenticDailog2(4);
                                        sweetAlertDialog.dismiss();
                                    }

                                })
                                .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                }).show();
                    }


                } else {
                    Intent intent = new Intent(MainActivity.this, ShipmentsReport.class);
                    startActivity(intent);

                }
                break;
            }
            case R.id.zoneReport:
                Intent intent = new Intent(MainActivity.this, ZoneReport.class);
                startActivity(intent);
                break;
            case R.id.logout:
                Intent intent1 = new Intent(MainActivity.this, Login.class);
                startActivity(intent1);
                break;


            case R.id.arLang: {
                saveLanguage("ar");
              //  getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                break;
            }
            case R.id.enLang: {
                saveLanguage("en");
           //     getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                break;
            }
            case  R.id.nav_backup_data: {
//            locationPermissionRequest.closeLocation();
                final Dialog dialog1 = new Dialog(MainActivity.this);
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
                        if (editText.getText().toString().trim().equals("2022000")) {
                            try {
                                verifyStoragePermissions(MainActivity.this);
                                copyFile();
                            }
                            catch (Exception e)
                            {verifyStoragePermissions(MainActivity.this);


                                Toast.makeText(MainActivity.this, ""+getResources().getString(R.string.backup_failed), Toast.LENGTH_SHORT).show();
                            }
                            dialog1.dismiss();
                        }else{
                            editText.setError("");
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



          break;  }

        }


        return true;

    }
public void exit(){


    Log.e("heeere", "BACK");

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
                showSweetDialog(MainActivity.this, 0, getString(R.string.noPermToExit), "");

            }

        } else {
            showSweetDialog(MainActivity.this, 0, getString(R.string.noPermToExit), "");

        }

    } else showSweetDialog(MainActivity.this, 0, getString(R.string.noPermToExit), "");



}
    void ExitDailog() {
        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
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
        String langCode = preferences.getString(KEY_LANG, Locale.getDefault().getLanguage());
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

        try {
            Respon_arrayList.clear();
            importData = new ImportData(MainActivity.this);
            importData.getAllItems(0);

        } catch (Exception e){
            Toast.makeText(MainActivity.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();

        }




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
       // LinearLayout userPerLin= dialog.findViewById(R.id.userPerLin);
        //userPerLin.setVisibility(View.GONE);
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
                            }else{
                                editText.setError("");
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
//            if (appSettings.get(0).getUpdateQTY().equals("1"))
//                qtyUP.setChecked(true);

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
    private void authenticDailog2(int enterflage) {


       authenticationdialog = new Dialog(MainActivity.this);
        authenticationdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        authenticationdialog.setCancelable(false);
        authenticationdialog.setContentView(R.layout.authentication);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(authenticationdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        authenticationdialog.getWindow().setAttributes(lp);

        authenticationdialog.show();

        Button button = authenticationdialog.findViewById(R.id.authentic);
        TextView cancelbutton = authenticationdialog.findViewById(R.id.cancel2);
        EditText UsNa = authenticationdialog.findViewById(R.id.username);
        UsNa.requestFocus();

        EditText pass = authenticationdialog.findViewById(R.id.pass);
        pass.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isMaster = "";
                if (!UsNa.getText().toString().trim().equals("") && !pass.getText().toString().trim().equals("")) {
                    isMaster = my_dataBase.userPermissionsDao().getIsMaster(UsNa.getText().toString().trim(), pass.getText().toString().trim());

                    if (isMaster != null && isMaster.equals("1")) {
                        if (enterflage == 1) {
                           getAllItems();

                            authenticationdialog.dismiss();

                        } else if (enterflage == 2) {
                            exportAllData();
                            authenticationdialog.dismiss();

                        } else if (enterflage == 3) {
                            Intent intent = new Intent(MainActivity.this, StockTakeReport.class);
                            startActivity(intent);
                            authenticationdialog.dismiss();
                        }
                        else if (enterflage == 4) {
                            Intent intent = new Intent(MainActivity.this, ShipmentsReport.class);
                            startActivity(intent);
                            authenticationdialog.dismiss();
                        }
                     else   if (enterflage == 5) {
                            Intent intent = new Intent(MainActivity.this, AddZone.class);
                            startActivity(intent);
                            authenticationdialog.dismiss();
                        }    else   if (enterflage == 6) {
                            Intent intent = new Intent(MainActivity.this,NewShipment.class);
                            startActivity(intent);
                            authenticationdialog.dismiss();
                        }
                        else   if (enterflage == 7) {
                            Intent intent = new Intent(MainActivity.this,Replacement.class);
                            startActivity(intent);
                            authenticationdialog.dismiss();
                        } else   if (enterflage == 8) {
                            Intent intent = new Intent(MainActivity.this,ReplenishmentReverse.class);
                            startActivity(intent);
                            authenticationdialog.dismiss();
                        }
                        else   if (enterflage == 9) {
                            Intent intent = new Intent(MainActivity.this,ZoneReplacment.class);
                            startActivity(intent);
                            authenticationdialog.dismiss();
                        }
                        else   if (enterflage == 10) {
                            Intent intent = new Intent(MainActivity.this,Stoketake.class);
                            startActivity(intent);
                            authenticationdialog.dismiss();
                        }

                    } else {
                        generalMethod.showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.Permission), "");

                    }

                } else {
                    UsNa.setError(getResources().getString(R.string.required));
                    pass.setError(getResources().getString(R.string.required));
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

    private void selectImage() {

        final CharSequence[] items = {"Take Picture", "Select from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Add Photo!");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Picture")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    Toast.makeText(getApplicationContext(), "Take Picture", Toast.LENGTH_SHORT).show();
                    try {
                        startActivityForResult(intent, TAKE_PHOTO);
                    }catch (Exception e){
                        Toast.makeText(MainActivity.this, "Storage Permission", Toast.LENGTH_SHORT).show();

                    }


                }

                else if (items[item].equals("Select from Library")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    intent.setType("image/*");

                    Toast.makeText(getApplicationContext(), "Select from Library", Toast.LENGTH_SHORT).show();



                    try {
                        startActivityForResult(Intent.createChooser(intent, "Select File"), FROM_STORAGE);

                    }catch (Exception e){
                        Toast.makeText(MainActivity.this, "Storage Permission", Toast.LENGTH_SHORT).show();

                    }

                }

                else if (items[item].equals("Cancel")) {

                    Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();

                }
            }
        });

        builder.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        File destination = null;

        if (resultCode == RESULT_OK) {

            if (requestCode == TAKE_PHOTO) {

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                destination = new File(Environment.getExternalStorageDirectory(),

                        System.currentTimeMillis() + ".jpg");

                FileOutputStream fo;

                try {

                    destination.createNewFile();

                    fo = new FileOutputStream(destination);

                    fo.write(bytes.toByteArray());

                    fo.close();

                }
                catch (FileNotFoundException e) {

                    e.printStackTrace();

                }
                catch (IOException e) {

                    e.printStackTrace();

                }

                ((ImageView) findViewById(R.id.logo)).setImageBitmap(thumbnail);

            }
            else if (requestCode == FROM_STORAGE) {

                Log.d("FROM_STORAGE ", " FROM_STORAGE");

                Uri selectedImageUri = data.getData();
                Log.e("selectedImageUri",selectedImageUri.getPath());
                try {
                    InputStream iStream =   getContentResolver().openInputStream(selectedImageUri);
                    byte[] inputData = getBytes(iStream);
                  my_dataBase.settingDao().updatimage(inputData);
                    try {
                        byte[] imageBytes= my_dataBase.settingDao().getlogo();


                        if (imageBytes != null)
                        {

                            Bitmap bmp= convertByteArrayToBitmap(imageBytes);
                            imageView.setImageBitmap(bmp);

                        }
                    }catch (Exception e){

                    }

                } catch (Exception e){}
                 String[] projection = {MediaStore.MediaColumns.DATA};

                CursorLoader cursorLoader = new CursorLoader(MainActivity.this, selectedImageUri, projection, null, null, null);

                Cursor cursor = cursorLoader.loadInBackground();

                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);

                String fileNameSegments[] = selectedImagePath.split("/");

                String fileName = fileNameSegments[fileNameSegments.length - 1];

                Bitmap bm;

                BitmapFactory.Options options = new BitmapFactory.Options();

                options.inJustDecodeBounds = true;

                BitmapFactory.decodeFile(selectedImagePath, options);

                final int REQUIRED_SIZE = 100;

                int scale = 1;

                while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale /
                        2 >= REQUIRED_SIZE)

                    scale *= 2;

                options.inSampleSize = scale;

                options.inJustDecodeBounds = false;

                bm = BitmapFactory.decodeFile(selectedImagePath, options);

              //  imagetobyte(selectedImageUri);

/*Log.e("bm ===", bm.toString()+"");
            String s=  BitMapToString(bm);
     my_dataBase.settingDao(). updatimage(s);*/

                ((ImageView) findViewById(R.id.logo)).setImageBitmap(bm);

            }
        }
    }





    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int permission3 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED&& permission3 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE,CAMERA}, Login.PERMISSION_REQUEST_CODE);
        selectImage();
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public void copyFile()
    {
        try
        {
            File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File data = Environment.getDataDirectory();
            boolean isPresent = true;
            if (!sd.canWrite())
            {
                isPresent= sd.mkdir();

            }



            String backupDBPath = "DBRoomIrbidCenter_backup";

            File currentDB= getApplicationContext().getDatabasePath("DBRoomIrbidCenter");
            File backupDB = new File(sd, backupDBPath);

            if (currentDB.exists()&&isPresent) {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(MainActivity.this, "Backup Succesfulley", Toast.LENGTH_SHORT).show();
            }else {

                Toast.makeText(MainActivity.this, "Backup Failed", Toast.LENGTH_SHORT).show();
            }
            isPresent=false;


            Log.e("backupDB.getA", backupDB.getAbsolutePath());
        }
        catch (Exception e) {
            Log.e("Settings Backup", e.getMessage());
        }
    }
}
