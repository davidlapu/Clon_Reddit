package cat.itb.clonreddit.utils;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventListener;
import java.util.Objects;
import java.util.OptionalInt;

import cat.itb.clonreddit.models.Comment;
import cat.itb.clonreddit.models.Post;
import cat.itb.clonreddit.models.SubReddit;

public class DBUtils {
    private static final DatabaseReference myRef = getDatabse().getReference();
    private static final DatabaseReference postReference = myRef.child("Post");
    private static final DatabaseReference subRedditReference = myRef.child("SubReddit");
    private static final DatabaseReference commentReference = myRef.child("Comment");
    private static int total;



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
        return postReference;
    }

    public static DatabaseReference getReferenceSubReddit(){
        return subRedditReference;
    }

    public static DatabaseReference getReferenceComment(){
        return commentReference;
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
        });


        return uriTask;
    }

    public static void uploadPost(Post post) {
        String key = postReference.push().getKey();
        post.setId(key);
        postReference.child(key).setValue(post);
    }

    public static String uploadSubreddit(SubReddit subReddit) {
        String key = subRedditReference.push().getKey();
        subReddit.setId(key);
        subRedditReference.child(key).setValue(subReddit);
        return key;
    }

    public static Task<com.google.firebase.database.DataSnapshot> getSubreddit(String id) {
        return subRedditReference.child(id).get();
    }

    public static void addCommentNum(String postId) {
        getPost(postId).addOnSuccessListener(dataSnapshot -> {
            int n = dataSnapshot.getValue(Post.class).getCommentsNum();
            n = n + 1;

            postReference.child(postId).child("commentsNum").setValue(n);
        });
    }

    public static Task<com.google.firebase.database.DataSnapshot> getPost(String id) {
        return postReference.child(id).get();
    }

    public static void uploadComment(String postId, Comment comment) {
        DatabaseReference currentRef = commentReference.child(postId);

        String key = currentRef.push().getKey();
        comment.setId(key);
        currentRef.child(key).setValue(comment);
        addCommentNum(postId);
    }


//    public static int totalCommentsPost(String postId){
//        commentReference.child(postId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                total = (int) snapshot.getChildrenCount();
//                System.out.println(total + "-------------------------------------------------------------------------");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        System.out.println(total + "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        return total;
//    }
}
