package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ntt.customgaugeview.library.GaugeView;
import com.squareup.picasso.Picasso;

import java.util.Random;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodAPI;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodHTTPHandler;
import ceg4110.fa2018.group21.snapasnack.http.callback.FetchSingleImageCallback;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;

public class ResultView extends AppCompatActivity implements GestureDetector.OnGestureListener
{

    public static final int SWIPE_THRESHOLD = 100;
    public static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private Button toCommentView;
    private TextView resultCommentText;
    private SeeFoodImage viewThis;
    private GestureDetector gestureDetector;
    private int SeeFoodID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_view);

        setupActivity();

        gestureDetector = new GestureDetector(this);
    }

    private void setupActivity()
    {
        Toolbar toolbar = findViewById(R.id.resultToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Results");
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
                                                 @Override
                                                 public void onClick(View view)
                                                 { finish();
                                                 }
                                             }
        );

        if (getIntent().hasExtra("SeeFoodResult"))
        {
            viewThis = (SeeFoodImage) getIntent().getSerializableExtra("SeeFoodResult");
            setImage(viewThis);
            setGauge(viewThis);
            setButton(viewThis);
            SeeFoodID = viewThis.getId();
        }
    }

    private void setImage(final SeeFoodImage image)
    {
        ImageView seeFoodImage = findViewById(R.id.result_comment_image);
        Picasso.get().load(SeeFoodAPI.BASE_URL + image.getImageLocation()).into(seeFoodImage);

        ImageView aiImage = findViewById(R.id.seeFoodAIPicture);
        aiImage.setImageResource(R.drawable.ic_ai_brain_light);
    }

    private void setGauge(final SeeFoodImage image)
    {
        final GaugeView gaugeView = findViewById(R.id.result_comment_gauge);
        resultCommentText = findViewById(R.id.resultCommentText);

        gaugeView.setShowRangeValues(false);

        final Random random = new Random();

        // The timer will allow the gauge to fluctuate between values before setting on a final target value
        final CountDownTimer timer = new CountDownTimer(4000, 2) {
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
                if (result > 5) {
                    gaugeView.setTargetValue(99);
                    resultCommentText.setText("AI says: This image definitely contains food!!! :-D");
                } else if (result < -3) {
                    gaugeView.setTargetValue(0);
                    resultCommentText.setText("AI says: This image is definitely not food! D-:");
                } else {
                    gaugeView.setTargetValue(((result + 3) / 8) * 100);
                    setIntelligenceDialog(((result + 3) / 8) * 100);
                }
            }
        };
        timer.start();
    }

    private void setIntelligenceDialog(float confidenceRating)
    {
        if(confidenceRating >= 90)
        {
            resultCommentText.setText("AI says: Definitely food! :-D");
        }
        else if (confidenceRating >= 60)
        {
            resultCommentText.setText("AI says: Probably food... :-)");
        }
        else if (confidenceRating >= 30)
        {
            resultCommentText.setText("AI says: Probably NOT food... :-(");
        }
        else
        {
            resultCommentText.setText("AI says: Definitely NOT food! :,-(");
        }
    }

    private void setButton(final SeeFoodImage image)
    {
        toCommentView = findViewById(R.id.toCommentView);
        toCommentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ResultView.this, CommentView.class);

                // Passing the SeeFoodID to CommentView
                intent.putExtra("SeeFoodID", image.getId());

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY)
    {

      float diffX = moveEvent.getX()-downEvent.getY();

          // right or left swipe
          if(Math.abs(diffX) > SWIPE_THRESHOLD & Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD)
          {
              if(diffX > 0)
              {
                  // swipe right
                  SeeFoodID = viewThis.getId() + 1;
              }
              else
              {
                  // swipe left
                  SeeFoodID = viewThis.getId() - 1;
              }

              SeeFoodHTTPHandler.getInstance().fetchSingleImage(SeeFoodID, new FetchSingleImageCallback()
            {
                @Override
                public void onSuccess(@NonNull SeeFoodImage image)
                {
                    SeeFoodID = image.getId();
                    setImage(image);
                    setGauge(image);
                    setButton(image);
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

              return true;

          }

        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }
}
