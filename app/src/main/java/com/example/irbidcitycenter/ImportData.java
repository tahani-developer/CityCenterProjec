package com.example.irbidcitycenter;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.irbidcitycenter.Models.Shipment;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ImportData {
    private Context context;
    public  String ipAddress="",CONO="",headerDll="",link="",PONO="";
    public static List<String> BoxNolist=new ArrayList<>();
    public static List<Shipment> POdetailslist=new ArrayList<>();
    public ImportData(Context context) {
        this.context = context;
        getIpAddress();
    }


    public void getboxno(Context context,String cono,String pono) {
        Log.e("ingetboxno","ingetboxno");
        new JSONTaskGetBoxNo(context,cono,pono).execute();
    }
    public void getPOdetails(Context context,String cono,String pono) {
        Log.e("","");
        new JSONTaskGetPOdetails(context,cono,pono).execute();
    }

    private void getIpAddress() {
        headerDll="";


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
    }





