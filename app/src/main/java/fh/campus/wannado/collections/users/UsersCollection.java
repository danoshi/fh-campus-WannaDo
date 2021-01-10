package fh.campus.wannado.collections.users;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UsersCollection {
    private final static String COLLECTION_NAME = "users";
    public final static String EMAIL_FIELD = "email";
    public final static String USERNAME_FIELD = "username";

    public static void getCurrentUser(OnCompleteListener<DocumentSnapshot> onCompleteListener){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getById(userId, onCompleteListener);
    }


    public static void getById(String userId, OnCompleteListener<DocumentSnapshot> onCompleteListener){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(userId);
        documentReference.get().addOnCompleteListener(onCompleteListener);
    }

    public static UsersDocument getUserDocumentOf(DocumentSnapshot documentSnapshot){
        return UsersDocument.builder()
                .email(documentSnapshot.getString(EMAIL_FIELD))
                .username(documentSnapshot.getString(USERNAME_FIELD))
                .build();
    }

}
