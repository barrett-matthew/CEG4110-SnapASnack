package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ceg4110.fa2018.group21.snapasnack.R;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Button toResultView = (Button) findViewById(R.id.uploadImage);

        toResultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UploadResultsView.class));
            }
        });

    }
}
