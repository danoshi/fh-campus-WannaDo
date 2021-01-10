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

import fh.campus.wannado.collections.chats.ChatCollection;
import fh.campus.wannado.collections.chats.ChatDocument;

import fh.campus.wannado.databinding.FragmentMessagesBinding;
import fh.campus.wannado.fragments.home.adapters.ChatDocumentAdapter;

public class MessagesFragment extends Fragment {

    private ArrayList<ChatDocument> items = new ArrayList<>();
    private FragmentMessagesBinding binding;
    private ChatDocumentAdapter chatDocumentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMessagesBinding.inflate(inflater,container, false);
        showMyMessages();
        return binding.getRoot();
    }

    private void showMyMessages(){
        items = new ArrayList<>();
        ChatCollection.getUserChats(task -> {
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                    ChatDocument chatDocument = queryDocumentSnapshot.toObject(ChatDocument.class);
                    items.add(chatDocument);
                    RecyclerView recyclerView = binding.MessagesRecyclerview;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    chatDocumentAdapter = new ChatDocumentAdapter(items, getContext());
                    recyclerView.setAdapter(chatDocumentAdapter);

                }
            }
        });
    }
}
