package cat.itb.clonreddit.utils;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class AuthWithGoogle {
    public static void authWithGoogle(Intent data, NavController navController, int id){
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        GoogleSignInAccount g = null;
        try {
            g = task.getResult(ApiException.class);

            if (g != null) {
                AuthCredential credential = GoogleAuthProvider.getCredential(g.getIdToken(),null);
                GoogleSignInAccount finalG = g;
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            navController.navigate(id);
                        }
                    }
                });
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
