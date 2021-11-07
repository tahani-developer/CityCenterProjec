package com.example.irbidcitycenter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.R;

import java.util.List;

public class ShipmentsReportAdapter extends BaseAdapter {
    private List<Shipment> list;
    Context context;
    TextView pono, itemcode, boxno, qty, isnew, itemname;

    public ShipmentsReportAdapter(List<Shipment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.shipmentreportrow, parent, false);
            pono = convertView.findViewById(R.id.textView_pono);
            itemcode = convertView.findViewById(R.id.textView_itemcode);
            boxno = convertView.findViewById(R.id.textView_boxno);
            qty = convertView.findViewById(R.id.textView_qty);
            isnew = convertView.findViewById(R.id.textView_isnew);
            itemname = convertView.findViewById(R.id.textView_itemname);


            pono.setText(list.get(i).getPoNo());
            itemcode.setText(list.get(i).getBarcode());
            boxno.setText(list.get(i).getBoxNo());
            qty.setText(list.get(i).getQty());
            isnew.setText(list.get(i).getIsNew());
            itemname.setText(list.get(i).getItemname());


        }
        return convertView;
    }
}
