package com.example.thebestteam.cs495capstonecomputing;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<HashMap<String,String>>mDataSource;

    //speeds up the rendering of the list
    //smoother scrolling(very needed)
    private static class ViewHolder {
        public TextView titleTextView;
        public TextView subtitleTextView;
        public TextView detailTextView;
        public ImageView thumbnailImageView;
    }


    public LViewAdapter(Context context, List<HashMap<String,String>> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }


    @Override
    public Object getItem(int position) {
        return mDataSource.get(position).get("place_name");
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null) {

            convertView = mInflater.inflate(R.layout.list_item, parent, false);

            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.list_thumbnail);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.list_title);
            holder.subtitleTextView = (TextView) convertView.findViewById(R.id.list_subtitle);
            holder.detailTextView = (TextView) convertView.findViewById(R.id.list_detail);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }


        //this is how many textViews/ImageViews/whatever is displayed on the activity
        //will need top go into XML and remove the subtitleTextView and detailTextView
        //if they do not end up being needed
        TextView titleTextView = holder.titleTextView;
        //TextView subtitleTextView = holder.subtitleTextView;
        //TextView detailTextView = holder.detailTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;

        //actually sets the text that is used displayed for each of the views
        titleTextView.setText(mDataSource.get(position).get("place_name"));
        //add more here...

        //inflates the image, and displays it
        Picasso.get()
                .load("https://i.imgur.com/tGbaZCY.jpg")
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .placeholder(R.drawable.failed1)
                .config(Bitmap.Config.RGB_565)
                .into(thumbnailImageView);

        return convertView;
    }
}
