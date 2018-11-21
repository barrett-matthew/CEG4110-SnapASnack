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
    private boolean hasNextPage;
    private GalleryViewAdapter adapter;
    private int currentPageNumber;
    private RecyclerView recyclerView;
    private List<SeeFoodImage> galleryList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_view);

        recyclerView = (RecyclerView) findViewById(R.id.gallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        SeeFoodHTTPHandler.getInstance().fetchAllImages(new FetchAllImagesCallback() {
            @Override
            public void onSuccess(@NonNull List<SeeFoodImage> images, int currentPageNumber, boolean hasNextPage)
            {
                adapter = new GalleryViewAdapter(getApplicationContext(), images);

                setHasNextPage(hasNextPage);
                setCurrentPageNumber(currentPageNumber);
                setGalleryList(images);

                recyclerView.setAdapter(adapter);
                configureButtons();
            }

            @Override
            public void onFailure(@NonNull Throwable throwable)
            {

            }

            @Override
            public void onError(@NonNull String errorMessage)
            {

            }
        });
    }

    public void configureButtons()
    {
        Button backButton = (Button) findViewById(R.id.backtomainmenu);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        Button nextPage = (Button) findViewById(R.id.nextpage);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(hasNextPage)
                {
                    SeeFoodHTTPHandler.getInstance().fetchAllImages(currentPageNumber+1, new FetchAllImagesCallback()
                    {
                        @Override
                        public void onSuccess(@NonNull List<SeeFoodImage> images, int currentPageNumber, boolean hasNextPage)
                        {
                            //galleryList.addAll(images);

                            GalleryViewAdapter newAdapter = new GalleryViewAdapter(getApplicationContext(), images); //galleryList);

                            recyclerView.setAdapter(newAdapter);

                            setGalleryList(galleryList);
                            setHasNextPage(hasNextPage);
                            setCurrentPageNumber(currentPageNumber);
                        }

                        @Override
                        public void onFailure(@NonNull Throwable throwable)
                        {

                        }

                        @Override
                        public void onError(@NonNull String errorMessage)
                        {

                        }
                    });
                }
            }
        });


        Button previousPage = (Button) findViewById(R.id.prevpage);
        previousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // if current page is at 1, do not decrement page
                if(currentPageNumber > 1)
                {
                    SeeFoodHTTPHandler.getInstance().fetchAllImages(currentPageNumber-1, new FetchAllImagesCallback()
                    {
                        @Override
                        public void onSuccess(@NonNull List<SeeFoodImage> images, int currentPageNumber, boolean hasNextPage)
                        {
                            galleryList.addAll(images);

                            GalleryViewAdapter newAdapter = new GalleryViewAdapter(getApplicationContext(), images);//galleryList);

                            recyclerView.setAdapter(newAdapter);

                            setGalleryList(galleryList);
                            setHasNextPage(hasNextPage);
                            setCurrentPageNumber(currentPageNumber);
                        }

                        @Override
                        public void onFailure(@NonNull Throwable throwable)
                        {

                        }

                        @Override
                        public void onError(@NonNull String errorMessage)
                        {

                        }
                    });
                }
            }
        });


    }

    private void setHasNextPage(boolean hasNextPage) { this.hasNextPage = hasNextPage; }

    private void setCurrentPageNumber(int currentPageNumber) { this.currentPageNumber = currentPageNumber; }

    private void setGalleryList(List<SeeFoodImage> galleryList) { this.galleryList = galleryList; }
}
