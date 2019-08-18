package in.sdtechnocrat.newsaway.api;

import java.util.ArrayList;

import in.sdtechnocrat.newsaway.model.ApiData;
import in.sdtechnocrat.newsaway.model.Country;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    /*
        https://newsapi.org/v2/top-headlines?country=us&apiKey=074509920a9349e6a56b441eaec67d35
     */
    @GET("top-headlines")
    Call<ApiData> getHeadlinesByCountry(@Query("apiKey") String apiKey, @Query("country") String country);

    /*
        https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=074509920a9349e6a56b441eaec67d35
     */
    @GET("top-headlines")
    Call<ApiData> getHeadlinesBySource(@Query("apiKey") String apiKey, @Query("sources") String sources);

    /*
        https://newsapi.org/v2/top-headlines?country=de&category=business&apiKey=074509920a9349e6a56b441eaec67d35
     */
    @GET("top-headlines")
    Call<ApiData> getHeadlinesByCategory(@Query("apiKey") String apiKey, @Query("country") String country, @Query("category") String category);

    /*
        https://newsapi.org/v2/top-headlines?q=trump&apiKey=074509920a9349e6a56b441eaec67d35
     */
    @GET("top-headlines")
    Call<ApiData> searchHeadlines(@Query("apiKey") String apiKey, @Query("q") String query);

    /*
        https://newsapi.org/v2/everything?q=bitcoin&apiKey=074509920a9349e6a56b441eaec67d35
     */
    @GET("everything")
    Call<ApiData> searchEverything(@Query("apiKey") String apiKey, @Query("q") String query);

    /*
        https://newsapi.org/v2/everything?q=apple&from=2019-06-21&to=2019-06-21&sortBy=popularity&apiKey=074509920a9349e6a56b441eaec67d35
     */
    @GET("everything")
    Call<ApiData> searchSortFilter(@Query("apiKey") String apiKey, @Query("q") String query, @Query("from") String from, @Query("to") String to, @Query("sortBy") String sortBy);

    /*
        https://newsapi.org/v2/everything?domains=wsj.com,nytimes.com&apiKey=074509920a9349e6a56b441eaec67d35
     */
    @GET("everything")
    Call<ApiData> searchByDomain(@Query("apiKey") String apiKey, @Query("domains") String commaSeparatedDomains);

    /*
        https://newsapi.org/v2/sources?apiKey=074509920a9349e6a56b441eaec67d35
     */
    @GET("sources")
    Call<ApiData> getAllSources(@Query("apiKey") String apiKey);

    /*
        https://newsapi.org/v2/sources?language=en&apiKey=074509920a9349e6a56b441eaec67d35
     */
    @GET("sources")
    Call<ApiData> getSourcesByLang(@Query("apiKey") String apiKey, @Query("language") String language);

    /*
        https://newsapi.org/v2/sources?language=en&country=us&apiKey=074509920a9349e6a56b441eaec67d35
     */
    @GET("sources")
    Call<ApiData> getSourcesByLangCountry(@Query("apiKey") String apiKey, @Query("language") String language, @Query("country") String country);

    /*
        Get country data
     */
    @GET("all")
    Call<ArrayList<Country>> getAllCountries();
}
