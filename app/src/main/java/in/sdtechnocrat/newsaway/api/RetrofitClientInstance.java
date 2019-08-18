package in.sdtechnocrat.newsaway.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static in.sdtechnocrat.newsaway.utils.Utilities.BASE_URL;

public class RetrofitClientInstance {

    private static Retrofit singleRetrofit;
    private static Retrofit defaultRetrofit;

    public static Retrofit getRetrofitInstance() {
        if (defaultRetrofit == null) {
            defaultRetrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return defaultRetrofit;
    }

    public static Retrofit getRetrofitInstanceForUrl(String baseUrl) {
        if (singleRetrofit == null) {
            singleRetrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return singleRetrofit;
    }
}
