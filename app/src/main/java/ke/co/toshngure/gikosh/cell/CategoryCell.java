package ke.co.toshngure.gikosh.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.model.Category;

/**
 * Created by Anthony Ngure on 31/12/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class CategoryCell extends SimpleCell<Category, CategoryCell.CategoryViewHolder> {

    public CategoryCell(@NonNull Category item) {
        super(item);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.cell_category;
    }

    @NonNull
    @Override
    protected CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return new CategoryViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i, @NonNull Context context, Object o) {
        categoryViewHolder.bind(getItem(), context);
    }

    static class CategoryViewHolder extends SimpleViewHolder {

        @BindView(R.id.nameTV)
        TextView nameTV;
        private Category category;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Category item, Context context) {
            this.category = item;
            nameTV.setText(category.getName());
        }
    }
}
