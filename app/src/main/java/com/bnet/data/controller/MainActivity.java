package com.bnet.data.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bnet.data.R;
import com.bnet.data.model.backend.AccountsRepository;
import com.bnet.data.model.backend.RepositoriesFactory;
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

                AccountsRepository repository = RepositoriesFactory.getAccountsRepository();

                if (repository.getOrNull(usernameField.getText().toString()) != null){
                    Toast.makeText(getApplicationContext(), R.string.username_is_taken_msg, Toast.LENGTH_SHORT).show();
                    return;
                }

                repository.add(new Account(usernameField.getText().toString(), passwordField.getText().toString()));
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
                Account account = RepositoriesFactory.getAccountsRepository().getOrNull(usernameField.getText().toString());
                if (account != null)
                    if (account.getPassword().equals(passwordField.getText().toString())) {
                        doSignIn(account);
                        return;
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        if(sharedPreferences.getBoolean("isLogIn",false))
            goToMenu();
        usernameField=(EditText)findViewById(R.id.usernameField);
        passwordField=(EditText)findViewById(R.id.passwordField);
        initializeButtons();
    }

    private void goToMenu() {
        Intent k = new Intent(this, Menu.class);
        startActivity(k);
    }

    private void doSignIn(Account item) {
        if(((CheckBox)findViewById(R.id.checkBox)).isChecked()) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLogIn", true);
            editor.commit();
        }
            Toast.makeText(getApplicationContext(), "TEMP: Signed in - "+ item.getUsername(), Toast.LENGTH_SHORT).show();
        goToMenu();
    }

}
