package com.example.mauri.volley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

public class Login extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    //ruta
    private static final String URL = "http://192.168.1.35:9090/ejercicios/Android/Proyecto12/login.php";
    //de la base de datos
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    //objetos
    EditText campo_username, campo_password;
    Button boton_ingresar, boton_regresar;
    CheckBox check_mostar_password2;
    //variables privadas para obtener los campos y ver los errores
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //título
        Login.this.setTitle("LOGIN");
        //referencia o instanciar
        campo_username = (EditText) findViewById(R.id.txt_usuario_login);
        campo_password = (EditText) findViewById(R.id.txt_password_login);
        check_mostar_password2 = (CheckBox) findViewById(R.id.check_mostrar2);
        boton_ingresar = (Button) findViewById(R.id.btn_ingresar);
        boton_regresar = (Button) findViewById(R.id.btn_regresar);
        //los eventos para que se ejecute
        boton_ingresar.setOnClickListener(this);
        boton_regresar.setOnClickListener(this);
        check_mostar_password2.setOnCheckedChangeListener(this);
    }

    //método para mostrar constraseña
    public void mostarContraseña()
    {
        if (!check_mostar_password2.isChecked()){
            //mostrar contraseña
            campo_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }else{
            //para que se oculte
            campo_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    //método para limpiar campos
    public void limpiarCampos()
    {
        campo_username.setText(null);
        campo_password.setText(null);
        campo_username.requestFocus();
    }

    //método para obtener campos
    public void obtenerCampos(){
        username = campo_username.getText().toString().trim();
        password = campo_password.getText().toString().trim();
    }

    //método para validar campos
    public boolean validarCampos(){
        boolean estado = true;
        if (username.isEmpty())
        {
            campo_username.setError("Ingrese su usuario por favor");
            estado = false;
        }
        if (password.isEmpty() || password.length()<5){
            campo_password.setError("Ingrese un password mayor a 5 carácteres");
            estado = false;
        }
        return estado;
    }

    //método para aceptar los campos
    public void aceptarCampos(){
        //llamamos al método obtener campos
        obtenerCampos();
        if (!validarCampos()){
            Toast.makeText(getApplicationContext(), "Llene todos los campos por favor", Toast.LENGTH_SHORT).show();
        }else{
            //llamamos a los método
            ingresarUsuario();
            limpiarCampos();
        }
    }

    //método para ingresar Usuario
    public void ingresarUsuario(){
        username = campo_username.getText().toString().trim();
        password = campo_password.getText().toString().trim();
        //librería de volley
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success"))
                {
                    enviarDatos();
                }else
                {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametro = new HashMap<String, String>();
                parametro.put(USERNAME, username);
                parametro.put(PASSWORD, password);
                return parametro;
            }
        };

        //para las peticiones
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //agregar al volley el obeto stringRequest
        requestQueue.add(stringRequest);
    }

    //método enviarDatos
    public void enviarDatos(){
        Intent intent = new Intent(getApplicationContext(), Bienvenido.class);
        intent.putExtra(USERNAME, username);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v==boton_ingresar)
        {
            //llamamos al método aceptar campos
            aceptarCampos();
        }
        if (v==boton_regresar)
        {
            startActivity(new Intent(getApplicationContext(), Registrar.class));
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView==check_mostar_password2)
        {
            //llamamos al método mostrar contraseña
            mostarContraseña();
        }

    }
}
