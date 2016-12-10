package com.bnet.data.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bnet.data.R;

public class Menu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initializeAddBusinessButton();
    }

    private void initializeAddBusinessButton() {
        findViewById(R.id.addBusinessBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(getApplicationContext(),BusinessEditor.class);
                startActivity(k);
            }
        });
    }
}
