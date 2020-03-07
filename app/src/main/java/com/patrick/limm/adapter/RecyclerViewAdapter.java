package com.patrick.limm.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.patrick.limm.R;
import com.patrick.limm.activity.ProductDetailActivity;
import com.patrick.limm.model.Common;
import com.patrick.limm.model.Product;

import java.util.ArrayList;


/**
 * Created by User on 2/12/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<Product> mChild = new ArrayList<>();
    private Context mContext;
    private String mASIN;

    public RecyclerViewAdapter(Context context, ArrayList<Product> child,String asin) {
        mChild=child;
        mContext = context;
        mASIN=asin;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mChild.get(position).getThumnail())
                .into(holder._image);

        holder._price.setText(String.format("$%.2f", mChild.get(position).getPrice()));

        if (mChild.get(position).getAsin().equals(mASIN)){
            holder._container.setBackgroundResource(R.drawable.back_edit_red_stroke);
        }

        holder._container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(mContext, ProductDetailActivity.class).putExtra("index", Common.getInstance().getIndex(mChild.get(position).getId()));
            mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChild.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView _image;
        TextView _price;
        LinearLayout _container;

        public ViewHolder(View itemView) {
            super(itemView);
            _image = itemView.findViewById(R.id.imgThumnail);
            _price = itemView.findViewById(R.id.txtPrice);
            _container=itemView.findViewById(R.id.linContainer);
        }
    }
}
