package in.sdtechnocrat.newsaway.utils;

import android.content.Context;
import android.content.SharedPreferences;

import in.sdtechnocrat.newsaway.utils.Utilities;

public class PreferenceManager {

    static String IS_FIRST_RUN = "isFirstRun";
    static String SOURCE_UPDATED_AT = "sourceUpdatedAt";
    static String IS_COUNTRY_SET = "isCountrySet";
    static String COUNTRY_NAME = "countryName";
    static String COUNTRY_CODE = "countryCode";

    Context mContext;
    SharedPreferences sharedPreferences;
    String NAME = "newsaway";
    SharedPreferences.Editor editor;

    public PreferenceManager(Context context) {
        this.mContext = context;
        sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean isFirstRun() {
        return sharedPreferences.getBoolean(IS_FIRST_RUN, true);
    }

    public void updateFirstRun() {
        editor.putBoolean(IS_FIRST_RUN, false).commit();
    }

    public String getSourceUpdatedTime() {
        return sharedPreferences.getString(SOURCE_UPDATED_AT, "");
    }

    public void setSourceUpdatedAt() {
        editor.putString(SOURCE_UPDATED_AT, Utilities.getCurrentDefaultDate()).commit();
    }

    public void setCountry(String countryName, String countryCode) {
        editor.putBoolean(IS_COUNTRY_SET, true);
        editor.putString(COUNTRY_CODE, countryCode);
        editor.putString(COUNTRY_NAME, countryName);
        editor.commit();
    }

    public boolean isCountrySet() {
        return sharedPreferences.getBoolean(IS_COUNTRY_SET, false);
    }

    public String getCountryName() {
        return sharedPreferences.getString(COUNTRY_NAME, "United States of America");
    }

    public String getCountryCode() {
        return sharedPreferences.getString(COUNTRY_CODE, "us");
    }
}
