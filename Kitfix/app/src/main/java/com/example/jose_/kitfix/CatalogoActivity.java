package com.example.jose_.kitfix;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CatalogoActivity extends AppCompatActivity {



String urlConsultar="http://187.252.154.103/aplicacionesmoviles/serviciosweb/consultar_catalogo.php";
    ProgressDialog pdEsperar;
    //Nombre de campo del JSON
    //los nombres pueden ser oguales o no
    //con id o sin id
    public static final String IDPRODUCTO="idproducto";
    public static final String IDREFACCIONARIA="nomrefaccionaria";
    public static final String NOMBRE="nombre";
    public static final String MARCA="marca";

    //Arreglo para guardar los datos del JSON
    ArrayList<HashMap<String, String>> Catalogo_List;
    //Adaptador para mostrar los datos del arreglo
    ListAdapter laAdaptador;

    //objeto de la pantalla con el listview
    ListView lvLista=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvLista=(ListView)findViewById(R.id.listViewCatalogo);
        Catalogo_List= new ArrayList<HashMap<String, String>>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAgregarCatalogo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                finish();
            }
        });

        llenarLista();
    }



    public void llenarLista(){
        //inicializar el progressdialog
        pdEsperar= new ProgressDialog(this);
        pdEsperar.setMessage("Cargando datos...");
        pdEsperar.show();

        //iniciams el volley
        JsonObjectRequest recuestConsultar= new JsonObjectRequest(urlConsultar, null, new Response.Listener<JSONObject>() {
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
                            misCampos.put(IDREFACCIONARIA,"Refaccionaria: "+mifila.getString(IDREFACCIONARIA));
                            misCampos.put(IDPRODUCTO,mifila.getString(IDPRODUCTO));
                            misCampos.put(NOMBRE,"Producto: "+mifila.getString(NOMBRE));
                            misCampos.put(MARCA,"Marca: "+mifila.getString(MARCA));

                            Catalogo_List.add(misCampos);
                        }
                        //Mostrar los datos almacenados en el arreglo:Catalogo_list
                        int[]idsTextView={R.id.textViewRefaccionaria,R.id.textViewProducto,R.id.textViewMarca,R.id.textViewCampoLlave};

                        String[] camposTextView= new String []{IDREFACCIONARIA,NOMBRE,MARCA,IDPRODUCTO};

                        //Un adpatador para llenar el formato de la fila
                        laAdaptador = new SimpleAdapter(getApplicationContext(),
                                Catalogo_List,
                                R.layout.formato_lista_catalogo,
                                camposTextView,
                                idsTextView);
                        //llenar la lista con el adaptador
                        lvLista.setAdapter(laAdaptador);

                        //Habilitar el click en el elemento seleccionado
                        lvLista.setOnItemClickListener(new ListItemClickListener());

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
        String Insertar_Etiqueta="com.example.jose_.kitfixapp.volleyStringRquest";
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(recuestConsultar,Insertar_Etiqueta);
    }

    class ListItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //Preparar la siguiente pantalla detalle
            Intent pantallaDetalle=new Intent(CatalogoActivity.this,CatalogoDetalle.class);

            //Mandarle un parametro desde una pantalla a la otra
            pantallaDetalle.putExtra("productoid",Catalogo_List.get(position));

            //Inicie la visualizacion de la pantalla detalle
            startActivity(pantallaDetalle);

        }
    }





}
