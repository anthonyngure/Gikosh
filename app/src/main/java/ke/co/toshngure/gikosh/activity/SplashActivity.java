package ke.co.toshngure.gikosh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.jaeger.library.StatusBarUtil;
import com.loopj.android.http.RequestParams;
import com.synnapps.carouselview.CarouselView;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.toshngure.basecode.log.BeeLog;
import ke.co.toshngure.basecode.networking.ResponseHandler;
import ke.co.toshngure.basecode.utils.BaseUtils;
import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.fragment.SignUpFragment;
import ke.co.toshngure.gikosh.model.FacebookUser;
import ke.co.toshngure.gikosh.network.BackEnd;
import ke.co.toshngure.gikosh.network.Client;
import ke.co.toshngure.gikosh.utils.PrefUtils;
import mehdi.sakout.fancybuttons.FancyButton;


public class SplashActivity extends BaseActivity implements
        FacebookCallback<LoginResult> {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private static final String[] readPermissions = new String[]{"email", "public_profile"};

    private CallbackManager mCallbackManager;

    LoginManager mLoginManager;
    AccessTokenTracker mAccessTokenTracker;

    @BindView(R.id.loginButton)
    FancyButton mLoginButton;

    @BindView(R.id.logoutButton)
    FancyButton mLogoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (!isLoggedIn()) {

            //Has not logged in with fb

            setContentView(R.layout.activity_splash);
            ButterKnife.bind(this);

            StatusBarUtil.setTransparent(this);


            CarouselView carouselView = findViewById(R.id.carouselView);
            carouselView.setPageCount(3);

            carouselView.setImageListener((position, imageView) -> {
                imageView.setImageResource(R.drawable.dummy);
            });

            updateFacebookButtonUI();

            setUpFacebookLogin();

        } else {
            startNewTaskActivity(new Intent(SplashActivity.this, MainActivity.class));
            SplashActivity.this.finish();
        }

    }

    @OnClick(R.id.signUpBtn)
    public void signUp() {
        FragmentActivity.start(getThis(), SignUpFragment.newInstance(), getString(R.string.sign_up));
    }

    @OnClick(R.id.logoutButton)
    public void logout() {
        mLoginManager.logOut();
        updateFacebookButtonUI();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        this.finish();
    }

    private void setUpFacebookLogin() {
        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                updateFacebookButtonUI();
            }
        };

        mLoginManager = LoginManager.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        mLoginManager.registerCallback(mCallbackManager, this);
        mLoginButton.setOnClickListener(view -> {
            if (AccessToken.getCurrentAccessToken() != null) {
                if (PrefUtils.getInstance().getFacebookUser() != null){
                    connect();
                } else {
                    readUserFBData();
                }
            } else {
                mAccessTokenTracker.startTracking();
                showProgressDialog();
                mLoginManager.logInWithReadPermissions(this, Arrays.asList(readPermissions));
            }
        });
    }

    private void updateFacebookButtonUI() {
        if (PrefUtils.getInstance().getFacebookUser() != null) {
            mLoginButton.setText(getString(R.string.continue_as, PrefUtils.getInstance().getFacebookUser().getFirstName()));
            mLogoutButton.setVisibility(View.VISIBLE);
        } else {
            mLoginButton.setText(getString(R.string.sign_in_with_facebook));
            mLogoutButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onSuccess(LoginResult loginResult) {
        toastDebug("Facebook Login onSuccess");
        readUserFBData();
    }

    @Override
    public void onCancel() {
        toastDebug("Facebook Login onCancel");
        hideProgressDialog();
    }

    @Override
    public void onError(FacebookException error) {
        BeeLog.e(TAG, error);
        toastDebug("Facebook Login onError " + String.valueOf(error));
        hideProgressDialog();
    }

    private void readUserFBData() {
        Bundle params = new Bundle();
        params.putString("fields", "email,id,name,first_name,last_name,picture,age_range,gender");
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                (object, response) -> {
                    BeeLog.i(TAG, String.valueOf(response));
                    FacebookUser user = BaseUtils.getSafeGson().fromJson(response.getJSONObject().toString(), FacebookUser.class);
                    BeeLog.i(TAG, String.valueOf(user));
                    PrefUtils.getInstance().saveFacebookUser(user);
                    hideProgressDialog();
                    //Connect to sign in to the api if the facebook user data exists
                    if (PrefUtils.getInstance().getFacebookUser() != null) {
                        connect();
                    }
                    updateFacebookButtonUI();
                });

        request.setParameters(params);
        request.executeAsync();
    }

    public void connect() {
        super.connect();

        FacebookUser user = PrefUtils.getInstance().getFacebookUser();

        RequestParams params = new RequestParams();
        params.put(BackEnd.Params.EMAIL, user.getEmail());
        params.put(BackEnd.Params.FACEBOOK_ID, user.getId());
        params.put(BackEnd.Params.PICTURE_URL, user.getPicture().getData().getUrl());
        params.put(BackEnd.Params.NAME, user.getName());
        params.put(BackEnd.Params.FIRST_NAME, user.getFirstName());
        params.put(BackEnd.Params.LAST_NAME, user.getLastName());
        params.put(BackEnd.Params.GENDER, user.getGender());
        params.put(BackEnd.Params.AGE_RANGE_MIN, user.getAgeRange().getMin());

        Client.getInstance().getClient()
                .post(Client.absoluteUrl(BackEnd.EndPoints.FACEBOOK_SIGN_IN),
                        params, new ResponseHandler(this));
    }

    @Override
    protected void onSuccessResponse(JSONObject data, JSONObject meta) {
        super.onSuccessResponse(data, meta);
        onAuthSuccessful(data, meta);
    }

}

 /*private void generateHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                BeeLog.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }*/
