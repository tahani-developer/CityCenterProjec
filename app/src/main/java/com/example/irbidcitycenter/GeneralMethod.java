package com.example.irbidcitycenter;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GeneralMethod {
    public  static  void showSweetDialog(Context context,int type,String title,String content){
        switch ( type){
            case 0://Error Type
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(title)
                        .setContentText(content)
                        .show();
                break;
            case 1://Error Type
                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(title)
                        .setContentText(content)
                        .show();
                break;
        }


    }
}
