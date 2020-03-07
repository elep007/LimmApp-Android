package com.patrick.limm.adapter;

import android.content.Context;
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

public class FileListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mNames = new ArrayList<>();
    private List<String> mIcons=new ArrayList<>();

    public FileListAdapter(Context context, ArrayList<String> names, ArrayList<String> icons) {
        super(context, 0 , names);
        mContext = context;
        mNames=names;
        mIcons=icons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_file,parent,false);

        TextView _name = (TextView) listItem.findViewById(R.id.txtName);
        _name.setText(mNames.get(position));

        if(!mIcons.get(position).isEmpty()){
            ImageView _image=(ImageView) listItem.findViewById(R.id.imgIcon);
            Glide.with(mContext)
                    .asBitmap()
                    .load(mIcons.get(position))
                    .into(_image);
        }

        return listItem;
    }
}
