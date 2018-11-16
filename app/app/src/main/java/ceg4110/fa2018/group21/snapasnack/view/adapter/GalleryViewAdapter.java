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
import java.util.ArrayList;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.model.seefood.PlaceHolderImage;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class GalleryViewAdapter extends RecyclerView.Adapter<GalleryViewAdapter.ViewHolder>
{
   //TODO: switch to SeaFoodImage class
    private ArrayList<PlaceHolderImage> galleryList;
    private Context context;

    //TODO: switch to SeaFoodImage class
    public GalleryViewAdapter(Context context, ArrayList<PlaceHolderImage> galleryList)
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


    // TODO: Account for Comments and other SeaFood img components
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i)
    {
        viewHolder.title.setText(galleryList.get(i).getTitle());

        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageResource(galleryList.get(i).getImg());

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

        viewHolder.comments.setText("20 comments");


        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                //TODO: (???) open a new activity to show image and associated comments.
                Toast.makeText(context, "selected image", Toast.LENGTH_SHORT).show();

            }
        });
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
