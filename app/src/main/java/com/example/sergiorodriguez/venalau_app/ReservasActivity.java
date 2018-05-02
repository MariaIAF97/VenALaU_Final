package com.example.sergiorodriguez.venalau_app;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservasActivity extends AppCompatActivity {
    Spinner spEspacio;
    FillList espacios;
    FillListDatos datos;
    private TextView txtFecha,txtHora,txtId,txtNombre,txtApellido;
    Button btnReserva;
    int horas,minutos;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mHourSetListener;
    String horaFecha;
    String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("creado","creado");
        setContentView(R.layout.activity_reservas);
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        datos=new FillListDatos();
        datos.execute();

        idUsuario=extras.getString("id");

        spEspacio=(Spinner) findViewById(R.id.spEspacio);
        Log.d("creado","creado");
        espacios=new FillList();
        espacios.execute();
        txtId=(TextView)findViewById(R.id.txtIdReserva);
        txtId.setText(idUsuario);
        btnReserva=(Button)findViewById(R.id.btnReservar);
        txtNombre=(TextView)findViewById(R.id.txtNombreReserva);
        txtApellido=(TextView)findViewById(R.id.txtApellidoReserva);
        txtFecha=(TextView)findViewById(R.id.txtFecha);
        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(
                        ReservasActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;

                String date=year+"-"+month+"-"+day;
                txtFecha.setText(date);
            }
        };

        txtHora=(TextView)findViewById(R.id.txtHora);
        txtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hora=cal.get(Calendar.HOUR_OF_DAY);
                int minutos=cal.get(Calendar.MINUTE);

                TimePickerDialog dialog=new TimePickerDialog(
                        ReservasActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mHourSetListener,
                        hora,minutos,false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mHourSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {


                String time=hour+":0"+min+":00.000";
                txtHora.setText(time);
            }
        };


        btnReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent =new Intent(ReservasActivity.this,ConfirmacionActivity.class);

                /*String nombre=txtNombre.getText().toString();
                String apellido=txtApellido.getText().toString();
                String fecha=txtFecha.getText().toString();
                String hora=txtHora.getText().toString();
                int id=Integer.parseInt(txtId.getText().toString());
                int tipoEspacio=spEspacio.getSelectedItemPosition()+1;
                String nombreEspacio=spEspacio.getSelectedItem().toString();

                intent.putExtra("nombre",nombre);
                intent.putExtra("apellido",apellido);
                intent.putExtra("fecha",fecha);
                intent.putExtra("hora",hora);
                intent.putExtra("id",id);
                intent.putExtra("tipoEspacio",tipoEspacio);
                intent.putExtra("nombreEspacio",nombreEspacio);*/


                //startActivity(intent);
                Intent intent=new Intent(v.getContext(),ConfirmacionActivity.class);
                intent.putExtra("id",txtId.getText().toString());
                intent.putExtra("nombre",txtNombre.getText().toString());
                intent.putExtra("apellido",txtApellido.getText().toString());
                intent.putExtra("tipoEspacio",spEspacio.getSelectedItem().toString());
                intent.putExtra("idEspacio",spEspacio.getSelectedItemPosition()+1);
                intent.putExtra("fecha",txtFecha.getText().toString());
                intent.putExtra("hora",txtHora.getText().toString());

                startActivity(intent);
            }
        });


    }

    //Llenado del Spinner
    class FillList extends AsyncTask<String,String,Void> {
        private ProgressDialog progressDialog=new ProgressDialog(ReservasActivity.this);
        InputStream inputStream=null;
        String result="";

        public FillList(){

        }

        protected void onPreExecute(){
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
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
                URL Url=new URL("http://venalau.azurewebsites.net/api/tipoEspacio/findAll");
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
            try{
                JSONArray jArray=new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject jObject = jArray.getJSONObject(i);
                    String tipo=jObject.getString("tipoEspacio");
                    lista.add(tipo);
                }
                this.progressDialog.dismiss();
            }catch(JSONException e){
                Log.e("JSONException","Error: "+e.toString());
            }

            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,lista);
            spEspacio.setAdapter(arrayAdapter);
        }

    }

    //Insertar reserva
    /*public Connection conexionDB(){
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

   public void agregarReserva(){
        horaFecha=txtFecha.getText().toString()+" "+txtHora.getText().toString();

        try{


            PreparedStatement pst = conexionDB().prepareStatement("insert into miu.Reserva values(?,?,?)");
            pst.setString(1,horaFecha);
            pst.setInt(2,Integer.parseInt(txtId.getText().toString()));
            pst.setInt(3,spEspacio.getSelectedItemPosition()+1);



            pst.executeUpdate();

            conexionDB().close();




        }

        catch(SQLException ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }*/

    //Carga de datos de pantalla inicio de sesión
    class FillListDatos extends AsyncTask<String,String,Void> {
        private ProgressDialog progressDialog=new ProgressDialog(ReservasActivity.this);
        InputStream inputStream=null;
        String result="";

        public FillListDatos(){

        }

        protected void onPreExecute(){
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    FillListDatos.this.cancel(true);
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
            try{
                JSONArray jArray=new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject jObject = jArray.getJSONObject(i);
                    nombre=jObject.getString("nombre");
                    apellido=jObject.getString("apellido");

                }
                this.progressDialog.dismiss();
            }catch(JSONException e){
                Log.e("JSONException","Error: "+e.toString());
            }

            txtNombre.setText(nombre);
            txtApellido.setText(apellido);


        }

    }


}
