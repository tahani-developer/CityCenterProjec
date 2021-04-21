package com.example.irbidcitycenter.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;
import com.example.irbidcitycenter.ScanActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.example.irbidcitycenter.Adapters.ReplacementAdapter;
import com.example.irbidcitycenter.Models.ReplacementModel;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import static com.example.irbidcitycenter.Activity.AddZone.listAllZone;
import static com.example.irbidcitycenter.ImportData.Storelist;
import static com.example.irbidcitycenter.Activity.AddZone.validateKind;

public class Replacement extends AppCompatActivity {

    public static TextView respon;
    GeneralMethod generalMethod;
 Spinner fromSpinner,toSpinner;
    ExportData exportData;
    ImportData importData;
    public static EditText itemKintText;
    public static EditText  zone,itemcode;
    EditText qty;
    Button save;
    //public static boolean validItem=false,validateKind=false;
    public  int indexZone=-1;
    public RoomAllData my_dataBase;
   String From,To,Zone,Itemcode,Qty;
   ReplacementModel replacement;
    ReplacementModel replacementModel;
   ReplacementAdapter adapter;
    FloatingActionButton add;
   RecyclerView replacmentRecycler;
    public static final int REQUEST_Camera_Barcode = 1;
    List<String> spinnerArray = new ArrayList<>();
    public static ArrayList<ReplacementModel>  replacementlist = new ArrayList<>();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement);
        init();


  ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(1);
  zone.requestFocus();
  itemcode.setEnabled(false);
  qty.setEnabled(false);
        my_dataBase= RoomAllData.getInstanceDataBase(Replacement.this);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           // for (int i=0; i<replacementlist.size();i++)
           //     model.insert(replacementlist.get(i));

                saveData();
              exportData();
            }
              });


         findViewById(R.id.Re_cancel).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                if(replacementlist.size()>0) {
                    replacementlist.clear();
                    adapter.notifyDataSetChanged();
                }
             }
         });


    }
    public void exportData() {
        exportData.exportReplacementList(replacementlist);
    }
    private void init() {
        itemKintText= findViewById(R.id.itemKintTextRE);
        exportData = new ExportData(Replacement.this);
        importData = new ImportData(Replacement.this);
        fromSpinner=findViewById(R.id.fromspinner);
        toSpinner=findViewById(R.id.tospinner);
        zone=findViewById(R.id.zoneedt);
        itemcode=findViewById(R.id.itemcodeedt);
        qty=findViewById(R.id.qtyedt);
        replacmentRecycler=findViewById(R.id.replacmentRec);
        save=findViewById(R.id.save);
        respon=findViewById(R.id.respons);
        qty.setOnEditorActionListener(onEditAction);
        itemcode.setOnEditorActionListener(onEditAction);
        zone.setOnEditorActionListener(onEditAction);
        generalMethod=new GeneralMethod(Replacement.this);
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
                if(editable.toString().length()!=0)
                {

                    if(editable.toString().equals("no data"))
                    {

                        respon.setText("");
                    }
                    else {

                        Log.e("afterTextChanged",""+editable.toString());

                        qty.requestFocus();

                    }

                }

            }
        });
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
                        qty.setEnabled(false);
                        generalMethod.showSweetDialog(Replacement.this,3,"This Item Not Exist","");
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
        for(int i=0;i<Storelist.size();i++)
            spinnerArray.add(Storelist.get(i).getSTORENO()+"  "+Storelist.get(i).getSTORENAME());


    }
    private void compareItemKind(String itemKind) {
        /*validItem=false;
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
        itemKintText.setText("");*/
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
    EditText.OnEditorActionListener onEditAction = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                    || i == EditorInfo.IME_NULL) {
                switch (textView.getId()) {
                    case R.id.zoneedt:
                        searchZone(zone.getText().toString());
                       // itemcode.setEnabled(true);
                       // itemcode.requestFocus();
                        break;

                    case R.id.itemcodeedt:

                        zone.setEnabled(false);

                     if(indexZone!=-1)
                        {
                            Log.e("itemKintText",""+itemKintText.getText().toString()+"\t"+validateKind);
                            if(itemKintText.getText().toString().equals("")&&validateKind==false)
                            {
                                validateItemKind(itemcode.getText().toString().trim());
                            }
                        }

                        else zone.setError("Invalid Zone");



                        qty.setEnabled(true);
                        qty.requestFocus();




                        break;

                        case R.id.qtyedt:
                        filldata();
                        zone.setText("");
                        itemcode.setText("");
                        qty.setText("");
                            zone.setEnabled(true);
                            zone.requestFocus();
                            itemcode.setEnabled(false);
                            qty.setEnabled(false);
                        break;
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
        zone.setError(null);
        for(int i=0;i<listAllZone.size();i++)
        {
            if(listAllZone.get(i).getZoneCode().equals(codeZone))
            {
                indexZone=i;

                itemcode.setEnabled(true);
                itemcode.requestFocus();
               // zone.setEnabled(false);
                break;
            }
        }
        if(indexZone==-1){
            //zone.setEnabled(false);
            itemcode.setEnabled(false);
            zone.setError("Invalid Zone");
        }


    }

    private void filldata(){
        From= fromSpinner.getSelectedItem().toString();
      To= toSpinner.getSelectedItem().toString();
       // Toast.makeText(Replacement.this, "sssss", Toast.LENGTH_SHORT).show();


        Zone= zone.getText().toString();
        Itemcode= itemcode.getText().toString();
        Qty= qty.getText().toString();
        if( Zone.toString().trim().equals("")) zone.setError("required");

           else if (Itemcode.toString().trim().equals("")) itemcode.setError("required");


              else  if (Qty.toString().trim().equals(""))
                    qty.setError("required");
                 else {

                    replacement = new ReplacementModel();
                    replacement.setFrom(From);
                    replacement.setTo(To);
            replacement.setZone(Zone);
                     replacement.setItemcode(Itemcode);
                     replacement.setQty(Qty);
                     replacement.setReplacementDate(generalMethod.getCurentTimeDate(1)+"");
                     replacement.setIsPosted("0");

                    replacementlist.add(replacement);
               replacmentRecycler.setLayoutManager(new LinearLayoutManager(Replacement.this));
                    adapter = new ReplacementAdapter(replacementlist, Replacement.this);
                    replacmentRecycler.setAdapter(adapter);
                }


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

    private void saveData() {

        long result[]= my_dataBase.replacementDao().insertAll(replacementlist);

        if(result.length!=0)
        {
            generalMethod.showSweetDialog(this,1,this.getResources().getString(R.string.savedSuccsesfule),"");
        }

        //replacementlist.clear();


    }
}