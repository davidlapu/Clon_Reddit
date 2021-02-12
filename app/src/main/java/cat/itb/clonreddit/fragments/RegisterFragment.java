package cat.itb.clonreddit.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cat.itb.clonreddit.R;

public class RegisterFragment extends Fragment {
    TextView policyTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        policyTextView = v.findViewById(R.id.policyUserTextView);

        SpannableStringBuilder spannable = new SpannableStringBuilder("By continuing, you agree to our User Agreement and Privacy Policy.");
        spannable.setSpan(
                new ForegroundColorSpan(Color.parseColor("#0078EF")),
                31, // start
                46, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannable.setSpan(
                new ForegroundColorSpan(Color.parseColor("#0078EF")),
                50,
                65,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        policyTextView.setText(spannable);
        // Inflate the layout for this fragment
        return v;
    }
}