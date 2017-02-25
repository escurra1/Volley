package com.example.mauri.volley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    //ruta
    private static final String URL = "http://192.168.1.35:9090/ejercicios/Android/Proyecto12/registrar.php";
    //base de datos
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    //objetos
    EditText campo_username, campo_email, campo_password;
    Button boton_registrar, boton_login;
    CheckBox check_mostrar_password;
    //variables privadas para obtener los datos y para ver si hay error o no
    private String username, password, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        //título
        Registrar.this.setTitle("REGISTRAR USUARIO");
        //referencias o instanciar
        campo_username = (EditText) findViewById(R.id.txt_usuario);
        campo_email = (EditText) findViewById(R.id.txt_email);
        campo_password = (EditText) findViewById(R.id.txt_password);
        boton_registrar = (Button) findViewById(R.id.btn_registrar);
        boton_login = (Button) findViewById(R.id.btn_login);
        check_mostrar_password = (CheckBox) findViewById(R.id.check_mostrar);
        //eventos para que escuchen y se pueda ejecutar los botones
        boton_registrar.setOnClickListener(this);
        boton_login.setOnClickListener(this);
        check_mostrar_password.setOnCheckedChangeListener(this);
    }

    //método limpiar campos
    public void limpiarCampos(){
        campo_username.setText(null);
        campo_email.setText(null);
        campo_password.setText(null);
        campo_username.requestFocus();
    }

    //método para obtener campos
    public void obtenerCampos()
    {
        username = campo_username.getText().toString().trim();
        email = campo_email.getText().toString().trim();
        password = campo_password.getText().toString().trim();
    }

    //método para validar campos
    public boolean validarCampos(){
        boolean estado=true;
        if (username.isEmpty()){
            campo_username.setError("Ingrese un usuario por favor");
            estado = false;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            campo_email.setError("Ingrese un email válido por favor");
            estado = false;
        }
        if (password.isEmpty() || password.length()<5){
            campo_password.setError("Ingrese un password mayor a 5 carácteres");
            estado = false;
        }
        return estado;
    }

    //método aceptar campos
    public void aceptarCampos(){
        //llamar al método obtener campos
        obtenerCampos();
        if (!validarCampos()){
            Toast.makeText(getApplicationContext(), "Llene todos los campos por favor", Toast.LENGTH_SHORT).show();
        }else{
            //llamar al método registrar usuario
            registrarUsuario();
            limpiarCampos();
        }
    }

    //método mostrar contraseña
    public void mostrarContraseña(){
        if (!check_mostrar_password.isChecked()){
            //para que se muestre
            campo_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }else
        {
            //para que se oculte
            campo_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    //método para registrar usuario
    public void registrarUsuario(){
        //obtenemos los datos de los campos
        final String username = campo_username.getText().toString().trim();
        final String email = campo_email.getText().toString().trim();
        final String password = campo_password.getText().toString().trim();
        //librería de Volley
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametro = new HashMap<String, String>();
                parametro.put(USERNAME,username);
                parametro.put(PASSWORD,password);
                parametro.put(EMAIL,email);
                return parametro;
            }
        };

        //para crear las peticiones
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //agregar al volley el obeto stringRequest
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v)
    {
        if (v==boton_registrar)
        {
            //llamamos al método aceptar campos
            aceptarCampos();
        }
        if(v==boton_login)
        {
            startActivity(new Intent(getApplicationContext(), Login.class));
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView==check_mostrar_password)
        {
            //llamamos al método mostrar contraseña
            mostrarContraseña();
        }

    }
}
