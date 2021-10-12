package com.example.irbidcitycenter.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.Adapters.ShipmentsReportAdapter;
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

import static com.example.irbidcitycenter.GeneralMethod.convertToEnglish;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;

public class ShipmentsReport extends AppCompatActivity {
TextView  SH_date,total_qty_text;
    Calendar myCalendar;
    EditText search_edt;
    Button perview;
    List<Shipment> shipments;
    public RoomAllData my_dataBase;
    ListView listView;
    private List<Shipment> searchlist=new ArrayList<>();
    private List<Shipment> allShipmentslist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipments_report);
        init();
        myCalendar = Calendar.getInstance();

allShipmentslist=my_dataBase.shipmentDao().getallShipment();
        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String today = df.format(currentTimeAndDate);
        SH_date.setText(today);
        SH_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ShipmentsReport.this, openDatePickerDialog(0), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        perview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shipments= my_dataBase.shipmentDao().getdateshipments( SH_date.getText().toString());
          fillAdapterData(shipments);
                Log.e("shipmentssize",shipments.size()+"");
                if(   shipments.size()==0)
                    Toast.makeText(ShipmentsReport.this,"No Data",Toast.LENGTH_SHORT).show();
                CalculateSum(  shipments);
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

                { if (keyEvent.getAction() == KeyEvent.ACTION_UP)
                    switch (view.getId()) {
                        case R.id.zoneedt:

                            break;
                    }  return true;
                }}
            return false;

        }};
    private void init() {
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
        { //tableRow.setVisibility(View.GONE);

          //  search_edt.setText("");
         }


}

    private void fillAdapterData(List<Shipment> searchlist) {
        ShipmentsReportAdapter reportAdapter=new ShipmentsReportAdapter(searchlist,ShipmentsReport.this);
        listView.setAdapter(reportAdapter);
    }
    private void CalculateSum(List<Shipment> Shipmentslist){
        int sum=0;
        for(int i = 0; i< Shipmentslist.size(); i++)
            sum+=Integer.parseInt(Shipmentslist.get(i).getQty());

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
    }