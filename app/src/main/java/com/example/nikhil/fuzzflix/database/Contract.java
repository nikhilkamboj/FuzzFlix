package com.example.nikhil.fuzzflix.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by nikhil on 28/02/18.
 */

public class Contract {

    /**
     * content authority, same as app's package name, hence guranteed to be unique at google play .
     * helps content provider find the db
     * and different associated tables. authority works just like the domain name and its website i.e app itself.
     */

    public static final String CONTENT_AUTHORITY = "com.example.nikhil.fuzzflix";


    /**
     * Base content URI, created using content authority and apps uses this to contact to content provider
     */

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * paths for different tables, such as main, popular, topRated etc
     * these paths are the one through which content provider will know which table to
     * go to.
     * example
     *  [BASE_CONTENT_URI] + MAIN_MOVIE_TAB_PATH
     *  [BASE_CONTENT_URI] + POPULAR_FIL_PATH etc, any wrong path from the specified wont be entertained
     */
    public static final String MAIN_MOVIES_PATH = "main";

    public static final String POPULAR_PATH = "popular";

    public static final String TOP_RATED_PATH = "top_rated";

    /**
     * inner class defines content for main table
     */

    public static final class MainMoviesEntry implements BaseColumns {
        /**
         * we have uri for the content provider, but we should also have it for tables.
         * this is the unique uri, so to reach this particular table, created using
         * CONTENT_URI(for main movies tab) = BASE_CONTENT_URI + MAIN_MOVIES_TAB_PATH
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(MAIN_MOVIES_PATH).build();

        /**
         * table name for internal usage
         */
        public static final String TABLE_NAME = "main";

        /**
         * movies id column
         */
        public static final String MOVIES_ID = "movie_id";

        /**
         * title of the movie
         */
        public static final String MOVIE_TITLE = "title";

        /**
         * overview of the movie
         */
        public static final String MOVIE_OVERVIEW = "overview";

        /**
         * release date of the movie
         */
        public static final String MOVIE_RELEASE_DATE = "release_date";

        /**
         * vote average of the movie
         */
        public static final String MOVIE_VOTE_AVERAGE  = "vote_average";

        /**
         * vote count of the movie
         */
        public static final String MOVIE_VOTE_COUNT = "vote_count";

        /**
         *front poster path
         */
        public static final String MOVIE_FRONT_POSTER_PATH = "foreground_poster_path";

        /**
         * background poster path
         */
        public static final String MOVIE_BACK_POSTER_PATH = "background_poster_path";

        /**
         * is_fav for letting user enter particular movie to its favorite db.
         */
        public static final String MOVIE_IS_FAVOURITE = "is_favourite";

        /**
         * is_popular to keep track of movie belongs to popular add_to_fav
         */
        public static final String MOVIE_IS_POPULAR = "is_popular";

        /**
         * is_top_rated to keep track of movie if belongs to top_rated add_to_fav
         */
        public static final String MOVIE_IS_TOP_RATED = "is_top_rated";
    }


    /**
     * inner class for popular table
     */
    public static final class PopularMoviesEntry implements BaseColumns {
        /**
         * ContentUri for the table
         * CONTENT_URI(for main popular tab) = BASE_CONTENT_URI + POPULAR_PATH
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(POPULAR_PATH).build();

        /**
         * table name for internal usage
         */
        public static final String TABLE_NAME = "popular";

        /**
         * movies id column
         */
        public static final String MOVIES_ID = "movie_id";


    }


    /**
     * inner class for top_rated table
     */
    public static final class TopRatedMoviesEntry implements BaseColumns {
        /**
         * content uri for top_rated table
         * CONTENT_URI(for main topRated tab) = BASE_CONTENT_URI + TOP_RATED_PATH
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TOP_RATED_PATH).build();

        /**
         * table name for internal usage
         */
        public static final String TABLE_NAME = "top_rated";

        /**
         * movies id column
         */
        public static final String MOVIES_ID = "movie_id";

    }
}
