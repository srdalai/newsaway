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
import in.sdtechnocrat.newsaway.model.Country;
import in.sdtechnocrat.newsaway.utils.Utilities;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    Context mContext;
    ArrayList<Country> countries;

    public CountryAdapter(Context mContext, ArrayList<Country> countries) {
        this.mContext = mContext;
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_country, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countries.get(position);

        holder.textCountryName.setText(country.getCountryName());
        String flagUrl = "https://www.countryflags.io/" + country.getIsoCountryCode() + "/flat/64.png";
        Picasso.get().load(flagUrl).into(holder.imageFlag);

        //Utilities.fetchSvg(mContext, country.getFlagUrl(), holder.imageFlag);

    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {

        ImageView imageFlag;
        TextView textCountryName;
        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFlag = itemView.findViewById(R.id.imageFlag);
            textCountryName = itemView.findViewById(R.id.textCountryName);
        }
    }
}
