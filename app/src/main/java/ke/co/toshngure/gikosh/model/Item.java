package ke.co.toshngure.gikosh.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Anthony Ngure on 31/12/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class Item implements Parcelable {

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
    private long id;
    private double price;
    private String name;
    private String description;
    private String condition;
    private Category category;
    private List<Media> media;
    /**
     * A # separated string
     */
    private String sizes;
    /**
     * A # separated string
     */
    private String colors;
    /**
     * A # separated string
     */
    private String brands;

    public Item() {
    }

    protected Item(Parcel in) {
        this.id = in.readLong();
        this.price = in.readDouble();
        this.name = in.readString();
        this.description = in.readString();
        this.condition = in.readString();
        this.category = in.readParcelable(Category.class.getClassLoader());
        this.media = in.createTypedArrayList(Media.CREATOR);
        this.sizes = in.readString();
        this.colors = in.readString();
        this.brands = in.readString();
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
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
        dest.writeString(this.condition);
        dest.writeParcelable(this.category, flags);
        dest.writeTypedList(this.media);
        dest.writeString(this.sizes);
        dest.writeString(this.colors);
        dest.writeString(this.brands);
    }
}
