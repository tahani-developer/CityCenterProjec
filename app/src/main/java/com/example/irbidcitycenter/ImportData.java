package com.example.irbidcitycenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irbidcitycenter.Activity.AddZone;
import com.example.irbidcitycenter.Activity.Login;
import com.example.irbidcitycenter.Activity.MainActivity;
import com.example.irbidcitycenter.Activity.NewShipment;
import com.example.irbidcitycenter.Activity.Replacement;


import com.example.irbidcitycenter.Activity.ReplenishmentReverse;
import com.example.irbidcitycenter.Activity.Stoketake;
import com.example.irbidcitycenter.Activity.ZoneReplacment;
import com.example.irbidcitycenter.Interfaces.ApiService;
import com.example.irbidcitycenter.Models.AllItems;
import com.example.irbidcitycenter.Models.AllPOs;
import com.example.irbidcitycenter.Models.CompanyInfo;
import com.example.irbidcitycenter.Models.ItemInfo;
import com.example.irbidcitycenter.Models.NewAllPOsInfo;
import com.example.irbidcitycenter.Models.NewZonsData;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.RetrofitInstance;
import com.example.irbidcitycenter.Models.Store;
import com.example.irbidcitycenter.Models.UserPermissions;
import com.example.irbidcitycenter.Models.ZoneModel;

import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.Models.ZonsData;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;


import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;
import static com.example.irbidcitycenter.Activity.AddZone.itemKind;
import static com.example.irbidcitycenter.Activity.AddZone.itemKintText;

import static com.example.irbidcitycenter.Activity.AddZone.validateKind;
import static com.example.irbidcitycenter.Activity.ItemChecker.ItC_itemcode;
import static com.example.irbidcitycenter.Activity.ItemChecker.itemRES;
import static com.example.irbidcitycenter.Activity.ItemChecker.stockqrtRes;

import static com.example.irbidcitycenter.Activity.MainActivity.Respon_arrayList;
import static com.example.irbidcitycenter.Activity.MainActivity.itemrespons;
import static com.example.irbidcitycenter.Activity.NewShipment.PONO_respon;
import static com.example.irbidcitycenter.Activity.NewShipment.poNo;
import static com.example.irbidcitycenter.Activity.Replacement.itemKintText1;
import static com.example.irbidcitycenter.Activity.Replacement.itemcode;

import static com.example.irbidcitycenter.Activity.Replacement.qtyrespons;
import static com.example.irbidcitycenter.Activity.Replacement.zone;

import static com.example.irbidcitycenter.Activity.ReplenishmentReverse.RepRev_Itemrespons;
import static com.example.irbidcitycenter.Activity.Stoketake.AllstocktakeDBlist;

import static com.example.irbidcitycenter.Activity.Stoketake.St_Itemrespons;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.RZ_itemcode;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.ZR_itemkind;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.ZR_respon;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.fromZoneRepActivity;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.fromzone;
import static com.example.irbidcitycenter.GeneralMethod.convertToEnglish;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;


public class ImportData {
 ApiService   myAPI;
    String msgbuilder1="",msgbuilder2="",msgbuilder3="";
    public static int actvityflage = 1;
    SweetAlertDialog pdVoucher;
    public static List<ZonsData> listAllZone = new ArrayList<>();
    public static ArrayList<ZonsData> WebSerlistAllZone = new ArrayList<>();
    public static ArrayList<ZoneModel> Zoneslist = new ArrayList<>();

    public static ArrayList<NewZonsData> NewZoneslist = new ArrayList<>();
    public static List<NewAllPOsInfo> NewPOdetailslist = new ArrayList<>();


    public static ArrayList<ZoneModel> itemdetalis = new ArrayList<>();
    public static ArrayList<UserPermissions> UserPermissions = new ArrayList<>();
    public static int posize;
    public static String itemn;
    public static String item_name = "";
    public static String poqty;
    private static Context context;
    public String ipAddress = "", CONO = "", headerDll = "", link = "";
    public RoomAllData my_dataBase;
    public static String zonetype;
    public static List<Store> Storelist = new ArrayList<>();
    public static List<String> BoxNolist = new ArrayList<>();
    public static ArrayList<AllPOs> PoNolist = new ArrayList<>();
    public static List<NewAllPOsInfo> POdetailslist = new ArrayList<>();
    public static List<ReplacementModel> stocksQty = new ArrayList<>();
    public static List<NewZonsData> New_listQtyZone = new ArrayList<>();




    public static ArrayList<CompanyInfo> companyInList = new ArrayList<>();
    public static String barcode = "";
    public static List<AllItems> AllImportItemlist = new ArrayList<>();
    public static List<ItemInfo> itemInfos = new ArrayList<>();
    public JSONArray jsonArrayPo;
    public JSONObject stringNoObject;
    public static String linkbarcode;

//

    static ProgressDialog progressDialog;
    static SweetAlertDialog POs_progressDialog,Zons_progressDialog;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private int max = 50;
    public static SweetAlertDialog pdUserPer, pditeminfo, storeinfo, zoneinfo,pditem;
    private int timer = 1;

    private void showProgressDialogWithTitle(String title, String substring) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(substring);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        if(!((Activity) context).isFinishing())
        {
            progressDialog.show();
        }


        // Start Process Operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    try{
                        // This is mock thread using sleep to show progress
                        Thread.sleep(200);
                        progressStatus += 1;
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    // Change percentage in the progress bar
                    handler.post(new Runnable() {
                        public void run() {
                            progressDialog.setProgress(progressStatus);
                        }
                    });
                }

            }
        }).start();

    }

    // Method to hide/ dismiss Progress bar
    public static void hideProgressDialogWithTitle() {
        Log.e("herea2","aaaaa");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.dismiss();
        Log.e("herea3","aaaaa");

        //showSweetDialog(context,2,"Done,All data is stored","");

    }





    public ImportData(Context context) {
        this.context = context;
        my_dataBase = RoomAllData.getInstanceDataBase(context);
        try {
            getIpAddress();
       link = "http://" + ipAddress.trim()  + headerDll.trim();
        //    link = "http://10.0.0.22:8085" ;
          //  link ="http://80.90.172.150:8085/falcons/JRD.dll/";
           Retrofit retrofit = RetrofitInstance.getInstance(link);


            myAPI = retrofit.create(ApiService.class);

        } catch (Exception e) {
         Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void getPoNum(){
        if(!ipAddress.equals(""))
            new JSONTask_getAllPoNum().execute();
        else
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
    }

    public void GetItemCountInSTR(){
        stocksQty.clear();
        if(!ipAddress.equals(""))
            new JSONTask_GetItemCountInSTR().execute();
        else
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
    }
    public void getUserPermissions(int act_flag){
        UserPermissions.clear();
        pdUserPer = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdUserPer.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdUserPer.setTitleText(" Start get User Permissions");
        pdUserPer.setCancelable(false);
        pdUserPer.show();
        if(!ipAddress.equals(""))
            new JSONTask_getAllUserPermissions(act_flag).execute();
        else
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
    }
    public void getAllItems(int act_flag){
        try {


            pditem = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pditem.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pditem.setTitleText(" Start get Data");
            pditem.setCancelable(false);
            pditem.show();
            AllImportItemlist.clear();
            Log.e("context", context.getClass().getName().toString());

            AllstocktakeDBlist.clear();


            if (!ipAddress.equals(""))
               // new JSONTask_getAllItems(context, act_flag).execute();
                //AllItems_fetchCallData(act_flag);
                AllItems_fetchCallData2(act_flag);
            else
                Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        }

    public void getItemInfo(){
        pditeminfo = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pditeminfo.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pditeminfo.setTitleText(" Start get Items Info");
        pditeminfo.setCancelable(false);
        pditeminfo.show();
        itemInfos.clear();
        if(!ipAddress.equals(""))
            new  JSONTask_getItemInfo().execute();
        else
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
    }
    public void getQty() {
        New_listQtyZone.clear();
            new  JSONTask_getQTYOFZone().execute();

    }
    public void getStore(int falge) {
        storeinfo = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        storeinfo.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        storeinfo.setTitleText(" Start get Stores Info");
        storeinfo.setCancelable(false);
        storeinfo.show();

        if(!ipAddress.equals(""))
        new JSONTask_getAllStoreData(falge).execute();
        else
        Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
    }

    public void getboxno() {
        Log.e("ingetboxno","ingetboxno");
        BoxNolist.clear();
        if(!ipAddress.equals(""))
        new JSONTask_getAllPOboxNO().execute();
        else
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();


    }


    public void getPOdetails() {
        Log.e("getPOdetails","getPOdetails");
        //new JSONTaskGetPOdetails(context,cono,pono).execute();
        POdetailslist.clear();
        if(!ipAddress.equals(""))
        new JSONTask_getAllPOdetails().execute();
else
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
    }

    private void getIpAddress() {
        headerDll="";
        ipAddress=my_dataBase.settingDao().getIpAddress().trim();
        CONO=my_dataBase.settingDao().getCono().trim();
        Log.e("getIpAddress",""+ipAddress);


    }
    public  void getAllZones(){
        zoneinfo = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        zoneinfo.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        zoneinfo.setTitleText(" Start get Zones Info");
        zoneinfo.setCancelable(false);
        WebSerlistAllZone.clear();
        zoneinfo.show();
        if(!ipAddress.equals(""))
        {
            new JSONTask_getAllZoneCode().execute();
        }
        else {
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
        }

    }
    public  void getZones(){
        if(!ipAddress.equals(""))
        {
            new JSONTask_getZone().execute();
        }
        else {
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
        }

    }
    public void getKindItem(String itemNo) {
        linkbarcode="";
        Log.e("getKindItem",""+itemNo);
        if(!ipAddress.equals(""))
        {
            new JSONTask_getItemKind(itemNo).execute();
        }
        else {
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
        }
    }
    public void New_getAllPoDetalis() {


        POs_progressDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        POs_progressDialog.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        POs_progressDialog.setTitleText(" Start get POs details");
        POs_progressDialog.setCancelable(false);
        POs_progressDialog.show();

        Log.e("New_getAllPoDetalis","New_getAllPoDetalis");

        if(!ipAddress.equals(""))
        {
            new JSONTaskNew_getAllPoDetalis().execute();
        }
        else {
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
        }
    }
    public void New_getAllZons() {
        Zons_progressDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        Zons_progressDialog.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        Zons_progressDialog.setTitleText(" Start get POs details");
        Zons_progressDialog.setCancelable(false);
        Zons_progressDialog.show();

        if(!ipAddress.equals(""))
        {
            new JSONTaskNew_getAllZons().execute();
        }
        else {
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
        }
    }

    public void getKindItem2(String itemNo) {
        if(!ipAddress.equals(""))
        {
            new JSONTask_getItemKind2(itemNo).execute();
        }
        else {
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCompanyInfo() {
        companyInList.clear();
        if(!ipAddress.equals(""))
        {
            try {
                new JSONTask_getCompanyInfo().execute();
            }
            catch (Exception e)
            {}

        }
        else {

            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
        }
    }

    public void updatePoClosed(String poNumber) {
        getJsnStr(poNumber);
       new JSONTask_UpdatePoClose(poNumber).execute();
    }

    private void getJsnStr(String poNumber) {

            jsonArrayPo = new JSONArray();
            JSONObject object=new JSONObject();
        try {
            object.put("PONO",poNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonArrayPo.put(object);


            try {
                stringNoObject=new JSONObject();
                stringNoObject.put("JSN",jsonArrayPo);
                Log.e("vouchersObject",""+jsonArrayPo.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }




    }

    public class JSONTask_UpdatePoClose extends AsyncTask<String, String, String> {

        private String poNo = "", JsonResponse;

        public JSONTask_UpdatePoClose(String po) {
            this.poNo = po;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {

                    //   http://localhost:8082/IrGetItemData?CONO=290&ITEMCODE=28200152701

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/UPDATEPO" ;
                    Log.e("linkUpdatePo", "" + link);
                }
            } catch (Exception e) {
                //progressDialog.dismiss();
                Toast.makeText(context, R.string.fill_setting, Toast.LENGTH_SHORT).show();

            }



                //*************************************
                String ipAddress = "",JsonResponse="";
                Log.e("UPDATEPO", "JsonResponseEXPORTDROPPRICE");


                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost request = new HttpPost();
                    try {
                        request.setURI(new URI(link));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("CONO", CONO.trim()));
                    nameValuePairs.add(new BasicNameValuePair("JSONSTR", stringNoObject.toString().trim()));
                     Log.e("nameValuePairs","JSONSTR"+stringNoObject.toString().trim());


                    request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                    HttpResponse response = client.execute(request);


                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(response.getEntity().getContent()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }

                    in.close();


                    JsonResponse = sb.toString();
                    Log.e("JsonResponse", "ExporVoucher" + JsonResponse);



                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });



            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                return null;
            }
            return JsonResponse;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("onPostExecute",""+result.toString());
            if (result != null) {





            }

        }
    }
    public class JSONTask_getItemKind extends AsyncTask<String, String, String> {

        private String itemNo = "", JsonResponse;

        public JSONTask_getItemKind(String itemNo) {
            this.itemNo = itemNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {

                 //   http://localhost:8082/IrGetItemData?CONO=290&ITEMCODE=28200152701

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetItemData?CONO=" + CONO.trim()+"&&ITEMCODE="+itemNo.trim();
                    linkbarcode=itemNo.trim();
                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
Log.e("Exception===",e.getMessage());
            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", "itemNo"+finalJson);



                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                validateKind = false;
                if (result.contains("ITEMTYPE")) {
                    try {
                        ZoneModel requestDetail=new ZoneModel();
                        JSONArray requestArray = null;
                        requestArray =  new JSONArray(result);


                        for (int i = 0; i < requestArray.length(); i++) {
                            JSONObject infoDetail = requestArray.getJSONObject(i);
                            requestDetail = new ZoneModel();
                            requestDetail.setZONETYPE(infoDetail.get("ITEMTYPE").toString());
                            requestDetail.setZoneCode(infoDetail.get("ITEMCODE").toString());
                            requestDetail.setZONENAME(infoDetail.get("ITEMNAME").toString());
                           barcode= infoDetail.get("ITEMCODE").toString();
                        }
                        itemKind=requestDetail.getZONENAME();
                        zonetype=requestDetail.getZONETYPE();
                        Log.e("itemKind",""+itemKind);
                        if(MainActivity.setflage==0)
                        itemKintText.setText(requestDetail.getZONETYPE());
                        else
                        if(MainActivity.setflage == 1)
                        itemKintText1.setText(requestDetail.getZONETYPE());

                    } catch (JSONException e) {
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }
                else
                {
                     if(MainActivity.setflage==0)
                    itemKintText.setText("NOTEXIST");
                    else
                     if(MainActivity.setflage == 1)
                        itemKintText1.setText("NOTEXIST");
                }





            }
            else {
                itemKintText.setText("ErrorNet");
            }
        }
    }

    public class JSONTask_getCompanyInfo extends AsyncTask<String, String, String> {

        private String itemNo = "", JsonResponse;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                if (!ipAddress.equals("")) {
                    //http://localhost:8082/IrGetCoYear

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetCoYear";
                    Log.e("link", "" + link);
                }
            } catch (Exception e) {

            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", "Company" + finalJson);



                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null ) {
                // {
                //    "CoNo": "200",
                //    "CoYear": "2021",
                //    "CoNameA": "Al Rayyan Plastic Factory 2017"
                //  },
                companyInList.clear();
                if (result.contains("CoNo")) {
                    try {
                        CompanyInfo requestDetail = new CompanyInfo();
                        JSONArray requestArray = null;
                        requestArray = new JSONArray(result);
                        companyInList = new ArrayList<>();


                        for (int i = 0; i < requestArray.length(); i++) {
                            JSONObject infoDetail = requestArray.getJSONObject(i);
                            requestDetail = new CompanyInfo();
                            requestDetail.setCoNo(infoDetail.get("CoNo").toString());
                            requestDetail.setCoYear(infoDetail.get("CoYear").toString());
                            requestDetail.setCoNameA(infoDetail.get("CoNameA").toString());

                            companyInList.add(requestDetail);
                        }
                        if (companyInList.size() != 0) {
                        //    getListCom.setText("fill");
                        }

                        Login.comRespon.setText("CoNo");
//                            itemKintText.setText(requestDetail.getZONETYPE());


                    } catch (JSONException e) {
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }


            }
            else {

            }
        }
    }

    private class JSONTask_getAllZoneCode extends AsyncTask<String, String, JSONArray> {

        private String custId = "", JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected JSONArray doInBackground(String... params) {

           try {
                if (!ipAddress.equals("")) {
                    //http://localhost:8082/IrGetAllZone?CONO=290

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetAllZone?CONO=" + CONO.trim();
                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
               zoneinfo.dismiss();
            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


                JSONArray parentObject = new JSONArray(finalJson);

                return parentObject;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex)

            {
                ex.printStackTrace();
//                progressDialog.dismiss();
                zoneinfo.dismiss();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
        }
         catch (Exception e)

            {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
//                progressDialog.dismiss();
                zoneinfo.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(JSONArray array) {
            super.onPostExecute(array);
            zoneinfo.dismiss();
            JSONObject result = null;


            if (array != null ) {
                if (array.length() != 0) {


                    for (int i = 0; i < array.length(); i++) {
                        try {
                            result = array.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ZonsData itemZone = new ZonsData();
                        try {
                            itemZone.setZoneCode(result.getString("ZONENO"));
                            itemZone.setZONENAME(result.getString("ZONENAME"));
                            itemZone.setZONETYPE(result.getString("ZONETYPE"));

                            WebSerlistAllZone.add(itemZone);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    MainActivity.getzonerespon.setText("ZONENO");

                }
            }
        }
    }



    /////////
    private class JSONTask_getAllPOdetails extends AsyncTask<String, String, String> {

        private String custId = "", JsonResponse;
        Shipment shipment;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {
                    //http://localhost:8082/IrGetAllZone?CONO=290

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetItemInfo?CONO=" + CONO.trim() + "&PONO=" + poNo.trim() + "&ITEMCODE=" + convertToEnglish(NewShipment.barCode.trim());

                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                Log.e("getAllPOdetails", e.getMessage());
            }

//                } catch (Exception e) {
//            Log.e("getAllPOdetails",e.getMessage());
//                }
//
            try {
//
//                //*************************************
//
                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


                //JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String array) {
            super.onPostExecute(array);

            JSONObject jsonObject1 = null;

            if (array != null) {
                if (array.length() != 0) {
                    if (array.contains("ItemOCode")) {


                        try {
                            JSONArray requestArray = null;
                            requestArray = new JSONArray(array);

                            for (int i = 0; i < requestArray.length(); i++) {

                                shipment = new Shipment();
                                jsonObject1 = requestArray.getJSONObject(i);
                                shipment.setBarcode(jsonObject1.getString("ItemOCode"));
                                shipment.setPoqty(jsonObject1.getString("Qty"));
                                shipment.setItemname(jsonObject1.getString("ItemNameA"));
                               shipment.setBoxNo(jsonObject1.getString("Hints"));
                            //    shipment.setBoxNo("6666");

                                //POdetailslist.add(shipment);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        NewShipment.respon.setText("ItemOCode");

                    }
                    else
                    {
                        NewShipment.respon.setText("invlalid");
                    }
                }
            }
            else{
                NewShipment.respon.setText("Nointernet");
            }

        }

    }
    private class JSONTask_getAllPOboxNO extends AsyncTask<String, String, String> {

        private String custId = "", JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            Log.e("onPreExecute", "onPreExecute");
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {


                   link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetBOXNO?CONO=" + CONO.trim() + "&PONO=" + convertToEnglish(poNo.trim());

                    Log.e("boxlink", "" + link);
                }
            } catch (Exception e) {
                Log.e("Exception",""+e.getMessage());
            }


            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


//                JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String respon) {
            super.onPostExecute(respon);

            JSONObject jsonObject1 = null;


            if (respon != null) {
                if (respon.length() != 0) {
                    if (respon.contains("BOXNO")) {
                        JSONArray array = null;


                        try {
                            array = new JSONArray(respon);



                        if (array.length()>0)

                            for (int i = 0; i < array.length(); i++) {
                            try {
                                jsonObject1 = array.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {

                                BoxNolist.add(jsonObject1.getString("BOXNO"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        NewShipment.boxnorespon.setText("BOXNO");    }
                    else if (respon.contains("No Parameter Found")) {
                        NewShipment.boxnorespon.setText("Not");
                    }
                    }

                }//nuul

        }

    }
        private class JSONTask_getAllStoreData extends AsyncTask<String, String, String> {
         int   actvityflage;

            public JSONTask_getAllStoreData(final int actvityflage) {
                this.actvityflage = actvityflage;
            }

            Store store;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                String do_ = "my";

            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    if (!ipAddress.equals("")) {
                        http:
//http://10.0.0.22:8082/Getsore?CONO=304

                        link = "http://" + ipAddress.trim() + headerDll.trim() + "/Getsore?CONO="+CONO.trim();

                        Log.e("link", "" + link);
                    }
                } catch (Exception e) {
                    Log.e("getAllSto", e.getMessage());
                    storeinfo.dismiss();
                }

                try {

                    //*************************************

                    String JsonResponse = null;
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(link));

//

                    HttpResponse response = client.execute(request);


                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(response.getEntity().getContent()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";
                    Log.e("finalJson***Import", sb.toString());

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }

                    in.close();


                    // JsonResponse = sb.toString();

                    String finalJson = sb.toString();


                    //JSONArray parentObject = new JSONArray(finalJson);

                    return finalJson;


                }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
                catch (HttpHostConnectException ex) {
                    ex.printStackTrace();
//                progressDialog.dismiss();

                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable() {
                        public void run() {

                            Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                            storeinfo.dismiss();
                        }
                    });


                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Exception", "" + e.getMessage());
                    storeinfo.dismiss();
//                progressDialog.dismiss();
                    return null;
                }


                //***************************

            }

            @Override
            protected void onPostExecute(String array) {
                super.onPostExecute(array);
                storeinfo.dismiss();
                JSONObject jsonObject1 = null;
                if (array != null) {
                    if (array.contains("STORENO")) {

                            if (array.length() != 0) {
                                try {
                                    JSONArray requestArray = null;
                                    requestArray = new JSONArray(array);
                                    Storelist.clear();

                                    for (int i = 0; i < requestArray.length(); i++) {
                                        store = new Store();
                                        jsonObject1 = requestArray.getJSONObject(i);
                                        store.setSTORENO(jsonObject1.getString("STORENO"));
                                        store.setSTORENAME(jsonObject1.getString("STORENAME"));
                                        store.setSTOREKIND(jsonObject1.getString("STOREKIND"));
                                        Storelist.add(store);
                                    }
                              if( actvityflage==1)
                                  Replacement.respon.setText("fill");
                              else if(actvityflage==3)
                                  ReplenishmentReverse.RepRev_storrespon.setText("fill");
                              else if(actvityflage==4)
                                  AddZone.storrespon.setText("fill");
                              else if(actvityflage==5)
                                  ZoneReplacment.storrespon.setText("fill");

                                  else
                                  Stoketake.respone.setText("fill");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }



                    }
                } else {

                    if( actvityflage==1)
                        Replacement.respon.setText("nodata");

                    else if(actvityflage==3)  ReplenishmentReverse.RepRev_storrespon.setText("nodata");
                    else if(actvityflage==4)
                        AddZone.storrespon.setText("nodata");
                    else if(actvityflage==5)
                        ZoneReplacment.storrespon.setText("fill");

                    else
                           Stoketake.respone.setText("nodata");

                }
            }



    }



    private class JSONTask_getQTYOFZone extends AsyncTask<String, String, String> {

        private String custId = "", JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {
                    // http://10.0.0.22:8082/GetZoneDatInfo?CONO=290&ZONENO=6&ITEMCODE=6253349404082

                    if(fromZoneRepActivity==0)    link = "http://" + ipAddress.trim() + headerDll.trim() + "/GetZoneDatInfo?CONO=" + CONO.trim()+"&ZONENO="+zone.getText().toString().trim()+"&ITEMCODE="+itemcode.getText().toString().trim();
                else
                        link = "http://" + ipAddress.trim() + headerDll.trim() + "/GetZoneDatInfo?CONO=" + CONO.trim()+"&ZONENO="+fromzone.getText().toString().trim()+"&ITEMCODE="+RZ_itemcode.getText().toString().trim();


                    //    link ="http://10.0.0.22:8082/GetZoneDatInfo?CONO=304&ZONENO=C03D&ITEMCODE=8058578435856";
                    Log.e("link", "" + link);
                }
            } catch (Exception e) {

            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


               // JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String array) {
            super.onPostExecute(array);
           String d="";
            JSONObject jsonObject1 = null;

            if (array != null) {
                if (array.contains("QTY")) {

                    if (array.length() != 0) {
                        try {

                            JSONArray requestArray = null;
                            requestArray = new JSONArray(array);

                            for (int i = 0; i < requestArray.length(); i++) {

                                ZoneModel zoneModel = new ZoneModel();
                                jsonObject1 = requestArray.getJSONObject(i);
                                zoneModel.setZoneCode(jsonObject1.getString("ZONENO"));
                                zoneModel.setItemCode(jsonObject1.getString("ITEMCODE"));
                                zoneModel.setQty(jsonObject1.getString("QTY"));
                                d=jsonObject1.getString("QTY");
                               // listQtyZone.add(zoneModel);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
               if(fromZoneRepActivity==0)   {  Replacement.qty.setText(d);
                    qtyrespons.setText("QTY");}
               else
               {
                   ZR_respon.setText("QTY");
               }


                    }
                else {

                    if(fromZoneRepActivity==0)   {   qtyrespons.setText("nodata");}

                    else
                    {
                        ZR_respon.setText("nodata");
                    }

                }

            }
            else {
                if(fromZoneRepActivity==0)
                    qtyrespons.setText("nodata");
                else
                {
                    ZR_respon.setText("nodata");
                }
            }
        }

    }


    private class  JSONTask_getAllPoNum extends AsyncTask<String, String, String> {

        private String custId = "", JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            Log.e("onPreExecute", "onPreExecute");
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {


                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/GetAllOPO?CONO=" + CONO.trim();

                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                Log.e("Exception",""+e.getMessage());
            }


            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


//                JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String respon) {
            super.onPostExecute(respon);
            max=100;
            JSONObject jsonObject1 = null;


            if (respon != null) {
                if (respon.length() != 0) {

                    if (respon.contains("Not Connected To DB")){
                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {

                                Toast.makeText(context, "Not Connected To DB", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                   else if (respon.contains("PONO")) {
                        JSONArray array = null;


                        try {
                            array = new JSONArray(respon);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (array.length()>0)

                            for (int i = 0; i < array.length(); i++) {

                            try {

                                jsonObject1 = array.getJSONObject(i);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                AllPOs allPOs = new AllPOs();
                                allPOs.setPoNum(jsonObject1.getString("PONO"));
                                PoNolist.add(allPOs);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        PONO_respon.setText("PONO");
                     }

                }

            }

        }

    }

    private class  JSONTask_getItemInfo extends AsyncTask<String, String, String> {

        private String custId = "", JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            Log.e("onPreExecute", "onPreExecute");
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {


                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetInfo?CONO=" + CONO.trim()+"&ITEMCODE="+ItC_itemcode.getText().toString().trim();

                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                Log.e("Exception",""+e.getMessage());
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        //  pdVoucher.dismissWithAnimation();
                        //hide Progressbar after finishing process
                        pditeminfo.dismiss();
                        Toast.makeText(context, "check Connection", Toast.LENGTH_SHORT).show();
                    }
                });

            }


            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


//                JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();
                pditeminfo.dismiss();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        pditeminfo.dismiss();
                       // hideProgressDialogWithTitle();
                        Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("itemException3==", "" + e.getMessage());
             //   hideProgressDialogWithTitle();
             pditeminfo.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String respon) {
            super.onPostExecute(respon);
            String d="";
            pditeminfo.dismiss();
            JSONObject jsonObject1 = null;



            if (respon != null) {
                if (respon.contains("ITEMCODE")) {

                    if (respon.length() != 0) {
                        try {

                            JSONArray requestArray = null;
                            requestArray = new JSONArray(respon);

                            for (int i = 0; i < requestArray.length(); i++) {

                               ItemInfo itemInfo= new ItemInfo ();
                                jsonObject1 = requestArray.getJSONObject(i);
                                itemInfo.setItemcode(jsonObject1.getString("ITEMCODE"));
                                itemInfo.setITEMNAME(jsonObject1.getString("ITEMNAME"));
                                itemInfo.setTAXPERC(jsonObject1.getString("TAXPERC"));
                                itemInfo.setAVGCOST(jsonObject1.getString("AVGCOST"));

                                itemInfo.setSALEPRICE(jsonObject1.getString("SALEPRICE"));
                                itemInfo.setLLCPRICE(jsonObject1.getString("LLCPRICE"));
                                itemInfo.setF_D(jsonObject1.getString("F_D"));
                                itemInfo.setLASTSPRICE(jsonObject1.getString("LASTSPRICE"));


                                itemInfo.setBUYERGGROUP(jsonObject1.getString("BUYERGGROUP"));
                                itemInfo.setDIVISION(jsonObject1.getString("DIVISION"));
                                itemInfo.setSUBDIVISION(jsonObject1.getString("SUBDIVISION"));
                                itemInfo.setCLASS(jsonObject1.getString("CLASS"));

                                itemInfo.setSSIZE(jsonObject1.getString("SSIZE"));
                                itemInfo.setSEASON(jsonObject1.getString("SEASON"));
                                itemInfo.setSTYLECODE(jsonObject1.getString("STYLECODE"));
                                itemInfo.setSECTION(jsonObject1.getString("SECTION"));


                                itemInfo.setBRANDNAME(jsonObject1.getString("BRANDNAME"));
                                itemInfo.setCOLORNAME(jsonObject1.getString("COLORNAME"));
                                itemInfo.setLENGTH(jsonObject1.getString("LENGTH"));
                                itemInfo.setCOLORCODE(jsonObject1.getString("COLORCODE"));

                                itemInfo.setZONE(jsonObject1.getString("ZONE"));
                                itemInfo.setSHELF(jsonObject1.getString("SHELF"));
                                itemInfo.setFREEZ(jsonObject1.getString("FREEZ"));


                                itemInfos .add(itemInfo);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                    itemRES.setText("ItemOCode");


                }
                else {

                    itemRES.setText("nodata");


                }

            }
            else {
                itemRES.setText("NoInterNet");

            }
        }


    }

    private class  JSONTask_getAllItems extends AsyncTask<String, String, String> {

        Context context;
        int flag;

        public JSONTask_getAllItems(final Context context, final int flag) {
            this.flag = flag;
            if (flag == 0) {
                this.context = (MainActivity) context;
            } else if (flag == 1) {
                this.context = (Stoketake) context;
            } else if (flag == 2) {
                this.context = (ReplenishmentReverse) context;
            }
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
                      Log.e("onPreExecute", "onPreExecute");
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {


                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetAllItems?CONO=" + CONO.trim();

                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                Log.e("itemException4===",""+e.getMessage());
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                      //  pdVoucher.dismissWithAnimation();
                        //hide Progressbar after finishing process
                        pditem.dismissWithAnimation();
                      //  Toast.makeText(context, "check Connection", Toast.LENGTH_SHORT).show();
                    }
                });

            }


            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());
                Log.e("in==", in.lines()+"");
                int y=0;
               while ((line = in.readLine()) != null ) {
              sb.append(line);

                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


//                JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {

              //  The target server failed to respond
              Log.e("itemExceptionhere==",ex.getMessage()+"");
//                progressDialog.dismiss();

                Handler h2 = new Handler(Looper.getMainLooper());
                h2.post(new Runnable() {
                    public void run() {
                        pditem.dismissWithAnimation();
                        Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();

                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("itemException2===", "" + e.getMessage());


                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        //  pdVoucher.dismissWithAnimation();
                        //hide Progressbar after finishing process
                        pditem.dismissWithAnimation();
                        // The target server failed to respond
                      //  Toast.makeText(MainActivity.this, "The target server failed to respond", Toast.LENGTH_SHORT).show();
                        showSweetDialog(context, 0, "The target server failed to respond", "");


                    }
                });

//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String respon) {
            super.onPostExecute(respon);
            String d="";
            JSONObject jsonObject1 = null;
try {
    Handler h = new Handler(Looper.getMainLooper());
    h.post(new Runnable() {
        public void run() {

            pditem.dismissWithAnimation();
        }
    });
}catch (Exception e){

}
            if (respon != null) {

                if(respon.contains("Internal Application Error")){

                    if(flag==0)       itemrespons.setText("Internal Application Error");
                    else     if(flag==1)     St_Itemrespons.setText("Internal Application Error");

                    else    if(flag==2) RepRev_Itemrespons.setText("Internal Application Error");


                }else
                if (respon.contains("ItemOCode")) {

                    if (respon.length() != 0) {
                        try {

                            JSONArray requestArray = null;
                            requestArray = new JSONArray(respon);

                            for (int i = 0; i < requestArray.length(); i++) {

                               AllItems allItems= new  AllItems ();
                                jsonObject1 = requestArray.getJSONObject(i);
                                allItems.setItemOCode(jsonObject1.getString("ItemOCode"));
                                allItems.setItemNCode(jsonObject1.getString("ItemNCode"));
                                allItems.setItemNameE(jsonObject1.getString("ItemNameE"));
                                allItems.setItemNameA(jsonObject1.getString("ItemNameA"));


                             AllImportItemlist.add(allItems);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

             if(flag==0)       itemrespons.setText("ItemOCode");
            else     if(flag==1)     St_Itemrespons.setText("ItemOCode");

                else    if(flag==2) RepRev_Itemrespons.setText("ItemOCode");

                }
                else {

                    if(flag==0)  itemrespons.setText("nodata");
                    else if(flag==1)     St_Itemrespons.setText("nodata");
                    else    if(flag==2) RepRev_Itemrespons.setText("nodata");

                }

            }
            else {
                if(flag==0)
                    itemrespons.setText("nodata");
                else if(flag==1)  St_Itemrespons.setText("nodata");
                else    if(flag==2)RepRev_Itemrespons.setText("nodata");
            }
        }


    }
    private class JSONTask_getZone extends AsyncTask<String, String, JSONArray> {

        private String custId = "", JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected JSONArray doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {
                    //http://localhost:8082/IrGetAllZone?CONO=290

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetAllZone?CONO=" + CONO.trim();
                    Log.e("link", "" + link);
                }
            } catch (Exception e) {

            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


                JSONArray parentObject = new JSONArray(finalJson);

                return parentObject;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(JSONArray array) {
            super.onPostExecute(array);

            JSONObject result = null;


            if (array != null ) {
                if (array.length() != 0) {


                    for (int i = 0; i < array.length(); i++) {
                        try {
                            result = array.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ZoneModel itemZone = new ZoneModel();
                        try {
                            itemZone.setZoneCode(result.getString("ZONENO"));
                            itemZone.setZONENAME(result.getString("ZONENAME"));
                            itemZone.setZONETYPE(result.getString("ZONETYPE"));

                            Zoneslist.add(itemZone);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                }
            }
        }
    }
    public class JSONTask_getItemKind2 extends AsyncTask<String, String, String> {

        private String itemNo = "", JsonResponse;

        public JSONTask_getItemKind2(String itemNo) {
            this.itemNo = itemNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {

                    //   http://localhost:8082/IrGetItemData?CONO=290&ITEMCODE=28200152701

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetItemData?CONO=" + CONO.trim()+"&&ITEMCODE="+convertToEnglish(itemNo.trim());
                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                Log.e("Exception===",e.getMessage());
            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", "itemNo"+finalJson);



                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                if (result.contains("ITEMTYPE")) {
                    try {
                        ZoneModel requestDetail=new ZoneModel();
                        JSONArray requestArray = null;
                        requestArray =  new JSONArray(result);


                        for (int i = 0; i < requestArray.length(); i++) {
                            JSONObject infoDetail = requestArray.getJSONObject(i);
                            requestDetail = new ZoneModel();
                            requestDetail.setZONETYPE(infoDetail.get("ITEMTYPE").toString());
                            requestDetail.setZoneCode(infoDetail.get("ITEMCODE").toString());
                            requestDetail.setZONENAME(infoDetail.get("ITEMNAME").toString());
                            itemdetalis.add( requestDetail);
                        }


                    } catch (JSONException e) {
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }

                    ZR_itemkind.setText("ITEMTYPE");
                }
                else
                {
                    ZR_itemkind.setText("NOTEXSISTS");
                }





            }
            else {
                ZR_itemkind.setText("NetworkError");
            }
        }
    }


    public class  JSONTask_getAllUserPermissions extends AsyncTask<String, String, String> {

        private String itemNo = "", JsonResponse;
       int flag;
        public JSONTask_getAllUserPermissions( final int flag) {


            this.flag = flag;
            this.itemNo = itemNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {

                    //  http://localhost:8085/GetIRUsers
                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/GetIRUsers";
                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                Log.e("Exception===", e.getMessage());
                pdUserPer.dismiss();
            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", "itemNo" + finalJson);


                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();
                pdUserPer.dismiss();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();

                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                pdUserPer.dismiss();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pdUserPer.dismiss();
            if (result != null) {
                if (result.contains("USERNO")) {
                    try {


//                            "COSTORE1":"1;2;",
//                                "COSTORE2":"","COSTORE3":"3;10;","COSTORE4":"","COSTORE5":"",
//                                "COSTORE6":"","COSTORE7":"","COSTORE8":"","COSTORE9":"","COSTORE10":"2;3;"}

                        Log.e("here UserPermissions1"," UserPermissions1");
                        UserPermissions requestDetail = new UserPermissions();
                        JSONArray requestArray = null;
                        requestArray = new JSONArray(result);


                        for (int i = 0; i < requestArray.length(); i++) {
                            Log.e("here UserPermissions2"," UserPermissions2");
                            JSONObject infoDetail = requestArray.getJSONObject(i);

                            requestDetail = new UserPermissions();
                            requestDetail.setUserNO(infoDetail.get("USERNO").toString());
                            requestDetail.setUserName(infoDetail.get("USERNAME").toString());
                            requestDetail.setUserPassword(infoDetail.get("PASSWORD").toString());


                            requestDetail.setCONO1(infoDetail.get("CONO1").toString());
                            requestDetail.setCONO2(infoDetail.get("CONO2").toString());


                            requestDetail.setCONO3(infoDetail.get("CONO3").toString());
                            requestDetail.setCONO4(infoDetail.get("CONO4").toString());
                            requestDetail.setCONO5(infoDetail.get("CONO5").toString());
                            requestDetail.setCONO6(infoDetail.get("CONO6").toString());
                            requestDetail.setCONO7(infoDetail.get("CONO7").toString());


                            requestDetail.setCONO8(infoDetail.get("CONO8").toString());
                            requestDetail.setCONO9(infoDetail.get("CONO9").toString());
                            requestDetail.setCONO10(infoDetail.get("CONO10").toString());
                            requestDetail.setUserActive(infoDetail.get("UACTIVE").toString());
                            requestDetail.setMasterUser(infoDetail.get("UMASTER").toString());


                            requestDetail.setSHIP_Open(infoDetail.get("NOPEN").toString());
                            requestDetail.setSHIP_Save(infoDetail.get("NFINISH").toString());
                            requestDetail.setSHIP_LocalDelete(infoDetail.get("NDELETE").toString());


                            requestDetail.setAddZone_Open(infoDetail.get("ZOPEN").toString());
                            requestDetail.setAddZone_Save(infoDetail.get("ZSAVE").toString());
                            requestDetail.setAddZone_LocalDelete(infoDetail.get("ZDELETE").toString());


                            requestDetail.setRep_Open(infoDetail.get("ROPEN").toString());
                            requestDetail.setRep_Save(infoDetail.get("RSAVE").toString());
                            requestDetail.setRep_LocalDelete(infoDetail.get("RDELETE").toString());



                            requestDetail.setZoneRep_Open(infoDetail.get("ZTOPEN").toString());
                            requestDetail.setZoneRep_Save(infoDetail.get("ZTSAVE").toString());
                            requestDetail.setZoneRep_LocalDelete(infoDetail.get("ZTDELETE").toString());
                            requestDetail.setZoneRep_UpdateQty(infoDetail.get("ZTUPDATEQTY").toString());


                            requestDetail.setStockTake_Open(infoDetail.get("STOPEN").toString());
                            requestDetail.setStockTake_Save(infoDetail.get("STSAVE").toString());
                            requestDetail.setStockTake_LocalDelete(infoDetail.get("STDELETE").toString());
                            requestDetail.setStockTake_UpdateQty(infoDetail.get("STUPDATEQTY").toString());


                            requestDetail.setSetting_Per(infoDetail.get("OSETTINGS").toString());
                            requestDetail.setExport_Per(infoDetail.get("OEXPORT").toString());
                            requestDetail.setImport_Per(infoDetail.get("OIMPORT").toString());
                            requestDetail.setSH_RepOpen(infoDetail.get("RSHIPMENT").toString());

                            requestDetail.setST_RepOpen(infoDetail.get("RSTOCKTAKE").toString());
                            requestDetail.setRevRep_Open(infoDetail.get("OREVERSE").toString());
                            requestDetail.setVIEWCost(infoDetail.get("OVIEWCOST").toString());

                            ///STORE PER
                            requestDetail.setCOSTORE1(infoDetail.get("COSTORE1").toString());
                            requestDetail.setCOSTORE2(infoDetail.get("COSTORE2").toString());
                            requestDetail.setCOSTORE3(infoDetail.get("COSTORE3").toString());
                            requestDetail.setCOSTORE4(infoDetail.get("COSTORE4").toString());
                            requestDetail.setCOSTORE5(infoDetail.get("COSTORE5").toString());

                            requestDetail.setCOSTORE6(infoDetail.get("COSTORE6").toString());
                            requestDetail.setCOSTORE7(infoDetail.get("COSTORE7").toString());
                            requestDetail.setCOSTORE8(infoDetail.get("COSTORE8").toString());
                            requestDetail.setCOSTORE9(infoDetail.get("COSTORE9").toString());
                            requestDetail.setCOSTORE10(infoDetail.get("COSTORE10").toString());
                           // requestDetail.setRepRev_LocalDelete(infoDetail.get("OREVERSE").toString());

                            UserPermissions.add(requestDetail);
                            Log.e("here UserPermissions"," UserPermissions");
                        }
                if(flag==2){
                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable() {
                        public void run() {
                           Login.dialog.dismiss();
                            showSweetDialog(context,1,"","Data Refresh Sucessfuly");
                        }
                    });

                }

                    }
                    catch (JSONException e) {
//                        progressDialog.dismiss();
                        Log.e("JSONException", e.getMessage());
                        e.printStackTrace();
                    }

                    Login.UserperRespons.setText("USERNO");
                } else {
                    Login. UserperRespons.setText("NetworkError");
                }


            } else {
                Login.  UserperRespons.setText("NetworkError");
            }
        }
    }

    private class JSONTask_GetItemCountInSTR extends AsyncTask<String, String, String> {

        private String custId = "", JsonResponse;
        Shipment shipment;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {
                    http://10.0.0.22:8085/IrGetItemCountInSTR?CONO=305&ITEMCODE=8031295530769

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetItemCountInSTR?CONO=" + CONO.trim() + "&ITEMCODE=" + ItC_itemcode.getText().toString().trim();

                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                Log.e("", e.getMessage());
            }

//                } catch (Exception e) {
//            Log.e("getAllPOdetails",e.getMessage());
//                }
//
            try {
//
//                //*************************************
//
                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


                //JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String array) {
            super.onPostExecute(array);

            JSONObject jsonObject1 = null;

            if (array != null) {
                if (array.length() != 0) {
                    if (array.contains("Stock_Code")) {


                        try {
                            JSONArray requestArray = null;
                            requestArray = new JSONArray(array);

                            for (int i = 0; i < requestArray.length(); i++) {

                                ReplacementModel replacementModel= new ReplacementModel();
                                jsonObject1 = requestArray.getJSONObject(i);
                                replacementModel.setFrom(jsonObject1.getString("Stock_Code"));
                                replacementModel.setFromName(jsonObject1.getString("Stock_NameA"));
                                replacementModel.setRecQty(jsonObject1.getString("QTY"));
                                stocksQty.add(replacementModel);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        stockqrtRes.setText("Stock_Code");
                    }
                    else
                    {

                    }
                }
            }
            else{

            }

        }

    }

    //new for offline version
    private class JSONTaskNew_getAllZons extends AsyncTask<String, String, JSONArray> {

        private String custId = "", JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected JSONArray doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {
                    //http://10.0.0.22:8085/IrGetAllZoneDataW?CONO=304

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetAllZoneDataW?CONO=" +CONO.trim();
                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                Zons_progressDialog.dismiss();
            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


                JSONArray parentObject = new JSONArray(finalJson);

                return parentObject;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
                Zons_progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                Zons_progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(JSONArray array) {
            super.onPostExecute(array);

            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                public void run() {

                    Zons_progressDialog.dismiss();         }
            });
            JSONObject result = null;

            NewZoneslist.clear();
            if (array != null ) {
                if (array.length() != 0) {
                    Respon_arrayList.add(1);

                    for (int i = 0; i < array.length(); i++) {
                        try {
                            result = array.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        NewZonsData itemZone = new NewZonsData();
                        try {

                            itemZone.setZONENO(result.getString("ZONENO"));
                            itemZone.setITEMCODE(result.getString("ITEMCODE"));
                            itemZone.setQTY(result.getString("QTY"));

                            NewZoneslist.add(itemZone);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    my_dataBase.newZonsDataDao().deleteAll();
                    my_dataBase.newZonsDataDao().insertAll(NewZoneslist);

                }  else   Respon_arrayList.add(0);
            } else   Respon_arrayList.add(0);
if(!Respon_arrayList.contains(0))
            showSweetDialog(context, 1, "Done,All data is stored", "");
else {
   if(Respon_arrayList.size()==3) {
       if (Respon_arrayList.get(0).equals(1))
           msgbuilder1 = "-Items Data Saved \n";
       else
           msgbuilder1 = "-Items Data does Not Saved \n";
       if (Respon_arrayList.get(1).equals(1))
           msgbuilder2 = "-Po's Info's Saved \n";
       else
           msgbuilder2 = "-Po's Data does Not Saved \n";
       if (Respon_arrayList.get(2).equals(1))
           msgbuilder3 = "-Zone Data Saved \n";
       else
           msgbuilder3 = "-Zone Data does Not Saved \n";
   }
    if(Respon_arrayList.size()==2){
        if (Respon_arrayList.get(0).equals(1))
            msgbuilder2 = "-Po's Info's Saved \n";
        else
            msgbuilder2 = "-Po's Data does Not Saved \n";
        if (Respon_arrayList.get(1).equals(1))
            msgbuilder3 = "-Zone Data Saved \n";
        else
            msgbuilder3 = "-Zone Data does Not Saved \n";
    }
    Handler h1 = new Handler(Looper.getMainLooper());
    h1.post(new Runnable() {
        public void run() {

//
//            AlertDialog alertDialog = new AlertDialog.Builder(context)
////set icon
//                    .setIcon(context.getResources().getDrawable(R.drawable.ic_baseline_warning_24))
////set title
//                    .setTitle("")
////set message
//                    .setMessage(msgbuilder)
////set positive button
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    })
//
//
//                    .show();

            openResponDialog(msgbuilder1,msgbuilder2,msgbuilder3);
                  }
    });



   // showSweetDialog(context, 0,msgbuilder+"", "");
}

        }
    }
    private void openResponDialog(String s1,String s2,String s3) {
        final Dialog dialog1 = new Dialog(context);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(false);
        dialog1.setContentView(R.layout.respondailog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());

        lp.gravity = Gravity.CENTER;
        dialog1.getWindow().setAttributes(lp);


        TextView textView1 = dialog1.findViewById(R.id.text1);
        TextView textView2 = dialog1.findViewById(R.id.text2);
        TextView textView3 = dialog1.findViewById(R.id.text3);
        textView1.setText(s1);
       if(s1.contains("Not")) textView1.setTextColor(context.getColor(R.color.red));
        if(s2.contains("Not")) textView2.setTextColor(context.getColor(R.color.red));
        if(s3.contains("Not")) textView3.setTextColor(context.getColor(R.color.red));


        textView2.setText(s2);
        textView3.setText(s3);

        Button donebutton = dialog1.findViewById(R.id.done);

        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    dialog1.dismiss();

            }
        });


        dialog1.show();
    }
    //new for offline version
    private class JSONTaskNew_getAllPoDetalis extends AsyncTask<String, String, JSONArray> {

        private String custId = "", JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected JSONArray doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {
                    //http://10.0.0.22:8085/IrGetPoDTLS?CONO=304

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetPoDTLS?CONO=" + CONO.trim();
                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                POs_progressDialog.dismiss();
            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


                JSONArray parentObject = new JSONArray(finalJson);

                return parentObject;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
                POs_progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
          POs_progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(JSONArray array) {
            super.onPostExecute(array);


            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                public void run() {

                    POs_progressDialog.dismiss();
                }
            });
            JSONObject result = null;
            NewPOdetailslist.clear();

            if (array != null ) {
                if (array.length() != 0) {


                    Respon_arrayList.add(1);

                    for (int i = 0; i < array.length(); i++) {
                        try {
                            result = array.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        NewAllPOsInfo shipment = new NewAllPOsInfo();
                        try {

                            shipment.setPONO(result.getString("PONO"));
                            shipment.setITEMCODE(result.getString("ITEMCODE"));
                            shipment.setQTY(result.getString("QTY"));
                            shipment.setBOXNO(result.getString("BOXNO"));
                            NewPOdetailslist.add(shipment);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                }else   Respon_arrayList.add(0);
            } else Respon_arrayList.add(0);

          New_getAllZons();
            my_dataBase.newAllPOsInfoDao().deleteAll();
            my_dataBase.newAllPOsInfoDao().insertAll(NewPOdetailslist);
        }
    }


    public void AllItems_fetchCallData(int flag) {
        Log.e("AllItems_fetchCallData", "AllItems_fetchCallData" );
        Call<List<AllItems>> myData = myAPI.gatItemInfoDetail(CONO);

        myData.enqueue(new Callback<List<AllItems>>() {
            @Override
            public void onResponse(Call<List<AllItems>> call, Response<List<AllItems>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context,  response.message()+",,call=="+call.request(), Toast.LENGTH_SHORT).show();
                    Log.e("onResponse333", "not=" + response.message());
                    pditem.dismissWithAnimation();
                    if(flag==0)  itemrespons.setText("nodata");
                    else if(flag==1)     St_Itemrespons.setText("nodata");
                    else    if(flag==2) RepRev_Itemrespons.setText("nodata");
                } else {

                    AllImportItemlist.clear();
                    AllImportItemlist.addAll(response.body());
                    if(flag==0)       itemrespons.setText("ItemOCode");
                    else     if(flag==1)     St_Itemrespons.setText("ItemOCode");

                    else    if(flag==2) RepRev_Itemrespons.setText("ItemOCode");
                    Log.e("onResponse", "=" + response.body().get(0).getItemNCode());
                    pditem.dismissWithAnimation();
                }
            }

            @Override
            public void onFailure(Call<List<AllItems>> call, Throwable throwable) {
                Log.e("onFailure===", "" + throwable.getMessage()+",,call=="+call.request());
               Toast.makeText(context, "Items are not saved ,throwable "+throwable.getMessage(), Toast.LENGTH_SHORT).show();

                if(flag==0)  itemrespons.setText("nodata");
                else if(flag==1)     St_Itemrespons.setText("nodata");
                else    if(flag==2) RepRev_Itemrespons.setText("nodata");
                pditem.dismissWithAnimation();
            }
        });
    }

    public void   AllItems_fetchCallData2(int flag ) {
        try {


            Observable observable = myAPI.gatItemInfoDetail2(CONO)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            Observer<List<AllItems>> observer = new Observer<List<AllItems>>() {

                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(List<AllItems> jenralData) {
                    AllImportItemlist.clear();
                    AllImportItemlist.addAll(jenralData);
                    if (flag == 0) itemrespons.setText("ItemOCode");
                    else if (flag == 1) St_Itemrespons.setText("ItemOCode");

                    else if (flag == 2) RepRev_Itemrespons.setText("ItemOCode");
                    Log.e("onResponse", "=" + jenralData.get(0).getItemNCode());
                    pditem.dismissWithAnimation();
                }

                @Override
                public void onError(Throwable e) {

                    Log.e("onError===", e.getMessage() + "");
                 //   Log.e("onFailure===", "" + e.getMessage() + ",,call==" + call.request());
                    Toast.makeText(context, "Items are not saved ,throwable " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    if (flag == 0) itemrespons.setText("nodata");
                    else if (flag == 1) St_Itemrespons.setText("nodata");
                    else if (flag == 2) RepRev_Itemrespons.setText("nodata");
                    pditem.dismissWithAnimation();

                }

                @Override
                public void onComplete() {

                }
            };
            observable.subscribe(observer);


        } catch (Exception e) {
            Log.e("Exception===", e.getMessage() + "");
        }

    }



}









