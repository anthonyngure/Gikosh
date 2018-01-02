package ke.co.toshngure.gikosh.activity;

import android.os.Bundle;
import android.view.Menu;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.fragment.SelectCategoryFragment;
import ke.co.toshngure.gikosh.model.Category;

public class MainActivity extends BaseActivity implements SelectCategoryFragment.Listener {

    @BindView(R.id.floatingActionsMenu)
    FloatingActionsMenu floatingActionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @OnClick(R.id.sellFab)
    public void onSellFabClicked() {
        SelectCategoryFragment.newInstance().show(getSupportFragmentManager(),
                SelectCategoryFragment.class.getSimpleName());
    }

    @OnClick(R.id.huntFab)
    public void onHuntFabClicked() {
        HuntActivity.start(this);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (floatingActionsMenu.isExpanded()){
            floatingActionsMenu.collapse();
        } else {

            this.finish();
        }
    }


    @Override
    public void onCategorySelected(Category category) {
        SellActivity.start(this, category);
        floatingActionsMenu.postDelayed(() -> floatingActionsMenu.collapse(), 2000);

    }
}
