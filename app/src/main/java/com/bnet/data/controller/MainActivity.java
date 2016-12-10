package com.bnet.data.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bnet.data.R;
import com.bnet.data.model.backend.DatabaseFactory;
import com.bnet.data.model.entities.Account;

public class MainActivity extends Activity {

    EditText usernameField;
    EditText passwordField;

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    private void initializeButtons()
    {
        initializeRegisterButton();
        initializeSignInButton();
    }

    private void initializeRegisterButton() {
        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateFields())
                    return;
                for (Account item : DatabaseFactory.getDatabase().getAllAccounts()) {
                    if (item.getUsername().equals(usernameField.getText().toString())) {
                        Toast.makeText(getApplicationContext(), R.string.username_is_taken_msg, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                DatabaseFactory.getDatabase().addAccount(new Account(usernameField.getText().toString(), passwordField.getText().toString()));
                Toast.makeText(getApplicationContext(), "TEMP: Acoount registerd - "+ usernameField.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initializeSignInButton() {
       findViewById(R.id.signInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateFields())
                    return;
                for (Account item: DatabaseFactory.getDatabase().getAllAccounts()) {
                    if(item.getUsername().equals(usernameField.getText().toString()))
                        if(item.getPassword().equals(passwordField.getText().toString())) {
                            doSignIn(item);
                            return;
                        }
                }
                Toast.makeText(getApplicationContext(), R.string.password_or_username_incorrect,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateFields() {
        if(isEmpty(usernameField)||isEmpty(passwordField)) {
            Toast.makeText(getApplicationContext(), R.string.empty_fields_msg, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameField=(EditText)findViewById(R.id.usernameField);
        passwordField=(EditText)findViewById(R.id.passwordField);
        initializeButtons();
    }

    private void doSignIn(Account item) {
       Toast.makeText(getApplicationContext(), "TEMP: Signed in - "+ item.getUsername(), Toast.LENGTH_SHORT).show();
        Intent k = new Intent(this, Menu.class);
        startActivity(k);
    }

}
