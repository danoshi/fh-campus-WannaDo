package fh.campus.wannado;

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

import java.util.ArrayList;

import fh.campus.wannado.collections.users.PostCollection;
import fh.campus.wannado.collections.users.PostDocument;
import fh.campus.wannado.databinding.SearchFragmentBinding;

public class SearchFragment  extends Fragment {

    private SearchFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SearchFragmentBinding.inflate(inflater, container, false);
        initRecyclerView();
        return binding.getRoot();
    }


    private void initRecyclerView(){
        ArrayList<PostDocument> items;
        items = new ArrayList<>();

        PostCollection.getCurrentUser(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();
                if(documentSnapshot.exists()){
                    PostDocument postDocument = PostCollection.getUserMessage(documentSnapshot);
                    items.add(postDocument);

                }
            }
        });

/*        items.add("First CardView Item");
        items.add("Second CardView Item");
        items.add("Third CardView Item");
        items.add("Fourth CardView Item");
        items.add("Fifth CardView Item");
        items.add("Sixth CardView Item");*/

        RecyclerView recyclerView = binding.postsRecyclerview;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Adapter adapter = new Adapter(getContext(), items);
        recyclerView.setAdapter(adapter);
    }
}
