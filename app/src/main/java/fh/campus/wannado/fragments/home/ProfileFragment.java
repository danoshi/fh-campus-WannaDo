package fh.campus.wannado.fragments.home;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import fh.campus.wannado.activities.MainActivity;
import fh.campus.wannado.collections.users.UsersCollection;
import fh.campus.wannado.collections.users.UsersDocument;
import fh.campus.wannado.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseAuth mFirebaseAuth;
    StorageReference storageReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mFirebaseAuth = FirebaseAuth.getInstance();

        setUserInfo();
        setButtonsListeners();
        setEmailNotVerifiedButton();
        resetPasswordButton();
        changeProfileImage();
        //Load picture when starting Profile fragment
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+mFirebaseAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ImageView imageView = binding.imageViewProfileImage;
                Picasso.get().load(uri).into(imageView);
            }
        });
        return view;
    }

    private void changeProfileImage(){
        binding.buttonChangeProfile.setOnClickListener(v -> {
            Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGalleryIntent, 1000);

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                //binding.imageViewProfileImage.setImageURI(imageUri);
                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri){
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef = storageReference.child("users/"+mFirebaseAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImageView imageView = binding.imageViewProfileImage;
                        Picasso.get().load(uri).into(imageView);
                    }
                });
                //Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetPasswordButton(){
        binding.buttonResetPassword.setOnClickListener(v -> {
            final EditText resetPassword = new EditText(v.getContext());

            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Change Password");
            passwordResetDialog.setMessage("Enter a new password");
            passwordResetDialog.setView(resetPassword);

            passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newPassword = resetPassword.getText().toString();
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "Password reset successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(v.getContext(), "Password reset failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            passwordResetDialog.create().show();
        });
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

