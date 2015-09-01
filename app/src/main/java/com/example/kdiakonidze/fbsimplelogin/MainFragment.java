package com.example.kdiakonidze.fbsimplelogin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class MainFragment extends android.support.v4.app.Fragment {

    ImageView imageView;
    TextView textView;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            if(profile !=null){
                textView.setText(" FB "+profile.getName());

//                URL img_value = null;
//                try {
//                    img_value = new URL(""+profile.getProfilePictureUri(128,128));
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//                Bitmap mIcon1=null;
//                try {
//                    mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                Uri uri = profile.getProfilePictureUri(200,400);
                //uri.toString()
//                imageView.setImageURI(uri);

                Picasso.with(getActivity())
                        .load(uri)
                        .resize(400,200)
                        .centerCrop()
                        .into(imageView);

            }

            Toast.makeText(getActivity(),"warmatebit sevida",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {

            Toast.makeText(getActivity(),"Canseled",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(FacebookException e) {
            Toast.makeText(getActivity(),"Errorio",Toast.LENGTH_LONG).show();

        }
    };

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.fbtext);
        imageView = (ImageView) view.findViewById(R.id.profileImage);

        LoginButton loginButton = (LoginButton) view.findViewById(R.id.FB_btn);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, mCallback);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
