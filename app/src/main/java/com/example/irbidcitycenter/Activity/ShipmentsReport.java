package com.example.irbidcitycenter.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.Adapters.ShipmentsReportAdapter;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.irbidcitycenter.Activity.MainActivity.FILE_NAME;
import static com.example.irbidcitycenter.Activity.MainActivity.KEY_LANG;
import static com.example.irbidcitycenter.GeneralMethod.convertToEnglish;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;

public class ShipmentsReport extends AppCompatActivity {
TextView  SH_date,total_qty_text;
    Calendar myCalendar;
    EditText search_edt;
    AppCompatSpinner PONO_edt;
    EditText         box;
    Button perview ,ponoperview;
    List<Shipment> shipments;
    public RoomAllData my_dataBase;
    ListView listView;
    private List<Shipment> PoNoShlist=new ArrayList<>();
    private List<Shipment> searchlist=new ArrayList<>();
    private List<Shipment> allShipmentslist=new ArrayList<>();
    private List<String> poNoSpinner=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_shipments_report);

        init();



        fillSp();
        fillpOSp();
        myCalendar = Calendar.getInstance();



        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String today = df.format(currentTimeAndDate);
        SH_date.setText(GeneralMethod.convertToEnglish(today));


        allShipmentslist.clear();
        allShipmentslist=my_dataBase.shipmentDao(). getdateshipments(SH_date.getText().toString());
            fillAdapterData(allShipmentslist);
        CalculateSum(  allShipmentslist);




        perview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!PONO_edt.getSelectedItem().toString().trim().equals(""))
                {
               if(!PONO_edt.getSelectedItem().equals("All")
               &&!box.getText().toString().trim().equals(""))
               {  Log.e("er1==","er");
                   PoNoShlist.clear();
                   PoNoShlist=    my_dataBase.shipmentDao().getShipmentsbyPONO(SH_date.getText().toString(),PONO_edt.getSelectedItem().toString().trim(),box.getText().toString().trim());

               if(PoNoShlist!=null&& PoNoShlist.size()!=0) {
                   fillAdapterData(PoNoShlist);
                   CalculateSum(  PoNoShlist);
                   Log.e("er2==","er");
               }

                else {
                    Log.e("er==","er");
                   Toast.makeText(ShipmentsReport.this, "No Data", Toast.LENGTH_SHORT).show();
                   PoNoShlist=new ArrayList<>();
                   fillAdapterData(PoNoShlist);

               }
                }
                else  if(PONO_edt.getSelectedItem().equals("All")
                       &&!box.getText().toString().trim().equals("")){

                   PoNoShlist.clear();
                   PoNoShlist=    my_dataBase.shipmentDao().getShipmentsbydate_box(SH_date.getText().toString(),box.getText().toString().trim());
                   fillAdapterData(PoNoShlist);
                   CalculateSum(  PoNoShlist);

               }
                else  if(!PONO_edt.getSelectedItem().equals("All")
                       &&box.getText().toString().trim().equals("")){
                   Log.e("er4==","er");
                   PoNoShlist.clear();
                   PoNoShlist=    my_dataBase.shipmentDao().getShipmentsbydate_pono(SH_date.getText().toString(),PONO_edt.getSelectedItem().toString().trim());
                   fillAdapterData(PoNoShlist);
                   CalculateSum(  PoNoShlist);

               }
                else {
                   Log.e("er3==","er");
                   PoNoShlist.clear();
                   PoNoShlist  =my_dataBase.shipmentDao(). getdateshipments(SH_date.getText().toString());
                   fillAdapterData(PoNoShlist);
                   CalculateSum(  PoNoShlist);


               }

                }
                else{
                 //   PONO_edt.setError("Empty");
                }
            }
        });
        SH_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ShipmentsReport.this, openDatePickerDialog(0), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

//        perview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shipments= my_dataBase.shipmentDao().getshipmentsbydate_po_box( SH_date.getText().toString(),box.getSelectedItem().toString(),PONO_edt.getSelectedItem().toString());
//          fillAdapterData(shipments);
//                Log.e("shipmentssize",shipments.size()+"");
//                if(   shipments.size()==0)
//                    Toast.makeText(ShipmentsReport.this,"No Data",Toast.LENGTH_SHORT).show();
//                CalculateSum(  shipments);
//            }
//        });



//        box.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int i, KeyEvent keyEvent) {
//                if (i == KeyEvent.KEYCODE_BACK) {
//                    onBackPressed();
//
//                } else if (i != KeyEvent.KEYCODE_ENTER) {
//                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
//                        if (!box.getText().toString().equals("")) {
//                            if (!PONO_edt.getSelectedItem().toString().trim().equals("")) {
//                                if (!PONO_edt.getSelectedItem().equals("All")
//                                        && !box.getText().toString().trim().equals("")) {
//                                    Log.e("er1==", "er");
//                                    PoNoShlist.clear();
//                                    PoNoShlist = my_dataBase.shipmentDao().getShipmentsbyPONO(SH_date.getText().toString(), PONO_edt.getSelectedItem().toString().trim(), box.getText().toString().trim());
//
//                                    if (PoNoShlist != null && PoNoShlist.size() != 0) {
//                                        fillAdapterData(PoNoShlist);
//                                        CalculateSum(PoNoShlist);
//                                        Log.e("er2==", "er");
//                                    } else {
//                                        Log.e("er==", "er");
//                                        Toast.makeText(ShipmentsReport.this, "No Data", Toast.LENGTH_SHORT).show();
//                                        PoNoShlist = new ArrayList<>();
//                                        fillAdapterData(PoNoShlist);
//
//                                    }
//                                } else if (PONO_edt.getSelectedItem().equals("All")
//                                        && !box.getText().toString().trim().equals("")) {
//
//                                    PoNoShlist.clear();
//                                    PoNoShlist = my_dataBase.shipmentDao().getShipmentsbydate_box(SH_date.getText().toString(), box.getText().toString().trim());
//                                    fillAdapterData(PoNoShlist);
//                                    CalculateSum(PoNoShlist);
//
//                                } else if (!PONO_edt.getSelectedItem().equals("All")
//                                        && box.getText().toString().trim().equals("")) {
//                                    Log.e("er4==", "er");
//                                    PoNoShlist.clear();
//                                    PoNoShlist = my_dataBase.shipmentDao().getShipmentsbydate_pono(SH_date.getText().toString(), PONO_edt.getSelectedItem().toString().trim());
//                                    fillAdapterData(PoNoShlist);
//                                    CalculateSum(PoNoShlist);
//
//                                } else {
//                                    Log.e("er3==", "er");
//                                    PoNoShlist.clear();
//                                    PoNoShlist = my_dataBase.shipmentDao().getdateshipments(SH_date.getText().toString());
//                                    fillAdapterData(PoNoShlist);
//                                    CalculateSum(PoNoShlist);
//
//
//                                }
//
//                            } else {
//                                //   PONO_edt.setError("Empty");
//                            }
//                        }
//
//                    } else {
//                        box.requestFocus();
//                    }
//                }
//                return true;
//            }
//                return false;
//        }
//        });


        box.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int i, KeyEvent KeyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    onBackPressed();

                }

                else
                if (i != KeyEvent.KEYCODE_ENTER) {
                    if (KeyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (!box.getText().toString().equals("")) {
                            if (!PONO_edt.getSelectedItem().toString().trim().equals("")) {
                                if (!PONO_edt.getSelectedItem().equals("All")
                                        && !box.getText().toString().trim().equals("")) {
                                    Log.e("er1==", "er");
                                    PoNoShlist.clear();
                                    PoNoShlist = my_dataBase.shipmentDao().getShipmentsbyPONO(SH_date.getText().toString(), PONO_edt.getSelectedItem().toString().trim(), box.getText().toString().trim());

                                    if (PoNoShlist != null && PoNoShlist.size() != 0) {
                                        fillAdapterData(PoNoShlist);
                                        CalculateSum(PoNoShlist);
                                        Log.e("er2==", "er");
                                    } else {
                                        Log.e("er==", "er");
                                        Toast.makeText(ShipmentsReport.this, "No Data", Toast.LENGTH_SHORT).show();
                                        PoNoShlist = new ArrayList<>();
                                        fillAdapterData(PoNoShlist);

                                    }
                                } else if (PONO_edt.getSelectedItem().equals("All")
                                        && !box.getText().toString().trim().equals("")) {

                                    PoNoShlist.clear();
                                    PoNoShlist = my_dataBase.shipmentDao().getShipmentsbydate_box(SH_date.getText().toString(), box.getText().toString().trim());
                                    fillAdapterData(PoNoShlist);
                                    CalculateSum(PoNoShlist);

                                } else if (!PONO_edt.getSelectedItem().equals("All")
                                        && box.getText().toString().trim().equals("")) {
                                    Log.e("er4==", "er");
                                    PoNoShlist.clear();
                                    PoNoShlist = my_dataBase.shipmentDao().getShipmentsbydate_pono(SH_date.getText().toString(), PONO_edt.getSelectedItem().toString().trim());
                                    fillAdapterData(PoNoShlist);
                                    CalculateSum(PoNoShlist);

                                } else {
                                    Log.e("er3==", "er");
                                    PoNoShlist.clear();
                                    PoNoShlist = my_dataBase.shipmentDao().getdateshipments(SH_date.getText().toString());
                                    fillAdapterData(PoNoShlist);
                                    CalculateSum(PoNoShlist);


                                }

                            } else {
                                //   PONO_edt.setError("Empty");
                            }

                            box.setText("");
                        }else {
                            box.requestFocus();
                        }

                    }




         return true;

                }  return false;}
                  });

    }

    private void loadLanguage() {
        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String langCode = preferences.getString(KEY_LANG, Locale.getDefault().getLanguage() );
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    TextView.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            Log.e("keyEvent.getAction()",keyEvent.getAction()+"");


            if (i == KeyEvent.KEYCODE_BACK) {
                onBackPressed();

            }

            if (i != KeyEvent.KEYCODE_ENTER) {

                { if (keyEvent.getAction() == KeyEvent.ACTION_UP)
                    switch (view.getId()) {
                        case R.id.zoneedt:

                            break;
                    }  return true;
                }}
            return false;

        }};
    private void init() {
        box=findViewById(R.id.box);
        ponoperview=findViewById(R.id.PONO_preview);
        PONO_edt=findViewById(R.id.PONUMBER);
        search_edt=findViewById(R.id.search_edt);
        my_dataBase = RoomAllData.getInstanceDataBase(ShipmentsReport.this);
        total_qty_text=findViewById(R.id.total_qty_text);
        SH_date=findViewById(R.id.SH_date);
        perview=findViewById(R.id.SH_preview);
        listView=findViewById(R.id.SH_reportlist);
        search_edt.setOnKeyListener(onKeyListener);
        search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()!=0){


                      search();
                  }
                  else {

                      List<Shipment> shipments= my_dataBase.shipmentDao().getdateshipments( SH_date.getText().toString());
                      fillAdapterData(shipments);
                    CalculateSum(  shipments);
                  }
            }
        });

    }
    public DatePickerDialog.OnDateSetListener openDatePickerDialog(final int flag) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(flag);
            }

        };
        return date;
    }
    private void updateLabel(int flag) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (flag == 0)
           SH_date.setText(sdf.format(myCalendar.getTime()));

    }
    private void search() {
        searchlist.clear();
        String searchED=search_edt.getText().toString().trim().toLowerCase();

        for (int i = 0; i < allShipmentslist.size(); i++) {

            if ( allShipmentslist.get(i).getShipmentDate().equals(SH_date.getText().toString())
               && (allShipmentslist.get(i).getBarcode().startsWith(searchED)
                    || (allShipmentslist.get(i).getItemname().toLowerCase().startsWith(searchED))
                    ||(allShipmentslist.get(i).getPoNo().startsWith(searchED))
            )  )
                searchlist.add(allShipmentslist.get(i));


        }


        if(searchlist.size()>0){
            fillAdapterData(searchlist);
            CalculateSum(searchlist);
           // tableRow.setVisibility(View.VISIBLE);
            }
        else
        {
            searchlist=new ArrayList<>();
            fillAdapterData(searchlist);
            total_qty_text.setText("");
            //tableRow.setVisibility(View.GONE);

          //  search_edt.setText("");
         }


}

    private void fillAdapterData(List<Shipment> searchlist) {
        Log.e("searchlist==",searchlist.size()+"");
        ShipmentsReportAdapter reportAdapter=new ShipmentsReportAdapter(searchlist,ShipmentsReport.this);
        listView.setAdapter(reportAdapter);
        CalculateSum(searchlist);
    }
    private void CalculateSum(List<Shipment> Shipmentslist){
       long sum=0;
        for(int i = 0; i< Shipmentslist.size(); i++)
            sum+= Long.parseLong(Shipmentslist.get(i).getQty());

        total_qty_text.setText(sum+"");
    }
    @Override
    protected void onPause() {
        Log.e("onPause","onPause");
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
        //openUthenticationDialog();

    }
    private void fillSp() {

        List<String> list=my_dataBase.shipmentDao().getboxes2();

        list.add(0,"All");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,list );

        //box.setAdapter(adapter);


        Log.e("sss1","sss1");


    }
    private void fillpOSp() {

        List<String> list=my_dataBase.shipmentDao().getallpo();

        list.add(0,"All");



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,list );
        Log.e("list",list.size()+"");
        PONO_edt.setAdapter(adapter);


        Log.e("sss1","sss1");


    }
    }