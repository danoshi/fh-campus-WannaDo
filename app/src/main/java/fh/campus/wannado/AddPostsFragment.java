package fh.campus.wannado;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import fh.campus.wannado.databinding.FragmentAddpostsBinding;

public class AddPostsFragment extends Fragment {

    private FragmentAddpostsBinding binding;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore firestore;
    String userID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddpostsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        buttonPublishThread();
        return view;
    }

    public void buttonPublishThread(){
        binding.buttonPublish.setOnClickListener(v -> {

            mFirebaseAuth = FirebaseAuth.getInstance();
            firestore = FirebaseFirestore.getInstance();

            EditText editTextTitle = binding.editTextTitle;
            EditText editTextMessage = binding.editTextMessage;
            Button btnPublish = binding.buttonPublish;

            String title = editTextTitle.getText().toString();
            String message = editTextMessage.getText().toString();

            if (title.isEmpty()){
                editTextTitle.setError("Please enter your title");
                editTextTitle.requestFocus();
            }
            else if (message.isEmpty()){
                editTextMessage.setError("Please enter your Message");
                editTextMessage.requestFocus();
            }
            else if (title.isEmpty() && message.isEmpty()){
                Toast.makeText(getActivity(), "Fields are empty!", Toast.LENGTH_SHORT).show();
            }
            else if (!(title.isEmpty() && message.isEmpty())){
                userID = mFirebaseAuth.getCurrentUser().getUid();
                DocumentReference documentReference = firestore.collection("thread").document();
                Map<String, Object> thread = new HashMap<>();
                thread.put("userID", userID);
                thread.put("title", title);
                thread.put("message", message);
                documentReference.set(thread).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "onSuccess: user post has been stored"+ userID);
                    }
                });
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
            else {
                Toast.makeText(getActivity(), "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
