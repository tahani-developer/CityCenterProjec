package com.example.irbidcitycenter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.irbidcitycenter.Activity.NewShipment;
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
import com.google.gson.Gson;

import static com.example.irbidcitycenter.Activity.AddZone.listAllZone;
import static com.example.irbidcitycenter.Activity.NewShipment.PoQTY;
import static com.example.irbidcitycenter.Activity.NewShipment.itemname;
import static com.example.irbidcitycenter.Activity.NewShipment.poNo;


public class ImportData {
    public  static String itemn;
    public  static String poqty;
    private Context context;
    public  String ipAddress="",CONO="",headerDll="",link="";
    public RoomAllData my_dataBase;

    public static List<String> BoxNolist=new ArrayList<>();
    public static List<Shipment> POdetailslist=new ArrayList<>();
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


    public void getboxno() {
        Log.e("ingetboxno","ingetboxno");
        new JSONTask_getAllPOboxNO().execute();
    }
    public void getPOdetails() {
        Log.e("getPOdetails","getPOdetails");
        //new JSONTaskGetPOdetails(context,cono,pono).execute();

        new JSONTask_getAllPOdetails().execute();
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

                        Toast.makeText(context, "Ip Connection Failed AccountStatment", Toast.LENGTH_LONG).show();
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
        private class JSONTask_getAllPOdetails extends AsyncTask<String, String, JSONArray> {

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

                        link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetItemInfo?CONO=" + CONO.trim()+"&PONO="+poNo+"&ITEMCODE=6253349404082";

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

                            Toast.makeText(context, "Ip Connection Failed AccountStatment", Toast.LENGTH_LONG).show();
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

                JSONObject jsonObject1 = null;


                if (array != null && array.length() != 0) {
                    Log.e("onPostExecute", "" + array.toString());

                    for (int i = 0; i < array.length(); i++) {
                        try {
                            jsonObject1 = array.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                      Shipment shipment=new Shipment();
                        try {
                            shipment.setBarcode(jsonObject1.getString("ItemOCode"));
                            shipment.setPoqty(jsonObject1.getString("Qty"));
                            shipment.setItemname(jsonObject1.getString("ItemNameA"));
                            shipment.setBoxNo(jsonObject1.getString("BOXNO"));
                            POdetailslist.add(shipment);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    itemname.setText(POdetailslist.get(0).getItemname());
                    PoQTY.setText(POdetailslist.get(0).getPoqty());
                    poqty=POdetailslist.get(0).getPoqty();
                    //



                    Log.e("    POdetailslist.add(shipment);", "" +     POdetailslist.size());
                    Log.e("    POdetailslist.add(shipment);", "" +     POdetailslist.get(0).getItemname());


                }
            }
        }




    private class JSONTask_getAllPOboxNO extends AsyncTask<String, String, JSONArray> {

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

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetBOXNO?CONO=" + CONO.trim()+"&PONO="+poNo;

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

                        Toast.makeText(context, "Ip Connection Failed AccountStatment", Toast.LENGTH_LONG).show();
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

            JSONObject jsonObject1 = null;


            if (array != null && array.length() != 0) {
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

            }
        }
    }






}





