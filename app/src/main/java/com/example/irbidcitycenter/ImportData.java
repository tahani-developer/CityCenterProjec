package com.example.irbidcitycenter;

import android.content.Context;
import android.net.http.DelegatingSSLSession;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.irbidcitycenter.Activity.MainActivity;
import com.example.irbidcitycenter.Activity.NewShipment;
import com.example.irbidcitycenter.Activity.Replacement;
import com.example.irbidcitycenter.Models.CompanyInfo;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.Store;
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
import java.util.ArrayList;
import java.util.List;

import static com.example.irbidcitycenter.Activity.AddZone.itemKind;
import static com.example.irbidcitycenter.Activity.AddZone.itemKintText;

import static com.example.irbidcitycenter.Activity.AddZone.validateKind;
import static com.example.irbidcitycenter.Activity.Login.getListCom;
import static com.example.irbidcitycenter.Activity.NewShipment.PoQTY;
import static com.example.irbidcitycenter.Activity.NewShipment.itemname;
import static com.example.irbidcitycenter.Activity.NewShipment.poNo;
import static com.example.irbidcitycenter.Activity.NewShipment.respon;
import static com.example.irbidcitycenter.Activity.Replacement.itemKintText1;
import static com.example.irbidcitycenter.GeneralMethod.convertToEnglish;


public class ImportData {
    public static ArrayList<ZoneModel> listAllZone =new ArrayList<>();
    public static int posize;
    public  static String itemn;
    public  static String poqty;
    private Context context;
    public  String ipAddress="",CONO="",headerDll="",link="";
    public RoomAllData my_dataBase;
    public static String zonetype;
    public static List<Store> Storelist=new ArrayList<>();
    public static List<String> BoxNolist=new ArrayList<>();
    public static List<Shipment> POdetailslist=new ArrayList<>();
    public static ArrayList<CompanyInfo> companyInList=new ArrayList<>();
    public ImportData(){}
    public ImportData(Context context) {
        this.context = context;
        my_dataBase= RoomAllData.getInstanceDataBase(context);
        try {
            getIpAddress();
        }catch (Exception e){
            Toast.makeText(context, "Fill Ip and Company No", Toast.LENGTH_SHORT).show();
        }

    }
    public void getStore() {
        if(!ipAddress.equals(""))
        new JSONTask_getAllStoreData().execute();
        else
        Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
    }

    public void getboxno() {
        Log.e("ingetboxno","ingetboxno");
        if(!ipAddress.equals(""))
        new JSONTask_getAllPOboxNO().execute();
        else
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
    }
    public void getPOdetails() {
        Log.e("getPOdetails","getPOdetails");
        //new JSONTaskGetPOdetails(context,cono,pono).execute();
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
        if(!ipAddress.equals(""))
        {
            new JSONTask_getAllZoneCode().execute();
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

    public void getCompanyInfo() {
        ipAddress="10.0.0.22:8082";
        if(!ipAddress.equals(""))
        {
            new JSONTask_getCompanyInfo().execute();
        }
        else {

            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
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
            Log.e("onPostExecute", ""+result );
            if (result != null ) {
                validateKind=false;
                if (result.contains("ITEMTYPE")) {
                    try {
                        ZoneModel requestDetail=new ZoneModel();
                        JSONArray requestArray = null;
                        requestArray =  new JSONArray(result);
                        Log.e("requestArray", "" + requestArray.length());


                        for (int i = 0; i < requestArray.length(); i++) {
                            JSONObject infoDetail = requestArray.getJSONObject(i);
                            requestDetail = new ZoneModel();
                            requestDetail.setZONETYPE(infoDetail.get("ITEMTYPE").toString());
                            requestDetail.setZoneCode(infoDetail.get("ITEMCODE").toString());
                            requestDetail.setZONENAME(infoDetail.get("ITEMNAME").toString());

                        }
                        itemKind=requestDetail.getZONENAME();
                        zonetype=requestDetail.getZONETYPE();
                        Log.e("itemKind",""+itemKind);
                        if(MainActivity.setflage==0)
                        itemKintText.setText(requestDetail.getZONETYPE());
                        else
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
                        itemKintText1.setText("NOTEXIST");
                }
                    Log.e("onPostExecute", "NotFound" + result.toString());




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
                Log.e("finalJson***Import", "Company"+finalJson);



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
            Log.e("onPostExecute", ""+result );
            if (result != null ) {
                // {
                //    "CoNo": "200",
                //    "CoYear": "2021",
                //    "CoNameA": "Al Rayyan Plastic Factory 2017"
                //  },

                if (result.contains("CoNo")) {
                    try {
                        CompanyInfo requestDetail=new CompanyInfo();
                        JSONArray requestArray = null;
                        requestArray =  new JSONArray(result);
                        companyInList=new ArrayList<>();


                        for (int i = 0; i < requestArray.length(); i++) {
                            JSONObject infoDetail = requestArray.getJSONObject(i);
                            requestDetail = new CompanyInfo();
                            requestDetail.setCoNo(infoDetail.get("CoNo").toString());
                            requestDetail.setCoYear(infoDetail.get("CoYear").toString());
                            requestDetail.setCoNameA(infoDetail.get("CoNameA").toString());

                            companyInList.add(requestDetail);
                        }
                        if(companyInList.size()!=0)
                        {
                            getListCom.setText("fill");
                        }




//                            itemKintText.setText(requestDetail.getZONETYPE());



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
                        itemKintText1.setText("NOTEXIST");
                }
                Log.e("onPostExecute", "NotFound" + result.toString());




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


            if (array != null && array.length() != 0) {
                Log.e("onPostExecute", "" + array.toString());

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

                Log.e("listAllZone", "" + listAllZone.size());

            }
        }
    }






        /////////
        private class JSONTask_getAllPOdetails extends AsyncTask<String, String,String> {

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

                        link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetItemInfo?CONO=" + CONO.trim()+"&PONO="+poNo.trim()+"&ITEMCODE="+convertToEnglish(NewShipment.barCode.trim());

                        Log.e("link", "" + link);
                    }
                } catch (Exception e) {
            Log.e("getAllPOdetails doInBackground, ",e.getMessage());
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

                if (array.contains("ItemOCode")){
                if (array != null && array.length() != 0) {
                    Log.e("onPostExecute", "" + array.toString());


                        try {
                            JSONArray requestArray = null;
                            requestArray =  new JSONArray(array);

                            for (int i = 0; i < requestArray.length(); i++) {

                            shipment=new Shipment();
                                jsonObject1 =  requestArray.getJSONObject(i);
                            shipment.setBarcode(jsonObject1.getString("ItemOCode"));
                            shipment.setPoqty(jsonObject1.getString("Qty"));
                            shipment.setItemname(jsonObject1.getString("ItemNameA"));
                            shipment.setBoxNo(jsonObject1.getString("BOXNO"));
                            POdetailslist.add(shipment);
                        } }
                    catch (JSONException e) {
                            e.printStackTrace();
                        }


                    NewShipment.respon.setText(POdetailslist.get(0).getBarcode().toString());
                    itemname.setText(POdetailslist.get(0).getItemname());
                    PoQTY.setText(POdetailslist.get(0).getPoqty());
                    poqty=POdetailslist.get(0).getPoqty();
                    //



                    Log.e("    POdetailslist.add(shipment);", "" +     POdetailslist.size());
                  posize= POdetailslist.size();
                    Log.e("    POdetailslist.add(shipment);", "" +     POdetailslist.get(0).getItemname());


                }
            }else {

                    NewShipment.respon.setText("notexists");



                }}

        }




    private class JSONTask_getAllPOboxNO extends AsyncTask<String, String, String> {

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
                    //http://localhost:8082/IrGetAllZone?CONO=290

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetBOXNO?CONO=" + CONO.trim()+"&PONO="+convertToEnglish(poNo.trim());

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


            if (respon != null && respon.length() != 0) {
                if(respon.contains("BOXNO"))
                {   JSONArray array = null;


                    try {
                        array =  new JSONArray(respon);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                Log.e("onPostExecute", "" + array.toString());

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
                Log.e("    BoxNolist", "" +     BoxNolist.size());
                Log.e("    BoxNolist", "" +     BoxNolist.get(0));
                    NewShipment.boxnorespon.setText(BoxNolist.get(0));
                    if (NewShipment.boxnorespon.getText().length() > 0) {
                        NewShipment.boxno.setEnabled(true);
                        NewShipment.boxno.requestFocus();
                    }
            }
                else if(respon.contains("No Parameter Found"))
                {
                    NewShipment.boxnorespon.setText("Not");
                }
            }

        }
    }


    private class JSONTask_getAllStoreData extends AsyncTask<String, String,String> {


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
                    http://10.0.0.22:8082/Getsore

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/Getsore" ;

                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                Log.e("getAllStore doInBackground, ",e.getMessage());
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
            if (array != null)
            if (array.contains("STORENO"))
            {
                if (array != null && array.length() != 0) {
                    Log.e("onPostExecute", "" +array.length()+"  "+ array.toString());
                    try {
                        JSONArray requestArray = null;
                        requestArray =  new JSONArray(array);


                    for (int i = 0; i < requestArray.length(); i++) {
                        store=new Store();
                        jsonObject1 =  requestArray.getJSONObject(i);
                        store.setSTORENO(jsonObject1.getString("STORENO"));
                        store.setSTORENAME(jsonObject1.getString("STORENAME"));

                        Storelist.add(store);
                    }
                    }
                         catch (JSONException e)
                        {
                            e.printStackTrace();
                        }


                    }
//                    Replacement.respon.setText(store.getSTORENO());

                    //

                }
            else {

                Replacement.respon.setText("nodata");



            }}
            }


    }









