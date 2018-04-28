package com.example.sergiorodriguez.venalau_app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EventosActivity extends AppCompatActivity {

    ListView lvEventos;
    FillListEventos eventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("creado","creado");
        setContentView(R.layout.activity_eventos);
        lvEventos=(ListView)findViewById(R.id.lvEventos);
        Log.d("creado","creado");
        eventos=new FillListEventos();
        eventos.execute();
    }

    class FillListEventos extends AsyncTask<String,String,Void>{
        private ProgressDialog progressDialog=new ProgressDialog(EventosActivity.this);
        InputStream inputStream=null;
        String result="";

        public FillListEventos(){

        }

        protected void onPreExecute(){
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    FillListEventos.this.cancel(true);
                }
            });

        }

        @Override
        protected Void doInBackground(String... params){
            try{
                URL Url=new URL("http://venalau.azurewebsites.net/api/eventos/findAll");
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
            List<String> listaEventos=new ArrayList<>();
            try{
                JSONArray jArray=new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject jObject = jArray.getJSONObject(i);
                    String nombreEvento=jObject.getString("nombre");
                    listaEventos.add(nombreEvento);
                }
                this.progressDialog.dismiss();
            }catch(JSONException e){
                Log.e("JSONException","Error: "+e.toString());
            }

            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listaEventos);
            lvEventos.setAdapter(arrayAdapter);
        }

    }
}
