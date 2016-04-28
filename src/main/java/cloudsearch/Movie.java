package cloudsearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie {

    @JsonProperty("actors")
    String[] actors;
    @JsonProperty("directors")
    String[] directors;
    @JsonProperty("genres")
    String[] genres;
    @JsonProperty("image_url")
    String imageUrl;
    @JsonProperty("plot")
    String plot;
    @JsonProperty("rank")
    int rank;
    @JsonProperty("rating")
    double rating;
    @JsonProperty("release_date")
    String releaseDate;
    @JsonProperty("running_time_secs")
    int runningTimeSecs;
    @JsonProperty("title")
    String title;
    @JsonProperty("year")
    String year;

    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }

    public String[] getDirectors() {
        return directors;
    }

    public void setDirectors(String[] directors) {
        this.directors = directors;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

//    public LocalDate getReleaseDate() {
//        return releaseDate;
//    }
//
//    public void setReleaseDate(LocalDate releaseDate) {
//        this.releaseDate = releaseDate;
//    }


    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRunningTimeSecs() {
        return runningTimeSecs;
    }

    public void setRunningTimeSecs(int runningTimeSecs) {
        this.runningTimeSecs = runningTimeSecs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
