package com.example.irbidcitycenter.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irbidcitycenter.Models.UserPermissions;
import com.example.irbidcitycenter.Models.ZoneReplashmentModel;
import com.example.irbidcitycenter.Models.appSettings;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import java.util.List;

import static com.example.irbidcitycenter.Activity.Login.userPermissions;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;

public class ZonePerAdapterr  extends RecyclerView.Adapter<ZonePerAdapterr .ZoneRepViewHolder > {
    Context context;
    List<ZoneReplashmentModel> replashmentList;
    public RoomAllData my_dataBase;
    List<com.example.irbidcitycenter.Models.appSettings> appSettings;

    public ZonePerAdapterr(Context context, List<ZoneReplashmentModel> replashmentList) {
        this.context = context;
        this.replashmentList = replashmentList;
    }

    @NonNull
    @Override
    public ZoneRepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zonerepraw, parent, false);
        return new ZonePerAdapterr.ZoneRepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZoneRepViewHolder holder, int i) {
        holder.FromZone.setText(replashmentList.get(i).getFromZone());
        holder.ToZone.setText(replashmentList.get(i).getToZone());
        holder.Qty.setText(replashmentList.get(i).getQty());
        holder.Qty.setTag(i);
        Log.e("tag==", "Qty=" + holder.Qty.getTag().toString());
        Log.e("tag==", "i==" + i);
        holder.Itemcode.setText(replashmentList.get(i).getItemcode());
        holder.Itemcode.setTag(i);
        Log.e("tag==", "Itemcode=" + holder.Itemcode.getTag().toString());
        holder.RECqty.setText(replashmentList.get(i).getRecQty());
        if( userPermissions==null) getUsernameAndpass();
        if (userPermissions != null) {
            if(userPermissions.getMasterUser().equals("0")){
                if(userPermissions.getZoneRep_UpdateQty().equals("0"))
                {
                    Log.e("ca1", "i");
                    holder.RECqty.setEnabled(false);}
                else    {
                    Log.e("ca2", "i");
                    holder.RECqty.setEnabled(true);
                }

            }
            else   {  holder.RECqty.setEnabled(true);
                Log.e("ca3", "i");

            }



    }


        holder.Qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() != 0) {
                    int position = (int) holder.Itemcode.getTag();
                    String newqty = editable.toString().trim();
                    Log.e("newqty1===", newqty);
                    Log.e("newqty2===", "position=" + position);
                    Log.e("tag===", holder.Qty.getTag().toString());
                    Log.e("from===", replashmentList.get(position).getFromZone());
                    if (Long.parseLong(replashmentList.get(position).getRecQty()) >= (Long.parseLong(newqty))) {
                        replashmentList.get(position).setQty(newqty);
                        my_dataBase.zoneReplashmentDao().updateqtyReplashment(replashmentList.get(position).getFromZone(), replashmentList.get(position).getToZone(), replashmentList.get(position).getItemcode(), newqty);


                    } else {
                        showSweetDialog(context, 3, "", context.getString(R.string.notvaildqty2) + " " + context.getResources().getString(R.string.msg));
                        holder.Qty.setText("1");
                        my_dataBase.zoneReplashmentDao().updateqtyReplashment(replashmentList.get(position).getFromZone(), replashmentList.get(position).getToZone(), replashmentList.get(position).getItemcode(), newqty);

                    }
                    Log.e("newqty2===", newqty);
                    Log.e("tag===", holder.Qty.getTag().toString());
                    Log.e("from===", replashmentList.get(position).getFromZone());
                    //   Log.e("newqty2===", newqty);


                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return replashmentList.size();
    }

    public class ZoneRepViewHolder extends RecyclerView.ViewHolder {
        TextView FromZone, ToZone, RECqty, Itemcode;
        EditText Qty;
        CheckBox updateqty_chk;

        public ZoneRepViewHolder(@NonNull View convertView) {
            super(convertView);

            my_dataBase = RoomAllData.getInstanceDataBase(context);
            FromZone = convertView.findViewById(R.id.textView_fromzone);
            ToZone = convertView.findViewById(R.id.textView_tozone);
            Qty = convertView.findViewById(R.id.textView_qty1);
            Itemcode = convertView.findViewById(R.id.textView_itemcodee);
            RECqty = convertView.findViewById(R.id.textView_RECqty);




        }
        private void CheckUpdateQty_Permissitions() {

            if (userPermissions != null) {
                if(userPermissions.getMasterUser().equals("0")){
                    if(userPermissions.getZoneRep_UpdateQty().equals("0"))
                        Qty.setEnabled(false);
                    else   Qty.setEnabled(true);

                }
                else   Qty.setEnabled(true);

            }

        }
    }

    public void getUsernameAndpass() {


        String comNUm="";
        String Userno="";
         appSettings=my_dataBase.settingDao().getallsetting();
        if(appSettings.size()!=0)
        {
            Userno=  appSettings.get(0).getUserNumber();
            comNUm= appSettings.get(0).getCompanyNum();

        }

        userPermissions=my_dataBase.userPermissionsDao().getUserPermissions( Userno);



        //   Toast.makeText(Login.this,"This user is not recognized ",Toast.LENGTH_SHORT).show();


    }
}