package cat.itb.clonreddit.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.utils.AuthWithGoogle;


public class BottomSheetFragmentNoLogIn extends BottomSheetDialogFragment {
    private MaterialButton appleButton;
    private NavController navController;
    private final int GOOGLE_SIGN_IN = 100;
    private final String origin;

    public BottomSheetFragmentNoLogIn(String origin) {
        this.origin = origin;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_sheet_no_log_in, container, false);
        TextView policyTextView = v.findViewById(R.id.policyUserTextView);
        MaterialButton emailButton = v.findViewById(R.id.emailButtonBottomSheetNoLogIn);
        MaterialButton googleButton = v.findViewById(R.id.googleButtonBottomSheetNoLogIn);
        appleButton = v.findViewById(R.id.appleButtonBottomSheetNoLogIn);
        TextView loginTextView = v.findViewById(R.id.loginButtonBottomSheetNoLogIn);

        emailButton.setOnClickListener(this::goToRegisterForm);
        loginTextView.setOnClickListener(this::goToLogInForm);

        googleButton.setOnClickListener(new View.OnClickListener() {
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
        return v;
    }

    public void goToLogInForm(View view) {
        if (origin.equals("main")) navController.navigate(R.id.action_mainFragment_to_loginFragment);
        else navController.navigate(R.id.action_commentPostFragment_to_loginFragment);
        dismiss();
    }

    public void goToRegisterForm(View view) {
        if (origin.equals("main")) navController.navigate(R.id.action_mainFragment_to_registerFragment);
        else {
            CommentPostFragmentDirections.ActionCommentPostFragmentToRegisterFragment dir =
            CommentPostFragmentDirections.actionCommentPostFragmentToRegisterFragment();

            dir.setDest("comment");

            navController.navigate(dir);
            //navController.navigate(R.id.action_commentPostFragment_to_registerFragment);
        }
        dismiss();
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN) {
            AuthWithGoogle.authWithGoogle(data, navController, R.id.mainFragment);
            dismiss();
        }
    }

//    public void showAlert(String text) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Error");
//        builder.setMessage(text);
//        builder.setPositiveButton("Aceptar",null);
//        AlertDialog al = builder.create();
//        al.show();
//    }
}