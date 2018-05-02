package com.example.sergiorodriguez.venalau_app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

public class EstadoCuentaActivity extends AppCompatActivity {
    EditText etNombre;
    EditText etApellido;
    EditText etID;
    EditText etMultas;
    ListView lvReservas;
    String id;
    FillListDatos fillListDatos;
    FillList reservas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_cuenta);
        Intent intent= getIntent();
        Bundle extras=intent.getExtras();
        id=extras.getString("id");

        etNombre=(EditText)findViewById(R.id.txtNombreEC);
        etApellido=(EditText)findViewById(R.id.txtApellidoEC);
        etID=(EditText)findViewById(R.id.txtIDEC);
        etMultas=(EditText)findViewById(R.id.txtMultasEC);
        lvReservas=(ListView)findViewById(R.id.lvReservas);

        etID.setText(id);

        fillListDatos=new FillListDatos();
        fillListDatos.execute();

        reservas=new FillList();
        reservas.execute();



    }
    //Llena los datos de la consulta
    class FillListDatos extends AsyncTask<String,String,Void> {
        private ProgressDialog progressDialog=new ProgressDialog(EstadoCuentaActivity.this);
        InputStream inputStream=null;
        String result="";
        int idUsuario=Integer.parseInt(id.toString());

        public FillListDatos(){

        }

        protected void onPreExecute(){
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    EstadoCuentaActivity.FillListDatos.this.cancel(true);
                }
            });

        }

        @Override
        protected Void doInBackground(String... params){
            try{
                URL Url=new URL("http://venalau.azurewebsites.net/api/persona/find/"+idUsuario);
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
            List<String> lista=new ArrayList<>();
            String nombre="";
            String apellido="";
            String cantidadMultas="";

            try{
                JSONArray jArray=new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject jObject = jArray.getJSONObject(i);
                    nombre=jObject.getString("nombre");
                    apellido=jObject.getString("apellido");
                    cantidadMultas=jObject.getString("multas");

                }
                this.progressDialog.dismiss();
            }catch(JSONException e){
                Log.e("JSONException","Error: "+e.toString());
            }

            etNombre.setText(nombre);
            etApellido.setText(apellido);
            etMultas.setText("Cantidad multas: "+cantidadMultas);


        }

    }
    class FillList extends AsyncTask<String,String,Void> {
        private ProgressDialog progressDialog=new ProgressDialog(EstadoCuentaActivity.this);
        InputStream inputStream=null;
        String result="";
        int idUsuario=Integer.parseInt(id.toString());

        public FillList(){

        }

        protected void onPreExecute(){
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    EstadoCuentaActivity.FillList.this.cancel(true);
                }
            });

        }

        @Override
        protected Void doInBackground(String... params){
            try{
                URL Url=new URL("http://venalau.azurewebsites.net/api/reserva/find/"+idUsuario);
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
            List<String> lista=new ArrayList<>();
            String date="";

            try{
                JSONArray jArray=new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject jObject = jArray.getJSONObject(i);
                    date = jObject.getString("horaFecha");
                    lista.add(date);
                }
                this.progressDialog.dismiss();
            }catch(JSONException e){
                Log.e("JSONException","Error: "+e.toString());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,
                    lista);
            lvReservas.setAdapter(arrayAdapter);


        }

    }

}
