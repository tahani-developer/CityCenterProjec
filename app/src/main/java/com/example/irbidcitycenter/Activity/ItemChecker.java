package com.example.irbidcitycenter.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.irbidcitycenter.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ItemChecker extends AppCompatActivity {
public EditText itemcode;
    public TextView itemname,itemkind,saleprice,qty;
    Button back;
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
                        case R.id.ITC_itemcodeedt: {
                            itemname.setText("");
                            itemkind.setText("");
                            saleprice.setText("");
                            qty.setText("");
                        }
                        break;
                    }
            }
            return true;
        }

            return false;
}

};
    private void init() {
        itemcode=findViewById(R.id.ITC_itemcodeedt);
        itemname=findViewById(R.id.ITC_itemname);
        itemkind=findViewById(R.id.ITC_itemkind);
        saleprice=findViewById(R.id.ITC_saleprice);
        qty=findViewById(R.id.ITC_qty);
        back=findViewById(R.id.ITC_cancel);
        back.setOnClickListener(onClickListener);
        itemcode.setOnKeyListener(onKeyListener);
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
}