package ceg4110.fa2018.group21.snapasnack;


import android.content.Context;
import android.view.View;
import android.widget.Button;

// GalleryView should be able to communicate with the API
// GalleryView should only be a view??
// Initialize this class and pop up this view when "Browse Community Uploads" is clicked in MainMenuView
public class GalleryView extends View {

    private Button mainMenuBtn;
    // private conf_rating List <ConfidenceRatingDisplay>



    public GalleryView(Context context) {
        super(context);
        System.out.println("HELP");
    }

    private void RetrieveImages()
    {
        // use this method to populate images into gallery
    }

    // we need a method for the to handle events for the main menu. this will take the user back to the main menu screen

    // also need a method to handle when the user clicks on the image in the gallery. images should be able to be
    // clicked on and handled accordingly


    // maybe we can import libraries instead of implementing from scratch?
    // https://github.com/mzelzoghbi/ZGallery
    // https://github.com/yanzhenjie/album




    // implementing a gallery view from scratch:
    // https://www.androidauthority.com/how-to-build-an-image-gallery-app-718976/

}
