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
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.adapters.SubredditAdapter;
import cat.itb.clonreddit.models.SubReddit;
import cat.itb.clonreddit.utils.ConexionBBDD;


public class SubRedditListFragment extends Fragment {
    private ImageView closeBTN;
    private NavController navController;
    private RecyclerView recyclerView;

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
        recyclerView = v.findViewById(R.id.recyclerViewSubrredit);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String dest = SubRedditListFragmentArgs.fromBundle(getArguments()).getOrigin();

        FirebaseRecyclerOptions<SubReddit> options = new FirebaseRecyclerOptions.Builder<SubReddit>()
                .setQuery(ConexionBBDD.getReferenceSubReddit(), SubReddit.class).build();

        adapter = new SubredditAdapter(options, requireContext(), dest);
        recyclerView.setAdapter(adapter);
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