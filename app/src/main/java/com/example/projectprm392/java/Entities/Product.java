package com.example.projectprm392.java.Entities;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

//public class Product implements Parcelable {
//    private String id;
//    private String name;
//    private String details;
//    private int quanlity;
//    private String search_image;
//    public Product() {
//    }
//
//    protected Product(Parcel in) {
//        id = in.readString();
//        name = in.readString();
//        details = in.readString();
//        quanlity = in.readInt();
//        search_image = in.readString();
//    }
//
//    public static final Creator<Product> CREATOR = new Creator<Product>() {
//        @Override
//        public Product createFromParcel(Parcel in) {
//            return new Product(in);
//        }
//
//        @Override
//        public Product[] newArray(int size) {
//            return new Product[size];
//        }
//    };
//
//    public String getSearch_image() {
//        return search_image;
//    }
//
//    public void setSearch_image(String search_image) {
//        this.search_image = search_image;
//    }
//
//    public Product(String id, String name, String details, int quanlity, String search_image) {
//        this.id = id;
//        this.name = name;
//        this.details = details;
//        this.quanlity = quanlity;
//        this.search_image = search_image;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDetails() {
//        return details;
//    }
//
//    public void setDetails(String details) {
//        this.details = details;
//    }
//
//    public int getQuanlity() {
//        return quanlity;
//    }
//
//    public void setQuanlity(int quanlity) {
//        this.quanlity = quanlity;
//    }
//
//    @Override
//    public String toString() {
//        return "Product{" +
//                "id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", details='" + details + '\'' +
//                ", quanlity=" + quanlity +
//                '}';
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(@NonNull Parcel parcel, int i) {
//        parcel.writeString(id);
//        parcel.writeString(name);
//        parcel.writeString(details);
//        parcel.writeInt(quanlity);
//        parcel.writeString(search_image);
//    }
//}

@SuppressLint("ParcelCreator")
public class Product implements Parcelable  {
    private int ProductID;
    private String ProductName;
    private String Description;
    private float Price;
    private int StockQuantity;
    private String ImageURL;

    public Product() {
    }

    public Product(int productID, String productName, String description, float price, int stockQuantity, String imageURL) {
        ProductID = productID;
        ProductName = productName;
        Description = description;
        Price = price;
        StockQuantity = stockQuantity;
        ImageURL = imageURL;
    }

    protected Product(Parcel in) {
        ProductID = in.readInt();
        ProductName = in.readString();
        Description = in.readString();
        Price = in.readFloat();
        StockQuantity = in.readInt();
        ImageURL = in.readString();
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getStockQuantity() {
        return StockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        StockQuantity = stockQuantity;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(ProductID);
        parcel.writeString(ProductName);
        parcel.writeString(Description);
        parcel.writeFloat(Price);
        parcel.writeInt(StockQuantity);
        parcel.writeString(ImageURL);
    }
    
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
