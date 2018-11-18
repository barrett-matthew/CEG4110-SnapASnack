package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodHTTPHandler;
import ceg4110.fa2018.group21.snapasnack.http.callback.PostImagesCallback;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;
import gun0912.tedbottompicker.TedBottomPicker;

public class MainActivity extends AppCompatActivity {

    private boolean allPermissionsGranted;

    private ArrayList<Button> mainMenuButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the button listeners programmatically
        configureButtons();

        // Handle permissions through Dexter
        allPermissionsGranted = false;
        requestPermissionsFromUser();
    }

    public void configureButtons()
    {
        mainMenuButtons = new ArrayList<>();

        Button toGalleryView = (Button) findViewById(R.id.galleryview);
        toGalleryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GalleryView.class));
            }
        });
        mainMenuButtons.add(toGalleryView);

        Button uploadImagesButton = (Button) findViewById(R.id.uploadImagesButton);
        uploadImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImages();
            }
        });
        mainMenuButtons.add(uploadImagesButton);
    }

    private void requestPermissionsFromUser() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // Update our global permissions variable
                        allPermissionsGranted = report.areAllPermissionsGranted();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                })
                .check();
    }

    public void uploadImages() {
        // TODO : Clean this up, does not actually check permissions every time (permanently disabled)
        // Verify permissions and exit if the permissions are not given
        requestPermissionsFromUser();
        if(!allPermissionsGranted) {
            Toast.makeText(this, "Snap a' Snack cannot perform the requested " +
                    "operation without proper permissions", Toast.LENGTH_LONG);
            return;
        }

        // Disable buttons
        for(Button btn : mainMenuButtons) {
            btn.setEnabled(false);
        }

        // If correct permissions are given, continue
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

                                    // Passing the SeaFoodImage List to UploadResultsView
                                    Intent intent = new Intent(MainActivity.this, UploadResultsView.class);
                                    intent.putExtra("SeaFoodResults", (Serializable) images);
                                    startActivity(intent);

                                    // Enable the buttons again
                                    for(Button btn : mainMenuButtons) {
                                        btn.setEnabled(true);
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Throwable throwable) {
                                    // TODO : Handle failures and errors
                                    // Enable the buttons again
                                    for(Button btn : mainMenuButtons) {
                                        btn.setEnabled(true);
                                    }
                                }

                                @Override
                                public void onError(@NonNull String errorMessage) {
                                    // TODO : Handle failures and errors
                                    // Enable the buttons again
                                    for(Button btn : mainMenuButtons) {
                                        btn.setEnabled(true);
                                    }
                                }
                            });
                        } else {
                            // TODO: This is NOT fired when no images are selected...find another route
                            // Enable the buttons again
                            for(Button btn : mainMenuButtons) {
                                btn.setEnabled(true);
                            }
                        }
                    }
                })
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Submit to AI")
                .setEmptySelectionText("No Images Selected")
                .create();

        bottomSheetDialogFragment.show(getSupportFragmentManager());
    }

}
