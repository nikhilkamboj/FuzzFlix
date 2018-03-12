package com.example.nikhil.fuzzflix.data;

/**
 * Created by nikhil on 12/03/18.
 */


/*
    ***************************************************************************************************************
                  A SIMPLE CLASS TO HOLD THE JSON OBJECT DATA INTO ITS VARIABLES
                  CONTAINS GETTERS AND SETTERS FOR ALL THE VARIABLES

                  ITS OBJECTS WILL STORE ALL THE DATA THAT NEED TO DISPLAYED ON-SCREEN

                  REGULAR POJO

    ***************************************************************************************************************
 */


public class ReviewData {

    private String id;

    private String authorName;

    private String reviewContent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }
}
