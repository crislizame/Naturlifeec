package net.lizame.naturlife.buscar;

/**
 * Created by CRISLIZAME on 25/8/2018.
 */

public class VerPedidos {
    private String nombre;
    private String pednumped;
    private String pedvaltot;
    private String fecha;
    private String estado;

    public VerPedidos(String nombre, String pednumped, String pedvaltot, String fecha, String estado) {
        this.nombre = nombre;
        this.pednumped = pednumped;
        this.pedvaltot = pedvaltot;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPednumped() {
        return pednumped;
    }

    public void setPednumped(String pednumped) {
        this.pednumped = pednumped;
    }

    public String getPedvaltot() {
        return pedvaltot;
    }

    public void setPedvaltot(String pedvaltot) {
        this.pedvaltot = pedvaltot;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
