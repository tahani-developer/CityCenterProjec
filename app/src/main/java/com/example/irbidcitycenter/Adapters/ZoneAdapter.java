package com.example.irbidcitycenter.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
        holder.rmovetxt.setTag(position);
    }
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class ZoneViewHolder extends RecyclerView.ViewHolder{
        TextView zoneCode,itemCode,qty,rmovetxt;
        public ZoneViewHolder(@NonNull View itemView) {
            super(itemView);

            zoneCode=itemView.findViewById(R.id.zoneCode);
            itemCode=itemView.findViewById(R.id.itemCode);
            qty=itemView.findViewById(R.id.qtyZone);
            rmovetxt=itemView.findViewById(R.id.remove);
            rmovetxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String tag= rmovetxt.getTag().toString();

                    final Dialog dialog = new Dialog(context);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.delete_entry);
                    dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            removeItem(Integer.parseInt(tag));
                            dialog.dismiss();

                        }
                    });
                    dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();

                        }
                    });

                    dialog.show();
                    dialog.setCanceledOnTouchOutside(true);



                }
            });





        }
    }
}
