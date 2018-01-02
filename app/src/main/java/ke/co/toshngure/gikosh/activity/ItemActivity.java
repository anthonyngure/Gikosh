package ke.co.toshngure.gikosh.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.toshngure.basecode.images.BaseNetworkImage;
import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.model.Category;
import ke.co.toshngure.gikosh.model.Item;

public class ItemActivity extends BaseActivity {

    private static final String EXTRA_CATEGORY = "extra_category";
    private static final String EXTRA_ITEM = "extra_item";
    @BindView(R.id.storeNameTV)
    TextView storeNameTV;
    @BindView(R.id.storeLocationTV)
    TextView storeLocationTV;
    @BindView(R.id.storeAvatarNI)
    BaseNetworkImage storeAvatarNI;
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbarSubTitle)
    TextView toolbarSubTitle;
    private Category mCategory;
    private Item mItem;

    private String[] dummyPhotos = new String[]{
            "https://lorempixel.com/400/400/fashion/?5",
            "https://lorempixel.com/400/400/fashion/?6",
            "https://lorempixel.com/400/400/fashion/?7",
            "https://lorempixel.com/400/400/fashion/?8"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        ButterKnife.bind(this);


        mCategory = getIntent().getExtras().getParcelable(EXTRA_CATEGORY);
        mItem = getIntent().getExtras().getParcelable(EXTRA_ITEM);

        toolbarTitle.setText(mItem.getName());

        storeNameTV.setText(mItem.getName());
        storeAvatarNI.loadImageFromNetwork("https://lorempixel.com/400/400/business/?9");


        CarouselView carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(dummyPhotos.length);
        carouselView.setViewListener(position -> {
            BaseNetworkImage imageNI = (BaseNetworkImage)
                    LayoutInflater.from(this).inflate(R.layout.content_item_carousel_item, null);
            imageNI.loadImageFromNetwork(dummyPhotos[position]);
            return imageNI;
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void start(Context context, Category category, Item item) {
        Intent starter = new Intent(context, ItemActivity.class);
        starter.putExtra(EXTRA_CATEGORY, category);
        starter.putExtra(EXTRA_ITEM, item);
        context.startActivity(starter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
