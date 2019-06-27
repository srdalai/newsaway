package in.sdtechnocrat.newsaway.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import in.sdtechnocrat.newsaway.model.Article;
import in.sdtechnocrat.newsaway.model.Source;

import static in.sdtechnocrat.newsaway.model.Article.SAVE_TYPE_TOTAL;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 8;

    // Database Name
    private static final String DATABASE_NAME = "newsaway_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {

        // create notes table
        db.execSQL(Source.CREATE_TABLE);
        db.execSQL(Article.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Source.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Article.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertSource(@NotNull Source source) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Source.COLUMN_ID, source.getId());
        values.put(Source.COLUMN_NAME, source.getName());
        values.put(Source.COLUMN_DESC, source.getDescription());
        values.put(Source.COLUMN_URL, source.getUrl());
        values.put(Source.COLUMN_CATEGORY, source.getCategory());
        values.put(Source.COLUMN_LANGUAGE, source.getLanguage());
        values.put(Source.COLUMN_COUNTRY, source.getCountry());

        // insert row
        long id = db.insert(Source.TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }

    public Source getSource(String id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Source.TABLE_NAME,
                new String[]{Source.COLUMN_ID, Source.COLUMN_NAME, Source.COLUMN_DESC, Source.COLUMN_URL, Source.COLUMN_CATEGORY, Source.COLUMN_LANGUAGE, Source.COLUMN_COUNTRY},
                Source.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Source source = new Source(
                cursor.getString(cursor.getColumnIndex(Source.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Source.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Source.COLUMN_DESC)),
                cursor.getString(cursor.getColumnIndex(Source.COLUMN_URL)),
                cursor.getString(cursor.getColumnIndex(Source.COLUMN_CATEGORY)),
                cursor.getString(cursor.getColumnIndex(Source.COLUMN_LANGUAGE)),
                cursor.getString(cursor.getColumnIndex(Source.COLUMN_COUNTRY)));

        // close the db connection
        cursor.close();

        return source;
    }

    public ArrayList<Source> getAllSources() {
        ArrayList<Source> sources = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Source.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Source source = new Source();
                source.setId(cursor.getString(cursor.getColumnIndex(Source.COLUMN_ID)));
                source.setName(cursor.getString(cursor.getColumnIndex(Source.COLUMN_NAME)));
                source.setDescription(cursor.getString(cursor.getColumnIndex(Source.COLUMN_DESC)));
                source.setUrl(cursor.getString(cursor.getColumnIndex(Source.COLUMN_URL)));
                source.setCategory(cursor.getString(cursor.getColumnIndex(Source.COLUMN_CATEGORY)));
                source.setLanguage(cursor.getString(cursor.getColumnIndex(Source.COLUMN_LANGUAGE)));
                source.setCountry(cursor.getString(cursor.getColumnIndex(Source.COLUMN_COUNTRY)));


                sources.add(source);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return sources;
    }

    public int getSourcesCount() {
        String countQuery = "SELECT  * FROM " + Source.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public void deleteDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Source.TABLE_NAME, null, null);
        db.close();
    }

    //OFFLINE DATASBASE
    public long insertOfflineData(Article article, String savetype) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        String jsonData = Utilities.POJOtoString(article);

        values.put(Article.COLUMN_ID, String.valueOf( System.currentTimeMillis()));
        values.put(Article.COLUMN_JSON_DATA, jsonData);
        values.put(Article.COLUMN_DATE_ADDED, Utilities.getCurrentDefaultDate());
        values.put(Article.COLUMN_UNIQUE_URL, article.getUrl());
        values.put(Article.COLUMN_SAVE_TYPE, savetype);

        // insert row
        long id = db.insert(Article.TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }

    public Article getOfflineArticle(String id, String savetype) {
        // get readable database as we are not inserting anything
        Article offlineArticle = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Article.TABLE_NAME,
                new String[]{Article.COLUMN_ID, Article.COLUMN_JSON_DATA, Article.COLUMN_IMAGE_FILE, Article.COLUMN_DATE_ADDED, Article.COLUMN_UNIQUE_URL, Article.COLUMN_SAVE_TYPE},
                Article.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            String jsonData = cursor.getString(cursor.getColumnIndex(Article.COLUMN_JSON_DATA));
            String imageFile = cursor.getString(cursor.getColumnIndex(Article.COLUMN_IMAGE_FILE));
            String dateAdded = cursor.getString(cursor.getColumnIndex(Article.COLUMN_DATE_ADDED));
            String uniqueURL = cursor.getString(cursor.getColumnIndex(Article.COLUMN_UNIQUE_URL));
            String savedType = cursor.getString(cursor.getColumnIndex(Article.COLUMN_SAVE_TYPE));

            if (savedType.equals(savetype)) {
                offlineArticle = (Article) Utilities.StringToPOJO(jsonData, Article.class);
            }

            // close the db connection
            cursor.close();

        }
        return offlineArticle;
    }

    public ArrayList<Article> getAllOfflineArticles(String savetype) {
        ArrayList<Article> offlineArticles = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Article.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String jsonData = cursor.getString(cursor.getColumnIndex(Article.COLUMN_JSON_DATA));
                String imageFile = cursor.getString(cursor.getColumnIndex(Article.COLUMN_IMAGE_FILE));
                String dateAdded = cursor.getString(cursor.getColumnIndex(Article.COLUMN_DATE_ADDED));
                String savedType = cursor.getString(cursor.getColumnIndex(Article.COLUMN_SAVE_TYPE));
                if (savetype.equals(SAVE_TYPE_TOTAL)) {
                    Article offlineArticle = (Article) Utilities.StringToPOJO(jsonData, Article.class);
                    offlineArticles.add(offlineArticle);
                } else if (savedType.equals(savetype)) {
                    Article offlineArticle = (Article) Utilities.StringToPOJO(jsonData, Article.class);
                    offlineArticles.add(offlineArticle);
                }

            } while (cursor.moveToNext());

            cursor.close();
        }

        // close db connection
        db.close();

        // return notes list
        return offlineArticles;
    }

    public ArrayList<String> getAllOfflineURLs(String savetype) {
        ArrayList<String> offlineURLs = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Article.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String offlineURL = cursor.getString(cursor.getColumnIndex(Article.COLUMN_UNIQUE_URL));
                String savedType = cursor.getString(cursor.getColumnIndex(Article.COLUMN_SAVE_TYPE));

                if (savedType.equals(savetype)) {
                    offlineURLs.add(offlineURL);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        // close db connection
        db.close();

        // return notes list
        return offlineURLs;
    }
}
