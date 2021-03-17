package cat.itb.clonreddit.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cat.itb.clonreddit.R;


public class SubRedditListFragment extends Fragment {
    private ImageView closeBTN;
    private NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sub_reddit_list, container, false);
        closeBTN = v.findViewById(R.id.closeBTN);

        closeBTN.setOnClickListener(this::toBackScreen);
        // Inflate the layout for this fragment
        return v;
    }

    private void toBackScreen(View view) {
        navController.popBackStack();
    }
}