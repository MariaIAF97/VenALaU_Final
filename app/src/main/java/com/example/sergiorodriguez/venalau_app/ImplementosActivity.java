package com.example.sergiorodriguez.venalau_app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;


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

public class ImplementosActivity extends AppCompatActivity {

    ListView lvImplementos;
    FillListImplementos implementos;

    FillSpinner simplementos;
    Spinner mySpinner;
    String text;
    String datoLista, selected2;

    int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("creado", "creado");
        setContentView(R.layout.activity_implementos);

        lvImplementos=(ListView)findViewById(R.id.lvImplementos);
        Log.d("creado","creado");
        mySpinner=(Spinner) findViewById(R.id.spListarDeporte);
        simplementos = new FillSpinner();
        simplementos.execute();

        //Creamos el adaptador
        /*ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.departamento,android.R.layout.simple_spinner_item);
        //Añadimos el layout para el menú
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Le indicamos al spinner el adaptador a usar
        mySpinner.setAdapter(adapter);*/
        //text = prueba.getSelectedItem().toString();
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=-1) {
                    selected = mySpinner.getSelectedItemPosition();
                    selected2= mySpinner.getSelectedItem().toString();
                    //selected = parent.getSelectedItem();
                    implementos = new FillListImplementos();
                    implementos.execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        //eventos.execute();


    }


    class FillListImplementos extends AsyncTask<String,String, Void> {
        private ProgressDialog progressDialog=new ProgressDialog(ImplementosActivity.this);
        InputStream inputStream=null;
        String result="";


        public FillListImplementos(){

        }

        protected void onPreExecute(){
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    FillListImplementos.this.cancel(true);
                }
            });

        }

        @Override
        protected Void doInBackground(String... params){
            try{


                //String UrlConvert=URLEncoder.encode(selected, "utf-8");
                URL Url=new URL("https://venalau.azurewebsites.net/api/implementos/findId/"+(selected+1));
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
            List<String> listaEventos=new ArrayList<>();
            /*try{
                JSONObject jObject = new JSONObject(result);
                String nombreEvento=jObject.getString("nombre");
                listaEventos.add(nombreEvento);
                this.progressDialog.dismiss();*/
            try {
                JSONArray jArray = new JSONArray(result);
                for(int i=0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    String name = jObject.getString("deporte");
                    listaEventos.add(name);
                    //String tab1_text = jObject.getString("tab1_text");
                    //int active = jObject.getInt("active");

                } // End Loop
                this.progressDialog.dismiss();
            }catch(JSONException e){
                Log.e("JSONException","Error: "+e.toString());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,
                    listaEventos);
            lvImplementos.setAdapter(arrayAdapter);

            lvImplementos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    Intent intent = new Intent(view.getContext(),DetalleImplementoActivity.class);
                    datoLista = (String) lvImplementos.getItemAtPosition(position);

                    intent.putExtra("Dato1",datoLista);
                    intent.putExtra("Dato2",selected2);
                    startActivity(intent);
                }
            });
        }



    }



    class FillSpinner extends AsyncTask<String,String, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(ImplementosActivity.this);
        InputStream inputStream = null;
        String result = "";


        public FillSpinner() {

        }

        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    FillSpinner.this.cancel(true);
                }
            });

        }

        @Override
        protected Void doInBackground(String... params) {
            try {


                //String UrlConvert=URLEncoder.encode(selected, "utf-8");
                URL Url = new URL("https://venalau.azurewebsites.net/api/tipoImplementos/findall");
                //Log.d("URL","http://mascotasu.azurewebsites.net/api/datos/find/"+UrlConvert);
                HttpURLConnection connection = (HttpURLConnection) Url.openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                connection.disconnect();
                is.close();
                result = sb.toString();


            } catch (Exception e) {
                Log.e("StringBuilding", "Error converting result " + e.toString());
            }
            return null;
        }


        protected void onPostExecute(Void v) {
            /*List<String> listaEventos=new ArrayList<>();
            try{
                JSONObject jObject = new JSONObject(result);
                String nombreEvento=jObject.getString("nombre");
                listaEventos.add(nombreEvento);
                this.progressDialog.dismiss();
            }catch(JSONException e){
                Log.e("JSONException","Error: "+e.toString());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,
                    listaEventos);
            lvEventos.setAdapter(arrayAdapter);*/

            List<String> lista = new ArrayList<>();
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    String name = jObject.getString("tipo");
                    lista.add(name);
                    //String tab1_text = jObject.getString("tab1_text");
                    //int active = jObject.getInt("active");

                } // End Loop
                this.progressDialog.dismiss();
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }

            /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,
                    lista);

            lvEventos.setAdapter(arrayAdapter);*/

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                    lista);

            mySpinner.setAdapter(arrayAdapter);


        }
    }

}
