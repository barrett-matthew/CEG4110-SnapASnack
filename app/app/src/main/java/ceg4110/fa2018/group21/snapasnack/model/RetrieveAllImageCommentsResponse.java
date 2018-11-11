package ceg4110.fa2018.group21.snapasnack.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RetrieveAllImageCommentsResponse {

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
