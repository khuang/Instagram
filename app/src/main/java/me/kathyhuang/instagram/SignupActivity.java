package me.kathyhuang.instagram;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.btnSignup) Button btnSignup;
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etUsername) EditText etUsername;
    @BindView(R.id.etPassword) EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ParseUser.getCurrentUser().logOut();

        ButterKnife.bind(this);

        getSupportActionBar().hide();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if(!email.isEmpty() || !username.isEmpty() || !password.isEmpty()){

                    ParseUser user = new ParseUser();
                    user.setEmail(email);
                    user.setUsername(username);
                    user.setPassword(password);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                Log.d("SignupActivity", "Signup successful.");
                            }else{
                                Log.d("SignupActivity", "Signup failed.");
                                Toast.makeText(SignupActivity.this, "Signup failed.", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    });

                    final Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else{
                    Toast.makeText(SignupActivity.this, "Fields cannot be blank.", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}
