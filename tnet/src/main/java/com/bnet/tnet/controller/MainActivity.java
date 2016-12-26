package com.bnet.tnet.controller;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.tnet.R;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private RecyclerView recyclerView;

    private AgenciesAdapter agenciesAdapter = new AgenciesAdapter();
    private TravelsAdapter travelsAdapter = new TravelsAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        // Set a Toolbar to replace the ActionBar.
        setSupportActionBar(toolbar);

        // Setup Toggle icon (the animation of 3 lines to an arrow)
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        registerAdaptersListeners();

        registerItemsSelector(nvDrawer);

        selectDrawerItem(nvDrawer.getMenu().findItem(R.id.nav_agencies));
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void registerAdaptersListeners() {
        agenciesAdapter.setOnItemClickListener(new AgenciesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Business agency) {
                startActivity(AgencyDetails.class, agency);
            }
        });
        travelsAdapter.setOnItemClickListener(new TravelsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Activity travel) {
                startActivity(TravelDetails.class, travel);
            }
        });
    }

    private void startActivity(Class activity, Providable providable) {

        Intent intent = new Intent(this, activity);

        Bundle bundle = ProvidableUtils.bundleConvert(providable);
        intent.putExtra(ProvidableUtils.getURIPath(providable), bundle);

        startActivity(intent);
    }

    private void registerItemsSelector(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    public void selectDrawerItem(MenuItem menuItem) {

        recyclerView.setAdapter(selectAdapter(menuItem.getItemId()));

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    RecyclerView.Adapter selectAdapter(int itemId) {
        switch (itemId) {
            case R.id.nav_agencies:
                return agenciesAdapter;
            case R.id.nav_travels:
                return travelsAdapter;
            default:
                return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
