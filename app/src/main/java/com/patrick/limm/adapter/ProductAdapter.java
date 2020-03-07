package com.patrick.limm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.patrick.limm.R;
import com.patrick.limm.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context mContext;
    private List<Product> mProducts = new ArrayList<>();

    public ProductAdapter(Context context, ArrayList<Product> list) {
        super(context, 0 , list);
        mContext = context;
        mProducts = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_product,parent,false);

        Product currentMovie = mProducts.get(position);

        if(!currentMovie.getImage().trim().isEmpty()) {
            final ImageView _image = (ImageView) listItem.findViewById(R.id.imgProduct);
            Glide.with(mContext)
                    .asBitmap()
                    .load(currentMovie.getImage())
                    .into(_image);
        }

        TextView _name = (TextView) listItem.findViewById(R.id.txtName);
        _name.setText(currentMovie.getName());

        return listItem;
    }
}
