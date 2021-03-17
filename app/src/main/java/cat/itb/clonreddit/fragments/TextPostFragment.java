package cat.itb.clonreddit.fragments;

import android.os.Bundle;

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


import cat.itb.clonreddit.R;

public class TextPostFragment extends Fragment {
    private NavController navController;
    private ImageView chooseCommunityArrowImageView;
    private TextView chooseCommunityTextView;



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

        chooseCommunityTextView.setOnClickListener(this::chooseCommunityFragment);
        chooseCommunityArrowImageView.setOnClickListener(this::chooseCommunityFragment);
        return v;
    }


    private void chooseCommunityFragment(View view) {
        navController.navigate(R.id.action_textPostFragment_to_subRedditListFragment);
    }
}