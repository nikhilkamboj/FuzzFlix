package com.example.nikhil.fuzzflix.constants;

import com.example.nikhil.fuzzflix.BuildConfig;

/**
 * Created by nikhil on 12/01/18.
 */

/*
   ********************************************************************************************
      A SIMPLE CLASS THAT STORES ALL THE CONSTANTS REQUIRED BY OUR APP AND ALL THEIR GETTERS
           eg:- constants to create baseUrl, imageUrl, JSON data attributes etc
  ********************************************************************************************
 */


public class AppConstants {


    //    setting the scheme of the url i.e request type HTTPs or GEO etc.
    private static final String SCHEME_OF_URL = "https";


/* ************************* START image url append path attributes ***************** */


    //    Image Url authority
    private static final String IMAGE_AUTHORITY = "image.tmdb.org";

    //    image path appender-1 t
    private static final String T = "t";

    //    image path appender-2 p
    private static final String P = "p";

    //    size of the poster is defined here, using w500 a medium size poster(image path appender-3)
    private static final String FOREGROUND_IMAGE_SIZE = "w500";

    private static final String BACKGROUND_IMAGE_SIZE = "w500";


 /* ----------------------------------------END IMAGE URL----------------------------------------------------------- */


    /*  ************************* START base movie url append path attributes *****************   */

    //    Base movie url Authority
    private static final String BASE_URL_AUTHORITY = "api.themoviedb.org";

    //    database version for the api base url {appender -1 '3'}
    private static final String DB_VERSION = "3";

    //    type of data request(movie, tv show etc). here we are looking for movie data  {appender -2 'movie'}
    private static final String MOVIE_DATA_REQUEST = "movie";

    //    setting the parameter to get the popular filter into the url, so to get popular movies data. {appender -3 'popular'}
    private static final String POPULAR_FILTER_VALUE = "popular";

    //    setting the parameter to get top rated filter into url, so to get the popular movies data. {appender -3 'top_rated'}
    private static final String TOP_RATED_FILTER_VALUE = "top_rated";

    private static final String PAGE_INDEX_FILTER_VALUE = "page";

    /* ----------------------------------------END BASE MOVIE URL----------------------------------------------------------- */



    /*  ******************* START appending query parameters *******************************/

    //    this would be used in appendTo Query to set the key value pairs.
    private static final String API_AUTHENTICATION_KEY_PARAM = "api_key";

    //    key to authorize api request calls.
    private static final String API_AUTHENTICATION_KEY_VALUE = BuildConfig.MY_MOVIE_DB_API_KEY;

    /* ----------------------------------------END APPEND QUERY PARAMETERS----------------------------------------------------------- */



    /* ********************* START JSON PARAMETERS REQUIRED TO DISPLAY ************************* */

    //    array in Json Oject to be traversed for the data
    private static final String RESULT_ARRAY_ATTRIBUTE = "results";

    //    title of the movie attribute
    private static final String TITLE_ATTRIBUTE = "title";

    //    release date attribute
    private static final String RELEASE_DATE_ATTRIBUTE = "release_date";

    //    poster path attribute
    private static final String MAIN_POSTER_PATH_ATTRIBUTE = "poster_path";

    //    background poster path attribute
    private static final String BACKGROUND_POSTER_PATH_ATTRIBUTE = "backdrop_path";

    //   rating of the movie attribute
    private static final String RATING_ATTRIBUTE = "vote_average";

    //   overview of the movie attribute
    private static final String OVERVIEW_ATTRIBUTE = "overview";


    // movieId attribute
    private static final String MOVIE_ID = "id";

    // movie vote count attribute
    private static final String MOVIE_VOTE_COUNT = "vote_count";


//   read time for the network call.
    private static final int READ_TIMEOUT = 15000;

//    connection timeout for the network call.
    private static final int CONNECTION_TIMEOUT = 15000;

//    type of connection to be establihed internally
    private static final String CONNECTION_TYPE = "GET";



    /**************************************************************************************************/

    private static final String POPULAR_FILTER_FLAG = "popular_val";

    private static final String TOP_RATED_FLAG = "top_rated_val";

    // for key value pair to send Movie_id from popular and TopRated to detail page easily
    private static final String KEY_MOVIE_ID = "movie_id";
    /**************************************************************************************************/

    /* **************************************END****************************************************** */

    public static String getMainPosterPathAttribute() {
        return MAIN_POSTER_PATH_ATTRIBUTE;
    }

    public static String getBackgroundPosterPathAttribute() {
        return BACKGROUND_POSTER_PATH_ATTRIBUTE;
    }

    public static String getRatingAttribute() {
        return RATING_ATTRIBUTE;
    }

    public static String getOverviewAttribute() {
        return OVERVIEW_ATTRIBUTE;
    }

    public static String getResultArrayAttribute() {
        return RESULT_ARRAY_ATTRIBUTE;
    }

    public static String getTitleAttribute() {
        return TITLE_ATTRIBUTE;
    }

    public static String getReleaseDateAttribute() {
        return RELEASE_DATE_ATTRIBUTE;
    }

    public static String getSchemeOfUrl() {
        return SCHEME_OF_URL;
    }

    public static String getImageAuthority() {
        return IMAGE_AUTHORITY;
    }

    public static String getT() {
        return T;
    }

    public static String getP() {
        return P;
    }

    public static String getForegroundImageSize() {
        return FOREGROUND_IMAGE_SIZE;
    }


    public static String getBaseUrlAuthority() {
        return BASE_URL_AUTHORITY;
    }

    public static String getDbVersion() {
        return DB_VERSION;
    }

    public static String getMovieDataRequest() {
        return MOVIE_DATA_REQUEST;
    }

    public static String getPopularFilterValue() {
        return POPULAR_FILTER_VALUE;
    }

    public static String getTopRatedFilterValue() {
        return TOP_RATED_FILTER_VALUE;
    }

    public static String getApiAuthenticationKeyParam() {
        return API_AUTHENTICATION_KEY_PARAM;
    }

    public static String getApiAuthenticationKeyValue() {
        return API_AUTHENTICATION_KEY_VALUE;
    }

    public static int getReadTimeout() {
        return READ_TIMEOUT;
    }

    public static int getConnectionTimeout() {
        return CONNECTION_TIMEOUT;
    }

    public static String getConnectionType() {
        return CONNECTION_TYPE;
    }


    public static String getPageIndexFilterValue() {
        return PAGE_INDEX_FILTER_VALUE;
    }

    public static String getBackgroundImageSize() {
        return BACKGROUND_IMAGE_SIZE;
    }

    public static String getMovieId() {
        return MOVIE_ID;
    }

    public static String getMovieVoteCount() {
        return MOVIE_VOTE_COUNT;
    }


    public static String getPopularFilterFlag() {
        return POPULAR_FILTER_FLAG;
    }

    public static String getTopRatedFlag() {
        return TOP_RATED_FLAG;
    }

    public static String getKeyMovieId() {
        return KEY_MOVIE_ID;
    }
}
