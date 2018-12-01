package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ntt.customgaugeview.library.GaugeView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.Random;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodAPI;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;

public class ResultView extends AppCompatActivity {

    private Button toCommentView;

    // retrieve id here and display it
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_view);
        retrieveAndSetup();
    }

    private void retrieveAndSetup() {
        if (getIntent().hasExtra("SeeFoodResult")) {
            SeeFoodImage viewThis = (SeeFoodImage) getIntent().getSerializableExtra("SeeFoodResult");
            setImage(viewThis);
            setGauge(viewThis);
            setButton(viewThis);
        }
    }

    private void setImage(final SeeFoodImage image) {
        ImageView imageView = findViewById(R.id.result_comment_image);
        Picasso.get().load(SeeFoodAPI.BASE_URL + image.getImageLocation()).into(imageView);
    }

    private void setGauge(final SeeFoodImage image) {
        final GaugeView gaugeView = findViewById(R.id.result_comment_gauge);

        gaugeView.setShowRangeValues(false);


        final Random random = new Random();

        // The timer will allow the gauge to fluctuate between values before setting on a final target value
        final CountDownTimer timer = new CountDownTimer(4000, 2) {
            @Override
            public void onTick(long millisUntilFinished) {
                gaugeView.setTargetValue(random.nextInt(100));
            }

            @Override
            public void onFinish() {
                float result = image.getHasFood() - image.getNotFood();

                // calculation to set gauge based on SeeFood AI results (calculating percentage using hasFood and notFood)
                // TODO: BUG-- why does app crash if I set the target value to 100??
                if (result > 5) {
                    gaugeView.setTargetValue(99);
                } else if (result < -3) {
                    gaugeView.setTargetValue(0);
                } else {
                    gaugeView.setTargetValue(((result + 3) / 8) * 100);
                }

            }
        };

        timer.start();
    }

    private void setButton(final SeeFoodImage image) {
        toCommentView = findViewById(R.id.toCommentView);
        toCommentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // Passing the SeeFoodComment List to CommentView
                Intent intent = new Intent(ResultView.this, CommentView.class);
                intent.putExtra("SeeFoodComments", (Serializable) image.getComments());
                startActivity(intent);
            }
        });


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
}
