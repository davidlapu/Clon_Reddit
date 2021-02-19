package cat.itb.clonreddit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import cat.itb.clonreddit.R;

public class MainFragment extends Fragment {
    private NavController navController;
    private Toolbar toolbar;
    //private ViewPager viewPager;
    //private TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        toolbar = v.findViewById(R.id.toolbar);
        // viewPager = v.findViewById(R.id.viewPager);
        // tabLayout = v.findViewById(R.id.tabLayout);

        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbar);

        //tabLayout.setupWithViewPager(viewPager);

        return v;
    }
}