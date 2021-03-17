package cat.itb.clonreddit.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.adapters.PostAdapter;
import cat.itb.clonreddit.adapters.SubredditAdapter;
import cat.itb.clonreddit.models.Post;
import cat.itb.clonreddit.models.SubReddit;
import cat.itb.clonreddit.utils.ConexionBBDD;


public class SubRedditListFragment extends Fragment {
    private ImageView closeBTN;
    private NavController navController;

    private SubredditAdapter adapter;
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
        RecyclerView recyclerView = v.findViewById(R.id.recyclerViewSubrredit);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        FirebaseRecyclerOptions<SubReddit> options = new FirebaseRecyclerOptions.Builder<SubReddit>()
                .setQuery(ConexionBBDD.getReferenceSubReddit(), SubReddit.class).build();
        adapter = new SubredditAdapter(options, requireContext());
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }

    private void toBackScreen(View view) {
        navController.popBackStack();
    }
}