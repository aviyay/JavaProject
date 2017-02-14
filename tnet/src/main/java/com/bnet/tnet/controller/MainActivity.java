package com.bnet.tnet.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.View;
import android.widget.Toast;

import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.tnet.R;
import com.bnet.tnet.Router;
import com.bnet.tnet.model.ActivitySearchFilter;
import com.bnet.tnet.model.BusinessSearchFilter;
import com.bnet.tnet.model.FilterDecorator;
import com.bnet.tnet.model.SearchFilter;
import com.bnet.tnet.model.UpdatesReceiver;

/**
 * Manages the navigation drawer, the swipe to refresh, the search and the lists
 */
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

    private boolean searchMsgShown=false;

    /**
     * Async reset all the known data, pull everything from the content provider and refresh the views
     */
    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    runFreshStart();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    recyclerView.getAdapter().notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }.execute();
        }
    };

    /**
     * ????
     * @param menu ????
     * @return ????
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();//(SearchView) MenuItemCompat.getActionView(searchItem);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                if(!searchMsgShown&&recyclerView.getAdapter()==travelsAdapter) {
                    Toast.makeText(getApplicationContext(), "You can search for range of prices using \"<150\" or \">50\"", Toast.LENGTH_SHORT).show();
                    searchMsgShown = true;
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        });
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

        swipeRefreshLayout.setRefreshing(true);
        refreshListener.onRefresh();

        setupAdapters();

        registerItemsSelector(nvDrawer);

        selectDrawerItem(nvDrawer.getMenu().findItem(R.id.nav_agencies));
    }

    /**
     * Find all the needed xml views
     */
    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
    }

    /**
     * Create a toggle button for the navigation drawer
     * @return the created toggle button
     */
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    /**
     * Setup the swipe-to-refresh service
     */
    private void setupSwipeRefreshListener() {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.swipeRefresh1,
                R.color.swipeRefresh2,
                R.color.swipeRefresh3);

        swipeRefreshLayout.setOnRefreshListener(refreshListener);
    }

    /**
     * Run FreshStart
     */
    private void runFreshStart() {
        UpdatesReceiver.freshStart(this);
    }

    /**
     * Setup the Agency and Travel adapters for the RecyclerView
     */
    private void setupAdapters() {
        ProvidableRepository<Activity> activityRepository = RepositoriesFactory.getActivitiesRepository();
        ProvidableRepository<Business> businessRepository = RepositoriesFactory.getBusinessesRepository();

        FilterDecorator<Activity> travelFilterDecorator = new FilterDecorator<>(activityRepository, travelSearchFilter);
        FilterDecorator<Business> agencyFilterDecorator = new FilterDecorator<>(businessRepository, agencySearchFilter);

        agenciesAdapter = new AgenciesAdapter(agencyFilterDecorator);
        travelsAdapter = new TravelsAdapter(travelFilterDecorator);

        registerAdaptersListeners();
    }

    /**
     * Register the listeners for when the user clicks on a list item
     */
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

    /**
     * Start a new activity with an attached providable
     * @param activity The activity to start
     * @param providable The attached Providable
     */
    private void startActivity(Class<? extends android.app.Activity> activity, Providable providable) {
        Router.getInstance().startActivity(this, activity, providable);
    }

    /**
     * Setup the navigation drawer items selector
     * @param navigationView
     */
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

    /**
     * Handle the selection of a navigation drawer menu item
     * @param menuItem The selected item
     */
    public void selectDrawerItem(MenuItem menuItem) {

        selectAdapterAndSearchFilter(menuItem.getItemId());
        if(menuItem.getTitle().equals("Exit"))
            mDrawer.closeDrawers();
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    /**
     * Switch to the appropriate adapter and search filter for the selected navigation drawer menu item
     * @param itemId The id of the selected navigation drawer menu item
     */
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

    /**
     * Close the application
     */
    private void closeApp() {
        finish();
        System.exit(0);
    }

    /**
     * Set the text to filter upon
     * @param searchText The text to filter upon
     */
    private void setSearchText(String searchText) {
        currentSearchFilter.setSearchText(searchText);

    }

    /**
     * Delegate the onOptionsItemSelected event to both the toggle button and the super class
     * @param item The selected item
     * @return True if the event was handled
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /**
     * Sync the toggle state after onRestoreInstanceState has occurred.
     * @param savedInstanceState The saved data from last show
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    /**
     * Pass any configuration change to the drawer toggles
     * @param newConfig The new configuration
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    boolean doubleBackToExitPressedOnce = false;

    /**
     * Do Return only if the user pressed twice on the return button
     */
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
