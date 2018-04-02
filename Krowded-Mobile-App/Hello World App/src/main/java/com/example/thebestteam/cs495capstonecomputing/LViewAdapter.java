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

public class LViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mDataSource;

    //speeds up the rendering of the list
    //smoother scrolling(very needed)
    private static class ViewHolder {
        public TextView titleTextView;
        public TextView subtitleTextView;
        public TextView detailTextView;
        public ImageView thumbnailImageView;
    }


    public LViewAdapter(Context context, ArrayList<String> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item

     //   if(convertView == null)
        //View rowView = mInflater.inflate(R.layout.list_item, parent, false);

        /*
        RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder();
        holder.icon = (ImageView) convertView.findViewById(R.id.listitem_image);
        holder.text = (TextView) convertView.findViewById(R.id.listitem_text);
        holder.timestamp = (TextView) convertView.findViewById(R.id.listitem_timestamp);
        //holder.progress = (ProgressBar) convertView.findViewById(R.id.progress_spinner);
        convertView.setTag(holder);
        */



        // Get title element
        //TextView titleTextView = (TextView) rowView.findViewById(R.id.list_title);

        // Get subtitle element
        //TextView subtitleTextView = (TextView) rowView.findViewById(R.id.list_subtitle);

        // Get detail element
        //TextView detailTextView = (TextView) rowView.findViewById(R.id.list_detail);

        // Get thumbnail element
        //ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.list_thumbnail);

        // 1
        //gets a value from the base adapter
        //the type should be what you are expecting to store

        ViewHolder holder;

// 1
        if(convertView == null) {

            // 2
            convertView = mInflater.inflate(R.layout.list_item, parent, false);

            // 3
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.list_thumbnail);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.list_title);
            holder.subtitleTextView = (TextView) convertView.findViewById(R.id.list_subtitle);
            holder.detailTextView = (TextView) convertView.findViewById(R.id.list_detail);

            // 4
            convertView.setTag(holder);
        }
        else{
            // 5
            holder = (ViewHolder) convertView.getTag();
        }

// 6
        //get the information back from the static class
        //these will change once the class attributes change
        //and the number of things i want to display change
        TextView titleTextView = holder.titleTextView;
        TextView subtitleTextView = holder.subtitleTextView;
        TextView detailTextView = holder.detailTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;

        String place = (String) getItem(position);


        //set the strings that are displayed in the list
        //always defaulting to title_view right now

        //these lines needed?
        titleTextView.setText(R.string.title_view);
        subtitleTextView.setText(R.string.subtitle_view);
        detailTextView.setText(R.string.detail_view);

        // 3
        //"https://www.google.com/search?hl=en&biw=1602&bih=796&tbm=isch&sa=1&ei=BFfBWtbgN42UsgXho4_YCg&btnG=Search&q=angry+coder#imgrc=o-T_Pvcw-XNTmM:"



        //why is replace here.....
        Picasso.get()
                .load("https://i.imgur.com/tGbaZCY.jpg")
                //.load("https://i.imgur.com/tGbaZCY.jpg".replace("large", "bmiddle"))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .placeholder(R.drawable.failed1)
                .config(Bitmap.Config.RGB_565)
                //.tag(PicassoOnScrollListener.TAG)
                .into(thumbnailImageView);
        /*
        Picasso.Builder builder = new Picasso.Builder(mContext);
        builder.listener(new Picasso.Listener()
        {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
            {
                Toast.makeText(mContext, "image failed to loada!", Toast.LENGTH_LONG).show();
                exception.printStackTrace();
            }
        });
        builder.build().load("https://i.imgur.com/tGbaZCY.jpg")
                .resize(200,200)
                .placeholder(R.drawable.failed1)
                .into(thumbnailImageView);

                */

        //Picasso.get().load("https://www.google.com/search?hl=en&biw=1602&bih=796&tbm=isch&sa=1&ei=BFfBWtbgN42UsgXho4_YCg&btnG=Search&q=angry+coder#imgrc=o-T_Pvcw-XNTmM:")
         //       .placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);

        //Picasso.with(mContext).load(recipe.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);

        return convertView;
    }


}
