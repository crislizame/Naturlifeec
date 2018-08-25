package net.lizame.naturlife.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.lizame.naturlife.R;
import net.lizame.naturlife.buscar.ConfirmarAdapter;
import net.lizame.naturlife.buscar.pedidos;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HacerPedActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ConfirmarAdapter mAdapter;
    public List<pedidos> contactList;
    private MenuItem send;
    public ArrayList<String> productores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> all = (ArrayList<String>) getIntent().getExtras().getSerializable("array");;

        setContentView(R.layout.activity_hacer_ped);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        contactList = new ArrayList<>();
        // Asociamos un adapter (ver más adelante cómo definirlo)
        mAdapter = new ConfirmarAdapter(this, contactList);
        recyclerView.setAdapter(mAdapter);
        productores = new ArrayList<>();
        final ArrayList<pedidos> items = new ArrayList<>();
Log.i("asd",""+all.size());
        for (int i = 0;i < Integer.parseInt(String.valueOf(all.size())) ;){
            float Mult = 0;
            String aa = all.get(i);
            String aa2 = all.get(i+1);
            String aa3 = all.get(i+2);
            String aa4 = all.get(i+3);
            productores.add(all.get(i));
            productores.add(all.get(i+1));
            productores.add(all.get(i+2));
            productores.add(all.get(i+3));
            Mult = Float.parseFloat(aa2)*Float.parseFloat(aa4);
            DecimalFormat df = new DecimalFormat("0.00");
            items.add(new pedidos(aa,aa4,String.valueOf(df.format(Mult))));


            i= i+4;
        }

        contactList.clear();
        contactList.addAll(items);

        // refreshing recycler view
        mAdapter.notifyDataSetChanged();
        Log.i("lasda",""+all);

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

            Intent intent = new Intent(HacerPedActivity.this, EnviarActivity.class);
            intent.putExtra("array", productores);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }
}
