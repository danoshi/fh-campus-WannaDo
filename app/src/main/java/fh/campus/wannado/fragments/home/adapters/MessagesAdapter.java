package fh.campus.wannado.fragments.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fh.campus.wannado.R;
import fh.campus.wannado.collections.messages.Message;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<Message> messages;
    private final Context context;


    public MessagesAdapter(Context context, List<Message> messages){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.message_view, viewGroup, false);
        return new MessagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message currentItem = messages.get(position);
        holder.sender.setText(currentItem.getSender().getNickname());
        holder.message.setText(currentItem.getContent());
    }

    public void setMessages(List<Message> messages){
        this.messages = messages;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView sender, message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.sender);
            message = itemView.findViewById(R.id.message);
        }
    }

}
