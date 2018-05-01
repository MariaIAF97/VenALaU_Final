package com.example.sergiorodriguez.venalau_app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    EditText etNombre;
    EditText etApellido;
    EditText etIdIns;
    //EditText TipoVinculo;
    EditText etCorreo;
    EditText etCont;
    //EditText CantMul;
    Button btnRegistrarse;
    String contenidoSpinner;
    Spinner spVinculos;
    FillList vinculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Log.d("creado", "creado");
        Log.d("creado", "creado");
        vinculos=new RegistroActivity.FillList();
        vinculos.execute();

        etNombre = (EditText)findViewById(R.id.txNombre);
        etIdIns = (EditText)findViewById(R.id.txIdInst);
        etApellido = (EditText)findViewById(R.id.txApellido);
        //TipoVinculo = (EditText)findViewById(R.id.txTipoVinculo);
        etCorreo = (EditText)findViewById(R.id.txCorreo);
        etCont = (EditText)findViewById(R.id.txCont);
        spVinculos=(Spinner)findViewById(R.id.spVinculo);
        //CantMul = (EditText)findViewById(R.id.txCantMul);
        btnRegistrarse = (Button)findViewById(R.id.btnRegistrarse);
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarUsuario();
            }
        });


    }

    public Connection conexionDB(){
        Connection conexion = null;
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://venalau.database.windows.net;port=1433;databaseName=VenALaUDB;user=adminvenalau;password=venalau2018!;");
        }

        catch(Exception ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return conexion;
    }

    public void agregarUsuario(){


        try{


            PreparedStatement pst = conexionDB().prepareStatement("insert into miu.Persona values(?,?,?,?,?,?,?)");
            pst.setInt(1,Integer.parseInt(etIdIns.getText().toString()));
            pst.setInt(2,spVinculos.getSelectedItemPosition()+1);
            pst.setString(3,etNombre.getText().toString());

            pst.setString(4,etApellido.getText().toString());

            pst.setString(5,etCorreo.getText().toString());


            pst.setString(6,etCont.getText().toString());
            pst.setInt(7,0);

            pst.executeUpdate();

            conexionDB().close();
        }

        catch(SQLException ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    //Consultas

    //Inicia llenado de Spinner desde la API
    class FillList extends AsyncTask<String, String, Void> {


        private ProgressDialog progressDialog = new ProgressDialog(RegistroActivity.this);
        InputStream inputStream = null;
        String result = "";

        public FillList() {

        }

        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    RegistroActivity.FillList.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {


            try {
                URL Url = new URL("http://venalau.azurewebsites.net/api/vinculo/findall");
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
            //parse JSON data
            List<String> lista = new ArrayList<>();
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    String name = jObject.getString("tipoVinculo");
                    lista.add(name);
                    //String tab1_text = jObject.getString("tab1_text");
                    //int active = jObject.getInt("active");

                } // End Loop
                this.progressDialog.dismiss();
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                    lista);

            spVinculos.setAdapter(arrayAdapter);

        }
    }


}
