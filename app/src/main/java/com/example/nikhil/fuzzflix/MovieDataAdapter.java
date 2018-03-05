package com.example.nikhil.fuzzflix;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nikhil.fuzzflix.database.Contract;
import com.example.nikhil.fuzzflix.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

/**
 * Created by nikhil on 13/01/18.
 */

/**
 *  this class is helps in creating viewHolder objects and then displaying it to the screen through UI thread.
 *  uses an {@link MovieDataAdapterViewHolder} inner class for caching the references of views to be shown on screen.
 *
 *  extends {@link RecyclerView}
 *
 */

public class MovieDataAdapter extends RecyclerView.Adapter<MovieDataAdapter.MovieDataAdapterViewHolder>{

    Cursor mMovieCursor;

    private ListItemClickListener mItemClickListener;

    /**
     * handling clicking events for the viewHolder objects.
     */
    public interface ListItemClickListener{
        void onListItemClick(Cursor displayDataAtPosition);
    }


    public MovieDataAdapter(ListItemClickListener listener ) {
        mItemClickListener = listener;
    }

    /**
     * this method is called when there is a need for viewHolders
     * on the screen. when Recycle View is out of viewHolders
     * this method gets called to create new ViewHolders.
     *
     *@param viewGroup view around which these viewHolders are contained into.
     *@param viewType  not used now
     *
     *@return A new MovieDataAdapterViewHolder that holds the View for each list item
     */
    @Override
    public MovieDataAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();

        int layoutForItemList = R.layout.movie_data_list;

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutForItemList,viewGroup,shouldAttachToParentImmediately);

        return new MovieDataAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     * using position param to get the arrayList current focused element and then accessing its requred
     * data values to be shown on-screen.
     *
     * @param movieHolder this is the holder object whose fields should be updated.
     * @param position     this tells about the current screen position
     */
    @Override
    public void onBindViewHolder(MovieDataAdapterViewHolder movieHolder, int position) {
        mMovieCursor.moveToPosition(position);
        boolean isBackgroundPoster = false;
        String mainPosterPath = mMovieCursor.getString(mMovieCursor.getColumnIndex(Contract.MainMoviesEntry.MOVIE_FRONT_POSTER_PATH));
        URL imageUrl = new NetworkUtils().buildImageUrl(mainPosterPath,isBackgroundPoster);
        String imageStringUrl = null;
        try{
            imageStringUrl = java.net.URLDecoder.decode(imageUrl.toString(), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        Uri imageUri = Uri.parse(imageStringUrl);
        Context context = movieHolder.mPosterPathImageView.getContext();
        Picasso.with(context)
                .load(imageUri)
                .into(movieHolder.mPosterPathImageView);
    }


    /**
     * this gets the item count of the list etc to be shown on-screen
     *
     * @return returns the item count
     */
    @Override
    public int getItemCount() {
        if(mMovieCursor == null){
            return 0;
        }
        return mMovieCursor.getCount();
    }


    /**
     * Cache of the children views for a movie list item.
     */
    public class MovieDataAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mPosterPathImageView;

        public MovieDataAdapterViewHolder(View itemView) {
            super(itemView);
            mPosterPathImageView = (ImageView) itemView.findViewById(R.id.iv_main_poster);

            itemView.setOnClickListener(this);
        }

        /**
         * handling the click events for a particular object by calling the interface method upon its reference mItemCicListener.
         * @param v View for the inflated parent class.
         */
        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();

            // through this position value we find out the cursor and then the other values
            mMovieCursor.moveToPosition(itemPosition);
            Cursor dataAtClickedPosition = mMovieCursor;

            mItemClickListener.onListItemClick(dataAtClickedPosition);
        }
    }

//    /**
//     * sets the data and notify the change
//     *
//     * @param movieData this is the ArrayList of objects whose data would be displayed
//     */
//    public void setMovieData(ArrayList<DisplayData> movieData) {
//        mMovieDataList = movieData;
//        notifyDataSetChanged();
//    }

    public void swapCursor(Cursor cursor) {
        mMovieCursor = cursor;
        notifyDataSetChanged();
    }
}
