package in.sdtechnocrat.newsaway.model;

import com.google.gson.annotations.SerializedName;

public class Article {

    public static final String TABLE_NAME = "articles";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_JSON_DATA = "jsondata";
    public static final String COLUMN_IMAGE_FILE = "imagefile";
    public static final String COLUMN_DATE_ADDED = "date";
    public static final String COLUMN_UNIQUE_URL = "url";
    public static final String COLUMN_SAVE_TYPE = "savetype";   // offline/bookmark

    public static final String SAVE_TYPE_OFFLINE = "offline";
    public static final String SAVE_TYPE_BOOKMARK = "bookmark";
    public static final String SAVE_TYPE_TOTAL = "total";


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " TEXT,"
                    + COLUMN_JSON_DATA + " TEXT,"
                    + COLUMN_IMAGE_FILE + " TEXT,"
                    + COLUMN_DATE_ADDED + " TEXT,"
                    + COLUMN_UNIQUE_URL + " TEXT,"
                    + COLUMN_SAVE_TYPE + " TEXT"
                    + ")";

    @SerializedName("source")
    private ArticleSource source;

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String url;

    @SerializedName("urlToImage")
    private String urlToImage;

    @SerializedName("publishedAt")
    private String publishedAt;

    @SerializedName("content")
    private String content;

    private boolean isRead = false;
    private boolean isBookmarked = false;
    private String jsonData;
    private int id;
    private String imageFile, dataAdded;

    public Article(ArticleSource source, String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public ArticleSource getSource() {
        return source;
    }

    public void setSource(ArticleSource source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
