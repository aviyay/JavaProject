package com.bnet.tnet.controller;

import android.app.FragmentManager;
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

import org.w3c.dom.Text;

public class WebActivity extends AppCompatActivity {

    private TextView agencyName;

    private FloatingActionButton homeButton;

    private WebFragment webFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        findViews();
        setFragment(getIntent().getStringExtra("LINK"));
        Toast.makeText(this, R.string.double_back_to_exit,Toast.LENGTH_LONG).show();
    }

    private void setFragment(String link) {
        Bundle bundle=new Bundle();
        bundle.putString("LINK",link);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        webFragment = new WebFragment();

        webFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_container, webFragment);
        fragmentTransaction.commit();
    }

    private void findViews() {
        agencyName=(TextView)findViewById(R.id.agencyName);
        agencyName.setText(getIntent().getStringExtra("NAME"));

        homeButton=(FloatingActionButton)findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webFragment.goHome();
            }
        });
    }

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
