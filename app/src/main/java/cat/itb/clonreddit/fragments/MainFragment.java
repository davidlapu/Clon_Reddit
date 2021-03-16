package cat.itb.clonreddit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    private BottomNavigationView bottomNavigationView;
    private SearchView searchView;
    private DrawerLayout drawerLayout;
    private View contents;
    private ImageView imageContents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        toolbar = v.findViewById(R.id.toolbar);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerViewMain);
        drawerLayout = v.findViewById(R.id.drawerLayout);
        bottomNavigationView = v.findViewById(R.id.bottomNavigation);
        searchView = v.findViewById(R.id.searchView);
        contents = v.findViewById(R.id.mainFragmentContents);
        imageContents = v.findViewById(R.id.imageContent);


        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        setUpRecycler(recyclerView);

        toolbar.setNavigationOnClickListener(v1 -> drawerLayout.open());

        bottomNavigationView.setOnNavigationItemSelectedListener(this::bottomMenu);

        return v;
    }

    private boolean bottomMenu(MenuItem menuItem) {
        final int itemId = menuItem.getItemId();
        if (itemId == R.id.post) {
            searchView.clearFocus();
            BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
            bottomSheetFragment.show(getParentFragmentManager(), "bottomSheet");
            return true;
        } else if (itemId == R.id.browse) {
            pocheador(R.drawable.suscriptions);
            return true;
        } else if (itemId == R.id.home) {
            contents.setVisibility(View.VISIBLE);
            imageContents.setVisibility(View.GONE);
            return true;
        } else if (itemId == R.id.chat) {
            pocheador(R.drawable.chat);
            return true;
        } else if (itemId == R.id.inbox) {
            pocheador(R.drawable.notifications);
            return true;
        } else {
            return false;
        }
    }

    private void pocheador(int imageId) {
        contents.setVisibility(View.GONE);
        imageContents.setImageResource(imageId);
        imageContents.setVisibility(View.VISIBLE);
    }


    private void setUpRecycler(RecyclerView recyclerView) {
        List<Post> postList = new ArrayList<>();

        SubReddit catsAreLiquid = new SubReddit("catsareliquid", R.drawable.communityiconcatsareliquid);
        SubReddit programmerHumor = new SubReddit("ProgrammerHumor", R.drawable.communityiconprogrammerhumor);
        SubReddit hentaimemes = new SubReddit("Hentaimemes", R.drawable.communityicon_hentaimemes);

        postList.add(new Post(programmerHumor, "jevoy6737", "8h", "It hurts", 20, R.drawable.post2, 113, 435));
        postList.add(new Post(catsAreLiquid, "LilDiomede", "3h", "Meowlk", 0, R.drawable.post3, 183, 6));
        postList.add(new Post(hentaimemes, "namx2u", "4h", "tbh I hate that too..", 4, R.drawable.post4, 4800, 59));

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new PostAdapter(postList, requireContext()));
    }
}