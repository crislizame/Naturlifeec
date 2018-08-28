package net.lizame.naturlife.buscar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import net.lizame.naturlife.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CRISLIZAME on 24/8/2018.
 */

public class MirarPed extends RecyclerView.Adapter<MirarPed.ViewHolder>  {
    private Context context;
    private List<VProductos> VProductos;
    private List<VProductos> contactListFiltered;

    public MirarPed(Context context,List<VProductos> VProductos) {
        this.context = context;
        this.VProductos = VProductos;
        this.contactListFiltered = VProductos;

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // en este ejemplo cada elemento consta solo de un título
        public TextView nombre;
        public TextView stock;
        public TextView precio;
        public ViewHolder(View view) {

            super(view);

            nombre = view.findViewById(R.id.nombre);
            stock = view.findViewById(R.id.stock);
            precio = view.findViewById(R.id.precio);
        }
    }
    @Override
    public MirarPed.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creamos una nueva vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mpedidos, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings
        // ...

        MirarPed.ViewHolder vh = new MirarPed.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MirarPed.ViewHolder holder, int position) {
        final VProductos contact = VProductos.get(position);

        holder.nombre.setText(contact.getNombre());
        holder.stock.setText(contact.getStock());
        holder.precio.setText(contact.getPrecio());

    }

    @Override
    public int getItemCount() {
        return VProductos.size();

    }
}
