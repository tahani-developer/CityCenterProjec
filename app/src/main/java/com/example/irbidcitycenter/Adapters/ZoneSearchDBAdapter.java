package com.example.irbidcitycenter.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.irbidcitycenter.Activity.AddZone;
import com.example.irbidcitycenter.Activity.Replacement;
import com.example.irbidcitycenter.Activity.Stoketake;
import com.example.irbidcitycenter.Activity.ZoneReplacment;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import java.util.List;

import static com.example.irbidcitycenter.Activity.AddZone.flage3;

import static com.example.irbidcitycenter.Activity.AddZone.searchdialog;
import static com.example.irbidcitycenter.Activity.Replacement.DIRE_itemcode;
import static com.example.irbidcitycenter.Activity.Replacement.DIRE_itemcodeshow;
import static com.example.irbidcitycenter.Activity.Replacement.DIRE_qtyshow;
import static com.example.irbidcitycenter.Activity.Replacement.DIRE_zoneshow;
import static com.example.irbidcitycenter.Activity.Replacement.DZRE_ZONEcode;
import static com.example.irbidcitycenter.Activity.Replacement.DZRE_delete;
import static com.example.irbidcitycenter.Activity.Replacement.DZRE_zonecodeshow;
import static com.example.irbidcitycenter.Activity.Replacement.Re_searchdialog;
import static com.example.irbidcitycenter.Activity.Replacement.getqtyofDBzone;
import static com.example.irbidcitycenter.Activity.Stoketake.ST_delete;
import static com.example.irbidcitycenter.Activity.Stoketake.ST_itemcode;
import static com.example.irbidcitycenter.Activity.Stoketake.ST_itemcodeshow;
import static com.example.irbidcitycenter.Activity.Stoketake.ST_preQTY;
import static com.example.irbidcitycenter.Activity.Stoketake.ST_qtyshow;
import static com.example.irbidcitycenter.Activity.Stoketake.ST_qtyshows;
import static com.example.irbidcitycenter.Activity.Stoketake.ST_searchdialog;
import static com.example.irbidcitycenter.Activity.Stoketake.ST_zonecodeshow;
import static com.example.irbidcitycenter.Activity.Stoketake.ST_zoneshow;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.ZRDI_ZONEcode;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.ZRDI_itemcode;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.ZRDI_itemcodeshow;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.ZRDI_preQTY;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.ZRDI_qtyshow;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.ZRDI_zoneshow;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.ZR_DZqtyshow;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.ZR_DZzonebarecodehow;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.ZR_DZzonecode;

public class ZoneSearchDBAdapter extends BaseAdapter {
    private Context context; //context
    private List<String> items; //data source of the list adapter
    public RoomAllData my_dataBase;
    //public constructor
    public ZoneSearchDBAdapter (Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.zonenamelist, parent, false);
        }
        my_dataBase = RoomAllData.getInstanceDataBase(context);
        // get current item to be displayed
        String currentItem = (String) getItem(position);

        // get the TextView for item name and item description
        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.itemname);
        LinearLayout linearLayout= convertView.findViewById(R.id.linear);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flage3==0) {



                    try {
                        AddZone.zonebarecode.setText(items.get(position));
                        //set zone barecode
                        Log.e("here","hee");
                            AddZone.zonecode1.setText(items.get(position));


                        //set qty of zone
                        long sumqty = my_dataBase.zoneDao().GetQtyOfZone(items.get(position));
                     AddZone.qty1.setText(sumqty + "");


                        //set zonename
                        String zoneNam = my_dataBase.zoneDao().GetNameOfZone(items.get(position));
                            AddZone.zonename1.setText(zoneNam);

                        AddZone.searchdialog.dismiss();
                    } catch (Exception e) {

                    }
                }

                else   if(flage3==6)
                {

                    AddZone.DDzoneEDT.setText(items.get(position));
                    AddZone.DDitemcode.setText("");
                    AddZone.DI_zonecode.setText("");
                    AddZone.DIqty.setText("");
                    AddZone.DD_preQTY.setText("");
                    AddZone.DI_itemcode.setText("");
                    AddZone.DDitemcode.setEnabled(true);
                    AddZone.DDitemcode.requestFocus();

                    AddZone.searchdialog.dismiss();

                }
                else   if(flage3==4)
                {
                    DZRE_ZONEcode.setText(items.get(position));
                    DZRE_zonecodeshow.setText(DZRE_ZONEcode.getText().toString().trim());
                    getqtyofDBzone();

                    DZRE_delete.setEnabled(true);
                    Re_searchdialog.dismiss();
                }

                else     if(flage3==5)
                {
                    Replacement.DIRE_ZONEcode.setText(items.get(position));
                    DIRE_qtyshow.setText("");
                    DIRE_zoneshow.setText("");
                    DIRE_itemcodeshow.setText("");
                    DIRE_itemcode.setText("");
                    DIRE_itemcode.setEnabled(true);
                    DIRE_itemcode.requestFocus();
                    Re_searchdialog.dismiss();
                } else     if(flage3==7)
                {
                    Stoketake.ST_ZONEcode.setText(items.get(position));
                    ST_zonecodeshow.setText(items.get(position));
                    Stoketake.getqtyofDBzone();
                    ST_delete.setEnabled(true);
                    ST_searchdialog.dismiss();
                }
                else  if(flage3==8){
                    Stoketake.ST_ZONEcod.setText(items.get(position));
                    ST_qtyshows.setText("");
                    ST_preQTY.setText("");
                    ST_zoneshow.setText("");
                    ST_itemcodeshow.setText("");
                    ST_itemcode.setText("");
                    ST_itemcode.setEnabled(true);
                    ST_itemcode.requestFocus();
                    ST_searchdialog.dismiss();
                }
                else  if(flage3==9){
                    ZR_DZzonecode.setText(items.get(position));
                    //set zone barecode
                    ZR_DZzonebarecodehow.setText(  ZR_DZzonecode.getText().toString());

                    //set qty of zone
                    long sumqty = my_dataBase.zoneReplashmentDao().GetQtyOfZone(  ZR_DZzonecode.getText().toString());
                    ZR_DZqtyshow.setText(sumqty + "");
                    ZoneReplacment.searchdialog .dismiss();

                }
                else  if(flage3==10){
                    ZRDI_ZONEcode.setText(items.get(position));
                    ZRDI_itemcode.setText("");
                    ZRDI_itemcode.setEnabled(true);
                    ZRDI_itemcode.requestFocus();
                    ZRDI_preQTY.setText("");
                    ZRDI_itemcodeshow.setText("");
                    ZRDI_zoneshow.setText("");
                    ZRDI_qtyshow.setText("");
                    ZoneReplacment.searchdialog.dismiss();
                }
            }


        });
        //sets the text for item name and item description from the current item object
        textViewItemName.setText(currentItem);

        // returns the view for the current row
        return convertView;
    }
}