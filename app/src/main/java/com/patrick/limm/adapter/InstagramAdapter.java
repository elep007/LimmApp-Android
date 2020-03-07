package com.patrick.limm.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.patrick.limm.R;
import com.patrick.limm.activity.ProductDetailActivity;
import com.patrick.limm.model.Common;
import com.patrick.limm.model.Product;

import java.util.ArrayList;


/**
 * Created by User on 2/12/2018.
 */

public class InstagramAdapter extends RecyclerView.Adapter<InstagramAdapter.ViewHolder> {

    private static final String TAG = "InstagramAdapter";

    //vars
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public InstagramAdapter(Context context, ArrayList<String> child) {
        mImages=child;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_instagram_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
String a=mImages.get(position);
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder._image);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView _image;

        public ViewHolder(View itemView) {
            super(itemView);
            _image = itemView.findViewById(R.id.imgThumnail);
        }
    }
}
