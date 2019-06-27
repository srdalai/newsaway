package in.sdtechnocrat.newsaway.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.sdtechnocrat.newsaway.R;
import in.sdtechnocrat.newsaway.model.Article;
import in.sdtechnocrat.newsaway.utils.DatabaseHelper;
import in.sdtechnocrat.newsaway.utils.Utilities;

public class HomeSmallCardAdapter extends RecyclerView.Adapter<HomeSmallCardAdapter.HomeSmallCardViewHolder> {

    ArrayList<Article> articles;
    Context mContext;
    DatabaseHelper databaseHelper;

    public HomeSmallCardAdapter( Context mContext, ArrayList<Article> articles) {
        this.articles = articles;
        this.mContext = mContext;
        databaseHelper = new DatabaseHelper(mContext);
    }

    @NonNull
    @Override
    public HomeSmallCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_home_small_card, parent, false);
        return new HomeSmallCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSmallCardViewHolder holder, int position) {
        Article article = articles.get(position);

        holder.txtDesc.setText(article.getTitle());

        String date = article.getPublishedAt();
        String midDate = date.split("Z")[0];
        String finaldate = midDate.split("T")[0] + " " + midDate.split("T")[1];
        holder.txtDate.setText(Utilities.getTimeDiff(finaldate, "yyyy-MM-dd HH:mm:ss"));

        String sourceID = article.getSource().getId();
        String sourceName = article.getSource().getName();

        if (sourceName == null && sourceName.trim().equals("")) {
            sourceName = "Anonymous";
        }
        holder.sourceChip.setText(sourceName);

        Picasso.get().load(article.getUrlToImage())
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.articleImage);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class HomeSmallCardViewHolder extends RecyclerView.ViewHolder {

        ImageView articleImage;
        TextView txtCat, txtDesc, txtDate;
        Chip sourceChip;

        public HomeSmallCardViewHolder(@NonNull View itemView) {
            super(itemView);
            articleImage = itemView.findViewById(R.id.articleImage);
            txtCat = itemView.findViewById(R.id.txtCat);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtDate = itemView.findViewById(R.id.txtDate);
            sourceChip = itemView.findViewById(R.id.sourceChip);
        }
    }
}
