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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class MainFragment extends android.support.v4.app.Fragment {
    AccessToken accessToken;
    String ss;
    ImageView imageView, imig2;
    TextView textView;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            if(profile !=null){
                textView.setText(" FB "+profile.getName());

                Uri uri = profile.getProfilePictureUri(300, 300);
                ss = uri.toString();
//                imageView.setImageURI(uri);

                Picasso.with(getActivity())
                        .load(uri)
                        .resize(400,400)
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
// MY FB ID 1209856909031863 k.d.
//          100000226954596
//            1188484511169103

    // 100000444121344 mari  mariiii87
    //    http://graph.facebook.com/100000226954596/picture?width=300
//    http://graph.facebook.com/1209856909031863/picture?type=square
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.fbtext);
        imageView = (ImageView) view.findViewById(R.id.profileImage);
        imig2 = (ImageView) view.findViewById(R.id.image2);
        Button bt2 = (Button) view.findViewById(R.id.bt2);

//        AccessToken accessToken =



        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile profile = Profile.getCurrentProfile();
                if(profile != null) {
                    textView.setText(profile.getId());

                    GraphRequest request = GraphRequest.newMeRequest(
                            accessToken,
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {

                                    textView.setText(object.toString());
                                }
                            });

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "gender,cover,birthday");
                    request.setParameters(parameters);
                    request.executeAsync();

//                    try {
//                        URL imgUrl = new URL("http://graph.facebook.com/"+profile.getId()+"/picture?type=small");
//                        Bitmap bmp = BitmapFactory.decodeStream(imgUrl.openConnection().getInputStream() );
//                        imig2.setImageBitmap(bmp);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }


                }
            }
        });

//    *******************************
        Button button = (Button) view.findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Profile profile = Profile.getCurrentProfile();
                if(profile != null) {
                    textView.setText(profile.getProfilePictureUri(100,100).toString());

                    Picasso.with(getActivity())
                            .load("http://graph.facebook.com/100000444121344/picture?width=300")
                            .resize(300, 300)
                            .centerCrop()
                            .into(imig2);
                }
            }
        });

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
