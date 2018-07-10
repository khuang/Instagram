package me.kathyhuang.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import me.kathyhuang.instagram.models.Post;

public class ParseApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("instagram")
                .clientKey("instagramsupersecretkey")
                .server("http://khuang-fbu-instagram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
