package com.example.irbidcitycenter.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.irbidcitycenter.Adapters.BoxnoSearchAdapter;
import com.example.irbidcitycenter.Adapters.PonoSearchAdapter;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.Models.DatabaseHandler;
import com.example.irbidcitycenter.Models.PO;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;
import com.example.irbidcitycenter.ScanActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import com.example.irbidcitycenter.Adapters.ShipmentAdapter;
import com.example.irbidcitycenter.Models.Shipment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class NewShipment extends AppCompatActivity {
    public static String boxnotag;
    public Button save;
    public static EditText pono;
    static EditText boxno;
    public static EditText barcode;
    public TextView barcodescan;
    EditText qty;
    public static Dialog dialog1,dialog2;
    public static String ponotag;
     static ArrayList<String> boxnumberslist;
    static ArrayList<String> ponumberslist;
    String poNo;
    String boxNo;
    String barCode;
    String Qty;
    GeneralMethod generalMethod;
    String POnumselected, BOXnumselected;
    RecyclerView recyclerView;
    FloatingActionButton add;
    TextView searchView1, searchView2;
    int parceQty;
    ShipmentAdapter adapter;
    BoxnoSearchAdapter boxnoSearchAdapter;
    public static List<Shipment> shipmentList = new ArrayList<>();
    public static List<PO> POlist = new ArrayList<>();
    Shipment shipment;
    RequestQueue requestQueue;
    PO po;
    ListView listView;
    public RoomAllData my_dataBase;
    BoxnoSearchAdapter searchadapter,searchadapter2;
 PonoSearchAdapter ponoSearchAdapter,searchponoSearchAdapter;
    public static int position = 1;
    public static final int REQUEST_Camera_Barcode = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shipment);

        my_dataBase= RoomAllData.getInstanceDataBase(NewShipment.this);



        init();
        save.setEnabled(false);
        pono.requestFocus();
        searchView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          showdailogponumber();

            }
        });
        searchView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdailogboxnumber();
            }});




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                saveData();

            }

        });

        findViewById(R.id.nextbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boxno.setEnabled(true);
                boxno.requestFocus();
                boxno.setText("");
                barcode.setText("");
                qty.setText("1");
                pono.setEnabled(false);
                searchView1.setEnabled(false);
                searchView2.setEnabled(true);

            }
        });

      findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              shipmentList.clear();
              adapter.notifyDataSetChanged();
              save.setEnabled(false);
              barcode.setText("");
              qty.setText("1");
              boxno.setText("");
              boxno.requestFocus();
          }
      });

    }

    EditText.OnEditorActionListener onEditAction = new EditText.OnEditorActionListener() {


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
                               pono.setEnabled(false);
                               searchView1.setEnabled(false);
                               boxno.setEnabled(false);
                               searchView2.setEnabled(false);
                               qty.setText("1");
                               barcodescan.requestFocus();
                               save.setEnabled(true);


                        break;

                }}

            return true;
        }
    };


    private void filldata() {
        poNo = pono.getText().toString();
        boxNo = boxno.getText().toString();
        barCode = barcode.getText().toString();
       Qty = qty.getText().toString();
        if( poNo.toString().trim().equals("")) pono.setError("required");

       else {

            if( boxNo.toString().trim().equals("")) boxno.setError("required");
            else {     if( barCode.toString().trim().equals("")) barcode.setError("required");

                  else   if( Qty.toString().trim().equals("")) {
                         qty.setError("required");
                     }
                     else {   // CheckPOnumber();


                shipment = new Shipment();
                shipment.setPoNo(poNo);
                shipment.setBoxNo(boxNo);
                shipment.setBarcode(barCode);
                shipment.setQty(Qty);
                shipment.setShipmentTime(String.valueOf(generalMethod.getCurentTimeDate(2)));
                shipment.setShipmentDate(String.valueOf(generalMethod.getCurentTimeDate(1)));


                if (hasDuplicates(shipment)) {
                    barcode.setError("");
                    Toast.makeText(NewShipment.this, " item Duplicate", Toast.LENGTH_LONG).show();
                }
                else {
                    shipmentList.add(shipment);
                    filladapter(shipmentList);
                    readBarcode(3);
                }

            }
                         }

    }}

    private void CheckPOnumber() {
        boolean flag=true;
        if(!ponumberslist.isEmpty())
        for(int i=0;i<ponumberslist.size();i++)
            if(!poNo.equals(ponumberslist.get(i)))
                flag=true;
            else
            {      flag=false;
                break;
            }
        if(flag)Toast.makeText(NewShipment.this,"Purchase order not found in ",Toast.LENGTH_LONG).show();
    }

    private void filladapter(java.util.List<Shipment> shipmentList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(NewShipment.this));
        adapter = new ShipmentAdapter(NewShipment.this, shipmentList);
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
            if (type == 3) {
                i.putExtra("key", "3");
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

    private void init() {
        pono = findViewById(R.id.poNotxt);
        boxno = findViewById(R.id.boxNotxt);
        barcode = findViewById(R.id.barCodetxt);
        qty = findViewById(R.id.Qtytxt);
        recyclerView = findViewById(R.id.shipRec);
        barcodescan=findViewById(R.id.barcodescan);
        save= findViewById(R.id.save);
        searchView1 = findViewById(R.id.ponoSearch);
        searchView2 = findViewById(R.id.boxnoSearch);
        qty.setOnEditorActionListener(onEditAction);
        generalMethod=new GeneralMethod(NewShipment.this);
        requestQueue= Volley.newRequestQueue(this);
    }

    void search() {
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

/*
    private void showdailog(Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.pono_dialog_listview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
        final RecyclerView recyclerView1;
        final EditText editText;
        final ArrayList<String> searcharrayAdapter = new ArrayList<>();
        final ArrayList<String> ponumberslist = new ArrayList<>();
        ponumberslist.add("po100");
        ponumberslist.add("po101");
        ponumberslist.add("po102");
        ponumberslist.add("po103");
        ponumberslist.add("po104");
        ponumberslist.add("po105");
        recyclerView1=dialog.findViewById(R.id.listview1);
        editText=dialog.findViewById(R.id.search_edt);

        recyclerView1.setLayoutManager(new LinearLayoutManager(NewShipment.this));
      searchadapter = new SearchAdapter(NewShipment.this,ponumberslist);
        recyclerView1.setAdapter(searchadapter);

      Button btndialog = (Button) dialog.findViewById(R.id.btndialog);
       btndialog.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               dialog.dismiss();
           }
       });
      TextView serach=dialog.findViewById(R.id.dailog_pono_Search);
        serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searcharrayAdapter.clear();
                for(int i=0;i<ponumberslist.size();i++)
                {
                    if (editText.getText().toString().trim().startsWith(ponumberslist.get(i)))
                        searcharrayAdapter.add(ponumberslist.get(i));
                    else
                        Toast.makeText(NewShipment.this,
                                "No Matched data", Toast.LENGTH_SHORT).show();

                }



                searchadapter2 = new SearchAdapter(NewShipment.this,searcharrayAdapter );
                recyclerView1.setAdapter(searchadapter2);


            }

        });



        dialog.show();
    }
*/

    void showdailogponumber() {


    dialog2 = new Dialog(NewShipment.this);
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.pono_dialog_listview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog2.getWindow().getAttributes());
        lp.width = 600;
        lp.height = 800;
        lp.gravity = Gravity.CENTER;

        dialog2.getWindow().setAttributes(lp);
        final RecyclerView recyclerView1;
        final EditText editText;
        final ArrayList<String> searcharrayAdapter = new ArrayList<>();
    ponumberslist = new ArrayList<>();
        ponumberslist.add("po100");
        ponumberslist.add("po101");
        ponumberslist.add("po102");
        ponumberslist.add("po103");
        ponumberslist.add("po104");
        ponumberslist.add("po105");
        ponumberslist.add("po106");
        ponumberslist.add("po107");
        ponumberslist.add("po108");
        ponumberslist.add("po109");
        ponumberslist.add("po110");
        ponumberslist.add("po111");

        recyclerView1=dialog2.findViewById(R.id.listview1);
        editText=dialog2.findViewById(R.id.search_edt);

        recyclerView1.setLayoutManager(new LinearLayoutManager(NewShipment.this));
      ponoSearchAdapter= new PonoSearchAdapter(NewShipment.this,ponumberslist);
        recyclerView1.setAdapter(  ponoSearchAdapter);
        Button btndialog = (Button) dialog2.findViewById(R.id.btndialog);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog2.dismiss();
            }
        });
        TextView serach=dialog2.findViewById(R.id.dailog_pono_Search);
        serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searcharrayAdapter.clear();
                for(int i=0;i<ponumberslist.size();i++)
                {
                    if (editText.getText().toString().trim().startsWith(ponumberslist.get(i)))
                        searcharrayAdapter.add(ponumberslist.get(i));
                   /* else
                        Toast.makeText(NewShipment.this,
                                "No Matched data", Toast.LENGTH_SHORT).show();*/

                }



                searchponoSearchAdapter = new PonoSearchAdapter(NewShipment.this,searcharrayAdapter );
                recyclerView1.setAdapter(searchadapter2);


            }

        });



        dialog2.show();

        dialog2.setCanceledOnTouchOutside(true);


    }


    void showdailogboxnumber() {

        dialog1 = new Dialog(NewShipment.this);
        dialog1.setCancelable(false);
        dialog1.setContentView(R.layout.pono_dialog_listview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = 200;
        lp.height = 400;
        lp.gravity = Gravity.RIGHT;
        lp.setColorMode(ActivityInfo.COLOR_MODE_DEFAULT);


        dialog1.getWindow().setAttributes(lp);
        final RecyclerView recyclerView1;
        final EditText editText;
        final ArrayList<String> searcharrayAdapter = new ArrayList<>();
         boxnumberslist = new ArrayList<>();
        boxnumberslist .add("box100");
        boxnumberslist .add("box101");
        boxnumberslist .add("box102");
        boxnumberslist .add("box103");
        boxnumberslist .add("box104");
        boxnumberslist .add("box105");
        boxnumberslist .add("box100");
        boxnumberslist .add("box101");
        boxnumberslist .add("box102");
        boxnumberslist .add("box103");
        boxnumberslist .add("box104");
        boxnumberslist .add("box105");

        recyclerView1=dialog1.findViewById(R.id.listview1);
        editText=dialog1.findViewById(R.id.search_edt);

        recyclerView1.setLayoutManager(new LinearLayoutManager(NewShipment.this));
        searchadapter = new BoxnoSearchAdapter(NewShipment.this,boxnumberslist );
        recyclerView1.setAdapter(searchadapter);

       LinearLayout linearLayout= dialog1.findViewById(R.id.linear);
        Button btndialog = (Button) dialog1.findViewById(R.id.btndialog);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog1.dismiss();
            }
        });
        TextView serach=dialog1.findViewById(R.id.dailog_pono_Search);
        serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searcharrayAdapter.clear();
                for(int i=0;i<boxnumberslist .size();i++)
                {
                    if (editText.getText().toString().trim().startsWith(boxnumberslist .get(i)))
                        searcharrayAdapter.add(boxnumberslist.get(i));


                }

                searchadapter2 = new BoxnoSearchAdapter(NewShipment.this,searcharrayAdapter );
                recyclerView1.setAdapter(searchadapter2);


            }

        });




        dialog1.show();
        dialog1.setCanceledOnTouchOutside(true);

    }

   public static void colsedialog(int i){
        if(i==1)
       dialog1.dismiss();
        else
           dialog2.dismiss();
    }
    public static void fillEdittext(){
        boxno.setText(boxnumberslist.get(Integer.parseInt(boxnotag)));
    }
    public static void fillEdittext2(){
        pono.setText(ponumberslist.get(Integer.parseInt(ponotag)));
    }
    public boolean hasDuplicates(Shipment shipment){


        for(int i=0;i<shipmentList.size();i++){

                if(shipmentList.get(i).getBoxNo().equals(shipment.getBoxNo())
                && shipmentList.get(i).getBarcode().equals(shipment.getBarcode()))
            {
                    return true;

            }
        }
        return false;
    }
    private void saveData() {

        long result[]= my_dataBase.shipmentDao().insertAll(shipmentList);

        if(result.length!=0)
        {
            generalMethod.showSweetDialog(this,1,this.getResources().getString(R.string.savedSuccsesfule),"");
        }

        shipmentList.clear();


    }



}