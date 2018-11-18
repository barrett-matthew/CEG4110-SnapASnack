package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;
import ceg4110.fa2018.group21.snapasnack.view.adapter.UploadResultsViewAdapter;

public class UploadResultsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.upload_results_view);

        LinearLayoutManager layoutManger = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.resultsView);
        recyclerView.setLayoutManager(layoutManger);
        recyclerView.setHasFixedSize(true);

        // Getting SeaFoodImageList from calling the intent
        final List<SeeFoodImage> cells = (List<SeeFoodImage>) getIntent().getSerializableExtra("SeaFoodResults");

        UploadResultsViewAdapter adapter = new UploadResultsViewAdapter(getApplicationContext(), cells);
        recyclerView.setAdapter(adapter);

        configureButtons();
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
