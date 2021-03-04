package cat.itb.clonreddit.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.models.Post;
import cat.itb.clonreddit.utils.Formater;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private final List<Post> postList;
    private final Context context;

    public PostAdapter(List<Post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(postList.get(position));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView textViewSubreddit, textViewSubtitle, textViewAwards, textViewTitle,
            textViewUpVotes;
        private final MaterialButton commentButton;
        private final ShapeableImageView imageViewPost, imageViewSubreddit;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewSubreddit = itemView.findViewById(R.id.textViewSubredditNamePost);
            textViewSubtitle = itemView.findViewById(R.id.textViewSubtitlePost);
            textViewAwards = itemView.findViewById(R.id.textViewAwardsPost);
            textViewTitle = itemView.findViewById(R.id.textViewTitlePost);
            textViewUpVotes = itemView.findViewById(R.id.textViewUpVotePost);
            imageViewPost = itemView.findViewById(R.id.imageViewPost);
            imageViewSubreddit = itemView.findViewById(R.id.imageViewSubredditPost);
            commentButton = itemView.findViewById(R.id.commentButton);
        }

        public void bind(Post post) {
            textViewSubreddit.setText(String.format("r/%s", post.getSubReddit().getTitle()));
            textViewSubtitle.setText(context.getString(R.string.subtitle, post.getUser(), post.getTime()));
            textViewAwards.setText(context.getString(R.string.awards, post.getNumAwards()));
            textViewTitle.setText(post.getTitle());
            textViewUpVotes.setText(Formater.format(post.getUpVotes()));
            commentButton.setText(String.valueOf(post.getCommentsNum()));
            imageViewPost.setImageResource(post.getImageId());
            imageViewSubreddit.setImageResource(post.getSubReddit().getImageId());
        }


    }

}
