package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.http.SeeFoodAPI;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodHTTPHandler;
import ceg4110.fa2018.group21.snapasnack.http.callback.FetchImagesByPageNumberCallback;
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

        SeeFoodHTTPHandler.getInstance().fetchImagesDefaultFirstPage(new FetchImagesByPageNumberCallback() {
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
                    SeeFoodHTTPHandler.getInstance().fetchImagesByPageNumber(currentPageNumber+1,
                            null,
                            null,
                            new FetchImagesByPageNumberCallback()
                    {
                        @Override
                        public void onSuccess(@NonNull List<SeeFoodImage> images, int currentPageNumber, boolean hasNextPage)
                        {
                            galleryList.addAll(images);

                            GalleryViewAdapter newAdapter = new GalleryViewAdapter(getApplicationContext(), galleryList);

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
                else
                {
                    System.out.println("there isn't a next page");
                  // there isn't a next page
                }
            }
        });
    }

    public void setHasNextPage(boolean hasNextPage) { this.hasNextPage = hasNextPage; }

    public void setCurrentPageNumber(int currentPageNumber) { this.currentPageNumber = currentPageNumber; }

    public void setGalleryList(List<SeeFoodImage> galleryList) { this.galleryList = galleryList; }
}
