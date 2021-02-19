package cat.itb.clonreddit.fragments;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import cat.itb.clonreddit.R;

public class HomeScreenFragment extends Fragment {
    VideoView videoBg;
    TextView policyTextView, loginTextView;
    private NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_screen, container, false);
        videoBg = v.findViewById(R.id.videoBg);
        loginTextView = v.findViewById(R.id.loginTextView);

        loginTextView.setOnClickListener(this::toMainScreen);

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