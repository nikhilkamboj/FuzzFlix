package com.example.nikhil.fuzzflix.data;

/**
 * Created by nikhil on 12/01/18.
 */

/*
    ***************************************************************************************************************
                  A SIMPLE CLASS TO HOLD THE JSON OBJECT DATA INTO ITS VARIABLES
                  CONTAINS GETTERS AND SETTERS FOR ALL THE VARIABLES

                  ITS OBJECTS WILL STORE ALL THE DATA THAT NEED TO DISPLAYED ON-SCREEN

    ***************************************************************************************************************
 */

public class DisplayData {

    private String titleOfMovie;

    private String dateOfRelease;

    private String ratingOfMovie;

    private String posterPath;

    private String backGroundPosterPath;

    private String overView;

    public String getTitleOfMovie() {
        return titleOfMovie;
    }

    public String getDateOfRelease() {
        return dateOfRelease;
    }

    public String getRatingOfMovie() {
        return ratingOfMovie;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackGroundPosterPath() {
        return backGroundPosterPath;
    }

    public String getOverView() {
        return overView;
    }

    public void setTitleOfMovie(String titleOfMovie) {
        this.titleOfMovie = titleOfMovie;
    }

    public void setDateOfRelease(String dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    public void setRatingOfMovie(String ratingOfMovie) {
        this.ratingOfMovie = ratingOfMovie;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setBackGroundPosterPath(String backGroundPosterPath) {
        this.backGroundPosterPath = backGroundPosterPath;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }
}
