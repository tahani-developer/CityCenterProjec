package com.example.irbidcitycenter.Activity;

import static com.example.irbidcitycenter.Activity.MainActivity.FILE_NAME;
import static com.example.irbidcitycenter.Activity.MainActivity.KEY_LANG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.Adapters.ShipmentsReportAdapter;
import com.example.irbidcitycenter.Adapters.StockTakeRepAdapter;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.Models.StocktakeModel;
import com.example.irbidcitycenter.Models.Store;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StockTakeReport extends AppCompatActivity {
    TextView STR_date, total_qty_text;
    Calendar myCalendar;
    Button STR_preview;
    RoomAllData my_dataBase;
    ListView listView;
    TextView STR_search_edt;
    private List<StocktakeModel> searchlist = new ArrayList<>();
    private List<StocktakeModel> allStoketakes = new ArrayList<>();
    private List<StocktakeModel> Stoketakes = new ArrayList<>();

    private Spinner storeSpinner;
    private ArrayList<String> spinnerArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_stock_take_report);

        listView = findViewById(R.id.STR_reportlist);
        total_qty_text = findViewById(R.id.STR_total_qty_text);
        STR_search_edt = findViewById(R.id.STR_search_edt);
        STR_search_edt.addTextChangedListener(new TextWatcher() {
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

                    allStoketakes = my_dataBase.stocktakeDao().getdateStoketakes(STR_date.getText().toString());
                    fillAdapterData(allStoketakes);
                    CalculateSum(allStoketakes);
                }
            }
        });


        my_dataBase = RoomAllData.getInstanceDataBase(StockTakeReport.this);
        allStoketakes = my_dataBase.stocktakeDao().getall();
        STR_preview = findViewById(R.id.STR_preview);
        STR_date = findViewById(R.id.STR_date);
        myCalendar = Calendar.getInstance();

        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String today = df.format(currentTimeAndDate);
        STR_date.setText(today);
        STR_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stoketakes = my_dataBase.stocktakeDao().getdateStoketakes(STR_date.getText().toString());
                fillAdapterData(Stoketakes);
                Log.e("Stoketakesssize", Stoketakes.size() + "");
                if (Stoketakes.size() == 0)
                    Toast.makeText(StockTakeReport.this, getString(R.string.noData), Toast.LENGTH_SHORT).show();
                CalculateSum(Stoketakes);
            }
        });
        STR_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(StockTakeReport.this, openDatePickerDialog(0), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        ////Initialize Store Spinner
        storeSpinner = findViewById(R.id.storeSpinner);

        spinnerArray.clear();
        spinnerArray.add(getString(R.string.allStores));

        List<String> stores = my_dataBase.stocktakeDao().getStoresNames();

        spinnerArray.addAll(stores);


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, spinnerArray
        );

        storeSpinner.setAdapter(spinnerAdapter);
        storeSpinner.setSelection(0);

        storeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (STR_search_edt.getText().toString().length() != 0) {
                    search();
                } else {
                    searchByStore();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void searchByStore() {

        if (storeSpinner.getSelectedItemPosition() == 0) {
            Stoketakes = my_dataBase.stocktakeDao().getdateStoketakes(STR_date.getText().toString());
            fillAdapterData(Stoketakes);
            Log.e("Stoketakesssize", Stoketakes.size() + "");
            if (Stoketakes.size() == 0)
                Toast.makeText(StockTakeReport.this, getString(R.string.noData), Toast.LENGTH_SHORT).show();
            CalculateSum(Stoketakes);
        } else {
            searchlist.clear();
            for (int i = 0; i < allStoketakes.size(); i++) {
                if (allStoketakes.get(i).getDate().equals(STR_date.getText().toString())
                        && storeSpinner.getSelectedItem().toString().contains(allStoketakes.get(i).getStore())) {

                    searchlist.add(allStoketakes.get(i));

                }
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

    }
    //////////

    private void loadLanguage() {
        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String langCode = preferences.getString(KEY_LANG, Locale.getDefault().getLanguage());
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private void search() {
        searchlist.clear();
        String searchED = STR_search_edt.getText().toString().trim().toLowerCase();

        for (int i = 0; i < allStoketakes.size(); i++) {

            if (storeSpinner.getSelectedItemPosition() == 0) {
                if (allStoketakes.get(i).getDate().equals(STR_date.getText().toString())
                        && (allStoketakes.get(i).getItemOcode().startsWith(searchED)
                        || (allStoketakes.get(i).getItemName().toLowerCase().startsWith(searchED))
                        || (allStoketakes.get(i).getZone().toLowerCase().startsWith(searchED))
                ))
                    searchlist.add(allStoketakes.get(i));
            } else {
                if (allStoketakes.get(i).getDate().equals(STR_date.getText().toString())
                        && (storeSpinner.getSelectedItem().toString().contains(allStoketakes.get(i).getStore()))
                        && ((allStoketakes.get(i).getItemOcode().startsWith(searchED)
                        || (allStoketakes.get(i).getItemName().toLowerCase().startsWith(searchED))
                        || (allStoketakes.get(i).getZone().toLowerCase().startsWith(searchED)))
                ))
                    searchlist.add(allStoketakes.get(i));
            }
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
            STR_date.setText(sdf.format(myCalendar.getTime()));

    }

    private void fillAdapterData(List<StocktakeModel> searchlist) {
        StockTakeRepAdapter reportAdapter = new StockTakeRepAdapter(searchlist, StockTakeReport.this);
        listView.setAdapter(reportAdapter);
    }

    private void CalculateSum(List<StocktakeModel> Stocktakslist) {
        long sum = 0;
        for (int i = 0; i < Stocktakslist.size(); i++)
            sum += Long.parseLong(Stocktakslist.get(i).getQty());

        total_qty_text.setText(sum + "");
    }

    @Override
    protected void onPause() {
        Log.e("onPause", "onPause");
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
        //openUthenticationDialog();

    }
}