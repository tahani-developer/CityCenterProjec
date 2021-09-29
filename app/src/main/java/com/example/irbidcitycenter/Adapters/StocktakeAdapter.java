package com.example.irbidcitycenter.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.irbidcitycenter.Models.StocktakeModel;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import java.util.ArrayList;
import java.util.List;

import static com.example.irbidcitycenter.Activity.Stoketake.getItemsSumQty;
import static com.example.irbidcitycenter.Activity.Stoketake.getStoreSumQty;
import static com.example.irbidcitycenter.Activity.Stoketake.getZoneSumQty;

public class StocktakeAdapter extends BaseAdapter {
    private Context context; //context
    private List<StocktakeModel> items; //data source of the list adapter
    public RoomAllData my_dataBase;
    public StocktakeAdapter(Context context, List<StocktakeModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent  ) {
        TextView zonecode,itemcode,store;
      EditText qty;
        my_dataBase = RoomAllData.getInstanceDataBase(context);
        CheckBox updateqty_chk;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.stocktakeraw, parent, false);
        }
        zonecode= convertView.findViewById(R.id.ROW_Zone);
        updateqty_chk= convertView.findViewById(R.id.updateqty_chk);
        itemcode= convertView.findViewById(R.id.ROW_itemCode);
        store= convertView.findViewById(R.id.ROW_store);
        qty= convertView.findViewById(R.id.ROW_recQty);
        qty.setEnabled(false);
        zonecode.setText( items.get( i).getZone());
        itemcode.setText(items.get( i).getItemOcode());
        store.setText( items.get( i).getStore());
        qty.setText( items.get( i).getQty());
       //qty.setTag(i);
        updateqty_chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updateqty_chk.isChecked()) qty.setEnabled(true);
                else qty.setEnabled(false);
            }
        });
        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                   String newqty = qty.getText().toString().trim();
                    my_dataBase.stocktakeDao().UpdateQty(newqty, items.get(i).getZone(),items.get(i).getItemOcode(),items.get(i).getStore());
                    Log.e( " newqty", newqty);

                    getItemsSumQty();
                    getZoneSumQty();
                    getStoreSumQty();

                }
            }
        });
        return convertView ;
    }
}
