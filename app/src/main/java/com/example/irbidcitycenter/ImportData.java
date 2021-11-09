package com.example.irbidcitycenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.irbidcitycenter.Activity.Login;
import com.example.irbidcitycenter.Activity.MainActivity;
import com.example.irbidcitycenter.Activity.NewShipment;
import com.example.irbidcitycenter.Activity.Replacement;
import com.example.irbidcitycenter.Activity.ReplenishmentReverse;
import com.example.irbidcitycenter.Activity.Stoketake;
import com.example.irbidcitycenter.Models.AllItems;
import com.example.irbidcitycenter.Models.CompanyInfo;
import com.example.irbidcitycenter.Models.ItemInfo;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.Store;
import com.example.irbidcitycenter.Models.UserPermissions;
import com.example.irbidcitycenter.Models.ZoneModel;

import com.example.irbidcitycenter.Models.Shipment;

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

import static com.example.irbidcitycenter.Activity.AddZone.itemKind;
import static com.example.irbidcitycenter.Activity.AddZone.itemKintText;

import static com.example.irbidcitycenter.Activity.AddZone.validateKind;
import static com.example.irbidcitycenter.Activity.ItemChecker.ItC_itemcode;
import static com.example.irbidcitycenter.Activity.ItemChecker.itemRES;
import static com.example.irbidcitycenter.Activity.ItemChecker.stockqrtRes;
import static com.example.irbidcitycenter.Activity.Login.getListCom;
import static com.example.irbidcitycenter.Activity.MainActivity.flg;
import static com.example.irbidcitycenter.Activity.MainActivity.itemrespons;
import static com.example.irbidcitycenter.Activity.NewShipment.PoQTY;
import static com.example.irbidcitycenter.Activity.NewShipment.itemname;
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


public class ImportData {
    public static int actvityflage = 1;
    SweetAlertDialog pdVoucher;
    public static ArrayList<ZoneModel> listAllZone = new ArrayList<>();
    public static ArrayList<ZoneModel> Zoneslist = new ArrayList<>();
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
    public static ArrayList<String> BoxNolist = new ArrayList<>();
    public static ArrayList<String> PoNolist = new ArrayList<>();
    public static List<Shipment> POdetailslist = new ArrayList<>();
    public static List<ReplacementModel> stocksQty = new ArrayList<>();
    public static List<ZoneModel> listQtyZone = new ArrayList<>();
    public static ArrayList<CompanyInfo> companyInList = new ArrayList<>();
    public static String barcode = "";
    public static List<AllItems> AllImportItemlist = new ArrayList<>();
    public static List<ItemInfo> itemInfos = new ArrayList<>();
    public JSONArray jsonArrayPo;
    public JSONObject stringNoObject;

//

    static ProgressDialog progressDialog;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private int max = 50;
    public static SweetAlertDialog pdUserPer, pditeminfo, storeinfo, zoneinfo;
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
        } catch (Exception e) {
            Toast.makeText(context, "Fill Ip and Company No", Toast.LENGTH_SHORT).show();
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
    public void getUserPermissions(){
        UserPermissions.clear();
        pdUserPer = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdUserPer.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdUserPer.setTitleText(" Start get User Permissions");
        pdUserPer.setCancelable(false);
        pdUserPer.show();
        if(!ipAddress.equals(""))
            new JSONTask_getAllUserPermissions().execute();
        else
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
    }
    public void getAllItems(){


        AllImportItemlist.clear();
        progressDialog = new ProgressDialog(context);
        Log.e("context",context.getClass().getName().toString());
       showProgressDialogWithTitle("Importing Data","Please Wait");
        AllstocktakeDBlist.clear();
        if(!ipAddress.equals(""))
            new  JSONTask_getAllItems().execute();
        else
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
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
        listQtyZone.clear();
            new  JSONTask_getQTYOFZone().execute();

    }
    public void getStore() {
        storeinfo = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        storeinfo.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        storeinfo.setTitleText(" Start get Stores Info");
        storeinfo.setCancelable(false);
        storeinfo.show();

        if(!ipAddress.equals(""))
        new JSONTask_getAllStoreData().execute();
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
        if(!ipAddress.equals(""))
        {
            new JSONTask_getItemKind(itemNo).execute();
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
                            getListCom.setText("fill");
                        }


//                            itemKintText.setText(requestDetail.getZONETYPE());


                    } catch (JSONException e) {
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }


            }
            else {
                if (MainActivity.setflage == 0)
                    itemKintText.setText("NOTEXIST");
                else
                    if(MainActivity.setflage == 1)
                    itemKintText1.setText("NOTEXIST");
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
                        ZoneModel itemZone = new ZoneModel();
                        try {
                            itemZone.setZoneCode(result.getString("ZONENO"));
                            itemZone.setZONENAME(result.getString("ZONENAME"));
                            itemZone.setZONETYPE(result.getString("ZONETYPE"));

                            listAllZone.add(itemZone);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


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
                                POdetailslist.add(shipment);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //NewShipment.respon.setText(POdetailslist.get(0).getBarcode().toString());
                        itemname.setText(POdetailslist.get(0).getItemname());
                        Log.e("itemname",itemname.getText().toString());
                        PoQTY.setText(POdetailslist.get(0).getPoqty());
                        poqty = POdetailslist.get(0).getPoqty();
                        //



                        posize = POdetailslist.size();
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



                        if (array.length()>0)for (int i = 0; i < array.length(); i++) {
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
                        NewShipment.boxnorespon.setText("BOXNO");
                        if (NewShipment.boxnorespon.getText().length() > 0) {
                            NewShipment.boxno.setEnabled(true);
                            NewShipment.boxno.requestFocus();
                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else if (respon.contains("No Parameter Found")) {
                        NewShipment.boxnorespon.setText("Not");
                    }
                    }

                }//nuul

        }

    }
        private class JSONTask_getAllStoreData extends AsyncTask<String, String, String> {


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
                              if( Replacement.actvityflage==1)
                                  Replacement.respon.setText("fill");
                              else if(Replacement.actvityflage==3)
                                  ReplenishmentReverse.RepRev_storrespon.setText("fill");
                                  else
                                  Stoketake.respone.setText("fill");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }



                    }
                } else {

                    if( Replacement.actvityflage==1)
                        Replacement.respon.setText("nodata");

                    else if(Replacement.actvityflage==3)  ReplenishmentReverse.RepRev_storrespon.setText("nodata");
                       else
                           Stoketake.respone.setText("fill");

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
                                listQtyZone.add(zoneModel);
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
                    if (respon.contains("PONO")) {
                        JSONArray array = null;


                        try {
                            array = new JSONArray(respon);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (array.length()>0)for (int i = 0; i < array.length(); i++) {
                            try {
                                jsonObject1 = array.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {

                                PoNolist.add(jsonObject1.getString("PONO"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


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
                Log.e("Exception", "" + e.getMessage());
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


                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetAllItems?CONO=" + CONO.trim();

                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                Log.e("Exception",""+e.getMessage());
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                      //  pdVoucher.dismissWithAnimation();
                        //hide Progressbar after finishing process
                        hideProgressDialogWithTitle();
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
                        hideProgressDialogWithTitle();
                        Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                hideProgressDialogWithTitle();
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
         //   pdVoucher.dismissWithAnimation();
            //hide Progressbar after finishing process
          //  hideProgressDialogWithTitle();
            if (respon != null) {
                if (respon.contains("ItemOCode")) {

                    if (respon.length() != 0) {
                        try {

                            JSONArray requestArray = null;
                            requestArray = new JSONArray(respon);

                            for (int i = 0; i < requestArray.length(); i++) {

                               AllItems allItems= new  AllItems ();
                                jsonObject1 = requestArray.getJSONObject(i);
                                allItems.setItemOcode(jsonObject1.getString("ItemOCode"));
                                allItems.setItemNCode(jsonObject1.getString("ItemNCode"));
                                allItems.setItemNameE(jsonObject1.getString("ItemNameE"));
                                allItems.setItemNameA(jsonObject1.getString("ItemNameA"));



                                allItems.setSalePrice(jsonObject1.getString("SalePrice"));
                                allItems.setLFCPrice(jsonObject1.getString("LFCPrice"));
                                allItems.setLLCPrice(jsonObject1.getString("LLCPrice"));


                                allItems.setOrdLvl(jsonObject1.getString("OrdLvl"));
                                allItems.setAVG_Cost(jsonObject1.getString("AVG_Cost"));
                                allItems.setLifo(jsonObject1.getString("Lifo"));
                                allItems.setFifo(jsonObject1.getString("Fifo"));
                                allItems.setItemG(jsonObject1.getString("ItemG"));

                                allItems.setItemK(jsonObject1.getString("ItemK"));
                                allItems.setItemM(jsonObject1.getString("ItemM"));
                                allItems.setItemU(jsonObject1.getString("ItemU"));
                                allItems.setItemL(jsonObject1.getString("ItemL"));

                                allItems.setIStatus(jsonObject1.getString("IStatus"));
                                allItems.setF_D(jsonObject1.getString("F_D"));
                                allItems.setITEMGS(jsonObject1.getString("ITEMGS"));

                             AllImportItemlist.add(allItems);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

             if(MainActivity.Items_activityflage==1)       itemrespons.setText("ItemOCode");
            else        St_Itemrespons.setText("ItemOCode");

                   if(flg==0) RepRev_Itemrespons.setText("ItemOCode");

                    Log.e("itemrespons",itemrespons.getText().toString()+d);

                    Log.e("itemrespons",itemrespons.getText().toString()+d);
                }
                else {

                    if(MainActivity.Items_activityflage==1)  itemrespons.setText("nodata");
                    else    St_Itemrespons.setText("nodata");

                }

            }
            else {
                if(MainActivity.Items_activityflage==1)
                    itemrespons.setText("nodata");
                else    St_Itemrespons.setText("nodata");
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

        public JSONTask_getAllUserPermissions() {
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
                           // requestDetail.setRepRev_LocalDelete(infoDetail.get("OREVERSE").toString());

                            UserPermissions.add(requestDetail);
                            Log.e("here UserPermissions"," UserPermissions");
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
}









