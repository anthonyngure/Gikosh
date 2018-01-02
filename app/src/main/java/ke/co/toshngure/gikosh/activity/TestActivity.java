package ke.co.toshngure.gikosh.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;

import com.synnapps.carouselview.CarouselView;

import ke.co.toshngure.basecode.images.BaseNetworkImage;
import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.view.SpecificationView;

public class TestActivity extends BaseActivity {

    private String[] photos = new String[]{
            "https://lorempixel.com/400/400/fashion/?5",
            "https://lorempixel.com/400/400/fashion/?6",
            "https://lorempixel.com/400/400/fashion/?7",
            "https://lorempixel.com/400/400/fashion/?8"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_test);
        setContentView(R.layout.activity_item);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        CarouselView carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(photos.length);
        carouselView.setViewListener(position -> {
            BaseNetworkImage imageNI = (BaseNetworkImage)
                    LayoutInflater.from(this).inflate(R.layout.content_item_carousel_item, null);
            imageNI.loadImageFromNetwork(photos[position]);
            return imageNI;
        });
        SpecificationView sizeSV = findViewById(R.id.sizeSV);
        sizeSV.addChips("XS", "S", "M", "L", "XL", "XXL");
        sizeSV.getChipCloud().setSelectedChip(3);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, TestActivity.class);
        context.startActivity(starter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
