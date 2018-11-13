package ceg4110.fa2018.group21.snapasnack.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodComment;

public class FetchAllCommentsOnImageResponse {

    @SerializedName("comments")
    @Expose
    private List<SeeFoodComment> comments;

    public List<SeeFoodComment> getComments() {
        return comments;
    }

    public void setComments(List<SeeFoodComment> comments) {
        this.comments = comments;
    }
}
