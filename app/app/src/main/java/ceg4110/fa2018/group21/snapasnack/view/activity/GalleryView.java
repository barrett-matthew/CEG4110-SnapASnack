package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.http.SeeFoodHTTPHandler;
import ceg4110.fa2018.group21.snapasnack.http.callback.FetchAllImagesCallback;
import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;
import ceg4110.fa2018.group21.snapasnack.view.adapter.GalleryViewAdapter;

// TODO: account for empty case (later)
public class GalleryView extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_view);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        SeeFoodHTTPHandler.getInstance().fetchAllImages(new FetchAllImagesCallback() {
            @Override
            public void onSuccess(@NonNull List<SeeFoodImage> images) {
                GalleryViewAdapter adapter = new GalleryViewAdapter(getApplicationContext(), images);
                recyclerView.setAdapter(adapter);
                configureBackButton();
            }

            @Override
            public void onFailure(@NonNull Throwable throwable) {

            }

            @Override
            public void onError(@NonNull String errorMessage) {

            }
        });
    }

    public void configureBackButton()
    {
        Button backButton = (Button) findViewById(R.id.backtomainmenu);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

}
