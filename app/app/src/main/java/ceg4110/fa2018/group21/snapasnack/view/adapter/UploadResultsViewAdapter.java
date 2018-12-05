package ceg4110.fa2018.group21.snapasnack.view.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ntt.customgaugeview.library.GaugeView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodAPI;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;

public class UploadResultsViewAdapter extends RecyclerView.Adapter<UploadResultsViewAdapter.ViewHolder>
{
    private List<SeeFoodImage> resultList;
    private Context context;

    public UploadResultsViewAdapter(Context context, List<SeeFoodImage> resultList)
    {
        this.resultList = resultList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seefood_result_cell, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {
        viewHolder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.get().load(SeeFoodAPI.BASE_URL + resultList.get(i).getImageLocation()).into(viewHolder.image);
        viewHolder.aiImage.setImageResource(R.drawable.ic_ai_brain_light);
        setConfidenceGauge(viewHolder, resultList.get(i).getHasFood(), resultList.get(i).getNotFood());
    }

    private void setConfidenceGauge(@NonNull final ViewHolder viewHolder, final float hasFood, final float notFood)
    {
        viewHolder.gaugeView.setShowRangeValues(false);

        final Random random = new Random();

        // The timer will allow the gauge to fluctuate between values before setting on a final target value
        final CountDownTimer timer = new CountDownTimer(4000, 2)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                viewHolder.gaugeView.setTargetValue(random.nextInt(100));
            }

            @Override
            public void onFinish()
            {
                float result = hasFood - notFood;

                // calculation to set gauge based on SeeFood AI results (calculating percentage using hasFood and notFood)
                if (result > 5)
                {
                    viewHolder.gaugeView.setTargetValue(99);
                    viewHolder.confidenceRatingText.setText("AI says: Eat up, this is definitely food!!! =D");
                }
                else if (result < -3)
                {
                    viewHolder.gaugeView.setTargetValue(0);
                    viewHolder.confidenceRatingText.setText("AI says: This image is totally not food! D=");
                }
                else
                {
                    viewHolder.gaugeView.setTargetValue(((result + 3) / 8) * 100);
                    setIntelligenceDialog((((result + 3) / 8) * 100), viewHolder);
                }

            }
        };

        timer.start();
    }

    private void setIntelligenceDialog(float confidenceRating, ViewHolder viewHolder)
    {
        if(confidenceRating >= 90)
        {
            viewHolder.confidenceRatingText.setText("AI says: Definitely food! =D");
        }
        else if (confidenceRating >= 70)
        {
            viewHolder.confidenceRatingText.setText("AI says: Most likely food! =)");
        }
        else if (confidenceRating >= 50)
        {
            viewHolder.confidenceRatingText.setText("AI says: Probably food... =P");
        }
        else if (confidenceRating >= 30)
        {
            viewHolder.confidenceRatingText.setText("AI says: Probably NOT food... =(");
        }
        else
        {
            viewHolder.confidenceRatingText.setText("AI says: Definitely NOT food! =((");
        }
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView aiImage, image;
        private TextView confidenceRatingText;
        private GaugeView gaugeView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            image = itemView.findViewById(R.id.resultImg);
            aiImage = itemView.findViewById(R.id.seeFoodAIPictureCell);
            confidenceRatingText = itemView.findViewById(R.id.resultCommentTextCell);
            gaugeView = itemView.findViewById(R.id.resultGauge);

        }
    }

}
