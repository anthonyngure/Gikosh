package ke.co.toshngure.gikosh.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import butterknife.BindView;
import ke.co.toshngure.gikosh.R;

public class ScrollingActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    private boolean isHideToolbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        appBar.addOnOffsetChangedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public static void start(Context context) {
        Intent starter = new Intent(context, ScrollingActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            getToolbar().setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            getToolbar().setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }

    }

    public void testBtn(View view){
        toastDebug("Clicked btn");
    }

}
