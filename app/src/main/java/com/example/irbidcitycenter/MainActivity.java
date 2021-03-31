package com.example.irbidcitycenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.irbidcitycenter.Models.DatabaseHandler;
import com.example.irbidcitycenter.Models.appSettings;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    public static String SET_userNO;
    private Toolbar toolbar;
    LinearLayout zoneLinear,shipmentlinear,replacmentlinear;
    public  String SET_qtyup;
    public appSettings settings;
    public DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        databaseHandler=new DatabaseHandler(MainActivity.this);
    }

    private void initial() {
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
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id=v.getId();
           switch (id){
               case R.id.zoneLinear:
                   Intent intent =new Intent(MainActivity.this,AddZone.class);
                   startActivity(intent);
                   break;
               case R.id.hipmentlinear:
                   Intent intent2 =new Intent(MainActivity.this,NewShipment.class);
                   startActivity(intent2);
                   break;
               case R.id.Replacmentlinear:
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

        }


        return true;

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
        settings= databaseHandler.getSettings();

        ip.setText(settings.getIP());
        conNO.setText( settings.getCompanyNum());
        years.setText(settings.getYears());
        //****************************
        dialog.findViewById(R.id.saveSetting).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {





                final String SET_IP=ip.getText().toString();
                final String SET_conNO=conNO.getText().toString();
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
                databaseHandler.addSettings(settings);
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


}
