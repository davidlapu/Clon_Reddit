package cat.itb.clonreddit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cat.itb.clonreddit.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_ClonReddit);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}