package fh.campus.wannado.fragments.home.adapters;

import android.content.Context;
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
import fh.campus.wannado.collections.post.PostDocument;

public class PostDocumentAdapter extends RecyclerView.Adapter<PostDocumentAdapter.ViewHolder> implements Filterable {

    private LayoutInflater layoutInflater;
    private List<PostDocument> postDocuments;
    private List<PostDocument> postDocumentsFull;
    private final Context context;

    public PostDocumentAdapter(Context context, List<PostDocument> data){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.postDocuments = data;
        postDocumentsFull = new ArrayList<>(postDocuments);
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
        holder.setOnClickListener(e -> Toast.makeText(context, "Clicked on " + currentItem.getTitle(), Toast.LENGTH_SHORT).show());
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
        return postFilter;
    }
    private Filter postFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PostDocument> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(postDocumentsFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(PostDocument item : postDocumentsFull){
                    if(item.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            postDocuments.clear();
            postDocuments.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
