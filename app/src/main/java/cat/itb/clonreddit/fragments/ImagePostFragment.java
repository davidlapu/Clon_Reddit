package cat.itb.clonreddit.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.models.Post;
import cat.itb.clonreddit.models.SubReddit;
import cat.itb.clonreddit.models.User;
import cat.itb.clonreddit.utils.Camera;
import cat.itb.clonreddit.utils.DBUtils;

import static android.app.Activity.RESULT_OK;

public class ImagePostFragment extends Fragment {
    private ImageView addBTN, imageViewPost, chooseCommunityArrowImageView, closeBTN;
    private TextView postBTN, chooseCommunityTextView;
    private EditText titlePostEditText;
    private ShapeableImageView imageViewSubredditPost;
    private Camera camera;
    static final int REQUEST_IMAGE_CAPTURE = 100;
    private File url;
    private String urlFoto;
    private NavController navController;
    private SubReddit subReddit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_post, container, false);
        addBTN = v.findViewById(R.id.addBTN);
        imageViewPost = v.findViewById(R.id.imageViewPost);
        postBTN = v.findViewById(R.id.postBTN);
        chooseCommunityTextView = v.findViewById(R.id.chooseSubredditTextView);
        chooseCommunityArrowImageView = v.findViewById(R.id.chooseSubredditArrow);
        titlePostEditText = v.findViewById(R.id.titlePostEditText);
        closeBTN = v.findViewById(R.id.closeBTN);
        imageViewSubredditPost = v.findViewById(R.id.imageViewSubredditPost);


        addBTN.setOnClickListener(this::takePicture);
        postBTN.setOnClickListener(this::pushPost);
        chooseCommunityTextView.setOnClickListener(this::chooseCommunityFragment);
        chooseCommunityArrowImageView.setOnClickListener(this::chooseCommunityFragment);
        closeBTN.setOnClickListener(this::toMainFragment);

        camera = new Camera(getContext());
        // Inflate the layout for this fragment
        return v;
    }

    public void toMainFragment(View view) {
        toMainFragment();
    }

    public void toMainFragment() {
        navController.navigate(R.id.action_imagePostFragment_to_mainFragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        if (!bundle.isEmpty()) {
            SubReddit s = ImagePostFragmentArgs.fromBundle(bundle).getSubreddit();

            chooseCommunityTextView.setText(s.getTitle());
            Picasso.with(requireContext()).load(s.getImgUrl()).into(imageViewSubredditPost);
            subReddit = s;
        }

    }

    private void chooseCommunityFragment(View view) {
        ImagePostFragmentDirections.ActionImagePostFragmentToSubRedditListFragment action =
                ImagePostFragmentDirections.actionImagePostFragmentToSubRedditListFragment("img");
        navController.navigate(action);
    }

    public void pushPost(View view) {
        if (allRequiredCamps()) {
            try {
                Task<Uri> task = DBUtils.subirImagen(camera.comprimirImagen(url));

                task.addOnCompleteListener(task1 -> {
                    Uri downloadUri = task1.getResult();
                    urlFoto = downloadUri.toString();

                    String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    String title = titlePostEditText.getText().toString();

                    DBUtils.uploadPost(new Post(subReddit.getId(), new User(username), "0m", title, urlFoto,
                            0, 0, 0));

                    //TODO Mover texto a strings
                    Toast.makeText(getContext(), "Imagen subida", Toast.LENGTH_SHORT).show();
                    toMainFragment();

                });
            } catch (Exception e) {
                //TODO Mover texto a strings
                Toast.makeText(getContext(), "IMPOSIBLE SUBIR POST SIN IMAGEN", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), R.string.all_required_fields, Toast.LENGTH_LONG).show();
        }
    }

    private boolean allRequiredCamps() {
        boolean allGood = true;

        if (subReddit == null) allGood = false;
        else if (titlePostEditText.getText().toString().isEmpty()) allGood = false;

        return allGood;
    }

    public void takePicture(View view) {
        CropImage.startPickImageActivity(getContext(), ImagePostFragment.this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            imageViewPost.setImageBitmap(imageBitmap);
        }
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri= CropImage.getPickImageResultUri(getContext(), data);
            camera.recortarImagen(imageUri, getContext(), this);
//            recortarImagen(imageUri);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resutUri = result.getUri();
                url = new File(resutUri.getPath());
                Picasso.with(getContext()).load(url).into(imageViewPost);
            }
        }
    }
}