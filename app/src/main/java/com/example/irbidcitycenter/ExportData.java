package com.example.irbidcitycenter;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import com.example.irbidcitycenter.Activity.MainActivity;
import com.example.irbidcitycenter.Activity.NewShipment;
import com.example.irbidcitycenter.Activity.Replacement;
import com.example.irbidcitycenter.Activity.Replacement;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.ReplenishmentReverseModel;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.Models.StocktakeModel;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.Models.ZoneReplashmentModel;
import com.example.irbidcitycenter.Models.RequestQueueSingleton;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.irbidcitycenter.Activity.AddZone.exportStateText;
import static com.example.irbidcitycenter.Activity.MainActivity.RepRevExportsatate;
import static com.example.irbidcitycenter.Activity.MainActivity.activityflage;
import static com.example.irbidcitycenter.Activity.MainActivity.exportFromMainAct;
import static com.example.irbidcitycenter.Activity.MainActivity.exportFromMainAct2;
import static com.example.irbidcitycenter.Activity.MainActivity.exportZonReprespon;
import static com.example.irbidcitycenter.Activity.MainActivity.exportrespon;
import static com.example.irbidcitycenter.Activity.MainActivity.re_res;
import static com.example.irbidcitycenter.Activity.MainActivity.sh_res;
import static com.example.irbidcitycenter.Activity.MainActivity.zo_res;
import static com.example.irbidcitycenter.Activity.NewShipment.poststate;
import static com.example.irbidcitycenter.Activity.Replacement.poststateRE;
import static com.example.irbidcitycenter.Activity.ReplenishmentReverse.RepRev_exportstate;
import static com.example.irbidcitycenter.Activity.Stoketake.datarespon;
import static com.example.irbidcitycenter.Activity.ZoneReplacment.ZonRepdatarespon;
import static com.example.irbidcitycenter.GeneralMethod.showSweetDialog;

public class ExportData {
    private Context context;
    public  String ipAddress="",CONO="",headerDll="",link="",PONO="";
    public RoomAllData my_dataBase;
    SweetAlertDialog pdVoucher,pdshipmant,pdRepla,pdstock,pdzone,pdRepRev;
    JSONObject vouchersObject;
    JSONObject ShipmentObject;
    Set<String> setVoucher=new HashSet<>();
    JSONObject ReplacmentObject,StockObject,ZoneRepObject,ReplacmentReverseObject;
    private JSONArray jsonArrayShipment;
    private JSONArray jsonArrayReplacement,jsonArrayStock,jsonArrayZoneRep,jsonArrayReversRep;
    private JSONArray jsonArrayVouchers;
    public  ArrayList<Shipment> listAllShipment   =new ArrayList<>();
    public  List<ZoneReplashmentModel> listAllZoneRep  =new ArrayList<>();
    public  List<StocktakeModel> listAllStock   =new ArrayList<>();
    public  ArrayList<ReplacementModel> listAllReplacment =new ArrayList<>();
    public  List<ReplenishmentReverseModel> listAllReplacmentRevers =new ArrayList<>();
    int typeExportZone=0;
    int typeExportShipment=0;
    SweetAlertDialog     saving;
    int typeExportReplacement=0;
    public ExportData(Context context) {
        this.context = context;
        my_dataBase= RoomAllData.getInstanceDataBase(context);
        try {
            getIpAddress();
        }catch (Exception e){
            Toast.makeText(context, context.getString(R.string.fillIpAndComNo), Toast.LENGTH_SHORT).show();
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
        pdRepla.setTitleText(context.getString(R.string.exportRep));
        pdRepla.setCancelable(false);
        pdRepla.show();

        new JSONTask_AddReplacment(replacementlist).execute();
    }

    public void exportReversReplacementList(List<ReplenishmentReverseModel>replacementlist) {
        getReversReplacmentObject(replacementlist);
        pdRepRev = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepRev.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdRepRev.setTitleText(context.getString(R.string.exportRevRep));
        pdRepRev.setCancelable(false);
        pdRepRev.show();

        new JSONTask_AddReversReplacment(replacementlist).execute();
    }

    public  void  exportStockTakeList(List<StocktakeModel>Stocktakelist) {
        getStocktakeObject(Stocktakelist);
        pdstock = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdstock.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdstock.setTitleText(context.getString(R.string.exportStockTake));
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
    private void  getReversReplacmentObject(List<ReplenishmentReverseModel>replacementlist) {
        jsonArrayReversRep = new JSONArray();
        for (int i = 0; i < replacementlist.size(); i++)
        {

            jsonArrayReversRep.put(replacementlist.get(i).getJSONObjectDelphi());

        }
        try {
            ReplacmentReverseObject=new JSONObject();
            ReplacmentReverseObject.put("JSN",jsonArrayReversRep);
            Log.e("vouchersObject",""+ReplacmentReverseObject.toString());
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
        pdzone.setTitleText(context.getString(R.string.exportZoneRep));
        pdzone.setCancelable(false);
        pdzone.show();

        new JSONTask_AddZoneRep(zoneReplashmentModelslist).execute();
    }
    public void exportAllUnposted(List<ZoneModel> listZone, ArrayList<Shipment> listShipment, ArrayList<ReplacementModel> listReplacment,List<StocktakeModel>liststocktake,List<ZoneReplashmentModel>zoneReplacmentList,List<ReplenishmentReverseModel>reverseModelList){
        Log.e("context",context.getClass().getName());
        exportZoneList(listZone,2);
        listAllShipment=listShipment;
        listAllStock=liststocktake;
        Log.e("exportAllUnposted","listAllShipment"+listAllShipment.size());
        listAllReplacment=listReplacment;
        listAllReplacmentRevers=reverseModelList;
        listAllZoneRep=zoneReplacmentList;

    }

    public void exportZoneList(List<ZoneModel> listZone,int type) {
        typeExportZone=type;
        getZoneObject(listZone);
        pdVoucher = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdVoucher.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdVoucher.setTitleText(context.getString(R.string.exportZones));
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

                        Toast.makeText(context, context.getString(R.string.checkCon), Toast.LENGTH_SHORT).show();
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
                if(result.contains("Internal Application Error")){
                    if(typeExportZone==1)
                    {
                        Log.e("Application Error","Application Error");
                    exportStateText.setText("Internal Application Error");}
                    else{
                        Handler h2 = new Handler(Looper.getMainLooper());
                        h2.post(new Runnable() {
                            public void run() {

                                showSweetDialog(context, 0, "Server Error", "");

                            }
                        });
                    }
                }
             else    if(result.contains("Saved Successfully"))
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


                    }else {
                        Toast.makeText(context, context.getString(R.string.noInternet), Toast.LENGTH_SHORT).show();

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
                Toast.makeText(context, context.getString(R.string.noInternet), Toast.LENGTH_SHORT).show();

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
        pdshipmant.setTitleText(context.getString(R.string.exportShip));
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
                if(result.contains("Internal Application Error"))
                {
                    ZonRepdatarespon.setText("Internal Application Error");
                }else
                if(result.contains("Saved Successfully"))
                {
                    Log.e("activityflage",activityflage+"");
                    if(activityflage==0)
                        ZonRepdatarespon.setText("exported");
                    else
                    {
                        exportZonReprespon.setText("exported");
                        exportReversReplacementList(listAllReplacmentRevers);

                    }

                }
                else

                {
                    if(activityflage==0)
                        ZonRepdatarespon.setText("not");
                    else
                    {  exportZonReprespon.setText("not");
                        exportReversReplacementList(listAllReplacmentRevers);

                    }

                }

            }
            else {

                if(activityflage==0)
                    ZonRepdatarespon.setText("not");
                else
                {  exportZonReprespon.setText("not");
                    exportReversReplacementList(listAllReplacmentRevers);

                }
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

                if(result.contains("Internal Application Error")){
                    datarespon.setText("Internal Application Error");
                }else
                if(result.contains("Saved Successfully"))
                {

                    Log.e("listAllZoneRep",listAllZoneRep.size()+"");

                 if(activityflage==2)
                     datarespon.setText("exported");
          else {
                     exportrespon.setText("exported");

                         exportZoneRepList(listAllZoneRep);
                 }


                }
                else

                {
                    if(activityflage==2)
                    datarespon.setText("not");
                    else {
                        exportrespon.setText("not");
                        exportZoneRepList(listAllZoneRep);
                    }

                }

            }
            else {
                Log.e("ellllse","ellse");
                if(activityflage==2)
                    datarespon.setText("not");
                else {
                    exportrespon.setText("not");
                    exportZoneRepList(listAllZoneRep);
                }
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

                if(result.contains("Internal Application Error")){
                    if( !exportFromMainAct)
                        poststate.setText("Internal Application Error");


                }else

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

                {    if( !exportFromMainAct)
                    poststate.setText("not");
                else
                    sh_res.setText("not");}
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
              Handler h = new Handler(Looper.getMainLooper());
              h.post(new Runnable() {
                  public void run() {
                      pdRepla.dismissWithAnimation();  }
              });


          }

          try {
              HttpClient client = new DefaultHttpClient();
              HttpPost request = new HttpPost();
              try {
                  request.setURI(new URI(link));
              } catch (URISyntaxException e) {

                  Handler h = new Handler(Looper.getMainLooper());
                  h.post(new Runnable() {
                      public void run() {
                          pdRepla.dismissWithAnimation();  }
                  });


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

              Handler h = new Handler(Looper.getMainLooper());
              h.post(new Runnable() {
                  public void run() {
                      pdRepla.dismissWithAnimation();  }
              });


              e.printStackTrace();
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
              if(result.contains("Internal Application Error")){

                  if( !exportFromMainAct)
                      poststateRE.setText("Internal Application Error");

              }

              else
              if (result.contains("Saved Successfully")) {
                //  poststateRE.setText("exported");
                  if( !exportFromMainAct)
                      poststateRE.setText("exported");
                  else
                  {    re_res.setText("exported");

                       exportStockTakeList(listAllStock);


                  }

                  setVoucher.clear();
                    for (int i = 0; i < replacementList.size(); i++)
                        setVoucher.add(replacementList.get(i).getSERIALZONE()+"");

                    if(setVoucher.size()>0) {
                        saving = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                        saving.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
                        saving.setTitleText("saving");
                        saving.setCancelable(false);
                        saving.show();
                        Iterator<String> it = setVoucher.iterator();
                        while (it.hasNext()) {
                            String value = it.next();
                            System.out.println(value);
                            Log.e("ithasNext?=", it.hasNext() + "");
                            if (it.hasNext())
                                new JSONTask_Transfer2(1, value).execute();
                            else new JSONTask_Transfer2(0, value).execute();
                        }
                }


//             SweetAlertDialog     saving = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//                  saving .getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//                  saving .setTitleText("saving");
//                  saving .setCancelable(false);
//                  saving .show();
//                  JSONTask_ExportTrans(  1 , saving);
              }

              else

              {
                  if( !exportFromMainAct)
                      poststateRE.setText("not");
                   else
                  { re_res.setText("not");
                 exportStockTakeList(listAllStock);
                  }

              }





              } else {
              if( !exportFromMainAct)
                  poststateRE.setText("not");
              else
              { re_res.setText("not");
                  exportStockTakeList(listAllStock);
              }



              }


          }

      }

    public class  JSONTask_AddReversReplacment extends AsyncTask<String, String, String> {
        private String JsonResponse = null;

        List<ReplenishmentReverseModel> replacementList = new ArrayList<>();

        public JSONTask_AddReversReplacment(List<ReplenishmentReverseModel> replacementList) {
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
                pdRepRev.dismissWithAnimation();

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
                nameValuePairs.add(new BasicNameValuePair("JSONSTR", ReplacmentReverseObject.toString().trim()));
                nameValuePairs.add(new BasicNameValuePair("IQ","0"));
                Log.e("JSONSTR", ReplacmentReverseObject.toString());
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
            Log.e("JSONTaskAddRepReverse", "" + result);
            pdRepRev.dismissWithAnimation();

            if (result != null && !result.equals("")) {
                if (result.contains("Internal Application Error")){
                    if(!exportFromMainAct2)
                        RepRev_exportstate.setText("Internal Application Error");


                }

                else if(result.contains("Access violation at address")){
                    RepRev_exportstate.setText("Access violation at address");
                }
                   else if (result.contains("Saved Successfully")) {
                    //  poststateRE.setText("exported");

                   if(!exportFromMainAct2)
                       RepRev_exportstate.setText("exported");

             else  RepRevExportsatate.setText("exported");


                    setVoucher.clear();
                    for (int i = 0; i < replacementList.size(); i++)
                        setVoucher.add(replacementList.get(i).getSERIAL()+"");

                    if(setVoucher.size()>0) {
                        saving = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                        saving.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
                        saving.setTitleText("saving");
                        saving.setCancelable(false);
                        saving.show();
                        Iterator<String> it = setVoucher.iterator();
                        while (it.hasNext()) {
                            String value = it.next();
                            System.out.println(value);
                            Log.e("ithasNext?=", it.hasNext() + "");
                            if (it.hasNext())
                                new JSONTask_Transfer2(1, value).execute();
                            else new JSONTask_Transfer2(0, value).execute();
                        }
                    }

                }
                else
                {


                    if(!exportFromMainAct2)
                        RepRev_exportstate.setText("not");
                    else
                        RepRevExportsatate.setText("not");
                }





            } else {

                if(!exportFromMainAct2)
                    RepRev_exportstate.setText("not");
                else
                    RepRevExportsatate.setText("not");

            }


        }

    }



    public class  JSONTask_Transfer extends AsyncTask<String, String, String> {
        private String JsonResponse = null;


        public JSONTask_Transfer() {

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

                    //  "http://" + ipAddress.trim() + headerDll.trim() + "/EXPORTTRANS" + "?CONO=" + CONO
                    //localhost:8082/IrTransFer?CONO=290&JSONSTR={"JSN":[{"ITEMCODE":"4032900116167","FROMSTR":"1","TOSTR":"2","QTY":"10","ZONE":"50"},{"ITEMCODE":"7614900001130","FROMSTR":"1","TOSTR":"2","QTY":"30","ZONE":"51"}]}
                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/EXPORTTRANS?CONO=" + CONO.trim();


                    Log.e("URL_TO_HIT", "" + link);
                }
            } catch (Exception e) {
                //progressDialog.dismiss();


            }

            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                try {
                    request.setURI(new URI(link));
                } catch (URISyntaxException e) {


                    e.printStackTrace();
                }


            } catch (Exception e) {


                e.printStackTrace();
            }
            return JsonResponse;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
//            progressDialog.dismiss();
            Log.e("JSONTaskAddReplacment", "" + result);


            if (result != null && !result.equals("")) {


                if (result.contains("Saved Successfully")) {
                    Log.e("JSONTaskAddReplacment", "" + "Saved Successfully");

                }


            }

        }
    }

    public class  JSONTask_Transfer2 extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        String VHFNO="";
        int flage;

        public JSONTask_Transfer2(int flage,String VHFNO) {
            this.flage= flage;
            this.VHFNO = VHFNO;
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

                       link = "http://" + ipAddress.trim() + headerDll.trim() + "/EXPORTTRANS?CONO=" + CONO.trim()+"&VHFNO=" +VHFNO;


                    Log.e("URL_TO_HIT", "" + link);
                }
            } catch (Exception e) {
                saving.dismiss();


            }
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                try {
                    request.setURI(new URI(link));
                } catch (URISyntaxException e) {
                    saving.dismiss();
                    e.printStackTrace();
                }

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO));




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
                Log.e("JsonResponse", "ExportSerial" + JsonResponse);


                //*******************************************


            } catch (Exception e) {
                saving.dismiss();
            }
            return JsonResponse;


        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
//            progressDialog.dismiss();
            Log.e("JSONTaskAddReplacment", "" + result);

            if(flage==0) {saving.dismissWithAnimation();}
            if (result != null && !result.equals("")) {



                Log.e("ExportTrans Response", result.toString());

                if (result.toString().contains("Saved Successfully")) {


                    //exportAllState.setText("exported");


                    if(flage==0)

                    {
                        showSweetDialog(context, 1, context.getResources().getString(R.string.savedSuccsesfule), "");

                    }
                    if  (   exportFromMainAct) {
                        if(flage==0)

                        {
                            ImportData importData = new ImportData(context);
                            importData.New_getAllPoDetalis();
                        }

                     //   importData.New_getAllZons();
                    }

                } else if (result.toString().contains("server error")) {

                    if(flage==0)   showSweetDialog(context, 0, "Internal server error", "");
                } else if (result.toString().contains("unique constraint")) {

                    Log.e("unique response", result.toString() + "");
                    if(flage==0)  showSweetDialog(context, 0, "Unique Constraint", "");

                }else
                {
                    if(flage==0)   showSweetDialog(context, 0, result.toString() + "", "");
                }


            }else
            {
                if(flage==0)     showSweetDialog(context, 0, result.toString() + "", "");
            }

        }
    }

    public void JSONTask_ExportTrans(int x,SweetAlertDialog savingDialog) {
        String url = "http://" + ipAddress.trim() + headerDll.trim() + "/EXPORTTRANS" + "?CONO="+CONO ;
        Log.e("Export Trans URL ", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                savingDialog.dismissWithAnimation();
                Log.e("ExportTrans Response", response.toString());

                if (response.toString().contains("Saved Successfully")) {

                    savingDialog.dismissWithAnimation();
                    //exportAllState.setText("exported");


                showSweetDialog(context, 1, context.getResources().getString(R.string.savedSuccsesfule), "");
                  if  (   exportFromMainAct) {
                      ImportData importData = new ImportData(context);
                      importData.New_getAllPoDetalis();
                      importData.New_getAllZons();
                  }

                } else if (response.toString().contains("server error")) {
                    savingDialog.dismissWithAnimation();
                    showSweetDialog(context, 0, "Internal server error", "");
                } else if (response.toString().contains("unique constraint")) {
                    savingDialog.dismissWithAnimation();
                    Log.e("unique response", response.toString() + "");
                    showSweetDialog(context, 0, "Unique Constraint", "");

                }else
                {
                    showSweetDialog(context, 0, response.toString() + "", "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                savingDialog.dismissWithAnimation();

                if ((error.getMessage() + "").contains("value too large for column "))
                    showSweetDialog(context, 0, "Server Error!", "Value too large for column ");
                else
                    showSweetDialog(context, 0, error.getMessage() + "", "");

                Log.e("ExportTrans Error ", error.getMessage() + "");
            }
        });

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 3;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }


}


