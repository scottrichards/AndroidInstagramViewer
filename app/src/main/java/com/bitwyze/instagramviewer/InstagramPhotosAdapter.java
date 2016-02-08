package com.bitwyze.instagramviewer;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
    DecimalFormat formatter;

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.context = context;
        roundUserImageTransformation = new RoundedTransformationBuilder()
                .borderColor(Color.LTGRAY)
                .borderWidthDp(1)
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);

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

    // return a string representing how long ago the createTime parameter timestamp in milliseconds is from now
    // if the createTime is in the past then it trims off the "ago, 3:30pm" so it would return a string like:
    // 1 hr.  or 52 min.
    private String formatRelativeTime(long createTime)
    {
        String relativeTime =  (String)DateUtils.getRelativeDateTimeString(context, createTime, DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);
        int lastIndexOfAgo = relativeTime.indexOf("ago");
        if (lastIndexOfAgo > 0 ) {
            String result = relativeTime.substring(0, lastIndexOfAgo);
            Log.d("instagram", "result: " + result);
            return(result);
        } else {
            return relativeTime;
        }
    }

    // Return the corresponding View with an Instagram entry for the Give position
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
        String relativeTime = formatRelativeTime(photo.createTime * 1000L);
        tvCreateTime.setText(relativeTime);
        tvUserName.setText(photo.username);
        tvLikes.setText(formatter.format(photo.likeCount));
        tvCaption.setText(photo.caption);
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).placeholder(context.getResources().getDrawable(R.drawable.image_placeholder)).error(context.getResources().getDrawable(R.drawable.image_placeholder)).into(ivPhoto);
        Picasso.with(getContext()).load(photo.userImageUrl).placeholder(context.getResources().getDrawable(R.drawable.user_image)).error(context.getResources().getDrawable(R.drawable.user_image)).transform(roundUserImageTransformation).into(ivUserImage);
        return convertView;
    }
}
