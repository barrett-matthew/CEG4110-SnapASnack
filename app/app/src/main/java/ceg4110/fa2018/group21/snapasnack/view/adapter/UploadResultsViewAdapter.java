package ceg4110.fa2018.group21.snapasnack.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ntt.customgaugeview.library.GaugeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodAPI;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;

public class UploadResultsViewAdapter extends RecyclerView.Adapter<UploadResultsViewAdapter.ViewHolder>
{
    // TODO: Switch to SeeFoodImage class
    private List<SeeFoodImage> resultList;
    private Context context;

    // TODO: Switch to SeeFoodImage class
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

    private void setConfidenceGauge(@NonNull final ViewHolder viewHolder, float hasFood, float notFood)
    {
        float total = hasFood + notFood;

        viewHolder.gaugeView.setShowRangeValues(false);

        final Random random = new Random();

        // The timer will allow the gauge to fluctuate between values before setting on a final target value
        final CountDownTimer timer = new CountDownTimer(10000, 2)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                viewHolder.gaugeView.setTargetValue(random.nextInt(100));
            }

            @Override
            public void onFinish()
            {
                // TODO: Find out calculations to set gauge based on SeeFood AI results (calculate percentage using hasFood and notFood)
                viewHolder.gaugeView.setTargetValue(20);
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
        TextView itemName;
        TextView confidenceRatingText;
        ImageView image;
        TextView name;
        GaugeView gaugeView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            //itemName = itemView.findViewById(R.id.itemName);
            //confidenceRatingText = itemView.findViewById(R.id.confidenceRatingText);
            image = itemView.findViewById(R.id.seefoodResult);
            name = itemView.findViewById(R.id.name);
            gaugeView = itemView.findViewById(R.id.gauge2);

        }
    }

}
