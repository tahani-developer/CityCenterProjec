package com.example.irbidcitycenter.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irbidcitycenter.Adapters.ZoneReportAdapter;
import com.example.irbidcitycenter.Models.StocktakeModel;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import android.app.DatePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.irbidcitycenter.Activity.MainActivity.FILE_NAME;
import static com.example.irbidcitycenter.Activity.MainActivity.KEY_LANG;

public class ZoneReport extends AppCompatActivity {
   ListView listView;
   List<ZoneModel> addZones=new ArrayList<>();
    List<ZoneModel> DateZones=new ArrayList<>();
    List<ZoneModel> adapterlist=new ArrayList<>();
RoomAllData mydatabase;
    Calendar myCalendar;
    ZoneReportAdapter zoneReportAdapter;
    TextView ZRep_date, total_qty_text;
    EditText ZRep_searchedt;
    EditText ZRep_zoneSpinner;
    Button  ZRep_preview;
    List<ZoneModel> searchlist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_zone_report);
        init();
       // fillSp();

        CalculateSum(addZones);
        fillAdapterData( addZones);
        myCalendar = Calendar.getInstance();

        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String today = df.format(currentTimeAndDate);
        ZRep_date.setText(today);
        addZones.clear();
        addZones=mydatabase.zoneDao().getzoneByDate( ZRep_date.getText().toString());
            fillAdapterData(addZones);
        CalculateSum(addZones);
        ZRep_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



               if(ZRep_zoneSpinner.getText().toString().trim().equals(""))

               {DateZones = mydatabase.zoneDao().getzoneByDate(ZRep_date.getText().toString().trim());

                zoneReportAdapter=new ZoneReportAdapter(DateZones,ZoneReport.this);

                listView.setAdapter(zoneReportAdapter);
                Log.e("Stoketakesssize", DateZones.size() + "");
                if (DateZones.size() == 0)
                    Toast.makeText(ZoneReport.this, getString(R.string.noData), Toast.LENGTH_SHORT).show();}

               else {
                   DateZones = mydatabase.zoneDao(). getzoneByDateandZone(ZRep_date.getText().toString().trim(),ZRep_zoneSpinner.getText().toString().trim());

                   zoneReportAdapter=new ZoneReportAdapter(DateZones,ZoneReport.this);

                   listView.setAdapter(zoneReportAdapter);
                   Log.e("Stoketakesssize", DateZones.size() + "");
               }
                CalculateSum(DateZones);
            }
        });
        ZRep_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ZoneReport.this, openDatePickerDialog(0), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        ZRep_zoneSpinner.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int i, KeyEvent KeyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    onBackPressed();

                }

                if (i != KeyEvent.KEYCODE_ENTER) {
                    if (KeyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (! ZRep_zoneSpinner.getText().toString().equals("")) {
                            if(ZRep_zoneSpinner.getText().toString().trim().equals(""))

                            {DateZones = mydatabase.zoneDao().getzoneByDate(ZRep_date.getText().toString().trim());

                                zoneReportAdapter=new ZoneReportAdapter(DateZones,ZoneReport.this);

                                listView.setAdapter(zoneReportAdapter);
                                Log.e("Stoketakesssize", DateZones.size() + "");
                                if (DateZones.size() == 0)
                                    Toast.makeText(ZoneReport.this, getString(R.string.noData), Toast.LENGTH_SHORT).show();}

                            else {
                                DateZones = mydatabase.zoneDao(). getzoneByDateandZone(ZRep_date.getText().toString().trim(),ZRep_zoneSpinner.getText().toString().trim());

                                zoneReportAdapter=new ZoneReportAdapter(DateZones,ZoneReport.this);

                                listView.setAdapter(zoneReportAdapter);
                                Log.e("Stoketakesssize", DateZones.size() + "");
                            }
                            CalculateSum(DateZones);
                            ZRep_zoneSpinner.setText("");
                        }else {
                            ZRep_zoneSpinner.requestFocus();
                        }

                    }




                    return true;

                } return false;
            }
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
    void init(){
        ZRep_preview=findViewById(R.id. ZRep_preview);
        ZRep_zoneSpinner=findViewById(R.id.ZRep_zoneSpinner);
        ZRep_date=findViewById(R.id. ZRep_date);
        ZRep_searchedt=findViewById(R.id. ZRep_searchedt);
        listView=findViewById(R.id.ZRep_reportlist);
        mydatabase=RoomAllData.getInstanceDataBase(ZoneReport.this);
        total_qty_text=findViewById(R.id. ZRep_total_qty_text);

        ZRep_searchedt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {

                    search();
                } else {
//
//                    DateZones = mydatabase.zoneDao().getzoneByDate(ZRep_date.getText().toString().trim());
//
//                    addZones
                    fillAdapterData(  addZones);
                    CalculateSum(DateZones);
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
            ZRep_date.setText(sdf.format(myCalendar.getTime()));

    }
    private void search() {
        searchlist.clear();
        String searchED = ZRep_searchedt.getText().toString().trim().toLowerCase();

        for (int i = 0; i < adapterlist.size(); i++) {

                if (
                         adapterlist.get(i).getItemCode().contains(searchED)
                        ||  adapterlist.get(i).getZoneCode().toLowerCase().contains(searchED)
                )
                    searchlist.add(adapterlist.get(i));
            }



        if (searchlist.size() > 0) {
            fillAdapterData(searchlist);
            CalculateSum(searchlist);
            // tableRow.setVisibility(View.VISIBLE);
        } else {
            searchlist = new ArrayList<>();
            fillAdapterData(searchlist);
            total_qty_text.setText("");
            //tableRow.setVisibility(View.GONE);

            //  STR_search_edt.setText("");
        }

    }
    private void CalculateSum(List<ZoneModel> zoneModels) {
        long sum = 0;
        for (int i = 0; i < zoneModels.size(); i++)
            sum += Long.parseLong(zoneModels.get(i).getQty());

        total_qty_text.setText(sum + "");
    }
    private void  fillAdapterData(List<ZoneModel>aList){
        adapterlist.clear();
        adapterlist.addAll(aList);
        zoneReportAdapter=new ZoneReportAdapter(aList,ZoneReport.this);

        listView.setAdapter(zoneReportAdapter);
        CalculateSum(aList);
    }
    private void fillSp() {

        List<String> list=mydatabase.zoneDao().getZonesUnposted();
        list.add(0,"All");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,list );

        //ZRep_zoneSpinner.setAdapter(adapter);


        Log.e("sss1","sss1");


    }
}