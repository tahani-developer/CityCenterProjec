package com.example.irbidcitycenter.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.Adapters.ZoneAdapter;
import com.example.irbidcitycenter.Adapters.ZoneSearchDBAdapter;
import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;
import com.example.irbidcitycenter.ScanActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.irbidcitycenter.ImportData.listAllZone;
import static com.example.irbidcitycenter.ImportData.zonetype;

public class AddZone extends AppCompatActivity {
    GeneralMethod generalMethod;
    public static EditText editZoneCode, editItemCode, editQty,itemKintText,exportStateText;
    public static final int REQUEST_Camera_Barcode = 1;
    public static ArrayList<ZoneModel> listZone;
    public static List<ZoneModel> listofZonesUnPosted;
    public static List<ZoneModel> listtest=new ArrayList<>();;
    public List<ZoneModel> deletedZonsList=new ArrayList<>();;
    public List<ZoneModel> zonescopylist=new ArrayList<>();
    public List<ZoneModel> ReducedItemlist =new ArrayList<>();
    RecyclerView recycleZone;
    List<String>zones;
    List<ZoneModel>zones2;
    LinearLayoutManager layoutManager;
    public RoomAllData my_dataBase;
    public static ZoneAdapter adapter;
    ImportData importData;
   public static TextView zoneName,itemName,DD_preQTY;
    public  static String itemKind="";
    public  int indexZone=-1,updatedIndex=-1;
    ExportData exportData;
    ListView listZones;
    Button delete;
    int index;
    EditText zonecode;
    Dialog authenticationdialog;
    ListView listView;
    String x;
    String selectedValue;
    public static EditText zonebarecode;
    public static Dialog searchdialog;
    public static boolean validItem=false,validateKind=false;
    public List<String>items;
    public static TextView zonecode1;
    public static TextView qty1;
    public static TextView zonename1;
    public static TextView  DIitemcode,DIqty, DDzoneEDT,DI_zonecode;
    public static EditText DDitemcode;
    public static TextView  DI_itemcode;
    private int searchflage;
    ArrayList<ZoneModel> searchlistAllZone;


    public  int sumOfQTY;
    private int ind;

    public static int flage3=0;
    private String preQTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_zone);

        initial();
        editQty.setEnabled(false);
   //my_dataBase.zoneDao().deleteAll();
       /* ZoneModel zoneModel=new ZoneModel();
        zoneModel.setZoneCode("C03D");
        zoneModel.setQty("33");
        zoneModel.setItemCode("8055203155196");
        zoneModel.setIsPostd("0");
        zoneModel.setZONENAME("UPIM HOMEWARE CATEGORY");
        ZoneModel zoneModel2=new ZoneModel();
        zoneModel2.setZoneCode("C03D");
        zoneModel2.setQty("37");
        zoneModel2.setItemCode("678954321");
        zoneModel2.setIsPostd("0");
        zoneModel2.setZONENAME("UPIM HOMEWARE CATEGORY");
        ZoneModel zoneModel3=new ZoneModel();
        zoneModel3.setZoneCode("M13C");
        zoneModel3.setQty("20");
        zoneModel3.setItemCode("6789543214567");
        zoneModel3.setIsPostd("0");
        zoneModel3.setZONENAME("UPIM HOMEWARE CATEGORY");
        my_dataBase.zoneDao().insert(zoneModel);
        my_dataBase.zoneDao().insert(zoneModel2);
        my_dataBase.zoneDao().insert(zoneModel3);*/

      /*  ZoneModel zoneModel=new ZoneModel();
        zoneModel.setZoneCode("gg");
        zoneModel.setQty("33");
        zoneModel.setItemCode("aaaaa");

    for(int i=0;i<59;i++)
            listZone.add(zoneModel);
        fillAdapter(listZone);*/

       //test code
     /*   listtest=my_dataBase.zoneDao().getAllZones();
      //  my_dataBase.zoneDao().updateZonePosted();
        layoutManager = new LinearLayoutManager(AddZone.this);
        recycleZone.setLayoutManager(layoutManager);
        adapter = new ZoneAdapter(AddZone.this, listtest);
        recycleZone.setAdapter(adapter);
        Log.e("listtest",listtest.size()+"");*/





        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDeleteDailog();
            }
        });



    }

    private void initial() {
        MainActivity.setflage=0;
        generalMethod = new GeneralMethod(AddZone.this);
        editZoneCode = findViewById(R.id.editZoneCode);
        editItemCode = findViewById(R.id.editItemCode);
      editItemCode.setOnEditorActionListener(onEditAction);
        editItemCode.setOnKeyListener(onKeyListener);
        editZoneCode.setOnKeyListener(onKeyListener);
        listofZonesUnPosted=new ArrayList<>();
        delete=findViewById(R.id.delete_btn);
    /*editItemCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != KeyEvent.KEYCODE_ENTER) {
                    {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP)

                        {
                            if(!editItemCode.getText().toString().equals(""))


                            {
                                Log.e("editItemCode",editItemCode.getText().toString());

                               if(indexZone!=-1)
                            {
                                Log.e("itemKintText",""+itemKintText.getText().toString()+"\t"+validateKind);
                                if(itemKintText.getText().toString().equals("")&&validateKind==false)
                                {
                                    validateItemKind(editItemCode.getText().toString().trim());
                                }
                            }



                            else {
//                                   editZoneCode.setText("");
//                                editZoneCode.setError("Invalid Zone");
                                   editItemCode.setText("");
                                   editItemCode.setError("Invalid Item");
                                   editItemCode.requestFocus();

                            }

                    }
                else
                            { editItemCode.requestFocus();
                                Log.e("elseeditZoneCode",editZoneCode.getText().toString());
                            }

                        return false;
            } }}
            return true;}
        });

        editZoneCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != KeyEvent.KEYCODE_ENTER) {
                    {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP){

                            if(editZoneCode.getText().length()!=0) {
                                searchZone(editZoneCode.getText().toString().trim());
                                Log.e("editZoneCode",editZoneCode.getText().toString());
                            }
                            else
                            { editZoneCode.requestFocus();
                            Log.e("elseeditZoneCode",editZoneCode.getText().toString());}
                    }
                    }

                    return true;
                }
           return false; }
        });*/

//        editItemCode.addTextChangedListener(new TextWatcher() {
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
//                if(!editable.toString().trim().equals(""))
//                {
//                    if(indexZone!=-1)
//                    {
//                       // Log.e("itemKintText",""+itemKintText.getText().toString()+"\t"+validateKind);
//                        if(itemKintText.getText().toString().equals("")&&validateKind==false)
//                        {
//                            validateItemKind(editItemCode.getText().toString().trim());
//                        }
//                    }
//                    else editZoneCode.setError("Invalid Zone");
//                }
//
//            }
//        });
        editZoneCode.setOnEditorActionListener(onEditAction);
        editQty = findViewById(R.id.editQty);
        zoneName= findViewById(R.id.zoneName);
        itemName= findViewById(R.id.itemName);
        itemKintText= findViewById(R.id.itemKintText);
        exportStateText= findViewById(R.id.exportStateText);
       // editQty.setOnEditorActionListener(onEditAction);
        listZone = new ArrayList<>();

        recycleZone = findViewById(R.id.recycleZone);
        my_dataBase= RoomAllData.getInstanceDataBase(AddZone.this);
        importData=new ImportData(AddZone.this);
        exportData=new ExportData(AddZone.this);
        importData.getAllZones();
        editItemCode.setEnabled(false);
        editQty.setEnabled(false);
        editZoneCode.requestFocus();
        itemKintText.addTextChangedListener(new TextWatcher() {
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
                    if(editable.toString().equals("NOTEXIST"))
                    {
                        validateKind=false;
                        editQty.setEnabled(false);
                       Log.e("here","here");
                        generalMethod.showSweetDialog(AddZone.this,3,"This Item Not Exist","");
                        itemKintText.setText("");
                        editItemCode.setText("");

                    }
                    else {
                        if(editable.toString().equals("ErrorNet"))
                        {
                            generalMethod.showSweetDialog(AddZone.this,3,"No Internet Connection","");
                        }
                        else {
                            validateKind=false;
                            Log.e("afterTextChanged",""+editable.toString());
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

                if(editable.toString().length()!=0)
                {
                    if(editable.toString().trim().equals("exported"))
                    {
                        //saveDataLocaky(1);
                        updateRowsPosted();
                        listZone.clear();
                        adapter.notifyDataSetChanged();

                    }
                    else  if(editable.toString().trim().equals("not"))
                    {
                        //saveDataLocaky(0);
                        listZone.clear();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public ZoneModel existsInDB (String bar){
     return   my_dataBase.zoneDao().getzone(bar);

    }
public boolean exists (String bar){
    Log.e("exists","exists");



    //ayah edit
    for (int x = 0; x < listZone.size(); x++)
            if (listZone.get(x).getItemCode().equals(bar))
                {


                   index=x;

                   return true;


                }


    return false;
}


    //

    TextView.OnKeyListener onKeyListener=new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {


            Log.e("keyEvent.getAction()",keyEvent.getAction()+"");


            if (i == KeyEvent.KEYCODE_BACK) {
                onBackPressed();


            }
            if (i != KeyEvent.KEYCODE_ENTER) {
                {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP)

                        switch (view.getId()) {

                            case R.id.editZoneCode:
                            {

                                if(editZoneCode.getText().length()!=0) {
                                    searchZone(editZoneCode.getText().toString().trim());
                                    Log.e("editZoneCode",editZoneCode.getText().toString());
                                }
                                else
                                { editZoneCode.requestFocus();
                                    Log.e("elseeditZoneCode",editZoneCode.getText().toString());}



                                break;
                            }
                            case R.id.editItemCode:
                            {

                                if(!editItemCode.getText().toString().equals("")) {
                                ZoneModel zoneModel=existsInDB(editItemCode.getText().toString());
                                    if(zoneModel!=null)
                                    {if(!exists(editItemCode.getText().toString()))
                                        listZone.add(zoneModel);}

                                       if (exists(editItemCode.getText().toString()))
                                        {
                                            Log.e("exists ", "true");
                                            listZone.get(index).setQty(String.valueOf(Integer.parseInt(listZone.get(index).getQty()) + 1));
                                            updateQtyOfRow(listZone.get(index).getItemCode(), listZone.get(index).getQty());
                                            editItemCode.setText("");
                                          fillAdapter(listZone);

                                        } else



                                            {
                                                Log.e("editItemCode", editItemCode.getText().toString());

                                                if (indexZone != -1) {
                                                    Log.e("itemKintText", "" + itemKintText.getText().toString() + "\t" + validateKind);
                                                    if (itemKintText.getText().toString().equals("") && validateKind == false) {
                                                        validateItemKind(editItemCode.getText().toString().trim());
                                                    }
                                                } else {
//
                                                    editItemCode.setText("");
                                                    editItemCode.setError("Invalid Item");
                                                    editItemCode.requestFocus();

                                                }
                                            }




                                }
                                else
                                { editItemCode.requestFocus();
                                    Log.e("elseeditZoneCode",editZoneCode.getText().toString());
                                }
                                break;
                            }



                        }}
                return true;
            }
            return false;
        }
    };




    private void compareItemKind(String itemTypa) {
        validItem=false;
        if(listAllZone.size()>0){
        if(listAllZone.get(indexZone).getZONETYPE().trim().equals(itemTypa))
        {
            validItem=true;
            itemName.setText(itemKind);
            editItemCode.setEnabled(false);
            //editQty.setEnabled(true);
            editQty.setText("1");
            if(validItem)
            {

                addRow();
                validItem=false;
                itemKintText.setText("");
            }
//            editQty.requestFocus();
        }
        else {
            editQty.setEnabled(false);
            generalMethod.showSweetDialog(AddZone.this,0,"Item Kind Not Match To Zone Type","");
            editItemCode.setText("");

        }}
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

                            if (editZoneCode.getText().length() != 0) {
                                searchZone(editZoneCode.getText().toString().trim());
                                Log.e("editZoneCode", editZoneCode.getText().toString());
                            } else {
                                editZoneCode.requestFocus();
                                Log.e("elseeditZoneCode", editZoneCode.getText().toString());
                            }


                            break;
                        }
                        case R.id.editItemCode: {

                            if (!editItemCode.getText().toString().equals("")) {
                                ZoneModel zoneModel = existsInDB(editItemCode.getText().toString());
                                if (zoneModel != null) {
                                    if (!exists(editItemCode.getText().toString()))
                                        listZone.add(zoneModel);
                                }

                                if (exists(editItemCode.getText().toString())) {
                                    Log.e("exists ", "true");
                                    listZone.get(index).setQty(String.valueOf(Integer.parseInt(listZone.get(index).getQty()) + 1));
                                    updateQtyOfRow(listZone.get(index).getItemCode(), listZone.get(index).getQty());
                                    editItemCode.setText("");
                                    fillAdapter(listZone);

                                } else {
                                    Log.e("editItemCode", editItemCode.getText().toString());

                                    if (indexZone != -1) {
                                        Log.e("itemKintText", "" + itemKintText.getText().toString() + "\t" + validateKind);
                                        if (itemKintText.getText().toString().equals("") && validateKind == false) {
                                            validateItemKind(editItemCode.getText().toString().trim());
                                        }
                                    } else {
//
                                        editItemCode.setText("");
                                        editItemCode.setError("Invalid Item");
                                        editItemCode.requestFocus();

                                    }
                                }


                            } else {
                                editItemCode.requestFocus();
                                Log.e("elseeditZoneCode", editZoneCode.getText().toString());
                            }
                            break;
                        }
                    }

                }
//            }
            return true;
        }
    };

    private void validateItemKind(String itemNo) {
        validateKind=true;
       // http://localhost:8082/IrGetItemData?CONO=290&ITEMCODE=28200152701
       importData.getKindItem(itemNo);
    }

    private void searchZone(String codeZone) {
        indexZone=-1;
        editZoneCode.setError(null);
        for(int i=0;i<listAllZone.size();i++)
        {
            if(listAllZone.get(i).getZoneCode().equals(codeZone))
            {
                indexZone=i;
                fillData(i,"");


                break;
            }
        }
        if(indexZone==-1) {
            if (listAllZone.size() != 0) {
                editItemCode.setEnabled(false);
                editQty.setEnabled(false);
                editZoneCode.setText("");
                editZoneCode.setError("Invalid Zone");

            }
            else {
                editZoneCode.setText("");
                Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private String getusernum() {
        return "";
    }
    private void addRow() {
        if (generalMethod.validateNotEmpty(editZoneCode)) {
            if (generalMethod.validateNotEmpty(editItemCode)) {
                if (generalMethod.validateNotEmpty(editQty)) {
                    if(generalMethod.validateNotZero(editQty))
                    {
                        ZoneModel itemZone = new ZoneModel();
                        itemZone.setZoneCode(editZoneCode.getText().toString().trim());
                        itemZone.setItemCode(editItemCode.getText().toString().trim());
                        itemZone.setItemCode( importData.barcode);
                        itemZone.setQty(editQty.getText().toString().trim());
                        itemZone.setIsPostd("0");
                        itemZone.setZONETYPE(zonetype);
                      itemZone.setItemName(itemName.getText().toString());
                        itemZone.setZONENAME(zoneName.getText().toString());
                        itemZone.setStoreNo("6");
                        itemZone.setZoneDate(generalMethod.getCurentTimeDate(1));
                        itemZone.setZoneTime(generalMethod.getCurentTimeDate(2));
                        itemZone.setStoreNo(getusernum());

                  ///ayah edit
                      /*  if(itemCodeExist(convertToEnglish(editItemCode.getText().toString().trim())))
                        {
                            updateListZones(itemZone,updatedIndex);

                        }
                        else {
                            listZone.add(itemZone);
                        }*/
                        listZone.add(itemZone);


                        //


                        editItemCode.setText("");
                        editItemCode.setEnabled(true);
                        editItemCode.requestFocus();

                        fillAdapter(listZone);
                        saveRow(itemZone);
                        clearData();


                    }


                }
            }
        }

    }

    private void saveRow(ZoneModel zone) {
       my_dataBase.zoneDao().insert(zone);

    }
    private void  updateRowsPosted(){
        my_dataBase.zoneDao().updateZonePosted();
    }
    private void updateListZones(ZoneModel itemZone, int updatedIndex) {
        Log.e("updateListZones1",""+listZone.get(updatedIndex).getQty());
        int currentQty=0,qtyNew=0;
        try {
            currentQty=Integer.parseInt(listZone.get(updatedIndex).getQty());
            qtyNew=Integer.parseInt(itemZone.getQty());
            Log.e("updateListZones2",""+currentQty+"\t"+qtyNew);
        }catch (Exception e)
        {}
        listZone.get(updatedIndex).setQty((currentQty+qtyNew)+"");
        Log.e("updateListZones3",""+listZone.get(updatedIndex).getQty());
    }

    private boolean itemCodeExist(String itemCode) {
        for(int i=0;i<listZone.size();i++)
        {
            Log.e("itemCodeExist",""+listZone.get(i).getItemCode()+"\t"+itemCode.trim());
            if((listZone.get(i).getItemCode().trim()).equals(itemCode.trim()))
            {
                updatedIndex=i;
                Log.e("itemCodeExist",""+updatedIndex);
                return  true;
            }
        }
        return  false;
    }

    private void fillAdapter(ArrayList<ZoneModel> listZone) {

        layoutManager = new LinearLayoutManager(AddZone.this);
        recycleZone.setLayoutManager(layoutManager);
        adapter = new ZoneAdapter(AddZone.this, listZone);
        recycleZone.setAdapter(adapter);

    }

    public static void clearData() {
        editItemCode.setText("");

//        editZoneCode.setText("");
        editItemCode.requestFocus();
        itemName.setText("");

    }
    public  static  void clearAllScreenZon(){

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

    public void saveData(View view) {
        switch (view.getId()) {
            case R.id.save:

                listofZonesUnPosted=my_dataBase.zoneDao().getAllZonesUnposted();

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


                break;
        }
    }

    private void saveDataLocaky(int isPosted) {
        if(isPosted==1)
        {
            for (int i=0;i<listZone.size();i++)
            {
                listZone.get(i).setIsPostd("1");
            }
        }
        Log.e("saveDataLocaky","listZone"+listZone.size());
        long result[]= my_dataBase.zoneDao().insertAll(listZone);

        if(result.length!=0)
        {
            generalMethod.showSweetDialog(this,1,this.getResources().getString(R.string.savedSuccsesfule),"");
        }

        clearAllScreenZon();
        fillAdapter(listZone);



    }
 /*  public void exportData(){
       exportData.exportZoneList(listZone,1);
   }*/
 public void exportData(){
     exportData.exportZoneList(listofZonesUnPosted,1);
 }
    public static void clearLists() {
        listZone.clear();

       // adapter.notifyDataSetChanged();

    }

    private void getDataZone() {
       List<ZoneModel> listZon=new ArrayList();
        listZon=my_dataBase.zoneDao().getAllZones();
    }

    public void exitAddZone(View view) {
        if(view.getId()==R.id.cancel_btn)
        {


            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getResources().getString(R.string.confirm_title))
                    .setContentText(getResources().getString(R.string.messageExit))
                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            if (listZone.size() > 0){
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


                                    }})
                                    .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();

                                        }
                                    })
                                    .show();


                        }else{
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

    }

    private void showExitDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.confirm_title))
                .setContentText(getResources().getString(R.string.messageExit))
                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (listZone.size() > 0){
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


                                        }})
                                    .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();

                                        }
                                    })
                                    .show();


                        }else{
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
    showExitDialog();
//    super.onBackPressed();
//    System.exit(0);
}
    public void showZoneDialog(View view) {
        if(view.getId()==R.id.zoneSearch)
        {
            if(listAllZone.size()!=0)
            {
                showDialogSearch(AddZone.this);
            }
            else {
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
        LinearLayout headerComp2=dialog.findViewById(R.id.headerComp2);
       searchlistAllZone = new ArrayList<>();
       String zoneSelected="";

        ArrayList<String> nameOfEngi=new ArrayList<>();
        listZones = dialog.findViewById(R.id.listViewEngineering);

        final int[] rowZone = new int[1];
        final String[] selectedZon= new String[1];
        if( listAllZone.size()!=0)
        {
            nameOfEngi.clear();
            for(int i=0;i<listAllZone.size();i++)
            {
//                nameOfEngi.add("tahani");
                searchlistAllZone.add(listAllZone.get(i));
                nameOfEngi.add(listAllZone.get(i).getZoneCode());
            }
            Log.e("nameOfEngi",""+nameOfEngi.size());
            fillAdapterSearch(nameOfEngi);

//                    simple_list_item_1 simple_list_item_activated_1

        }
        headerComp2.setVisibility(View.GONE);
        EditText searchZoneText=dialog.findViewById(R.id.searchZoneText);
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
                if(searchZoneText.getText().toString().trim().length()!=0)
                { nameOfEngi.clear();
                    searchlistAllZone.clear();
                    for(int i=0;i<listAllZone.size();i++)
                    {

                        if(listAllZone.get(i).getZoneCode().toLowerCase().trim().contains(searchZoneText.getText().toString().toLowerCase().trim()))
                        {
                            nameOfEngi.add(listAllZone.get(i).getZoneCode());
                            searchlistAllZone.add(listAllZone.get(i));
                        }


                    }
                    Log.e("nameOfEngi","search"+nameOfEngi.size());
                    fillAdapterSearch(nameOfEngi);
                }


            }
        });


        listZones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rowZone[0] =position;


                listZones.requestFocusFromTouch();
                listZones.setSelection(position);
                if(searchlistAllZone.size()!=0)
                {searchflage=0;
                    selectedZon[0] =searchlistAllZone.get(position).getZoneCode();


                }else {
                    searchflage=1;
                    selectedZon[0] =listAllZone.get(position).getZoneCode();

                }

                Log.e("nameOfEngi",""+selectedZon[0]);

            }
        });


        Button btn_send = dialog.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setZone( rowZone[0],selectedZon[0]);
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

    void setZone(int i,String zone){
       if(!editZoneCode.getText().toString().trim().equals(""))
       {
           new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                   .setTitleText(getResources().getString(R.string.confirm_title))
                   .setContentText(getResources().getString(R.string.clearAllData))
                   .setConfirmButton(getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                       @Override
                       public void onClick(SweetAlertDialog sweetAlertDialog) {
                           sweetAlertDialog.dismissWithAnimation();
                           clearAllScreenZon();
                           fillAdapter(listZone);
                           fillData(i,zone);


                       }
                   }).setCancelButton(getResources().getString(R.string.cancel), new SweetAlertDialog.OnSweetClickListener() {
               @Override
               public void onClick(SweetAlertDialog sweetAlertDialog) {
                   sweetAlertDialog.dismissWithAnimation();
               }
           })
                   .show();

       }else {
           fillData(i,zone);
       }


    }

    private void fillData(int i,String zone) {
//     if(i!=-1)
//     {
//
//     }
        indexZone=i;

       editZoneCode.setText(listAllZone.get(i).getZoneCode());

        zoneName.setText(listAllZone.get(i).getZONETYPE());
        editZoneCode.setEnabled(false);
        editItemCode.setEnabled(true);
        editItemCode.requestFocus();


    }

    private void updateQtyOfRow(String barecode,String Qty){
        my_dataBase.zoneDao().updateQTY(barecode,Qty);
    }
    private void  OpenDeleteDailog(){


        final Dialog dialog = new Dialog(AddZone.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.deletelayout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        Button deletezone=dialog.findViewById(R.id.deletezone);
        Button deleteitem=dialog.findViewById(R.id.deleteitem);
        zones=new ArrayList<>();
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
                 zones2=new ArrayList<>();
                 zones2.clear();
//                 zonescopylist.clear();
                 zones2=my_dataBase.zoneDao().getAllZonesUnposted();

                 //zonescopylist.addAll(zones2);
                if( zones2.size()>0) {
                    copylist();


                }

                 if( zones2.size()>0) {

                     authenticDailog(2);

                 }
                else
                    Toast.makeText(AddZone.this, "No Data", Toast.LENGTH_SHORT).show();
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
    public boolean isEx(){
     boolean f=false;
 for (int x = 0; x < zones2.size(); x++)
            if (zones2.get(x).getZoneCode().equals(DDzoneEDT.getText().toString().trim())) {
                f=true;
        break;
    }
 else {
                f=false;

    }
 return f;
 }
    public boolean ismatch() {
     boolean f=false;
        for (int x = 0; x < zones2.size(); x++) {

            Log.e("m1 ",zones2.get(x).getZoneCode());
            Log.e("m2 ",zones2.get(x).getItemCode());
            Log.e("m3 ", DDzoneEDT.getText().toString().trim());
            Log.e("m4 ",DDitemcode.getText().toString().trim());
            if (zones2.get(x).getZoneCode().equals(DDzoneEDT.getText().toString().trim())
                    && zones2.get(x).getItemCode().equals(DDitemcode.getText().toString().trim()))
            {

                ind=x;

              f=true;
break;

            } else {
                f= false;
            }

        }
        return f; }


       public void copylist(){
     List<String> zon=new ArrayList<>();
            List<String> item=new ArrayList<>();
            List<String> qtys=new ArrayList<>();
            for (int i = 0; i < zones2.size(); i++)
            {
                zon.add(zones2.get(i).getZoneCode());
                item.add(zones2.get(i).getItemCode());
                qtys.add(zones2.get(i).getQty());
            }

            for (int i = 0; i <  zon.size(); i++)
            {
                ZoneModel zoneModel=new ZoneModel();
                zoneModel.setQty( qtys.get(i));
                zoneModel.setZoneCode(  zon.get(i));
                zoneModel.setItemCode(  item.get(i));
                zonescopylist.add(zoneModel);
            }

        }







    private void openDeleteitemDailog() {


        Log.e(" zones2",""+zones2.size());

        final Dialog Deleteitemdialog = new Dialog(AddZone.this);
        Deleteitemdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Deleteitemdialog.setCancelable(false);
        Deleteitemdialog.setContentView(R.layout.deleteitemdailog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Deleteitemdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        Deleteitemdialog.getWindow().setAttributes(lp);
        Deleteitemdialog.show();
        items=new ArrayList<>();



       DDitemcode=Deleteitemdialog.findViewById(R.id.DD_itemcode);
        DI_itemcode  =Deleteitemdialog.findViewById(R.id.DI_itemcode);
    DIqty=Deleteitemdialog.findViewById(R.id.DI_qty);
      DDzoneEDT =Deleteitemdialog.findViewById(R.id.DD_ZONEcode);
        DI_zonecode=Deleteitemdialog.findViewById(R.id.DI_zone);
        DD_preQTY=Deleteitemdialog.findViewById(R.id.DD_preQTY);
       Button deletebtn= Deleteitemdialog.findViewById(R.id.DD_deleteitem);

        DDitemcode.setEnabled(false);
        deletebtn.setEnabled(false);
        DD_preQTY.setText("");
        DDzoneEDT.requestFocus();
        DDzoneEDT.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != KeyEvent.KEYCODE_ENTER) {

                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (!DDzoneEDT.getText().toString().equals("")) {
                          if (isEx())
                          {
                              DDitemcode.setEnabled(true);
                              DDitemcode.requestFocus();

                                } else {
                                    DDzoneEDT.setError("INvalid");
                                    DDzoneEDT.setText("");
                                }

                        } else {
                            DDitemcode.requestFocus();
                        }
                        return true;
                    }
              }
                return false;
            }
        });
        Deleteitemdialog.findViewById(R.id.cancel1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if(ReducedItemlist.size()>0) {

                    new SweetAlertDialog(AddZone.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.returndata))
                            .setConfirmButton(getResources().getString(R.string.yes),
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            int x;
                                            for (int i = 0; i < ReducedItemlist.size(); i++)
                                            {
                                                Log.e("ReducedItemsList", ReducedItemlist.get(i).getItemCode()+"");
                                                Log.e("ReducedItemsListzone", ReducedItemlist.get(i).getZoneCode()+"");
                                                preQTY=getpreQty(ReducedItemlist.get(i).getZoneCode(),ReducedItemlist.get(i).getItemCode());
                                                x=  my_dataBase.zoneDao().updateQTYreduced(ReducedItemlist.get(i).getItemCode(), preQTY, ReducedItemlist.get(i).getZoneCode());
                                                zones2.get(i).setQty(preQTY);

                                                Log.e("valux==",x+"");


                                            }
                                            Toast.makeText(AddZone.this,getResources().getString(R.string.dataRet),Toast.LENGTH_LONG).show();

                                            AddZone.DDitemcode.setText("");
                                            AddZone.DI_zonecode.setText("");
                                            AddZone.DIqty.setText("");
                                            AddZone.DD_preQTY.setText("");
                                            AddZone.DI_itemcode.setText("");

                                            AddZone.DDzoneEDT.setText("");
                                            AddZone.DDzoneEDT.setEnabled(true);
                                            AddZone.DDzoneEDT.requestFocus();
                                            AddZone.DDitemcode.setEnabled(false);
                                            ReducedItemlist.clear();
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


                }
                else
                {
                    Toast.makeText(AddZone.this,getResources().getString(R.string.NODATA),Toast.LENGTH_LONG).show();
                }
                ///





            }
        });

        Deleteitemdialog.findViewById(R.id.zoneSearch2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                zones.clear();
                zones=my_dataBase.zoneDao().getZonesUnposted();
                if(zones.size()!=0) {
                   flage3=6;
                    searchZonecodeDailog();
                }
                else
                    Toast.makeText(AddZone.this,"No Data",Toast.LENGTH_LONG).show();


            }
        });


        //dditemcode=DDitemcode.getText().toString().trim();
        DDitemcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i != KeyEvent.KEYCODE_ENTER) {

                    {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            if (!DDitemcode.getText().toString().equals(""))

                           {


                                 if (ismatch())
                                 {


                                     DD_preQTY.setText(zonescopylist.get(ind).getQty()+"");

                                     Log.e("zonesforPre_qty", zonescopylist.get(ind).getQty()+"");
                                     Log.e( " ind ",ind +"");
                                     sumOfQTY = Integer.parseInt(zones2.get(ind).getQty()) ;
                                     if(  sumOfQTY >1) {
                                         sumOfQTY -= 1;
                                         // oldQTY.setText( sumOfQTY+"" );
                                         Log.e(" sumOfQTY ", sumOfQTY + "");
                                         DI_itemcode.setText(  DDitemcode.getText().toString());
                                    if( ReducedItemlist.size()>0)
                                    {

                                        if(notExists(zones2.get(ind).getZoneCode(),zones2.get(ind).getItemCode()))
                                         {
                                             Log.e("heere","heere");
                                             ZoneModel zoneModel=new ZoneModel();
                                             zoneModel.setItemCode(zones2.get(ind).getItemCode());
                                             zoneModel.setZoneCode(zones2.get(ind).getZoneCode());
                                             //zoneModel.setQty(zonescopylist.get(ind).getQty());


                                             Log.e("zoneModelitemcode", zones2.get(ind).getItemCode()+"");
                                             Log.e("zoneModelzonecode", zones2.get(ind).getZoneCode()+"");
                                             Log.e("zoneModelzoneqty", zonescopylist.get(ind).getQty()+"");



                                             ReducedItemlist.add(zoneModel);

                                         }
                                    }
                                    else
                                         {
                                             ZoneModel zoneModel=new ZoneModel();

                                             zoneModel.setItemCode(zones2.get(ind).getItemCode());
                                             zoneModel.setZoneCode(zones2.get(ind).getZoneCode());
                                           //  zoneModel.setQty(zonescopylist.get(ind).getQty());
                                             Log.e("elzoneModelitemcode", zones2.get(ind).getItemCode()+"");
                                             Log.e("elzoneModelzonecode", zones2.get(ind).getZoneCode()+"");
                                             ReducedItemlist.add(zoneModel);
                                         }
                                         zones2.get(ind).setQty(sumOfQTY + "");
                                         //  sumOfQTY -= 1;
                                         my_dataBase.zoneDao().reduceQTY(DDitemcode.getText().toString().trim(), String.valueOf(sumOfQTY), DDzoneEDT.getText().toString().trim());
                                         DIqty.setText(sumOfQTY + "");
                                         DI_zonecode.setText(zones2.get(ind).getZoneCode());

                                         deletebtn.setEnabled(true);
                                         DDitemcode.setText("");
                                         DDitemcode.requestFocus();

                                     }
                                     else {
                                         new SweetAlertDialog(AddZone.this, SweetAlertDialog.BUTTON_CONFIRM)
                                                 .setTitleText(getResources().getString(R.string.confirm_title))
                                                 .setContentText(getResources().getString(R.string.delete3))
                                                 .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                     @Override
                                                     public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                         Log.e("zonecodez",DI_zonecode.getText().toString());
                                                         Log.e("itemcodez",DI_itemcode.getText().toString());

                                                         int z= my_dataBase.zoneDao().deleteITEM(DI_zonecode.getText().toString(), DI_itemcode.getText().toString());
                                                         Log.e("valusofz",z+"");
                                                         Log.e("removezones2",zones2.get(ind).getItemCode());
                                                         Log.e("removezonesforPre_qty", zonescopylist.get(ind).getQty());
                                                         zones2.remove(ind);
                                                         // zonesforPre_qty.remove(ind);

                                                         zones.clear();
                                                         zones=my_dataBase.zoneDao().getZonesUnposted();

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

                                         DI_itemcode.setText(  DDitemcode.getText().toString());
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
                                }
                            else
                                DDitemcode.requestFocus();
                        }
                    }
                    return true;}

                return  false;     }
                 });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!DI_itemcode.getText().toString().trim().equals("")&& ! DI_zonecode.getText().toString().trim().equals("")) {
                      try {
                          Log.e("zonecodez",DI_zonecode.getText().toString());
                          Log.e("itemcodez",DI_itemcode.getText().toString());

                          int z= my_dataBase.zoneDao().deleteITEM(DI_zonecode.getText().toString(), DI_itemcode.getText().toString());
                          Log.e("valusofz",z+"");
                          Log.e("removezones2",zones2.get(ind).getItemCode());
                          Log.e("removezonesforPre_qty", zonescopylist.get(ind).getQty());
                          zones2.remove(ind);
                          // zonesforPre_qty.remove(ind);

                          zones.clear();
                          zones=my_dataBase.zoneDao().getZonesUnposted();

                          cleardataOfDailog();


               }catch (Exception e){

               }}
               else
                    {
                        if(DDitemcode.getText().toString().trim().equals("")) DDitemcode.setError("required");
                        if(DDzoneEDT.getText().toString().trim().equals("")) DDitemcode.setError("required");
                    }
                }
        });


        Deleteitemdialog.findViewById(R.id.DD_dialogcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deleteitemdialog.dismiss();
            }
        });

    }

    private String getpreQty(String zone, String itemcode) {
     for(int i=0;i<zonescopylist.size();i++)
         if(zone.equals(zonescopylist.get(i).getZoneCode())&&itemcode.equals(zonescopylist.get(i).getItemCode()))
     return zonescopylist.get(i).getQty();

             return  "";
    }

    private boolean notExists(String zonecode,String itemcode) {
        boolean flage=false;
    for(int i=0;i<ReducedItemlist.size();i++)
    if(!ReducedItemlist.get(i).getZoneCode().equals(zonecode)&&!ReducedItemlist.get(i).getItemCode().equals(itemcode)  )
        {
            flage=true;
        }

    else
        {

        flage=false;
        break;
        }
        return  flage;
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

        dialog.findViewById(R.id.BACK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
                                        int sumqty = my_dataBase.zoneDao().GetQtyOfZone(zonebarecode.getText().toString());
                                        qty1.setText(sumqty + "");


                                        //set zonename
                                        String zoneNam = my_dataBase.zoneDao().GetNameOfZone(zonebarecode.getText().toString());
                                        zonename1.setText(zoneNam);
                                    } catch (Exception e) {

                                    }
                                } else {
                                    Toast.makeText(AddZone.this, "No Data", Toast.LENGTH_LONG).show();
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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                zones.clear();
                zones = my_dataBase.zoneDao().getZonesUnposted();
                if (zones.size() != 0) {
                    flage3 = 0;

                    searchZonecodeDailog();
                } else
                    Toast.makeText(AddZone.this, "No Data", Toast.LENGTH_LONG).show();


            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!zonebarecode.getText().toString().equals(""))
                 {   if(deletedZonsList.size()==0)
                     deletedZonsList=my_dataBase.zoneDao().getzoneRows(zonebarecode.getText().toString().trim());
                 else
                     deletedZonsList.addAll(my_dataBase.zoneDao().getzoneRows(zonebarecode.getText().toString().trim()));
                     int c= my_dataBase.zoneDao().deletezonedata(zonebarecode.getText().toString().trim());
                     zones.remove(zonebarecode.getText().toString().trim());
                     zonebarecode.setText("");
                     zonecode1.setText("");
                     qty1.setText("");
                     zonename1.setText("");
                     }

                else zonebarecode.setError(getResources().getString(R.string.required));


            }
        });

        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(deletedZonsList.size()>0)
                {     new SweetAlertDialog(AddZone.this, SweetAlertDialog.WARNING_TYPE)
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
                                        Toast.makeText(AddZone.this,getResources().getString(R.string.dataRet),Toast.LENGTH_LONG).show();
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
                        .show();}
                else
                {
                    Toast.makeText(AddZone.this,getResources().getString(R.string.NODATA),Toast.LENGTH_LONG).show();

                }

            }
        });
        dialog.show();
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

        Button button= authenticationdialog.findViewById(R.id.authentic);
        Button cancelbutton= authenticationdialog.findViewById(R.id.cancel2);
        EditText UsNa= authenticationdialog.findViewById(R.id.username);
        UsNa.requestFocus();

        EditText pass= authenticationdialog.findViewById(R.id.pass);
        pass.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UsNa.getText().toString().trim().equals("123")&&pass.getText().toString().trim().equals("123"))
                {
                     if(enterflage==1)
                    openDeleteZoneDailog();
                     else
                         openDeleteitemDailog();


                }
                else {

                    if(!UsNa.getText().toString().trim().equals("123"))
                    {
                        UsNa.setError(getResources().getString(R.string.invalid_username));

                    }
                    else {

                    } pass.setError(getResources().getString(R.string.invalid_password));
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



    private void searchZonecodeDailog() {
        searchdialog = new Dialog(AddZone.this);
        searchdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchdialog.setCancelable(true);
        searchdialog.setContentView(R.layout.dialog_zone_search);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(searchdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        searchdialog.getWindow().setAttributes(lp);
        Button button=searchdialog.findViewById(R.id.btn_send);
        button.setVisibility(View.GONE);
        listView=searchdialog.findViewById(R.id.listViewEngineering);
        ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(AddZone.this,zones);
        listView.setAdapter(adapter);
List<String> list=new ArrayList<>();

        EditText editText =searchdialog.findViewById(R.id.searchZoneText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    if(!editable.toString().equals("")){
                        list.clear();
                        for (int i=0;i<zones.size();i++)
                            if(zones.get(i).toUpperCase().contains(editable.toString().trim().toUpperCase()))
                        list.add(zones.get(i));

                        ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(AddZone.this, list);
                        listView.setAdapter(adapter);
                    }
                    else
                    {
                        ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(AddZone.this,zones);
                        listView.setAdapter(adapter);
                    }
            }
        });


        searchdialog.show();

    }
}



        /*int sumOfQTY;

                                dditemcode=DDitemcode.getText().toString().trim();
                                if(items.contains(DDitemcode.getText().toString().trim())) {

                                    ZoneModel zoneModel=my_dataBase.zoneDao().getzone(DDitemcode.getText().toString().trim());
                                   if(zoneModel!=null)
                                   {

                                       sumOfQTY=Integer.parseInt(zoneModel.getQty());

                                       if( sumOfQTY!=1)
                                       {    sumOfQTY-=1;
                                       my_dataBase.zoneDao().updateQTY(DDitemcode.getText().toString().trim(), (String.valueOf(sumOfQTY) ));


                                    //FILL DATA OF SELECT item
                                    DIitemcode.setText(DDitemcode.getText().toString().trim());
                                  //  int sum= my_dataBase.zoneDao().getsumofqty(DDitemcode.getText().toString().trim());

                                    DIqty.setText(sumOfQTY+"");
                                    String Zname=my_dataBase.zoneDao().getzonename(DDitemcode.getText().toString().trim());
                                    DIitzone.setText(Zname);

                                       DDitemcode.setText("");*/

                                /*   else
                                   {

                                       //FILL DATA OF SELECT item
                                       DIitemcode.setText(DDitemcode.getText().toString().trim());
                                       DIqty.setText(sumOfQTY+"");

                                       String Zname=my_dataBase.zoneDao().getzonename(DDitemcode.getText().toString().trim());
                                       DIitzone.setText(Zname);



                                       new SweetAlertDialog(AddZone.this, SweetAlertDialog.BUTTON_CONFIRM)
                                               .setTitleText(getResources().getString(R.string.confirm_title))
                                               .setContentText(getResources().getString(R.string.delete3))
                                               .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                   @Override
                                                   public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                       saveDailog2(DDitemcode.getText().toString());
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

                                   }*/