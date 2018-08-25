package net.lizame.naturlife.buscar;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import net.lizame.naturlife.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 16/11/17.
 */

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<Productos> contactList;
    private List<Productos> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, phone,stock,sel;
        public ImageView thumbnail;
        public ImageButton btnadd,btndel,btn_12;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_nombre);
            phone = view.findViewById(R.id.tv_artista);
            thumbnail = view.findViewById(R.id.img_musica);
            stock = view.findViewById(R.id.et_stock2);
            btnadd = view.findViewById(R.id.btn_add);
            btndel = view.findViewById(R.id.btn_delete);
            sel = view.findViewById(R.id.et_stock);
            btn_12 = view.findViewById(R.id.btn_12);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));

                }
            });
        }
        void setOnClickListener(){
            btnadd.setOnClickListener(this);
            btndel.setOnClickListener(this);
            btn_12.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
           Productos productitos = contactListFiltered.get(getAdapterPosition());
            switch (view.getId()){
                case R.id.btn_add:
                    Log.i("nombre0 ",""+productitos.getTitle());

                    Integer Suma = Integer.parseInt(sel.getText().toString());
                    Integer stock2 = Integer.parseInt(stock.getText().toString());
                    Suma = Suma + 1;
                    if(Suma >= 0 & Suma<=stock2){
                        sel.setText(String.valueOf(Suma));
                        productitos.setMsel(sel.getText().toString());

                    }else{

                    }


                    break;
                case R.id.btn_12:
                    Integer Suma2 = Integer.parseInt(sel.getText().toString());
                    Integer stock22 = Integer.parseInt(stock.getText().toString());
                    Suma2 = Suma2 + 12;
                    if(Suma2 >= 0 & Suma2<=stock22){

                        sel.setText(String.valueOf(Suma2));

                    productitos.setMsel(sel.getText().toString());
                    }else{

                    }

                    break;
                case R.id.btn_delete:
                    Integer Resta = Integer.parseInt(sel.getText().toString());

                    if(Resta <= 0){

                    }else{
                        Resta = Resta - 1;
                        sel.setText(String.valueOf(Resta));
                        productitos.setMsel(sel.getText().toString());

                    }

                    break;
            }
        }
    }


    public PedidoAdapter(Context context, List<Productos> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Productos contact = contactListFiltered.get(position);
        holder.name.setText(contact.getTitle());
        holder.phone.setText(contact.getPrecio());
        holder.stock.setText(contact.getStock());
        holder.sel.setText(contact.getMsel());
        holder.thumbnail.setImageBitmap(contact.getDrawableResource());
        holder.setOnClickListener();


    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<Productos> filteredList = new ArrayList<>();
                    for (Productos row : contactList) {

                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getPrecio().contains(charSequence)) {
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
                contactListFiltered = (ArrayList<Productos>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Productos contact);
    }
}