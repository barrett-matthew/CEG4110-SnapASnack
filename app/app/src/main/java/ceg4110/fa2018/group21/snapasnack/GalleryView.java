package ceg4110.fa2018.group21.snapasnack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

// TODO: account for empty case (later)
// TODO: need to account for SeaFoodImage rather than placeholderimage
public class GalleryView extends AppCompatActivity
{
    // PLACEHOLDER TITLES
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
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_view);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        //TODO: Switch to SeaFoodImage
        final ArrayList<PlaceHolderImage> cells = prepareData();

        GalleryViewAdapter adapter = new GalleryViewAdapter(getApplicationContext(), cells);
        recyclerView.setAdapter(adapter);

        configureBackButton();
    }

    //TODO: Switch to SeaFoodImage
    private ArrayList<PlaceHolderImage> prepareData()
    {
        //TODO: Switch to SeaFoodImage
        ArrayList<PlaceHolderImage> seaFoodImages = new ArrayList<>();

        for(int i = 0; i < image_titles.length; i++)
        {
            PlaceHolderImage cell = new PlaceHolderImage(image_titles[i], image_ids[i]);
            seaFoodImages.add(cell);
        }

        return seaFoodImages;
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
