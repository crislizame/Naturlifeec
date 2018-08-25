package net.lizame.naturlife;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.lizame.naturlife.buscar.Item;

import java.util.List;
import java.util.Objects;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> mItems;
    private ItemListener mListener;

    public ItemAdapter(List<Item> items, ItemListener listener) {
        mItems = items;
        mListener = listener;
    }

    public void setListener(ItemListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
        public Item item;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            textView2 = (TextView) itemView.findViewById(R.id.textView4);
        }

        public void setData(Item item) {
            this.item = item;
            imageView.setImageResource(item.getDrawableResource());
            textView.setText(titleize(item.getTitle().toLowerCase()));
            textView2.setText(item.getRuc());
        }
        public String titleize(final String input) {
            Objects.requireNonNull(input, "The input parameter may not be null.");

            final StringBuilder output = new StringBuilder(input.length());
            boolean lastCharacterWasWhitespace = true;

            for (final char currentCharacter : input.toCharArray()) {
                if (lastCharacterWasWhitespace) {
                    output.append(Character.toTitleCase(currentCharacter));
                } else {
                    output.append(currentCharacter);
                }
                lastCharacterWasWhitespace = Character.isWhitespace(currentCharacter);
            }
            return output.toString();
        }
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    public interface ItemListener {
        void onItemClick(Item item);
    }
}