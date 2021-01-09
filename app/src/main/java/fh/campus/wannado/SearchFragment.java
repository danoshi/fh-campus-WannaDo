package fh.campus.wannado;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import fh.campus.wannado.collections.users.PostCollection;
import fh.campus.wannado.collections.users.PostDocument;
import fh.campus.wannado.databinding.SearchFragmentBinding;

public class SearchFragment  extends Fragment {

    private SearchFragmentBinding binding;
    private Adapter adapter;
    ArrayList<PostDocument> items;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SearchFragmentBinding.inflate(inflater, container, false);
        initRecyclerView();
        clickCardView();
        return binding.getRoot();
    }

    private void clickCardView(){
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), onClickThreadActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initRecyclerView(){
        items = new ArrayList<>();
        PostCollection.getAllPosts(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    PostDocument postDocument = PostCollection.postOf(document);
                    items.add(postDocument);
                    RecyclerView recyclerView = binding.postsRecyclerview;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    Adapter adapter = new Adapter(getContext(), items);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }
}
