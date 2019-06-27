package in.sdtechnocrat.newsaway.ui;

import android.content.Context;
import android.content.SharedPreferences;

import in.sdtechnocrat.newsaway.utils.Utilities;

public class PreferenceManager {

    static String IS_FIRST_RUN = "isFirstRun";
    static String SOURCE_UPDATED_AT = "sourceUpdatedAt";

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
        editor.putBoolean(IS_FIRST_RUN, false).apply();
    }

    public String getSourceUpdatedTime() {
        return sharedPreferences.getString(SOURCE_UPDATED_AT, "");
    }

    public void setSourceUpdatedAt() {
        editor.putString(SOURCE_UPDATED_AT, Utilities.getCurrentDefaultDate()).apply();
    }
}
