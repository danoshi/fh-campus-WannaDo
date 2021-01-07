package fh.campus.wannado;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import fh.campus.wannado.collections.users.PostCollection;
import fh.campus.wannado.databinding.FragmentMypostsBinding;
import fh.campus.wannado.databinding.FragmentProfileBinding;

public class MyPostsFragment extends Fragment {

    private FragmentMypostsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMypostsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    public void showMyPosts(){

        PostCollection.getCurrentUser(task -> {
            if (task.isSuccessful()){

            }
        });
    }

}
