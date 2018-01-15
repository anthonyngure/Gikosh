package ke.co.toshngure.gikosh.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Anthony Ngure on 14/01/2018.
 * Email : anthonyngure25@gmail.com.
 */

public class Media implements Parcelable {

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel source) {
            return new Media(source);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
    private long id;
    private String url;

    public Media() {
    }

    public Media(String url) {
        this.url = url;
    }

    protected Media(Parcel in) {
        this.id = in.readLong();
        this.url = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.url);
    }
}
