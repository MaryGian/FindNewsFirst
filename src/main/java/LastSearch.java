public class LastSearch {

    private String whatTypeOfSearch;
    private String country;
    private String category;
    private String sources;
    private String from_date;
    private String to_date;
    private String parameterq;

    public LastSearch() {
        this.whatTypeOfSearch = "";
        this.country = "";
        this.category = "";
        this.sources = "";
        this.from_date = "";
        this.to_date = "";
        this.parameterq = "";
    }

    public void setParameterq(String parameterq) {
        this.parameterq = parameterq;
    }

    public String getParameterq() {
        return parameterq;
    }

    public String getWhatTypeOfSearch() {
        return whatTypeOfSearch;
    }

    public String getCountry() {
        return country;
    }

    public String getCategory() {
        return category;
    }

    public String getSources() {
        return sources;
    }

    public String getFrom_date() {
        return from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setWhatTypeOfSearch(String whatTypeOfSearch) {
        this.whatTypeOfSearch = whatTypeOfSearch;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }


}
