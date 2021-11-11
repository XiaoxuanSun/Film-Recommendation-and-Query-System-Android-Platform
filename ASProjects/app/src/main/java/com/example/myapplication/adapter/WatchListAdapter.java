package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DetailActivity;
import com.example.myapplication.Model.WathListModeServer;
import com.example.myapplication.R;
import com.example.myapplication.WatchlistFragment;
import com.example.myapplication.modal.ListItem;
import com.example.myapplication.modal.WatchListItem;
import com.example.myapplication.module.GlideApp;

import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.ViewHolder>{

    // Context object used to inflate list_item layout
    private List<WatchListItem> listItems;
    private Context context;
    private ViewGroup parent;
    private String watchlistTypeText;
    // Generated constructor from members
    public WatchListAdapter(List<WatchListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public WatchListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.watchlist_item, parent, false);
        return new WatchListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchListAdapter.ViewHolder holder, int position) {
        WatchListItem listItem = listItems.get(position);
        if(listItem.getType().equals("movie")) {
            watchlistTypeText = "Movie";
        } else if(listItem.getType().equals("tv")) {
            watchlistTypeText = "TV";
        }
        holder.WatchListType.setText(watchlistTypeText);

        GlideApp.with(holder.itemView)
                .load(listItem.getPath())
                .into(holder.imageWatchList)
        ;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                myIntent.putExtra("DetailActivityId",listItem.getId());
                myIntent.putExtra("MediaType",listItem.getType());
                myIntent.putExtra("name", listItem.getName());
                myIntent.putExtra("path", listItem.getPath());
                holder.itemView.getContext().startActivity(myIntent);
            }
        });

        holder.imageButtonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WathListModeServer.Remove(listItem.getId(), (FragmentActivity) holder.imageButtonItem.getContext());
                listItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeRemoved(position,listItems.size());

                if(WathListModeServer.GetWatchlist((FragmentActivity) holder.imageButtonItem.getContext()).size()<1){
                    View Lparent = (View) parent.getParent();
                    TextView textView = Lparent.findViewById(R.id.WatchlistTextV);
                    textView.setVisibility(View.VISIBLE);

                }

                Toast.makeText((FragmentActivity) holder.imageButtonItem.getContext(), "\"" + listItem.getName() + "\""+" was removed from watchlist", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView WatchListType;
        public ImageView imageWatchList;
        public ImageButton imageButtonItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            WatchListType = (TextView) itemView.findViewById(R.id.WatchListType);
            imageWatchList = (ImageView) itemView.findViewById(R.id.imageWatchList);
            imageButtonItem = (ImageButton) itemView.findViewById(R.id.imageButtonItem);
        }
    }
}

