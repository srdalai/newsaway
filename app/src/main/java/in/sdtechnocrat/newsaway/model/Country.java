package in.sdtechnocrat.newsaway.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Country {

    @SerializedName("name")
    private String countryName;

    @SerializedName("alpha2Code")
    private String isoCountryCode;

    @SerializedName("alpha3Code")
    private String fullCountryCode;

    @SerializedName("nativeName")
    private String countryNativeName;

    @SerializedName("flag")
    private String flagUrl;

    @SerializedName("languages")
    private ArrayList<Language> languages;


    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getIsoCountryCode() {
        return isoCountryCode;
    }

    public void setIsoCountryCode(String isoCountryCode) {
        this.isoCountryCode = isoCountryCode;
    }

    public String getFullCountryCode() {
        return fullCountryCode;
    }

    public void setFullCountryCode(String fullCountryCode) {
        this.fullCountryCode = fullCountryCode;
    }

    public String getCountryNativeName() {
        return countryNativeName;
    }

    public void setCountryNativeName(String countryNativeName) {
        this.countryNativeName = countryNativeName;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<Language> languages) {
        this.languages = languages;
    }

    private class Language{

        @SerializedName("name")
        private String langName;

        @SerializedName("iso639_1")
        private String isoLangCode;

        @SerializedName("iso639_2")
        private String fullLangCode;

        @SerializedName("nativeName")
        private String langNativeName;

        public String getLangName() {
            return langName;
        }

        public void setLangName(String langName) {
            this.langName = langName;
        }

        public String getIsoLangCode() {
            return isoLangCode;
        }

        public void setIsoLangCode(String isoLangCode) {
            this.isoLangCode = isoLangCode;
        }

        public String getFullLangCode() {
            return fullLangCode;
        }

        public void setFullLangCode(String fullLangCode) {
            this.fullLangCode = fullLangCode;
        }

        public String getLangNativeName() {
            return langNativeName;
        }

        public void setLangNativeName(String langNativeName) {
            this.langNativeName = langNativeName;
        }
    }

}
