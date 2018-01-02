package ke.co.toshngure.gikosh.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ke.co.toshngure.gikosh.R;

public class HuntActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, HuntActivity.class);
        context.startActivity(starter);
    }

}
