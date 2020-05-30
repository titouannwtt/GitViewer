package com.esiea.githubv.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.esiea.githubv.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListRepoAdapter extends RecyclerView.Adapter<ListRepoAdapter.ViewHolder> {
    private List<Repo> values;
    private Context context;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtHeader;
        TextView txtFooter;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    public void add(int position, Repo item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListRepoAdapter(List<Repo> myDataset) {
        values = myDataset;
    }

    @Override
    public ListRepoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Repo name = values.get(position);
        holder.txtHeader.setText(name.getName());
        holder.txtFooter.setText(""+name.getDescription());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

}
