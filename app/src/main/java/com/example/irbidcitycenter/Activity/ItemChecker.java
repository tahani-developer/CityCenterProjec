package com.example.irbidcitycenter.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.irbidcitycenter.Adapters.ShipmentAdapter;
import com.example.irbidcitycenter.Adapters.StockInfoqtyAdapter;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.AllItems;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;
import static com.example.irbidcitycenter.ImportData.AllImportItemlist;
import static com.example.irbidcitycenter.ImportData.hideProgressDialogWithTitle;
import static com.example.irbidcitycenter.ImportData.itemInfos;
import static com.example.irbidcitycenter.ImportData.pditeminfo;
import static com.example.irbidcitycenter.ImportData.stocksQty;

public class ItemChecker extends AppCompatActivity {
    private  TextView ITEMNAME,  TAXPERC,
    AVGCOST,


    SALEPRICE,
    LLCPRICE,
    F_D,
    LASTSPRICE,

    BUYERGGROUP,
    DIVISION ,
    SUBDIVISION ,
    CLASS ,

    SSIZE ,
    SEASON ,
    STYLECODE,
    SECTION ,

    BRANDNAME,
    COLORNAME,
    LENGTH ,
    COLORCODE,
    ZONE ,
    SHELF ;;
    public static  EditText ItC_itemcode;
    public TextView itemname,itemkind,saleprice,qty;
          public  static TextView itemRES,stockqrtRes;
    Button back;
 ImportData importData;
 RecyclerView  recyclerView;
    private StockInfoqtyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_checker);


        init();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id=v.getId();
            switch (id) {

                case R.id.ITC_cancel:
                    finish();
                    break;
            }
        }
    };
TextView.OnKeyListener onKeyListener=new View.OnKeyListener() {
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK) {
            onBackPressed();

        }


        if (i != KeyEvent.KEYCODE_ENTER) {

            {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP)
                    switch (view.getId()) {
                        case R.id.ItC_itemcode: {
                            if(!ItC_itemcode.getText().toString().equals("")){
                                clearData();
                            importData=new ImportData(ItemChecker.this);
                            importData.getItemInfo();
                                stocksQty.clear();
                            filladapter();
                                importData.   GetItemCountInSTR();

                        }else{
                                clearData();
                                ItC_itemcode.requestFocus();
                            }


                        }
                        break;
                    }
            }
            return true;
        }

            return false;
}

};
void clearData(){

    ITEMNAME .setText("");
    TAXPERC .setText("");
    AVGCOST .setText("");


    SALEPRICE .setText("");
    LLCPRICE.setText("");
    F_D .setText("");
    LASTSPRICE .setText("");

    BUYERGGROUP.setText("");
    DIVISION .setText("");
    SUBDIVISION .setText("");
    CLASS.setText("");

    SSIZE.setText("");
    SEASON .setText("");
    STYLECODE.setText("");
    SECTION .setText("");

    BRANDNAME .setText("");
    COLORNAME.setText("");
    LENGTH .setText("");
    COLORCODE .setText("");

    ZONE.setText("");
    SHELF .setText("");


}
    private void init() {
        recyclerView=findViewById(R.id.stockQty);
        itemRES = findViewById(R.id.itemRES);
        importData = new ImportData(ItemChecker.this);
        stockqrtRes=findViewById(R.id.stockqrtRes);
        ItC_itemcode=findViewById(R.id.ItC_itemcode);

        ITEMNAME = findViewById(R.id.ITC_ITEMNAME1);
        TAXPERC = findViewById(R.id.ITC_TAXPERC);
        AVGCOST = findViewById(R.id.ITC_AVGCOST);


        SALEPRICE = findViewById(R.id.SALEPRICE);
        LLCPRICE= findViewById(R.id.ITC_LLCPRICE);
        F_D = findViewById(R.id.ITC_F_D);
        LASTSPRICE = findViewById(R.id.ITC_LASTSPRICE);

        BUYERGGROUP= findViewById(R.id.ITC_BUYERGGROUP);
        DIVISION = findViewById(R.id.ITC_DIVISION);
        SUBDIVISION = findViewById(R.id.ITC_SUBDIVISION);
        CLASS = findViewById(R.id.ITC_CLASS);

        SSIZE = findViewById(R.id.ITC_SSIZE);
        SEASON = findViewById(R.id.ITC_SEASON);
        STYLECODE= findViewById(R.id.ITC_STYLECODE);
        SECTION = findViewById(R.id.ITC_SECTION);

        BRANDNAME = findViewById(R.id.ITC_BRANDNAME);
        COLORNAME = findViewById(R.id.ITC_COLORNAME);
        LENGTH = findViewById(R.id.ITC_LENGTH);
        COLORCODE = findViewById(R.id.ITC_COLORCODE);

        ZONE = findViewById(R.id.ITC_ZONE);
        SHELF = findViewById(R.id.ITC_SHELF);


        back = findViewById(R.id.ITC_cancel);
        back.setOnClickListener(onClickListener);
        ItC_itemcode.setOnKeyListener(onKeyListener);
        itemRES.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {

                    if (editable.toString().equals("ItemOCode")) {

                        ITEMNAME .setText(itemInfos.get(0).getITEMNAME());
                        TAXPERC .setText(itemInfos.get(0).getTAXPERC());
                        AVGCOST .setText(itemInfos.get(0).getAVGCOST());


                        SALEPRICE .setText(itemInfos.get(0).getSALEPRICE());
                        LLCPRICE.setText(itemInfos.get(0).getLLCPRICE());
                        F_D .setText(itemInfos.get(0).getF_D());
                        LASTSPRICE .setText(itemInfos.get(0).getLASTSPRICE());

                        BUYERGGROUP.setText(itemInfos.get(0).getBUYERGGROUP());
                        DIVISION .setText(itemInfos.get(0).getDIVISION());
                        SUBDIVISION .setText(itemInfos.get(0).getSUBDIVISION());
                        CLASS.setText(itemInfos.get(0).getITEMNAME());

                        SSIZE.setText(itemInfos.get(0).getSSIZE());
                        SEASON .setText(itemInfos.get(0).getSEASON ());
                        STYLECODE.setText(itemInfos.get(0).getSTYLECODE());
                        SECTION .setText(itemInfos.get(0).getSECTION());

                        BRANDNAME .setText(itemInfos.get(0).getBRANDNAME());
                        COLORNAME.setText(itemInfos.get(0).getCOLORNAME());
                        LENGTH .setText(itemInfos.get(0).getLENGTH());
                        COLORCODE .setText(itemInfos.get(0).getCOLORCODE());

                        ZONE.setText(itemInfos.get(0).getZONE());
                        SHELF .setText(itemInfos.get(0).getSHELF());
                        ItC_itemcode.setText("");
                        ItC_itemcode.requestFocus();


                    }  if (editable.toString().equals("nodata")) {
                     //   showSweetDialog(ItemChecker.this,0,"","No Data For this Item");
                          ItC_itemcode.setError("Invalid");
                        ItC_itemcode.setText("");
                        Log.e("ca:","ca");
                        ItC_itemcode.requestFocus();

                    }else  if (editable.toString().equals("NoInterNet")) {
                       showSweetDialog(ItemChecker.this,0,"","Check Connection");
                        ItC_itemcode.setText("");
                        ItC_itemcode.requestFocus();
                    }
            }}

        });
        stockqrtRes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {

                    if (editable.toString().equals("Stock_Code")) {

                        filladapter();
                    }

                }
            }
        });
    }

    private void filladapter() {

        recyclerView.setLayoutManager(new LinearLayoutManager(ItemChecker.this));
        adapter = new StockInfoqtyAdapter(  stocksQty,ItemChecker.this);
        recyclerView.setAdapter(adapter);

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
    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( pditeminfo!=null && pditeminfo.isShowing() ){
            pditeminfo.cancel();
        }
    }
}