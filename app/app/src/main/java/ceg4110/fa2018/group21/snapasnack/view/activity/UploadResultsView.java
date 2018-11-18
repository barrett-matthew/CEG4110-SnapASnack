package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;
import ceg4110.fa2018.group21.snapasnack.view.adapter.UploadResultsViewAdapter;

public class UploadResultsView extends AppCompatActivity {

    // TODO: We need to pass the List of SeeFoodImages to this class

    // PLACEHOLDER IMAGES
    private final String image_titles[] = {
            "Img1",
            "Img2",
            "Img3",
            "Img4",
            "Img5",
            "Img6",
            "Img7",
            "Img8"
    };

    // PLACEHOLDER IMAGES
    private final Integer image_ids[] = {
            /*R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8*/
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_results_view);

        LinearLayoutManager layoutManger = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.resultsView);
        recyclerView.setLayoutManager(layoutManger);
        recyclerView.setHasFixedSize(true);


        // TODO: Switch to SeeFoodImage class
        final ArrayList<SeeFoodImage> cells = prepareData();

        UploadResultsViewAdapter adapter = new UploadResultsViewAdapter(getApplicationContext(), cells);
        recyclerView.setAdapter(adapter);

        configureButtons();
    }


    // TODO: Switch to SeeFoodImage class
    public ArrayList<SeeFoodImage> prepareData()
    {
        // TODO: Switch to SeeFoodImage class
        ArrayList<SeeFoodImage> seeFoodImages = new ArrayList<>();

        for(int i = 0; i < image_titles.length; i++)
        {
            //PlaceHolderImage cell = new PlaceHolderImage(image_titles[i], image_ids[i]);
            //seeFoodImages.add(cell);
        }

        return seeFoodImages;
    }

    public void configureButtons()
    {
        Button backButton = (Button) findViewById(R.id.backtomainmenu2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        Button toGalleryView = (Button) findViewById(R.id.togalleryview);
        toGalleryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
                startActivity(new Intent(UploadResultsView.this, GalleryView.class));
            }
        });
    }


}
