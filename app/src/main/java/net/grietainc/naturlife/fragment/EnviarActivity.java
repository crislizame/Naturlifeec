package net.grietainc.naturlife.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.grietainc.naturlife.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EnviarActivity extends AppCompatActivity implements View.OnClickListener {
    TextView subtotal,iva,total;
    Button enviar;
    float Suma = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayList<String> all = (ArrayList<String>) getIntent().getExtras().getSerializable("array");;
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
            Toast.makeText(this,"Datos no enviados falta WebService",Toast.LENGTH_LONG).show();
        }
    }
}
