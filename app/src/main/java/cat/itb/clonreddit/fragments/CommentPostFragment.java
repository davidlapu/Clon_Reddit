package cat.itb.clonreddit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.paging.DatabasePagingOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.adapters.CommentAdapter;
import cat.itb.clonreddit.models.Comment;
import cat.itb.clonreddit.models.Post;
import cat.itb.clonreddit.models.SubReddit;
import cat.itb.clonreddit.models.User;
import cat.itb.clonreddit.utils.DBUtils;
import cat.itb.clonreddit.utils.Formater;

public class CommentPostFragment extends Fragment {
    private RecyclerView recycler;
    private CommentAdapter adapter;
    private Post post;
    private MaterialButton buttonCreateComment;
    private MaterialTextView textViewNameSubreddit;
    private NavController navController;
    private ShapeableImageView imageViewSubredditPostTopBar;
    private ImageView closeBTN;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comment_post, container, false);
        // Inflate the layout for this fragment
        recycler = v.findViewById(R.id.recyclerComments);
        buttonCreateComment= v.findViewById(R.id.addCommentButton);
        textViewNameSubreddit = v.findViewById(R.id.textViewNameSubreddit);
        imageViewSubredditPostTopBar = v.findViewById(R.id.imageViewSubredditPostTopBar);
        closeBTN = v.findViewById(R.id.closeBTN);
        closeBTN.setOnClickListener(this::closeButton);

        return v;
    }

    private void closeButton(View view) {
        navController.navigate(R.id.action_commentPostFragment_to_mainFragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        post = CommentPostFragmentArgs.fromBundle(getArguments()).getPost();
        bindPost();
        setUpRecycler();

        buttonCreateComment.setOnClickListener(v -> {
            CommentPostFragmentDirections.ActionCommentPostFragmentToCreateCommentFragment a =
            CommentPostFragmentDirections.actionCommentPostFragmentToCreateCommentFragment(post.getTitle(), post.getId());

            navController.navigate(a);
        });

        //createPlaceholderComments();
    }

    private void createPlaceholderComments() {
        for (int i = 0; i < post.getCommentsNum(); i++) {
            DBUtils.uploadComment(post.getId(), new Comment(post.getTitle(), new User("Redditor" + String.valueOf(i))));
        }
    }

    private void bindPost() {
        MaterialTextView textViewSubreddit, textViewSubtitle, textViewAwards,
                textViewTitle, textViewUpVotes, textViewText;
        MaterialButton commentButton;
        ShapeableImageView imageViewPost, imageViewSubreddit;

        View itemView = getView();

        textViewSubreddit = itemView.findViewById(R.id.textViewSubredditNamePost);
        textViewSubtitle = itemView.findViewById(R.id.textViewSubtitlePost);
        textViewAwards = itemView.findViewById(R.id.textViewAwardsPost);
        textViewTitle = itemView.findViewById(R.id.textViewTitlePost);
        textViewUpVotes = itemView.findViewById(R.id.textViewUpVotePost);
        imageViewPost = itemView.findViewById(R.id.imageViewPost);
        imageViewSubreddit = itemView.findViewById(R.id.imageViewSubredditPost);
        commentButton = itemView.findViewById(R.id.commentButton);
        textViewText = itemView.findViewById(R.id.textViewTextPost);

        Task<com.google.firebase.database.DataSnapshot> taskPost = DBUtils.getSubreddit(post.getSubRedditID());
        taskPost.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                SubReddit s = task.getResult().getValue(SubReddit.class);

                Picasso.with(getContext()).load(s.getImgUrl()).into(imageViewSubreddit);
                Picasso.with(getContext()).load(s.getImgUrl()).into(imageViewSubredditPostTopBar);

                String title = String.format("r/%s", s.getTitle());
                textViewSubreddit.setText(title);
                textViewNameSubreddit.setText(title);
            }
        });

        if (post.getText() == null) {
            Picasso.with(getContext()).load(post.getImgUrl()).into(imageViewPost);
        } else {
            textViewText.setText(post.getText());
        }

        textViewSubtitle.setText(getContext().getString(R.string.subtitle, post.getUser().getUserName(), post.getTime()));
        textViewAwards.setText(getContext().getString(R.string.awards, post.getNumAwards()));
        textViewTitle.setText(post.getTitle());
        textViewUpVotes.setText(Formater.format(post.getUpVotes()));
        commentButton.setText(String.valueOf(post.getCommentsNum()));
    }

    private void setUpRecycler() {
        Query baseQuery = DBUtils.getReferenceComment().child(post.getId());

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(30)
                .build();

        DatabasePagingOptions<Comment> options = new DatabasePagingOptions.Builder<Comment>()
                .setLifecycleOwner(this)
                .setQuery(baseQuery, config, Comment.class)
                .build();

        adapter = new CommentAdapter(options);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

        /*
                DBUtils.getReferencePost().child(post.getId()).child("commentsNum").get().addOnSuccessListener(
                dataSnapshot -> {
                    int n = dataSnapshot.getValue(int.class);

                    if (commentButton != null) commentButton.setText(String.valueOf(n));

                }
        );
        */
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}