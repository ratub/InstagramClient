package com.ratus.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rbhavsar on 2/22/2015.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto>{
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    // Use the template to display each photo


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        InstagramPhoto photo = getItem(position);

        //Check if we are using recycled view

        if (convertView == null){
            // Create new view from template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent,false);

        }
        // Lookup the view for populating the data
        TextView tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);

        // Insert the model data into each of the view item
        tvCaption.setText(photo.caption);

        // Clear out the image view
        ivPhoto.setImageResource(0);

        // Insert the image using picaso
        //Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        Picasso.with(getContext()).load(photo.imageUrl).fit().centerCrop().placeholder(R.drawable.ic_launcher).into(ivPhoto);


        // Return the created item as a view

        return convertView;

    }
}
