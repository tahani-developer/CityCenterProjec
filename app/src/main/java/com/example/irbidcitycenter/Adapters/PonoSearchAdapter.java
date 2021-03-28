package com.example.irbidcitycenter.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irbidcitycenter.NewShipment;
import com.example.irbidcitycenter.R;

import java.util.ArrayList;

public class PonoSearchAdapter  extends RecyclerView.Adapter<PonoSearchAdapter.SearchViewHolder1 >{
    private ArrayList<String> list;
    Context shipment;

    public PonoSearchAdapter(Context shipment,ArrayList<String> list) {
        this.list = list;
        this.shipment = shipment;
    }

    @NonNull
    @Override
    public SearchViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pono_listitem, parent, false);
        return new PonoSearchAdapter .SearchViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder1 holder, int position) {
        holder.ponumber.setText(list.get(position));
        holder.ponumber.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SearchViewHolder1 extends RecyclerView.ViewHolder {

        TextView ponumber;
        LinearLayout linearLayout;


        public SearchViewHolder1(@NonNull View itemView) {

            super(itemView);
            ponumber = itemView.findViewById(R.id.po);

            ponumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(shipment);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.confirm_pono_insearch);


                    dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            NewShipment.ponotag = ponumber.getTag().toString();
                            NewShipment.colsedialog(2);
                            NewShipment.fillEdittext2();
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

