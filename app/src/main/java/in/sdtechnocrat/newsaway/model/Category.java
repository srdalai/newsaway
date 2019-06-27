package in.sdtechnocrat.newsaway.model;

public class Category {
    String categoryName;
    String categorySlug;

    public Category(String categoryName, String categorySlug) {
        this.categoryName = categoryName;
        this.categorySlug = categorySlug;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }
}
