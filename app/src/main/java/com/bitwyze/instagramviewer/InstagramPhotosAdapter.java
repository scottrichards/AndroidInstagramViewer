package com.bitwyze.instagramviewer;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by scottrichards on 11/16/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    Transformation roundUserImageTransformation;
    Context context;

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.context = context;
        roundUserImageTransformation = new RoundedTransformationBuilder()
                .borderColor(Color.LTGRAY)
                .borderWidthDp(1)
                .cornerRadiusDp(30)
                .oval(false)
                .build();
    }

    private String convertTimeToDate(long createTime)
    {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
            Date netDate = (new Date(createTime * 1000L));
            String resultStr = sdf.format(netDate);
            return resultStr;
        }
        catch(Exception ex){
            return "xx";
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        TextView tvCaption = (TextView)convertView.findViewById(R.id.caption);
        ImageView ivPhoto = (ImageView)convertView.findViewById(R.id.imageView);
        ImageView ivUserImage = (ImageView)convertView.findViewById(R.id.userImageView);
        TextView tvLikes = (TextView)convertView.findViewById(R.id.likes);
        TextView tvUserName = (TextView)convertView.findViewById(R.id.username);
        TextView tvCreateTime = (TextView)convertView.findViewById(R.id.createTime);
        tvCreateTime.setText(convertTimeToDate(photo.createTime));
        tvUserName.setText(photo.username);
        tvLikes.setText(photo.likes);
        tvCaption.setText(photo.caption);
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).placeholder(context.getResources().getDrawable(R.drawable.image_placeholder)).error(context.getResources().getDrawable(R.drawable.image_placeholder)).into(ivPhoto);
        Picasso.with(getContext()).load(photo.userImageUrl).placeholder(context.getResources().getDrawable(R.drawable.user_image)).error(context.getResources().getDrawable(R.drawable.user_image)).transform(roundUserImageTransformation).into(ivUserImage);
        return convertView;
    }
}
