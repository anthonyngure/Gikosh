package ke.co.toshngure.gikosh.fragment;


import android.support.v7.app.AlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ke.co.toshngure.basecode.fragment.BaseAppFragment;
import ke.co.toshngure.basecode.log.BeeLog;
import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.activity.BaseActivity;
import ke.co.toshngure.gikosh.model.User;
import ke.co.toshngure.gikosh.network.BackEnd;
import ke.co.toshngure.gikosh.utils.PrefUtils;

/**
 * Created by Anthony Ngure on 11/06/2017.
 * Email : anthonyngure25@gmail.com.
 * Company : VibeCampo Social Network..
 */

public class BaseFragment extends BaseAppFragment {

    public static final String TAG = BaseFragment.class.getSimpleName();

    public BaseFragment() {
    }

    protected User getUser() {
        return PrefUtils.getInstance().getUser();
    }

    public void signOut() {
        ((BaseActivity) getActivity()).signOut();
    }


    protected void onSuccessResponse(JSONObject data, JSONObject meta) {

    }

    protected void onSuccessResponse(JSONArray data, JSONObject meta) {

    }


    @Override
    public void onConnectionFailed(int statusCode, JSONObject response) {
        BeeLog.e(TAG, "Connection failed! " + statusCode + ", " + String.valueOf(response));
        hideProgressDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if ((statusCode == 0) || (statusCode == 408)) {
            builder.setTitle(R.string.connection_timed_out)
                    .setMessage(R.string.error_connection)
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(R.string.retry, (dialog, which) -> connect()).create().show();
        } else if (statusCode == 500) {
            builder.setCancelable(true)
                    .setTitle(R.string.server_error)
                    .setMessage(getString(R.string.error_application))
                    .setNegativeButton(R.string.report, (dialog, which) -> {
                    })
                    .setPositiveButton(android.R.string.ok, null).create().show();
        } else {
            try {
                if (response != null) {
                    JSONObject meta = response.getJSONObject(BackEnd.Response.META);
                    JSONObject data = response.getJSONObject(BackEnd.Response.DATA);
                    ((BaseActivity) getActivity()).onErrorResponse(meta.getString(BackEnd.Response.ERROR_CODE),
                            meta.getString(BackEnd.Response.MESSAGE), data);
                } else {
                    onConnectionFailed(0, null);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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


    protected boolean isLoggedIn() {
        return ((BaseActivity) getActivity()).isLoggedIn();
    }
}
