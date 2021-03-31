package com.example.irbidcitycenter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.Adapters.ReplacementAdapter;
import com.example.irbidcitycenter.Adapters.ZoneAdapter;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddZone extends AppCompatActivity {
    GeneralMethod generalMethod;
    public static EditText editZoneCode, editItemCode, editQty;
    public static final int REQUEST_Camera_Barcode = 1;
    public static ArrayList<ZoneModel> listZone;
    RecyclerView recycleZone;
    LinearLayoutManager layoutManager;

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
        editQty.setOnEditorActionListener(onEditAction);
        listZone = new ArrayList<>();
        recycleZone = findViewById(R.id.recycleZone);
    }

    TextView.OnEditorActionListener onEditAction = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() != KeyEvent.ACTION_UP) {

                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_NULL) {
                    switch (textView.getId()) {
                        case R.id.editZoneCode:
                            editItemCode.requestFocus();
                            break;
                        case R.id.editItemCode:
                            editQty.requestFocus();
                            break;
                        case R.id.editQty:
                            addRow();
                            break;
                    }

                }
            }
            return true;
        }
    };

    private void addRow() {
        if (generalMethod.validateNotEmpty(editZoneCode)) {
            if (generalMethod.validateNotEmpty(editItemCode)) {
                if (generalMethod.validateNotEmpty(editQty)) {
                    ZoneModel itemZone = new ZoneModel();
                    itemZone.setZoneCode(editZoneCode.getText().toString());
                    itemZone.setItemCode(editItemCode.getText().toString());
                    itemZone.setQty(editQty.getText().toString());
                    listZone.add(itemZone);
                    fillAdapter(listZone);
                }
            }
        }

        // generalMethod.showSweetDialog(AddZone.this, 0, "Barcode", "Added");

    }

    private void fillAdapter(ArrayList<ZoneModel> listZone) {

        layoutManager = new LinearLayoutManager(AddZone.this);
        recycleZone.setLayoutManager(layoutManager);
        ZoneAdapter adapter = new ZoneAdapter(AddZone.this, listZone);
        recycleZone.setAdapter(adapter);
        clearData();
    }

    private void clearData() {
        editItemCode.setText("");
        editQty.setText("");
        editZoneCode.setText("");
        editZoneCode.requestFocus();
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

                saveDataLocaky();
                break;
        }
    }

    private void saveDataLocaky() {

    }
}



