package fh.campus.wannado.fragments.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import fh.campus.wannado.R;
import fh.campus.wannado.databinding.HomeFragmentBinding;
import fh.campus.wannado.fragments.home.adapters.PostDocumentAdapter;
import fh.campus.wannado.collections.post.PostCollection;
import fh.campus.wannado.collections.post.PostDocument;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;
    private ArrayList<PostDocument> items = new ArrayList<>();
    private PostDocumentAdapter postDocumentAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        initRecyclerView();
        initSearchBar();
        return binding.getRoot();
    }


    private void initSearchBar() {
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                postDocumentAdapter.filterList(newText);
                return true;
            }
        });
    }


    private void initRecyclerView() {
        items = new ArrayList<>();
        PostCollection.getAllPosts(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    PostDocument postDocument = PostCollection.postOf(document);
                    items.add(postDocument);
                    RecyclerView recyclerView = binding.postsRecyclerview;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    postDocumentAdapter = new PostDocumentAdapter(getContext(), items);
                    recyclerView.setAdapter(postDocumentAdapter);
                }
            }
        });
    }

}
