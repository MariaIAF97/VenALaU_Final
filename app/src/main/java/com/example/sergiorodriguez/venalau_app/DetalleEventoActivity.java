package com.example.sergiorodriguez.venalau_app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class DetalleEventoActivity extends AppCompatActivity {

    String datoNombre;
    TextView nombres, fechas, detalles, lugars;
    FillList seventos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento);

        Intent intencion=getIntent();
        Bundle extras=intencion.getExtras();
        datoNombre=extras.getString("Dato1");

        nombres=(TextView)findViewById(R.id.txtNombreEvento);
        fechas = findViewById(R.id.txtFechaEvento);
        detalles = findViewById(R.id.txtDescripcionEvento);
        lugars = findViewById(R.id.txtLugarEvento);
        nombres.setText(datoNombre);

        seventos = new FillList();
        seventos.execute();
    }

    private void setUpActionBar(){
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

    }

    class FillList extends AsyncTask<String,String, Void> {
        private ProgressDialog progressDialog=new ProgressDialog(DetalleEventoActivity.this);
        InputStream inputStream=null;
        String result="";


        public FillList(){

        }

        protected void onPreExecute(){
            //progressDialog.setMessage("Downloading your data...");
            //progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    FillList.this.cancel(true);
                }
            });

        }

        @Override
        protected Void doInBackground(String... params){
            try{


                String UrlConvert= URLEncoder.encode(datoNombre, "utf-8");
                URL Url=new URL("https://venalau.azurewebsites.net/api/eventos/findEv/"+UrlConvert);
                //Log.d("URL","https://venalau.azurewebsites.net/api/evento/find/"+UrlConvert);
                HttpURLConnection connection=(HttpURLConnection) Url.openConnection();
                InputStream is=connection.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                StringBuilder sb=new StringBuilder();
                String line;
                while((line=br.readLine())!=null){
                    sb.append(line);
                }
                connection.disconnect();
                is.close();
                result=sb.toString();



            }catch(Exception e){
                Log.e("StringBuilding","Error converting result "+e.toString());
            }
            return null;
        }


        protected  void onPostExecute(Void v){

            String fecha ="", detalle="",lugar="";
            try{
                JSONArray jArray = new JSONArray(result);
                for(int i=0; i < jArray.length(); i++) {
                    JSONObject jObject = jArray.getJSONObject(i);
                    String nombreEvento = jObject.getString("nombre");
                    fecha = jObject.getString("fecha");
                    detalle = jObject.getString("detalle");
                    lugar = jObject.getString("lugar");
                }
                this.progressDialog.dismiss();


            }catch(JSONException e){
                Log.e("JSONException","Error: "+e.toString());
            }

            fechas.setText(fecha);
            fechas.setTextColor(Color.BLACK);
            detalles.setText(detalle);
            detalles.setTextColor(Color.BLACK);
            lugars.setText(lugar);
            lugars.setTextColor(Color.BLACK);


        }



    }

}
