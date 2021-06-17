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

import com.example.irbidcitycenter.Adapters.PonoSearchAdapter;
import com.example.irbidcitycenter.Adapters.ZoneAdapter;
import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.Shipment;
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

import static com.example.irbidcitycenter.Activity.AddZone.itemKind;
import static com.example.irbidcitycenter.Activity.NewShipment.colsedialog;
import static com.example.irbidcitycenter.GeneralMethod.convertToEnglish;
import static com.example.irbidcitycenter.ImportData.listAllZone;
import static com.example.irbidcitycenter.ImportData.zonetype;

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
   public static TextView zoneName,itemName;
    public  static String itemKind="";
    public  int indexZone=-1,updatedIndex=-1;
    ExportData exportData;
    public static boolean validItem=false,validateKind=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_zone);

        initial();

       /* ZoneModel zoneModel=new ZoneModel();
        zoneModel.setZoneCode("gg");
        zoneModel.setQty("33");
        zoneModel.setItemCode("aaaaa");
        for(int i=0;i<59;i++)
            listZone.add(zoneModel);
        fillAdapter(listZone);*/
    }

    private void initial() {
        MainActivity.setflage=0;
        generalMethod = new GeneralMethod(AddZone.this);
        editZoneCode = findViewById(R.id.editZoneCode);
        editItemCode = findViewById(R.id.editItemCode);
      editItemCode.setOnEditorActionListener(onEditAction);
        editItemCode.setOnKeyListener(onKeyListener);
        editZoneCode.setOnKeyListener(onKeyListener);

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
                       Log.e("here","here");
                        generalMethod.showSweetDialog(AddZone.this,3,"This Item Not Exist","");
                        itemKintText.setText("");
                        editItemCode.setText("");
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
        if(listAllZone.get(indexZone).getZONETYPE().equals(itemTypa))
        {
            validItem=true;
            itemName.setText(itemKind);
            editItemCode.setEnabled(false);
            editQty.setEnabled(true);
            editQty.setText("1");
            if(validItem)
            {
                itemKintText.setText("");
                addRow();
                validItem=false;
            }
//            editQty.requestFocus();
        }
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
//                if (i!= KeyEvent.ACTION_UP) {// duplicate
                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_NULL) {
                    switch (textView.getId()) {
                        case R.id.editZoneCode:
                            if(editZoneCode.getText().length()!=0) {
                                searchZone(editZoneCode.getText().toString().trim());
                                Log.e("editZoneCode",editZoneCode.getText().toString());
                            }
                            else
                            { editZoneCode.requestFocus();
                                Log.e("elseeditZoneCode",editZoneCode.getText().toString());}

                            break;
                        case R.id.editItemCode:
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



                                else {editZoneCode.setError("Invalid Zone");
                                    editZoneCode.setText("");}

                            }
                            else
                            { editItemCode.requestFocus();
                                Log.e("elseeditZoneCode",editZoneCode.getText().toString());
                            }
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
                fillData(i);


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

    private void addRow() {
        if (generalMethod.validateNotEmpty(editZoneCode)) {
            if (generalMethod.validateNotEmpty(editItemCode)) {
                if (generalMethod.validateNotEmpty(editQty)) {
                    if(generalMethod.validateNotZero(editQty))
                    {
                        ZoneModel itemZone = new ZoneModel();
                        itemZone.setZoneCode(editZoneCode.getText().toString().trim());
                        itemZone.setItemCode(editItemCode.getText().toString().trim());
                        itemZone.setQty(editQty.getText().toString().trim());
                        itemZone.setIsPostd("0");
                        itemZone.setZONETYPE(zonetype);
                        itemZone.setItemName(itemName.getText().toString());
                        itemZone.setStoreNo("6");
                        itemZone.setZoneDate(generalMethod.getCurentTimeDate(1));
                        itemZone.setZoneTime(generalMethod.getCurentTimeDate(2));
                        if(itemCodeExist(convertToEnglish(editItemCode.getText().toString().trim())))
                        {
                            updateListZones(itemZone,updatedIndex);

                        }
                        else {
                            listZone.add(itemZone);
                        }
                        editItemCode.setText("");
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
            if(convertToEnglish(listZone.get(i).getItemCode()).equals(convertToEnglish(itemCode.trim())))
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
                if(listZone.size()!=0)
                {
                    for(int i=0;i<listZone.size();i++) {
                        listZone.get(i).setItemCode(convertToEnglish(listZone.get(i).getItemCode()));
                        listZone.get(i).setQty(convertToEnglish(listZone.get(i).getQty()));
                    }
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
                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        new SweetAlertDialog(AddZone.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(getResources().getString(R.string.confirm_title))
                                .setContentText(getResources().getString(R.string.messageExit2))
                                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismissWithAnimation();
                                        finish();
//                        Intent intent=new Intent(AddZone.this,MainActivity.class);
//                        startActivity(intent);
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
                })
              .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                  @Override
                  public void onClick(SweetAlertDialog sweetAlertDialog) {
                      sweetAlertDialog.dismiss();
                  }
              })
                .show();
    }

//    @Override
//    public void onBackPressed() {
////        showExitDialog();
////        finish();
//        super.onBackPressed();
//    }
@Override
public void onBackPressed() {
    super.onBackPressed();
    System.exit(0);
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
        LinearLayout headerComp2=dialog.findViewById(R.id.headerComp2);
        headerComp2.setVisibility(View.GONE);
        ArrayList<String> nameOfEngi=new ArrayList<>();
        final ListView listZones = dialog.findViewById(R.id.listViewEngineering);

        final int[] rowZone = new int[1];
        final String[] selectedZon= new String[1];
        if( listAllZone.size()!=0)
        {
            for(int i=0;i<listAllZone.size();i++)
            {
//                nameOfEngi.add("tahani");
                nameOfEngi.add(listAllZone.get(i).getZoneCode());
            }
            Log.e("nameOfEngi",""+nameOfEngi.size());

//                    simple_list_item_1 simple_list_item_activated_1
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                    (AddZone.this, android.R.layout.simple_list_item_1, nameOfEngi) {
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
//            ArrayAdapter<String> itemsAdapter =
//                    new ArrayAdapter<String>(AddZone.this, android.R.layout.simple_list_item_1, nameOfEngi);
            listZones.setAdapter(arrayAdapter);
        }
        listZones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rowZone[0] =position;
                listZones.requestFocusFromTouch();
                listZones.setSelection(position);
                selectedZon[0] =listAllZone.get(position).getZoneCode();
                Log.e("nameOfEngi",""+selectedZon[0]);

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

        zoneName.setText(listAllZone.get(i).getZONETYPE());
        editZoneCode.setEnabled(false);
        editItemCode.setEnabled(true);
        editItemCode.requestFocus();


    }
}



