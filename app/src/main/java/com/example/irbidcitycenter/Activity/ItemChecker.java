package com.example.irbidcitycenter.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.Adapters.ShipmentAdapter;
import com.example.irbidcitycenter.Adapters.StockInfoqtyAdapter;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.AllItems;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import static com.example.irbidcitycenter.Activity.MainActivity.FILE_NAME;
import static com.example.irbidcitycenter.Activity.MainActivity.KEY_LANG;
import static com.example.irbidcitycenter.Activity.Login.userPermissions;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;
import static com.example.irbidcitycenter.ImportData.AllImportItemlist;
import static com.example.irbidcitycenter.ImportData.UserPermissions;
import static com.example.irbidcitycenter.ImportData.hideProgressDialogWithTitle;
import static com.example.irbidcitycenter.ImportData.itemInfos;
import static com.example.irbidcitycenter.ImportData.pditeminfo;
import static com.example.irbidcitycenter.ImportData.stocksQty;

import java.util.Locale;

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
    COLORNAME,  ITC_itemcodeshow,
    LENGTH ,
    COLORCODE,
    ZONE ,ITC_freeze,discountpercentage,
    SHELF ;;
    public static  EditText ItC_itemcode;
    public TextView itemname,itemkind,saleprice,qty;
          public  static TextView itemRES,stockqrtRes;
    Button back;
 ImportData importData;
 RecyclerView  recyclerView;
    private StockInfoqtyAdapter adapter;
LinearLayout avgcostLin, headerLin, LASTSPRICELin,LLCPRICELin;
    private String dis_per="";
    AudioManager mode ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_item_checker);


        init();
        mode = (AudioManager)ItemChecker.this.getSystemService(Context.AUDIO_SERVICE);
        mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        headerLin.setVisibility(View.INVISIBLE);
        try {
            if(userPermissions.getMasterUser().equals("0") )
                if(userPermissions.getVIEWCost().equals("0") )
                {
                    avgcostLin .setVisibility(View.GONE);
                LASTSPRICELin.setVisibility(View.GONE);
                    LLCPRICELin.setVisibility(View.GONE);

                }
                else
            {  avgcostLin.setVisibility(View.VISIBLE);
                LASTSPRICELin.setVisibility(View.VISIBLE);
                LLCPRICELin.setVisibility(View.VISIBLE);
            }

            else
            {
                avgcostLin .setVisibility(View.VISIBLE);
                LASTSPRICELin.setVisibility(View.VISIBLE);
                LLCPRICELin.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){

        }

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
            try {
                onBackPressed();
            }catch (Exception e){

            }


        }


  else      if (i != KeyEvent.KEYCODE_ENTER) {

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
                                importData. GetItemCountInSTR();

                        }
                            else{
                                clearData();
                                ItC_itemcode.requestFocus();
                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                // Vibrate for 500 milliseconds
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                                } else {
                                    //deprecated in API 26
                                    v.vibrate(500);
                                }
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
    ITC_itemcodeshow.setText("");
    TAXPERC .setText("");
    AVGCOST .setText("");

   ITC_freeze.setText("");
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
    discountpercentage.setText("");
    ZONE.setText("");
    SHELF .setText("");


}
    private void init() {
        discountpercentage=findViewById(R.id.ITC_discountpercentage);
        ITC_freeze=findViewById(R.id.ITC_freeze);
        headerLin=findViewById(R.id. headerLin);
        LASTSPRICELin=findViewById(R.id.LASTSPRICELin);
        LLCPRICELin =findViewById(R.id. LLCPRICELin);
        avgcostLin=findViewById(R.id.avgcostLin);
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
        ITC_itemcodeshow = findViewById(R.id.ITC_itemcodeshow);
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
                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                pditeminfo.dismiss();
                                 }
                        });

                        ITEMNAME .setText(itemInfos.get(0).getITEMNAME());
                        ITC_itemcodeshow.setText(itemInfos.get(0).getItemcode());
                        TAXPERC .setText(itemInfos.get(0).getTAXPERC());
                        AVGCOST .setText(itemInfos.get(0).getAVGCOST());


                        SALEPRICE .setText(itemInfos.get(0).getSALEPRICE());
                        LLCPRICE.setText(itemInfos.get(0).getLLCPRICE());
                        F_D .setText(itemInfos.get(0).getF_D());
                        LASTSPRICE .setText(itemInfos.get(0).getLASTSPRICE());

                        BUYERGGROUP.setText(itemInfos.get(0).getBUYERGGROUP());
                        DIVISION .setText(itemInfos.get(0).getDIVISION());
                        SUBDIVISION .setText(itemInfos.get(0).getSUBDIVISION());
                        CLASS.setText(itemInfos.get(0).getCLASS());

                        SSIZE.setText(itemInfos.get(0).getSSIZE());
                        SEASON .setText(itemInfos.get(0).getSEASON ());
                        STYLECODE.setText(itemInfos.get(0).getSTYLECODE());
                        SECTION .setText(itemInfos.get(0).getSECTION());

                        BRANDNAME .setText(itemInfos.get(0).getBRANDNAME());
                        COLORNAME.setText(itemInfos.get(0).getCOLORNAME());
                        LENGTH .setText(itemInfos.get(0).getLENGTH());
                        COLORCODE .setText(itemInfos.get(0).getCOLORCODE());
                        //dis_per=1-(offerprice-posprice);
                        discountpercentage.setText(dis_per);
                        if(itemInfos.get(0).getFREEZ().equals("1"))
                        {
                            ITC_freeze.setText("Yes");
                            ITC_freeze.setTextColor(Color.RED);
                        }
                       else {

                           ITC_freeze.setText("No");
                            ITC_freeze.setTextColor(Color.GREEN);
                       }
                        ZONE.setText(itemInfos.get(0).getZONE());
                        SHELF .setText(itemInfos.get(0).getSHELF());
                        ItC_itemcode.setText("");
                        ItC_itemcode.requestFocus();

                    }  if (editable.toString().equals("nodata")) {


                        try {
                            Handler h = new Handler(Looper.getMainLooper());
                            h.post(new Runnable() {
                                public void run() {
                                    pditeminfo.dismiss();
                                    // hideProgressDialogWithTitle();
                                }
                            });

                        }
                   catch (Exception e){

                   }
                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                showSweetDialog(ItemChecker.this,0,"","No Data For this Item  "+ ItC_itemcode.getText());
                                ItC_itemcode.setText("");
                            }
                        });
                         Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            v.vibrate(500);
                        }

                       //   ItC_itemcode.setError("Invalid");

                        Log.e("ca:","ca");
                        ItC_itemcode.requestFocus();

                    }else  if (editable.toString().equals("NoInterNet")) {

                     Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {
                                pditeminfo.dismiss();
                                // hideProgressDialogWithTitle();
                                     }
                        });

                        Handler h1 = new Handler(Looper.getMainLooper());
                        h1.post(new Runnable() {
                            public void run() {
                                showSweetDialog(ItemChecker.this,0,"","Check Connection");

                            }
                        });
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
                        headerLin.setVisibility(View.VISIBLE);
                        filladapter();
                    }
                    else{

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