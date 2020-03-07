package com.patrick.limm.model;

import java.util.ArrayList;

public class Product {
    String asin,name,shortname,image,ebook, manual, video,amazon, description,parentproduct,thumnail,faq,reviewurl;
    double price,originalprice,rating;
    int id,reviews,answers;
    ArrayList<Product> mChild = new ArrayList<>();

    public Product(){

    }

//    public Product(String name, String image, int id, String asin, String description, double oprice, double price, double rating,
//                   int answer, int review,String amazon, String ebook,String manual, String video, String thumnail,String parentproduct,
//                   String reviewurl, String faq){
//        this.name=name;
//        this.image=image;
//        this.id=id;
//        this.asin=asin;
//        this.description=description;
//        this.originalprice=oprice;
//        this.price=price;
//        this.rating=rating;
//        this.answers=answer;
//        this.reviews=review;
//        this.amazon=amazon;
//        this.ebook=ebook;
//        this.manual=manual;
//        this.video=video;
//        this.thumnail=thumnail;
//        this.parentproduct=parentproduct;
//        this.reviewurl=reviewurl;
//        this.faq=faq;
//    }

    public String getName() {
        return name;
    }

    public String getShortname() {
        return shortname;
    }

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public String getAsin(){return asin;}

    public String getDescription() {
        return description;
    }

    public double getOriginalprice() {
        return originalprice;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public int getAnswers() {
        return answers;
    }

    public int getReviews() {
        return reviews;
    }

    public String getAmazon() {
        return amazon;
    }

    public String getEbook() {
        return ebook;
    }

    public String getManual() {
        return manual;
    }

    public String getVideo() {
        return video;
    }

    public String getThumnail() {
        return thumnail;
    }

    public String getParentproduct() {
        return parentproduct;
    }

    public String getFaq() {
        return faq;
    }

    public String getReviewurl() {
        return reviewurl;
    }

    public ArrayList<Product> getChild() {
        return mChild;
    }

    public void addChild(Product child){
        this.mChild.add(child);
    }
}
