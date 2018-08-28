package net.lizame.naturlife.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.lizame.naturlife.R;
import net.lizame.naturlife.buscar.Item;
import net.lizame.naturlife.buscar.MirarPed;
import net.lizame.naturlife.buscar.PedidoAdapter;
import net.lizame.naturlife.buscar.VProductos;
import net.lizame.naturlife.core.Session;
import net.lizame.naturlife.core.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MirarClienteActivity extends AppCompatActivity {
    TextView tv_ruc,tv_ciudad,tv_zona,tv_cupo;
    private ProgressBar pb;
    private ViewPager vp;
    Session session;
    RecyclerView.LayoutManager lm;
    public List<VProductos> contactList;
    private MirarPed mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirar_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pb = findViewById(R.id.pb_nuevopro);
        vp = findViewById(R.id.vp_pb);
        tv_ruc = findViewById(R.id.tv_ruc);
        tv_ciudad = findViewById(R.id.tv_ciudad);
        tv_zona = findViewById(R.id.tv_zona);
        tv_cupo = findViewById(R.id.tv_cupos);
        session = new Session(getApplicationContext());


        recyclerView = findViewById(R.id.rc_mirar);
        contactList = new ArrayList<>();
        mAdapter = new MirarPed(this, contactList);
        lm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(lm);

        // white background notification bar
        String urling = core.BASE_URL + "clientes/bdcliente_ws.php?lol=listo";
        RequestQueue queue4 = Volley.newRequestQueue(getApplicationContext());
        urling = urling.replaceAll(" ", "%20");
        final ArrayList<VProductos> items = new ArrayList<>();
        StringRequest stringRequest4 = new StringRequest(Request.Method.POST,urling,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                // mTextView.setText("Response is: "+ response.substring(0,500));
                Log.i("Success","Todo Belen "+response);
                if(response.length()>0 || response != null){
                    try {
                        JSONArray ja = new JSONArray(response);
                       /* Log.i("sizejson","a"+ja.length());
                        Log.i("sizejson2",""+json_data.getString("clinombre"));
                        Log.i("sizejson3",""+json_data2.getString("clinombre"));*/
String ciudad = "",ruccito = "",zona = "",cupo = "";

                        for (int i = 0;i<ja.length();i++){
                            JSONObject json_data = ja.getJSONObject(i);
                             ciudad = json_data.getString("ciudad");
                             ruccito = json_data.getString("cliruc");
                             zona = json_data.getString("zona");
                             cupo = json_data.getString("cupo");
                            String pednumped = json_data.getString("pednumped");
                            String fechped = json_data.getString("fpedido");
                            String precio = json_data.getString("precio");

                            if(!pednumped.equals(""))
                            {
                                items.add(new VProductos(fechped, "",pednumped,"$"+precio));

                            }else{
                                Toast toast = Toast.makeText(getApplication(),"No se encontro Pedidos.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, -100);
                                toast.show();
                            }

                        }
                        tv_ruc.setText(ruccito);
                        tv_ciudad.setText(ciudad);
                        tv_zona.setText(zona);
                        tv_cupo.setText(cupo);
                        contactList.clear();
                        contactList.addAll(items);
                        recyclerView.setAdapter(mAdapter);
                        //mAdapter.notifyDataSetChanged();

                        pb.setVisibility(View.INVISIBLE);
                        vp.setVisibility(View.INVISIBLE);




                    } catch (JSONException e) {
                        pb.setVisibility(View.INVISIBLE);
                        vp.setVisibility(View.INVISIBLE);
                        Toast toast = Toast.makeText(getApplication(),"No se encontro Pedidos.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, -100);
                        toast.show();
                        Log.i("sizejson","");
                        e.printStackTrace();
                    }


                }else{

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                pb.setVisibility(View.INVISIBLE);
                vp.setVisibility(View.INVISIBLE);
                Log.i("Error","Todo Malon "+error.toString());

                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
            Log.i("ID",""+session.getclicodigo());
                Map<String, String>  params = new HashMap<String, String>();
                params.put("clicodigo", session.getclicodigo());



                return params;
            }

            /*stringRequest4.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
        };
        queue4.add(stringRequest4);


    }

}
