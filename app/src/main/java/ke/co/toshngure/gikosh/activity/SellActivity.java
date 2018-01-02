package ke.co.toshngure.gikosh.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.adroitandroid.chipcloud.ChipCloud;
import com.adroitandroid.chipcloud.ChipListener;
import com.github.irshulx.Editor;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.toshngure.basecode.utils.BaseUtils;
import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.fragment.SelectCategoryFragment;
import ke.co.toshngure.gikosh.model.Category;
import ke.co.toshngure.gikosh.model.Item;
import ke.co.toshngure.gikosh.utils.PrefUtils;
import ke.co.toshngure.gikosh.utils.Utils;
import ke.co.toshngure.gikosh.view.ImagePicker;
import ke.co.toshngure.gikosh.view.SpecificationView;

public class SellActivity extends BaseActivity implements SelectCategoryFragment.Listener {

    private static final String EXTRA_CATEGORY = "extra_category";
    private static final int IMAGE_ONE_REQUEST = 100;
    private static final int IMAGE_TWO_REQUEST = 200;
    private static final int IMAGE_THREE_REQUEST = 300;
    private static final int IMAGE_FOUR_REQUEST = 400;
    private static final int DESCRIPTION_REQUEST = 500;

    @BindView(R.id.nameMET)
    MaterialEditText nameMET;
    @BindView(R.id.priceMET)
    MaterialEditText priceMET;
    @BindView(R.id.descriptionRenderer)
    Editor descriptionRenderer;
    @BindView(R.id.imagePicker1)
    ImagePicker imagePicker1;
    @BindView(R.id.imagePicker2)
    ImagePicker imagePicker2;
    @BindView(R.id.imagePicker3)
    ImagePicker imagePicker3;
    @BindView(R.id.imagePicker4)
    ImagePicker imagePicker4;
    @BindView(R.id.sizeSV)
    SpecificationView sizeSV;
    @BindView(R.id.categorySV)
    SpecificationView categorySV;
    @BindView(R.id.brandSV)
    SpecificationView brandSV;
    @BindView(R.id.conditionSV)
    SpecificationView conditionSV;
    private Category mCategory;
    private SelectCategoryFragment mSelectCategoryFragment;

    public static void start(Context context, Category category) {
        Intent starter = new Intent(context, SellActivity.class);
        starter.putExtra(EXTRA_CATEGORY, category);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        ButterKnife.bind(this);

        mCategory = getIntent().getExtras().getParcelable(EXTRA_CATEGORY);

        nameMET.addValidator(BaseUtils.createRequiredValidator(getString(R.string.error_field_is_required)));
        BaseUtils.cacheInput(nameMET, R.string.hint_item_name, PrefUtils.getInstance());

        priceMET.addValidator(BaseUtils.createRequiredValidator(getString(R.string.error_field_is_required)));
        BaseUtils.cacheInput(priceMET, R.string.hint_item_price, PrefUtils.getInstance());

        descriptionRenderer.setDividerLayout(R.layout.editor_divider);
        descriptionRenderer.setListItemLayout(R.layout.editor_list_item);
        descriptionRenderer.render();

        imagePicker1.setActivity(this, IMAGE_ONE_REQUEST, true);
        imagePicker2.setActivity(this, IMAGE_TWO_REQUEST);
        imagePicker3.setActivity(this, IMAGE_THREE_REQUEST);
        imagePicker4.setActivity(this, IMAGE_FOUR_REQUEST);

        /*Size spec*/
        sizeSV.addChips(Utils.arrayFromHashSeparatedString(mCategory.getSizeOptions()));
        sizeSV.getChipCloud().setMode(ChipCloud.Mode.MULTI);
        sizeSV.getChipCloud().setChipListener(new ChipListener() {
            @Override
            public void chipSelected(int i) {
            }

            @Override
            public void chipDeselected(int i) {

            }
        });

        /*Category Spec*/
        categorySV.setName(mCategory.getName());
        String[] childCategoryNames = new String[mCategory.getChildren().size()];
        for (int i = 0; i < mCategory.getChildren().size(); i++) {
            childCategoryNames[i] = mCategory.getChildren().get(i).getName();
        }
        categorySV.addChips(childCategoryNames);
        categorySV.getChipCloud().setMode(ChipCloud.Mode.SINGLE);
        categorySV.getChipCloud().setChipListener(new ChipListener() {
            @Override
            public void chipSelected(int i) {

            }

            @Override
            public void chipDeselected(int i) {

            }
        });
        categorySV.setActionListener(R.string.change, () -> {
            mSelectCategoryFragment = SelectCategoryFragment.newInstance();
            mSelectCategoryFragment.show(getSupportFragmentManager(), SelectCategoryFragment.class.getSimpleName());
        });


        /*Brand spec*/
        brandSV.setActionListener(R.string.add_brand, () -> {

        });

        /*Condition Spec*/
        conditionSV.addChips(getResources().getStringArray(R.array.specification_conditions));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.descriptionRenderer)
    public void writeDescription(View view) {
        EditorActivity.start(this, DESCRIPTION_REQUEST, descriptionRenderer.getContentAsHTML());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DESCRIPTION_REQUEST && resultCode == Activity.RESULT_OK){
            if (data.getExtras() != null){
                String desc = data.getExtras().getString(EditorActivity.EXTRA_TEXT);
                if (desc != null){
                    descriptionRenderer.render(desc.trim());
                }
            }
        } else {
            imagePicker1.onActivityResult(requestCode, resultCode, data);
            imagePicker2.onActivityResult(requestCode, resultCode, data);
            imagePicker3.onActivityResult(requestCode, resultCode, data);
            imagePicker4.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.previewBtn)
    public void onViewClicked() {
        if (nameMET.validate() && priceMET.validate()) {
            if (imagePicker1.getFile() != null) {
                Item item = new Item();
                item.setName(nameMET.getText().toString());
                item.setPrice(Double.parseDouble(priceMET.getText().toString()));
                item.setDescription(descriptionRenderer.getContentAsHTML());
                ItemActivity.start(this, mCategory, item);
            } else {
                toast(R.string.error_photo);
                Snackbar.make(nameMET, R.string.error_photo, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.ok, view -> {
                        }).show();
            }
        }
    }

    @Override
    public void onCategorySelected(Category category) {

        mCategory = category;

        sizeSV.addChips(Utils.arrayFromHashSeparatedString(mCategory.getSizeOptions()));

        categorySV.setName(mCategory.getName());
        String[] childCategoryNames = new String[mCategory.getChildren().size()];
        for (int i = 0; i < mCategory.getChildren().size(); i++) {
            childCategoryNames[i] = mCategory.getChildren().get(i).getName();
        }
        categorySV.addChips(childCategoryNames);

        mSelectCategoryFragment = null;
    }


}
