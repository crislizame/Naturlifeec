package net.grietainc.naturlife.buscar;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Productos {


        private String mDrawableRes;
        private Bitmap valorconvertido;
        private String mTitle;
        private String mPrecio;
        private String mStock;
        private String mcodigo;
        private String msel;

        public Productos(String drawable, String title,String precio,String stock,String codigo,String sel) {
            mDrawableRes = drawable;
            mTitle = title;
            mPrecio = precio;
            mStock = stock;
            mcodigo = codigo;
            msel = sel;
        }

        public Bitmap getDrawableResource() {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] imageBytes = baos.toByteArray();
            imageBytes = Base64.decode(mDrawableRes, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            valorconvertido = decodedImage;
            return valorconvertido;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getPrecio() {
            return mPrecio;
        }
    public String getcodigo() {
        return mcodigo;
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
}
