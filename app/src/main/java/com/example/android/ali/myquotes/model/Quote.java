package com.example.android.ali.myquotes.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

public class Quote implements Parcelable {
    private String id;
    private int page;
    private String text;
    private String remark;
    private String color;

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getColor(){
        return color;
    }

    public Quote() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(page);
        dest.writeString(text);
        dest.writeString(remark);
    }

    protected Quote(Parcel in) {
        id = in.readString();
        page = in.readInt();
        text = in.readString();
        remark = in.readString();
    }

    public static final Creator<Quote> CREATOR = new Creator<Quote>() {
        @Override
        public Quote createFromParcel(Parcel in) {
            Quote quote = new Quote();
            quote.id = in.readString();
            quote.page = in.readInt();
            quote.text = in.readString();
            quote.remark = in.readString();

            return quote;
        }

        @Override
        public Quote[] newArray(int size) {
            return new Quote[size];
        }
    };
}
