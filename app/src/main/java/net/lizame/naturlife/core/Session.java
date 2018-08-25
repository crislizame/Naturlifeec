package net.lizame.naturlife.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by CRISLIZAME on 22/2/2018.
 */

public class Session {
    private SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;
    private static final String PREFER_NAME = "Session";
    int PRIVATE_MODE = 0;
    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
        prefs = cntx.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }
    public void limpiarsession(){
        editor.clear().commit();
    }
    public void setusername(String username) {
        editor.putString("username", username).commit();
        // prefs.commit();
    }

    public String getusername() {
        String username = prefs.getString("username","null");
        return username;
    }
    public void setusrcodigo(String usrcodigo) {
        editor.putString("usrcodigo", usrcodigo).commit();
        // prefs.commit();
    }

    public String getusrcodigo() {
        String usrcodigo = prefs.getString("usrcodigo","");
        return usrcodigo;
    }
    public void setusrcorreo(String usrcorreo) {
        editor.putString("usrcorreo", usrcorreo).commit();
        // prefs.commit();
    }

    public String getusrcorreo() {
        String usrcorreo = prefs.getString("usrcorreo","");
        return usrcorreo;
    }
    public void setusrcargotexto(String usrcargotexto) {
        editor.putString("usrcargotexto", usrcargotexto).commit();
        // prefs.commit();
    }

    public String getusrcargotexto() {
        String usrcargotexto = prefs.getString("usrcargotexto","");
        return usrcargotexto;
    }
    public void setclicodigo(String clicodigo) {
        editor.putString("clicodigo", clicodigo).commit();
        // prefs.commit();
    }

    public String getclicodigo() {
        String clicodigo = prefs.getString("clicodigo","");
        return clicodigo;
    }
}
