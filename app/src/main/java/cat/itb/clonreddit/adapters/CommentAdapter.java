package cat.itb.clonreddit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.paging.DatabasePagingOptions;
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;
import com.firebase.ui.database.paging.LoadingState;
import com.google.android.material.textview.MaterialTextView;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.models.Comment;

public class CommentAdapter extends FirebaseRecyclerPagingAdapter<Comment, CommentAdapter.ViewHolder> {

    public CommentAdapter(@NonNull DatabasePagingOptions<Comment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull Comment model) {
        viewHolder.bind(model);
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewUserName, commentTextView;
        private final MaterialTextView textViewUpVotePost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            commentTextView = itemView.findViewById(R.id.commentTextView);
            textViewUpVotePost = itemView.findViewById(R.id.textViewUpVotePost);
        }

        public void bind(Comment c) {
            textViewUserName.setText(c.getUser().getUserName());
            commentTextView.setText(c.getText());
            textViewUpVotePost.setText(String.valueOf(c.getUpVotes()));
        }
    }
}
