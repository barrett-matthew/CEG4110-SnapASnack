package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    private String currentOrderBy, newOrderBy;

    private ArrayList<String> orderByValues;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_view);

        recyclerView = (RecyclerView) findViewById(R.id.gallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        currentOrderBy = getResources().getString(R.string.filterBy_datePosted);
        //currentOrderDirection = SeeFoodAPI.FETCH_DIR_DESC;

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

        orderByValues = new ArrayList<>();
        orderByValues.addAll(Arrays.asList(getResources().getStringArray(R.array.filterBy)));
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
                            SeeFoodAPI.FETCH_ORDER_BY_DATE,
                            SeeFoodAPI.FETCH_DIR_DESC,
                            new FetchImagesByPageNumberCallback()
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
                            updatePrevNextButtons();
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
                    SeeFoodHTTPHandler.getInstance().fetchImagesByPageNumber(currentPageNumber-1,
                            SeeFoodAPI.FETCH_ORDER_BY_DATE,
                            SeeFoodAPI.FETCH_DIR_DESC,
                            new FetchImagesByPageNumberCallback()
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
                            updatePrevNextButtons();
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

        // Update prev/next buttons with the initial configureButtons() call
        updatePrevNextButtons();

        // OnClick logic implemented in showFilterDialog()
        ImageButton filterButton = (ImageButton) findViewById(R.id.filterButton);
    }

    public void showFilterDialog (View view) {
        // Determine what the current index should be
        int currentOrderingIndex = orderByValues.indexOf(currentOrderBy);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filter by:");
        builder.setSingleChoiceItems(R.array.filterBy, currentOrderingIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        newOrderBy = orderByValues.get(0);
                        break;
                    case 1:
                        newOrderBy = orderByValues.get(1);
                        break;
                    case 2:
                        newOrderBy = orderByValues.get(2);
                        break;
                }
            }
        });
        builder.setPositiveButton(R.string.filterdialog_filter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(orderByValues.contains(newOrderBy)) {
                    // If a different filter was not selected, just return
                    if(currentOrderBy.equals(newOrderBy)) {
                        return;
                    }
                    // Update the currentOrderBy global variable
                    currentOrderBy = newOrderBy;
                    // Restart at the beginning of the pages
                    currentPageNumber = 1;

                    if(currentOrderBy.equals(getResources().getString(R.string.filterBy_datePosted))) {
                        reloadFilterByDate();
                    }
                    else if(currentOrderBy.equals(getResources().getString(R.string.filterBy_confidenceRating))) {
                        reloadFilterByScore();
                    }
                    else if(currentOrderBy.equals(getResources().getString(R.string.filterBy_comments))) {
                        reloadFilterByComments();
                    }
                    else {
                        // TODO : Error handling?
                    }
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void updatePrevNextButtons() {
        Button nextPgBtn = (Button) findViewById(R.id.nextpage);
        Button prevPgBtn = (Button) findViewById(R.id.prevpage);
        if(!hasNextPage) {
            nextPgBtn.setEnabled(false);
        }
        else {
            nextPgBtn.setEnabled(true);
        }

        if(currentPageNumber > 1) {
            prevPgBtn.setEnabled(true);
        }
        else {
            prevPgBtn.setEnabled(false);
        }
    }

    private void reloadFilterByDate() {
        SeeFoodHTTPHandler
                .getInstance()
                .fetchImagesByPageNumber(1,
                        SeeFoodAPI.FETCH_ORDER_BY_DATE,
                        SeeFoodAPI.FETCH_DIR_DESC,
                        new FetchImagesByPageNumberCallback() {
                            @Override
                            public void onSuccess(@NonNull List<SeeFoodImage> images, int currentPageNumber, boolean hasNextPage) {
                                GalleryViewAdapter newAdapter = new GalleryViewAdapter(getApplicationContext(), images); //galleryList);

                                recyclerView.setAdapter(newAdapter);

                                setGalleryList(galleryList);
                                setHasNextPage(hasNextPage);
                                setCurrentPageNumber(currentPageNumber);
                                updatePrevNextButtons();
                            }

                            @Override
                            public void onFailure(@NonNull Throwable throwable) {

                            }

                            @Override
                            public void onError(@NonNull String errorMessage) {

                            }
                        });
    }

    private void reloadFilterByScore() {
        SeeFoodHTTPHandler
                .getInstance()
                .fetchImagesByPageNumber(1,
                        SeeFoodAPI.FETCH_ORDER_BY_SCORE,
                        SeeFoodAPI.FETCH_DIR_DESC,
                        new FetchImagesByPageNumberCallback() {
                            @Override
                            public void onSuccess(@NonNull List<SeeFoodImage> images, int currentPageNumber, boolean hasNextPage) {
                                GalleryViewAdapter newAdapter = new GalleryViewAdapter(getApplicationContext(), images); //galleryList);

                                recyclerView.setAdapter(newAdapter);

                                setGalleryList(galleryList);
                                setHasNextPage(hasNextPage);
                                setCurrentPageNumber(currentPageNumber);
                                updatePrevNextButtons();
                            }

                            @Override
                            public void onFailure(@NonNull Throwable throwable) {

                            }

                            @Override
                            public void onError(@NonNull String errorMessage) {

                            }
                        });
    }

    private void reloadFilterByComments() {
        SeeFoodHTTPHandler
                .getInstance()
                .fetchImagesByPageNumber(1,
                        SeeFoodAPI.FETCH_ORDER_BY_COMMENTS,
                        SeeFoodAPI.FETCH_DIR_DESC,
                        new FetchImagesByPageNumberCallback() {
                            @Override
                            public void onSuccess(@NonNull List<SeeFoodImage> images, int currentPageNumber, boolean hasNextPage) {
                                GalleryViewAdapter newAdapter = new GalleryViewAdapter(getApplicationContext(), images); //galleryList);

                                recyclerView.setAdapter(newAdapter);

                                setGalleryList(galleryList);
                                setHasNextPage(hasNextPage);
                                setCurrentPageNumber(currentPageNumber);
                                updatePrevNextButtons();
                            }

                            @Override
                            public void onFailure(@NonNull Throwable throwable) {

                            }

                            @Override
                            public void onError(@NonNull String errorMessage) {

                            }
                        });
    }

    private void setHasNextPage(boolean hasNextPage) { this.hasNextPage = hasNextPage; }

    private void setCurrentPageNumber(int currentPageNumber) { this.currentPageNumber = currentPageNumber; }

    private void setGalleryList(List<SeeFoodImage> galleryList) { this.galleryList = galleryList; }
}
