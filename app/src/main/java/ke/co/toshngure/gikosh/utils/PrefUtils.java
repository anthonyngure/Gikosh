/*
 * Copyright (c) 2017. Laysan Incorporation
 * Website http://laysan.co.ke
 * Tel +254723203475/+254706356815
 */

package ke.co.toshngure.gikosh.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ke.co.toshngure.basecode.utils.BaseUtils;
import ke.co.toshngure.basecode.utils.PrefUtilsImpl;
import ke.co.toshngure.gikosh.App;
import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.model.FacebookUser;
import ke.co.toshngure.gikosh.model.User;


/**
 * Created by Anthony Ngure on 7/1/2016.
 * Email : anthonyngure25@gmail.com.
 */
public class PrefUtils extends PrefUtilsImpl {

    private static final String TAG = PrefUtils.class.getSimpleName();
    private static volatile PrefUtils mInstance;
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private User user;
    private FacebookUser mFacebookUser;

    private PrefUtils(Context context) {
        this.mContext = context;
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static PrefUtils getInstance() {
        PrefUtils localInstance = mInstance;
        if (localInstance == null) {
            synchronized (PrefUtils.class) {
                localInstance = mInstance;
                if (localInstance == null) {
                    mInstance = localInstance = new PrefUtils(App.getInstance());
                }
            }
        }
        return localInstance;
    }


    public void signOut() {
        mSharedPreferences.edit().clear().apply();
        invalidate();
    }


    public void saveUser(User user) {
        writeString(R.string.pref_user, BaseUtils.getSafeGson().toJson(user, User.class));
        invalidate();
    }



    public User getUser() {
        if (user == null) {
            String userJson = mSharedPreferences.getString(mContext.getString(R.string.pref_user), "");
            if (userJson.isEmpty()) {
                return null;
            } else {
                this.user = BaseUtils.getSafeGson().fromJson(userJson, User.class);
                return user;
            }
        }
        return user;
    }

    public void saveFacebookUser(FacebookUser user) {
        writeString(R.string.pref_facebook_user, BaseUtils.getSafeGson().toJson(user, FacebookUser.class));
        invalidate();
    }

    public FacebookUser getFacebookUser() {
        if (mFacebookUser == null) {
            String userJson = mSharedPreferences.getString(mContext.getString(R.string.pref_facebook_user), "");
            if (userJson.isEmpty()) {
                return null;
            } else {
                this.mFacebookUser = BaseUtils.getSafeGson().fromJson(userJson, FacebookUser.class);
                return mFacebookUser;
            }
        }
        return mFacebookUser;
    }

    @Override
    protected SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    @Override
    protected Context getContext() {
        return mContext;
    }

    /**
     * Load shared prefs again
     */
    @Override
    protected void invalidate() {
        mInstance = null;
        mSharedPreferences = null;
        user = null;
        mFacebookUser = null;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }


}
