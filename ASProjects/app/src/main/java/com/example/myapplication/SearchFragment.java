package com.example.myapplication;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.SearchAdapter;
import com.example.myapplication.modal.ListItem;
import com.example.myapplication.vollyHelper.VollyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private View view;
    private final String SearchUrl = "https://xiaox-sun-node-hw9.wl.r.appspot.com/multi_search/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.fragment_search, container, false);
            // return inflater.inflate(R.layout.fragment_search, container, false);


            init();
        }catch (Exception e) {

        }

        return view;
    }

    public void init() {
        SearchView searchView = view.findViewById(R.id.SearchMovie);
        searchView.findViewById(androidx.appcompat.R.id.search_plate).setBackgroundColor(Color.TRANSPARENT);
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        EditText searchBox = ((EditText) searchView.findViewById(R.id.search_src_text));
        searchBox.setTextColor(Color.WHITE);

        ImageView searchButton = (ImageView) searchView.findViewById(R.id.search_button);
        searchButton.setColorFilter(Color.WHITE);

        ImageView searchClose = (ImageView) searchView.findViewById(R.id.search_close_btn);
        searchClose.setColorFilter(Color.WHITE);

        SearchFragment _this = this;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    VollyHelper.ResponseRes(SearchUrl + newText, _this.getContext(), _this::SearchFun);
                }

                //Log.i("",newText);
                return false;
            }
        });


    }

    public void SearchFun(JSONArray Json) {

        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;
        recyclerView = (RecyclerView) view.findViewById(R.id.SearchMovieRes);
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<ListItem> listItems = new ArrayList<>();

        for (int i = 0; i < Json.length(); i++) {
            try {
                String media_type = Json.getJSONObject(i).getString("media_type").toUpperCase();
                media_type +=" (";
                String time = Json.getJSONObject(i).getString("release_date").substring(0, 4);
                media_type += time + ")";


                Float vote_average = Float.parseFloat(Json.getJSONObject(i).getString("vote_average")) / 2;
                BigDecimal b = new BigDecimal(vote_average);
                float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
                listItems.add(new ListItem(
                        media_type,
                        f1 + "",
                        Json.getJSONObject(i).getString("title"),
                        Json.getJSONObject(i).getString("backdrop_path"),
                        Json.getJSONObject(i).getString("id"),
                        Json.getJSONObject(i).getString("media_type"),
                        Json.getJSONObject(i).getString("poster_path")

                ));


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        TextView t = view.findViewById(R.id.SearchResTextv);
        if(listItems.size() < 1){
            if(t.getVisibility() == View.GONE){
                t.setVisibility(View.VISIBLE);
            }

            if(recyclerView.getVisibility() == View.VISIBLE){
                recyclerView.setVisibility(View.GONE);
            }



        }else{
            if(t.getVisibility() == View.VISIBLE){
                t.setVisibility(View.GONE);
            }
            if(recyclerView.getVisibility() == View.GONE){
                recyclerView.setVisibility(View.VISIBLE);
            }
            adapter = new SearchAdapter(listItems, getActivity());
            recyclerView.setAdapter(adapter);
        }



    }
}
