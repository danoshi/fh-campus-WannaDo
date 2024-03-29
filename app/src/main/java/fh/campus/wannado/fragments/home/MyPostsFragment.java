package fh.campus.wannado.fragments.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import fh.campus.wannado.fragments.home.adapters.PostDocumentAdapter;
import fh.campus.wannado.collections.post.PostCollection;
import fh.campus.wannado.collections.post.PostDocument;
import fh.campus.wannado.databinding.FragmentMypostsBinding;

public class MyPostsFragment extends Fragment {

    private FragmentMypostsBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMypostsBinding.inflate(inflater, container, false);
        showMyPosts();
        return binding.getRoot();
    }

    private void showMyPosts(){
        ArrayList<PostDocument> items;
        items = new ArrayList<>();

        PostCollection.getCurrentUserPosts(task -> {
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    PostDocument postDocument = PostCollection.postOf(documentSnapshot);
                    items.add(postDocument);
                    RecyclerView recyclerView = binding.myMessagesRecyclerview;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    PostDocumentAdapter postDocumentAdapter = new PostDocumentAdapter(getContext(), items);
                    recyclerView.setAdapter(postDocumentAdapter);
                }
            }
        });
    }

}
