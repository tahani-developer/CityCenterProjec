package com.example.irbidcitycenter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irbidcitycenter.Models.ReplenishmentReverseModel;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import java.util.List;

public class RepReverseAdapter extends RecyclerView.Adapter<RepReverseAdapter.replacementViewHolder> {
    private List<ReplenishmentReverseModel> list;
    Context context;
    String newqty;
    public RoomAllData my_dataBase;

    public RepReverseAdapter(List<ReplenishmentReverseModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public replacementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.replacmentrevsersrecycler, parent, false);
        return new replacementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull replacementViewHolder holder, int position) {
        holder.from.setText(list.get(position).getFromName());
        holder.to.setText(list.get(position).getToName());

        holder.itemcode.setText(list.get(position).getItemcode());
        holder.qty.setText(list.get(position).getRecQty());
        holder.qty.setTag(position);
        holder.qty.setEnabled(true);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class replacementViewHolder extends RecyclerView.ViewHolder {
        TextView from, to, zone, itemcode, itemqty;
        ;
        TextView qty;

        public replacementViewHolder(@NonNull View itemView) {
            super(itemView);
            my_dataBase = RoomAllData.getInstanceDataBase(context);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.to);

            itemcode = (TextView) itemView.findViewById(R.id.itemcode);
            qty = itemView.findViewById(R.id.tblqty);


        }


    }


}
