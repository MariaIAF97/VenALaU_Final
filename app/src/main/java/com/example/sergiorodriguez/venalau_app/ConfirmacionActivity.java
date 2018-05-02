package com.example.sergiorodriguez.venalau_app;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConfirmacionActivity extends AppCompatActivity {

    TextView txtNombreConf;
    TextView txtApellidoConf;
    TextView txtIdInst;
    TextView txtFecha;
    TextView txtHora;
    TextView txtLugar;
    int idLugar;
    String horaFecha;
    String nombre;
    String apellido;
    String fecha;
    String hora;
    String id;
    int tipoEspacio;
    Button btnConfirmacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion);

        Intent intent = getIntent();
        Bundle extras= intent.getExtras();
        nombre= extras.getString("nombre");
        apellido=extras.getString("apellido");
        fecha=extras.getString("fecha");
        hora=extras.getString("hora");
        id=extras.getString("id");
        tipoEspacio=extras.getInt("idEspacio");
        String nombreEspacio=extras.getString("tipoEspacio");

        btnConfirmacion=(Button)findViewById(R.id.btnConfirmar);
        txtApellidoConf=(TextView)findViewById(R.id.txApellidoConf);
        txtNombreConf=(TextView)findViewById(R.id.txNombreConf);
        txtIdInst=(TextView)findViewById(R.id.txtIdConf);
        txtHora=(TextView)findViewById(R.id.txtHoraConf);
        txtFecha=(TextView)findViewById(R.id.txtFechaConf);
        txtLugar=(TextView)findViewById(R.id.txtLugarConf);

        txtIdInst.setText(id);
        txtNombreConf.setText(nombre);
        txtApellidoConf.setText(apellido);
        txtHora.setText(hora);
        txtFecha.setText(fecha);
        txtLugar.setText(nombreEspacio);
        int idLugar=tipoEspacio;


        btnConfirmacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarReserva();


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

    public void agregarReserva(){
        horaFecha=txtFecha.getText().toString()+" "+txtHora.getText().toString();

        try{


            PreparedStatement pst = conexionDB().prepareStatement("insert into miu.Reserva values(?,?,?)");
            pst.setString(1,horaFecha);
            pst.setInt(2,Integer.parseInt(id.toString()));
            pst.setInt(3,tipoEspacio);



            pst.executeUpdate();

            conexionDB().close();

            Toast toast1 = Toast.makeText(getApplicationContext(), "Reserva satisfactoria", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();

            Intent intent1=new Intent(ConfirmacionActivity.this,DeportesHomeActivity.class);
            startActivity(intent1);


        }

        catch(SQLException ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}
