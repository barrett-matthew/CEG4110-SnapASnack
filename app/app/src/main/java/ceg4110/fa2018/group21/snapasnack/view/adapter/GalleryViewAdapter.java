package ceg4110.fa2018.group21.snapasnack.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ntt.customgaugeview.library.GaugeView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodAPI;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;
import ceg4110.fa2018.group21.snapasnack.view.activity.ResultView;

public class GalleryViewAdapter extends RecyclerView.Adapter<GalleryViewAdapter.ViewHolder>
{
    private List<SeeFoodImage> galleryList;
    private Context context;

    public GalleryViewAdapter(Context context, List <SeeFoodImage> galleryList)
    {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seefood_gallery_cell, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i)
    {
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Picasso.get().load(SeeFoodAPI.BASE_URL + galleryList.get(i).getThumbnailLocation()).into(viewHolder.img);

        setConfidenceGauge(viewHolder, galleryList.get(i).getHasFood(), galleryList.get(i).getNotFood());

        // Set the number of comments for each image
        viewHolder.comments.setText(galleryList.get(i).getComments().size() + " comments");

        // Adds click listener to image-- the ResultView will pop up if clicked
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                SeeFoodImage passThisResult = galleryList.get(i);

                Intent intent = new Intent(context, ResultView.class);
                intent.putExtra("SeeFoodResult", (Serializable) passThisResult);
                context.startActivity(intent);
            }
        });
    }

    // Sets confidence gauge for each SeaFoodImage
    private void setConfidenceGauge(@NonNull final ViewHolder viewHolder, final float hasFood, final float notFood)
    {
        viewHolder.gaugeView.setShowRangeValues(false);

        float result = hasFood - notFood;

        // calculation to set gauge based on SeeFood AI results (calculating percentage using hasFood and notFood)
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
            viewHolder.gaugeView.setTargetValue(((result + 3) / 8) * 100);
        }

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout parentLayout;
        private ImageView img;
        private GaugeView gaugeView;
        private TextView comments;

        public ViewHolder(View view)
        {
            super(view);

            img = (ImageView) view.findViewById(R.id.galleryImg);
            gaugeView = (GaugeView) view.findViewById(R.id.galleryGauge);
            comments = (TextView) view.findViewById(R.id.galleryComments);
            parentLayout = (LinearLayout) view.findViewById(R.id.parent_layout);
        }
    }
}
