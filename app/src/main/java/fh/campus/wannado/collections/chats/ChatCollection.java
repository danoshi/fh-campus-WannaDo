package fh.campus.wannado.collections.chats;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import fh.campus.wannado.collections.messages.MessagesDocument;

public class ChatCollection {
    private final static String COLLECTION_NAME = "chat";
    private final static String PARTICIPANTS_FILED = "participantsID";

    //// w onCompleteListener document.toObject(ChatDocument.class)
    public static void getUserChats(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestore.collection(COLLECTION_NAME)
                .whereArrayContains("participantsID", userID)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }


    public static void getChatWithUser(OnCompleteListener<QuerySnapshot> onCompleteListener, String secondUserID) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CollectionReference colRef = firestore.collection(COLLECTION_NAME);
        colRef.whereArrayContains("participantsID", userID);
        colRef.whereArrayContains("participantsID", secondUserID);
        colRef.get().addOnCompleteListener(onCompleteListener);
    }


    public static String createNewChat(String firstUserID, String secondUserID){
        String documentID = RandomStringUtils.random(15, true, true);
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document(documentID);
        Map<String, Object> message = new HashMap<>();
        message.put(PARTICIPANTS_FILED, Arrays.asList(firstUserID, secondUserID));
        documentReference.set(message);
        return documentID;
    }



}
