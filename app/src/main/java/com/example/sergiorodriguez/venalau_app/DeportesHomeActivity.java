package com.example.sergiorodriguez.venalau_app;

import android.content.Intent;
import android.net.Uri;
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
                Intent intent =new Intent(DeportesHomeActivity.this,IniciarSesionActivity.class);
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
        llCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://docs.google.com/document/d/e/2PACX-1vSzELjrGeSP8j1tQVyeQ9IUuVFsL8GuuMBhfmn2VCN8TGQ0P9_DyWVyMq8c0auz3NW9NccdQDiRHnEj/pub");
                Intent intent =new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        llPiscina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://docs.google.com/document/d/e/2PACX-1vTqGd8OwrYpzsNt9yX9UAfCypD_RyF8eI2W-7LBz8KZhGanf83gTYg8_e-g1hytNc_K793tk_fBBpuA/pub");
                Intent intent =new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });



    }
}
