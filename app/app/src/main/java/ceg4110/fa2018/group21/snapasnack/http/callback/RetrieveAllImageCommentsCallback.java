package ceg4110.fa2018.group21.snapasnack.http.callback;

import android.support.annotation.NonNull;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodComment;

public interface RetrieveAllImageCommentsCallback {
    void onSuccess(@NonNull List<SeeFoodComment> comments);
    void onFailure(@NonNull Throwable throwable);
    void onError(@NonNull String errorMessage);
}
