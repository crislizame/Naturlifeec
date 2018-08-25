package net.lizame.naturlife.buscar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.lizame.naturlife.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CRISLIZAME on 24/8/2018.
 */

public class VerPedidosAdapter extends RecyclerView.Adapter<VerPedidosAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<VerPedidos> VProductos;
    private List<VerPedidos> contactListFiltered;

    public VerPedidosAdapter(Context context,List<VerPedidos> VerPedidos) {
        this.context = context;
        this.VProductos = VerPedidos;
        this.contactListFiltered = VerPedidos;

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
                    List<VerPedidos> filteredList = new ArrayList<>();
                    for (VerPedidos row : VProductos) {

                        if (row.getNombre().toLowerCase().contains(charString.toLowerCase()) || row.getFecha().contains(charSequence)) {
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
                contactListFiltered = (ArrayList<VerPedidos>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // en este ejemplo cada elemento consta solo de un título
        public TextView nombre;
        public TextView fecha;
        public TextView pednumped;
        public TextView pedvaltot;
        public LinearLayout status;
        public ViewHolder(View view) {

            super(view);

            nombre = view.findViewById(R.id.nombre);
            fecha = view.findViewById(R.id.fecha);
            pednumped = view.findViewById(R.id.pednumped);
            pedvaltot = view.findViewById(R.id.pedvaltot);
            status = view.findViewById(R.id.status);
        }
    }
    @Override
    public VerPedidosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creamos una nueva vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vpedidos, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings
        // ...

        VerPedidosAdapter.ViewHolder vh = new VerPedidosAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(VerPedidosAdapter.ViewHolder holder, int position) {
        final VerPedidos contact = contactListFiltered.get(position);

        holder.nombre.setText(contact.getNombre());
        holder.fecha.setText(contact.getFecha());
        holder.pednumped.setText(contact.getPednumped());
        holder.pedvaltot.setText(contact.getPedvaltot());

        switch (contact.getEstado())
        {
            case "p":
                holder.status.setBackgroundResource(R.color.yello);
                break;
            case "1":
                holder.status.setBackgroundResource(R.color.green);
                break;
            case "0":
                holder.status.setBackgroundResource(R.color.red);

                break;

        }


    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();

    }
}
