package com.example.nikhil.fuzzflix.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by nikhil on 01/03/18.
 */

public class MoviesProvider extends ContentProvider {

    /**
     * related to particular data such as popular, top_rated ,favourite etc hence not givivng value 100
     */
    public static final int CODE_MAIN_MOVIES = 100;
    public static final int CODE_MAIN_MOVIES_ID = 101;

    private static final String TAG = MoviesProvider.class.getSimpleName();


    public static final UriMatcher sUriMatcher = buildUriMatcher();
    private DbHelper mMoviesOpenHelper;

    /**
     *
     * @return returns the UriMatcher object which that matches the above code
     */
    public static UriMatcher buildUriMatcher() {
        /**
         * creating a uri matcher instance
         * uriMatcher when used returns the code associated with the particular uri, as for now
         * during declaration we are setting mo code
         */
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;


        /**
         * adding the different uri's and associating them with code.
         */
        // main table uri code
        uriMatcher.addURI(authority,Contract.MAIN_MOVIES_PATH,CODE_MAIN_MOVIES);
        // UriMatcher for a particular row as per its Movie_ID.
        uriMatcher.addURI(authority,Contract.MAIN_MOVIES_PATH + Contract.MainMoviesEntry.MOVIES_ID + "=?",CODE_MAIN_MOVIES_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mMoviesOpenHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)) {
            case CODE_MAIN_MOVIES:
                cursor = mMoviesOpenHelper.getReadableDatabase().query(
                        Contract.MainMoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );
                break;
            // requires some more thought
            case CODE_MAIN_MOVIES_ID:
                cursor = mMoviesOpenHelper.getReadableDatabase().query(
                        Contract.MainMoviesEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database = mMoviesOpenHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case CODE_MAIN_MOVIES:
                database.beginTransaction();
                int deletedRows = 0;
                try {
                    deletedRows = database.delete(Contract.MainMoviesEntry.TABLE_NAME,selection,selectionArgs);
                }
                finally {
                    database.endTransaction();
                }
                return deletedRows;
            default:
                return 0;
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mMoviesOpenHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (sUriMatcher.match(uri)) {
            case CODE_MAIN_MOVIES:
                // selection would be movie_id = ?, selectionArgs be movie_id. changes the content values
                db.beginTransaction();
                try {
                    rowsUpdated = db.update(Contract.MainMoviesEntry.TABLE_NAME,values,selection,selectionArgs);
                    db.setTransactionSuccessful();
                }
                finally {
                    db.endTransaction();
                }
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mMoviesOpenHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case CODE_MAIN_MOVIES:
                db.beginTransaction();
                int insertedRow = 0;
                try{
                    for (ContentValues cv: values) {
//                        long _id = db.insert(Contract.MainMoviesEntry.TABLE_NAME,null,cv);
                        long _id = db.insertWithOnConflict(Contract.MainMoviesEntry.TABLE_NAME,null,cv, SQLiteDatabase.CONFLICT_IGNORE);
                        if (_id != -1) {
                            insertedRow++;
                        }
                    }
                    db.setTransactionSuccessful();
                }
                finally {
                    db.endTransaction();
                }
                return insertedRow;
            default:
                return super.bulkInsert(uri, values);
        }
    }


}
