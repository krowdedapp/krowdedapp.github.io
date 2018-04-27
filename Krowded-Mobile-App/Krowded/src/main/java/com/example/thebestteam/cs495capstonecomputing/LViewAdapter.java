package com.example.thebestteam.cs495capstonecomputing;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class LViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<JSONObject>mDataSource;

    //speeds up the rendering of the list
    //smoother scrolling(very needed)
    private static class ViewHolder {
        public TextView titleTextView;
        public TextView subtitleTextView;
        public TextView detailTextView;
        public ImageView thumbnailImageView;
    }


    public LViewAdapter(Context context, List<JSONObject> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public String getURL(int position) {

        String photo_reference = "";
        try {
            JSONArray temp = PlaceJSONParser.allPlaces.get(position).getJSONArray("photos");
            JSONObject obj = (JSONObject)temp.get(0);
            photo_reference = obj.getString("photo_reference");

        }
        catch(JSONException e)
        {}

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=");
        sb.append(photo_reference);
        sb.append("&key=");
        sb.append("AIzaSyBb4_AGSb9PWWsv3AfQQpvJMZpGV9oajiQ");
        return sb.toString();
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }


    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        String URL = "";

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



        try {
            titleTextView.setText(mDataSource.get(position).getString("name"));
        }
        catch (JSONException e) {}


        try {
            URL = PlaceJSONParser.allPlaces.get(position).getString("icon");
        }
        catch(JSONException e)
        {}


        Picasso.get()
                .load(getURL(position))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .placeholder(R.drawable.failed1)
                .config(Bitmap.Config.RGB_565)//affects how many bits are used to store each color
                .into(thumbnailImageView);


        return convertView;
    }
}
