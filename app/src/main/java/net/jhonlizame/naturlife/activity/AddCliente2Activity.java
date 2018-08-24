package net.jhonlizame.naturlife.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.jhonlizame.naturlife.R;
import net.jhonlizame.naturlife.buscar.Productos;
import net.jhonlizame.naturlife.core.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddCliente2Activity extends AppCompatActivity {

    private MenuItem send;
    EditText ed_EPedido,ed_dirFarmacia,ed_HVHasta2,ed_dirConsultorio,
            ed_HVDesde,ed_profClient,ed_obs,ed_correo,ed_espCliente,ed_website;
    Spinner sp_EntPedido,sp_DVisita;
    String clinombre,cliruc,clitelef1,clitelef2,clitelef3,clitelmovil,
            tipcodigo,ciucodigo,zoncodigo,clisexo,cliecategoria,clifecnac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cliente2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main3, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        send = menu.findItem(R.id.action_send);
        send.setVisible(true);



        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            clinombre = getIntent().getStringExtra("clinombre");
            cliruc = getIntent().getStringExtra("cliruc");
            clitelef1 = getIntent().getStringExtra("clitelef1");
            clitelef2 = getIntent().getStringExtra("clitelef2");
            clitelef3 = getIntent().getStringExtra("clitelef3");
            clitelmovil = getIntent().getStringExtra("clitelmovil");
            tipcodigo = getIntent().getStringExtra("tipcodigo");
            ciucodigo = getIntent().getStringExtra("ciucodigo");
            zoncodigo = getIntent().getStringExtra("zoncodigo");
            clisexo = getIntent().getStringExtra("clisexo");
            cliecategoria = getIntent().getStringExtra("cliecategoria");
            clifecnac = getIntent().getStringExtra("clifecnac");

            ed_EPedido = findViewById(R.id.ed_EPedido);
            ed_dirFarmacia = findViewById(R.id.ed_dirFarmacia);
            ed_HVHasta2 = findViewById(R.id.ed_HVHasta2);
            ed_dirConsultorio = findViewById(R.id.ed_dirConsultorio);
            ed_HVDesde = findViewById(R.id.ed_HVDesde);
            ed_profClient = findViewById(R.id.ed_profClient);
            ed_obs = findViewById(R.id.ed_obs);
            ed_correo = findViewById(R.id.ed_correo);
            ed_espCliente = findViewById(R.id.ed_espCliente);
            ed_website = findViewById(R.id.ed_website);
            sp_EntPedido = findViewById(R.id.sp_EntPedido);
            sp_DVisita = findViewById(R.id.sp_DVisita);
            //mm
            String urling = core.BASE_URL + "clientes/nclientes_ws.php?lol=listo";
            RequestQueue queue4 = Volley.newRequestQueue(getApplicationContext());
            urling = urling.replaceAll(" ", "%20");
            final ArrayList<Productos> items = new ArrayList<>();
            StringRequest stringRequest4 = new StringRequest(Request.Method.POST,urling,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    Log.i("Error","Todo Malon "+response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //mTextView.setText("That didn't work!");

                    Log.i("Error","Todo Malon "+error.toString());

                    error.printStackTrace();

                }
            }){
                @Override
                protected Map<String, String> getParams()
                {
                    final String bc_string = "";
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("clinombre", clinombre);
                    params.put("cliruc", cliruc);
                    params.put("clitelef1", clitelef1);
                    params.put("clitelef2", clitelef2);
                    params.put("clitelef3", clitelef3);
                    params.put("clitelmovil", clitelmovil);
                    params.put("tipcodigo", tipcodigo);
                    params.put("ciucodigo", ciucodigo);
                    params.put("zoncodigo", zoncodigo);
                    params.put("clisexo", clisexo);
                    params.put("cliecategoria", cliecategoria);
                    params.put("clifecnac", clifecnac);
                    params.put("clidiapagofac", ed_EPedido.getText().toString());
                    params.put("clidirfarma", ed_dirFarmacia.getText().toString());
                    params.put("clihoravisitahasta", ed_HVHasta2.getText().toString());
                    params.put("clihoravisitadesde", ed_HVDesde.getText().toString());
                    params.put("cliedirconsul", ed_dirConsultorio.getText().toString());
                    params.put("cliprofesion", ed_profClient.getText().toString());
                    params.put("cliobserva", ed_obs.getText().toString());
                    params.put("cliemail", ed_correo.getText().toString());
                    params.put("cliespecialidad", ed_espCliente.getText().toString());
                    params.put("website", ed_website.getText().toString());
                    params.put("clidiaentregaped", sp_EntPedido.getSelectedItem().toString());
                    params.put("clidiavisita", sp_DVisita.getSelectedItem().toString());

                    return params;
                }

            /*stringRequest4.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
            };
            queue4.add(stringRequest4);

        }
        return super.onOptionsItemSelected(item);

    }

}
