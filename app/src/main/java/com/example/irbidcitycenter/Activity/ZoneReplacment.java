package com.example.irbidcitycenter.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.Adapters.ZoneAdapter;
import com.example.irbidcitycenter.Adapters.ZonePerAdapterr;

import com.example.irbidcitycenter.Adapters.ZoneSearchDBAdapter;
import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.UserPermissions;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.Models.ZoneRepLogs;
import com.example.irbidcitycenter.Models.ZoneReplashmentModel;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.irbidcitycenter.Activity.Login.userPermissions;
import static com.example.irbidcitycenter.Activity.MainActivity.FILE_NAME;
import static com.example.irbidcitycenter.Activity.MainActivity.KEY_LANG;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;
import static com.example.irbidcitycenter.ImportData.itemdetalis;
import static com.example.irbidcitycenter.ImportData.listAllZone;
import static com.example.irbidcitycenter.ImportData.listQtyZone;

public class ZoneReplacment extends AppCompatActivity {
    public static EditText fromzone, tozone, RZ_itemcode;
    TextView FromZoneName, ToZoneName;
    public static int fromZoneRepActivity = 1;
    public static TextView ZR_respon;
    public RoomAllData my_dataBase;
    RecyclerView zonelistview;
    public int localitempostion;
    private int ind;
    ZonePerAdapterr zoneRepAdapter;
    GeneralMethod generalMethod;
    ImportData importData;
    Button ZR_nextZone, ZR_nexttoZone, ZR_back, ZR_Delete, ZR_save;
    List<ZoneReplashmentModel> DBZoneReps = new ArrayList<>();
    List<ZoneReplashmentModel> LocalZoneReps = new ArrayList<>();
    ListView listView;
    public static TextView ZonRepdatarespon;
    List<String> DB_zone;
    //reduce
    List<String> zones;
    List<String> zonelist = new ArrayList<>();
    ;
    List<ZoneReplashmentModel> zones2;
    public List<ZoneReplashmentModel> deletedZonsList = new ArrayList<>();
    Dialog authenticationdialog;
    EditText UsNa;
    public static Dialog searchdialog;
    public static EditText ZR_DZzonecode;
    public static TextView ZR_DZqtyshow, ZR_DZzonebarecodehow;
    public List<ZoneReplashmentModel> zonescopylist = new ArrayList<>();
    public List<String> items;
    ExportData exportData;
    public String UserNo;
    public static EditText ZRDI_itemcode, ZRDI_ZONEcode;
    public static TextView ZRDI_itemcodeshow,
            ZRDI_qtyshow, ZRDI_zoneshow, ZRDI_preQTY;

    public List<ZoneReplashmentModel> ReducedItemlist = new ArrayList<>();
    public long sumOfQTY;
    public int pos_item_inreducelist;
    public static ArrayList<ZoneModel> ZR_listZone;
    public static ZoneAdapter adapter;
    List<com.example.irbidcitycenter.Models.appSettings> appSettings;
    private String deviceId = "";

    public static TextView ZR_itemkind;
    private String tozonetype;
    private String fromzonetype;
    private Animation animation;
    private String itemtype;
    private String Tozonetype = "", Fzonetype = "1";

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_zone_replacment);
        init();
        //ChecksavePermissition();

        fromzone.requestFocus();
        UserNo = my_dataBase.settingDao().getUserNo();
        Log.e(" UserNo==", UserNo + "k");
//my_dataBase.zoneReplashmentDao().deleteAll();
        RZ_itemcode.setEnabled(false);
        tozone.setEnabled(false);
        ZR_nexttoZone.setEnabled(false);

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

    TextView.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (i == KeyEvent.KEYCODE_BACK) {
                onBackPressed();
            }
            if (i != KeyEvent.KEYCODE_ENTER) {
                {
                    Log.e("log", "log");
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP)

                        switch (view.getId()) {
                            case R.id.fromzoneedt:
                                if (!fromzone.getText().toString().equals("")) {
                                    if (zoneExists(fromzone.getText().toString())) {
                                        Fzonetype = itemtype;
                                        Log.e("fromzone", "fromzone");
                                        fromzone.setEnabled(false);
                                        tozone.setEnabled(true);
                                        tozone.requestFocus();
                                    } else {
                                        fromzone.setError("Invalid");
                                        fromzone.setText("");
                                    }

                                } else
                                    fromzone.requestFocus();
                                break;

                            case R.id.tozoneedt:
                                if (!tozone.getText().toString().equals("")) {
                                    if (!tozone.getText().toString().equals(fromzone.getText().toString())) {

                                        if (zoneExists(tozone.getText().toString())) {

                                            Tozonetype = itemtype;
                                            Log.e("fromzonetype", fromzonetype + "  " + Tozonetype);
                                            if (Fzonetype.equals(Tozonetype)) {
                                                RZ_itemcode.setEnabled(true);
                                                RZ_itemcode.requestFocus();
                                                tozone.setEnabled(false);

                                            } else {
                                                generalMethod.showSweetDialog(ZoneReplacment.this, 3, "", getResources().getString(R.string.zonetype));

                                                tozone.setText("");
                                                tozone.requestFocus();

                                            }


                                        } else {
                                            tozone.setError("Invalid");
                                            tozone.setText("");
                                        }

                                    } else {
                                        tozone.setError("Same Zone");
                                        tozone.setText("");
                                        tozone.requestFocus();
                                    }

                                } else
                                    tozone.requestFocus();
                                break;


                            case R.id.ZR_itemcodeedt:
                                if (!RZ_itemcode.getText().toString().equals("")) {
                                    ZR_nexttoZone.setEnabled(true);
                                    ZR_nextZone.setEnabled(true);
                                    if (RZ_itemcode.getText().toString().length() <= 15) {

                                        if (LocalZoneReps.size() > 0) {
                                            if (ItemExistsLocal()) {


                                                if (Long.parseLong(LocalZoneReps.get(localitempostion).getRecQty()) > getQTYOFItem()) {
                                                    Log.e("case1", "case1");
                                                    long x = Long.parseLong(LocalZoneReps.get(localitempostion).getQty()) + 1;
                                                    LocalZoneReps.get(localitempostion).setQty(x + "");
                                                    my_dataBase.zoneReplashmentDao().updateqtyReplashment(LocalZoneReps.get(localitempostion).getFromZone(), LocalZoneReps.get(localitempostion).getToZone(), LocalZoneReps.get(localitempostion).getItemcode(), x + "");
                                                    filladapter();
                                                    RZ_itemcode.setText("");
                                                    RZ_itemcode.requestFocus();
                                                } else {
                                                    Log.e("case2", "case2");
                                                    showSweetDialog(ZoneReplacment.this, 3, "", getResources().getString(R.string.notvaildqty2) + " " + fromzone.getText().toString() + " " + getResources().getString(R.string.msg));
                                                    RZ_itemcode.setText("");
                                                    RZ_itemcode.requestFocus();
                                                }

                                            } else {

                                                ZoneReplashmentModel replashmentModel = my_dataBase.zoneReplashmentDao().getReplashment(fromzone.getText().toString().trim(), tozone.getText().toString().trim(), RZ_itemcode.getText().toString().trim());
                                                if (replashmentModel != null) {
                                                    if (Long.parseLong(replashmentModel.getRecQty()) > getQTYOFItem()) {
                                                        Log.e("case3", "case3");
                                                        long x = Long.parseLong(replashmentModel.getQty()) + 1;
                                                        replashmentModel.setQty(x + "");
                                                        my_dataBase.zoneReplashmentDao().updateqtyReplashment(fromzone.getText().toString().trim(), tozone.getText().toString().trim(), RZ_itemcode.getText().toString().trim(), x + "");
                                                        LocalZoneReps.add(replashmentModel);
                                                        filladapter();
                                                        RZ_itemcode.setText("");
                                                        RZ_itemcode.requestFocus();
                                                    } else {
                                                        Log.e("case4", "case4");
                                                        showSweetDialog(ZoneReplacment.this, 3, "", getResources().getString(R.string.notvaildqty2) + " " + fromzone.getText().toString() + " " + getResources().getString(R.string.msg));
                                                        RZ_itemcode.setText("");
                                                        RZ_itemcode.requestFocus();
                                                    }


                                                } else {


                                                    //if()
                                                    ZoneReplacment.fromZoneRepActivity = 1;
                                                    importData.getQty();
                                                    Log.e("case5", "case5");
                                                }
                                            }
                                            RZ_itemcode.requestFocus();
                                        } else {
                                            ZoneReplashmentModel replashmentModel = my_dataBase.zoneReplashmentDao().getReplashment(fromzone.getText().toString().trim(), tozone.getText().toString().trim(), RZ_itemcode.getText().toString().trim());
                                            if (replashmentModel != null) {
                                                Log.e("case6", "case6");
                                                if (Long.parseLong(replashmentModel.getRecQty()) > getQTYOFItem()) {
                                                    long x = Long.parseLong(replashmentModel.getQty()) + 1;
                                                    replashmentModel.setQty(x + "");
                                                    my_dataBase.zoneReplashmentDao().updateqtyReplashment(fromzone.getText().toString().trim(), tozone.getText().toString().trim(), RZ_itemcode.getText().toString().trim(), x + "");
                                                    LocalZoneReps.add(replashmentModel);
                                                    filladapter();
                                                    RZ_itemcode.setText("");
                                                    RZ_itemcode.requestFocus();
                                                } else {
                                                    showSweetDialog(ZoneReplacment.this, 3, "", getResources().getString(R.string.notvaildqty2) + " " + fromzone.getText().toString() + " " + getResources().getString(R.string.msg));
                                                    RZ_itemcode.setText("");
                                                    RZ_itemcode.requestFocus();
                                                }
                                            } else {
                                                Log.e("case7", "case7");
                                                ZoneReplacment.fromZoneRepActivity = 1;
                                                importData.getQty();
                                                filladapter();

                                            }
                                        }

                                    } else {
                                        RZ_itemcode.setError("InValid");
                                        RZ_itemcode.setEnabled(true);
                                        RZ_itemcode.setText("");
                                        RZ_itemcode.requestFocus();
                                    }
                                } else
                                    RZ_itemcode.requestFocus();
                                break;

                        }


                }


                return true;
            }
            return false;
        }
    };

    public boolean zoneExists(String zone) {
        boolean f = false;
        for (int x = 0; x < listAllZone.size(); x++)
            if (listAllZone.get(x).getZoneCode().equals(zone)) {
                f = true;
                Log.e("ssss1", "sss2");
                itemtype = listAllZone.get(x).getZONETYPE();
                break;
            }
        return f;


    }

    public boolean ItemExistsLocal() {
        boolean flage = false;
        for (int x = 0; x < LocalZoneReps.size(); x++) {
            Log.e("locallist", LocalZoneReps.get(x).getItemcode());
            if (LocalZoneReps.get(x).getFromZone().contains(fromzone.getText().toString().trim())
                    && LocalZoneReps.get(x).getToZone().equals(tozone.getText().toString().trim())
                    && LocalZoneReps.get(x).getItemcode().equals(RZ_itemcode.getText().toString().trim())) {
                flage = true;
                localitempostion = x;
                break;
            } else {
                flage = false;
            }
        }

        return flage;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.ZR_nextZone:
                    animation = AnimationUtils.loadAnimation(ZoneReplacment.this, R.anim.modal_in);
                    ZR_nextZone.startAnimation(animation);
                    LocalZoneReps.clear();
                    filladapter();
                    fromzone.setEnabled(true);
                    fromzone.setText("");
                    tozone.setText("");
                    fromzone.requestFocus();
                    RZ_itemcode.setText("");

                    break;
                case R.id.ZR_nextToZone:
                    animation = AnimationUtils.loadAnimation(ZoneReplacment.this, R.anim.modal_in);
                    ZR_nexttoZone.startAnimation(animation);
                    tozone.setEnabled(true);
                    tozone.setText("");
                    tozone.requestFocus();
                    RZ_itemcode.setText("");

                    break;
                case R.id.ZR_back:
                    animation = AnimationUtils.loadAnimation(ZoneReplacment.this, R.anim.modal_in);
                    ZR_back.startAnimation(animation);
                    new SweetAlertDialog(ZoneReplacment.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.messageExit))
                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    if (LocalZoneReps.size() > 0) {

                                        new SweetAlertDialog(ZoneReplacment.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText(getResources().getString(R.string.confirm_title))
                                                .setContentText(getResources().getString(R.string.messageExit2))
                                                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {


                                                        LocalZoneReps.clear();
                                                        filladapter();
                                                        sweetAlertDialog.dismissWithAnimation();
                                                        finish();
                                                    }
                                                })
                                                .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        sweetAlertDialog.dismiss();
                                                    }
                                                }).show();
                                    } else {
                                        sweetAlertDialog.dismiss();
                                        finish();
                                    }
                                }
                            }).setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
                    break;
                case R.id.ZR_delete:
                    if (userPermissions == null) getUsernameAndpass();
                    animation = AnimationUtils.loadAnimation(ZoneReplacment.this, R.anim.modal_in);
                    ZR_Delete.startAnimation(animation);
                    if (userPermissions.getMasterUser().equals("0")) {
                        if (userPermissions.getZoneRep_LocalDelete().equals("1"))
                            OpenDeleteDailog();
                        else {

                            new SweetAlertDialog(ZoneReplacment.this, SweetAlertDialog.WARNING_TYPE)
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
                    break;
                case R.id.ZR_save:
                    if (userPermissions == null) getUsernameAndpass();
                    animation = AnimationUtils.loadAnimation(ZoneReplacment.this, R.anim.modal_in);
                    ZR_save.startAnimation(animation);

                    if (userPermissions.getMasterUser().equals("0")) {
                        if (userPermissions.getZoneRep_Save().equals("1")) {
                            if (LocalZoneReps.size() > 0) exportdata();
                            else
                                generalMethod.showSweetDialog(ZoneReplacment.this, 3, getResources().getString(R.string.warning), getResources().getString(R.string.fillYourList));


                            ZR_nextZone.setEnabled(false);
                            ZR_nexttoZone.setEnabled(false);
                            tozone.setEnabled(false);
                            RZ_itemcode.setEnabled(false);
                        } else {
                            generalMethod.showSweetDialog(ZoneReplacment.this, 3, getResources().getString(R.string.warning), getResources().getString(R.string.savePermission));

                        }
                    } else {
                        if (LocalZoneReps.size() > 0) exportdata();
                        else
                            generalMethod.showSweetDialog(ZoneReplacment.this, 3, getResources().getString(R.string.warning), getResources().getString(R.string.fillYourList));


                        ZR_nextZone.setEnabled(false);
                        ZR_nexttoZone.setEnabled(false);
                        tozone.setEnabled(false);
                        RZ_itemcode.setEnabled(false);
                    }
                    break;
            }

        }
    };

    public void exportdata() {
        Log.e("exportdata", " exportdata");
        List<ZoneReplashmentModel> models = my_dataBase.zoneReplashmentDao().getAllZonesUnposted();


        for (int i = 0; i < models.size(); i++)
            if (models.get(i).getDeviceId() == null) models.get(i).setDeviceId(deviceId);

        Log.e("models", models.size() + "");
        MainActivity.activityflage = 0;
        exportData.exportZoneRepList(models);


    }

    EditText.OnEditorActionListener onEditAction = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                    || i == EditorInfo.IME_NULL) {
                switch (textView.getId()) {
                    case R.id.fromzoneedt:
                        if (!fromzone.getText().toString().equals("")) {
                            if (zoneExists(fromzone.getText().toString())) {
                                Fzonetype = itemtype;
                                Log.e("fromzone", "fromzone");
                                fromzone.setEnabled(false);
                                tozone.setEnabled(true);
                                tozone.requestFocus();
                            } else {
                                fromzone.setError("Invalid");
                                fromzone.setText("");
                            }

                        } else
                            fromzone.requestFocus();
                        break;

                    case R.id.tozoneedt:
                        if (!tozone.getText().toString().equals("")) {
                            if (!tozone.getText().toString().equals(fromzone.getText().toString())) {

                                if (zoneExists(tozone.getText().toString())) {

                                    Tozonetype = itemtype;
                                    Log.e("fromzonetype", fromzonetype + "  " + Tozonetype);
                                    if (Fzonetype.equals(Tozonetype)) {
                                        RZ_itemcode.setEnabled(true);
                                        RZ_itemcode.requestFocus();
                                        tozone.setEnabled(false);

                                    } else {
                                        generalMethod.showSweetDialog(ZoneReplacment.this, 3, "", getResources().getString(R.string.zonetype));

                                        tozone.setText("");
                                        tozone.requestFocus();

                                    }


                                } else {
                                    tozone.setError("Invalid");
                                    tozone.setText("");
                                }

                            } else {
                                tozone.setError("Same Zone");
                                tozone.setText("");
                                tozone.requestFocus();
                            }

                        } else
                            tozone.requestFocus();
                        break;


                    case R.id.ZR_itemcodeedt:
                        if (!RZ_itemcode.getText().toString().equals("")) {
                            ZR_nexttoZone.setEnabled(true);
                            ZR_nextZone.setEnabled(true);
                            if (RZ_itemcode.getText().toString().length() <= 15) {

                                if (LocalZoneReps.size() > 0) {
                                    if (ItemExistsLocal()) {


                                        if (Long.parseLong(LocalZoneReps.get(localitempostion).getRecQty()) > getQTYOFItem()) {
                                            Log.e("case1", "case1");
                                            long x = Long.parseLong(LocalZoneReps.get(localitempostion).getQty()) + 1;
                                            LocalZoneReps.get(localitempostion).setQty(x + "");
                                            my_dataBase.zoneReplashmentDao().updateqtyReplashment(LocalZoneReps.get(localitempostion).getFromZone(), LocalZoneReps.get(localitempostion).getToZone(), LocalZoneReps.get(localitempostion).getItemcode(), x + "");
                                            filladapter();
                                            RZ_itemcode.setText("");
                                            RZ_itemcode.requestFocus();
                                        } else {
                                            Log.e("case2", "case2");
                                            showSweetDialog(ZoneReplacment.this, 3, "", getResources().getString(R.string.notvaildqty2) + " " + fromzone.getText().toString() + " " + getResources().getString(R.string.msg));
                                            RZ_itemcode.setText("");
                                            RZ_itemcode.requestFocus();
                                        }

                                    } else {

                                        ZoneReplashmentModel replashmentModel = my_dataBase.zoneReplashmentDao().getReplashment(fromzone.getText().toString().trim(), tozone.getText().toString().trim(), RZ_itemcode.getText().toString().trim());
                                        if (replashmentModel != null) {
                                            if (Long.parseLong(replashmentModel.getRecQty()) > getQTYOFItem()) {
                                                Log.e("case3", "case3");
                                                long x = Long.parseLong(replashmentModel.getQty()) + 1;
                                                replashmentModel.setQty(x + "");
                                                my_dataBase.zoneReplashmentDao().updateqtyReplashment(fromzone.getText().toString().trim(), tozone.getText().toString().trim(), RZ_itemcode.getText().toString().trim(), x + "");
                                                LocalZoneReps.add(replashmentModel);
                                                filladapter();
                                                RZ_itemcode.setText("");
                                                RZ_itemcode.requestFocus();
                                            } else {
                                                Log.e("case4", "case4");
                                                showSweetDialog(ZoneReplacment.this, 3, "", getResources().getString(R.string.notvaildqty2) + " " + fromzone.getText().toString() + " " + getResources().getString(R.string.msg));
                                                RZ_itemcode.setText("");
                                                RZ_itemcode.requestFocus();
                                            }


                                        } else {


                                            //if()
                                            ZoneReplacment.fromZoneRepActivity = 1;
                                            importData.getQty();
                                            Log.e("case5", "case5");
                                        }
                                    }
                                    RZ_itemcode.requestFocus();
                                } else {
                                    ZoneReplashmentModel replashmentModel = my_dataBase.zoneReplashmentDao().getReplashment(fromzone.getText().toString().trim(), tozone.getText().toString().trim(), RZ_itemcode.getText().toString().trim());
                                    if (replashmentModel != null) {
                                        Log.e("case6", "case6");
                                        if (Long.parseLong(replashmentModel.getRecQty()) > getQTYOFItem()) {
                                            long x = Long.parseLong(replashmentModel.getQty()) + 1;
                                            replashmentModel.setQty(x + "");
                                            my_dataBase.zoneReplashmentDao().updateqtyReplashment(fromzone.getText().toString().trim(), tozone.getText().toString().trim(), RZ_itemcode.getText().toString().trim(), x + "");
                                            LocalZoneReps.add(replashmentModel);
                                            filladapter();
                                            RZ_itemcode.setText("");
                                            RZ_itemcode.requestFocus();
                                        } else {
                                            showSweetDialog(ZoneReplacment.this, 3, "", getResources().getString(R.string.notvaildqty2) + " " + fromzone.getText().toString() + " " + getResources().getString(R.string.msg));
                                            RZ_itemcode.setText("");
                                            RZ_itemcode.requestFocus();
                                        }
                                    } else {
                                        Log.e("case7", "case7");
                                        ZoneReplacment.fromZoneRepActivity = 1;
                                        importData.getQty();
                                        filladapter();

                                    }
                                }

                            } else {
                                RZ_itemcode.setError("InValid");
                                RZ_itemcode.setEnabled(true);
                                RZ_itemcode.setText("");
                                RZ_itemcode.requestFocus();
                            }
                        } else
                            RZ_itemcode.requestFocus();
                        break;
                }


            }

            return true;
        }
    };


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

    private void init() {
        if (userPermissions == null) getUsernameAndpass();
        DBZoneReps.clear();
        LocalZoneReps.clear();
        ZR_itemkind = findViewById(R.id.ZR_itemkind);
        ZonRepdatarespon = findViewById(R.id.ZonRepdatarespon);
        ZR_nextZone = findViewById(R.id.ZR_nextZone);
        ZR_nexttoZone = findViewById(R.id.ZR_nextToZone);
        ZR_back = findViewById(R.id.ZR_back);
        ZR_Delete = findViewById(R.id.ZR_delete);
        ZR_save = findViewById(R.id.ZR_save);
        importData = new ImportData(ZoneReplacment.this);
        exportData = new ExportData(ZoneReplacment.this);
        importData.getAllZones();
        generalMethod = new GeneralMethod(ZoneReplacment.this);
        zonelistview = findViewById(R.id.zonelist);
        my_dataBase = RoomAllData.getInstanceDataBase(ZoneReplacment.this);
        fromzone = findViewById(R.id.fromzoneedt);
        ZR_respon = findViewById(R.id.ZR_respon);
        tozone = findViewById(R.id.tozoneedt);
        RZ_itemcode = findViewById(R.id.ZR_itemcodeedt);
        FromZoneName = findViewById(R.id.fromzoneName);
        ToZoneName = findViewById(R.id.tozoneName);
        fromzone.setOnKeyListener(onKeyListener);
        tozone.setOnKeyListener(onKeyListener);
        RZ_itemcode.setOnKeyListener(onKeyListener);

        fromzone.setOnEditorActionListener(onEditAction);
        tozone.setOnEditorActionListener(onEditAction);
        RZ_itemcode.setOnEditorActionListener(onEditAction);


        ZR_nextZone.setOnClickListener(onClickListener);
        ZR_nexttoZone.setOnClickListener(onClickListener);
        ZR_Delete.setOnClickListener(onClickListener);
        ZR_back.setOnClickListener(onClickListener);
        ZR_save.setOnClickListener(onClickListener);
        try {
            appSettings = my_dataBase.settingDao().getallsetting();
        } catch (Exception e) {
        }

        if (appSettings.size() != 0) {
            deviceId = appSettings.get(0).getDeviceId();
            Log.e("appSettings", "+" + deviceId);

        }


     /*   ZR_itemkind.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {
                if(   ZR_itemkind.getText().toString().equals("ITEMTYPE")){

                    getzonetype();
try {
    if( itemdetalis.get(0).getZONETYPE().equals(fromzonetype )&&
            itemdetalis.get(0).getZONETYPE().equals(tozonetype )) {
        Log.e("msg", "msg");
        filldata();
    }
    else{
        generalMethod.showSweetDialog(ZoneReplacment.this,3,"",getResources().getString(R.string.zonetype));

    }
}catch (Exception e)
{
    Log.e("Exception==",e.getMessage());
}
                           RZ_itemcode.setText("");
                            RZ_itemcode.requestFocus();

                }
                else if(   ZR_itemkind.getText().toString().equals("NetworkError"))
                {
                    generalMethod.showSweetDialog(ZoneReplacment.this,3,"No Internet Connection","");

                }
                else {
                    generalMethod.showSweetDialog(ZoneReplacment.this,3,getResources().getString(R.string.existsBARCODE),"");

                }
            }




            }
        });*/


        ZonRepdatarespon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() != 0) {

                    if (ZonRepdatarespon.getText().toString().equals("exported")) {
                        savedata("1");
                        showSweetDialog(ZoneReplacment.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                        LocalZoneReps.clear();
                        filladapter();
                        fromzone.setText("");
                        tozone.setText("");
                        RZ_itemcode.setText("");
                        fromzone.setEnabled(true);
                        fromzone.requestFocus();
                    } else {

                        LocalZoneReps.clear();
                        filladapter();
                        fromzone.setText("");
                        tozone.setText("");
                        RZ_itemcode.setText("");

                        fromzone.setEnabled(true);
                        fromzone.requestFocus();
                    }
                }


            }
        });
        ZR_respon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() != 0) {
                    Log.e("afterTextChanged", ZR_respon.getText().toString());
                    if (ZR_respon.getText().toString().equals("QTY")) {
                        if (Long.parseLong(listQtyZone.get(0).getQty()) > 0) {
                            {
                                try {
                                    //   importData.getKindItem2(RZ_itemcode.getText().toString().trim());
                                    filldata();

                                } catch (Exception e) {
                                    Log.e("Exception", e.getMessage());
                                }

                                RZ_itemcode.setText("");
                                RZ_itemcode.requestFocus();
                            }
                        } else {

                            Toast.makeText(ZoneReplacment.this, getResources().getString(R.string.notvaildqty), Toast.LENGTH_SHORT).show();
                            RZ_itemcode.setText("");
                            RZ_itemcode.requestFocus();
                        }


                    } else {
                        showSweetDialog(ZoneReplacment.this, 3, "", getResources().getString(R.string.existsBARCODE));


                        RZ_itemcode.setText("");
                        RZ_itemcode.requestFocus();

                    }
                }
            }
        });
    }

    private void getzonetype() {


        for (int x = 0; x < listAllZone.size(); x++)
            if (listAllZone.get(x).getZoneCode().equals(fromzone.getText().toString().trim())) {
                fromzonetype = listAllZone.get(x).getZONETYPE();
                Log.e(" fromzonetype", fromzonetype);
                break;
            }

        for (int x = 0; x < listAllZone.size(); x++)
            if (listAllZone.get(x).getZoneCode().equals(tozone.getText().toString().trim())) {
                tozonetype = listAllZone.get(x).getZONETYPE();
                Log.e(" tozonetype", tozonetype);
                break;
            }

    }

    private void savedata(String s) {
        my_dataBase.zoneReplashmentDao().setposted();
    }

    private void filldata() {

        if (!RZ_itemcode.getText().equals("")) {
            if (!ItemExistsLocal()) {
                if (RZ_itemcode.getText().toString().length() <= 15) {
                    ZoneReplashmentModel replashmentModel = new ZoneReplashmentModel();
                    replashmentModel.setDate(generalMethod.getCurentTimeDate(1));
                    replashmentModel.setFromZone(fromzone.getText().toString().trim());
                    replashmentModel.setToZone(tozone.getText().toString().trim());
                    replashmentModel.setItemcode(RZ_itemcode.getText().toString().trim());
                    replashmentModel.setTime(generalMethod.getCurentTimeDate(2));
                    replashmentModel.setRecQty(listQtyZone.get(0).getQty());
                    replashmentModel.setIsPosted("0");
                    replashmentModel.setUserNO(UserNo);
                    replashmentModel.setQty("1");
                    replashmentModel.setDeviceId(deviceId);
                    LocalZoneReps.add(replashmentModel);
                    saveRaw(replashmentModel);
                    filladapter();
                } else {
                    RZ_itemcode.setError("InValid");
                    RZ_itemcode.setEnabled(true);
                    RZ_itemcode.setText("");
                    RZ_itemcode.requestFocus();
                }
            } else {
                RZ_itemcode.setEnabled(true);
                RZ_itemcode.setText("");
                RZ_itemcode.requestFocus();
            }
        } else {
            RZ_itemcode.requestFocus();
        }

    }

    private void saveRaw(ZoneReplashmentModel replashmentModel) {
        my_dataBase.zoneReplashmentDao().insert(replashmentModel);
    }

    private void filladapter() {
        zonelistview.setLayoutManager(new LinearLayoutManager(ZoneReplacment.this));

        zoneRepAdapter = new ZonePerAdapterr(ZoneReplacment.this, LocalZoneReps);
        zonelistview.setAdapter(zoneRepAdapter);
    }

    private void OpenDeleteDailog() {


        final Dialog dialog = new Dialog(ZoneReplacment.this);
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
                                                  Toast.makeText(ZoneReplacment.this, getString(R.string.noData), Toast.LENGTH_SHORT).show();
                                          }
                                      }
        );

        dialog.findViewById(R.id.dialogcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    private void OpenDeleteDailog2() {


        final Dialog dialog = new Dialog(ZoneReplacment.this);
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


                                              authenticDailog2(2);

                                          }
                                      }
        );

        dialog.findViewById(R.id.dialogcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    private void authenticDailog(int enterflage) {
        authenticationdialog = new Dialog(ZoneReplacment.this);
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
                if (UsNa.getText().toString().trim().equals(userPermissions.getUserNO()) && pass.getText().toString().trim().equals(userPermissions.getUserPassword())) {
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
        authenticationdialog = new Dialog(ZoneReplacment.this);
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
                        if (enterflage == 1) {
                            openDeleteZoneDailog();
                            authenticationdialog.dismiss();
                        } else {
                            openDeleteitemDailog();
                            authenticationdialog.dismiss();
                        }

                    } else {
                        generalMethod.showSweetDialog(ZoneReplacment.this, 3, getResources().getString(R.string.Permission), "");

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

    private void openDeleteZoneDailog() {

        Log.e("zone: ", zones.size() + "");
        final Dialog dialog = new Dialog(ZoneReplacment.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.zr_deletezone);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);

        ZR_DZzonecode = dialog.findViewById(R.id.ZR_DZzonecode);
        ZR_DZqtyshow = dialog.findViewById(R.id.ZR_DZqtyshow);

        zones = my_dataBase.zoneReplashmentDao().getZonesUnposted();
        TextView ZR_DZsearch = dialog.findViewById(R.id.ZR_DZsearch);
        Button ZR_DZsavebutton = dialog.findViewById(R.id.ZR_DZsavebutton);
        Button ZR_DZcancelbutton = dialog.findViewById(R.id.ZR_DZcancelbutton);
        Button ZR_DZdeltebutton = dialog.findViewById(R.id.ZR_DZdeltebutton);
        ZR_DZzonebarecodehow = dialog.findViewById(R.id.ZR_DZzonebarecodehow);

        dialog.findViewById(R.id.BACK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                authenticationdialog.dismiss();
            }
        });
        ZR_DZzonecode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != KeyEvent.KEYCODE_ENTER) {


                    {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            if (ZR_DZzonecode.getText().toString().equals(""))
                                ZR_DZzonecode.requestFocus();
                            else {

                                if (zones.contains(ZR_DZzonecode.getText().toString().trim())) {
                                    //FILL DATA OF SELECT ZONE


                                    try {
                                        //set zone barecode
                                        ZR_DZzonebarecodehow.setText(ZR_DZzonecode.getText().toString());

                                        //set qty of zone
                                        long sumqty = my_dataBase.zoneReplashmentDao().GetQtyOfZone(ZR_DZzonecode.getText().toString());
                                        ZR_DZqtyshow.setText(sumqty + "");


                                    } catch (Exception e) {

                                    }
                                } else {
                                    Toast.makeText(ZoneReplacment.this, getString(R.string.noData), Toast.LENGTH_SHORT).show();
                                    ZR_DZzonecode.setText("");
                                    ZR_DZzonebarecodehow.setText("");
                                    ZR_DZqtyshow.setText("");
                                }
                            }
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        ZR_DZsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    DB_zone.clear();
           /*     for(int i=0;i<  zones.size();i++) {

                        if (!DB_zone.contains(zones.get(i).getToZone()))
                            DB_zone.add(zones.get(i).getToZone());
                    }*/


                //   zones.clear();

                if (zones.size() != 0) {
                    AddZone.flage3 = 9;

                    searchZonecodeDailog(zones);
                } else
                    Toast.makeText(ZoneReplacment.this, getString(R.string.noData), Toast.LENGTH_SHORT).show();


            }
        });

        ZR_DZdeltebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ZR_DZzonecode.getText().toString().equals(""))
                    new SweetAlertDialog(ZoneReplacment.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.deletezone1))
                            .setConfirmButton(getResources().getString(R.string.yes),
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                                            //


                                            {
                                                if (deletedZonsList.size() == 0) {
                                                    deletedZonsList = my_dataBase.zoneReplashmentDao().getzoneRows(ZR_DZzonecode.getText().toString().trim());
                                                } else {
                                                    deletedZonsList.addAll(my_dataBase.zoneReplashmentDao().getzoneRows(ZR_DZzonecode.getText().toString().trim()));

                                                }


                                                zones.remove(ZR_DZzonecode.getText().toString().trim());

                                                ZR_DZzonebarecodehow.setText("");
                                                ZR_DZqtyshow.setText("");


                                                ZR_DZzonecode.setText("");
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


                else ZR_DZzonecode.setError(getResources().getString(R.string.required));

///
            }
        });
        ZR_DZsavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deletedZonsList.size() > 0) {
                    for (int i = 0; i < deletedZonsList.size(); i++) {
                        int c = my_dataBase.zoneReplashmentDao().deletezonedata(deletedZonsList.get(i).getToZone());
                    }

                    for (int i = 0; i < deletedZonsList.size(); i++) {

                        ZoneRepLogs zoneLogs = new ZoneRepLogs();
                        zoneLogs.setZoneCode(deletedZonsList.get(i).getToZone());
                        zoneLogs.setItemCode(deletedZonsList.get(i).getItemcode());
                        zoneLogs.setUserNO(UsNa.getText().toString());
                        zoneLogs.setLogsDATE(generalMethod.getCurentTimeDate(1));
                        zoneLogs.setLogsTime(generalMethod.getCurentTimeDate(2));
                        zoneLogs.setPreqty(deletedZonsList.get(i).getQty());
                        zoneLogs.setNewqty("0");
                        my_dataBase.zoneRepLogsDao().insert(zoneLogs);


                    }
                   /* if (ZR_listZone != null) {
                        Log.e("istZone", "" + ZR_listZone.size());
                        if (ZR_listZone.size() > 0) {
                            Log.e("istZone1", "" + ZR_listZone.get(0).getZoneCode());
                            Log.e("zonebarecode", "" +ZR_DZzonecode.getText().toString().trim());
                            for (int i = 0; i < deletedZonsList.size(); i++) {
                                if (ZR_listZone.get(0).getZoneCode().equals(deletedZonsList.get(i).getToZone())) {

                                    ZR_listZone.clear();
                                    break;
                                }
                                Log.e("here2", "here2");
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                            }
                        }
                    }
                    deletedZonsList.clear();*/

                    Toast.makeText(ZoneReplacment.this, getString(R.string.app_done), Toast.LENGTH_LONG).show();
                    authenticationdialog.dismiss();
                    dialog.dismiss();
                    checkLocalList(1);
                } else {
                    Toast.makeText(ZoneReplacment.this, getString(R.string.noData), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    authenticationdialog.dismiss();

                }
            }


        });
        ZR_DZcancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deletedZonsList.size() > 0) {
                    new SweetAlertDialog(ZoneReplacment.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.returndata))
                            .setConfirmButton(getResources().getString(R.string.yes),
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                                            for (int i = 0; i < deletedZonsList.size(); i++)
                                                my_dataBase.zoneReplashmentDao().insert(deletedZonsList.get(i));

                                            deletedZonsList.clear();
                                            zones.clear();
                                            Toast.makeText(ZoneReplacment.this, getResources().getString(R.string.dataRet), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ZoneReplacment.this, getResources().getString(R.string.NODATA), Toast.LENGTH_SHORT).show();

                }

            }
        });
        dialog.show();
    }

    private void searchZonecodeDailog(List<String> Zonelist) {
        searchdialog = new Dialog(ZoneReplacment.this);
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
        ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(ZoneReplacment.this, Zonelist);
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

                    ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(ZoneReplacment.this, list);
                    listView.setAdapter(adapter);
                } else {
                    ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(ZoneReplacment.this, Zonelist);
                    listView.setAdapter(adapter);
                }
            }
        });


        searchdialog.show();

    }

    public boolean isEx() {
        boolean f = false;
        for (int x = 0; x < zones2.size(); x++)
            if (zones2.get(x).getToZone().equals(ZRDI_ZONEcode.getText().toString().trim())) {
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


            if (zones2.get(x).getToZone().equals(ZRDI_ZONEcode.getText().toString().trim())
                    && zones2.get(x).getItemcode().equals(ZRDI_itemcode.getText().toString().trim())) {

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

    private void openDeleteitemDailog() {


        Log.e(" zones2size", "" + zones2.size());

        final Dialog Deleteitemdialog = new Dialog(ZoneReplacment.this);
        Deleteitemdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Deleteitemdialog.setCancelable(false);
        Deleteitemdialog.setContentView(R.layout.zr_deleteitem);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Deleteitemdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        Deleteitemdialog.getWindow().setAttributes(lp);
        Deleteitemdialog.show();
        items = new ArrayList<>();


        ZRDI_itemcode = Deleteitemdialog.findViewById(R.id.ZRDI_itemcode);
        ZRDI_itemcodeshow = Deleteitemdialog.findViewById(R.id.ZRDI_itemcodeshow);
        ZRDI_qtyshow = Deleteitemdialog.findViewById(R.id.ZRDI_qtyshow);
        ZRDI_ZONEcode = Deleteitemdialog.findViewById(R.id.ZRDI_ZONEcode);
        ZRDI_zoneshow = Deleteitemdialog.findViewById(R.id.ZRDI_zoneshow);
        ZRDI_preQTY = Deleteitemdialog.findViewById(R.id.ZRDI_preQTY);
        Button deletebtn = Deleteitemdialog.findViewById(R.id.ZRDI_deleteitem);

        ZRDI_itemcode.setEnabled(false);
        deletebtn.setEnabled(false);
        ZRDI_preQTY.setText("");
        ZRDI_ZONEcode.requestFocus();
        ZRDI_ZONEcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != KeyEvent.KEYCODE_ENTER) {

                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (!ZRDI_ZONEcode.getText().toString().equals("")) {
                            if (isEx()) {
                                ZRDI_itemcode.setEnabled(true);
                                ZRDI_itemcode.requestFocus();

                            } else {
                                ZRDI_ZONEcode.setError("INvalid");
                                ZRDI_ZONEcode.setText("");
                            }

                        } else {
                            ZRDI_ZONEcode.requestFocus();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        Deleteitemdialog.findViewById(R.id.ZR_DIclose_btn).setOnClickListener(new View.OnClickListener() {
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
        Deleteitemdialog.findViewById(R.id.ZRDI_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ReducedItemlist.size() > 0) {

                    new SweetAlertDialog(ZoneReplacment.this, SweetAlertDialog.WARNING_TYPE)
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


                                            Toast.makeText(ZoneReplacment.this, getResources().getString(R.string.dataRet), Toast.LENGTH_LONG).show();

                                            ZRDI_itemcode.setText("");
                                            ZRDI_itemcodeshow.setText("");
                                            ZRDI_preQTY.setText("");
                                            ZRDI_itemcodeshow.setText("");
                                            ZRDI_ZONEcode.setText("");
                                            ZRDI_zoneshow.setText("");
                                            ZRDI_ZONEcode.setEnabled(true);
                                            ZRDI_ZONEcode.requestFocus();
                                            ZRDI_itemcode.setEnabled(false);
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
                    Toast.makeText(ZoneReplacment.this, getResources().getString(R.string.NODATA), Toast.LENGTH_LONG).show();
                }
                ///


            }
        });

        Deleteitemdialog.findViewById(R.id.ZRDI_zoneSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zonelist.clear();
                for (int i = 0; i < zones2.size(); i++)
                    if (!zonelist.contains(zones2.get(i).getToZone()))
                        zonelist.add(zones2.get(i).getToZone());


                if (zonelist.size() != 0) {
                    AddZone.flage3 = 10;
                    searchZonecodeDailog(zonelist);
                } else
                    Toast.makeText(ZoneReplacment.this, getString(R.string.noData), Toast.LENGTH_LONG).show();


            }
        });


        ZRDI_itemcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i != KeyEvent.KEYCODE_ENTER) {

                    {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            if (!ZRDI_itemcode.getText().toString().equals("")) {
                                if (ismatch()) {
                                    String x = getpreQty(zones2.get(ind).getToZone(), zones2.get(ind).getItemcode());
                                    ZRDI_preQTY.setText(x);


                                    sumOfQTY = Long.parseLong(zones2.get(ind).getQty());
                                    if (sumOfQTY > 1) {
                                        sumOfQTY -= 1;
                                        // oldQTY.setText( sumOfQTY+"" );

                                        ZRDI_itemcodeshow.setText(ZRDI_itemcode.getText().toString());


                                        zones2.get(ind).setQty(sumOfQTY + "");
                                        if (ReducedItemlist.size() > 0) {


                                            if (Exists(zones2.get(ind).getToZone(), zones2.get(ind).getItemcode())) {
                                                Log.e("ReducedItemlistsumOfQTY", sumOfQTY + "");
                                                Log.e("updated", zones2.get(ind).getItemcode());
                                                Log.e("ReducedItemlistQTY", zones2.get(ind).getQty() + "");
                                                Log.e("pos_item_inreducelist", "" + pos_item_inreducelist);
                                                ReducedItemlist.remove(pos_item_inreducelist);
                                                ReducedItemlist.add(zones2.get(ind));
                                                //   ReducedItemlist.get(pos_item_inreducelist).setQty(sumOfQTY+"");

                                            } else {
                                                zones2.get(ind).setQty(sumOfQTY + "");
                                                ReducedItemlist.add(zones2.get(ind));
                                                Log.e("ReducedItemlistQTY", zones2.get(ind).getQty() + "");
                                                Log.e("added", zones2.get(ind).getItemcode());
                                                Log.e("ReducedItemlistsumOfQTY", sumOfQTY + "");


                                            }
                                        } else {
                                            Log.e("elseadded", zones2.get(ind).getItemcode());
                                            Log.e("ReducedItemlistQTY", zones2.get(ind).getQty() + "");
                                            ReducedItemlist.add(zones2.get(ind));
                                        }

                                        //  sumOfQTY -= 1;

                                        ZRDI_qtyshow.setText(sumOfQTY + "");
                                        ZRDI_zoneshow.setText(zones2.get(ind).getToZone());

                                        deletebtn.setEnabled(true);
                                        ZRDI_itemcode.setText("");
                                        ZRDI_itemcode.requestFocus();

                                    }//end of if(sumOfQTY >1)
                                    else {
                                        new SweetAlertDialog(ZoneReplacment.this, SweetAlertDialog.BUTTON_CONFIRM)
                                                .setTitleText(getResources().getString(R.string.confirm_title))
                                                .setContentText(getResources().getString(R.string.delete3))
                                                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        Log.e("ReducedItemlist", ReducedItemlist.size() + "");
                                                        if (ReducedItemlist.size() > 0) {
                                                            if (Exists(zones2.get(ind).getToZone(), zones2.get(ind).getItemcode())) {
                                                                ReducedItemlist.get(pos_item_inreducelist).setQty("0");
                                                                zones2.remove(ind);

                                                            } else {
                                                                Log.e("zones2", "==zones2");

                                                                zones2.get(ind).setQty("0");
                                                                ReducedItemlist.add(zones2.get(ind));
                                                                zones2.remove(ind);
                                                                //   ReducedItemlist.get(pos_item_inreducelist).setQty("0");
                                                            }
                                                        } else {
                                                            Log.e("elzones2", "==zones2");
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

                                        ZRDI_itemcodeshow.setText(ZRDI_itemcode.getText().toString());
                                        ZRDI_qtyshow.setText("1");
                                        ZRDI_ZONEcode.setText(zones2.get(ind).getToZone());
                                        deletebtn.setEnabled(true);
                                        ZRDI_itemcode.setText("");
                                        ZRDI_itemcode.requestFocus();

                                    }
                                } else {
                                    ZRDI_itemcode.setError("Invalid");
                                    ZRDI_itemcode.setText("");
                                }
                            } else
                                ZRDI_itemcode.requestFocus();
                        }
                    }
                    return true;
                }

                return false;
            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ZRDI_itemcodeshow.getText().toString().trim().equals("") && !ZRDI_zoneshow.getText().toString().trim().equals("")) {


                    new SweetAlertDialog(ZoneReplacment.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.deleteitem))
                            .setConfirmButton(getResources().getString(R.string.yes),
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {


                                            try {

                                                if (ReducedItemlist.size() > 0) {

                                                    if (!Exists(zones2.get(ind).getToZone(), zones2.get(ind).getItemcode())) {


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
                    if (ZRDI_itemcode.getText().toString().trim().equals(""))
                        ZRDI_itemcode.setError("required");
                    if (ZRDI_ZONEcode.getText().toString().trim().equals(""))
                        ZRDI_ZONEcode.setError("required");
                }
            }
        });


        Deleteitemdialog.findViewById(R.id.ZRDI_dialogsave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doupdate();
                ReducedItemlist.clear();
                Deleteitemdialog.dismiss();
                authenticationdialog.dismiss();

            }
        });

    }

    private void doupdate() {
        int z = 6, x = 9;
        Log.e("doupdatesize", ReducedItemlist.size() + "");
        if (ReducedItemlist.size() > 0) {
            for (int i = 0; i < ReducedItemlist.size(); i++) {

                if (ReducedItemlist.get(i).getQty().equals("0")) {


                    ZoneRepLogs zoneLogs = new ZoneRepLogs();
                    zoneLogs.setZoneCode(ReducedItemlist.get(i).getToZone());
                    zoneLogs.setItemCode(ReducedItemlist.get(i).getItemcode());
                    zoneLogs.setUserNO(UsNa.getText().toString());
                    zoneLogs.setLogsDATE(generalMethod.getCurentTimeDate(1));
                    zoneLogs.setLogsTime(generalMethod.getCurentTimeDate(2));
                    zoneLogs.setNewqty("0");
                    zoneLogs.setPreqty(getpreQty(ReducedItemlist.get(i).getToZone(), ReducedItemlist.get(i).getItemcode()));
                    my_dataBase.zoneRepLogsDao().insert(zoneLogs);


                    z = my_dataBase.zoneReplashmentDao().deleteITEM(ReducedItemlist.get(i).getToZone(), ReducedItemlist.get(i).getItemcode());
                    Log.e("doupdatez=", z + "");
                } else {
                    x = my_dataBase.zoneReplashmentDao().updateQTYreduced(ReducedItemlist.get(i).getItemcode(), ReducedItemlist.get(i).getQty(), ReducedItemlist.get(i).getToZone());
                    Log.e("doupdateelse=", x + "");


                    ZoneRepLogs zoneLogs = new ZoneRepLogs();
                    zoneLogs.setZoneCode(ReducedItemlist.get(i).getToZone());
                    zoneLogs.setItemCode(ReducedItemlist.get(i).getItemcode());
                    zoneLogs.setUserNO(UsNa.getText().toString());
                    zoneLogs.setLogsDATE(generalMethod.getCurentTimeDate(1));
                    zoneLogs.setLogsTime(generalMethod.getCurentTimeDate(2));
                    zoneLogs.setNewqty(ReducedItemlist.get(i).getQty());
                    zoneLogs.setPreqty(getpreQty(ReducedItemlist.get(i).getToZone(), ReducedItemlist.get(i).getItemcode()));
                    my_dataBase.zoneRepLogsDao().insert(zoneLogs);


                }
            }
            checkLocalList(2);
            Toast.makeText(ZoneReplacment.this, getString(R.string.app_done), Toast.LENGTH_LONG).show();
        }
    }

    private void checkLocalList(int flage) {
        if (flage == 1) {
            if (deletedZonsList != null) {

                if (LocalZoneReps.size() > 0) {
                    Log.e("q111", "===q111");
                    for (int i = 0; i < deletedZonsList.size(); i++) {
                        for (int j = 0; j < LocalZoneReps.size(); j++) {
                            Log.e("q111", "=" + LocalZoneReps.get(j).getToZone().length() + " " + deletedZonsList.get(i).getToZone().length());
                            if (LocalZoneReps.get(j).getToZone().equals(deletedZonsList.get(i).getToZone())) {
                                LocalZoneReps.remove(j);
                                Log.e("q111", LocalZoneReps.size() + "");
                            } else {
                                Log.e("elseq111", LocalZoneReps.size() + "");
                            }
                        }
                    }
                    filladapter();

                }
            }
        } else if (flage == 2) {
            if (LocalZoneReps != null) {
                if (LocalZoneReps.size() > 0) {
                    for (int i = 0; i < ReducedItemlist.size(); i++) {
                        for (int j = 0; j < LocalZoneReps.size(); j++) {

                            if (LocalZoneReps.get(j).getToZone().equals(ReducedItemlist.get(i).getToZone()) &&
                                    LocalZoneReps.get(j).getItemcode().equals(ReducedItemlist.get(i).getItemcode())) {
                                if (ReducedItemlist.get(i).getQty().equals("0")) {
                                    LocalZoneReps.remove(j);

                                } else {
                                    Log.e("ReducedItemlistgetQt", ReducedItemlist.get(i).getQty() + "");
                                    LocalZoneReps.get(j).setQty(ReducedItemlist.get(i).getQty());

                                }
                            }
                        }
                        filladapter();
                    }
                }
            }
        }
    }

    private String getpreQty(String zone, String itemcode) {

        Log.e("getpreQty", zone + "\t" + itemcode);
        Log.e("zonecopysize", zonescopylist.size() + "");
        String z = "";
        for (int i = 0; i < zonescopylist.size(); i++) {
            if (zone.equals(zonescopylist.get(i).getToZone()) && itemcode.equals(zonescopylist.get(i).getItemcode())) {
                Log.e("getpreQty2", zonescopylist.get(i).getToZone() + "\t" + zonescopylist.get(i).getItemcode());
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

    private void getzonefromDB() {
        zones2 = new ArrayList<>();
        zones2.clear();
//                 zonescopylist.clear();
        zones2 = my_dataBase.zoneReplashmentDao().getAllZonesUnposted();

        //zonescopylist.addAll(zones2);
        if (zones2.size() > 0) {
            copylist();
            //    preint();
            //    preint2();

        }
    }

    private void cleardataOfDailog() {
        ZRDI_itemcodeshow.setText("");
        ZRDI_zoneshow.setText("");
        ZRDI_qtyshow.setText("");
        ZRDI_preQTY.setText("");
    }

    public void copylist() {
        zonescopylist.clear();
        List<String> zon = new ArrayList<>();
        List<String> item = new ArrayList<>();
        List<String> qtys = new ArrayList<>();
        for (int i = 0; i < zones2.size(); i++) {
            zon.add(zones2.get(i).getToZone());
            item.add(zones2.get(i).getItemcode());
            qtys.add(zones2.get(i).getQty());
        }

        for (int i = 0; i < zon.size(); i++) {
            ZoneReplashmentModel replashmentModel = new ZoneReplashmentModel();
            replashmentModel.setQty(qtys.get(i));
            replashmentModel.setToZone(zon.get(i));
            replashmentModel.setItemcode(item.get(i));
            zonescopylist.add(replashmentModel);
        }

    }

    private boolean Exists(String zonecode, String itemcode) {
        boolean flage = false;
        for (int i = 0; i < ReducedItemlist.size(); i++) {
            if (ReducedItemlist.get(i).getToZone().equals(zonecode)
                    && ReducedItemlist.get(i).getItemcode().equals(itemcode)) {
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

    long getQTYOFItem() {
        long sum = my_dataBase.zoneReplashmentDao().getQTYofItem(fromzone.getText().toString(), RZ_itemcode.getText().toString());
        return sum;
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
                if (userPermissions.getZoneRep_Save().equals("0"))
                    ZR_save.setEnabled(false);
                else
                    ZR_save.setEnabled(true);
            } else
                ZR_save.setEnabled(true);


        }
    }

    private void CheckdeletePermissition() {

        if (userPermissions != null) {
            if (userPermissions.getMasterUser().equals("0")) {
                if (userPermissions.getZoneRep_LocalDelete().equals("0"))
                    ZR_Delete.setEnabled(false);
                else
                    ZR_Delete.setEnabled(true);
            } else ZR_Delete.setEnabled(true);
        }
    }
}

