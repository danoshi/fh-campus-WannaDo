package fh.campus.wannado;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fh.campus.wannado.collections.users.PostDocument;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {

    private LayoutInflater layoutInflater;
    private List<PostDocument> postDocuments;
    private List<PostDocument> postDocumentsFull;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    Adapter(Context context, List<PostDocument> data){
        this.layoutInflater = LayoutInflater.from(context);
        this.postDocuments = data;
        postDocumentsFull = new ArrayList<>(postDocuments);
    }




    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_view, viewGroup, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostDocument currentItem = postDocuments.get(position);
        holder.textTitle.setText(currentItem.getTitle());
        holder.textDescription.setText(currentItem.getMessage());

    }

    @Override
    public int getItemCount() {
        return postDocuments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textDescription;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDesc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
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
