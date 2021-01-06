package fh.campus.wannado;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import fh.campus.wannado.collections.users.UsersCollection;
import fh.campus.wannado.collections.users.UsersDocument;
import fh.campus.wannado.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseAuth mFirebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mFirebaseAuth = FirebaseAuth.getInstance();
        setUserInfo();
        setButtonsListeners();
        setEmailNotVerifiedButton();

        return view;
    }

    private void setEmailNotVerifiedButton() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        if (!user.isEmailVerified()) {
            binding.textViewNotVerified.setVisibility(View.VISIBLE);
            binding.buttonResendCode.setVisibility(View.VISIBLE);

            binding.buttonResendCode.setOnClickListener(
                    v -> user.sendEmailVerification()
                            .addOnSuccessListener(e -> Toast.makeText(v.getContext(), "Email", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(v.getContext(), "Cannot send", Toast.LENGTH_SHORT).show())
            );
        }
    }

    private void setButtonsListeners() {
        binding.buttonLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });
    }

    private void setUserInfo() {
        showLoadingCircle();
        UsersCollection.getCurrentUser(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    UsersDocument usersDocument = UsersCollection.getUserDocumentOf(document);
                    binding.textEmail.setText(usersDocument.getEmail());
                    binding.textUsername.setText(usersDocument.getUsername());
                    binding.loadingPanel.setVisibility(View.GONE);
                }
            }
            hideLoadingCircle();
        });
    }

    private void showLoadingCircle() {
        binding.loadingPanel.setVisibility(View.VISIBLE);
    }

    private void hideLoadingCircle() {
        binding.loadingPanel.setVisibility(View.GONE);
    }
}

