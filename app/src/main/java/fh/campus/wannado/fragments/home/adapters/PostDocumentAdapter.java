package fh.campus.wannado.fragments.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fh.campus.wannado.R;
import fh.campus.wannado.activities.HomeActivity;
import fh.campus.wannado.activities.ThreadDetailsActivity;
import fh.campus.wannado.collections.post.PostDocument;
import fh.campus.wannado.fragments.home.filters.PostDocumentFilter;

public class PostDocumentAdapter extends RecyclerView.Adapter<PostDocumentAdapter.ViewHolder> implements Filterable {

    private final LayoutInflater layoutInflater;
    private List<PostDocument> postDocuments;
    private final Context context;
    private final PostDocumentFilter filter;


    public PostDocumentAdapter(Context context, List<PostDocument> postDocuments){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.postDocuments = postDocuments;
        this.filter = new PostDocumentFilter(postDocuments, this);
    }

    public void setList(List<PostDocument> list) {
        this.postDocuments = list;
    }

    public void filterList(String text) {
        filter.filter(text);
    }

    @NonNull
    @Override
    public PostDocumentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostDocument currentItem = postDocuments.get(position);
        holder.textTitle.setText(currentItem.getTitle());
        holder.textDescription.setText(currentItem.getMessage());
        holder.setOnClickListener(e -> {
            Intent intent;
            intent = new Intent(context, ThreadDetailsActivity.class);
            intent.putExtra("THREAD", currentItem);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return postDocuments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDesc);
        }

        public void setOnClickListener(View.OnClickListener onClickListener){
            itemView.setOnClickListener(onClickListener);
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

}
