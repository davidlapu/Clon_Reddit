package cat.itb.clonreddit.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import cat.itb.clonreddit.R;


public class BottomSheetFragment extends BottomSheetDialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        ImageView closeImg = v.findViewById(R.id.closeBTN);
        closeImg.setOnClickListener(this::exit);

        return v;
    }

    private void exit(View view) {
        dismiss();
    }
}