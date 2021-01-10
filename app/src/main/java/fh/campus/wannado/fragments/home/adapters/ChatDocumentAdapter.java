package fh.campus.wannado.fragments.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fh.campus.wannado.R;
import fh.campus.wannado.activities.ChatDetailsActivity;
import fh.campus.wannado.collections.chats.ChatDocument;

public class ChatDocumentAdapter extends RecyclerView.Adapter<ChatDocumentAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<ChatDocument> chatDocuments;
    private final Context context;

    public ChatDocumentAdapter(List<ChatDocument> chatDocuments, Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.chatDocuments = chatDocuments;
        this.context = context;
    }

    public void setList(List<ChatDocument> list){
        this.chatDocuments = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatDocument currentItem = chatDocuments.get(position);
        holder.username.setText(currentItem.getParticipantsID().get(0));
        holder.message.setText(currentItem.getLastMessage());
        holder.setOnClickListener(e -> {
            Intent intent;
            intent = new Intent(context, ChatDetailsActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chatDocuments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView username, message;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.textTitle);
            message = itemView.findViewById(R.id.textDesc);
        }
        public void setOnClickListener(View.OnClickListener onClickListener){
            itemView.setOnClickListener(onClickListener);
        }
    }
}
