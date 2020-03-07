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
import com.patrick.limm.adapter.FileListAdapter;
import com.patrick.limm.adapter.VideoListAdapter;
import com.patrick.limm.model.Common;
import com.patrick.limm.model.Video;

import java.util.ArrayList;

public class VideoFolderActivity extends AppCompatActivity {
    ArrayList<String> mAsins=new ArrayList<>();
    ArrayList<String> mNames=new ArrayList<>();
    VideoListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_folder);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Workout Video Portal");
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

        ListView _lstFolders = findViewById(R.id.lstFolders);
        _lstFolders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(),VideoListActivity.class).putExtra("asin",mAsins.get(position));
                startActivity(intent);
            }
        });

        loadData();
    }

    private void loadData(){
        for(Video theVideo: Common.getInstance().getmVideos()){
            String name=Common.getInstance().getProductFromAsin(theVideo.getAsin()).getShortname();
            if(!mNames.contains(name)){
                mNames.add(name);
                mAsins.add(theVideo.getAsin());
            }
        }
        mAdapter = new VideoListAdapter(this, mNames,"folder");
        ListView _lstFiles=findViewById(R.id.lstFolders);
        _lstFiles.setAdapter(mAdapter);
    }
}
