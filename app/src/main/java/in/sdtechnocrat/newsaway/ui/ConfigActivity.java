package in.sdtechnocrat.newsaway.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import in.sdtechnocrat.newsaway.R;
import in.sdtechnocrat.newsaway.adapters.CountryAdapter;
import in.sdtechnocrat.newsaway.api.GetDataService;
import in.sdtechnocrat.newsaway.api.RetrofitClientInstance;
import in.sdtechnocrat.newsaway.model.Country;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConfigActivity extends AppCompatActivity {

    RecyclerView countryRecycler;

    Retrofit retrofitClient;
    GetDataService getDataService;
    String countryBaseUrl = "https://restcountries.eu/rest/v2/";
    ArrayList<Country> countries;
    CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        retrofitClient = RetrofitClientInstance.getRetrofitInstanceForUrl(countryBaseUrl);
        getDataService = retrofitClient.create(GetDataService.class);

        countryRecycler = findViewById(R.id.countryRecycler);


        makeApiCall();
    }

    private void makeApiCall() {
        Call<ArrayList<Country>> apiCall = getDataService.getAllCountries();
        apiCall.enqueue(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse(Call<ArrayList<Country>> call, Response<ArrayList<Country>> response) {
                Log.d("Size", response.body().size()+"");
                countries = response.body();
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ConfigActivity.this);
                countryAdapter = new CountryAdapter(ConfigActivity.this, countries);
                countryRecycler.setLayoutManager(layoutManager);
                countryRecycler.setAdapter(countryAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Country>> call, Throwable t) {

            }
        });
    }
}
