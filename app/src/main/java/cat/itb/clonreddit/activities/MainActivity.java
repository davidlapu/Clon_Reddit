package cat.itb.clonreddit.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cat.itb.clonreddit.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_ClonReddit);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}