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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irbidcitycenter.Activity.NewShipment;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.RoomAllData;

import java.util.ArrayList;
import java.util.List;

import static com.example.irbidcitycenter.Activity.NewShipment.CheckFlag;
import static com.example.irbidcitycenter.Activity.NewShipment.Flag1;
import static com.example.irbidcitycenter.Activity.NewShipment.PoQTY;
import static com.example.irbidcitycenter.Activity.NewShipment.localList;
import static com.example.irbidcitycenter.Activity.NewShipment.updateAdpapter;
import static com.example.irbidcitycenter.GeneralMethod.convertToEnglish;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;


public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ShipmentViewHolder> {
    private List<Shipment> list;
    Context shipment;
    public static String newqty, oldqty, olddif;
    public static long sum;
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
        holder.qtytxt.setText(list.get(position).getQty());
        holder.diff.setText(list.get(position).getDiffer());
        holder.rmovetxt.setTag(position);
        holder.qtytxt.setTag(position);
        newqty = list.get(position).getQty();
        oldqty = list.get(position).getQty();

    }

    public void removeItem(int position) {


        my_dataBase.shipmentDao().deleteshipment(list.get(position).getBarcode(), list.get(position).getPoNo(), list.get(position).getBoxNo());
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
            my_dataBase = RoomAllData.getInstanceDataBase(shipment);
            ponotxt = itemView.findViewById(R.id.pono);
            boxnotxt = itemView.findViewById(R.id.boxno);
            barcodetxt = itemView.findViewById(R.id.barcode);
            qtytxt = itemView.findViewById(R.id.tbl_qty);
            // qtytxt.setOnEditorActionListener(onEditAction);


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
                            Log.e("on remove click", sum + "");
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
                            // qtytxt.selectAll();
                            //  newqty = convertToEnglish(qtytxt.getText().toString());
                            int position = Integer.parseInt(qtytxt.getTag().toString());
                            newqty = qtytxt.getText().toString().trim();
                            if (!newqty.trim().equals("0")) {
                                if (list.get(position).getIsNew().equals("0")) {// ITEM IS NOT NEW ==0
                                    oldqty = localList.get(position).getQty();
                                    olddif = localList.get(position).getDiffer();
                                    sum += Long.parseLong(oldqty);
                                    Log.e("newqty", newqty);
                                    localList.get(position).setQty(newqty);

                                    list.get(position).setQty(newqty);

                                    // Log.e("qtyvlau", localList.get(Integer.parseInt(qtytxt.getTag().toString())).getQty());
                                    //  Log.e("befordifferentvalue", localList.get(Integer.parseInt(qtytxt.getTag().toString())).getDiffer());
                                    String differ = String.valueOf(-1 * (Long.parseLong(localList.get(position).getPoqty()) - Long.parseLong(newqty)));
                                    localList.get(position).setDiffer(String.valueOf(-1 * (Long.parseLong(localList.get(position).getPoqty()) - Long.parseLong(newqty))));
                                    list.get(position).setDiffer(differ);
                                    diff.setText(differ);


                                    Log.e("afterdifferentvalue", differ);
//                                    updateAdpapter();
//
                                    my_dataBase.shipmentDao().updateQTY(
                                            localList.get(Integer.parseInt(qtytxt.getTag().toString())).getBarcode(),
                                            localList.get(Integer.parseInt(qtytxt.getTag().toString())).getPoNo(),
                                            localList.get(Integer.parseInt(qtytxt.getTag().toString())).getBoxNo(),
                                            localList.get(Integer.parseInt(qtytxt.getTag().toString())).getQty(),
                                            localList.get(Integer.parseInt(qtytxt.getTag().toString())).getDiffer());
//                                    updateData(localList);

                                    //updateAdpapter();
//                                notifyDataSetChanged();
                                }
//
//
                                else {//ITEM IS NEW IN PO ==1

                                    localList.get(Integer.parseInt(qtytxt.getTag().toString())).setQty(newqty);
                                    Log.e("elsetextlistner", localList.get(Integer.parseInt(qtytxt.getTag().toString())).getQty());
                                    list.get(position).setQty(newqty);

                                    my_dataBase.shipmentDao().updateQTY(
                                            localList.get(Integer.parseInt(qtytxt.getTag().toString())).getBarcode(),
                                            localList.get(Integer.parseInt(qtytxt.getTag().toString())).getPoNo(),
                                            localList.get(Integer.parseInt(qtytxt.getTag().toString())).getBoxNo(),
                                            localList.get(Integer.parseInt(qtytxt.getTag().toString())).getQty(),
                                            "1");

                                }
                            } else {

                                oldqty = localList.get(position).getQty();
                                localList.get(Integer.parseInt(qtytxt.getTag().toString())).setQty(oldqty);
                                list.get(position).setQty(oldqty);
                                qtytxt.setText(oldqty);
                                showSweetDialog(shipment, 3, "", shipment.getResources().getString(R.string.qtyerror3));
                            }

                        } catch (Exception e) {
                        }
                    }


                }
            });
        }


    }

    private void updateData(ArrayList<Shipment> localList) {

        this.list.clear();
        this.list.addAll(localList);
        notifyDataSetChanged();


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