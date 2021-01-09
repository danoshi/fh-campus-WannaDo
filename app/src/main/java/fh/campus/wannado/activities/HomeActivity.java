package fh.campus.wannado.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fh.campus.wannado.fragments.home.adapters.PostDocumentAdapter;
import fh.campus.wannado.R;
import fh.campus.wannado.fragments.home.AddPostsFragment;
import fh.campus.wannado.fragments.home.MessagesFragment;
import fh.campus.wannado.fragments.home.MyPostsFragment;
import fh.campus.wannado.fragments.home.ProfileFragment;
import fh.campus.wannado.fragments.home.SearchFragment;

public class HomeActivity extends AppCompatActivity {

    private PostDocumentAdapter postDocumentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.nav_search:
                        selectedFragment = new SearchFragment();
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
                postDocumentAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}