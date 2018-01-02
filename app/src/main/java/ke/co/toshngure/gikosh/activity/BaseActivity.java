/*
 * Copyright (c) 2017. Laysan Incorporation
 * Website http://laysan.co.ke
 * Tel +254723203475/+254706356815
 */

package ke.co.toshngure.gikosh.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import ke.co.toshngure.basecode.app.BaseAppActivity;
import ke.co.toshngure.basecode.app.DialogAsyncTask;
import ke.co.toshngure.basecode.log.BeeLog;
import ke.co.toshngure.basecode.networking.ConnectionListener;
import ke.co.toshngure.basecode.utils.BaseUtils;
import ke.co.toshngure.gikosh.App;
import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.database.Database;
import ke.co.toshngure.gikosh.model.User;
import ke.co.toshngure.gikosh.network.BackEnd;
import ke.co.toshngure.gikosh.network.Client;
import ke.co.toshngure.gikosh.utils.PrefUtils;


/**
 * Created by Anthony Ngure on 7/1/2016.
 * Email : anthonyngure25@gmail.com.
 */

@SuppressLint("Registered")
public class BaseActivity extends BaseAppActivity implements ConnectionListener {

    private static final String TAG = BaseActivity.class.getSimpleName();


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            Client.getInstance().getClient().cancelRequests(this, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLoggedIn() {
        return ((getUser() != null)
                && (PrefUtils.getInstance().getFacebookUser() != null)
                && ((AccessToken.getCurrentAccessToken() != null)));
    }

    protected User getUser() {
        return PrefUtils.getInstance().getUser();
    }

    public void onAuthSuccessful(JSONObject data, JSONObject meta) {

        User user = BaseUtils.getSafeGson().fromJson(data.toString(), User.class);
        PrefUtils.getInstance().saveUser(user);

        Client.getInstance().invalidate();

        startNewTaskActivity(new Intent(this, MainActivity.class));

        //toast(meta.message);

        this.finish();
    }

    @SuppressLint("StaticFieldLeak")
    public void signOut() {

        new DialogAsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                //mLocalUserConnectedRef.setValue(ServerValue.TIMESTAMP);
                /*try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                User user = PrefUtils.getInstance().getUser();
                PrefUtils.getInstance().signOut();
                LoginManager.getInstance().logOut();
                App.getInstance().getJobManager().clear();
                Database.getInstance().clean();
                Client.getInstance().invalidate();
                PrefUtils.getInstance().writeString(R.string.email, user.getEmail());
                PrefUtils.getInstance().writeString(R.string.phone, user.getPhone());
                return null;
            }

            @Override
            protected Activity getActivity() {
                return getThis();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                startNewTaskActivity(new Intent(getActivity(), SplashActivity.class));
                exitActivity();
            }
        }.execute();
    }

    @Override
    public void connect() {

    }

    @Override
    public void onConnectionStarted() {
        showProgressDialog();
    }


    @Override
    public void onConnectionFailed(int statusCode, JSONObject response) {
        BeeLog.e(TAG, "Connection failed! " + statusCode + ", " + String.valueOf(response));
        hideProgressDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if ((statusCode == 0) || (statusCode == 408)) {
            builder.setTitle(R.string.connection_timed_out)
                    .setMessage(R.string.error_connection)
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(R.string.retry, (dialog, which) -> connect()).create().show();
        } else if (statusCode == 500) {
            builder.setCancelable(true)
                    .setTitle(R.string.server_error)
                    .setMessage(getString(R.string.error_application))
                    .setNegativeButton(R.string.report, (dialog, which) -> {})
                    .setPositiveButton(android.R.string.ok, null).create().show();
        } else {
            try {
                if (response != null) {
                    JSONObject meta = response.getJSONObject(BackEnd.Response.META);
                    JSONObject data = response.getJSONObject(BackEnd.Response.DATA);
                    onErrorResponse(
                            meta.getString(BackEnd.Response.ERROR_CODE),
                            meta.getString(BackEnd.Response.MESSAGE),
                            data
                    );
                } else {
                    onConnectionFailed(0, null);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    protected void showErrorAlertDialog(String message) {
        new AlertDialog.Builder(this).setCancelable(true)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show();
    }

    protected void onSuccessResponse(JSONObject data, JSONObject meta) {

    }

    protected void onSuccessResponse(JSONArray data, JSONObject meta) {

    }

    public void onErrorResponse(String errorCode, String message, JSONObject data) {
        switch (errorCode) {
            case BackEnd.ErrorCodes.VALIDATION_ERROR:
                StringBuilder sb = new StringBuilder();
                Iterator<String> iterator = data.keys();
                while (iterator != null && iterator.hasNext()) {
                    String name = iterator.next();
                    try {
                        JSONArray valueErrors = data.getJSONArray(name);
                        sb.append(name.toUpperCase()).append("\n");
                        for (int i = 0; i < valueErrors.length(); i++) {
                            sb.append(valueErrors.get(i)).append("\n");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                showErrorAlertDialog(sb.toString());
                break;
        }
    }

    @Override
    public void onConnectionSuccess(JSONObject response) {
        BeeLog.d(TAG, "onConnectionSuccess, Response = " + String.valueOf(response));
        hideProgressDialog();
        try {
            if (response.get(BackEnd.Response.DATA) instanceof JSONObject) {
                //Data is Object
                onSuccessResponse(response.getJSONObject(BackEnd.Response.DATA), response.getJSONObject(BackEnd.Response.META));
            } else {
                //Data is Array
                onSuccessResponse(response.getJSONArray(BackEnd.Response.DATA), response.getJSONObject(BackEnd.Response.META));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionProgress(int progress) {

    }

    @Override
    public Context getListenerContext() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                TestActivity.start(this);
                return true;
            case R.id.action_cart:
                TestActivity.start(this);
                return true;
            case R.id.action_sign_out:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
