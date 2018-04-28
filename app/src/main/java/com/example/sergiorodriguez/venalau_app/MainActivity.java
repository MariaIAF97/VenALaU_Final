package com.example.sergiorodriguez.venalau_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout llRegistro;
    LinearLayout llEventos;
    LinearLayout llDeportes;
    LinearLayout llAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llRegistro=(LinearLayout)findViewById(R.id.llregistro);
        llEventos=(LinearLayout)findViewById(R.id.lleventos);
        llDeportes=(LinearLayout)findViewById(R.id.lldeportes);
        llAbout=(LinearLayout)findViewById(R.id.llabout);

        llRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,RegistroActivity.class);
                startActivity(intent);
            }
        });

        llEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,EventosActivity.class);
                startActivity(intent);
            }
        });
        llDeportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,DeportesHomeActivity.class);
                startActivity(intent);
            }
        });
        llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

    }
}
