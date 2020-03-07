package com.patrick.limm.activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.patrick.limm.R;
import com.patrick.limm.adapter.ProductAdapter;
import com.patrick.limm.model.Common;
import com.patrick.limm.model.Product;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    private ArrayList<Product> mProducts;
    private ArrayList<Product> mTempProducts;
    ProductAdapter mAdapter;
    ListView _lstProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        addEventListener();
        getProductData();
        showProducts();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                _lstProduct.smoothScrollToPosition(Common.getInstance().getCurPosition());
//            }
//        },100);
//
//    }

    private void addEventListener(){
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Products");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_backbutton);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getBaseContext(),HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

        findViewById(R.id.imgSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editSearch=(EditText)findViewById(R.id.editSearch);
                String pattern=editSearch.getText().toString().trim();
                searchProduct(pattern);
            }
        });

        findViewById(R.id.imgSearch_Close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTempProducts=new ArrayList<Product>(mProducts);
                findViewById(R.id.imgSearch_Close).setVisibility(View.INVISIBLE);
                EditText _pattern=findViewById(R.id.editSearch);
                _pattern.setText("");
                showProducts();
            }
        });

        _lstProduct = findViewById(R.id.lstProducts);
        _lstProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent intent = new Intent(getBaseContext(), ProductDetailActivity.class)
                            .putExtra("index", Common.getInstance().getIndex(mTempProducts.get(position).getId()));
                    startActivity(intent);
                }catch (Exception ex){
                    Toast.makeText(getBaseContext(),ex.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //toolbar============
    private void getProductData(){
        mProducts= Common.getInstance().getParentProducts();
        mTempProducts=new ArrayList<Product>(mProducts);
    }


    private void showProducts(){
        mAdapter = new ProductAdapter(this, mTempProducts);
        _lstProduct.setAdapter(mAdapter);
    }

    private void searchProduct(String pattern){
        mTempProducts.clear();
        if(pattern.isEmpty()){
            mTempProducts=new ArrayList<Product>(mProducts);
            findViewById(R.id.imgSearch_Close).setVisibility(View.INVISIBLE);
        }
        else{
            for(Product theProduct:mProducts){
                if(theProduct.getName().toLowerCase().contains(pattern.toLowerCase())){
                    mTempProducts.add(theProduct);
                }
            }
            findViewById(R.id.imgSearch_Close).setVisibility(View.VISIBLE);
        }
        showProducts();
    }
}
