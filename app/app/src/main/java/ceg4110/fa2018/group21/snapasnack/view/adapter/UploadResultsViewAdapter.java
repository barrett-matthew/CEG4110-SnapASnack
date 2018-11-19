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
        // viewHolder.confidenceRatingText.setText("This is not food!");
        // viewHolder.itemName.setText(resultList.get(i).getTitle());

        viewHolder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.get().load(SeeFoodAPI.BASE_URL + resultList.get(i).getImageLocation()).into(viewHolder.image);

        setConfidenceGauge(viewHolder, resultList.get(i).getHasFood(), resultList.get(i).getNotFood());
    }

    private void setConfidenceGauge(@NonNull final ViewHolder viewHolder, final float hasFood, final float notFood)
    {
        viewHolder.gaugeView.setShowRangeValues(false);

        final Random random = new Random();

        // The timer will allow the gauge to fluctuate between values before setting on a final target value
        final CountDownTimer timer = new CountDownTimer(1000, 2)
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
                // TODO: BUG-- why does app crash if I set the target value to 100??
                if (result > 5)
                {
                    viewHolder.gaugeView.setTargetValue(99);
                }
                else if (result < -3)
                {
                    viewHolder.gaugeView.setTargetValue(0);
                }
                else
                {
                    viewHolder.gaugeView.setTargetValue((int) ((result + 3) / 8) * 100);
                }

            }
        };

        timer.start();
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView itemName;
        private TextView confidenceRatingText;
        private ImageView image;
        private TextView name;
        private GaugeView gaugeView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            //itemName = itemView.findViewById(R.id.itemName);
            //confidenceRatingText = itemView.findViewById(R.id.confidenceRatingText);
            image = itemView.findViewById(R.id.resultImg);
            name = itemView.findViewById(R.id.resultTitle);
            gaugeView = itemView.findViewById(R.id.resultGauge);

        }
    }

}
