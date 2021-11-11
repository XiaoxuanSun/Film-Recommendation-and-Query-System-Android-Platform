package com.example.myapplication.adapter;


import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.DetailActivity;
import com.example.myapplication.HomeFragment;
import com.example.myapplication.R;
import com.example.myapplication.modal.SliderData;
import com.example.myapplication.module.GlideApp;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

//import jp.wasabeef.glide.transformations.BlurTransformation;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {
    // list for storing urls of images.
    private List<SliderData> mSliderItems;

    // Constructor
    public SliderAdapter(Context context, ArrayList<SliderData> sliderDataArrayList) {
        notifyDataSetChanged();
        this.mSliderItems = sliderDataArrayList;
    }
    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {


        final SliderData sliderItem = mSliderItems.get(position);

        // Glide is use to load image
        // from url in your imageview
        GlideApp.with(viewHolder.itemView)
                .load(sliderItem.getImgUrl())
                .fitCenter()
                .into(viewHolder.imageViewBackground)
                ;
//        Glide.with(viewHolder.itemView)
//                .load(sliderItem.getImgUrl())
//                .fitCenter()
//                .apply(RequestOptions.bitmapTransform(new BlurTransformation(3,3)))
//                .into(viewHolder.imageViewBackground2);
//        this.notifyDataSetChanged();
        GlideApp.with(viewHolder.itemView)
                .load(sliderItem.getImgUrl())
                .fitCenter()
                .override(80,180)
                .into(viewHolder.imageViewBackground2);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(viewHolder.itemView.getContext(), DetailActivity.class);
                myIntent.putExtra("DetailActivityId",sliderItem.getId());
                myIntent.putExtra("MediaType",sliderItem.getType());
                myIntent.putExtra("name", sliderItem.getName());
                myIntent.putExtra("path", sliderItem.getImgUrl());
                viewHolder.itemView.getContext().startActivity(myIntent);
            }
        });
    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        ImageView imageViewBackground;
        ImageView imageViewBackground2;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
            imageViewBackground2 = itemView.findViewById(R.id.myimage1);



            this.itemView = itemView;
        }
    }

}
