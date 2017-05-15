package com.bustamante.jose.mymovieapp.entity;

    import java.util.ArrayList;
    import java.util.List;
    import com.google.gson.annotations.Expose;
    import com.google.gson.annotations.SerializedName;

public class Movies {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<Movie> results = null;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    /**
     * No args constructor for use in serialization
     *
     */
    public Movies() {
        this.page = 0;
        this.results = new ArrayList<>();
        this.totalResults = 0;
        this.totalPages = 0;
    }

    /**
     *
     * @param results
     * @param totalResults
     * @param page
     * @param totalPages
     */
    public Movies(Integer page, List<Movie> results, Integer totalResults, Integer totalPages) {
        super();
        this.page = page;
        this.results = results;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Movie obtenerPelicula(int position) {
        if (this.getResults() != null && this.getResults().size() != 0)  {
            return this.getResults().get(position);
        }
        return null;
    }

}
