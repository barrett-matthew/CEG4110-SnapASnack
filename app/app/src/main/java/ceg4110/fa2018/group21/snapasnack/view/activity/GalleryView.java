package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ceg4110.fa2018.group21.snapasnack.http.SeeFoodAPI;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodHTTPHandler;
import ceg4110.fa2018.group21.snapasnack.http.callback.FetchImagesByPageNumberCallback;
import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;
import ceg4110.fa2018.group21.snapasnack.view.adapter.GalleryViewAdapter;

public class GalleryView extends AppCompatActivity
{
    public static int ACTIVITY_IMAGE_RESULT_VIEW = 100;

    private boolean hasNextPage;
    private int currentPageNumber;
    private RecyclerView recyclerView;
    private List<SeeFoodImage> galleryList;
    private String currentOrderBy, newOrderBy, currentOrderDirection;

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

        orderByValues = new ArrayList<>();
        orderByValues.addAll(Arrays.asList(getResources().getStringArray(R.array.filterBy)));
        currentOrderBy = getResources().getString(R.string.filterBy_datePosted);
        currentOrderDirection = SeeFoodAPI.FETCH_DIR_DESC;

        String[] queryParams = getQueryParams();
        loadImages(1, queryParams[0], queryParams[1]);

        Toolbar toolbar = findViewById(R.id.resultToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Community Gallery");
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
                                                 @Override
                                                 public void onClick(View view)
                                                 { finish();
                                                 }
                                             }
        );

        configureButtons();
    }

    public void configureButtons()
    {
        Button nextPage = (Button) findViewById(R.id.nextpage);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(hasNextPage)
                {
                    String[] queryParams = getQueryParams();
                    loadImages(currentPageNumber+1, queryParams[0], queryParams[1]);
                }
            }
        });


        Button previousPage = (Button) findViewById(R.id.prevpage);
        previousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if current page is at 1, do not decrement page
                if (currentPageNumber > 1) {
                    String[] queryParams = getQueryParams();
                    loadImages(currentPageNumber-1, queryParams[0], queryParams[1]);
                }
            }
        });

        // OnClick logic implemented in showFilterDialog()
        ImageButton filterButton = (ImageButton) findViewById(R.id.filterButton);

        // "Checked" state == ascending
        // "Unchecked" state == descending
        ToggleButton orderDirToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        orderDirToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    currentOrderDirection = SeeFoodAPI.FETCH_DIR_ASC;
                } else {
                    currentOrderDirection = SeeFoodAPI.FETCH_DIR_DESC;
                }

                setCurrentPageNumber(1);
                String[] queryParams = getQueryParams();
                loadImages(currentPageNumber, queryParams[0], queryParams[1]);
            }
        });

        // Update prev/next buttons with the initial configureButtons() call
        updatePrevNextButtons();

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

                    // Get the parameters
                    String[] queryParams = getQueryParams();

                    // Update the page
                    loadImages(currentPageNumber, queryParams[0], queryParams[1]);
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

        TextView pageNumberView = (TextView) findViewById(R.id.pageNumberView);
        pageNumberView.setText("PG: " + String.valueOf(currentPageNumber));
    }

    private void loadImages(final int pageNumber, final String orderBy, final String orderDirection) {
        SeeFoodHTTPHandler
                .getInstance()
                .fetchImagesByPageNumber(pageNumber, orderBy, orderDirection,
                        new FetchImagesByPageNumberCallback() {
                            @Override
                            public void onSuccess(@NonNull List<SeeFoodImage> images, int currentPageNumber, boolean hasNextPage) {
                                // Create a new adapter with the query results for this page
                                GalleryViewAdapter newAdapter = new GalleryViewAdapter(getApplicationContext(), images);
                                recyclerView.setAdapter(newAdapter);

                                // Update globals
                                setGalleryList(galleryList);
                                setHasNextPage(hasNextPage);
                                setCurrentPageNumber(currentPageNumber);

                                // Refresh next/previous page buttons
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

    // Returns a pair of Strings to be used in querying the server
    private String[] getQueryParams() {
        // retVal[0] == orderBy
        // retVal[1] == orderDirection
        String[] retVal = new String[2];

        if(currentOrderBy.equals(getResources().getString(R.string.filterBy_datePosted))) {
            retVal[0] = SeeFoodAPI.FETCH_ORDER_BY_DATE;
        }
        else if(currentOrderBy.equals(getResources().getString(R.string.filterBy_confidenceRating))) {
            retVal[0] = SeeFoodAPI.FETCH_ORDER_BY_SCORE;
        }
        else if(currentOrderBy.equals(getResources().getString(R.string.filterBy_comments))) {
            retVal[0] = SeeFoodAPI.FETCH_ORDER_BY_COMMENTS;
        }

        // Get direction from the global. No conversion needed.
        retVal[1] = currentOrderDirection;

        return retVal;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_IMAGE_RESULT_VIEW) //check if the request code is the one you've sent
        {
            // This is mainly to update comment counts after the user comes back to the activity
            String[] queryParams = getQueryParams();
            loadImages(currentPageNumber, queryParams[0], queryParams[1]);
        }
    }

    private void setHasNextPage(boolean hasNextPage) { this.hasNextPage = hasNextPage; }

    private void setCurrentPageNumber(int currentPageNumber) { this.currentPageNumber = currentPageNumber; }

    private void setGalleryList(List<SeeFoodImage> galleryList) { this.galleryList = galleryList; }
}
