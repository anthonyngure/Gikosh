package ke.co.toshngure.gikosh.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.toshngure.basecode.images.camera.CameraActivity;
import ke.co.toshngure.basecode.images.compression.FileUtil;
import ke.co.toshngure.basecode.images.compression.ImageCompressor;
import ke.co.toshngure.basecode.images.simplecrop.util.CropUtils;
import ke.co.toshngure.gikosh.R;

/**
 * Created by Anthony Ngure on 31/12/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class ImagePicker extends FrameLayout {

    private static final String TAG = "ImagePicker";

    @BindView(R.id.photoIV)
    ImageView photoIV;
    @BindView(R.id.deleteIV)
    ImageView deleteIV;
    @BindView(R.id.addPhotoLL)
    LinearLayout addPhotoLL;
    @BindView(R.id.photoFL)
    FrameLayout photoFL;
    @BindView(R.id.loaderFL)
    FrameLayout loaderFL;
    @BindView(R.id.textTV)
    TextView textTV;
    private File file;

    private AppCompatActivity mActivity;
    private int mRequestCode;

    public ImagePicker(@NonNull Context context) {
        this(context, null);
    }

    public ImagePicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImagePicker(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_image_picker, this, true);
        ButterKnife.bind(this);
    }


    public void setActivity(AppCompatActivity activity, int requestCode) {
        this.mActivity = activity;
        this.mRequestCode = requestCode;
    }

    public void setActivity(AppCompatActivity activity, int requestCode, boolean required) {
        this.mActivity = activity;
        this.mRequestCode = requestCode;
        if (required){
            textTV.setText(Html.fromHtml(mActivity.getString(R.string.add_a_photo_required)));
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*BeeLog.i(TAG, "Request Code: " + String.valueOf(requestCode));
        BeeLog.i(TAG, "Result Code: " + String.valueOf(resultCode));
        BeeLog.i(TAG, "Data: " + String.valueOf(data));*/
        if ((requestCode == mRequestCode) && (resultCode == Activity.RESULT_OK) && data != null) {
            new AsyncTask<Uri, Void, File>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    addPhotoLL.setVisibility(GONE);
                    photoFL.setVisibility(GONE);
                    loaderFL.setVisibility(VISIBLE);
                }

                @Override
                protected File doInBackground(Uri... uris) {
                    try {
                        return new ImageCompressor.Builder(mActivity)
                                .setDestinationDirectoryPath(FileUtil.getAppExternalDirectoryFolder(mActivity))
                                .setFileName("image_" + mRequestCode)
                                .build().compressToFile(FileUtil.from(mActivity, uris[0]));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(File file) {
                    super.onPostExecute(file);
                    ImagePicker.this.file = file;
                    if (file != null) {
                        photoIV.setImageURI(Uri.fromFile(file));
                        addPhotoLL.setVisibility(GONE);
                        loaderFL.setVisibility(GONE);
                        photoFL.setVisibility(VISIBLE);
                    } else {
                        Toast.makeText(mActivity, "Image loading Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute(data.getData());
        }
    }

    @OnClick(R.id.deleteIV)
    public void onDeleteIVClicked() {
        addPhotoLL.setVisibility(VISIBLE);
        loaderFL.setVisibility(GONE);
        photoFL.setVisibility(GONE);
        if (file != null) {
            file.delete();
            file = null;
        }
    }

    @OnClick(R.id.addPhotoLL)
    public void onAddPhotoLLClicked() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.add_item_photo)
                .setItems(new String[]{"Gallery", "Camera"}, (dialogInterface, which) -> {
                    if (which == 0) {
                        CropUtils.pickImage(mActivity, mRequestCode);
                    } else {
                        Intent startCustomCameraIntent = new Intent(mActivity, CameraActivity.class);
                        mActivity.startActivityForResult(startCustomCameraIntent, mRequestCode);
                    }
                })
                .setPositiveButton(R.string.cancel, (dialogInterface, i) -> {

                })
                .show();
    }

    public File getFile() {
        return file;
    }
}
