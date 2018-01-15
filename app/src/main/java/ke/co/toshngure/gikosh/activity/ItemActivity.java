package ke.co.toshngure.gikosh.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.toshngure.basecode.images.NetworkImage;
import ke.co.toshngure.basecode.view.MaterialTextView;
import ke.co.toshngure.editor.Editor;
import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.model.Item;
import ke.co.toshngure.gikosh.utils.Utils;
import ke.co.toshngure.gikosh.view.SpecificationView;

public class ItemActivity extends BaseActivity {

    private static final String EXTRA_ITEM = "extra_item";
    private static final String EXTRA_ITEM_PREVIEW = "extra_item_preview";
    @BindView(R.id.storeAvatarNI)
    NetworkImage storeAvatarNI;
    @BindView(R.id.storeNameTV)
    TextView storeNameTV;
    @BindView(R.id.storeLocationTV)
    MaterialTextView storeLocationTV;
    @BindView(R.id.carouselView)
    CarouselView carouselView;
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbarSubTitle)
    TextView toolbarSubTitle;
    @BindView(R.id.descriptionRenderer)
    Editor descriptionRenderer;
    @BindView(R.id.colorsSV)
    SpecificationView colorsSV;
    @BindView(R.id.sizesSV)
    SpecificationView sizesSV;
    @BindView(R.id.categorySV)
    SpecificationView categorySV;
    @BindView(R.id.brandSV)
    SpecificationView brandSV;
    @BindView(R.id.conditionSV)
    SpecificationView conditionSV;
    @BindView(R.id.offerBtn)
    Button offerBtn;
    @BindView(R.id.buyBtn)
    Button buyBtn;
    private Item mItem;
    private boolean mItemIsPreview;

    public static void start(Context context, Item item, boolean preview) {
        Intent starter = new Intent(context, ItemActivity.class);
        starter.putExtra(EXTRA_ITEM, item);
        starter.putExtra(EXTRA_ITEM_PREVIEW, preview);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mItem = getIntent().getExtras().getParcelable(EXTRA_ITEM);
        mItemIsPreview = getIntent().getExtras().getBoolean(EXTRA_ITEM_PREVIEW, false);

        toolbarTitle.setText(mItem.getName());

        storeNameTV.setText(mItem.getName());
        storeAvatarNI.loadImageFromNetwork("https://lorempixel.com/400/400/business/?9");


        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(mItem.getMedia().size());
        carouselView.setViewListener(position -> {
            NetworkImage imageNI = (NetworkImage)
                    LayoutInflater.from(this).inflate(R.layout.content_item_carousel_item, null);
            if (mItemIsPreview) {
                imageNI.loadImageFromMediaStore(mItem.getMedia().get(position).getUrl());
            } else {
                imageNI.loadImageFromNetwork(mItem.getMedia().get(position).getUrl());
            }
            return imageNI;
        });

        descriptionRenderer.render(mItem.getDescription());
        if (TextUtils.isEmpty(mItem.getColors())) {
            colorsSV.setVisibility(View.GONE);
        } else {
            colorsSV.addChips(Utils.arrayFromHashSeparatedString(mItem.getColors()));
        }
        if (TextUtils.isEmpty(mItem.getSizes())) {
            sizesSV.setVisibility(View.GONE);
        } else {
            sizesSV.addChips(Utils.arrayFromHashSeparatedString(mItem.getSizes()));
        }
        if (TextUtils.isEmpty(mItem.getBrands())) {
            brandSV.setVisibility(View.GONE);
        } else {
            brandSV.addChips(Utils.arrayFromHashSeparatedString(mItem.getBrands()));
        }
        categorySV.addChips(mItem.getCategory().getName());
        conditionSV.addChips(mItem.getCondition());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @OnClick(R.id.offerBtn)
    public void onOfferBtnClicked() {
    }

    @OnClick(R.id.buyBtn)
    public void onBuyBtnClicked() {
    }
}
