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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.irbidcitycenter.Adapters.BoxnoSearchAdapter;
import com.example.irbidcitycenter.Adapters.D_SH_PosearchAdapter;
import com.example.irbidcitycenter.Adapters.D_SH_boxsearchAdapter;
import com.example.irbidcitycenter.Adapters.PonoSearchAdapter;
import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.NewAllPOsInfo;
import com.example.irbidcitycenter.Models.PO;
import com.example.irbidcitycenter.Models.ShipmentLogs;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;
import com.example.irbidcitycenter.ScanActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import com.example.irbidcitycenter.Adapters.ShipmentAdapter;
import com.example.irbidcitycenter.Models.Shipment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.irbidcitycenter.Activity.Login.userPermissions;
import static com.example.irbidcitycenter.Activity.MainActivity.FILE_NAME;
import static com.example.irbidcitycenter.Activity.MainActivity.KEY_LANG;
import static com.example.irbidcitycenter.Adapters.ShipmentAdapter.sum;
import static com.example.irbidcitycenter.GeneralMethod.checkIfUserWhoLoginIsMaster;
import static com.example.irbidcitycenter.GeneralMethod.convertToEnglish;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;
import static com.example.irbidcitycenter.ImportData.BoxNolist;
import static com.example.irbidcitycenter.ImportData.POdetailslist;
import static com.example.irbidcitycenter.ImportData.PoNolist;


public class NewShipment extends AppCompatActivity {
    boolean saved = false;
    int FinishProcessFlag = 0;
    Shipment newshipment;
    ExportData exportData;
    static ImportData importData;
    public static String boxnotag;
    public static int updateflage = 1;
    public static int CheckFlag = 0;
    public static Dialog db_posearchdialog, db_boxsearchdialog;
    public Button next;
    public static  Button DS_delete1;
    public Button save;
    public static TextView respon;
    public static TextView boxnorespon, DS_poediteshow, DS_boxcountediteshow, DS_Itemcountediteshow, Db_boxSearch, Dbsh_poSearch;
    public static EditText pono, DS_poedit;
    public static int Flag1 = 1;
    public static EditText boxno, itemname, PoQTY, poststate, DIsh_poedit, DIsh_boxedit, DIsh_itemcodeedit;
    public static EditText barcode, Db_boxedit, Dbsh_poedit;
    public TextView barcodescan;
    public static TextView DIsh_boxSearch,PONO_respon;
    EditText qty, currentpo, currentbox, itemscounts;
    public static Dialog dialog1, dialog2;
    public static String ponotag;
    EditText UsNa;
    public EditText lastpo, lastbox, itemcount;
    public static String poNo;
    String boxNo;
    public static String barCode;
    String Qty;
    GeneralMethod generalMethod;
    String POnumselected, BOXnumselected;
    RecyclerView recyclerView;
    FloatingActionButton add;
    ImageView searchView1, searchView2, ponoClose;
    int parceQty;
    int pos;
    public static ShipmentAdapter adapter;
    BoxnoSearchAdapter boxnoSearchAdapter;
    List<Shipment> list = new ArrayList<>();
    List<String> allpo = new ArrayList<>();
    public static List<Shipment> reducedshipmentsList = new ArrayList<>();
    public static List<String> deletedPOList = new ArrayList<>();
    public static List<Shipment> shipmentsList = new ArrayList<>();
    public static List<Shipment> poshipmentsList = new ArrayList<>();
    public static ArrayList<Shipment> localList = new ArrayList<>();
    public static List<Shipment> POlist = new ArrayList<>();
    public static List<String> numBOx = new ArrayList<>();
    public static ArrayList<String> searchBoxList = new ArrayList<>();
    public static List<String> AllBoxesInDB = new ArrayList<>();
    public static List<Shipment> deletedBoxes = new ArrayList<>();
    RequestQueue requestQueue;
    PO po;
    ListView listView;
    public static RoomAllData my_dataBase;
    BoxnoSearchAdapter boxsearchadapter, boxsearchadapter2;
    PonoSearchAdapter ponoSearchAdapter, searchponoSearchAdapter;
    public static int position = 1;
    public static final int REQUEST_Camera_Barcode = 1;
    public static String barcodeofrow;
    Shipment DbShip;
    private Dialog authenticationdialog;
    List<Shipment> BbShip_list = new ArrayList();
    List<Shipment> BbShip_listcopy = new ArrayList();
    List<String> listponame = new ArrayList();
    List<String> listboxname = new ArrayList();
    private int exists_index;
    int ind;
    public static TextView DIsh_poediteshow;
    public static TextView DIsh_boxcountediteshow, Db_boxnumshow, Db_Itemcountediteshow;
    public static TextView DIsh_Itemcodeediteshow, Dbsh_poSearcht;
    public static TextView DIsh_qtyedit, DIsh_preQTY;
    public String UserNo;
    public static int dailogNum = 9;
    public static Dialog deleteBoxdialog;
    public String deviceId = "";
    Button Ship_delete;
    List<com.example.irbidcitycenter.Models.appSettings> appSettings;
    AudioManager mode ;
    private Animation animation;
  HorizontalScrollView HorizontalScroll;
    public static List<String>allPOs;
    public static List<String> POs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_new_shipment);
        my_dataBase = RoomAllData.getInstanceDataBase(NewShipment.this);
        BoxNolist.clear();
        POdetailslist.clear();

        UserNo = my_dataBase.settingDao().getUserNo();
        Log.e("usenumber", UserNo);
        init();
        try {
            mode = (AudioManager)NewShipment.this.getSystemService(Context.AUDIO_SERVICE);
            mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        }catch (Exception e){
            Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
        }

        fillLastBoxinfo();
        next.setEnabled(false);

        pono.requestFocus();
        boxno.setEnabled(false);
        barcode.setEnabled(false);
        qty.setEnabled(false);

        //

        searchView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBoxList.clear();
                boxno.setText("");
                barcode.setText("");
                if ( allPOs.size() != 0) {
                    showdailogponumber();
                } else
                    Toast.makeText(NewShipment.this, getResources().getString(R.string.nopo), Toast.LENGTH_LONG).show();


            }

        });

        searchView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove redundant boxs
                searchBoxList.clear();
                if (!pono.getText().toString().equals("")) {
                    Set<String> set = new HashSet<String>();

                    for (int i = 0; i < BoxNolist.size(); i++) {

                        if (!BoxNolist.get(i).equals(""))
                            set.add(BoxNolist.get(i));
                    }
                    searchBoxList.addAll(set);

                    if (searchBoxList.size() != 0)
                        showdailogboxnumber();
                    else
                        Toast.makeText(NewShipment.this, getResources().getString(R.string.nobox), Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(NewShipment.this, getResources().getString(R.string.fillpo), Toast.LENGTH_SHORT).show();
            }
        });


        //1. save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userPermissions == null) getUsernameAndpass();
                animation = AnimationUtils.loadAnimation(NewShipment.this, R.anim.modal_in);
                save.startAnimation(animation);

                if (localList.size() != 0) {
                if (userPermissions.getMasterUser().equals("0")) {
                    if (userPermissions.getSHIP_Save().equals("1")) {

                        saveData();

                    } else {
                      //  generalMethod.showSweetDialog(NewShipment.this, 3, getResources().getString(R.string.savePermission), "");

                        new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
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

                    generalMethod.showSweetDialog(NewShipment.this, 3, getResources().getString(R.string.warning), getResources().getString(R.string.fillYourList));

                }


            }

        });

        //2. next box button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animation = AnimationUtils.loadAnimation(NewShipment.this, R.anim.modal_in);
                next.startAnimation(animation);
                pono.setEnabled(false);
                boxno.setText("");
                boxno.setEnabled(true);
                boxno.requestFocus();
                barcode.setText("");
                barcode.setEnabled(false);
                qty.setText("1");
                qty.setEnabled(false);
                ////
                PoQTY.setText("");
                itemname.setText("");
                searchView2.setEnabled(true);
                localList.clear();
                if (adapter != null) adapter.notifyDataSetChanged();


            }
        });


        //3. cancel button
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable() {
                        public void run() {
                            animation = AnimationUtils.loadAnimation(NewShipment.this, R.anim.modal_in);
                            findViewById(R.id.cancel).startAnimation(animation);

                            new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.confirm_title))
                                    .setContentText(getResources().getString(R.string.messageExit))
                                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {


                                            if (localList.size() != 0) {
                                                new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                                                        .setTitleText(getResources().getString(R.string.confirm_title))
                                                        .setContentText(getResources().getString(R.string.messageExit2))
                                                        .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                sweetAlertDialog.dismissWithAnimation();

                                                                localList.clear();
                                                                filladapter(localList);


                                                                Intent intent = new Intent(NewShipment.this, MainActivity.class);
                                                                startActivity(intent);
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
                                                Intent intent = new Intent(NewShipment.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();

                                            }
                                        }

                                    })
                                    .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            barcode.setEnabled(true);
                                            sweetAlertDialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    });


                }catch (Exception e){

                }
            }
        });


        Ship_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userPermissions == null) getUsernameAndpass();
                animation = AnimationUtils.loadAnimation(NewShipment.this, R.anim.modal_in);
                Ship_delete.startAnimation(animation);
                if (userPermissions.getMasterUser().equals("0")) {
                    if (userPermissions.getSHIP_LocalDelete().equals("1")) {
                        OpenDeleteDailog();

                    } else {


                        new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
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

    private void checkFinishPermission() {
        if (userPermissions.getMasterUser().equals("0")) {
            if (userPermissions.getSHIP_Save().equals("1")) {
                save.setEnabled(true);
            } else
                save.setEnabled(false);
        } else {
            save.setEnabled(true);
        }
    }

    private void checkDeletePermission() {
        //   userPermissions.setMasterUser("1");
        if (userPermissions.getMasterUser().equals("0")) {
            if (userPermissions.getSHIP_LocalDelete().equals("1")) {
                Ship_delete.setEnabled(true);
            } else
                Ship_delete.setEnabled(false);
        } else {
            //     Log.e("fffff","fff");
            Ship_delete.setEnabled(true);
        }
    }

    public void OpenDeleteDailog() {
        final Dialog dialog = new Dialog(NewShipment.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.deleteshipments);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();


        dialog.findViewById(R.id.Shdialogcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.sh_deleteitem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticDailog(1);
            }
        });
        dialog.findViewById(R.id.sh_deletebox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticDailog(2);
            }
        });
        dialog.findViewById(R.id.sh_deletePo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticDailog(3);

            }
        });

    }

    public void OpenDeleteDailog2() {
        final Dialog dialog = new Dialog(NewShipment.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.deleteshipments);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();


        dialog.findViewById(R.id.Shdialogcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.sh_deleteitem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticDailog2(1);
            }
        });
        dialog.findViewById(R.id.sh_deletebox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticDailog2(2);
            }
        });
        dialog.findViewById(R.id.sh_deletePo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticDailog2(3);

            }
        });

    }

    private void authenticDailog(int enterflage) {


        authenticationdialog = new Dialog(NewShipment.this);
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
                    ||checkIfUserWhoLoginIsMaster(NewShipment.this,UsNa.getText().toString().trim(),pass.getText().toString().trim())){
                    if (enterflage == 1)
                        openDeleteItemDailog();
                    else if (enterflage == 2) openDeleteBoxDailog();

                    else if (enterflage == 3) openDeletePoDailog();

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
                fillPOinfo(pono.getText().toString().trim());

            }
        });
        authenticationdialog.show();


    }

    private void authenticDailog2(int enterflage) {


        authenticationdialog = new Dialog(NewShipment.this);
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
                            openDeleteItemDailog();
                            authenticationdialog.dismiss();

                        } else if (enterflage == 2) {
                            openDeleteBoxDailog();
                            authenticationdialog.dismiss();

                        } else if (enterflage == 3) {
                            openDeletePoDailog();
                            authenticationdialog.dismiss();
                        }
                        else if (enterflage == 4) {
                            authenticationdialog.dismiss();
                            saveData();

                        }
                    } else {
                        generalMethod.showSweetDialog(NewShipment.this, 3, getResources().getString(R.string.Permission), "");

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
    private void   saveData(){

            shipmentsList = my_dataBase.shipmentDao().getUnpostedShipment("0");
            Log.e("shipmentsListsize", shipmentsList.size() + "");
            for (int i = 0; i < shipmentsList.size(); i++)
                if (shipmentsList.get(i).getDeviceId() == null)
                    shipmentsList.get(i).setDeviceId(deviceId);
            exportData(shipmentsList);

            localList.clear();
            shipmentsList.clear();
            PoQTY.setText("");
            itemname.setText("");
            next.setEnabled(false);

        if (adapter != null) adapter.notifyDataSetChanged();
        pono.setText("");
        pono.setEnabled(true);
        pono.requestFocus();
        boxno.setText("");
        barcode.setText("");
        boxno.setEnabled(false);
        barcode.setEnabled(false);
        qty.setEnabled(false);
        PoQTY.setText("");
        itemname.setText("");
        ////

        searchView1.setEnabled(true);
        importData.BoxNolist.clear();
        importData.POdetailslist.clear();
        fillPOinfo(pono.getText().toString().trim());
        clearLastBoxinfo();
    }
    private void openDeletePoDailog() {
        deletedPOList.clear();
        allpo = my_dataBase.shipmentDao().getallpo();
        Dialog deletePOdialog = new Dialog(NewShipment.this);
        deletePOdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        deletePOdialog.setCancelable(false);
        deletePOdialog.setContentView(R.layout.deletepodailog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(deletePOdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        deletePOdialog.getWindow().setAttributes(lp);

        deletePOdialog.show();
   DS_delete1 = deletePOdialog.findViewById(R.id.DS_delete);
        Button DS_cancel = deletePOdialog.findViewById(R.id.DS_cancel);
        Button DS_dialogsave = deletePOdialog.findViewById(R.id.DS_dialogsave);
        DS_poedit = deletePOdialog.findViewById(R.id.DS_poedit);
        DS_poediteshow = deletePOdialog.findViewById(R.id.DS_poediteshow);
        DS_boxcountediteshow = deletePOdialog.findViewById(R.id.DS_boxcountediteshow);
        DS_Itemcountediteshow = deletePOdialog.findViewById(R.id.DS_Itemcountediteshow);
        DS_poedit.requestFocus();
        DS_delete1.setEnabled(false);
        DS_poedit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != KeyEvent.KEYCODE_ENTER) {

                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (!DS_poedit.getText().toString().equals("")) {
                            if (allpo.contains(DS_poedit.getText().toString().trim())) {


                                filldeletePOdialogDATA();

                                DS_delete1.setEnabled(true);
                            } else {
                                Toast.makeText(NewShipment.this, getString(R.string.noPoData), Toast.LENGTH_SHORT).show();
                                DS_poedit.setText("");
                            }

                        } else {
                            DS_poedit.requestFocus();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        DS_poedit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                if (i != KeyEvent.ACTION_UP) {

                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                            || i == EditorInfo.IME_NULL) {
                        if (!DS_poedit.getText().toString().equals("")) {
                            if (allpo.contains(DS_poedit.getText().toString().trim())) {


                                filldeletePOdialogDATA();

                                DS_delete1.setEnabled(true);
                            } else {
                                Toast.makeText(NewShipment.this, getString(R.string.noPoData), Toast.LENGTH_SHORT).show();
                                DS_poedit.setText("");
                            }

                        } else {
                            DS_poedit.requestFocus();
                        }
                    }
                }

                return true;    }
        });
        deletePOdialog.findViewById(R.id.DS_BACK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePOdialog.dismiss();
                authenticationdialog.dismiss();
            }
        });
        deletePOdialog.findViewById(R.id.DS_poSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dailogNum = 1;
                searchDBpoDailog(allpo);

            }
        });
        DS_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              if(!DS_poedit.getText().toString().equals(""))
              {   new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.confirm_title))
                        .setContentText(getResources().getString(R.string.deletepo1))
                        .setConfirmButton(getResources().getString(R.string.yes),
                                new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        deletedPOList.add(DS_poedit.getText().toString().trim());
                                        allpo.remove(DS_poedit.getText().toString().trim());
                                        DS_poediteshow.setText("");
                                        DS_boxcountediteshow.setText("");
                                        DS_Itemcountediteshow.setText("");
                                        DS_poedit.setText("");
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


            }

        });

        DS_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deletedPOList.size() > 0) {
                    new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.returndata)).setConfirmButton(R.string.yes, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            deletedPOList.clear();
                            allpo.clear();
                            allpo = my_dataBase.shipmentDao().getallpo();
                            DS_poediteshow.setText("");
                            DS_boxcountediteshow.setText("");
                            DS_Itemcountediteshow.setText("");
                            DS_poedit.setText("");
                            DS_poedit.requestFocus();
                            sweetAlertDialog.dismiss();
                        }
                    }).setCancelButton(R.string.no, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
                } else {
                    Toast.makeText(NewShipment.this, getResources().getString(R.string.NODATA), Toast.LENGTH_LONG).show();
                }
            }

        });
        DS_dialogsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deletedPOList.size() > 0) {

                    addDeletePoLoges();
                    checkLocalList(3);
                    for (int i = 0; i < deletedPOList.size(); i++)
                        my_dataBase.shipmentDao().deletePO(deletedPOList.get(i));
                    fillLastBoxinfo();
                    fillPOinfo(pono.getText().toString().trim());
                    Toast.makeText(NewShipment.this, getString(R.string.app_done), Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(NewShipment.this, getString(R.string.nODataChanged), Toast.LENGTH_SHORT).show();
                DS_poediteshow.setText("");
                DS_boxcountediteshow.setText("");
                DS_Itemcountediteshow.setText("");
                DS_poedit.setText("");
                deletePOdialog.dismiss();
                authenticationdialog.dismiss();
                deletedPOList.clear();

            }
        });
    }

    private void addDeletePoLoges() {

        Log.e("addDeletePoLoges", "addDeletePoLoges");
        List<Shipment> list = new ArrayList();
        list.clear();
        for (int i = 0; i < deletedPOList.size(); i++)
            list.addAll(my_dataBase.shipmentDao().getPOShipments(deletedPOList.get(i)));

        for (int i = 0; i < list.size(); i++) {
            ShipmentLogs shipmentLogs = new ShipmentLogs();
            shipmentLogs.setPoNo(list.get(i).getPoNo());
            shipmentLogs.setBoxNo(list.get(i).getBoxNo());
            shipmentLogs.setDiffer(list.get(i).getDiffer());
            shipmentLogs.setNewQty("0");
            shipmentLogs.setPreQty(list.get(i).getQty());
            shipmentLogs.setItemCode(list.get(i).getBarcode());
            shipmentLogs.setLogsDATE(generalMethod.getCurentTimeDate(1));
            shipmentLogs.setLogsTime(generalMethod.getCurentTimeDate(2));

            shipmentLogs.setUserNO(UsNa.getText().toString());
            my_dataBase.shipmentLogsDao().insert(shipmentLogs);
            Log.e("addDeletePoLogesx=", "" + shipmentLogs.getPoNo());
        }
    }

    public static void filldeletePOdialogDATA() {
        DS_poediteshow.setText(DS_poedit.getText().toString().trim());
        String boxcount = my_dataBase.shipmentDao().getboxescount(DS_poedit.getText().toString().trim());
        DS_boxcountediteshow.setText(boxcount);
        long allitemqty = my_dataBase.shipmentDao().getsumofitemsqty(DS_poedit.getText().toString().trim());
        DS_Itemcountediteshow.setText(allitemqty + "");
    }

    private void openDeleteBoxDailog() {
        deletedBoxes.clear();
        BbShip_list.clear();
        BbShip_list = my_dataBase.shipmentDao().getUnpostedShipment("0");
        deleteBoxdialog = new Dialog(NewShipment.this);
        deleteBoxdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        deleteBoxdialog.setCancelable(false);
        deleteBoxdialog.setContentView(R.layout.deleteboxdailog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(deleteBoxdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        deleteBoxdialog.getWindow().setAttributes(lp);
        deleteBoxdialog.show();
        Button Db_delete = deleteBoxdialog.findViewById(R.id.Db_delete);
        Button Db_dialogsave = deleteBoxdialog.findViewById(R.id.Db_dialogsave);
        Button Db_cancel = deleteBoxdialog.findViewById(R.id.Db_cancel);
        Db_boxedit = deleteBoxdialog.findViewById(R.id.Db_boxedit);
        Db_boxSearch = deleteBoxdialog.findViewById(R.id.Db_boxSearch);
        Db_boxnumshow = deleteBoxdialog.findViewById(R.id.Db_boxnumshow);
        Db_Itemcountediteshow = deleteBoxdialog.findViewById(R.id.Db_Itemcountediteshow);
        Dbsh_poedit = deleteBoxdialog.findViewById(R.id.Dbsh_poedit);
        Dbsh_poSearch = deleteBoxdialog.findViewById(R.id.Dbsh_poSearch);
        deleteBoxdialog.findViewById(R.id.Db_BACK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBoxdialog.dismiss();
                authenticationdialog.dismiss();
            }
        });
        Db_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Db_boxedit.getText().toString().trim().equals("") && !Dbsh_poedit.getText().toString().trim().equals(""))
                    new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.deleteitem))
                            .setConfirmButton(getResources().getString(R.string.yes),
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            Shipment shipment = new Shipment();
                                            shipment.setBoxNo(Db_boxedit.getText().toString().trim());
                                            shipment.setPoNo(Dbsh_poedit.getText().toString().trim());
                                            deletedBoxes.add(shipment);


                                            {

                                                for (int i = 0; i < BbShip_list.size(); i++) {
                                                    Log.e("Db_delet", BbShip_list.get(i).getPoNo() + "");
                                                    Log.e("Db_delet", BbShip_list.get(i).getBoxNo() + "");
                                                    if (BbShip_list.get(i).getPoNo().equals
                                                            (Dbsh_poedit.getText().toString().trim()) &&
                                                            BbShip_list.get(i).getBoxNo().equals
                                                                    (Db_boxedit.getText().toString().trim())) {
                                                        Log.e("BbShip_listsizeb", BbShip_list.size() + "");
                                                        Log.e("Db_deleteBbShip_list1", i + "");
                                                        Log.e("Db_deleteBbShip_list2", Dbsh_poedit.getText() + "");
                                                        Log.e("Db_deleteBbShip_list3", BbShip_list.get(i).getPoNo() + "");
                                                        Log.e("Db_deleteBbShip_list4", BbShip_list.get(i).getBoxNo() + "");
                                                        BbShip_list.remove(i);
                                                        i--;
                                                        Log.e("  BbShip_listsizeaf", BbShip_list.size() + "");
                                                    }
                                                }
                                                sweetAlertDialog.dismiss();
                                                Db_boxedit.setText("");
                                                Dbsh_poedit.setText("");
                                                Db_boxnumshow.setText("");
                                                Db_Itemcountediteshow.setText("");
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
        Dbsh_poSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listponame.clear();
                if (BbShip_list.size() > 0) {
                    for (int i = 0; i < BbShip_list.size(); i++) {
                        if (!listponame.contains(BbShip_list.get(i).getPoNo()))
                            listponame.add(BbShip_list.get(i).getPoNo());
                    }
                    dailogNum = 2;
                    searchDBpoDailog(listponame);
                } else {
                    searchDBpoDailog(listponame);
                  //  Toast.makeText(NewShipment.this, getString(R.string.noPoData), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Db_boxSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listboxname.clear();
                if (BbShip_list.size() > 0) {
                    for (int i = 0; i < BbShip_list.size(); i++) {
                        if (BbShip_list.get(i).getPoNo().equals(Dbsh_poedit.getText().toString().trim()))
                            if (!listboxname.contains(BbShip_list.get(i).getBoxNo()))
                                listboxname.add(BbShip_list.get(i).getBoxNo());
                    }
                    dailogNum = 2;
                    searchDBboxDailog(listboxname);
                } else {
                    searchDBboxDailog(listboxname);
                    //Toast.makeText(NewShipment.this, getString(R.string.noBoxData), Toast.LENGTH_SHORT).show();
                }
            }
        });



        Dbsh_poedit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != KeyEvent.KEYCODE_ENTER) {

                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (!Dbsh_poedit.getText().toString().trim().equals("")) {
                            for (int x = 0; x < BbShip_list.size(); x++)
                                if (BbShip_list.get(x).getPoNo().equals(Dbsh_poedit.getText().toString().trim())) {
                                    Db_boxedit.setEnabled(true);
                                    Db_boxedit.requestFocus();
                                    break;
                                } else {
                                    Toast.makeText(NewShipment.this, getString(R.string.noPoData), Toast.LENGTH_SHORT).show();
                                    Dbsh_poedit.setText("");
                                }

                        } else {
                            Dbsh_poedit.requestFocus();
                        }
                        return true;
                    }
                }
                return false;

            }

        });
        Db_boxedit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != KeyEvent.KEYCODE_ENTER) {

                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (!Db_boxedit.getText().toString().trim().equals("")) {

                            long allqtys = my_dataBase.shipmentDao().getsumofboxitemsqty(Dbsh_poedit.getText().toString().trim(), Db_boxedit.getText().toString().trim());
                         if(allqtys!=0)
                         {Db_Itemcountediteshow.setText("" + allqtys);

                             Db_boxnumshow.setText(Db_boxedit.getText().toString().trim());
                         }
                         else {
                            Toast.makeText(NewShipment.this, getString(R.string.noBoxData), Toast.LENGTH_SHORT).show();

                             Db_boxedit.setText("");
                        }

                        } else {
                            Db_boxedit.requestFocus();
                        }


                    }
                    return true;
                }

                return false;

            }
        });

        Dbsh_poedit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                if (i != KeyEvent.ACTION_UP) {

                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                            || i == EditorInfo.IME_NULL) {
                        if (!Dbsh_poedit.getText().toString().trim().equals("")) {
                            for (int x = 0; x < BbShip_list.size(); x++)
                                if (BbShip_list.get(x).getPoNo().equals(Dbsh_poedit.getText().toString().trim())) {
                                    Db_boxedit.setEnabled(true);
                                    Db_boxedit.requestFocus();
                                    break;
                                } else {
                                    Toast.makeText(NewShipment.this, getString(R.string.noPoData), Toast.LENGTH_SHORT).show();
                                    Dbsh_poedit.setText("");
                                }

                        } else {
                            Dbsh_poedit.requestFocus();
                        }
                    }
                }

                return true;    }
        });

        Db_boxedit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                if (i != KeyEvent.ACTION_UP) {

                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                            || i == EditorInfo.IME_NULL) {
                        if (!Db_boxedit.getText().toString().trim().equals("")) {

                            long allqtys = my_dataBase.shipmentDao().getsumofboxitemsqty(Dbsh_poedit.getText().toString().trim(), Db_boxedit.getText().toString().trim());
                            if(allqtys!=0)
                            {Db_Itemcountediteshow.setText("" + allqtys);

                                Db_boxnumshow.setText(Db_boxedit.getText().toString().trim());
                            }
                            else {
                                Toast.makeText(NewShipment.this, getString(R.string.noBoxData), Toast.LENGTH_SHORT).show();

                                Db_boxedit.setText("");
                            }

                        } else {
                            Db_boxedit.requestFocus();
                        }
                    }
                }

                return true;    }
        });

        Db_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deletedBoxes.size() > 0) {
                    new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.returndata)).setConfirmButton(R.string.yes, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            BbShip_list.clear();
                            BbShip_list = my_dataBase.shipmentDao().getUnpostedShipment("0");
                            deletedBoxes.clear();
                            Db_boxedit.setText("");
                            Dbsh_poedit.setText("");
                            Db_boxnumshow.setText("");
                            Db_Itemcountediteshow.setText("");
                            sweetAlertDialog.dismiss();


                        }
                    }).setCancelButton(R.string.no, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
                } else {
                    Toast.makeText(NewShipment.this, getResources().getString(R.string.NODATA), Toast.LENGTH_LONG).show();
                }

            }
        });
        Db_dialogsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (deletedBoxes.size() > 0) {
                    addDeleteBoxsLoges();
                    for (int i = 0; i < deletedBoxes.size(); i++) {

                        int x = my_dataBase.shipmentDao().deleteBox(deletedBoxes.get(i).getPoNo(), deletedBoxes.get(i).getBoxNo());
                        Log.e("dialogsavex", x + "");
                    }
                    checkLocalList(2);
                    deletedBoxes.clear();
                    fillLastBoxinfo();
                    fillPOinfo(pono.getText().toString().trim());
                    Toast.makeText(NewShipment.this, getString(R.string.app_done), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(NewShipment.this, getString(R.string.nODataChanged), Toast.LENGTH_SHORT).show();

                }
                deleteBoxdialog.dismiss();
                authenticationdialog.dismiss();
            }
        });






    }

    private void addDeleteBoxsLoges() {

        List<Shipment> list = new ArrayList();
        list.clear();
        for (int i = 0; i < deletedBoxes.size(); i++)
            list.addAll(my_dataBase.shipmentDao().getBoxsShipments(deletedBoxes.get(i).getPoNo(), deletedBoxes.get(i).getBoxNo()));

        for (int i = 0; i < list.size(); i++) {
            ShipmentLogs shipmentLogs = new ShipmentLogs();
            shipmentLogs.setPoNo(list.get(i).getPoNo());
            shipmentLogs.setBoxNo(list.get(i).getBoxNo());
            shipmentLogs.setDiffer(list.get(i).getDiffer());
            shipmentLogs.setNewQty("0");
            shipmentLogs.setPreQty(list.get(i).getQty());
            shipmentLogs.setItemCode(list.get(i).getBarcode());
            shipmentLogs.setLogsDATE(generalMethod.getCurentTimeDate(1));
            shipmentLogs.setLogsTime(generalMethod.getCurentTimeDate(2));
            shipmentLogs.setUserNO(UsNa.getText().toString());
            my_dataBase.shipmentLogsDao().insert(shipmentLogs);
        }

    }

    private void openDeleteItemDailog() {
        BbShip_list.clear();
        BbShip_list = my_dataBase.shipmentDao().getUnpostedShipment("0");
        copylist(BbShip_list);
        Dialog deleteitemdialog = new Dialog(NewShipment.this);
        deleteitemdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        deleteitemdialog.setCancelable(false);
        deleteitemdialog.setContentView(R.layout.deletesh_itemdailog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(deleteitemdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        deleteitemdialog.getWindow().setAttributes(lp);
        deleteitemdialog.show();
        DIsh_poedit = deleteitemdialog.findViewById(R.id.DIsh_poedit);
        DIsh_boxedit = deleteitemdialog.findViewById(R.id.DIsh_boxedit);
        DIsh_boxSearch = deleteitemdialog.findViewById(R.id.DIsh_boxSearch);
        DIsh_itemcodeedit = deleteitemdialog.findViewById(R.id.DIsh_itemcodeedit);

        DIsh_poediteshow = deleteitemdialog.findViewById(R.id.DIsh_poediteshow);
        DIsh_boxcountediteshow = deleteitemdialog.findViewById(R.id.DIsh_boxcountediteshow);
        DIsh_Itemcodeediteshow = deleteitemdialog.findViewById(R.id.DIsh_Itemcodeediteshow);
        DIsh_qtyedit = deleteitemdialog.findViewById(R.id.DIsh_qtyedit);
        DIsh_preQTY = deleteitemdialog.findViewById(R.id.DIsh_preQTY);
        Button DS_dialogsave = deleteitemdialog.findViewById(R.id.DS_dialogsave);
        Button DS_cancel = deleteitemdialog.findViewById(R.id.DS_cancel);
        Button DS_delete = deleteitemdialog.findViewById(R.id.DS_delete);
        DIsh_boxedit.setEnabled(false);
        DIsh_boxSearch.setEnabled(false);
        DIsh_itemcodeedit.setEnabled(false);
        DS_delete.setEnabled(false);
        deleteitemdialog.findViewById(R.id.DIsh_BACK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteitemdialog.dismiss();
                authenticationdialog.dismiss();
            }
        });
        deleteitemdialog.findViewById(R.id.DIsh_poSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listponame.clear();
                for (int i = 0; i < BbShip_list.size(); i++) {
                    if (!listponame.contains(BbShip_list.get(i).getPoNo()))
                        listponame.add(BbShip_list.get(i).getPoNo());
                }
                dailogNum = 0;
                searchDBpoDailog(listponame);


            }
        });

        DIsh_boxSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listboxname.clear();
                for (int i = 0; i < BbShip_list.size(); i++) {
                    if (BbShip_list.get(i).getPoNo().equals(DIsh_poedit.getText().toString().trim()))
                        if (!listboxname.contains(BbShip_list.get(i).getBoxNo()))
                            listboxname.add(BbShip_list.get(i).getBoxNo());
                }
                dailogNum = 0;
                searchDBboxDailog(listboxname);

            }
        });



        DIsh_poedit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != KeyEvent.KEYCODE_ENTER) {

                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (!DIsh_poedit.getText().toString().equals("")) {
                            DIsh_boxSearch.setEnabled(true);
                            for (int x = 0; x < BbShip_list.size(); x++)
                                if (BbShip_list.get(x).getPoNo().contains(DIsh_poedit.getText().toString().trim())) {
                                    DIsh_boxedit.setEnabled(true);
                                    DIsh_boxedit.requestFocus();
                                    break;
                                } else {
                                    Toast.makeText(NewShipment.this, getString(R.string.noPoData), Toast.LENGTH_SHORT).show();
                                }

                        } else {
                            DIsh_poedit.requestFocus();
                        }
                        return true;
                    }
                }
                return false;

            }
        });
        DIsh_boxedit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != KeyEvent.KEYCODE_ENTER) {

                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (!DIsh_boxedit.getText().toString().equals("")) {
                            for (int x = 0; x < BbShip_list.size(); x++)
                                if (BbShip_list.get(x).getPoNo().equals(DIsh_poedit.getText().toString().trim()) && BbShip_list.get(x).getBoxNo().equals(DIsh_boxedit.getText().toString().trim())) {
                                    DIsh_itemcodeedit.setEnabled(true);
                                    DIsh_itemcodeedit.requestFocus();
                                    break;
                                } else {
                                    Toast.makeText(NewShipment.this, getString(R.string.noBoxData), Toast.LENGTH_SHORT).show();
                                }

                        } else {
                            DIsh_boxedit.requestFocus();
                        }
                        return true;
                    }
                }
                return false;

            }
        });
        DIsh_itemcodeedit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != KeyEvent.KEYCODE_ENTER) {

                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {

                        if (!DIsh_itemcodeedit.getText().toString().equals("")) {
                            int flage = 0;
                            long newqty;
                            DS_delete.setEnabled(true);
                            for (int x = 0; x < BbShip_list.size(); x++)
                                if (BbShip_list.get(x).getPoNo().equals(DIsh_poedit.getText().toString().trim())
                                        && BbShip_list.get(x).getBoxNo().equals(DIsh_boxedit.getText().toString().trim())
                                        && BbShip_list.get(x).getBarcode().equals(DIsh_itemcodeedit.getText().toString().trim())) {
                                    ind = x;
                                    flage = 0;
                                    newqty = Long.parseLong(BbShip_list.get(x).getQty());
                                    if (newqty > 1) {
                                        newqty -= 1;
                                        BbShip_list.get(x).setQty(newqty + "");
                                        if (BbShip_list.get(x).getDiffer() != null && !BbShip_list.get(x).getDiffer().equals(""))
                                            BbShip_list.get(x).setDiffer(String.valueOf(Long.parseLong(BbShip_list.get(x).getDiffer()) - 1));
                                        DIsh_poediteshow.setText(DIsh_poedit.getText().toString().trim());
                                        DIsh_boxcountediteshow.setText(DIsh_boxedit.getText().toString().trim());
                                        DIsh_Itemcodeediteshow.setText(DIsh_itemcodeedit.getText().toString().trim());
                                        String pre = getpreQty(BbShip_list.get(x).getPoNo(), BbShip_list.get(x).getBoxNo(), BbShip_list.get(x).getBarcode());
                                        DIsh_preQTY.setText(pre);
                                        DIsh_qtyedit.setText(BbShip_list.get(x).getQty());
                                        flage = 0;
                                        if (reducedshipmentsList.size() == 0)
                                            reducedshipmentsList.add(BbShip_list.get(x));

                                        else {

                                            if (exists(BbShip_list.get(x).getPoNo(), BbShip_list.get(x).getBoxNo(), BbShip_list.get(x).getBarcode()) == 0)
                                                reducedshipmentsList.add(BbShip_list.get(x));
                                            else {
                                                reducedshipmentsList.get(exists_index).setQty(newqty + "");
                                                reducedshipmentsList.get(exists_index).setDiffer(String.valueOf(Long.parseLong(reducedshipmentsList.get(exists_index).getDiffer()) - 1));
                                            }
                                        }
                                    } else {
                                        DIsh_preQTY.setText("1");
                                        DIsh_poediteshow.setText(DIsh_poedit.getText().toString().trim());
                                        DIsh_boxcountediteshow.setText(DIsh_boxedit.getText().toString().trim());
                                        DIsh_Itemcodeediteshow.setText(DIsh_itemcodeedit.getText().toString().trim());
                                        DIsh_qtyedit.setText("1");

                                        new SweetAlertDialog(NewShipment.this, SweetAlertDialog.BUTTON_CONFIRM)
                                                .setTitleText(getResources().getString(R.string.confirm_title))
                                                .setContentText(getResources().getString(R.string.delete3))
                                                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                        if (reducedshipmentsList.size() > 0) {

                                                            if (exists(BbShip_list.get(ind).getPoNo(), BbShip_list.get(ind).getBoxNo(), BbShip_list.get(ind).getBarcode()) == 0) {
                                                                BbShip_list.get(ind).setQty("0");
                                                                reducedshipmentsList.add(BbShip_list.get(ind));
                                                                BbShip_list.remove(ind);


                                                            } else {
                                                                reducedshipmentsList.remove(exists_index);
                                                                BbShip_list.get(ind).setQty("0");
                                                                reducedshipmentsList.add(BbShip_list.get(ind));
                                                                BbShip_list.remove(ind);

                                                            }


                                                        } else {
                                                            BbShip_list.get(ind).setQty("0");
                                                            reducedshipmentsList.add(BbShip_list.get(ind));
                                                            BbShip_list.remove(ind);
                                                        }

                                                        cleardataOfdeleteitemdialog();

                                                        sweetAlertDialog.dismiss();
                                                        DS_delete.setEnabled(false);
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

                                } else {
                                    flage = 1;
                                }
                            if (flage == 1)
                                Toast.makeText(NewShipment.this, getString(R.string.noItemCodeData), Toast.LENGTH_LONG).show();
                            DIsh_itemcodeedit.setText("");
                        } else {
                            DIsh_itemcodeedit.requestFocus();
                        }
                    }
                }
                return false;

            }

        });

        DIsh_poedit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                if (i != KeyEvent.ACTION_UP) {

                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                            || i == EditorInfo.IME_NULL) {
                        if (!DIsh_poedit.getText().toString().equals("")) {
                            DIsh_boxSearch.setEnabled(true);
                            for (int x = 0; x < BbShip_list.size(); x++)
                                if (BbShip_list.get(x).getPoNo().contains(DIsh_poedit.getText().toString().trim())) {
                                    DIsh_boxedit.setEnabled(true);
                                    DIsh_boxedit.requestFocus();
                                    break;
                                } else {
                                    Toast.makeText(NewShipment.this, getString(R.string.noPoData), Toast.LENGTH_SHORT).show();
                                }

                        } else {
                            DIsh_poedit.requestFocus();
                        }
                    }
                }

                return true;    }
        });


        DIsh_boxedit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                if (i != KeyEvent.ACTION_UP) {

                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                            || i == EditorInfo.IME_NULL) {
                        if (!DIsh_boxedit.getText().toString().equals("")) {
                            for (int x = 0; x < BbShip_list.size(); x++)
                                if (BbShip_list.get(x).getPoNo().equals(DIsh_poedit.getText().toString().trim()) && BbShip_list.get(x).getBoxNo().equals(DIsh_boxedit.getText().toString().trim())) {
                                    DIsh_itemcodeedit.setEnabled(true);
                                    DIsh_itemcodeedit.requestFocus();
                                    break;
                                } else {
                                    Toast.makeText(NewShipment.this, getString(R.string.noBoxData), Toast.LENGTH_SHORT).show();
                                }

                        } else {
                            DIsh_boxedit.requestFocus();
                        }
                    }
                }

                return true;    }
        });

        DIsh_itemcodeedit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                if (i != KeyEvent.ACTION_UP) {

                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                            || i == EditorInfo.IME_NULL) {
                        if (!DIsh_itemcodeedit.getText().toString().equals("")) {
                            int flage = 0;
                            long newqty;
                            DS_delete.setEnabled(true);
                            for (int x = 0; x < BbShip_list.size(); x++)
                                if (BbShip_list.get(x).getPoNo().equals(DIsh_poedit.getText().toString().trim())
                                        && BbShip_list.get(x).getBoxNo().equals(DIsh_boxedit.getText().toString().trim())
                                        && BbShip_list.get(x).getBarcode().equals(DIsh_itemcodeedit.getText().toString().trim())) {
                                    ind = x;
                                    flage = 0;
                                    newqty = Long.parseLong(BbShip_list.get(x).getQty());
                                    if (newqty > 1) {
                                        newqty -= 1;
                                        BbShip_list.get(x).setQty(newqty + "");
                                        if (BbShip_list.get(x).getDiffer() != null && !BbShip_list.get(x).getDiffer().equals(""))
                                            BbShip_list.get(x).setDiffer(String.valueOf(Long.parseLong(BbShip_list.get(x).getDiffer()) - 1));
                                        DIsh_poediteshow.setText(DIsh_poedit.getText().toString().trim());
                                        DIsh_boxcountediteshow.setText(DIsh_boxedit.getText().toString().trim());
                                        DIsh_Itemcodeediteshow.setText(DIsh_itemcodeedit.getText().toString().trim());
                                        String pre = getpreQty(BbShip_list.get(x).getPoNo(), BbShip_list.get(x).getBoxNo(), BbShip_list.get(x).getBarcode());
                                        DIsh_preQTY.setText(pre);
                                        DIsh_qtyedit.setText(BbShip_list.get(x).getQty());
                                        flage = 0;
                                        if (reducedshipmentsList.size() == 0)
                                            reducedshipmentsList.add(BbShip_list.get(x));

                                        else {

                                            if (exists(BbShip_list.get(x).getPoNo(), BbShip_list.get(x).getBoxNo(), BbShip_list.get(x).getBarcode()) == 0)
                                                reducedshipmentsList.add(BbShip_list.get(x));
                                            else {
                                                reducedshipmentsList.get(exists_index).setQty(newqty + "");
                                                reducedshipmentsList.get(exists_index).setDiffer(String.valueOf(Long.parseLong(reducedshipmentsList.get(exists_index).getDiffer()) - 1));
                                            }
                                        }
                                    } else {
                                        DIsh_preQTY.setText("1");
                                        DIsh_poediteshow.setText(DIsh_poedit.getText().toString().trim());
                                        DIsh_boxcountediteshow.setText(DIsh_boxedit.getText().toString().trim());
                                        DIsh_Itemcodeediteshow.setText(DIsh_itemcodeedit.getText().toString().trim());
                                        DIsh_qtyedit.setText("1");

                                        new SweetAlertDialog(NewShipment.this, SweetAlertDialog.BUTTON_CONFIRM)
                                                .setTitleText(getResources().getString(R.string.confirm_title))
                                                .setContentText(getResources().getString(R.string.delete3))
                                                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                        if (reducedshipmentsList.size() > 0) {

                                                            if (exists(BbShip_list.get(ind).getPoNo(), BbShip_list.get(ind).getBoxNo(), BbShip_list.get(ind).getBarcode()) == 0) {
                                                                BbShip_list.get(ind).setQty("0");
                                                                reducedshipmentsList.add(BbShip_list.get(ind));
                                                                BbShip_list.remove(ind);


                                                            } else {
                                                                reducedshipmentsList.remove(exists_index);
                                                                BbShip_list.get(ind).setQty("0");
                                                                reducedshipmentsList.add(BbShip_list.get(ind));
                                                                BbShip_list.remove(ind);

                                                            }


                                                        } else {
                                                            BbShip_list.get(ind).setQty("0");
                                                            reducedshipmentsList.add(BbShip_list.get(ind));
                                                            BbShip_list.remove(ind);
                                                        }

                                                        cleardataOfdeleteitemdialog();

                                                        sweetAlertDialog.dismiss();
                                                        DS_delete.setEnabled(false);
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

                                } else {
                                    flage = 1;
                                }
                            if (flage == 1)
                                Toast.makeText(NewShipment.this, getString(R.string.noItemCodeData), Toast.LENGTH_LONG).show();
                            DIsh_itemcodeedit.setText("");
                        } else {
                            DIsh_itemcodeedit.requestFocus();
                        }
                    }
                }

                return true;    }
        });






        DS_dialogsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("reducedshipmentsiz", reducedshipmentsList.size() + "");
                if (reducedshipmentsList.size() > 0) {
                    for (int i = 0; i < reducedshipmentsList.size(); i++)
                        if (reducedshipmentsList.get(i).getQty().equals("0")) {
                            my_dataBase.shipmentDao().deleteshipment(reducedshipmentsList.get(i).getBarcode()
                                    , reducedshipmentsList.get(i).getPoNo(), reducedshipmentsList.get(i).getBoxNo());
                            ShipmentLogs shipmentLogs = new ShipmentLogs();
                            shipmentLogs.setPoNo(reducedshipmentsList.get(i).getPoNo());
                            shipmentLogs.setBoxNo(reducedshipmentsList.get(i).getBoxNo());
                            shipmentLogs.setDiffer(reducedshipmentsList.get(i).getDiffer());
                            shipmentLogs.setNewQty("0");
                            String pre = getpreQty(reducedshipmentsList.get(i).getPoNo(), reducedshipmentsList.get(i).getBoxNo(), reducedshipmentsList.get(i).getBarcode());
                            shipmentLogs.setPreQty(pre);
                            shipmentLogs.setItemCode(reducedshipmentsList.get(i).getBarcode());
                            shipmentLogs.setLogsDATE(generalMethod.getCurentTimeDate(1));
                            shipmentLogs.setLogsTime(generalMethod.getCurentTimeDate(2));
                            shipmentLogs.setUserNO(UsNa.getText().toString());
                            Log.e("UsNa", UsNa.getText().toString() + "");
                            my_dataBase.shipmentLogsDao().insert(shipmentLogs);
                        } else {
                            my_dataBase.shipmentDao().updateQTY(reducedshipmentsList.get(i).getBarcode()
                                    , reducedshipmentsList.get(i).getPoNo(), reducedshipmentsList.get(i).getBoxNo(), reducedshipmentsList.get(i).getQty(), reducedshipmentsList.get(i).getDiffer());
                            Log.e("CASE2", "CASE2");

                            ShipmentLogs shipmentLogs = new ShipmentLogs();
                            shipmentLogs.setPoNo(reducedshipmentsList.get(i).getPoNo());
                            shipmentLogs.setBoxNo(reducedshipmentsList.get(i).getBoxNo());
                            shipmentLogs.setDiffer(reducedshipmentsList.get(i).getDiffer());
                            shipmentLogs.setNewQty(reducedshipmentsList.get(i).getQty());
                            shipmentLogs.setItemCode(reducedshipmentsList.get(i).getBarcode());
                            String pre = getpreQty(reducedshipmentsList.get(i).getPoNo(), reducedshipmentsList.get(i).getBoxNo(), reducedshipmentsList.get(i).getBarcode());
                            shipmentLogs.setPreQty(pre);

                            shipmentLogs.setLogsDATE(generalMethod.getCurentTimeDate(1));
                            shipmentLogs.setLogsTime(generalMethod.getCurentTimeDate(2));
                            shipmentLogs.setUserNO(UsNa.getText().toString());
                            Log.e("UsNa2", UsNa.getText().toString() + "");
                            my_dataBase.shipmentLogsDao().insert(shipmentLogs);


                        }
                    checkLocalList(1);
                    reducedshipmentsList.clear();
                    deleteitemdialog.dismiss();
                    authenticationdialog.dismiss();
                    Toast.makeText(NewShipment.this, getString(R.string.app_done), Toast.LENGTH_LONG).show();
                    fillLastBoxinfo();
                    fillPOinfo(pono.getText().toString());
                    //clearPOinfo();
                } else {
                    Toast.makeText(NewShipment.this, getString(R.string.nODataChanged), Toast.LENGTH_SHORT).show();
                    deleteitemdialog.dismiss();
                    authenticationdialog.dismiss();
                }


            }
        });
        DS_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reducedshipmentsList.size() > 0) {
                    new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.returndata)).setConfirmButton(R.string.yes, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            reducedshipmentsList.clear();
                            BbShip_list.clear();
                            BbShip_list = my_dataBase.shipmentDao().getUnpostedShipment("0");
                            cleardataOfdeleteitemdialog();
                            sweetAlertDialog.dismiss();
                        }
                    }).setCancelButton(R.string.no, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
                } else {
                    Toast.makeText(NewShipment.this, getResources().getString(R.string.NODATA), Toast.LENGTH_LONG).show();
                }
            }
        });
        DS_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.confirm_title))
                        .setContentText(getResources().getString(R.string.deleteitem))
                        .setConfirmButton(getResources().getString(R.string.yes),
                                new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {


                                        try {
                                            if (reducedshipmentsList.size() > 0) {
                                                if (exists(BbShip_list.get(ind).getPoNo(), BbShip_list.get(ind).getBoxNo(), BbShip_list.get(ind).getBarcode()) == 0) {
                                                    BbShip_list.get(ind).setQty("0");
                                                    reducedshipmentsList.add(BbShip_list.get(ind));
                                                    BbShip_list.remove(ind);


                                                } else {
                                                    reducedshipmentsList.remove(exists_index);
                                                    BbShip_list.get(ind).setQty("0");
                                                    reducedshipmentsList.add(BbShip_list.get(ind));
                                                    BbShip_list.remove(ind);

                                                }
                                            } else {
                                                BbShip_list.get(ind).setQty("0");
                                                reducedshipmentsList.add(BbShip_list.get(ind));
                                                BbShip_list.remove(ind);
                                            }
                                            sweetAlertDialog.dismiss();
                                            cleardataOfdeleteitemdialog();
                                            DIsh_poedit.requestFocus();
                                            DIsh_boxedit.setEnabled(false);
                                            DIsh_itemcodeedit.setEnabled(false);

                                        } catch (Exception e) {

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


    private void checkLocalList(int flage) {

        if (flage == 1) {
            if (localList != null) {
                if (localList.size() > 0) {

                    Log.e("checkLocalList", reducedshipmentsList.size() + "");
                    for (int i = 0; i < reducedshipmentsList.size(); i++) {
                        for (int j = 0; j < localList.size(); j++) {

                            if (localList.get(j).getPoNo().equals(reducedshipmentsList.get(i).getPoNo()) &&
                                    localList.get(j).getBoxNo().equals(reducedshipmentsList.get(i).getBoxNo()) &&
                                    localList.get(j).getBarcode().equals(reducedshipmentsList.get(i).getBarcode())) {

                                if (reducedshipmentsList.get(i).getQty().equals("0")) {

                                    localList.remove(j);
                                    if (adapter != null) adapter.notifyDataSetChanged();
                                } else {

                                    localList.get(j).setQty(reducedshipmentsList.get(i).getQty());
                                    if (adapter != null) adapter.notifyDataSetChanged();
                                }

                            }
                        }
                    }
                }

            }
        } else if (flage == 2) {

            if (localList != null) {
                if (localList.size() > 0) {
                    for (int i = 0; i < deletedBoxes.size(); i++) {
                        for (int j = 0; j < localList.size(); j++) {
                            Log.e("LocalListsize", localList.size() + "");
                            if (localList.get(j).getPoNo().equals(deletedBoxes.get(i).getPoNo()) &&
                                    localList.get(j).getBoxNo().equals(deletedBoxes.get(i).getBoxNo()))


                                localList.remove(j);
                            if (adapter != null) adapter.notifyDataSetChanged();
                        }
                    }

                }
            }
        } else {
            Log.e("LocalListsize", "dddddd" + "");
            if (localList != null) {
                Log.e("LocalListsize", localList.size() + "");
                Log.e("deletedPOList", deletedPOList.size() + "");
                if (localList.size() > 0) {

                    for (int i = 0; i < deletedPOList.size(); i++) {
                        for (int j = 0; j < localList.size(); j++) {
                            Log.e("LocalListsize", localList.size() + "");
                            if (localList.get(j).getPoNo().equals(deletedPOList.get(i)))
                                localList.remove(j);

                        }
                        if (adapter != null) adapter.notifyDataSetChanged();
                    }
                }
            }
        }


    }

    private void cleardataOfdeleteitemdialog() {
        DIsh_poediteshow.setText("");
        DIsh_boxcountediteshow.setText("");
        DIsh_qtyedit.setText("");
        DIsh_preQTY.setText("");
        DIsh_itemcodeedit.setText("");
        DIsh_boxedit.setText("");
        DIsh_poedit.setText("");
        DIsh_Itemcodeediteshow.setText("");
    }

    private int exists(String pono, String box, String itemcode) {
        int flage = 0;
        for (int j = 0; j < reducedshipmentsList.size(); j++)
            if (reducedshipmentsList.get(j).getPoNo().equals(pono)
                    && reducedshipmentsList.get(j).getBoxNo().equals(box)
                    && reducedshipmentsList.get(j).getBarcode().equals(itemcode)) {
                flage = 1;
                exists_index = j;
                break;
            } else {
                flage = 0;
            }
        return flage;
    }

    private String getpreQty(String po, String box, String itemcode) {

        Log.e("zonecopysize", BbShip_listcopy.size() + "");
        String z = "";
        for (int i = 0; i < BbShip_listcopy.size(); i++) {
            if (po.equals(BbShip_listcopy.get(i).getPoNo()) && itemcode.equals(BbShip_listcopy.get(i).getBarcode())
                    && box.equals(BbShip_listcopy.get(i).getBoxNo())) {
                z = BbShip_listcopy.get(i).getQty();
                break;
            } else {
                z = "";
            }

        }
        return z;
    }

    private void copylist(List<Shipment> bbShip_list) {

        BbShip_listcopy.clear();
        List<String> po = new ArrayList<>();
        List<String> box = new ArrayList<>();
        List<String> item = new ArrayList<>();
        List<String> qtys = new ArrayList<>();
        for (int i = 0; i < BbShip_list.size(); i++) {
            po.add(BbShip_list.get(i).getPoNo());
            box.add(BbShip_list.get(i).getBoxNo());
            item.add(BbShip_list.get(i).getBarcode());
            qtys.add(BbShip_list.get(i).getQty());
        }

        for (int i = 0; i < qtys.size(); i++) {
            Shipment shipment = new Shipment();
            shipment.setQty(qtys.get(i));
            shipment.setPoNo(po.get(i));
            shipment.setBarcode(item.get(i));
            shipment.setBoxNo(box.get(i));
            BbShip_listcopy.add(shipment);
        }
    }


    private void searchDBboxDailog(List<String> listboxname) {
        db_boxsearchdialog = new Dialog(NewShipment.this);
        db_boxsearchdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        db_boxsearchdialog.setCancelable(true);
        db_boxsearchdialog.setContentView(R.layout.db_boxlistdailog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(db_boxsearchdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        db_boxsearchdialog.getWindow().setAttributes(lp);
        db_boxsearchdialog.show();


        ListView listView = db_boxsearchdialog.findViewById(R.id.DIsh_boxlistview);
        D_SH_boxsearchAdapter adapter = new D_SH_boxsearchAdapter(NewShipment.this, listboxname);
        listView.setAdapter(adapter);
    }

    private void searchDBpoDailog(List<String> polist) {
        db_posearchdialog = new Dialog(NewShipment.this);
        db_posearchdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        db_posearchdialog.setCancelable(true);
        db_posearchdialog.setContentView(R.layout.db_polistdailog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(db_posearchdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        db_posearchdialog.getWindow().setAttributes(lp);
        db_posearchdialog.show();

        ListView listView = db_posearchdialog.findViewById(R.id.DIsh_listview);
        D_SH_PosearchAdapter adapter = new D_SH_PosearchAdapter(NewShipment.this, polist);
        listView.setAdapter(adapter);


    }

    TextView.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {


            Log.e("keyEvent.getAction()", keyEvent.getAction() + "");


            if (i == KeyEvent.KEYCODE_BACK) {
           //     onBackPressed();


            }

           else if (i != KeyEvent.KEYCODE_ENTER) {
                {

                    if (keyEvent.getAction() == KeyEvent.ACTION_UP)

                        switch (view.getId()) {

                            case R.id.poNotxt: {

                                if (!pono.getText().toString().trim().equals("")) {

                                    New_getboxData();
                                    next.setEnabled(false);

                                } else {
                                    pono.requestFocus();
                                    try {


                                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                        // Vibrate for 1000 milliseconds
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                        } else {
                                            //deprecated in API 26
                                            v.vibrate(1000);
                                        }
                                    }catch (Exception e){
                                        Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                break;
                            }
                            case R.id.boxNotxt: {
                                next.setEnabled(true);

                                pono.setEnabled(false);
                                if (!boxno.getText().toString().trim().equals("")) {
                                    if (checkboxvalidty()) {


                                        barcode.setEnabled(true);
                                        barcode.requestFocus();
                                        break;


                                    } else {
                                        try {


                                            if (mode != null)
                                                mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                            // Vibrate for 1000  milliseconds
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                            } else {
                                                //deprecated in API 26
                                                v.vibrate(1000);
                                            }
                                            Log.e("ar==", "ar");

                                            showconfirmBoxNoDialog();
                                        }catch (Exception e){
                                            Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                } else
                                { boxno.requestFocus();
                                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    // Vibrate for 1000  milliseconds
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        v.vibrate(VibrationEffect.createOneShot(1000 , VibrationEffect.DEFAULT_AMPLITUDE));
                                    } else {
                                        //deprecated in API 26
                                        v.vibrate(1000 );
                                    }

                                }


                                break;
                            }

                            case R.id.barCodetxt: {
                                next.setEnabled(true);

                                Log.e("barcodeedittxt", barcode.getText().toString());
                                Qty = "1";
                                if (barcode.getText().toString().trim().length() != 0) {

                                    if (barcode.getText().toString().trim().length() <= 15) {
                                        barcode.setEnabled(false);
                                        Shipment shipment = new Shipment();
                                        shipment.setPoNo(pono.getText().toString().trim());
                                        shipment.setUserNO(UserNo);
                                        shipment.setBoxNo(boxno.getText().toString().trim());
                                        shipment.setBarcode(barcode.getText().toString().trim());
                                        CheckShipmentObject(shipment);

                                        boxno.setEnabled(false);
                                        boxno.setEnabled(false);
                                        // getPOdetails();
                                    } else {


                                      try {


                                        if(mode!=null)  mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                        showalertdaiog(NewShipment.this,"Invalid Item  ");
                                        //showSweetDialog(NewShipment.this,0,"","Invalid Item  "+barcode.getText());

                                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                        // Vibrate for 1000  milliseconds
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            v.vibrate(VibrationEffect.createOneShot(1000 , VibrationEffect.DEFAULT_AMPLITUDE));
                                        } else {
                                            //deprecated in API 26
                                            v.vibrate(1000 );
                                        }
                                        barcode.setText("");
                                        barcode.setEnabled(true);
                                        barcode.requestFocus();}
                                      catch (Exception e){
                                          Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                                      }
                                    }
                                } else
                                {
                                    barcode.requestFocus();

                                    try {


                                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    // Vibrate for 1000  milliseconds
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        v.vibrate(VibrationEffect.createOneShot(1000 , VibrationEffect.DEFAULT_AMPLITUDE));
                                    } else {
                                        //deprecated in API 26
                                        v.vibrate(1000 );
                                    }
                                 }catch (Exception e){
                                        Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                break;


                            }

                        }
                }

                return true;
            }
            return false;
        }
    };


    //4. actions will done when user bress next key in keyboard
    TextView.OnEditorActionListener onEditAction = new TextView.OnEditorActionListener() {


        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
          // Log.e("onEditorAction",""+keyEvent.getAction()+"\t"+i);
            if (i != KeyEvent.ACTION_UP) {

                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_NULL) {
                    switch (textView.getId()) {
                        case R.id.poNotxt: {

                            if (!pono.getText().toString().trim().equals("")) {

                                New_getboxData();
                                next.setEnabled(false);

                            } else {
                                pono.requestFocus();


                            }
                            break;
                        }
                        case R.id.boxNotxt: {
                            next.setEnabled(true);
                            Log.e("", barcode.getText().toString());
                            pono.setEnabled(false);
                            if (!boxno.getText().toString().trim().equals("")) {
                                if (checkboxvalidty()) {


                                    barcode.setEnabled(true);
                                    barcode.requestFocus();
                                    break;


                                } else {

                                    showconfirmBoxNoDialog();


                                }
                            } else
                            { boxno.requestFocus();


                            }


                            break;
                        }

                        case R.id.barCodetxt: {
                            next.setEnabled(true);

                            Log.e("barcodeedittxt", barcode.getText().toString());
                            Qty = "1";
                            if (barcode.getText().toString().trim().length() != 0) {

                                if (barcode.getText().toString().trim().length() <= 15) {
                                    barcode.setEnabled(false);
                                    Shipment shipment = new Shipment();
                                    shipment.setPoNo(pono.getText().toString().trim());
                                    shipment.setUserNO(UserNo);
                                    shipment.setBoxNo(boxno.getText().toString().trim());
                                    shipment.setBarcode(barcode.getText().toString().trim());
                                    CheckShipmentObject(shipment);

                                    boxno.setEnabled(false);
                                    boxno.setEnabled(false);
                                    // getPOdetails();
                                } else {
                                    //  barcode.setError(getResources().getString(R.string.invalid));
                                    showSweetDialog(NewShipment.this,0,"","Invalid Item  "+barcode.getText());


                                    barcode.setText("");
                                    barcode.setEnabled(true);
                                    barcode.requestFocus();
                                }
                            } else
                            {
                                barcode.requestFocus();

                            }
                            break;

                        }

                    }
                }

            }

            return true;
        }
    };

    @Override
    public void onBackPressed() {

        showExitDialog();


    }


    private void showExitDialog() {
        Handler h = new Handler(Looper.getMainLooper());
        h.post(new Runnable() {
            public void run() {
                new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.confirm_title))
                        .setContentText(getResources().getString(R.string.messageExit))
                        .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {


                                if (localList.size() != 0) {
                                    new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText(getResources().getString(R.string.confirm_title))
                                            .setContentText(getResources().getString(R.string.messageExit2))
                                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();

                                                    localList.clear();
                                                    filladapter(localList);


                                          /*  Intent intent = new Intent(NewShipment.this, MainActivity.class);
                                            startActivity(intent);*/
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
                                    Intent intent = new Intent(NewShipment.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }

                        })
                        .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                barcode.setEnabled(true);
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .show();
                     }
        });



    }


    private void showconfirmBoxNoDialog() {
        new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.confirm_title))
                .setContentText(getResources().getString(R.string.boxconfirm)+" "+boxno.getText().toString())
                .setConfirmButton(getResources().getString(R.string.yes),
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                barcode.setEnabled(true);
                                barcode.requestFocus();

                                sweetAlertDialog.dismiss();
                                try {
                                    if(mode!=null)mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                                }catch (Exception e){
                                    Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        try {


                        if(mode!=null)mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        boxno.setText("");
                        boxno.setEnabled(true);
                        boxno.requestFocus();
                        sweetAlertDialog.dismiss();
                        }catch (Exception e){
                            Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();


    }


    private void getPOdetails() {

        barCode = barcode.getText().toString().trim();
        importData.getPOdetails();
    }
    private void New_getPOdetails() {
        POdetailslist.clear();
        barCode = barcode.getText().toString().trim();
        NewAllPOsInfo shipment=my_dataBase.newAllPOsInfoDao().getPOdetails(poNo.trim() ,NewShipment.barCode.trim());
        POdetailslist.add(shipment);
if(shipment!=null)
    NewShipment.respon.setText("ItemOCode");
                    else
        NewShipment.respon.setText("invlalid");
    }
    public void exportData(List<Shipment> shipmentList) {
        exportData.exportShipmentsList(shipmentList);
    }

    private static void getboxData() {
        Log.e("newshipment", "getboxData");
        poNo = pono.getText().toString().trim();
        importData.getboxno();


    }

    private void CheckShipmentObject(Shipment shipment) {
        //check obj is in local list
        if (CheckIsExistsINLocalList(shipment.getBarcode(), shipment.getBoxNo(), shipment.getPoNo())) {
            Log.e("CheckShipmentObject", "CheckIsExistsINLocalList");
            localList.get(pos).setQty(String.valueOf(Long.parseLong(localList.get(pos).getQty()) + (Long.parseLong((Qty)))));
            if (localList.get(pos).getIsNew().equals("0")) {
                localList.get(pos).setDiffer(String.valueOf(Long.parseLong(localList.get(pos).getDiffer()) + Long.parseLong("1")));
                PoQTY.setText(localList.get(pos).getPoqty());
                itemname.setText(localList.get(pos).getItemname());

            } else {
                localList.get(pos).setDiffer("0");
            }


            updateAdpapter();

            updateRow(localList.get(pos).getBarcode(), localList.get(pos).getPoNo(), localList.get(pos).getBoxNo(), localList.get(pos).getQty(), localList.get(pos).getDiffer());
            fillLastBoxinfo();
            fillPOinfo(pono.getText().toString().trim());
            barcode.setText("");
            barcode.setEnabled(true);
            barcode.requestFocus();

        } else {

            ArrayList<Shipment> List = new ArrayList<>();
            if (CheckIsExistsINDB(shipment)) {

                //show item in rec
                Log.e("listgetQty", "" + DbShip.getQty());
                DbShip.setQty(String.valueOf(Long.parseLong(DbShip.getQty()) + Long.parseLong("1")));
                if (DbShip.getIsNew().equals("0")) {
                    DbShip.setDiffer(String.valueOf(Long.parseLong(DbShip.getDiffer()) + Long.parseLong("1")));
                    PoQTY.setText(DbShip.getPoqty());
                    itemname.setText(DbShip.getItemname());

                } else {
                    // localList.get(pos).setDiffer("0");
                    DbShip.setDiffer("0");
                }
                localList.add(DbShip);
                pos = localList.size() - 1;
                filladapter(localList);
                //localList.add();

                updateRow(localList.get(pos).getBarcode(), localList.get(pos).getPoNo(), localList.get(pos).getBoxNo(), localList.get(pos).getQty(), localList.get(pos).getDiffer());
                fillLastBoxinfo();
                fillPOinfo(pono.getText().toString().trim());
//            Log.e("shipment.getQty",shipment.getQty());

                barcode.setText("");
                barcode.setEnabled(true);
                barcode.requestFocus();
            } else {
                Log.e("else", "not in local and db");
                New_getPOdetails();
          /*   localList.clear();
            saveRow(shipment);
            localList.add(shipment);
            filladapter(localList);*/

            }

        } //end of CheckIsExistsINLocalList

    }

    private boolean CheckNewShipmentObject(Shipment shipment) {


        boolean fl = false;
        try {
            ArrayList<Shipment> List = new ArrayList<>();

            if (CheckNewIsExistsINDB(shipment)) {
                Log.e("CheckIsExistsINDB", "getIsPosted===" + list.get(0).getIsPosted());

                fl = true;


            } else {

                fl = false;
                Log.e("CheckIsExistsINDB2", "true4");
            }
            Log.e("CheckIsExistsINDB", "fl===" + fl);
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }

        return fl;
    }

    private boolean CheckIsExistsINDB(Shipment shipment) {
        Log.e("CheckIsExistsINDB", "CheckIsExistsINDB");

        DbShip = my_dataBase.shipmentDao().getShipments(shipment.getBarcode(), shipment.getPoNo(), shipment.getBoxNo());
        if (DbShip != null) {
            Log.e("listgetQty", "" + DbShip.getQty());
            localList.clear();
            return true;

        } else
            return false;

    }

    private boolean CheckNewIsExistsINDB(Shipment shipment) {
        Log.e("CheckIsExistsINDB", "CheckIsExistsINDB");

        list = my_dataBase.shipmentDao().getNEWShipments(shipment.getBarcode(), shipment.getPoNo(), shipment.getBoxNo());

        Log.e("CheckIsExists", "" + list.size());
        if (list.size() > 0) {
            localList.clear();
            return true;

        } else
            return false;

    }

    private boolean CheckIsExistsINLocalList(String barcode, String boxno, String pono) {


        boolean flag = false;
        if (localList.size() != 0)
            for (int i = 0; i < localList.size(); i++) {

                if (convertToEnglish(localList.get(i).getBoxNo()).equals(convertToEnglish(boxno))
                        && convertToEnglish(localList.get(i).getBarcode()).equals(convertToEnglish(barcode))
                        && convertToEnglish(localList.get(i).getPoNo()).equals(convertToEnglish(pono)
                )) {
                    pos = i;
                    flag = true;
                    break;

                } else {
                    flag = false;
                    continue;
                }
            }

        return flag;


    }

    private boolean NewCheckIsExistsINLocalList(String barcode, String boxno, String pono) {


        boolean flag = false;
        if (localList.size() != 0)
            for (int i = 0; i < localList.size(); i++) {

                if (
                        convertToEnglish(localList.get(i).getBarcode()).equals(convertToEnglish(barcode))

                ) {
                    pos = i;
                    flag = true;
                    break;

                } else {
                    flag = false;
                    continue;
                }
            }

        return flag;


    }


    public long getDiff(long qty) {


        if (sum >= qty) {
            sum -= qty;
            return -sum;

        } else {
            return Math.abs(sum);
        }

    }


    private void filladapter(java.util.List<Shipment> shipmentList) {

        recyclerView.setLayoutManager(new LinearLayoutManager(NewShipment.this));
        adapter = new ShipmentAdapter(NewShipment.this, shipmentList);
        recyclerView.setAdapter(adapter);
           fillLastBoxinfo();
        fillPOinfo(pono.getText().toString().trim());
    }

    public void ScanCode(View view) {
        switch (view.getId()) {

            case R.id.barcodescan:
                readBarcode(3);


        }
    }

    private void readBarcode(int type) {
        //new IntentIntegrator(AddZone.this).setOrientationLocked(false).setCaptureActivity(CustomScannerActivity.class).initiateScan();

        openSmallCapture(type);
    }

    private void openSmallCapture(int type) {
        if (ContextCompat.checkSelfPermission(NewShipment.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NewShipment.this, new String[]{Manifest.permission.CAMERA}, REQUEST_Camera_Barcode);
            if (ContextCompat.checkSelfPermission(NewShipment.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {//just for first time
                Log.e("requestresult", "PERMISSION_GRANTED");
                Intent i = new Intent(NewShipment.this, ScanActivity.class);
                i.putExtra("key", "1");
                startActivity(i);
                // searchByBarcodeNo(s + "");
            }
        } else {
            Intent i = new Intent(NewShipment.this, ScanActivity.class);
            if (type == 3) {
                i.putExtra("key", "3");
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
                Toast.makeText(NewShipment.this, getString(R.string.cancelled), Toast.LENGTH_SHORT).show();


            } else {
                String valueBarcode = Result.getContents();

            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void fillLastBoxinfo2() {
        Log.e("fillLastBoxinfo", "fillLastBoxinfo");
        try {

            Shipment shipment = my_dataBase.shipmentDao().getlastbox();
         if(shipment!=null) {  lastpo.setText(shipment.getPoNo().toString());
            lastbox.setText(shipment.getBoxNo().toString());
            long sum = my_dataBase.shipmentDao().getsumofboxitemsqty2(shipment.getPoNo(), shipment.getBoxNo());
            itemcount.setText(sum + "");}
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }

    }
    public void fillLastBoxinfo() {
        Log.e("fillLastBoxinfo2", "fillLastBoxinfo2");
        try {

            Shipment shipment = my_dataBase.shipmentDao().getlastbox2();
            if(shipment!=null) {  lastpo.setText(shipment.getPoNo().toString());
                lastbox.setText(shipment.getBoxNo().toString());
                long sum = my_dataBase.shipmentDao().getsumofboxitemsqty3(shipment.getPoNo(), shipment.getBoxNo());
                itemcount.setText(sum + "");}
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }

    }

    public void fillPOinfo(String s) {
              clearPOinfo();
        try {
            numBOx.clear();

            ///
            POlist = my_dataBase.shipmentDao().getPOShipments(s);
            currentpo.setText(POlist.get(0).getPoNo());

            //      filladapter( POlist);

            ///
            for (int i = 0; i < POlist.size(); i++) {
                if (!numBOx.contains(POlist.get(i).getBoxNo())) {
                    Log.e("numBOx", numBOx.size() + "");
                    numBOx.add(POlist.get(i).getBoxNo());
                }
            }

            Set<String> set = new HashSet<String>();

            for (int i = 0; i < numBOx.size(); i++) {
                Log.e("inputif", numBOx.size() + "");
                set.add(numBOx.get(i));
            }
            for (String s1 : set) {
                Log.e("setcontent", s1);
            }

            currentbox.setText(set.size() + "");
            long sum = my_dataBase.shipmentDao().getsumofqty(s);
            Log.e("sum", sum + "");
            itemscounts.setText(sum + "");
        } catch (Exception e) {

        }

    }

    public void clearLastBoxinfo() {
        try {

            lastpo.setText("");
            lastbox.setText("");
            itemcount.setText("");
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }

    }

    public void clearPOinfo() {
        try {
            currentpo.setText("");
            currentbox.setText("");
            itemscounts.setText("");
        } catch (Exception e) {

        }
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

    private void init() {
        if (userPermissions == null) getUsernameAndpass();
        MainActivity.exportFromMainAct = false;
        localList.clear();
        HorizontalScroll=findViewById(R.id.HorizontalScroll);
        HorizontalScroll.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                 barcode.requestFocus();
               // HorizontalScroll.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        HorizontalScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                barcode.requestFocus();
            }
        });
        exportData = new ExportData(NewShipment.this);
        importData = new ImportData(NewShipment.this);
        poststate = findViewById(R.id.poststate);
        Ship_delete = findViewById(R.id.Ship_delete);
        POs=new ArrayList();
        POs=my_dataBase.newAllPOsInfoDao().getAll();

        allPOs=new ArrayList();
        allPOs.clear();
        for(int i=0;i<POs.size();i++)
        if(!allPOs.contains(POs.get(i)))allPOs.add(POs.get(i));
   //     allPOs=my_dataBase.newAllPOsInfoDao().getAll();

//
//       if(allPOs.size()==0) {
//
//           getPoNu();
//
//       }
        PONO_respon= findViewById(R.id.PONO_respon);
        next = findViewById(R.id.nextbox);
        boxnorespon = findViewById(R.id.boxnorespon);
        respon = findViewById(R.id.respon);
        respon.setText("");
        pono = findViewById(R.id.poNotxt);
        boxno = findViewById(R.id.boxNotxt);
        barcode = findViewById(R.id.barCodetxt);
        qty = findViewById(R.id.RecQtytxt);
        recyclerView = findViewById(R.id.shipRec);
        barcodescan = findViewById(R.id.barcodescan);
        save = findViewById(R.id.save);
        searchView1 = findViewById(R.id.ponoSearch);
        searchView2 = findViewById(R.id.boxnoSearch);
        ponoClose = findViewById(R.id.ponoClose);
        // ponoClose.setVisibility(View.GONE);
        PONO_respon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 if( s.length()!=0){
                     my_dataBase.allPOsDao().deleteAll();
                     my_dataBase.allPOsDao().insertAll( PoNolist);
                     NewShipment.allPOs=my_dataBase.allPOsDao().getAll();

                 }
            }
        });
        ponoClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pono.getText().toString().equals("")) {
                    updatePo(pono.getText().toString().trim());


                } else {
                    pono.setError(getResources().getString(R.string.reqired_filled));
                }
            }
        });
        try {
            appSettings = my_dataBase.settingDao().getallsetting();
        } catch (Exception e) {
        }

        if (appSettings.size() != 0) {
            deviceId = appSettings.get(0).getDeviceId();
            Log.e("appSettings", "+" + deviceId);

        }
        currentpo = findViewById(R.id.currentpo);
        currentbox = findViewById(R.id.currentbox);
        itemscounts = findViewById(R.id.itemcount);
        lastpo = findViewById(R.id.LASTpo);
        lastbox = findViewById(R.id.LASTbox);
        itemcount = findViewById(R.id.itemcount2);
        ;
        // qty.setOnEditorActionListener(onEditAction);
        pono.setOnEditorActionListener(onEditAction);
        boxno.setOnEditorActionListener(onEditAction);
        barcode.setOnEditorActionListener(onEditAction);


        pono.setOnKeyListener(onKeyListener);
        boxno.setOnKeyListener(onKeyListener);
        barcode.setOnKeyListener(onKeyListener);
        generalMethod = new GeneralMethod(NewShipment.this);
        requestQueue = Volley.newRequestQueue(this);

        itemname = findViewById(R.id.Itemnametxt);

//        itemname.setScroller(new Scroller(NewShipment.this));
//        itemname.setMaxLines(1);
//        itemname.setVerticalScrollBarEnabled(true);
//        itemname.setMovementMethod(new ScrollingMovementMethod());

        PoQTY = findViewById(R.id.PoQtytxt);

        respon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {
                    if (editable.toString().equals("invlalid")) {
                        Log.e("respon:afterTextChanged", "" + respon.getText().toString());
                        Qty = "1";
                        newshipment = new Shipment();
                        newshipment.setPoNo(convertToEnglish(pono.getText().toString().trim()));
                        newshipment.setBoxNo(convertToEnglish(boxno.getText().toString().trim()));
                        newshipment.setReceivedBox("");
                        newshipment.setBarcode(convertToEnglish(barcode.getText().toString().trim()));
                        newshipment.setUserNO(UserNo);



                        if(barcode.getText().toString().trim().length() <= 15)    {
                        if (CheckNewShipmentObject(newshipment) == false
                                && !barcode.getText().equals("")
                                && barcode.getText().toString().trim().length() <= 15) {


                            try {


                            Log.e("barcodehere", barcode.getText().toString());
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            // Vibrate for 1000  milliseconds
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(1000 , VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                //deprecated in API 26
                                v.vibrate(1000 );
                            }
                            if(mode!=null)mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                            showConfirmBarcodeDailog();
                            }catch (Exception e){
                                Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.e("aya", "=======");



                            if (list.size() > 0) {
                                if (list.get(0).getIsPosted().trim().equals("1")) {
                                    /*  list.get(0). setPoNo(convertToEnglish(pono.getText().toString().trim()));
                                      list.get(0).setBoxNo(convertToEnglish(boxno.getText().toString().trim()));
                                      list.get(0).setBarcode(convertToEnglish(barcode.getText().toString().trim()));*/


                                    list.get(0).setPoqty("1");
                                    list.get(0).setDiffer("");

                                    list.get(0).setQty("1");
                                    list.get(0).setIsPosted("0");
                                    list.get(0).setShipmentTime(String.valueOf(generalMethod.getCurentTimeDate(2)));
                                    list.get(0).setShipmentDate(String.valueOf(generalMethod.getCurentTimeDate(1)));
                                    list.get(0).setIsNew("1");
                                    list.get(0).setUserNO(UserNo);
                                    list.get(0).setItemname("");

                                    Shipment newShipment = new Shipment();
                                    newShipment.setPoNo(list.get(0).getPoNo());
                                    newShipment.setBoxNo(list.get(0).getBoxNo());
                                    newShipment.setBarcode(list.get(0).getBarcode());
                                    newShipment.setReceivedBox("");
                                    newShipment.setPoqty("1");
                                    newShipment.setDeviceId(deviceId);
                                    newShipment.setDiffer("");
                                    newShipment.setQty("1");
                                    newShipment.setIsPosted("0");
                                    newShipment.setShipmentTime(String.valueOf(generalMethod.getCurentTimeDate(2)));
                                    newShipment.setShipmentDate(String.valueOf(generalMethod.getCurentTimeDate(1)));
                                    newShipment.setIsNew("1");
                                    newShipment.setUserNO(UserNo);
                                    newShipment.setItemname("");
                                    localList.add(newShipment);
                                    filladapter(localList);
                                    Log.e("CheckIsExistsINDB2", "true2");
                                    my_dataBase.shipmentDao().insert(newShipment);



                                    barcode.setText("");
                                    barcode.setEnabled(true);
                                    barcode.requestFocus();



                                } else {


                                    list.get(0).setQty(String.valueOf(Long.parseLong(list.get(0).getQty()) + Long.parseLong("1")));
                                    localList.add(list.get(0));
                                    Log.e("CheckIsExistsINDB2", "true3");
                                    updateRow(list.get(0).getBarcode(), list.get(0).getPoNo(), list.get(0).getBoxNo(), list.get(0).getQty(), "0");
                                    filladapter(localList);
                                    barcode.setText("");
                                    barcode.setEnabled(true);
                                    barcode.requestFocus();

                                }
                            } else {

                                //   barcode.setError(getResources().getString(R.string.invalid));
                                Handler h = new Handler(Looper.getMainLooper());
                                h.post(new Runnable() {
                                    public void run() {
                                        showSweetDialog(NewShipment.this, 0, "", "Invalid Item  " + barcode.getText());
                                        barcode.setText("");
                                        barcode.setEnabled(true);
                                        barcode.requestFocus();
                                    }
                                });

                                try {
                                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                                    // Vibrate for 1000  milliseconds
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                    } else {
                                        //deprecated in API 26
                                        v.vibrate(1000);
                                    }
                                }catch (Exception e){
                                    Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                                }

                            }
                        }}else {
                            try {
                                if (mode != null)
                                    mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                Handler h = new Handler(Looper.getMainLooper());
                                h.post(new Runnable() {
                                    public void run() {

                                        showalertdaiog(NewShipment.this, "Invalid Item  " );

                                    }
                                });

                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                // Vibrate for 1000  milliseconds
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                } else {
                                    //deprecated in API 26
                                    v.vibrate(1000);
                                }
                                barcode.setText("");
                                barcode.setEnabled(true);
                                barcode.requestFocus();

                            }catch (Exception e){
                                Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                            }

                            //  barcode.setText("");
                        /*    barcode.setEnabled(true);
                            barcode.requestFocus();*/
                        }
                    }
                    if (editable.toString().equals("ItemOCode")) {

                        try {





                            //NewShipment.respon.setText(POdetailslist.get(0).getBarcode().toString());
                            itemname.setText(POdetailslist.get(0).getITEMCODE());
                            Log.e("itemname",itemname.getText().toString());
                            PoQTY.setText(POdetailslist.get(0).getQTY());
                            ImportData.poqty = POdetailslist.get(0).getQTY();
                            //



                            ImportData.posize = POdetailslist.size();


                            Log.e("afterTextChanged", "" + POdetailslist.get(0).getQTY() + "");
                            if (Long.parseLong(POdetailslist.get(0).getQTY()) > 0) {
                                sum = Long.parseLong(POdetailslist.get(0).getQTY().toString());
                                {


                                    Shipment shipment = new Shipment();
                                    CheckFlag = 0;
                                    shipment.setPoNo(convertToEnglish(pono.getText().toString().trim()));
                                    shipment.setBoxNo(convertToEnglish(boxno.getText().toString().trim()));
                                    shipment.setBarcode(convertToEnglish(barcode.getText().toString().trim()));
                                    Qty = "1";
                                    shipment.setQty(Qty);
                                    shipment.setIsPosted("0");
                                    long qty = Long.parseLong(Qty);
                                    shipment.setIsNew("0");
                                    shipment.setDeviceId(deviceId);
                                    shipment.setShipmentTime(String.valueOf(generalMethod.getCurentTimeDate(2)));
                                    shipment.setShipmentDate(String.valueOf(generalMethod.getCurentTimeDate(1)));
                                    shipment.setPoqty(POdetailslist.get(0).getQTY());
                                    PoQTY.setText(POdetailslist.get(0).getQTY());
                                    itemname.setText(POdetailslist.get(0).getITEMCODE());
                                    shipment.setItemname(POdetailslist.get(0).getITEMCODE());
                                    shipment.setReceivedBox(convertToEnglish(POdetailslist.get(0).getBOXNO()));
                                    shipment.setUserNO(UserNo);
                                    long differ = getDiff(qty);
                                    if (differ > 0)
                                        shipment.setDiffer("+" + differ);
                                    else
                                        shipment.setDiffer(differ + "");


                                    respon.setText("");
                                    pono.setEnabled(false);

                                    boxno.setEnabled(false);
                                    next.setEnabled(true);


                                    itemname.setText("");
                                    //PoQTY.setText("");
                                    if (barcode.getText().toString().length() <= 15) {
                                        localList.clear();
                                        saveRow(shipment);
                                        localList.add(shipment);


                                        filladapter(localList);

                                        barcode.setText("");
                                        barcode.setEnabled(true);
                                        barcode.requestFocus();

                                    } else {
                                        barcode.setText("");
                                        try {


                                            if (mode != null)
                                                mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                            //  barcode.setError(getResources().getString(R.string.invalid));
                                            Handler h = new Handler(Looper.getMainLooper());
                                            h.post(new Runnable() {
                                                public void run() {
                                                    showalertdaiog(NewShipment.this, "Invalid Item  " + barcode.getText());

                                                }
                                            });

                                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                            // Vibrate for 1000  milliseconds
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                                            } else {
                                                //deprecated in API 26
                                                v.vibrate(1000);
                                            }

                                        }catch (Exception e){
                                            Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                                        }
                                        barcode.setText("");
                                        barcode.setEnabled(true);
                                        barcode.requestFocus();

                                    }
                                }
                            } else {
                              //  Toast.makeText(NewShipment.this, getResources().getString(R.string.notvaildqty), Toast.LENGTH_SHORT).show();
                                if(mode!=null)mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                Handler h = new Handler(Looper.getMainLooper());
                                h.post(new Runnable() {
                                    public void run() {
                                        showalertdaiog(NewShipment.this,getResources().getString(R.string.notvaildqty));

                                    }
                                });
                try {


                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 1000  milliseconds
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        v.vibrate(1000);
                    }
                }catch (Exception e){
                    Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                }


                                barcode.setText("");
                                barcode.setEnabled(true);
                                barcode.requestFocus();
                            }

                        } catch (NumberFormatException e) {
                            Log.e("NumberFormatException", "" + e.getMessage() + "");
                        }

                    } else if (editable.toString().equals("Nointernet")) {
                        barcode.setEnabled(true);
                    }

                }

            }
        });

        poststate.addTextChangedListener(new TextWatcher() {
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
                                showSweetDialog(NewShipment.this, 0, "Server Error", "");
                            }
                        });
                        localList.clear();

                        filladapter(localList);
                        if (adapter != null) adapter.notifyDataSetChanged();

                    }else
                    if (editable.toString().trim().equals("exported")) {
                        Log.e("poststate", editable.toString());


                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                showSweetDialog(NewShipment.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                            }
                        });

                             saved = true;
                        my_dataBase.shipmentDao().updateShipmentPosted();
                        localList.clear();

                        filladapter(localList);
                        if (adapter != null) adapter.notifyDataSetChanged();
                    } else if (editable.toString().trim().equals("not")) {

                        Log.e("poststate", editable.toString());
                        saved = true;
                        localList.clear();

                        filladapter(localList);
                        if (adapter != null) adapter.notifyDataSetChanged();

                    } else {


                        localList.clear();

                        filladapter(localList);
                        if (adapter != null) adapter.notifyDataSetChanged();

                    }
                }
            }
        });


        boxnorespon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() != 0) {
                    if (editable.toString().equals("Not")) {

                        try {
                            Log.e("case1",BoxNolist.size()+"New_getboxData");
                        if(mode!=null)mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                       // NewShipment.pono.setError(getResources().getString(R.string.invalid));
                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {

                                showalertdaiog(NewShipment.this,"Invalid Po  "+ NewShipment.pono.getText());
                                NewShipment.pono.setText("");
                            }
                        });

                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 1000  milliseconds
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            v.vibrate(VibrationEffect.createOneShot(1000 , VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            v.vibrate(1000 );
                        }
                        }catch (Exception e){
                            Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        if (editable.toString().equals("BOXNO")) {
                            Log.e("case2",BoxNolist.size()+"New_getboxData");
                            NewShipment.boxno.setEnabled(true);
                            NewShipment.boxno.requestFocus();

                        }
                    }

                }
            }
        });
        pono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0)


                fillPOinfo(pono.getText().toString().trim());

                Log.e("localList", localList.size() + "");
                try {

                    localList.clear();
                    List<Shipment> shipments = new ArrayList<>();
                    shipments = my_dataBase.shipmentDao().getlastShipments(pono.getText().toString().trim());

                    if (shipments.size() > 0) {
                        localList.add(shipments.get(shipments.size() - 1));
                        filladapter(localList);
                    }

                } catch (Exception e) {
                    Log.e("Exception==", e.getMessage());
                }


            }
        });
    }

    private void updatePo(String poNumber) {
        importData.updatePoClosed(poNumber);


    }

    private String getusernum() {
        return "";
    }

    private void showConfirmBarcodeDailog() {
        if (!barcode.getText().equals("")) {
            new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getResources().getString(R.string.confirm_title))
                    .setContentText(getResources().getString(R.string.barcodeconfirm)+" "+barcode.getText().toString())
                    .setConfirmButton(getResources().getString(R.string.yes),
                            new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    PoQTY.setText("");
                                    itemname.setText("");
                                    newshipment.setQty("1");
                                    newshipment.setPoqty("1");
                                    newshipment.setDiffer("0");
                                    newshipment.setIsPosted("0");
                                    newshipment.setItemname("");
                                    newshipment.setDeviceId(deviceId);

                                    newshipment.setShipmentTime(String.valueOf(generalMethod.getCurentTimeDate(2)));
                                    newshipment.setShipmentDate(String.valueOf(generalMethod.getCurentTimeDate(1)));
                                    newshipment.setIsNew("1");
                                    CheckFlag = 1;
                                    localList.clear();

                                    Log.e("newshipmentobj", newshipment.toString());
                                    localList.add(newshipment);
                                    filladapter(localList);
                                    saveRow(newshipment);
                                    sweetAlertDialog.dismiss();

                                    barcode.setText("");
                                    barcode.setEnabled(true);
                                    barcode.requestFocus();
                                    CheckFlag = 0;
                                    //FinishProcessFlag=0;

                                    try {


                                        if (mode != null)
                                            mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                    }catch (Exception e){
                                        Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            })
                    .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.dismiss();
                            barcode.setText("");
                            barcode.setEnabled(true);
                            barcode.requestFocus();
                            try {


                                if (mode != null)
                                    mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                            }catch (Exception e){
                                Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .show();
        }

    }

    private void getPoNu() {
        PoNolist.clear();
        importData = new ImportData(NewShipment.this);
        importData.getPoNum();
    }

    void search() {
  /*  searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            if( searchedlist.contains(s)){
                searchadapter.getFilter().filter(s);
            }else{
                Toast.makeText(NewShipment.this, "No Match found",Toast.LENGTH_LONG).show();
            }
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {

            return false;
        }
    });*/
    }


    void showdailogponumber() {


        dialog2 = new Dialog(NewShipment.this);
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.pono_dialog_listview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog2.getWindow().getAttributes());
        lp.width = 500;
        lp.height = 600;
        lp.gravity = Gravity.CENTER;

        dialog2.getWindow().setAttributes(lp);
        final RecyclerView recyclerView1;
        final EditText editText;
        final ArrayList<String> searcharrayAdapter = new ArrayList<>();

        recyclerView1 = dialog2.findViewById(R.id.listview1);
        editText = dialog2.findViewById(R.id.search_edt);


        recyclerView1.setLayoutManager(new LinearLayoutManager(NewShipment.this));
        ponoSearchAdapter = new PonoSearchAdapter(NewShipment.this, allPOs);
        recyclerView1.setAdapter(ponoSearchAdapter);
        Button btndialog = (Button) dialog2.findViewById(R.id.cancelbtndialog);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog2.dismiss();
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("afterText", editText.getText().toString());
                if (editText.getText().toString().trim().equals("")) {
                    ponoSearchAdapter = new PonoSearchAdapter(NewShipment.this, allPOs);
                    recyclerView1.setAdapter(ponoSearchAdapter);

                } else {
                    searcharrayAdapter.clear();
                    for (int i = 0; i < allPOs.size(); i++) {
                        if (editText.getText().toString().trim().equals(allPOs
                                .get(i)))
                            searcharrayAdapter.add(allPOs.get(i));


                    }
                    ponoSearchAdapter = new PonoSearchAdapter(NewShipment.this, searcharrayAdapter);
                    recyclerView1.setAdapter(ponoSearchAdapter);

                }
            }
        });

        dialog2.show();

        dialog2.setCanceledOnTouchOutside(true);


    }


    void showdailogboxnumber() {


        Log.e("setlist", searchBoxList.size() + "");
        dialog1 = new Dialog(NewShipment.this);
        dialog1.setCancelable(false);
        dialog1.setContentView(R.layout.box_dialog_listview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = 500;
        lp.height = 600;
        lp.gravity = Gravity.CENTER;
//        lp.setColorMode(ActivityInfo.COLOR_MODE_DEFAULT);


        dialog1.getWindow().setAttributes(lp);
        final RecyclerView recyclerView1;
        final EditText editText;
        final ArrayList<String> searcharrayAdapter = new ArrayList<>();

        recyclerView1 = dialog1.findViewById(R.id.boxlistview);
        editText = dialog1.findViewById(R.id.boxsearch_edt);

        recyclerView1.setLayoutManager(new LinearLayoutManager(NewShipment.this));
        boxsearchadapter = new BoxnoSearchAdapter(NewShipment.this, searchBoxList);
        recyclerView1.setAdapter(boxsearchadapter);

        LinearLayout linearLayout = dialog1.findViewById(R.id.boxlinear);
        Button btndialog = (Button) dialog1.findViewById(R.id.cancelbtnBoxdialog);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog1.dismiss();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText.getText().toString().trim().equals("")) {

                    boxsearchadapter2 = new BoxnoSearchAdapter(NewShipment.this, searchBoxList);
                    recyclerView1.setAdapter(boxsearchadapter2);

                } else {
                    searcharrayAdapter.clear();

                    for (int i = 0; i < searchBoxList.size(); i++) {
                        Log.e(" searchBoxList33==", searchBoxList.get(i) + "");
                      //  if (editText.getText().toString().trim().contains(searchBoxList.get(i)))
                        if (searchBoxList.get(i).trim().contains(editText.getText().toString().trim())) {
                            Log.e(" searchBoxList==", searchBoxList.get(i) + "");
                            searcharrayAdapter.add(searchBoxList.get(i));
                        }
                    }

                    boxsearchadapter2 = new BoxnoSearchAdapter(NewShipment.this, searcharrayAdapter);
                    recyclerView1.setAdapter(boxsearchadapter2);

                }
            }
        });
        dialog1.show();
        dialog1.setCanceledOnTouchOutside(true);

    }

    public static void colsedialog(int i) {
        if (i == 1)
            dialog1.dismiss();
        else
            dialog2.dismiss();
    }

    public static void fillPoEdittext() {
        try {
            pono.setText(allPOs.get(Integer.parseInt(ponotag)));

            New_getboxData();
            boxno.setEnabled(true);
            boxno.requestFocus();
        } catch (Exception e) {
        }


    }

    public static void fillBoxEdittext() {
        boxno.setText(searchBoxList.get(Integer.parseInt(boxnotag)));

        barcode.setEnabled(true);
        barcode.requestFocus();
    }


    private void saveRow(Shipment shipment) {
            my_dataBase.shipmentDao().insert(shipment);


        fillLastBoxinfo();
        fillPOinfo(pono.getText().toString().trim());
    }

    private void updateRow(String barcpde, String po, String box, String qty, String dif) {
        int g = my_dataBase.shipmentDao().updateQTY(barcpde, po, box, qty, dif);
        Log.e("updateRow", g + "");

    }


    public static void updateAdpapter() {

        adapter.notifyDataSetChanged();

    }


    public boolean checkboxvalidty() {

        checkBoxIsIndatabase();

        if (importData.BoxNolist.contains(convertToEnglish(boxno.getText().toString().trim()))
                || AllBoxesInDB.contains(convertToEnglish(boxno.getText().toString().trim()))) {
            Log.e("checkboxvalidty1", boxno.getText().toString());
            return true;
        } else {
            Log.e("checkboxvalidty2", boxno.getText().toString());
            return false;
        }
    }

    private boolean checkitemcodevalidty() {
        boolean f = false;
        Log.e("checkitemcodevalidty", "checkitemcodevalidty");

        for (int i = 0; i < importData.POdetailslist.size(); i++) {

            if (!convertToEnglish(boxno.getText().toString().trim()).
                    equals(importData.POdetailslist.get(i).getBOXNO())
                    && !convertToEnglish(barcode.getText().toString().trim()).
                    equals(importData.POdetailslist.get(i).getITEMCODE())

            ) {
                f = false;
                break;
            } else
                f = true;
            continue;

        }


        return f;
    }


    void checkBoxIsIndatabase() {

        AllBoxesInDB = my_dataBase.shipmentDao().getboxes();

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

    @Override
    protected void onResume() {
        super.onResume();
        mode = (AudioManager) NewShipment.this.getSystemService(Context.AUDIO_SERVICE);

    }

    public  void showalertdaiog(Context context,String Msg){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)

                .setContentText(Msg)
                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        try {


                        if(mode!=null)mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        sweetAlertDialog.dismiss();
                        }catch (Exception e){
                            Toast.makeText(NewShipment.this,e.getMessage()+"",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();

    }

    private static void New_getboxData() {
Log.e("New_getboxData","New_getboxData");
        poNo = pono.getText().toString().trim();

        BoxNolist.clear();
        BoxNolist = my_dataBase.newAllPOsInfoDao().getboxs(poNo.trim());

        Log.e("BoxNolist",BoxNolist.size()+"New_getboxData");
if(BoxNolist.size()!=0)
        NewShipment.boxnorespon.setText("BOXNO");
               else
        NewShipment.boxnorespon.setText("Not");

    }

    }