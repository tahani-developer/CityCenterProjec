package com.example.irbidcitycenter.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.net.http.LoggingEventHandler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Activity.Replacement;
import com.example.irbidcitycenter.RoomAllData;


import java.util.List;

import static com.example.irbidcitycenter.Activity.NewShipment.updateAdpapter;
import static com.example.irbidcitycenter.Activity.Replacement.replacementlist;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;
import static com.example.irbidcitycenter.ImportData.itemn;
import static com.example.irbidcitycenter.ImportData.listQtyZone;

public class ReplacementAdapter extends RecyclerView.Adapter<ReplacementAdapter.replacementViewHolder> {
    private List<ReplacementModel> list;
    Context context;
    String newqty;
    public RoomAllData my_dataBase;

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
        holder.from.setText(list.get(position).getFromName());
        holder.to.setText(list.get(position).getToName());
        holder.zone.setText(list.get(position).getZone());
        holder.itemcode.setText(list.get(position).getItemcode());
        Log.e("onBindViewHolder202020", "" + list.get(position).getRecQty());
        holder.qty.setText(list.get(position).getRecQty());
        holder.qty.setTag(position);
        holder.rmovetxt.setTag(position);
        holder.itemqty.setText(list.get(position).getQty());
        holder.itemqty.setEnabled(false);
        holder.qty.setEnabled(true);
    }

    public void removeItem(int position) {
        if (position < list.size()) {
            my_dataBase.replacementDao().deleteReplacement(list.get(position).getItemcode(), list.get(position).getFrom(), list.get(position).getTo());
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class replacementViewHolder extends RecyclerView.ViewHolder {
        TextView from, to, zone, itemcode, rmovetxt, itemqty;
        ;
        TextView qty;

        public replacementViewHolder(@NonNull View itemView) {
            super(itemView);
            my_dataBase = RoomAllData.getInstanceDataBase(context);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.to);
            zone = itemView.findViewById(R.id.zone);
            itemcode = (TextView) itemView.findViewById(R.id.itemcode);
            qty = itemView.findViewById(R.id.tblqty);
            // qty.setOnEditorActionListener(onEditAction);
            rmovetxt = itemView.findViewById(R.id.remove);
            itemqty = itemView.findViewById(R.id.itemqty);



   /* qty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.toString().length() != 0) {
                        try {
                            int pos=Integer.parseInt(qty.getTag().toString());
                            newqty=editable.toString();
                            if(!newqty.trim().equals("0"))
                            {

                            String zone = replacementlist.get(pos).getZone();
                            String itemcode = replacementlist.get(pos).getItemcode();




                           if(Integer.parseInt(newqty)<=Integer.parseInt(replacementlist.get(pos).getQty()))

                           {
                               replacementlist.get(pos).setRecQty(newqty);
                               int s= my_dataBase.replacementDao().updateQTY(replacementlist.get(pos).getItemcode(),replacementlist.get(pos).getRecQty());

                           }

                           else
                           {
                               showSweetDialog(context, 3, "", context.getResources().getString(R.string.notvaildqty));
                               replacementlist.get(pos).setRecQty( replacementlist.get(pos).getRecQty());
                               Replacement.updateAdpapter();
                           }


                            }else
                            {
                                replacementlist.get(pos).setRecQty( replacementlist.get(pos).getRecQty());
                                Replacement.updateAdpapter();
                                showSweetDialog(context, 3, "", context.getResources().getString(R.string.qtyerror3));
                            }
                        }catch (Exception e){}

                        //newqty = qty.getText().toString();

                    }
                }});*/


            rmovetxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String tag = rmovetxt.getTag().toString();

                    final Dialog dialog = new Dialog(context);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.delete_entry);
                    dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            removeItem(Integer.parseInt(tag));
                            dialog.dismiss();
                            Log.e("removeItemposition", Integer.parseInt(tag) + "");

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
                        case R.id.tblqty:

                            newqty = qty.getText().toString();
                            String zone = replacementlist.get(Integer.parseInt(qty.getTag().toString())).getZone();
                            String itemcode = replacementlist.get(Integer.parseInt(qty.getTag().toString())).getItemcode();
                            if (checkQtyValidateinRow(newqty, itemcode, zone)) {
                                // updateQTYOfZoneinRow(newqty,zone,itemcode);
                                replacementlist.get(Integer.parseInt(qty.getTag().toString())).setRecQty(newqty);
                            } else
                                showSweetDialog(context, 3, "", context.getResources().getString(R.string.notvaildqty));
                            break;
                    }

                }

                return true;
            }
        };
    }

    public boolean checkQtyValidateinRow(String newQty, String itemco, String zonecode) {
        Log.e("checkQtyValidateinRow", "heckQtyValidate");
        for (int i = 0; i < listQtyZone.size(); i++) {
            if (itemco.trim().equals(listQtyZone.get(i).getItemCode().trim())
                    && zonecode.trim().equals(listQtyZone.get(i).getZoneCode().trim())) {
                if (Integer.parseInt(newQty) <= Integer.parseInt(listQtyZone.get(i).getQty())) {
                    Log.e("checkQtyValidateinRow", listQtyZone.get(i).getQty() + " " + newQty);
                    return true;

                } else {
                    return false;


                }
            }
        }

        return false;
    }

    /*private void updateQTYOfZoneinRow(String newQty, String itemco,String zonecode) {
        String d="";
        Log.e("updateQTYOfZoneinRow()",d) ;
        for (int i = 0; i < listQtyZone.size(); i++) {
            if (itemco.trim().equals(listQtyZone.get(i).getItemCode().trim())
                    && zonecode.trim().equals(listQtyZone.get(i).getZoneCode().trim())) {
                listQtyZone.get(i).setQty(Integer.parseInt(listQtyZone.get(i).getQty()) - Integer.parseInt( newQty) + "");
                d = listQtyZone.get(i).getQty();
            }
        }

    }*/
}
