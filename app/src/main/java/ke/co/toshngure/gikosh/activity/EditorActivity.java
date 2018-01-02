package ke.co.toshngure.gikosh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.irshulx.Editor;
import com.github.irshulx.models.EditorTextStyle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.toshngure.gikosh.R;

public class EditorActivity extends BaseActivity {

    public static final String EXTRA_TEXT = "extra_text";

    @BindView(R.id.textEditor)
    Editor textEditor;

    public static void start(Activity activity, int requestCode, @Nullable String html) {
        Intent starter = new Intent(activity, EditorActivity.class);
        starter.putExtra(EXTRA_TEXT, html);
        activity.startActivityForResult(starter, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            String text = getIntent().getExtras().getString(EXTRA_TEXT);
            if (text != null) {
                textEditor.render(text);
            }
        }

        textEditor.setDividerLayout(R.layout.editor_divider);
        textEditor.setListItemLayout(R.layout.editor_list_item);
        textEditor.render();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                data.putExtra(EXTRA_TEXT, textEditor.getContentAsHTML());
                setResult(Activity.RESULT_OK, data);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick({R.id.boldBtn, R.id.italicBtn, R.id.alignLeftBtn, R.id.alignRightBtn, R.id.dividerBtn,
            R.id.unorderedListBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.boldBtn:
                textEditor.updateTextStyle(EditorTextStyle.BOLD);
                break;
            case R.id.italicBtn:
                textEditor.updateTextStyle(EditorTextStyle.ITALIC);
                break;
            case R.id.alignLeftBtn:
                textEditor.updateTextStyle(EditorTextStyle.INDENT);
                break;
            case R.id.alignRightBtn:
                textEditor.updateTextStyle(EditorTextStyle.OUTDENT);
                break;
            case R.id.dividerBtn:
                textEditor.insertDivider();
                break;
            case R.id.unorderedListBtn:
                textEditor.insertList(false);
                break;
        }
    }

}
