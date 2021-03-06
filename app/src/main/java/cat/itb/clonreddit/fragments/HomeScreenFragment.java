package cat.itb.clonreddit.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.utils.AuthWithGoogle;
import cat.itb.clonreddit.utils.DBUtils;

public class HomeScreenFragment extends Fragment {
    private VideoView videoBg;
    private TextView policyTextView, skipTextView, loginTextView;
    private Button emailButton;
    private MaterialButton gButton, appleButton;
    private NavController navController;
    private int GOOGLE_SIGN_IN = 100;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
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

        DBUtils.getReferenceUser();

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

        gButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);
                googleSignInClient.signOut();

                startActivityForResult(googleSignInClient.getSignInIntent(), GOOGLE_SIGN_IN);
            }
        });


        session();


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


    private void session() {
        SharedPreferences sp = getActivity().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email = sp.getString("email",null);

        if (email != null) {
            View l = getActivity().getWindow().getDecorView().findViewById(R.id.homeScreenFragment);
            l.setVisibility(View.INVISIBLE);

            navController.navigate(R.id.action_homeScreenFragment_to_mainFragment);
        }
    }


    @Override
    public void onStart() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            navController.navigate(R.id.action_homeScreenFragment_to_mainFragment);
        }
        super.onStart();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN) {
            AuthWithGoogle.authWithGoogle(data,navController, R.id.action_homeScreenFragment_to_mainFragment);
        }
    }
}