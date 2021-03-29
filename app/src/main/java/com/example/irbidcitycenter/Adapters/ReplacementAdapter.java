package com.example.irbidcitycenter.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irbidcitycenter.NewShipment;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Replacement;


import java.util.List;

public class ReplacementAdapter extends RecyclerView.Adapter<ReplacementAdapter.replacementViewHolder > {
    private List<ReplacementModel> list;
    Context context;
    String newqty;
    public ReplacementAdapter(List<ReplacementModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public replacementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.replacmentrecycler, parent, false);
        return new replacementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull replacementViewHolder holder, int position) {
        holder.from.setText(list.get(position).getFrom());
        holder.to.setText(list.get(position).getTo());
        holder.zone.setText(list.get(position).getZone());
        holder.itemcode.setText(list.get(position).getItemcode());
        holder.qty.setText(list.get(position).getQty());
        holder.qty.setTag(position);
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

    class replacementViewHolder extends RecyclerView.ViewHolder{
          TextView from,to,zone,itemcode ,rmovetxt;;
          EditText qty;

        public replacementViewHolder(@NonNull View itemView) {
            super(itemView);
            from=itemView.findViewById( R.id.from);
            to=itemView.findViewById( R.id.to);
            zone=itemView.findViewById( R.id.zone);
            itemcode=itemView.findViewById( R.id.itemcode);
           qty=itemView.findViewById( R.id.qty);
            qty.setOnEditorActionListener(onEditAction);
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
        EditText.OnEditorActionListener onEditAction = new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_NULL) {
                    switch (textView.getId()) {
                        case R.id.qty:

                            newqty=qty.getText().toString();
                            Replacement.replacementlist.get(Integer.parseInt(qty.getTag().toString())).setQty(newqty);
                            break;
                    }

                }

                return true;
            }
        };
    }



}
