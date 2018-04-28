package com.example.sergiorodriguez.venalau_app;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservasActivity extends AppCompatActivity {

    Spinner spEspacio;
    FillList espacios;
    private TextView txtFecha,txtHora;
    int horas,minutos;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mHourSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("creado","creado");
        setContentView(R.layout.activity_reservas);
        spEspacio=(Spinner) findViewById(R.id.spEspacio);
        Log.d("creado","creado");
        espacios=new FillList();
        espacios.execute();

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

                String date=month+"-"+day+"-"+year;
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


                String time=hour+":"+min;
                txtHora.setText(time);
            }
        };


    }

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
                URL Url=new URL("http://venalau.azurewebsites.net/api/espacio/findAll");
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
                    String tipo=jObject.getString("tipo");
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
}
