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
import com.example.irbidcitycenter.Activity.NewShipment;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import java.util.List;

import static com.example.irbidcitycenter.Activity.NewShipment.DIsh_Itemcodeediteshow;
import static com.example.irbidcitycenter.Activity.NewShipment.DIsh_boxSearch;
import static com.example.irbidcitycenter.Activity.NewShipment.DIsh_boxcountediteshow;
import static com.example.irbidcitycenter.Activity.NewShipment.DIsh_boxedit;
import static com.example.irbidcitycenter.Activity.NewShipment.DIsh_poedit;
import static com.example.irbidcitycenter.Activity.NewShipment.DIsh_poediteshow;
import static com.example.irbidcitycenter.Activity.NewShipment.DIsh_preQTY;
import static com.example.irbidcitycenter.Activity.NewShipment.DIsh_qtyedit;
import static com.example.irbidcitycenter.Activity.NewShipment.DS_poedit;
import static com.example.irbidcitycenter.Activity.NewShipment.Db_boxedit;
import static com.example.irbidcitycenter.Activity.NewShipment.Dbsh_poedit;
import static com.example.irbidcitycenter.Activity.NewShipment.dailogNum;
import static com.example.irbidcitycenter.Activity.NewShipment.db_posearchdialog;
import static com.example.irbidcitycenter.Activity.NewShipment.deleteBoxdialog;
import static com.example.irbidcitycenter.Activity.NewShipment.filldeletePOdialogDATA;

public class D_SH_PosearchAdapter extends BaseAdapter {
    private Context context; //context
    private List<String> items; //data source of the list adapter
    public RoomAllData my_dataBase;

    //public constructor
    public D_SH_PosearchAdapter(Context context, List<String> items) {
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
                    inflate(R.layout.db_polist, parent, false);
        }

        // get current item to be displayed
        String currentItem = (String) getItem(position);

        // get the TextView for item name and item description
        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.item);
        textViewItemName.setText(currentItem);
        LinearLayout linearLayout = convertView.findViewById(R.id.polinear);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dailogNum == 0) {
                    DIsh_poedit.setText(items.get(position));
                    DIsh_poediteshow.setText("");
                    DIsh_boxcountediteshow.setText("");
                    DIsh_Itemcodeediteshow.setText("");
                    DIsh_preQTY.setText("");
                    DIsh_boxedit.setText("");
                    DIsh_qtyedit.setText("");
                    DIsh_boxSearch.setEnabled(true);
                    Log.e("dailogNum==0", "dailogNum==0");
                    DIsh_boxedit.setEnabled(true);
                    DIsh_boxedit.requestFocus();
                    db_posearchdialog.dismiss();
                } else if (dailogNum == 1) {
                    DS_poedit.setText(items.get(position));
                    Log.e("dailogNum==1", "dailogNum==1");
                    db_posearchdialog.dismiss();
                    filldeletePOdialogDATA();
                } else if (dailogNum == 2) {
                    Dbsh_poedit.setText(items.get(position));
                    Log.e("dailogNum==2", "dailogNum==2");
                    Db_boxedit.requestFocus();
                    db_posearchdialog.dismiss();
                }
            }
        });
        //sets the text for item name and item description from the current item object

        return convertView;
    }
}