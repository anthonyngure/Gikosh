package ke.co.toshngure.gikosh.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.loopj.android.http.AsyncHttpClient;

import java.util.List;

import ke.co.toshngure.basecode.dataloading.DataLoadingConfig;
import ke.co.toshngure.basecode.dataloading.ModelListBottomSheetFragment;
import ke.co.toshngure.basecode.log.BeeLog;
import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.cell.CategoryCell;
import ke.co.toshngure.gikosh.database.Database;
import ke.co.toshngure.gikosh.model.Category;
import ke.co.toshngure.gikosh.model.CategoryDao;
import ke.co.toshngure.gikosh.network.BackEnd;
import ke.co.toshngure.gikosh.network.Client;

/**
 * Created by Anthony Ngure on 31/12/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class SelectCategoryFragment extends ModelListBottomSheetFragment<Category, CategoryCell>
        implements SimpleCell.OnCellClickListener<Category> {

    private static final String TAG = "SelectCategoryFragment";
    private Listener listener;
    private Category mSelectedCategory;
    private Button nextBtn;
    private TextView selectedCategoryTV;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new IllegalArgumentException("Context must implement SelectCategoryFragment.Listener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        BeeLog.i(TAG, "onResume");
        for (SimpleCell simpleCell : getSimpleRecyclerView().getAllCells()){
            simpleCell.setOnCellClickListener(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        BeeLog.i(TAG, "onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BeeLog.i(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BeeLog.i(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BeeLog.i(TAG, "onDetach");
    }

    public static SelectCategoryFragment newInstance() {

        Bundle args = new Bundle();
        SelectCategoryFragment fragment = new SelectCategoryFragment();
        fragment.setCancelable(false);
        fragment.setRetainInstance(false);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected CategoryCell onCreateCell(Category category) {
        CategoryCell categoryCell = new CategoryCell(category);
        categoryCell.setOnCellClickListener(this);
        return categoryCell;
    }

    @Override
    protected DataLoadingConfig getDataLoadingConfig() {
        return new DataLoadingConfig().setCursorsEnabled(false)
                .setCacheEnabled(true)
                .setAutoRefreshEnabled(Database.getInstance().getDaoSession().getCategoryDao().count() == 0)
                .disableConnection(Database.getInstance().getDaoSession().getCategoryDao().count() > 0)
                .setUrl(Client.absoluteUrl(BackEnd.EndPoints.CATEGORIES));
    }

    @Override
    protected List<Category> onLoadCaches() {
        return Database.getInstance().getDaoSession().getCategoryDao().queryBuilder()
                .where(CategoryDao.Properties.Depth.eq(0),
                        CategoryDao.Properties.ParentId.eq(0)).list();
    }

    @Override
    protected void onSaveItem(Category item) {
        super.onSaveItem(item);
        Database.getInstance().getDaoSession().getCategoryDao().insertOrReplace(item);
        if (item.getChildren() != null) {
            for (Category child : item.getChildren()) {
                Database.getInstance().getDaoSession().getCategoryDao().insertOrReplace(child);
            }
        }
    }

    @Override
    protected Class<Category> getModelClass() {
        return Category.class;
    }

    @Override
    protected AsyncHttpClient getClient() {
        return Client.getInstance().getClient();
    }


    @Override
    public void onCellClicked(@NonNull Category category) {
        category.getChildren();
        if (mSelectedCategory != null) {
            mSelectedCategory = null;
            selectedCategoryTV.setText("");
        } else {
            mSelectedCategory = category;
            selectedCategoryTV.setText(mSelectedCategory.getName());
        }
        category.setSelected(!category.isSelected());
        nextBtn.setEnabled(mSelectedCategory != null);
    }

    @Override
    protected int getFreshLoadView() {
        return R.layout.fragment_select_category_fresh_load;
    }

    @Override
    protected void setUpTopView(FrameLayout topViewContainer) {
        super.setUpTopView(topViewContainer);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_select_category_top_view, null);
        view.findViewById(R.id.cancelBtn).setOnClickListener(view12 -> {
            mSelectedCategory = null;
            dismissAllowingStateLoss();
        });
        nextBtn = view.findViewById(R.id.nextBtn);
        selectedCategoryTV = view.findViewById(R.id.selectedCategoryTV);
        nextBtn.setOnClickListener(view1 -> {
            listener.onCategorySelected(mSelectedCategory);
            mSelectedCategory = null;
            dismiss();
        });
        topViewContainer.addView(view);
    }


    @Override
    protected void setUpSimpleRecyclerView(SimpleRecyclerView simpleRecyclerView) {
        super.setUpSimpleRecyclerView(simpleRecyclerView);
        simpleRecyclerView.showDivider();
    }

    @Override
    public void connect() {
        super.connect();
        Toast.makeText(getActivity(), "Connect called!", Toast.LENGTH_SHORT).show();
    }


    public interface Listener {
        void onCategorySelected(Category category);
    }

}
