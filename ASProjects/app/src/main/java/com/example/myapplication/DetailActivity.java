package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.Model.WatchlistModel;
import com.example.myapplication.Model.WathListModeServer;
import com.example.myapplication.module.GlideApp;
import com.example.myapplication.vollyHelper.VollyHelper;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String MovieDetailUrl = "https://xiaox-sun-node-hw9.wl.r.appspot.com/movie_detail/";
    private static final String TVDetailUrl = "https://xiaox-sun-node-hw9.wl.r.appspot.com/tv_detail/";
    private static final String MovieRecomUrl = "https://xiaox-sun-node-hw9.wl.r.appspot.com/recommend_movie/";
    private static final String TvRecomUrl = "https://xiaox-sun-node-hw9.wl.r.appspot.com/recommend_tv/";
    private static final String TMDBUrl = "https://www.themoviedb.org/";

    private static String DetailUrl = "";
    private static String recommendUrl = "";
    private static String tempUrl = "";
    //private YouTubePlayerView youtubePlayer;
//    private String detailVideo;
    private TextView mOverview;
    private String detailOverview;
    private TextView mDetailTitle;
    private ImageView mDetailBackImg;
    private TextView mGenresTextV;
    private TextView mYearTextV;
    private ImageButton mDetailWlBtn;
    private ImageButton mDetailFBBtn;
    private ImageButton mDetailTWBtn;
    private GridLayout mDetailCastGrid;
    private LinearLayout mReviewContentLayout;
    private LinearLayout mRecommendScroll;

    private LinearLayout mDetailVideoLayout;
    private LinearLayout mDetailBackdropLayout;

    private RequestQueue queue;
    private JsonObjectRequest request;
    private YouTubePlayerView youtubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            WatchlistFragment.flag = true;
            setContentView(R.layout.activity_detail);

            Intent intent = getIntent();
            String DetailActivityId = intent.getStringExtra("DetailActivityId");
            String MediaType = intent.getStringExtra("MediaType");
            String name = intent.getStringExtra("name");
            String path = intent.getStringExtra("path");

            ImageButton imageButton = findViewById(R.id.detailWlBtn);
            if (WathListModeServer.IsExist(DetailActivityId, DetailActivity.this)) {
                imageButton.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24);
            }
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String DisplayName = "";
                    if (WathListModeServer.IsExist(DetailActivityId, DetailActivity.this)) {
                        DisplayName = "removed from";
                        WathListModeServer.Remove(DetailActivityId, DetailActivity.this);
                        imageButton.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
                    } else {
                        DisplayName = "added to";
                        WathListModeServer.Add(new WatchlistModel(DetailActivityId, name, MediaType, path), DetailActivity.this);
                        imageButton.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24);
                    }

                    Toast.makeText(DetailActivity.this, name + " was " + DisplayName + " Watchlist", Toast.LENGTH_SHORT).show();

                }
            });

//        String DetailUrl = "";
            if (MediaType.equals("movie")) {
                DetailUrl = MovieDetailUrl + DetailActivityId;
                recommendUrl = MovieRecomUrl + DetailActivityId;
                tempUrl = TMDBUrl + "movie" + "/" + DetailActivityId;
            } else if (MediaType.equals("tv")) {
                DetailUrl = TVDetailUrl + DetailActivityId;
                recommendUrl = TvRecomUrl + DetailActivityId;
                tempUrl = TMDBUrl + "tv" + "/" + DetailActivityId;
            } else {
                DetailUrl = "";
            }
//        TextView textView = findViewById(R.id.test);
//        textView.setText(DetailActivityId);
            detailQuery();

            mDetailTitle = findViewById(R.id.detailTitle);
            mOverview = findViewById(R.id.readmoreView);
            mDetailBackImg = findViewById(R.id.detailBackdropImg);
            mGenresTextV = findViewById(R.id.genresTextV);
            mYearTextV = findViewById(R.id.yearTextV);
            mDetailFBBtn = findViewById(R.id.detailFBBtn);
            mDetailTWBtn = findViewById(R.id.detailTWBtn);
            mDetailCastGrid = findViewById(R.id.detailCastGrid);
            mReviewContentLayout = findViewById(R.id.reviewContentLayout);
            mRecommendScroll = findViewById(R.id.RecommendScroll);
            mDetailVideoLayout = findViewById(R.id.detailVideoLayout);
            mDetailBackdropLayout = findViewById(R.id.detailBackdropLayout);

            VollyHelper.ResponseRes(recommendUrl, this, this::test);
        } catch (Exception e) {

        }


    }

    public void test(JSONArray response) {
        try {
            if(response.length() < 1) {
                findViewById(R.id.detailRecomLayout).setVisibility(View.GONE);
            }else {
                for (int i = 0; i < response.length(); i++) {

                    JSONObject jsonObject = response.getJSONObject(i);
                    // Log.d("TAG", jsonObject.getString("poster_path"));
                    View layOut4 = getLayoutInflater().inflate(R.layout.recommend_item, null);
                    ImageView mRecomImageItem = layOut4.findViewById(R.id.recomImageItem);
                    URL url = new URL(jsonObject.getString("poster_path"));
                    String movieId = jsonObject.getString("id");

                    if(!DetailActivity.this.isFinishing()){
                        GlideApp.with(layOut4)
                                .load(url)
                                .into(mRecomImageItem);
                    }


                    mRecomImageItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent myIntent = new Intent(mRecomImageItem.getContext(), DetailActivity.class);
                                myIntent.putExtra("DetailActivityId", movieId);
                                myIntent.putExtra("path", jsonObject.getString("poster_path"));
                                myIntent.putExtra("MediaType", jsonObject.getString("media_type"));
                                myIntent.putExtra("name", jsonObject.getString("title"));
                                mRecomImageItem.getContext().startActivity(myIntent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                    mRecommendScroll.addView(layOut4);

                }
            }
        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void detailQuery() {
        try {
            queue = Volley.newRequestQueue(this);

            request = new JsonObjectRequest(Request.Method.GET, DetailUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONArray detailArray = new JSONArray();
                    JSONArray detailVideoArray = new JSONArray();
                    JSONArray detailCastArray = new JSONArray();
                    JSONArray detailReviewArray = new JSONArray();
                    JSONObject jsonObject = new JSONObject();
                    String detailTitle = "";
                    try {
                        //View layOut = getLayoutInflater().inflate(R.layout.activity_detail, null);
                        View layOut = findViewById(R.id.DetailLinl);

                        detailArray = response.getJSONArray("movieDetial");
                        detailVideoArray = response.getJSONArray("movieVideo");
                        detailCastArray = response.getJSONArray("movieCast");
                        detailReviewArray = response.getJSONArray("movieReview");
                        jsonObject = detailArray.getJSONObject(0);

                        String detailVideo = detailVideoArray.getJSONObject(0).getString("key");
                        URL url = new URL(jsonObject.getString("backdrop_path"));
                        detailOverview = jsonObject.getString("overview");

                        if (detailVideo.equals("S0Q4gqBUs7c")) {
                            mDetailVideoLayout.setVisibility(View.GONE);
                            mDetailBackdropLayout.setVisibility(View.VISIBLE);
                        }

                        setVideo(detailVideo);
                        if(!DetailActivity.this.isFinishing()){
                            GlideApp.with(layOut)
                                    .load(url)
                                    .into(mDetailBackImg);
                        }


                        mDetailTitle.setText(jsonObject.getString("title"));

                        if (mDetailTitle.getLineCount() > 1) {
                            mDetailTitle.setGravity(Gravity.CENTER);
                        } else {
                            mDetailTitle.setGravity(Gravity.LEFT);
                        }

                        if (TextUtils.isEmpty(detailOverview)) {
                            findViewById(R.id.detailOverviewLayout).setVisibility(View.GONE);
                        } else {
                            mOverview.setText(detailOverview);
                        }
                        JSONArray genresJsonArr = jsonObject.getJSONArray("genres");
                        String GenresName = "";

                        if (genresJsonArr.length() < 1) {
                            findViewById(R.id.detailGenreLayout).setVisibility(View.GONE);
                        } else {
                            for (int i = 0; i < genresJsonArr.length(); i++) {
                                GenresName += genresJsonArr.getJSONObject(i).getString("name");
                                if (i != genresJsonArr.length() - 1) {
                                    GenresName += ", ";
                                }
                            }
                            mGenresTextV.setText(GenresName);
                        }

                        mYearTextV.setText(jsonObject.getString("release_date").substring(0, 4));
                        mDetailFBBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intentfbD = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://facebook.com/sharer/sharer.php?u=" + tempUrl));
                                startActivity(intentfbD);
                            }
                        });
                        mDetailTWBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intenttwD = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://twitter.com/intent/tweet?text=Check%20this%20out!%20" + tempUrl));
                                startActivity(intenttwD);
                            }
                        });
                        if (detailCastArray.length() < 1) {
                            findViewById(R.id.detailCastLayout).setVisibility(View.GONE);
                        } else {
                            for (int i = 0; i < detailCastArray.length(); i++) {
                                View layOut2 = getLayoutInflater().inflate(R.layout.cast_item, null);
                                ImageView mCastImg = layOut2.findViewById(R.id.castCircleImage);
                                TextView mCastName = layOut2.findViewById(R.id.castName1);
                                URL url2 = new URL(detailCastArray.getJSONObject(i).getString("profile_path"));
                                if(!DetailActivity.this.isFinishing()){
                                    GlideApp.with(layOut2)
                                            .load(url2)
                                            .into(mCastImg);
                                }

                                mCastName.setText(detailCastArray.getJSONObject(i).getString("name"));
                                mDetailCastGrid.addView(layOut2);
                            }
                        }
                        if (detailReviewArray.length() < 1) {
                            findViewById(R.id.detailReviewLayout).setVisibility(View.GONE);
                        } else {
                            for (int j = 0; j < detailReviewArray.length(); j++) {
                                View layOut3 = getLayoutInflater().inflate(R.layout.review_item, null);
                                TextView mReviewCreater = layOut3.findViewById(R.id.reviewItemPeople);
//                        TextView mReviewTime = layOut3.findViewById(R.id.reviewItemTime);
                                TextView mReviewRating = layOut3.findViewById(R.id.reviewItemRating);
                                TextView mReviewContent = layOut3.findViewById(R.id.reviewItemContent);

                                SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy");
                                String time = formatter.format(new SimpleDateFormat("yyyy-MM-dd").parse(detailReviewArray.getJSONObject(j).getString("created_at")));

                                mReviewCreater.setText("by " + detailReviewArray.getJSONObject(j).getString("author") + " on " + time);
//                        mReviewTime.setText(time);
//                        mReviewTime.setText(formatterDate);
                                int rating = Integer.parseInt(detailReviewArray.getJSONObject(j).getString("rating"));
                                String ratingStr = rating % 2 == 0 ? rating / 2 + "" : Float.parseFloat(detailReviewArray.getJSONObject(j).getString("rating")) / 2 + "";
                                mReviewRating.setText(ratingStr);

                                mReviewContent.setText(detailReviewArray.getJSONObject(j).getString("content"));

                                CardView reviewItemCard = layOut3.findViewById(R.id.reviewItemCard);
                                reviewItemCard.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
                                        TextView resultRateTextV = v.findViewById(R.id.reviewItemRating);
                                        TextView reviewItemPeop = v.findViewById(R.id.reviewItemPeople);
//                                TextView reviewItemTime = v.findViewById(R.id.reviewItemTime);
                                        TextView reviewItemContent = v.findViewById(R.id.reviewItemContent);

                                        Bundle bundle = new Bundle();
                                        bundle.putCharSequence("reviewRate", resultRateTextV.getText());
                                        bundle.putCharSequence("reviewPeople", reviewItemPeop.getText());
//                                bundle.putCharSequence("reviewTime",time);
                                        bundle.putCharSequence("reviewContent", reviewItemContent.getText());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                                mReviewContentLayout.addView(layOut3);
                            }
                        }


                    } catch (JSONException | MalformedURLException | ParseException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e("TAG", error.getMessage(), error);
                }
            });
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);

            queue.add(request);
        } catch (Exception e) {

        }

    }

    public void setVideo(String youtubeVideoId) {

        try {
            youtubePlayerView = findViewById(R.id.youtube_player_view);

            youtubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
                youTubePlayer.cueVideo(youtubeVideoId, 0);
            });
//        //youtubePlayerView.set
//        getLifecycle().addObserver(youtubePlayerView);
//        youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
////                String videoId = null;
////                try {
////                    videoId = video.getJSONObject(0).getString("key");
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                };
////                if(videoId!=null) {
////                    youTubePlayer.loadVideo(videoId, 0);
////                }
//
//
//            }
//        });
        } catch (Exception e) {

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (youtubePlayerView != null) {

            youtubePlayerView.release();


        }
        GlideApp.with(getApplicationContext()).pauseRequests();
    }

}