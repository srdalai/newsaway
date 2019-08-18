package in.sdtechnocrat.newsaway.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import in.sdtechnocrat.newsaway.R;
import in.sdtechnocrat.newsaway.adapters.CountryAdapter;
import in.sdtechnocrat.newsaway.api.GetDataService;
import in.sdtechnocrat.newsaway.api.RetrofitClientInstance;
import in.sdtechnocrat.newsaway.model.Country;
import in.sdtechnocrat.newsaway.utils.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConfigActivity extends AppCompatActivity {

    ListView countryListView;

    Retrofit retrofitClient;
    GetDataService getDataService;
    String countryBaseUrl = "https://restcountries.eu/rest/v2/";
    ArrayList<Country> countries;
    CountryAdapter countryAdapter;
    ProgressDialog progressDialog;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        retrofitClient = RetrofitClientInstance.getRetrofitInstanceForUrl(countryBaseUrl);
        getDataService = retrofitClient.create(GetDataService.class);
        preferenceManager = new PreferenceManager(this);

        countryListView = findViewById(R.id.countryListView);

        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ConfigActivity.this, countries.get(position).getCountryName()+" is Selected as Default Country", Toast.LENGTH_SHORT).show();
                preferenceManager.setCountry(countries.get(position).getCountryName(), countries.get(position).getIsoCountryCode());
                Intent intent = new Intent(ConfigActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Data");
        makeApiCall();
    }

    private void makeApiCall() {
        progressDialog.show();
        Call<ArrayList<Country>> apiCall = getDataService.getAllCountries();
        apiCall.enqueue(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse(Call<ArrayList<Country>> call, Response<ArrayList<Country>> response) {
                Log.d("Size", response.body().size()+"");
                countries = response.body();
                countryAdapter = new CountryAdapter(ConfigActivity.this, countries);
                countryListView.setAdapter(countryAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<Country>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
