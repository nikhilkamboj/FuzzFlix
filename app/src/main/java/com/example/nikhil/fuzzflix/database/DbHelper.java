package com.example.nikhil.fuzzflix.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nikhil on 28/02/18.
 */

public class DbHelper extends SQLiteOpenHelper {

    /**
     * name of the database
     */
    public static final String DATABASE_NAME = "movies.db";

    /**
     * database version
     */
    public static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // setting the primary key and foreign key right

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MAIN_TABLE =
                "CREATE TABLE" + Contract.MainMoviesEntry.TABLE_NAME + "(" +
                        Contract.MainMoviesEntry.MOVIES_ID                 + "INTEGER PRIMARY KEY" +
                        Contract.MainMoviesEntry.MOVIE_TITLE               + "TEXT NOT NULL" +
                        Contract.MainMoviesEntry.MOVIE_OVERVIEW            + "TEXT NOT NULL" +
                        Contract.MainMoviesEntry.MOVIE_RELEASE_DATE        + "INTEGER NOT NULL" +
                        Contract.MainMoviesEntry.MOVIE_VOTE_AVERAGE        + "INTEGER NOT NULL" +
                        Contract.MainMoviesEntry.MOVIE_VOTE_COUNT          + "INTEGER NOT NULL" +
                        Contract.MainMoviesEntry.MOVIE_FRONT_POSTER_PATH   + "VARCHAR NOT NULL" +
                        Contract.MainMoviesEntry.MOVIE_BACK_POSTER_PATH    + "VARCHAR NOT NULL" +
                        Contract.MainMoviesEntry.MOVIE_IS_FAVOURITE        + "INTEGER NOT NULL" +
                        Contract.MainMoviesEntry.MOVIE_IS_POPULAR          + "INTEGER NOT NULL" +
                        Contract.MainMoviesEntry.MOVIE_IS_TOP_RATED        + "INTEGER NOT NULL);";


        final String SQL_CREATE_POPULAR_TABLE =
                "CREATE TABLE" + Contract.PopularMoviesEntry.TABLE_NAME + "(" +
                        Contract.PopularMoviesEntry.MOVIES_ID + "INTEGER NOT NULL);";

        final String SQL_CREATE_TOP_RATED_TABLE =
                "CREATE TABLE" + Contract.TopRatedMoviesEntry.TABLE_NAME + "(" +
                        Contract.PopularMoviesEntry.MOVIES_ID + "INTEGER NOT NULL);";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
