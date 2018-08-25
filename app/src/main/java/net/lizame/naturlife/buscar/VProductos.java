package net.lizame.naturlife.buscar;


public class VProductos {

    private String nombre;
    private String line;
    private String stock;
    private String precio;

    public VProductos(String nombre, String line, String stock, String precio) {
        this.nombre = nombre;
        this.line = line;
        this.stock = stock;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
