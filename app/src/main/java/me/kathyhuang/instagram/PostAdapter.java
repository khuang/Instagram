package me.kathyhuang.instagram;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kathyhuang.instagram.models.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private List<Post> mPosts;
    Context context;

    public PostAdapter(List<Post> posts){
        mPosts = posts;
    }

    //for each row, inflate the layout and cache references in the ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get data according to position
        Post post = mPosts.get(position);

        holder.tvDescription.setText(post.getDescription());
        holder.tvUsername.setText(post.getUser().getUsername());

        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.ivPicture);

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDescription) TextView tvDescription;
        @BindView(R.id.tvUsername) TextView tvUsername;
        @BindView(R.id.ivPicture) ImageView ivPicture;

        public ViewHolder(View itemView){
            super(itemView);

            //bind views from xml file
            ButterKnife.bind(this, itemView);

        }

    }
}
