package cat.itb.clonreddit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.adapters.PostAdapter;
import cat.itb.clonreddit.models.Post;
import cat.itb.clonreddit.models.User;
import cat.itb.clonreddit.utils.DBUtils;


public class MainFragment extends Fragment {
    private NavController navController;
    private Toolbar toolbar;
    private StorageReference mStorageRef;
    private BottomNavigationView bottomNavigationView;
    private SearchView searchView;
    private DrawerLayout drawerLayout;
    private View contents;
    private ImageView imageContents;
    private PostAdapter adapter;
    private RecyclerView recyclerView;
    private MaterialTextView navUsername;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }


    /*++++ CARGA EL USERNAME DEL USUARIO EN EL DRAWER ++++*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String username = user.getDisplayName();
            if (username == null || username.isEmpty()) {
                navUsername.setText(R.string.no_user);
            } else {
                navUsername.setText(username);
            }
        } else {
            navUsername.setText(R.string.no_user);
        }
    }
    /*++++ CARGA EL USERNAME DEL USUARIO EN EL DRAWER ++++*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        toolbar = v.findViewById(R.id.toolbar);
        recyclerView = v.findViewById(R.id.recyclerViewMain);
        drawerLayout = v.findViewById(R.id.drawerLayout);
        bottomNavigationView = v.findViewById(R.id.bottomNavigation);
        searchView = v.findViewById(R.id.searchView);
        contents = v.findViewById(R.id.mainFragmentContents);
        imageContents = v.findViewById(R.id.imageContent);

        NavigationView navigationView = v.findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.nameUserDrawer);




       /*+++++++ LOG OUT +++++++*/
        ImageButton logOutButton = v.findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    FirebaseAuth.getInstance().signOut();

                    navController.navigate(R.id.action_mainFragment_to_homeScreenFragment);
                }else{
                    Toast.makeText(getContext(), "NO HAY NADIE LOGEADO", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*+++++++ LOG OUT +++++++*/


        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        setUpRecycler(recyclerView);

        toolbar.setNavigationOnClickListener(v1 -> drawerLayout.open());

        bottomNavigationView.setOnNavigationItemSelectedListener(this::bottomMenu);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filtrarRecycler(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtrarRecycler(newText);
                return false;
            }
        });

        return v;
    }




    public void filtrarRecycler(String query) {
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(DBUtils.getReferencePost().orderByChild("title").startAt(query).endAt(query + "\uf8ff"), Post.class).build();
        adapter = new PostAdapter(options, requireContext());
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }





    private boolean bottomMenu(MenuItem menuItem) {
        final int itemId = menuItem.getItemId();
        if (itemId == R.id.post) {
            searchView.clearFocus();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null){
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getParentFragmentManager(), "bottomSheet");
            }else{
                BottomSheetFragmentNoLogIn bottomSheetFragmentNoLogIn = new BottomSheetFragmentNoLogIn("main");
                bottomSheetFragmentNoLogIn.show(getParentFragmentManager(), "bottomSheet");
            }

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
/*        DBUtils.uploadPost(new Post("-MW--YgVorRGpajcRNoj", new User("onlyforjazzmemes"), "8h", "version 8.0", "https://i.redd.it/w2nwivnbtjt61.jpg",
                20, 113, 0));*/
/*        ConexionBBDD.uploadPost(new Post("-MW--YgVorRGpajcRNok", new User("DeveloperNightshade"), "10h", "Hmm, fuck it", "https://i.redd.it/q2uhlnvdkhn61.jpg",
                24, 3800, 456));*/

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(DBUtils.getReferencePost(), Post.class).build();
        adapter = new PostAdapter(options, requireContext());
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
}