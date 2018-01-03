package ke.co.toshngure.gikosh.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.toshngure.basecode.utils.BaseUtils;
import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.model.Post;
import ke.co.toshngure.gikosh.view.autocomplete.AutoCompleteView;

public class AutocompleteTextActivity extends BaseActivity implements AutoCompleteView.SelectionListener {

    @BindView(R.id.autoCompleteTV)
    AutoCompleteView autoCompleteTV;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public static void start(Context context) {
        Intent starter = new Intent(context, AutocompleteTextActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete_text);
        ButterKnife.bind(this);

        autoCompleteTV.setAutocompleteUrl("https://jsonplaceholder.typicode.com/posts?_limit=10?query=");
        autoCompleteTV.setLoadingIndicator(progressBar);
        autoCompleteTV.setSelectionListener(this);
        autoCompleteTV.setParser(response -> {
            List<String> strings = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Post post = BaseUtils.getSafeGson().fromJson(jsonArray.getJSONObject(i).toString(), Post.class);
                    strings.add(post.getTitle());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return strings;
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemSelected(String string) {
        toast(string);
    }
}
