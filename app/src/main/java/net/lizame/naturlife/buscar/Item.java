package net.lizame.naturlife.buscar;

/**
 * Created by CRISLIZAME on 29/7/2018.
 */

public class Item {

    private int mDrawableRes;

    private String mTitle;
    private String mRuc;
    private String mcodigo;

    public Item(int drawable, String title,String ruc,String codigo) {
        mDrawableRes = drawable;
        mTitle = title;
        mRuc = ruc;
        mcodigo = codigo;
    }

    public int getDrawableResource() {
        return mDrawableRes;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getRuc() {
        return mRuc;
    }
    public String getCodigo() {
        return mcodigo;
    }

}