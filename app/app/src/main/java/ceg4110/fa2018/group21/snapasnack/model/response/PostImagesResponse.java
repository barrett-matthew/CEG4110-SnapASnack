package ceg4110.fa2018.group21.snapasnack.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;

public class PostImagesResponse {

    @SerializedName("images")
    @Expose
    private List<SeeFoodImage> images;

    @SerializedName("message")
    @Expose
    private String message;

    public List<SeeFoodImage> getImages() {
        return images;
    }

    public void setImages(List<SeeFoodImage> images) {
        this.images = images;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
