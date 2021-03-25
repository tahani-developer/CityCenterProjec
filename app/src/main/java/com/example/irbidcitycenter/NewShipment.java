package com.example.irbidcitycenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shipment);
        pono=findViewById(R.id.poNotxt);
        boxno=findViewById(R.id.boxNotxt);
        barcode=findViewById(R.id.barCodetxt);
        qty=findViewById(R.id.Qtytxt);
        recyclerView=findViewById(R.id.shipRec);





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

      }

  });


  }

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



    }
