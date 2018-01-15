package ke.co.toshngure.gikosh.utils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import ke.co.toshngure.basecode.utils.BaseUtils;
import ke.co.toshngure.gikosh.R;


/**
 * Created by Anthony Ngure on 06/01/2018.
 * Email : anthonyngure25@gmail.com.
 */

public class DrawerManager {

    private AppCompatActivity mContext;
    private Drawer mDrawer;

    private DrawerManager(AppCompatActivity activity, Toolbar toolbar) {
        this.mContext = activity;
        init(toolbar);
    }

    public static DrawerManager init(AppCompatActivity activity, Toolbar toolbar) {
        return new DrawerManager(activity, toolbar);
    }

    private void init(Toolbar toolbar) {
//User user = getUser();

        AccountHeaderBuilder accountHeaderBuilder = new AccountHeaderBuilder()
                .withCloseDrawerOnProfileListClick(false)
                .withNameTypeface(Typeface.DEFAULT_BOLD)
                .withHeaderBackground(new ColorDrawable(BaseUtils.getColor(mContext, R.attr.colorAccent)))
                .withTextColor(Color.WHITE)
                .withActivity(mContext)
                .withThreeSmallProfileImages(true)
                .withAlternativeProfileHeaderSwitching(true)
                .withHeaderBackgroundScaleType(ImageView.ScaleType.CENTER_CROP)
                .withPaddingBelowHeader(true)

                .withProfileImagesClickable(false)
                .withSelectionListEnabled(true)
                .withCloseDrawerOnProfileListClick(false)
                .addProfiles(new ProfileDrawerItem()
                                .withName("User Account")
                                .withIcon(R.drawable.profile)
                                .withTextColorRes(R.color.colorAccent)
                                .withNameShown(true)
                                .withEmail("+2547123456789"),
                        new ProfileDrawerItem()
                                .withName("Business Account")
                                .withIcon(R.drawable.profile)
                                .withTextColorRes(R.color.colorAccent)
                                .withNameShown(true)
                                .withEmail("+2547123456789"));

        mDrawer = new DrawerBuilder().withActivity(mContext)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(true)
                .withTranslucentStatusBar(true)
                .withAccountHeader(accountHeaderBuilder.build())
                .withHeaderPadding(true)
                .withCloseOnClick(true)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        new SecondaryDrawerItem().withName("Invite a friend").withIcon(R.drawable.ic_person_add_black_24dp),
                        new SecondaryDrawerItem().withName("About Us").withIcon(R.drawable.ic_info_black_24dp),
                        new SecondaryDrawerItem().withName("Privacy Policy").withIcon(R.drawable.ic_security_black_24dp),
                        new SecondaryDrawerItem().withName("Terms of Use").withIcon(R.drawable.ic_warning_black_24dp),
                        new SecondaryDrawerItem().withName("F.A.Q").withIcon(R.drawable.ic_question_answer_black_24dp)
                )
                .addStickyDrawerItems(
                        new SecondaryDrawerItem().withName("Sign Out").withIcon(R.drawable.ic_sign_out_black_24dp),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("GIKOSH @ 2018").withIcon(R.drawable.ic_copyright_black_24dp)
                )
                //.withShowDrawerUntilDraggedOpened(true)
                .build();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mDrawer.getHeader().setPadding(0, 24, 0, 0);
        }
    }

    public Drawer getDrawer() {
        return mDrawer;
    }
}
