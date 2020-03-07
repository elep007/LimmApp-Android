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

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mNames = new ArrayList<>();
    private  String mType;

    public VideoListAdapter(Context context, ArrayList<String> names, String type) {
        super(context, 0 , names);
        mContext = context;
        mNames=names;
        mType=type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_video,parent,false);

        TextView _name = (TextView) listItem.findViewById(R.id.txtName);
        _name.setText(mNames.get(position));

        ImageView _image=(ImageView) listItem.findViewById(R.id.imgIcon);
        if(mType.equals("folder")){
            _image.setImageResource(R.drawable.ic_video_folder);
        }
        else{
            _image.setImageResource(R.drawable.ic_video_home);
        }
        return listItem;
    }
}
