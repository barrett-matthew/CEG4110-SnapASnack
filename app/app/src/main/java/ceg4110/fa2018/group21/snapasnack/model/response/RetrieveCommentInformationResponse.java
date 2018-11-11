package ceg4110.fa2018.group21.snapasnack.model.response;

import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodComment;

public class RetrieveCommentInformationResponse {

    private SeeFoodComment comment;

    public SeeFoodComment getComment() {
        return comment;
    }

    public void setComment(SeeFoodComment comment) {
        this.comment = comment;
    }

}
