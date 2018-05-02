package com.example.pooya.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pooya.popularmovies.R;

public class FragmentAdapter extends RecyclerView.Adapter<TextItemViewHolder> {
    private static final String TAG = FragmentAdapter.class.getSimpleName();

    String[] items;
    static CategorizedMovie[] movieArray;

    public FragmentAdapter(String[] items) {
        this.items = items;
    }

    @Override
    public TextItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item, parent, false);
        return new TextItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TextItemViewHolder holder, int position) {
        holder.bind(items[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public void setData(CategorizedMovie[] movieCategorizer) {
        movieArray = movieCategorizer;
    }
}
