package com.example.mauri.volley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Bienvenido extends AppCompatActivity implements View.OnClickListener{
    //objeto
    TextView campo_bienvenido;
    Button boton_volver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);
        //título
        Bienvenido.this.setTitle("BIENVENIDO");
        //referencia o instanciar
        campo_bienvenido = (TextView) findViewById(R.id.txt_bienvenido);
        boton_volver = (Button) findViewById(R.id.btn_volver);
        //escucha del evento para que se ejecute
        boton_volver.setOnClickListener(this);
        //recibir datos
        Intent intent = getIntent();
        //para que se visualice en el campo de texto
        campo_bienvenido.setText("Bienvenido Usuario: " + intent.getStringExtra(Login.USERNAME));
        //para que se visualice en un mensaje Toast
        Toast.makeText(getApplicationContext(), "Bienvenido Usuario: " + intent.getStringExtra(Login.USERNAME), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v==boton_volver)
        {
            startActivity(new Intent(getApplicationContext(), Login.class));
        }
    }
}

