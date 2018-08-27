package net.lizame.naturlife.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import net.lizame.naturlife.MainActivity;
import net.lizame.naturlife.R;

@SuppressLint("ValidFragment")
public class DetectNet extends DialogFragment {
    Context context;
    Activity ac;
    private static ConnectivityManager manager;

    public DetectNet(Context context, Activity ac) {
        this.context = context;
        this.ac = ac;
    }

    public Boolean isNetDisponible() {

        Boolean result;
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(this.context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        if(actNetInfo != null && actNetInfo.isConnected())
        {
            result = true;

        }else{
            result = false;
            Toast.makeText(this.context,"Debes estas conectado a la red",Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.ac.stopLockTask();
            }
        }
        return result;
    }
    public Boolean isOnlineNet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(!(networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.ac);
            builder.setMessage("Nesecitas INTERNET para usar Naturlife")
                    .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DetectNet.this.ac.finish();
                        }
                    });
            builder.create();
            builder.show();
        }


        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();


        }
}