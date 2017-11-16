package com.mojka.poisk.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.mojka.poisk.R;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        fetchToolbar();

        if (attachBottomNavigation())
            setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (bottomNavigationView == null)
            return;

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_list:
                        startActivity(new Intent(BaseActivity.this, ProfileActivity.class));
                        finish();
                        return true;

                    case R.id.item_map:
                        startActivity(new Intent(BaseActivity.this, MapActivity.class));
                        finish();
                        return true;

                    case R.id.item_profile:
                        startActivity(new Intent(BaseActivity.this, ProfileActivity.class));
                        finish();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void fetchToolbar() {
        toolbar = findViewById(R.id.toolbar);

        if (toolbar == null)
            return;

        setupToolbar(toolbar);
    }

    private void setupToolbar(Toolbar toolbar) {
        TextView tvTitle = toolbar.findViewById(R.id.tv_title);
        tvTitle.setText(getActivityTitle());

        ImageButton ibClose = toolbar.findViewById(R.id.ib_close);
        if (getOnCloseButtonListener() != null)
            ibClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnCloseButtonListener().onclick();
                }
            });
        if (!showCloseButton())
            ibClose.setVisibility(View.GONE);
        else
            ibClose.setVisibility(View.VISIBLE);
    }

    abstract int getLayoutId();

    abstract String getActivityTitle();

    protected Boolean showCloseButton() {
        return true;
    }

    protected Boolean attachBottomNavigation() {
        return false;
    }

    abstract OnCloseButtonListener getOnCloseButtonListener();

    interface OnCloseButtonListener {
        void onclick();
    }
}
