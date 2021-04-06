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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.utils.AddListenerOnTextChange;
import cat.itb.clonreddit.utils.AuthWithGoogle;

public class LoginFragment extends Fragment {
    private TextView policyTextView;
    private FirebaseAuth mAuth;
    private TextInputEditText editTextEmail, editTextPass;
    private TextInputLayout emailInputLayout, passwordInputLayout;
    private int GOOGLE_SIGN_IN = 100;



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
        emailInputLayout = v.findViewById(R.id.usernameInputLayout);
        passwordInputLayout = v.findViewById(R.id.passwordInputLayout);


        editTextEmail.addTextChangedListener(new AddListenerOnTextChange(getContext(), emailInputLayout));
        editTextPass.addTextChangedListener(new AddListenerOnTextChange(getContext(), passwordInputLayout));

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
//        buttonGoogle.setOnClickListener(this::toMainScreen);
        continueBTN.setOnClickListener(this::login);
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
        //loginTextView

        return v;
    }

    private void login(View view) {
        if (editTextEmail.getText().toString().isEmpty() || editTextPass.getText().toString().isEmpty()){
            if (editTextEmail.getText().toString().isEmpty()){
                isEmpty(emailInputLayout);
            }else{
                isEmpty(passwordInputLayout);
            }

        }else{
            mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(),
                    editTextPass.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    toMainScreen();
                } else {
//                    Toast.makeText(getContext(), "Authentication failed.",
//                            Toast.LENGTH_SHORT).show();
                    notMatchFields(emailInputLayout);
                    notMatchFields(passwordInputLayout);
                }
            });
        }


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
            AuthWithGoogle.authWithGoogle(data, navController, R.id.action_loginFragment_to_mainFragment);
        }
    }
}