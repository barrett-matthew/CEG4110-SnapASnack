package ceg4110.fa2018.group21.snapasnack.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;

public class RetrieveSingleImageResponse {

    @SerializedName("image")
    @Expose
    private List<SeeFoodImage> image;

    public SeeFoodImage getImage() {
        return image.get(0);
    }

    public void setImage(SeeFoodImage image) {
        this.image.set(0, image);
    }

}
