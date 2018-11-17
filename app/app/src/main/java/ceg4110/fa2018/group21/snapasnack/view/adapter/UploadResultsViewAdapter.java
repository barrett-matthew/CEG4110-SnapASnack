package ceg4110.fa2018.group21.snapasnack.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.model.seefood.PlaceHolderImage;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class UploadResultsViewAdapter extends RecyclerView.Adapter<UploadResultsViewAdapter.ViewHolder>
{
    // TODO: Switch to SeeFoodImage class
    private ArrayList<PlaceHolderImage> resultList;
    private Context context;

    // TODO: Switch to SeeFoodImage class
    public UploadResultsViewAdapter(Context context, ArrayList<PlaceHolderImage> resultList)
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
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.name.setText(resultList.get(i).getTitle());
        viewHolder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.image.setImageResource(resultList.get(i).getImg());


        viewHolder.confidenceRating.setPointStartColor(Color.RED);
        viewHolder.confidenceRating.setPointEndColor(Color.RED);
        viewHolder.confidenceRating.setPointSize(30);
        viewHolder.confidenceRating.setStartAngle(135);
        viewHolder.confidenceRating.setStrokeCap("ROUND");
        viewHolder.confidenceRating.setStrokeColor(Color.GRAY);
        //viewHolder.confidenceRating.setStrokeWidth();
        viewHolder.confidenceRating.setStartValue(0);
        viewHolder.confidenceRating.setEndValue(1000);
        viewHolder.confidenceRating.setSweepAngle(270);


       // viewHolder.confidenceRatingText.setText("This is not food!");
       // viewHolder.itemName.setText(resultList.get(i).getTitle());
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
        CustomGauge confidenceRating;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            //itemName = itemView.findViewById(R.id.itemName);
            //confidenceRatingText = itemView.findViewById(R.id.confidenceRatingText);
            image = itemView.findViewById(R.id.seefoodResult);
            name = itemView.findViewById(R.id.name);
            confidenceRating = itemView.findViewById(R.id.gauge2);

        }
    }

}
