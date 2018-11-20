package ceg4110.fa2018.group21.snapasnack.http.callback;

import android.support.annotation.NonNull;

import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;

public interface FetchSingleImageCallback {
    void onSuccess(@NonNull SeeFoodImage image);
    void onFailure(@NonNull Throwable throwable);
    void onError(@NonNull String errorMessage);
}
