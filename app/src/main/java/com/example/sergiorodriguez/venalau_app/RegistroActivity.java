package com.example.sergiorodriguez.venalau_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etApellido = (EditText)findViewById(R.id.txNombre);
        etIdIns = (EditText)findViewById(R.id.txIdInst);
        etIdIns = (EditText)findViewById(R.id.txApellido);
        //TipoVinculo = (EditText)findViewById(R.id.txTipoVinculo);
        etCorreo = (EditText)findViewById(R.id.txCorreo);
        etCont = (EditText)findViewById(R.id.txCont);
        //CantMul = (EditText)findViewById(R.id.txCantMul);
        btnRegistrarse = (Button)findViewById(R.id.btnRegistrarse);


        spVinculos = (Spinner) findViewById(R.id.spVinculo);
        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.vinculoArray , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVinculos.setAdapter(spinner_adapter);



    }
}
