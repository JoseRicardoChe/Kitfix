package com.example.jose_.kitfix;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProductoActivity extends AppCompatActivity {

    String urlAgregarProducto= "http://api.easysoftpc.com.mx/android/mysql/crud/insertar.php";
    EditText textProducto;
    ProgressDialog pdEsperar;
    Button btnInsertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        textProducto=(EditText)findViewById(R.id.editTextProducto);
        btnInsertar=(Button)findViewById(R.id.buttonInsertar);
        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoRegistro();
            }
        });

        pdEsperar=new ProgressDialog(this);
        pdEsperar.setMessage("Conectando... por favor esperar");
        pdEsperar.setCancelable(false);
    }

    public void nuevoRegistro(){
        //validar que exista datos
        if (textProducto.getText().toString().isEmpty()){
            //Mensaje de error
            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
            return;
        }
        //iniciar el progress dialog
        pdEsperar.show();
        final String strDescripcion=textProducto.getText().toString();

        StringRequest requestInsertar=
                new StringRequest(Request.Method.POST, urlAgregarProducto,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                pdEsperar.dismiss();
                                textProducto.setText("");
                                Toast.makeText(getApplicationContext(),"Registro agregado!",Toast.LENGTH_LONG).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdEsperar.dismiss();

                        Toast.makeText(getApplicationContext(),"Error del envio",Toast.LENGTH_LONG).show();

                    }
                }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params=new HashMap<String, String>();
                        params.put("item name",strDescripcion);
                        return params;
                    }
                };
        String Insertar_Etiqueta="com.example.jose_.kitfixapp.volleyStringRquest";
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(requestInsertar,Insertar_Etiqueta);
    }


}
