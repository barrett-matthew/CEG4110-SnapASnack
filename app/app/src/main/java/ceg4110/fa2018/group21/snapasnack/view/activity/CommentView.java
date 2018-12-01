package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodHTTPHandler;
import ceg4110.fa2018.group21.snapasnack.http.callback.PostCommentToImageCallback;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodComment;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;
import ceg4110.fa2018.group21.snapasnack.view.adapter.CommentsAdapter;
import ceg4110.fa2018.group21.snapasnack.view.adapter.GalleryViewAdapter;
import ceg4110.fa2018.group21.snapasnack.view.adapter.UploadResultsViewAdapter;

public class CommentView extends AppCompatActivity {

    private CommentsAdapter adapter;
    private RecyclerView recyclerView;
    private List<SeeFoodComment> commentList;
    private Button AddComment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_view);

        LinearLayoutManager layoutManger = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.commentView);
        recyclerView.setLayoutManager(layoutManger);
        recyclerView.setHasFixedSize(true);

        // Getting SeaFoodImageList from calling the intent
        final List<SeeFoodComment> cells = (List<SeeFoodComment>) getIntent().getSerializableExtra("SeeFoodComments");

        CommentsAdapter adapter = new CommentsAdapter(getApplicationContext(), cells);
        recyclerView.setAdapter(adapter);

        configureButtons();
    }


    public void configureButtons()
    {

        AddComment = findViewById(R.id.addComment);
        AddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AddComment(image);
                System.out.println("sike niqqa!");
            }
        });
    }

    public void AddComment(final SeeFoodImage image)
    {
        // TODO: Change test to textfield input
        SeeFoodHTTPHandler.getInstance().postCommentToImage(image.getId(), "Hi!", new PostCommentToImageCallback() {
            @Override
            public void onSuccess(@NonNull SeeFoodComment comments) {
               // seeFoodComments.add(comments);
              //  CommentsAdapter adapter = new CommentsAdapter(getApplicationContext(), seeFoodComments);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Throwable throwable) {

            }

            @Override
            public void onError(@NonNull String errorMessage) {

            }
        });
    }

}
