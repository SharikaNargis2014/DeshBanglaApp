package com.deshbangla.shrimpandfish.activities;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.deshbangla.shrimpandfish.fragment.HomeFragment;
import com.deshbangla.shrimpandfish.fragment.ProfileFragment;
import com.deshbangla.shrimpandfish.fragment.SearchFragment;
import com.deshbangla.shrimpandfish.fragment.myCartFragment;
import com.deshbangla.shrimpandfish.fragment.myOrdersFragment;
import com.deshbangla.shrimpandfish.fragment.LoginFragment;
import com.deshbangla.shrimpandfish.fragment.wishListFragment;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.SharedPreferenceData;
import com.deshbangla.shrimpandfish.utils.Utils;
import com.deshbangla.shrimpandfish.menu.DrawerAdapter;
import com.deshbangla.shrimpandfish.menu.DrawerItem;
import com.deshbangla.shrimpandfish.menu.SimpleItem;
import com.deshbangla.shrimpandfish.viewmodel.PendingOrdersViewModel;
import com.sagarkoli.chetanbottomnavigation.chetanBottomNavigation;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    public static chetanBottomNavigation navBar;
    private Toolbar toolbar;
    private SlidingRootNav slidingRootNav;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    public static DrawerAdapter adapter;

    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new wishListFragment(1);
    final Fragment fragment3 = new myCartFragment(1);
    final Fragment fragment4 = new myOrdersFragment();
    final Fragment fragment5 = new LoginFragment(1, "main");
    final Fragment fragment6 = new ProfileFragment();
    final FragmentManager fm = getSupportFragmentManager();
    private Fragment active = fragment1;

    private static final int home = 0;
    private static final int wishList = 1;
    private static final int cart = 2;
    private static final int orders = 3;
    private static final int account = 4;

    boolean doubleBackToExitPressedOnce = false;
    private Fragment fragment = null;
    private Fragment searchFragment = null;

    private PendingOrdersViewModel pendingOrdersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        int flag = SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        getWindow().getDecorView().setSystemUiVisibility(flag);
        getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_RESIZE);

        navBar = findViewById(R.id.navBar);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleMarginStart(0);
        toolbar.setTitle("");
        toolbar.setLogo(R.drawable.logo_toolbar);
        setSupportActionBar(toolbar);

        pendingOrdersViewModel = ViewModelProviders.of(this).get(PendingOrdersViewModel.class);

        //navigation drawer start
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(home).setChecked(true),
                createItemFor(wishList),
                createItemFor(cart),
                createItemFor(orders),
                createItemFor(account)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        if (getIntent().getStringExtra("login") == null) {
            adapter.setSelected(home);
        } else {
            adapter.setSelected(account);
        } //navigation drawer end

        //bottom nav start
        navBar.add(new chetanBottomNavigation.Model(home, R.drawable.ic_home));
        navBar.add(new chetanBottomNavigation.Model(wishList, R.drawable.ic_wishlist));
        navBar.add(new chetanBottomNavigation.Model(cart, R.drawable.ic_orders));
        navBar.add(new chetanBottomNavigation.Model(orders, R.drawable.ic_cart));
        navBar.add(new chetanBottomNavigation.Model(account, R.drawable.ic_person));

        navBar.setOnShowListener(item -> {
            switch (item.getId()) {
                case home:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    break;
                case wishList:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    break;
                case cart:
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    break;
                case orders:
                    fm.beginTransaction().hide(active).show(fragment4).commit();
                    active = fragment4;
                    break;
                case account:
                    if (SharedPreferenceData.getUserPreferenceData(MainActivity.this) != null) {
                        fm.beginTransaction().hide(active).show(fragment6).commit();
                        active = fragment6;
                    } else {
                        fm.beginTransaction().hide(active).show(fragment5).commit();
                        active = fragment5;
                    }
                    break;
                default:
                    active = fragment1;

            }
        });
        if (SharedPreferenceData.getUserPreferenceData(MainActivity.this) != null) {
            fm.beginTransaction().add(R.id.fragment_container, fragment5, "5").hide(fragment6).commit();
        } else {
            fm.beginTransaction().add(R.id.fragment_container, fragment5, "5").hide(fragment5).commit();
        }

        fm.beginTransaction().add(R.id.fragment_container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment1, "1").commit();

        navBar.setOnClickMenuListener(item -> adapter.setSelected(item.getId()));

        navBar.setOnReselectListener(item -> {

        });

        if (getIntent().getStringExtra("login") == null) {
            navBar.show(home, true);
        } else {
            navBar.show(account, true);
        } //bottom nav end

        if (SharedPreferenceData.getUserPreferenceData(this)!= null){
            getPendingOrders();
//            Utils.showToastShort(this, String.valueOf(SharedPreferenceData.getUserPreferenceData(this).getId()));
        }
    }

    private void getPendingOrders() {
        pendingOrdersViewModel.getPendingOrders(SharedPreferenceData.getUserPreferenceData(this).getId()).observe(this, orderDetailsResponse -> {
            if (orderDetailsResponse!=null){
                if (orderDetailsResponse.getOrders().size()>0){
                    navBar.setCount(3, String.valueOf(orderDetailsResponse.getOrders().size()));
                } else {
                    navBar.clearCount(3);
                }
            }
        });
    }

    //navigation drawer methods
    @Override
    public void onItemSelected(int position) {

        if (position == home) {

            fragment = new HomeFragment();
            navBar.show(home, true);

        } else if (position == wishList) {

            fragment = new wishListFragment(1);
            navBar.show(wishList, true);

        } else if (position == cart) {

            fragment = new myCartFragment(1);
            navBar.show(cart, true);

        } else if (position == orders) {

            fragment = new myOrdersFragment();
            navBar.show(orders, true);

        } else if (position == account) {
            if (SharedPreferenceData.getUserPreferenceData(MainActivity.this) != null) {
                fragment = new ProfileFragment();
            } else {
                fragment = new LoginFragment(1, "main");
            }
            navBar.show(account, true);

        }
        slidingRootNav.closeMenu();
        showFragment(fragment);
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.PrimaryColor))
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.PrimaryColor))
                .withSelectedTextTint(color(R.color.PrimaryColor));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    } //navigation drawer methods end

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_item, menu);

        MenuItem menuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setQueryHint("Search");

        SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(searchFragment).commit();
                }
                if (!newText.equals("")) {
                    searchFragment = new SearchFragment(newText);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.fragment_search, searchFragment)
                            .commit();
                }
                return true;
            }
        };

        searchView.setOnQueryTextListener(onQueryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {


        if (slidingRootNav.isMenuClosed()) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Utils.showToastShort(MainActivity.this, "Please click again to exit");

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        } else {
            slidingRootNav.closeMenu(true);
        }

    }
}