package com.example.sergiorodriguez.venalau_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class IniciarSesionEC extends AppCompatActivity {

    Button btnIniciarSesion;
    TextView txtUsuario;
    TextView txtContraseña;
    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion_ec);

        btnIniciarSesion=(Button)findViewById(R.id.btnIniciarSesionE);
        txtUsuario=(TextView)findViewById(R.id.txtIDISE) ;
        txtContraseña=(TextView)findViewById(R.id.txtContISE);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IniciarSesionEC.CheckLogin checkLogin = new IniciarSesionEC.CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkLogin.execute("");

            }
        });


    }
    public class CheckLogin extends AsyncTask<String,String,String>
    {
        private ProgressDialog progressDialog = new ProgressDialog(IniciarSesionEC.this);
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute()
        {
           /* progressDialog.setMessage("Downloading your data...");
            progressDialog.show();*/

        }

        @Override
        protected void onPostExecute(String r)
        {
            Toast.makeText(IniciarSesionEC.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess)
            {
                Toast.makeText(IniciarSesionEC.this , "Login Successfull" , Toast.LENGTH_LONG).show();
                //finish();
            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String usernam = txtUsuario.getText().toString();
            String passwordd = txtContraseña.getText().toString();
            if(usernam.trim().equals("")|| passwordd.trim().equals(""))
                z = "Please enter Username and Password";
            else
            {
                try
                {
                    con = conexionDB();       // Connect to database
                    if (con == null)
                    {
                        z = "Check Your Internet Access!";
                    }
                    else
                    {
                        // Change below query according to your own database.
                        String query = "select * from miu.Persona where IdPersona= '" + usernam.toString() + "' and Contraseña= '"+ passwordd.toString() +"'  ";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next())
                        {
                            z = "Inicio de sesión correcto";
                            isSuccess=true;
                            con.close();
                            Intent intent =new Intent(IniciarSesionEC.this,EstadoCuentaActivity.class);
                            intent.putExtra("id",txtUsuario.getText().toString());
                            startActivity(intent);
                        }
                        else
                        {
                            z = "Datos incorrectos";
                            isSuccess = false;
                        }
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
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
}
