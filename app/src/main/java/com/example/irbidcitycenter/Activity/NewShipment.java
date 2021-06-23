package com.example.irbidcitycenter.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.http.DelegatingSSLSession;
import android.net.http.LoggingEventHandler;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageView;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.irbidcitycenter.Adapters.ShipmentAdapter.sum;
import static com.example.irbidcitycenter.GeneralMethod.convertToEnglish;
import static com.example.irbidcitycenter.ImportData.BoxNolist;
import static com.example.irbidcitycenter.ImportData.POdetailslist;
import static com.example.irbidcitycenter.ImportData.PoNolist;


public class NewShipment extends AppCompatActivity {
    boolean saved=false;
  int FinishProcessFlag=0;
    Shipment newshipment;
    ExportData exportData;
    static ImportData importData;
    public static String boxnotag;
    public static int updateflage = 1;
  public static  int  CheckFlag =0;
    public Button next;
    public Button save;
    public static TextView respon;
    public static TextView boxnorespon;
    public static EditText pono;
    public static int  Flag1=1;
    public static EditText boxno, itemname, PoQTY,poststate;
    public static EditText barcode;
    public TextView barcodescan;
    EditText qty,currentpo,currentbox,itemscounts;
    public static Dialog dialog1, dialog2;
    public static String ponotag;

    public EditText lastpo,lastbox,itemcount;
    public static String poNo;
    String boxNo;
    public static String barCode;
    String Qty;
    GeneralMethod generalMethod;
    String POnumselected, BOXnumselected;
    RecyclerView recyclerView;
    FloatingActionButton add;
    ImageView searchView1, searchView2;
    int parceQty;
    int pos;
    public static ShipmentAdapter adapter;
    BoxnoSearchAdapter boxnoSearchAdapter;
    List<Shipment> list = new ArrayList<>();
    public static List<Shipment> shipmentsList = new ArrayList<>();
    public static List<Shipment> poshipmentsList = new ArrayList<>();
    public static ArrayList<Shipment> localList = new ArrayList<>();
    public static List<Shipment> POlist = new ArrayList<>();
    public static List<String> numBOx = new ArrayList<>();
    public static ArrayList<String> searchBoxList = new ArrayList<>();

    RequestQueue requestQueue;
    PO po;
    ListView listView;
    public RoomAllData my_dataBase;
    BoxnoSearchAdapter boxsearchadapter, boxsearchadapter2;
    PonoSearchAdapter ponoSearchAdapter, searchponoSearchAdapter;
    public static int position = 1;
    public static final int REQUEST_Camera_Barcode = 1;
    public static String barcodeofrow;
    Shipment DbShip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shipment);
        my_dataBase = RoomAllData.getInstanceDataBase(NewShipment.this);
        BoxNolist.clear();
        POdetailslist.clear();



        init();
        fillLastBoxinfo();
my_dataBase.shipmentDao().deleteALL();
   // shipmentsList=my_dataBase.shipmentDao().getallShipment();
     //   filladapter(shipmentsList);
/////////////TESTING CODE
    /* Shipment  shipment=new Shipment();
        shipment.setPoNo("1");
        shipment.setQty("1");
        shipment.setBarcode("123458997658087");
        shipment.setBoxNo("box22");
        shipment.setDiffer("22");

        for (int i=0;i<50;i++)
            shipmentList.add(shipment);
        filladapter(shipmentList);*/
        //


        save.setEnabled(false);
        pono.requestFocus();
        boxno.setEnabled(false);
        barcode.setEnabled(false);
        qty.setEnabled(false);
        next.setEnabled(false);
        //

      searchView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBoxList.clear();
                boxno.setText("");
                barcode.setText("");
                if(PoNolist.size()!=0)
                { showdailogponumber();
               }
                else
                Toast.makeText(NewShipment.this, getResources().getString(R.string.nopo), Toast.LENGTH_LONG).show();


            }

        });
    
        searchView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove redundant boxs
                searchBoxList.clear();
             if(!pono.getText().toString().equals("")) {
                 Set<String> set = new HashSet<String>();

                 for (int i = 0; i < BoxNolist.size(); i++) {

                     if(!BoxNolist.get(i).equals(""))
                     set.add(BoxNolist.get(i));
                 }
                 searchBoxList.addAll(set);

                 if (searchBoxList.size() != 0)
                     showdailogboxnumber();
                 else
                     Toast.makeText(NewShipment.this, getResources().getString(R.string.nobox), Toast.LENGTH_SHORT).show();

             }
             else
                 Toast.makeText(NewShipment.this, getResources().getString(R.string.fillpo), Toast.LENGTH_SHORT).show();
            }});


      //1. save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



               shipmentsList=my_dataBase.shipmentDao().getUnpostedShipment("0");
               Log.e("shipmentsListsize",shipmentsList.size()+"");
               exportData(shipmentsList);

                localList.clear();
                shipmentsList.clear();
                adapter.notifyDataSetChanged();
                    pono.setText("");
                    pono.setEnabled(true);
                    pono.requestFocus();
                    boxno.setText("");
                    barcode.setText("");
                    boxno.setEnabled(false);
                    barcode.setEnabled(false);
                    qty.setEnabled(false);
                    ////

                searchView1.setEnabled(true);
                importData.BoxNolist.clear();
                importData.POdetailslist.clear();


            }

        });

        //2. next box button
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

                searchView2.setEnabled(true);
                localList.clear();
                adapter.notifyDataSetChanged();
                //

            }
        });

        //3. cancel button
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.confirm_title))
                        .setContentText(getResources().getString(R.string.messageExit))
                        .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {



                                new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.confirm_title))
                                        .setContentText(getResources().getString(R.string.messageExit2))
                                        .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismissWithAnimation();
                             if (localList.size() != 0) {
                                 localList.clear();
                                  filladapter( localList);

                              }

                             Intent intent =new Intent(NewShipment.this,MainActivity.class);
                             startActivity(intent);
                              finish();

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
                                barcode.setEnabled(true);
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .show();

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

                case R.id.poNotxt:
                    {

                    if (!pono.getText().toString().trim().equals("")) {

                   try {

                       poshipmentsList.clear();
                       Shipment shipment ;
                       shipment = my_dataBase.shipmentDao().getlastShipment(pono.getText().toString().trim());
                       if(shipment!=null){
                           localList.add(shipment);

                       Log.e("shipments", shipment.toString());
                       filladapter(localList);}
                       getboxData();

                   }catch (Exception e){
                       Log.e("Exception",e.getMessage());
                   }
                    }
                    else
                       pono.requestFocus();

                    break;
                }
                case R.id.boxNotxt:
                    {
                    Log.e("",barcode.getText().toString());
                    pono.setEnabled(false);
                    if(!boxno.getText().toString().trim().equals("")) {
                        if (checkboxvalidty()) {


                            barcode.setEnabled(true);
                            barcode.requestFocus();
                            break;


                        } else {

                            showconfirmBoxNoDialog();


                        }
                    }
                    else
                        boxno.requestFocus();


                    break;
                }

                case R.id.barCodetxt: {


               //  if(FinishProcessFlag==0) {
                 //     FinishProcessFlag=1;
                   Log.e("barcodeedittxt",barcode.getText().toString());
                    Qty = "1";
                          if (barcode.getText().toString().trim().length() != 0) {
                              barcode.setEnabled(false);
                              Shipment shipment = new Shipment();
                              shipment.setPoNo(pono.getText().toString().trim());
                              shipment.setBoxNo(boxno.getText().toString().trim());
                              shipment.setBarcode(barcode.getText().toString().trim());
                              CheckShipmentObject(shipment);


                              boxno.setEnabled(false);
                              boxno.setEnabled(false);
                              // getPOdetails();
                          } else

                              barcode.requestFocus();
             //   }
                    break;


            }



            }}
            return true;
            }
                    return false;
        }
    };



    //4. actions will done when user bress next key in keyboard
    TextView.OnEditorActionListener onEditAction = new TextView.OnEditorActionListener() {


        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
           // Log.e("onEditorAction",""+keyEvent.getAction()+"\t"+i);
              if (i!= KeyEvent.ACTION_UP) {

            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                    || i == EditorInfo.IME_NULL) {
                switch (textView.getId()) {
                    case R.id.poNotxt: {
                        if (!pono.getText().toString().trim().equals("")) {

                            try {

                                poshipmentsList.clear();
                                Shipment shipment ;
                                shipment = my_dataBase.shipmentDao().getlastShipment(pono.getText().toString().trim());
                                if(shipment!=null){
                                    localList.add(shipment);
                                    Log.e("shipments", shipment.toString());
                                    filladapter(localList);}
                                getboxData();

                            } catch (Exception e) {
                                Log.e("Exception", e.getMessage());
                            }
                        } else
                            pono.requestFocus();

                        break;
                    }
                    case R.id.boxNotxt:
                    {
                        Log.e("",barcode.getText().toString());
                        pono.setEnabled(false);
                        if(!boxno.getText().toString().trim().equals("")) {
                            if (checkboxvalidty()) {


                                barcode.setEnabled(true);
                                barcode.requestFocus();
                                break;


                            } else {

                                showconfirmBoxNoDialog();


                            }
                        }
                        else
                            boxno.requestFocus();


                        break;
                    }

                    case R.id.barCodetxt: {

                        if(barcode.getText().toString().trim().length()!=0)

                        {     boxno.setEnabled(false);
                            boxno.setEnabled(false);
                            getPOdetails();}
                        else

                            barcode.requestFocus();
                        break;

                    }


                    case R.id.RecQtytxt:
                        if (qty.getText().toString().equals("0")) qty.setText("1");


                    {
                        barcode.setEnabled(false);
                        //filldata();
                        pono.setEnabled(false);

                        boxno.setEnabled(false);


                        barcodescan.requestFocus();
                        next.setEnabled(true);
                        save.setEnabled(true);
                        barcode.setText("");
                        qty.setText("1");
                    }


                    barcode.setEnabled(true);
                    // barcode.requestFocus();

                    qty.setEnabled(false);


                    //clear item data
                    itemname.setText("");
                    PoQTY.setText("");
                    next.setEnabled(true);

                    break;


                }
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
        new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.confirm_title))
                .setContentText(getResources().getString(R.string.messageExit))
                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {



                        new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(getResources().getString(R.string.confirm_title))
                                .setContentText(getResources().getString(R.string.messageExit2))
                                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismissWithAnimation();
                                 // if (localList.size() != 0)
                                       {
                                       //     localList.clear();
                                        //    filladapter(localList);

                                        }

                                        Intent intent =new Intent(NewShipment.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();

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


    private void showconfirmBoxNoDialog() {
        new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.confirm_title))
                .setContentText(getResources().getString(R.string.boxconfirm))
                .setConfirmButton(getResources().getString(R.string.yes),
                        new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {



                        sweetAlertDialog.dismiss();


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






    private void getPOdetails() {

        barCode = barcode.getText().toString().trim();
        importData.getPOdetails();
    }

    public void exportData(List<Shipment> shipmentList) {
        exportData.exportShipmentsList(shipmentList);
    }

    private static void getboxData() {
        Log.e("newshipment", "getboxData");
        poNo = pono.getText().toString().trim();
        importData.getboxno();



    }

    private void CheckShipmentObject(Shipment shipment){
        //check obj is in local list
      if(CheckIsExistsINLocalList(shipment.getBarcode(),shipment.getBoxNo(),shipment.getPoNo()))
        {
            Log.e("CheckShipmentObject","CheckIsExistsINLocalList");
            localList.get(pos).setQty(Integer.parseInt(localList.get(pos).getQty()) + Integer.parseInt(Qty) + "");
            localList.get(pos).setDiffer(String.valueOf(Integer.parseInt(localList.get(pos).getDiffer())+Integer.parseInt("1")));
        updateAdpapter();

            updateRow(localList.get(pos).getBarcode(),localList.get(pos).getPoNo(),localList.get(pos).getBoxNo(), localList.get(pos).getQty(),localList.get(pos).getDiffer());


            barcode.setText("");
            barcode.setEnabled(true);
            barcode.requestFocus();

        }


        else {

            ArrayList<Shipment> List = new ArrayList<>();
        if(CheckIsExistsINDB(shipment))
        {
          //  Log.e("list.get(0)",list.get(0).getQty()+"\t"+localList.get(0).getQty()+"\t"+Integer.parseInt(Qty)+"");
          //show item in rec
            Log.e("listgetQty",""+DbShip.getQty());
            DbShip.setQty(String.valueOf(Integer.parseInt(DbShip.getQty()) + Integer.parseInt("1")));
            if(DbShip.getIsNew().equals("0") )
                DbShip.setDiffer( String.valueOf(Integer.parseInt( DbShip.getDiffer())+Integer.parseInt("1")));
            localList.add(DbShip);
            pos=localList.size()-1 ;
            filladapter(localList);
            //localList.add();

            updateRow(localList.get(pos).getBarcode(), localList.get(pos).getPoNo(), localList.get(pos).getBoxNo(),localList.get(pos).getQty(),localList.get(pos).getDiffer());

//            Log.e("shipment.getQty",shipment.getQty());

            barcode.setText("");
            barcode.setEnabled(true);
            barcode.requestFocus();
        }


        else {
            Log.e("else","not in local and db");
            getPOdetails();
          /*   localList.clear();
            saveRow(shipment);
            localList.add(shipment);
            filladapter(localList);*/

        }

       } //end of CheckIsExistsINLocalList

    }

    private boolean CheckNewShipmentObject(Shipment shipment){
        Log.e("CheckNew,CheckIsExistsINLocalList","CheckNewShipmentObject");

        {
try{
            ArrayList<Shipment> List = new ArrayList<>();
            if(CheckNewIsExistsINDB(shipment))
            {

                if(list.get(0).getIsPosted().equals("1"))
                {
                    list.get(0). setPoNo(convertToEnglish(pono.getText().toString().trim()));
                    list.get(0).setBoxNo(convertToEnglish(boxno.getText().toString().trim()));
                    list.get(0).setBarcode(convertToEnglish(barcode.getText().toString().trim()));
                    list.get(0).setQty("1");
                    list.get(0).setPoqty("1");
                    list.get(0).setDiffer("");
                    list.get(0).setIsPosted("0");
                    list.get(0).setShipmentTime(String.valueOf(generalMethod.getCurentTimeDate(2)));
                    list.get(0).setShipmentDate(String.valueOf(generalMethod.getCurentTimeDate(1)));
                    list.get(0).setIsNew("0");
                }
else
                {
                    list.get(0).setQty(String.valueOf(Integer.parseInt(list.get(0).getQty()) + Integer.parseInt("1")));
                }
                localList.add(list.get(0));
                filladapter(localList);
                Log.e("CheckIsExistsINDB","true");
            //    barcode.setText("");
              //  barcode.requestFocus();
                return true;
            }

        }      catch(Exception e){Log.e("Exception",e.getMessage());}}

        return false;
    }
    private  boolean CheckIsExistsINDB(Shipment shipment) {
        Log.e("CheckIsExistsINDB","CheckIsExistsINDB");

    DbShip =my_dataBase.shipmentDao().getShipments(shipment.getBarcode(),shipment.getPoNo(),shipment.getBoxNo());
   if(DbShip!=null)
   {  Log.e("listgetQty",""+DbShip.getQty());
       localList.clear();
       return true;

   }

   else
       return false;

    }
    private  boolean CheckNewIsExistsINDB(Shipment shipment) {
        Log.e("CheckIsExistsINDB","CheckIsExistsINDB");

        list =my_dataBase.shipmentDao().getNEWShipments(shipment.getBarcode(),shipment.getPoNo(),shipment.getBoxNo());

        if(list.size()>0)
        {
            localList.clear();
            return true;

        }

        else
            return false;

    }

    private boolean CheckIsExistsINLocalList(String barcode,String boxno,String pono) {
        Log.e("CheckIsExistsINLocalList","CheckIsExistsINLocalList");
        Log.e("CheckIsExists",boxno);
        Log.e("CheckIsExistslocalList.size()",localList.size()+"");
            boolean flag = false;
            if ( localList.size() != 0)
                for (int i = 0; i < localList.size(); i++) {
                    Log.e("Check","CheckIsExistsINLocalList");
                    if (convertToEnglish(localList.get(i).getBoxNo()).equals(convertToEnglish(boxno))
                            && convertToEnglish(localList.get(i).getBarcode()).equals(convertToEnglish(barcode))
                            && convertToEnglish(localList.get(i).getPoNo()).equals(convertToEnglish(pono)
                    )) {
                        pos=i;
                        flag = true;
                        break;

                    } else {
                        flag = false;
                        continue;
                    }
                }

            return flag;


    }

    private boolean NewCheckIsExistsINLocalList(String barcode,String boxno,String pono) {
        Log.e("CheckIsExistsINLocalList","CheckIsExistsINLocalList");
        Log.e("CheckIsExists",boxno);
        Log.e("CheckIsExistslocalList.size()",localList.size()+"");
        boolean flag = false;
        if ( localList.size() != 0)
            for (int i = 0; i < localList.size(); i++) {
                Log.e("Check","CheckIsExistsINLocalList");
                if (
                  convertToEnglish(localList.get(i).getBarcode()).equals(convertToEnglish(barcode))

                ) {
                    pos=i;
                    flag = true;
                    break;

                } else {
                    flag = false;
                    continue;
                }
            }

        return flag;


    }

    /*private void filldata() {
        Log.e("filldata1", "filldata");

        Qty = "1";
        boxNo = boxno.getText().toString().trim();


        ShipmentAdapter.newqty = Qty;

//        if (poNo.toString().trim().equals("")) pono.setError("required");
//
//        else {
//
//            if (boxNo.toString().trim().equals("")) boxno.setError("required");
//            else {
//                if (barCode.toString().trim().equals("")) barcode.setError("required");
//
//                else if (Qty.toString().trim().equals("")) {
//
//                }
//
//
//
//
//                else


        // CheckPOnumber();


        shipment = new Shipment();
        shipment.setPoNo(poNo);
        shipment.setBoxNo(boxNo);
        shipment.setBarcode(barCode);
        shipment.setQty(Qty);
        shipment.setIsPosted("0");
        int qty = Integer.parseInt(Qty);


        shipment.setShipmentTime(String.valueOf(generalMethod.getCurentTimeDate(2)));
        shipment.setShipmentDate(String.valueOf(generalMethod.getCurentTimeDate(1)));
        shipment.setPoqty(PoQTY.getText().toString());
        shipment.setItemname(itemname.getText().toString());


        if (AddInCaseDuplicates(shipment)) {
            // barcode.requestFocus();
            //updateRow(shipment.getBarcode(), shipment.getQty());
            Log.e("up", "up");

          //  updateRow();

        } else {
            Log.e("filldata", "1111else" + barCode);
            int differ = getDiff(qty);
            if (differ > 0)
                shipment.setDiffer("+" + differ);
            else
                shipment.setDiffer(differ + "");
            Log.e("shipment.getDiffer", shipment.getDiffer() + "");
            shipmentList.add(shipment);
            updateflage = 1;
            filladapter(shipmentList);
            //saveRow(shipment);
        }


    }*/



    public int getDiff(int qty) {

        Log.e("getDifferentQTY()sum", String.valueOf(sum)+"\tqty="+qty);


        if(sum>=qty)
        { Log.e("sum",sum+"");
            Log.e("qty",qty+"");
            sum-= qty;
            return   -sum;

        }
        else {
            return Math.abs(sum);
        }

    }


    private void filladapter(java.util.List<Shipment> shipmentList) {
        Log.e("filldata","1111elsefilladapter"+barCode);
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
    public void fillLastBoxinfo(){
        try {
            Shipment shipment=my_dataBase.shipmentDao().getlastbox();
            lastpo.setText(shipment.getPoNo().toString());
            lastbox.setText(shipment.getBoxNo().toString());
            int sum=my_dataBase.shipmentDao().getsumofboxitemsqty(shipment.getPoNo(),shipment.getBoxNo());
            itemcount.setText(sum+"");
        }
        catch (Exception e){
            Log.e("",e.getMessage());
        }

    }
    public void fillPOinfo(String s){

       try{

           ///
           POlist =my_dataBase.shipmentDao().getPOShipments(s);
           currentpo.setText(POlist.get(0).getPoNo());

     //      filladapter( POlist);

           ///
           for(int i=0;i<POlist.size();i++) {
               if(!numBOx.contains(POlist.get(i).getBoxNo()))
               {   Log.e("numBOx",numBOx.size()+"");
                   numBOx.add(POlist.get(i).getBoxNo());}
           }

          Set<String> set = new HashSet<String>();

           for(int i = 0; i < numBOx.size(); i++){
               Log.e("inputif",numBOx.size()+"");
               set.add(numBOx.get(i));
           }


           currentbox.setText(set.size()+"");
           int sum=my_dataBase.shipmentDao().getsumofqty(s);
           Log.e("sum",sum+"");
           itemscounts.setText( sum +"");
       }
       catch (Exception e){

       }

    }

    private void init() {

        localList.clear();
        exportData = new ExportData(NewShipment.this);
        importData = new ImportData(NewShipment.this);
        poststate=findViewById(R.id.poststate);
        getPoNu();
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

        currentpo= findViewById(R.id.currentpo);
          currentbox= findViewById(R.id.currentbox);
          itemscounts= findViewById(R.id.itemcount);
        lastpo= findViewById(R.id.LASTpo);
        lastbox= findViewById(R.id.LASTbox);
        itemcount= findViewById(R.id.itemcount2);;
       // qty.setOnEditorActionListener(onEditAction);
      pono.setOnEditorActionListener(onEditAction);
       boxno.setOnEditorActionListener(onEditAction);
     barcode.setOnEditorActionListener(onEditAction);


pono.setOnKeyListener(onKeyListener);
  boxno.setOnKeyListener(onKeyListener);
barcode.setOnKeyListener(onKeyListener);
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
              if(editable.toString().equals("invlalid"))
                        {
                            Log.e("respon:afterTextChanged",""+respon.getText().toString());
                            Qty = "1";
                            newshipment = new Shipment();
                            newshipment.setPoNo(convertToEnglish(pono.getText().toString().trim()));
                            newshipment.setBoxNo(convertToEnglish(boxno.getText().toString().trim()));
                            newshipment.setBarcode(convertToEnglish(barcode.getText().toString().trim()));
                              if(CheckNewShipmentObject(newshipment)==false&&!barcode.getText().equals("")) {
                                  Log.e("barcodehere",barcode.getText().toString());
                                  showConfirmBarcodeDailog();

                              }
                             else {
                                  barcode.setText("");
                           barcode.setEnabled(true);
                            barcode.requestFocus();

                              }
                           save.setEnabled(true);
                          //  barcode.setText("");
                        /*    barcode.setEnabled(true);
                            barcode.requestFocus();*/
                        }
                   if(editable.toString().equals("ItemOCode"))  {

                            try {
                                Log.e("afterTextChanged",""+POdetailslist.get(0).getPoqty()+"");
                                sum= Integer.parseInt(POdetailslist.get(0).getPoqty().toString());
                                {


                                    Shipment shipment = new Shipment();
                                    shipment.setPoNo(convertToEnglish(pono.getText().toString().trim()));
                                    shipment.setBoxNo(convertToEnglish(boxno.getText().toString().trim()));
                                    shipment.setBarcode(convertToEnglish(barcode.getText().toString().trim()));
                                    Qty = "1";
                                    shipment.setQty( Qty );
                                    shipment.setIsPosted("0");
                                    int qty = Integer.parseInt(Qty);
                                    shipment.setIsNew("0");
                                    shipment.setShipmentTime(String.valueOf(generalMethod.getCurentTimeDate(2)));
                                    shipment.setShipmentDate(String.valueOf(generalMethod.getCurentTimeDate(1)));
                                    shipment.setPoqty(PoQTY.getText().toString());
                                    shipment.setItemname(itemname.getText().toString());
                                    int differ = getDiff(qty);
                                    if (differ > 0)
                                        shipment.setDiffer("+" + differ);
                                    else
                                        shipment.setDiffer(differ + "");




                                    respon.setText("");
                                    pono.setEnabled(false);

                                    boxno.setEnabled(false);
                                    next.setEnabled(true);
                                    save.setEnabled(true);


                                    itemname.setText("");
                                    PoQTY.setText("");
                                    localList.clear();
                                    saveRow(shipment);
                                    localList.add(shipment);
                                    filladapter(localList);
                                    barcode.setText("");
                                    barcode.setEnabled(true);
                                    barcode.requestFocus();


                                }

                            }catch (NumberFormatException e)
                            {
                                Log.e("NumberFormatException",""+e.getMessage()+"");
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
                    {
                        Log.e("poststate",editable.toString());

                            saved=true;
                        my_dataBase.shipmentDao().updateShipmentPosted();
                      localList.clear();
                        adapter.notifyDataSetChanged();
                        filladapter(localList);
                    }
                    else  if(editable.toString().trim().equals("not"))
                    {
                        Log.e("poststate",editable.toString());
                        saved=true;
                        localList.clear();
                        adapter.notifyDataSetChanged();
                        filladapter(localList);

                    }
                }
            }
        });


        boxnorespon.addTextChangedListener(new TextWatcher() {
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
                    if(editable.toString().equals("Not"))
                    {
                        NewShipment.pono.setError("Invalid");
                        NewShipment.pono.setText("");


                    }else {
                        if (boxnorespon.getText().equals("BOXNO")) {
                            NewShipment.boxno.setEnabled(true);
                            NewShipment.boxno.requestFocus();

                        }
                    }

                }
            }
        });
pono.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
       if(editable.length()!=0)
        fillPOinfo(pono.getText().toString().trim());
    }
});
    }

    private void showConfirmBarcodeDailog() {
        if(!barcode.getText().equals("")) {
            new SweetAlertDialog(NewShipment.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getResources().getString(R.string.confirm_title))
                    .setContentText(getResources().getString(R.string.barcodeconfirm))
                    .setConfirmButton(getResources().getString(R.string.yes),
                            new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {


                                    newshipment.setQty("1");
                                    newshipment.setPoqty("1");
                                    newshipment.setDiffer("");
                                    newshipment.setIsPosted("0");
                                    newshipment.setItemname("");
                                    newshipment.setShipmentTime(String.valueOf(generalMethod.getCurentTimeDate(2)));
                                    newshipment.setShipmentDate(String.valueOf(generalMethod.getCurentTimeDate(1)));
                                    newshipment.setIsNew("1");
                                    CheckFlag = 1;
                                    localList.clear();

                                    Log.e("newshipmentobj", newshipment.toString());
                                    localList.add(newshipment);
                                    filladapter(localList);
                                    saveRow(newshipment);
                                    sweetAlertDialog.dismiss();

                                    barcode.setText("");
                                    barcode.setEnabled(true);
                                    barcode.requestFocus();
                                    //FinishProcessFlag=0;

                                }
                            })
                    .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.dismiss();
                            barcode.setText("");
                            barcode.setEnabled(true);
                            barcode.requestFocus();
                            // FinishProcessFlag=0;
                        }
                    })
                    .show();
        }

    }

    private void getPoNu() {
        PoNolist.clear();
        importData.getPoNum();
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
        lp.width = 500;
        lp.height = 600;
        lp.gravity = Gravity.CENTER;

        dialog2.getWindow().setAttributes(lp);
        final RecyclerView recyclerView1;
        final EditText editText;
        final ArrayList<String> searcharrayAdapter = new ArrayList<>();

        recyclerView1 = dialog2.findViewById(R.id.listview1);
        editText = dialog2.findViewById(R.id.search_edt);


        recyclerView1.setLayoutManager(new LinearLayoutManager(NewShipment.this));
        ponoSearchAdapter = new PonoSearchAdapter(NewShipment.this, PoNolist );
        recyclerView1.setAdapter(ponoSearchAdapter);
        Button btndialog = (Button) dialog2.findViewById(R.id.cancelbtndialog);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog2.dismiss();
            }
        });
       
        

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("afterText",editText.getText().toString());
                if (editText.getText().toString().trim().equals("")) {
                    ponoSearchAdapter = new PonoSearchAdapter(NewShipment.this, PoNolist);
                    recyclerView1.setAdapter(ponoSearchAdapter);

                } else {
                    searcharrayAdapter.clear();
                    for (int i = 0; i < PoNolist.size(); i++) {
                        if (editText.getText().toString().trim().equals(PoNolist.get(i)))
                            searcharrayAdapter.add(PoNolist.get(i));


                    }
                    ponoSearchAdapter = new PonoSearchAdapter(NewShipment.this, searcharrayAdapter);
                    recyclerView1.setAdapter(ponoSearchAdapter);

                }
            }
        });

        dialog2.show();

        dialog2.setCanceledOnTouchOutside(true);


    }


    void showdailogboxnumber() {



        Log.e("setlist",searchBoxList.size()+"");
        dialog1 = new Dialog(NewShipment.this);
        dialog1.setCancelable(false);
        dialog1.setContentView(R.layout.box_dialog_listview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = 500;
        lp.height = 600;
        lp.gravity = Gravity.CENTER;
//        lp.setColorMode(ActivityInfo.COLOR_MODE_DEFAULT);


        dialog1.getWindow().setAttributes(lp);
        final RecyclerView recyclerView1;
        final EditText editText;
        final ArrayList<String> searcharrayAdapter = new ArrayList<>();

        recyclerView1 = dialog1.findViewById(R.id.boxlistview);
        editText = dialog1.findViewById(R.id.boxsearch_edt);

        recyclerView1.setLayoutManager(new LinearLayoutManager(NewShipment.this));
        boxsearchadapter = new BoxnoSearchAdapter(NewShipment.this,searchBoxList);
        recyclerView1.setAdapter(boxsearchadapter);

        LinearLayout linearLayout = dialog1.findViewById(R.id.boxlinear);
        Button btndialog = (Button) dialog1.findViewById(R.id.cancelbtnBoxdialog);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog1.dismiss();
            }
        });
        
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText.getText().toString().trim().equals("")) {

                    boxsearchadapter2 = new BoxnoSearchAdapter(NewShipment.this, searchBoxList);
                    recyclerView1.setAdapter( boxsearchadapter2);

                } else {
                    searcharrayAdapter.clear();
                    for (int i = 0; i < searchBoxList.size(); i++) {
                        if (editText.getText().toString().trim().equals(searchBoxList.get(i)))
                            searcharrayAdapter.add(searchBoxList.get(i));

                    }

                    boxsearchadapter2 = new BoxnoSearchAdapter(NewShipment.this, searcharrayAdapter);
                    recyclerView1.setAdapter( boxsearchadapter2);

                }
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

    public static void fillPoEdittext() {
        pono.setText(PoNolist.get(Integer.parseInt(ponotag)));

        getboxData();
        boxno.setEnabled(true);
        boxno.requestFocus();


    }

    public static void fillBoxEdittext() {
        boxno.setText(searchBoxList.get(Integer.parseInt(boxnotag)));
        barcode.setEnabled(true);
        barcode.requestFocus();
    }

   /* public boolean AddInCaseDuplicates(Shipment shipment) {
        Log.e("filldata1","AddInCaseDuplicates");
        boolean flag = false;
        if (shipmentList.size() != 0)
            for (int i = 0; i < shipmentList.size(); i++) {

                if (convertToEnglish(shipmentList.get(i).getBoxNo()).equals(convertToEnglish(shipment.getBoxNo()))
                        && convertToEnglish(shipmentList.get(i).getBarcode()).equals(convertToEnglish(shipment.getBarcode()))) {
                    shipmentList.get(i).setQty(Integer.parseInt(shipmentList.get(i).getQty()) + Integer.parseInt(Qty) + "");
                   // Log.e("filldata2 dif",shipmentList.get(i).getDiffer());
                    Flag1=0;
                    shipmentList.get(i).setDiffer(String.valueOf(Integer.parseInt(shipmentList.get(i).getDiffer())+Integer.parseInt("1")));
                   // Log.e("filldata1 dif",shipmentList.get(i).getDiffer());
                    updateAdpapter();
                    flag = true;
                    break;


                } else
                    flag = false;
                continue;
            }

        return flag;

    }*/




   /* private void saveData(int isposted) {
      //  Log.e("poststate",shipmentList.get(0).getBoxNo()+" ");

        if(isposted==1)
            for (int i = 0; i < shipmentList.size(); i++)
                shipmentList.get(i).setIsPosted("1");

        long result[] = my_dataBase.shipmentDao().insertAll(shipmentList);

        if (result.length != 0) {

            generalMethod.showSweetDialog(this, 1, this.getResources().getString(R.string.savedSuccsesfule), "");
        }
        else
            Log.e("poststate"," not saaave");

        //  shipmentList.clear();



    }*/
    private void saveRow(Shipment shipment) {

      my_dataBase.shipmentDao().insert(shipment);

    }
    private void updateRow(String barcpde,String po,String box,String qty,String dif){
        int g=my_dataBase.shipmentDao(). updateQTY(barcpde,po,box,qty,dif);
        Log.e("updateRow",g+"");

    }

    public static void updateAdpapter() {
        adapter.notifyDataSetChanged();
    }

    public boolean checkboxvalidty() {


        if (!importData.BoxNolist.contains(convertToEnglish(boxno.getText().toString().trim())))
        {
            Log.e("checkboxvalidty",boxno.getText().toString());
        return false;
    }
       else
        {    Log.e("checkboxvalidty",boxno.getText().toString());
            return true;}
}
private boolean checkitemcodevalidty() {
        boolean f=false;
        Log.e("checkitemcodevalidty", "checkitemcodevalidty");

        for (int i = 0; i < importData.POdetailslist.size(); i++)
        {

            if(!convertToEnglish(boxno.getText().toString().trim()).
                    equals(importData.POdetailslist.get(i).getBoxNo())
                  && !convertToEnglish(barcode.getText().toString().trim()).
                    equals(importData.POdetailslist.get(i).getBarcode())

            ) {
              f=false;
               break;
                }
            else
                  f=true;
            continue;

      }


   return  f; }

  /*if(!s.substring(s.length()/2,s.length()).equals(s.toString().substring(0,s.length()/2))
            ||
            !s.substring(2,s.length()/3).equals(s.toString().substring(s.length()/3,s.length()-s.length()/3)))
    showConfirmBarcodeDailog();*/




}