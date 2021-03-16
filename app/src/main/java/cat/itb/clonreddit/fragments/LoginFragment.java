package cat.itb.clonreddit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import cat.itb.clonreddit.R;

public class LoginFragment extends Fragment {
    private TextView policyTextView;
    private FirebaseAuth mAuth;
    private TextInputEditText editTextEmail, editTextPass;


    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        policyTextView = v.findViewById(R.id.policyUserTextView);
        ImageView closeForm = v.findViewById(R.id.closeBTN);
        TextView singUpTextView = v.findViewById(R.id.singUpTextView);
        MaterialButton buttonGoogle = v.findViewById(R.id.googleLoginBTN);
        MaterialButton continueBTN = v.findViewById(R.id.continueBTN);
        MaterialButton appleButton = v.findViewById(R.id.appleLoginBTN);
        editTextEmail = v.findViewById(R.id.usernameEditText);
        editTextPass = v.findViewById(R.id.passwordEditText);

        SpannableStringBuilder spannable = new SpannableStringBuilder(getText(R.string.privacyText));
        spannable.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.alien, null)),
                31, // start
                46, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannable.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.alien, null)),
                50,
                65,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        policyTextView.setText(spannable);

        closeForm.setOnClickListener(this::toBackScreen);
        singUpTextView.setOnClickListener(this::toRegisterScreen);
        buttonGoogle.setOnClickListener(this::toMainScreen);
        continueBTN.setOnClickListener(this::login);
        appleButton.setOnClickListener(this::toMainScreen);
        //loginTextView

        return v;
    }

    private void login(View view) {
        mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(),
                editTextPass.getText().toString()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        toMainScreen();
                    } else {
                        Toast.makeText(getContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void toMainScreen(View view) {
        navController.navigate(R.id.action_loginFragment_to_mainFragment);
    }

    private void toMainScreen() {
        navController.navigate(R.id.action_loginFragment_to_mainFragment);
    }

    private void toBackScreen(View view) {
        navController.popBackStack();
    }

    private void toRegisterScreen(View view) {
        navController.navigate(R.id.action_loginFragment_to_registerFragment);
    }
}