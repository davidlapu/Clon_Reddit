package cat.itb.clonreddit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.models.Post;
import cat.itb.clonreddit.models.SubReddit;
import cat.itb.clonreddit.utils.DBUtils;
import cat.itb.clonreddit.utils.Formater;

public class PostAdapter extends FirebaseRecyclerAdapter<Post, PostAdapter.PostViewHolder> {
    private final Context context;


    public PostAdapter(@NonNull FirebaseRecyclerOptions<Post> options, Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);

        return new PostViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post model) {
        holder.bind(model);
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView textViewSubreddit, textViewSubtitle, textViewAwards,
                textViewTitle, textViewUpVotes;
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

        private void bind(Post post) {
            Task<com.google.firebase.database.DataSnapshot> taskPost = DBUtils.getSubreddit(post.getSubRedditID());
            taskPost.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    SubReddit s = task.getResult().getValue(SubReddit.class);

                    Picasso.with(context).load(s.getImgUrl()).into(imageViewSubreddit);
                    textViewSubreddit.setText(String.format("r/%s", s.getTitle()));
                }
            });

            Picasso.with(context).load(post.getImgUrl()).into(imageViewPost);

            //textViewSubreddit.setText(String.format("r/%s", post.getSubReddit().getTitle()));
            textViewSubtitle.setText(context.getString(R.string.subtitle, post.getUser().getUserName(), post.getTime()));
            textViewAwards.setText(context.getString(R.string.awards, post.getNumAwards()));
            textViewTitle.setText(post.getTitle());
            textViewUpVotes.setText(Formater.format(post.getUpVotes()));
            commentButton.setText(String.valueOf(post.getCommentsNum()));
            //imageViewPost.setImageResource(post.getImageId());
           // imageViewSubreddit.setImageResource(post.getSubReddit().getImageId());
        }


    }

}
