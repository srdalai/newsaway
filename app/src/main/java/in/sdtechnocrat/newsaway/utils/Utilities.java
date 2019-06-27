package in.sdtechnocrat.newsaway.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Utilities {

    public static final String BASE_URL = "https://newsapi.org/v2/";
    public static final String API_KEY = "074509920a9349e6a56b441eaec67d35";
    //public static final String API_KEY = "API_KEY";
    public static final String STATUS_OK = "ok";
    public static final String STATUS_ERROR = "error";
    public static final String DEFAULT_LANG = "en";
    public static final String DEFAULT_COUNTRY = "us";
    public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /*
    200 - OK. The request was executed successfully.
    400 - Bad Request. The request was unacceptable, often due to a missing or misconfigured parameter.
    401 - Unauthorized. Your API key was missing from the request, or wasn't correct.
    429 - Too Many Requests. You made too many requests within a window of time and have been rate limited. Back off for a while.
    500 - Server Error. Something went wrong on our side.
    */

    public static String formatDate(String inputDate, String ipDateFormat, String opDateFormat) {

        String outputDate = "";
        DateFormat ipFormat = new SimpleDateFormat(ipDateFormat, Locale.getDefault());
        DateFormat opFormat = new SimpleDateFormat(opDateFormat, Locale.getDefault());

        try {
            Date date = ipFormat.parse(inputDate);
            outputDate = opFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    public static String getTimeDiff(String time, String format) {
        String timeDiff = "";
        Date curDate = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = dateFormat.parse(time);
            long difference = curDate.getTime() - date.getTime();
            long diffSec = TimeUnit.MILLISECONDS.toSeconds(difference);
            long diffMin = TimeUnit.MILLISECONDS.toMinutes(difference);
            long diffHour = TimeUnit.MILLISECONDS.toHours(difference);
            long diffDay = TimeUnit.MILLISECONDS.toDays(difference);

            if (diffSec < 3600) {
                if (diffMin <=1 ) {
                    timeDiff = "1 min ago";
                } else {
                    timeDiff = diffMin + " mins ago";
                }
            } else if (diffHour < 24) {
                if (diffDay <= 1) {
                    timeDiff = "1 hour ago";
                } else {
                    timeDiff = diffHour + " hours ago";
                }
            } else if (diffDay <= 30){
                if (diffDay <= 1) {
                    timeDiff = "1 day ago";
                } else {
                    timeDiff = diffDay + " days ago";
                }
            } else {
                timeDiff = "long days ago";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timeDiff;
    }

    public static long getTimeDiffInDays(String time, String format) {
        long diffDay = 0;
        Date curDate = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(time);
            long difference = curDate.getTime() - date.getTime();
            diffDay = TimeUnit.MILLISECONDS.toDays(difference);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diffDay;
    }

    public static String getCurrentDefaultDate() {
        String outputDate = "";
        Date curDate = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT, Locale.getDefault());
        outputDate = dateFormat.format(curDate);
        return outputDate;
    }

    public static String POJOtoString(Object object) {
        Gson gson = new Gson();
        String gsonString = gson.toJson(object);
        return gsonString;
    }

    public static Object StringToPOJO(String jsonString, Class<?> cls) {
        Gson gson = new Gson();
        Object people;
        people = gson.fromJson(jsonString, cls);
        return people;
    }

}
