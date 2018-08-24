package net.jhonlizame.naturlife.buscar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.jhonlizame.naturlife.R;

import java.util.List;

/**
 * Created by CRISLIZAME on 3/8/2018.
 */

public class ConfirmarAdapter  extends RecyclerView.Adapter<ConfirmarAdapter.ViewHolder>{
    private Context contextx;
    private List<pedidos> pedidos;
    // Obtener referencias de los componentes visuales para cada elemento
    // Es decir, referencias de los EditText, TextViews, Buttons
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // en este ejemplo cada elemento consta solo de un título
        public TextView cant;
        public TextView namee;
        public TextView precio;
        public ViewHolder(View view) {
            super(view);
            cant = view.findViewById(R.id.tv_cant);
            namee = view.findViewById(R.id.tv_name);
            precio = view.findViewById(R.id.tv_precio);
        }
    }

    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
    public ConfirmarAdapter(Context context,List<pedidos> pedidos) {
        this.contextx = context;
        this.pedidos = pedidos;
    }

    // El layout manager invoca este método
    // para renderizar cada elemento del RecyclerView
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // Creamos una nueva vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listpro, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings
        // ...

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Este método reemplaza el contenido de cada view,
    // para cada elemento de la lista (nótese el argumento position)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - obtenemos un elemento del dataset según su posición
        // - reemplazamos el contenido de los views según tales datos
        final pedidos contact = pedidos.get(position);

        holder.cant.setText(contact.getmCant());
        holder.namee.setText(contact.getmTitulo());
        holder.precio.setText(contact.getmPrecio());
    }

    @Override
    public int getItemCount() {

        return pedidos.size();
    }


}
