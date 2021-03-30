package cat.itb.clonreddit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.paging.DatabasePagingOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.Query;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.adapters.CommentAdapter;
import cat.itb.clonreddit.models.Comment;
import cat.itb.clonreddit.models.Post;
import cat.itb.clonreddit.utils.DBUtils;

public class CommentPostFragment extends Fragment {
    private RecyclerView recycler;
    private CommentAdapter adapter;
    private Post post;
    private MaterialButton buttonCreateComment;
    private NavController navController;

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
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        post = CommentPostFragmentArgs.fromBundle(getArguments()).getPost();
        setUpRecycler();
        buttonCreateComment.setOnClickListener(v -> {
            CommentPostFragmentDirections.ActionCommentPostFragmentToCreateCommentFragment a =
            CommentPostFragmentDirections.actionCommentPostFragmentToCreateCommentFragment(post.getTitle(), post.getId());

            navController.navigate(a);
        });
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
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}