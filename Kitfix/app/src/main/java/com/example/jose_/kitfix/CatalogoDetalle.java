package com.example.jose_.kitfix;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CatalogoDetalle extends AppCompatActivity {

    String urlConsultarDetalle="http://187.252.154.103/aplicacionesmoviles/serviciosweb/consultar_detalle.php";
    String urlImagenes="http://187.252.154.103/aplicacionesmoviles/serviciosweb/imagenes/";

    TextView txtViewRefeaccionaria, txtViewProducto, txtViewMarca, txtViewPrecio, txtViewModelo,txtViewDescripcion;
    ImageView imageView_Imagen;
    ProgressDialog pdEsperar;
    String strCampoLlave="";

    //Arreglo para guardar los datos del JSON
    ArrayList<HashMap<String, String>> Catalogo_Detalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCerrar_Detalle);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pdEsperar=new ProgressDialog(this);
        pdEsperar.setMessage("Conectandose...");
        pdEsperar.setCancelable(false);

        Catalogo_Detalle= new ArrayList<HashMap<String, String>>();

        txtViewRefeaccionaria=(TextView)findViewById(R.id.textView_Refaccionaria);
        txtViewProducto=(TextView)findViewById(R.id.textView_Producto);
        txtViewMarca=(TextView)findViewById(R.id.textView_Marca);
        txtViewPrecio=(TextView)findViewById(R.id.textView_Precio);
        txtViewModelo=(TextView)findViewById(R.id.textView_Modelo);
        txtViewDescripcion=(TextView)findViewById(R.id.textView_Descripcion);
        txtViewDescripcion.setMovementMethod(new ScrollingMovementMethod());

        imageView_Imagen=(ImageView)findViewById(R.id.imageView_Imagen);

        //Obtener parametros de la pantalla listado
        Intent i =getIntent();
        HashMap<String,String> miProducto= (HashMap<String, String>)i.getSerializableExtra("productoid");
        txtViewRefeaccionaria.setText(miProducto.get(CatalogoActivity.IDREFACCIONARIA));
        txtViewProducto.setText(miProducto.get(CatalogoActivity.NOMBRE));
        //txtViewMarca.setText(miProducto.get(CatalogoActivity.MARCA));
        strCampoLlave=miProducto.get(CatalogoActivity.IDPRODUCTO);

        TraeDetalle();
    }

    public void TraeDetalle () {

        pdEsperar= new ProgressDialog(this);
        pdEsperar.setMessage("Cargando datos...");
        pdEsperar.show();

        //iniciams el volley
        JsonObjectRequest recuestConsultar= new JsonObjectRequest(urlConsultarDetalle + "?id="+ strCampoLlave,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //si hay datos y tuviste exito
                try
                {
                    int miSuccess= response.getInt("success");
                    if (miSuccess==1){
                        JSONArray miCatalogo= response.getJSONArray("productos");
                        for (int i=0;i<miCatalogo.length();i++){
                            JSONObject mifila=miCatalogo.getJSONObject(i);
                            HashMap<String,String>misCampos=new HashMap<String, String>();
                            misCampos.put("modelo",mifila.getString("modelo"));
                            misCampos.put("descripcion",mifila.getString("descripcion"));
                            misCampos.put("precio",mifila.getString("precio"));
                            misCampos.put("imagen",mifila.getString("imagen"));
                            misCampos.put("marca",mifila.getString("marca"));

                            Catalogo_Detalle.add(misCampos);
                        }

                        String strModeloyMarca="Modelo: "+
                                Catalogo_Detalle.get(0).get("modelo")+
                                " | Marca: "+
                                Catalogo_Detalle.get(0).get("marca");

                        txtViewMarca.setText(strModeloyMarca);

                        txtViewPrecio.setText("Precio: $"+Catalogo_Detalle.get(0).get("precio")+" MXN ");

                        txtViewDescripcion.setText("Descripcion: \n\n"+Catalogo_Detalle.get(0).get("descripcion"));

                        Picasso.get().load(urlImagenes+Catalogo_Detalle.get(0).get("imagen")).into(imageView_Imagen);

                        //prgressdialog apagar
                        pdEsperar.dismiss();

                    }
                    if (miSuccess==0){
                        Toast.makeText(getApplicationContext(),"Error del envio",Toast.LENGTH_LONG).show();
                    }
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdEsperar.dismiss();
                Toast.makeText(getApplicationContext(),"ERROR DE CONEXION CON EL SERVIDOR",Toast.LENGTH_LONG).show();            }

        });
        String Detalle_Etiqueta="com.example.jose_.kitfixapp.volleyJsonObjectRecuestDetalle";
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(recuestConsultar,Detalle_Etiqueta);
    }

}
