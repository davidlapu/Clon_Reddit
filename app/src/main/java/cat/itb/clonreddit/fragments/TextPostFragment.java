package cat.itb.clonreddit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.models.Post;
import cat.itb.clonreddit.models.SubReddit;
import cat.itb.clonreddit.models.User;
import cat.itb.clonreddit.utils.DBUtils;

public class TextPostFragment extends Fragment {
    private NavController navController;
    private ImageView chooseCommunityArrowImageView, closeBTN;
    private ShapeableImageView imageViewSubredditPost;
    private EditText editTextTitle, editTextText;
    private TextView chooseCommunityTextView;
    private MaterialTextView textViewPost;
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
        closeBTN = v.findViewById(R.id.closeBTN);
        editTextTitle = v.findViewById(R.id.editTextTitleTextPost);
        editTextText = v.findViewById(R.id.editTextTextPost);
        textViewPost = v.findViewById(R.id.buttonPostText);

        chooseCommunityTextView.setOnClickListener(this::chooseCommunityFragment);
        chooseCommunityArrowImageView.setOnClickListener(this::chooseCommunityFragment);
        closeBTN.setOnClickListener(this::toMainFragment);
        textViewPost.setOnClickListener(this::pushPost);

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

    /**
     * Método que hace la navegacion del fragment actual al fragmen SubRedditListFragment
     * @param view
     */
    private void chooseCommunityFragment(View view) {
        TextPostFragmentDirections.ActionTextPostFragmentToSubRedditListFragment action =
                TextPostFragmentDirections.actionTextPostFragmentToSubRedditListFragment("txt");
        navController.navigate(action);
    }


    public void toMainFragment(View view) {
        toMainFragment();
    }

    /**
     * Método que hace la navegación del fragment actual al MainFragment
     */
    public void toMainFragment() {
        navController.navigate(R.id.action_textPostFragment_to_mainFragment);
    }




    /**
     * Sube el post a Firebase
     * @param view
     */
    public void pushPost(View view) {
        if (allRequiredCamps()) {
            try {
                String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                String title = editTextTitle.getText().toString();
                String text = editTextText.getText().toString();

                Post p = new Post(subReddit.getId(), new User(username), "0m", title,
                        0, text , 0, 0);

                DBUtils.uploadPost(p);

                toMainFragment();

            }catch (Exception e){
                //TODO Mover texto a strings
                Toast.makeText(getContext(), "IMPOSIBLE SUBIR POST SIN IMAGEN", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Comprueba que todos los campos requeridos del formulario están rellenados.
     * @return boolean
     */
    private boolean allRequiredCamps() {
        boolean allGood = true;

        if (subReddit == null) allGood = false;
        else if (editTextTitle.getText().toString().isEmpty()) allGood = false;

        return allGood;
    }
}