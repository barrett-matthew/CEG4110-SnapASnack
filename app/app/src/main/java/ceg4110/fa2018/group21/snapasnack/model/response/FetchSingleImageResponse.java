package ceg4110.fa2018.group21.snapasnack.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;

public class FetchSingleImageResponse {

    @SerializedName("image")
    @Expose
    private SeeFoodImage image;

    public SeeFoodImage getImage() {
        return image;
    }

    public void setImage(SeeFoodImage image) {
        this.image = image;
    }

}
