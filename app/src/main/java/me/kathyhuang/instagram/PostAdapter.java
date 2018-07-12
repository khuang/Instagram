package me.kathyhuang.instagram;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseImageView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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

        View view = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get data according to position
        Post post = mPosts.get(position);

        holder.tvDescription.setText(post.getDescription());
        holder.tvUsername.setText(post.getUser().getUsername());
        holder.tvCreatedAt.setText(post.getCreatedDateString());
        holder.tvUsername2.setText(post.getUser().getUsername());

        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.ivPicture);

        Glide.with(context)
                .load(post.getUser().getParseFile("profilePic").getUrl())
                .apply(
                        RequestOptions.circleCropTransform()
                )
                .into(holder.ivProfilePic);

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
        @BindView(R.id.ivPicture) ParseImageView ivPicture;
        @BindView(R.id.tvCreatedAt) TextView tvCreatedAt;
        @BindView(R.id.tvUsername2) TextView tvUsername2;
        @BindView(R.id.ivProfilePic) ParseImageView ivProfilePic;
        @BindView(R.id.btnLike) ImageButton btnLike;

        public ViewHolder(View itemView){
            super(itemView);

            //bind views from xml file
            ButterKnife.bind(this, itemView);

            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setSelected(!view.isSelected());
                }
            });

        }


    }
}
