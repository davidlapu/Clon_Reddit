package cat.itb.clonreddit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import cat.itb.clonreddit.R;


public class BottomSheetFragment extends BottomSheetDialogFragment {
    private ImageView imageView;
    private ImageView imageViewPostImage;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        ImageView closeImg = v.findViewById(R.id.closeBTN);
        imageView = v.findViewById(R.id.imageView4);
        imageViewPostImage = v.findViewById(R.id.imageView2);



        closeImg.setOnClickListener(this::exit);
        imageView.setOnClickListener(this::toPostTextFragment);
        imageViewPostImage.setOnClickListener(this::toPostImageFragment);


        return v;
    }

    private void exit(View view) {
        dismiss();

    }

    private void toPostTextFragment(View view){
        navController.navigate(R.id.action_mainFragment_to_textPostFragment);
        dismiss();
    }

    private void toPostImageFragment(View view){
        navController.navigate(R.id.action_mainFragment_to_imagePostFragment);
        dismiss();
    }
}