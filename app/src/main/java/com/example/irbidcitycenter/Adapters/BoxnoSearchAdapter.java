package com.example.irbidcitycenter.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irbidcitycenter.Activity.NewShipment;
import com.example.irbidcitycenter.R;

import java.util.ArrayList;

import static com.example.irbidcitycenter.Activity.NewShipment.dialog1;
import static com.example.irbidcitycenter.Activity.NewShipment.dialog2;

public  class BoxnoSearchAdapter extends RecyclerView.Adapter<BoxnoSearchAdapter.SearchViewHolder >{
    private ArrayList<String> list;
    static Context shipment;


    public BoxnoSearchAdapter(Context shipment, ArrayList<String> list) {
        this.list = list;
        this.shipment = shipment;
    }

    @NonNull
    @Override
    public BoxnoSearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pono_listitem, parent, false);
        return new BoxnoSearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoxnoSearchAdapter.SearchViewHolder holder, int position) {
        holder.boxnumber.setText(list.get(position));
        holder.boxnumber.setTag(position);
    }

    @Override
    public int getItemCount() {

            return list.size();
}


    static class SearchViewHolder extends RecyclerView.ViewHolder{


        TextView boxnumber;
        LinearLayout linearLayout;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            boxnumber=itemView.findViewById(R.id.po);

            boxnumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog dialog = new Dialog(shipment);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.confirm_pono_insearch);
                    Button cancel=dialog.findViewById(R.id.cancel);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            dialog1.dismiss();
                        }
                    });

                   dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           NewShipment.boxnotag= boxnumber.getTag().toString();
                           NewShipment.colsedialog(1);
                           NewShipment.fillBoxEdittext();
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
