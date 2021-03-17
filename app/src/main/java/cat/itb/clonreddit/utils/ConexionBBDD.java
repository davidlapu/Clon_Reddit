package cat.itb.clonreddit.utils;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import cat.itb.clonreddit.models.Post;

public class ConexionBBDD {
    private static final DatabaseReference myRef = getDatabse().getReference();
    public static String urlFoto;
    public static String id;



    public static StorageReference getStorage(){
        return FirebaseStorage.getInstance().getReference().child("img_comprimidas");
    }

    public static FirebaseDatabase getDatabse(){
        return FirebaseDatabase.getInstance();
    }

    public static DatabaseReference getReferenceUser(){
        return myRef.child("User");
    }

    public static DatabaseReference getReferencePost(){
        return myRef.child("Post");
    }

    public static DatabaseReference getReferenceSubReddit(){
        return myRef.child("SubReddit");
    }

    public static DatabaseReference getReferenceComment(){
        return myRef.child("Comment");
    }


    public static void  insertPost(Post post){
        String id = getReferencePost().push().getKey();
        post.setId(id);
        getReferencePost().setValue(post);
    }




    public static Task<Uri> subirImagen(byte[] img) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        String nameImage = timestamp + ".jpg";
        StorageReference ref = getStorage().child(nameImage);

        UploadTask uploadTask = ref.putBytes(img);
        Task<Uri> uriTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }
            return ref.getDownloadUrl();
//        }).addOnCompleteListener(task -> {
//            Uri downloadUri = task.getResult();
////                bbdd.getReference().push().child("urlfoto").setValue(downloadUri.toString());
//            urlFoto = downloadUri.toString();
//            id = getReferencePost().push().getKey();

        });


        return uriTask;
    }
}
