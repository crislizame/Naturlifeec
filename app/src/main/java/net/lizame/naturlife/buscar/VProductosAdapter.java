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

public class VProductosAdapter extends RecyclerView.Adapter<VProductosAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<VProductos> VProductos;
    private List<VProductos> contactListFiltered;

    public VProductosAdapter(Context context,List<VProductos> VProductos) {
        this.context = context;
        this.VProductos = VProductos;
        this.contactListFiltered = VProductos;

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = VProductos;
                } else {
                    List<VProductos> filteredList = new ArrayList<>();
                    for (VProductos row : VProductos) {

                        if (row.getNombre().toLowerCase().contains(charString.toLowerCase()) || row.getLine().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<VProductos>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // en este ejemplo cada elemento consta solo de un título
        public TextView nombre;
        public TextView line;
        public TextView stock;
        public TextView precio;
        public ViewHolder(View view) {

            super(view);

            nombre = view.findViewById(R.id.nombre);
            line = view.findViewById(R.id.line);
            stock = view.findViewById(R.id.stock);
            precio = view.findViewById(R.id.precio);
        }
    }
    @Override
    public VProductosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creamos una nueva vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vproductos, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings
        // ...

        VProductosAdapter.ViewHolder vh = new VProductosAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(VProductosAdapter.ViewHolder holder, int position) {
        final VProductos contact = contactListFiltered.get(position);

        holder.nombre.setText(contact.getNombre());
        holder.line.setText(contact.getLine());
        holder.stock.setText(contact.getStock());
        holder.precio.setText(contact.getPrecio());

    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();

    }
}
