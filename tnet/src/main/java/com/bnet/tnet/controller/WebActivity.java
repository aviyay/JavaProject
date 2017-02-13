package com.bnet.tnet.controller;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bnet.tnet.R;

/**
 * This activity helps with surfing the web
 */
public class WebActivity extends AppCompatActivity {

    private WebFragment webFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        setupViews();
        setFragment(getIntent().getStringExtra("LINK"));
        Toast.makeText(this, R.string.double_back_to_exit,Toast.LENGTH_LONG).show();
    }

    /**
     * Setup the WebFragment
     * @param link The link to open
     */
    private void setFragment(String link) {
        Bundle bundle=new Bundle();
        bundle.putString("LINK",link);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        webFragment = new WebFragment();

        webFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_container, webFragment);
        fragmentTransaction.commit();
    }

    /**
     * Setup the xml views
     */
    private void setupViews() {
        TextView agencyName = (TextView) findViewById(R.id.agencyName);
        agencyName.setText(getIntent().getStringExtra("NAME"));

        FloatingActionButton homeButton = (FloatingActionButton) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webFragment.goHome();
            }
        });
    }

    /**
     * ???
     * @param keyCode ???
     * @param event ???
     * @return ???
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    boolean doubleBackToExitPressedOnce = false;

    /**
     * Exit the activity only if the user clicked the return button twice fast
     */
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        if(!webFragment.goBack())
            Toast.makeText(this,R.string.double_back_to_exit,Toast.LENGTH_LONG).show();

        this.doubleBackToExitPressedOnce = true;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 500);
    }
}
