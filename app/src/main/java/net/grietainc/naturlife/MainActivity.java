package net.grietainc.naturlife;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.grietainc.naturlife.core.Session;
import net.grietainc.naturlife.fragment.AddClienteFragment;
import net.grietainc.naturlife.fragment.BuscarClienteFragment;
import net.grietainc.naturlife.fragment.InicioFragment;
import net.grietainc.naturlife.fragment.PedidoFragment;
import net.grietainc.naturlife.fragment.ProductosFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, InicioFragment.OnFragmentInteractionListener,
        AddClienteFragment.OnFragmentInteractionListener,
        ProductosFragment.OnFragmentInteractionListener,PedidoFragment.OnFragmentInteractionListener,BuscarClienteFragment.OnFragmentInteractionListener {
    private TextView usrnombre_txt;
    private TextView usrcorreo_txt;
    private TextView usrcargotexto_txt;
    private Context context;
    public Session session;
    public FragmentManager fm;
    public FragmentTransaction ft;
    InicioFragment inicio = new InicioFragment();
    ProductosFragment productos = new ProductosFragment();
    PedidoFragment pedidos = new PedidoFragment();
    AddClienteFragment addcli = new AddClienteFragment();
    BuscarClienteFragment buscar = new BuscarClienteFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session = new Session(getApplicationContext());

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fragment_inicio,inicio);
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //con esto generamos el usuario en el header del menu-------------------------------
        View hView = navigationView.getHeaderView(0);
        usrnombre_txt = hView.findViewById(R.id.usrnombre_txt);
        usrcorreo_txt = hView.findViewById(R.id.usrcorreo_txt);
        usrcargotexto_txt = hView.findViewById(R.id.usrcargo_txt);
        usrnombre_txt.setText(ucFirst(session.getusername()));
        usrcorreo_txt.setText(session.getusrcorreo());
        usrcargotexto_txt.setText(ucFirst(session.getusrcargotexto().toLowerCase()));
    }
    public static String ucFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public AlertDialog createSimpleDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Cerrar Sesión")
                .setMessage("¿Esta Seguro de Cerrar Sesión?")
                .setPositiveButton("Cerrar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                session.limpiarsession();
                                Intent ListSong = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(ListSong);
                                finish();
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        return builder.create();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar Item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.salir_btn) {
createSimpleDialog().show();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view Item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            ft = fm.beginTransaction();
            ft.replace(R.id.fragment_inicio,pedidos);
            ft.addToBackStack(null);
            ft.commit();
        }else if (id == R.id.add_cli) {
            ft = fm.beginTransaction();
            ft.replace(R.id.fragment_inicio,addcli);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.bus_cli) {
            ft = fm.beginTransaction();
            ft.replace(R.id.fragment_inicio,buscar);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            ft = fm.beginTransaction();
            ft.replace(R.id.fragment_inicio,productos);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
    }
}
