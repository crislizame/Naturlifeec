package net.lizame.naturlife;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.lizame.naturlife.buscar.PedidoAdapter;
import net.lizame.naturlife.buscar.Productos;
import net.lizame.naturlife.core.Session;
import net.lizame.naturlife.core.core;
import net.lizame.naturlife.fragment.EnviarActivity;
import net.lizame.naturlife.fragment.HacerPedActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoproActivity extends AppCompatActivity implements  PedidoAdapter.ContactsAdapterListener, View.OnClickListener {
    private static final String TAG = PedidoproActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private GridLayoutManager glm;
    public List<Productos> contactList;
    private PedidoAdapter mAdapter;
    private SearchView searchView;
    private MenuItem send;
    public ArrayList<String> productores;
    private ProgressBar pb;
    private ViewPager vp;

    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidopro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);
        pb = findViewById(R.id.pb_nuevopro);
        vp = findViewById(R.id.vp_pb);
        recyclerView = findViewById(R.id.recycler_view);
        contactList = new ArrayList<>();
        mAdapter = new PedidoAdapter(this, contactList, this);


        // white background notification bar
        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
       // glm = new GridLayoutManager(this, 2);
        //recyclerView.setLayoutManager(glm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        pb.setVisibility(View.VISIBLE);
        vp.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                fetchContacts();
            }
        }).start();

    }
    private void fetchContacts() {
        String urling = core.BASE_URL + "productos/pedidos3_ws.php?lol=listo";
        RequestQueue queue4 = Volley.newRequestQueue(getApplicationContext());
        urling = urling.replaceAll(" ", "%20");
        final ArrayList<Productos> items = new ArrayList<>();
                StringRequest stringRequest4 = new StringRequest(Request.Method.POST,urling,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                // mTextView.setText("Response is: "+ response.substring(0,500));
                Log.i("Success","Todo Belen "+response);
                if(response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                      /*  Log.i("sizejson2",""+json_data.getString("clinombre"));
                        Log.i("sizejson3",""+json_data2.getString("clinombre"));*/


                        for (int i = 0;i<ja.length();i++){
                            JSONObject json_data = ja.getJSONObject(i);
                            String titulo = json_data.getString("titulo");
                            String precio = json_data.getString("precio");
                            String stock = json_data.getString("stock");
                            String codigo = json_data.getString("code");

                                items.add(new Productos(getApplicationContext(), titulo,precio,stock,codigo,"0"));

                        }
                        pb.setVisibility(View.INVISIBLE);
                        vp.setVisibility(View.INVISIBLE);
                        // adding contacts to contacts list
                        contactList.clear();
                        contactList.addAll(items);
                        send.setVisible(true);

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else{
                    Log.i("sizejson","");
                }
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
                params.put("cliruc", bc_string);



                return params;
            }

            /*stringRequest4.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
        };
        queue4.add(stringRequest4);




    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        send = menu.findItem(R.id.action_send);
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
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        if (id == R.id.action_send) {
            Log.i("lol","lalallaal");
            Integer suma = 0;
            productores = new ArrayList<>();
            for (int i = 0; i < mAdapter.getItemCount(); i++){
                Productos productito = contactList.get(i);
                if(Integer.parseInt(productito.getMsel())>0){
                    productores.add(productito.getTitle());
                    productores.add(productito.getPrecio());
                    productores.add(productito.getcodigo());
                    productores.add(productito.getMsel());
                }
                suma = Integer.parseInt(productito.getMsel())+suma;

            }
            if(suma >0){
                Log.i("lol","esto es lo que es "+suma);

                Intent intent = new Intent(PedidoproActivity.this, HacerPedActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                intent.putExtra("array", productores);

                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this,"No has seleccionado Productos",Toast.LENGTH_LONG).show();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("tipo", "hpedido");
            startActivity(intent);
        }
        super.onBackPressed();
    }
    @Override
    public void onContactSelected(Productos contact) {

        //Toast.makeText(getApplicationContext(), "Selected: " + contact.getTitle() + ", " + contact.getPrecio(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {

    }
}
