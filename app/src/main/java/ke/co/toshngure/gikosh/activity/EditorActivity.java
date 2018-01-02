package ke.co.toshngure.gikosh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.mthli.knife.KnifeText;
import ke.co.toshngure.gikosh.R;

public class EditorActivity extends BaseActivity {

    public static final String EXTRA_TEXT = "extra_text";

    @BindView(R.id.knifeEditor)
    KnifeText knifeEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            String text = getIntent().getExtras().getString(EXTRA_TEXT);
            knifeEditor.setText(text);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void start(Activity activity, int requestCode, @Nullable String text) {
        Intent starter = new Intent(activity, EditorActivity.class);
        starter.putExtra(EXTRA_TEXT, text);
        activity.startActivityForResult(starter, requestCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_editor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_cart).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                Intent data = new Intent();
                data.putExtra(EXTRA_TEXT, knifeEditor.toHtml());
                setResult(Activity.RESULT_OK, data);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick({R.id.unorderedListBtn, R.id.orderedListBtn, R.id.boldBtn, R.id.italicBtn, R.id.underlineBtn, R.id.justifyLeftBtn, R.id.justifyRightBtn, R.id.indentBtn, R.id.outdentBtn, R.id.undoBtn, R.id.redoBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.unorderedListBtn:
                knifeEditor.bullet(knifeEditor.contains(KnifeText.FORMAT_BULLET));
                break;
            case R.id.orderedListBtn:
                break;
            case R.id.boldBtn:
                break;
            case R.id.italicBtn:
                break;
            case R.id.underlineBtn:
                break;
            case R.id.justifyLeftBtn:
                break;
            case R.id.justifyRightBtn:
                break;
            case R.id.indentBtn:
                break;
            case R.id.outdentBtn:
                break;
            case R.id.undoBtn:
                break;
            case R.id.redoBtn:
                break;
        }
    }
}
