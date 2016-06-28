package com.fooyemeet2.Activities.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fooyemeet2.Activities.Models.Photos;
import com.fooyemeet2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lzx on 21/06/16.
 */
public class PhotosAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Photos> items;
    private Context context;

    public PhotosAdapter(Context context, List<Photos> items) {
        this.items = items;
        this.context = context;
    }

    public static class PhotosViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public PhotosViewHolder(View v){
            super(v);
            mImageView = (ImageView) v.findViewById(R.id.imageView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotosViewHolder(LayoutInflater.from(context).inflate(R.layout
                        .item_photo,
                parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhotosViewHolder photosViewHolder = (PhotosViewHolder) holder;
        bindPhoto(photosViewHolder, position);
    }

    private void bindPhoto(final PhotosViewHolder photosViewHolder, final int
            position) {
        Photos p = items.get(position);
        Picasso.with(context).load(p.getPhoto()).fit().centerCrop().noFade().into(photosViewHolder
                .mImageView);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
