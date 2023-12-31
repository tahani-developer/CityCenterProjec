package com.example.irbidcitycenter.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.irbidcitycenter.Adapters.StocktakeAdapterr;
import com.example.irbidcitycenter.Adapters.ZoneSearchDBAdapter;
import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.AllItems;
import com.example.irbidcitycenter.Models.StocktakeLogs;
import com.example.irbidcitycenter.Models.StocktakeModel;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.irbidcitycenter.Activity.MainActivity.FILE_NAME;
import static com.example.irbidcitycenter.Activity.MainActivity.KEY_LANG;
import static com.example.irbidcitycenter.GeneralMethod.checkIfUserWhoLoginIsMaster;
import static com.example.irbidcitycenter.ImportData.zoneinfo;

import static com.example.irbidcitycenter.Activity.AddZone.flage3;
import static com.example.irbidcitycenter.Activity.Login.userPermissions;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;
import static com.example.irbidcitycenter.ImportData.AllImportItemlist;
import static com.example.irbidcitycenter.ImportData.Storelist;
import static com.example.irbidcitycenter.ImportData.hideProgressDialogWithTitle;
import static com.example.irbidcitycenter.ImportData.listAllZone;

public class Stoketake extends AppCompatActivity {
    static AppCompatSpinner StoreSpinner;
    static AppCompatSpinner spinner, spinner2;
    List<String> spinnerArray = new ArrayList<>();
    ImportData importData;
    public static TextView respone;
    public static Button saveButton, reduiceButton, backButton, importButton, nextzoneButton, ST_delete;
    static EditText zonecode;
    EditText itemOcode;
    EditText recQty;
    TextView itemname;
    int position;
    static RoomAllData my_dataBase;
    StocktakeAdapterr stocktakeAdapter;
    GeneralMethod generalMethod;
    public String UserNo;
    EditText UsNa;
    public static EditText ST_ZONEcode;
    public static TextView ST_zonecodeshow;
    public static TextView ST_qtyshow;

    public static List<AllItems> AllstocktakeDBlist = new ArrayList<>();
    public static List<StocktakeModel> stocktakelist = new ArrayList<>();
    public static List<StocktakeModel> DB_stocktakecopy = new ArrayList<>();
    RecyclerView stockListView;
    private Dialog authenticationdialog;
    public static List<StocktakeModel> DB_stocktake = new ArrayList<>();
    ;
    List<String> DB_store;
    List<String> DB_zone;
    List<StocktakeModel> deleted_DBzone;
    int indexDBitem, indexDBZone;

    public static Dialog ST_searchdialog;
    private int zonepostion, localitempostion;
    public TextView ST_close_btn;
    public static TextView ST_zoneSearch2, ST_preQTY, ST_itemcodeshow,
            ST_zoneshow, ST_qtyshows;
    public String deviceId = "";
    public List<StocktakeModel> ST_reducedqtyitemlist = new ArrayList<>();

    public static EditText ST_ZONEcod,
            ST_itemcode;
    private int indexOfReduceditem;

    public static TextView St_Itemrespons, datarespon, total_zoneqty_text, total_scanedqty_text, total_storeqty_text;
    List<com.example.irbidcitycenter.Models.appSettings> appSettings;
    ExportData exportData;

    private Animation animation;
    AudioManager mode ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_stoketake);
        init();
        mode = (AudioManager) Stoketake.this.getSystemService(Context.AUDIO_SERVICE);
        mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        AllstocktakeDBlist.clear();


                AllstocktakeDBlist.addAll(my_dataBase.itemDao().getAll2());


        if (AllstocktakeDBlist.size() == 0) {
            new SweetAlertDialog(Stoketake.this, SweetAlertDialog.BUTTON_CONFIRM)
                    .setTitleText(getResources().getString(R.string.confirm_title))
                    .setContentText(getResources().getString(R.string.Msg1))
                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {


                            try {
                                importData.getAllItems(1);
                                sweetAlertDialog.dismiss();
                            } catch (Exception e){
                                Toast.makeText(Stoketake.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                                sweetAlertDialog.dismiss();
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

        zonecode.requestFocus();
        itemOcode.setEnabled(false);
        //my_dataBase.itemDao().deleteall();


        //    AllstocktakeDBlist.addAll(my_dataBase.itemDao().getAll());

        Replacement.actvityflage = 2;
        Storelist.clear();
        Storelist = my_dataBase.storeDao().getall();


        if (Storelist.size() > 0) {
            for (int i = 0; i < Storelist.size(); i++) {
                spinnerArray.add(Storelist.get(i).getSTORENO() + "  " + Storelist.get(i).getSTORENAME());

            }
            fillSpinner();
        } else if (Storelist.size() == 0) getStors();
        UserNo = my_dataBase.settingDao().getUserNo();

        stocktakelist.clear();
        //     itemOcode.setEnabled(false);
        StoreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getStoreSumQty();
                itemOcode.setText("");
                itemname.setText("");
                zonecode.setText("");
                zonecode.setEnabled(true);
                zonecode.requestFocus();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

    private void getStors() {

        Replacement.actvityflage = 2;
        importData.getStore(2);


    }

    TextView.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            Log.e("keyEvent.getAction()", keyEvent.getAction() + "");


            if (i == KeyEvent.KEYCODE_BACK) {


//                Handler h = new Handler(Looper.getMainLooper());
//                h.post(new Runnable() {
//                    public void run() {
//                        animation = AnimationUtils.loadAnimation(Stoketake.this, R.anim.modal_in);
//                        backButton.startAnimation(animation);
//                        new SweetAlertDialog(Stoketake.this, SweetAlertDialog.WARNING_TYPE)
//                                .setTitleText(getResources().getString(R.string.confirm_title))
//                                .setContentText(getResources().getString(R.string.messageExit))
//                                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
//                                    @Override
//                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                        if (stocktakelist.size() > 0) {
//
//                                            new SweetAlertDialog(Stoketake.this, SweetAlertDialog.WARNING_TYPE)
//                                                    .setTitleText(getResources().getString(R.string.confirm_title))
//                                                    .setContentText(getResources().getString(R.string.messageExit2))
//                                                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
//                                                        @Override
//                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//
//
//                                                            stocktakelist.clear();
//                                                            stocktakeAdapter.notifyDataSetChanged();
//                                                            sweetAlertDialog.dismissWithAnimation();
//                                                            finish();
//                                                        }
//                                                    })
//                                                    .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
//                                                        @Override
//                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                                            sweetAlertDialog.dismiss();
//                                                        }
//                                                    }).show();
//                                        } else {
//                                            sweetAlertDialog.dismiss();
//                                            finish();
//                                        }
//                                    }
//                                }).setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                sweetAlertDialog.dismiss();
//                            }
//                        }).show();
//                    }
//                });
            }

          else  if (i != KeyEvent.KEYCODE_ENTER) {

                {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP)
                        switch (view.getId()) {
                            case R.id.ST_zoneedt:
                                Log.e("ST_zoneedt", "ST_zoneedt");
                                if (!zonecode.getText().toString().trim().equals("")) {
//                                 if(zoneExists(zonecode.getText().toString()))
//                                 {
                                    mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                    Log.e("zoneedt", zonecode.getText().toString());
                                    mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                    itemOcode.setEnabled(true);
                                    itemOcode.requestFocus();
                                    getZoneSumQty();
//                                 }
//                                 else{
//                                     zonecode.setError("Invalid");
//                                     zonecode.setText("");
//                                     zonecode.requestFocus();
//                                 }

                                } else {
                                     Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    // Vibrate for 1000 milliseconds
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                    } else {
                                        //deprecated in API 26
                                        v.vibrate(1000);
                                    }
                                    zonecode.requestFocus();
                                    Log.e("zoneedt===", zonecode.getText().toString());
                                }

                                break;
                            case R.id.ST_itemcodeedt:

                                if (!itemOcode.getText().toString().equals("")) {

                                    if (itemOcode.getText().toString().length() <= 15) {
                                        zonecode.setEnabled(false);
                                        mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                        itemname.setText("");
                                        stocktakelist.clear();
                                        Log.e("itemcode", itemOcode.getText().toString());
                                        boolean existsflage = false;
                                        //local list check
                                        if (stocktakelist.size() > 0) {


                                            if (ItemExistsLocal()) {
                                                mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                                long sum = Long.parseLong(stocktakelist.get(localitempostion).getQty()) + 1;
                                                stocktakelist.get(localitempostion).setQty(sum + "");
                                                int z = my_dataBase.stocktakeDao().IncreasQty(stocktakelist.get(localitempostion).getQty(), stocktakelist.get(localitempostion).getZone(), stocktakelist.get(localitempostion).getItemOcode(), stocktakelist.get(localitempostion).getStore());
                                                Log.e("z====", "" + z);
                                                existsflage = true;

                                                itemOcode.setText("");
                                                itemOcode.requestFocus();

                                                getItemsSumQty();
                                                getZoneSumQty();
                                                getStoreSumQty();
                                                if (stocktakeAdapter != null)
                                                    stocktakeAdapter.notifyDataSetChanged();
                                                else
                                                    filladapter();
                                                break;
                                            }
                                            //check database
                                            else if (existsflage == false) {


                                                StocktakeModel stocktakeModel = my_dataBase.stocktakeDao().getstockObj(StoreSpinner.getSelectedItem().toString().substring(0, StoreSpinner.getSelectedItem().toString().indexOf(" ")), zonecode.getText().toString().trim(), itemOcode.getText().toString().trim());
                                                if (stocktakeModel != null) {
                                                    mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                                    Log.e("notnull", "notnull");
                                                    long sum = Long.parseLong(stocktakeModel.getQty()) + 1;
                                                    stocktakeModel.setQty(sum + "");
                                                    my_dataBase.stocktakeDao().IncreasQty(stocktakeModel.getQty(), stocktakeModel.getZone(), stocktakeModel.getItemOcode(), stocktakeModel.getStore());
                                                    stocktakelist.add(stocktakeModel);
                                                    stocktakeAdapter.notifyDataSetChanged();

                                                    itemOcode.setText("");
                                                    itemOcode.requestFocus();
                                                    getItemsSumQty();
                                                    getZoneSumQty();
                                                    getStoreSumQty();
                                                }
                                            } else if (ItemExists(itemOcode.getText().toString())) {
                                                mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                                filldata();

                                                itemOcode.setText("");
                                                itemOcode.requestFocus();
                                                break;
                                            } else {
                                                showalertdaiog(Stoketake.this,"InValid Item  "+ itemOcode.getText());
                                             //   showSweetDialog(Stoketake.this, 3, "","InValid Item  "+ itemOcode.getText());
                                                mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                                // Vibrate for 1000 milliseconds
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                    v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                                } else {
                                                    //deprecated in API 26
                                                    v.vibrate(1000);
                                                }
                                                itemOcode.setText("");
                                            }
                                        } else {


                                            Log.e("checkdatabase", "checkdatabase");
                                            StocktakeModel stocktakeModel = my_dataBase.stocktakeDao().getstockObj(StoreSpinner.getSelectedItem().toString().substring(0, StoreSpinner.getSelectedItem().toString().indexOf(" ")), zonecode.getText().toString().trim(), itemOcode.getText().toString().trim());
                                            if (stocktakeModel != null) {
                                                mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                                Log.e("notnull", "notnull");
                                                long sum = Long.parseLong(stocktakeModel.getQty()) + 1;
                                                stocktakeModel.setQty(sum + "");
                                                my_dataBase.stocktakeDao().IncreasQty(stocktakeModel.getQty(), stocktakeModel.getZone(), stocktakeModel.getItemOcode(), stocktakeModel.getStore());
                                                stocktakelist.add(stocktakeModel);
                                                itemname.setText(stocktakeModel.getItemName());
                                                filladapter();
                                                getItemsSumQty();
                                                getZoneSumQty();
                                                getStoreSumQty();
                                                itemOcode.setText("");
                                                itemOcode.requestFocus();

                                            }


                                            //check AllstocktakeDBlist
                                            else if (ItemExists(itemOcode.getText().toString())) {
                                                mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                                filldata();

                                                itemOcode.setText("");

                                                itemOcode.requestFocus();
                                                break;
                                            } else {
                                                showalertdaiog(Stoketake.this,"InValid Item  "+ itemOcode.getText());
                                             //   showSweetDialog(Stoketake.this, 3, "","InValid Item  "+ itemOcode.getText());
                                                mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                                // Vibrate for 1000 milliseconds
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                    v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                                } else {
                                                    //deprecated in API 26
                                                    v.vibrate(1000);
                                                }
                                                itemOcode.setText("");
                                            }
                                        }


                                    } else {
                                        showalertdaiog(Stoketake.this,"InValid Item  ");
                                    //    showSweetDialog(Stoketake.this, 3, "","InValid Item  "+ itemOcode.getText());

                                        mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                        // Vibrate for 1000 milliseconds
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                        } else {
                                            //deprecated in API 26
                                            v.vibrate(1000);
                                        }
                                        itemOcode.setText("");
                                        itemOcode.setEnabled(true);
                                        itemOcode.requestFocus();

                                    }
                                } else
                                {  itemOcode.requestFocus();

                                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    // Vibrate for 1000 milliseconds
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                    } else {
                                        //deprecated in API 26
                                        v.vibrate(1000);
                                    }
                                }
                                break;
                        }
                    return true;
                }
            }
            return false;

        }
    };

    public static void getStoreSumQty() {
        long total_storeqty = my_dataBase.stocktakeDao().getStoreQty(StoreSpinner.getSelectedItem().toString().substring(0, StoreSpinner.getSelectedItem().toString().indexOf(" ")));
        try { //   long  total_storeqty= my_dataBase.stocktakeDao().getStoreQty(StoreSpinner.getSelectedItem().toString());
            total_storeqty_text.setText(total_storeqty + "");
            Log.e("total_storeqty", total_storeqty + "");
        } catch (Exception e) {

        }
    }

    public static void getItemsSumQty() {
        try {
            long total_scanedqty = my_dataBase.stocktakeDao().getItemQty();
            total_scanedqty_text.setText(total_scanedqty + "");
            Log.e("total_storeqty", total_scanedqty + "");
        } catch (Exception e) {

        }
    }

    public static void getZoneSumQty() {
        try {
            Log.e("zonecode.getText==", zonecode.getText().toString().trim() + "");
            long total_zoneqty = my_dataBase.stocktakeDao().getZoneQty(zonecode.getText().toString().trim());
            Log.e("total_zoneqty", total_zoneqty + "");
            total_zoneqty_text.setText(total_zoneqty + "");
        } catch (Exception e) {

        }

    }

    public boolean ItemExistsLocal() {
        boolean flage = false;
        for (int x = 0; x < stocktakelist.size(); x++) {
            Log.e("locallist", stocktakelist.get(x).getItemOcode());
            if (stocktakelist.get(x).getZone().contains(zonecode.getText().toString().trim())
                    && stocktakelist.get(x).getItemOcode().equals(itemOcode.getText().toString().trim())) {
                flage = true;
                localitempostion = x;
                break;
            } else {
                flage = false;
            }
        }

        return flage;
    }


    /*  public  boolean ZoneExists(){
          boolean flage=false;
          for (int x = 0; x < AllstocktakeDBlist.size(); x++) {
              Log.e("zoneedt",AllstocktakeDBlist.get(x).getZone());
              if (AllstocktakeDBlist.get(x).getZone().contains(zonecode.getText().toString().trim()))
              {flage=true;
              zonepostion=x;
              break;
              }
              else {
                  flage=false;
              }
      }

         return flage;
      }*/
    public boolean ItemExists( String itemOcode) {
/*
        boolean flage = false;


        for (int x1 = 0; x1 < AllstocktakeDBlist.size(); x1++) {
            if (AllstocktakeDBlist.get(x1).getItemOcode().equals(itemOcode.getText().toString().trim())) {
                flage = true;
                itemname.setText(AllstocktakeDBlist.get(x1).getItemNameE());
                break;
            } else {
                flage = false;
            }
        }

        return flage;*/
        String item= my_dataBase.itemDao().getitem2( itemOcode);
       if(item!=null)
           return true;
       else
           return false;


    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {

                case R.id.ST_save:
                    if (userPermissions == null) getUsernameAndpass();
                    animation = AnimationUtils.loadAnimation(Stoketake.this, R.anim.modal_in);
                    saveButton.startAnimation(animation);
                    if (stocktakelist.size() > 0) {
                    if (userPermissions.getMasterUser().equals("0")) {
                        if (userPermissions.getStockTake_Save().equals("1")) {

                        saveData();
                        } else {
                           // generalMethod.showSweetDialog(Stoketake.this, 3, getResources().getString(R.string.warning), getResources().getString(R.string.savePermission));
                            new SweetAlertDialog(Stoketake.this, SweetAlertDialog.WARNING_TYPE)
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
                        }
                    } else {
                        saveData();

                    }} else {

                        generalMethod.showSweetDialog(Stoketake.this, 3, getResources().getString(R.string.warning), getResources().getString(R.string.fillYourList));

                    }
                    break;
                case R.id.ST_deletebtn:
                    if (userPermissions == null) getUsernameAndpass();
                    animation = AnimationUtils.loadAnimation(Stoketake.this, R.anim.modal_in);
                    reduiceButton.startAnimation(animation);
                    if (userPermissions.getMasterUser().equals("0")) {
                        if (userPermissions.getStockTake_LocalDelete().equals("1")) {

                            OpenDeleteDailog();
                        } else {

                            new SweetAlertDialog(Stoketake.this, SweetAlertDialog.WARNING_TYPE)
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


                    } else
                        OpenDeleteDailog();
                    break;
                case R.id.ST_nextZone:
                    animation = AnimationUtils.loadAnimation(Stoketake.this, R.anim.modal_in);
                    nextzoneButton.startAnimation(animation);
                    zonecode.setEnabled(true);
                    itemname.setText("");
                    itemOcode.setText("");
                    itemOcode.setEnabled(false);
                    zonecode.setText("");
                    zonecode.requestFocus();
                    total_zoneqty_text.setText("0");
                    break;
                case R.id.ST_cancel:



                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable() {
                        public void run() {
                            animation = AnimationUtils.loadAnimation(Stoketake.this, R.anim.modal_in);
                            backButton.startAnimation(animation);
                            new SweetAlertDialog(Stoketake.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.confirm_title))
                                    .setContentText(getResources().getString(R.string.messageExit))
                                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            if (stocktakelist.size() > 0) {

                                                new SweetAlertDialog(Stoketake.this, SweetAlertDialog.WARNING_TYPE)
                                                        .setTitleText(getResources().getString(R.string.confirm_title))
                                                        .setContentText(getResources().getString(R.string.messageExit2))
                                                        .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {


                                                                stocktakelist.clear();
                                                                stocktakeAdapter.notifyDataSetChanged();
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
                        }
                    });

                    break;
            }
        }
    };

    private void clearAll() {
        zonecode.setText("");
        itemOcode.setText("");
        itemname.setText("");
        recQty.setText("");
        total_zoneqty_text.setText("");
        total_storeqty_text.setText("");
        total_scanedqty_text.setText("");
        zonecode.setEnabled(true);
        zonecode.requestFocus();
    }


    public void exportdata() {
        Log.e("exportdata", " exportdata");
        List<StocktakeModel> models = my_dataBase.stocktakeDao().getall();


        for (int i = 0; i < models.size(); i++)
            if (models.get(i).getDeviceId() == null) models.get(i).setDeviceId(deviceId);

        Log.e("models", models.size() + "");
        exportData.exportStockTakeList(models);
        // my_dataBase.stocktakeDao().setposted();

    }

    EditText.OnEditorActionListener onEditAction = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                    || i == EditorInfo.IME_NULL) {
                switch (textView.getId()) {
                    case R.id.ST_zoneedt:
                        Log.e("ST_zoneedt", "ST_zoneedt");
                        if (!zonecode.getText().toString().trim().equals("")) {
//                                 if(zoneExists(zonecode.getText().toString()))
//                                 {
                            mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            Log.e("zoneedt", zonecode.getText().toString());
                            mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            itemOcode.setEnabled(true);
                            itemOcode.requestFocus();
                            getZoneSumQty();
//                                 }
//                                 else{
//                                     zonecode.setError("Invalid");
//                                     zonecode.setText("");
//                                     zonecode.requestFocus();
//                                 }

                        } else {
                            mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            // Vibrate for 1000 milliseconds
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                //deprecated in API 26
                                v.vibrate(1000);
                            }
                            zonecode.requestFocus();
                            Log.e("zoneedt===", zonecode.getText().toString());
                        }

                        break;
                    case R.id.ST_itemcodeedt:

                        if (!itemOcode.getText().toString().equals("")) {

                            if (itemOcode.getText().toString().length() <= 15) {
                                zonecode.setEnabled(false);
                                mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                itemname.setText("");
                                stocktakelist.clear();
                                Log.e("itemcode", itemOcode.getText().toString());
                                boolean existsflage = false;
                                //local list check
                                if (stocktakelist.size() > 0) {


                                    if (ItemExistsLocal()) {
                                        mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                        long sum = Long.parseLong(stocktakelist.get(localitempostion).getQty()) + 1;
                                        stocktakelist.get(localitempostion).setQty(sum + "");
                                        int z = my_dataBase.stocktakeDao().IncreasQty(stocktakelist.get(localitempostion).getQty(), stocktakelist.get(localitempostion).getZone(), stocktakelist.get(localitempostion).getItemOcode(), stocktakelist.get(localitempostion).getStore());
                                        Log.e("z====", "" + z);
                                        existsflage = true;

                                        itemOcode.setText("");
                                        itemOcode.requestFocus();

                                        getItemsSumQty();
                                        getZoneSumQty();
                                        getStoreSumQty();
                                        if (stocktakeAdapter != null)
                                            stocktakeAdapter.notifyDataSetChanged();
                                        else
                                            filladapter();
                                        break;
                                    }
                                    //check database
                                    else if (existsflage == false) {


                                        StocktakeModel stocktakeModel = my_dataBase.stocktakeDao().getstockObj(StoreSpinner.getSelectedItem().toString().substring(0, StoreSpinner.getSelectedItem().toString().indexOf(" ")), zonecode.getText().toString().trim(), itemOcode.getText().toString().trim());
                                        if (stocktakeModel != null) {
                                            mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                            Log.e("notnull", "notnull");
                                            long sum = Long.parseLong(stocktakeModel.getQty()) + 1;
                                            stocktakeModel.setQty(sum + "");
                                            my_dataBase.stocktakeDao().IncreasQty(stocktakeModel.getQty(), stocktakeModel.getZone(), stocktakeModel.getItemOcode(), stocktakeModel.getStore());
                                            stocktakelist.add(stocktakeModel);
                                            stocktakeAdapter.notifyDataSetChanged();

                                            itemOcode.setText("");
                                            itemOcode.requestFocus();
                                            getItemsSumQty();
                                            getZoneSumQty();
                                            getStoreSumQty();
                                        }
                                    } else if (ItemExists(itemOcode.getText().toString())) {
                                        mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                        filldata();

                                        itemOcode.setText("");
                                        itemOcode.requestFocus();
                                        break;
                                    } else {
                                        showSweetDialog(Stoketake.this, 3, "","InValid Item  "+ itemOcode.getText());
                                        mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                        // Vibrate for 1000 milliseconds
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                        } else {
                                            //deprecated in API 26
                                            v.vibrate(1000);
                                        }
                                        itemOcode.setText("");
                                    }
                                } else {


                                    Log.e("checkdatabase", "checkdatabase");
                                    StocktakeModel stocktakeModel = my_dataBase.stocktakeDao().getstockObj(StoreSpinner.getSelectedItem().toString().substring(0, StoreSpinner.getSelectedItem().toString().indexOf(" ")), zonecode.getText().toString().trim(), itemOcode.getText().toString().trim());
                                    if (stocktakeModel != null) {
                                        mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                        Log.e("notnull", "notnull");
                                        long sum = Long.parseLong(stocktakeModel.getQty()) + 1;
                                        stocktakeModel.setQty(sum + "");
                                        my_dataBase.stocktakeDao().IncreasQty(stocktakeModel.getQty(), stocktakeModel.getZone(), stocktakeModel.getItemOcode(), stocktakeModel.getStore());
                                        stocktakelist.add(stocktakeModel);
                                        itemname.setText(stocktakeModel.getItemName());
                                        filladapter();
                                        getItemsSumQty();
                                        getZoneSumQty();
                                        getStoreSumQty();
                                        itemOcode.setText("");
                                        itemOcode.requestFocus();

                                    }


                                    //check AllstocktakeDBlist
                                    else if (ItemExists(itemOcode.getText().toString())) {
                                        mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                        filldata();

                                        itemOcode.setText("");

                                        itemOcode.requestFocus();
                                        break;
                                    } else {
                                        showSweetDialog(Stoketake.this, 3, "","InValid Item  "+ itemOcode.getText());
                                        mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                        // Vibrate for 1000 milliseconds
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                        } else {
                                            //deprecated in API 26
                                            v.vibrate(1000);
                                        }
                                        itemOcode.setText("");
                                    }
                                }


                            } else {

                                showSweetDialog(Stoketake.this, 3, "","InValid Item  "+ itemOcode.getText());

                                mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                // Vibrate for 1000 milliseconds
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                } else {
                                    //deprecated in API 26
                                    v.vibrate(1000);
                                }
                                itemOcode.setText("");
                                itemOcode.setEnabled(true);
                                itemOcode.requestFocus();

                            }
                        } else
                        {  itemOcode.requestFocus();
                            mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            // Vibrate for 1000 milliseconds
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                //deprecated in API 26
                                v.vibrate(1000);
                            }
                        }
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

        stocktakelist.clear();
        exportData = new ExportData(Stoketake.this);
        total_zoneqty_text = findViewById(R.id.total_zoneqty_text);
        total_scanedqty_text = findViewById(R.id.total_scanedqty_text);
        total_storeqty_text = findViewById(R.id.total_storeqty_text);
        my_dataBase = RoomAllData.getInstanceDataBase(Stoketake.this);
        try {
            appSettings = my_dataBase.settingDao().getallsetting();
        } catch (Exception e) {
        }

        if (appSettings.size() != 0) {
            deviceId = appSettings.get(0).getDeviceId();
            Log.e("appSettings", "+" + deviceId);

        }
        StoreSpinner = findViewById(R.id.storespinner);
        importData = new ImportData(Stoketake.this);
        // importData.getAllZones();
        respone = findViewById(R.id.respone3);
        saveButton = findViewById(R.id.ST_save);
        reduiceButton = findViewById(R.id.ST_deletebtn);
        backButton = findViewById(R.id.ST_cancel);
        importButton = findViewById(R.id.ST_importbtn);
        zonecode = findViewById(R.id.ST_zoneedt);
        itemOcode = findViewById(R.id.ST_itemcodeedt);
        recQty = findViewById(R.id.ST_qtyedt);
        generalMethod = new GeneralMethod(Stoketake.this);
        zonecode.setOnKeyListener(onKeyListener);
        itemOcode.setOnKeyListener(onKeyListener);


        zonecode.setOnEditorActionListener(onEditAction);
        itemOcode.setOnEditorActionListener(onEditAction);

        itemname = findViewById(R.id.ST_itemname);
        stockListView = findViewById(R.id.stocklist);
        nextzoneButton = findViewById(R.id.ST_nextZone);
        datarespon = findViewById(R.id.ST_respon);
        saveButton.setOnClickListener(onClickListener);
        nextzoneButton.setOnClickListener(onClickListener);
        importButton.setOnClickListener(onClickListener);
        reduiceButton.setOnClickListener(onClickListener);
        backButton.setOnClickListener(onClickListener);
        St_Itemrespons = findViewById(R.id.Itemrespons);
        St_Itemrespons.addTextChangedListener(new TextWatcher() {
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
                                    showSweetDialog(Stoketake.this, 3, "Internal Application Error", "");
                                } catch (WindowManager.BadTokenException e) {
                                    //use a log message
                                }
                            }
                        });

                    }else
                    if (editable.toString().equals("ItemOCode")) {

                        my_dataBase.itemDao().deleteall();
                        my_dataBase.itemDao().insertAll(AllImportItemlist);
                        AllstocktakeDBlist.clear();
                        AllstocktakeDBlist.addAll(my_dataBase.itemDao().getAll());


                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                try {
                                    showSweetDialog(Stoketake.this, 1, getString(R.string.dataStored), "");
                                } catch (WindowManager.BadTokenException e) {
                                    //use a log message
                                }
                            }
                        });


                    } else if (editable.toString().equals("nodata")) {
                        //  Toast.makeText(Stoketake.this,"NetWork Error",Toast.LENGTH_SHORT).show();

                        Handler h = new Handler(Looper.getMainLooper());

                        h.post(new Runnable() {
                            public void run() {
                                try {
                                    showSweetDialog(Stoketake.this, 0, getString(R.string.netWorkError), "");
                                } catch (WindowManager.BadTokenException e) {
                                    //use a log message
                                }


                            }
                        });

                    } else {

                        Handler h = new Handler(Looper.getMainLooper());

                        h.post(new Runnable() {
                            public void run() {
                                try {
                                    Toast.makeText(Stoketake.this, "Server Error", Toast.LENGTH_SHORT).show();
                                } catch (WindowManager.BadTokenException e) {
                                    //use a log message
                                }


                            }
                        });
                    }


                }
            }
        });
        datarespon.addTextChangedListener(new TextWatcher() {
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




                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                showSweetDialog(Stoketake.this, 0, "Server Error", "");
                            }
                        });


                        stocktakelist.clear();
                        filladapter();
                    }else
                    if (editable.toString().trim().equals("exported")) {
                        savedata("1");
                        try {

                            stocktakelist.clear();
                            filladapter();
                            Handler h = new Handler(Looper.getMainLooper());
                            h.post(new Runnable() {
                                public void run() {
                                    showSweetDialog(Stoketake.this, 1, getResources().getString(R.string.savedSuccsesfule), "");

                                }
                            });

                            zonecode.setEnabled(true);
                            zonecode.requestFocus();
                        } catch (WindowManager.BadTokenException e) {
                            //use a log message
                        }


                    } else {
                        // savedata("0");
                        Log.e("el==", "here");
                        stocktakelist.clear();
                        filladapter();
                        zonecode.setEnabled(true);
                        zonecode.requestFocus();
                    }
                }

            }
        });
        respone.addTextChangedListener(new TextWatcher() {
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
                            for (int i = 0; i < Storelist.size(); i++) {
                                spinnerArray.add(Storelist.get(i).getSTORENO() + "  " + Storelist.get(i).getSTORENAME());
                                my_dataBase.storeDao().insert(Storelist.get(i));
                            }
                        }
                        fillSpinner();
                        Log.e("afterTextChanged", "" + editable.toString());


                    }

                }
            }
        });
    }

    private void getDialog() {
        showSweetDialog(Stoketake.this, 2, getString(R.string.dataStored), "");

    }

    private void savedata(String poststate) {
        my_dataBase.stocktakeDao().setposted(poststate);
    }

    private void fillSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        StoreSpinner.setAdapter(adapter);
        StoreSpinner.setSelection(1);

    }

    public boolean zoneExists(String zone) {
        boolean f = false;
        for (int x = 0; x < listAllZone.size(); x++)
            if (listAllZone.get(x).getZoneCode().equals(zone)) {
                f = true;
                break;
            }
        return f;


    }

    private void filldata() {
        Log.e("filldata", "filldata");
        if (!itemOcode.getText().toString().equals("")) {

            if (itemOcode.getText().toString().length() <= 15) {

                StocktakeModel stocktakeModel = new StocktakeModel();
                stocktakeModel.setIsPosted("0");
                stocktakeModel.setQty("1");
                stocktakeModel.setDate(generalMethod.getCurentTimeDate(1));
                stocktakeModel.setTime(generalMethod.getCurentTimeDate(2));
                stocktakeModel.setUserNO(UserNo);

                stocktakeModel.setStore(StoreSpinner.getSelectedItem().toString().substring(0, StoreSpinner.getSelectedItem().toString().indexOf(" ")));
                stocktakeModel.setStoreName(StoreSpinner.getSelectedItem().toString());
                stocktakeModel.setItemOcode(itemOcode.getText().toString());
                stocktakeModel.setZone(zonecode.getText().toString());
                stocktakeModel.setItemName(itemname.getText().toString());
                stocktakeModel.setDeviceId(deviceId);
                stocktakelist.add(stocktakeModel);
                Log.e(" stocktakelist======", stocktakelist.size() + "");
                my_dataBase.stocktakeDao().insert(stocktakeModel);

                filladapter();

                getItemsSumQty();
                getZoneSumQty();
                getStoreSumQty();
            } else {
                itemOcode.setError("Invalid");
                itemOcode.setText("");
                itemOcode.setEnabled(true);
                itemOcode.requestFocus();

            }
        }
    }

    private void filladapter() {

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        stockListView.setLayoutManager(llm);

        stocktakeAdapter = new StocktakeAdapterr(this, stocktakelist);
        stockListView.setAdapter(stocktakeAdapter);
        stocktakeAdapter.notifyDataSetChanged();
    }

    private void OpenDeleteDailog() {


        final Dialog dialog = new Dialog(Stoketake.this);
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

        deletezone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticDailog(2);
            }
        });

        deleteitem.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {

                                              authenticDailog(1);
                                          }
                                      }
        );

        dialog.findViewById(R.id.dialogcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //upadte data after delete or reduce
                getItemsSumQty();
                getZoneSumQty();
                getStoreSumQty();
                dialog.dismiss();
            }
        });


    }

    private void OpenDeleteDailog2() {


        final Dialog dialog = new Dialog(Stoketake.this);
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

        deletezone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticDailog2(2);
            }
        });

        deleteitem.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {

                                              authenticDailog2(1);
                                          }
                                      }
        );

        dialog.findViewById(R.id.dialogcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //upadte data after delete or reduce
                getItemsSumQty();
                getZoneSumQty();
                getStoreSumQty();
                dialog.dismiss();
            }
        });


    }

    private void authenticDailog(int enterflage) {


        authenticationdialog = new Dialog(Stoketake.this);
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
        UsNa = authenticationdialog.findViewById(R.id.username);
        UsNa.requestFocus();

        EditText pass = authenticationdialog.findViewById(R.id.pass);
        pass.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UsNa.getText().toString().trim().equals(userPermissions.getUserNO()) && pass.getText().toString().trim().equals(userPermissions.getUserPassword())
                        ||     checkIfUserWhoLoginIsMaster(Stoketake.this,UsNa.getText().toString().trim(),pass.getText().toString().trim())){
                    if (enterflage == 1)
                        openDeleteitemDailog();
                    else if (enterflage == 2) openDeleteZoneDailog();


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


        authenticationdialog = new Dialog(Stoketake.this);
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
                            openDeleteitemDailog();
                            authenticationdialog.dismiss();
                        } else if (enterflage == 2) {
                            openDeleteZoneDailog();
                            authenticationdialog.dismiss();
                        }else if (enterflage ==4) {
                            authenticationdialog.dismiss();
                           saveData();

                        }


                    } else {
                        generalMethod.showSweetDialog(Stoketake.this, 3, getResources().getString(R.string.Permission), "");

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
    private void  saveData(){
        Log.e("ST_save", "ST_save");
        MainActivity.activityflage = 2;

            exportdata();
            clearAll();


        //  MainActivity.activityflage=2;
        // exportdata();
        clearAll();
    }
    private void copylist() {
        DB_stocktakecopy.clear();
        List<String> zon = new ArrayList<>();
        List<String> item = new ArrayList<>();
        List<String> qtys = new ArrayList<>();
        List<String> store = new ArrayList<>();
        for (int i = 0; i < DB_stocktake.size(); i++) {
            zon.add(DB_stocktake.get(i).getZone());
            item.add(DB_stocktake.get(i).getItemOcode());
            qtys.add(DB_stocktake.get(i).getQty());
            store.add(DB_stocktake.get(i).getStore());
        }

        for (int i = 0; i < zon.size(); i++) {
            StocktakeModel stocktakeModel = new StocktakeModel();
            stocktakeModel.setStore(store.get(i));
            stocktakeModel.setZone(zon.get(i));
            stocktakeModel.setItemOcode(item.get(i));
            stocktakeModel.setQty(qtys.get(i));
            DB_stocktakecopy.add(stocktakeModel);
        }
    }

    private void openDeleteitemDailog() {
        ST_reducedqtyitemlist.clear();
        DB_stocktake = my_dataBase.stocktakeDao().getall();
        copylist();
        DB_store = new ArrayList<>();
        DB_zone = new ArrayList<>();

        final Dialog Deleteitemdialog = new Dialog(Stoketake.this);
        Deleteitemdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Deleteitemdialog.setCancelable(false);
        Deleteitemdialog.setContentView(R.layout.deleterep_itemsdialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Deleteitemdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        Deleteitemdialog.getWindow().setAttributes(lp);
        Deleteitemdialog.show();

        ST_close_btn = Deleteitemdialog.findViewById(R.id.DIRE_close_btn);
        ST_zoneSearch2 = Deleteitemdialog.findViewById(R.id.DIRE_zoneSearch2);
        ST_preQTY = Deleteitemdialog.findViewById(R.id.DIRE_preQTY);
        ST_itemcodeshow = Deleteitemdialog.findViewById(R.id.DIRE_itemcodeshow);
        ST_zoneshow = Deleteitemdialog.findViewById(R.id.DIRE_zoneshow);
        ST_qtyshows = Deleteitemdialog.findViewById(R.id.DIRE_qtyshow);


        Button DIRE_dialogsave = Deleteitemdialog.findViewById(R.id.DIRE_dialogsave);
        Button DIRE_deleteitem = Deleteitemdialog.findViewById(R.id.DIRE_deleteitem);
        Button DIRE_cancel1 = Deleteitemdialog.findViewById(R.id.DIRE_cancel1);

        ST_ZONEcod = Deleteitemdialog.findViewById(R.id.DIRE_ZONEcode);
        ST_itemcode = Deleteitemdialog.findViewById(R.id.DIRE_itemcode);
        ST_itemcode.setEnabled(false);
        DIRE_deleteitem.setEnabled(false);
        ST_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticationdialog.dismiss();
                Deleteitemdialog.dismiss();


            }
        });
        ST_zoneSearch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB_zone.clear();
                for (int i = 0; i < DB_stocktake.size(); i++) {
                    if (DB_stocktake.get(i).getStore().equals(spinner2.getSelectedItem().toString().substring(0, spinner2.getSelectedItem().toString().indexOf(" ")))) {
                        if (!DB_zone.contains(DB_stocktake.get(i).getZone()))
                            DB_zone.add(DB_stocktake.get(i).getZone());
                    }

                }


                if (DB_zone.size() != 0) {
                    flage3 = 8;

                    searchZonecodeDailog(DB_zone);
                } else
                    Toast.makeText(Stoketake.this, getString(R.string.noData), Toast.LENGTH_SHORT).show();

            }
        });

        spinner2 = Deleteitemdialog.findViewById(R.id.DIRE_storespinner);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ST_ZONEcod.setText("");
                ST_itemcode.setText("");
                ST_qtyshows.setText("");
                ST_zoneshow.setText("");
                ST_itemcodeshow.setText("");
                ST_preQTY.setText("");
                ST_ZONEcod.requestFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for (int i = 0; i < DB_stocktake.size(); i++)
            if (!DB_store.contains(DB_stocktake.get(i).getStoreName()))
                DB_store.add(DB_stocktake.get(i).getStoreName());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, DB_store);
        spinner2.setAdapter(adapter);
        Deleteitemdialog.show();


        ST_ZONEcod.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i != KeyEvent.KEYCODE_ENTER) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (!ST_ZONEcod.getText().toString().equals("")) {
                            if (isExists(1, ST_ZONEcod.getText().toString().trim(), spinner2.getSelectedItem().toString().substring(0, spinner2.getSelectedItem().toString().indexOf(" ")), "")) {
                                ST_itemcode.setEnabled(true);
                                ST_itemcode.requestFocus();
                                Log.e("a1", "a1");

                            } else {
                                ST_ZONEcod.setText("");
                                ST_ZONEcod.setError("invalid");
                                Log.e("a2", "a2");
                            }

                        } else {
                            Log.e("a3", "a3");
                            ST_ZONEcod.requestFocus();
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        ST_itemcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i != KeyEvent.KEYCODE_ENTER) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (!ST_itemcode.getText().toString().equals("")) {
                            if (isExists(2, ST_ZONEcod.getText().toString().trim(), spinner2.getSelectedItem().toString().substring(0, spinner2.getSelectedItem().toString().indexOf(" ")), ST_itemcode.getText().toString().trim())) {
                                long preqty = Long.parseLong(getpreQty(spinner2.getSelectedItem().toString().substring(0, spinner2.getSelectedItem().toString().indexOf(" ")), ST_ZONEcod.getText().toString().trim(), ST_itemcode.getText().toString().trim()));
                                ST_preQTY.setText(preqty + "");
                                long sumqty = Long.parseLong(DB_stocktake.get(indexDBitem).getQty());
                                if (sumqty > 1) {

                                    sumqty--;

                                    DB_stocktake.get(indexDBitem).setQty(sumqty + "");
                                    ST_qtyshows.setText(DB_stocktake.get(indexDBitem).getQty());
                                    ST_zoneshow.setText(ST_ZONEcod.getText().toString().trim());
                                    ST_itemcodeshow.setText(ST_itemcode.getText().toString().trim());

                                    if (isExistsInReducedlist())
                                        ST_reducedqtyitemlist.get(indexOfReduceditem).setQty(sumqty + "");
                                    else
                                        ST_reducedqtyitemlist.add(DB_stocktake.get(indexDBitem));
                                    ST_itemcode.setText("");
                                    ST_itemcode.requestFocus();
                                } else {
                                    new SweetAlertDialog(Stoketake.this, SweetAlertDialog.BUTTON_CONFIRM)
                                            .setTitleText(getResources().getString(R.string.confirm_title))
                                            .setContentText(getResources().getString(R.string.delete3))
                                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    DB_stocktake.get(indexDBitem).setQty("0");
                                                    ST_preQTY.setText("1");
                                                    ST_qtyshows.setText("1");
                                                    ST_zoneshow.setText(ST_ZONEcod.getText().toString().trim());
                                                    ST_itemcodeshow.setText(ST_itemcode.getText().toString().trim());

                                                    if (isExistsInReducedlist())
                                                        ST_reducedqtyitemlist.get(indexOfReduceditem).setQty("0");
                                                    else
                                                        ST_reducedqtyitemlist.add(DB_stocktake.get(indexDBitem));


                                                    DB_stocktake.remove(indexDBitem);
                                                    ST_itemcode.setText("");
                                                    ST_ZONEcod.setText("");
                                                    ST_qtyshows.setText("");
                                                    ST_zoneshow.setText("");
                                                    ST_itemcodeshow.setText("");
                                                    ST_preQTY.setText("");
                                                    sweetAlertDialog.dismiss();
                                                }
                                            })
                                            .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                    ST_itemcode.setText("");
                                                    ST_preQTY.setText("");
                                                }
                                            })
                                            .show();
                                }
                                DIRE_deleteitem.setEnabled(true);
                            } else {
                                ST_itemcode.setText("");
                                ST_itemcode.setError("invalid");
                                ST_qtyshows.setText("");
                                ST_zoneshow.setText("");
                                ST_itemcodeshow.setText("");
                                ST_preQTY.setText("");
                            }

                        } else {
                            ST_itemcode.requestFocus();
                        }
                    }
                    return true;
                }
                return false;
            }
        });



        ST_ZONEcod.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                if (i != KeyEvent.ACTION_UP) {

                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                            || i == EditorInfo.IME_NULL) {
                        if (!ST_ZONEcod.getText().toString().equals("")) {
                            if (isExists(1, ST_ZONEcod.getText().toString().trim(), spinner2.getSelectedItem().toString().substring(0, spinner2.getSelectedItem().toString().indexOf(" ")), "")) {
                                ST_itemcode.setEnabled(true);
                                ST_itemcode.requestFocus();
                                Log.e("a1", "a1");

                            } else {
                                ST_ZONEcod.setText("");
                                ST_ZONEcod.setError("invalid");
                                Log.e("a2", "a2");
                            }

                        } else {
                            Log.e("a3", "a3");
                            ST_ZONEcod.requestFocus();
                        }
                    }
                }

                return true;    }
        });

                ST_itemcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                if (i != KeyEvent.ACTION_UP) {

                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                            || i == EditorInfo.IME_NULL) {
                        if (!ST_itemcode.getText().toString().equals("")) {
                            if (isExists(2, ST_ZONEcod.getText().toString().trim(), spinner2.getSelectedItem().toString().substring(0, spinner2.getSelectedItem().toString().indexOf(" ")), ST_itemcode.getText().toString().trim())) {
                                long preqty = Long.parseLong(getpreQty(spinner2.getSelectedItem().toString().substring(0, spinner2.getSelectedItem().toString().indexOf(" ")), ST_ZONEcod.getText().toString().trim(), ST_itemcode.getText().toString().trim()));
                                ST_preQTY.setText(preqty + "");
                                long sumqty = Long.parseLong(DB_stocktake.get(indexDBitem).getQty());
                                if (sumqty > 1) {

                                    sumqty--;

                                    DB_stocktake.get(indexDBitem).setQty(sumqty + "");
                                    ST_qtyshows.setText(DB_stocktake.get(indexDBitem).getQty());
                                    ST_zoneshow.setText(ST_ZONEcod.getText().toString().trim());
                                    ST_itemcodeshow.setText(ST_itemcode.getText().toString().trim());

                                    if (isExistsInReducedlist())
                                        ST_reducedqtyitemlist.get(indexOfReduceditem).setQty(sumqty + "");
                                    else
                                        ST_reducedqtyitemlist.add(DB_stocktake.get(indexDBitem));
                                    ST_itemcode.setText("");
                                    ST_itemcode.requestFocus();
                                } else {
                                    new SweetAlertDialog(Stoketake.this, SweetAlertDialog.BUTTON_CONFIRM)
                                            .setTitleText(getResources().getString(R.string.confirm_title))
                                            .setContentText(getResources().getString(R.string.delete3))
                                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    DB_stocktake.get(indexDBitem).setQty("0");
                                                    ST_preQTY.setText("1");
                                                    ST_qtyshows.setText("1");
                                                    ST_zoneshow.setText(ST_ZONEcod.getText().toString().trim());
                                                    ST_itemcodeshow.setText(ST_itemcode.getText().toString().trim());

                                                    if (isExistsInReducedlist())
                                                        ST_reducedqtyitemlist.get(indexOfReduceditem).setQty("0");
                                                    else
                                                        ST_reducedqtyitemlist.add(DB_stocktake.get(indexDBitem));


                                                    DB_stocktake.remove(indexDBitem);
                                                    ST_itemcode.setText("");
                                                    ST_ZONEcod.setText("");
                                                    ST_qtyshows.setText("");
                                                    ST_zoneshow.setText("");
                                                    ST_itemcodeshow.setText("");
                                                    ST_preQTY.setText("");
                                                    sweetAlertDialog.dismiss();
                                                }
                                            })
                                            .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                    ST_itemcode.setText("");
                                                    ST_preQTY.setText("");
                                                }
                                            })
                                            .show();
                                }
                                DIRE_deleteitem.setEnabled(true);
                            } else {
                                ST_itemcode.setText("");
                                ST_itemcode.setError("invalid");
                                ST_qtyshows.setText("");
                                ST_zoneshow.setText("");
                                ST_itemcodeshow.setText("");
                                ST_preQTY.setText("");
                            }

                        } else {
                            ST_itemcode.requestFocus();
                        }
                    }
                }

                return true;    }
        });




        DIRE_dialogsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ST_reducedqtyitemlist.size() > 0) {

                    checkLocalList(2);
                    addStocktakeLogs(1);
                    for (int i = 0; i < ST_reducedqtyitemlist.size(); i++) {
                        Log.e("aya", "here");
                        if (ST_reducedqtyitemlist.get(i).getQty().equals("0"))

                            my_dataBase.stocktakeDao().deletestockObj(ST_reducedqtyitemlist.get(i).getZone(), ST_reducedqtyitemlist.get(i).getItemOcode(), ST_reducedqtyitemlist.get(i).getStore());
                        else
                            my_dataBase.stocktakeDao().UpdateQty(ST_reducedqtyitemlist.get(i).getQty(), ST_reducedqtyitemlist.get(i).getZone(), ST_reducedqtyitemlist.get(i).getItemOcode(), ST_reducedqtyitemlist.get(i).getStore());


                    }
                    Toast.makeText(Stoketake.this, getString(R.string.app_done), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Stoketake.this, getString(R.string.nODataChanged), Toast.LENGTH_SHORT).show();
                }

                authenticationdialog.dismiss();

                Deleteitemdialog.dismiss();

            }
        });

        DIRE_deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(Stoketake.this, SweetAlertDialog.BUTTON_CONFIRM)
                        .setTitleText(getResources().getString(R.string.confirm_title))
                        .setContentText(getResources().getString(R.string.deleteitem))
                        .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                DB_stocktake.get(indexDBitem).setQty("0");
                                if (ST_reducedqtyitemlist.size() > 0) {


                                    if (isExistsInReducedlist()) {

                                        ST_reducedqtyitemlist.get(indexOfReduceditem).setQty("0");
                                    } else {

                                        ST_reducedqtyitemlist.add(DB_stocktake.get(indexDBitem));
                                    }
                                } else
                                    ST_reducedqtyitemlist.add(DB_stocktake.get(indexDBitem));


                                Log.e(" reducedqtyitemlist", "" + ST_reducedqtyitemlist.size());
                                Log.e(" reducedqtyitemlist", "" + DB_stocktake.get(indexDBitem).getItemOcode());
                                DB_stocktake.remove(indexDBitem);
                                ST_itemcode.setText("");
                                ST_ZONEcod.setText("");
                                ST_qtyshows.setText("");
                                ST_zoneshow.setText("");
                                ST_itemcodeshow.setText("");
                                ST_preQTY.setText("");
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
        });

        DIRE_cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ST_reducedqtyitemlist.size() > 0)

                    new SweetAlertDialog(Stoketake.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.returndata))
                            .setConfirmButton(getResources().getString(R.string.yes),
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {


                                            ST_reducedqtyitemlist.clear();
                                            ST_ZONEcod.setText("");
                                            ST_itemcode.setText("");
                                            ST_qtyshows.setText("");
                                            ST_zoneshow.setText("");
                                            ST_itemcodeshow.setText("");
                                            ST_preQTY.setText("");
                                            DB_stocktake.clear();
                                            DB_stocktake = my_dataBase.stocktakeDao().getall();
                                            sweetAlertDialog.dismiss();


                                        }
                                    }).setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.dismiss();

                            // FinishProcessFlag=0;
                        }
                    })
                            .show();
                else
                    Toast.makeText(Stoketake.this, getResources().getString(R.string.NODATA), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addStocktakeLogs(int flage) {
        if (flage == 1) {
            StocktakeLogs stocktakeLogs = new StocktakeLogs();
            for (int i = 0; i < ST_reducedqtyitemlist.size(); i++) {
                stocktakeLogs.setStore(ST_reducedqtyitemlist.get(i).getStore());
                stocktakeLogs.setZoneCode(ST_reducedqtyitemlist.get(i).getZone());
                stocktakeLogs.setNewQty(ST_reducedqtyitemlist.get(i).getQty());
                stocktakeLogs.setItemCode(ST_reducedqtyitemlist.get(i).getItemOcode());
                String preqty = getpreQty(ST_reducedqtyitemlist.get(i).getStore(), ST_reducedqtyitemlist.get(i).getZone(), ST_reducedqtyitemlist.get(i).getItemOcode());
                stocktakeLogs.setPreQty(preqty);

                stocktakeLogs.setLogsDATE(generalMethod.getCurentTimeDate(1));
                stocktakeLogs.setLogsTime(generalMethod.getCurentTimeDate(2));
                stocktakeLogs.setUserNO(UsNa.getText().toString());
                my_dataBase.stocktakelogsDao().insert(stocktakeLogs);
            }
        } else {
            StocktakeLogs stocktakeLog = new StocktakeLogs();
            Log.e("deleted_DBzone", deleted_DBzone.size() + "");
            for (int i = 0; i < deleted_DBzone.size(); i++) {
                stocktakeLog.setStore(deleted_DBzone.get(i).getStore());
                stocktakeLog.setZoneCode(deleted_DBzone.get(i).getZone());
                stocktakeLog.setNewQty("0");
                stocktakeLog.setPreQty(deleted_DBzone.get(i).getQty());
                stocktakeLog.setItemCode(deleted_DBzone.get(i).getItemOcode());
                stocktakeLog.setLogsDATE(generalMethod.getCurentTimeDate(1));
                stocktakeLog.setLogsTime(generalMethod.getCurentTimeDate(2));
                stocktakeLog.setUserNO(UsNa.getText().toString());
                my_dataBase.stocktakelogsDao().insert(stocktakeLog);
            }

        }


    }

    private String getpreQty(String store, String zone, String itemcode) {
        Log.e("getpreQty", zone + "\t" + itemcode);
        Log.e("zonecopysize", DB_stocktakecopy.size() + "");
        String z = "";
        for (int i = 0; i < DB_stocktakecopy.size(); i++) {
            if (zone.equals(DB_stocktakecopy.get(i).getZone()) && itemcode.equals(DB_stocktakecopy.get(i).getItemOcode())
                    && store.equals(DB_stocktakecopy.get(i).getStore())) {
                Log.e("getpreQty2", DB_stocktakecopy.get(i).getZone() + "\t" + DB_stocktakecopy.get(i).getItemOcode());
                Log.e("getpreQty3", DB_stocktakecopy.get(i).getQty());
                Log.e("index", i + "");
                z = DB_stocktakecopy.get(i).getQty();
                break;
            } else {
                z = "";
            }

        }
        return z;
    }

    private boolean isExistsInReducedlist() {
        boolean f = false;


        for (int x = 0; x < ST_reducedqtyitemlist.size(); x++)
            if (ST_reducedqtyitemlist.get(x).getZone().equals(ST_ZONEcod.getText().toString().trim()) &&
                    ST_reducedqtyitemlist.get(x).getStore().equals(spinner2.getSelectedItem().toString().substring(0, spinner2.getSelectedItem().toString().indexOf(" ")))
                    && ST_reducedqtyitemlist.get(x).getItemOcode().equals(ST_itemcodeshow.getText().toString().trim())) {
                f = true;
                indexOfReduceditem = x;
            } else {
                f = false;


                continue;
            }

        return f;
    }

    private void openDeleteZoneDailog() {
        DB_stocktake.clear();
        DB_stocktake = my_dataBase.stocktakeDao().getall();
        DB_store = new ArrayList<>();
        DB_zone = new ArrayList<>();
        deleted_DBzone = new ArrayList<>();

        DB_store.clear();
        DB_zone.clear();
        deleted_DBzone.clear();
        final Dialog dialog = new Dialog(Stoketake.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.deletere_zonediaolg);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        spinner = dialog.findViewById(R.id.DZRE_storespinner);
        for (int i = 0; i < DB_stocktake.size(); i++)
            if (!DB_store.contains(DB_stocktake.get(i).getStoreName()))
                DB_store.add(DB_stocktake.get(i).getStoreName());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, DB_store);
        spinner.setAdapter(adapter);
        dialog.show();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ST_ZONEcode.setText("");
                ST_zonecodeshow.setText("");
                ST_qtyshow.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ST_delete = dialog.findViewById(R.id.DZRE_delete);
        ST_delete.setEnabled(false);
        ST_ZONEcode = dialog.findViewById(R.id.DZRE_ZONEcode);
        ST_zonecodeshow = dialog.findViewById(R.id.DZRE_zonecodeshow);
        ST_qtyshow = dialog.findViewById(R.id.DZRE_qtyshow);
        dialog.findViewById(R.id.DZRE_zoneSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB_zone.clear();
                for (int i = 0; i < DB_stocktake.size(); i++) {
                    if (DB_stocktake.get(i).getStore().equals(spinner.getSelectedItem().toString().substring(0, spinner.getSelectedItem().toString().indexOf(" ")))) {
                        if (!DB_zone.contains(DB_stocktake.get(i).getZone()))
                            DB_zone.add(DB_stocktake.get(i).getZone());
                    }

                }
                if (DB_zone.size() != 0) {
                    flage3 = 7;

                    searchZonecodeDailog(DB_zone);
                } else
                    Toast.makeText(Stoketake.this, "No Data", Toast.LENGTH_SHORT).show();

            }
        });

        ST_ZONEcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i != KeyEvent.KEYCODE_ENTER) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (!ST_ZONEcode.getText().toString().equals("")) {
                            if (isExists(1, ST_ZONEcode.getText().toString().trim(), spinner.getSelectedItem().toString().substring(0, spinner.getSelectedItem().toString().indexOf(" ")), "")) {
                                ST_zonecodeshow.setText(ST_ZONEcode.getText().toString().trim());

                                getqtyofDBzone();
                                ST_delete.setEnabled(true);
                            } else {
                                ST_ZONEcode.setText("");
                                ST_ZONEcode.setError("invalid");
                            }

                        } else {
                            ST_ZONEcode.requestFocus();
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        ST_ZONEcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                if (i != KeyEvent.ACTION_UP) {

                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                            || i == EditorInfo.IME_NULL) {
                        if (!ST_ZONEcode.getText().toString().equals("")) {
                            if (isExists(1, ST_ZONEcode.getText().toString().trim(), spinner.getSelectedItem().toString().substring(0, spinner.getSelectedItem().toString().indexOf(" ")), "")) {
                                ST_zonecodeshow.setText(ST_ZONEcode.getText().toString().trim());

                                getqtyofDBzone();
                                ST_delete.setEnabled(true);
                            } else {
                                ST_ZONEcode.setText("");
                                ST_ZONEcode.setError("invalid");
                            }

                        } else {
                            ST_ZONEcode.requestFocus();
                        }
                    }
                }

                return true;    }
        });

        dialog.findViewById(R.id.DZRE_close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticationdialog.dismiss();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.DZRE_dialogsave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleted_DBzone.size() > 0) {

                    checkLocalList(1);
                    addStocktakeLogs(2);

                    for (int i = 0; i < deleted_DBzone.size(); i++)
                        my_dataBase.stocktakeDao()
                                .deletezone(deleted_DBzone.get(i).getZone(), deleted_DBzone.get(i).getStore());
                    authenticationdialog.dismiss();
                    dialog.dismiss();
                    Toast.makeText(Stoketake.this, "Done", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Stoketake.this, "NO data changed", Toast.LENGTH_LONG).show();
                }
            }
        });
        ST_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(Stoketake.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.confirm_title))
                        .setContentText(getResources().getString(R.string.deleteitem))
                        .setConfirmButton(getResources().getString(R.string.yes),
                                new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        ;

                                        for (int i = 0; i < DB_stocktake.size(); i++)
                                            if (spinner.getSelectedItem().toString().substring(0, spinner.getSelectedItem().toString().indexOf(" ")).equals(DB_stocktake.get(i).getStore()) &&
                                                    ST_ZONEcode.getText().toString().trim().equals(DB_stocktake.get(i).getZone())) {

                                                deleted_DBzone.add(DB_stocktake.get(i));
                                                Log.e("DB_stocktakegetZone", DB_stocktake.get(i).getZone());
                                                Log.e("DB_stocktakegetstore", DB_stocktake.get(i).getStore());
                                                DB_stocktake.remove(i);
                                                i--;
                                            }
                                        ST_ZONEcode.setText("");
                                        ST_zonecodeshow.setText("");
                                        ST_qtyshow.setText("");

                                        sweetAlertDialog.dismiss();

                                    }
                                }).setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        sweetAlertDialog.dismiss();
                    }
                })
                        .show();
            }
        });
        dialog.findViewById(R.id.DZRE_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleted_DBzone.size() > 0) {
                    new SweetAlertDialog(Stoketake.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.returndata)).setConfirmButton(R.string.yes, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            DB_stocktake.clear();
                            DB_stocktake = my_dataBase.stocktakeDao().getall();

                            deleted_DBzone.clear();
                            ST_ZONEcode.setText("");
                            ST_zonecodeshow.setText("");
                            ST_qtyshow.setText("");
                            sweetAlertDialog.dismiss();

                        }
                    }).setCancelButton(R.string.no, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
                } else {
                    Toast.makeText(Stoketake.this, getResources().getString(R.string.NODATA), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isExists(int flage, String zone, String store, String itemcode) {
        boolean f = false;
        if (flage == 1) {
            for (int i = 0; i < DB_stocktake.size(); i++)
                if (DB_stocktake.get(i).getZone().equals(zone) &&
                        DB_stocktake.get(i).getStore().equals(store)) {
                    f = true;
                    indexDBZone = i;
                    break;
                } else {
                    f = false;
                    continue;
                }

        }
        if (flage == 2) {
            for (int i = 0; i < DB_stocktake.size(); i++)
                if (DB_stocktake.get(i).getZone().equals(zone) &&
                        DB_stocktake.get(i).getStore().equals(store)
                        && DB_stocktake.get(i).getItemOcode().equals(itemcode)) {
                    f = true;
                    indexDBitem = i;
                    break;
                } else {
                    f = false;
                    continue;
                }
        }
        return f;
    }

    public static void getqtyofDBzone() {
        int sum = 0;
        for (int x = 0; x < DB_stocktake.size(); x++)
            if (DB_stocktake.get(x).getStore().equals(spinner.getSelectedItem().toString().substring(0, spinner.getSelectedItem().toString().indexOf(" "))) &&
                    DB_stocktake.get(x).getZone().equals(ST_ZONEcode.getText().toString().trim())) {
                Log.e("getqtyofDBzone", sum + "");
                sum += Long.parseLong(DB_stocktake.get(x).getQty());
            }
        ST_qtyshow.setText(sum + "");
    }

    private void checkLocalList(int flage) {

        if (flage == 1) {
            if (stocktakelist != null) {
                if (stocktakelist.size() > 0) {

                    Log.e("checkLocalList", stocktakelist.size() + "");
                    for (int i = 0; i < deleted_DBzone.size(); i++) {
                        for (int j = 0; j < stocktakelist.size(); j++) {

                            if (stocktakelist.get(j).getStore().equals(deleted_DBzone.get(i).getStore()) &&
                                    stocktakelist.get(j).getZone().equals(deleted_DBzone.get(i).getZone())
                            ) {


                                stocktakelist.remove(j);
                                if (stocktakeAdapter != null)
                                    stocktakeAdapter.notifyDataSetChanged();


                            }
                        }
                    }
                }

            }
        } else if (flage == 2) {
            Log.e("flage", flage + "");
            Log.e("ST_reducedqtyitem", ST_reducedqtyitemlist.size() + "");
            Log.e("stocktakelist", stocktakelist.size() + "");

            if (stocktakelist != null) {
                if (stocktakelist.size() > 0) {
                    for (int i = 0; i < ST_reducedqtyitemlist.size(); i++) {
                        for (int j = 0; j < stocktakelist.size(); j++) {
                            Log.e("LocalListsize", stocktakelist.size() + "");
                            if (stocktakelist.get(j).getStore().equals(ST_reducedqtyitemlist.get(i).getStore()) &&
                                    stocktakelist.get(j).getZone().equals(ST_reducedqtyitemlist.get(i).getZone())
                                    && stocktakelist.get(j).getItemOcode().equals(ST_reducedqtyitemlist.get(i).getItemOcode()))

                                if (ST_reducedqtyitemlist.get(i).getQty().equals("0"))
                                    stocktakelist.remove(j);
                                else
                                    stocktakelist.get(j).setQty(ST_reducedqtyitemlist.get(i).getQty());
                            if (stocktakeAdapter != null) stocktakeAdapter.notifyDataSetChanged();
                        }
                    }

                }
            }
        }
    }

    private void searchZonecodeDailog(List<String> Zonelist) {
        ST_searchdialog = new Dialog(Stoketake.this);
        ST_searchdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ST_searchdialog.setCancelable(true);
        ST_searchdialog.setContentView(R.layout.dialog_zone_search);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(ST_searchdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        ST_searchdialog.getWindow().setAttributes(lp);
        Button button = ST_searchdialog.findViewById(R.id.btn_send);
        button.setVisibility(View.GONE);
        ListView listView = ST_searchdialog.findViewById(R.id.listViewEngineering);
        ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(Stoketake.this, Zonelist);
        listView.setAdapter(adapter);
        List<String> list = new ArrayList<>();

        EditText editText = ST_searchdialog.findViewById(R.id.searchZoneText);
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

                    ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(Stoketake.this, list);
                    listView.setAdapter(adapter);
                } else {
                    ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(Stoketake.this, Zonelist);
                    listView.setAdapter(adapter);
                }
            }
        });


        ST_searchdialog.show();

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

    private void ChecksavePermissitions() {


        if (userPermissions != null) {
            if (userPermissions.getMasterUser().equals("0")) {
                if (userPermissions.getStockTake_Save().equals("1"))
                    saveButton.setEnabled(true);
                else
                    saveButton.setEnabled(false);
            } else
                saveButton.setEnabled(true);


        }
    }

    private void CheckdeletePermissitions() {


        if (userPermissions != null) {
            if (userPermissions.getMasterUser().equals("0")) {
                if (userPermissions.getStockTake_LocalDelete().equals("1")) {
                    reduiceButton.setEnabled(true);

                } else {
                    reduiceButton.setEnabled(false);
                }
            } else reduiceButton.setEnabled(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (zoneinfo != null && zoneinfo.isShowing()) {
            zoneinfo.cancel();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mode = (AudioManager) Stoketake.this.getSystemService(Context.AUDIO_SERVICE);

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
}