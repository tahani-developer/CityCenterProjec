package com.example.irbidcitycenter.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.Models.appSettings;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static com.example.irbidcitycenter.Activity.AddZone.exportStateText;
import static com.example.irbidcitycenter.ImportData.listAllZone;

public class Login extends AppCompatActivity {
    EditText username,password;

    LinearLayout colorLinear, imgInner, imgOutter;
    FrameLayout mainLinearAnim;
    ImportData importData;
    public RoomAllData my_dataBase;
    public  static   TextView getListCom,selectedCompany;
    public   String selectedCom="", cono="",coYear="";;
    GeneralMethod generalMethod;
TextView settings;
    public String COMPANYNO;
    public appSettings setting;
    List<appSettings> appSettings;
    public  String SET_qtyup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        colorLinear = findViewById(R.id.colorLinear);
        mainLinearAnim = findViewById(R.id.mainLinearAnim);
        imgInner = (LinearLayout) findViewById(R.id.colorLinear);
        imgOutter = (LinearLayout) findViewById(R.id.colorLinear2);
        selectedCompany=findViewById(R.id.selectedCompany);
        generalMethod=new GeneralMethod(Login.this);
        my_dataBase= RoomAllData.getInstanceDataBase(Login.this);
        setImageLoop();
        username = findViewById(R.id.username_input);
        password = findViewById(R.id.pass);
        importData=new ImportData(Login.this);
        settings=findViewById(R.id.setting);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingDialog();
            }
        });

        username=findViewById(R.id.username_input);
        password=findViewById(R.id.pass);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().equals("6") && password.getText().toString().trim().equals("123")) {
                    MainActivity.SET_userNO = username.getText().toString().trim();
                    if(existCoNo(2))
                    {
                        if(existCoNo(1))
                        {
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            generalMethod.showSweetDialog(Login.this,3,"fill company No setting","");
                        }

                    }
                    else {
                        generalMethod.showSweetDialog(Login.this,3,"fill ip setting","");
                    }

                } else {
                    username.setError("");
                    password.setError("");

                }
            }
        });

    }

    private void setImageLoop() {
        // Need a thread to get the real size or the parent
        // container, after the UI is displayed
        imgInner.post(new Runnable() {
            @Override
            public void run() {
                TranslateAnimation outAnim =
                        new TranslateAnimation(
                                0f, mainLinearAnim.getWidth(), 0f, 0f);
                // move from 0 (START) to width (PARENT_SIZE)
                outAnim.setInterpolator(new LinearInterpolator());
                outAnim.setRepeatMode(Animation.INFINITE); // repeat the animation
                outAnim.setRepeatCount(Animation.INFINITE);
                outAnim.setDuration(2000);

                TranslateAnimation inAnim =
                        new TranslateAnimation(
                                -mainLinearAnim.getWidth(), 0f, 0f, 0f);
                // move from out width (-PARENT_SIZE) to 0 (START)
                inAnim.setInterpolator(new LinearInterpolator());
                inAnim.setRepeatMode(Animation.INFINITE);
                inAnim.setRepeatCount(Animation.INFINITE);
                inAnim.setDuration(2000); // same duration as the first

                imgInner.startAnimation(outAnim); // start first anim
                imgOutter.startAnimation(inAnim); // start second anim
            }
        });
    }

    private void animateFromButtumToTop() {
        Animation RightSwipe = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f);

        RightSwipe.setDuration(2000); // animation duration
        RightSwipe.setRepeatCount(Animation.INFINITE); // animation repeat count
        RightSwipe.setRepeatMode(2); // repeat animation (left to right, right to left)

        colorLinear.startAnimation(RightSwipe);
    }

    public void slidefromRightToLeft(View view) {

        TranslateAnimation animate;
        if (view.getHeight() == 0) {
            view.getHeight(); // parent layout
            animate = new TranslateAnimation(view.getWidth() / 2,
                    0, 0, 0);
        } else {
            animate = new TranslateAnimation(view.getWidth(), 0, 0, 0); // View for animation
        }

        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE); // Change visibility VISIBLE or GONE
    }

    public void showCompanyDialog(View view) {
        if(view.getId()==R.id.companyName)
        {
            if(existCoNo(2))
            showCompany(Login.this);
            else {
                generalMethod.openSettingDialog();
            }
        }
    }
    private void showCompany(final Context context1) {

        final Dialog dialog = new Dialog(context1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.zone_search);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
         appSettings settings;
        ArrayList<String> nameOfEngi=new ArrayList<>();
        final ListView listCompany = dialog.findViewById(R.id.listViewEngineering);
        importData.getCompanyInfo();

        getListCom=dialog.findViewById(R.id.getListCom);

        getListCom.addTextChangedListener(new TextWatcher() {
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
                    if(editable.toString().equals("fill"))
                    {
                        if( importData.companyInList.size()!=0) {
                            for (int i = 0; i < importData.companyInList.size(); i++) {
                                nameOfEngi.add(importData.companyInList.get(i).getCoNo() + "\t\t" + importData.companyInList.get(i).getCoYear() + "\t\t" + importData.companyInList.get(i).getCoNameA());
                            }

                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                    (Login.this, android.R.layout.simple_list_item_1, nameOfEngi) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    /// Get the Item from ListView
                                    View view = super.getView(position, convertView, parent);

                                    TextView tv = (TextView) view.findViewById(android.R.id.text1);

                                    // Set the text size 25 dip for ListView each item
                                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                                    tv.setTextColor(getResources().getColor(R.color.text_color));
                                    // Return the view
                                    return view;
                                }
                            };

                            // DataBind ListView with items from ArrayAdapter
                            listCompany.setAdapter(arrayAdapter);


                        }
                    }

                }

            }
        });


        final int[] rowZone = new int[1];
        selectedCom="";

        listCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cono="";coYear="";
                rowZone[0] =position;
                listCompany.requestFocusFromTouch();
                listCompany.setSelection(position);
                selectedCom =importData.companyInList.get(position).getCoNameA();
                cono=importData.companyInList.get(position).getCoNo();
                coYear=importData.companyInList.get(position).getCoYear();

            }
        });


        Button btn_send = dialog.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCompany.setText(selectedCom);

                    updateCono(cono,coYear);

                dialog.dismiss();


            }
        });
        dialog.show();

    }

    private void updateCono(String cono, String coYear) {
        my_dataBase.settingDao().updateCompanyInfo(cono);
        my_dataBase.settingDao().updateCompanyYear(coYear);

       //Log.e("updateCono",""+up);
    }

    private void addSetting() {
        //my_dataBase.settingDao().insert(settings);
    }



    private boolean existCoNo(int flag ) {
           // ipAddress=my_dataBase.settingDao().getIpAddress().trim();
        String  CONO="";
        try {
            if(flag==1)
            {
                CONO=my_dataBase.settingDao().getCono();
            }else {
                if(flag==2)
                    CONO=my_dataBase.settingDao().getIpAddress();
            }

            Log.e("CONO",""+CONO);
            if(CONO!=null)
            {
                if(CONO.length()!=0)
                {
                    return true;
                }

            }
        }catch (Exception e){
            return  false;
        }


            return  false;


        }
    private void openSettingDialog() {
        final Dialog dialog = new Dialog(Login.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ip_setting_dialog);
        dialog.show();


        final EditText ip= dialog.findViewById(R.id.ipEditText);
        final EditText conNO= dialog.findViewById(R.id.cono);
        final EditText years=dialog.findViewById(R.id.storeNo_edit);
        final CheckBox qtyUP=(CheckBox)dialog.findViewById(R.id.qtycheck);
        final EditText usernum= dialog.findViewById(R.id.usernumber);
       // usernum.setText(SET_userNO);

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
                //usernum.setText(SET_userNO);

                if(qtyUP.isChecked())
                    SET_qtyup="1";
                else
                    SET_qtyup="0";

                setting = new appSettings();
                setting.setIP(SET_IP);
                setting.setCompanyNum(SET_conNO);
                setting.setUpdateQTY(SET_years);
                setting.setYears(SET_qtyup);
                setting.setUserNumber("");
                saveData(setting);
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
    private void saveData(appSettings settings) {

        my_dataBase.settingDao().insert(settings);

        generalMethod.showSweetDialog(this,1,this.getResources().getString(R.string.savedSuccsesfule),"");

    }
}