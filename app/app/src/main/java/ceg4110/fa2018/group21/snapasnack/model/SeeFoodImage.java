package ceg4110.fa2018.group21.snapasnack.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeeFoodImage {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("has_food")
    @Expose
    private float hasFood;

    @SerializedName("not_food")
    @Expose
    private float notFood;

    @SerializedName("image_location")
    @Expose
    private String imageLocation;

    @SerializedName("thumbnail_location")
    @Expose
    private String thumbnailLocation;

    @SerializedName("posted_at")
    @Expose
    private String postedAt;

    @SerializedName("comments")
    @Expose
    private List<SeeFoodComment> comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getHasFood() {
        return hasFood;
    }

    public void setHasFood(float hasFood) {
        this.hasFood = hasFood;
    }

    public float getNotFood() {
        return notFood;
    }

    public void setNotFood(float notFood) {
        this.notFood = notFood;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getThumbnailLocation() {
        return thumbnailLocation;
    }

    public void setThumbnailLocation(String thumbnailLocation) {
        this.thumbnailLocation = thumbnailLocation;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public List<SeeFoodComment> getComments() {
        return comments;
    }

    public void setComments(List<SeeFoodComment> comments) {
        this.comments = comments;
    }
}
