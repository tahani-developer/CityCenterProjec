package com.example.irbidcitycenter;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.irbidcitycenter.Activity.NewShipment;
import com.example.irbidcitycenter.Activity.Replacement;
import com.example.irbidcitycenter.Activity.Replacement;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.Models.StocktakeModel;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.Models.ZoneReplashmentModel;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.irbidcitycenter.Activity.AddZone.exportStateText;
import static com.example.irbidcitycenter.Activity.MainActivity.activityflage;
import static com.example.irbidcitycenter.Activity.MainActivity.exportFromMainAct;
import static com.example.irbidcitycenter.Activity.MainActivity.exportZonReprespon;
import static com.example.irbidcitycenter.Activity.MainActivity.exportrespon;
import static com.example.irbidcitycenter.Activity.MainActivity.re_res;
import static com.example.irbidcitycenter.Activity.MainActivity.sh_res;
import static com.example.irbidcitycenter.Activity.MainActivity.zo_res;
import static com.example.irbidcitycenter.Activity.NewShipment.poststate;
import static com.example.irbidcitycenter.Activity.Replacement.poststateRE;
import static com.example.irbidcitycenter.Activity.Stoketake.datarespon;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.ZonRepdatarespon;

public class ExportData {
    private Context context;
    public  String ipAddress="",CONO="",headerDll="",link="",PONO="";
    public RoomAllData my_dataBase;
    SweetAlertDialog pdVoucher,pdshipmant,pdRepla,pdstock,pdzone;
    JSONObject vouchersObject;
    JSONObject ShipmentObject;
    JSONObject ReplacmentObject,StockObject,ZoneRepObject;
    private JSONArray jsonArrayShipment;
    private JSONArray jsonArrayReplacement,jsonArrayStock,jsonArrayZoneRep;
    private JSONArray jsonArrayVouchers;
    public  ArrayList<Shipment> listAllShipment   =new ArrayList<>();
    public  List<ZoneReplashmentModel> listAllZoneRep  =new ArrayList<>();
    public  List<StocktakeModel> listAllStock   =new ArrayList<>();
    public  ArrayList<ReplacementModel> listAllReplacment =new ArrayList<>();
    int typeExportZone=0;
    int typeExportShipment=0;
    int typeExportReplacement=0;
    public ExportData(Context context) {
        this.context = context;
        my_dataBase= RoomAllData.getInstanceDataBase(context);
        try {
            getIpAddress();
        }catch (Exception e){
            Toast.makeText(context, "Fill Ip and Company No", Toast.LENGTH_SHORT).show();
        }

    }
    private void getIpAddress() {
        headerDll="";
        ipAddress=my_dataBase.settingDao().getIpAddress().trim();
        CONO=my_dataBase.settingDao().getCono().trim();
        Log.e("getIpAddress",""+ipAddress);


    }
    public void exportReplacementList(List<ReplacementModel>replacementlist) {
        getReplacmentObject(replacementlist);
        pdRepla = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdRepla.setTitleText(" Start export Replenishments");
        pdRepla.setCancelable(false);
        pdRepla.show();

        new JSONTask_AddReplacment(replacementlist).execute();
    }
    public void exportStockTakeList(List<StocktakeModel>Stocktakelist) {
        getStocktakeObject(Stocktakelist);
        pdstock = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdstock.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdstock.setTitleText(" Start export Stock Take ");
        pdstock.setCancelable(false);
        pdstock.show();

       new JSONTask_AddStocktake(Stocktakelist).execute();
    }
    private void  getReplacmentObject(List<ReplacementModel>replacementlist) {
        jsonArrayReplacement = new JSONArray();
        for (int i = 0; i < replacementlist.size(); i++)
        {

            jsonArrayReplacement.put(replacementlist.get(i).getJSONObjectDelphi());

        }
        try {
            ReplacmentObject=new JSONObject();
            ReplacmentObject.put("JSN",jsonArrayReplacement);
            Log.e("vouchersObject",""+ReplacmentObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        }
    private void  getStocktakeObject(List<StocktakeModel>Stocktakelist) {
        jsonArrayStock = new JSONArray();
        for (int i = 0; i <Stocktakelist.size(); i++)
        {

            jsonArrayStock.put(Stocktakelist.get(i).getJSONObjectDelphi());

        }
        try {
            StockObject=new JSONObject();
            StockObject.put("JSN",jsonArrayStock);
            Log.e("vouchersObject",""+ StockObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void  getZoneRepObject(List<ZoneReplashmentModel>zonereplashmentlist) {
        jsonArrayZoneRep = new JSONArray();
        for (int i = 0; i <zonereplashmentlist.size(); i++)
        {

            jsonArrayZoneRep.put(zonereplashmentlist.get(i).getJSONObjectDelphi());

        }
        try {
            ZoneRepObject=new JSONObject();
            ZoneRepObject.put("JSN",jsonArrayZoneRep);
            Log.e("vouchersObject",""+ ZoneRepObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void exportZoneRepList(List<ZoneReplashmentModel>zoneReplashmentModelslist) {
        getZoneRepObject(zoneReplashmentModelslist);
        pdzone = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdzone.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdzone.setTitleText(" Start export ZoneReplenishments");
        pdzone.setCancelable(false);
        pdzone.show();

        new JSONTask_AddZoneRep(zoneReplashmentModelslist).execute();
    }
    public void exportAllUnposted(List<ZoneModel> listZone, ArrayList<Shipment> listShipment, ArrayList<ReplacementModel> listReplacment,List<StocktakeModel>liststocktake,List<ZoneReplashmentModel>zoneReplacmentList){
        exportZoneList(listZone,2);
        listAllShipment=listShipment;
        listAllStock=liststocktake;
        Log.e("exportAllUnposted","listAllShipment"+listAllShipment.size());
        listAllReplacment=listReplacment;
        listAllZoneRep=zoneReplacmentList;

    }

    public void exportZoneList(List<ZoneModel> listZone,int type) {
        typeExportZone=type;
        getZoneObject(listZone);
        pdVoucher = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdVoucher.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdVoucher.setTitleText(" Start export Zones");
        pdVoucher.setCancelable(false);
        pdVoucher.show();

        new JSONTaskDelphi(listZone).execute();
    }

    private void getZoneObject(List<ZoneModel> listZone) {
        jsonArrayVouchers = new JSONArray();
        for (int i = 0; i < listZone.size(); i++)
        {

                jsonArrayVouchers.put(listZone.get(i).getJSONObjectDelphi());

        }
        try {
            vouchersObject=new JSONObject();
            vouchersObject.put("JSN",jsonArrayVouchers);
            Log.e("vouchersObject",""+vouchersObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class JSONTaskDelphi extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
       List<ZoneModel> listZones=new ArrayList<>();

        public JSONTaskDelphi(List<ZoneModel> listZones) {
            this.listZones = listZones;
        }

        public  String salesNo="",finalJson;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            URLConnection connection = null;
            BufferedReader reader = null;

            try {
                if (!ipAddress.equals("")) {

                    //http://10.0.0.22:8082/ExportIrZone?CONO=290&JSONSTR={JSN:[{ZONENO:AB1,ITEMCODE:123456,QTY:10,TRDATE:13/04/2021,TRTIME:10:15}]}

                    link = "http://"+ipAddress.trim()+headerDll.trim()+"/ExportIrZone";



                    Log.e("URL_TO_HIT",""+link);
                }
            } catch (Exception e) {
                //progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        pdVoucher.dismissWithAnimation();

                        Toast.makeText(context, "check Connection", Toast.LENGTH_SHORT).show();
                    }
                });


            }

//********************************************************


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
                nameValuePairs.add(new BasicNameValuePair("JSONSTR", vouchersObject.toString().trim()));
                // Log.e("nameValuePairs","JSONSTR"+vouchersObject.toString().trim());


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



                //*******************************************


            } catch (Exception e) {
            }
            return JsonResponse;

        }


        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
//            progressDialog.dismiss();
            Log.e("onPostExecute",""+result);
            pdVoucher.dismissWithAnimation();
            if (result != null && !result.equals("")) {
                if(result.contains("Saved Successfully"))
                {
                    if(typeExportZone==1)
                    {

                        exportStateText.setText("exported");



                    }
                    else {
                        if(typeExportZone==2)
                        {
                            my_dataBase.zoneDao().updateZonePosted();
                            exportShipmentsList(listAllShipment);

                        }
                    }



                }else {
                    if(typeExportZone==1) {
                     exportStateText.setText("not");


                    }


                }
//                Toast.makeText(context, "onPostExecute"+result, Toast.LENGTH_SHORT).show();


            } else {  if(typeExportZone==1) {

                try {
                    if( !exportFromMainAct)  {


                        exportStateText.setText("not");

                    }
                }catch (Exception e){}




            }
            else{
                exportShipmentsList(listAllShipment);
            }

            }
        }
    }

    private void updateDataBasePosted() {
        my_dataBase.zoneDao().updateZonePosted();
//        my_dataBase.shipmentDao().updateShipmentPosted();
//        my_dataBase.replacementDao().updateReplacmentPosted();
    }

    public void exportShipmentsList(List<Shipment> listShipment) {

        Log.e("exportShipmentsList","exportShipmentsList");
        getShipmentObject(listShipment);
        pdshipmant = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdshipmant.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdshipmant.setTitleText(" Start export shipments");
        pdshipmant.setCancelable(false);
        pdshipmant.show();

        new JSONTask_AddShipments(listShipment).execute();
    }


    private void getShipmentObject(List<Shipment> listShipment) {
        Log.e("getShipmentObject","getShipmentObject");
        jsonArrayShipment = new JSONArray();
        for (int i = 0; i < listShipment.size(); i++)
        {

            jsonArrayShipment.put(listShipment.get(i).getJSONObjectDelphi());
            Log.e("list elements",""+listShipment.get(i).getJSONObjectDelphi());

        }
        try {
            ShipmentObject=new JSONObject();
            ShipmentObject.put("JSN",jsonArrayShipment);
            Log.e("ShipmentObject",""+ShipmentObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class  JSONTask_AddZoneRep extends AsyncTask<String, String, String> {

        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        List<ZoneReplashmentModel> ZoneRepModelsList = new ArrayList<>();

        public JSONTask_AddZoneRep(List<ZoneReplashmentModel> zoneRepModelsList) {

            ZoneRepModelsList = zoneRepModelsList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            URLConnection connection = null;
            BufferedReader reader = null;

            try {
                if (!ipAddress.equals("")) {

                    //    http://localhost:8085/IrZoneTransfer?CONO=304
                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrZoneTransfer";


                    Log.e("URL_TO_HIT", "" + link);
                }
            } catch (Exception e) {
                //progressDialog.dismiss();
                pdstock.dismissWithAnimation();

            }

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
                nameValuePairs.add(new BasicNameValuePair("JSONSTR", ZoneRepObject.toString().trim()));
                Log.e("URL_TO_HIT222", "" +  ZoneRepObject.toString());
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
                Log.e("JsonResponse", "ExporObject" + JsonResponse);


            } catch (Exception e) {
            }
            return JsonResponse;
        }
        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            Log.e("JSONTask_AddZonRep",""+result);
            pdzone.dismissWithAnimation();



            if (result != null && !result.equals("")) {
                if(result.contains("Saved Successfully"))
                {
                    if(activityflage==0)
                        ZonRepdatarespon.setText("exported");
                    else
                        exportZonReprespon.setText("exported");

                }
                else

                {
                    if(activityflage==0)
                        ZonRepdatarespon.setText("not");
                    else
                        exportZonReprespon.setText("not");

                }

            }
            else {
                Log.e("ellllse","ellse");
                if(activityflage==0)
                    ZonRepdatarespon.setText("not");
                else
                    exportZonReprespon.setText("not");
            }




        }}



    public class  JSONTask_AddStocktake extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        List<StocktakeModel> stocktakeModelsList = new ArrayList<>();

        public JSONTask_AddStocktake(List<StocktakeModel> stocktakeModelsList) {
            this.stocktakeModelsList = stocktakeModelsList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {
            URLConnection connection = null;
            BufferedReader reader = null;

            try {
                if (!ipAddress.equals("")) {

                    http://10.0.0.22:8085/IrExportStockTake?CONO=304&JSONSTR={%22JSN%22:[{%22STORE%22:%2220%22,%22USERNO%22:%226%22,%22ZONECODE%22:%22M13F%22,%22ITEMOCODE%22:%228050514685134%22,%22DATE%22:%2219\/08\/2021%22,%22TIME%22:%2209:38%22,%22QTY%22:%221%22,%22ITEMNAME%22:%22TIES%22,%22DEVICEID%22:%221111%22}]}
                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrExportStockTake";


                    Log.e("URL_TO_HIT", "" + link);
                }
            } catch (Exception e) {
                //progressDialog.dismiss();
                pdstock.dismissWithAnimation();

            }

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
                nameValuePairs.add(new BasicNameValuePair("JSONSTR", StockObject.toString().trim()));
                Log.e("URL_TO_HIT222", "" + StockObject.toString());
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
                Log.e("JsonResponse", "ExporStockObject" + JsonResponse);


            } catch (Exception e) {
            }
            return JsonResponse;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
//            progressDialog.dismiss();
            Log.e("JSONTask_Addstocktake",""+result);
            pdstock.dismissWithAnimation();



            if (result != null && !result.equals("")) {
                if(result.contains("Saved Successfully"))
                {

                        exportZoneRepList(listAllZoneRep);
                    Log.e("listAllZoneRep",listAllZoneRep.size()+"");

                 if(activityflage==0)   datarespon.setText("exported");
          else
              exportrespon.setText("exported");




                }
                else

                {
                    if(activityflage==0)
                    datarespon.setText("not");
                    else
                        exportrespon.setText("not");

                }

            }
            else {
                Log.e("ellllse","ellse");
                if(activityflage==0)
                    datarespon.setText("not");
                else
                    exportrespon.setText("not");
            }




        }
    }

        public class JSONTask_AddShipments extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
           List<Shipment> shipmentList=new ArrayList<>();


        public JSONTask_AddShipments(List<Shipment> shipmentList) {
            this.shipmentList = shipmentList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }
        @Override
        protected String doInBackground(String... params) {
            URLConnection connection = null;
            BufferedReader reader = null;

            try {
                if (!ipAddress.equals("")) {

                    //http://localhost:8082/ExportIrNEWSHIPMENT?CONO=290&JSONSTR={"JSN":[{"PONO":60,"BOXNO":150,"ITEMCODE":123456,"ITEMNAME":"AAAAA",
                    // "SHIPMENTTIME":"10:30","SHIPMENTDATE":"01/04/2021","QTY":15,"POQTY":10,"ISPOSTED":0},{"PONO":60,"BOXNO":150,
                    // "ITEMCODE":7894566,"ITEMNAME":"BBBB","SHIPMENTTIME":"11:30",
                    // "SHIPMENTDATE":"01/04/2021","QTY":20,"POQTY":30,"ISPOSTED":0}]}

                    link = "http://"+ipAddress.trim()+headerDll.trim()+"/ExportIrNEWSHIPMENT";



                    Log.e("URL_TO_HIT",""+link);
                }
            } catch (Exception e) {
                //progressDialog.dismiss();
                pdshipmant.dismissWithAnimation();

            }

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
                nameValuePairs.add(new BasicNameValuePair("JSONSTR", ShipmentObject.toString().trim()));
                Log.e("URL_TO_HIT222",""+ ShipmentObject.toString());
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
                Log.e("JsonResponse", "Exporship" + JsonResponse);


            }
            catch (Exception e) {
            }
            return JsonResponse ;
    }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
//            progressDialog.dismiss();
            Log.e("JSONTask_AddShipment",""+result);
            pdshipmant.dismissWithAnimation();



            if (result != null && !result.equals("")) {
                if(result.contains("Saved Successfully"))
                    {
                        if( !exportFromMainAct)
                            poststate.setText("exported");
                        else
                        {
                            sh_res.setText("exported");
                            exportReplacementList(listAllReplacment);
                        }


                    }
                    else

                        {
                            poststate.setText("not");
                        }

                   // exportReplacementList(listAllReplacment);


            }
else{
                if( !exportFromMainAct)
                    poststate.setText("not");
                else
                    sh_res.setText("not");
        }



                }
//                Toast.makeText(context, "onPostExecute"+result, Toast.LENGTH_SHORT).show();




        }




  public class  JSONTask_AddReplacment extends AsyncTask<String, String, String> {
      private String JsonResponse = null;

      List<ReplacementModel> replacementList = new ArrayList<>();

      public JSONTask_AddReplacment(List<ReplacementModel> replacementList) {
          this.replacementList = replacementList;
      }

      @Override
      protected void onPreExecute() {
          super.onPreExecute();


      }

      @Override
      protected String doInBackground(String... strings) {


          URLConnection connection = null;
          BufferedReader reader = null;

          try {
              if (!ipAddress.equals("")) {

                  http:
//localhost:8082/IrTransFer?CONO=290&JSONSTR={"JSN":[{"ITEMCODE":"4032900116167","FROMSTR":"1","TOSTR":"2","QTY":"10","ZONE":"50"},{"ITEMCODE":"7614900001130","FROMSTR":"1","TOSTR":"2","QTY":"30","ZONE":"51"}]}
                  link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrTransFer";


                  Log.e("URL_TO_HIT", "" + link);
              }
          } catch (Exception e) {
              //progressDialog.dismiss();
              pdRepla.dismissWithAnimation();

          }

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
              nameValuePairs.add(new BasicNameValuePair("JSONSTR", ReplacmentObject.toString().trim()));
              Log.e("JSONSTR", ReplacmentObject.toString());
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
              Log.e("JsonResponse", "Expor Replacement" + JsonResponse);


          } catch (Exception e) {
          }
          return JsonResponse;
      }

      @Override
      protected void onPostExecute(final String result) {
          super.onPostExecute(result);
//            progressDialog.dismiss();
          Log.e("JSONTaskAddReplacment", "" + result);
          pdRepla.dismissWithAnimation();

          if (result != null && !result.equals("")) {
              if (result.contains("Saved Successfully")) {
                //  poststateRE.setText("exported");
                  if( !exportFromMainAct)
                      poststateRE.setText("exported");
                  else
                      re_res.setText("exported");


                  exportStockTakeList(listAllStock);
              }

              else

              {
                  if( !exportFromMainAct)
                      poststateRE.setText("not");
                  else
                      re_res.setText("not");
              }





              } else {
                  poststateRE.setText("not");



              }


          }

      }


  }


