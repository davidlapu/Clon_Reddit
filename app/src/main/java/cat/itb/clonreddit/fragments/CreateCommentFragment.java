package cat.itb.clonreddit.fragments;

import android.net.Uri;
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
import com.google.firebase.auth.FirebaseUser;

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
        String photoUrl;

        String text = ediTextComment.getText().toString();
        if(!text.isEmpty()) {
            FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
            User currentUser = new User(u.getDisplayName());


            Uri photoUri = u.getPhotoUrl();

            if (photoUri == null) {
                photoUrl = "https://styles.redditmedia.com/t5_185atu/styles/profileIcon_snoo1154b8cb-c9d6-4d9c-8e7f-d75b041827ae-headshot.png?width=256&height=256&crop=256:256,smart&s=136f561bb38b698950406f42b7a1bb90b8c83d26";
            } else {
                photoUrl = photoUri.toString();
            }
            currentUser.setPictureUri(photoUrl);

            Comment comment = new Comment(text, currentUser);

            DBUtils.uploadComment(postId, comment);
            navController.popBackStack();
        } else {
            Toast.makeText(getContext(), R.string.all_required_fields, Toast.LENGTH_LONG).show();
        }
    }
}