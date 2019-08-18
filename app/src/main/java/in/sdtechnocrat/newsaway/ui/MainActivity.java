package in.sdtechnocrat.newsaway.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import in.sdtechnocrat.newsaway.R;
import in.sdtechnocrat.newsaway.api.GetDataService;
import in.sdtechnocrat.newsaway.api.RetrofitClientInstance;
import in.sdtechnocrat.newsaway.model.ApiData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static in.sdtechnocrat.newsaway.utils.Utilities.API_KEY;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        loadFragment(new HomeFragment());

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                switch (item.getItemId()) {
                    case R.id.home:
                        if (!(currentFragment instanceof HomeFragment)) {
                            fragment = new HomeFragment();
                        }
                        break;
                    case R.id.reads:
                        if (!(currentFragment instanceof ReadsFragment)) {
                            fragment = new ReadsFragment();
                        }
                        break;
                    case R.id.bookmarks:
                        if (!(currentFragment instanceof BookmarkFragment)) {
                            fragment = new BookmarkFragment();
                        }
                        break;
                    case R.id.profile:
                        if (!(currentFragment instanceof ProfileFragment)) {
                            fragment = new ProfileFragment();
                        }
                        break;
                }
                loadFragment(fragment);
                return true;
            }
        });


    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Conform Exit!");
        builder.setMessage("Are you sure ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
}
