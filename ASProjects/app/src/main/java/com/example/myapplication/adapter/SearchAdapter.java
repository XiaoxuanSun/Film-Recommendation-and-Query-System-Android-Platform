package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.modal.ListItem;
import com.example.myapplication.module.GlideApp;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    // Context object used to inflate list_item layout
    private List<ListItem> listItems;
    private Context context;
    // Generated constructor from members
    public SearchAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);
        holder.resultTypeTextV.setText(listItem.getResultTypeTextV());
        holder.resultRateTextV.setText(listItem.getResultRateTextV());
        holder.resultNameTextV.setText(listItem.getResultNameTextV());

        GlideApp.with(holder.itemView)
                .load(listItem.getResultImageViewUrl())
                .into(holder.resultImageView)
        ;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                myIntent.putExtra("DetailActivityId",listItem.getId());
                myIntent.putExtra("MediaType",listItem.getType());
                myIntent.putExtra("name", listItem.getResultNameTextV());
                myIntent.putExtra("path", listItem.getPath());
                holder.itemView.getContext().startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView resultTypeTextV;
        public TextView resultRateTextV;
        public TextView resultNameTextV;
        public ImageView resultImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resultTypeTextV = (TextView) itemView.findViewById(R.id.resultTypeTextV);
            resultRateTextV = (TextView) itemView.findViewById(R.id.resultRateTextV);
            resultNameTextV = (TextView) itemView.findViewById(R.id.resultNameTextV);
            resultImageView = (ImageView) itemView.findViewById(R.id.resultImageView);
        }
    }
}
