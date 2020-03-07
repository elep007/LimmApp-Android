package com.patrick.limm.model;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class Common {

    private String baseURL="http://The-work-kw.com/limm/";//"http://10.0.2.2/limm/";//
    private ArrayList<Product> mProducts=new ArrayList<>();
    private ArrayList<Book> mBooks=new ArrayList<>();
    private ArrayList<Video> mVideos=new ArrayList<>();
    private int curPosition=0;

    private static Common instance = new Common();

    public void Common(){

    }

    public static Common getInstance()
    {
        return instance;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public ArrayList<Product> getProducts() {
        return mProducts;
    }

    public ArrayList<Product> getParentProducts() {
        ArrayList<Product> temp=new ArrayList<>();
        for(Product theProduct:mProducts){
            if(theProduct.getParentproduct().equals("no")){
                temp.add(theProduct);
            }
        }
        return temp;
    }

    public void setmProducts(ArrayList<Product> mProducts) {
        this.mProducts = mProducts;
        //add child
        for(Product theProduct: mProducts){
            theProduct.addChild(theProduct);

            if(!theProduct.getParentproduct().equals("no")){
                Product parent=getProductFromAsin(theProduct.getParentproduct());
                if(parent!=null){
                    parent.addChild(theProduct);
                }
            }
        }
    }

    public Product getProductFromAsin(String asin){
        try {
            for(Product theProduct: mProducts){
                if(theProduct.getAsin().equals(asin)){
                    return theProduct;
                }
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public int getIndex(int id ){
        try{
        int index=-1;
            for(Product theProduct: mProducts){
                index++;
                if(theProduct.getId()==id){
                    return index;
                }
            }
            return 0;
        }catch (Exception ex){
            return 1;
        }
    }

    public ArrayList<Book> getBooks() {
        return mBooks;
    }

    public void setmBooks(ArrayList<Book> mBooks) {
        this.mBooks = mBooks;
    }

    public void showAlert(Context context, String title, String message){
        new android.app.AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("Close", null)
                .show();
    }

    public ArrayList<Video> getmVideos() {
        return mVideos;
    }

    public void setmVideos(ArrayList<Video> mVideos) {
        this.mVideos = mVideos;
    }

    public int getCurPosition() {
        return curPosition;
    }

    public void setCurPosition(int curPosition) {
        this.curPosition = curPosition;
    }
}
