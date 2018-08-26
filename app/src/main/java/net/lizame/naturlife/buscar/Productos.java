package net.lizame.naturlife.buscar;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.lizame.naturlife.core.core;
import net.lizame.naturlife.fragment.EnviarActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Productos {


    private Context mDrawableRes;
    private String valorconvertido;
    private String mTitle;
    private String mPrecio;
    private String mStock;
    private String mcodigo;
    private String msel;

    public Productos(Context drawable, String title, String precio, String stock, String codigo, String sel) {
        mDrawableRes = drawable;
        mTitle = title;
        mPrecio = precio;
        mStock = stock;
        mcodigo = codigo;
        msel = sel;
    }

    public String getDrawableResource() {

        valorconvertido = "";


        return valorconvertido;
    }

    public String getTitle() {
        return capitalize(mTitle);
    }

    public String getPrecio() {
        return mPrecio;
    }
    public String getcodigo() {
        return  mcodigo;
    }

    public String getStock() {
        return mStock;
    }

    public String getMsel() {
        return msel;
    }
    public void setMsel(String sel) {
        msel = sel;
    }
    public static String capitalize(String input) {
        if (input == null || input.length() <= 0) {
            return input;
        }
        char[] chars = new char[1];
        input.getChars(0, 1, chars, 0);
        if (Character.isUpperCase(chars[0])) {
            return input;
        } else {
            StringBuilder buffer = new StringBuilder(input.length());
            buffer.append(Character.toUpperCase(chars[0]));
            buffer.append(input.toCharArray(), 1, input.length()-1);
            return buffer.toString();
        }
    }
}
