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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.GeneralMethod;
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




public class Replacement extends AppCompatActivity {
    GeneralMethod generalMethod;
 Spinner fromSpinner,toSpinner;
    public static EditText  zone,itemcode;
    EditText qty;
    Button save;
    public RoomAllData my_dataBase;
   String From,To,Zone,Itemcode,Qty;
   ReplacementModel replacement;
    ReplacementModel replacementModel;
   ReplacementAdapter adapter;
    FloatingActionButton add;
   RecyclerView replacmentRecycler;
    public static final int REQUEST_Camera_Barcode = 1;

    public static List<ReplacementModel>  replacementlist = new ArrayList<>();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement);
        init();


        my_dataBase= RoomAllData.getInstanceDataBase(Replacement.this);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           // for (int i=0; i<replacementlist.size();i++)
           //     model.insert(replacementlist.get(i));

                saveData();
            }
              });


         findViewById(R.id.Re_cancel).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 replacementlist.clear();
                 adapter.notifyDataSetChanged();

             }
         });


    }

    private void init() {
        fromSpinner=findViewById(R.id.fromspinner);
        toSpinner=findViewById(R.id.tospinner);
        zone=findViewById(R.id.zoneedt);
        itemcode=findViewById(R.id.itemcodeedt);
        qty=findViewById(R.id.qtyedt);
        replacmentRecycler=findViewById(R.id.replacmentRec);
        save=findViewById(R.id.save);
        qty.setOnEditorActionListener(onEditAction);
        generalMethod=new GeneralMethod(Replacement.this);
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
                        itemcode.requestFocus();
                        break;

                    case R.id.itemcodeedt:
                        qty.requestFocus();
                        break;
                    case R.id.qtyedt:
                        filldata();
                        zone.setText("");
                        itemcode.setText("");
                        qty.setText("");
                        break;
                }

            }

            return true;
        }
    };
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
                     replacement.setItemcode(Itemcode);
                     replacement.setQty(Qty);
                     replacement.setReplacementDate(generalMethod.getCurentTimeDate(1)+"");

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

        replacementlist.clear();


    }
}