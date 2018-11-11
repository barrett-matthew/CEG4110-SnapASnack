package ceg4110.fa2018.group21.snapasnack.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;

public class RetrieveAllImagesResponse {

    @SerializedName("has_next")
    @Expose
    private boolean hasNext;

    @SerializedName("has_prev")
    @Expose
    private boolean hasPrevious;

    @SerializedName("images")
    @Expose
    private List<SeeFoodImage> images;

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("per_page")
    @Expose
    private int perPage;

    @SerializedName("total")
    @Expose
    private int total;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public List<SeeFoodImage> getImages() {
        return images;
    }

    public void setImages(List<SeeFoodImage> images) {
        this.images = images;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
