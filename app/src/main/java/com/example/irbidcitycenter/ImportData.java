package com.example.irbidcitycenter;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ImportData {
    private Context context;
    public  String ipAddress="",CONO="",headerDll="",link="";

    public ImportData(Context context) {
        this.context = context;
        getIpAddress();
    }

    private void getIpAddress() {
        headerDll="";


    }

    private class JSONTask_ZoneCode extends AsyncTask<String, String, String> {

        private String custId = "";

        public JSONTask_ZoneCode(String customerId) {
            this.custId = customerId;
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
//                    URL_TO_HIT = "http://"+ipAddress.trim()+":" + ipWithPort.trim() +"/Falcons/VAN.dll/GetTheUnCollectedCheques?ACCNO=1224";

                    link = "http://"+ipAddress.trim()+headerDll+"/GetTheUnCollectedCheques?ACCNO="+custId;
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


                JSONObject parentObject = new JSONObject(finalJson);

                return JsonResponse;


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
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject result = null;
            String impo = "";
            if (s != null) {
                if (s.contains("AccCode")) {
                    // Log.e("CUSTOMER_INFO","onPostExecute\t"+s.toString());
                    //{"CUSTOMER_INFO":[{"VHFNo":"0","TransName":"ÞíÏ ÇÝÊÊÇÍí","VHFDATE":"31-DEC-19","DEBIT":"0","Credit":"16194047.851"}

//                    try {
//                        //[{"AccCode":"1224","RECVD":"3528","PAIDAMT":"0"}]
//
//
//                    } catch (JSONException e) {
////                        progressDialog.dismiss();
//                        e.printStackTrace();
//                    }
                } else Log.e("onPostExecute", "" + s.toString());
//                progressDialog.dismiss();
            }
        }

    }


}
