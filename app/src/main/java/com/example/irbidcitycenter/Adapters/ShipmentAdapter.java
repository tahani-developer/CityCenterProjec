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

import com.example.irbidcitycenter.NewShipment;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.Models.Shipment;

import java.util.List;

public  class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ShipmentViewHolder > {
    private List<Shipment> list;
    Context shipment;

    public ShipmentAdapter(Context shipment, List<Shipment> list) {
        this.list = list;
        this.shipment = shipment;
    }

    @NonNull
    @Override
    public ShipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipmentrecycler, parent, false);
        return new ShipmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipmentViewHolder holder, int position) {
        holder.ponotxt.setText(list.get(position).getPoNo());
        holder.barcodetxt.setText(list.get(position).getBarcode());
        holder.boxnotxt.setText(list.get(position).getBoxNo());
        holder.qtytxt.setText(list.get(position).getQty()+"");
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

    class ShipmentViewHolder extends RecyclerView.ViewHolder{
        TextView ponotxt,boxnotxt,barcodetxt,qtytxt,rmovetxt;
        public ShipmentViewHolder(@NonNull View itemView) {
            super(itemView);

            ponotxt=itemView.findViewById(R.id.pono);
            boxnotxt=itemView.findViewById(R.id.boxno);
            barcodetxt=itemView.findViewById(R.id.barcode);
            qtytxt=itemView.findViewById(R.id.qty);
            rmovetxt=itemView.findViewById(R.id.remove);
            rmovetxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String tag= rmovetxt.getTag().toString();

                    final Dialog dialog = new Dialog(shipment);
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
