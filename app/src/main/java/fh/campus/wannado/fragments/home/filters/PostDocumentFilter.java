package fh.campus.wannado.fragments.home.filters;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import fh.campus.wannado.collections.post.PostDocument;
import fh.campus.wannado.fragments.home.adapters.PostDocumentAdapter;

public class PostDocumentFilter extends Filter {
    private final List<PostDocument> filteredPosts;
    private final PostDocumentAdapter adapter;
    private final List<PostDocument> allPosts;


    public PostDocumentFilter(List<PostDocument> postsList, PostDocumentAdapter adapter) {
        this.adapter = adapter;
        this.allPosts = postsList;
        this.filteredPosts = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredPosts.clear();
        final FilterResults results = new FilterResults();

        if(constraint == null || constraint.length() == 0){
            filteredPosts.addAll(allPosts);
        }

        else {
            String filterPattern = constraint.toString().toLowerCase().trim();

            for(PostDocument item : allPosts){
                if(item.getTitle().toLowerCase().contains(filterPattern)){
                    filteredPosts.add(item);
                }
            }
        }

        results.values = filteredPosts;
        results.count = filteredPosts.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setList(filteredPosts);
        adapter.notifyDataSetChanged();
    }
}
