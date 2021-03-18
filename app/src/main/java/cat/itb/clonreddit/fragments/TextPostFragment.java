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
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.models.SubReddit;

public class TextPostFragment extends Fragment {
    private NavController navController;
    private ImageView chooseCommunityArrowImageView;
    private ShapeableImageView imageViewSubredditPost;
    private TextView chooseCommunityTextView;
    private SubReddit subReddit;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_text_post, container, false);
        chooseCommunityTextView = v.findViewById(R.id.chooseSubredditTextView);
        chooseCommunityArrowImageView = v.findViewById(R.id.chooseSubredditArrow);
        imageViewSubredditPost = v.findViewById(R.id.imageViewSubredditPost);

        chooseCommunityTextView.setOnClickListener(this::chooseCommunityFragment);
        chooseCommunityArrowImageView.setOnClickListener(this::chooseCommunityFragment);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (!bundle.isEmpty()) {
            SubReddit s = TextPostFragmentArgs.fromBundle(bundle).getSubreddit();

            chooseCommunityTextView.setText(s.getTitle());
            Picasso.with(requireContext()).load(s.getImgUrl()).into(imageViewSubredditPost);
            subReddit = s;
        }

    }

    private void chooseCommunityFragment(View view) {
        TextPostFragmentDirections.ActionTextPostFragmentToSubRedditListFragment action =
                TextPostFragmentDirections.actionTextPostFragmentToSubRedditListFragment("txt");
        navController.navigate(action);
    }
}