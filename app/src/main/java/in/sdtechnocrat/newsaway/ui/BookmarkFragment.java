package in.sdtechnocrat.newsaway.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.sdtechnocrat.newsaway.R;
import in.sdtechnocrat.newsaway.adapters.HomeSmallCardAdapter;
import in.sdtechnocrat.newsaway.utils.DatabaseHelper;

import static in.sdtechnocrat.newsaway.model.Article.SAVE_TYPE_BOOKMARK;
import static in.sdtechnocrat.newsaway.model.Article.SAVE_TYPE_OFFLINE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment {


    public BookmarkFragment() {
        // Required empty public constructor
    }

    RecyclerView recycler;
    DatabaseHelper databaseHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bookmark, container, false);
        recycler = rootView.findViewById(R.id.recycler);
        databaseHelper = new DatabaseHelper(requireContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        HomeSmallCardAdapter adapter = new HomeSmallCardAdapter(requireContext(), databaseHelper.getAllOfflineArticles(SAVE_TYPE_BOOKMARK));
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
        return rootView;
    }

}
