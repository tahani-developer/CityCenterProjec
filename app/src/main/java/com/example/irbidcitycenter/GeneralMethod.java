package com.example.irbidcitycenter;

import android.content.Context;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GeneralMethod {
    public  Context myContext;
    public GeneralMethod(Context context) {
        this.myContext=context;
    }

    public  static  void showSweetDialog(Context context, int type, String title, String content){
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
    public boolean validateNotEmpty(EditText editText) {
        if(!editText.getText().toString().equals(""))
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
}
