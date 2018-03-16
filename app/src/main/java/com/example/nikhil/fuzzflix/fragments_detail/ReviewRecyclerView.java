package com.example.nikhil.fuzzflix.fragments_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhil.fuzzflix.R;
import com.example.nikhil.fuzzflix.data.ReviewData;

import java.util.ArrayList;

/**
 * Created by nikhil on 16/03/18.
 */

public class ReviewRecyclerView extends RecyclerView.Adapter<ReviewRecyclerView.ReviewViewHolder>{

    ArrayList<ReviewData> reviewDataArrayList;



    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        int layoutForItemList = R.layout.review_layout;

        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForItemList,parent,shouldAttachToParentImmediately);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        ReviewData reviewData = reviewDataArrayList.get(position);

        String authorName = reviewData.getAuthorName();

        String reviewContent = reviewData.getReviewContent();

        holder.nameTextView.setText(authorName);
        holder.reviewTextView.setText(reviewContent);

    }

    @Override
    public int getItemCount() {
        if (reviewDataArrayList == null || reviewDataArrayList.size() == 0){
            return 0;
        }else {
            return reviewDataArrayList.size();
        }

    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView reviewTextView;
        // setting the ui
        public ReviewViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.tv_name_of_reviewer);
            reviewTextView = (TextView) itemView.findViewById(R.id.tv_review_given);
        }
    }



    public void swapList(ArrayList<ReviewData> list) {
        reviewDataArrayList = list;
        notifyDataSetChanged();
    }

}
