package me.kathyhuang.instagram;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.kathyhuang.instagram.models.Post;

public class ProfileFragment extends Fragment {

    @BindView(R.id.btnLogout) Button btnLogout;
    @BindView(R.id.ivProfilePic) ParseImageView ivProfilePic;
    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.rvSimplePosts) RecyclerView rvSimplePosts;

    private Unbinder unbinder;
    private OnFragmentInteractionListener mListener;

    private SimplePostAdapter simplePostAdapter;
    private final ArrayList<Post> simplePosts = new ArrayList<>();

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder =
                ButterKnife.bind(this, view);

        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mListener.onLogoutButtonPressed(view);
            }
        });

        simplePostAdapter = new SimplePostAdapter(simplePosts);

        rvSimplePosts.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvSimplePosts.setAdapter(simplePostAdapter);

        ParseUser currentUser = ParseUser.getCurrentUser();
        try{
            String profilePic = currentUser.getParseFile("profilePic").getUrl();
            if(profilePic != null){

                Glide.with(getContext())
                        .load(profilePic)
                        .apply(
                                RequestOptions.circleCropTransform()
                        )
                        .into(ivProfilePic);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        tvUsername.setText(currentUser.getUsername());

        // Define the class we would like to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // Define our query conditions
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        // Execute the find asynchronously
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, com.parse.ParseException e) {
                if(e == null){
                    simplePosts.clear();
                    simplePosts.addAll(objects);
                    simplePostAdapter.notifyDataSetChanged();

                    Log.d("ProfileFragment", simplePosts.toString());
                }else{
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onLogoutButtonPressed(View v);
    }
}
