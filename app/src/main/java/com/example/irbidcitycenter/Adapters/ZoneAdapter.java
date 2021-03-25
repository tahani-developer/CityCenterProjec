package com.example.irbidcitycenter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.R;

import java.util.List;

public class ZoneAdapter extends RecyclerView.Adapter<ZoneAdapter.ZoneViewHolder > {
    private List<ZoneModel> list;
    Context context;

    public ZoneAdapter(Context context, List<ZoneModel> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ZoneAdapter.ZoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zone_row, parent, false);
        return new ZoneAdapter.ZoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZoneAdapter.ZoneViewHolder holder, int position) {
        holder.zoneCode.setText(list.get(position).getZoneCode());
        holder.itemCode.setText(list.get(position).getItemCode());
        holder.qty.setText(list.get(position).getQty()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ZoneViewHolder extends RecyclerView.ViewHolder{
        TextView zoneCode,itemCode,qty;
        public ZoneViewHolder(@NonNull View itemView) {
            super(itemView);

            zoneCode=itemView.findViewById(R.id.zoneCode);
            itemCode=itemView.findViewById(R.id.itemCode);
            qty=itemView.findViewById(R.id.qtyZone);




        }
    }
}
