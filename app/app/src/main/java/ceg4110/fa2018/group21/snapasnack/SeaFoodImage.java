package ceg4110.fa2018.group21.snapasnack;

import android.media.Image;

import java.util.ArrayList;

public class SeaFoodImage
{
    private Image image;  // image can be from camera or photo directory
    private ArrayList<String> comments;
    private ConfidenceRatingDisplay display;

    // image and confidence rating are set once AI is done analyzing
    // whereas comments are dynamic and can change (AKA add comments)
    public SeaFoodImage(Image image, ConfidenceRatingDisplay display)
    {
        this.image = image;
        this.display = display;
    }

    public void AddComment(String comment)
    {
        comments.add(comment);
    }

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public ArrayList<String> getComments()
    {
        return comments;
    }

    public void setComments(ArrayList<String> comments)
    {
        this.comments = comments;
    }

    public ConfidenceRatingDisplay getDisplay()
    {
        return display;
    }

    public void setDisplay(ConfidenceRatingDisplay display)
    {
        this.display = display;
    }
}
