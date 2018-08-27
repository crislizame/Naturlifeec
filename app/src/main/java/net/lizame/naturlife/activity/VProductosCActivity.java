package net.lizame.naturlife.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.lizame.naturlife.R;
import net.lizame.naturlife.buscar.Productos;
import net.lizame.naturlife.buscar.VProductos;
import net.lizame.naturlife.buscar.VProductosAdapter;
import net.lizame.naturlife.core.DetectNet;
import net.lizame.naturlife.core.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VProductosCActivity extends AppCompatActivity implements View.OnClickListener {
    private  VProductosAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private  ArrayList<VProductos> data;
    private ProgressBar pb;
    private ViewPager vp;
    String tipo;
    private SearchView searchView;
DetectNet dn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vproductos_c);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pb = findViewById(R.id.pb_nuevopro);
        vp = findViewById(R.id.vp_pb);
        tipo = getIntent().getStringExtra("tipo");
        dn = new DetectNet(getApplicationContext(),this);

        dn.isNetDisponible();
        dn.isOnlineNet();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        data = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<>();
        adapter = new VProductosAdapter(getApplicationContext(),data);
        recyclerView.setAdapter(adapter);

        pb.setVisibility(View.VISIBLE);
        vp.setVisibility(View.VISIBLE);

        switch (tipo)
        {
            default:
                final ArrayList<VProductos> items = new ArrayList<>();

                String urling = core.BASE_URL + "productos/tipos_ws.php?lol="+tipo;
                RequestQueue queue4 = Volley.newRequestQueue(getApplicationContext());
                urling = urling.replaceAll(" ", "%20");
                StringRequest stringRequest4 = new StringRequest(Request.Method.GET,urling,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Success","Todo Belen "+response);
                        if(response.length()>0){
                            try {
                                JSONArray ja = new JSONArray(response);
                                Log.i("sizejson",""+ja.length());
                                DecimalFormat df = new DecimalFormat("0.00");

                                for (int i = 0;i<ja.length();i++){
                                    JSONObject json_data = ja.getJSONObject(i);
                                    String nombre = json_data.getString("nombre");
                                    String line = json_data.getString("line");
                                    String stock = json_data.getString("stock");
                                    float preciox = Float.parseFloat(json_data.getString("precio"));
                                    String precio = "$"+df.format(preciox);

                                    items.add(new VProductos(nombre, line,stock,precio));

                                }
                                data.clear();
                                data.addAll(items);
                                adapter.notifyDataSetChanged();

                                pb.setVisibility(View.INVISIBLE);
                                vp.setVisibility(View.INVISIBLE);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"¡No hay Articulos!",Toast.LENGTH_LONG).show();
                                pb.setVisibility(View.INVISIBLE);
                                vp.setVisibility(View.INVISIBLE);
                            }


                        }else{
                            Log.i("sizejson","");
                        }
                        if(response == "null")
                        {
                            Toast.makeText(getApplicationContext(),"¡No hay Articulos!",Toast.LENGTH_LONG).show();
                            pb.setVisibility(View.INVISIBLE);
                            vp.setVisibility(View.INVISIBLE);

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //mTextView.setText("That didn't work!");

                        Log.i("Error","Todo Malon "+error.toString());

                        error.printStackTrace();

                    }
                });
                queue4.add(stringRequest4);

                break;
        }


    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main2, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
}
