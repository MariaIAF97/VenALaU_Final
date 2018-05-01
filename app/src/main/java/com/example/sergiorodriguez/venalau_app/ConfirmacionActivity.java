package com.example.sergiorodriguez.venalau_app;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion);

        Intent intent = getIntent();
        Bundle extras= intent.getExtras();
        String nombre= extras.getString("nombre");
        String apellido=extras.getString("apellido");
        String fecha=extras.getString("fecha");
        String hora=extras.getString("hora");
        int id=extras.getInt("id");
        int tipoEspacio=extras.getInt("tipoEspacio");
        String nombreEspacio=extras.getString("nombreEspacio");

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


    }

    //Insertar reserva
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


        String horaFecha=txtFecha.getText().toString()+" "+txtHora.getText().toString();


        try{


            PreparedStatement pst = conexionDB().prepareStatement("insert into miu.Reserva values(?,?,?)");
            pst.setString(1,horaFecha);
            pst.setInt(2,Integer.parseInt(txtIdInst.getText().toString()));
            pst.setInt(3,idLugar);



            pst.executeUpdate();

            conexionDB().close();
        }

        catch(SQLException ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}
