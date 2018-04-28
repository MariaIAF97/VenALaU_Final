package com.example.sergiorodriguez.venalau_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class DeportesHomeActivity extends AppCompatActivity {

    LinearLayout llReservas;
    LinearLayout llImplementos;
    LinearLayout llCursos;
    LinearLayout llPiscina;
    LinearLayout llCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deportes_home);

        llReservas=(LinearLayout)findViewById(R.id.llreservas);
        llImplementos=(LinearLayout)findViewById(R.id.llimplementos);
        llCursos=(LinearLayout)findViewById(R.id.llcursos);
        llPiscina=(LinearLayout)findViewById(R.id.llpiscina);
        llCuenta=(LinearLayout)findViewById(R.id.llcuenta);

        llReservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(DeportesHomeActivity.this,ReservasActivity.class);
                startActivity(intent);
            }
        });
        llImplementos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(DeportesHomeActivity.this,ImplementosActivity.class);
                startActivity(intent);
            }
        });
        llCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(DeportesHomeActivity.this,EstadoCuentaActivity.class);
                startActivity(intent);
            }
        });


    }
}
