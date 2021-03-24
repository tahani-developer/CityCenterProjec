package com.example.irbidcitycenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ShipmentViewHolder > {
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
        holder.pono.setText(list.get(position).getPoNo());
        holder.barcode.setText(list.get(position).getBarcode());
        holder.boxno.setText(list.get(position).getBoxNo());
        holder.qty.setText(list.get(position).getQty()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ShipmentViewHolder extends RecyclerView.ViewHolder{
        TextView pono,boxno,barcode,qty;
        public ShipmentViewHolder(@NonNull View itemView) {
            super(itemView);

            pono=itemView.findViewById(R.id.pono);
            boxno=itemView.findViewById(R.id.boxno);
            barcode=itemView.findViewById(R.id.barcode);
            qty=itemView.findViewById(R.id.qty);




        }
    }
}
