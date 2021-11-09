package com.example.irbidcitycenter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irbidcitycenter.Activity.Replacement;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.ReplenishmentReverseModel;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import java.util.List;

public class StockInfoqtyAdapter extends RecyclerView.Adapter<StockInfoqtyAdapter.StockInfoqtyViewHolder> {
    private List<ReplacementModel> list;
    Context context;

    public StockInfoqtyAdapter(List<ReplacementModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public StockInfoqtyAdapter.StockInfoqtyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stockqtyrecycle, parent, false);
        return new StockInfoqtyAdapter.StockInfoqtyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockInfoqtyAdapter.StockInfoqtyViewHolder holder, int position) {
        holder.Stock_Code.setText(list.get(position).getFrom());
        holder.Stock_NameA.setText(list.get(position).getFromName());
        holder.QTY.setText(list.get(position).getRecQty());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class StockInfoqtyViewHolder extends RecyclerView.ViewHolder {
        TextView Stock_Code, Stock_NameA, QTY;

        public StockInfoqtyViewHolder(@NonNull View itemView) {
            super(itemView);
            // my_dataBase = RoomAllData.getInstanceDataBase(context);
            Stock_Code = itemView.findViewById(R.id.Stock_Code);
            Stock_NameA = itemView.findViewById(R.id.Stock_NameA);
            QTY = (TextView) itemView.findViewById(R.id.QTY);


        }


    }
}
