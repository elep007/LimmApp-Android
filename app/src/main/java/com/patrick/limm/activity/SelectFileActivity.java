package com.patrick.limm.activity;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.patrick.limm.R;
import com.patrick.limm.adapter.FileListAdapter;
import com.patrick.limm.model.Common;

import java.util.ArrayList;

public class SelectFileActivity extends AppCompatActivity {

    ArrayList<String> mUrls=new ArrayList<>();
    ArrayList<String> mNames=new ArrayList<>();
    ArrayList<String> mIcon=new ArrayList<>();

    FileListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_file);

        String title=getIntent().getStringExtra("title");
        String data=getIntent().getStringExtra("data");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
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

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrls.get(position)));
                startActivity(browserIntent);
            }
        });

        initView(data);
    }

    private void initView(String data){
        String [] pdfs=data.split("_split_");

        for(String thepdf : pdfs){
            String [] temp=thepdf.split("_name_");
            mUrls.add(temp[0]);
            String [] temp1=temp[1].split("_icon_");
            mNames.add(temp1[0]);
            if(temp1.length==1){
                mIcon.add("");
            }
            else {
                mIcon.add(Common.getInstance().getBaseURL() + "ebookicon/" + temp1[1]);
            }
        }

        mAdapter = new FileListAdapter(this, mNames,mIcon);
        ListView _lstFiles=findViewById(R.id.lstFiles);
        _lstFiles.setAdapter(mAdapter);
    }
}
