package com.example.pooya.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pooya.popularmovies.R;

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.TrailerViewHolder> {
    private static final String TAG = FragmentAdapter.class.getSimpleName();

    private final ForecastAdapterOnClickHandler forecastAdapterOnClickHandler;
    int clickedPosition;

    public interface ForecastAdapterOnClickHandler {
        void onClick(CategorizedMovie CategorizedMovie, int ide);
    }

    Context context;
    private static String ur = "http://image.tmdb.org/t/p/w780/";
    static CategorizedMovie[] tabArray;
    TextView textView;

    public FragmentAdapter(FragmentAdapter.ForecastAdapterOnClickHandler clickHandler) {
        forecastAdapterOnClickHandler = clickHandler;
    }

    public void setData(CategorizedMovie[] tabCategorizer) {
        tabArray = tabCategorizer;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.fragment_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutIdForListItem, parent, false);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (tabArray != null) return tabArray.length;
        else return 0;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TrailerViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.trailer_list_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickedPosition = getAdapterPosition();
            forecastAdapterOnClickHandler.onClick(tabArray[clickedPosition], clickedPosition);
        }
    }
}
