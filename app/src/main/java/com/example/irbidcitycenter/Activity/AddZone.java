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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.Adapters.PonoSearchAdapter;
import com.example.irbidcitycenter.Adapters.ZoneAdapter;
import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;
import com.example.irbidcitycenter.ScanActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.irbidcitycenter.Activity.NewShipment.colsedialog;
import static com.example.irbidcitycenter.ImportData.listAllZone;

public class AddZone extends AppCompatActivity {
    GeneralMethod generalMethod;
    public static EditText editZoneCode, editItemCode, editQty,itemKintText,exportStateText;
    public static final int REQUEST_Camera_Barcode = 1;
    public static ArrayList<ZoneModel> listZone;
    RecyclerView recycleZone;
    LinearLayoutManager layoutManager;
    public RoomAllData my_dataBase;
    public static ZoneAdapter adapter;
    ImportData importData;
   public static TextView zoneName;
    public  static String itemKind="";
    public  int indexZone=-1,updatedIndex=-1;
    ExportData exportData;
    public static boolean validItem=false,validateKind=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_zone);

        initial();
    }

    private void initial() {
        generalMethod = new GeneralMethod(AddZone.this);
        editZoneCode = findViewById(R.id.editZoneCode);
        editItemCode = findViewById(R.id.editItemCode);
        editItemCode.setOnEditorActionListener(onEditAction);
        editZoneCode.setOnEditorActionListener(onEditAction);
        editQty = findViewById(R.id.editQty);
        zoneName= findViewById(R.id.zoneName);
        itemKintText= findViewById(R.id.itemKintText);
        exportStateText= findViewById(R.id.exportStateText);
        editQty.setOnEditorActionListener(onEditAction);
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
                        generalMethod.showSweetDialog(AddZone.this,3,"This Item Not Exist","");
                        itemKintText.setText("");
                    }
                    else {
                        validateKind=false;
                        Log.e("afterTextChanged",""+editable.toString());
                        compareItemKind(editable.toString().trim());
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
                        saveDataLocaky(1);
                    }
                    else  if(editable.toString().trim().equals("not"))
                    {
                        saveDataLocaky(0);
                    }
                }
            }
        });
    }

    private void compareItemKind(String itemKind) {
        validItem=false;
        if(listAllZone.get(indexZone).getZONETYPE().equals(itemKind))
        {
            validItem=true;
            editItemCode.setEnabled(false);
            editQty.setEnabled(true);
            editQty.requestFocus();}
        else {
            editQty.setEnabled(false);
            generalMethod.showSweetDialog(AddZone.this,0,"Item Kind Not Match To Zone Type","");
        }
        itemKintText.setText("");
    }

    TextView.OnEditorActionListener onEditAction = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//            if (keyEvent.getAction() != KeyEvent.ACTION_UP) {// null object reference
               // Log.e("onEditorAction","i"+i+"\ti"+keyEvent.getDeviceId());
                if (i!= KeyEvent.ACTION_UP) {// duplicate
                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_NULL) {
                    switch (textView.getId()) {
                        case R.id.editZoneCode:
                            searchZone(editZoneCode.getText().toString().trim());

                            break;
                        case R.id.editItemCode:
                            if(indexZone!=-1)
                            {
                                Log.e("itemKintText",""+itemKintText.getText().toString()+"\t"+validateKind);
                                if(itemKintText.getText().toString().equals("")&&validateKind==false)
                                {
                                    validateItemKind(editItemCode.getText().toString().trim());
                                }
                            }

                            else editZoneCode.setError("Invalid Zone");

                            break;
                        case R.id.editQty:
                            if(validItem)
                            {
                                itemKintText.setText("");
                                addRow();
                            }
                            else editQty.setError("Invalid Item");
                            break;
                    }

                }
            }
            return true;
        }
    };

    private void validateItemKind(String itemNo) {
        validateKind=true;
       // http://localhost:8082/IrGetItemData?CONO=290&ITEMCODE=28200152701
       importData.getKindItem(itemNo);
    }

    private void searchZone(String codeZone) {
        editZoneCode.setError(null);
        for(int i=0;i<listAllZone.size();i++)
        {
            if(listAllZone.get(i).getZoneCode().equals(codeZone))
            {
                indexZone=i;

                zoneName.setText(listAllZone.get(i).getZONENAME());
                editZoneCode.setEnabled(false);
                editItemCode.setEnabled(true);
                editItemCode.requestFocus();
                break;
            }
        }
        if(indexZone==-1){
            editItemCode.setEnabled(false);
            editQty.setEnabled(false);
            editZoneCode.setError("Invalid Zone");
        }


    }

    private void addRow() {
        if (generalMethod.validateNotEmpty(editZoneCode)) {
            if (generalMethod.validateNotEmpty(editItemCode)) {
                if (generalMethod.validateNotEmpty(editQty)) {
                    if(generalMethod.validateNotZero(editQty))
                    {
                        ZoneModel itemZone = new ZoneModel();
                        itemZone.setZoneCode(editZoneCode.getText().toString());
                        itemZone.setItemCode(editItemCode.getText().toString());
                        itemZone.setQty(editQty.getText().toString());
                        itemZone.setIsPostd("0");
                        itemZone.setStoreNo("6");
                        itemZone.setZoneDate(generalMethod.getCurentTimeDate(1));
                        itemZone.setZoneTime(generalMethod.getCurentTimeDate(2));
                        if(itemCodeExist(editItemCode.getText().toString()))
                        {
                            updateListZones(itemZone,updatedIndex);

                        }
                        else {
                            listZone.add(itemZone);
                        }
                        editItemCode.setEnabled(true);
                        editItemCode.requestFocus();

                        fillAdapter(listZone);
                        clearData();

                    }


                }
            }
        }

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
            if(listZone.get(i).getItemCode().equals(itemCode.trim()))
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
        editQty.setText("");
//        editZoneCode.setText("");
        editItemCode.requestFocus();
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
                if(listZone.size()!=0)
                {
                    exportData();

                }
                else {
                    generalMethod.showSweetDialog(AddZone.this,3,getResources().getString(R.string.warning),getResources().getString(R.string.fillYourList));
                }


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
   public void exportData(){
       exportData.exportZoneList(listZone,1);
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
          showExitDialog();
        }

    }

    private void showExitDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.confirm_title))
                .setContentText(getResources().getString(R.string.messageExit))
                .setConfirmButton(getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        finish();
                        Intent intent=new Intent(AddZone.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
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
        dialog.setContentView(R.layout.zone_search);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        ArrayList<String> nameOfEngi=new ArrayList<>();
        final ListView listZones = dialog.findViewById(R.id.listViewEngineering);

        final int[] rowZone = new int[1];
        final String[] selectedZon= new String[1];
        if( listAllZone.size()!=0)
        {
            for(int i=0;i<listAllZone.size();i++)
            {
//                nameOfEngi.add("tahani");
                nameOfEngi.add(listAllZone.get(i).getZONENAME());
            }
            Log.e("nameOfEngi",""+nameOfEngi.size());

//                    simple_list_item_1 simple_list_item_activated_1
            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(AddZone.this, android.R.layout.simple_list_item_1, nameOfEngi);
            listZones.setAdapter(itemsAdapter);
        }
        listZones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rowZone[0] =position;
                listZones.requestFocusFromTouch();
                listZones.setSelection(position);
                selectedZon[0] =listAllZone.get(position).getZoneCode();

            }
        });


        Button btn_send = dialog.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setZone( rowZone[0]);
                dialog.dismiss();


            }
        });
        dialog.show();

    }
   void setZone(int i){
       if(!editZoneCode.getText().toString().equals(""))
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
                           fillData(i);

                       }
                   }).setCancelButton(getResources().getString(R.string.cancel), new SweetAlertDialog.OnSweetClickListener() {
               @Override
               public void onClick(SweetAlertDialog sweetAlertDialog) {
                   sweetAlertDialog.dismissWithAnimation();
               }
           })
                   .show();

       }else {
           fillData(i);
       }


    }

    private void fillData(int i) {
        indexZone=i;
        editZoneCode.setText(listAllZone.get(i).getZoneCode());

        zoneName.setText(listAllZone.get(i).getZONENAME());
        editZoneCode.setEnabled(false);
        editItemCode.setEnabled(true);
        editItemCode.requestFocus();
    }
}


