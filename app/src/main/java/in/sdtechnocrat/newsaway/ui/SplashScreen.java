package in.sdtechnocrat.newsaway.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

import in.sdtechnocrat.newsaway.R;
import in.sdtechnocrat.newsaway.api.GetDataService;
import in.sdtechnocrat.newsaway.api.RetrofitClientInstance;
import in.sdtechnocrat.newsaway.model.ApiData;
import in.sdtechnocrat.newsaway.model.Source;
import in.sdtechnocrat.newsaway.utils.DatabaseHelper;
import in.sdtechnocrat.newsaway.utils.PreferenceManager;
import in.sdtechnocrat.newsaway.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static in.sdtechnocrat.newsaway.utils.Utilities.API_KEY;
import static in.sdtechnocrat.newsaway.utils.Utilities.STATUS_OK;

public class SplashScreen extends AppCompatActivity {

    Retrofit retrofitClient = RetrofitClientInstance.getRetrofitInstance();
    GetDataService getDataService;
    PreferenceManager preferenceManager;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        preferenceManager = new PreferenceManager(this);
        databaseHelper = new DatabaseHelper(this);

        getDataService = retrofitClient.create(GetDataService.class);

        String updatedAt = preferenceManager.getSourceUpdatedTime();
        if (updatedAt.equals("") || Utilities.getTimeDiffInDays(updatedAt, Utilities.DEFAULT_TIME_FORMAT) > 7) {
            Log.d("SATYA", "Updating Source");
            makeApiCall();
        } else {
            Log.d("SATYA", "Sources are already up-to-date");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(SplashScreen.this, ConfigActivity.class);
                    if (preferenceManager.isCountrySet()) {
                        intent = new Intent(SplashScreen.this, MainActivity.class);
                    }
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }


    }

    public void makeApiCall() {
        Call<ApiData> call = getDataService.getSourcesByLang(API_KEY, "en");
        call.enqueue(new Callback<ApiData>() {
            @Override
            public void onResponse(Call<ApiData> call, Response<ApiData> response) {
                if (response.body().getStatus().equals(STATUS_OK)) {
                    ArrayList<Source> sources = response.body().getSources();
                    saveSourcesToDataBase(sources);
                }
            }

            @Override
            public void onFailure(Call<ApiData> call, Throwable t) {

            }
        });
    }

    private void saveSourcesToDataBase(ArrayList<Source> sources) {
        databaseHelper.deleteDatabase();
        for (Source source : sources) {
            databaseHelper.insertSource(source);
        }
        preferenceManager.setSourceUpdatedAt();
        startActivity(new Intent(SplashScreen.this, ConfigActivity.class));
        finish();
    }
}
