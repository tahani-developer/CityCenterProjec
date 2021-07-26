package com.example.irbidcitycenter.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.Store;
import com.example.irbidcitycenter.Models.appSettings;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;
import com.example.irbidcitycenter.ScanActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.example.irbidcitycenter.Adapters.ReplacementAdapter;
import com.example.irbidcitycenter.Models.ReplacementModel;

import java.util.ArrayList;
import java.util.List;


import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.irbidcitycenter.GeneralMethod.convertToEnglish;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;
import static com.example.irbidcitycenter.ImportData.Storelist;
import static com.example.irbidcitycenter.Activity.AddZone.validateKind;
import static com.example.irbidcitycenter.Activity.AddZone.validItem;
import static com.example.irbidcitycenter.ImportData.barcode;
import static com.example.irbidcitycenter.ImportData.listAllZone;
import static com.example.irbidcitycenter.ImportData.listQtyZone;

public class Replacement extends AppCompatActivity {
    boolean saved = false;
    int position;
    public static TextView respon,qtyrespons;
    GeneralMethod generalMethod;
    Spinner fromSpinner, toSpinner;
    ExportData exportData;
    ImportData importData;
    public static EditText itemKintText1, poststateRE;
    public static EditText zone, itemcode;
    public static TextView qty;
    public  String deviceId="";
    EditText recqty;
    Button save;
    public int indexZone = -1;
    public RoomAllData my_dataBase;
    String From, To, Zone, Itemcode, Qty;
    String FromNo, ToNo;
    ReplacementModel replacement;
    ReplacementModel replacementModel;
    static ReplacementAdapter adapter;
    FloatingActionButton add;
    RecyclerView replacmentRecycler;
    public static final int REQUEST_Camera_Barcode = 1;
    List<com.example.irbidcitycenter.Models.appSettings> appSettings;

    List<String> spinnerArray = new ArrayList<>();
    public static ArrayList<ReplacementModel> replacementlist = new ArrayList<>();
    public List<ReplacementModel> UnPostedreplacementlist = new ArrayList<>();
    public static ArrayList<Store> Storelistt = new ArrayList<>();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement);
        init();
        getStors();
        zone.setEnabled(true);
        zone.requestFocus();
        itemcode.setEnabled(false);
        recqty.setEnabled(false);
        save.setEnabled(false);

//my_dataBase.replacementDao().deleteALL();
      /*  if(Storelistt.size()==0) {
            itemcode.setEnabled(false);
            zone.setEnabled(false);

        }*/
        //testcode
/*ReplacementModel replacementModel=new ReplacementModel();
        replacementModel.setRecQty("1");
        replacementModel.setFrom("1234");
        replacementModel.setTo("122222222222");
        replacementModel.setItemcode("tttttttttt");
        replacementModel.setQty("1");
        replacementModel.setZone("fffff");
        for (int i=0;i<50;i++)
      replacementlist.add(replacementModel);
        fillAdapter();*/


findViewById(R.id.nextZone).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        zone.setText("");
        zone.setEnabled(true);
        zone.requestFocus();
        itemcode.setText("");
        if(replacementlist!=null)
            if(replacementlist.size()>0)   replacementlist.clear();
        if(adapter!=null) adapter.notifyDataSetChanged();
    }
});

 try {

     fromSpinner.setSelection(0);
     toSpinner.setSelection(1);
     toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
             // your code her

             if (toSpinner.getSelectedItem().toString().equals(fromSpinner.getSelectedItem())) {
                 showSweetDialog(Replacement.this, 3, getResources().getString(R.string.samestore), "");
                 if (fromSpinner.getSelectedItemPosition() != 1) toSpinner.setSelection(1);
             }
         }

         @Override
         public void onNothingSelected(AdapterView<?> parentView) {
             // your code here
         }

     });
     fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
             // your code her

             if (toSpinner.getSelectedItem().toString().equals(fromSpinner.getSelectedItem())) {
                 showSweetDialog(Replacement.this, 3, getResources().getString(R.string.samestore), "");
                 if (toSpinner.getSelectedItemPosition() != 0) fromSpinner.setSelection(0);
                 //else
                 //   fromSP.setSelection(1);

             }
         }

         @Override
         public void onNothingSelected(AdapterView<?> parentView) {
             // your code here
         }

     });
 }catch (Exception e){}





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // for (int i=0; i<replacementlist.size();i++)
                //     model.insert(replacementlist.get(i));

                /*if (replacementlist.size() > 0) {
                    for (int i = 0; i < replacementlist.size(); i++) {
                        replacementlist.get(i).setItemcode(convertToEnglish(replacementlist.get(i).getItemcode()));
                        replacementlist.get(i).setQty(convertToEnglish(replacementlist.get(i).getQty()));
                    }*/
                UnPostedreplacementlist=my_dataBase.replacementDao().getallReplacement();
                    exportData();
                    zone.setEnabled(true);
                    zone.requestFocus();

           zone.setText("");
               itemcode.setText("");
          //     qty.setText("");
            }
        });


        findViewById(R.id.Re_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(Replacement.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.confirm_title))
                        .setContentText(getResources().getString(R.string.messageExit))
                        .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (replacementlist.size() > 0) {

                                    new SweetAlertDialog(Replacement.this, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText(getResources().getString(R.string.confirm_title))
                                            .setContentText(getResources().getString(R.string.messageExit2))
                                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {


                                                    replacementlist.clear();
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
                                            }).show();
                                }
                                else
                                {
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


    }


    public boolean CaseDuplicates(ReplacementModel replacement) {
        boolean flag = false;
        if (replacementlist.size() != 0)
            for (int i = 0; i < replacementlist.size(); i++) {

                if (convertToEnglish(replacementlist.get(i).getZone()).equals(replacement.getZone())
                        && convertToEnglish(replacementlist.get(i).getItemcode()).equals(replacement.getItemcode())
                        &&replacementlist.get(i).getFrom().equals(replacement.getFrom())
                &&replacementlist.get(i).getTo().equals(replacement.getTo()))
                {

                   position=i;
                    flag = true;
                    break;

                } else
                    flag = false;
                continue;
            }

        return flag;

    }


    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    private void showExitDialog() {
        new SweetAlertDialog(Replacement.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.confirm_title))
                .setContentText(getResources().getString(R.string.messageExit))
                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (replacementlist.size() > 0) {

                            new SweetAlertDialog(Replacement.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.confirm_title))
                                    .setContentText(getResources().getString(R.string.messageExit2))
                                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {


                                            replacementlist.clear();
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
                                    }).show();
                        }
                        else
                        {
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

    private void getStors() {
        importData.getStore();
    }

    public void exportData() {
        try {

            exportData.exportReplacementList(UnPostedreplacementlist);
        }catch (Exception e){

            // test
        }

    }

    private void init() {
        my_dataBase = RoomAllData.getInstanceDataBase(Replacement.this);
        replacementlist.clear();
        poststateRE = findViewById(R.id.poststatRE);
        MainActivity.setflage = 1;
        itemKintText1 = findViewById(R.id.itemKintTextRE);
        exportData = new ExportData(Replacement.this);
        importData = new ImportData(Replacement.this);
        listAllZone.clear();
        importData.getAllZones();
        listQtyZone.clear();
        appSettings=new ArrayList();
        try {
            appSettings=my_dataBase.settingDao().getallsetting();
        }
        catch (Exception e){}
        fromSpinner = findViewById(R.id.fromspinner);
        toSpinner = findViewById(R.id.tospinner);
        zone = findViewById(R.id.zoneedt);
        itemcode = findViewById(R.id.itemcodeedt);
        qty = findViewById(R.id.qty);
        recqty = findViewById(R.id.qtyedt);
        replacmentRecycler = findViewById(R.id.replacmentRec);
        save = findViewById(R.id.save);
        respon = findViewById(R.id.respons);
        qtyrespons = findViewById(R.id.qtyrespon);
      //  recqty.setOnEditorActionListener(onEditAction);
        itemcode.setOnEditorActionListener(onEditAction);
   zone.setOnEditorActionListener(onEditAction);
        generalMethod = new GeneralMethod(Replacement.this);


       zone.setOnKeyListener(onKeyListener);
        itemcode.setOnKeyListener(onKeyListener);








        //importData.getAllZones();
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

                    if (editable.toString().equals("no data")) {

                        respon.setText("");
                    } else {
                        if (editable.toString().equals("fill")) {
                            for (int i = 0; i < Storelist.size(); i++)
                                spinnerArray.add(Storelist.get(i).getSTORENO() + "  " + Storelist.get(i).getSTORENAME());
                        }
                        fillSp();

                        zone.requestFocus();
                        Log.e("afterTextChanged", "" + editable.toString());



                    }

                }

            }
        });
        itemKintText1.addTextChangedListener(new TextWatcher() {
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
                        recqty.setEnabled(false);
                        showSweetDialog(Replacement.this, 3, "This Item Not Exist", "");
                        itemKintText1.setText("");
                    } else {
                        validateKind = false;
                        Log.e("afterTextChanged", "" + editable.toString());
                        compareItemKind(editable.toString().trim());
                    }

                }

            }
        });


        poststateRE.addTextChangedListener(new TextWatcher() {
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
                        { //saveData(1);
                            my_dataBase.replacementDao().updateReplashmentPosted();
                            showSweetDialog(Replacement.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                        saved = true;
                    replacementlist.clear();
                    fillAdapter();
                      adapter.notifyDataSetChanged();
                        }
                    }
                    else if (editable.toString().trim().equals("not")) {
                        saved = true;
                        //saveData(0);
                      replacementlist.clear();
                    fillAdapter();
                      adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        qtyrespons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() != 0) {
                    Log.e("afterTextChanged",qtyrespons.getText().toString());
                    if (qtyrespons.getText().toString().equals("QTY"))
                    {
                        recqty.setEnabled(true);

                        try {
                            filldata();
                           Replacement.qty.setText("");
                        }
                        catch (Exception e){
                            Log.e("Exception",e.getMessage());
                        }
                        save.setEnabled(true);


                        recqty.setText("1");


                        zone.setEnabled(false);
                        itemcode.setText("");
                        itemcode.requestFocus();

                        recqty.setEnabled(false);


                    }
                    else
                    {
                        showSweetDialog(Replacement.this, 3, "", getResources().getString(R.string.existsBARCODE));
                        recqty.setEnabled(false);

                        itemcode.setText("");
                        itemcode.requestFocus();

                    }
                }
            }
        });

        if(appSettings.size()!=0)
        {
            deviceId=  appSettings.get(0).getDeviceId();
            Log.e("appSettings","+"+deviceId);

        }


    }

    private void fillSp() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(1);

    }

    private void compareItemKind(String itemKind) {

        validItem = false;
        if (listAllZone.get(indexZone).getZONETYPE().equals(itemKind)) {
            validItem = true;
            itemcode.setEnabled(false);
            recqty.setEnabled(true);
            recqty.requestFocus();
        } else {
            recqty.setEnabled(false);
            showSweetDialog(Replacement.this, 0, "Item Kind Not Match To Zone Type", "");
        }
        itemKintText1.setText("");
    }

    public void ScanCode(View view) {
        switch (view.getId()) {
            case R.id.scanZoneCode:
                readBarcode(4);

                break;
            case R.id.scanItemCode:
                readBarcode(5);

                break;
        }
    }



    TextView.OnKeyListener onKeyListener=new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            Log.e("keyEvent.getAction()",keyEvent.getAction()+"");


            if (i == KeyEvent.KEYCODE_BACK) {
                onBackPressed();

            }

            if (i != KeyEvent.KEYCODE_ENTER) {

                { if (keyEvent.getAction() == KeyEvent.ACTION_UP)
            switch (view.getId()) {
                case R.id.zoneedt:
                    Log.e("ZONEHERE",zone.getText().toString()+"");
                    if(!zone.getText().toString().equals(""))
                        if(searchZone(zone.getText().toString().trim()))
                        {
                            Log.e("zone",zone.getText().toString());
                        }
                        else {
                            zone.setError("Invalid");
                            Log.e("elsezone",zone.getText().toString());
                            zone.setText("");}
                    else
                        zone.requestFocus();
            break;
                case R.id.itemcodeedt:

                  if(!itemcode.getText().toString().equals(""))
                  {

                      Log.e( "itemcodeedt ",itemcode.getText().toString()+"");

                      From = fromSpinner.getSelectedItem().toString();
                      To = toSpinner.getSelectedItem().toString();
                      FromNo=From.substring(0,From.indexOf(" "));
                      ToNo=To.substring(0,To.indexOf(" "));
                      Zone = convertToEnglish(zone.getText().toString().trim());
                      Itemcode = convertToEnglish(itemcode.getText().toString().trim());


                      replacement = new ReplacementModel();
                      replacement.setFrom( FromNo);
                      replacement.setTo(ToNo);
                      replacement.setZone(Zone);
                      replacement.setItemcode(Itemcode);
                      replacement.setFromName(From);
                      replacement.setToName(To);
                      replacement.setDeviceId(deviceId);
                      zone.setEnabled(false);
                    ReplacementModel replacementModel=my_dataBase.replacementDao().getReplacement(Itemcode,Zone, FromNo,ToNo);
                    if(replacementModel!=null) {
                        if (!CaseDuplicates(replacementModel))
                            replacementlist.add(replacementModel);
                        save.setEnabled(true);
                    }


                          if (CaseDuplicates(replacement)) {
                              Log.e("replacementFrom",replacement.getTo());
                              Log.e("AddInCaseDuplicates","AddInCaseDuplicates");
                              //update qty in Duplicate case
                              int sum=Integer.parseInt(replacementlist.get(position).getRecQty()) + Integer.parseInt("1");
                              Log.e("aaasum ",sum+"");

                              if(sum<=Integer.parseInt(replacementlist.get(position).getQty()))
                              {
                                  replacementlist.get(position).setRecQty((sum+""));
                                  my_dataBase.replacementDao().updateQTY(replacementlist.get(position).getItemcode(),replacementlist.get(position).getRecQty());

                                  zone.setEnabled(false);
                                  itemcode.setText("");
                                  itemcode.requestFocus();
                                  fillAdapter();
                                  Log.e("heree","here2");
                              }
                              else
                              {   showSweetDialog(Replacement.this, 3, "", getResources().getString(R.string.notvaildqty));

                                  fillAdapter();
                                  zone.setEnabled(false);
                                  itemcode.setText("");
                                  itemcode.requestFocus();
                              }


                          }


                          else
                          {
                              Log.e("not in db","not in db");
                              importData.getQty();

                          }
                      }

                  else
                      itemcode.requestFocus();
                    break;
        }  return true;
                }}
                return false;

    }};




    EditText.OnEditorActionListener onEditAction = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                    || i == EditorInfo.IME_NULL) {
                switch (textView.getId()) {
                    case R.id.zoneedt:
                        Log.e("ZONEHERE",zone.getText().toString()+"");
                        if(!zone.getText().toString().equals(""))
                            if(searchZone(zone.getText().toString().trim()))
                            {
                                Log.e("zone",zone.getText().toString());
                            }
                            else {
                                zone.setError("Invalid");
                                Log.e("elsezone",zone.getText().toString());
                                zone.setText("");}
                        else
                            zone.requestFocus();
                        break;
                    case R.id.itemcodeedt:

                        if(!itemcode.getText().toString().equals(""))
                        {

                            Log.e( "itemcodeedt ",itemcode.getText().toString()+"");

                            From = fromSpinner.getSelectedItem().toString();
                            To = toSpinner.getSelectedItem().toString();
                            FromNo=From.substring(0,From.indexOf(" "));
                            ToNo=To.substring(0,To.indexOf(" "));
                            Zone = convertToEnglish(zone.getText().toString().trim());
                            Itemcode = convertToEnglish(itemcode.getText().toString().trim());


                            replacement = new ReplacementModel();
                            replacement.setFrom( FromNo);
                            replacement.setTo(ToNo);
                            replacement.setZone(Zone);
                            replacement.setItemcode(Itemcode);
                            replacement.setFromName(From);
                            replacement.setToName(To);
                            replacement.setDeviceId(deviceId);
                            zone.setEnabled(false);
                            ReplacementModel replacementModel=my_dataBase.replacementDao().getReplacement(Itemcode,Zone, FromNo,ToNo);
                            if(replacementModel!=null) {
                                if (!CaseDuplicates(replacementModel))
                                    replacementlist.add(replacementModel);
                                save.setEnabled(true);
                            }


                            if (CaseDuplicates(replacement)) {
                                Log.e("replacementFrom",replacement.getTo());
                                Log.e("AddInCaseDuplicates","AddInCaseDuplicates");
                                //update qty in Duplicate case
                                int sum=Integer.parseInt(replacementlist.get(position).getRecQty()) + Integer.parseInt("1");
                                Log.e("aaasum ",sum+"");

                                if(sum<=Integer.parseInt(replacementlist.get(position).getQty()))
                                {
                                    replacementlist.get(position).setRecQty((sum+""));
                                    my_dataBase.replacementDao().updateQTY(replacementlist.get(position).getItemcode(),replacementlist.get(position).getRecQty());

                                    zone.setEnabled(false);
                                    itemcode.setText("");
                                    itemcode.requestFocus();
                                    fillAdapter();
                                    Log.e("heree","here2");
                                }
                                else
                                {   showSweetDialog(Replacement.this, 3, "", getResources().getString(R.string.notvaildqty));

                                    fillAdapter();
                                    zone.setEnabled(false);
                                    itemcode.setText("");
                                    itemcode.requestFocus();
                                }


                            }


                            else
                            {
                                Log.e("not in db","not in db");
                                importData.getQty();

                            }
                        }

                        else
                            itemcode.requestFocus();
                        break;
                    case R.id.qtyedt: {


                  if(Integer.parseInt(recqty.getText().toString())==0)
                           recqty.setText("1");


                     //       if (checkQtyValidate(recqty.getText().toString()))
                       {
                                filldata();
//                                Replacement.qty.setText("");
                                save.setEnabled(true);
                                zone.setText("");
                                itemcode.setText("");
                                recqty.setText("1");
                                zone.setEnabled(true);
                                zone.requestFocus();
                                recqty.setEnabled(false);
                                break;
                            }
                            //else
                          //      showSweetDialog(Replacement.this, 3, "", getResources().getString(R.string.notvaildqty));


                    }


                }



            }

            return true;
        }
    };

    private void validateItemKind(String itemNo) {
        validateKind = true;
        // http://localhost:8082/IrGetItemData?CONO=290&ITEMCODE=28200152701
        importData.getKindItem(itemNo);
    }
   /* private boolean searchZone(String codeZone) {
        Log.e("search", " searchZone1");
        Log.e("listAllZone", "" + listAllZone.size());
        zone.setError(null);
        for (int i = 0; i < listAllZone.size(); i++) {
            if (listAllZone.get(i).getZoneCode().equals(codeZone)) {
                Log.e(" searchZone", " searchZone");
               return true;

            }
        }
   return false; }*/
    private boolean searchZone(String codeZone) {
        Log.e("search",codeZone);
        Log.e("search"," searchZone1");
        Log.e("listAllZone",""+listAllZone.size());
        zone.setError(null);
        for (int i = 0; i < listAllZone.size(); i++) {

            if (listAllZone.get(i).getZoneCode().equals(codeZone)) {
                itemcode.setEnabled(true);
                itemcode.requestFocus();
                zone.setEnabled(false);
                return true;

            }
        }

return  false;

    }
    private String getusernum() {
        return "";
    }
    private void filldata() {

        Qty = recqty.getText().toString().trim();
        if (Zone.toString().trim().equals("")) zone.setError("required");

        else if (Itemcode.toString().trim().equals("")) itemcode.setError("required");


        else if (Qty.toString().trim().equals(""))
            recqty.setError("required");
        else {


            replacement.setRecQty(Qty);
            Log.e("replacement","1=="+qty.getText().toString());
            replacement.setQty( listQtyZone.get(0).getQty());
            Log.e("replacement","2=="+replacement.getQty());
            replacement.setIsPosted("0");
            replacement.setUserNO(getusernum());
            replacement.setDeviceId(deviceId);
            replacement.setReplacementDate(generalMethod.getCurentTimeDate(1) + "");
             Log.e("else","AddInCaseDuplicates");
                Log.e("replacementlist.size", replacementlist.size()+"");
                replacementlist.add(replacement);
                Log.e("replacementlist.size", replacementlist.size()+""+replacementlist.get(0).getFrom());
                fillAdapter();
               SaveRow(replacement);
            }




    }

    private void SaveRow(ReplacementModel replacement) {
        Log.e("SaveRow","replacement"+replacement.getDeviceId());
        my_dataBase.replacementDao().insert(replacement);
    }

    private void updateQTYOfZone() {
        String d="";
        for (int i = 0; i < listQtyZone.size(); i++) {
            if (itemcode.getText().toString().trim().equals(listQtyZone.get(i).getItemCode().trim())
                    && zone.getText().toString().trim().equals(listQtyZone.get(i).getZoneCode().trim())) {
                {
                    listQtyZone.get(i).setQty(Integer.parseInt(listQtyZone.get(i).getQty()) -Integer.parseInt( Qty)+"");
                    d=listQtyZone.get(i).getQty();
                }
            }}
       Log.e(" updateQTYOfZone()",d) ;
    }

    private void fillAdapter() {
        Log.e(" fillAdapter"," fillAdapter");
        replacmentRecycler.setLayoutManager(new LinearLayoutManager(Replacement.this));
        adapter = new ReplacementAdapter(replacementlist, Replacement.this);
        replacmentRecycler.setAdapter(adapter);
    }


    private void readBarcode(int type) {
        //new IntentIntegrator(AddZone.this).setOrientationLocked(false).setCaptureActivity(CustomScannerActivity.class).initiateScan();

        openSmallCapture(type);
    }

    private void openSmallCapture(int type) {
        if (ContextCompat.checkSelfPermission(Replacement.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Replacement.this, new String[]{Manifest.permission.CAMERA}, REQUEST_Camera_Barcode);
            if (ContextCompat.checkSelfPermission(Replacement.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {//just for first time
                Log.e("requestresult", "PERMISSION_GRANTED");
                Intent i = new Intent(Replacement.this, ScanActivity.class);
                i.putExtra("key", "1");
                startActivity(i);
                // searchByBarcodeNo(s + "");
            }
        } else {
            Intent i = new Intent(Replacement.this, ScanActivity.class);
            if (type == 4) {
                i.putExtra("key", "4");
            } else {
                i.putExtra("key", "5");
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
                Toast.makeText(Replacement.this, "cancelled", Toast.LENGTH_SHORT).show();


            } else {
                String valueBarcode = Result.getContents();

            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void saveData(int isposted) {
        if (isposted == 1)
            for (int i = 0; i < replacementlist.size(); i++)
                replacementlist.get(i).setIsPosted("1");
        long result[] = my_dataBase.replacementDao().insertAll(replacementlist);

        if (result.length != 0) {
            showSweetDialog(this, 1, this.getResources().getString(R.string.savedSuccsesfule), "");
        }


    }
    public static void updateAdpapter() {
        adapter.notifyDataSetChanged();
    }

  public  boolean checkQtyValidate(String recqty) {
        Log.e("checkQtyValidate","heckQtyValidate");
        for (int i = 0; i < listQtyZone.size(); i++) {
            Log.e("checkQtyValidate","for");
            if (itemcode.getText().toString().trim().equals(listQtyZone.get(i).getItemCode().trim())
                    && zone.getText().toString().trim().equals(listQtyZone.get(i).getZoneCode().trim())) {
                if (Integer.parseInt(recqty) <= Integer.parseInt(listQtyZone.get(i).getQty()))
                {

                    return true;

              }
                else {

                    return false;


                }
            }
        }

   return false; }
}