package com.patrick.limm;

import android.content.Intent;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.patrick.limm.activity.HomeActivity;
import com.patrick.limm.model.Book;
import com.patrick.limm.model.Common;
import com.patrick.limm.model.Product;
import com.patrick.limm.model.Video;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    boolean timeFinished, productLoaded, eBookLoaded, videoLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeFinished=false;
        productLoaded=false;
        eBookLoaded=false;
        videoLoaded=false;

        loadData();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeFinished=true;
                moveToHome();
            }
        },3000);
    }

    private void loadData(){
        final DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        DatabaseReference  myRef = mDatabase.child("product");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    ArrayList<Product> products=new ArrayList<>();
                    for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                        Product theProduct= singleSnapshot.getValue(Product.class);
                        products.add(theProduct);
                    }
                    Common.getInstance().setmProducts(products);
                    productLoaded=true;
                    moveToHome();

                } catch (Exception ex) {
                    Toast.makeText(getBaseContext(),ex.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getBaseContext(),"Connect to server failed. Please check your internet connection and try again.",Toast.LENGTH_LONG).show();
            }
        });
        //load eBook data
        DatabaseReference  ebookRef = mDatabase.child("ebook");
        ebookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    ArrayList<Book> books=new ArrayList<>();
                    for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                        Book theBook= singleSnapshot.getValue(Book.class);
                        books.add(theBook);
                    }
                    Common.getInstance().setmBooks(books);
                    eBookLoaded=true;
                    moveToHome();

                } catch (Exception ex) {
                    Toast.makeText(getBaseContext(),ex.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getBaseContext(),"Connect to server failed. Please check your internet connection and try again.",Toast.LENGTH_LONG).show();
            }
        });
        //load eBook data
        DatabaseReference  videoRef = mDatabase.child("video");
        videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    ArrayList<Video> videos=new ArrayList<>();
                    for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                        Video theVideo= singleSnapshot.getValue(Video.class);
                        videos.add(theVideo);
                    }
                    Common.getInstance().setmVideos(videos);
                    videoLoaded=true;
                    moveToHome();

                } catch (Exception ex) {
                    Toast.makeText(getBaseContext(),ex.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getBaseContext(),"Connect to server failed. Please check your internet connection and try again.",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void moveToHome(){
        if(timeFinished && productLoaded && eBookLoaded && videoLoaded){
            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
