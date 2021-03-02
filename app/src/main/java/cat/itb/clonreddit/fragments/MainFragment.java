package cat.itb.clonreddit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.adapters.PostAdapter;
import cat.itb.clonreddit.models.Post;
import cat.itb.clonreddit.models.SubReddit;

public class MainFragment extends Fragment {
    private NavController navController;
    private Toolbar toolbar;
    private StorageReference mStorageRef;
    //private ViewPager viewPager;
    //private TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        toolbar = v.findViewById(R.id.toolbar);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerViewMain);
        DrawerLayout drawerLayout = v.findViewById(R.id.drawerLayout);
        NavigationView navigationView= v.findViewById(R.id.navigationView);
        // viewPager = v.findViewById(R.id.viewPager);
        // tabLayout = v.findViewById(R.id.tabLayout);

        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbar);

        //tabLayout.setupWithViewPager(viewPager);
        setUpRecycler(recyclerView);

        toolbar.setNavigationOnClickListener(v1 -> {
            drawerLayout.open();
        });

/*        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        }) { menuItem ->
                // Handle menu item selected
                menuItem.isChecked = true
            drawerLayout.close()
            true
        }*/

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