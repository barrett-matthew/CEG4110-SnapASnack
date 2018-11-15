package ceg4110.fa2018.group21.snapasnack.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
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

import java.util.List;
import java.util.Random;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodAPI;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodComment;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seefood_cell, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i)
    {
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Picasso.get().load(SeeFoodAPI.BASE_URL + galleryList.get(i).getImageLocation()).into(viewHolder.img);

        setConfidenceGauge(viewHolder, galleryList.get(i).getHasFood(), galleryList.get(i).getNotFood());

        // Set the number of comments for each image
        viewHolder.comments.setText(galleryList.get(i).getComments().size() + " comments");

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                //TODO: (???) open a new activity to show full image and related information.
                Toast.makeText(context, "selected image", Toast.LENGTH_SHORT).show();


                // TODO: Pass this variable to the new activity and call "fetch single image"
                int id = galleryList.get(i).getId();

            }
        });
    }

    // TODO: Find out calculations to set gauge based on SeeFood AI results (has_food - not_food)
    private void setConfidenceGauge(@NonNull final ViewHolder viewHolder, final float hasFood, final float notFood)
    {

//        int food = receiveArgs.getInt("food");
//        int notFood = receiveArgs.getInt("not");
//        int total = receiveArgs.getInt("total");
//
//        int percentFood = (int) ((food / (double) total) * 100);

       float total = hasFood + notFood;

       //(int) ((hasFood / (hasFood+notFood)) * 100);

        viewHolder.gaugeView.setShowRangeValues(false);
        viewHolder.gaugeView.setTargetValue(99);//(int) (((hasFood) / (hasFood+notFood)) * 100)-55);

//        final Random random = new Random();
//
//        final CountDownTimer timer = new CountDownTimer(10000, 2)
//        {
//            @Override
//            public void onTick(long millisUntilFinished)
//            {
//                viewHolder.gaugeView.setTargetValue(random.nextInt(100));
//            }
//
//            @Override
//            public void onFinish()
//            {
//                // calculate using has_food - not_food
//                viewHolder.gaugeView.setTargetValue(20);
//            }
//        };
//
//        timer.start();
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
        private GaugeView gaugeView;
        private TextView comments;

        public ViewHolder(View view)
        {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
            gaugeView = (GaugeView) view.findViewById(R.id.gauge_view);
            comments = (TextView) view.findViewById(R.id.comments);
            parentLayout = (LinearLayout) view.findViewById(R.id.parent_layout);
        }
    }
}
