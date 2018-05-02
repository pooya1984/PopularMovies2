package com.example.pooya.popularmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pooya.popularmovies.R;


public class TextItemViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public TextItemViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.trailer_list_item);
    }

    public void bind(String text) {
        textView.setText(text);
    }
}
