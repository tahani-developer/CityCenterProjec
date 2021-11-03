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

import com.example.irbidcitycenter.Models.StocktakeModel;
import com.example.irbidcitycenter.Models.UserPermissions;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import java.text.BreakIterator;
import java.util.List;

import static com.example.irbidcitycenter.Activity.Login.userPermissions;
import static com.example.irbidcitycenter.Activity.Stoketake.getItemsSumQty;
import static com.example.irbidcitycenter.Activity.Stoketake.getStoreSumQty;
import static com.example.irbidcitycenter.Activity.Stoketake.getZoneSumQty;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;

public  class StocktakeAdapterr extends RecyclerView.Adapter<StocktakeAdapterr.StocktakeViewHolder> {


    private Context context; //context
    private List<StocktakeModel> items; //data source of the list adapter
    public RoomAllData my_dataBase;
    List<com.example.irbidcitycenter.Models.appSettings> appSettings;
    public StocktakeAdapterr(Context context, List<StocktakeModel> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public StocktakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stocktakeraw, parent, false);
        return new StocktakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StocktakeViewHolder holder, int i) {
        holder.zonecode.setText( items.get( i).getZone());
        holder.itemcode.setText(items.get( i).getItemOcode());
        holder.store.setText( items.get( i).getStore());
        holder.    qty.setText( items.get( i).getQty());
        holder.    qty.setTag(i);
        Log.e("tag:" ,holder. qty.getTag().toString());
        if( userPermissions==null) getUsernameAndpass();
        if (userPermissions != null) {
            if(userPermissions.getMasterUser().equals("0")){
                if(userPermissions.getStockTake_UpdateQty().equals("0"))
                    holder.   qty.setEnabled(false);
                else     holder.  qty.setEnabled(true);

            }
            else   holder.    qty.setEnabled(true);

        }


        holder.    qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()!=0) {
                    String newqty =  holder.   qty.getText().toString().trim();
                    int pos= Integer.parseInt(holder.   qty.getTag().toString());
                    my_dataBase.stocktakeDao().UpdateQty(newqty, items.get(pos).getZone(), items.get(pos).getItemOcode(), items.get(pos).getStore());
                    Log.e(" newqty", newqty);

                    getItemsSumQty();
                    getZoneSumQty();
                    getStoreSumQty();

                }
                //  Log.e("tag:" , qty.getTag().toString());
            }
        });
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public class StocktakeViewHolder extends RecyclerView.ViewHolder {

        TextView zonecode,itemcode,store;
        EditText qty;

        public StocktakeViewHolder(@NonNull View   convertView) {
            super(convertView);

            my_dataBase = RoomAllData.getInstanceDataBase(context);
            CheckBox updateqty_chk;


            zonecode = convertView.findViewById(R.id.ROW_Zone);
            updateqty_chk = convertView.findViewById(R.id.updateqty_chk);
            itemcode = convertView.findViewById(R.id.ROW_itemCode);
            store = convertView.findViewById(R.id.ROW_store);
            qty = convertView.findViewById(R.id.ROW_recQty);
           // qty.setEnabled(false);

            //qty.setTag(i);
            updateqty_chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (updateqty_chk.isChecked()) qty.setEnabled(true);
                    else qty.setEnabled(false);
                }
            });


           ///






        }

    }
    private void CheckUpdateQty_Permissitions() {

       /* if (userPermissions != null) {
            if(userPermissions.getMasterUser().equals("0")){
                if(userPermissions.getStockTake_UpdateQty().equals("0"))
                    qty.setEnabled(false);
                else    qty.setEnabled(true);

            }
            else    qty.setEnabled(true);

        }*/

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
