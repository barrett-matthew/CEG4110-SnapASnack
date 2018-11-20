package ceg4110.fa2018.group21.snapasnack.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodComment;

public class PostCommentToImageResponse {

    @SerializedName("comment")
    @Expose
    private SeeFoodComment comment;

    @SerializedName("message")
    @Expose
    private String message;

    public SeeFoodComment getComment() {
        return comment;
    }

    public void setComment(SeeFoodComment comment) {
        this.comment = comment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
