package in.sdtechnocrat.newsaway.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.sdtechnocrat.newsaway.R;
import in.sdtechnocrat.newsaway.model.Category;
import in.sdtechnocrat.newsaway.utils.DatabaseHelper;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryViewHolder> {

    Context mContext;
    DatabaseHelper databaseHelper;
    ArrayList<Category> categories;
    int selectedPosition = 0;

    public HomeCategoryAdapter(Context mContext, ArrayList<Category> categories) {
        this.mContext = mContext;
        this.categories = categories;
    }

    @NonNull
    @Override
    public HomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_home_category, parent, false);
        return new HomeCategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryViewHolder holder, int position) {
        Category category = categories.get(position);

        holder.txtCatName.setText(category.getCategoryName());

        if (position == selectedPosition) {
            holder.txtCatName.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.frame.setBackground(mContext.getDrawable(R.drawable.home_cat_focused_bg));
        } else {
            holder.txtCatName.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.frame.setBackground(mContext.getDrawable(R.color.transparent));
        }

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class HomeCategoryViewHolder extends RecyclerView.ViewHolder {

        TextView txtCatName;
        FrameLayout frame;
        public HomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCatName = itemView.findViewById(R.id.txtCatName);
            frame = itemView.findViewById(R.id.frame);
        }
    }

    public void setSelectPos(int pos) {
        selectedPosition = pos;
        notifyDataSetChanged();
    }
}
