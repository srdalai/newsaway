package in.sdtechnocrat.newsaway.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.sdtechnocrat.newsaway.R;
import in.sdtechnocrat.newsaway.adapters.HomeCategoryAdapter;
import in.sdtechnocrat.newsaway.adapters.HomeLargeCardAdapter;
import in.sdtechnocrat.newsaway.adapters.HomeSmallCardAdapter;
import in.sdtechnocrat.newsaway.api.GetDataService;
import in.sdtechnocrat.newsaway.api.RetrofitClientInstance;
import in.sdtechnocrat.newsaway.model.ApiData;
import in.sdtechnocrat.newsaway.model.Article;
import in.sdtechnocrat.newsaway.model.Category;
import in.sdtechnocrat.newsaway.utils.DatabaseHelper;
import in.sdtechnocrat.newsaway.utils.RecyclerTouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static in.sdtechnocrat.newsaway.model.Article.SAVE_TYPE_BOOKMARK;
import static in.sdtechnocrat.newsaway.model.Article.SAVE_TYPE_OFFLINE;
import static in.sdtechnocrat.newsaway.model.Article.SAVE_TYPE_TOTAL;
import static in.sdtechnocrat.newsaway.utils.Utilities.API_KEY;
import static in.sdtechnocrat.newsaway.utils.Utilities.STATUS_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ProgressDialog progressDoalog;
    Retrofit retrofitClient = RetrofitClientInstance.getRetrofitInstance();
    GetDataService getDataService;
    RecyclerView trendingRecycler;
    ArrayList<Article> articles = new ArrayList<>();
    ArrayList<Article> bigStoryArticles = new ArrayList<>();
    ArrayList<Category> categories = new ArrayList<>();
    RecyclerView categoriesList;
    RecyclerView bigStoryRecycler;
    HomeCategoryAdapter homeCategoryAdapter;
    HomeSmallCardAdapter homeSmallCardAdapter;
    HomeLargeCardAdapter homeLargeCardAdapter;
    String category = "general";
    DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        trendingRecycler = rootView.findViewById(R.id.trendingRecycler);
        categoriesList = rootView.findViewById(R.id.categoriesList);
        bigStoryRecycler = rootView.findViewById(R.id.bigStoryRecycler);

        databaseHelper = new DatabaseHelper(requireContext());

        /*homeCategoryAdapter = new HomeCategoryAdapter(requireContext(), articles);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        trendingRecycler.setLayoutManager(layoutManager);
        trendingRecycler.setAdapter(homeCategoryAdapter);*/


        trendingRecycler.addOnItemTouchListener(new RecyclerTouchListener(requireContext(), trendingRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(requireContext(), BrowserActivity.class);
                intent.putExtra("URL", articles.get(position).getUrl());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        categoriesList.addOnItemTouchListener(new RecyclerTouchListener(requireContext(), categoriesList, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                homeCategoryAdapter.setSelectPos(position);
                category = categories.get(position).getCategorySlug();
                //Toast.makeText(requireContext(), "Start " + categories.get(position).getCategoryName(), Toast.LENGTH_SHORT).show();
                prepareBigStory();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        getDataService = retrofitClient.create(GetDataService.class);

        prepareCategories();
        prepareBigStory();

        return rootView;
    }

    private void prepareBigStory() {
        bigStoryRecycler.invalidate();
        bigStoryArticles = new ArrayList<>();
        Call<ApiData> call = getDataService.getHeadlinesByCategory(API_KEY, "in", category);
        call.enqueue(new Callback<ApiData>() {
            @Override
            public void onResponse(Call<ApiData> call, Response<ApiData> response) {
                progressDoalog.dismiss();
                if (response.body().getStatus().equals(STATUS_OK)) {
                    bigStoryArticles = response.body().getArticles();
                    homeLargeCardAdapter = new HomeLargeCardAdapter(requireContext(), bigStoryArticles, new HomeLargeCardAdapter.HomeBigCardListener() {
                        @Override
                        public void readButtonOnClick(View view, Article article) {
                            if (article.isRead()) {
                                //article.setRead(false);
                            } else {
                                article.setRead(true);
                                databaseHelper.insertOfflineData(article, SAVE_TYPE_OFFLINE);
                                int size1 = databaseHelper.getAllOfflineArticles(SAVE_TYPE_OFFLINE).size();
                                int size2 = databaseHelper.getAllOfflineArticles(SAVE_TYPE_BOOKMARK).size();
                                int size3 = databaseHelper.getAllOfflineArticles(SAVE_TYPE_TOTAL).size();
                                Log.d("DATA SIZE", "Offline:- " + size1 + ", Bookmark:- " + size2 + ", Total:- " + size3);
                            }

                        }

                        @Override
                        public void bookmarkButtonOnClick(View view, Article article) {
                            if (article.isBookmarked()) {
                                //article.setFavorite(false);
                            } else {
                                article.setBookmarked(true);
                                databaseHelper.insertOfflineData(article, SAVE_TYPE_BOOKMARK);
                                int size1 = databaseHelper.getAllOfflineArticles(SAVE_TYPE_OFFLINE).size();
                                int size2 = databaseHelper.getAllOfflineArticles(SAVE_TYPE_BOOKMARK).size();
                                int size3 = databaseHelper.getAllOfflineArticles(SAVE_TYPE_TOTAL).size();
                                Log.d("DATA SIZE", "Offline:- " + size1 + ", Bookmark:- " + size2 + ", Total:- " + size3);
                            }
                        }

                        @Override
                        public void bigImageOnClick(View view, Article article) {
                            Intent intent = new Intent(requireContext(), BrowserActivity.class);
                            intent.putExtra("URL", article.getUrl());
                            startActivity(intent);
                        }
                    });
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
                    bigStoryRecycler.setLayoutManager(layoutManager);
                    bigStoryRecycler.setAdapter(homeLargeCardAdapter);
                }
                prepareTrending();

            }

            @Override
            public void onFailure(@NotNull Call<ApiData> call, @NotNull Throwable t) {
                progressDoalog.dismiss();
                prepareTrending();

            }
        });
    }

    private void prepareTrending() {
        progressDoalog.show();
        trendingRecycler.invalidate();
        Call<ApiData> call = getDataService.getHeadlinesByCountry(API_KEY, "in");
        call.enqueue(new Callback<ApiData>() {
            @Override
            public void onResponse(@NotNull Call<ApiData> call, @NotNull Response<ApiData> response) {
                progressDoalog.dismiss();
                if (response.body().getStatus().equals(STATUS_OK)) {
                    articles = response.body().getArticles();
                    homeSmallCardAdapter = new HomeSmallCardAdapter(requireContext(), articles);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
                    trendingRecycler.setLayoutManager(layoutManager);
                    trendingRecycler.setAdapter(homeSmallCardAdapter);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ApiData> call, @NotNull Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(requireContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareCategories() {
        categoriesList.invalidate();
        categories.clear();

        Category category = new Category("General", "general");
        categories.add(category);

        category = new Category("Business", "business");
        categories.add(category);

        category = new Category("Entertainment", "entertainment");
        categories.add(category);

        category = new Category("Health", "health");
        categories.add(category);

        category = new Category("Science", "science");
        categories.add(category);

        category = new Category("Sports", "sports");
        categories.add(category);

        category = new Category("Technology", "technology");
        categories.add(category);


        homeCategoryAdapter = new HomeCategoryAdapter(requireContext(), categories);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        categoriesList.setLayoutManager(layoutManager);
        categoriesList.setAdapter(homeCategoryAdapter);
    }

}
