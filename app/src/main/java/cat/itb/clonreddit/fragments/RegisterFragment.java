package cat.itb.clonreddit.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import cat.itb.clonreddit.R;

public class RegisterFragment extends Fragment {
    TextView policyTextView;
    Button continueBTN;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        policyTextView = v.findViewById(R.id.policyUserTextView);
        TextView textViewLogin = v.findViewById(R.id.loginTextView);
        MaterialButton buttonGoogle = v.findViewById(R.id.googleRegisterBTN);
        continueBTN = v.findViewById(R.id.continuBTN);

//        continueBTN.setBackground();

        SpannableStringBuilder spannable = new SpannableStringBuilder(getText(R.string.privacyText));
        spannable.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.darkBlue)),
                31, // start
                46, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannable.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.darkBlue)),
                50,
                65,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        policyTextView.setText(spannable);

        textViewLogin.setOnClickListener(this::toLogin);
        buttonGoogle.setOnClickListener(this::toMainScreen);
        return v;
    }

    private void toMainScreen(View view) {
        navController.navigate(R.id.action_registerFragment_to_mainFragment);
    }

    private void toLogin(View view) {
        navController.navigate(R.id.action_registerFragment_to_loginFragment);
    }
}