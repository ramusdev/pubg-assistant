package com.rb.pubgassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class TweetsViewAdapter extends RecyclerView.Adapter {

    private final List<Tweet> mValues;
    private Context context;

    public TweetsViewAdapter(Context context, List<Tweet> items) {
        mValues = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item_noimage, parent, false);
                return new ViewHolderNoImage(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item_oneimage, parent, false);
                return new ViewHolderOneImage(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item_twoimage, parent, false);
                return new ViewHolderTwoImage(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item_noimage, parent, false);
                return new ViewHolderNoImage(view);
        }

        // return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ((ViewHolderNoImage) holder).mItem = mValues.get(position);
                ((ViewHolderNoImage) holder).mTitleView.setText("@PUBG " + mValues.get(position).getDate());
                ((ViewHolderNoImage) holder).mTextView.setText((mValues.get(position).getText()));
                break;
            case 1:
                ((ViewHolderOneImage) holder).mItem = mValues.get(position);
                ((ViewHolderOneImage) holder).mTitleView.setText("@PUBG " + mValues.get(position).getDate());
                ((ViewHolderOneImage) holder).mTextView.setText((mValues.get(position).getText()));
                Glide.with(context)
                        .load(mValues.get(position).getImages().get(0))
                        .dontTransform()
                        .transition(withCrossFade())
                        .into(((ViewHolderOneImage) holder).mImageView);
                break;
            case 2:
                ((ViewHolderTwoImage) holder).mItem = mValues.get(position);
                ((ViewHolderTwoImage) holder).mTitleView.setText("@PUBG " + mValues.get(position).getDate());
                ((ViewHolderTwoImage) holder).mTextView.setText((mValues.get(position).getText()));
                Glide.with(context)
                        .load(mValues.get(position).getImages().get(0))
                        .dontTransform()
                        .transition(withCrossFade())
                        .into(((ViewHolderTwoImage) holder).mImageViewOne);
                Glide.with(context)
                        .load(mValues.get(position).getImages().get(1))
                        .dontTransform()
                        .transition(withCrossFade())
                        .into(((ViewHolderTwoImage) holder).mImageViewTwo);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mValues.get(position).getImages().size() == 1) {
            return 1;
        } else if (mValues.get(position).getImages().size() >= 2) {
            return 2;
        } else {
            return 0;
        }

        // return super.getItemViewType(position);
    }


    /*
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);
        // holder.mTextView.setText(Html.fromHtml(mValues.get(position).getText(), FROM_HTML_MODE_COMPACT));
        holder.mTextView.setText((mValues.get(position).getText()));
        holder.mTitleView.setText("@PlayApex " + mValues.get(position).getDate());

        if (mValues.get(position).getImages().size() > 0) {
            holder.mImageView.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(mValues.get(position).getImages().get(0))
                    .dontTransform()
                    .transition(withCrossFade())
                    .into(holder.mImageView);
        } else {
            holder.mImageView.setVisibility(View.GONE);
        }
    }
    */

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolderNoImage extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mTextView;
        public Tweet mItem;

        public ViewHolderNoImage(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.news_title);
            mTextView = (TextView) view.findViewById((R.id.news_text));
        }
    }

    public class ViewHolderOneImage extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mTextView;
        public final ImageView mImageView;
        public Tweet mItem;

        public ViewHolderOneImage(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.news_title);
            mTextView = (TextView) view.findViewById((R.id.news_text));
            mImageView = (ImageView) view.findViewById((R.id.news_image));
        }
    }

    public class ViewHolderTwoImage extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mTextView;
        public final ImageView mImageViewOne;
        public final ImageView mImageViewTwo;
        public Tweet mItem;

        public ViewHolderTwoImage(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.news_title);
            mTextView = (TextView) view.findViewById((R.id.news_text));
            mImageViewOne = (ImageView) view.findViewById((R.id.news_image_one));
            mImageViewTwo = (ImageView) view.findViewById((R.id.news_image_two));
        }
    }
}