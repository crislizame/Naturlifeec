package net.lizame.naturlife.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.lizame.naturlife.MainActivity;
import net.lizame.naturlife.R;
import net.lizame.naturlife.activity.VProductosCActivity;
import net.lizame.naturlife.buscar.Productos;
import net.lizame.naturlife.core.Session;
import net.lizame.naturlife.core.core;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnviarActivity extends AppCompatActivity implements View.OnClickListener {
    TextView subtotal,iva,total;
    Button enviar;
    float Suma = 0;
    Session session;
    ArrayList<String> all;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getApplicationContext());
        setContentView(R.layout.activity_enviar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        all = (ArrayList<String>) getIntent().getExtras().getSerializable("array");

        for (int i = 0;i < Integer.parseInt(String.valueOf(all.size())) ;){
            float Mult = 0;
            String aa = all.get(i);
            String aa2 = all.get(i+1);
            String aa3 = all.get(i+2);
            String aa4 = all.get(i+3);
            Mult = Float.parseFloat(aa2)*Float.parseFloat(aa4);
            Suma = Suma + Mult;


            i=i+4;
        }


        subtotal = findViewById(R.id.tv_subtotal);
        iva = findViewById(R.id.tv_iva);
        total = findViewById(R.id.tv_total);
        enviar = findViewById(R.id.btn_enviar);
        enviar.setOnClickListener(this);
        DecimalFormat df = new DecimalFormat("0.00");

        subtotal.setText(String.valueOf(df.format(Suma)));
        total.setText(String.valueOf(df.format(Suma)));

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_enviar){
            final String idcli = session.getclicodigo();
            String urling = core.BASE_URL + "pedidos/npedido_ws.php?lol=listo";
            RequestQueue queue4 = Volley.newRequestQueue(getApplicationContext());
            urling = urling.replaceAll(" ", "%20");
            final ArrayList<Productos> items = new ArrayList<>();
            StringRequest stringRequest4 = new StringRequest(Request.Method.POST,urling,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EnviarActivity.this, MainActivity.class);
                    intent.putExtra("tipo", "npedido");
                    startActivity(intent);
                    EnviarActivity.this.finish();

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
                    params.put("idcli", idcli);
                    params.put("subtotal", subtotal.getText().toString());
                    params.put("total", total.getText().toString());
                    params.put("usrcodigo",session.getusrcodigo());
                    int a = 0;
                    for (int i = 0;i < Integer.parseInt(String.valueOf(all.size())) ;){
                        float Mult = 0;
                        String aa = all.get(i);
                        String aa2 = all.get(i+1);
                        String aa3 = all.get(i+2);
                        String aa4 = all.get(i+3);
                        params.put("tituloimg"+a, aa);
                        params.put("precio"+a, aa2);
                        params.put("stock"+a, aa3);
                        params.put("msel"+a, aa4);

                        a=a+1;

                        i=i+4;
                    }


                    return params;
                }

            /*stringRequest4.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
            };
            queue4.add(stringRequest4);        }
    }
}
