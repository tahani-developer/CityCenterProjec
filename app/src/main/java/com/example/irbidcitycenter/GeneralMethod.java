package com.example.irbidcitycenter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.irbidcitycenter.Activity.Login;
import com.example.irbidcitycenter.Activity.MainActivity;
import com.example.irbidcitycenter.Models.UserPermissions;
import com.example.irbidcitycenter.Models.appSettings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.irbidcitycenter.Activity.NewShipment.colsedialog;
import static com.example.irbidcitycenter.Activity.NewShipment.respon;

public class GeneralMethod {
    public  Context myContext;
    public RoomAllData my_dataBase;
    public appSettings settings;
    List<appSettings> appSettingsList=new ArrayList<>();
    public GeneralMethod(Context context) {
        this.myContext=context;
        my_dataBase= RoomAllData.getInstanceDataBase(myContext);
    }

    public  static  void showSweetDialog(Context context, int type, String title, String content){
        switch ( type){
            case 0://Error Type
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(title)
                        .setContentText(content)
                        .setConfirmText(context.getString(R.string.ok))
                        .show();
                break;
            case 1://Succes Type
                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(title)
                        .setContentText(content)
                        .setConfirmText(context.getString(R.string.ok))
                        .show();
                break;
            case 3://warning Type
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(title)
                        .setContentText(content)
                        .setConfirmText(context.getString(R.string.ok))
                        .show();
                break;

        }
    }

    public String getCurentTimeDate(int flag){
        String dateCurent="",timeCurrent,dateTime="";
        Date currentTimeAndDate;
        SimpleDateFormat dateFormat, timeformat;
        currentTimeAndDate = Calendar.getInstance().getTime();
        if(flag==1)// return date
        {

            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateCurent = dateFormat.format(currentTimeAndDate);
           dateTime=convertToEnglish(dateCurent);

        }
        else {
            if(flag==2)// return time
            {
                timeformat = new SimpleDateFormat("hh:mm");
                dateCurent = timeformat.format(currentTimeAndDate);
                dateTime=convertToEnglish(dateCurent);
            }
        }
        return dateTime;

    }
    public static String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }
    public boolean validateNotEmpty(EditText editText) {
        if(!editText.getText().toString().trim().equals(""))
        {
            editText.setError(null);
            return true;
        }
        else {
            editText.setError(myContext.getResources().getString(R.string.reqired_filled));
            editText.requestFocus();
            return false;
        }

    }
    public boolean validateNotZero(EditText editText) {
        if(!editText.getText().toString().trim().equals("0") &&Integer.parseInt(editText.getText().toString().trim())!=0)
        {
            editText.setError(null);
            return true;
        }
        else {
            editText.setError(myContext.getResources().getString(R.string.invaledZero));
            editText.requestFocus();
            return false;
        }

    }


    public void openSettingDialog() {
        final Dialog dialog = new Dialog(myContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ip_setting_dialog);
        dialog.show();


        final EditText ip= dialog.findViewById(R.id.ipEditText);
        final EditText conNO= dialog.findViewById(R.id.cono);
//        conNO.setEnabled(false);
        final EditText years=dialog.findViewById(R.id.storeNo_edit);
        years.setEnabled(false);
        final CheckBox qtyUP=(CheckBox)dialog.findViewById(R.id.qtycheck);
        final EditText usernum= dialog.findViewById(R.id.usernumber);
        usernum.setEnabled(false);
       // usernum.setText(SET_userNO);
        appSettingsList=new ArrayList<>();

        try {
            appSettingsList=my_dataBase.settingDao().getallsetting();
        }
        catch (Exception e){}
        if(appSettingsList.size()!=0) {

            ip.setText(appSettingsList.get(0).getIP());
            conNO.setText(appSettingsList.get(0).getCompanyNum());
            years.setText(appSettingsList.get(0).getYears());
//            if (appSettingsList.get(0).getUpdateQTY().equals("1"))
//                qtyUP.setChecked(true);
        }
        //****************************
        dialog.findViewById(R.id.saveSetting).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                deletesettings();
                final String SET_IP=ip.getText().toString().trim();
                final String SET_conNO=conNO.getText().toString().trim();
                final String SET_years=years.getText().toString().trim();
                usernum.setText("6");

//                if(qtyUP.isChecked())
//                    SET_qtyup="1";
//                else
//                    SET_qtyup="0";

                settings = new appSettings();
                settings.setIP(SET_IP);
                settings.setCompanyNum(SET_conNO);
                settings.setUpdateQTY(SET_years);
                settings.setYears("2023");
                settings.setUserNumber("6");
//                saveData(settings);
                my_dataBase.settingDao().insert(settings);
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
    private void deletesettings(){
        if(appSettingsList.size()!=0)
            my_dataBase.settingDao().deleteALL();
    }

    public static boolean  checkIfUserWhoLoginIsMaster(Context context,String username,String pass){
RoomAllData my_dataBase=  RoomAllData.getInstanceDataBase(context);

      String s=  my_dataBase.userPermissionsDao().getIsMaster(username,pass);
if(s!=null)
    if(s.equals("1"))
            return true;
        else
            return false;
        else
    return false;
    }
public  List<String> GetAllowedStore(){
    String comNUm = "";
    String Userno = "";
    int count = 0;
   List<appSettings >appSettings = my_dataBase.settingDao().getallsetting();
    if (appSettings.size() != 0) {
        Userno = appSettings.get(0).getUserNumber();
        comNUm = appSettings.get(0).getCompanyNum();

    }

  UserPermissions userPermissions= my_dataBase.userPermissionsDao().getUserPermissions(Userno);
    List<String> items = new ArrayList<>();
    if(userPermissions!=null) {
      if (userPermissions.getCONO1().equals(comNUm)) count = 1;
      else if (userPermissions.getCONO2().equals(comNUm)) count = 2;
      else if (userPermissions.getCONO3().equals(comNUm)) count = 3;
      else if (userPermissions.getCONO4().equals(comNUm)) count = 4;
      else if (userPermissions.getCONO5().equals(comNUm)) count = 5;
      else if (userPermissions.getCONO6().equals(comNUm)) count = 6;
      else if (userPermissions.getCONO7().equals(comNUm)) count = 7;
      else if (userPermissions.getCONO8().equals(comNUm)) count = 8;
      else if (userPermissions.getCONO9().equals(comNUm)) count = 9;
      else if (userPermissions.getCONO10().equals(comNUm)) count = 10;



      if (count == 1) {

          items = Arrays.asList(userPermissions.getCOSTORE1().split(";"));

      } else if (count == 2) {

          items = Arrays.asList(userPermissions.getCOSTORE2().split(";"));

      } else if (count == 3) {

          items = Arrays.asList(userPermissions.getCOSTORE3().split(";"));

      } else if (count == 4) {

          items = Arrays.asList(userPermissions.getCOSTORE4().split(";"));

      } else if (count == 5) {

          items = Arrays.asList(userPermissions.getCOSTORE5().split(";"));

      } else if (count == 6) {

          items = Arrays.asList(userPermissions.getCOSTORE6().split(";"));

      } else if (count == 7) {

          items = Arrays.asList(userPermissions.getCOSTORE7().split(";"));

      } else if (count == 8) {

          items = Arrays.asList(userPermissions.getCOSTORE8().split(";"));

      } else if (count == 9) {

          items = Arrays.asList(userPermissions.getCOSTORE9().split(";"));

      } else if (count == 10) {

          items = Arrays.asList(userPermissions.getCOSTORE10().split(";"));

      }

      for (int i = 0; i < items.size(); i++)
          Log.e("items==", items.get(i) + "   " + items.size() + "");
  }
return items;
}
}
