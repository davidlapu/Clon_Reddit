package cat.itb.clonreddit.adapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.models.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private final List<Post> postList;

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
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
            textViewUpVotes, textViewComments;

        private final ShapeableImageView imageViewPost, imageViewSubreddit;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewSubreddit = itemView.findViewById(R.id.textViewSubredditNamePost);
            textViewSubtitle = itemView.findViewById(R.id.textViewSubtitlePost);
            textViewAwards = itemView.findViewById(R.id.textViewAwardsPost);
            textViewTitle = itemView.findViewById(R.id.textViewTitlePost);
            textViewUpVotes = itemView.findViewById(R.id.textViewUpVotePost);
            textViewComments = itemView.findViewById(R.id.textViewCommentCountPost);
            imageViewPost = itemView.findViewById(R.id.imageViewPost);
            imageViewSubreddit = itemView.findViewById(R.id.imageViewSubredditPost);
        }

        public void bind(Post post) {
            textViewSubreddit.setText(post.getSubReddit().getTitle());
            textViewSubtitle.setText(String.format("u/%s • %s • i.redd.it", post.getUser(), post.getTime()));
            textViewAwards.setText(Resources.getSystem().getText(R.string.awards, String.valueOf(post.getNumAwards())));
            textViewTitle.setText(post.getTitle());
            textViewUpVotes.setText(String.valueOf(post.getUpVotes()));
            textViewComments.setText(String.valueOf(post.getCommentsNum()));
            imageViewPost.setImageResource(post.getImageId());
        }


    }

}
