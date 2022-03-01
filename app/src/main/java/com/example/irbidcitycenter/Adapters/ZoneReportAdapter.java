package com.example.irbidcitycenter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.R;

import java.util.List;

public class ZoneReportAdapter extends BaseAdapter {
    private List<ZoneModel> list;
    Context context;
    public TextView zoneCode, itemCode, qty;

    public ZoneReportAdapter(final List<ZoneModel> list, final Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.zonereportrow, parent, false);

            zoneCode = convertView.findViewById(R.id.zone);
            itemCode = convertView.findViewById(R.id.item);
            qty = convertView.findViewById(R.id.qty);

            zoneCode.setText(list.get(position).getZoneCode());
            itemCode.setText(list.get(position).getItemCode());
            qty.setText(list.get(position).getQty());

        }
        return convertView;  }
}