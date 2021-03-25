package com.example.irbidcitycenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.irbidcitycenter.Adapters.ReplacementAdapter;
import com.example.irbidcitycenter.Models.ReplacementModel;

import java.util.ArrayList;
import java.util.List;

public class Replacement extends AppCompatActivity {
 Spinner fromSpinner,toSpinner;
    EditText  zone,itemcode,qty;
   String From,To,Zone,Itemcode,Qty;
   ReplacementModel replacement;
   ReplacementAdapter adapter;
   RecyclerView replacmentRecycler;
    private List<ReplacementModel>  replacementlist = new ArrayList<>();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement);

        fromSpinner=findViewById(R.id.fromspinner);
        toSpinner=findViewById(R.id.tospinner);
        zone=findViewById(R.id.zoneedt);
        itemcode=findViewById(R.id.itemcodeedt);
        qty=findViewById(R.id.qtyedt);
        replacmentRecycler=findViewById(R.id.replacmentRec);




        zone.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    itemcode.requestFocus();
                    return true;
                }
                return false;
            }
        });
       itemcode.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN)&&
                        (keyCode == KeyEvent.KEYCODE_ENTER))  {
                    // Perform action on key press
                    qty.requestFocus();
                    return true;
                }
                return false;
            }
        });
        qty.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))  {
                    // Perform action on key press
                    filldata();
                    return true;
                }
                return false;
            }
        });


    }
    private void filldata(){
        From= fromSpinner.getSelectedItem().toString();
        To= toSpinner.getSelectedItem().toString();
        Zone= zone.getText().toString();
        Itemcode= itemcode.getText().toString();
        Qty= qty.getText().toString();
        replacmentRecycler.setLayoutManager(new LinearLayoutManager(Replacement.this));
        replacement=new ReplacementModel (From,To,Zone,Itemcode,Qty);
        replacementlist.add(replacement);
        adapter =new ReplacementAdapter(replacementlist,Replacement.this);
        replacmentRecycler.setAdapter(adapter);

    }
}