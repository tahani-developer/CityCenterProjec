package com.example.irbidcitycenter.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.net.http.DelegatingSSLSession;
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

import com.example.irbidcitycenter.Activity.NewShipment;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.RoomAllData;

import java.util.List;

import static com.example.irbidcitycenter.Activity.NewShipment.Flag1;
import static com.example.irbidcitycenter.Activity.NewShipment.PoQTY;
import static com.example.irbidcitycenter.Activity.NewShipment.localList;
import static com.example.irbidcitycenter.Activity.NewShipment.updateAdpapter;
import static com.example.irbidcitycenter.GeneralMethod.convertToEnglish;


public  class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ShipmentViewHolder > {
    private List<Shipment> list;
    Context shipment;
    public static String newqty, oldqty,olddif;
    public static int sum ;
    public RoomAllData my_dataBase;
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
        Log.e("getqty",list.get(position).getQty());
        holder.qtytxt.setText(list.get(position).getQty());

       holder.diff.setText(list.get(position).getDiffer());
        holder.rmovetxt.setTag(position);
        holder.qtytxt.setTag(position);
        newqty = list.get(position).getQty();
        oldqty = list.get(position).getQty();
    }

    public void removeItem(int position) {

        my_dataBase = RoomAllData.getInstanceDataBase(shipment);
        my_dataBase.shipmentDao().deleteshipment( list.get(position).getBarcode(),list.get(position).getPoNo(),list.get(position).getBoxNo());
        list.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ShipmentViewHolder extends RecyclerView.ViewHolder {
        TextView ponotxt, boxnotxt, barcodetxt, rmovetxt, diff;

        EditText qtytxt;

        public ShipmentViewHolder(@NonNull View itemView) {
            super(itemView);

            ponotxt = itemView.findViewById(R.id.pono);
            boxnotxt = itemView.findViewById(R.id.boxno);
            barcodetxt = itemView.findViewById(R.id.barcode);
            qtytxt = itemView.findViewById(R.id.tbl_qty);
           // qtytxt.setOnEditorActionListener(onEditAction);
           qtytxt.addTextChangedListener(new TextWatcher() {
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
                            oldqty = localList.get(Integer.parseInt(qtytxt.getTag().toString())).getQty();
                            olddif = localList.get(Integer.parseInt(qtytxt.getTag().toString())).getDiffer();
                            sum += Integer.parseInt(oldqty);
                            newqty =convertToEnglish( qtytxt.getText().toString());
                            Log.e("newqty", newqty);
                            localList.get(Integer.parseInt(qtytxt.getTag().toString())).setQty(newqty);

                            Log.e("filldataadapter", localList.get(Integer.parseInt(qtytxt.getTag().toString())).getDiffer());
                            localList.get(Integer.parseInt(qtytxt.getTag().toString())).setDiffer(String.valueOf(-1 * (Integer.parseInt(localList.get(Integer.parseInt(qtytxt.getTag().toString())).getPoqty()) - Integer.parseInt(newqty))));
                            Log.e("fillataadapter", localList.get(Integer.parseInt(qtytxt.getTag().toString())).getDiffer());
                            my_dataBase.shipmentDao(). updateQTY(localList.get(Integer.parseInt(qtytxt.getTag().toString())).getBarcode(),
                                    localList.get(Integer.parseInt(qtytxt.getTag().toString())).getPoNo(),
                                    localList.get(Integer.parseInt(qtytxt.getTag().toString())).getBoxNo(),
                                    localList.get(Integer.parseInt(qtytxt.getTag().toString())).getQty(),
                                    localList.get(Integer.parseInt(qtytxt.getTag().toString())).getDiffer());

                            updateAdpapter();
                        } catch (Exception e) {
                        }
                    }









                }
            });



            rmovetxt = itemView.findViewById(R.id.remove);
            diff = itemView.findViewById(R.id.differ);
            rmovetxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String tag = rmovetxt.getTag().toString();
                    final Dialog dialog = new Dialog(shipment);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.delete_entry);
                    dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //sum += Integer.parseInt(list.get(Integer.parseInt(tag)).getQty());
                            Log.e("on remove click",sum+"");
                            removeItem(Integer.parseInt(tag));
                            //PoQTY.setText(sum + "");
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
                        case R.id.tbl_qty:

                            oldqty =NewShipment.localList.get(Integer.parseInt(qtytxt.getTag().toString())).getQty();
                            olddif =NewShipment.localList.get(Integer.parseInt(qtytxt.getTag().toString())).getDiffer();
                            Log.e("oldqty", oldqty);
                            sum+=Integer.parseInt(oldqty);
                            newqty = qtytxt.getText().toString();
                            Log.e("newqty",  newqty);
                            NewShipment.localList.get(Integer.parseInt(qtytxt.getTag().toString())).setQty(newqty);
                            NewShipment.localList.get(Integer.parseInt(qtytxt.getTag().toString())).setDiffer(String.valueOf(-1*(sum-Integer.parseInt(newqty))));
                            sum-=Integer.parseInt(newqty);
                            my_dataBase.shipmentDao(). updateQTY(localList.get(Integer.parseInt(qtytxt.getTag().toString())).getBarcode(),
                                    localList.get(Integer.parseInt(qtytxt.getTag().toString())).getPoNo(),
                                    localList.get(Integer.parseInt(qtytxt.getTag().toString())).getBoxNo(),
                                    localList.get(Integer.parseInt(qtytxt.getTag().toString())).getQty(),
                                    localList.get(Integer.parseInt(qtytxt.getTag().toString())).getDiffer());
                            updateAdpapter();
                    }

                }

                return true;
            }
        };

    }

   /* public int getDifferentQTY() {//int sum=ImportData.POdetailslist.get(0).getReceivedqty())
        if (NewShipment.updateflage == 1) {
            sum-= Integer.parseInt(newqty);
            Log.e("getDifferentQTY()  sum", String.valueOf(sum));
            Log.e("getDifferentQTY()  newqty", newqty);

            PoQTY.setText(sum + "");
            return sum;

        } else {

            return sum;
        }


    }
    */


 /*  public int minusPOQTY(){
       sum-= Integer.parseInt(newqty);
       Log.e("getDifferentQTY()  sum", String.valueOf(sum));


       PoQTY.setText(sum + "");
       Log.e("getDifferentQTY()  newqty", String.valueOf(newqty));
       return sum;
   }**/






}