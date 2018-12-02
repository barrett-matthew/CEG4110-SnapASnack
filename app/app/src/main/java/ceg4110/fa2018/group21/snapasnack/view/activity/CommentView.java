package ceg4110.fa2018.group21.snapasnack.view.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.http.SeeFoodHTTPHandler;
import ceg4110.fa2018.group21.snapasnack.http.callback.FetchSingleImageCallback;
import ceg4110.fa2018.group21.snapasnack.http.callback.PostCommentToImageCallback;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodComment;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;
import ceg4110.fa2018.group21.snapasnack.view.adapter.CommentsAdapter;

public class CommentView extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private List<SeeFoodComment> commentList;
    private int SeeFoodID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_view);

        SetupActivity();

        LinearLayoutManager layoutManger = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.commentView);
        recyclerView.setLayoutManager(layoutManger);
        recyclerView.setHasFixedSize(true);

        // Getting SeeFoodID & commentList from calling the intent

        SeeFoodID = getIntent().getIntExtra("SeeFoodID", 0);
        
        commentList = (List<SeeFoodComment>) getIntent().getSerializableExtra("SeeFoodComments");
        CommentsAdapter adapter = new CommentsAdapter(getApplicationContext(), commentList);
        recyclerView.setAdapter(adapter);

        //TODO: why won't fetch single image retrieve the correct SeeFoodImage object?
//        SeeFoodHTTPHandler.getInstance().fetchSingleImage(SeeFoodID, new FetchSingleImageCallback() {
//            @Override
//            public void onSuccess(@NonNull SeeFoodImage image)
//            {
//                commentList = image.getComments();
//                CommentsAdapter adapter = new CommentsAdapter(getApplicationContext(), commentList);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(@NonNull Throwable throwable)
//            {
//
//            }
//
//            @Override
//            public void onError(@NonNull String errorMessage)
//            {
//
//            }
//        });
    }

    private void SetupActivity()
    {
        Toolbar toolbar = findViewById(R.id.commentToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Comments");
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
                                                 @Override
                                                 public void onClick(View view)
                                                 { finish();
                                                 }
                                             }
        );

        final EditText addAComment = findViewById(R.id.addCommentTF);
        ImageView profilePicture = findViewById(R.id.profilePicture);
        TextView postButton = findViewById(R.id.postButton);

        Picasso.get().load("http://bbcpersian7.com/images/transparent-people-png-clipart-1.jpg").into(profilePicture);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addAComment.getText().toString().equals(""))
                {
                    Toast.makeText(CommentView.this, "Fill in a comment!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    addComment(addAComment.getText().toString());
                    addAComment.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    addAComment.setText("");
                }
            }
        });

    }

    private void addComment(String comment)
    {
        SeeFoodHTTPHandler.getInstance().postCommentToImage(SeeFoodID, comment, new PostCommentToImageCallback()
        {
            @Override
            public void onSuccess(@NonNull SeeFoodComment comments)
            {
                commentList.add(comments);
                CommentsAdapter adapter = new CommentsAdapter(getApplicationContext(), commentList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Throwable throwable)
            {
            }

            @Override
            public void onError(@NonNull String errorMessage)
            {
            }
        });
    }
}
