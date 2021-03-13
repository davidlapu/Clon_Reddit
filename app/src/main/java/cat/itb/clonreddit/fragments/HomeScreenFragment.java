package cat.itb.clonreddit.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.utils.ConexionBBDD;

public class HomeScreenFragment extends Fragment {
    private VideoView videoBg;
    private TextView policyTextView, skipTextView, loginTextView;
    private Button emailButton;
    private MaterialButton gButton, appleButton;
    private NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        Object mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_screen, container, false);
        videoBg = v.findViewById(R.id.videoBg);
        skipTextView = v.findViewById(R.id.singUpTextView);
        emailButton = v.findViewById(R.id.emailHomeScreenButton);
        gButton = v.findViewById(R.id.googleHomeScreenButton);
        appleButton = v.findViewById(R.id.appleHomeScreenButton);
        loginTextView = v.findViewById(R.id.loginHomeScreen);

        ConexionBBDD.getReferenceUser();

        skipTextView.setOnClickListener(this::toMainScreen);
        emailButton.setOnClickListener(this::toRegisterForm);
        gButton.setOnClickListener(this::toMainScreen);
        appleButton.setOnClickListener(this::toMainScreen);
        loginTextView.setOnClickListener(this::toLoginForm);

        String path = "android.resource://cat.itb.clonreddit/" + R.raw.loki;
        Uri uri = Uri.parse(path);
        videoBg.setVideoURI(uri);
        videoBg.setScaleX(1.2f);
        videoBg.setScaleY(1);
        videoBg.start();

        videoBg.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0, 0);
                mp.setLooping(true);
            }
        });

        policyTextView = v.findViewById(R.id.policyHomeScreen);
        SpannableStringBuilder spannable = new SpannableStringBuilder(getText(R.string.privacyText));
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        spannable.setSpan(boldSpan, 31, 46, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        StyleSpan boldSpan2 = new StyleSpan(Typeface.BOLD);
        spannable.setSpan(boldSpan2, 50, 65, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        policyTextView.setText(spannable);

        return v;
    }

    private void toMainScreen(View view) {
        navController.navigate(R.id.action_homeScreenFragment_to_mainFragment);
    }

    private void toRegisterForm(View view) {
        navController.navigate(R.id.action_homeScreenFragment_to_registerFragment);
    }

    private void toLoginForm(View view) {
        navController.navigate(R.id.action_homeScreenFragment_to_loginFragment);
    }




    @Override
    public void onResume() {
        videoBg.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        videoBg.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        videoBg.stopPlayback();
        super.onDestroy();
    }





}