package com.patrick.limm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.patrick.limm.R;
import com.patrick.limm.adapter.VideoListAdapter;
import com.patrick.limm.model.Common;
import com.patrick.limm.model.Video;

import java.util.ArrayList;

public class VideoListActivity extends AppCompatActivity {
    ArrayList<Video> mVideos=new ArrayList<>();
    VideoListAdapter mAdapter;
    String mAsin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        mAsin=getIntent().getStringExtra("asin");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Workout Videos");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_backbutton);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                }
        );

        ListView _lstFiles = findViewById(R.id.lstFiles);
        _lstFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mVideos.get(position).getUrl()));
                startActivity(browserIntent);
            }
        });

        loadData();
    }

    private void loadData(){
        ArrayList<String> names=new ArrayList<>();

        for(Video theVideo:Common.getInstance().getmVideos()){
            if(theVideo.getAsin().equals(mAsin)){
                mVideos.add(theVideo);
                names.add(theVideo.getName());
            }
        }
        mAdapter = new VideoListAdapter(this, names,"file");
        ListView _lstFiles=findViewById(R.id.lstFiles);
        _lstFiles.setAdapter(mAdapter);
    }
}
