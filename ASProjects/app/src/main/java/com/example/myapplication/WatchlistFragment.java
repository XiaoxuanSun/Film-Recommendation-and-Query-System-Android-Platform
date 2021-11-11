package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.WatchlistModel;
import com.example.myapplication.Model.WathListModeServer;
import com.example.myapplication.adapter.SearchAdapter;
import com.example.myapplication.adapter.WatchListAdapter;
import com.example.myapplication.modal.ListItem;
import com.example.myapplication.modal.WatchListItem;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WatchlistFragment extends Fragment {


    private View view;
    List<WatchListItem> listItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.fragment_watchlist, container, false);
            listItems = new ArrayList<>();

        } catch (Exception e) {

        }
        return view;
    }

    public void Init() {
        //  WathListModeServer.ClearData(getActivity());
        List<WatchlistModel> watchlistModelList = WathListModeServer.GetWatchlist(getActivity());
        for (WatchlistModel item : watchlistModelList) {
            listItems.add(new WatchListItem(item.getId(), item.getName(), item.getType(), item.getPath()));
        }
        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;
        recyclerView = (RecyclerView) view.findViewById(R.id.SearchWatchListRes);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        boolean flag1 = false;
        if (recyclerView.getAdapter() == null) {
            flag1 = true;
        }

        adapter = new WatchListAdapter(listItems, getActivity());
        recyclerView.setAdapter(adapter);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);

        if (flag1) {
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }


        if (watchlistModelList.size() < 1) {
            TextView t = view.findViewById(R.id.WatchlistTextV);
            t.setVisibility(View.VISIBLE);
            return;
        }


    }

    //ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP|ItemTouchHelper.DOWN
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, 0) {

        /**
         * 设置RecyclerView支持拖动和滑动的方向
         */
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

            //支持上下拖动
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

            //支持左右滑动
            int swipeFlag = 0;

            int flags = makeMovementFlags(dragFlags, swipeFlag);
            return flags;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {

                    Collections.swap(listItems, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {

                    Collections.swap(listItems, i, i - 1);
                }
            }

            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);


            WathListModeServer.ResetData(listItems, getActivity());


            return true;
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);

            WatchListAdapter adapter = new WatchListAdapter(listItems, getActivity());
            recyclerView.setAdapter(adapter);

        }


        //        @Override
//        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
//                //不是空闲状态
//                viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
//            }
//
//            super.onSelectedChanged(viewHolder, actionState);
//        }
//        @Override
//        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//            viewHolder.itemView.setBackgroundColor(Color.WHITE);
//            super.clearView(recyclerView, viewHolder);
//        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        //
        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }
    };

    static boolean flag = false;

    @Override
    public void onResume() {

//        Init();

//        onCreate(null);
//        if(flag){
//
//            Intent intent = new Intent(getActivity(),WatchlistFragment.class);
//            startActivity(intent);
////            flag = false;
//        }

        listItems = new ArrayList<>();
        Init();

        super.onResume();


    }
}
