package com.example.irbidcitycenter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.irbidcitycenter.Models.ZoneReplashmentModel;
import com.example.irbidcitycenter.R;

import java.util.List;

public class ZoneRepAdapter extends BaseAdapter {
    Context context;
    List<ZoneReplashmentModel> replashmentList;

    TextView FromZone,ToZone,Qty,Itemcode,RECqty;

    public ZoneRepAdapter(Context context, List<ZoneReplashmentModel> replashmentList) {
        this.context = context;
        this.replashmentList = replashmentList;
    }

    @Override
    public int getCount() {
        return replashmentList.size();
    }

    @Override
    public Object getItem(int i) {
        return replashmentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.zonerepraw, parent, false);
            FromZone=   convertView.findViewById(R.id.textView_fromzone);
            ToZone=   convertView.findViewById(R.id.textView_tozone);
            Qty =   convertView.findViewById(R.id.textView_qty1);
            Itemcode=   convertView.findViewById(R.id.textView_itemcodee);
            RECqty=convertView.findViewById(R.id.textView_RECqty);

            FromZone.setText(replashmentList.get(i).getFromZone());
            ToZone.setText(replashmentList.get(i).getToZone());
            Qty.setText(replashmentList.get(i).getQty());
            Itemcode.setText(replashmentList.get(i).getItemcode());
            RECqty.setText(replashmentList.get(i).getRecQty());
        }
        return convertView;
    }
}
