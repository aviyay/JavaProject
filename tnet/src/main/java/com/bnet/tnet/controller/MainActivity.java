package com.bnet.tnet.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.EntitiesSamples;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.tnet.R;
import com.bnet.tnet.model.ActivitySearchFilter;
import com.bnet.tnet.model.BusinessSearchFilter;
import com.bnet.tnet.model.FilterDecorator;
import com.bnet.tnet.model.SearchFilter;
import com.bnet.tnet.model.UpdatesReceiver;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private AgenciesAdapter agenciesAdapter;
    private TravelsAdapter travelsAdapter;

    private ActivitySearchFilter travelSearchFilter = new ActivitySearchFilter();
    private BusinessSearchFilter agencySearchFilter = new BusinessSearchFilter();
    private SearchFilter currentSearchFilter;

    static {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground(Void... params) {

                RepositoriesFactory.getBusinessesRepository().addAndReturnAssignedId(EntitiesSamples.makeBusiness());
                RepositoriesFactory.getBusinessesRepository().addAndReturnAssignedId(EntitiesSamples.makeBusiness2());
                RepositoriesFactory.getActivitiesRepository().addAndReturnAssignedId(EntitiesSamples.makeActivity());
                RepositoriesFactory.getActivitiesRepository().addAndReturnAssignedId(EntitiesSamples.makeActivity2());
                return null;
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();//(SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setSearchText(newText);
                recyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

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

        setupSwipeRefreshListener();

        //UpdatesReceiver.freshStart(this); //TODO: enable this

        setupAdapters();

        registerItemsSelector(nvDrawer);

        selectDrawerItem(nvDrawer.getMenu().findItem(R.id.nav_agencies));
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupSwipeRefreshListener() {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.swipeRefresh1,
                R.color.swipeRefresh2,
                R.color.swipeRefresh3);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                runFreshStart();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void runFreshStart() {
        UpdatesReceiver.freshStart(this);
        recyclerView.getAdapter().notifyDataSetChanged();
         swipeRefreshLayout.setRefreshing(false);
    }

    private void setupAdapters() {
        ProvidableRepository<Activity> activityRepository = RepositoriesFactory.getActivitiesRepository();
        ProvidableRepository<Business> businessRepository = RepositoriesFactory.getBusinessesRepository();

        FilterDecorator travelFilterDecorator = new FilterDecorator(activityRepository, travelSearchFilter);
        FilterDecorator agencyFilterDecorator = new FilterDecorator(businessRepository, agencySearchFilter);

        agenciesAdapter = new AgenciesAdapter(agencyFilterDecorator);
        travelsAdapter = new TravelsAdapter(travelFilterDecorator);

        registerAdaptersListeners();
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

        selectAdapterAndSearchFilter(menuItem.getItemId());

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    void selectAdapterAndSearchFilter(int itemId) {
        RecyclerView.Adapter chosen;

        switch (itemId) {
            case R.id.nav_agencies:
                chosen = agenciesAdapter;
                currentSearchFilter = agencySearchFilter;
                break;
            case R.id.nav_travels:
                chosen = travelsAdapter;
                currentSearchFilter = travelSearchFilter;
                break;
            case R.id.nav_exit:

                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle(getString(R.string.exit));
                alertDialog.setMessage(getString(R.string.exit_msg));
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                closeApp();
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return;
            default:
                chosen = null;
                currentSearchFilter = null;
                break;
        }

        recyclerView.setAdapter(chosen);
    }

    private void closeApp() {
        finish();
        System.exit(0);
    }

    private void setSearchText(String searchText) {
        currentSearchFilter.setSearchText(searchText);

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
