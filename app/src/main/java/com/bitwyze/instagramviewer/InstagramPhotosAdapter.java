package com.bitwyze.instagramviewer;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
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

    private String formatTime(long createTime)
    {

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
        Date createDate = new Date(photo.createTime * 1000L);
        Date now = new Date();
        long nowInMillis = now.getTime();
        String relativeTime =  (String)DateUtils.getRelativeDateTimeString(context, photo.createTime * 1000L, DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);
        String relatimteTimeSpan = (String)DateUtils.getRelativeTimeSpanString(context,photo.createTime * 1000L);
        Calendar calendar = Calendar.getInstance();
        long nowCalendar = calendar.getTimeInMillis();
        //String relativeTime = DateUtils.getRelativeTimeSpanString(createDate, DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS);
       // tvCreateTime.setText(convertTimeToDate(photo.createTime));
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
