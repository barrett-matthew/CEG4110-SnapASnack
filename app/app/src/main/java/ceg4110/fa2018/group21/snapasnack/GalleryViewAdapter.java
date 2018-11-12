package ceg4110.fa2018.group21.snapasnack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class GalleryViewAdapter extends RecyclerView.Adapter<GalleryViewAdapter.ViewHolder> {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seafood_img, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(galleryList.get(i).getTitle());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageResource(galleryList.get(i).getImg());
        viewHolder.img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Toast.makeText(context, "Image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private ImageView img;

        public ViewHolder(View view)
        {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }
}
