package com.example.irbidcitycenter.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
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
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.Adapters.ZoneAdapter;
import com.example.irbidcitycenter.Adapters.ZoneSearchDBAdapter;
import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.NewZonsData;
import com.example.irbidcitycenter.Models.ZoneLogs;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.Models.ZonsData;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;
import com.example.irbidcitycenter.ScanActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.irbidcitycenter.Activity.Login.userPermissions;
import static com.example.irbidcitycenter.Activity.MainActivity.FILE_NAME;
import static com.example.irbidcitycenter.Activity.MainActivity.KEY_LANG;
import static com.example.irbidcitycenter.GeneralMethod.checkIfUserWhoLoginIsMaster;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;
import static com.example.irbidcitycenter.ImportData.Storelist;
import static com.example.irbidcitycenter.ImportData.listAllZone;
import static com.example.irbidcitycenter.ImportData.zonetype;
import static com.example.irbidcitycenter.ImportData.zoneinfo;
public class AddZone extends AppCompatActivity {
    GeneralMethod generalMethod;
    public static EditText editZoneCode, editItemCode, editQty, itemKintText, exportStateText;
    public static final int REQUEST_Camera_Barcode = 1;
    public static ArrayList<ZoneModel> listZone=new ArrayList<>();
    public static List<ZoneModel> DBlistZoneS;
    public static List<ZoneModel> listofZonesUnPosted;
    public static List<ZoneModel> listtest = new ArrayList<>();
    public static  TextView storrespon;
    public List<ZoneModel> deletedZonsList = new ArrayList<>();
    ;
    public List<ZoneModel> zonescopylist = new ArrayList<>();
    public List<ZoneModel> ReducedItemlist = new ArrayList<>();
    RecyclerView recycleZone;
    List<String> zones;
    List<String> zonelist = new ArrayList<>();
    ;
    List<ZoneModel> zones2;
    LinearLayoutManager layoutManager;
    public RoomAllData my_dataBase;
    public static ZoneAdapter adapter;
    ImportData importData;
    public static TextView zoneName, itemName, DD_preQTY;
    public static String itemKind = "";
    public int indexZone = -1, updatedIndex = -1;
    ExportData exportData;
    ListView listZones;
    Button delete;
    int index;

    Dialog authenticationdialog;
    ListView listView;
    EditText UsNa;
    String x;
    String selectedValue;
    public static EditText zonebarecode;
    public static Dialog searchdialog;
    public static boolean validItem = false, validateKind = false;
    public List<String> items;
    public static TextView zonecode1;
    public static TextView qty1;
    public static TextView zonename1;
    public static TextView DIitemcode, DIqty, DDzoneEDT, DI_zonecode;
    public static EditText DDitemcode;
    public static TextView DI_itemcode;
    private int searchflage;
    ArrayList<ZonsData> searchlistAllZone;
    TextView Zonescount, ZonestotalQty, AllZonestotalQty;
    Button AddZonesave;
    public long sumOfQTY;
    private int ind;

    public static int flage3 = 0;
    private String preQTY;
    public int pos_item_inreducelist;
    public String UserNo;
    public String deviceId = "";
    List<com.example.irbidcitycenter.Models.appSettings> appSettings;

    private Animation animation;
    Button AD_nextZone;
    RadioButton RB_add, RB_reduce;
    RadioGroup RadioGroup;
String NewItemCode;
    List<String> FromArray = new ArrayList<>();
    public static final int DPM_ACTIVATION_REQUEST_CODE = 100;

    private ComponentName adminComponent;
    private DevicePolicyManager devicePolicyManager;
    AudioManager mode;

    Spinner store_spinner ;
  String  SelectedStore="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_add_zone);

        try {


            initial();
            Storelist.clear();
            Storelist=  my_dataBase.storeDao().getall();
            if(Storelist.size()>0) {

                for (int i = 0; i < Storelist.size(); i++) {
                    FromArray.add(Storelist.get(i).getSTORENO() + "  " + Storelist.get(i).getSTORENAME());
                }
                fillSp();
            }else
            {
                getStors();
            }
            mode = (AudioManager) AddZone.this.getSystemService(Context.AUDIO_SERVICE);
            mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);


            RB_add.setChecked(true);
            CalculateTotalandCount();

            editQty.setEnabled(false);
            UserNo = my_dataBase.settingDao().getUserNo();


            AD_nextZone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //   devicePolicyManager.setCameraDisabled(adminComponent, true); // Disable camera.
                    editZoneCode.setEnabled(true);
                    editZoneCode.setText("");
                    editZoneCode.requestFocus();


                    zoneName.setText("");
                    editItemCode.setText("");
                    itemName.setText("");

                    listZone.clear();
                    CalculateTotalandCount();
                    fillAdapter(listZone);
                }
            });
            AddZonesave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (userPermissions == null) getUsernameAndpass();
                    animation = AnimationUtils.loadAnimation(AddZone.this, R.anim.modal_in);
                    AddZonesave.startAnimation(animation);

                    if (listZone.size() != 0) {


                        if (userPermissions.getMasterUser().equals("0")) {
                            if (userPermissions.getAddZone_Save().equals("1"))

                                saveData();
                            else
                                //     generalMethod.showSweetDialog(AddZone.this, 3, getResources().getString(R.string.warning), getResources().getString(R.string.savePermission));

                                new SweetAlertDialog(AddZone.this, SweetAlertDialog.WARNING_TYPE)
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
                                        })
                                        .show();

                        } else {
                            saveData();
                        }
                    } else {
                        generalMethod.showSweetDialog(AddZone.this, 3, getResources().getString(R.string.warning), getResources().getString(R.string.fillYourList));
                    }
                }
            });


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (userPermissions == null) getUsernameAndpass();
                    animation = AnimationUtils.loadAnimation(AddZone.this, R.anim.modal_in);
                    delete.startAnimation(animation);
                    if (userPermissions.getMasterUser().equals("0")) {
                        if (userPermissions.getAddZone_LocalDelete().equals("1")) {
                            OpenDeleteDailog();
                        } else {
                            new SweetAlertDialog(AddZone.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.confirm_title))
                                    .setContentText(getResources().getString(R.string.del_per))
                                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            OpenDeleteDailog2();
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

                        OpenDeleteDailog();
                    }
                }
            });

        } catch (Exception e) {
            Toast.makeText(AddZone.this, e.getMessage() + "", Toast.LENGTH_LONG).show();
        }
    }

    private void loadLanguage() {
        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String langCode = preferences.getString(KEY_LANG, Locale.getDefault().getLanguage());
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
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
        RadioGroup = findViewById(R.id.RadioGroup);

        MainActivity.setflage = 0;

        importData = new ImportData(AddZone.this);
        exportData = new ExportData(AddZone.this);
        Zonescount = findViewById(R.id.Zonescount);
        storrespon= findViewById(R.id.storrespon);
        storrespon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {

                    if (editable.toString().equals("no data")) {


                    } else {
                        if (editable.toString().equals("fill")) {
                            Log.e("afterTextChanged", "" + editable.toString());
                            for (int i = 0; i < Storelist.size(); i++) {


                                    {

                                            FromArray.add(Storelist.get(i).getSTORENO() + "  " + Storelist.get(i).getSTORENAME());

                                    }


                            }
                            my_dataBase.storeDao().insertAll(Storelist);
                            fillSp();
                        }



                    }

                }

            }
        });

        ZonestotalQty = findViewById(R.id.ZonestotalQty);
        AllZonestotalQty = findViewById(R.id.AllZonestotalQty);
        AddZonesave = findViewById(R.id.save);
        AD_nextZone = findViewById(R.id.AD_nextZone);
        RB_add = findViewById(R.id.RB_add);
        RB_reduce = findViewById(R.id.RB_reduce);
        generalMethod = new GeneralMethod(AddZone.this);
        editZoneCode = findViewById(R.id.editZoneCode);
        editItemCode = findViewById(R.id.editItemCode);
        editItemCode.setOnEditorActionListener(onEditAction);
        editItemCode.setOnKeyListener(onKeyListener);
        editZoneCode.setOnKeyListener(onKeyListener);
        listofZonesUnPosted = new ArrayList<>();
        delete = findViewById(R.id.delete_btn);
        store_spinner= findViewById(R.id.store_spinner);
        store_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedStore=store_spinner.getSelectedItem().toString();
                SelectedStore= SelectedStore. substring(0, SelectedStore.indexOf(" "));
                Log.e("SelectedStore",SelectedStore.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editZoneCode.setOnEditorActionListener(onEditAction);
        editQty = findViewById(R.id.editQty);
        zoneName = findViewById(R.id.zoneName);
        itemName = findViewById(R.id.itemName);
        itemKintText = findViewById(R.id.itemKintText);
        exportStateText = findViewById(R.id.exportStateText);
        // editQty.setOnEditorActionListener(onEditAction);
        listZone = new ArrayList<>();

        recycleZone = findViewById(R.id.recycleZone);
        my_dataBase = RoomAllData.getInstanceDataBase(AddZone.this);
        if (userPermissions == null) getUsernameAndpass();

        //       importData.getAllZones();
        editItemCode.setEnabled(false);
        editQty.setEnabled(false);
        editZoneCode.requestFocus();


        try {
            appSettings = my_dataBase.settingDao().getallsetting();
        } catch (Exception e) {
        }

        if (appSettings.size() != 0) {
            deviceId = appSettings.get(0).getDeviceId();
            Log.e("appSettings", "+" + deviceId);

        }


        itemKintText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {
                    if (editable.toString().equals("NOTEXIST")) {
                        validateKind = false;
                        editQty.setEnabled(false);
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 1000 milliseconds
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            v.vibrate(1000);
                        }
                        Log.e("here", "here");
                        mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        try {
                            Handler h = new Handler(Looper.getMainLooper());
                            h.post(new Runnable() {
                                public void run() {
                                    showalertdaiog(AddZone.this, getString(R.string.itemNotExist) + "  " + ImportData.linkbarcode);
                                       editItemCode.setText("");
                                }
                            });

                        } catch (Exception e) {
                          //  Toast.makeText(AddZone.this, getString(R.string.itemNotExist), Toast.LENGTH_SHORT).show();
                        }

                        itemKintText.setText("");


                    } else {
                        if (editable.toString().equals("ErrorNet")) {

                            Handler h = new Handler(Looper.getMainLooper());
                            h.post(new Runnable() {
                                public void run() {

                                    generalMethod.showSweetDialog(AddZone.this, 3, getString(R.string.noInternet), "");
                                }
                            });
                            editItemCode.setText("");
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            // Vibrate for 1000 milliseconds
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                //deprecated in API 26
                                v.vibrate(1000);
                            }
                            itemKintText.setText("");
                        } else {
                            validateKind = false;
                            Log.e("afterTextChanged=====", "" + editable.toString());
                            compareItemKind(itemKintText.getText().toString().trim());
                        }

                    }

                }

            }
        });
        exportStateText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() != 0) {

                    if (editable.toString().trim().equals("Internal Application Error")) {

                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                showSweetDialog(AddZone.this, 0, "Server Error", "");
                                editItemCode.setText("");
                                editZoneCode.setEnabled(true);
                                editItemCode.setEnabled(false);
                                editZoneCode.setText("");
                                editZoneCode.requestFocus();
                                zoneName.setText("");
                                itemName.setText("");
                            }
                        });


                        listZone.clear();
                        CalculateTotalandCount();
                        if (adapter != null) adapter.notifyDataSetChanged();


                    } else if (editable.toString().trim().equals("exported")) {

                        //saveDataLocaky(1);
                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                showSweetDialog(AddZone.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                                editItemCode.setText("");
                                editZoneCode.setEnabled(true);
                                editItemCode.setEnabled(false);
                                editZoneCode.setText("");
                                editZoneCode.requestFocus();
                                zoneName.setText("");
                                itemName.setText("");

                            }
                        });
                        updateRowsPosted();




                        listZone.clear();
                        CalculateTotalandCount();
                        if (adapter != null) adapter.notifyDataSetChanged();

                    } else if (editable.toString().trim().equals("not")) {

                        //saveDataLocaky(0);
                        listZone.clear();
                        CalculateTotalandCount();
                        if (adapter != null) adapter.notifyDataSetChanged();

                    } else {

                        editItemCode.setText("");
                        editZoneCode.setEnabled(true);
                        editItemCode.setEnabled(false);
                        editZoneCode.setText("");
                        editZoneCode.requestFocus();
                        zoneName.setText("");
                        itemName.setText("");
                        listZone.clear();
                        CalculateTotalandCount();
                        if (adapter != null) adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public ZoneModel existsInDB(String zonebar, String itembar,String SelectedStore) {
        return my_dataBase.zoneDao().getzone(zonebar, itembar,SelectedStore);

    }

    public boolean exists(String zonecode, String itemcode) {
        Log.e("exists", "exists");


        //ayah edit
        for (int x = 0; x < listZone.size(); x++)
            if (listZone.get(x).getZoneCode().equals(zonecode)
                    && listZone.get(x).getItemCode().equals(itemcode)
             && listZone.get(x).getStoreID().equals(SelectedStore)
            ) {


                index = x;

                return true;


            }


        return false;
    }


    //

    TextView.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {


            Log.e("keyEvent.getAction()", keyEvent.getAction() + "");

            if (i == KeyEvent.KEYCODE_BACK) {
                //onBackPressed();


            } else if (i != KeyEvent.KEYCODE_ENTER) {
                {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP)

                        switch (view.getId()) {

                            case R.id.editZoneCode: {

                                if (editZoneCode.getText().toString().trim().length() != 0) {
                                    searchZone(editZoneCode.getText().toString().trim());
                                    Log.e("editZoneCode", editZoneCode.getText().toString());
                                } else {
                                    // editZoneCode.requestFocus();
                                 //   editZoneCode.clearFocus();
                                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    // Vibrate for 1000 milliseconds
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                    } else {
                                        //deprecated in API 26
                                        v.vibrate(1000);
                                    }
                                    Log.e("elseeditZoneCode", editZoneCode.getText().toString());


                                }


                                break;
                            }
                            case R.id.editItemCode: {
                                AD_nextZone.setEnabled(true);
                                if (!editItemCode.getText().toString().trim().equals("")) {

                                    if (editItemCode.getText().toString().trim().length() <= 15) {

                                        NewItemCode=editItemCode.getText().toString().trim();

                                        validateKind = false;
                                        clearqtyiszero();
                                        ZoneModel zoneModel = existsInDB(editZoneCode.getText().toString().trim(),  NewItemCode,SelectedStore);
                                        if (zoneModel != null) {
                                            if (!exists(editZoneCode.getText().toString().trim(),  NewItemCode))
                                                listZone.add(zoneModel);

                                        }


                                        if (exists(editZoneCode.getText().toString().trim(),  NewItemCode)) {
                                            Log.e("exists ", "true");

                                            if (RB_add.isChecked() == true) {
                                                Log.e("RB_add.isChecked() ", "true");


                                                listZone.get(index).setQty(String.valueOf(Long.parseLong(listZone.get(index).getQty()) + 1));


                                            } else {
                                                Log.e("RB_reduce.isChecked() ", "true");
                                                listZone.get(index).setQty(String.valueOf(Long.parseLong(listZone.get(index).getQty()) + -1));
                                                RB_add.setChecked(true);
                                            }
                                            if (!listZone.get(index).getQty().equals("0"))
                                                updateQtyOfRow(listZone.get(index).getItemCode(), listZone.get(index).getQty(), listZone.get(index).getZoneCode());
                                            else {


                                                my_dataBase.zoneDao().deleteITEM(listZone.get(index).getZoneCode(), listZone.get(index).getItemCode());
                                                // listZone.remove(index);

                                            }


                                            fillAdapter(listZone);
                                            CalculateTotalandCount();
                                            Log.e("case2===", listZone.size() + "");
                                            for (int x = 0; x < listZone.size(); x++) {
                                                Log.e("case2===", listZone.get(x).getZoneCode() + "");
                                            }
                                            Log.e("case2", "case2");
                                        } else {
                                            //Log.e("editItemCode", editItemCode.getText().toString());

                                            if (indexZone != -1) {
                                                Log.e("case", "case");
                                                Log.e("itemKintText", "" + itemKintText.getText().toString() + "\t" + validateKind);
                                                if (itemKintText.getText().toString().equals("") && validateKind == false) {
                                                    New_validateItemKind( NewItemCode);
                                                    Log.e("case3", "case3");
                                                    Log.e("getKindItem", "1=" + editItemCode.getText().toString());
                                                }
                                            } else {
                                                Log.e("case4", "case4");
                                                mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                                showalertdaiog(AddZone.this, "Invalid Item" + "  " +  NewItemCode);
                                                editItemCode.setText("");
                                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                                // Vibrate for 1000 milliseconds
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                    v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                                } else {
                                                    //deprecated in API 26
                                                    v.vibrate(1000);
                                                }
                                                editItemCode.requestFocus();

                                            }
                                        }


                                    } else {
                                        Log.e("length==", editItemCode.length() + "");
                                        mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                            editItemCode.setText("");
                                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                        // Vibrate for 1000 milliseconds
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                        } else {
                                            //deprecated in API 26
                                            v.vibrate(1000);
                                        }
                                        editItemCode.setEnabled(true);
                                        editItemCode.requestFocus();
                                    }
                                } else {
                                    editItemCode.setEnabled(true);
                                    editItemCode.requestFocus();
                                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    // Vibrate for 1000 milliseconds
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                    } else {
                                        //deprecated in API 26
                                        v.vibrate(1000);
                                    }
                                    Log.e("elseeditZoneCode", editZoneCode.getText().toString());
                                }


                            }
                            break;


                        }
                }
                return true;
            }
            return false;
        }


    };


    private void compareItemKind(String itemTypa) {
        validItem = false;
        if (listAllZone.size() > 0) {
            if (listAllZone.get(indexZone).getZONETYPE().trim().equals(itemTypa)) {
                validItem = true;
                itemName.setText(itemKind);
                editItemCode.setEnabled(false);
                //editQty.setEnabled(true);

                if (validItem) {
                    Log.e("lastcase777==", "lastcase");
                    CalculateTotalandCount();
                    addRow();
                    validItem = false;
                    itemKintText.setText("");
                } else {
                    Log.e("lastcase888==", "lastcase");
                    editItemCode.setText("");
                    editItemCode.setEnabled(true);
                    editItemCode.requestFocus();
                }

            } else {
                editQty.setEnabled(false);
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 1000 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(1000);
                }
                mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                showalertdaiog(AddZone.this, getString(R.string.kindDifferent) + "  " +  NewItemCode);

                //generalMethod.showSweetDialog(AddZone.this, 0, getString(R.string.kindDifferent) + "  " + editItemCode.getText(), "");
                editItemCode.setText("");
                editItemCode.setEnabled(true);
                editItemCode.requestFocus();

            }

        } else {
            Log.e("lastcase", "lastcase");
            editItemCode.setEnabled(true);
            editItemCode.setText("");
            editItemCode.requestFocus();
        }
        itemKintText.setText("");
    }

    TextView.OnEditorActionListener onEditAction = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//            if (keyEvent.getAction() != KeyEvent.ACTION_UP) {// null object reference
            // Log.e("onEditorAction","i"+i+"\ti"+keyEvent.getDeviceId());
//                if (i!= KeyEvent.ACTION_UP) {// duplicate
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                    || i == EditorInfo.IME_NULL) {
                switch (textView.getId()) {
                    case R.id.editZoneCode: {

                        if (editZoneCode.getText().toString().trim().length() != 0) {
                            searchZone(editZoneCode.getText().toString().trim());
                            Log.e("editZoneCode", editZoneCode.getText().toString());
                        } else {
                            editZoneCode.requestFocus();

                            Log.e("elseeditZoneCode", editZoneCode.getText().toString());
                        }


                        break;
                    }
                    case R.id.editItemCode: {
                        AD_nextZone.setEnabled(true);
                        if (!editItemCode.getText().toString().trim().equals("")) {

                            if (editItemCode.getText().toString().length() <= 15) {

                                validateKind = false;
                                clearqtyiszero();
                                ZoneModel zoneModel = existsInDB(editZoneCode.getText().toString().trim(), editItemCode.getText().toString().trim(),SelectedStore);
                                if (zoneModel != null) {
                                    if (!exists(editZoneCode.getText().toString().trim(), editItemCode.getText().toString()))
                                        listZone.add(zoneModel);
                                    Log.e("case1===", zoneModel.getZoneCode());
                                    Log.e("case1===", zoneModel.getItemCode());
                                    Log.e("case1==", listZone.size() + "");
                                    Log.e("case1", "case1");
                                }


                                if (exists(editZoneCode.getText().toString().trim(), editItemCode.getText().toString())) {
                                    Log.e("exists ", "true");

                                    if (RB_add.isChecked() == true) {
                                        Log.e("RB_add.isChecked() ", "true");

                                        Log.e("ZoneCode()== ", listZone.get(index).getZoneCode());
                                        Log.e("ItemCode()==", listZone.get(index).getItemCode());

                                        listZone.get(index).setQty(String.valueOf(Long.parseLong(listZone.get(index).getQty()) + 1));


                                    } else {
                                        Log.e("RB_reduce.isChecked() ", "true");
                                        listZone.get(index).setQty(String.valueOf(Long.parseLong(listZone.get(index).getQty()) + -1));
                                        RB_add.setChecked(true);
                                    }
                                    if (!listZone.get(index).getQty().equals("0"))
                                        updateQtyOfRow(listZone.get(index).getItemCode(), listZone.get(index).getQty(), listZone.get(index).getZoneCode());
                                    else {


                                        my_dataBase.zoneDao().deleteITEM(listZone.get(index).getZoneCode(), listZone.get(index).getItemCode());
                                        // listZone.remove(index);

                                    }


                                    fillAdapter(listZone);
                                    CalculateTotalandCount();
                                    Log.e("case2===", listZone.size() + "");
                                    for (int x = 0; x < listZone.size(); x++) {
                                        Log.e("case2===", listZone.get(x).getZoneCode() + "");
                                    }
                                    Log.e("case2", "case2");
                                } else {
                                    //Log.e("editItemCode", editItemCode.getText().toString());

                                    if (indexZone != -1) {
                                        Log.e("case", "case");
                                        Log.e("itemKintText", "" + itemKintText.getText().toString() + "\t" + validateKind);
                                        if (itemKintText.getText().toString().equals("") && validateKind == false) {
                                            New_validateItemKind(editItemCode.getText().toString().trim());
                                            Log.e("case3", "case3");
                                        }
                                    } else {
                                        Log.e("case4", "case4");

                                        generalMethod.showSweetDialog(AddZone.this, 0, "", "Invalid Item  " + editItemCode.getText());
                                        editItemCode.setText("");

                                        editItemCode.requestFocus();

                                    }
                                }


                            } else {
                                Log.e("case66", "case66");
                                generalMethod.showSweetDialog(AddZone.this, 0, "", "Invalid Item  " + editItemCode.getText());
                                editItemCode.setText("");

                                editItemCode.setEnabled(true);
                                editItemCode.requestFocus();
                            }
                        } else {
                            editItemCode.requestFocus();

                            Log.e("elseeditZoneCode", editZoneCode.getText().toString());
                        }


                    }
                    break;


                }

            }
//            }
            return true;
        }
    };

    private void clearqtyiszero() {
        for (int i = 0; i < listZone.size(); i++)
            if (listZone.get(i).getQty().equals("0"))
                listZone.remove(i);
    }

    private void validateItemKind(String itemNo) {
        validateKind = true;
        // http://localhost:8082/IrGetItemData?CONO=290&ITEMCODE=28200152701

    importData.getKindItem(itemNo);


    }

    private void searchZone(String codeZone) {
        indexZone = -1;
        editZoneCode.setError(null);

        for (int i = 0; i < listAllZone.size(); i++) {
            if (listAllZone.get(i).getZoneCode().equals(codeZone)) {
                indexZone = i;
                fillData(i, "");


                break;
            }
        }
        if (indexZone == -1) {
            if (listAllZone.size() != 0) {
                editItemCode.setEnabled(false);
                editQty.setEnabled(false);
                mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                showalertdaiog(AddZone.this, "Invalid Zone" + "  " + editZoneCode.getText());
                //  showSweetDialog(AddZone.this, 3, getResources().getString(R.string.warning), "Invalid Zone" + "  " + editZoneCode.getText());
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 1000 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(1000);
                }

                editZoneCode.setText("");
            } else {
                editZoneCode.setText("");
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 1000 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(1000);
                }
                Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private String getusernum() {
        return "";
    }

    private void addRow() {
        if (!editZoneCode.getText().toString().equals("")) {
            if (! NewItemCode.equals("")) {

                if ( NewItemCode.length() <= 15) {

                    if (!exists(editZoneCode.getText().toString().trim(),
                            editItemCode.getText().toString())) {
                        ZoneModel itemZone = new ZoneModel();
                        itemZone.setZoneCode(editZoneCode.getText().toString().trim());
                        itemZone.setItemCode( NewItemCode);
                     //   itemZone.setItemCode(importData.barcode);
                        if (RB_add.isChecked() == true)
                            itemZone.setQty("1");
                        else
                            itemZone.setQty("-1");

                        RB_add.setChecked(true);
                        itemZone.setIsPostd("0");
                        itemZone.setStoreID(SelectedStore);
                        itemZone.setZONETYPE(zonetype);
                        itemZone.setItemName(itemName.getText().toString());
                        itemZone.setZONENAME(zoneName.getText().toString());
                        itemZone.setDeviceId(deviceId);
                        itemZone.setStoreNo("6");
                        itemZone.setUserNO(UserNo);
                        itemZone.setZoneDate(generalMethod.getCurentTimeDate(1));
                        itemZone.setZoneTime(generalMethod.getCurentTimeDate(2));
                        itemZone.setStoreNo(getusernum());


                        listZone.add(itemZone);

                        editItemCode.setText("");
                        editItemCode.setEnabled(true);
                        editItemCode.requestFocus();


                        CalculateTotalandCount();
                        fillAdapter(listZone);
                        saveRow(itemZone);
                        clearData();

                    } else {


                        generalMethod.showSweetDialog(AddZone.this, 3, "", "Invalid Item  " + editItemCode.getText());
                        editItemCode.setText("");
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 1000 milliseconds
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            v.vibrate(1000);
                        }
                        editItemCode.setEnabled(true);
                        editItemCode.requestFocus();


                    }
                } else {


                       editItemCode.setText("");
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 1000 milliseconds
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        v.vibrate(1000);
                    }
                    editItemCode.setEnabled(true);
                    editItemCode.requestFocus();


                }
            }
                else {
                editItemCode.setText("");
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 1000 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(1000);
                }
                editItemCode.setEnabled(true);
                editItemCode.requestFocus();
            }


        }

    }

    private void saveRow(ZoneModel zone) {
        my_dataBase.zoneDao().insert(zone);
        CalculateTotalandCount();

    }

    private void updateRowsPosted() {
        my_dataBase.zoneDao().updateZonePosted();
    }

    private void updateListZones(ZoneModel itemZone, int updatedIndex) {
        Log.e("updateListZones1", "" + listZone.get(updatedIndex).getQty());
        long currentQty = 0, qtyNew = 0;
        try {
            currentQty = Long.parseLong(listZone.get(updatedIndex).getQty());
            qtyNew = Long.parseLong(itemZone.getQty());
            Log.e("updateListZones2", "" + currentQty + "\t" + qtyNew);
        } catch (Exception e) {
        }
        listZone.get(updatedIndex).setQty((currentQty + qtyNew) + "");
        Log.e("updateListZones3", "" + listZone.get(updatedIndex).getQty());
    }

    private boolean itemCodeExist(String itemCode) {
        for (int i = 0; i < listZone.size(); i++) {
            Log.e("itemCodeExist", "" + listZone.get(i).getItemCode() + "\t" + itemCode.trim());
            if ((listZone.get(i).getItemCode().trim()).equals(itemCode.trim())) {
                updatedIndex = i;
                Log.e("itemCodeExist", "" + updatedIndex);
                return true;
            }
        }
        return false;
    }

    private void fillAdapter(ArrayList<ZoneModel> listZone) {

        layoutManager = new LinearLayoutManager(AddZone.this);
        recycleZone.setLayoutManager(layoutManager);
        adapter = new ZoneAdapter(AddZone.this, listZone);
        recycleZone.setAdapter(adapter);
        CalculateTotalandCount();
        editItemCode.setText("");
    }

    public static void clearData() {
        editItemCode.setText("");

//        editZoneCode.setText("");
        editItemCode.requestFocus();
        itemName.setText("");

    }

    public static void clearAllScreenZon() {

        clearData();
        clearLists();
        editZoneCode.setText("");
        editZoneCode.setEnabled(true);
        editZoneCode.requestFocus();
        zoneName.setText("");

    }

    public void ScanCode(View view) {
        switch (view.getId()) {
            case R.id.scanZoneCode:
                readBarcode(1);
                break;
            case R.id.scanItemCode:
                readBarcode(2);
                break;
        }
    }

    private void readBarcode(int type) {
        //new IntentIntegrator(AddZone.this).setOrientationLocked(false).setCaptureActivity(CustomScannerActivity.class).initiateScan();

        openSmallCapture(type);
    }

    private void openSmallCapture(int type) {
        if (ContextCompat.checkSelfPermission(AddZone.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddZone.this, new String[]{Manifest.permission.CAMERA}, REQUEST_Camera_Barcode);
            if (ContextCompat.checkSelfPermission(AddZone.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {//just for first time
                Intent i = new Intent(AddZone.this, ScanActivity.class);
                i.putExtra("key", "1");
                startActivity(i);
                // searchByBarcodeNo(s + "");
            }
        } else {
            Intent i = new Intent(AddZone.this, ScanActivity.class);
            if (type == 1) {
                i.putExtra("key", "1");
            } else {
                i.putExtra("key", "2");
            }

            startActivity(i);
            //  searchByBarcodeNo(s + "");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Toast.makeText(AddZone.this, "cancelled", Toast.LENGTH_SHORT).show();


            } else {
                String valueBarcode = Result.getContents();

            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void saveData() {


        listofZonesUnPosted = my_dataBase.zoneDao().getAllZonesUnposted();


        for (int i = 0; i < listofZonesUnPosted.size(); i++)
            if (listofZonesUnPosted.get(i).getDeviceId() == null)
                listofZonesUnPosted.get(i).setDeviceId(deviceId);

        editItemCode.setText("");

  editZoneCode.setText("");
        editZoneCode.requestFocus();
        zoneName.setText("");
        itemName.setText("");

        exportData();

             /*   if(listZone.size()!=0)
                {
                    for(int i=0;i<listZone.size();i++) {
                        listZone.get(i).setItemCode(convertToEnglish(listZone.get(i).getItemCode()));
                        listZone.get(i).setQty(convertToEnglish(listZone.get(i).getQty()));
                    }
                        exportData();

                }
                else {
                    generalMethod.showSweetDialog(AddZone.this,3,getResources().getString(R.string.warning),getResources().getString(R.string.fillYourList));
                }*/


    }

    private void saveDataLocaky(int isPosted) {
        if (isPosted == 1) {
            for (int i = 0; i < listZone.size(); i++) {
                listZone.get(i).setIsPostd("1");
            }
        }
        Log.e("saveDataLocaky", "listZone" + listZone.size());
        long result[] = my_dataBase.zoneDao().insertAll(listZone);

        if (result.length != 0) {
            generalMethod.showSweetDialog(this, 1, this.getResources().getString(R.string.savedSuccsesfule), "");
        }

        clearAllScreenZon();
        CalculateTotalandCount();
        fillAdapter(listZone);


    }

    /*  public void exportData(){
          exportData.exportZoneList(listZone,1);
      }*/
    public void exportData() {
        exportData.exportZoneList(listofZonesUnPosted, 1);
    }

    public static void clearLists() {
        listZone.clear();

        // adapter.notifyDataSetChanged();

    }

    private void getDataZone() {
        List<ZoneModel> listZon = new ArrayList();
        listZon = my_dataBase.zoneDao().getAllZones();
    }

    public void exitAddZone(View view) {
        if (view.getId() == R.id.cancel_btn) {
            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                public void run() {
                    animation = AnimationUtils.loadAnimation(AddZone.this, R.anim.modal_in);
                    findViewById(R.id.cancel_btn).startAnimation(animation);
                    new SweetAlertDialog(AddZone.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.messageExit))
                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    if (listZone.size() > 0) {
                                        new SweetAlertDialog(AddZone.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText(getResources().getString(R.string.confirm_title))
                                                .setContentText(getResources().getString(R.string.messageExit2))
                                                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                        listZone.clear();
                                                        adapter.notifyDataSetChanged();

                                                        sweetAlertDialog.dismissWithAnimation();
                                                        finish();


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
                                        sweetAlertDialog.dismiss();
                                        finish();
                                    }
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
            });


        }

    }

    private void showExitDialog() {


                new SweetAlertDialog(AddZone.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.confirm_title))
                        .setContentText(getResources().getString(R.string.messageExit))
                        .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (listZone.size() > 0) {
                                    new SweetAlertDialog(AddZone.this, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText(getResources().getString(R.string.confirm_title))
                                            .setContentText(getResources().getString(R.string.messageExit2))
                                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                    listZone.clear();
                                                    adapter.notifyDataSetChanged();
                                                    sweetAlertDialog.dismiss();
                                                    finish();


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
                                    sweetAlertDialog.dismiss();
                                    finish();
                                }
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
    public void onBackPressed() {

     //   super.onBackPressed();
                showExitDialog();

//    System.exit(0);
    }

    public void showZoneDialog(View view) {
        if (view.getId() == R.id.zoneSearch) {
            if (listAllZone.size() != 0) {
                showDialogSearch(AddZone.this);
            }

            else {
                importData = new ImportData(AddZone.this);
                importData.getAllZones();
            }


        }
    }

    private void showDialogSearch(final Context context1) {

        final Dialog dialog = new Dialog(context1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_zone_search);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        LinearLayout headerComp2 = dialog.findViewById(R.id.headerComp2);
        searchlistAllZone = new ArrayList<>();
        String zoneSelected = "";

        ArrayList<String> nameOfEngi = new ArrayList<>();
        listZones = dialog.findViewById(R.id.listViewEngineering);

        final int[] rowZone = new int[1];
        final String[] selectedZon = new String[1];
        if (listAllZone.size() != 0) {
            nameOfEngi.clear();
            for (int i = 0; i < listAllZone.size(); i++) {
//                nameOfEngi.add("tahani");
                searchlistAllZone.add(listAllZone.get(i));
                nameOfEngi.add(listAllZone.get(i).getZoneCode());
            }
            Log.e("nameOfEngi", "" + nameOfEngi.size());
            fillAdapterSearch(nameOfEngi);

//                    simple_list_item_1 simple_list_item_activated_1

        }
        headerComp2.setVisibility(View.GONE);
        EditText searchZoneText = dialog.findViewById(R.id.searchZoneText);
        searchZoneText.setVisibility(View.GONE);
        searchZoneText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (searchZoneText.getText().toString().trim().length() != 0) {
                    nameOfEngi.clear();
                    searchlistAllZone.clear();
                    for (int i = 0; i < listAllZone.size(); i++) {

                        if (listAllZone.get(i).getZoneCode().toLowerCase().trim().contains(searchZoneText.getText().toString().toLowerCase().trim())) {
                            nameOfEngi.add(listAllZone.get(i).getZoneCode());
                            searchlistAllZone.add(listAllZone.get(i));
                        }


                    }
                    Log.e("nameOfEngi", "search" + nameOfEngi.size());
                    fillAdapterSearch(nameOfEngi);
                }


            }
        });


        listZones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rowZone[0] = position;


                listZones.requestFocusFromTouch();
                listZones.setSelection(position);
                if (searchlistAllZone.size() != 0) {
                    searchflage = 0;
                    selectedZon[0] = searchlistAllZone.get(position).getZoneCode();


                } else {
                    searchflage = 1;
                    selectedZon[0] = listAllZone.get(position).getZoneCode();

                }

                Log.e("nameOfEngi", "" + selectedZon[0]);

            }
        });


        Button btn_send = dialog.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setZone(rowZone[0], selectedZon[0]);
                editZoneCode.setEnabled(true);
                dialog.dismiss();


            }
        });
        dialog.show();

    }

    private void fillAdapterSearch(ArrayList<String> nameOfEngi) {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (AddZone.this, android.R.layout.simple_list_item_1, nameOfEngi) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                /// Get the Item from ListView
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                tv.setTextColor(getResources().getColor(R.color.text_color));

                return view;
            }
        };
        listZones.setAdapter(arrayAdapter);
    }

    void setZone(int i, String zone) {
        if (!editZoneCode.getText().toString().trim().equals("")) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getResources().getString(R.string.confirm_title))
                    .setContentText(getResources().getString(R.string.clearAllData))
                    .setConfirmButton(getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            clearAllScreenZon();

                            CalculateTotalandCount();
                            fillAdapter(listZone);
                            fillData(i, zone);


                        }
                    }).setCancelButton(getResources().getString(R.string.cancel), new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            })
                    .show();

        } else {
            fillData(i, zone);
        }


    }

    private void fillData(int i, String zone) {
//     if(i!=-1)
//     {
//
//     }
        indexZone = i;

        editZoneCode.setText(listAllZone.get(i).getZoneCode());

        zoneName.setText(listAllZone.get(i).getZONETYPE());
        editZoneCode.setEnabled(false);
        editItemCode.setEnabled(true);
        editItemCode.requestFocus();


    }

    private void updateQtyOfRow(String barecode, String Qty, String zone) {
        int s = my_dataBase.zoneDao().updateQTY(barecode, Qty, zone);
        Log.e("s=====", s + "");

    }

    private void OpenDeleteDailog() {


        final Dialog dialog = new Dialog(AddZone.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.deletelayout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        Button deletezone = dialog.findViewById(R.id.deletezone);
        Button deleteitem = dialog.findViewById(R.id.deleteitem);
        zones = new ArrayList<>();
        deletezone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletedZonsList.clear();

                authenticDailog(1);

            }
        });

        deleteitem.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              getzonefromDB();

                                              if (zones2.size() > 0) {

                                                  authenticDailog(2);

                                              } else
                                                  Toast.makeText(AddZone.this, "No Data", Toast.LENGTH_SHORT).show();
                                          }
                                      }
        );

        dialog.findViewById(R.id.dialogcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalculateTotalandCount();
                dialog.dismiss();
            }
        });


    }

    private void OpenDeleteDailog2() {


        final Dialog dialog = new Dialog(AddZone.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.deletelayout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        Button deletezone = dialog.findViewById(R.id.deletezone);
        Button deleteitem = dialog.findViewById(R.id.deleteitem);
        zones = new ArrayList<>();
        deletezone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletedZonsList.clear();

                authenticDailog2(1);

            }
        });

        deleteitem.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              getzonefromDB();

                                              if (zones2.size() > 0) {

                                                  authenticDailog2(2);

                                              } else
                                                  Toast.makeText(AddZone.this, "No Data", Toast.LENGTH_SHORT).show();
                                          }
                                      }
        );

        dialog.findViewById(R.id.dialogcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalculateTotalandCount();
                dialog.dismiss();
            }
        });


    }

    public boolean isEx() {
        boolean f = false;
        for (int x = 0; x < zones2.size(); x++)
            if (zones2.get(x).getZoneCode().equals(DDzoneEDT.getText().toString().trim())) {
                f = true;
                break;
            } else {
                f = false;

            }
        return f;
    }

    public boolean ismatch() {
        boolean f = false;
        for (int x = 0; x < zones2.size(); x++) {


            if (zones2.get(x).getZoneCode().equals(DDzoneEDT.getText().toString().trim())
                    && zones2.get(x).getItemCode().equals(DDitemcode.getText().toString().trim())) {

                ind = x;

                f = true;
                break;

            } else {
                f = false;
                continue;
            }

        }
        return f;
    }


    public void copylist() {
        zonescopylist.clear();
        List<String> zon = new ArrayList<>();
        List<String> item = new ArrayList<>();
        List<String> qtys = new ArrayList<>();
        for (int i = 0; i < zones2.size(); i++) {
            zon.add(zones2.get(i).getZoneCode());
            item.add(zones2.get(i).getItemCode());
            qtys.add(zones2.get(i).getQty());
        }

        for (int i = 0; i < zon.size(); i++) {
            ZoneModel zoneModel = new ZoneModel();
            zoneModel.setQty(qtys.get(i));
            zoneModel.setZoneCode(zon.get(i));
            zoneModel.setItemCode(item.get(i));
            zonescopylist.add(zoneModel);
        }

    }


    private void openDeleteitemDailog() {


        Log.e(" zones2size", "" + zones2.size());

        final Dialog Deleteitemdialog = new Dialog(AddZone.this);
        Deleteitemdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Deleteitemdialog.setCancelable(false);
        Deleteitemdialog.setContentView(R.layout.deleteitemdailog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Deleteitemdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        Deleteitemdialog.getWindow().setAttributes(lp);
        Deleteitemdialog.show();
        items = new ArrayList<>();


        DDitemcode = Deleteitemdialog.findViewById(R.id.DD_itemcode);
        DI_itemcode = Deleteitemdialog.findViewById(R.id.DI_itemcode);
        DIqty = Deleteitemdialog.findViewById(R.id.DI_qty);
        DDzoneEDT = Deleteitemdialog.findViewById(R.id.DD_ZONEcode);
        DI_zonecode = Deleteitemdialog.findViewById(R.id.DI_zone);
        DD_preQTY = Deleteitemdialog.findViewById(R.id.DD_preQTY);
        Button deletebtn = Deleteitemdialog.findViewById(R.id.DD_deleteitem);

        DDitemcode.setEnabled(false);
        deletebtn.setEnabled(false);
        DD_preQTY.setText("");
        DDzoneEDT.requestFocus();
        DDzoneEDT.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != KeyEvent.KEYCODE_ENTER) {

                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (!DDzoneEDT.getText().toString().trim().equals("")) {
                            if (isEx()) {
                                DDitemcode.setEnabled(true);
                                DDitemcode.requestFocus();

                            } else {
                                DDzoneEDT.setError("INvalid");
                                DDzoneEDT.setText("");
                            }

                        } else {
                            DDzoneEDT.requestFocus();
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        DDzoneEDT.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                if (i != KeyEvent.ACTION_UP) {

                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                            || i == EditorInfo.IME_NULL) {
                        if (!DDzoneEDT.getText().toString().trim().equals("")) {
                            if (isEx()) {
                                DDitemcode.setEnabled(true);
                                DDitemcode.requestFocus();

                            } else {
                                DDzoneEDT.setError("INvalid");
                                DDzoneEDT.setText("");
                            }

                        } else {
                            DDzoneEDT.requestFocus();
                        }
                    }
                }

                return true;
            }
        });


        Deleteitemdialog.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deleteitemdialog.dismiss();
                authenticationdialog.dismiss();
                ReducedItemlist.clear();
                zones2.clear();
                zonescopylist.clear();
                zonelist.clear();
            }
        });
        Deleteitemdialog.findViewById(R.id.cancel1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ReducedItemlist.size() > 0) {

                    new SweetAlertDialog(AddZone.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.returndata))
                            .setConfirmButton(getResources().getString(R.string.yes),
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {


                                            ReducedItemlist.clear();
                                            zones2.clear();
                                            zonescopylist.clear();
                                            getzonefromDB();


                                            Toast.makeText(AddZone.this, getResources().getString(R.string.dataRet), Toast.LENGTH_LONG).show();

                                            AddZone.DDitemcode.setText("");
                                            AddZone.DI_zonecode.setText("");
                                            AddZone.DIqty.setText("");
                                            AddZone.DD_preQTY.setText("");
                                            AddZone.DI_itemcode.setText("");

                                            AddZone.DDzoneEDT.setText("");
                                            AddZone.DDzoneEDT.setEnabled(true);
                                            AddZone.DDzoneEDT.requestFocus();
                                            AddZone.DDitemcode.setEnabled(false);
                                            sweetAlertDialog.dismiss();


                                        }

                                    })
                            .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismiss();

                                    // FinishProcessFlag=0;
                                }
                            })
                            .show();


                } else {
                    Toast.makeText(AddZone.this, getResources().getString(R.string.NODATA), Toast.LENGTH_LONG).show();
                }
                ///


            }
        });

        Deleteitemdialog.findViewById(R.id.zoneSearch2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zonelist.clear();
                for (int i = 0; i < zones2.size(); i++)
                    if (!zonelist.contains(zones2.get(i).getZoneCode()))
                        zonelist.add(zones2.get(i).getZoneCode());


                if (zonelist.size() != 0) {
                    flage3 = 6;
                    searchZonecodeDailog(zonelist);
                } else
                    Toast.makeText(AddZone.this, "No Data", Toast.LENGTH_LONG).show();


            }
        });


        //dditemcode=DDitemcode.getText().toString().trim();
        DDitemcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i != KeyEvent.KEYCODE_ENTER) {

                    {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            if (!DDitemcode.getText().toString().equals("")) {
                                if (ismatch()) {
                                    String x = getpreQty(zones2.get(ind).getZoneCode(), zones2.get(ind).getItemCode());
                                    DD_preQTY.setText(x);


                                    sumOfQTY = Long.parseLong(zones2.get(ind).getQty());
                                    if (sumOfQTY > 1) {
                                        sumOfQTY -= 1;
                                        // oldQTY.setText( sumOfQTY+"" );

                                        DI_itemcode.setText(DDitemcode.getText().toString());


                                        zones2.get(ind).setQty(sumOfQTY + "");
                                        if (ReducedItemlist.size() > 0) {


                                            if (Exists(zones2.get(ind).getZoneCode(), zones2.get(ind).getItemCode())) {
                                                Log.e("ReducedItemlistsumOfQTY", sumOfQTY + "");
                                                Log.e("updated", zones2.get(ind).getItemCode());
                                                Log.e("ReducedItemlistQTY", zones2.get(ind).getQty() + "");
                                                Log.e("pos_item_inreducelist", "" + pos_item_inreducelist);
                                                ReducedItemlist.remove(pos_item_inreducelist);
                                                ReducedItemlist.add(zones2.get(ind));
                                                //   ReducedItemlist.get(pos_item_inreducelist).setQty(sumOfQTY+"");

                                            } else {
                                                zones2.get(ind).setQty(sumOfQTY + "");
                                                ReducedItemlist.add(zones2.get(ind));
                                                Log.e("ReducedItemlistQTY", zones2.get(ind).getQty() + "");
                                                Log.e("added", zones2.get(ind).getItemCode());
                                                Log.e("ReducedItemlistsumOfQTY", sumOfQTY + "");


                                            }
                                        } else {
                                            Log.e("elseadded", zones2.get(ind).getItemCode());
                                            Log.e("ReducedItemlistQTY", zones2.get(ind).getQty() + "");
                                            ReducedItemlist.add(zones2.get(ind));
                                        }

                                        //  sumOfQTY -= 1;

                                        DIqty.setText(sumOfQTY + "");
                                        DI_zonecode.setText(zones2.get(ind).getZoneCode());

                                        deletebtn.setEnabled(true);
                                        DDitemcode.setText("");
                                        DDitemcode.requestFocus();

                                    }//end of if(sumOfQTY >1)
                                    else {
                                        new SweetAlertDialog(AddZone.this, SweetAlertDialog.BUTTON_CONFIRM)
                                                .setTitleText(getResources().getString(R.string.confirm_title))
                                                .setContentText(getResources().getString(R.string.delete3))
                                                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        if (ReducedItemlist.size() > 0) {
                                                            if (Exists(zones2.get(ind).getZoneCode(), zones2.get(ind).getItemCode())) {

                                                                ReducedItemlist.get(pos_item_inreducelist).setQty("0");
                                                                zones2.remove(ind);
                                                            } else {
                                                                zones2.get(ind).setQty("0");
                                                                ReducedItemlist.add(zones2.get(ind));
                                                                zones2.remove(ind);
                                                            }
                                                        } else {
                                                            zones2.get(ind).setQty("0");
                                                            ReducedItemlist.add(zones2.get(ind));
                                                            zones2.remove(ind);
                                                        }

                                                        cleardataOfDailog();
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

                                        DI_itemcode.setText(DDitemcode.getText().toString());
                                        DIqty.setText("1");
                                        DI_zonecode.setText(zones2.get(ind).getZoneCode());
                                        deletebtn.setEnabled(true);
                                        DDitemcode.setText("");
                                        DDitemcode.requestFocus();

                                    }
                                } else {
                                    DDitemcode.setError("Invalid");
                                    DDitemcode.setText("");
                                }
                            } else
                                DDitemcode.requestFocus();
                        }
                    }
                    return true;
                }

                return false;
            }
        });

        DDitemcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                if (i != KeyEvent.ACTION_UP) {

                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                            || i == EditorInfo.IME_NULL) {
                        if (!DDitemcode.getText().toString().equals("")) {
                            if (ismatch()) {
                                String x = getpreQty(zones2.get(ind).getZoneCode(), zones2.get(ind).getItemCode());
                                DD_preQTY.setText(x);


                                sumOfQTY = Long.parseLong(zones2.get(ind).getQty());
                                if (sumOfQTY > 1) {
                                    sumOfQTY -= 1;
                                    // oldQTY.setText( sumOfQTY+"" );

                                    DI_itemcode.setText(DDitemcode.getText().toString());


                                    zones2.get(ind).setQty(sumOfQTY + "");
                                    if (ReducedItemlist.size() > 0) {


                                        if (Exists(zones2.get(ind).getZoneCode(), zones2.get(ind).getItemCode())) {
                                            Log.e("ReducedItemlistsumOfQTY", sumOfQTY + "");
                                            Log.e("updated", zones2.get(ind).getItemCode());
                                            Log.e("ReducedItemlistQTY", zones2.get(ind).getQty() + "");
                                            Log.e("pos_item_inreducelist", "" + pos_item_inreducelist);
                                            ReducedItemlist.remove(pos_item_inreducelist);
                                            ReducedItemlist.add(zones2.get(ind));
                                            //   ReducedItemlist.get(pos_item_inreducelist).setQty(sumOfQTY+"");

                                        } else {
                                            zones2.get(ind).setQty(sumOfQTY + "");
                                            ReducedItemlist.add(zones2.get(ind));
                                            Log.e("ReducedItemlistQTY", zones2.get(ind).getQty() + "");
                                            Log.e("added", zones2.get(ind).getItemCode());
                                            Log.e("ReducedItemlistsumOfQTY", sumOfQTY + "");


                                        }
                                    } else {
                                        Log.e("elseadded", zones2.get(ind).getItemCode());
                                        Log.e("ReducedItemlistQTY", zones2.get(ind).getQty() + "");
                                        ReducedItemlist.add(zones2.get(ind));
                                    }

                                    //  sumOfQTY -= 1;

                                    DIqty.setText(sumOfQTY + "");
                                    DI_zonecode.setText(zones2.get(ind).getZoneCode());

                                    deletebtn.setEnabled(true);
                                    DDitemcode.setText("");
                                    DDitemcode.requestFocus();

                                }//end of if(sumOfQTY >1)
                                else {
                                    new SweetAlertDialog(AddZone.this, SweetAlertDialog.BUTTON_CONFIRM)
                                            .setTitleText(getResources().getString(R.string.confirm_title))
                                            .setContentText(getResources().getString(R.string.delete3))
                                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    if (ReducedItemlist.size() > 0) {
                                                        if (Exists(zones2.get(ind).getZoneCode(), zones2.get(ind).getItemCode())) {

                                                            ReducedItemlist.get(pos_item_inreducelist).setQty("0");
                                                            zones2.remove(ind);
                                                        } else {
                                                            zones2.get(ind).setQty("0");
                                                            ReducedItemlist.add(zones2.get(ind));
                                                            zones2.remove(ind);
                                                        }
                                                    } else {
                                                        zones2.get(ind).setQty("0");
                                                        ReducedItemlist.add(zones2.get(ind));
                                                        zones2.remove(ind);
                                                    }

                                                    cleardataOfDailog();
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

                                    DI_itemcode.setText(DDitemcode.getText().toString());
                                    DIqty.setText("1");
                                    DI_zonecode.setText(zones2.get(ind).getZoneCode());
                                    deletebtn.setEnabled(true);
                                    DDitemcode.setText("");
                                    DDitemcode.requestFocus();

                                }
                            } else {
                                DDitemcode.setError("Invalid");
                                DDitemcode.setText("");
                            }
                        } else
                            DDitemcode.requestFocus();
                    }
                }

                return true;
            }
        });


        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!DI_itemcode.getText().toString().trim().equals("") && !DI_zonecode.getText().toString().trim().equals("")) {


                    new SweetAlertDialog(AddZone.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.deleteitem))
                            .setConfirmButton(getResources().getString(R.string.yes),
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {


                                            try {

                                                if (ReducedItemlist.size() > 0) {

                                                    if (!Exists(zones2.get(ind).getZoneCode(), zones2.get(ind).getItemCode())) {


                                                        zones2.get(ind).setQty("0");
                                                        ReducedItemlist.add(zones2.get(ind));
                                                        zones2.remove(ind);
                                                        cleardataOfDailog();


                                                        ////
                                                    } else {
                                                        ReducedItemlist.remove(pos_item_inreducelist);
                                                        zones2.get(ind).setQty("0");
                                                        ReducedItemlist.add(zones2.get(ind));

                                                        zones2.remove(ind);
                                                        cleardataOfDailog();

                                                    }
                                                    sweetAlertDialog.dismiss();
                                                } else {

                                                    zones2.get(ind).setQty("0");
                                                    ReducedItemlist.add(zones2.get(ind));

                                                    zones2.remove(ind);
                                                    cleardataOfDailog();

                                                    sweetAlertDialog.dismiss();
                                                }


                                            } catch (Exception e) {

                                            }
                                        }


                                    })
                            .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismiss();

                                    // FinishProcessFlag=0;
                                }
                            })
                            .show();
                } else {
                    if (DDitemcode.getText().toString().trim().equals(""))
                        DDitemcode.setError("required");
                    if (DDzoneEDT.getText().toString().trim().equals(""))
                        DDzoneEDT.setError("required");
                }
            }
        });


        Deleteitemdialog.findViewById(R.id.DD_dialogsave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doupdate();
                ReducedItemlist.clear();
                Deleteitemdialog.dismiss();
                authenticationdialog.dismiss();

            }
        });

    }

    private void getzonefromDB() {
        zones2 = new ArrayList<>();
        zones2.clear();
//                 zonescopylist.clear();
        zones2 = my_dataBase.zoneDao().getAllZonesUnposted();

        //zonescopylist.addAll(zones2);
        if (zones2.size() > 0) {
            copylist();
            //    preint();
            //    preint2();

        }
    }


    private String getpreQty(String zone, String itemcode) {
        preint();
        Log.e("getpreQty", zone + "\t" + itemcode);
        Log.e("zonecopysize", zonescopylist.size() + "");
        String z = "";
        for (int i = 0; i < zonescopylist.size(); i++) {
            if (zone.equals(zonescopylist.get(i).getZoneCode()) && itemcode.equals(zonescopylist.get(i).getItemCode())) {
                Log.e("getpreQty2", zonescopylist.get(i).getZoneCode() + "\t" + zonescopylist.get(i).getItemCode());
                Log.e("getpreQty3", zonescopylist.get(i).getQty());
                Log.e("index", i + "");
                z = zonescopylist.get(i).getQty();
                break;
            } else {
                z = "";
            }

        }
        return z;
    }

    private void preint() {
        for (int i = 0; i < zonescopylist.size(); i++) {
            Log.e("zonescopyZoneCode1", zonescopylist.get(i).getZoneCode());
            Log.e("zonescopyZoneCode1", zonescopylist.get(i).getItemCode());
            Log.e("zonescopygetQty1", zonescopylist.get(i).getQty());
        }

    }

    private void preint3() {
        for (int i = 0; i < ReducedItemlist.size(); i++) {
            Log.e("zonescopyZoneCode1", ReducedItemlist.get(i).getZoneCode());
            Log.e("zonescopyZoneCode1", ReducedItemlist.get(i).getItemCode());
            Log.e("zonescopygetQty1", ReducedItemlist.get(i).getQty());
        }

    }

    private void preint2() {
        for (int i = 0; i < zones2.size(); i++) {
            Log.e("zones2ZoneCode1", zones2.get(i).getZoneCode());
            Log.e("zones2ZoneCode1", zones2.get(i).getItemCode());
            Log.e("zones2ZoneCode1", zones2.get(i).getQty());
        }

    }

    private boolean Exists(String zonecode, String itemcode) {
        boolean flage = false;
        for (int i = 0; i < ReducedItemlist.size(); i++) {
            if (ReducedItemlist.get(i).getZoneCode().equals(zonecode)
                    && ReducedItemlist.get(i).getItemCode().equals(itemcode)) {
                flage = true;
                Log.e("222", "2222");
                pos_item_inreducelist = i;
                break;
            } else {
                Log.e("1111111", "1111");

                flage = false;
                continue;
            }
        }
        return flage;
    }

    private void openDeleteZoneDailog() {

        Log.e("zone: ", zones.size() + "");
        final Dialog dialog = new Dialog(AddZone.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.deletezone);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);

        zonecode1 = dialog.findViewById(R.id.itemcode1);
        qty1 = dialog.findViewById(R.id.qty1);
        zonename1 = dialog.findViewById(R.id.zonename1);

        TextView search = dialog.findViewById(R.id.zoneSearch);
        Button savebutton = dialog.findViewById(R.id.save);
        Button cancelbutton = dialog.findViewById(R.id.cancel1);
        zonebarecode = dialog.findViewById(R.id.ZoneCode);
        zones = my_dataBase.zoneDao().getZonesUnposted();
        dialog.findViewById(R.id.BACK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                authenticationdialog.dismiss();
            }
        });
        zonebarecode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != KeyEvent.KEYCODE_ENTER) {


                    {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            if (zonebarecode.getText().toString().equals(""))
                                zonebarecode.requestFocus();
                            else {

                                if (zones.contains(zonebarecode.getText().toString().trim())) {
                                    //FILL DATA OF SELECT ZONE


                                    try {
                                        //set zone barecode
                                        zonecode1.setText(zonebarecode.getText().toString());

                                        //set qty of zone
                                        long sumqty = my_dataBase.zoneDao().GetQtyOfZone(zonebarecode.getText().toString());
                                        qty1.setText(sumqty + "");


                                        //set zonename
                                        String zoneNam = my_dataBase.zoneDao().GetNameOfZone(zonebarecode.getText().toString());
                                        zonename1.setText(zoneNam);
                                    } catch (Exception e) {

                                    }
                                } else {
                                    Toast.makeText(AddZone.this, "No Data", Toast.LENGTH_SHORT).show();
                                    zonebarecode.setText("");
                                }
                            }
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        zonebarecode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                if (i != KeyEvent.ACTION_UP) {

                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                            || i == EditorInfo.IME_NULL) {
                        if (zonebarecode.getText().toString().equals(""))
                            zonebarecode.requestFocus();
                        else {

                            if (zones.contains(zonebarecode.getText().toString().trim())) {
                                //FILL DATA OF SELECT ZONE


                                try {
                                    //set zone barecode
                                    zonecode1.setText(zonebarecode.getText().toString());

                                    //set qty of zone
                                    long sumqty = my_dataBase.zoneDao().GetQtyOfZone(zonebarecode.getText().toString());
                                    qty1.setText(sumqty + "");


                                    //set zonename
                                    String zoneNam = my_dataBase.zoneDao().GetNameOfZone(zonebarecode.getText().toString());
                                    zonename1.setText(zoneNam);
                                } catch (Exception e) {

                                }
                            } else {
                                Toast.makeText(AddZone.this, "No Data", Toast.LENGTH_SHORT).show();
                                zonebarecode.setText("");
                            }
                        }
                    }
                }
                return true;
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // zones.clear();
                //zones = my_dataBase.zoneDao().getZonesUnposted();
                if (zones.size() != 0) {
                    flage3 = 0;

                    searchZonecodeDailog(zones);
                } else
                    Toast.makeText(AddZone.this, "No Data", Toast.LENGTH_SHORT).show();


            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!zonebarecode.getText().toString().equals(""))
                    new SweetAlertDialog(AddZone.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.deletezone1))
                            .setConfirmButton(getResources().getString(R.string.yes),
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                                            //


                                            {
                                                if (deletedZonsList.size() == 0) {
                                                    deletedZonsList = my_dataBase.zoneDao().getzoneRows(zonebarecode.getText().toString().trim());
                                                } else {
                                                    deletedZonsList.addAll(my_dataBase.zoneDao().getzoneRows(zonebarecode.getText().toString().trim()));

                                                }


                                                zones.remove(zonebarecode.getText().toString().trim());

                                                zonecode1.setText("");
                                                qty1.setText("");
                                                zonename1.setText("");


                                                zonebarecode.setText("");
                                                sweetAlertDialog.dismiss();

                                            }
                                        }

                                    })
                            .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismiss();

                                    // FinishProcessFlag=0;
                                }
                            })
                            .show();


                else zonebarecode.setError(getResources().getString(R.string.required));

///
            }
        });
        dialog.findViewById(R.id.DZ_dialogsave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deletedZonsList.size() > 0) {
                    for (int i = 0; i < deletedZonsList.size(); i++) {
                        int c = my_dataBase.zoneDao().deletezonedata(deletedZonsList.get(i).getZoneCode());
                    }

                    for (int i = 0; i < deletedZonsList.size(); i++) {

                        ZoneLogs zoneLogs = new ZoneLogs();
                        zoneLogs.setZoneCode(deletedZonsList.get(i).getZoneCode());
                        zoneLogs.setItemCode(deletedZonsList.get(i).getItemCode());
                        zoneLogs.setUserNO(UsNa.getText().toString());
                        zoneLogs.setLogsDATE(generalMethod.getCurentTimeDate(1));
                        zoneLogs.setLogsTime(generalMethod.getCurentTimeDate(2));
                        zoneLogs.setPreqty(deletedZonsList.get(i).getQty());
                        zoneLogs.setZONENAME(deletedZonsList.get(i).getZONENAME());
                        zoneLogs.setZONETYPE(deletedZonsList.get(i).getZONETYPE());
                        zoneLogs.setStoreNo(deletedZonsList.get(i).getStoreNo());
                        zoneLogs.setNewqty("0");
                        my_dataBase.zoneLogsDao().insert(zoneLogs);


                    }
                    if (listZone != null) {

                        if (listZone.size() > 0) {

                            for (int i = 0; i < deletedZonsList.size(); i++) {
                                if (listZone.get(0).getZoneCode().equals(deletedZonsList.get(i).getZoneCode())) {

                                    listZone.clear();
                                    break;
                                }

                            }

                            if (adapter != null) adapter.notifyDataSetChanged();
                        }
                    }
                    deletedZonsList.clear();

                    Toast.makeText(AddZone.this, "Done", Toast.LENGTH_LONG).show();
                    authenticationdialog.dismiss();
                    dialog.dismiss();
                    //  checkLocalList2();
                } else {
                    Toast.makeText(AddZone.this, "NO data changed", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    authenticationdialog.dismiss();

                }
            }


        });
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deletedZonsList.size() > 0) {
                    new SweetAlertDialog(AddZone.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.returndata))
                            .setConfirmButton(getResources().getString(R.string.yes),
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                                            for (int i = 0; i < deletedZonsList.size(); i++)
                                                my_dataBase.zoneDao().insert(deletedZonsList.get(i));

                                            deletedZonsList.clear();
                                            zones.clear();
                                            Toast.makeText(AddZone.this, getResources().getString(R.string.dataRet), Toast.LENGTH_SHORT).show();
                                            zones = my_dataBase.zoneDao().getZonesUnposted();
                                            sweetAlertDialog.dismiss();
                                            authenticationdialog.dismiss();
                                        }

                                    })
                            .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismiss();

                                    // FinishProcessFlag=0;
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(AddZone.this, getResources().getString(R.string.NODATA), Toast.LENGTH_SHORT).show();

                }

            }
        });
        dialog.show();
    }

    void doupdate() {

        int z = 6, x = 9;
        Log.e("doupdatesize", ReducedItemlist.size() + "");
        if (ReducedItemlist.size() > 0) {
            for (int i = 0; i < ReducedItemlist.size(); i++) {

                if (ReducedItemlist.get(i).getQty().equals("0")) {


                    ZoneLogs zoneLogs = new ZoneLogs();
                    zoneLogs.setZoneCode(ReducedItemlist.get(i).getZoneCode());
                    zoneLogs.setItemCode(ReducedItemlist.get(i).getItemCode());
                    zoneLogs.setUserNO(UsNa.getText().toString());
                    zoneLogs.setLogsDATE(generalMethod.getCurentTimeDate(1));
                    zoneLogs.setLogsTime(generalMethod.getCurentTimeDate(2));
                    zoneLogs.setNewqty("0");
                    zoneLogs.setPreqty(getpreQty(ReducedItemlist.get(i).getZoneCode(), ReducedItemlist.get(i).getItemCode()));
                    my_dataBase.zoneLogsDao().insert(zoneLogs);


                    z = my_dataBase.zoneDao().deleteITEM(ReducedItemlist.get(i).getZoneCode(), ReducedItemlist.get(i).getItemCode());
                    Log.e("doupdatez=", z + "");
                } else {
                    x = my_dataBase.zoneDao().updateQTYreduced(ReducedItemlist.get(i).getItemCode(), ReducedItemlist.get(i).getQty(), ReducedItemlist.get(i).getZoneCode());
                    Log.e("doupdateelse=", x + "");


                    ZoneLogs zoneLogs = new ZoneLogs();
                    zoneLogs.setZoneCode(ReducedItemlist.get(i).getZoneCode());
                    zoneLogs.setItemCode(ReducedItemlist.get(i).getItemCode());
                    zoneLogs.setUserNO(UsNa.getText().toString());
                    zoneLogs.setLogsDATE(generalMethod.getCurentTimeDate(1));
                    zoneLogs.setLogsTime(generalMethod.getCurentTimeDate(2));
                    zoneLogs.setNewqty(ReducedItemlist.get(i).getQty());
                    zoneLogs.setPreqty(getpreQty(ReducedItemlist.get(i).getZoneCode(), ReducedItemlist.get(i).getItemCode()));
                    my_dataBase.zoneLogsDao().insert(zoneLogs);


                }
            }
            checkLocalList();
            Toast.makeText(AddZone.this, "Done", Toast.LENGTH_LONG).show();
        }

    }

    private void checkLocalList() {
        if (listZone != null) {
            if (listZone.size() > 0) {

                for (int i = 0; i < ReducedItemlist.size(); i++) {
                    for (int j = 0; j < listZone.size(); j++) {
                        if (listZone.get(j).getZoneCode().equals(ReducedItemlist.get(i).getZoneCode()) &&
                                listZone.get(j).getItemCode().equals(ReducedItemlist.get(i).getItemCode())) {
                            if (ReducedItemlist.get(i).getQty().equals("0")) {
                                listZone.remove(j);
                                if (adapter != null) adapter.notifyDataSetChanged();
                            } else {
                                Log.e("ReducedItemlistgetQt", ReducedItemlist.get(i).getQty() + "");
                                listZone.get(j).setQty(ReducedItemlist.get(i).getQty());
                                if (adapter != null) adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }

            }

        }

    }

    private void cleardataOfDailog() {
        DDitemcode.setText("");
        DI_zonecode.setText("");
        DIqty.setText("");
        DDzoneEDT.setText("");
        DD_preQTY.setText("");
        DDzoneEDT.requestFocus();
        DI_itemcode.setText("");
    }

    private void authenticDailog(int enterflage) {
        authenticationdialog = new Dialog(AddZone.this);
        authenticationdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        authenticationdialog.setCancelable(false);
        authenticationdialog.setContentView(R.layout.authentication);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(authenticationdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        authenticationdialog.getWindow().setAttributes(lp);

        Button button = authenticationdialog.findViewById(R.id.authentic);
        TextView cancelbutton = authenticationdialog.findViewById(R.id.cancel2);
        UsNa = authenticationdialog.findViewById(R.id.username);
        UsNa.requestFocus();

        EditText pass = authenticationdialog.findViewById(R.id.pass);
        pass.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UsNa.getText().toString().trim().equals(userPermissions.getUserNO()) && pass.getText().toString().trim().equals(userPermissions.getUserPassword())

                        || checkIfUserWhoLoginIsMaster(AddZone.this, UsNa.getText().toString().trim(), pass.getText().toString().trim())) {
                    if (enterflage == 1)
                        openDeleteZoneDailog();
                    else
                        openDeleteitemDailog();


                } else {

                    if (!UsNa.getText().toString().trim().equals(userPermissions.getUserNO())) {
                        UsNa.setError(getResources().getString(R.string.invalid_username));

                    } else {
                        pass.setError(getResources().getString(R.string.invalid_password));
                    }
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

    private void authenticDailog2(int enterflage) {
        authenticationdialog = new Dialog(AddZone.this);
        authenticationdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        authenticationdialog.setCancelable(false);
        authenticationdialog.setContentView(R.layout.authentication);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(authenticationdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        authenticationdialog.getWindow().setAttributes(lp);

        Button button = authenticationdialog.findViewById(R.id.authentic);
        TextView cancelbutton = authenticationdialog.findViewById(R.id.cancel2);
        UsNa = authenticationdialog.findViewById(R.id.username);
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

                        if (enterflage == 4) {
                            authenticationdialog.dismiss();
                            saveData();
                        } else if (enterflage == 1) {
                            openDeleteZoneDailog();
                            authenticationdialog.dismiss();
                        } else {
                            openDeleteitemDailog();
                            authenticationdialog.dismiss();
                        }


                    } else {

                        generalMethod.showSweetDialog(AddZone.this, 3, getResources().getString(R.string.Permission), "");

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


    private void searchZonecodeDailog(List<String> Zonelist) {
        searchdialog = new Dialog(AddZone.this);
        searchdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchdialog.setCancelable(true);
        searchdialog.setContentView(R.layout.dialog_zone_search);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(searchdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        searchdialog.getWindow().setAttributes(lp);
        Button button = searchdialog.findViewById(R.id.btn_send);
        button.setVisibility(View.GONE);
        listView = searchdialog.findViewById(R.id.listViewEngineering);
        ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(AddZone.this, Zonelist);
        listView.setAdapter(adapter);
        List<String> list = new ArrayList<>();

        EditText editText = searchdialog.findViewById(R.id.searchZoneText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    list.clear();
                    for (int i = 0; i < Zonelist.size(); i++)
                        if (Zonelist.get(i).toUpperCase().contains(editable.toString().trim().toUpperCase()))
                            list.add(Zonelist.get(i));

                    ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(AddZone.this, list);
                    listView.setAdapter(adapter);
                } else {
                    ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(AddZone.this, Zonelist);
                    listView.setAdapter(adapter);
                }
            }
        });


        searchdialog.show();

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

    private void ChecksavePermissition() {


        if (userPermissions != null) {
            if (userPermissions.getMasterUser().equals("0")) {
                if (userPermissions.getAddZone_Save().equals("1"))
                    AddZonesave.setEnabled(true);
                else
                    AddZonesave.setEnabled(false);
            } else
                AddZonesave.setEnabled(true);


        }
    }

    private void CheckDeletePermissition() {
        if (userPermissions != null) {
            if (userPermissions.getMasterUser().equals("0")) {
                if (userPermissions.getAddZone_LocalDelete().equals("1")) {
                    delete.setEnabled(true);

                } else
                    delete.setEnabled(false);
            } else {
                delete.setEnabled(true);

            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (zoneinfo != null && zoneinfo.isShowing()) {
            zoneinfo.cancel();
        }
    }

    void CalculateTotalandCount() {


try {


    int zoncount = 0, zonsqty = 0, allzonsqty = 0;
    for (int i = 0; i < listZone.size(); i++)
        allzonsqty += Integer.parseInt(listZone.get(i).getQty());

    AllZonestotalQty.setText(allzonsqty + "");


//
    DBlistZoneS = my_dataBase.zoneDao().getAllZonesUnposted();
    Set<String> set = new HashSet<String>();
    Log.e("DBlistZoneSize", DBlistZoneS.size() + "");
    for (int i = 0; i < DBlistZoneS.size(); i++) {
        set.add(DBlistZoneS.get(i).getZoneCode());
    }

    Zonescount.setText(set.size() + "");


    for (int i = 0; i < DBlistZoneS.size(); i++)
        zonsqty += Integer.parseInt(DBlistZoneS.get(i).getQty());
    ZonestotalQty.setText(zonsqty + "");
}catch (Exception e){
    Log.e("Exception2==",e.getMessage());
}
    }
public  void showalertdaiog(Context context,String Msg){
    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)

            .setContentText(Msg)
            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    sweetAlertDialog.dismiss();
                }
            }).show();

}
@Override
protected void onResume() {
        super.onResume();
        mode = (AudioManager) AddZone.this.getSystemService(Context.AUDIO_SERVICE);

        }



    private void New_validateItemKind(String itemNo) {
        validateKind = true;
        importData.linkbarcode=itemNo.trim();
      //   importData.getKindItem(itemNo);
    NewZonsData newZonsData= my_dataBase.newZonsDataDao().getItemInfo(itemNo.trim());


    if(newZonsData==null)itemKintText.setText("NOTEXIST");
    else{

        String ZONETYPE= my_dataBase.zonsDataDao().getzonetype(newZonsData.getZONENO());
        itemKintText.setText(ZONETYPE);
        }
    }

    private void getStors() {

        importData.getStore(4);
    }
    private void fillSp() {

        //

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, FromArray);


        store_spinner.setAdapter(adapter);

        store_spinner.setSelection(0);


    }
}



