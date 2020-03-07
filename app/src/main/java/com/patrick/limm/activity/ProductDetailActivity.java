package com.patrick.limm.activity;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.patrick.limm.R;
import com.patrick.limm.adapter.RecyclerViewAdapter;
import com.patrick.limm.model.Common;
import com.patrick.limm.model.Product;
import com.willy.ratingbar.BaseRatingBar;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {
    int mIndex;
    Product theProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_product_detail);

            mIndex = getIntent().getIntExtra("index", 0);
            theProduct = Common.getInstance().getProducts().get(mIndex);

            //Common.getInstance().setCurPosition(mIndex);

            addEventListener();
            initView();
        }
        catch(Exception ex){
            Toast.makeText(getBaseContext(),"detail_create:"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void addEventListener() {
        try {
            final Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_backbutton);
            toolbar.setNavigationOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), ProductsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
            );
        }
        catch (Exception ex){
            Toast.makeText(getBaseContext(),"detail_addeventlistener:"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        findViewById(R.id.imgVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),VideoListActivity.class).putExtra("asin",theProduct.getAsin());
                startActivity(intent);
            }
        });

        findViewById(R.id.imgManual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String manual=theProduct.getManual();
                if(manual.contains("_split_")){
                    Intent intent=new Intent(getBaseContext(),SelectFileActivity.class).putExtra("title","Instruction Manuals").putExtra("data",manual);
                    startActivity(intent);
                }
                else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(theProduct.getManual()));
                    //Intent intent=new Intent(getBaseContext(),WebActivity.class).putExtra("index",mIndex);
                    startActivity(browserIntent);
                }
            }
        });

        findViewById(R.id.imgEbook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ebook=theProduct.getEbook();
                if(ebook.contains("_split_")){
                    Intent intent=new Intent(getBaseContext(),SelectFileActivity.class).putExtra("title","Workout Guides and Sheets").putExtra("data",ebook);
                    startActivity(intent);
                }
                else{
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(theProduct.getEbook()));
                    startActivity(browserIntent);
                }
            }
        });

        findViewById(R.id.imgProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(theProduct.getAmazon()));
                startActivity(browserIntent);
            }
        });

        findViewById(R.id.txtGotoReviews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(theProduct.getReviewurl()));
                startActivity(browserIntent);
            }
        });

        findViewById(R.id.labelAnswer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!theProduct.getFaq().equals("no")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(theProduct.getFaq()));
                    startActivity(browserIntent);
                }
            }
        });
    }

    private void initView(){
        try {
            TextView a = findViewById(R.id.labelAnswer);
            String htmlString = "<u>Answered Questions</u>";
            a.setText(Html.fromHtml(htmlString));

            ImageView _image = findViewById(R.id.imgProduct);
            Glide.with(this)
                    .asBitmap()
                    .load(theProduct.getImage())
                    .into(_image);
            TextView _name = findViewById(R.id.txtName);
            _name.setText(theProduct.getName());

            int intPart = (int) theProduct.getPrice();
            int decimalPart = (int) ((theProduct.getPrice() - intPart) * 100);
            TextView _priceInt = findViewById(R.id.txtPriceInt);
            _priceInt.setText(String.format("%d.", intPart));
            TextView _priceDecimal = findViewById(R.id.txtPriceDecimal);
            _priceDecimal.setText(String.format("%02d", decimalPart));

            if (theProduct.getOriginalprice() > 0) {
                TextView _salePrice = findViewById(R.id.txtOriginalPrice);
                _salePrice.setText(String.format("$%.2f", theProduct.getOriginalprice()));

                int saleRate = 100 - (int) (theProduct.getPrice() / theProduct.getOriginalprice() * 100);
                TextView _saleRate = findViewById(R.id.txtSaleRate);
                _saleRate.setText(String.format("(%d", saleRate) + "% off)");
            } else {
                findViewById(R.id.linSalePart).setVisibility(View.GONE);
            }
            BaseRatingBar _ratScore = findViewById(R.id.ratScore);
            _ratScore.setRating((float) theProduct.getRating());

            TextView _reviews = findViewById(R.id.txtReviews);
            _reviews.setText(String.format("%,d", theProduct.getReviews()));

            TextView _answers = findViewById(R.id.txtAnswers);
            _answers.setText(String.format("%,d", theProduct.getAnswers()));

            TextView _description = findViewById(R.id.txtDescription);
            _description.setText(theProduct.getDescription());

            if (theProduct.getManual().equals("no")) {
                findViewById(R.id.imgManual).setVisibility(View.GONE);
            }

            if (theProduct.getEbook().equals("no")) {
                findViewById(R.id.imgEbook).setVisibility(View.GONE);
            }

            if (theProduct.getVideo().equals("no")) {
                findViewById(R.id.imgVideo).setVisibility(View.GONE);
            }

            ArrayList<Product> childList = new ArrayList<>();
            if (theProduct.getParentproduct().equals("no")) {
                childList = theProduct.getChild();
            } else {
                childList = Common.getInstance().getProductFromAsin(theProduct.getParentproduct()).getChild();
            }
            if (childList.size() > 1) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerView = findViewById(R.id.recChild);
                recyclerView.setLayoutManager(layoutManager);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, childList, theProduct.getAsin());
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(childList.indexOf(theProduct));
            } else {
                findViewById(R.id.childPart).setVisibility(View.GONE);
            }
        }
        catch (Exception ex){
            Toast.makeText(getBaseContext(),"detail_init:"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
