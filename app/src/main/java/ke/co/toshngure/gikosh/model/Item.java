package ke.co.toshngure.gikosh.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Anthony Ngure on 31/12/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class Item implements Parcelable {

    private long id;
    private double price;
    private String name;
    private String description;

    public Item() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeDouble(this.price);
        dest.writeString(this.name);
        dest.writeString(this.description);
    }

    protected Item(Parcel in) {
        this.id = in.readLong();
        this.price = in.readDouble();
        this.name = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
