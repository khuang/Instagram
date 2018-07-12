package me.kathyhuang.instagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.ParseImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kathyhuang.instagram.models.Post;

public class SimplePostAdapter extends RecyclerView.Adapter<SimplePostAdapter.ViewHolder> {

    private final List<Post> mPosts;
    Context context;

    public SimplePostAdapter(List<Post> posts){
        mPosts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_simple_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SimplePostAdapter.ViewHolder holder, int position) {
        Post post = mPosts.get(position);

        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.ivSimplePic);
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

        @BindView(R.id.ivSimplePic) ImageView ivSimplePic;

        public ViewHolder(View itemView){
            super(itemView);

            //bind views from xml file
            ButterKnife.bind(this, itemView);

        }

    }
}
