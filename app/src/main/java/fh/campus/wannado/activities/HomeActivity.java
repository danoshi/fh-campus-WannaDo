package fh.campus.wannado.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import fh.campus.wannado.collections.post.PostCollection;
import fh.campus.wannado.collections.post.PostDocument;
import fh.campus.wannado.fragments.home.adapters.PostDocumentAdapter;
import fh.campus.wannado.R;
import fh.campus.wannado.fragments.home.AddPostsFragment;
import fh.campus.wannado.fragments.home.MessagesFragment;
import fh.campus.wannado.fragments.home.MyPostsFragment;
import fh.campus.wannado.fragments.home.ProfileFragment;
import fh.campus.wannado.fragments.home.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    private PostDocumentAdapter postDocumentAdapter;
    private List<PostDocument> postDocuments;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        cardView = findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(HomeActivity.this, ThreadDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_star:
                        selectedFragment = new MyPostsFragment();
                        break;
                    case R.id.nav_add:
                        selectedFragment = new AddPostsFragment();
                        break;
                    case R.id.nav_messages:
                        selectedFragment = new MessagesFragment();
                        break;
                    case R.id.nav_account:
                        selectedFragment = new ProfileFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();


                return true;
            };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search_top);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                postDocuments = new ArrayList<>();
 //               PostCollection.getAllPosts(task -> {
 //                   if(task.isSuccessful()){
  //                      for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
   //                         PostDocument postDocument = PostCollection.postOf(documentSnapshot);
    //                        postDocuments.add(postDocument);
                            postDocumentAdapter = new PostDocumentAdapter(getApplicationContext(), postDocuments);
                            postDocumentAdapter.getFilter().filter(newText);
      //                  }
      //              }
       //         });
                return false;
            }
        });

        return true;
    }
}