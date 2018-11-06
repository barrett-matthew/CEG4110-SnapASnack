package ceg4110.fa2018.group21.snapasnack;


import android.content.Context;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

// GalleryView should be able to communicate with the API
// GalleryView should only be a view??
// Initialize this class and pop up this view when "Browse Community Uploads" is clicked in MainMenuView
// account for empty case
public class GalleryView extends View
{
    private Button mainMenuBtn;
    private ArrayList<SeaFoodImage> images;


    public GalleryView(Context context)
    {
        super(context);
        InitalizeImages();
        RetrieveImages(images);
    }

    public void InitalizeImages()
    {
        // communicate with swagger UI to create SeaFoodImage objects.

        // for every image in swagger:
        // {
        // SeaFoodImage completedImage = new SeaFoodImage(SwaggerUI.getImage, SwaggerUI.getConfidenceRating);
        // images.add(completedImage)
        // }
    }

    public void RetrieveImages(ArrayList<SeaFoodImage> completedImages)
    {

        for (int x = 0; x < completedImages.size(); x++)
        {
            // populate based on index
        }
    }

    // we need a method to handle events for the main menu. this will take the user back to the main menu screen

    // also need a method to handle when the user clicks on the image in the gallery. images should be able to be
    // clicked on and handled accordingly, should be able to add comments as well


    // maybe we can import libraries instead of implementing from scratch?
    // https://github.com/mzelzoghbi/ZGallery
    // https://github.com/yanzhenjie/album




    // implementing a gallery view from scratch:
    // https://www.androidauthority.com/how-to-build-an-image-gallery-app-718976/

}
