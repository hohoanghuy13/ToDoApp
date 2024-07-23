package com.example.todoapp.adapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.todoapp.R;

public class ImageBindingAdapter {
    @BindingAdapter({"imageUrl"})
    public static void setImageUrl(ImageView imageView, String urlImage) {
        //Bo goc hinh anh
        RequestOptions requestOptions = new RequestOptions().transform(new RoundedCorners(8));

        Glide.with(imageView.getContext())
                .load(urlImage)
                .error(R.drawable.uploadimg)
                .apply(requestOptions)
                .into(imageView);
    }
}
