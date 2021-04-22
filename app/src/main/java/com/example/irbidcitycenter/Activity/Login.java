package com.example.irbidcitycenter.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.appSettings;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import java.util.ArrayList;

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


        username=findViewById(R.id.username_input);
        password=findViewById(R.id.pass);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().equals("6") && password.getText().toString().trim().equals("123")) {
                    MainActivity.SET_userNO = username.getText().toString().trim();
                    Log.e("sssssssss","ssssssss");
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
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
                            Log.e("nameOfEngi", "" + nameOfEngi.size());

//                    simple_list_item_1 simple_list_item_activated_1 simple_list_item_1
//                            ArrayAdapter<String> itemsAdapter =
//                                    new ArrayAdapter<String>(Login.this, android.R.layout.simple_list_item_activated_1, nameOfEngi);
//                            listCompany.setAdapter(itemsAdapter);

                            //**********************
                            // Create an ArrayAdapter from List
                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                    (Login.this, android.R.layout.simple_list_item_1, nameOfEngi) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    /// Get the Item from ListView
                                    View view = super.getView(position, convertView, parent);

                                    TextView tv = (TextView) view.findViewById(android.R.id.text1);

                                    // Set the text size 25 dip for ListView each item
                                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);

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
                Log.e("onItemClick",""+rowZone[0]+"\t"+selectedCom);
                Log.e("onItemClick",""+cono+"\t"+coYear);

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

            return  false;


        }

}