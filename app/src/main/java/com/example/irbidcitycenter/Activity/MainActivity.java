package com.example.irbidcitycenter.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.Models.appSettings;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    GeneralMethod generalMethod;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    public static String SET_userNO;
    private Toolbar toolbar;
    LinearLayout zoneLinear,shipmentlinear,replacmentlinear;
    public  String SET_qtyup;
    public appSettings settings;
    ExportData exportData;

    public static int setflage=0;
    public static String COMPANYNO;
    private Animation animation;
    public List<appSettings> settingslist=new ArrayList<>();
ImportData importData;
    public RoomAllData my_dataBase;
    List<appSettings> appSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        getStors();

        my_dataBase= RoomAllData.getInstanceDataBase(MainActivity.this);


    }

    private void getStors() {
        importData.getStore();
    }

    private void initial() {
        importData=new ImportData(MainActivity.this);
        drawerLayout = findViewById(R.id.main_drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        zoneLinear=findViewById(R.id.zoneLinear);
        zoneLinear.setOnClickListener(onClickListener);
        shipmentlinear=findViewById(R.id.hipmentlinear);
        shipmentlinear.setOnClickListener(onClickListener);
        replacmentlinear=findViewById(R.id.Replacmentlinear);
        replacmentlinear.setOnClickListener(onClickListener);
        exportData=new ExportData(MainActivity.this);




    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id=v.getId();
           switch (id){
               case R.id.zoneLinear:
                   animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                   zoneLinear.startAnimation(animation);
                   Intent intent =new Intent(MainActivity.this,AddZone.class);
                   startActivity(intent);
                   break;
               case R.id.hipmentlinear:
                   animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                   shipmentlinear.startAnimation(animation);
                   Intent intent2 =new Intent(MainActivity.this,NewShipment.class);
                   startActivity(intent2);
                   break;
               case R.id.Replacmentlinear:
                   animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                   replacmentlinear.startAnimation(animation);
                   Intent intent3 =new Intent(MainActivity.this,Replacement.class);
                   startActivity(intent3);
                   break;
           }
        }
    };

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            Log.e("iditem", "onOptionsItemSelected " + item.getItemId());
            return true;
        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.e("id", "onNavigationItemSelected " + id);
        switch (id) {
            case R.id.menu_setting: {
                Log.e("id", "menu_setting " + id);
                drawerLayout.closeDrawer(navigationView);
                openSettingDialog();
            }
            break;
            case R.id.menu_export:
                exportAllData();

            break;

        }


        return true;

    }

    private void exportAllData() {
        List<ZoneModel> listZon=new ArrayList();
        ArrayList<Shipment> newShipmentList=new ArrayList();
        ArrayList<ReplacementModel> replacementList=new ArrayList();
        List<Shipment> listShipment;
        List<ReplacementModel>listReplasment;

        listZon=my_dataBase.zoneDao().getUnpostedZone("0");
        listShipment=my_dataBase.shipmentDao().getUnpostedShipment("0");
        listReplasment=my_dataBase.replacementDao().getUnpostedReplacement("0");

        newShipmentList=(ArrayList<Shipment>) listShipment;

        replacementList=(ArrayList<ReplacementModel>)listReplasment ;
        Log.e("exportAllData","2"+newShipmentList.size());
        Log.e("listZon2","unposted"+listZon.size()+"\tShipment"+newShipmentList.size()+"repla"+replacementList.size());
        exportData.exportAllUnposted(listZon,newShipmentList,replacementList);

    }

    private void openSettingDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ip_setting_dialog);
        dialog.show();


        final EditText ip= dialog.findViewById(R.id.ipEditText);
        final EditText conNO= dialog.findViewById(R.id.cono);
        final EditText years=dialog.findViewById(R.id.storeNo_edit);
        final CheckBox qtyUP=(CheckBox)dialog.findViewById(R.id.qtycheck);
        final EditText usernum= dialog.findViewById(R.id.usernumber);
        usernum.setText(SET_userNO);

        getDataZone();
        if(appSettings.size()!=0) {

            ip.setText(appSettings.get(0).getIP());
            conNO.setText(appSettings.get(0).getCompanyNum());
            COMPANYNO=appSettings.get(0).getCompanyNum();
            years.setText(appSettings.get(0).getYears());
            if (appSettings.get(0).getUpdateQTY().equals("1"))
                qtyUP.setChecked(true);
        }
        //****************************
        dialog.findViewById(R.id.saveSetting).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                deletesettings();
                final String SET_IP=ip.getText().toString();
                final String SET_conNO=conNO.getText().toString();
                COMPANYNO=conNO.getText().toString();
                final String SET_years=years.getText().toString();
                usernum.setText(SET_userNO);

                if(qtyUP.isChecked())
                    SET_qtyup="1";
                else
                    SET_qtyup="0";

                settings = new appSettings();
                settings.setIP(SET_IP);
                settings.setCompanyNum(SET_conNO);
                settings.setUpdateQTY(SET_years);
                settings.setYears(SET_qtyup);
                settings.setUserNumber(SET_userNO);
                saveData(settings);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void saveData(appSettings settings) {

         my_dataBase.settingDao().insert(settings);

            generalMethod.showSweetDialog(this,1,this.getResources().getString(R.string.savedSuccsesfule),"");

    }
    private void getDataZone() {
         appSettings=new ArrayList();
         try {
             appSettings=my_dataBase.settingDao().getallsetting();
         }
        catch (Exception e){}
    }
    private void deletesettings(){
        if(appSettings.size()!=0)
        my_dataBase.settingDao().deleteALL();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
