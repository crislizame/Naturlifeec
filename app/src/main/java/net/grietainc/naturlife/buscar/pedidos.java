package net.grietainc.naturlife.buscar;

/**
 * Created by CRISLIZAME on 3/8/2018.
 */

public class pedidos {
    private String mTitulo;
    private String mCant;
    private String mPrecio;

    public pedidos(String mTitulo, String mCant, String mPrecio) {
        this.mTitulo = mTitulo;
        this.mCant = mCant;
        this.mPrecio = mPrecio;
    }

    public String getmTitulo() {
        return mTitulo;
    }

    public void setmTitulo(String mTitulo) {
        this.mTitulo = mTitulo;
    }

    public String getmCant() {
        return mCant;
    }

    public void setmCant(String mCant) {
        this.mCant = mCant;
    }

    public String getmPrecio() {
        return mPrecio;
    }

    public void setmPrecio(String mPrecio) {
        this.mPrecio = mPrecio;
    }
}
