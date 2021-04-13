package cat.itb.clonreddit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.utils.AddListenerOnTextChange;
import cat.itb.clonreddit.utils.AuthWithGoogle;

public class RegisterFragment extends Fragment {
    private TextView policyTextView;
    private TextInputEditText editTextEmail, editTextPass, editTextUserName;
    private MaterialButton continueBTN, buttonGoogle, appleButton;
    private NavController navController;
    private FirebaseAuth mAuth;
    private TextInputLayout emailInputLayout, passwordInputLayout, userNameInputLayout;
    private int GOOGLE_SIGN_IN = 100;
    private String dest = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        policyTextView = v.findViewById(R.id.policyUserTextView);
        TextView textViewLogin = v.findViewById(R.id.singUpTextView);
        buttonGoogle = v.findViewById(R.id.googleLoginBTN);
        appleButton = v.findViewById(R.id.appleLoginBTN);
        continueBTN = v.findViewById(R.id.continueBTN);
        ImageView closeForm = v.findViewById(R.id.closeBTN);
        editTextEmail = v.findViewById(R.id.emailEditText);
        editTextPass = v.findViewById(R.id.passwordEditText);
        editTextUserName = v.findViewById(R.id.usernameEditText);
        emailInputLayout = v.findViewById(R.id.emailInputLayout);
        passwordInputLayout = v.findViewById(R.id.passwordInputLayout);
        userNameInputLayout = v.findViewById(R.id.usernameInputLayout);


        editTextEmail.addTextChangedListener(new AddListenerOnTextChange(getContext(), emailInputLayout));
        editTextPass.addTextChangedListener(new AddListenerOnTextChange(getContext(), passwordInputLayout));
        editTextUserName.addTextChangedListener(new AddListenerOnTextChange(getContext(), userNameInputLayout));

//        continueBTN.setBackground();

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
        textViewLogin.setOnClickListener(this::toLogin);

        continueBTN.setOnClickListener(this::continueClicked);
        appleButton.setOnClickListener(this::toMainScreen);
        buttonGoogle.setOnClickListener(new View.OnClickListener() {
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
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dest = RegisterFragmentArgs.fromBundle(getArguments()).getDest();
    }

    private void continueClicked(View view) {
        createUser(editTextEmail.getText().toString(), editTextPass.getText().toString());
    }

    private void toMainScreen() {
        if (dest.equals("comment")) navController.popBackStack();
        else navController.navigate(R.id.action_registerFragment_to_mainFragment);
    }

    private void toMainScreen(View view) {
        toMainScreen();
    }

    private void toLogin(View view) {
        navController.navigate(R.id.action_registerFragment_to_loginFragment);
    }

    private void toBackScreen(View view) {
        navController.popBackStack();
    }


    private void createUser(String email, String password) {
        if (editTextEmail.getText().toString().isEmpty() || editTextPass.getText().toString().isEmpty()
        || editTextUserName.getText().toString().isEmpty()){
            if (editTextEmail.getText().toString().isEmpty()){
                isEmpty(emailInputLayout);
            }else if (editTextUserName.getText().toString().isEmpty()){
                isEmpty(userNameInputLayout);
            }else{
                isEmpty(passwordInputLayout);
            }
        }else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), task -> {
                        if (task.isSuccessful()) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest
                                    .Builder().setDisplayName(editTextUserName.getText().toString()).build();
                            mAuth.getCurrentUser().updateProfile(profileUpdates);
                            toMainScreen();
                        } else {
//                            Toast.makeText(getContext(), "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();

                            notMatchFields(emailInputLayout);
                            notMatchFields(passwordInputLayout);
                        }
                    });
        }
    }

    public void isEmpty(TextInputLayout t){
        String msg = getString(R.string.required);
        t.setError(msg);
    }

    public void notMatchFields(TextInputLayout t){
        String msg;
        if (t == emailInputLayout){
            msg = getString(R.string.notMatchEmail);
        }else{
            msg = getString(R.string.notMatchPass);
        }
        t.setError(msg);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN) {
            AuthWithGoogle.authWithGoogle(data,navController, R.id.action_registerFragment_to_mainFragment);
        }
    }
}
