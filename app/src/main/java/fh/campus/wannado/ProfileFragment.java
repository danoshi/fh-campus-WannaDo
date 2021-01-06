package fh.campus.wannado;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import fh.campus.wannado.collections.users.UsersDocument;
import fh.campus.wannado.collections.users.UsersCollection;
import fh.campus.wannado.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setUserInfo();
        setButtonsListeners();
        return view;
    }

    private void setButtonsListeners(){
        binding.buttonLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });
    }

    private void setUserInfo() {
        showLoadingCircle();
        UsersCollection.getCurrentUser(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                if(document.exists()){
                    UsersDocument usersDocument = UsersCollection.getUserDocumentOf(document);
                    binding.textEmail.setText(usersDocument.getEmail());
                    binding.textUsername.setText(usersDocument.getUsername());
                    binding.loadingPanel.setVisibility(View.GONE);
                }
            }
            hideLoadingCircle();
        });
    }

    private void showLoadingCircle(){
        binding.loadingPanel.setVisibility(View.VISIBLE);
    }

    private void hideLoadingCircle(){
        binding.loadingPanel.setVisibility(View.GONE);
    }
}

