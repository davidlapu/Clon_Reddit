package cat.itb.clonreddit.fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import cat.itb.clonreddit.R;

public class HomeScreenFragment extends Fragment {
    VideoView videoBg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_screen, container, false);
        videoBg = v.findViewById(R.id.videoBg);

        String path = "android.resource://cat.itb.clonreddit/" + R.raw.loki;
        Uri uri = Uri.parse(path);
        videoBg.setVideoURI(uri);

        videoBg.start();

        videoBg.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0, 0);
                mp.setLooping(true);
            }
        });

        return v;
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