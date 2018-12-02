package ceg4110.fa2018.group21.snapasnack.model.seefood;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SeeFoodComment implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("posted_at")
    @Expose
    private String postedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostedAt() { return postedAt; }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }
}
