package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodHTTPHandler;
import ceg4110.fa2018.group21.snapasnack.http.callback.PostImagesCallback;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;
import gun0912.tedbottompicker.TedBottomPicker;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the button listeners programmatically
        configureButtons();
    }

    public void configureButtons()
    {
        Button toGalleryView = (Button) findViewById(R.id.galleryview);
        toGalleryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GalleryView.class));
            }
        });

        Button uploadImagesButton = (Button) findViewById(R.id.uploadImagesButton);
        uploadImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImages();
            }
        });
    }

    public void uploadImages() {
        TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(MainActivity.this)
                .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(ArrayList<Uri> uriList) {
                        if(uriList.size() > 0) {
                            // Get a list of all filepaths from the uriList
                            ArrayList<String> filepaths = new ArrayList<>();
                            for(Uri uri : uriList) {
                                filepaths.add(uri.getPath());
                            }

                            // Submit the POST request and handle responses accordingly
                            SeeFoodHTTPHandler.getInstance().postImages(filepaths, new PostImagesCallback() {
                                @Override
                                public void onSuccess(@NonNull List<SeeFoodImage> images) {
                                    // TODO : Call Brian's ResultView activity here
                                    Log.v("POST", "Images posted: " + images.size());
                                }

                                @Override
                                public void onFailure(@NonNull Throwable throwable) {
                                    // TODO : Handle failures and errors
                                }

                                @Override
                                public void onError(@NonNull String errorMessage) {
                                    // TODO : Handle failures and errors
                                }
                            });
                        } else {
                            // TODO: Handle the case of no images being selected
                        }
                    }
                })
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
                .create();

        bottomSheetDialogFragment.show(getSupportFragmentManager());

    }

}
