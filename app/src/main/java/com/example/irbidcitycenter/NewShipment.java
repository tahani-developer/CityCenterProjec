package com.example.irbidcitycenter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
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
    String POnumselected,  BOXnumselected;
    RecyclerView recyclerView;
    TextView searchView1,searchView2;
    int Qty;
    ShipmentAdapter adapter;
    private List<Shipment> shipmentList = new ArrayList<>();

    Shipment shipment;
    ListView listView;
    ArrayAdapter<String > searchadapter;
    public  static  int position=1;
    public static final int REQUEST_Camera_Barcode = 1;

    private String[] List = new String[]{"po100", "po101",
            "po102","po103"
            ,"po104","po105",
            "po106","po107"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shipment);

 init();
        pono.requestFocus();
        searchView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showdailogponumber();
            pono.setText( POnumselected);
            }
        });


        searchView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showdailogboxnumber();

            }
        });



     /* pono.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if (keyCode == EditorInfo.IME_ACTION_DONE
                        ||event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {


                    if(!TextUtils.isEmpty(poNo))
                    boxno.requestFocus();
                    else {
                        pono.setError("req");
                        pono.requestFocus();
                    }
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
          showdailog(NewShipment.this);
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
                        break;

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
    private void init(){
        pono=findViewById(R.id.poNotxt);
        boxno=findViewById(R.id.boxNotxt);
        barcode=findViewById(R.id.barCodetxt);
        qty=findViewById(R.id.Qtytxt);
        recyclerView=findViewById(R.id.shipRec);

        searchView1=findViewById(R.id.ponoSearch);
        searchView2=findViewById(R.id.boxnoSearch);

    }
void  search(){
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





   private void showdailog( Activity activity){

    final Dialog dialog = new Dialog(activity);
       dialog.setCancelable(false);
       dialog.setContentView(R.layout.pono_dialog_listview);

       /*Button btndialog = (Button) dialog.findViewById(R.id.btndialog);
       btndialog.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               dialog.dismiss();
           }
       });*/

       ListView listView = (ListView) dialog.findViewById(R.id.listview);
       ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.pono_dialog_listview, R.id.tv, List);
       listView.setAdapter(arrayAdapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           //textView.setText("You have clicked : "+List[position]);
               dialog.dismiss();
           }
       });

       dialog.show();
    }


void showdailogponumber(){
    AlertDialog.Builder builderSingle = new AlertDialog.Builder(NewShipment.this);
    //builderSingle.setIcon(R.drawable.ic_start_up);
    builderSingle.setTitle("Select One Name:-");

    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(NewShipment.this, android.R.layout.select_dialog_item);
    arrayAdapter.add("po100");
    arrayAdapter.add("po101");
    arrayAdapter.add("po102");
    arrayAdapter.add("po103");
    arrayAdapter.add("po104");
    arrayAdapter.add("po105");


    builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });

    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            POnumselected = arrayAdapter.getItem(which);
            AlertDialog.Builder builderInner = new AlertDialog.Builder(NewShipment.this);
            builderInner.setMessage(POnumselected);
            builderInner.setTitle("Your Selected Item is");
            builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,int which) {
                    dialog.dismiss();
                    pono.setText( POnumselected);

                }
            });
            builderInner.show();

        }
    });
    builderSingle.show();

    }






    void showdailogboxnumber(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(NewShipment.this);
        //builderSingle.setIcon(R.drawable.ic_start_up);
        builderSingle.setTitle("Select One Name:-");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(NewShipment.this, android.R.layout.select_dialog_item);
        arrayAdapter.add("box100");
        arrayAdapter.add("box101");
        arrayAdapter.add("box102");
        arrayAdapter.add("box103");
        arrayAdapter.add("box104");
        arrayAdapter.add("box105");


        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BOXnumselected = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(NewShipment.this);
                builderInner.setMessage(  BOXnumselected);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                        boxno.setText(  BOXnumselected);

                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

}
