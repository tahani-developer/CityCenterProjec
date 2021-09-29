package com.example.irbidcitycenter.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.Adapters.ZoneSearchDBAdapter;
import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.ReplashmentLogs;
import com.example.irbidcitycenter.Models.Store;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.Models.appSettings;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;
import com.example.irbidcitycenter.ScanActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.example.irbidcitycenter.Adapters.ReplacementAdapter;
import com.example.irbidcitycenter.Models.ReplacementModel;

import java.util.ArrayList;
import java.util.List;


import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.irbidcitycenter.GeneralMethod.convertToEnglish;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;
import static com.example.irbidcitycenter.ImportData.Storelist;
import static com.example.irbidcitycenter.Activity.AddZone.validateKind;
import static com.example.irbidcitycenter.Activity.AddZone.validItem;
import static com.example.irbidcitycenter.ImportData.listAllZone;
import static com.example.irbidcitycenter.ImportData.listQtyZone;
import static com.example.irbidcitycenter.Activity.AddZone.flage3;
public class Replacement extends AppCompatActivity {
    boolean saved = false;
    int position;
    public static  int actvityflage=1;
    public String UserNo;
    public static TextView respon,qtyrespons;
    GeneralMethod generalMethod;
    Spinner fromSpinner, toSpinner;
    ExportData exportData;
    ImportData importData;
    public static EditText itemKintText1, poststateRE, DZRE_ZONEcode;
    public static EditText zone, itemcode;
    public static TextView qty;
    public  String deviceId="";
    public static TextView DZRE_zonecodeshow, DZRE_qtyshow;
    public static  List<ReplacementModel> DB_replist=new ArrayList<>();;
    public static  List<ReplacementModel> DB_replistcopy=new ArrayList<>();
    public static  List<ReplacementModel> reducedqtyitemlist=new ArrayList<>();
    public static Dialog   Re_searchdialog;
    EditText recqty;
    Button save;
    public int indexZone = -1;
    public int indexDBZone = 0,indexDBitem=0,indexOfReduceditem=0;
    public RoomAllData my_dataBase;
    String From, To, Zone, Itemcode, Qty;
    String FromNo, ToNo;
    ReplacementModel replacement;
    ReplacementModel replacementModel;
    static ReplacementAdapter adapter;
    FloatingActionButton add;
    RecyclerView replacmentRecycler;
    public static final int REQUEST_Camera_Barcode = 1;
    List<com.example.irbidcitycenter.Models.appSettings> appSettings;

   List<ReplacementModel>deleted_DBzone;
    private Dialog authenticationdialog;
    List<String> spinnerArray = new ArrayList<>();
    public static  Spinner spinner,spinner2;
    public static ArrayList<ReplacementModel> replacementlist = new ArrayList<>();
    public List<ReplacementModel> UnPostedreplacementlist = new ArrayList<>();
    public static ArrayList<Store> Storelistt = new ArrayList<>();
    public static  Button DZRE_delete;
    public static TextView DIRE_close_btn,
    DIRE_zoneSearch2,
    DIRE_preQTY,
    DIRE_itemcodeshow,
    DIRE_zoneshow,
    DIRE_qtyshow ;
    List<String>DB_store;
  List<String>DB_zone;
    EditText UsNa;
    public static EditText   DIRE_ZONEcode, DIRE_itemcode;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement);
        init();
        Storelist.clear();
        Storelist=  my_dataBase.storeDao().getall();

       if(Storelist.size()>0) {
           Log.e("sss","sss");
           for (int i = 0; i < Storelist.size(); i++) {
               spinnerArray.add(Storelist.get(i).getSTORENO() + "  " + Storelist.get(i).getSTORENAME());

           }
           fillSp();
       }

      else
     if( Storelist.size()==0)
          {getStors();
           Log.e("sss4","sss4");}

        zone.setEnabled(true);
        zone.requestFocus();
        itemcode.setEnabled(false);
        recqty.setEnabled(false);
        save.setEnabled(false);

        my_dataBase = RoomAllData.getInstanceDataBase(Replacement.this);
        UserNo=my_dataBase.settingDao().getUserNo();
//my_dataBase.replacementDao().deleteALL();
      /*  if(Storelistt.size()==0) {
            itemcode.setEnabled(false);
            zone.setEnabled(false);

        }*/
        //testcode
/*ReplacementModel replacementModel=new ReplacementModel();
        replacementModel.setRecQty("1");
        replacementModel.setFrom("1234");
        replacementModel.setTo("122222222222");
        replacementModel.setItemcode("tttttttttt");
        replacementModel.setQty("1");
        replacementModel.setZone("fffff");
        for (int i=0;i<50;i++)
      replacementlist.add(replacementModel);
        fillAdapter();*/


findViewById(R.id.nextZone).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        zone.setText("");
        zone.setEnabled(true);
        zone.requestFocus();
        itemcode.setText("");
        if(replacementlist!=null)
            if(replacementlist.size()>0)   replacementlist.clear();
        if(adapter!=null) adapter.notifyDataSetChanged();
    }
});

 try {

     fromSpinner.setSelection(0);
     toSpinner.setSelection(1);
     toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
             // your code her

             if (toSpinner.getSelectedItem().toString().equals(fromSpinner.getSelectedItem())) {
                 showSweetDialog(Replacement.this, 3, getResources().getString(R.string.samestore), "");
                 if (fromSpinner.getSelectedItemPosition() != 1) toSpinner.setSelection(1);
             }
         }

         @Override
         public void onNothingSelected(AdapterView<?> parentView) {
             // your code here
         }

     });
     fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
             // your code her

             if (toSpinner.getSelectedItem().toString().equals(fromSpinner.getSelectedItem())) {
                 showSweetDialog(Replacement.this, 3, getResources().getString(R.string.samestore), "");
                 if (toSpinner.getSelectedItemPosition() != 0) fromSpinner.setSelection(0);
                 //else
                 //   fromSP.setSelection(1);

             }
         }

         @Override
         public void onNothingSelected(AdapterView<?> parentView) {
             // your code here
         }

     });
 }catch (Exception e){}



    findViewById(R.id.Re_delete) .setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            OpenDeleteDailog();
        }
    });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromSpinner.setEnabled(true);
                toSpinner.setEnabled(true);

                // for (int i=0; i<replacementlist.size();i++)
                //     model.insert(replacementlist.get(i));

                /*if (replacementlist.size() > 0) {
                    for (int i = 0; i < replacementlist.size(); i++) {
                        replacementlist.get(i).setItemcode(convertToEnglish(replacementlist.get(i).getItemcode()));
                        replacementlist.get(i).setQty(convertToEnglish(replacementlist.get(i).getQty()));
                    }*/
                UnPostedreplacementlist=my_dataBase.replacementDao().getallReplacement();
                    exportData();
                    zone.setEnabled(true);
                    zone.requestFocus();

           zone.setText("");
               itemcode.setText("");
          //     qty.setText("");
            }
        });


        findViewById(R.id.Re_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(Replacement.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.confirm_title))
                        .setContentText(getResources().getString(R.string.messageExit))
                        .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (replacementlist.size() > 0) {

                                    new SweetAlertDialog(Replacement.this, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText(getResources().getString(R.string.confirm_title))
                                            .setContentText(getResources().getString(R.string.messageExit2))
                                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {


                                                    replacementlist.clear();
                                                    adapter.notifyDataSetChanged();
                                                    sweetAlertDialog.dismissWithAnimation();
                                                    finish();
                                                }
                                            })
                                            .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                }
                                            }).show();
                                }
                                else
                                {
                                    sweetAlertDialog.dismiss();
                                    finish();
                                }
                            }
                        }).setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }).show();


            }
        });


    }


    private void  OpenDeleteDailog(){


        final Dialog dialog = new Dialog(Replacement.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.deletelayout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        Button deletezone=dialog.findViewById(R.id.deletezone);
        Button deleteitem=dialog.findViewById(R.id.deleteitem);

        deletezone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticDailog(2);
            }
        });

        deleteitem.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {

                                              authenticDailog(1);
                                          }
                                      }
        );

        dialog.findViewById(R.id.dialogcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }
    private void authenticDailog(int enterflage) {


        authenticationdialog = new Dialog(Replacement.this);
        authenticationdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        authenticationdialog.setCancelable(false);
        authenticationdialog.setContentView(R.layout.authentication);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(authenticationdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        authenticationdialog.getWindow().setAttributes(lp);

        authenticationdialog.show();

        Button button= authenticationdialog.findViewById(R.id.authentic);
        TextView cancelbutton= authenticationdialog.findViewById(R.id.cancel2);
      UsNa= authenticationdialog.findViewById(R.id.username);
        UsNa.requestFocus();

        EditText pass= authenticationdialog.findViewById(R.id.pass);
        pass.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UsNa.getText().toString().trim().equals("123")&&pass.getText().toString().trim().equals("123"))
                {
                    if(enterflage==1)
                        openDeleteitemDailog();
                    else  if(enterflage==2)   openDeleteZoneDailog();


                }
                else {

                    if(!UsNa.getText().toString().trim().equals("123"))
                    {
                        UsNa.setError(getResources().getString(R.string.invalid_username));

                    }
                    else {

                    } pass.setError(getResources().getString(R.string.invalid_password));
                }
            }
        });
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticationdialog.dismiss();
            }
        });
        authenticationdialog.show();


    }
    private void openDeleteZoneDailog() {
        DB_replist.clear();
      DB_replist=my_dataBase.replacementDao().getallReplacement();
       DB_store=new ArrayList<>();
      DB_zone=new ArrayList<>();
      deleted_DBzone=new ArrayList<>();

        DB_store.clear();
        DB_zone.clear();
        deleted_DBzone.clear();
        final Dialog dialog = new Dialog(Replacement.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.deletere_zonediaolg);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
    spinner=dialog.findViewById(R.id.DZRE_storespinner);
        for(int i=0;i< DB_replist.size();i++)
          if(!DB_store.contains(DB_replist.get(i).getTo()))  DB_store.add(DB_replist.get(i).getTo());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, DB_store);
        spinner.setAdapter(adapter);
        dialog.show();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DZRE_ZONEcode.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
     DZRE_delete= dialog.findViewById(R.id.DZRE_delete);
        DZRE_delete.setEnabled(false);
        DZRE_ZONEcode =dialog.findViewById(R.id.DZRE_ZONEcode);
        DZRE_zonecodeshow =dialog.findViewById(R.id.DZRE_zonecodeshow);
        DZRE_qtyshow =dialog.findViewById(R.id.DZRE_qtyshow);
        dialog.findViewById(R.id.DZRE_zoneSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB_zone.clear();
                for(int i=0;i< DB_replist.size();i++) {
                    if (DB_replist.get(i).getTo().equals(spinner.getSelectedItem().toString())) {
                        if (!DB_zone.contains(DB_replist.get(i).getZone()))
                            DB_zone.add(DB_replist.get(i).getZone());
                    }

                }
                if (DB_zone.size() != 0) {
                    flage3 = 4;

                    searchZonecodeDailog(DB_zone );
                } else
                    Toast.makeText(Replacement.this, "No Data", Toast.LENGTH_SHORT).show();

            }
        });

        DZRE_ZONEcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i != KeyEvent.KEYCODE_ENTER){
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    if(!DZRE_ZONEcode.getText().toString().equals(""))
                    {
                        if(isExists(1,DZRE_ZONEcode.getText().toString().trim(),spinner.getSelectedItem().toString(),"")) {
                        DZRE_zonecodeshow.setText(DZRE_ZONEcode.getText().toString().trim());

           getqtyofDBzone();
                            DZRE_delete.setEnabled(true);
                        }
                        else
                        {
                            DZRE_ZONEcode.setText("");
                            DZRE_ZONEcode.setError("invalid");
                        }

                    }
                    else
                    {
                        DZRE_ZONEcode.requestFocus();
                    }
                    }
                    return true;  }
                return false;
            }
        });


        dialog.findViewById(R.id.DZRE_close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               authenticationdialog.dismiss();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.DZRE_dialogsave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleted_DBzone.size() > 0) {
                    checkLocalList(1);
                    addReplashmentLogs(2);
                    for (int i = 0; i < deleted_DBzone.size(); i++)
                        my_dataBase.replacementDao().deletezone(deleted_DBzone.get(i).getZone(), deleted_DBzone.get(i).getTo());
                    authenticationdialog.dismiss();
                    dialog.dismiss();
                    Toast.makeText(Replacement.this,"Done",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Replacement.this,"NO data changed",Toast.LENGTH_LONG).show();
                }
            }
        });
        DZRE_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(Replacement.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.confirm_title))
                        .setContentText(getResources().getString(R.string.deleteitem))
                        .setConfirmButton(getResources().getString(R.string.yes),
                                new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
;

                                        for(int i=0;i< DB_replist.size();i++)
                                       if(  spinner.getSelectedItem().toString().equals(DB_replist.get(i).getTo())&&
                                               DZRE_ZONEcode.getText().toString().trim().equals(DB_replist.get(i).getZone()))
                                       {

                                           deleted_DBzone.add(DB_replist.get(i));
                                           Log.e("DB_replistgetZone",DB_replist.get(i).getZone());
                                           Log.e("DB_replistgetstore",DB_replist.get(i).getTo());
                                           DB_replist.remove(i);
                                           i--;
                                       }
                                            DZRE_ZONEcode.setText("");
                                            DZRE_zonecodeshow.setText("");
                                            DZRE_qtyshow.setText("");

                                        sweetAlertDialog.dismiss();

                                    }}   ).setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                sweetAlertDialog.dismiss();
                                            }
                                        })
                                                .show();
            }
        });
        dialog.findViewById(R.id.DZRE_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( deleted_DBzone.size() > 0) {
                    new SweetAlertDialog(Replacement.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.returndata)).setConfirmButton(R.string.yes, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            DB_replist.clear();
                            DB_replist=my_dataBase.replacementDao().getallReplacement();

                deleted_DBzone.clear();
                DZRE_ZONEcode.setText("");
                DZRE_zonecodeshow.setText("");
                DZRE_qtyshow.setText("");
                 sweetAlertDialog.dismiss();

                        }
                    }).setCancelButton(R.string.no, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
                } else
                {
                    Toast.makeText(Replacement.this,getResources().getString(R.string.NODATA),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private boolean isExistsInReducedlist(){
        boolean f=false;



                for(int x=0;x< reducedqtyitemlist.size();x++)
            if(reducedqtyitemlist.get(x).getZone().equals(DIRE_ZONEcode.getText().toString().trim())&&
                    reducedqtyitemlist.get(x).getTo().equals(spinner2.getSelectedItem().toString())
                    && reducedqtyitemlist.get(x).getItemcode().equals(DIRE_itemcodeshow.getText().toString().trim()))
                {  f=true;
                    indexOfReduceditem =x;
                }
                  else
                {    f=false;


                    continue;}

            return f;
    }
    private boolean isExists(int flage,String zone,String store,String itemcode) {
        boolean f=false;
    if(flage==1)    {
        for(int i=0;i< DB_replist.size();i++)
            if(DB_replist.get(i).getZone().equals(zone)&&
                    DB_replist.get(i).getTo().equals(store)  ){
                f=true;
       indexDBZone =i;
                break;
            }
        else
            {    f=false;
            continue;}

   }
        if(flage==2){
            for(int i=0;i< DB_replist.size();i++)
                if(DB_replist.get(i).getZone().equals(zone)&&
                        DB_replist.get(i).getTo().equals(store)
                && DB_replist.get(i).getItemcode().equals(itemcode)){
                    f=true;
                    indexDBitem =i;
                    break;
                }
                else
                {    f=false;
                    continue;}
        }
        return f;
    }

    public static void getqtyofDBzone() {
        int sum=0;
        for(int x=0;x< DB_replist.size();x++)
            if(DB_replist.get(x).getTo().equals(spinner.getSelectedItem().toString())&&
                    DB_replist.get(x).getZone().equals(DZRE_ZONEcode.getText().toString().trim()) )
                sum+=Integer.parseInt(DB_replist.get(x).getRecQty());
        DZRE_qtyshow.setText(sum+"");
    }

    private void searchZonecodeDailog(List< String> Zonelist) {
        Re_searchdialog = new Dialog(Replacement.this);
        Re_searchdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Re_searchdialog.setCancelable(true);
        Re_searchdialog.setContentView(R.layout.dialog_zone_search);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom( Re_searchdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        Re_searchdialog.getWindow().setAttributes(lp);
        Button button= Re_searchdialog.findViewById(R.id.btn_send);
        button.setVisibility(View.GONE);
      ListView listView= Re_searchdialog.findViewById(R.id.listViewEngineering);
        ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(Replacement.this, Zonelist);
        listView.setAdapter(adapter);
        List<String> list=new ArrayList<>();

        EditText editText = Re_searchdialog.findViewById(R.id.searchZoneText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals("")){
                    list.clear();
                    for (int i=0;i< Zonelist.size();i++)
                        if( Zonelist.get(i).toUpperCase().contains(editable.toString().trim().toUpperCase()))
                            list.add( Zonelist.get(i));

                    ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(Replacement.this, list);
                    listView.setAdapter(adapter);
                }
                else
                {
                    ZoneSearchDBAdapter adapter = new ZoneSearchDBAdapter(Replacement.this, Zonelist);
                    listView.setAdapter(adapter);
                }
            }
        });


        Re_searchdialog.show();

    }

    private void openDeleteitemDailog() {
        DB_replist=my_dataBase.replacementDao().getallReplacement();
        copylist();
        DB_store=new ArrayList<>();
        DB_zone=new ArrayList<>();

        final Dialog Deleteitemdialog = new Dialog(Replacement.this);
        Deleteitemdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Deleteitemdialog.setCancelable(false);
        Deleteitemdialog.setContentView(R.layout.deleterep_itemsdialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Deleteitemdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        Deleteitemdialog.getWindow().setAttributes(lp);
        Deleteitemdialog.show();

       DIRE_close_btn=Deleteitemdialog.findViewById(R.id.DIRE_close_btn);
         DIRE_zoneSearch2=Deleteitemdialog.findViewById(R.id.DIRE_zoneSearch2);
     DIRE_preQTY=Deleteitemdialog.findViewById(R.id.DIRE_preQTY);
    DIRE_itemcodeshow=Deleteitemdialog.findViewById(R.id.DIRE_itemcodeshow);
   DIRE_zoneshow=Deleteitemdialog.findViewById(R.id.DIRE_zoneshow);
       DIRE_qtyshow =Deleteitemdialog.findViewById(R.id.DIRE_qtyshow);


       Button DIRE_dialogsave=Deleteitemdialog.findViewById(R.id.DIRE_dialogsave);
        Button DIRE_deleteitem=Deleteitemdialog.findViewById(R.id.DIRE_deleteitem);
        Button DIRE_cancel1=Deleteitemdialog.findViewById(R.id. DIRE_cancel1);

        DIRE_ZONEcode=Deleteitemdialog.findViewById(R.id.DIRE_ZONEcode);
        DIRE_itemcode=Deleteitemdialog.findViewById(R.id.DIRE_itemcode);
        DIRE_itemcode.setEnabled(false);
        DIRE_deleteitem.setEnabled(false);
        DIRE_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticationdialog.dismiss();
                Deleteitemdialog.dismiss();
            }
        });
        DIRE_zoneSearch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB_zone.clear();
                for(int i=0;i< DB_replist.size();i++) {
                    if (DB_replist.get(i).getTo().equals(spinner2.getSelectedItem().toString())) {
                        if (!DB_zone.contains(DB_replist.get(i).getZone()))
                            DB_zone.add(DB_replist.get(i).getZone());
                    }

                }



                if (DB_zone.size() != 0) {
                    flage3 = 5;

                    searchZonecodeDailog(DB_zone );
                } else
                    Toast.makeText(Replacement.this, "No Data", Toast.LENGTH_SHORT).show();

            }
        });

        spinner2=Deleteitemdialog.findViewById(R.id.DIRE_storespinner);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DIRE_ZONEcode.setText("");
                        DIRE_itemcode.setText("");
                DIRE_qtyshow.setText("");
                DIRE_zoneshow.setText("");
                DIRE_itemcodeshow.setText("");
                DIRE_preQTY.setText("");
                DIRE_ZONEcode.requestFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for(int i=0;i< DB_replist.size();i++)
            if(!DB_store.contains(DB_replist.get(i).getTo()))  DB_store.add(DB_replist.get(i).getTo());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, DB_store);
        spinner2.setAdapter(adapter);
        Deleteitemdialog.show();


        DIRE_ZONEcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i != KeyEvent.KEYCODE_ENTER){
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if(!DIRE_ZONEcode.getText().toString().equals(""))
                        {
                            if(isExists(1,DIRE_ZONEcode.getText().toString().trim(),spinner2.getSelectedItem().toString(),"")) {
                                DIRE_itemcode.setEnabled(true);
                                DIRE_itemcode.requestFocus();

                            }
                            else
                            { DIRE_ZONEcode.setText("");
                                DIRE_ZONEcode.setError("invalid");
                            }

                        }
                        else
                        {
                            DIRE_ZONEcode.requestFocus();
                        }
                    }
                    return true;  }
                return false;
            }
        });

        DIRE_itemcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i != KeyEvent.KEYCODE_ENTER){
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if(!DIRE_itemcode.getText().toString().equals(""))
                        {
                           if(isExists(2,DIRE_ZONEcode.getText().toString().trim(),spinner2.getSelectedItem().toString(),DIRE_itemcode.getText().toString().trim()))

                            {
                               int preqty= Integer.parseInt(getpreQty(spinner2.getSelectedItem().toString(),DIRE_ZONEcode.getText().toString().trim(),DIRE_itemcode.getText().toString().trim()));
                                DIRE_preQTY.setText(preqty+"");
                                int sumqty=Integer.parseInt(DB_replist.get( indexDBitem ).getRecQty());
                                if(sumqty>1){

                                    sumqty--;
                                    DB_replist.get( indexDBitem ).setRecQty( sumqty+"");
                                    DIRE_qtyshow.setText(DB_replist.get( indexDBitem ).getRecQty());
                                    DIRE_zoneshow.setText(DIRE_ZONEcode.getText().toString().trim());
                                    DIRE_itemcodeshow.setText(DIRE_itemcode.getText().toString().trim());

                                 if(isExistsInReducedlist())
                                    reducedqtyitemlist.get( indexOfReduceditem ).setRecQty( sumqty+"");
                                    else
                                        reducedqtyitemlist.add( DB_replist.get(indexDBitem));

                                    DIRE_itemcode.setText("");
                                    DIRE_itemcode.requestFocus();
                                }
                                else
                                {
                                    new SweetAlertDialog(Replacement.this, SweetAlertDialog.BUTTON_CONFIRM)
                                            .setTitleText(getResources().getString(R.string.confirm_title))
                                            .setContentText(getResources().getString(R.string.delete3))
                                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    DB_replist.get( indexDBitem ).setRecQty("0");
                                                    DIRE_preQTY.setText("1");
                                                    DIRE_qtyshow.setText("1");
                                                    DIRE_zoneshow.setText(DIRE_ZONEcode.getText().toString().trim());
                                                    DIRE_itemcodeshow.setText(DIRE_itemcode.getText().toString().trim());

                                                    if(isExistsInReducedlist())
                                                        reducedqtyitemlist.get( indexOfReduceditem ).setRecQty("0");
                                                    else
                                                        reducedqtyitemlist.add( DB_replist.get(indexDBitem));


                                                    DB_replist.remove(indexDBitem);
                                                    DIRE_itemcode.setText("");
                                                    DIRE_ZONEcode.setText("");
                                                    DIRE_qtyshow.setText("");
                                                    DIRE_zoneshow.setText("");
                                                    DIRE_itemcodeshow.setText("");
                                                    DIRE_preQTY.setText("");
                                                    sweetAlertDialog.dismiss();
                                                }
                                            })
                                            .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                }
                                            })
                                            .show();
                                }
                                DIRE_deleteitem.setEnabled(true);  }
                            else
                            { DIRE_itemcode.setText("");
                                DIRE_itemcode.setError("invalid");
                            }

                        }
                        else
                        {
                            DIRE_itemcode.requestFocus();
                        }
                    }
                    return true;  }
                return false;
            }
        });

        DIRE_dialogsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(reducedqtyitemlist.size()>0) {
                    checkLocalList(2);
                    addReplashmentLogs(1);
                    for (int i = 0; i < reducedqtyitemlist.size(); i++) {
                        if (reducedqtyitemlist.get(i).getRecQty().equals("0"))
                            my_dataBase.replacementDao().deleteDbReplacement(reducedqtyitemlist.get(i).getZone(), reducedqtyitemlist.get(i).getItemcode(), reducedqtyitemlist.get(i).getTo());
                        else
                            my_dataBase.replacementDao().updateDBQTY(reducedqtyitemlist.get(i).getRecQty(), reducedqtyitemlist.get(i).getZone(), reducedqtyitemlist.get(i).getItemcode(), reducedqtyitemlist.get(i).getTo());
                    }
                    Toast.makeText(Replacement.this,"Done",Toast.LENGTH_LONG).show();   }
                else{
                    Toast.makeText(Replacement.this,"NO data changed",Toast.LENGTH_SHORT).show();
                }

            authenticationdialog.dismiss();

                Deleteitemdialog.dismiss();

            }
        });

        DIRE_deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(Replacement.this, SweetAlertDialog.BUTTON_CONFIRM)
                        .setTitleText(getResources().getString(R.string.confirm_title))
                        .setContentText(getResources().getString(R.string.deleteitem))
                        .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                DB_replist.get( indexDBitem ).setRecQty("0");
                               if(reducedqtyitemlist.size()>0) {


                                       if (isExistsInReducedlist()) {

                                       reducedqtyitemlist.get(indexOfReduceditem).setRecQty("0");
                                   } else {

                                       reducedqtyitemlist.add(DB_replist.get(indexDBitem));
                                   }
                               }else
                                   reducedqtyitemlist.add(DB_replist.get(indexDBitem));


                                Log.e(" reducedqtyitemlist",""+ reducedqtyitemlist.size());
                                Log.e(" reducedqtyitemlist",""+ DB_replist.get(indexDBitem).getItemcode());
                                DB_replist.remove(indexDBitem);
                                DIRE_itemcode.setText("");
                                DIRE_ZONEcode.setText("");
                                DIRE_qtyshow.setText("");
                                DIRE_zoneshow.setText("");
                                DIRE_itemcodeshow.setText("");
                                DIRE_preQTY.setText("");
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        DIRE_cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(reducedqtyitemlist.size()>0)

                    new SweetAlertDialog(Replacement.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.returndata))
                            .setConfirmButton(getResources().getString(R.string.yes),
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {


                                            reducedqtyitemlist.clear();
                                            DIRE_ZONEcode.setText("");
                                            DIRE_itemcode.setText("");
                                            DIRE_qtyshow.setText("");
                                            DIRE_zoneshow.setText("");
                                            DIRE_itemcodeshow.setText("");
                                            DB_replist.clear();
                                            DB_replist=my_dataBase.replacementDao().getallReplacement();
                                            sweetAlertDialog.dismiss();


                                        }
                                    }).setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                    sweetAlertDialog.dismiss();

                                                    // FinishProcessFlag=0;
                                                }
                                            })
                                                    .show();
                else
                    Toast.makeText(Replacement.this,getResources().getString(R.string.NODATA),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addReplashmentLogs(int flage) {
    if(flage==1) {
        ReplashmentLogs replashmentLogs = new ReplashmentLogs();
        for (int i = 0; i < reducedqtyitemlist.size(); i++) {
            replashmentLogs.setStore(reducedqtyitemlist.get(i).getTo());
            replashmentLogs.setZoneCode(reducedqtyitemlist.get(i).getZone());
            replashmentLogs.setNewQty(reducedqtyitemlist.get(i).getRecQty());
            String preqty = getpreQty(reducedqtyitemlist.get(i).getTo(), reducedqtyitemlist.get(i).getZone(), reducedqtyitemlist.get(i).getItemcode());
            replashmentLogs.setPreQty(preqty);
            replashmentLogs.setItemCode(reducedqtyitemlist.get(i).getItemcode());
            replashmentLogs.setLogsDATE(generalMethod.getCurentTimeDate(1));
            replashmentLogs.setLogsTime(generalMethod.getCurentTimeDate(2));
            replashmentLogs.setUserNO( UsNa.getText().toString());
            my_dataBase.replashmentLogsDao().insert(replashmentLogs);
        }
    }
        else{
        ReplashmentLogs replashmentLogs = new ReplashmentLogs();
        Log.e("deleted_DBzone",deleted_DBzone.size()+"");
        for (int i = 0; i < deleted_DBzone.size(); i++) {
            replashmentLogs.setStore(deleted_DBzone.get(i).getTo());
            replashmentLogs.setZoneCode(deleted_DBzone.get(i).getZone());
            replashmentLogs.setNewQty("0");
            replashmentLogs.setPreQty(deleted_DBzone.get(i).getRecQty());
            replashmentLogs.setItemCode(deleted_DBzone.get(i).getItemcode());
            replashmentLogs.setLogsDATE(generalMethod.getCurentTimeDate(1));
            replashmentLogs.setLogsTime(generalMethod.getCurentTimeDate(2));
            replashmentLogs.setUserNO(UsNa.getText().toString());
            my_dataBase.replashmentLogsDao().insert(replashmentLogs);
        }

            }


        }

    private void copylist() {
        DB_replistcopy.clear();
        List<String> zon=new ArrayList<>();
        List<String> item=new ArrayList<>();
        List<String> qtys=new ArrayList<>();
        List<String> store=new ArrayList<>();
        for (int i = 0; i < DB_replist.size(); i++)
        {
            zon.add(DB_replist.get(i).getZone());
            item.add(DB_replist.get(i).getItemcode());
            qtys.add(DB_replist.get(i).getRecQty());
            store.add(DB_replist.get(i).getTo());
        }

        for (int i = 0; i <  zon.size(); i++)
        {
          ReplacementModel replacementModel=new ReplacementModel();
            replacementModel.setTo(store.get(i));
            replacementModel.setZone(zon.get(i));
            replacementModel.setItemcode(item.get(i));
            replacementModel.setRecQty(qtys.get(i));
            DB_replistcopy.add(replacementModel);
        }
    }

    private String getpreQty(String store,String zone, String itemcode) {
        Log.e("getpreQty",zone+"\t"+itemcode);
        Log.e("zonecopysize",DB_replistcopy.size()+"");
        String  z="";
        for(int i=0;i<DB_replistcopy.size();i++) {
            if (zone.equals(DB_replistcopy.get(i).getZone()) && itemcode.equals(DB_replistcopy.get(i).getItemcode())
            &&store.equals(DB_replistcopy.get(i).getTo())) {
                Log.e("getpreQty2", DB_replistcopy.get(i).getZone() + "\t" +DB_replistcopy.get(i).getItemcode());
                Log.e("getpreQty3", DB_replistcopy.get(i).getRecQty());
                Log.e("index", i+"");
                z=DB_replistcopy.get(i).getRecQty();
                break;
            }
            else{
                z="";
            }

        }
        return  z;   }
    private void checkLocalList(int flage ) {

        if (flage == 1) {
            if (replacementlist != null) {
                if (replacementlist.size() > 0) {

                    Log.e("checkLocalList", replacementlist.size() + "");
                    for (int i = 0; i < deleted_DBzone.size(); i++) {
                        for (int j = 0; j < replacementlist.size(); j++) {

                            if (replacementlist.get(j).getTo().equals(deleted_DBzone.get(i).getTo()) &&
                                    replacementlist.get(j).getZone().equals(deleted_DBzone.get(i).getZone())
                                   ) {



                                replacementlist.remove(j);
                                    if (adapter != null) adapter.notifyDataSetChanged();


                            }
                        }
                    }
                }

            }
        } else if (flage == 2) {
            Log.e("flage", flage + "");
            if (replacementlist!= null) {
                if (replacementlist.size() > 0) {
                    for (int i = 0; i < reducedqtyitemlist.size(); i++) {
                        for (int j = 0; j < replacementlist.size(); j++) {
                            Log.e("LocalListsize", replacementlist.size() + "");
                            if (replacementlist.get(j).getTo().equals(reducedqtyitemlist.get(i).getTo()) &&
                                    replacementlist.get(j).getZone().equals(reducedqtyitemlist.get(i).getZone())
                        &&  replacementlist.get(j).getItemcode().equals(reducedqtyitemlist.get(i).getItemcode()))

                                if ( reducedqtyitemlist.get(i).getRecQty().equals("0"))
                                    replacementlist.remove(j);
                          else
                                replacementlist.get(j).setRecQty(reducedqtyitemlist.get(i).getRecQty());
                            if (adapter != null) adapter.notifyDataSetChanged();
                        }
                    }

                }
            }
        }




    }


    public boolean CaseDuplicates(ReplacementModel replacement) {
        boolean flag = false;
        if (replacementlist.size() != 0)
            for (int i = 0; i < replacementlist.size(); i++) {

                if (convertToEnglish(replacementlist.get(i).getZone()).equals(replacement.getZone())
                        && convertToEnglish(replacementlist.get(i).getItemcode()).equals(replacement.getItemcode())
                        &&replacementlist.get(i).getFrom().equals(replacement.getFrom())
                &&replacementlist.get(i).getTo().equals(replacement.getTo()))
                {

                   position=i;
                    flag = true;
                    break;

                } else
                    flag = false;
                continue;
            }

        return flag;

    }


    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    private void showExitDialog() {
        new SweetAlertDialog(Replacement.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.confirm_title))
                .setContentText(getResources().getString(R.string.messageExit))
                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (replacementlist.size() > 0) {

                            new SweetAlertDialog(Replacement.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.confirm_title))
                                    .setContentText(getResources().getString(R.string.messageExit2))
                                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {


                                            replacementlist.clear();
                                            adapter.notifyDataSetChanged();
                                            sweetAlertDialog.dismissWithAnimation();
                                            finish();
                                        }
                                    })
                                    .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    }).show();
                        }
                        else
                        {
                            sweetAlertDialog.dismiss();
                            finish();
                        }
                    }
                }).setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        }).show();
    }

    private void getStors() {
        actvityflage=1;
        importData.getStore();
    }

    public void exportData() {
        try {

            exportData.exportReplacementList(UnPostedreplacementlist);
        }catch (Exception e){

            // test
        }

    }

    private void init() {

        my_dataBase = RoomAllData.getInstanceDataBase(Replacement.this);
        replacementlist.clear();
        poststateRE = findViewById(R.id.poststatRE);
        MainActivity.setflage = 1;
        itemKintText1 = findViewById(R.id.itemKintTextRE);
        exportData = new ExportData(Replacement.this);
        importData = new ImportData(Replacement.this);
        listAllZone.clear();
        importData.getAllZones();
        listQtyZone.clear();
        appSettings=new ArrayList();
        try {
            appSettings=my_dataBase.settingDao().getallsetting();
        }
        catch (Exception e){}
        fromSpinner = findViewById(R.id.fromspinner);
        toSpinner = findViewById(R.id.tospinner);
        zone = findViewById(R.id.zoneedt);
        itemcode = findViewById(R.id.itemcodeedt);
        qty = findViewById(R.id.qty);
        recqty = findViewById(R.id.qtyedt);
        replacmentRecycler = findViewById(R.id.replacmentRec);
        save = findViewById(R.id.save);
        respon = findViewById(R.id.respons);
        qtyrespons = findViewById(R.id.qtyrespon);
      //  recqty.setOnEditorActionListener(onEditAction);
        itemcode.setOnEditorActionListener(onEditAction);
   zone.setOnEditorActionListener(onEditAction);
        generalMethod = new GeneralMethod(Replacement.this);


       zone.setOnKeyListener(onKeyListener);
        itemcode.setOnKeyListener(onKeyListener);








        //importData.getAllZones();
        respon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {

                    if (editable.toString().equals("no data")) {

                        respon.setText("");
                    } else {
                        if (editable.toString().equals("fill")) {
                            for (int i = 0; i < Storelist.size(); i++)
                            {   spinnerArray.add(Storelist.get(i).getSTORENO() + "  " + Storelist.get(i).getSTORENAME());
                            my_dataBase.storeDao().insert(Storelist.get(i));}
                        }
                        fillSp();

                        zone.requestFocus();
                        Log.e("afterTextChanged", "" + editable.toString());

                    }

                }

            }
        });
        itemKintText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {
                    if (editable.toString().equals("NOTEXIST")) {
                        validateKind = false;
                        recqty.setEnabled(false);
                        showSweetDialog(Replacement.this, 3, "This Item Not Exist", "");
                        itemKintText1.setText("");
                    } else {
                        validateKind = false;
                        Log.e("afterTextChanged", "" + editable.toString());
                        compareItemKind(editable.toString().trim());
                    }

                }

            }
        });
        poststateRE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() != 0) {
                    if (editable.toString().trim().equals("exported")) {
                        { //saveData(1);
                            my_dataBase.replacementDao().updateReplashmentPosted();
                            showSweetDialog(Replacement.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                        saved = true;
                    replacementlist.clear();
                    fillAdapter();
                      adapter.notifyDataSetChanged();
                        }
                    }
                    else if (editable.toString().trim().equals("not")) {
                        saved = true;
                        //saveData(0);
                      replacementlist.clear();
                    fillAdapter();
                      adapter.notifyDataSetChanged();
                    }
                    else{
                        replacementlist.clear();
                        fillAdapter();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        qtyrespons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() != 0) {
                    Log.e("afterTextChanged",qtyrespons.getText().toString());
                    if (qtyrespons.getText().toString().equals("QTY"))
                    {
                       if(Integer.parseInt(listQtyZone.get(0).getQty())>0)
                        {
                            try {


                                filldata();
                                Replacement.qty.setText("");


                            } catch (Exception e) {
                                Log.e("Exception", e.getMessage());
                            }
                            save.setEnabled(true);
                            recqty.setText("1");


                            zone.setEnabled(false);
                            itemcode.setText("");
                            itemcode.requestFocus();

                            recqty.setEnabled(false);
                        }
                        else
                        {
                            Toast.makeText(Replacement.this,getResources().getString(R.string.notvaildqty),Toast.LENGTH_SHORT).show();
                            itemcode.setText("");
                            itemcode.requestFocus();
                        }



                    }
                    else
                    {
                        showSweetDialog(Replacement.this, 3, "", getResources().getString(R.string.existsBARCODE));
                        recqty.setEnabled(false);

                        itemcode.setText("");
                        itemcode.requestFocus();

                    }
                }
            }
        });

        if(appSettings.size()!=0)
        {
            deviceId=  appSettings.get(0).getDeviceId();
            Log.e("appSettings","+"+deviceId);

        }


    }

    private void fillSp() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(1);
        Log.e("sss1","sss1");
    }

    private void compareItemKind(String itemKind) {

        validItem = false;
        if (listAllZone.get(indexZone).getZONETYPE().equals(itemKind)) {
            validItem = true;
            itemcode.setEnabled(false);
            recqty.setEnabled(true);
            recqty.requestFocus();
        } else {
            recqty.setEnabled(false);
            showSweetDialog(Replacement.this, 0, "Item Kind Not Match To Zone Type", "");
        }
        itemKintText1.setText("");
    }

    public void ScanCode(View view) {
        switch (view.getId()) {
            case R.id.scanZoneCode:
                readBarcode(4);

                break;
            case R.id.scanItemCode:
                readBarcode(5);

                break;
        }
    }



    TextView.OnKeyListener onKeyListener=new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            Log.e("keyEvent.getAction()",keyEvent.getAction()+"");


            if (i == KeyEvent.KEYCODE_BACK) {
                onBackPressed();

            }

            if (i != KeyEvent.KEYCODE_ENTER) {

                { if (keyEvent.getAction() == KeyEvent.ACTION_UP)
            switch (view.getId()) {
                case R.id.zoneedt:
                    Log.e("ZONEHERE",zone.getText().toString()+"");
                    if(!zone.getText().toString().equals(""))
                        if(searchZone(zone.getText().toString().trim()))
                        {
                            Log.e("zone",zone.getText().toString());
                        }
                        else {
                            zone.setError("Invalid");
                            Log.e("elsezone",zone.getText().toString());
                            zone.setText("");}
                    else
                        zone.requestFocus();
            break;
                case R.id.itemcodeedt:

                  if(!itemcode.getText().toString().equals(""))
                  {


                      fromSpinner.setEnabled(false);
                      toSpinner.setEnabled(false);
                      Log.e( "itemcodeedt ",itemcode.getText().toString()+"");

                      From = fromSpinner.getSelectedItem().toString();
                      To = toSpinner.getSelectedItem().toString();
                      FromNo=From.substring(0,From.indexOf(" "));
                      ToNo=To.substring(0,To.indexOf(" "));
                      Zone = convertToEnglish(zone.getText().toString().trim());
                      Itemcode = convertToEnglish(itemcode.getText().toString().trim());


                      replacement = new ReplacementModel();
                      replacement.setFrom( FromNo);
                      replacement.setTo(ToNo);
                      replacement.setZone(Zone);
                      replacement.setItemcode(Itemcode);
                      replacement.setFromName(From);
                      replacement.setToName(To);
                      replacement.setDeviceId(deviceId);

                      zone.setEnabled(false);
                    ReplacementModel replacementModel=my_dataBase.replacementDao().getReplacement(Itemcode,Zone, FromNo,ToNo);
                    if(replacementModel!=null) {
                        if (!CaseDuplicates(replacementModel))
                            replacementlist.add(replacementModel);
                        save.setEnabled(true);
                    }


                          if (CaseDuplicates(replacement)) {
                              Log.e("replacementFrom",replacement.getTo());
                              Log.e("AddInCaseDuplicates","AddInCaseDuplicates");
                              //update qty in Duplicate case
                              int sum=Integer.parseInt(replacementlist.get(position).getRecQty()) + Integer.parseInt("1");
                              Log.e("aaasum ",sum+"");

                              if(sum<=Integer.parseInt(replacementlist.get(position).getQty()))
                              {
                                  replacementlist.get(position).setRecQty((sum+""));
                                  my_dataBase.replacementDao().updateQTY(replacementlist.get(position).getItemcode(),replacementlist.get(position).getRecQty());

                                  zone.setEnabled(false);
                                  itemcode.setText("");
                                  itemcode.requestFocus();
                                  fillAdapter();
                                  Log.e("heree","here2");
                              }
                              else
                              {   showSweetDialog(Replacement.this, 3, "", getResources().getString(R.string.notvaildqty));

                                  fillAdapter();
                                  zone.setEnabled(false);
                                  itemcode.setText("");
                                  itemcode.requestFocus();
                              }


                          }


                          else
                          {
                              Log.e("not in db","not in db");
                              ZoneReplacment.fromZoneRepActivity=0;
                              importData.getQty();

                          }
                      }

                  else
                      itemcode.requestFocus();
                    break;
        }  return true;
                }}
                return false;

    }};




    EditText.OnEditorActionListener onEditAction = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                    || i == EditorInfo.IME_NULL) {
                switch (textView.getId()) {
                    case R.id.zoneedt:
                        Log.e("ZONEHERE",zone.getText().toString()+"");
                        if(!zone.getText().toString().equals(""))
                            if(searchZone(zone.getText().toString().trim()))
                            {
                                Log.e("zone",zone.getText().toString());
                            }
                            else {
                                zone.setError("Invalid");
                                Log.e("elsezone",zone.getText().toString());
                                zone.setText("");}
                        else
                            zone.requestFocus();
                        break;

                    case R.id.qtyedt: {


                  if(Integer.parseInt(recqty.getText().toString())==0)
                           recqty.setText("1");


                     //       if (checkQtyValidate(recqty.getText().toString()))
                       {
                                filldata();
//                                Replacement.qty.setText("");
                                save.setEnabled(true);
                                zone.setText("");
                                itemcode.setText("");
                                recqty.setText("1");
                                zone.setEnabled(true);
                                zone.requestFocus();
                                recqty.setEnabled(false);
                                break;
                            }
                            //else
                          //      showSweetDialog(Replacement.this, 3, "", getResources().getString(R.string.notvaildqty));


                    }


                }



            }

            return true;
        }
    };

    private void validateItemKind(String itemNo) {
        validateKind = true;
        // http://localhost:8082/IrGetItemData?CONO=290&ITEMCODE=28200152701
        importData.getKindItem(itemNo);
    }
   /* private boolean searchZone(String codeZone) {
        Log.e("search", " searchZone1");
        Log.e("listAllZone", "" + listAllZone.size());
        zone.setError(null);
        for (int i = 0; i < listAllZone.size(); i++) {
            if (listAllZone.get(i).getZoneCode().equals(codeZone)) {
                Log.e(" searchZone", " searchZone");
               return true;

            }
        }
   return false; }*/
    private boolean searchZone(String codeZone) {
        Log.e("search",codeZone);
        Log.e("search"," searchZone1");
        Log.e("listAllZone",""+listAllZone.size());
        zone.setError(null);
        for (int i = 0; i < listAllZone.size(); i++) {

            if (listAllZone.get(i).getZoneCode().equals(codeZone)) {
                itemcode.setEnabled(true);
                itemcode.requestFocus();
                zone.setEnabled(false);
                return true;

            }
        }

return  false;

    }
    private String getusernum() {
        return "";
    }
    private void filldata() {

        Qty = recqty.getText().toString().trim();
        if (Zone.toString().trim().equals("")) zone.setError("required");

        else if (Itemcode.toString().trim().equals("")) itemcode.setError("required");


        else if (Qty.toString().trim().equals(""))
            recqty.setError("required");
        else {


            replacement.setRecQty(Qty);
            replacement.setUserNO(UserNo);
            Log.e("replacement","1=="+qty.getText().toString());
            replacement.setQty( listQtyZone.get(0).getQty());
            Log.e("replacement","2=="+replacement.getQty());
            replacement.setIsPosted("0");
            replacement.setUserNO(getusernum());
            replacement.setDeviceId(deviceId);
            replacement.setReplacementDate(generalMethod.getCurentTimeDate(1) + "");
             Log.e("else","AddInCaseDuplicates");
                Log.e("replacementlist.size", replacementlist.size()+"");
                replacementlist.add(replacement);
                Log.e("replacementlist.size", replacementlist.size()+""+replacementlist.get(0).getFrom());
                fillAdapter();
               SaveRow(replacement);
            }




    }

    private void SaveRow(ReplacementModel replacement) {
        Log.e("SaveRow","replacement"+replacement.getDeviceId());
        my_dataBase.replacementDao().insert(replacement);
    }

    private void updateQTYOfZone() {
        String d="";
        for (int i = 0; i < listQtyZone.size(); i++) {
            if (itemcode.getText().toString().trim().equals(listQtyZone.get(i).getItemCode().trim())
                    && zone.getText().toString().trim().equals(listQtyZone.get(i).getZoneCode().trim())) {
                {
                    listQtyZone.get(i).setQty(Integer.parseInt(listQtyZone.get(i).getQty()) -Integer.parseInt( Qty)+"");
                    d=listQtyZone.get(i).getQty();
                }
            }}
       Log.e(" updateQTYOfZone()",d) ;
    }

    private void fillAdapter() {
        Log.e(" fillAdapter"," fillAdapter");
        replacmentRecycler.setLayoutManager(new LinearLayoutManager(Replacement.this));
        adapter = new ReplacementAdapter(replacementlist, Replacement.this);
        replacmentRecycler.setAdapter(adapter);
    }


    private void readBarcode(int type) {
        //new IntentIntegrator(AddZone.this).setOrientationLocked(false).setCaptureActivity(CustomScannerActivity.class).initiateScan();

        openSmallCapture(type);
    }

    private void openSmallCapture(int type) {
        if (ContextCompat.checkSelfPermission(Replacement.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Replacement.this, new String[]{Manifest.permission.CAMERA}, REQUEST_Camera_Barcode);
            if (ContextCompat.checkSelfPermission(Replacement.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {//just for first time
                Log.e("requestresult", "PERMISSION_GRANTED");
                Intent i = new Intent(Replacement.this, ScanActivity.class);
                i.putExtra("key", "1");
                startActivity(i);
                // searchByBarcodeNo(s + "");
            }
        } else {
            Intent i = new Intent(Replacement.this, ScanActivity.class);
            if (type == 4) {
                i.putExtra("key", "4");
            } else {
                i.putExtra("key", "5");
            }

            startActivity(i);
            //  searchByBarcodeNo(s + "");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Toast.makeText(Replacement.this, "cancelled", Toast.LENGTH_SHORT).show();


            } else {
                String valueBarcode = Result.getContents();

            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void saveData(int isposted) {
        if (isposted == 1)
            for (int i = 0; i < replacementlist.size(); i++)
                replacementlist.get(i).setIsPosted("1");
        long result[] = my_dataBase.replacementDao().insertAll(replacementlist);

        if (result.length != 0) {
            showSweetDialog(this, 1, this.getResources().getString(R.string.savedSuccsesfule), "");
        }


    }
    public static void updateAdpapter() {
        adapter.notifyDataSetChanged();
    }

  public  boolean checkQtyValidate(String recqty) {
        Log.e("checkQtyValidate","heckQtyValidate");
        for (int i = 0; i < listQtyZone.size(); i++) {
            Log.e("checkQtyValidate","for");
            if (itemcode.getText().toString().trim().equals(listQtyZone.get(i).getItemCode().trim())
                    && zone.getText().toString().trim().equals(listQtyZone.get(i).getZoneCode().trim())) {
                if (Integer.parseInt(recqty) <= Integer.parseInt(listQtyZone.get(i).getQty()))
                {

                    return true;

              }
                else {

                    return false;


                }
            }
        }

   return false; }
}