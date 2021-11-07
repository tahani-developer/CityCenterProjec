package com.example.irbidcitycenter.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.Adapters.RepReverseAdapter;
import com.example.irbidcitycenter.ExportData;
import com.example.irbidcitycenter.GeneralMethod;
import com.example.irbidcitycenter.ImportData;
import com.example.irbidcitycenter.Models.AllItems;
import com.example.irbidcitycenter.Models.ReplashmentReversLogs;
import com.example.irbidcitycenter.Models.ReplenishmentReverseModel;
import com.example.irbidcitycenter.Models.appSettings;
import com.example.irbidcitycenter.R;
import com.example.irbidcitycenter.RoomAllData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.irbidcitycenter.Activity.Login.userPermissions;
import static com.example.irbidcitycenter.Activity.MainActivity.FILE_NAME;
import static com.example.irbidcitycenter.Activity.MainActivity.KEY_LANG;
import static com.example.irbidcitycenter.GeneralMethod.convertToEnglish;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;
import static com.example.irbidcitycenter.ImportData.Storelist;
import static com.example.irbidcitycenter.ImportData.storeinfo;

public class ReplenishmentReverse extends AppCompatActivity {

    Spinner fromSpinner, toSpinner;
    EditText RepRev_itemcodeedt;
    private String From, To, FromNo, ToNo, Itemcode;
    ReplenishmentReverseModel replacement;
    public static ArrayList<ReplenishmentReverseModel> replacementlist = new ArrayList<>();
    ImportData importData;
    public RoomAllData my_dataBase;
    private int position;
    RecyclerView replacmentRecycler;
    static RepReverseAdapter adapter;
    List<appSettings> appSettings;
    private String deviceId;
    public List<AllItems> AllstocktakeDBlist = new ArrayList<>();
    private String UserNo;
    GeneralMethod generalMethod;
    public static TextView RepRev_storrespon;
    List<String> spinnerArray = new ArrayList<>();
    List<String> spinnerArray2 = new ArrayList<>();
    Button backBtn, saveBtn, deleteBtn;
    private Animation animation;
    public static List<ReplenishmentReverseModel> DB_Reversreplist = new ArrayList<>();
    ;
    private List<ReplenishmentReverseModel> UnPostedreplacementlist;
    ExportData exportData;
    List<String> DB_store;
    private ArrayList<ReplenishmentReverseModel> deleted_DBzone;
    private Spinner spinner;
    private EditText DRepRev_itemcode;
    private TextView DRepRev_itemcodeshow;
    private Button DRepRev_delete;
    private TextView DRepRev_qtyshow;
    private Dialog authenticationdialog;
    private EditText UsNa;
    private int indexDBitem;
    LinearLayout zoneLin;
    public static List<ReplenishmentReverseModel> reducedqtyitemlist = new ArrayList<>();
    public static List<ReplenishmentReverseModel> DB_replistcopy = new ArrayList<>();
    private int indexOfReduceditem;
    public static TextView RepRev_exportstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_replenishment_reverse);
        init();

//my_dataBase.repReversDao().deleteALL();
        AllstocktakeDBlist.clear();

        AllstocktakeDBlist.addAll(my_dataBase.itemDao().getAll());


        if (AllstocktakeDBlist.size() == 0) {
            new SweetAlertDialog(ReplenishmentReverse.this, SweetAlertDialog.BUTTON_CONFIRM)
                    .setTitleText(getResources().getString(R.string.confirm_title))
                    .setContentText(getResources().getString(R.string.Msg1))
                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            importData.getAllItems();
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
        Storelist.clear();
        Storelist = my_dataBase.storeDao().getall();

        if (Storelist.size() > 0) {
            Log.e("sss", "sss");
            for (int i = 0; i < Storelist.size(); i++) {
                spinnerArray.add(Storelist.get(i).getSTORENO() + "  " + Storelist.get(i).getSTORENAME());

            }
            fillSp2();
        } else if (Storelist.size() == 0) {

            getStors();
            Log.e("sss4", "sss4");


        }
        UserNo = my_dataBase.settingDao().getUserNo();
    }

    private void loadLanguage() {
        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String langCode = preferences.getString(KEY_LANG, Locale.getDefault().getLanguage() );
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    TextView.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            Log.e("keyEvent.getAction()", keyEvent.getAction() + "");


            if (i == KeyEvent.KEYCODE_BACK) {
                onBackPressed();

            }

            if (i != KeyEvent.KEYCODE_ENTER) {

                {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP)
                        switch (view.getId()) {

                            case R.id.RepRev_itemcodeedt:

                                if (!RepRev_itemcodeedt.getText().toString().equals("")) {
                                    if (fromSpinner.getSelectedItem() != null && toSpinner.getSelectedItem() != null) {
                                        if (RepRev_itemcodeedt.getText().toString().length() <= 15) {
                                            fromSpinner.setEnabled(false);
                                            toSpinner.setEnabled(false);
                                            Log.e("itemcodeedt ", RepRev_itemcodeedt.getText().toString() + "");

                                            From = fromSpinner.getSelectedItem().toString();
                                            To = toSpinner.getSelectedItem().toString();
                                            FromNo = From.substring(0, From.indexOf(" "));
                                            ToNo = To.substring(0, To.indexOf(" "));

                                            Itemcode = convertToEnglish(RepRev_itemcodeedt.getText().toString().trim());


                                            replacement = new ReplenishmentReverseModel();
                                            replacement.setFrom(FromNo);
                                            replacement.setTo(ToNo);
                                            replacement.setZone("");
                                            replacement.setItemcode(Itemcode);
                                            replacement.setFromName(From);
                                            replacement.setToName(To);
                                            replacement.setDeviceId(deviceId);

                                            ReplenishmentReverseModel replacementModel = my_dataBase.repReversDao().getReplacement(Itemcode, FromNo, ToNo);
                                            if (replacementModel != null) {
                                                if (!CaseDuplicates(replacementModel))
                                                    replacementlist.add(replacementModel);

                                            }


                                            if (CaseDuplicates(replacement)) {
                                                Log.e("replacementFrom", replacement.getTo());
                                                Log.e("AddInCaseDuplicates", "AddInCaseDuplicates");
                                                //update qty in Duplicate case
                                                long sum = Long.parseLong(replacementlist.get(position).getRecQty()) + Long.parseLong("1");
                                                Log.e("aaasum ", sum + "");

                                                {
                                                    replacementlist.get(position).setRecQty((sum + ""));
                                                    my_dataBase.repReversDao().updateQTY(FromNo, replacementlist.get(position).getItemcode(), replacementlist.get(position).getRecQty());


                                                    RepRev_itemcodeedt.setText("");
                                                    RepRev_itemcodeedt.requestFocus();
                                                    fillAdapter();
                                                    Log.e("heree", "here2");
                                                }


                                            } else {
                                                Log.e("not in db", "not in db");
                                                if (ItemExists()) {
                                                    filldata();
                                                } else {
                                                    RepRev_itemcodeedt.setError("InValid");
                                                    RepRev_itemcodeedt.setEnabled(true);
                                                    RepRev_itemcodeedt.setText("");
                                                    RepRev_itemcodeedt.requestFocus();
                                                }
                                                ZoneReplacment.fromZoneRepActivity = 20;


                                            }
                                        } else {
                                            RepRev_itemcodeedt.setError("InValid");
                                            RepRev_itemcodeedt.setEnabled(true);
                                            RepRev_itemcodeedt.setText("");
                                            RepRev_itemcodeedt.requestFocus();

                                        }
                                    } else {

                                    }
                                } else
                                    RepRev_itemcodeedt.requestFocus();
                                break;
                        }
                    return true;
                }
            }
            return false;

        }
    };
    EditText.OnEditorActionListener onEditAction = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                    || i == EditorInfo.IME_NULL) {
                switch (textView.getId()) {


                    case R.id.RepRev_itemcodeedt:

                        if (!RepRev_itemcodeedt.getText().toString().equals("")) {

                            if (RepRev_itemcodeedt.getText().toString().length() <= 15) {
                                fromSpinner.setEnabled(false);
                                toSpinner.setEnabled(false);
                                Log.e("itemcodeedt ", RepRev_itemcodeedt.getText().toString() + "");

                                From = fromSpinner.getSelectedItem().toString();
                                To = toSpinner.getSelectedItem().toString();
                                FromNo = From.substring(0, From.indexOf(" "));
                                ToNo = To.substring(0, To.indexOf(" "));

                                Itemcode = convertToEnglish(RepRev_itemcodeedt.getText().toString().trim());


                                replacement = new ReplenishmentReverseModel();
                                replacement.setFrom(FromNo);
                                replacement.setTo(ToNo);
                                replacement.setZone("");
                                replacement.setItemcode(Itemcode);
                                replacement.setFromName(From);
                                replacement.setToName(To);
                                replacement.setDeviceId(deviceId);

                                ReplenishmentReverseModel replacementModel = my_dataBase.repReversDao().getReplacement(Itemcode, FromNo, ToNo);
                                if (replacementModel != null) {
                                    if (!CaseDuplicates(replacementModel))
                                        replacementlist.add(replacementModel);

                                }


                                if (CaseDuplicates(replacement)) {
                                    Log.e("replacementFrom", replacement.getTo());
                                    Log.e("AddInCaseDuplicates", "AddInCaseDuplicates");
                                    //update qty in Duplicate case
                                    long sum = Long.parseLong(replacementlist.get(position).getRecQty()) + Long.parseLong("1");
                                    Log.e("aaasum ", sum + "");

                                    //     if (sum <= Long.parseLong(replacementlist.get(position).getQty()))
                                    {
                                        replacementlist.get(position).setRecQty((sum + ""));
                                        my_dataBase.repReversDao().updateQTY(replacementlist.get(position).getFrom(), replacementlist.get(position).getItemcode(), replacementlist.get(position).getRecQty());


                                        RepRev_itemcodeedt.setText("");
                                        RepRev_itemcodeedt.requestFocus();
                                        fillAdapter();
                                        Log.e("heree", "here2");
                                    }
                                      /*  else {
                                            showSweetDialog(Replacement.this, 3, "", getResources().getString(R.string.notvaildqty));

                                            fillAdapter();
                                            zone.setEnabled(false);
                                            itemcode.setText("");
                                            itemcode.requestFocus();
                                        }*/


                                } else {
                                    Log.e("not in db", "not in db");
                                    if (ItemExists()) {
                                        filldata();
                                    }
                                    ZoneReplacment.fromZoneRepActivity = 20;


                                }
                            } else {
                                RepRev_itemcodeedt.setError("InValid");
                                RepRev_itemcodeedt.setEnabled(true);
                                RepRev_itemcodeedt.setText("");
                                RepRev_itemcodeedt.requestFocus();

                            }
                        } else
                            RepRev_itemcodeedt.requestFocus();
                        break;

                }


            }

            return true;
        }
    };


    private void filldata() {


        if (Itemcode.toString().trim().equals("")) RepRev_itemcodeedt.setError("required");


        else {

            if (Itemcode.toString().trim().length() <= 15) {
                replacement.setUserNO(UserNo);
                replacement.setRecQty("1");
                replacement.setIsPosted("0");
                //   replacement.setUserNO(getusernum());
                replacement.setDeviceId(deviceId);
                replacement.setReplacementDate(generalMethod.getCurentTimeDate(1) + "");
                Log.e("else", "AddInCaseDuplicates");
                Log.e("replacementlist.size", replacementlist.size() + "");
                replacementlist.add(replacement);
                Log.e("replacementlist.size", replacementlist.size() + "" + replacementlist.get(0).getFrom());
                fillAdapter();
                SaveRow(replacement);
                RepRev_itemcodeedt.setText("");

            } else {
                RepRev_itemcodeedt.setError("InValid");
                RepRev_itemcodeedt.setText("");
                RepRev_itemcodeedt.setEnabled(true);
                RepRev_itemcodeedt.requestFocus();
            }
        }


    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.RepRev_cancel:
                    animation = AnimationUtils.loadAnimation(ReplenishmentReverse.this, R.anim.modal_in);
                    backBtn.startAnimation(animation);

                    new SweetAlertDialog(ReplenishmentReverse.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.messageExit))
                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    if (replacementlist.size() > 0) {

                                        new SweetAlertDialog(ReplenishmentReverse.this, SweetAlertDialog.WARNING_TYPE)
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
                                    } else {
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
                    break;
                case R.id.RepRev_save:


                    savedata();
                    break;
                case R.id.RepRev_delete:
                    if (userPermissions == null) getUsernameAndpass();
                    animation = AnimationUtils.loadAnimation(ReplenishmentReverse.this, R.anim.modal_in);
                    deleteBtn.startAnimation(animation);
                    if (userPermissions.getMasterUser().equals("0")) {
                        if (userPermissions.getRep_LocalDelete().equals("1"))
                            OpenDeleteDailog();
                        else {


                            new SweetAlertDialog(ReplenishmentReverse.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.confirm_title))
                                    .setContentText(getResources().getString(R.string.del_per))
                                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            OpenDeleteDailog2();
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
                    } else {
                        OpenDeleteDailog();
                    }


            }

        }


    };


    private boolean isExists(int flage, String store, String itemcode) {
        boolean f = false;
        if (flage == 2) {
            for (int i = 0; i < DB_Reversreplist.size(); i++)
                if (DB_Reversreplist.get(i).getTo().equals(store)
                        && DB_Reversreplist.get(i).getItemcode().equals(itemcode)) {
                    f = true;
                    indexDBitem = i;
                    break;
                } else {
                    f = false;
                    continue;
                }

        }
        return f;
    }

    private void openDeleteItemsDailog() {


        DB_Reversreplist.clear();
        DB_Reversreplist = my_dataBase.repReversDao().getallReplacement();
        DB_store = new ArrayList<>();
        deleted_DBzone = new ArrayList<>();
        DB_store.clear();
        deleted_DBzone.clear();
        copylist();


        final Dialog dialog = new Dialog(ReplenishmentReverse.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.rep_revers_deletedailog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        spinner = dialog.findViewById(R.id.DZRE_storespinner);


        for (int i = 0; i < DB_Reversreplist.size(); i++)
            if (!DB_store.contains(DB_Reversreplist.get(i).getToName()))
                DB_store.add(DB_Reversreplist.get(i).getToName());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, DB_store);
        spinner.setAdapter(adapter);
        dialog.show();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DRepRev_itemcode.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        DRepRev_delete = dialog.findViewById(R.id.DRepRev_delete);
        DRepRev_delete.setEnabled(false);
        DRepRev_itemcode = dialog.findViewById(R.id.DRepRev_itemcode);
        //   DRepRev_zonecodeshow
        DRepRev_itemcodeshow = dialog.findViewById(R.id.DRepRev_zonecodeshow);
        DRepRev_qtyshow = dialog.findViewById(R.id.DRepRev_qtyshow);

        TextView DRepRev_preQTY = dialog.findViewById(R.id.DRepRev_preQTY);
        DRepRev_itemcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i != KeyEvent.KEYCODE_ENTER) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (!DRepRev_itemcode.getText().toString().equals("")) {
                            if (isExists(2, spinner.getSelectedItem().toString().substring(0, spinner.getSelectedItem().toString().indexOf(" ")), DRepRev_itemcode.getText().toString().trim())) {


                                long preqty = Long.parseLong(getpreQty(spinner.getSelectedItem().toString().substring(0, spinner.getSelectedItem().toString().indexOf(" ")), DRepRev_itemcode.getText().toString().trim()));
                                DRepRev_preQTY.setText(preqty + "");
                                long sumqty = Long.parseLong(DB_Reversreplist.get(indexDBitem).getRecQty());
                                if (sumqty > 1) {

                                    sumqty--;
                                    DB_Reversreplist.get(indexDBitem).setRecQty(sumqty + "");
                                    DRepRev_qtyshow.setText(DB_Reversreplist.get(indexDBitem).getRecQty());

                                    DRepRev_itemcodeshow.setText(DRepRev_itemcode.getText().toString().trim());

                                    if (isExistsInReducedlist()) {
                                        Log.e("Case1==", "Case1");
                                        reducedqtyitemlist.get(indexOfReduceditem).setRecQty(sumqty + "");
                                    } else {
                                        Log.e("Case2==", "Case2");
                                        reducedqtyitemlist.add(DB_Reversreplist.get(indexDBitem));
                                    }
                                    DRepRev_itemcode.setText("");
                                    DRepRev_itemcode.requestFocus();
                                } else {
                                    new SweetAlertDialog(ReplenishmentReverse.this, SweetAlertDialog.BUTTON_CONFIRM)
                                            .setTitleText(getResources().getString(R.string.confirm_title))
                                            .setContentText(getResources().getString(R.string.delete3))
                                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    DB_Reversreplist.get(indexDBitem).setRecQty("0");
                                                    DRepRev_preQTY.setText("1");
                                                    DRepRev_qtyshow.setText("1");

                                                    DRepRev_itemcodeshow.setText(DRepRev_itemcode.getText().toString().trim());

                                                    if (isExistsInReducedlist())
                                                        reducedqtyitemlist.get(indexOfReduceditem).setRecQty("0");
                                                    else
                                                        reducedqtyitemlist.add(DB_replistcopy.get(indexDBitem));

                                                    DB_Reversreplist.remove(indexDBitem);
                                                    // DB_replistcopy.remove(indexDBitem);
                                                    DRepRev_itemcode.setText("");

                                                    DRepRev_qtyshow.setText("");

                                                    DRepRev_itemcodeshow.setText("");
                                                    DRepRev_preQTY.setText("");
                                                    sweetAlertDialog.dismiss();
                                                }
                                            })
                                            .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                    DRepRev_preQTY.setText("1");
                                                    DRepRev_qtyshow.setText("1");

                                                    DRepRev_itemcodeshow.setText(DRepRev_itemcode.getText().toString().trim());

                                                }
                                            })
                                            .show();
                                }
                                DRepRev_delete.setEnabled(true);
                            } else {
                                DRepRev_itemcode.setError("InValid");
                                DRepRev_itemcode.setText("");
                                DRepRev_qtyshow.setText("");
                                DRepRev_itemcodeshow.setText("");
                            }
                        }

                    } else {
                        DRepRev_itemcode.requestFocus();
                    }


                    return true;
                }

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
        dialog.findViewById(R.id.DRepRev_dialogsave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reducedqtyitemlist.size() > 0) {
                    checkLocalList();
                    addReplashmentLogs();
                    for (int i = 0; i < reducedqtyitemlist.size(); i++) {
                        if (reducedqtyitemlist.get(i).getRecQty().equals("0"))
                            my_dataBase.repReversDao().deleteDbReplacement(reducedqtyitemlist.get(i).getItemcode(), reducedqtyitemlist.get(i).getFrom());
                        else
                            my_dataBase.repReversDao().updateDBQTY(reducedqtyitemlist.get(i).getRecQty(), reducedqtyitemlist.get(i).getItemcode(), reducedqtyitemlist.get(i).getFrom());
                    }
                    Toast.makeText(ReplenishmentReverse.this, getString(R.string.app_done), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ReplenishmentReverse.this, getString(R.string.nODataChanged), Toast.LENGTH_SHORT).show();
                }

                authenticationdialog.dismiss();

                dialog.dismiss();
            }
        });
        DRepRev_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new SweetAlertDialog(ReplenishmentReverse.this, SweetAlertDialog.BUTTON_CONFIRM)
                        .setTitleText(getResources().getString(R.string.confirm_title))
                        .setContentText(getResources().getString(R.string.deleteitem))
                        .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                DB_Reversreplist.get(indexDBitem).setRecQty("0");
                                if (reducedqtyitemlist.size() > 0) {


                                    if (isExistsInReducedlist()) {

                                        reducedqtyitemlist.get(indexOfReduceditem).setRecQty("0");
                                    } else {

                                        reducedqtyitemlist.add(DB_Reversreplist.get(indexDBitem));
                                    }
                                } else
                                    reducedqtyitemlist.add(DB_Reversreplist.get(indexDBitem));


                                Log.e(" reducedqtyitemlist", "" + reducedqtyitemlist.size());
                                Log.e(" reducedqtyitemlist", "" + DB_Reversreplist.get(indexDBitem).getItemcode());
                                DB_Reversreplist.remove(indexDBitem);
                                RepRev_itemcodeedt.setText("");
                                DRepRev_itemcodeshow.setText("");
                                DRepRev_preQTY.setText("");
                                DRepRev_qtyshow.setText("");

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
        dialog.findViewById(R.id.DZRE_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (reducedqtyitemlist.size() > 0)

                    new SweetAlertDialog(ReplenishmentReverse.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.confirm_title))
                            .setContentText(getResources().getString(R.string.returndata))
                            .setConfirmButton(getResources().getString(R.string.yes),
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {


                                            reducedqtyitemlist.clear();
                                            RepRev_itemcodeedt.setText("");
                                            DRepRev_itemcodeshow.setText("");
                                            DRepRev_preQTY.setText("");
                                            DRepRev_qtyshow.setText("");

                                            DB_Reversreplist.clear();
                                            DB_Reversreplist = my_dataBase.repReversDao().getallReplacement();
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
                    Toast.makeText(ReplenishmentReverse.this, getResources().getString(R.string.NODATA), Toast.LENGTH_LONG).show();
            }

        });
    }

    private boolean isExistsInReducedlist() {
        boolean f = false;


        for (int x = 0; x < reducedqtyitemlist.size(); x++)
            if (
                    reducedqtyitemlist.get(x).getTo().equals(spinner.getSelectedItem().toString().substring(0, spinner.getSelectedItem().toString().indexOf(" ")))
                            && reducedqtyitemlist.get(x).getItemcode().equals(DRepRev_itemcodeshow.getText().toString().trim())) {
                f = true;
                indexOfReduceditem = x;
            } else {
                f = false;


                continue;
            }

        return f;
    }

    private void addReplashmentLogs() {

        Log.e("reducedqtyitemlist==", reducedqtyitemlist.size() + "");
        ReplashmentReversLogs replashmentLogs = new ReplashmentReversLogs();
        for (int i = 0; i < reducedqtyitemlist.size(); i++) {
            replashmentLogs.setStore(reducedqtyitemlist.get(i).getTo());
            replashmentLogs.setItemCode(reducedqtyitemlist.get(i).getItemcode());
            replashmentLogs.setNewQty(reducedqtyitemlist.get(i).getRecQty());
            String preqty = getpreQty(reducedqtyitemlist.get(i).getTo(), reducedqtyitemlist.get(i).getItemcode());
            replashmentLogs.setPreQty(preqty);

            replashmentLogs.setLogsDATE(generalMethod.getCurentTimeDate(1));
            replashmentLogs.setLogsTime(generalMethod.getCurentTimeDate(2));
            replashmentLogs.setUserNO(UsNa.getText().toString());
            my_dataBase.replashmentReversLogsDao().insert(replashmentLogs);
        }


    }

    private String getpreQty(String store, String itemcode) {

        Log.e("zonecopysize", DB_replistcopy.size() + "");
        String z = "";

        Log.e("A", store + "");
        Log.e("B", itemcode + "");


        for (int i = 0; i < DB_replistcopy.size(); i++) {
            Log.e("A", DB_replistcopy.get(i).getItemcode() + "");
            Log.e("B", DB_replistcopy.get(i).getTo() + "");


            if (itemcode.equals(DB_replistcopy.get(i).getItemcode())
                    && store.equals(DB_replistcopy.get(i).getTo())) {
                Log.e("getpreQty3", DB_replistcopy.get(i).getRecQty());
                Log.e("index", i + "");
                z = DB_replistcopy.get(i).getRecQty();
                break;
            } else {
                z = "";
            }

        }
        return z;
    }

    public void getqtyofDBITEMS() {
        long sum = 0;
        for (int x = 0; x < DB_Reversreplist.size(); x++)
            if (DB_Reversreplist.get(x).getTo().equals(spinner.getSelectedItem().toString().substring(0, spinner.getSelectedItem().toString().indexOf(" "))) &&
                    DB_Reversreplist.get(x).getZone().equals(DRepRev_itemcode.getText().toString().trim()))
                sum += Long.parseLong(DB_Reversreplist.get(x).getRecQty());
        DRepRev_qtyshow.setText(sum + "");

    }

    private void checkLocalList() {

        if (replacementlist != null) {
            if (replacementlist.size() > 0) {
                for (int i = 0; i < reducedqtyitemlist.size(); i++) {
                    for (int j = 0; j < replacementlist.size(); j++) {
                        Log.e("LocalListsize", replacementlist.size() + "");
                        if (replacementlist.get(j).getFrom().equals(reducedqtyitemlist.get(i).getFrom())
                                && replacementlist.get(j).getItemcode().equals(reducedqtyitemlist.get(i).getItemcode()))

                            if (reducedqtyitemlist.get(i).getRecQty().equals("0"))
                                replacementlist.remove(j);
                            else
                                replacementlist.get(j).setRecQty(reducedqtyitemlist.get(i).getRecQty());
                        if (adapter != null) adapter.notifyDataSetChanged();
                    }
                }

            }
        }
    }

    public void getUsernameAndpass() {


        String comNUm = "";
        String Userno = "";
        appSettings = my_dataBase.settingDao().getallsetting();
        if (appSettings.size() != 0) {
            Userno = appSettings.get(0).getUserNumber();
            comNUm = appSettings.get(0).getCompanyNum();

        }

        userPermissions = my_dataBase.userPermissionsDao().getUserPermissions(Userno);


        //   Toast.makeText(Login.this,"This user is not recognized ",Toast.LENGTH_SHORT).show();


    }

    private void savedata() {
        if (userPermissions == null) getUsernameAndpass();
        animation = AnimationUtils.loadAnimation(ReplenishmentReverse.this, R.anim.modal_in);
        saveBtn.startAnimation(animation);


        if (userPermissions.getMasterUser().equals("0")) {
            if (userPermissions.getRep_Save().equals("1")) {
                if (replacementlist.size() > 0) {
                    fromSpinner.setEnabled(true);
                    toSpinner.setEnabled(true);


                    UnPostedreplacementlist = my_dataBase.repReversDao().getallReplacement();

                    for (int i = 0; i < UnPostedreplacementlist.size(); i++)
                        if (UnPostedreplacementlist.get(i).getDeviceId() == null)
                            UnPostedreplacementlist.get(i).setDeviceId(deviceId);

                    MainActivity.exportFromMainAct = false;
                    exportData();

                    RepRev_itemcodeedt.setText("");
                } else {
                    generalMethod.showSweetDialog(ReplenishmentReverse.this, 3, getResources().getString(R.string.warning), getResources().getString(R.string.fillYourList));
                }

            } else {
                generalMethod.showSweetDialog(ReplenishmentReverse.this, 3, getResources().getString(R.string.warning), getResources().getString(R.string.savePermission));

            }
        } else {
            if (replacementlist.size() > 0) {
                fromSpinner.setEnabled(true);
                toSpinner.setEnabled(true);


                UnPostedreplacementlist = my_dataBase.repReversDao().getallReplacement();

                for (int i = 0; i < UnPostedreplacementlist.size(); i++)
                    if (UnPostedreplacementlist.get(i).getDeviceId() == null)
                        UnPostedreplacementlist.get(i).setDeviceId(deviceId);

                MainActivity.exportFromMainAct = false;
                exportData();

                RepRev_itemcodeedt.setText("");
            } else {
                generalMethod.showSweetDialog(ReplenishmentReverse.this, 3, getResources().getString(R.string.warning), getResources().getString(R.string.fillYourList));
            }
        }
    }

    private void copylist() {
        DB_replistcopy.clear();
        List<String> zon = new ArrayList<>();
        List<String> item = new ArrayList<>();
        List<String> qtys = new ArrayList<>();
        List<String> store = new ArrayList<>();
        for (int i = 0; i < DB_Reversreplist.size(); i++) {

            item.add(DB_Reversreplist.get(i).getItemcode());
            qtys.add(DB_Reversreplist.get(i).getRecQty());
            store.add(DB_Reversreplist.get(i).getTo());
        }

        for (int i = 0; i < item.size(); i++) {
            ReplenishmentReverseModel replacementModel = new ReplenishmentReverseModel();
            replacementModel.setTo(store.get(i));
            replacementModel.setItemcode(item.get(i));
            replacementModel.setRecQty(qtys.get(i));
            DB_replistcopy.add(replacementModel);
        }
        Log.e("DB_replistcopy===", DB_replistcopy.size() + "");
    }

    public void exportData() {
        try {
            MainActivity.exportFromMainAct2 = false;
            exportData.exportReversReplacementList(UnPostedreplacementlist);
        } catch (Exception e) {

            // test
        }

    }

    private void SaveRow(ReplenishmentReverseModel replacement) {
        Log.e("SaveRow", "replacement" + replacement.getDeviceId());
        my_dataBase.repReversDao().insert(replacement);
    }

    public boolean CaseDuplicates(ReplenishmentReverseModel replacement) {
        boolean flag = false;
        if (replacementlist.size() != 0)
            for (int i = 0; i < replacementlist.size(); i++) {

                if (convertToEnglish(replacementlist.get(i).getItemcode()).equals(replacement.getItemcode())
                        && replacementlist.get(i).getFrom().equals(replacement.getFrom())
                        && replacementlist.get(i).getTo().equals(replacement.getTo())) {

                    position = i;
                    flag = true;
                    break;

                } else
                    flag = false;
                continue;
            }

        return flag;

    }

    private void fillSp() {
        spinnerArray.remove(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        fromSpinner.setAdapter(adapter);

        //    toSpinner.setAdapter(adapter);
        //  toSpinner.setSelection(1);

        Log.e("sss1", "sss1");
    }

    private void fillSp2() {
        List<String> spinnerArray2 = new ArrayList<>();
        spinnerArray2.add(spinnerArray.get(0));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray2);

        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(0);
        Log.e("sss1", "sss1");
        fillSp();
    }

    private void init() {
        replacementlist.clear();
        RepRev_exportstate = findViewById(R.id.RepRev_exportstate);
        exportData = new ExportData(ReplenishmentReverse.this);
        deleteBtn = findViewById(R.id.RepRev_delete);
        saveBtn = findViewById(R.id.RepRev_save);
        backBtn = findViewById(R.id.RepRev_cancel);
        backBtn.setOnClickListener(onClickListener);
        saveBtn.setOnClickListener(onClickListener);
        deleteBtn.setOnClickListener(onClickListener);
        RepRev_storrespon = findViewById(R.id.RepRev_storrespon);
        fromSpinner = findViewById(R.id.RepRev_fromspinner);
        toSpinner = findViewById(R.id.RepRev_tospinner);

        generalMethod = new GeneralMethod(ReplenishmentReverse.this);

        replacmentRecycler = findViewById(R.id.RepRev_Recycl);

        RepRev_itemcodeedt = findViewById(R.id.RepRev_itemcodeedt);

        RepRev_itemcodeedt.setOnEditorActionListener(onEditAction);


        RepRev_itemcodeedt.setOnKeyListener(onKeyListener);
        importData = new ImportData(ReplenishmentReverse.this);

        my_dataBase = RoomAllData.getInstanceDataBase(ReplenishmentReverse.this);

        appSettings = new ArrayList();
        try {
            appSettings = my_dataBase.settingDao().getallsetting();
        } catch (Exception e) {
        }
        if (appSettings.size() != 0) {
            deviceId = appSettings.get(0).getDeviceId();
            Log.e("appSettings", "+" + deviceId);

        }

        RepRev_storrespon.addTextChangedListener(new TextWatcher() {
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


                    } else {
                        if (editable.toString().equals("fill")) {
                            for (int i = 0; i < Storelist.size(); i++) {
                                spinnerArray.add(Storelist.get(i).getSTORENO() + "  " + Storelist.get(i).getSTORENAME());
                                my_dataBase.storeDao().insert(Storelist.get(i));
                            }
                        }
                        fillSp2();


                        Log.e("afterTextChanged", "" + editable.toString());

                    }

                }

            }
        });


        RepRev_exportstate.addTextChangedListener(new TextWatcher() {
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
                            my_dataBase.repReversDao().updateReplashmentPosted();
                            showSweetDialog(ReplenishmentReverse.this, 1, getResources().getString(R.string.savedSuccsesfule), "");

                            replacementlist.clear();
                            fillAdapter();
                            adapter.notifyDataSetChanged();
                        }
                    } else if (editable.toString().trim().equals("not")) {
                        Toast.makeText(ReplenishmentReverse.this, getString(R.string.noInternet), Toast.LENGTH_SHORT).show();

                        //saveData(0);
                        replacementlist.clear();
                        fillAdapter();
                        adapter.notifyDataSetChanged();
                    } else {
                        // Toast.makeText(ReplenishmentReverse.this,"Server Error",Toast.LENGTH_SHORT).show();
                        replacementlist.clear();
                        fillAdapter();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });


    }

    private void fillAdapter() {
        Log.e(" fillAdapter", " fillAdapter");
        replacmentRecycler.setLayoutManager(new LinearLayoutManager(ReplenishmentReverse.this));
        adapter = new RepReverseAdapter(replacementlist, ReplenishmentReverse.this);
        replacmentRecycler.setAdapter(adapter);
    }

    public void ScanCode(View view) {
    }

    public boolean ItemExists() {

        boolean flage = false;


        for (int x1 = 0; x1 < AllstocktakeDBlist.size(); x1++) {
            if (AllstocktakeDBlist.get(x1).getItemOcode().equals(RepRev_itemcodeedt.getText().toString().trim())) {
                flage = true;
                //      itemname.setText(AllstocktakeDBlist.get(x1).getItemNameE());
                break;
            } else {
                flage = false;
            }
        }

        return flage;

    }

    private void getStors() {
        Replacement.actvityflage = 3;
        importData.getStore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (storeinfo != null && storeinfo.isShowing()) {
            storeinfo.cancel();
        }
    }

    private void authenticDailog() {


        authenticationdialog = new Dialog(ReplenishmentReverse.this);
        authenticationdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        authenticationdialog.setCancelable(false);
        authenticationdialog.setContentView(R.layout.authentication);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(authenticationdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        authenticationdialog.getWindow().setAttributes(lp);

        authenticationdialog.show();

        Button button = authenticationdialog.findViewById(R.id.authentic);
        TextView cancelbutton = authenticationdialog.findViewById(R.id.cancel2);
        UsNa = authenticationdialog.findViewById(R.id.username);
        UsNa.requestFocus();

        EditText pass = authenticationdialog.findViewById(R.id.pass);
        pass.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UsNa.getText().toString().trim().equals(userPermissions.getUserNO()) && pass.getText().toString().trim().equals(userPermissions.getUserPassword())) {
                    openDeleteItemsDailog();


                } else {

                    if (!UsNa.getText().toString().trim().equals(userPermissions.getUserNO())) {
                        UsNa.setError(getResources().getString(R.string.invalid_username));

                    } else {
                        pass.setError(getResources().getString(R.string.invalid_password));
                    }
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

    private void OpenDeleteDailog() {


        final Dialog dialog = new Dialog(ReplenishmentReverse.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.deletelayout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        lp.height = 300;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        Button deletezone = dialog.findViewById(R.id.deletezone);
        Button deleteitem = dialog.findViewById(R.id.deleteitem);
        zoneLin = dialog.findViewById(R.id.zoneLin);
        zoneLin.setVisibility(View.GONE);
        deletezone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticDailog();
            }
        });

        deleteitem.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {

                                              authenticDailog();
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

    private void OpenDeleteDailog2() {


        final Dialog dialog = new Dialog(ReplenishmentReverse.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.deletelayout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        Button deletezone = dialog.findViewById(R.id.deletezone);
        Button deleteitem = dialog.findViewById(R.id.deleteitem);

        deletezone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticDailog2();
            }
        });

        deleteitem.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {

                                              authenticDailog2();
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

    private void authenticDailog2() {


        authenticationdialog = new Dialog(ReplenishmentReverse.this);
        authenticationdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        authenticationdialog.setCancelable(false);
        authenticationdialog.setContentView(R.layout.authentication);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(authenticationdialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        authenticationdialog.getWindow().setAttributes(lp);

        authenticationdialog.show();

        Button button = authenticationdialog.findViewById(R.id.authentic);
        TextView cancelbutton = authenticationdialog.findViewById(R.id.cancel2);
        UsNa = authenticationdialog.findViewById(R.id.username);
        UsNa.requestFocus();

        EditText pass = authenticationdialog.findViewById(R.id.pass);
        pass.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String isMaster = "";
                if (!UsNa.getText().toString().trim().equals("")
                        && !pass.getText().toString().trim().equals("")) {
                    isMaster = my_dataBase.userPermissionsDao().getIsMaster(UsNa.getText().toString().trim(), pass.getText().toString().trim());

                    if (isMaster != null && isMaster.equals("1")) {


                        openDeleteItemsDailog();
                        authenticationdialog.dismiss();


                    } else {

                        generalMethod.showSweetDialog(ReplenishmentReverse.this, 3, getResources().getString(R.string.Permission), "");

                    }
                } else {
                    UsNa.setError(getResources().getString(R.string.required));
                    pass.setError(getResources().getString(R.string.required));

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
}