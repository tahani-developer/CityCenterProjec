package com.example.irbidcitycenter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

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
import static com.example.irbidcitycenter.Activity.AddZone.listAllZone;
import static com.example.irbidcitycenter.Activity.AddZone.validateKind;

public class ImportData {
    private Context context;
    public  String ipAddress="",CONO="",headerDll="",link="",PONO="";
    public RoomAllData my_dataBase;

    public static List<String> BoxNolist=new ArrayList<>();
    public static List<Shipment> POdetailslist=new ArrayList<>();
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
        new JSONTask_getAllPOboxNO().execute();
    }
    public void getPOdetails() {
        Log.e("","");
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

    public void getKindItem(String itemNo) {
        if(!ipAddress.equals(""))
        {
            new JSONTask_getItemKind(itemNo).execute();
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

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetItemData?CONO=" + CONO.trim()+"&&ITEMCODE="+itemNo.trim();
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

                        Toast.makeText(context, "Ip Connection Failed AccountStatment", Toast.LENGTH_LONG).show();
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
                        itemKind=requestDetail.getZONETYPE();
                        itemKintText.setText(itemKind);


                    } catch (JSONException e) {
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }
                else
                {
                    itemKintText.setText("NOTEXIST");
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
 private class JSONTaskGetBoxNo extends AsyncTask<String, String, String>{
        private Context context;
        private String Pono;
        private String Cono;
        public JSONTaskGetBoxNo( Context context, String pono,String cono) {
            this.context=context;
            this.Pono=pono;
            this.Cono=cono;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e("onPreExecute","onPreExecute");
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                ipAddress = "10.0.0.22:8082/IrGetBOXNO?";
                if (!ipAddress.equals("")) {
                    link = "http://" + ipAddress;
                    Log.e("doInBackground","doInBackground");
                }
            } catch (Exception e) {

            }

            try {
                Log.e(" try"," try");
                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(link));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("PONO", Pono));
                nameValuePairs.add(new BasicNameValuePair("COMPANYNO", Cono));
                Log.e("Pono",Pono);
                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                return JsonResponse;


            } catch (HttpHostConnectException ex) {
                ex.printStackTrace();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if (s != null) {
                    if (s.contains("RESULT")) {

                        JSONObject jsonObject = null;

                            jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("RESULT");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                BoxNolist.add(jsonObject1.getString("BOXNO"));
                            }



                    } else {

                    }

                }
                else {

                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }


        }
}

private class JSONTaskGetPOdetails extends AsyncTask<String, String, String>{
        private Context context;
        private String Pono;
        private String Cono;


        public JSONTaskGetPOdetails(Context context, String pono, String cono) {
            this.context = context;
            Pono = pono;
            Cono = cono;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... strings) {
            try {

                ipAddress = "10.0.0.22:8082/IrGetItemInfo?";
                if (!ipAddress.equals("")) {
                    link = "http://" + ipAddress;
                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(link));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("PONO", Pono));
                nameValuePairs.add(new BasicNameValuePair("COMPANYNO", Cono));
                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                return JsonResponse;


            } catch (HttpHostConnectException ex) {
                ex.printStackTrace();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if (s != null) {
                    if (s.contains("RESULT")) {

                        JSONObject jsonObject = null;

                        jsonObject = new JSONObject(s);
                        JSONArray jsonArray = jsonObject.getJSONArray("RESULT");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Shipment shipment=new Shipment();
                            shipment.setBarcode(jsonObject1.getString("ItemOCode"));
                            shipment.setQty(jsonObject1.getString("Qty"));
                            shipment.setItemname(jsonObject1.getString("ItemNameA"));
                            shipment.setBoxNo(jsonObject1.getString("BOXNO"));

                            POdetailslist.add(shipment);
                        }



                    } else {

                    }

                }
                else {

                }
            }
            catch (JSONException e) {
                e.printStackTrace();
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

                        link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetItemInfo?CONO=" + CONO.trim()+"&PONO=6&ITEMCODE=6253349404082";

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
                Log.e("onPostExecute", "" + array.length());

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
                            shipment.setQty(jsonObject1.getString("Qty"));
                            shipment.setItemname(jsonObject1.getString("ItemNameA"));
                            shipment.setBoxNo(jsonObject1.getString("BOXNO"));
                            POdetailslist.add(shipment);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e("    POdetailslist.add(shipment);", "" +     POdetailslist.size());

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

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetBOXNO?CONO=" + CONO.trim()+"&PONO=6";

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
            Log.e("onPostExecute", "" + array.length());

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

            }
        }
    }






}





