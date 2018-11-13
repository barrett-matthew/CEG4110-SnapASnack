package ceg4110.fa2018.group21.snapasnack;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaceHolderImage implements Parcelable
{
    private String title;
    private Integer img;

    public PlaceHolderImage(String title, Integer img)
    {
        this.title = title;
        this.img = img;
    }

    protected PlaceHolderImage(Parcel in) {
        title = in.readString();
        if (in.readByte() == 0) {
            img = null;
        } else {
            img = in.readInt();
        }
    }

    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public static final Creator<PlaceHolderImage> CREATOR = new Creator<PlaceHolderImage>() {
        @Override
        public PlaceHolderImage createFromParcel(Parcel in) {
            return new PlaceHolderImage(in);
        }

        @Override
        public PlaceHolderImage[] newArray(int size) {
            return new PlaceHolderImage[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        if (img == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(img);
        }
    }
}
