package com.example.irbidcitycenter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.irbidcitycenter.Models.StocktakeModel;
import com.example.irbidcitycenter.R;

import java.util.List;

public class StockTakeRepAdapter extends BaseAdapter {
    TextView store, zone, itemcode, itemname, qty;

    List<StocktakeModel> stocktakeModels;
    Context context;

    public StockTakeRepAdapter(List<StocktakeModel> stocktakeModels, Context context) {
        this.stocktakeModels = stocktakeModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return stocktakeModels.size();
    }

    @Override
    public Object getItem(int i) {
        return stocktakeModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.stocktakereportrow, parent, false);
            store = convertView.findViewById(R.id.textView_store);
            zone = convertView.findViewById(R.id.textView_zone);
            itemcode = convertView.findViewById(R.id.textView_itemcode1);
            itemname = convertView.findViewById(R.id.textView_itemname1);
            qty = convertView.findViewById(R.id.textView_qty1);

            store.setText(stocktakeModels.get(i).getStore());
            zone.setText(stocktakeModels.get(i).getZone());
            itemcode.setText(stocktakeModels.get(i).getItemOcode());
            qty.setText(stocktakeModels.get(i).getQty());
            itemname.setText(stocktakeModels.get(i).getItemName());

        }
        return convertView;
    }
}
