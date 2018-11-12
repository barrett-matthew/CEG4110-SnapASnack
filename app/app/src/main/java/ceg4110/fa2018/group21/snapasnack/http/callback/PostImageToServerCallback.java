package ceg4110.fa2018.group21.snapasnack.http.callback;

import android.support.annotation.NonNull;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;

public interface PostImageToServerCallback {
    void onSuccess(@NonNull List<SeeFoodImage> images);
    void onFailure(@NonNull Throwable throwable);
    void onError(@NonNull String errorMessage);
}
