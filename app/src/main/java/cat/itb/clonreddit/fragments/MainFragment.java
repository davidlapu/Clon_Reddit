package cat.itb.clonreddit.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.adapters.PostAdapter;
import cat.itb.clonreddit.models.Post;
import cat.itb.clonreddit.models.SubReddit;

public class MainFragment extends Fragment {
    private NavController navController;
    private Toolbar toolbar;
    //private ViewPager viewPager;
    //private TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        toolbar = v.findViewById(R.id.toolbar);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerViewMain);
        // viewPager = v.findViewById(R.id.viewPager);
        // tabLayout = v.findViewById(R.id.tabLayout);

        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbar);

        //tabLayout.setupWithViewPager(viewPager);
        setUpRecycler(recyclerView);

        return v;
    }

    private void setUpRecycler(RecyclerView recyclerView) {
        List<Post> postList = new ArrayList<>();

        SubReddit catsAreLiquid = new SubReddit();
        catsAreLiquid.setTitle("catsareliquid");
        SubReddit programmerHumor = new SubReddit();
        programmerHumor.setTitle("ProgrammerHumor");

        postList.add(new Post(programmerHumor, "jevoy6737", "8h", "It hurts", 20, R.drawable.post2, 113, 435));
        postList.add(new Post(catsAreLiquid, "LilDiomede", "3h", "Meowlk", 0, R.drawable.post3, 183, 6));

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new PostAdapter(postList, requireContext()));
    }
}