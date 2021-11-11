package com.example.myapplication;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.Model.WatchlistModel;
import com.example.myapplication.Model.WathListModeServer;
import com.example.myapplication.adapter.SliderAdapter;
import com.example.myapplication.modal.SliderData;
import com.example.myapplication.module.GlideApp;
import com.example.myapplication.vollyHelper.VollyHelper;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private Button button;
    private TextView textView;
    private LinearLayout mTVSliderLinear;

    private RequestQueue queue;
    private StringRequest stringRequest;
    private String url = "https://xiaox-sun-node-hw9.wl.r.appspot.com/";


    //    SliderLayout sliderLayout;
    private List<SliderData> sliderData;

    private ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

    private JsonArrayRequest request;
    private RequestQueue requestQueue;

    private static final String MovieUrl = "https://xiaox-sun-node-hw9.wl.r.appspot.com/current_playing";
    private static final String MovieUrl1 = "https://xiaox-sun-node-hw9.wl.r.appspot.com/popular_movie";
    private static final String MovieUrl2 = "https://xiaox-sun-node-hw9.wl.r.appspot.com/top_movie";

    private static final String TVUrl = "https://xiaox-sun-node-hw9.wl.r.appspot.com/trending_tv";
    private static final String TVUrl1 = "https://xiaox-sun-node-hw9.wl.r.appspot.com/popular_tv";
    private static final String TVUrl2 = "https://xiaox-sun-node-hw9.wl.r.appspot.com/top_tv";

    private ArrayList<List> dataList = new ArrayList<>();
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //R.id.slider
    public boolean setMoivewSliderViews(JSONArray response) {
        sliderDataArrayList = new ArrayList<>();
        JSONObject jsonObject = null;
        for (int i = 0; i < response.length(); i++) {
            try {
                jsonObject = response.getJSONObject(i);
               // Log.d("TAG", jsonObject.getString("poster_path"));
                SliderData sliderData = new SliderData(jsonObject.getString("poster_path"), jsonObject.getString("id"), jsonObject.getString("media_type"), jsonObject.getString("title"));
                sliderDataArrayList.add(sliderData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SliderView sliderView = view.findViewById(R.id.slider);
        SliderAdapter adapter = new SliderAdapter(this.getActivity(), sliderDataArrayList);
        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);
        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);
        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);
        // to start autocycle below method is used.
        sliderView.startAutoCycle();

        return true;
    }

    //R.id.topMovieScroll
    public boolean setMovieItem(JSONArray response) {
        LinearLayout linearLayout = view.findViewById(R.id.topMovieScroll);
        for (int i = 0; i < response.length(); i++) {
            try {
                final JSONObject jsonObject = response.getJSONObject(i);
                //Log.d("TAG", jsonObject.getString("poster_path"));
                View layOut = getLayoutInflater().inflate(R.layout.movie_item, null);

                ImageView imageView = layOut.findViewById(R.id.imageViewItem);
                ImageButton imageBtn = layOut.findViewById(R.id.imageButtonItem);
                URL url = new URL(jsonObject.getString("poster_path"));
                String movieId = jsonObject.getString("id");
                String tempUrl = "https://www.themoviedb.org/" + "movie" + "/" + movieId;

                GlideApp.with(layOut)
                        .load(url)
                        .into(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(imageView.getContext(), DetailActivity.class);
                        try {
                            myIntent.putExtra("DetailActivityId", jsonObject.getString("id"));
                            myIntent.putExtra("MediaType", "movie");
                            myIntent.putExtra("name", jsonObject.getString("title"));
                            myIntent.putExtra("path", jsonObject.getString("poster_path"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        imageView.getContext().startActivity(myIntent);
                    }
                });
                PopupMenu imgBtnPop = new PopupMenu(getActivity(), imageBtn);
                imgBtnPop.getMenuInflater().inflate(R.menu.popup_menu, imgBtnPop.getMenu());

                if(WathListModeServer.IsExist(jsonObject.getString("id"),getActivity())){
                    imgBtnPop.getMenu().findItem(R.id.homeAddWl).setTitle("Remove from Watchlist");
                }

                imageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgBtnPop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                // Toast message on menu item clicked
//                                Toast.makeText(getActivity(), "You Clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();

                                switch (item.getItemId()) {
                                    case R.id.openInTmdb:
                                        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.themoviedb.org/movie/" + movieId));
                                        startActivity(intent);
                                        break;
                                    case R.id.homeShareFb:
                                        Intent intentfb = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://facebook.com/sharer/sharer.php?u=" + tempUrl));
                                        startActivity(intentfb);
                                        break;
                                    case R.id.homeShareTw:
                                        Intent intenttw = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://twitter.com/intent/tweet?text=Check%20this%20out!%20" + tempUrl));
                                        startActivity(intenttw);
                                        break;
                                    case R.id.homeAddWl:
                                        try {
                                        String popItem = item.getTitle().toString();
                                        String DisplayName = "";
                                        if (popItem.equals("Add to Watchlist")) {
                                            DisplayName = "added to";
                                            item.setTitle("Remove from Watchlist");

                                            WathListModeServer.Add(new WatchlistModel(
                                                    jsonObject.getString("id"),
                                                    jsonObject.getString("title"),
                                                    "movie",
                                                    jsonObject.getString("poster_path")), getActivity());
                                        } else {
                                            DisplayName = "removed from";
                                            item.setTitle("Add to Watchlist");
                                            WathListModeServer.Remove(jsonObject.getString("id"), getActivity());
                                        }
                                            Toast.makeText(getActivity(), jsonObject.getString("title") + " was "+DisplayName+" Watchlist", Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                }
                                return true;
                            }
                        });
                        // Showing the popup menu
                        imgBtnPop.show();
                    }
                });
                linearLayout.addView(layOut);
            } catch (JSONException | MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    //R.id.popularMovieScroll
    public boolean setPopMovieItem(JSONArray response) {
        LinearLayout linearLayout = view.findViewById(R.id.popularMovieScroll);
        for (int i = 0; i < response.length(); i++) {
            try {
                final JSONObject jsonObject = response.getJSONObject(i);
                //Log.d("TAG", jsonObject.getString("poster_path"));
                View layOut = getLayoutInflater().inflate(R.layout.movie_item, null);

                String movieId = jsonObject.getString("id");
                ImageView imageView = layOut.findViewById(R.id.imageViewItem);
                ImageButton imageBtn = layOut.findViewById(R.id.imageButtonItem);
                URL url = new URL(jsonObject.getString("poster_path"));
                String tempUrl = "https://www.themoviedb.org/" + "movie" + "/" + movieId;

                GlideApp.with(layOut)
                        .load(url)
                        .into(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(imageView.getContext(), DetailActivity.class);
                        try {
                            myIntent.putExtra("DetailActivityId", jsonObject.getString("id"));
                            myIntent.putExtra("MediaType", "movie");
                            myIntent.putExtra("name", jsonObject.getString("title"));
                            myIntent.putExtra("path", jsonObject.getString("poster_path"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        imageView.getContext().startActivity(myIntent);
                    }
                });
                PopupMenu imgBtnPop = new PopupMenu(getActivity(), imageBtn);
                imgBtnPop.getMenuInflater().inflate(R.menu.popup_menu, imgBtnPop.getMenu());

                if(WathListModeServer.IsExist(jsonObject.getString("id"),getActivity())){
                    imgBtnPop.getMenu().findItem(R.id.homeAddWl).setTitle("Remove from Watchlist");
                }
                imageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgBtnPop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                // Toast message on menu item clicked
//                                Toast.makeText(getActivity(), "You Clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();

                                switch (item.getItemId()) {
                                    case R.id.openInTmdb:
                                        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.themoviedb.org/movie/" + movieId));
                                        startActivity(intent);
                                        break;
                                    case R.id.homeShareFb:
                                        Intent intentfb = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://facebook.com/sharer/sharer.php?u=" + tempUrl));
                                        startActivity(intentfb);
                                        break;
                                    case R.id.homeShareTw:
                                        Intent intenttw = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://twitter.com/intent/tweet?text=Check%20this%20out!%20" + tempUrl));
                                        startActivity(intenttw);
                                        break;
                                    case R.id.homeAddWl:
                                        try {
                                            String popItem = item.getTitle().toString();
                                            String DisplayName = "";
                                            if (popItem.equals("Add to Watchlist")) {
                                                DisplayName = "added to";
                                                item.setTitle("Remove from Watchlist");

                                                WathListModeServer.Add(new WatchlistModel(
                                                        jsonObject.getString("id"),
                                                        jsonObject.getString("title"),
                                                        "movie",
                                                        jsonObject.getString("poster_path")), getActivity());
                                            } else {
                                                DisplayName = "removed from";
                                                item.setTitle("Add to Watchlist");

                                                WathListModeServer.Remove(jsonObject.getString("id"), getActivity());
                                            }
                                            Toast.makeText(getActivity(), jsonObject.getString("title") + " was "+DisplayName+" Watchlist"
                                                    , Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                }
                                return true;
                            }
                        });
                        // Showing the popup menu
                        imgBtnPop.show();
                    }
                });
                linearLayout.addView(layOut);
            } catch (JSONException | MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    //R.id.sliderTv
    public boolean setTVSliderViews(JSONArray response) {
        sliderDataArrayList = new ArrayList<>();
        JSONObject jsonObject = null;
        for (int i = 0; i < response.length(); i++) {
            try {
                jsonObject = response.getJSONObject(i);
               // Log.d("TAG", jsonObject.getString("poster_path"));
                SliderData sliderData = new SliderData(jsonObject.getString("poster_path"), jsonObject.getString("id"), jsonObject.getString("media_type"), jsonObject.getString("title"));
                sliderDataArrayList.add(sliderData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SliderView sliderView = view.findViewById(R.id.sliderTv);
        SliderAdapter adapter = new SliderAdapter(this.getActivity(), sliderDataArrayList);
        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);
        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);
        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);
        // to start autocycle below method is used.
        sliderView.startAutoCycle();
        return true;
    }

    //R.id.topTVScroll
    public boolean setTVItem(JSONArray response) {
        LinearLayout linearLayout = view.findViewById(R.id.topTVScroll);
        for (int i = 0; i < response.length(); i++) {
            try {
                final JSONObject jsonObject = response.getJSONObject(i);
                //Log.d("TAG", jsonObject.getString("poster_path"));
                View layOut = getLayoutInflater().inflate(R.layout.movie_item, null);

                ImageView imageView = layOut.findViewById(R.id.imageViewItem);
                ImageButton imageBtn = layOut.findViewById(R.id.imageButtonItem);
                URL url = new URL(jsonObject.getString("poster_path"));
                String movieId = jsonObject.getString("id");
                String tempUrl = "https://www.themoviedb.org/" + "tv" + "/" + movieId;

                GlideApp.with(layOut)
                        .load(url)
                        .into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(imageView.getContext(), DetailActivity.class);
                        try {
                            myIntent.putExtra("DetailActivityId", jsonObject.getString("id"));
                            myIntent.putExtra("MediaType", "tv");
                            myIntent.putExtra("name", jsonObject.getString("title"));
                            myIntent.putExtra("path", jsonObject.getString("poster_path"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        imageView.getContext().startActivity(myIntent);
                    }
                });
                PopupMenu imgBtnPop = new PopupMenu(getActivity(), imageBtn);
                imgBtnPop.getMenuInflater().inflate(R.menu.popup_menu, imgBtnPop.getMenu());

                if(WathListModeServer.IsExist(jsonObject.getString("id"),getActivity())){
                    imgBtnPop.getMenu().findItem(R.id.homeAddWl).setTitle("Remove from Watchlist");
                }
                imageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgBtnPop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                // Toast message on menu item clicked
//                                Toast.makeText(getActivity(), "You Clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();

                                switch (item.getItemId()) {
                                    case R.id.openInTmdb:
                                        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.themoviedb.org/tv/" + movieId));
                                        startActivity(intent);
                                        break;
                                    case R.id.homeShareFb:
                                        Intent intentfb = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://facebook.com/sharer/sharer.php?u=" + tempUrl));
                                        startActivity(intentfb);
                                        break;
                                    case R.id.homeShareTw:
                                        Intent intenttw = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://twitter.com/intent/tweet?text=Check%20this%20out!%20" + tempUrl));
                                        startActivity(intenttw);
                                        break;
                                    case R.id.homeAddWl:
                                        try {
                                            String popItem = item.getTitle().toString();
                                            String DisplayName = "";
                                            if (popItem.equals("Add to Watchlist")) {
                                                DisplayName = "added to";
                                                item.setTitle("Remove from Watchlist");

                                                WathListModeServer.Add(new WatchlistModel(
                                                        jsonObject.getString("id"),
                                                        jsonObject.getString("title"),
                                                        "tv",
                                                        jsonObject.getString("poster_path")), getActivity());
                                            } else {
                                                DisplayName = "removed from";
                                                item.setTitle("Add to Watchlist");

                                                WathListModeServer.Remove(jsonObject.getString("id"), getActivity());
                                            }
                                            Toast.makeText(getActivity(), jsonObject.getString("title") + " was "+DisplayName+" Watchlist"
                                                    , Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                }
                                return true;
                            }
                        });
                        // Showing the popup menu
                        imgBtnPop.show();
                    }
                });
                linearLayout.addView(layOut);
            } catch (JSONException | MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    //R.id.popularTVScroll
    public boolean setPoptvItem(JSONArray response) {
        LinearLayout linearLayout = view.findViewById(R.id.popularTVScroll);
        for (int i = 0; i < response.length(); i++) {
            try {
                final JSONObject jsonObject = response.getJSONObject(i);
                //Log.d("TAG", jsonObject.getString("poster_path"));
                View layOut = getLayoutInflater().inflate(R.layout.movie_item, null);

                ImageView imageView = layOut.findViewById(R.id.imageViewItem);
                ImageButton imageBtn = layOut.findViewById(R.id.imageButtonItem);
                URL url = new URL(jsonObject.getString("poster_path"));
                String movieId = jsonObject.getString("id");
                String tempUrl = "https://www.themoviedb.org/" + "tv" + "/" + movieId;

                GlideApp.with(layOut)
                        .load(url)
                        .into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(imageView.getContext(), DetailActivity.class);
                        try {
                            myIntent.putExtra("DetailActivityId", jsonObject.getString("id"));
                            myIntent.putExtra("MediaType", "tv");
                            myIntent.putExtra("name", jsonObject.getString("title"));
                            myIntent.putExtra("path", jsonObject.getString("poster_path"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        imageView.getContext().startActivity(myIntent);
                    }
                });
                PopupMenu imgBtnPop = new PopupMenu(getActivity(), imageBtn);
                imgBtnPop.getMenuInflater().inflate(R.menu.popup_menu, imgBtnPop.getMenu());

                if(WathListModeServer.IsExist(jsonObject.getString("id"),getActivity())){
                    imgBtnPop.getMenu().findItem(R.id.homeAddWl).setTitle("Remove from Watchlist");
                }
                imageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgBtnPop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                // Toast message on menu item clicked
//                                Toast.makeText(getActivity(), "You Clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();

                                switch (item.getItemId()) {
                                    case R.id.openInTmdb:
                                        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.themoviedb.org/tv/" + movieId));
                                        startActivity(intent);
                                        break;
                                    case R.id.homeShareFb:
                                        Intent intentfb = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://facebook.com/sharer/sharer.php?u=" + tempUrl));
                                        startActivity(intentfb);
                                        break;
                                    case R.id.homeShareTw:
                                        Intent intenttw = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://twitter.com/intent/tweet?text=Check%20this%20out!%20" + tempUrl));
                                        startActivity(intenttw);
                                        break;
                                    case R.id.homeAddWl:
                                        try {
                                            String popItem = item.getTitle().toString();
                                            String DisplayName = "";
                                            if (popItem.equals("Add to Watchlist")) {
                                                DisplayName = "added to";
                                                item.setTitle("Remove from Watchlist");

                                                WathListModeServer.Add(new WatchlistModel(
                                                        jsonObject.getString("id"),
                                                        jsonObject.getString("title"),
                                                        "tv",
                                                        jsonObject.getString("poster_path")), getActivity());
                                            } else {
                                                DisplayName = "removed from";
                                                item.setTitle("Add to Watchlist");

                                                WathListModeServer.Remove(jsonObject.getString("id"), getActivity());
                                            }
                                            Toast.makeText(getActivity(), jsonObject.getString("title") + " was "+DisplayName+" Watchlist"
                                                    , Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                }
                                return true;
                            }
                        });
                        // Showing the popup menu
                        imgBtnPop.show();
                    }
                });
                linearLayout.addView(layOut);
            } catch (JSONException | MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try{
            view = inflater.inflate(R.layout.fragment_home, container, false);

            VollyHelper.ResponseRes(MovieUrl, this.getActivity(), this::setMoivewSliderViews);

            VollyHelper.ResponseRes(MovieUrl2, this.getActivity(), this::setMovieItem);

            VollyHelper.ResponseRes(MovieUrl1, this.getActivity(), this::setPopMovieItem);


            VollyHelper.ResponseRes(TVUrl, this.getActivity(), this::setTVSliderViews);

            VollyHelper.ResponseRes(TVUrl2, this.getActivity(), this::setTVItem);

            VollyHelper.ResponseRes(TVUrl1, this.getActivity(), this::setPoptvItem);

            TextView mLink = view.findViewById(R.id.TMDBLink);
            mLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.themoviedb.org/"));
                    startActivity(intent);
                }
            });


            RadioButton homeMovieTextV = view.findViewById(R.id.homeMovieTextV);
            homeMovieTextV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homeMovieTextVClick(1);

                }
            });

            RadioButton homeTVTextV = view.findViewById(R.id.homeTVTextV);
            homeTVTextV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    homeMovieTextVClick(2);
                }
            });

            Init();
        }catch (Exception e){

        }
        return view;
    }

    public void homeMovieTextVClick(int Type) {
        if (Type == 1) {
            SliderView sliderView = view.findViewById(R.id.sliderTv);
            sliderView.setVisibility(View.GONE);
            LinearLayout topTVScroll = view.findViewById(R.id.homeTVTopLinear);
            topTVScroll.setVisibility(View.GONE);
            LinearLayout popularTVScroll = view.findViewById(R.id.homeTVPopularLinear);
            popularTVScroll.setVisibility(View.GONE);

            SliderView sliderView1 = view.findViewById(R.id.slider);
            sliderView1.setVisibility(View.VISIBLE);
            LinearLayout topTVScroll1 = view.findViewById(R.id.homeMovieTopLinear);
            topTVScroll1.setVisibility(View.VISIBLE);
            LinearLayout popularTVScroll1 = view.findViewById(R.id.homeMoviePopularLinear);
            popularTVScroll1.setVisibility(View.VISIBLE);
        } else {
            SliderView sliderView = view.findViewById(R.id.sliderTv);
            sliderView.setVisibility(View.VISIBLE);
            LinearLayout topTVScroll = view.findViewById(R.id.homeTVTopLinear);
            topTVScroll.setVisibility(View.VISIBLE);
            LinearLayout popularTVScroll = view.findViewById(R.id.homeTVPopularLinear);
            popularTVScroll.setVisibility(View.VISIBLE);

            SliderView sliderView1 = view.findViewById(R.id.slider);
            sliderView1.setVisibility(View.GONE);
            LinearLayout topTVScroll1 = view.findViewById(R.id.homeMovieTopLinear);
            topTVScroll1.setVisibility(View.GONE);
            LinearLayout popularTVScroll1 = view.findViewById(R.id.homeMoviePopularLinear);
            popularTVScroll1.setVisibility(View.GONE);
        }

    }

    public void Init() {
        SliderView sliderView = view.findViewById(R.id.sliderTv);
        sliderView.setVisibility(View.GONE);
        LinearLayout topTVScroll = view.findViewById(R.id.homeTVTopLinear);
        topTVScroll.setVisibility(View.GONE);
        LinearLayout popularTVScroll = view.findViewById(R.id.homeTVPopularLinear);
        popularTVScroll.setVisibility(View.GONE);
    }


}
