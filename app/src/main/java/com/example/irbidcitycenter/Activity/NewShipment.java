package com.example.irbidcitycenter.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.android.volley.toolbox.Volley;
import com.example.irbidcitycenter.Adapters.BoxnoSearchAdapter;
import com.example.irbidcitycenter.Adapters.PonoSearchAdapter;
import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.PO;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;
import com.example.irbidcitycenter.ScanActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import com.example.irbidcitycenter.Adapters.ShipmentAdapter;
import com.example.irbidcitycenter.Models.Shipment;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.irbidcitycenter.Adapters.ShipmentAdapter.sum;
import static com.example.irbidcitycenter.ImportData.BoxNolist;
import static com.example.irbidcitycenter.ImportData.POdetailslist;
import static com.example.irbidcitycenter.ImportData.poqty;
import static com.example.irbidcitycenter.ImportData.posize;


public class NewShipment extends AppCompatActivity {
    boolean saved=false;
    ExportData exportData;
    ImportData importData;
    public static String boxnotag;
    public static int updateflage = 1;
    public Button next;
    public Button save;
    public static TextView respon;
    public static TextView boxnorespon;
    public static EditText pono;

    public static EditText boxno, itemname, PoQTY,poststate;
    public static EditText barcode;
    public TextView barcodescan;
    EditText qty;
    public static Dialog dialog1, dialog2;
    public static String ponotag;
    static ArrayList<String> boxnumberslist;
    static ArrayList<String> ponumberslist;
    public static String poNo;
    String boxNo;
    public static String barCode;
    String Qty;
    GeneralMethod generalMethod;
    String POnumselected, BOXnumselected;
    RecyclerView recyclerView;
    FloatingActionButton add;
    TextView searchView1, searchView2;
    int parceQty;
    public static ShipmentAdapter adapter;
    BoxnoSearchAdapter boxnoSearchAdapter;
    public static ArrayList<Shipment> shipmentList = new ArrayList<>();
    public static List<PO> POlist = new ArrayList<>();
    Shipment shipment;
    RequestQueue requestQueue;
    PO po;
    ListView listView;
    public RoomAllData my_dataBase;
    BoxnoSearchAdapter searchadapter, searchadapter2;
    PonoSearchAdapter ponoSearchAdapter, searchponoSearchAdapter;
    public static int position = 1;
    public static final int REQUEST_Camera_Barcode = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shipment);
        my_dataBase = RoomAllData.getInstanceDataBase(NewShipment.this);
        BoxNolist.clear();
        POdetailslist.clear();
        init();
        save.setEnabled(false);
        pono.requestFocus();
        ///
        boxno.setEnabled(false);
        barcode.setEnabled(false);
        qty.setEnabled(false);
        ////



      /*  searchView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          showdailogponumber();

            }
        });
        searchView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdailogboxnumber();
            }});*/
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (shipmentList.size() > 0) {

                    exportData();
                   // saveData();
                  /*  if(saved==true)
                    {importData.BoxNolist.clear();
                        importData.POdetailslist.clear();
                        shipmentList.clear();
                        adapter.notifyDataSetChanged();

                        filladapter(shipmentList);}*/
                    ///

                    pono.setText("");
                    pono.setEnabled(true);
                    pono.requestFocus();
                    boxno.setText("");
                    barcode.setText("");
                    boxno.setEnabled(false);
                    barcode.setEnabled(false);
                    qty.setEnabled(false);



                    ////
                } else {
                    generalMethod.showSweetDialog(NewShipment.this, 3, getResources().getString(R.string.warning), getResources().getString(R.string.fillYourList));
                }
            }

        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pono.setEnabled(false);

                boxno.setText("");
                boxno.setEnabled(true);
                boxno.requestFocus();
                barcode.setText("");
                barcode.setEnabled(false);
                qty.setText("1");
                qty.setEnabled(false);
                ////
                searchView1.setEnabled(false);
                searchView2.setEnabled(false);

            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shipmentList.size() != 0) {
                    shipmentList.clear();
                    filladapter( shipmentList);

                }
                Intent intent =new Intent(NewShipment.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    TextView.OnEditorActionListener onEditAction = new TextView.OnEditorActionListener() {


        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            //  if (keyEvent.getAction() != KeyEvent.ACTION_UP) {

            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                    || i == EditorInfo.IME_NULL) {
                switch (textView.getId()) {
                    case R.id.poNotxt:

                        if (pono.getText().toString().trim().equals("6")) {
                            getboxData();
                            if (boxnorespon.getText().length() > 0) {
                                NewShipment.boxno.setEnabled(true);
                                NewShipment.boxno.requestFocus();
                            }
                        } else
                            generalMethod.showSweetDialog(NewShipment.this, 3, getResources().getString(R.string.warning), getResources().getString(R.string.invalidPONO));
                        break;
                    case R.id.boxNotxt:
                         pono.setEnabled(false);
                        if (checkboxvalidty())
                        { barcode.setEnabled(true);
                        barcode.requestFocus();}
                        else
                            barcode.setEnabled(false);

                        break;
                    case R.id.barCodetxt:
                        boxno.setEnabled(false);
                        POdetailslist.clear();
                        getPOdetails();

                           // if (checkitemcodevalidty())
                            {
                                qty.setEnabled(true);
                                qty.requestFocus();

                                break;
                                //next.setEnabled(true);
                            }



                    case R.id.RecQtytxt:
                      barcode.setEnabled(false);

                        checkitemcodevalidty();
                            filldata();
                            pono.setEnabled(false);
                            searchView1.setEnabled(false);
                            boxno.setEnabled(false);
                            searchView2.setEnabled(false);

                            barcodescan.requestFocus();
                            next.setEnabled(true);
                            save.setEnabled(true);
                            barcode.setText("");
                            qty.setText("1");

                        /*else {
                            //   barcode.setError("");
                            next.setEnabled(true);
                        }*/
                        barcode.setEnabled(true);
                        barcode.requestFocus();
                        qty.setEnabled(false);


                        //clear item data
                        itemname.setText("");
                        PoQTY.setText("");







                        break;

                }
            }


            return true;
        }
    };
    @Override
    public void onBackPressed() {
        showExitDialog();
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
                        Intent intent=new Intent(NewShipment.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }
    private void getPOdetails() {
        Log.e("newshipment", "getPOdetails");
        barCode = barcode.getText().toString();
        importData.getPOdetails();
    }

    public void exportData() {
        exportData.exportShipmentsList(shipmentList);
    }

    private void getboxData() {
        Log.e("newshipment", "getboxData");
        poNo = pono.getText().toString();
        importData.getboxno();
        //importData.getPOdetails();


    }


    private void filldata() {
        Qty = qty.getText().toString();
        boxNo = boxno.getText().toString();


        ShipmentAdapter.newqty = Qty;

        if (poNo.toString().trim().equals("")) pono.setError("required");

        else {

            if (boxNo.toString().trim().equals("")) boxno.setError("required");
            else {
                if (barCode.toString().trim().equals("")) barcode.setError("required");

                else if (Qty.toString().trim().equals("")) {
                    qty.setError("required");
                } else {   // CheckPOnumber();


                    shipment = new Shipment();
                    shipment.setPoNo(poNo);
                    shipment.setBoxNo(boxNo);
                    shipment.setBarcode(barCode);
                    shipment.setQty(Qty);
                    shipment.setIsPosted("0");
                    shipment.setDiffer(getDiff() + "");
                    shipment.setShipmentTime(String.valueOf(generalMethod.getCurentTimeDate(2)));
                    shipment.setShipmentDate(String.valueOf(generalMethod.getCurentTimeDate(1)));
                    shipment.setPoqty(PoQTY.getText().toString());
                    shipment.setItemname(itemname.getText().toString());


                    if (AddInCaseDuplicates(shipment)) {
                        // barcode.setError("");
                        // Toast.makeText(NewShipment.this, " item Duplicate", Toast.LENGTH_LONG).show();

                    } else {
                        shipmentList.add(shipment);
                        updateflage = 1;
                        filladapter(shipmentList);
                        try {
                            readBarcode(3);
                        }catch (Exception e)
                        {
                            Log.e("readBarcode",""+e.getMessage());
                        }

                    }

                }
            }

        }
    }

    public int getDiff() {

        sum-= Integer.parseInt(Qty);
        Log.e("getDifferentQTY()  sum", String.valueOf(sum));


        //PoQTY.setText(sum + "");
        Log.e("getDifferentQTY()  newqty", String.valueOf(Qty));
        return sum;
    }


    private void CheckPOnumber() {
        boolean flag = true;
        if (!ponumberslist.isEmpty())
            for (int i = 0; i < ponumberslist.size(); i++)
                if (!poNo.equals(ponumberslist.get(i)))
                    flag = true;
                else {
                    flag = false;
                    break;
                }
        if (flag)
            Toast.makeText(NewShipment.this, "Purchase order not found in ", Toast.LENGTH_LONG).show();
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
        poststate=findViewById(R.id.poststate);
        exportData = new ExportData(NewShipment.this);
        importData = new ImportData(NewShipment.this);
        next = findViewById(R.id.nextbox);
        boxnorespon = findViewById(R.id.boxnorespon);
        respon = findViewById(R.id.respon);
        respon.setText("");
        pono = findViewById(R.id.poNotxt);
        boxno = findViewById(R.id.boxNotxt);
        barcode = findViewById(R.id.barCodetxt);
        qty = findViewById(R.id.RecQtytxt);
        recyclerView = findViewById(R.id.shipRec);
        barcodescan = findViewById(R.id.barcodescan);
        save = findViewById(R.id.save);
        searchView1 = findViewById(R.id.ponoSearch);
        searchView2 = findViewById(R.id.boxnoSearch);
        qty.setOnEditorActionListener(onEditAction);
        pono.setOnEditorActionListener(onEditAction);
        boxno.setOnEditorActionListener(onEditAction);
        barcode.setOnEditorActionListener(onEditAction);
        generalMethod = new GeneralMethod(NewShipment.this);
        requestQueue = Volley.newRequestQueue(this);

        itemname = findViewById(R.id.Itemnametxt);
        PoQTY = findViewById(R.id.PoQtytxt);
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
                    if(editable.toString().equals("notexists"))
                    {


                        generalMethod.showSweetDialog(NewShipment.this,3,"This barcode Not Exist","");
                        qty.setEnabled(false);
                        respon.setText("");
                    }
                    else {
                        if(editable.toString().equals("nodata"))
                        {}
                        else {
                            Log.e("afterTextChanged",""+editable.toString());


                         //   qty.requestFocus();
                            try {
                                Log.e("afterTextChanged",""+POdetailslist.get(0).getPoqty()+"");
                                sum= Integer.parseInt(POdetailslist.get(0).getPoqty().toString());


                            }catch (NumberFormatException e)
                            {
                                Log.e("afterTextChanged",""+e.getMessage()+"");
                            }

                        }



                    }

                }

            }
        });

        poststate.addTextChangedListener(new TextWatcher() {
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
                    {saveData(1);
                            saved=true;
                        importData.BoxNolist.clear();
                        importData.POdetailslist.clear();
                        shipmentList.clear();
                        adapter.notifyDataSetChanged();

                        filladapter(shipmentList);
                    }
                    else  if(editable.toString().trim().equals("not"))
                    {         saved=true;
                        saveData(0);;
                    }
                }
            }
        });







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

        recyclerView1 = dialog2.findViewById(R.id.listview1);
        editText = dialog2.findViewById(R.id.search_edt);

        recyclerView1.setLayoutManager(new LinearLayoutManager(NewShipment.this));
        ponoSearchAdapter = new PonoSearchAdapter(NewShipment.this, ponumberslist);
        recyclerView1.setAdapter(ponoSearchAdapter);
        Button btndialog = (Button) dialog2.findViewById(R.id.btndialog);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog2.dismiss();
            }
        });
        TextView serach = dialog2.findViewById(R.id.dailog_pono_Search);
        serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searcharrayAdapter.clear();
                for (int i = 0; i < ponumberslist.size(); i++) {
                    if (editText.getText().toString().trim().startsWith(ponumberslist.get(i)))
                        searcharrayAdapter.add(ponumberslist.get(i));
                   /* else
                        Toast.makeText(NewShipment.this,
                                "No Matched data", Toast.LENGTH_SHORT).show();*/

                }


                searchponoSearchAdapter = new PonoSearchAdapter(NewShipment.this, searcharrayAdapter);
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
        boxnumberslist.add("box100");
        boxnumberslist.add("box101");
        boxnumberslist.add("box102");
        boxnumberslist.add("box103");
        boxnumberslist.add("box104");
        boxnumberslist.add("box105");
        boxnumberslist.add("box100");
        boxnumberslist.add("box101");
        boxnumberslist.add("box102");
        boxnumberslist.add("box103");
        boxnumberslist.add("box104");
        boxnumberslist.add("box105");

        recyclerView1 = dialog1.findViewById(R.id.listview1);
        editText = dialog1.findViewById(R.id.search_edt);

        recyclerView1.setLayoutManager(new LinearLayoutManager(NewShipment.this));
        searchadapter = new BoxnoSearchAdapter(NewShipment.this, boxnumberslist);
        recyclerView1.setAdapter(searchadapter);

        LinearLayout linearLayout = dialog1.findViewById(R.id.linear);
        Button btndialog = (Button) dialog1.findViewById(R.id.btndialog);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog1.dismiss();
            }
        });
        TextView serach = dialog1.findViewById(R.id.dailog_pono_Search);
        serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searcharrayAdapter.clear();
                for (int i = 0; i < boxnumberslist.size(); i++) {
                    if (editText.getText().toString().trim().startsWith(boxnumberslist.get(i)))
                        searcharrayAdapter.add(boxnumberslist.get(i));


                }

                searchadapter2 = new BoxnoSearchAdapter(NewShipment.this, searcharrayAdapter);
                recyclerView1.setAdapter(searchadapter2);


            }

        });


        dialog1.show();
        dialog1.setCanceledOnTouchOutside(true);

    }

    public static void colsedialog(int i) {
        if (i == 1)
            dialog1.dismiss();
        else
            dialog2.dismiss();
    }

    public static void fillEdittext() {
        boxno.setText(boxnumberslist.get(Integer.parseInt(boxnotag)));
    }

    public static void fillEdittext2() {
        pono.setText(ponumberslist.get(Integer.parseInt(ponotag)));
    }

    public boolean AddInCaseDuplicates(Shipment shipment) {
        boolean flag = false;
        if (shipmentList.size() != 0)
            for (int i = 0; i < shipmentList.size(); i++) {

                if (shipmentList.get(i).getBoxNo().equals(shipment.getBoxNo())
                        && shipmentList.get(i).getBarcode().equals(shipment.getBarcode())) {
                    shipmentList.get(i).setQty(Integer.parseInt(shipmentList.get(i).getQty()) + Integer.parseInt(Qty) + "");
                    shipmentList.get(i).setDiffer(Integer.parseInt(shipmentList.get(i).getDiffer()) - Integer.parseInt(Qty) + "");
                    updateAdpapter();
                    flag = true;
                    break;


                } else
                    flag = false;
                continue;
            }

        return flag;

    }


    private void saveData(int isposted) {


        if(isposted==1)
            for (int i = 0; i < shipmentList.size(); i++)
                shipmentList.get(i).setIsPosted("1");

        long result[] = my_dataBase.shipmentDao().insertAll(shipmentList);

        if (result.length != 0) {
            generalMethod.showSweetDialog(this, 1, this.getResources().getString(R.string.savedSuccsesfule), "");
        }

        //  shipmentList.clear();



    }

    public static void updateAdpapter() {
        adapter.notifyDataSetChanged();
    }

    public boolean checkboxvalidty() {


        if (!importData.BoxNolist.contains(boxno.getText().toString()))
        { generalMethod.showSweetDialog(NewShipment.this, 3, "", this.getResources().getString(R.string.boxnovalidate));
        return false;
    }
       else
               return true;
}
private void checkitemcodevalidty() {

        for (int i = 0; i < importData.POdetailslist.size(); i++)
        {

            if(!boxno.getText().toString().equals(importData.POdetailslist.get(i).getBoxNo())) {
                    generalMethod.showSweetDialog(NewShipment.this, 3, "", this.getResources().getString(R.string.barcodevalidate));
               break;
                }

      }


    }

}