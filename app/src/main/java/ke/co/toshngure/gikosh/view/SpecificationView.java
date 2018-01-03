package ke.co.toshngure.gikosh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.view.chipcloud.ChipCloud;

/**
 * Created by Anthony Ngure on 01/01/2018.
 * Email : anthonyngure25@gmail.com.
 */

public class SpecificationView extends FrameLayout {
    @BindView(R.id.iconIV)
    ImageView iconIV;
    @BindView(R.id.nameTV)
    TextView nameTV;

    @BindView(R.id.chipCloud)
    ChipCloud chipCloud;
    @BindView(R.id.noChipsTV)
    TextView noChipsTV;
    @BindView(R.id.actionBtn)
    TextView actionBtn;
    private ActionListener actionListener;

    public SpecificationView(@NonNull Context context) {
        this(context, null);
    }

    public SpecificationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpecificationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_specification, this, true);
        ButterKnife.bind(this);
        init(attrs);

        if (isInEditMode()) {
            addChips("XS", "S", "M", "L", "XL", "XXL");
            chipCloud.setSelectedChip(2);
        }
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SpecificationView);
        setName(typedArray.getString(R.styleable.SpecificationView_specificationName));
        setNoChipsText(typedArray.getString(R.styleable.SpecificationView_specificationNoChipsText));
        iconIV.setImageDrawable(typedArray.getDrawable(R.styleable.SpecificationView_specificationIcon));
        typedArray.recycle();
    }

    public void setNoChipsText(String string) {
        if (string == null){
            noChipsTV.setText(R.string.not_applicable);
            return;
        }
        noChipsTV.setText(string);
    }

    public void setName(String name) {
        nameTV.setText(name);
    }

    public void addChips(String... chips) {
        if (chips == null) {
            return;
        }
        chipCloud.removeAllViews();
        chipCloud.addChips(chips);
        noChipsTV.setVisibility(chips.length > 0 ? GONE : VISIBLE);
    }

    public ChipCloud getChipCloud() {
        return chipCloud;
    }

    public void setActionListener(@StringRes int actionText, ActionListener actionListener) {
        this.actionListener = actionListener;
        this.actionBtn.setText(actionText);
        this.actionBtn.setVisibility(VISIBLE);
    }

    @OnClick(R.id.actionBtn)
    public void onViewClicked() {
        this.actionListener.onClickActionBtn();
    }

    public interface ActionListener {
        void onClickActionBtn();
    }
}
