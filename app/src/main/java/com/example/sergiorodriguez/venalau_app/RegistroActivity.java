package com.example.sergiorodriguez.venalau_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegistroActivity extends AppCompatActivity {

    EditText Nombre;
    EditText Apellido;
    EditText IdIns;
    //EditText TipoVinculo;
    EditText Correo;
    EditText Cont;
    //EditText CantMul;
    Button Registrarse;
    String contenidoSpinner;
    Spinner Vinculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Nombre = (EditText)findViewById(R.id.txNombre);
        IdIns = (EditText)findViewById(R.id.txIdInst);
        Apellido = (EditText)findViewById(R.id.txApellido);
        //TipoVinculo = (EditText)findViewById(R.id.txTipoVinculo);
        Correo = (EditText)findViewById(R.id.txCorreo);
        Cont = (EditText)findViewById(R.id.txCont);
        //CantMul = (EditText)findViewById(R.id.txCantMul);
        Registrarse = (Button)findViewById(R.id.btnRegistrarse);


        Vinculos = (Spinner) findViewById(R.id.spVinculo);
        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.vinculoArray , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Vinculos.setAdapter(spinner_adapter);

    }
}
