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
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kathyhuang.instagram.models.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    interface Callback {
        void passUser(@NonNull ParseUser user);
    }

    private Callback inputCallback;

    private List<Post> mPosts;
    Context context;
    private List<String> likes;
    String userId;
    private Post post;

    public PostAdapter(List<Post> posts, Callback callback){
        mPosts = posts;
        inputCallback = callback;
    }

    //for each row, inflate the layout and cache references in the ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_post, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.tvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputCallback.passUser(
                        mPosts.get(viewHolder.getAdapterPosition()).getUser()
                );
            }
        });

        viewHolder.tvUsername2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                inputCallback.passUser(
                        mPosts.get(viewHolder.getAdapterPosition()).getUser()
                );
            }
        });

        viewHolder.ivProfilePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                inputCallback.passUser(
                        mPosts.get(viewHolder.getAdapterPosition()).getUser()
                );
            }
        });

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get data according to position
        post = mPosts.get(position);

        userId = ParseUser.getCurrentUser().getObjectId();

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

        try{
            likes = post.getLikes();
            if(likes.contains(userId)){
                holder.btnLike.setSelected(true);
            }else{
                holder.btnLike.setSelected(false);
            }

            holder.tvLikes.setText(Integer.toString(likes.size()) + " Likes");

        }catch(NullPointerException e){
            e.printStackTrace();
            holder.tvLikes.setText("0 Likes");
        }

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
        @BindView(R.id.tvLikes) TextView tvLikes;

        public ViewHolder(View itemView) {
            super(itemView);

            //bind views from xml file
            ButterKnife.bind(this, itemView);

            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (view.isSelected()) {
                        view.setSelected(false);

                        try{
                            likes.remove(userId);
                            tvLikes.setText(Integer.toString(likes.size()) + " Likes");
                        }catch(NullPointerException e){
                            e.printStackTrace();
                        }


                    } else {
                        view.setSelected(true);
                        try{
                            likes.add(userId);
                            tvLikes.setText(Integer.toString(likes.size()) + " Likes");
                        }catch(NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                }
            });

        }

    }
}
