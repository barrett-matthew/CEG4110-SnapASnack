package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ntt.customgaugeview.library.GaugeView;
import com.squareup.picasso.Picasso;

import java.util.Random;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodAPI;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodHTTPHandler;
import ceg4110.fa2018.group21.snapasnack.http.callback.FetchAllImagesCallback;
import ceg4110.fa2018.group21.snapasnack.http.callback.FetchSingleImageCallback;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodComment;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;

public class ResultCommentView extends AppCompatActivity {


    // retrieve id here and display it along with a comment view
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_comment_view);
        retrieveAndSetup();
    }

    private void retrieveAndSetup()
    {
        if(getIntent().hasExtra("SeeFoodResult"))
        {
            SeeFoodImage viewThis = (SeeFoodImage) getIntent().getSerializableExtra("SeeFoodResult");

            setImage(viewThis);
            setGauge(viewThis);
            setComments(viewThis);

        }
    }

    private void setImage(final SeeFoodImage image)
    {
        ImageView imageView = findViewById(R.id.result_comment_image);
        Picasso.get().load(SeeFoodAPI.BASE_URL + image.getImageLocation()).into(imageView);
    }

    private void setGauge(final SeeFoodImage image)
    {
        final GaugeView gaugeView = findViewById(R.id.result_comment_gauge);

        gaugeView.setShowRangeValues(false);


        final Random random = new Random();

        // The timer will allow the gauge to fluctuate between values before setting on a final target value
        final CountDownTimer timer = new CountDownTimer(4000, 2)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                gaugeView.setTargetValue(random.nextInt(100));
            }

            @Override
            public void onFinish()
            {
                float result = image.getHasFood() - image.getNotFood();

                // calculation to set gauge based on SeeFood AI results (calculating percentage using hasFood and notFood)
                // TODO: BUG-- why does app crash if I set the target value to 100??
                if (result > 5)
                {
                    gaugeView.setTargetValue(99);
                }
                else if (result < -3)
                {
                    gaugeView.setTargetValue(0);
                }
                else
                {
                    gaugeView.setTargetValue(((result + 3) / 8) * 100);
                }

            }
        };

        timer.start();
    }

    private void setComments(SeeFoodImage image)
    {

        for(SeeFoodComment comment: image.getComments())
        {
            // pass the strings to an adapter to be added in a list
            // look at bottom sheet dialog
        }

        TextView textView = findViewById(R.id.result_comments);
        textView.setText("Comments go here");
    }

    // will need to request
    public void addComment()
    {

    }

    //TODO: maybe add swiping motions to change images? then use fetchSingleImage

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
