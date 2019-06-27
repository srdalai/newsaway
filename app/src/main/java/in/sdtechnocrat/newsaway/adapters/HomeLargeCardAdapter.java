package in.sdtechnocrat.newsaway.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.sdtechnocrat.newsaway.R;
import in.sdtechnocrat.newsaway.model.Article;
import in.sdtechnocrat.newsaway.utils.DatabaseHelper;
import in.sdtechnocrat.newsaway.utils.Utilities;

import static in.sdtechnocrat.newsaway.model.Article.SAVE_TYPE_BOOKMARK;
import static in.sdtechnocrat.newsaway.model.Article.SAVE_TYPE_OFFLINE;

public class HomeLargeCardAdapter extends RecyclerView.Adapter<HomeLargeCardAdapter.HomeLargeCardViewHolder> {

    ArrayList<Article> articles;
    Context mContext;
    DatabaseHelper databaseHelper;
    HomeBigCardListener bigCardListener;
    ArrayList<String> offlineURLs = new ArrayList<>();
    ArrayList<String> bookmarkURLs = new ArrayList<>();

    public HomeLargeCardAdapter( Context mContext, ArrayList<Article> articles, HomeBigCardListener bigCardListener) {
        this.articles = articles;
        this.mContext = mContext;
        this.bigCardListener = bigCardListener;
        databaseHelper = new DatabaseHelper(mContext);
        offlineURLs = databaseHelper.getAllOfflineURLs(SAVE_TYPE_OFFLINE);
        bookmarkURLs = databaseHelper.getAllOfflineURLs(SAVE_TYPE_BOOKMARK);
    }

    @NonNull
    @Override
    public HomeLargeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_home_large_card, parent, false);
        return new HomeLargeCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeLargeCardViewHolder holder, final int position) {
        final Article article = articles.get(position);

        holder.txtDesc.setText(article.getTitle());
        holder.txtAuthorName.setText(article.getSource().getName());

        String date = article.getPublishedAt();
        String midDate = date.split("Z")[0];
        String finaldate = midDate.split("T")[0] + " " + midDate.split("T")[1];
        holder.txtPublishDate.setText(Utilities.formatDate(finaldate, "yyyy-MM-dd HH:mm:ss", "dd MMM yyyy"));

        holder.txtAuthorName.setSelected(true);


        Picasso.get().load(article.getUrlToImage())
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.articleImage);

        Picasso.get().load(R.drawable.author_image).into(holder.authorImage);

        if (offlineURLs.size() > 0 && offlineURLs.contains(article.getUrl())) {
            article.setRead(true);
        }

        if (bookmarkURLs.size() > 0 && bookmarkURLs.contains(article.getUrl())) {
            article.setBookmarked(true);
        }

        if (article.isRead()) {
            holder.imageVIewRead.setColorFilter(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            holder.imageVIewRead.setColorFilter(mContext.getResources().getColor(R.color.grey));
        }

        if (article.isBookmarked()) {
            holder.imageVIewBookmark.setColorFilter(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            holder.imageVIewBookmark.setColorFilter(mContext.getResources().getColor(R.color.grey));
        }

        holder.articleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigCardListener.bigImageOnClick(v, article);
                notifyDataSetChanged();
            }
        });

        holder.imageVIewBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigCardListener.bookmarkButtonOnClick(v, article);
                notifyDataSetChanged();
            }
        });

        holder.imageVIewRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigCardListener.readButtonOnClick(v, article);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class HomeLargeCardViewHolder extends RecyclerView.ViewHolder {

        ImageView articleImage, authorImage, imageVIewBookmark, imageVIewRead;
        TextView txtDesc, txtPublishDate, txtAuthorName;
        public HomeLargeCardViewHolder(@NonNull View itemView) {
            super(itemView);
            articleImage = itemView.findViewById(R.id.articleImage);
            txtAuthorName = itemView.findViewById(R.id.txtAuthorName);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            authorImage = itemView.findViewById(R.id.authorImage);
            txtPublishDate = itemView.findViewById(R.id.txtPublishDate);
            imageVIewBookmark = itemView.findViewById(R.id.imageVIewBookmark);
            imageVIewRead = itemView.findViewById(R.id.imageVIewRead);
        }
    }

    public interface HomeBigCardListener {
        void readButtonOnClick(View view, Article article);
        void bookmarkButtonOnClick(View view, Article article);
        void bigImageOnClick(View view, Article article);
    }
}
