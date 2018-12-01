package ceg4110.fa2018.group21.snapasnack.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import ceg4110.fa2018.group21.snapasnack.R;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodComment;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder>
{
    private List <SeeFoodComment> commentsList;
    private Context context;

    public CommentsAdapter(Context context, List <SeeFoodComment> commentList)
    {
        this.commentsList = commentList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seefood_comment_cell, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i)
    {
//        viewHolder.id.setText(commentsList.get(i).getId());
        viewHolder.comment.setText(commentsList.get(i).getText());
        viewHolder.postedAt.setText(commentsList.get(i).getPostedAt());
    }

    @Override
    public int getItemCount()
    {
        return commentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView id;
        private TextView comment;
        private TextView postedAt;

        public ViewHolder(View view)
        {
            super(view);

            id = (TextView) view.findViewById(R.id.userID);
            comment = (TextView) view.findViewById(R.id.userComment);
            postedAt = (TextView) view.findViewById(R.id.userPostedAt);
        }
    }




}
