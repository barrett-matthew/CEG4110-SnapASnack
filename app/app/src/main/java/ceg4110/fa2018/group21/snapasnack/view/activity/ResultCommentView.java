package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodAPI;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodHTTPHandler;
import ceg4110.fa2018.group21.snapasnack.http.callback.FetchAllImagesCallback;
import ceg4110.fa2018.group21.snapasnack.http.callback.FetchSingleImageCallback;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;
import ceg4110.fa2018.group21.snapasnack.view.adapter.GalleryViewAdapter;

public class ResultCommentView extends AppCompatActivity {


    // retrieve id here and display it along with a comment view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_comment_view);
        retrieveAndSetup();
    }

    private void retrieveAndSetup()
    {
        if(getIntent().hasExtra("SeeFoodResult"))
        {
//            int seeFoodID = getIntent().getIntExtra("SeeFoodResultID", 0);

            SeeFoodImage viewThis = (SeeFoodImage) getIntent().getSerializableExtra("SeeFoodResult");

            setImage(viewThis);
            setGauge(viewThis);
            setComments(viewThis);

//            SeeFoodHTTPHandler.getInstance().fetchSingleImage(seeFoodID, new FetchSingleImageCallback()
//            {
//                @Override
//                public void onSuccess(@NonNull SeeFoodImage image)
//                {
//                    setImage(image);
//                    setGauge(image);
//                    setComments(image);
//                }
//
//                @Override
//                public void onFailure(@NonNull Throwable throwable)
//                {
//
//                }
//
//                @Override
//                public void onError(@NonNull String errorMessage)
//                {
//
//                }
//            });


        }
    }

    private void setImage(SeeFoodImage image)
    {
        ImageView imageView = findViewById(R.id.result_comment_image);
        Picasso.get().load(SeeFoodAPI.BASE_URL + image.getImageLocation()).into(imageView);
    }

    private void setGauge(SeeFoodImage image)
    {

    }
    private void setComments(SeeFoodImage image)
    {
        TextView textView = findViewById(R.id.result_comments);
        textView.setText("Testing");
    }

    // will need to request
    public void addComment()
    {

    }

}
