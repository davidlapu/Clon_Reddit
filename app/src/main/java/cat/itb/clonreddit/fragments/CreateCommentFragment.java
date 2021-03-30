package cat.itb.clonreddit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.models.Comment;
import cat.itb.clonreddit.models.User;
import cat.itb.clonreddit.utils.DBUtils;

public class CreateCommentFragment extends Fragment {
    private MaterialTextView textViewTitle, buttonPost;
    private EditText ediTextComment;
    private NavController navController;
    private String postId;

    public CreateCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_comment, container, false);

        textViewTitle = v.findViewById(R.id.textViewTitleComment);
        ediTextComment = v.findViewById(R.id.editTextTextComment);
        buttonPost = v.findViewById(R.id.buttonPostText);
        v.findViewById(R.id.closeBTN).setOnClickListener(v1 -> navController.popBackStack());

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (!bundle.isEmpty()) {
            CreateCommentFragmentArgs args = CreateCommentFragmentArgs.fromBundle(bundle);
            String title = args.getTitle();
            postId = args.getPostId();
            textViewTitle.setText(title);

            buttonPost.setOnClickListener(this::postClicked);
        }
    }

    private void postClicked(View view) {
        String text = ediTextComment.getText().toString();
        if(!text.isEmpty()) {
            User currentUser = new User(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            Comment comment = new Comment(text, currentUser);

            DBUtils.uploadComment(postId, comment);
            navController.popBackStack();
        } else {
            Toast.makeText(getContext(), R.string.all_required_fields, Toast.LENGTH_LONG).show();
        }
    }
}