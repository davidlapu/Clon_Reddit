package cat.itb.clonreddit.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.models.Post;
import cat.itb.clonreddit.utils.Camera;
import cat.itb.clonreddit.utils.ConexionBBDD;

import static android.app.Activity.RESULT_OK;

public class ImagePostFragment extends Fragment {
    private ImageView addBTN, imageViewPost, chooseCommunityArrowImageView, closeBTN;
    private TextView postBTN, chooseCommunityTextView;
    private EditText titlePostEditText, captionPostEditText;
    private Camera camera;
    static final int REQUEST_IMAGE_CAPTURE = 100;
    private byte[] thumb_byte;
    private File url;
    private String urlFoto;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        captionPostEditText = v.findViewById(R.id.captionsPostEditText);
        closeBTN = v.findViewById(R.id.closeBTN);


        addBTN.setOnClickListener(this::takePicture);
        postBTN.setOnClickListener(this::pushPost);

        camera = new Camera(getContext());
        // Inflate the layout for this fragment
        return v;
    }

    public void pushPost(View view) {
        try {
            thumb_byte = camera.comprimirImagen(url);

            Task<Uri> task = ConexionBBDD.subirImagen(thumb_byte);

            task.addOnCompleteListener(task1 -> {
                Uri downloadUri = task1.getResult();
                urlFoto = downloadUri.toString();

                Post post = new Post();

            });
        }catch (Exception e){
            Toast.makeText(getContext(), "IMPOSIBLE SUBIR POST SIN IMAGEN", Toast.LENGTH_SHORT).show();
        }

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