package com.deshbangla.shrimpandfish.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.deshbangla.shrimpandfish.adapter.ProductsAdapter;
import com.deshbangla.shrimpandfish.fragment.SearchFragment;
import com.deshbangla.shrimpandfish.model.products.Datum;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.viewmodel.AllProductsViewModel;
import com.deshbangla.shrimpandfish.viewmodel.CategoryProductsViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

public class CategoryItemsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Fragment fragment = null;
    private Fragment searchFragment = null;

    private RecyclerView allProducts;
    private ProductsAdapter productsAdapter;
    private List<Datum> productList = new ArrayList<>();

    private AllProductsViewModel allProductsViewModel;
    private CategoryProductsViewModel categoryProductsViewModel;

    private ProgressBar progressBar;

    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);
        allProductsViewModel = ViewModelProviders.of(this).get(AllProductsViewModel.class);
        categoryProductsViewModel = ViewModelProviders.of(this).get(CategoryProductsViewModel.class);

        getWindow().setStatusBarColor(Color.TRANSPARENT);

        int flag = SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        getWindow().getDecorView().setSystemUiVisibility(flag);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleMarginStart(0);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.PrimaryColor));
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        allProducts = findViewById(R.id.allProducts);
        progressBar = findViewById(R.id.progressBar);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        allProducts.setLayoutManager(gridLayoutManager);

        productsAdapter = new ProductsAdapter(this, productList, 0);
        allProducts.setAdapter(productsAdapter);
        allProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!allProducts.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1;
                        if (getIntent().getStringExtra("type").equals("all")) {
                            getAllProducts();
                        } else {
                            getProductsByCategory();
                        }

                    }
                }
            }
        });

        if (getIntent().getStringExtra("type").equals("all")) {
            getAllProducts();
        } else {
            getProductsByCategory();
        }

    }

    public void getAllProducts() {
        progressBar.setVisibility(View.VISIBLE);
        allProductsViewModel.getAllProducts(currentPage).observe(this, ProductResponse -> {
            progressBar.setVisibility(View.GONE);
            if (ProductResponse != null) {
                totalAvailablePages = ProductResponse.getMeta().getLastPage();
                if (ProductResponse.getData() != null) {
                    int OldCount = productsAdapter.getItemCount();
                    productList.addAll(ProductResponse.getData());
                    productsAdapter.notifyItemRangeInserted(OldCount, productList.size());
                }
            }
        });
    }

    public void getProductsByCategory() {
        progressBar.setVisibility(View.VISIBLE);
        categoryProductsViewModel.getCategoryProducts(getIntent().getStringExtra("type"), currentPage).observe(this, ProductResponse -> {
            progressBar.setVisibility(View.GONE);
            if (ProductResponse != null) {
                totalAvailablePages = ProductResponse.getMeta().getLastPage();
                if (ProductResponse.getData() != null) {
                    int OldCount = productsAdapter.getItemCount();
                    productList.addAll(ProductResponse.getData());
                    productsAdapter.notifyItemRangeInserted(OldCount, productList.size());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.category_item_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        MenuItem cart = menu.findItem(R.id.cart);
        MenuItem wishlist = menu.findItem(R.id.wishlist);

        cart.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(CategoryItemsActivity.this, CartWishlistActivity.class);
                intent.putExtra("activity", 1);
                intent.putExtra("page", "cart");
                intent.putExtra("type",getIntent().getStringExtra("type"));
                intent.putExtra("title",getIntent().getStringExtra("title"));
                startActivity(intent);
                finish();
                return false;
            }
        });

        wishlist.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(CategoryItemsActivity.this, CartWishlistActivity.class);
                intent.putExtra("activity", 1);
                intent.putExtra("page", "wishlist");
                intent.putExtra("type",getIntent().getStringExtra("type"));
                intent.putExtra("title",getIntent().getStringExtra("title"));
                startActivity(intent);
                finish();
                return false;
            }
        });

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
        finish();
        startActivity(new Intent(CategoryItemsActivity.this, MainActivity.class));
    }
}