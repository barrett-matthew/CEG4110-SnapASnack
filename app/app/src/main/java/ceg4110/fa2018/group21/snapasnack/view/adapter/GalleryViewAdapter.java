package ceg4110.fa2018.group21.snapasnack.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodAPI;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class GalleryViewAdapter extends RecyclerView.Adapter<GalleryViewAdapter.ViewHolder>
{
    private List<SeeFoodImage> galleryList;
    private Context context;

    //TODO: switch to SeaFoodImage class
    public GalleryViewAdapter(Context context, List <SeeFoodImage> galleryList)
    {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seefood_cell, viewGroup, false);
        return new ViewHolder(view);
    }

    // TODO: Account for comments
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i)
    {
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //viewHolder.title.setText(galleryList.get(i).getId());

        Picasso.get().load(SeeFoodAPI.BASE_URL + galleryList.get(i).getImageLocation()).into(viewHolder.img);

        setConfidenceGauge(viewHolder);

        //viewHolder.comments.setText(galleryList.get(i).getComments().size() + " comments");
        viewHolder.comments.setText("20 comments");

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                //TODO: (???) open a new activity to show full image and associated comments.
                // TODO: Pass the SeeFood List to the new activity
                Toast.makeText(context, "selected image", Toast.LENGTH_SHORT).show();

            }
        });
    }

    // TODO: Find out calculations to set gauge based on SeeFood AI results (has_food - not_food) range of -5 to 5
    private void setConfidenceGauge(@NonNull ViewHolder viewHolder)
    {
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

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout parentLayout;
        private TextView title;
        private ImageView img;
        private CustomGauge confidenceRating;
        private TextView comments;

        public ViewHolder(View view)
        {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
            confidenceRating = (CustomGauge) view.findViewById(R.id.gauge1);
            comments = (TextView) view.findViewById(R.id.comments);
            parentLayout = (LinearLayout) view.findViewById(R.id.parent_layout);
        }
    }
}
