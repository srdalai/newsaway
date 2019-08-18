package in.sdtechnocrat.newsaway.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import in.sdtechnocrat.newsaway.R;
import in.sdtechnocrat.newsaway.model.Country;
import in.sdtechnocrat.newsaway.utils.Utilities;

public class CountryAdapter extends ArrayAdapter<Country> {

    private Context mContext;
    private ArrayList<Country> countries;

    public CountryAdapter(Context mContext, ArrayList<Country> countries) {
        super(mContext, -1, countries);
        this.mContext = mContext;
        this.countries = countries;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_country, parent, false);
        }

        ImageView imageFlag = convertView.findViewById(R.id.imageFlag);
        TextView textCountryName = convertView.findViewById(R.id.textCountryName);

        Country country = getItem(position);

        textCountryName.setText(country.getCountryName());

        String flagUrl = "https://www.countryflags.io/" + country.getIsoCountryCode() + "/flat/64.png";
        Picasso.get().load(flagUrl).into(imageFlag);

        //Utilities.fetchSvg(mContext, country.getFlagUrl(), imageFlag);

        return convertView;
    }

}
