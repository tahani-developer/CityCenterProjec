package com.example.irbidcitycenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import com.example.irbidcitycenter.Adapters.ShipmentAdapter;
import com.example.irbidcitycenter.Models.Shipment;

import java.util.ArrayList;
import java.util.List;

public class NewShipment extends AppCompatActivity {
EditText  pono,boxno,barcode,qty;
    String  poNo;
    String boxNo;
    String barCode;
    RecyclerView recyclerView;
    int Qty;
    ShipmentAdapter adapter;
    private List<Shipment> shipmentList = new ArrayList<>();
    Shipment shipment;
    public  static  int position=1;
    public static final int REQUEST_Camera_Barcode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shipment);

        // init
        pono=findViewById(R.id.poNotxt);
        boxno=findViewById(R.id.boxNotxt);
        barcode=findViewById(R.id.barCodetxt);
        qty=findViewById(R.id.Qtytxt);
        recyclerView=findViewById(R.id.shipRec);




/*
      pono.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if (keyCode == EditorInfo.IME_ACTION_DONE
                        ||event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                    // Perform action on key press
                    boxno.requestFocus();
                    return true;
                }
                return false;
            }
        });
        boxno.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    barcode.requestFocus();
                    return true;
                }
                return false;
            }
        });
        barcode.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    qty.requestFocus();
                    return true;
                }
                return false;
            }
        });
        qty.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    filldata();
                    return true;
                }
                return false;
            }
        });





  findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
filldata();
      }

  });


  }
    TextView.OnEditorActionListener onEditAction = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                    || i == EditorInfo.IME_NULL) {
                switch (textView.getId()) {
                    case R.id.poNotxt:
                        boxno.requestFocus();
                        break;
                    case R.id.boxNotxt:
                        barcode.requestFocus();
                        break;
                    case R.id.barCodetxt:
                        qty.requestFocus();
                        break;
                    case R.id.Qtytxt:
                        filldata();
                }

            }
            return true;
        }
    };
private void filldata(){

    poNo=pono.getText().toString();
    boxNo=boxno.getText().toString();
    barCode=barcode.getText().toString();
    Qty=Integer.parseInt(qty.getText().toString());
    shipment=new Shipment( poNo,boxNo,barCode, Qty);
    shipmentList.add(shipment);
    recyclerView.setLayoutManager(new LinearLayoutManager(NewShipment.this));
    adapter = new ShipmentAdapter(NewShipment.this,shipmentList);
    recyclerView.setAdapter(adapter);
}
    public void ScanCode(View view) {
        switch (view.getId()) {
            case R.id.boxnumscan:
                readBarcode(1);
                break;
            case R.id.barcodescan:
                readBarcode(2);
                break;
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
                Toast.makeText(NewShipment.this, "cancelled", Toast.LENGTH_SHORT).show();


            } else {
                String valueBarcode = Result.getContents();

            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    }
